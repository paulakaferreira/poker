package poker;

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
    }
}
