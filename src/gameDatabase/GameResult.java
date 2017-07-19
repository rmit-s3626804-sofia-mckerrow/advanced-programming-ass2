package gameDatabase;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameResult {
	
	private SimpleStringProperty athleteID;
	private SimpleStringProperty time;
	private SimpleStringProperty points;
	private SimpleStringProperty gameID;
	private SimpleStringProperty officialID;
	private SimpleStringProperty date;
	
	public GameResult(String athleteID, String time, String points, String gameID, String officialID, String date) {
		this.athleteID = new SimpleStringProperty(athleteID);
		this.time = new SimpleStringProperty(time);
		this.points = new SimpleStringProperty(points);
		this.gameID = new SimpleStringProperty(gameID);
		this.officialID = new SimpleStringProperty(officialID);
		this.date = new SimpleStringProperty(date);
	}

	public String getAthleteID() {
		return athleteID.get();
	}

	public String getTime() {
		return time.get();
	}

	public String getPoints() {
		return points.get();
	}

	public String getGameID() {
		return gameID.get();
	}

	public String getOfficialID() {
		return officialID.get();
	}

	public String getDate() {
		return date.get();
	}

}
