package gameDatabase;

// @author Calvin (methods from Assignment 1) and Sofia

import java.io.BufferedWriter;

import java.sql.Connection;
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
import java.util.Scanner;

import gameComponents.Athlete;
import gameComponents.Cycle;
import gameComponents.Cyclist;
import gameComponents.Game;
import gameComponents.Official;
import gameComponents.Run;
import gameComponents.Runner;
import gameComponents.SuperAthlete;
import gameComponents.Swim;
import gameComponents.Swimmer;

public class DataBase {
	
	private static Connection connection = SQLiteConnection.connector();
	private ArrayList<Athlete> athletes = new ArrayList<Athlete>();
	private ArrayList<Official> officials = new ArrayList<Official>();
	private ArrayList<Game> games = new ArrayList<Game>();
	private ArrayList<GameResult> results = new ArrayList<GameResult>();
		
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
	
	public void initialiseParticipantsListFromDatabase() {
		String query = "SELECT * FROM participants";
		
		try {
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery();
			
			while (resultSet.next()) {
				sortParticipantsIntoType(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("type"), 
					resultSet.getInt("age"), resultSet.getString("state"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void readParticipantsFromFile() throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File("Assets/Participants.txt"));	
		while(fileIn.hasNextLine()) {
			String[] props = fileIn.nextLine().split(", ");
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
	
	// record game to database and results.txt file
	public void recordLastGame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		Game myGame = getLastGame();			
		String gameID = myGame.getRaceID();
		String officialID = myGame.getRaceOfficial().getID();
		String date = myGame.getDate();
		
		boolean writeNew = true;
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter("Assets/gameResults.txt", writeNew));
		
		// for (int i = 0; i < games.size(); i++) {
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++) {
			String athleteID = myGame.getRaceAthletes().get(i).getID();
			double time = myGame.getResultArray().get(i);
			int points = myGame.getRaceAthletes().get(i).getRoundPoints();
			writer.write(athleteID + ", " + time + ", " + points);
			writer.newLine();
			if (canDatabaseFileBeFound()) { // if the database is connected, add game results to database
				addResultToDatabase(athleteID, time, points, gameID, officialID, date);
			}
			else { // if database is not connected, add game results to arraylist results
				String resultTime = String.valueOf(time);
				String resultPoints = String.valueOf(points);
				GameResult gameResult = new GameResult(gameID, athleteID, resultTime, resultPoints, officialID, date);
				results.add(gameResult);
			}
		}
		writer.newLine();
		writer.close();
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
		
	public ResultSet getResultsFromDatabase() throws SQLException {
		Statement state = connection.createStatement();
		ResultSet resultSet = state.executeQuery("SELECT * FROM results");
		return resultSet;
	}
		
	// deletes all records from results table in database
	public void emptyResultsTable() throws SQLException {
		Statement state = connection.createStatement();
		state.executeUpdate("DELETE FROM results;");
	}
	
	// deletes all records from results file
	public void emptyResultsFile() throws IOException {
		PrintWriter pw = new PrintWriter("Assets/gameResults.txt");
		pw.close();
	}
	
	
	// check if database file exists
	public boolean canDatabaseFileBeFound() {
		File dbTest = new File("/gui/ozlympics.db");
		if (dbTest.exists())
			return true;
		else
			return false;
	}
	
	// check if participants file exists
	public boolean canParticipantsFileBeFound() {
		File fileTest = new File("Assets/Participants.txt");
		if (fileTest.exists())
			return true;
		else
			return false;
	}
}