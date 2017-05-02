
package com.cc.server.dao.impl;

import static com.cc.server.dao.utils.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cc.server.dao.exception.DAOException;
import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.modal.User;
import com.cc.server.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UserDB {
	private final static String	TABLE_NAME_KEY		= "user";

	private static final String	SQL_DELETE			= "DELETE FROM " + TABLE_NAME_KEY + "  WHERE id = ?";													;
	private static final String	SQL_FIND_BY_EMAIL	= "SELECT * FROM " + TABLE_NAME_KEY + "  WHERE email = ? And password = MD5(?)";

	private static final String	SQL_FIND_BY_ID		= "SELECT  * FROM " + TABLE_NAME_KEY + " WHERE id = ?";
	private static final String	SQL_INSERT			= "INSERT INTO " + TABLE_NAME_KEY + "  (name, email,  password, usertype) VALUES (?, ?, MD5(?), ?)";
	private static final String	SQL_SELECT_ALL		= "SELECT  * FROM " + TABLE_NAME_KEY;
	private static final String	SQL_UPDATE			= "UPDATE " + TABLE_NAME_KEY + "  SET name = ?, email = ?, password = ? WHERE id = ?";
	private DAOFactory			daoFactory			= null;

	public UserDB(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public User create(User user) throws IllegalArgumentException, DAOException {

		if (user.getId() != null)
		{
			throw new IllegalArgumentException("User is already created, the User ID is not null.");
		}

		Object[] values = { user.getName(), user.getEmail(), user.getPassword(), user.getUserType() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					user.setId(generatedKeys.getLong(1));
				}
				else
				{
					throw new DAOException("Creating user failed, no generated key obtained.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return user;
	}

	public boolean delete(User user) throws DAOException {
		Object[] values = { user.getId() };
		boolean isDeleted = false;

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new DAOException("Deleting user failed, no rows affected.");
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

	public boolean existEmail(String email) throws DAOException {

		return false;
	}

	public User find(String sql, Object... values) throws DAOException {
		User user = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();)
				{
			if (resultSet.next())
			{
				user = mapRow(resultSet);
			}
				}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}

		return user;
	}

	/**Return The List Of User
	 * @param groupID
	 * @return
	 * @throws Exception
	 */
	public JsonArray findUsers() throws Exception {
		JsonArray jsonArray = null;
		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_SELECT_ALL, false, "student");)

		{
			ResultSet resultSet = statement.executeQuery();
			if (resultSet == null)
			{
				throw new DAOException("Getting User List Failed, no rows affected.");
			}
			else
			{
				jsonArray = mapRowToJson(resultSet);
			}
		}

		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return jsonArray;
	}

	public User findUser(Long ID) throws DAOException {
		return find(SQL_FIND_BY_ID, ID);
	}

	public User findUser(String email, String password) {
		return find(SQL_FIND_BY_EMAIL, email, password);
	}

	public User mapRow(ResultSet resultset) throws IllegalArgumentException, SQLException {
		User user = new User(resultset.getLong(Constants.ID_KEY), resultset.getString(Constants.USER_TABLE_NAME_KEY), resultset.getString(Constants.USER_TABLE_EMAIL_KEY),
				resultset.getString(Constants.USER_TABLE_PHONE_KEY), resultset.getString(Constants.USER_TABLE_USER_TYPE_KEY));
		return user;
	}

	public JsonArray mapRowToJson(ResultSet resultset) throws IllegalArgumentException, SQLException {
		JsonArray jsonArray = new JsonArray();

		while (resultset.next())
		{
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty(Constants.ID_KEY, resultset.getLong(Constants.ID_KEY));
			jsonObject.addProperty(Constants.USER_TABLE_NAME_KEY, resultset.getString(Constants.USER_TABLE_NAME_KEY));
			jsonObject.addProperty(Constants.USER_TABLE_EMAIL_KEY, resultset.getString(Constants.USER_TABLE_EMAIL_KEY));
			jsonObject.addProperty(Constants.USER_TABLE_USER_TYPE_KEY, resultset.getString(Constants.USER_TABLE_USER_TYPE_KEY));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	public User update(User user) throws IllegalArgumentException, DAOException {

		if (user.getId() == null)
		{
			throw new IllegalArgumentException("user is not created yet, the user ID is null.");
		}

		Object[] values = { user.getName(), user.getEmail(), user.getPassword(), user.getId() };

		try (Connection connection = daoFactory.getConnection(); PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);)
		{
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				user.setUpdated(false);
				throw new DAOException("Updating user failed, no rows affected.");
			}
			else
			{
				user.setUpdated(true);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		return user;
	}

}
