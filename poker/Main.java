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

    // Creates list of players depending on the dificulty level
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

    static class HandRankAndPlayerIndex implements Comparable<HandRankAndPlayerIndex> {
        private HandEval.HandRank handRank;
        private int playerIndex;

        public HandRankAndPlayerIndex(HandEval.HandRank handRank, int playerIndex) {
            this.handRank = handRank;
            this.playerIndex = playerIndex;
        }

        public HandEval.HandRank getHandRank() {
            return handRank;
        }

        public int getPlayerIndex() {
            return playerIndex;
        }

        @Override
        public int compareTo(HandRankAndPlayerIndex other) {
            return other.handRank.compareTo(handRank);
        }
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
        int raise = 0;
        int int_raise = 0;

        for (Player player : role.getRemainingPlayers(players)) {
            if (player == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, big_blind);
                    pot += call;
                } else if (chosen_action.equals("2")) {
                    System.out.println("You have decided to raise the amount on the table!");
                    System.out.println("Your total number of chips is: " + you.getChips());
                    System.out.print("Please indicate the amount you wish to raise: ");
                    String chosen_raise = scanner.nextLine();
                    int_raise = Integer.parseInt(chosen_raise);
                    int min_raise = big_blind * 2; // Minimum raise is double the big blind
                    if (int_raise < min_raise) {
                        System.out.println("You need to raise at least " + min_raise);
                        continue; // Repeat the loop to allow the player to choose again
                    }
                    int your_raise = role.raise(you, big_blind, int_raise);
                    raise = 1;
                    pot += your_raise;
                } else if (chosen_action.equals("3")) {
                    role.fold(players, you);
                }
            } else {
                int call = role.call(player, big_blind);
                pot += call;
            }
        }

        if (you != null && you.isSmallBlind()) {
            System.out.println("----------------------------------");
            System.out.println("You were the Small Blind for this round.");
            System.out.println("Do you wish to complete your bet and stay in the game?");
            System.out.println("1 - Yes");
            System.out.println("2 - Fold (quit round)");
            System.out.print("Your answer: ");
            String small_chosen_action = scanner.nextLine();
            if (small_chosen_action.equals("1")) {
                int complete_small_blind = role.printSmallBlindCompletion(you);
                pot += complete_small_blind;
            } else if (small_chosen_action.equals("2")) {
                role.fold(players, you);
            }
        } else {
            for (Player player : players) {
                if (player.isSmallBlind()) {
                    int complete_small_blind = role.printSmallBlindCompletion(player);
                    pot += complete_small_blind;
                }
            }
        }

        // Continues the game if player 'you' decided to raise
        if (raise == 1) {
            for (Player player : players) {
                if (player != you) {
                    int call = role.call(player, int_raise);
                    pot += call;
                }
            }
            raise = 0;
        }

        System.out.println("----------------------------------");
        System.out.println("End of first round.");
        System.out.println("Total amount on the table: " + pot);

        // Second round starts here
        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 2 - FLOP");
        System.out.println("----------------------------------");

        // Distribute the flop cards
        Card flop1 = deck.drawCard();
        Card flop2 = deck.drawCard();
        Card flop3 = deck.drawCard();
        System.out.println("Flopped Cards: " + flop1 + ", " + flop2 + ", " + flop3);
        System.out.println("----------------------------------");

        if (you != null && players.contains(you)) {
            System.out.println(you);
        }

        // Betting starts again but this time starting with the dealer
        int dealerIndex = players.indexOf(dealer);

        for (int i = dealerIndex; i < players.size() + dealerIndex; i++) {
            Player currentPlayer = players.get(i % players.size());
        
            if (currentPlayer == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, 25);
                    pot += call;
                } else if (chosen_action.equals("2")) {
                    System.out.println("You have decided to raise the amount on the table!");
                    System.out.println("Your total number of chips is: " + you.getChips());
                    System.out.print("Please indicate the amount you wish to raise: ");
                    String chosen_raise = scanner.nextLine();
                    int_raise = Integer.parseInt(chosen_raise);
                    int min_raise = big_blind * 2; // Minimum raise is double the big blind
                    if (int_raise < min_raise) {
                        System.out.println("You need to raise at least " + min_raise);
                        continue; // Repeat the loop to allow the player to choose again
                    }
                    int your_raise = role.raise(you, 25, int_raise);
                    raise = 1;
                    pot += your_raise;
                } else if (chosen_action.equals("3")) {
                    role.fold(players, you);
                }
            } else {
                int call = role.call(currentPlayer, 25);
                pot += call; 
            }
        }

        // Continues the game if player 'you' decided to raise
        if (raise == 1) {
            for (Player player : players) {
                if (player != you) {
                    int call = role.call(player, int_raise);
                    pot += call;
                }
            }
            raise = 0;
        }

        System.out.println("----------------------------------");
        System.out.println("End of second round.");
        System.out.println("Total amount on the table: " + pot);


        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 3 - TURN");
        System.out.println("----------------------------------");


        // Distribute the flop cards
        Card flop4 = deck.drawCard();
        System.out.println("Flopped Cards: " + flop1 + ", " + flop2 + ", " + flop3 + ",  "+ flop4);

        // Betting starts again but this time starting from the person after the dealer
        for (int i = (dealerIndex + 2) % players.size(); i < players.size() + dealerIndex; i++) {
            Player currentPlayer = players.get(i % players.size());
        
            if (currentPlayer == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, 25);
                    pot += call;
                } else if (chosen_action.equals("2")) {
                    System.out.println("You have decided to raise the amount on the table!");
                    System.out.println("Your total number of chips is: " + you.getChips());
                    System.out.print("Please indicate the amount you wish to raise: ");
                    String chosen_raise = scanner.nextLine();
                    int_raise = Integer.parseInt(chosen_raise);
                    int min_raise = big_blind * 2; // Minimum raise is double the big blind
                    if (int_raise < min_raise) {
                        System.out.println("You need to raise at least " + min_raise);
                        continue; // Repeat the loop to allow the player to choose again
                    }
                    int your_raise = role.raise(you, 25, int_raise);
                    raise = 1;
                    pot += your_raise;
                } else if (chosen_action.equals("3")) {
                    role.fold(players, you);
                }
            } else {
                int call = role.call(currentPlayer, 25);
                pot += call;
            }
        }

        // Continues the game if player 'you' decided to raise
        if (raise == 1) {
            for (Player player : players) {
                if (player != you) {
                    int call = role.call(player, int_raise);
                    pot += call;
                }
            }
            raise = 0;
        }


        System.out.println("----------------------------------");
        System.out.println("End of third round.");
        System.out.println("Total amount on the table: " + pot);


        System.out.println("----------------------------------");
        System.out.println("BETTING ROUND 4 - RIVER");
        System.out.println("----------------------------------");


        for (int i = (dealerIndex + 3) % players.size(); i < players.size() + dealerIndex; i++) {
            Player currentPlayer = players.get(i % players.size());
        
            if (currentPlayer == you) {
                role.menu();
                System.out.print("Please choose your action: ");
                String chosen_action = scanner.nextLine();
                if (chosen_action.equals("1")) {
                    int call = role.call(you, 25);
                    pot += call;
                } else if (chosen_action.equals("2")) {
                    System.out.println("You have decided to raise the amount on the table!");
                    System.out.println("Your total number of chips is: " + you.getChips());
                    System.out.print("Please indicate the amount you wish to raise: ");
                    String chosen_raise = scanner.nextLine();
                    int_raise = Integer.parseInt(chosen_raise);
                    int min_raise = big_blind * 2; // Minimum raise is double the big blind
                    if (int_raise < min_raise) {
                        System.out.println("You need to raise at least " + min_raise);
                        continue; // Repeat the loop to allow the player to choose again
                    }
                    int your_raise = role.raise(you, 25, int_raise);
                    raise = 1;
                    pot += your_raise;
                } else if (chosen_action.equals("3")) {
                    role.fold(players, you);
                }
            } else {
                int call = role.call(currentPlayer, 25);
                pot += call;
            }
        }

        // Continues the game if player 'you' decided to raise
        if (raise == 1) {
            for (Player player : players) {
                if (player != you) {
                    int call = role.call(player, int_raise);
                    pot += call;
                }
            }
            raise = 0;
        }

        System.out.println("----------------------------------");
        System.out.println("End of fourth round.");
        System.out.println("Total amount on the table: " + pot);


        System.out.println("----------------------------------");
        System.out.println("TIME FOR THE SHOWNDOWN");
        System.out.println("");


        for (Player player : players) {
            System.out.println(player);
        }

        List<Card> communityCards = new ArrayList<>();

        communityCards.add(flop1);
        communityCards.add(flop2);
        communityCards.add(flop2);
        communityCards.add(flop3);
        communityCards.add(flop4);

        for (Player player : players) {
            for (Card card : communityCards) {
                player.addToHand(card);
            }
        }

        // Create a list to hold evaluated hands and player indexes
        List<HandRankAndPlayerIndex> evaluatedHands = new ArrayList<>();

        // Loop through players and evaluate their hands
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<Card> playerHand = player.getHand();  // Get the player's hole cards + community cards
            HandEval.HandRank handRank = HandEval.evaluateHand(playerHand);
            evaluatedHands.add(new HandRankAndPlayerIndex(handRank, i));
        }

        // Sort the evaluated hands list in descending order of hand rank
        Collections.sort(evaluatedHands);

        // Get the highest ranked hand
        HandEval.HandRank highestRank = evaluatedHands.get(0).getHandRank();

        // Find the players with the highest ranked hand
        List<Player> winners = new ArrayList<>();
        for (HandRankAndPlayerIndex evaluatedHand : evaluatedHands) {
            if (evaluatedHand.getHandRank() == highestRank) {
                winners.add(players.get(evaluatedHand.getPlayerIndex()));
            }
        }

        // Determine the winner(s)
        if (winners.size() == 1) {
            Player winner = winners.get(0);
            System.out.println("The winner is: " + winner.getName());
        } else {
            System.out.println("It's a tie between the following players:");
            for (Player winner : winners) {
                System.out.println(winner.getName());
            }
        }

        scanner.close();
    }
}
