package gov.bls.responseRates.utility.user;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.bls.platform.log.*;
import org.json.JSONArray;
import org.json.JSONException;

import gov.bls.responseRates.properties.PropertyFactory;
import gov.bls.responseRates.service.dbAccess.DbConnection;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static ILog logger = LogFactory.getLogger();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String lanName = "";		
		
		if(session.getAttribute("LOGINNAME") != null) {
			lanName = session.getAttribute("LOGINNAME").toString();
			logger.info("User Access", "lanName: " + lanName);
		} else {
			logger.info("UserAccess", "Access denied !");
		}
	 

		// lanName="Elrod_C";
		String last_name = "";
		String first_name = "";

		DbConnection dconn = DbConnection.getInstance();
		String sql = "SELECT user_first_name, user_last_name FROM udb_user WHERE UPPER(lan_name) = UPPER('" + lanName
				+ "')";

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dconn.getConnection().createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				first_name = rs.getString(1).trim();
				last_name = rs.getString(2).trim();
				logger.info("First name is " + first_name);
				logger.info("Last name is " + last_name);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			// closes all the database resources.
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();

			} catch (SQLException sqle) {
				System.err.println("INSIDE FINALLY CLOSING: " + sqle);
			}
		} // end finally

		String sql2 = "";
		Boolean is_admin = null;

		try {
			stmt = dconn.getConnection().createStatement();

			sql2 = "SELECT COUNT(*) from worker " + "WHERE worker_status = 'Y'" + "AND org_unit_id IN (10,11,14,34,35)"
					+ "AND user_code IN" + "(SELECT user_code FROM udb_user WHERE UPPER(lan_name) = UPPER('" + lanName
					+ "'))";

			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				Integer temp = rs.getInt(1);

				if (temp >= 1) {
					is_admin = true;
				} else {
					is_admin = false;
				}

				logger.info("Is admin " + is_admin);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			// closes all the database resources.
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (dconn != null)
					dconn.closeConnection();
			} catch (SQLException sqle) {
				System.err.println("INSIDE FINALLY CLOSING: " + sqle);
			}
		} // end finally

		JSONArray json_data = new JSONArray();

		// json_data.put(lanName);
		json_data.put(first_name);
		json_data.put(last_name);
		json_data.put(is_admin);
		json_data.put(lanName);

		logger.info("UserServlet 148 json_data.toString  " + json_data.toString());

		res.setContentType("application/json");
		try {
			res.getWriter().println(json_data);
		} catch (Exception e) {
			logger.error((new JSONException("There was a Web server error, needing system admin attention: " + e)).toString());
		}
	}
}