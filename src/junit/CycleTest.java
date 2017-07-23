package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;
import gameComponents.Athlete;
import gameComponents.Cycle;
import gameComponents.Cyclist;
import gameComponents.Official;

public class CycleTest {
	
	Cycle cycle;
	Cyclist c1;
	Cyclist c2;
	Cyclist c3;
	Cyclist c4;
	Cyclist c5;
	Cyclist c6;
	Cyclist c7;
	Cyclist c8;
	Cyclist c9;
	Official official;

	@Before
	public void setUp() throws Exception {
		cycle = new Cycle("cycleRace");
		c1 = new Cyclist("c111", "Test Cyclist", "Cyclist", 20, "VIC");
		c2 = new Cyclist("c222", "Test Cyclist", "Cyclist", 20, "VIC");
		c3 = new Cyclist("c333", "Test Cyclist", "Cyclist", 20, "VIC");
		c4 = new Cyclist("c444", "Test Cyclist", "Cyclist", 20, "VIC");
		c5 = new Cyclist("c555", "Test Cyclist", "Cyclist", 20, "VIC");
		c6 = new Cyclist("c666", "Test Cyclist", "Cyclist", 20, "VIC");
		c7 = new Cyclist("c777", "Test Cyclist", "Cyclist", 20, "VIC");
		c8 = new Cyclist("c888", "Test Cyclist", "Cyclist", 20, "VIC");
		c9 = new Cyclist("c999", "Test Cyclist", "Cyclist", 20, "VIC");
		official = new Official("0111", "Test Official", "Official", 30, "NSW");
		
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		athletes.add(c1);
		athletes.add(c2);
		athletes.add(c3);
		cycle.setAthletesForRace(athletes);
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there are less than the minimum number of athletes
	// Expected outcome is that the test will pass
	@Test (expected=TooFewAthleteException.class)
	public void testCheckIfRaceHasMin1() throws TooFewAthleteException {
			cycle.checkIfRaceHasMin(cycle);
	}
	
	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there are the minimum number of athletes
	// Expected outcome is that the test will fail
	@Test (expected=TooFewAthleteException.class)
	public void testCheckIfRaceHasMin2() throws TooFewAthleteException {
		cycle.getRaceAthletes().add(c4);
		cycle.checkIfRaceHasMin(cycle);
	}

	// Check if checkIfRaceHasMax() will throw the GameFullException if there are the maximum number of athletes
	// Expected outcome is that the test will fail
	@Test (expected=GameFullException.class)
	public void testCheckIfRaceHasMax1() throws GameFullException {
		cycle.getRaceAthletes().add(c5);
		cycle.getRaceAthletes().add(c6);
		cycle.getRaceAthletes().add(c7);
		cycle.getRaceAthletes().add(c8);
				
		cycle.checkIfRaceHasMax(cycle);
		
	}
	
	// Check if checkIfRaceHasMax() will throw the GameFullException if there are greater than the maximum number of athletes
	// Expected outcome is that the test will pass
	@Test (expected=GameFullException.class)
	public void testCheckIfRaceHasMax2() throws GameFullException {
		cycle.getRaceAthletes().add(c5);
		cycle.getRaceAthletes().add(c6);
		cycle.getRaceAthletes().add(c7);
		cycle.getRaceAthletes().add(c8);
		cycle.getRaceAthletes().add(c9);
					
		cycle.checkIfRaceHasMax(cycle);
			
	}
	
	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is no official for the race
	// Expected outcome is that the test will pass
	@Test (expected=NoRefereeException.class)
	public void testCheckIfRaceHasOfficial1() throws NoRefereeException {
		cycle.checkIfRaceHasOfficial(cycle);
		
	}
	
	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is an official for the race
	// Expected outcome is that the test will fail
	@Test (expected=NoRefereeException.class)
	public void testCheckIfRaceHasOfficial2() throws NoRefereeException {
		cycle.setOfficialForRace(official);
	
		cycle.checkIfRaceHasOfficial(cycle);
		
	}

}
