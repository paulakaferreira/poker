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
            String suitEmoji = "";
            String rankEmoji = "";

            switch (suit) {
                case CLUBS:
                    suitEmoji = "♣️";
                    break;
                case DIAMONDS:
                    suitEmoji = "♦️";
                    break;
                case HEARTS:
                    suitEmoji = "♥️";
                    break;
                case SPADES:
                    suitEmoji = "♠️";
                    break;
            }

            switch (rank) {
                case TWO:
                    rankEmoji = "2";
                    break;
                case THREE:
                    rankEmoji = "3";
                    break;
                case FOUR:
                    rankEmoji = "4";
                    break;
                case FIVE:
                    rankEmoji = "5";
                    break;
                case SIX:
                    rankEmoji = "6";
                    break;
                case SEVEN:
                    rankEmoji = "7";
                    break;
                case EIGHT:
                    rankEmoji = "8";
                    break;
                case NINE:
                    rankEmoji = "9";
                    break;
                case TEN:
                    rankEmoji = "10";
                    break;
                case JACK:
                    rankEmoji = "J";
                    break;
                case QUEEN:
                    rankEmoji = "Q";
                    break;
                case KING:
                    rankEmoji = "K";
                    break;
                case ACE:
                    rankEmoji = "A";
                    break;
            }

            return rankEmoji + suitEmoji;
        }

    }

    public class Deck {
        private List<Card> cards;
        private Random random;
        private List<Card> flop;
        private Card turn;
        private Card river;

        public Deck() {
            cards = new ArrayList<>();
            flop = new ArrayList<>();
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

        public List<Card> dealFlop() {
            for (int i = 0; i < 3; i++) {
                flop.add(drawCard());
            }
            return flop;
        }

        public void printFlop(List<Card> flop) {
            for (Card card : flop) {
                System.out.println(card);
            }
        }

        public Card dealTurn() {
            turn = cards.remove(cards.size() - 1);
            return turn;
        }

        public Card dealRiver() {
            river = cards.remove(cards.size() - 1);
            return river;
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
