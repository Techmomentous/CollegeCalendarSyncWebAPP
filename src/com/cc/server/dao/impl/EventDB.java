
package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.Event;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EventDB {

	DAOFactory					daoFactory;
	private static final String	SQL_GET_ALL						= "SELECT * FROM EVENT";
	private static final String	SQL_FIND_BY_ID					= "SELECT * FROM EVENT WHERE ID = ?";
	private static final String	SQL_GET_BY_CATEGORY_ID			= "SELECT * FROM EVENT WHERE EventCatId = ?";
	private static final String	SQL_GET_BY_CATEGORY_ID_AND_YEAR	= "SELECT * FROM EVENT WHERE EventCatId = ? AND YEAR = ?";
	private static final String	SQL_GET_BY_TITLE				= "SELECT * FROM EVENT WHERE TITLE = ?";
	private static final String	SQL_GET_BY_LOCATION				= "SELECT * FROM EVENT WHERE LOCATION = ?";
	private static final String	SQL_GET_BY_TIME					= "SELECT * FROM EVENT WHERE TIME = ?";
	private static final String	SQL_GET_BY_START_DATE			= "SELECT * FROM EVENT WHERE STARTDATE = ?";
	private static final String	SQL_GET_BY_END_DATE				= "SELECT * FROM EVENT WHERE ENDDATE = ?";
	private static final String	SQL_GET_BY_BRANCH				= "SELECT * FROM EVENT WHERE BRANCH = ?";
	private static final String	SQL_GET_BY_YEAR					= "SELECT * FROM EVENT WHERE YEAR = ?";
	private static final String	SQL_INSERT						= "INSERT INTO EVENT (uniqueid, eventcatid, title,  description, location, time, startDate, endDate, setReminder, setNotif, branch, year,attachment, attachmenturl )"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String	SQL_UPDATE						= "UPDATE EVENT SET eventcatid = ? ,title=?, description=?, location=?, time=?, startDate=?, endDate=?, setReminder=?, setNotif=?,  year=?, branch=?,attachment= ?, attachmenturl= ?  WHERE id = ?";
	private static final String	SQL_DELETE						= "DELETE FROM EVENT WHERE id = ?";
	private static final String	SQL_DELETE_BY_UNIQUE_ID			= "DELETE FROM EVENT WHERE uniqueid = ?";

	public EventDB(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 *Creating new event in event table
	 *
	 * @return event
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	public Event create(Event event) throws IllegalArgumentException, DAOException {

		Object[] values = { event.getUniqueID(), event.getEventCatId(), event.getTitle(), event.getDescription(), event.getLocation(), event.getTime(), event.getStartDate(),
				event.getEndDate(), event.getReminder(), event.getNotification(), event.getBranch(), event.getYear(), event.getAttachment(), event.getAttachmenturl() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating event failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					event.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating event failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return event;
	}

	/**
	 *Updating an event in assignment table
	 *
	 * @return event
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public Event update(Event event) throws IllegalArgumentException, DAOException {

		if (event.getId() == null)
		{
			throw new IllegalArgumentException("Event is not created yet, the  ID is null.");
		}
		//eventcatid = ?,title=?, description=?, location=?, time=?, startDate=?, endDate=?, setReminder=?, setNotif=?,  year=?,branch=?, WHERE id = ?
		Object[] values = { event.getEventCatId(), event.getTitle(), event.getDescription(), event.getLocation(), event.getTime(), event.getStartDate(), event.getEndDate(),
				event.getReminder(), event.getNotification(), event.getYear(), event.getBranch(), event.getAttachment(), event.getAttachmenturl(), event.getId() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				event.setUpdate(false);
				throw new DAOException("Updating event failed, no rows affected.");
			}
			else
			{
				event.setUpdate(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return event;
	}

	/**
	 *Deleting an event in event table
	 *
	 * @return boolean
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public boolean delete(Event event) throws DAOException {
		Object[] values = { event.getId() };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting Event failed, no rows affected.");
			}
			else
			{
				isDeleted = true;
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return isDeleted;
	}

	public boolean removeByUniqueID(String uniqueID) throws DAOException {
		Object[] values = { uniqueID };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE_BY_UNIQUE_ID, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting Event By Unique ID failed, no rows affected.");
			}
			else
			{
				isDeleted = true;
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return isDeleted;
	}

	/**
	 * Get all the records from event table
	 *
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalStateException
	 */
	public JsonArray getAllEvent() throws DAOException, IllegalStateException {
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_ALL, false);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;

	}

	/**
	 *  Return {@link JsonArray} object of given title
	 *

	 * @param title
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByTitle(String title) throws DAOException {
		Object[] values = { title };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_TITLE, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Title Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	public JsonArray findEventByCategoryID(Long eventCatID) throws DAOException {
		Object[] values = { eventCatID };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_CATEGORY_ID, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Category failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	public JsonArray findEventByCategoryIDAndYear(Long eventCatID, String year) throws DAOException {
		Object[] values = { eventCatID, year };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_CATEGORY_ID_AND_YEAR, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Category And Year failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given location
	 *

	 * @param location
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByLocation(String location) throws DAOException {
		Object[] values = { location };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_LOCATION, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Location Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given time
	 *

	 * @param time
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByTime(String time) throws DAOException {
		Object[] values = { time };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_TIME, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Time Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given startdate
	 *

	 * @param startdate
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByStartDate(String startDate) throws DAOException {
		Object[] values = { startDate };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_START_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Start Date Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given enddate
	 *

	 * @param enddate
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByEndDate(String endDate) throws DAOException {
		Object[] values = { endDate };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_END_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By EndDate Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given branch
	 *

	 * @param branch
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByBranch(String branch) throws DAOException {
		Object[] values = { branch };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_BRANCH, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Branch Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**
	 *  Return {@link JsonArray} object of given year
	 *

	 * @param year
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findEventByYear(String year) throws DAOException {
		Object[] values = { year };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_YEAR, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Event By Year Data failed, no rows affected.");

			}
			else
			{
				jsonArray = mapJson(resultSet);
			}

				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return jsonArray;
	}

	/**Find By Given Auto ID
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Event find(Long id) throws DAOException {
		return find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the event from the database matching the given SQL query with the
	 * given values.
	 *
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The user from the database matching the given SQL query with the
	 *         given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Event find(String sql, Object... values) throws DAOException {
		Event event = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet.next())
			{
				event = map(resultSet);
			}
				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return event;
	}

	//	Columns:
	//	id int(11) AI PK
	//	uniqueid varchar(50)
	//	eventcatid int(11)
	//	title varchar(200)
	//	description varchar(200)
	//	location varchar(100)
	//	time varchar(50)
	//	startdate varchar(50)
	//	enddate varchar(50)
	//	setreminder varchar(50)
	//	setnotif varchar(50)
	//	year varchar(50)
	//	branch varchar(50)
	private JsonArray mapJson(ResultSet resultSet) throws IllegalStateException, SQLException {
		JsonArray jsonArray = new JsonArray();
		while (resultSet.next())
		{
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.ID_KEY, resultSet.getLong(1));
			jsonObject.addProperty(Constants.UNIQUE_ID_KEY, resultSet.getString(2));
			jsonObject.addProperty(Constants.EVENT_CATEGORY_ID, resultSet.getLong(3));
			jsonObject.addProperty(Constants.TITLE_KEY, resultSet.getString(4));
			jsonObject.addProperty(Constants.DESCRIPTION_KEY, resultSet.getString(5));
			jsonObject.addProperty(Constants.EVENT_LOCATION_KEY, resultSet.getString(6));
			jsonObject.addProperty(Constants.TIME_KEY, resultSet.getString(7));
			jsonObject.addProperty(Constants.START_DATE_KEY, resultSet.getString(8));
			jsonObject.addProperty(Constants.END_DATE_KEY, resultSet.getString(9));
			jsonObject.addProperty(Constants.SET_REMINDER_KEY, resultSet.getString(10));
			jsonObject.addProperty(Constants.SET_NOTIF_KEY, resultSet.getString(11));
			jsonObject.addProperty(Constants.YEAR_KEY, resultSet.getString(12));
			jsonObject.addProperty(Constants.BRANCH_KEY, resultSet.getString(13));
			jsonObject.addProperty(Constants.ATTACHMENT_URL, resultSet.getString(14));
			jsonObject.addProperty(Constants.ATTACHMENT, resultSet.getString(15));
			jsonArray.add(jsonObject);
		}
		return jsonArray;

	}

	private Event map(ResultSet resultSet) throws IllegalStateException, SQLException {
		Event event = new Event(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
				resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
				resultSet.getString(13));
		event.setAttachment(resultSet.getString(14));
		event.setAttachmenturl(resultSet.getString(15));
		return event;
	}

}
