package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.WrongTypeException;
import gameComponents.Cycle;
import gameComponents.Run;
import gameComponents.Swim;
import gameComponents.Swimmer;

public class SwimmerTest {

	Swimmer swimmer;
	Cycle cycle;
	Run run;
	Swim swim;

	@Before
	public void setUp() throws Exception {
		swimmer = new Swimmer("s111", "Test Swimmer", "Swimmer", 20, "SA");
		cycle = new Cycle("c01");
		run = new Run("r01");
		swim = new Swim("s01");
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if a swimmer can be added to a Cycle game
	@Test
	public void testCanRaceInGame1() throws WrongTypeException {
		assertFalse(swimmer.canRaceInGame(cycle));
	}

	// Check if a swimmer can be added to a Run game
	@Test
	public void testCanRaceInGame2() throws WrongTypeException {
		assertFalse(swimmer.canRaceInGame(run));
	}

	// Check if a swimmer can be added to a Swim game
	@Test
	public void testCanRaceInGame3() throws WrongTypeException {
		assertTrue(swimmer.canRaceInGame(swim));
	}

}
