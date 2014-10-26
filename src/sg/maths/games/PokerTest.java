package sg.maths.games;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PokerTest {

	PokerHand pokerHand1;
	PokerHand pokerHand2;

	@Test
	public void test1() {

		pokerHand1 = PokerHand.getOneFiveCardHand("5H 5C 6S 7S KD");
		pokerHand2 = PokerHand.getOneFiveCardHand("2C 3S 8S 8D TD");
		assertTrue(pokerHand1.compare(pokerHand2) < 0);
	}
	
	@Test
	public void test2() {

		pokerHand1 = PokerHand.getOneFiveCardHand("5D 8C 9S JS AC");
		pokerHand2 = PokerHand.getOneFiveCardHand("2C 5C 7D 8S QH");
		assertTrue(pokerHand1.compare(pokerHand2) > 0);
	}
	
	@Test
	public void test3() {

		pokerHand1 = PokerHand.getOneFiveCardHand("2D 9C AS AH AC");
		pokerHand2 = PokerHand.getOneFiveCardHand("3D 6D 7D TD QD");
		assertTrue(pokerHand1.compare(pokerHand2) < 0);
	}
	
	@Test
	public void test4() {

		pokerHand1 = PokerHand.getOneFiveCardHand("4D 6S 9H QH QC");
		pokerHand2 = PokerHand.getOneFiveCardHand("3D 6D 7H QD QS");
		assertTrue(pokerHand1.compare(pokerHand2) > 0);
	}
	
	@Test
	public void test5() {
		pokerHand1 = PokerHand.getOneFiveCardHand("2H 2D 4C 4D 4S");
		pokerHand2 = PokerHand.getOneFiveCardHand("3C 3D 3S 9S 9D");
		assertTrue(pokerHand1.compare(pokerHand2) > 0);
	}
	
	@Test
	public void test6() {
		pokerHand1 = PokerHand.getOneFiveCardHand("TH JH QH KH AH");
		pokerHand2 = PokerHand.getOneFiveCardHand("9S TS JS QS KS");
		assertTrue(pokerHand1.compare(pokerHand2) > 0);
	}	
	
	@Test
	public void test7() {
		pokerHand1 = PokerHand.getOneFiveCardHand("2C 3D 4H 5S 6C");
		pokerHand2 = PokerHand.getOneFiveCardHand("2C 2D 2H 2S AC");
		assertTrue(pokerHand1.compare(pokerHand2) < 0);
	}	
	
	@Test
	public void test8() {
		pokerHand1 = PokerHand.getOneFiveCardHand("2C 3D 6H 2S 6C");
		pokerHand2 = PokerHand.getOneFiveCardHand("2D 6D 2H 6S 4C");
		assertTrue(pokerHand1.compare(pokerHand2) < 0);
	}	

	@Test
	public void test9() {
		pokerHand1 = PokerHand.getOneFiveCardHand("2D 9C AS TH TC");
		pokerHand2 = PokerHand.getOneFiveCardHand("3D 6C JS JH QC");
		assertTrue(pokerHand1.compare(pokerHand2) < 0);
	}	
}
