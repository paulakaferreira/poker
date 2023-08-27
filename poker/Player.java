package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<CardGame.Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("'s Hand:\n");
        for (CardGame.Card card : hand) {
            sb.append(card).append("\n");
        }
        return sb.toString();
    }
}
