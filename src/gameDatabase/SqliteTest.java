package gameDatabase;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import customExceptions.GameFullException;
import gameComponents.Athlete;
import gameComponents.Game;
import ozlympics.Driver;

public class SqliteTest {

	public static void main(String[] args) {
		
		DataBase db = new DataBase();
		Driver dr = new Driver();
		
		try {
			db.readParticipantsCheckRegex();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		// display participants
		try {
			ResultSet rs;
			rs = db.displayParticipants();
//			while(rs.next()){
//				System.out.println(rs.getString("id") + " " + rs.getString("name") + " " + rs.getString("type") + " " + rs.getInt("age") + " " + rs.getString("state"));
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.addRace("swim");
		Game game = db.getLastGame();
		try {
			game.setRandomValidAthletesForRace(db);
			game.getOfficialForRace(db);
		} catch (GameFullException e) {
			e.printStackTrace();
		}
		game.competeAthletes();
		game.getRaceOfficial().sortRace(db);
		game.getRaceOfficial().givePointsToWinners(db);
		System.out.println();
		System.out.println(game.getDate());
		System.out.println();
		System.out.println("   Name			Time (seconds)");
		for (int i = 0; i < game.getRaceAthletes().size(); i++){ 		
			System.out.print((i+1) +". ");
			System.out.printf("%-20s %8s %n", game.getRaceAthletes().get(i).getName(), game.getResultArray().get(i));
		}
		
		System.out.println();
		
		for(int i = 0; i < game.getRaceSize(); i++) {
			Athlete add = game.getRaceAthletes().get(i);
			String athleteID = add.getID();
			double time = game.getResultArray().get(i);
			int points = add.getRoundPoints();
			String gameID = game.getRaceID();
			String officialID = game.getRaceOfficial().getID();
			String date = game.getDate();
			System.out.println(athleteID + " " + time + " " + points + " " + gameID + " " + officialID + " " + date);
			try {
				db.addResult(athleteID, time, points, gameID, officialID, date);
			} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			}
		}
		// display results
		try {
			ResultSet r;
			r = db.displayResults();
			System.out.println();
			System.out.println("table reslts");
			System.out.println(r.next());
			while(r.next()) {
				System.out.println(r.getString("athleteID") + " " + r.getString("result") + " "  + r.getString("score") + " "  + r.getString("gameID") + " "  + r.getString("officialID") + " "  + r.getString("date"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
