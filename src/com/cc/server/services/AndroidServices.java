
package com.cc.server.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.impl.AssignmentDB;
import com.cc.server.dao.impl.EventDB;
import com.cc.server.dao.impl.ExamDB;
import com.cc.server.dao.impl.TimeTableDB;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class AndroidServices
 */
@WebServlet("/AndroidServices")
public class AndroidServices extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private static final String	TAG					= AndroidServices.class.getName();
	DAOFactory					daoFactory;
	AssignmentDB				assignmentDB;
	EventDB						eventDB;
	ExamDB						examDB;
	TimeTableDB					timeTableDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AndroidServices() {
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

		String branch, year;
		String dataJson;
		Long eventcatid;
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
			if (eventDB == null)
			{
				eventDB = daoFactory.getEventDB();
			}

			if (timeTableDB == null)
			{
				timeTableDB = daoFactory.getTimeTableDB();
			}

			if (examDB == null)
			{
				examDB = daoFactory.getExamDB();
			}

			String serviceRequest = request.getParameter("serviceRequest");
			System.out.println("ServiceRequest : " + serviceRequest + " " + TAG);

			switch (serviceRequest) {
				case "getAssignment":
					jsonObject = new JsonObject();
					branch = request.getParameter(Constants.BRANCH_KEY);
					year = request.getParameter(Constants.YEAR_KEY);
					jsonArray = assignmentDB.findAssignmentByYear(year);
					jsonObject.add("data", jsonArray);
					writer.write(jsonObject.toString());
					break;
				case "getTimeTable":
					jsonObject = new JsonObject();
					branch = request.getParameter(Constants.BRANCH_KEY);
					year = request.getParameter(Constants.YEAR_KEY);
					jsonArray = timeTableDB.findTimeTableByYear(year);
					jsonObject.add("data", jsonArray);
					writer.write(jsonObject.toString());
					break;
				case "getExams":
					jsonObject = new JsonObject();
					branch = request.getParameter(Constants.BRANCH_KEY);
					year = request.getParameter(Constants.YEAR_KEY);
					jsonArray = examDB.findExamByYear(year);
					jsonObject.add("data", jsonArray);
					writer.write(jsonObject.toString());
					break;
				case "getEventsByID":
					jsonObject = new JsonObject();
					eventcatid = Long.valueOf(request.getParameter(Constants.EVENT_CATEGORY_ID));
					branch = request.getParameter(Constants.BRANCH_KEY);
					year = request.getParameter(Constants.YEAR_KEY);
					jsonArray = eventDB.findEventByCategoryIDAndYear(eventcatid, year);
					jsonObject.add("data", jsonArray);
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
}
