
package com.cc.server.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.impl.ExamDB;
import com.cc.server.dao.modal.Exam;
import com.cc.server.utils.Constants;
import com.cc.server.utils.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class ExamService
 */
@WebServlet("/ExamService")
@MultipartConfig
public class ExamService extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private static final String	TAG					= ExamService.class.getName();
	DAOFactory					daoFactory;
	ExamDB						examDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExamService() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		JsonArray jsonArray;
		JsonParser jsonParser;
		JsonObject jsonObject;
		Long userID;
		String uniqueID;
		String dataJson;
		try
		{
			writer = response.getWriter();
			if (daoFactory == null)
			{
				daoFactory = DAOFactory.getInstance(Constants.PROPERTIES_JDBC_KEY);
			}
			if (examDB == null)
			{
				examDB = daoFactory.getExamDB();
			}

			String serviceRequest = request.getParameter("serviceRequest");
			System.out.println("ServiceRequest : " + serviceRequest + " " + TAG);
			switch (serviceRequest) {
				case "GetExamSchedules":
					userID = Long.valueOf(request.getParameter(Constants.USER_ID_KEY));
					jsonArray = examDB.getAllExamByUserID(userID);
					writer.write(jsonArray.toString());
					break;
				case "AddExamSchedules":
					Part part = request.getPart("examAttachment");
					dataJson = request.getParameter("dataJson");
					URL reconstructedURL = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), "/" + Constants.DATA_FOLDER_PARAM + "/");
					jsonParser = new JsonParser();
					jsonArray = jsonParser.parse(dataJson).getAsJsonArray();
					boolean isAdded = addExamSchedules(part, jsonArray, reconstructedURL);
					jsonObject = new JsonObject();
					String message = "";
					if (isAdded)
					{
						message = "Sucessfully Added Exam Details.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Added Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						message = "Failed To Add Exam Details.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Add Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					response.sendRedirect("message.jsp?message=" + message);
					break;
				case "UpdateExamSchedule":
					dataJson = request.getParameter("dataJson");
					jsonParser = new JsonParser();
					JsonObject dataObject = jsonParser.parse(dataJson).getAsJsonObject();
					boolean isUpdated = updateDetails(dataObject);
					jsonObject = new JsonObject();
					if (isUpdated)
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Update Time Table Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Update Time Table Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					writer.write(jsonObject.toString());
					break;
				case "RemoveExamSchedule":
					uniqueID = request.getParameter(Constants.UNIQUE_ID_KEY);
					jsonObject = new JsonObject();
					if (examDB.delete(uniqueID))
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Removed Time Table Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Remove Time Table Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					writer.write(jsonObject.toString());
					break;
			}
		}
		catch (NullPointerException e)
		{
			jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sorry for inconvenience caused to you. Please Try Later");
			jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
			writer.write(jsonObject.toString());
			System.err.println("Error in Process Request " + e + " " + TAG);
		}
		catch (Exception e)
		{
			jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sorry for inconvenience caused to you. Please Try Later");
			jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
			writer.write(jsonObject.toString());
			System.err.println("Error in Process Request " + e + " " + TAG);

		}
	}

	private boolean updateDetails(JsonObject dataObject) {
		boolean isUpdated = false;
		Exam exam = new Exam();
		exam.setUniqueID(dataObject.get(Constants.UNIQUE_ID_KEY).getAsString());
		exam.setSubject(dataObject.get(Constants.SUBJECT_KEY).getAsString());
		exam.setTime(dataObject.get(Constants.TIME_KEY).getAsString());
		exam.setStartDate(dataObject.get(Constants.DATE_KEY).getAsString());
		exam.setEndDate(dataObject.get(Constants.DATE_KEY).getAsString());
		exam.setSetReminder(dataObject.get(Constants.SET_REMINDER_KEY).getAsString());
		exam.setSetNotif(dataObject.get(Constants.SET_NOTIF_KEY).getAsString());
		exam.setBranch(dataObject.get(Constants.BRANCH_KEY).getAsString());
		exam.setYear(dataObject.get(Constants.YEAR_KEY).getAsString());
		exam = examDB.update(exam);
		if (exam.isUpdated())
		{
			return true;
		}

		return isUpdated;
	}

	private boolean addExamSchedules(Part part, JsonArray jsonArray, URL reconstructedURL) throws IOException {
		boolean isAdded = true;
		String recvFileName = Utility.getFileName(part).replaceAll(" ", "_");
		Long id = System.currentTimeMillis();
		String directoryPath = Constants.DATA_FOLDER + File.separator + id;
		File attachmentFolder = new File(directoryPath);
		if (!attachmentFolder.exists())
		{
			attachmentFolder.mkdirs();
		}
		String attachment = directoryPath + "\\" + recvFileName;
		String attachmentURL = reconstructedURL.toExternalForm() + "/" + id + "/" + recvFileName;
		boolean isSaved = Utility.saveUploadFiles(part, directoryPath, recvFileName);
		for (int i = 0; i < jsonArray.size(); i++)
		{
			JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
			Exam exam = new Exam();
			exam.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());
			exam.setSubject(jsonObject.get(Constants.SUBJECT_KEY).getAsString());
			exam.setTime(jsonObject.get(Constants.TIME_KEY).getAsString());
			exam.setStartDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			exam.setEndDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			exam.setSetReminder(jsonObject.get(Constants.SET_REMINDER_KEY).getAsString());
			exam.setSetNotif(jsonObject.get(Constants.SET_NOTIF_KEY).getAsString());
			exam.setBranch(jsonObject.get(Constants.BRANCH_KEY).getAsString());
			exam.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
			exam.setUserid(jsonObject.get(Constants.USER_ID_KEY).getAsLong());
			exam.setAttachment(attachment);
			exam.setAttachmenturl(attachmentURL);
			exam = examDB.create(exam);
			if (exam.getId() == null)
			{
				return false;
			}

		}
		return isAdded;
	}

}
