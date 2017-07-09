package ozlympics;
//@author Sofia

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;

public class Driver {
	DataBase thisDB = new DataBase();
	Game myGame;
	private Scanner keyboard;
	private Scanner keyboard2;
	private Scanner keyboard3;
			
	// Start interface
	public void run() {
		readData();
		boolean userExited = false; // user has not chosen to exit
		while (!userExited) {
			int option = menu(); // display menu

			switch (option) {
			case 1: // Select a game to run
				selectGameToRun();
				System.out.println();
				break;
			case 2: // Predict winner of game
				getUserPrediction();
				System.out.println();
				break;
			case 3: // Start the game
				runRace();
				System.out.println();
							    
				break;
			case 4: // Display final results of all games
				thisDB.getResultList();
				System.out.println();
				break;
			case 5: // Display points of all athletes
				thisDB.sortAthletesByPointsThenPrint();
				System.out.println();
				break;
			case 6: // Exit
				try {
					thisDB.recordGame(); // records game results
					System.out.println("Game results saved to gameResults.txt");
					System.out.println();
				} catch (IOException e) {
					e.printStackTrace();
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
				System.out.println("Goodbye!");
				userExited = true; // User exits the program
				break;
			}
		}
	}
	
	// Display menu
	public int menu() {
		int option = 0;
		boolean validInput = false;

		// Do while loop until user enters a valid menu option
		do {
			try {
				// Prompt user to enter a menu option
				System.out.println("Welcome to the Ozlympic Games!");
				System.out.println("==============================");
				System.out.println("1. Select a game to run");
				System.out.println("2. Predict the winner of the game");
				System.out.println("3. Start the game");
				System.out.println("4. Display the final results of all games");
				System.out.println("5. Display the points of all athletes");
				System.out.println("6. Exit");
				
				keyboard = new Scanner(System.in);
				option = keyboard.nextInt();
				// Check if user entry is between 1 and 6
				if (option >= 1 && option <= 6) {
					validInput = true;
				} else {
					// If user entry is outside range of 1 - 6 display error message and redisplay menu
					System.out.println("Invalid option. Please try again.");
					System.out.println();
					validInput = false;
				}
				// Catch user entry if it is not an integer and redisplay menu
			} catch (Exception e) {
				System.out.println("Invalid option. Please try again.");
				System.out.println();
			}
		} while (!validInput);
		return option;
	}
	
	// Select a game to run
	public void selectGameToRun() {
		if (thisDB.getGames().size() > 0){
			Game lastGame = thisDB.getLastGame();
			// Check if user has selected a game to run but has not predicted a winner
			if (lastGame.getResultArray().isEmpty() == true && lastGame.getRaceAthletes().size() > 4){
				System.out.println(lastGame.getRaceID() + " hasn't raced yet.");
				System.out.println("Please predict a winner and start the game before creating a new game!");
			}
			else {
				addGame();
				startGame();
			}
		}
		else {
			addGame();
			startGame();
		}
	}
	
	// Add a game to the database
	public void addGame() {
		int option = 0;
		boolean validInput = false;
		String raceType;
				
		do {
			try {
				System.out.println("Enter number for type of game to play");
				System.out.println("1 - Cycling race");
				System.out.println("2 - Running race");
				System.out.println("3 - Swimming race");
				keyboard2 = new Scanner(System.in);
				option = keyboard2.nextInt();

				if (option == 1) { // Add a cycling race
					raceType = "cycle";
					myGame = thisDB.addRace(raceType); 
					validInput = true;
				}
				else if (option == 2) { // Add a running race
					raceType = "run";
					myGame = thisDB.addRace(raceType); 
					validInput = true;
				}
				else if (option == 3) { // Add a swimming race
					raceType = "swim";
					myGame = thisDB.addRace(raceType); 
					validInput = true;
				} 
				else { // If user entry is outside range of 1 - 3 display error message and redisplay menu
					System.out.println("Invalid option. Please try again.");
					System.out.println();
					validInput = false;
				}				
			} catch (Exception e) { // Catch user entry if it is not an integer and redisplay menu
				System.out.println("Invalid option. Please try again.");
				System.out.println();
			}
		} while (!validInput);
		
		System.out.println("Race ID: " + myGame.getRaceID());
	}
	
	// Set up a game
	public void startGame() {	
		myGame = thisDB.getLastGame();
		try {
			myGame.setRandomValidAthletesForRace(thisDB); // get athletes for the race
			myGame.getOfficialForRace(thisDB); // get the official for the race
			
			System.out.println("Race Official: " + myGame.getRaceOfficial().getName());
			System.out.println();
			System.out.println("   Name");
			
			// print list of athletes for race
			for (int i = 0; i < myGame.getRaceAthletes().size(); i++){
				System.out.println((i+1) +". " + myGame.getRaceAthletes().get(i).getName());
			}
		} catch (GameFullException e) {
			e.getMessage();
		} 		
	}
	
	// Run a race
	public void runRace(){
		// Check if user has selected a game before starting one
		if (thisDB.getGames().size() == 0){
			System.out.println("You must select a game to run before starting!");
		}
		else {
			myGame = thisDB.getLastGame();
			try {
				myGame.checkIfRaceHasMin(myGame); // check if there are enough athletes to start a race
				myGame.checkIfRaceHasOfficial(myGame); // check if race has an official
				System.out.println("The race is on");
				System.out.println();
				myGame.competeAthletes(); // get race times
				myGame.getRaceOfficial().sortRace(thisDB); // sort the results from lowest to highest times - if there is a tie official decides
				
				// print results of race
				System.out.println("   Name			Time (seconds)");
				for (int i = 0; i < myGame.getRaceAthletes().size(); i++){ 		
					System.out.print((i+1) +". ");
					System.out.printf("%-20s %8s %n", myGame.getRaceAthletes().get(i).getName(), myGame.getResultArray().get(i));
				}
				System.out.println();
				myGame.getRaceOfficial().givePointsToWinners(thisDB); 	// give points to top three places
				checkUserPrediction(); 									// check if user predicted the winner
			} catch (TooFewAthleteException e) {
				e.getMessage();
			} catch (NoRefereeException e) {
				e.getMessage();
			}
		}
	}
	
	// Get the user's predicted winner
	public Athlete getUserPrediction() { 
		Athlete userPrediction = null;
		boolean validInput = false;
				
		// Check if user has selected a game before making a prediction
		if (thisDB.getGames().size() == 0) {
			System.out.println("You must select a game to run before making a prediction!");
		}
		else {
			myGame = thisDB.getLastGame();
			do {
				try {
					System.out.println("Enter number of the athlete you predict will be the winner: ");
					keyboard3 = new Scanner(System.in);
					int predictionNumber = keyboard3.nextInt();
					if (predictionNumber >= 1 && predictionNumber <= 8 ) { // check if user entry is a valid entry
						userPrediction = myGame.getRaceAthletes().get(predictionNumber - 1);
						myGame.setUserPrediction(userPrediction);
						System.out.println("Predicted winner: " + myGame.getRaceAthletes().get(predictionNumber - 1).getName());
						validInput = true;
					} else { // If user entry is outside range of 1 - 8 display error message
						System.out.println("Invalid option. Please try again.");
						System.out.println();
						validInput = false;
					}
				} catch (Exception e) { // Catch user entry if it is not an integer and redisplay menu
					System.out.println("Invalid option. Please try again.");
					System.out.println();
				}
			} while (!validInput);
		}
		return userPrediction;		
	}
	
	// Check if the user's predicted winner matches the actual winner
	public void checkUserPrediction() { 
		myGame = thisDB.getLastGame();
		Athlete userPrediction = myGame.getUserPrediction();
		if (userPrediction == myGame.getRaceAthletes().get(0)) {
			System.out.println();
			System.out.println("Congratulations, you predicted the correct winner!");
		}
	}
	
	public void readData() {
		// read participants file
		try {
			thisDB.readParticipantsFromFile();
		} catch (FileNotFoundException e) {
			System.out.println("Participants.txt not found"); 			//test
			e.printStackTrace();										//test
		} 
		
		try {
			thisDB.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}