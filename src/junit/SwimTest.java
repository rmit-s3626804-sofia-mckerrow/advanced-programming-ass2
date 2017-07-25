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
import gameComponents.Official;
import gameComponents.Swim;
import gameComponents.Swimmer;

public class SwimTest {

	Swim swim;
	Swimmer s1;
	Swimmer s2;
	Swimmer s3;
	Swimmer s4;
	Swimmer s5;
	Swimmer s6;
	Swimmer s7;
	Swimmer s8;
	Swimmer s9;
	Official official;

	@Before
	public void setUp() throws Exception {
		swim = new Swim("s01");
		s1 = new Swimmer("s111", "Test Swimmer", "Swimmer", 20, "QLD");
		s2 = new Swimmer("s222", "Test Swimmer", "Swimmer", 20, "QLD");
		s3 = new Swimmer("s333", "Test Swimmer", "Swimmer", 20, "QLD");
		s4 = new Swimmer("s444", "Test Swimmer", "Swimmer", 20, "QLD");
		s5 = new Swimmer("s555", "Test Swimmer", "Swimmer", 20, "QLD");
		s6 = new Swimmer("s666", "Test Swimmer", "Swimmer", 20, "QLD");
		s7 = new Swimmer("s777", "Test Swimmer", "Swimmer", 20, "QLD");
		s8 = new Swimmer("s888", "Test Swimmer", "Swimmer", 20, "QLD");
		s9 = new Swimmer("s999", "Test Swimmer", "Swimmer", 20, "QLD");
		official = new Official("0111", "Test Official", "Official", 30, "NSW");

		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		athletes.add(s1);
		athletes.add(s2);
		athletes.add(s3);
		swim.setAthletesForRace(athletes);
	}

	@After
	public void tearDown() throws Exception {
	}

	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there is less than the minimum number of athletes
	@Test(expected = TooFewAthleteException.class)
	public void testCheckIfRaceHasMin1() throws TooFewAthleteException {
		swim.checkIfRaceHasMin(swim);
	}

	// Check if checkIfRaceHasMin() will throw the TooFewAthleteException if there is the minimum number of athletes
	@Test
	public void testCheckIfRaceHasMin2() {
		TooFewAthleteException exception = null;
		swim.getRaceAthletes().add(s4);

		try {
			swim.checkIfRaceHasMin(swim);
		} catch (TooFewAthleteException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}

	// Check if checkIfRaceHasMax() will throw the GameFullException if there is the maximum number of athletes
	@Test
	public void testCheckIfRaceHasMax1() {
		GameFullException exception = null;
		swim.getRaceAthletes().add(s5);
		swim.getRaceAthletes().add(s6);
		swim.getRaceAthletes().add(s7);
		swim.getRaceAthletes().add(s8);

		try {
			swim.checkIfRaceHasMax(swim);
		} catch (GameFullException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}

	// Check if checkIfRaceHasMax() will throw the GameFullException if there is greater than the maximum number of athletes
	@Test(expected = GameFullException.class)
	public void testCheckIfRaceHasMax2() throws GameFullException {
		swim.getRaceAthletes().add(s5);
		swim.getRaceAthletes().add(s6);
		swim.getRaceAthletes().add(s7);
		swim.getRaceAthletes().add(s8);
		swim.getRaceAthletes().add(s9);

		swim.checkIfRaceHasMax(swim);
	}

	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is no official for the race
	@Test(expected = NoRefereeException.class)
	public void testCheckIfRaceHasOfficial1() throws NoRefereeException {
		swim.checkIfRaceHasOfficial(swim);
	}

	// Check if checkIfRaceHasOfficial() will throw the NoRefereeException if there is an official for the race
	@Test
	public void testCheckIfRaceHasOfficial2() {
		NoRefereeException exception = null;
		swim.setOfficialForRace(official);

		try {
			swim.checkIfRaceHasOfficial(swim);
		} catch (NoRefereeException e) {
			exception = e;
			e.printStackTrace();
		}

		assertNull(exception);
	}
}
