package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gameComponents.Athlete;
import gameComponents.Cyclist;
import gameComponents.Official;
import gameDatabase.DataBase;

public class OfficialTest {
	
	DataBase db;
	Official official;
	Cyclist c1;
	Cyclist c2;
	Cyclist c3;
	Cyclist c4;

	@Before
	public void setUp() throws Exception {
		db = new DataBase();
		official = new Official("o001", "Test Official", "Official", 30, "TAS");
		c1 = new Cyclist("c111", "Test Cyclist", "Cyclist", 20, "VIC");
		c2 = new Cyclist("c222", "Test Cyclist", "Cyclist", 20, "VIC");
		c3 = new Cyclist("c333", "Test Cyclist", "Cyclist", 20, "VIC");
		c4 = new Cyclist("c444", "Test Cyclist", "Cyclist", 20, "VIC");
		
		db.addRace("cycle");
		db.getLastGame().setOfficialForRace(official);
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		athletes.add(c1);
		athletes.add(c2);
		athletes.add(c3);
		athletes.add(c4);
		db.getLastGame().setAthletesForRace(athletes);
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if givePointsToWinners() gives 5 points to the athlete in first place
	// Expected outcome is that the test will pass
	@Test
	public void testGivePointsToWinners1() {
		db.getLastGame().competeAthletes();
		db.getLastGame().getRaceOfficial().sortRace(db);
		db.getLastGame().getRaceOfficial().givePointsToWinners(db);
		
		assertEquals(5, db.getLastGame().getRaceAthletes().get(0).getRoundPoints());
	}
	
	// Check if givePointsToWinners() gives 3 points to the athlete in second place
	// Expected outcome is that the test will pass
	@Test
	public void testGivePointsToWinners2() {
		db.getLastGame().competeAthletes();
		db.getLastGame().getRaceOfficial().sortRace(db);
		db.getLastGame().getRaceOfficial().givePointsToWinners(db);
			
		assertEquals(3, db.getLastGame().getRaceAthletes().get(1).getRoundPoints());
	}
	
	// Check if givePointsToWinners() gives 1 point to the athlete in third place
	// Expected outcome is that the test will pass
	@Test
	public void testGivePointsToWinners3() {
		db.getLastGame().competeAthletes();
		db.getLastGame().getRaceOfficial().sortRace(db);
		db.getLastGame().getRaceOfficial().givePointsToWinners(db);
			
		assertEquals(1, db.getLastGame().getRaceAthletes().get(2).getRoundPoints());
	}
	
	// Check if givePointsToWinners() gives 0 points to the athlete in fourth place
	// Expected outcome is that the test will pass
	@Test
	public void testGivePointsToWinners4() {
		db.getLastGame().competeAthletes();
		db.getLastGame().getRaceOfficial().sortRace(db);
		db.getLastGame().getRaceOfficial().givePointsToWinners(db);
			
		assertEquals(0, db.getLastGame().getRaceAthletes().get(3).getRoundPoints());
	}
	
	

}
