
package com.cc.server.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.server.dao.factory.DAOFactory;
import com.cc.server.dao.impl.UserDB;
import com.cc.server.dao.modal.User;
import com.cc.server.utils.Constants;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AuthService
 */
@WebServlet("/AuthService")
public class AuthService extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	DAOFactory					daoFactory;
	UserDB						userDB;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthService() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			if (daoFactory == null)
			{
				daoFactory = DAOFactory.getInstance(Constants.PROPERTIES_JDBC_KEY);
			}
			if (userDB == null)
			{
				userDB = daoFactory.getUserDB();
			}
			String serviceRequest = request.getParameter("serviceRequest");
			Long id;
			String fullname;
			String email;
			String password;
			String userType;
			String branch;
			String year;
			User user;
			System.out.println("Service Request : " + serviceRequest + " [ " + AuthService.class.getName() + " ]");
			switch (serviceRequest) {
				case "login":
					email = request.getParameter(Constants.USER_TABLE_EMAIL_KEY);
					password = request.getParameter(Constants.USER_TABLE_PASSWORD_KEY);
					user = userDB.findUser(email, password);
					if (user != null)
					{
						String data = "id=" + user.getId() + "&fullname=" + user.getName() + "&email=" + user.getEmail();
						if (user.getUserType().equalsIgnoreCase("admin"))
						{
							response.sendRedirect("admin.jsp?" + data);
						}
						else
						{
							response.sendRedirect("user.jsp?" + data);
						}
					}
					else
					{
						response.sendRedirect("message.jsp?error=user not found");
					}
					break;
				case "signup":

					fullname = request.getParameter(Constants.USER_TABLE_NAME_KEY);
					email = request.getParameter(Constants.USER_TABLE_EMAIL_KEY);
					password = request.getParameter(Constants.USER_TABLE_PASSWORD_KEY);
					userType = request.getParameter(Constants.USER_TABLE_USER_TYPE_KEY);
					user = userDB.findUser(email, password);
					if (user == null)
					{
						user = new User(fullname, email, password, userType);

						user = userDB.create(user);
						if (user.getId() != null)
						{
							String data = "id=" + user.getId() + "&fullname=" + user.getName() + "&password=" + user.getPassword() + "usertype=" + user.getUserType();
							if (user.getUserType().equalsIgnoreCase("admin"))
							{
								response.sendRedirect("admin.jsp?" + data);

							}
							else
							{
								response.sendRedirect("message.jsp?error=Faculty Account Created.");

							}
						}

					}
					else
					{
						response.sendRedirect("message.jsp?error=faculty with givne email id already registered.");
					}
					break;
				case "appLogin":
					branch = request.getParameter(Constants.BRANCH_KEY);
					year = request.getParameter(Constants.YEAR_KEY);

					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty(Constants.JSON_RESPONSE_MESSAGE, "Login Success.");
					jsonObject.addProperty(Constants.JSON_RESPONSE_RESULT, true);
					response.getWriter().write(jsonObject.toString());
					break;
				default:
					break;
			}
		}
		catch (NullPointerException e)
		{
			System.err.println("Error In Process Request : " + e);
		}
		catch (Exception e)
		{
			System.err.println("Error In Process Request : " + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
