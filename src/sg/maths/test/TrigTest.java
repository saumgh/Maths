package sg.maths.test;
import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class TrigTest {

	@Test
	public void testNaN() {
		assertTrue(Double.isNaN(Trig.sin(Double.NaN)));
	}

	@Test
	public void testNegativeX() {
		double x1 = (new Random().nextDouble()) * 10;
		double x2 = (-1) * x1;
		assertTrue(Math.abs(Trig.sin(x1) + Trig.sin(x2)) < Trig.TOLERANCE);
	}

	@Test
	public void testTwoNPiePlusX() {
		double x1 = new Random().nextDouble() * 10;
		double x2 = 2 * Trig.PIE * (new Random().nextInt(100)) + x1;
		assertTrue(Math.abs(Trig.sin(x1) - Trig.sin(x2)) < Trig.TOLERANCE);
	}

	@Test
	public void testPiePlusX() {
		double x1 = new Random().nextDouble() * 10;
		double x2 = Trig.PIE + x1;
		assertTrue(Math.abs(Trig.sin(x1) + Trig.sin(x2)) < Trig.TOLERANCE);
	}

	@Test
	public void testPieMinusX() {
		double x1 = new Random().nextDouble() * 10;
		double x2 = Trig.PIE - x1;
		assertTrue(Math.abs(Trig.sin(x1) - Trig.sin(x2)) < Trig.TOLERANCE);
	}

	@Test
	public void testZero() {
		assertTrue(Math.abs(Trig.sin(0.0d)) < Trig.TOLERANCE);
	}

	@Test
	public void testPieBySix() {
		assertTrue(Math.abs(Trig.sin(Trig.PIE / 6) - 0.5d) < Trig.TOLERANCE);
	}

	@Test
	public void testPieByFour() {
		assertTrue(Math.abs(Trig.sin(Trig.PIE / 4) - 0.7071d) < Trig.TOLERANCE);
	}

	@Test
	public void testPieByThree() {
		assertTrue(Math.abs(Trig.sin(Trig.PIE / 3) - 0.8660d) < Trig.TOLERANCE);
	}

	@Test
	public void testPieByTwo() {
		assertTrue(Math.abs(Trig.sin(Trig.PIE / 2) - 1.0d) < Trig.TOLERANCE);
	}

	@Test
	public void testPie() {
		assertTrue(Math.abs(Trig.sin(Trig.PIE)) < Trig.TOLERANCE);
	}

	@Test
	public void testThreePieByTwo() {
		assertTrue(Math.abs(Trig.sin((3 * Trig.PIE) / 2) + 1) < Trig.TOLERANCE);
	}

	@Test
	public void testTwoPie() {
		assertTrue(Math.abs(Trig.sin(2 * Trig.PIE)) < Trig.TOLERANCE);
	}
}
