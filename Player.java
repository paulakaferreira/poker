package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<CardGame.Card> hand;
    private int chips = 1500;
    private boolean isBigBlind;
    private boolean isSmallBlind;
    private boolean isDealer;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        isBigBlind = false;
        isSmallBlind = false;
        isDealer = false;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public int removeChips(int amount){
        return this.chips -= amount;
    }

    public void addToHand(CardGame.Card card) {
        hand.add(card);
    }

    public List<CardGame.Card> getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    }

    public boolean isBigBlind() {
        return isBigBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        isBigBlind = bigBlind;
    }

    public int dealBigBlind() {
        if (this.chips >= 10) {
            this.chips -= 10;
            return 10;
        } else {
            // Handle case when player doesn't have enough chips for big blind
            System.out.println(name + " doesn't have enough chips for the big blind.");
            return 0;
        }
    }

    public boolean isSmallBlind() {
        return isSmallBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        isSmallBlind = smallBlind;
    }

    public int dealSmallBlind() {
        if (this.chips >= 5) {
            this.chips -= 5;
            setSmallBlind(true);
            return 5;
        } else {
            // Handle case when player doesn't have enough chips for small blind
            System.out.println(name + " doesn't have enough chips for the small blind.");
            return 0;
        }
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("'s Hand:\n");
        for (CardGame.Card card : hand) {
            sb.append(card).append("\n");
        }
        sb.append("\n");
        sb.append("Total of chips: ").append(chips);
        return sb.toString();
    }
}
