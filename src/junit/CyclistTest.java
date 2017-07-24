package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.WrongTypeException;
import gameComponents.Cycle;
import gameComponents.Cyclist;
import gameComponents.Run;
import gameComponents.Swim;

public class CyclistTest {
	
	Cyclist cyclist;
	Cycle cycle;
	Run run;
	Swim swim;

	@Before
	public void setUp() throws Exception {
		cyclist = new Cyclist("c111", "Test Cyclist", "Cyclist", 20, "SA");
		cycle = new Cycle("c01");
		run = new Run("r01");
		swim = new Swim("s01");
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if a cyclist can be added to a Cycle game
	@Test
	public void testCanRaceInGame1() throws WrongTypeException {
		assertTrue(cyclist.canRaceInGame(cycle));
	}
	
	// Check if a cyclist can be added to a Run game
	@Test
	public void testCanRaceInGame2() throws WrongTypeException {
		assertFalse(cyclist.canRaceInGame(run));
	}
	
	// Check if a cyclist can be added to a Swim game
	@Test
	public void testCanRaceInGame3() throws WrongTypeException {
		assertFalse(cyclist.canRaceInGame(swim));
	}

}
