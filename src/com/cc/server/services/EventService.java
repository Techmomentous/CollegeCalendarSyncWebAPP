
package com.cc.server.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.impl.EventDB;
import com.cc.server.dao.impl.TimeTableDB;
import com.cc.server.dao.modal.Event;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class EventService
 */
@WebServlet("/EventService")
public class EventService extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private static final String	TAG					= EventService.class.getName();
	DAOFactory					daoFactory;
	EventDB						eventDB;
	TimeTableDB					timeTableDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventService() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		JsonArray jsonArray;
		JsonParser jsonParser;
		JsonObject jsonObject;
		try
		{
			writer = response.getWriter();
			if (daoFactory == null)
			{
				daoFactory = DAOFactory.getInstance(Constants.PROPERTIES_JDBC_KEY);
			}

			if (eventDB == null)
			{
				eventDB = daoFactory.getEventDB();
			}

			if (timeTableDB == null)
			{
				timeTableDB = daoFactory.getTimeTableDB();
			}
			String serviceRequest = request.getParameter("serviceRequest");
			System.out.println("ServiceRequest : " + serviceRequest + " " + TAG);
			Long id;
			Long userID;
			String uniqueID;
			String eventTitle;
			String eventDescription;
			String startDate;
			String endDate;
			String startTime;
			String endTime;
			String eventJson;
			boolean isReminderOn;
			boolean isRemove = false;

			switch (serviceRequest) {
				case "AddEvents":
					eventJson = request.getParameter("eventJson");
					jsonParser = new JsonParser();
					jsonArray = jsonParser.parse(eventJson).getAsJsonArray();
					System.out.println("uniqueID : " + eventJson + " " + TAG);

					jsonObject = new JsonObject();
					if (addEvents(jsonArray))
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Added Events.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Add Events.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					writer.write(jsonObject.toString());
					break;
				case "updateEvent":

					eventJson = request.getParameter("eventJson");
					jsonParser = new JsonParser();
					JsonObject eventObject = jsonParser.parse(eventJson).getAsJsonObject();
					jsonObject = new JsonObject();
					if (updateEvent(eventObject))
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Sucessfully Updated Event.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Failed To Update Event.");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}

					writer.write(jsonObject.toString());
					break;

				case "removeEvent":
					uniqueID = request.getParameter(Constants.UNIQUE_ID_KEY);
					isRemove = eventDB.removeByUniqueID(uniqueID);
					jsonObject = new JsonObject();
					if (isRemove)
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Event With ID " + uniqueID + " is removed");
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					}
					else
					{
						jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Faild to remove event With ID " + uniqueID);
						jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, false);
					}
					writer.write(jsonObject.toString());
					System.out.println("uniqueID : " + uniqueID + " " + TAG);
					break;
				case "GetAll":
					jsonArray = eventDB.getAllEvent();
					writer.write(jsonArray.toString());
					break;

				default:
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

	private boolean addEvents(JsonArray jsonArray) {
		boolean isEvent = false;
		for (int i = 0; i < jsonArray.size(); i++)
		{
			JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

			Event event = new Event();
			String eventype = jsonObject.get(Constants.EVENTCAT_EVENTTYPE_KEY).getAsString();
			if (eventype.equals("Fees"))
			{
				event.setEventCatId(new Long(1));
			}
			else if (eventype.equals("Campus"))
			{
				event.setEventCatId(new Long(1));
			}
			else if (eventype.equals("Sports"))
			{
				event.setEventCatId(new Long(3));
			}
			event.setTitle(jsonObject.get(Constants.TITLE_KEY).getAsString());
			event.setDescription(jsonObject.get(Constants.DESCRIPTION_KEY).getAsString());
			//We are not passing details about event location its bydefault college
			//			event.setLocation(jsonObject.get(Constants.EVENT_LOCATION_KEY).getAsString());
			event.setTime(jsonObject.get(Constants.TIME_KEY).getAsString());
			event.setStartDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			event.setEndDate(jsonObject.get(Constants.DATE_KEY).getAsString());
			event.setReminder(jsonObject.get(Constants.SET_REMINDER_KEY).getAsString());
			event.setNotification(jsonObject.get(Constants.SET_NOTIF_KEY).getAsString());
			event.setBranch(jsonObject.get(Constants.BRANCH_KEY).getAsString());
			event.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
			event.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());

			event = eventDB.create(event);
			if (event.getId() != null)
			{
				isEvent = true;
			}
			else
			{
				return false;
			}
		}
		return isEvent;

	}

	private boolean updateEvent(JsonObject jsonObject) {
		boolean isUpdate = false;
		Event event = new Event();
		event.setId(jsonObject.get(Constants.ID_KEY).getAsLong());
		event.setTitle(jsonObject.get(Constants.TITLE_KEY).getAsString());
		event.setDescription(jsonObject.get(Constants.DESCRIPTION_KEY).getAsString());
		event.setTime(jsonObject.get(Constants.TIME_KEY).getAsString());
		event.setStartDate(jsonObject.get(Constants.DATE_KEY).getAsString());
		event.setEndDate(jsonObject.get(Constants.DATE_KEY).getAsString());
		event.setReminder(jsonObject.get(Constants.SET_REMINDER_KEY).getAsString());
		event.setNotification(jsonObject.get(Constants.SET_NOTIF_KEY).getAsString());
		event.setBranch(jsonObject.get(Constants.BRANCH_KEY).getAsString());
		event.setYear(jsonObject.get(Constants.YEAR_KEY).getAsString());
		event.setUniqueID(jsonObject.get(Constants.UNIQUE_ID_KEY).getAsString());
		event.setEventCatId(jsonObject.get(Constants.EVENT_CATEGORY_ID).getAsLong());

		event = eventDB.update(event);
		if (event.isUpdate())
		{
			isUpdate = true;
		}
		return isUpdate;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
