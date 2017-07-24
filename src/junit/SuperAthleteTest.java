package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.WrongTypeException;
import gameComponents.Cycle;
import gameComponents.Run;
import gameComponents.SuperAthlete;
import gameComponents.Swim;

public class SuperAthleteTest {

	SuperAthlete superAthlete;
	Cycle cycle;
	Run run;
	Swim swim;

	@Before
	public void setUp() throws Exception {
		superAthlete = new SuperAthlete("s111", "Test SuperAthlete", "SuperAthlete", 20, "SA");
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if a superathlete can be added to a Cycle game
	@Test
	public void testCanRaceInGame1() throws WrongTypeException {
		assertTrue(superAthlete.canRaceInGame(cycle));
	}

	// Check if a superathlete can be added to a Run game
	@Test
	public void testCanRaceInGame2() throws WrongTypeException {
		assertTrue(superAthlete.canRaceInGame(run));
	}

	// Check if a superathlete can be added to a Swim game
	@Test
	public void testCanRaceInGame3() throws WrongTypeException {
		assertTrue(superAthlete.canRaceInGame(swim));
	}

}
