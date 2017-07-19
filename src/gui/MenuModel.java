package gui;

import java.sql.Connection;

import gameDatabase.SQLiteConnection;

public class MenuModel {

	private Connection connection;

	public MenuModel() {
		connection = SQLiteConnection.connector();
		if (connection == null) {
			System.out.println("Connection not successful");
			System.exit(1);
		}
			
	}

}
