
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

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.impl.AssignmentDB;
import com.cc.server.dao.modal.Assignment;
import com.cc.server.utils.Constants;
import com.cc.server.utils.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class AssignmentService
 */
@WebServlet("/AssignmentService")
@MultipartConfig
public class AssignmentService extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private static final String	TAG					= AssignmentService.class.getName();
	DAOFactory					daoFactory;
	AssignmentDB				assignmentDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignmentService() {
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
			if (assignmentDB == null)
			{
				assignmentDB = daoFactory.getAssignmentDB();
			}
			String serviceRequest = request.getParameter("serviceRequest");
			System.out.println("ServiceRequest : " + serviceRequest + " " + TAG);
			switch (serviceRequest) {
				case "GetAllAssignment":
					userID = Long.valueOf(request.getParameter(Constants.USER_ID_KEY));
					jsonArray = assignmentDB.getAllExamByUserID(userID);
					writer.write(jsonArray.toString());
					break;
				case "AddAssignment":
					Part part = request.getPart("assignmentAttachment");
					URL reconstructedURL = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), "/" + Constants.DATA_FOLDER_PARAM + "/");
					dataJson = request.getParameter("dataJson");
					jsonParser = new JsonParser();
					jsonArray = jsonParser.parse(dataJson).getAsJsonArray();
					boolean isAdded = addAssigments(part, jsonArray, reconstructedURL);
					jsonObject = new JsonObject();
					String message = "";
					if (isAdded)
					{
						message = "Sucessfully Added Assignment.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Added Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						message = "Failed To Add Assignment.";
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Add Time Table.");
						//						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					response.sendRedirect("message.jsp?message=" + message);
					break;
				case "UpdateAssignment":
					dataJson = request.getParameter("dataJson");
					jsonParser = new JsonParser();
					JsonObject dataObject = jsonParser.parse(dataJson).getAsJsonObject();
					boolean isUpdated = updateDetails(dataObject);
					jsonObject = new JsonObject();
					if (isUpdated)
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Update Assignment Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Update Assignment Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					writer.write(jsonObject.toString());
					break;
				case "RemoveAssignment":
					uniqueID = request.getParameter(Constants.UNIQUE_ID_KEY);
					jsonObject = new JsonObject();
					if (assignmentDB.delete(uniqueID))
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Removed Assignment Entry.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Remove Assignment Entry.");
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

	private boolean uploadFile(HttpServletRequest request, JsonArray jsonArray) {

		try
		{

			Part uploaded_fileName = request.getPart("uploaded_file");
			String recvFileName = Utility.getFileName(uploaded_fileName);
			String filePath = Constants.DATA_FOLDER + recvFileName;

			File outFolder = new File(filePath);
			if (!outFolder.exists())
			{
				outFolder.mkdirs();
			}
			boolean isSaved = Utility.saveUploadFiles(uploaded_fileName, filePath, recvFileName);
			URL reconstructedURL = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), "/" + Constants.DATA_FOLDER_PARAM + "/" + recvFileName);
			System.out.println("New File Upload : " + recvFileName);
			System.out.println("New File Upload url : " + reconstructedURL.toExternalForm());

			if (daoFactory == null)
			{
				daoFactory = DAOFactory.getInstance(Constants.PROPERTIES_JDBC_KEY);
			}

		}
		catch (DAOException e)
		{
			System.err.println("Error In Processing uploadFile  Request : " + e);
		}
		catch (NullPointerException e)
		{
			System.err.println("Error In Processing uploadFile  Request : " + e);
		}
		catch (Exception e)
		{
			System.err.println("Error In Processing uploadFile Request : " + e);
		}

		return false;
	}

	private boolean updateDetails(JsonObject jsonObject) {
		boolean isUpdated = false;
		Assignment assignment = new Assignment();
		assignment.setSubject(jsonObject.get(Constants.SUBJECT_KEY).getAsString());
		assignment.setAssignment(jsonObject.get(Constants.ASSIGNMENT_KEY).getAsString());
		assignment.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());
		assignment.setTime(jsonObject.get(Constants.TIME_KEY).getAsString());
		assignment.setStartDate(jsonObject.get(Constants.DATE_KEY).getAsString());
		assignment.setEndDate(jsonObject.get(Constants.DATE_KEY).getAsString());
		assignment.setSetReminder(jsonObject.get(Constants.SET_REMINDER_KEY).getAsString());
		assignment.setSetNotif(jsonObject.get(Constants.SET_NOTIF_KEY).getAsString());
		assignment.setBranch(jsonObject.get(Constants.BRANCH_KEY).getAsString());
		assignment.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
		assignment = assignmentDB.updateByUniqueID(assignment);
		if (assignment.isUpdated())
		{
			return true;
		}

		return isUpdated;
	}

	private boolean addAssigments(Part part, JsonArray jsonArray, URL reconstructedURL) throws IOException {
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
			Assignment assignment = new Assignment();
			assignment.setSubject(jsonObject.get(Constants.SUBJECT_KEY).getAsString());
			assignment.setAssignment(jsonObject.get(Constants.ASSIGNMENT_KEY).getAsString());
			assignment.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());
			assignment.setTime(jsonObject.get(Constants.TIME_KEY).getAsString());
			assignment.setStartDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			assignment.setEndDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			assignment.setSetReminder(jsonObject.get(Constants.SET_REMINDER_KEY).getAsString());
			assignment.setSetNotif(jsonObject.get(Constants.SET_NOTIF_KEY).getAsString());
			assignment.setBranch(jsonObject.get(Constants.BRANCH_KEY).getAsString());
			assignment.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
			assignment.setUserID(jsonObject.get(Constants.USER_ID_KEY).getAsLong());
			assignment.setAttachment(attachment);
			assignment.setAttachmenturl(attachmentURL);
			assignment = assignmentDB.create(assignment);
			if (assignment.getId() == null)
			{
				return false;
			}
		}
		return isAdded;
	}
}
