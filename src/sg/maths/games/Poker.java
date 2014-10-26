package sg.maths.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeSet;

enum PokerRank {
		  HIGH_CARD	 (1),
	       ONE_PAIR	 (2),
	       TWO_PAIR	 (3),
	THREE_OF_A_KIND	 (4),
	       STRAIGHT	 (5),
	          FLUSH	 (6),
	     FULL_HOUSE	 (7),
	 FOUR_OF_A_KIND	 (8),
	 STRAIGHT_FLUSH	 (9),
	    ROYAL_FLUSH	(10);
	
	private final int value;
	PokerRank(int value) {
		this.value = value;
	}
	int value() {
		return value;
	}
};

class PokerHand {
	TreeSet<Card> handCards = new TreeSet<Card>(new CardReverseSort());
	Map<Rank, Integer> rankCount = new HashMap<Rank, Integer>();
	PokerRank pokerRank = null;
	Rank baseRank1, baseRank2 = null;
	
	static final int STANDARD_SIZE = 5;
	
	PokerHand() {}
	
	PokerHand(Card card1, Card card2, Card card3, Card card4, Card card5) {
		handCards.add(card1);
		handCards.add(card2);
		handCards.add(card3);
		handCards.add(card4);
		handCards.add(card5);
	}
	
	public static PokerHand getOneFiveCardHand(String cardsStr) {
		return getNFiveCardHands(cardsStr, 1).get(0);
	}
	
	public static List<PokerHand> getTwoFiveCardHands(String cardsStr) {
		return getNFiveCardHands(cardsStr, 2);
	}
	
	public static List<PokerHand> getNFiveCardHands(String cardsStr, int n) {
		
		String[] cards = cardsStr.split(" ");
		if (cards.length != STANDARD_SIZE * n) {
			throw new RuntimeException(cardsStr + " contains " + cards.length + " cards while " 
																		+ (STANDARD_SIZE * n) + " was expected");
		}
		List<PokerHand> pokerHands = new ArrayList<PokerHand>();
		for (int i=0; i<n; ++i) {
			PokerHand pokerHand = new PokerHand();
			for (int j=0; j<STANDARD_SIZE; ++j) {
				pokerHand.handCards.add(new Card(cards[i * STANDARD_SIZE + j]));
			}
			pokerHands.add(pokerHand);
		}
		return pokerHands;
	}
	
	public String toString() {
		String cardsStr = "[";
		for (Card card : handCards) {
			if (cardsStr != "[") {
				cardsStr += " ";
			}
			cardsStr += card;
		}
		cardsStr += "]";
		return cardsStr;
	}
	
	PokerRank value() {
		if (pokerRank != null) {
			return pokerRank;
		}
		boolean straight = true, sameSuite = true;
		Rank lastRank = null;
		for (Card card : handCards) {
			Integer count = rankCount.get(card.rank);
			if (count == null) {
				count = 0;
			}
			rankCount.put(card.rank, count+1);
			if (lastRank != null) {
				if (straight && (card.rank.index() != lastRank.index()-1)) {
					straight = false;
				}
			}
			lastRank = card.rank;
			if (sameSuite && (card.suite != handCards.first().suite)) {
				sameSuite = false;
			}
		}
		
		baseRank1 = handCards.first().rank;
		if (sameSuite) {
			if (straight) {
				if (baseRank1 == Rank.ACE) {
					pokerRank = PokerRank.ROYAL_FLUSH;
					return pokerRank;
				} else {
					pokerRank = PokerRank.STRAIGHT_FLUSH;					
					return pokerRank;
				}
			} else {
				pokerRank = PokerRank.FLUSH;				
				return pokerRank;
			}
		} else if (straight) {
			pokerRank = PokerRank.STRAIGHT;
			return pokerRank;
		}
		
		boolean threeOfAKind = false;
		int pairs = 0;
		for (Entry<Rank, Integer> entry : rankCount.entrySet()) {
			if (entry.getValue() == 4) {
				baseRank1 = entry.getKey();
				return PokerRank.FOUR_OF_A_KIND;
			} else if (entry.getValue() == 3) {
				threeOfAKind = true;
				if (pairs == 1) {
					baseRank2 = baseRank1;
				}
				baseRank1 = entry.getKey();
			} else if (entry.getValue() == 2) {
				++pairs;
				if (pairs == 1) {
					if (!threeOfAKind) {
						baseRank1 = entry.getKey();
					} else {
						baseRank2 = entry.getKey();
					}
				} else if (pairs == 2) {
					if (baseRank1.value() < entry.getKey().value()) {
						baseRank2 = baseRank1;
						baseRank1 = entry.getKey();
					} else {
						baseRank2 = entry.getKey();						
					}
				}
			}
		}
		if (threeOfAKind) {
			if (pairs == 1) {
				pokerRank = PokerRank.FULL_HOUSE;
				return pokerRank;
			} else {
				pokerRank = PokerRank.THREE_OF_A_KIND;
				return pokerRank;
			}
		}
		if (pairs == 2) {
			pokerRank = PokerRank.TWO_PAIR;
			return pokerRank;
		} else if (pairs == 1) {
			pokerRank = PokerRank.ONE_PAIR;
			return pokerRank;
		}
		
		pokerRank = PokerRank.HIGH_CARD;
		return pokerRank;
	}
	
