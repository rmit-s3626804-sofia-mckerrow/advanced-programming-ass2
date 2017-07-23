package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.WrongTypeException;
import gameComponents.Cycle;
import gameComponents.Run;
import gameComponents.Runner;
import gameComponents.Swim;

public class RunnerTest {

	Runner runner;
	Cycle cycle;
	Run run;
	Swim swim;

	@Before
	public void setUp() throws Exception {
		runner = new Runner("r111", "Test Runner", "Runner", 20, "SA");
		cycle = new Cycle("c01");
		run = new Run("r01");
		swim = new Swim("s01");
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if a runner can be added to a Cycle game
	// Expected outcome is that the test will pass
	@Test
	public void testCanRaceInGame1() throws WrongTypeException {
		assertFalse(runner.canRaceInGame(cycle));
	}

	// Check if a runner can be added to a Run game
	// Expected outcome is that the test will pass
	@Test
	public void testCanRaceInGame2() throws WrongTypeException {
		assertTrue(runner.canRaceInGame(run));
	}

	// Check if a runner can be added to a Swim game
	// Expected outcome is that the test will pass
	@Test
	public void testCanRaceInGame3() throws WrongTypeException {
		assertFalse(runner.canRaceInGame(swim));
	}

}
