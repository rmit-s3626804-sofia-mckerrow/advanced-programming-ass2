package junit;

// @author: Sofia

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;
import gameComponents.Athlete;
import gameComponents.Official;
import gameComponents.Run;
import gameComponents.Runner;

public class RunTest {

	Run run;
	Runner r1;
	Runner r2;
	Runner r3;
	Runner r4;
	Runner r5;
	Runner r6;
	Runner r7;
	Runner r8;
	Runner r9;
	Official official;

	@Before
	public void setUp() throws Exception {
		run = new Run("runRace");
		r1 = new Runner("r111", "Test Runner", "Runner", 20, "VIC");
		r2 = new Runner("c222", "Test Runner", "Runner", 20, "VIC");
		r3 = new Runner("c333", "Test Runner", "Runner", 20, "VIC");
		r4 = new Runner("c444", "Test Runner", "Runner", 20, "VIC");
		r5 = new Runner("c555", "Test Runner", "Runner", 20, "VIC");
		r6 = new Runner("c666", "Test Runner", "Runner", 20, "VIC");
		r7 = new Runner("c777", "Test Runner", "Runner", 20, "VIC");
		r8 = new Runner("c888", "Test Runner", "Runner", 20, "VIC");
		r9 = new Runner("c999", "Test Runner", "Runner", 20, "VIC");
		official = new Official("0111", "Test Official", "Official", 30, "NSW");

		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		athletes.add(r1);
		athletes.add(r2);
		athletes.add(r3);
		run.setAthletesForRace(athletes);
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there are less than the minimum number of athletes
	@Test(expected = TooFewAthleteException.class)
	public void testCheckIfRaceHasMin1() throws TooFewAthleteException {
		run.checkIfRaceHasMin(run);
	}

	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there are the minimum number of athletes
	@Test
	public void testCheckIfRaceHasMin2() {
		TooFewAthleteException exception = null;
		run.getRaceAthletes().add(r4);

		try {
			run.checkIfRaceHasMin(run);
		} catch (TooFewAthleteException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);

	}

	// Check if checkIfRaceHasMax() will throw the GameFullException if there are the maximum number of athletes
	@Test
	public void testCheckIfRaceHasMax1() {
		GameFullException exception = null;
		run.getRaceAthletes().add(r5);
		run.getRaceAthletes().add(r6);
		run.getRaceAthletes().add(r7);
		run.getRaceAthletes().add(r8);

		try {
			run.checkIfRaceHasMax(run);
		} catch (GameFullException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}

	// Check if checkIfRaceHasMax() will throw the GameFullException if there are greater than the maximum number of athletes
	@Test(expected = GameFullException.class)
	public void testCheckIfRaceHasMax2() throws GameFullException {
		run.getRaceAthletes().add(r5);
		run.getRaceAthletes().add(r6);
		run.getRaceAthletes().add(r7);
		run.getRaceAthletes().add(r8);
		run.getRaceAthletes().add(r9);

		run.checkIfRaceHasMax(run);

	}

	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is no official for the race
	@Test(expected = NoRefereeException.class)
	public void testCheckIfRaceHasOfficial1() throws NoRefereeException {
		run.checkIfRaceHasOfficial(run);

	}

	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is an official for the race
	@Test
	public void testCheckIfRaceHasOfficial2() {
		NoRefereeException exception = null;
		run.setOfficialForRace(official);

		try {
			run.checkIfRaceHasOfficial(run);
		} catch (NoRefereeException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);

	}

}
