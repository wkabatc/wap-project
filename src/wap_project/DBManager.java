package wap_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static String _host = "localhost";
	private static String _port = "3306";
	private static String _user = "root";
	private static String _pass = "qwerty324";
	private static String _db = "dcmproject";
	
	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + _host + ":" + _port + "/" + _db + "";
			connection = DriverManager.getConnection(url, _user, _pass);
		}
		return connection;
	}
}
