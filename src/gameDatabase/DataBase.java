package gameDatabase;
// @author Calvin

import java.io.BufferedWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gameComponents.Athlete;
import gameComponents.Cycle;
import gameComponents.Cyclist;
import gameComponents.Game;
import gameComponents.Official;
import gameComponents.Participant;
import gameComponents.Run;
import gameComponents.Runner;
import gameComponents.SuperAthlete;
import gameComponents.Swim;
import gameComponents.Swimmer;
import gui.MenuModel;

public class DataBase {
	
	private static Connection connection = SQLiteConnection.connector();
	private ArrayList<Athlete> athletes = new ArrayList<Athlete>();			// temporary array list to read in athletes
	private ArrayList<Official> officials = new ArrayList<Official>(); 		// temporary array list to read in officials
	private ArrayList<Game> games = new ArrayList<Game>();					// arraylist of games
	private ArrayList<GameResult> results = new ArrayList<GameResult>();	// arraylist of game results
		
	public ArrayList<Athlete> getAthletes() {
		return athletes;
	}
	
	public ArrayList<Official> getOfficials() {
		return officials;
	}
	
	public ArrayList<Game> getGames() {
		return games;
	}
	
	public ArrayList<GameResult> getResults() {
		return results;
	}
	
	public void readParticipantsFromFile() throws FileNotFoundException {
		// read athlete db file
		Scanner fileIn = new Scanner(new File("Assets/Participants.txt"));	
		while(fileIn.hasNextLine()) {
			String[] props = fileIn.nextLine().split(", "); // read .txt file, add athlete to ArrayList
			sortParticipantsIntoType(props[0], props[1], props[2], Integer.valueOf(props[3]), props[4]);
		} fileIn.close();
		
	}
	
	public void sortParticipantsIntoType(String id, String name, String type, int age, String state) {
		if (type.equals("Official")) {
			Official thisOfficial = new Official(id, name, type, age, state);
			officials.add(thisOfficial);
		}
		else {
			Athlete thisAthlete = null;
			if (type.equals("Swimmer")) thisAthlete = new Swimmer(id, name, type, age, state);
			if (type.equals("Runner")) thisAthlete = new Runner(id, name, type, age, state);
			if (type.equals("Cyclist")) thisAthlete = new Cyclist(id, name, type, age, state);
			if (type.equals("SuperAthlete")) thisAthlete = new SuperAthlete(id, name, type, age, state);
			athletes.add(thisAthlete);
		}
	}
	
	// record game to database and results.txt file
	public void recordLastGame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		Game myGame = getLastGame();			
		String gameID = myGame.getRaceID();
		String officialID = myGame.getRaceOfficial().getID();
		String date = myGame.getDate();
		
		boolean writeNew = false;
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter("Assets/gameResults.txt", writeNew));
		
		for (int i = 0; i < games.size(); i++) {
			Game recordGame = games.get(i);
			String recordID = recordGame.getRaceID();
			String recordOfficialID = recordGame.getRaceOfficial().getID();
			String recordDate = recordGame.getDate();
			writer.write(recordID + ", " + recordOfficialID + ", " + recordDate);
			writer.newLine();
			for (int j = 0; j < recordGame.getRaceAthletes().size(); j++){
				String athleteID = recordGame.getRaceAthletes().get(j).getID();
				double time = recordGame.getResultArray().get(j);
				int points = recordGame.getRaceAthletes().get(j).getRoundPoints();
				writer.write(athleteID + ", " + time + ", " + points); 
				writer.newLine();
				if (doesDatabaseExist()) { // if the database is connected, add game results to database
					addResultToDatabase(athleteID, time, points, gameID, officialID, date);
				}
				else { // if database is not connected, add game results to arraylist results
					String resultTime = String.valueOf(time);
					String resultPoints = String.valueOf(points);
					GameResult gameResult = new GameResult(gameID, athleteID, resultTime, resultPoints, recordOfficialID, recordDate);
					results.add(gameResult);
				}
			}
			writer.newLine();
		}
		writer.close();
	}
	
	// generate raceID and pass it to a new Game in the arraylist of Games
	public Game addRace(String raceType) { 		
		String raceID = Game.getNextID(raceType, this.games.size());
		Game thisGame = null;
		if (raceType.equals("swim")) thisGame = new Swim(raceID);
		if (raceType.equals("run")) thisGame = new Run(raceID);
		if (raceType.equals("cycle")) thisGame = new Cycle(raceID);
		this.games.add(thisGame);
		return thisGame;
	}
	
	// retrieve the last game in the list of games
	public Game getLastGame() {					
		int lastIndex = (games.size() - 1);
		return games.get(lastIndex);
	}
	
	// initialise arraylist of Athletes from database
	public ArrayList<Athlete> initialiseAthletesListFromDatabase() {
		String query = "SELECT id, name, type, age, state FROM participants WHERE id LIKE 'a%'";
		
		try {
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery();
			
			Athlete thisAthlete = null;
			while (resultSet.next()) {
				if (resultSet.getString("type").equals("Swimmer")) 
					thisAthlete = new Swimmer(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
							resultSet.getInt("age"), resultSet.getString("state"));
				if (resultSet.getString("type").equals("Runner")) 
					thisAthlete = new Runner(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
							resultSet.getInt("age"), resultSet.getString("state"));
				if (resultSet.getString("type").equals("Cyclist")) 
					thisAthlete = new Cyclist(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
							resultSet.getInt("age"), resultSet.getString("state"));
				if (resultSet.getString("type").equals("SuperAthlete")) 
					thisAthlete = new SuperAthlete(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
							resultSet.getInt("age"), resultSet.getString("state"));
				athletes.add(thisAthlete);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return athletes;
	}
	
	// initialise arraylist of Officials from database
	public ArrayList<Official> initialiseOfficialsListFromDatabase() {
		String query = "SELECT id, name, type, age, state FROM participants WHERE id LIKE 'o%'";
		
		try {
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery();
			
			Official thisOfficial = null;
			while (resultSet.next()) {
				thisOfficial = new Official(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
						resultSet.getInt("age"), resultSet.getString("state"));
				officials.add(thisOfficial);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return officials;
	}
	
	// deletes all records from results table in database
	public void emptyResultsTable() throws SQLException {
		Statement state = connection.createStatement();
		state.executeUpdate("DELETE FROM results;");
	}
	
	// deletes all records from results file
	public void emptyResultsFile() throws IOException {
		PrintWriter pw = new PrintWriter("gameResults.txt");
		pw.print("");
		pw.close();
	}
	
	// add game results to database
	public void addResultToDatabase(String athleteID, double time, int points, String gameID, String officialID, String date) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		PreparedStatement prep = connection.prepareStatement("INSERT INTO results values(?,?,?,?,?,?);");
		prep.setString(1, athleteID);
		prep.setDouble(2, time);
		prep.setInt(3, points);
		prep.setString(4, gameID);
		prep.setString(5, officialID);
		prep.setString(6, date);
		prep.execute();
	}
	
	// get the game results from database
	public ResultSet getResultsFromDatabase() throws SQLException {
		Statement state = connection.createStatement();
		ResultSet resultSet = state.executeQuery("SELECT * FROM results");
		return resultSet;
	}
	
	// check if database file exists
	public boolean doesDatabaseExist() {
		File dbTest = new File("/gui/ozlympics.db");
		if (dbTest.exists())
			return true;
		else
			return false;
	}
	
	public boolean canParticipantsFileBeFound() {
		File fileTest = new File("Assets/Participants.txt");
		if (fileTest.exists())
			return true;
		else
			return false;
	}
}