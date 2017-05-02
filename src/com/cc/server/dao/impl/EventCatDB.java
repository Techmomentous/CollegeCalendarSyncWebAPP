package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.EventCat;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EventCatDB {
	
	DAOFactory daoFactory;
	private final String SQL_GET_ALL = "SELECT * FROM EVENTCAT";
	private final String SQL_INSERT= "INSERT INTO EVENTCAT (title )"
				+ " VALUES (?)";
	private final String SQL_UPDATE	="UPDATE EVENTCAT SET title=?"	;
	private final String SQL_DELETE= "DELETE FROM EVENTCAT WHERE id = ?";

	public EventCatDB(DAOFactory daoFactory) {
		this.daoFactory=daoFactory;	
		
	}
	
	/**
	 *Creating new event category in eventCat table
	 * 
	 * @return eventCat
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	public EventCat create(EventCat ec) throws IllegalArgumentException, DAOException {

		if (ec.getId() != null)
		{
			throw new IllegalArgumentException("Event category already created, ID is not null.");
		}

		Object[] values = { ec.getEventType()};

		try (Connection connection = daoFactory.getConnection(); 
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating an event category failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					ec.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating an event category failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return ec;
	}
	
	/**
	 *Updating an event category in evenCat table
	 * 
	 * @return eventCat
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */
	
	public EventCat update(EventCat ec) throws IllegalArgumentException, DAOException {

		if (ec.getId() == null)
		{
			throw new IllegalArgumentException("Event category is not created yet, ID is null.");
		}

		Object[] values = {  ec.getEventType() };

		try (Connection connection = daoFactory.getConnection(); 
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				ec.setUpdated(false);
				throw new DAOException("Updating event category failed, no rows affected.");
			}
			else
			{
				ec.setUpdated(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return ec;
	}
	
	/**
	 *Deleting an event category in eventCat table
	 * 
	 * @return boolean
	 * @throws DAOException
	 * @throws IllegalArgumentException
	 */

	public boolean delete(EventCat ec) throws DAOException {
		Object[] values = { ec.getId() };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting Event category failed, no rows affected.");
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
	 * Get all the records from eventCat table
	 * 
	 * @return JsonArray
	 * @throws DAOException
	 * @throws IllegalStateException
	 */
	public JsonArray getAllEventCat() throws DAOException, IllegalStateException {
		JsonArray jsonArray = new JsonArray();
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection,
						SQL_GET_ALL , false);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet == null) {
				throw new DAOException(
						"Getting All Event Category Data failed, no rows affected.");

			} else {
				mapJson(resultSet);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return jsonArray;
	
	
}
	
	private JsonArray mapJson(ResultSet resultSet)throws IllegalStateException, SQLException {
		JsonArray jsonArray = new JsonArray();
		while (resultSet.next()) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.ID_KEY,
					resultSet.getLong(1));
			jsonObject.addProperty(Constants.EVENTCAT_EVENTTYPE_KEY,
					resultSet.getString(2));
			jsonObject.addProperty(Constants.UNIQUE_ID_KEY,
					resultSet.getString(3));
			jsonArray.add(jsonObject);}
		return jsonArray;

		}

}
