package gui;

import java.sql.Statement;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public void initialiseDatabase() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM participants where username = ? and password = ?";
		
	}

}
