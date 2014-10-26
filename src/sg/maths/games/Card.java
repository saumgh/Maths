package sg.maths.games;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

enum Rank {
	  TWO	('2',  2),
    THREE	('3',  3),
	 FOUR	('4',  4),
     FIVE	('5',  5),
	  SIX 	('6',  6),
    SEVEN	('7',  7),
	EIGHT	('8',  8),
     NINE	('9',  9),
	  TEN	('T', 10),
     JACK	('J', 11),
	QUEEN	('Q', 12),
     KING	('K', 13),
      ACE	('A', 14);
	
    private final char value;
    private final int index;
    private static final Map<Character, Rank> lookup = new TreeMap<Character, Rank>();
    
    Rank(char value, int index) {
        this.value = value;
        this.index = index;
    }
    
    char value() {
        return value;
    }
    
    int index() {
        return index;
    }
    
    public String toString() {
    	return String.valueOf(value);
    }
    
	static {
		for (Rank rank : EnumSet.allOf(Rank.class)) {
			lookup.put(rank.value, rank);
		}
	}
	
	static Rank fromValue(char value) {
		return lookup.get(value);
	};
}

enum Suite {
	   CLUBS	('C'),
	DIAMONDS	('D'),
	  HEARTS	('H'),
	   SPADE	('S');
	
	private final char value;
    private static final Map<Character, Suite> lookup = new TreeMap<Character, Suite>();
	
	Suite(char value) {
		this.value = value;
	}
	
	char value() {
		return value;
	}
	
    public String toString() {
    	return String.valueOf(value);
    }
    
	static {
		for (Suite suite : EnumSet.allOf(Suite.class)) {
			lookup.put(suite.value, suite);
		}
	}
	
	static Suite fromValue(char value) {
		return lookup.get(value);
	};
};

class CardReverseSort implements Comparator<Card> {

	@Override
	public
	int compare(Card o1, Card o2) {
		if (o2.rank.index() == o1.rank.index()) {
			return o2.suite.value() - o1.suite.value();
		}
		return o2.rank.index() - o1.rank.index();
	}
}

public class Card {
	Rank rank;
	Suite suite;
	
	Card() {}
	
	Card(Rank rank, Suite suite) {
		this.rank = rank;
		this.suite = suite;
	}
	
	Card(String card) {
		this(Rank.fromValue(card.charAt(0)), Suite.fromValue(card.charAt(1))); 
	}
	
    public String toString() {
    	return String.valueOf(rank.value()) + String.valueOf(suite.value());
    }
}
