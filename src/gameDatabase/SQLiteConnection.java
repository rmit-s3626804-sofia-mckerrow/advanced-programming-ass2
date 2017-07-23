package gameDatabase;

//@author Sofia

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConnection {
	
	public static Connection connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:ozlympics.db");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
