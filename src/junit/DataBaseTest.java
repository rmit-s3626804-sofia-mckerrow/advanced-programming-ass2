package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gameComponents.Cyclist;
import gameComponents.Official;
import gameComponents.Runner;
import gameComponents.SuperAthlete;
import gameComponents.Swim;
import gameComponents.Swimmer;
import gameDatabase.DataBase;

public class DataBaseTest {
	
	DataBase db;

	@Before
	public void setUp() throws Exception {
		db = new DataBase();
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if addRace() adds a game to the arraylist of games
	// Expected outcome is that the test will pass
	@Test
	public void testAddRace() {
		db.addRace("cycle");
		assertNotNull(db.getLastGame());
	}
	
	// Check if getLastGame() gets the last game in the arraylist of games
	// Expected outcome is that the test will pass
	@Test
	public void testGetLastGame() {
		db.addRace("cycle");
		db.addRace("run");
		db.addRace("swim");
		assertTrue(db.getLastGame() instanceof Swim);
	}
	
	// Check if sortParticipantsIntoType() adds a new Cyclist to the arraylist of athletes if the type is "Cyclist"
	// Expected outcome is that the test will pass
	@Test
	public void testSortParticipantsIntoType1() {
		db.sortParticipantsIntoType("c001", "Test Cyclist", "Cyclist", 20, "WA");
		assertTrue(db.getAthletes().get(0) instanceof Cyclist);
	}
	
	// Check if sortParticipantsIntoType() adds a new Runner to the arraylist of athletes if the type is "Runner"
	// Expected outcome is that the test will pass
	@Test
	public void testSortParticipantsIntoType2() {
		db.sortParticipantsIntoType("r001", "Test Runner", "Runner", 20, "WA");
		assertTrue(db.getAthletes().get(0) instanceof Runner);
	}

	// Check if sortParticipantsIntoType() adds a new Swimmer to the arraylist of athletes if the type is "Swimmer"
	// Expected outcome is that the test will pass
	@Test
	public void testSortParticipantsIntoType3() {
		db.sortParticipantsIntoType("s001", "Test Swimmer", "Swimmer", 20, "WA");
		assertTrue(db.getAthletes().get(0) instanceof Swimmer);
	}
	
	// Check if sortParticipantsIntoType() adds a new SuperAthlete to the arraylist of athletes if the type is "SuperAthlete"
	// Expected outcome is that the test will pass
	@Test
	public void testSortParticipantsIntoType4() {
		db.sortParticipantsIntoType("s001", "Test Superathlete", "SuperAthlete", 20, "WA");
		assertTrue(db.getAthletes().get(0) instanceof SuperAthlete);
	}
	
	// Check if sortParticipantsIntoType() adds a new Official to the arraylist of officials if the type is "Official"
	// Expected outcome is that the test will pass
	@Test
	public void testSortParticipantsIntoType5() {
		db.sortParticipantsIntoType("o001", "Test Official", "Official", 20, "WA");
		assertTrue(db.getOfficials().get(0) instanceof Official);
	}

}
