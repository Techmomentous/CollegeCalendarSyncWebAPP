
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
import com.cc.server.dao.impl.TimeTableDB;
import com.cc.server.dao.modal.TimeTable;
import com.cc.server.utils.Constants;
import com.cc.server.utils.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class TimeTableService
 */
@WebServlet("/TimeTableService")
@MultipartConfig
public class TimeTableService extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private static final String	TAG					= TimeTableService.class.getName();
	DAOFactory					daoFactory;
	TimeTableDB					timeTableDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TimeTableService() {
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
		Long userID, autoid;
		String dataJson;
		String uniqueID;
		try
		{
			writer = response.getWriter();
			if (daoFactory == null)
			{
				daoFactory = DAOFactory.getInstance(Constants.PROPERTIES_JDBC_KEY);
			}

			if (timeTableDB == null)
			{
				timeTableDB = daoFactory.getTimeTableDB();
			}

			String serviceRequest = request.getParameter("serviceRequest");
			System.out.println("ServiceRequest : " + serviceRequest + " " + TAG);
			switch (serviceRequest) {
				case "GetTimeTable":
					userID = Long.valueOf(request.getParameter(Constants.USER_ID_KEY));
					jsonArray = timeTableDB.findByUserID(userID);
					writer.write(jsonArray.toString());
					break;
				case "AddTimeTable":
					Part part = request.getPart("ttAttachment");
					dataJson = request.getParameter("dataJson");
					URL reconstructedURL = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), "/" + Constants.DATA_FOLDER_PARAM + "/");
					jsonParser = new JsonParser();
					jsonArray = jsonParser.parse(dataJson).getAsJsonArray();
					boolean isAdded = addTimeTable(part, jsonArray, reconstructedURL);
					jsonObject = new JsonObject();
					String message = "";
					if (isAdded)
					{
						message = "Sucessfully Added Time Table.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Added Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						message = "Failed To Add Time Table.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Add Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					response.sendRedirect("message.jsp?message=" + message);
					//					writer.write(jsonObject.toString());
					break;
				case "RemoveTimeTable":
					uniqueID = request.getParameter(Constants.UNIQUE_ID_KEY);
					jsonObject = new JsonObject();
					if (timeTableDB.delete(uniqueID))
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
				case "updateTimeTable":
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
		TimeTable timeTable = new TimeTable();
		timeTable.setUniqueID(dataObject.get(Constants.UNIQUE_ID_KEY).getAsString());
		timeTable.setSubject(dataObject.get(Constants.SUBJECT_KEY).getAsString());
		timeTable.setTime(dataObject.get(Constants.TIME_KEY).getAsString());
		timeTable.setStartDate(dataObject.get(Constants.DATE_KEY).getAsString());
		timeTable.setEndDate(dataObject.get(Constants.DATE_KEY).getAsString());
		timeTable.setSetReminder(dataObject.get(Constants.SET_REMINDER_KEY).getAsString());
		timeTable.setSetNotif(dataObject.get(Constants.SET_NOTIF_KEY).getAsString());
		timeTable.setUserID(dataObject.get(Constants.USER_ID_KEY).getAsLong());
		timeTable.setYear(dataObject.get(Constants.YEAR_KEY).getAsString());
		timeTable.setBranch(dataObject.get(Constants.BRANCH_KEY).getAsString());

		timeTable = timeTableDB.update(timeTable);
		if (timeTable.isUpdate())
		{
			return true;
		}
		return false;
	}

	private boolean addTimeTable(Part part, JsonArray jsonArray, URL reconstructedURL) throws IOException {
		boolean isAdded = false;
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
			TimeTable timeTable = new TimeTable(jsonObject.get(Constants.SUBJECT_KEY).getAsString(), jsonObject.get(Constants.TIME_KEY).getAsString(), jsonObject
					.get(Constants.DATE_KEY).getAsString(), jsonObject.get(Constants.DATE_KEY).getAsString(), jsonObject.get(Constants.SET_REMINDER_KEY).getAsString(), jsonObject
					.get(Constants.SET_NOTIF_KEY).getAsString(), jsonObject.get(Constants.BRANCH_KEY).getAsString());
			timeTable.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
			timeTable.setUserID(jsonObject.get(Constants.USER_ID_KEY).getAsLong());
			timeTable.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());
			timeTable.setAttachment(attachment);
			timeTable.setAttachmenturl(attachmentURL);
			timeTable = timeTableDB.create(timeTable);
			if (timeTable.getId() == null)
			{
				return isAdded;
			}
			else
			{
				isAdded = true;

			}
		}
		return isAdded;
	}

}