	int compare (PokerHand pokerHands) {
		if (pokerRank == null) {
			pokerRank = value();
		}
		if (pokerHands.pokerRank == null) {
			pokerHands.pokerRank = pokerHands.value();
		}
		int diff = 0;
		if (pokerRank == pokerHands.pokerRank) {
			switch (pokerRank) {
				case STRAIGHT_FLUSH:
				case STRAIGHT:
				case FOUR_OF_A_KIND:
				case THREE_OF_A_KIND:
					diff = baseRank1.index() - pokerHands.baseRank1.index();
					break;
				case FULL_HOUSE:
				case TWO_PAIR:
				case ONE_PAIR:
					if (baseRank1 != pokerHands.baseRank1) {
						diff = baseRank1.index() - pokerHands.baseRank1.index();
						break;
					} else if (baseRank2 != pokerHands.baseRank2) {
						diff = baseRank2.index() - pokerHands.baseRank2.index();
						break;
					}
				case FLUSH:
				case HIGH_CARD:
					Iterator<Card> iter1 = handCards.iterator();
					Iterator<Card> iter2 = pokerHands.handCards.iterator();
					Rank rank1, rank2;
					while (iter1.hasNext() && iter2.hasNext()) {
						rank1 = iter1.next().rank;
						rank2 = iter2.next().rank;
						if (rank1 != rank2) {
							if (baseRank1 != rank1) {
								baseRank2 = rank1;
								pokerHands.baseRank2 = rank2;
							}
							diff = rank1.index() - rank2.index();
							break;
						}
					}
					break;
				default:
					throw new RuntimeException("Unhandled poker rank = " + pokerRank);
			}
		} else {
			diff = pokerRank.value() - pokerHands.pokerRank.value();
		}
		
		System.out.println("Poker Hand 1 " + this + " has value " + pokerRank + " with base rank(s) "
													+ baseRank1.value() + ((baseRank2 == null) ? "" : ", " + baseRank2.value()));
		System.out.println("Poker Hand 2 " + pokerHands + " has value " + pokerHands.pokerRank + " with base rank(s) "
					+ pokerHands.baseRank1.value() + ((pokerHands.baseRank2 == null) ? "" : ", " + pokerHands.baseRank2.value()));
		
		System.out.println(" => " + (diff>0 ? "Player 1" : (diff==0 ? "Nobody" : "Player 2")) + " wins");
		return diff;
	}
}

public class Poker {
	
	public static void main(String... args) {
		
		checkPokerWinner("/Users/saughosh/My/gen/Tech/Q/Maths/data/poker.txt");
	}
	
	/* http://projecteuler.net/problem=54 */
	static void checkPokerWinner(String fileName) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		List<PokerHand> pokerHands;
		int diff, win1 = 0, win2 = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			pokerHands = PokerHand.getTwoFiveCardHands(line);
			if (pokerHands.size() < 2) {
				continue;
			}
			diff = pokerHands.get(0).compare(pokerHands.get(1));
			if (diff > 0) {
				++win1;
			} else if (diff < 0) {
				++win2;
			}
		}
		System.out.println("Player 1 wins " + win1 + " times while player 2 wins " + win2 + " times");
	}
}
