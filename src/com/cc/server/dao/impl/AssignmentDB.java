
package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.Assignment;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AssignmentDB {

	DAOFactory				daoFactory;

	private final String	SQL_GET_ALL				= "SELECT * FROM ASSIGNMENT";
	private final String	SQL_GET_BY_SUBJECT		= "SELECT * FROM ASSIGNMENT WHERE SUBJECT = ?";
	private final String	SQL_GET_BY_TIME			= "SELECT * FROM ASSIGNMENT WHERE TIME = ?";
	private final String	SQL_GET_BY_START_DATE	= "SELECT * FROM ASSIGNMENT WHERE STARTDATE = ?";
	private final String	SQL_GET_BY_END_DATE		= "SELECT * FROM ASSIGNMENT WHERE ENDDATE = ?";
	private final String	SQL_GET_BY_BRANCH		= "SELECT * FROM ASSIGNMENT WHERE BRANCH = ?";
	private final String	SQL_GET_BY_YEAR			= "SELECT * FROM ASSIGNMENT WHERE YEAR = ?";
	private final String	SQL_INSERT				= "INSERT INTO ASSIGNMENT (subject, assignment, time, startDate, endDate, setReminder, setNotif, branch, year,uniqueid, userid, attachment, attachmenturl )"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String	SQL_UPDATE				= "UPDATE ASSIGNMENT SET subject = ?, assignment = ?, time = ?, startDate = ?, endDate = ?, setReminder = ?, setNotif = ?, branch = ?, year=?, attachment= ?, attachmenturl= ? WHERE id = ?";
	private final String	SQL_UPDATE_BY_UNIQUE_ID	= "UPDATE ASSIGNMENT SET subject = ?, assignment = ?, time = ?, startDate = ?, endDate = ?, setReminder = ?, setNotif = ?, branch = ?, year=?, attachment= ?, attachmenturl= ? WHERE uniqueid = ?";
	private final String	SQL_DELETE				= "DELETE FROM ASSIGNMENT WHERE id = ?";
	private final String	SQL_DELETE_BY_UNIQUE_ID	= "DELETE FROM ASSIGNMENT WHERE uniqueid = ?";
	private final String	SQL_GET_BY_USER_ID		= "SELECT * FROM ASSIGNMENT WHERE userid = ?";

	public AssignmentDB(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;

	}

	/**
	 *Creating new assignment in assignment table
	 *
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	public Assignment create(Assignment assign) throws IllegalArgumentException, DAOException {

		Object[] values = { assign.getSubject(), assign.getAssignment(), assign.getTime(), assign.getStartDate(), assign.getEndDate(), assign.getSetReminder(),
				assign.getSetNotif(), assign.getBranch(), assign.getYear(), assign.getUniqueID(), assign.getUserID(), assign.getAttachment(), assign.getAttachmenturl(), };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating assignment failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					assign.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating assignment failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return assign;
	}

	/**
	 *Updating an assignment in assignment table
	 *
	 * @return assignment
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public Assignment update(Assignment assign) throws IllegalArgumentException, DAOException {

		if (assign.getId() == null)
		{
			throw new IllegalArgumentException("Assignment is not created yet, the  ID is null.");
		}

		Object[] values = { assign.getSubject(), assign.getAssignment(), assign.getTime(), assign.getStartDate(), assign.getEndDate(), assign.getSetReminder(),
				assign.getSetNotif(), assign.getBranch(), assign.getYear(), assign.getAttachment(), assign.getAttachmenturl() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				assign.setUpdated(false);
				throw new DAOException("Updating assignment failed, no rows affected.");
			}
			else
			{
				assign.setUpdated(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return assign;
	}

	public Assignment updateByUniqueID(Assignment assign) throws IllegalArgumentException, DAOException {

		if (assign.getUniqueID() == null || assign.getUniqueID().isEmpty())
		{
			throw new IllegalArgumentException("Assignment is not created yet, the  UniqueID is null.");
		}

		Object[] values = { assign.getSubject(), assign.getAssignment(), assign.getTime(), assign.getStartDate(), assign.getEndDate(), assign.getSetReminder(),
				assign.getSetNotif(), assign.getBranch(), assign.getYear(), assign.getAttachment(), assign.getAttachmenturl(), assign.getUniqueID() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_BY_UNIQUE_ID, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				assign.setUpdated(false);
				throw new DAOException("Updating assignment failed, no rows affected.");
			}
			else
			{
				assign.setUpdated(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return assign;
	}

	/**
	 *Deleting an assignment in assignment table
	 *
	 * @return boolean
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public boolean delete(Assignment assign) throws DAOException {
		Object[] values = { assign.getId() };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting assignment failed, no rows affected.");
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
				throw new DAOException("Deleting assignment by uniqueIDfailed, no rows affected.");
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
	 * Get all the records from assignment table
	 *
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalStateException
	 */
	public JsonArray getAllAssignment() throws DAOException, IllegalStateException {
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_ALL, false);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment Data failed, no rows affected.");

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

	public JsonArray getAllExamByUserID(Long userid) throws DAOException, IllegalStateException {
		JsonArray jsonArray;
		Object[] values = { userid };
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_USER_ID, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignemt  Data By User ID failed, no rows affected.");

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
	public JsonArray findAssignmentBySubject(String subject) throws DAOException {
		Object[] values = { subject };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_SUBJECT, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By Subject Data failed, no rows affected.");

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
	public JsonArray findAssignmentByTime(String time) throws DAOException {
		Object[] values = { time };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_TIME, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By time Data failed, no rows affected.");

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
	public JsonArray findAssignmentByStartDate(String startDate) throws DAOException {
		Object[] values = { startDate };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_START_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By Start Date Data failed, no rows affected.");

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
	public JsonArray findAssignmentByEndDate(String endDate) throws DAOException {
		Object[] values = { endDate };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_END_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By End Date Data failed, no rows affected.");

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
	public JsonArray findAssignmentByBranch(String branch) throws DAOException {
		Object[] values = { branch };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_BRANCH, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By Branch Data failed, no rows affected.");

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
	 * @return jsonArray
	 * @throws DAOException
	 */
	public JsonArray findAssignmentByYear(String year) throws DAOException {
		Object[] values = { year };
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_YEAR, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Assignment By Year Data failed, no rows affected.");

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
			jsonObject.addProperty(Constants.SUBJECT_KEY, resultSet.getString(2));
			jsonObject.addProperty(Constants.ASSIGNMENT_KEY, resultSet.getString(3));
			jsonObject.addProperty(Constants.TIME_KEY, resultSet.getString(4));
			jsonObject.addProperty(Constants.START_DATE_KEY, resultSet.getString(5));
			jsonObject.addProperty(Constants.END_DATE_KEY, resultSet.getString(6));
			jsonObject.addProperty(Constants.SET_REMINDER_KEY, resultSet.getString(7));
			jsonObject.addProperty(Constants.SET_NOTIF_KEY, resultSet.getString(8));
			jsonObject.addProperty(Constants.BRANCH_KEY, resultSet.getString(9));
			jsonObject.addProperty(Constants.YEAR_KEY, resultSet.getString(10));
			jsonObject.addProperty(Constants.UNIQUE_ID_KEY, resultSet.getString(11));
			jsonObject.addProperty(Constants.USER_ID_KEY, resultSet.getLong(12));
			jsonObject.addProperty(Constants.ATTACHMENT, resultSet.getString(13));
			jsonObject.addProperty(Constants.ATTACHMENT_URL, resultSet.getString(14));
			jsonArray.add(jsonObject);
		}
		return jsonArray;

	}

}
