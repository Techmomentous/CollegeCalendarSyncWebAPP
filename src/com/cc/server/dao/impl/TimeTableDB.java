
package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.TimeTable;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TimeTableDB {

	DAOFactory				daoFactory;
	private final String	SQL_GET_ALL				= "SELECT * FROM TIMETABLE";
	private final String	SQL_GET_BY_SUBJECT		= "SELECT * FROM TIMETABLE WHERE SUBJECT = ?";
	private final String	SQL_GET_BY_TIME			= "SELECT * FROM TIMETABLE WHERE TIME = ?";
	private final String	SQL_GET_BY_START_DATE	= "SELECT * FROM TIMETABLE WHERE STARTDATE = ?";
	private final String	SQL_GET_BY_END_DATE		= "SELECT * FROM TIMETABLE WHERE ENDDATE = ?";
	private final String	SQL_GET_BY_BRANCH		= "SELECT * FROM TIMEABLE WHERE BRANCH = ?";
	private final String	SQL_GET_BY_YEAR			= "SELECT * FROM TIMETABLE WHERE YEAR = ?";
	private final String	SQL_GET_BY_USER_ID		= "SELECT * FROM TIMETABLE WHERE userid = ?";
	private final String	SQL_INSERT				= "INSERT INTO TIMETABLE (uniqueid,subject, time, startDate, endDate, setReminder, setNotif, branch, year, userid  ,attachment, attachmenturl)"
															+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String	SQL_UPDATE				= "UPDATE TIMETABLE SET subject=?, time=?, startDate=?, endDate=?, setReminder=?, setNotif=?, branch=?, year=?,attachment= ?, attachmenturl= ?  WHERE uniqueid = ?";
	private final String	SQL_DELETE				= "DELETE FROM TIMETABLE WHERE id = ?";
	private final String	SQL_DELETE_BY_UNIQUE_ID	= "DELETE FROM TIMETABLE WHERE uniqueid = ?";

	public TimeTableDB(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 *Creating new exam in TimeTable table
	 *
	 * @return timetable
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	public TimeTable create(TimeTable timetable) throws IllegalArgumentException, DAOException {

		Object[] values = { timetable.getUniqueID(), timetable.getSubject(), timetable.getTime(), timetable.getStartDate(), timetable.getEndDate(), timetable.getSetReminder(),
				timetable.getSetNotif(), timetable.getBranch(), timetable.getYear(), timetable.getUserID(), timetable.getAttachment(), timetable.getAttachmenturl() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating timetable failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					timetable.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating timetable failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return timetable;
	}

	/**
	 *Updating  timetable in  timetable table
	 *
	 * @return exam
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public TimeTable update(TimeTable timetable) throws IllegalArgumentException, DAOException {

		if (timetable.getUniqueID() == null || timetable.getUniqueID().isEmpty())
		{
			throw new IllegalArgumentException("TimeTable is not created yet, ID is null.");
		}

		Object[] values = { timetable.getSubject(), timetable.getTime(), timetable.getStartDate(), timetable.getEndDate(), timetable.getSetReminder(), timetable.getSetNotif(),
				timetable.getBranch(), timetable.getYear(), timetable.getAttachment(), timetable.getAttachmenturl(), timetable.getUniqueID() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				timetable.setUpdate(false);
				throw new DAOException("Updating TimeTable failed, no rows affected.");
			}
			else
			{
				timetable.setUpdate(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return timetable;
	}

	/**
	 *Deleting in TimeTable table
	 *
	 * @return boolean
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public boolean delete(Long id) throws DAOException {
		Object[] values = { id };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting TimeTable failed, no rows affected.");
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

	public boolean delete(String uniqueID) throws DAOException {
		Object[] values = { uniqueID };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE_BY_UNIQUE_ID, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting TimeTable failed, no rows affected.");
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
	 * Get all the records from TimeTable table
	 *
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalStateException
	 */
	public JsonArray getAllTimeTable() throws DAOException, IllegalStateException {
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_ALL, false);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable Data failed, no rows affected.");

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

	public JsonArray findByUserID(Long userid) throws DAOException, IllegalStateException {
		Object[] values = { userid };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_USER_ID, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable Data By User ID failed, no rows affected.");

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
	 *  Return {@link JsonArray} object of given subject
	 *

	 * @param subject
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findTimeTableBySubject(String subject) throws DAOException {
		Object[] values = { subject };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_SUBJECT, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By Subject Data failed, no rows affected.");

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
	public JsonArray findTimeTableByTime(String time) throws DAOException {
		Object[] values = { time };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_TIME, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By time Data failed, no rows affected.");

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
	 *  Return {@link JsonArray} object of given start date
	 *

	 * @param start date
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findTimeTableByStartDate(String startDate) throws DAOException {
		Object[] values = { startDate };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_START_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By Start Date Data failed, no rows affected.");

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
	 *  Return {@link JsonArray} object of given end date
	 *

	 * @param end date
	 * @return
	 * @throws DAOException
	 */
	public JsonArray findTimeTableByEndDate(String endDate) throws DAOException {
		Object[] values = { endDate };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_END_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By End Date Data failed, no rows affected.");

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
	public JsonArray findTimeTableByBranch(String branch) throws DAOException {
		Object[] values = { branch };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_BRANCH, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By Branch Data failed, no rows affected.");

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
	public JsonArray findTimeTableByYear(String year) throws DAOException {
		Object[] values = { year };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_YEAR, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All TimeTable By Year Data failed, no rows affected.");

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

	private JsonArray mapJson(ResultSet resultSet) throws IllegalStateException, SQLException {
		JsonArray jsonArray = new JsonArray();
		while (resultSet.next())
		{
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.ID_KEY, resultSet.getLong(1));
			jsonObject.addProperty(Constants.UNIQUE_ID_KEY, resultSet.getString(2));
			jsonObject.addProperty(Constants.SUBJECT_KEY, resultSet.getString(3));
			jsonObject.addProperty(Constants.TIME_KEY, resultSet.getString(4));
			jsonObject.addProperty(Constants.START_DATE_KEY, resultSet.getString(5));
			jsonObject.addProperty(Constants.END_DATE_KEY, resultSet.getString(6));
			jsonObject.addProperty(Constants.SET_REMINDER_KEY, resultSet.getString(7));
			jsonObject.addProperty(Constants.SET_NOTIF_KEY, resultSet.getString(8));
			jsonObject.addProperty(Constants.BRANCH_KEY, resultSet.getString(9));
			jsonObject.addProperty(Constants.YEAR_KEY, resultSet.getString(10));
			jsonObject.addProperty(Constants.USER_ID_KEY, resultSet.getString(11));
			jsonObject.addProperty(Constants.ATTACHMENT_URL, resultSet.getString(12));
			jsonObject.addProperty(Constants.ATTACHMENT, resultSet.getString(13));
			jsonArray.add(jsonObject);
		}
		return jsonArray;

	}

}
