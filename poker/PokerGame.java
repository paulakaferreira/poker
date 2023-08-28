package poker;

import java.util.ArrayList;
import java.util.List;

public class PokerGame {
    
    public class Role {

        public Role(List<Player> players) {

            // Randomly choose the dealer, small blind, and big blind
            int dealerIndex = (int) (Math.random() * players.size());
            int smallBlindIndex = (dealerIndex + 1) % players.size();
            int bigBlindIndex = (dealerIndex + 2) % players.size();

            // Set up the roles
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                player.setDealer(i == dealerIndex);
                player.setSmallBlind(i == smallBlindIndex);
                player.setBigBlind(i == bigBlindIndex);

            }
        }

        public Player getDealer(List<Player> players) {
            for (Player player : players) {
                if (player.isDealer() == true) {
                    return player;
                }
            }
            return null;
        }

        public Player getSmallBlind(List<Player> players) {
            for (Player player : players) {
                if (player.isSmallBlind() == true) {
                    return player;
                }
            }
            return null;
        }

        public Player getBigBlind(List<Player> players) {
            for (Player player : players) {
                if (player.isBigBlind() == true) {
                    return player;
                }
            }
            return null;
        }

        public void printRole(List<Player> players) {
            System.out.print("Here are the roles for this round:");
            System.out.println();
            System.out.println("Dealer: " + getDealer(players).getName());
            System.out.println("Small Blind: " + getSmallBlind(players).getName());
            System.out.println("Big Blind: " + getBigBlind(players).getName());
        }

        public int printSmallBlind(List<Player> players) {
            System.out.println("----------------------------------");
            System.out.print("The Small Blind places its bet: ");
    
            Player small_blind_player = this.getSmallBlind(players);
            int small_blind = small_blind_player.dealSmallBlind();
            System.out.println();
            System.out.println(small_blind_player.getName() + " deals " + small_blind);
            return small_blind;
        }

        public int printSmallBlindCompletion(Player player) {
            System.out.println("----------------------------------");
            System.out.print("The Small Blind completes its bet: ");
            int complete_small_blind = player.dealSmallBlind();
            System.out.println();
            System.out.println(player.getName() + " deals " + complete_small_blind);
            return complete_small_blind;
        }

        public int printBigBlind(List<Player> players) {
            System.out.println("----------------------------------");
            System.out.print("The Big Blind places its bet: ");
    
            Player big_blind_player = this.getBigBlind(players);
            int big_blind = big_blind_player.dealBigBlind();
            System.out.println();
            System.out.println(big_blind_player.getName() + " deals " + big_blind);
            return big_blind;
        }

        public List<Player> getRemainingPlayers(List<Player> players) {
            List<Player> remainingPlayers = new ArrayList<>();
            
            Player smallBlindPlayer = getSmallBlind(players);
            Player bigBlindPlayer = getBigBlind(players);
            
            for (Player player : players) {
                if (player != smallBlindPlayer && player != bigBlindPlayer) {
                    remainingPlayers.add(player);
                }
            }    
            return remainingPlayers;
        }

        public void fold(List<Player> players, Player player) {
            players.remove(player);
            System.out.println("----------------------------------");
            System.out.print(player.getName() + " has decided to fold");
            System.out.print(player.getName() + " has been removed from this round");
        }

        public int raise(Player player, int amount) {
            if (amount <= player.getChips()) {
                player.removeChips(amount);
                return amount;
            } else {
                System.out.println(player.getName() + " doesn't have enough chips to raise.");
                return 0;
            }
        }

        public int call(Player player, int amount) {
            if (amount <= player.getChips()) {
                player.removeChips(amount);
                System.out.println("----------------------------------");
                System.out.println(player.getName() + " decided to 'Call' ");
                System.out.println(player.getName() + " deals " + amount);
                return amount;
            } else {
                System.out.println(player.getName() + " doesn't have enough chips to call.");
                return 0;
            }
        }

        public void menu() {
            System.out.println("----------------------------------");
            System.out.println("It is your turn to place the bet. ");
            System.out.println("1- Call (follows the bet amount on the table)");
            System.out.println("2- Raise (increases the bet amount)");
            System.out.println("3- Fold (quit the round)");
        }
    }
}
