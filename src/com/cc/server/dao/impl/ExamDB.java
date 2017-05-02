
package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.Exam;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExamDB {

	DAOFactory				daoFactory;
	private final String	SQL_GET_ALL				= "SELECT * FROM EXAM";
	private final String	SQL_GET_BY_SUBJECT		= "SELECT * FROM EXAM WHERE SUBJECT = ?";
	private final String	SQL_GET_BY_TIME			= "SELECT * FROM EXAM WHERE TIME = ?";
	private final String	SQL_GET_BY_START_DATE	= "SELECT * FROM EXAM WHERE STARTDATE = ?";
	private final String	SQL_GET_BY_END_DATE		= "SELECT * FROM EXAM WHERE ENDDATE = ?";
	private final String	SQL_GET_BY_BRANCH		= "SELECT * FROM EXAM WHERE BRANCH = ?";
	private final String	SQL_GET_BY_YEAR			= "SELECT * FROM EXAM WHERE YEAR = ?";
	private final String	SQL_GET_BY_USER_ID		= "SELECT * FROM EXAM WHERE userid = ?";
	private final String	SQL_INSERT				= "INSERT INTO EXAM (uniqueid,subject, time, startDate, endDate, setReminder, setNotif, branch, year,userid,attachment, attachmenturl )"
															+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String	SQL_UPDATE				= "UPDATE EXAM SET subject=?, time=?, startDate=?, endDate=?, setReminder=?, setNotif=?, branch=?, year=?,attachment= ?, attachmenturl= ?  WHERE uniqueid = ?";
	private final String	SQL_DELETE				= "DELETE FROM EXAM WHERE id = ?";
	private final String	SQL_DELETE_UNIQUE_ID	= "DELETE FROM EXAM WHERE uniqueid = ?";

	public ExamDB(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 *Creating new exam in Exam table
	 *
	 * @return exam
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	public Exam create(Exam exam) throws IllegalArgumentException, DAOException {

		Object[] values = { exam.getUniqueID(), exam.getSubject(), exam.getTime(), exam.getStartDate(), exam.getEndDate(), exam.getSetReminder(), exam.getSetNotif(),
				exam.getBranch(), exam.getYear(), exam.getUserid(), exam.getAttachment(), exam.getAttachmenturl() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating exam failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					exam.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating exam failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return exam;
	}

	/**
	 *Updating an exam in exam table
	 *
	 * @return exam
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public Exam update(Exam exam) throws IllegalArgumentException, DAOException {

		if (exam.getUniqueID() == null || exam.getUniqueID().isEmpty())
		{
			throw new IllegalArgumentException("exam is not created yet, the  ID is null.");
		}

		Object[] values = { exam.getSubject(), exam.getTime(), exam.getStartDate(), exam.getEndDate(), exam.getSetReminder(), exam.getSetNotif(), exam.getBranch(), exam.getYear(),
				exam.getAttachment(), exam.getAttachmenturl(), exam.getUniqueID() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{

				exam.setUpdated(false);
				throw new DAOException("Updating Exam failed, no rows affected.");
			}
			else
			{
				exam.setUpdated(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return exam;
	}

	/**
	 *Deleting an exam in Exam table
	 *
	 * @return boolean
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public boolean delete(Long id) throws DAOException {
		Object[] values = { id };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE_UNIQUE_ID, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting Exam failed, no rows affected.");
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

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE_UNIQUE_ID, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting Exam failed, no rows affected.");
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
	 * Get all the records from exam table
	 *
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalStateException
	 */
	public JsonArray getAllExam() throws DAOException, IllegalStateException {
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_ALL, false);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam Data failed, no rows affected.");

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
				throw new DAOException("Getting All Exam Data By User ID failed, no rows affected.");

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
	public JsonArray findExamBySubject(String subject) throws DAOException {
		Object[] values = { subject };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_SUBJECT, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By Subject Data failed, no rows affected.");

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
	public JsonArray findExamByTime(String time) throws DAOException {
		Object[] values = { time };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_TIME, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By time Data failed, no rows affected.");

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
	public JsonArray findExamByStartDate(String startDate) throws DAOException {
		Object[] values = { startDate };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_START_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By Start Date Data failed, no rows affected.");

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
	public JsonArray findExamByEndDate(String endDate) throws DAOException {
		Object[] values = { endDate };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_END_DATE, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By End Date Data failed, no rows affected.");

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
	public JsonArray findExamByBranch(String branch) throws DAOException {
		Object[] values = { branch };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_BRANCH, false, values);
				ResultSet resultSet = statement.executeQuery();)
		{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By Branch Data failed, no rows affected.");

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
	public JsonArray findExamByYear(String year) throws DAOException {
		Object[] values = { year };
		JsonArray jsonArray;
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_GET_BY_YEAR, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet == null)
			{
				throw new DAOException("Getting All Exam By Year Data failed, no rows affected.");

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
