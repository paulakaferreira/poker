
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import poker.CardGame;
import poker.CardGame.*;
import poker.Player;
import poker.PokerGame;
import poker.PokerGame.*;

public class Main {
    public static void main(String[] args) {
        CardGame game = new CardGame();
        Deck deck = game.new Deck();
        PokerGame pokerGame = new PokerGame();

        deck.shuffle();

        Scanner scanner = new Scanner(System.in);

        List<Player> players = new ArrayList<>();

        System.out.print("Welcome to MIMO Texa's Hold'em Game !");
        System.out.println();

        // Prompt the user for their name
        System.out.print("Please enter your name to start: ");
        String playerName = scanner.nextLine();

        // Creates players list
        Player you = new Player(playerName);;
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        players.add(you);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        // Add cards to players' hands
        you.addToHand(deck.drawCard());
        you.addToHand(deck.drawCard());
        player2.addToHand(deck.drawCard());
        player2.addToHand(deck.drawCard());
        player3.addToHand(deck.drawCard());
        player3.addToHand(deck.drawCard());
        player4.addToHand(deck.drawCard());
        player4.addToHand(deck.drawCard());

        System.out.println("\n");
        System.out.println("----------------------------------");
        System.out.print("The cards have been distributed !");
        System.out.println("\n");
        System.out.println(you);

        // Sets each player's roles
        Role role = pokerGame.new Role(players);

        
        //List<CardGame.Card> flop = deck.dealFlop();
        //System.out.println("Flop:");
        //deck.printFlop(flop);
        //System.out.println("\n");

        //CardGame.Card turn = deck.dealTurn();
        //System.out.println("Turn:");
        //System.out.println(turn);
        //System.out.println("\n");

        //CardGame.Card river = deck.dealRiver();
        //System.out.println("River:");
        //System.out.println(river);
        //System.out.println("\n");

        //System.out.println(you.isBigBlind());
        //System.out.println(player2.isBigBlind());
        //System.out.println(player3.isBigBlind());
        //System.out.println(player4.isBigBlind());
        //System.out.println("\n");

        //System.out.println(you.isSmallBlind());
        //System.out.println(player2.isSmallBlind());
        //System.out.println(player3.isSmallBlind());
        //System.out.println(player4.isSmallBlind());
        //System.out.println("\n");

        //System.out.println(you.isDealer());
        //System.out.println(player2.isDealer());
        //System.out.println(player3.isDealer());
        //System.out.println(player4.isDealer());
        //System.out.println("\n");

        System.out.println("----------------------------------");
        System.out.print("Here are the roles for this round:");
        System.out.println();

        for (Player player : players) {
            if (player.isDealer() == true) {
                System.out.println("Dealer: " + player.getName());
            }
            if (player.isSmallBlind() == true) {
                System.out.println("Small Blind: " + player.getName());
            }
            if (player.isBigBlind() == true) {
                System.out.println("Big Blind: " + player.getName());
            }
        }

        System.out.println("----------------------------------");
        System.out.print("The Small Blind places its bet: ");

        for (Player player : players) {
            if (player.isSmallBlind() == true) {
                int small_blind = player.dealSmallBlind();
                System.out.println();
                System.out.println(player.getName() + " deals " + small_blind);
            }
        }

        System.out.println("----------------------------------");
        System.out.print("The Big Blind places its bet: ");

        for (Player player : players) {
            if (player.isBigBlind() == true) {
                int big_blind = player.dealBigBlind();
                System.out.println();
                System.out.println(player.getName() + " deals " + big_blind);
            }
        }

        scanner.close();
    }
}
