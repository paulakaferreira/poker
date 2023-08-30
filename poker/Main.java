package poker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import poker.CardGame.*;
import poker.Player;
import poker.PokerGame;
import poker.PokerGame.*;


public class Main {

    // Créer une liste de joueurs en fonction de la difficulté
    public static List<Player> createPlayers(String difficulty, String playerName) {
        List<Player> players = new ArrayList<>();
        List<String> possiblePlayers = Arrays.asList("Florian", "Charles", "Nadine", "Poornima", "Lucas", "Gabriel", "Solene", "Louis", "Chloe");
        Collections.shuffle(possiblePlayers);

        int numberOfPlayers = difficulty.equalsIgnoreCase("easy") ? 3 :
                difficulty.equalsIgnoreCase("medium") ? 6 :
                        10; // difficult

        players.add(new Player(playerName));
        players.addAll(possiblePlayers.subList(0, numberOfPlayers - 1).stream().map(Player::new).collect(Collectors.toList()));
        return players;
    }

    public static void main(String[] args) {
        CardGame game = new CardGame();
        Deck deck = game.new Deck();
        PokerGame pokerGame = new PokerGame();
        int dealer_turn = 1;
        int pot = 0;

        deck.shuffle();

        Scanner scanner = new Scanner(System.in);
        List<Player> players;

        System.out.print("Welcome to MIMO Texa's Hold'em Game!");
        System.out.println();

        System.out.print("Please enter your name to start: ");
        String playerName = scanner.nextLine();

        System.out.print("Choose difficulty (easy, medium, hard): ");
        String difficulty = scanner.nextLine();

        players = createPlayers(difficulty, playerName);

        // Add cards to players' hands
        for (Player player : players) {
            player.addToHand(deck.drawCard());
            player.addToHand(deck.drawCard());
        }

        System.out.println("----------------------------------");
        System.out.println("The cards have been shuffled and distributed!");
        System.out.println();

        Player you = players.stream()
                .filter(player -> playerName.equals(player.getName()))
                .findFirst()
                .orElse(null);

        if (you != null) {
            System.out.println(you);
        }

        // Sets each player's roles
        Role role = pokerGame.new Role(players);

        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 1 - PRE-FLOP");
        System.out.println("----------------------------------");

        role.printRole(players);
        int small_blind = role.printSmallBlind(players);
        pot += small_blind;
        int big_blind = role.printBigBlind(players);
        pot += big_blind;
        Player dealer = role.getDealer(players);

        for (Player player : role.getRemainingPlayers(players)) {
            if (player == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, big_blind);
                    pot += call;
                }
            } else {
                int call = role.call(player, big_blind);
                pot += call;
            }
        }

        if (you != null && you.isSmallBlind()) {
            System.out.println("You were the Small Blind for this round.");
            System.out.println("Do you wish to complete your bet?");
            System.out.println("1 - Yes");
            System.out.println("2 - Fold (quit round)");
            System.out.print("Your answer: ");
            String small_chosen_action = scanner.nextLine();
            if (small_chosen_action.equals("1")) {
                int complete_small_blind = role.printSmallBlindCompletion(you);
                pot += complete_small_blind;
            }
        } else {
            for (Player player : players) {
                if (player.isSmallBlind()) {
                    int complete_small_blind = role.printSmallBlindCompletion(player);
                    pot += complete_small_blind;
                }
            }
        }

        System.out.println("----------------------------------");
        System.out.println("End of first round.");
        System.out.println("Total amount on the table: " + pot);
        System.out.println("----------------------------------");

        // Second round starts here
        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 2 - FLOP");
        System.out.println("----------------------------------");
        role.rotateRoles(players);
        role.printRole(players);

        // Distribute the flop cards
        Card flop1 = deck.drawCard();
        Card flop2 = deck.drawCard();
        Card flop3 = deck.drawCard();
        System.out.println("Flopped Cards: " + flop1 + ", " + flop2 + ", " + flop3);

        // Betting starts again but this time without small and big blinds
        for (Player player : players) {
            if (player == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                // Implement your logic for 'Call', 'Raise', and 'Fold'
            } else {
                
            }
        }


        System.out.println("----------------------------------");
        System.out.println("End of second round.");
        System.out.println("Total amount on the table: " + pot);
        System.out.println("----------------------------------");

        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 3 - TURN");
        System.out.println("----------------------------------");

        role.rotateRoles(players);
        role.printRole(players);

        // Distribute the flop cards
        Card flop4 = deck.drawCard();
        System.out.println("Flop Cards: " + flop1 + ", " + flop2 + ", " + flop3 + ",  "+ flop4);

        // Betting starts again but this time without small and big blinds
        for (Player player : players) {
            if (player == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                // Implement your logic for 'Call', 'Raise', and 'Fold'
            } else {
                
            }
        }


        System.out.println("----------------------------------");
        System.out.println("End of third round.");
        System.out.println("Total amount on the table: " + pot);
        System.out.println("----------------------------------");

        // Le quatrième tour commence ici
        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 4 - RIVER");
        System.out.println("----------------------------------");
        role.rotateRoles(players);
        role.printRole(players);

        for (Player player : role.getRemainingPlayers(players)) {
            if (player == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, big_blind);  
                    pot += call;
                }
                
            } else {
                int call = role.call(player, big_blind);  
                pot += call;
            }
        }

        System.out.println("----------------------------------");
        System.out.println("End of fourth round.");
        System.out.println("Total amount on the table: " + pot);
        System.out.println("----------------------------------");
 
        scanner.close();
    }
}
