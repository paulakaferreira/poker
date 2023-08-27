package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardGame {

    public enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }

    public enum Rank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public class Card {
        private Suit suit;
        private Rank rank;

        public Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }

        public Suit getSuit() {
            return suit;
        }

        public Rank getRank() {
            return rank;
        }

        public String toString() {
            return rank + " of " + suit;
        }
    }

    public class Deck {
        private List<Card> cards;
        private Random random;

        public Deck() {
            cards = new ArrayList<>();
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    Card card = new Card(suit, rank);
                    cards.add(card);
                }
            }
            random = new Random();
        }

        public void shuffle() {
            int n = cards.size();
            for (int i = n - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                Card temp = cards.get(i);
                cards.set(i, cards.get(j));
                cards.set(j, temp);
            }
        }

        public Card drawCard() {
            if (cards.isEmpty()) {
                throw new IllegalStateException("No more cards in the deck.");
            }
            return cards.remove(cards.size() - 1);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Card card : cards) {
                sb.append(card.toString()).append("\n");
            }
            return sb.toString();
        }
    }
}
