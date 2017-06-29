package gui;

import java.sql.Connection;
import java.sql.SQLException;

import gameDatabase.SQLiteConnection;

public class MenuModel {

	Connection connection;

	public MenuModel() {
		connection = SQLiteConnection.connector();
		if (connection == null) {
			System.out.println("Connection not successful");
			System.exit(1);
		}
			
	}

	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}
