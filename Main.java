
import poker.CardGame;
import poker.CardGame.*;
import poker.Player;

public class Main {
    public static void main(String[] args) {
        CardGame game = new CardGame();
        Deck deck = game.new Deck();

        deck.shuffle();

        System.out.println("Deck:");
        System.out.println(deck);

        Player you = new Player("You");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");

        you.addToHand(deck.drawCard());
        you.addToHand(deck.drawCard());

        player2.addToHand(deck.drawCard());
        player2.addToHand(deck.drawCard());

        player3.addToHand(deck.drawCard());
        player3.addToHand(deck.drawCard());

        player4.addToHand(deck.drawCard());
        player4.addToHand(deck.drawCard());

        System.out.println(you);
        System.out.println(player2);
        System.out.println(player3);
        System.out.println(player4);

    }
}
