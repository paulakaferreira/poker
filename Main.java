
import poker.CardGame;
import poker.CardGame.*;

public class Main {
    public static void main(String[] args) {
        CardGame game = new CardGame();
        Deck deck = game.new Deck();

        deck.shuffle();

        System.out.println("Deck:");
        System.out.println(deck);

        System.out.println("Shuffled Deck:");
        System.out.println(deck);

        Card drawnCard = deck.drawCard();
        System.out.println("Drawn card: " + drawnCard);
    }
}
