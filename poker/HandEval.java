package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import poker.CardGame.*;

public class HandEval {

    // Enumeration for different hand ranks
    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE,
        FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    // Evaluate the rank of a player's hand
    public static HandRank evaluateHand(List<Card> hand) {
        if (isRoyalFlush(hand)) return HandRank.ROYAL_FLUSH;
        if (isStraightFlush(hand)) return HandRank.STRAIGHT_FLUSH;
        if (isFourOfAKind(hand)) return HandRank.FOUR_OF_A_KIND;
        if (isFullHouse(hand)) return HandRank.FULL_HOUSE;
        if (isFlush(hand)) return HandRank.FLUSH;
        if (isStraight(hand)) return HandRank.STRAIGHT;
        if (isThreeOfAKind(hand)) return HandRank.THREE_OF_A_KIND;
        if (isTwoPair(hand)) return HandRank.TWO_PAIR;
        if (isOnePair(hand)) return HandRank.ONE_PAIR;
        return HandRank.HIGH_CARD;
    }

    // Check if the hand is a Royal Flush
    private static boolean isRoyalFlush(List<Card> hand) {
        return isStraightFlush(hand) && getHighCardRank(hand) == Card.Rank.ACE;
    }

    // Check if the hand is a Straight Flush
    private static boolean isStraightFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }

    // Check if the hand is Four of a Kind
    private static boolean isFourOfAKind(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 4);
    }

    // Check if the hand is a Full House
    private static boolean isFullHouse(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return ranks.stream().distinct().count() == 2 &&
               (Collections.frequency(ranks, ranks.get(0)) == 2 ||
                Collections.frequency(ranks, ranks.get(0)) == 3);
    }

    // Check if the hand is a Flush
    private static boolean isFlush(List<Card> hand) {
        return hand.stream().map(Card::getSuit).distinct().count() == 1;
    }

    // Check if the hand is a Straight
    private static boolean isStraight(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        int distinctRanks = (int) ranks.stream().distinct().count();
        return distinctRanks == 5 && (ranks.get(0).ordinal() - ranks.get(4).ordinal() == 4 ||
                                      ranks.get(0) == Card.Rank.ACE && ranks.get(1) == Card.Rank.FIVE);
    }

    // Check if the hand is Three of a Kind
    private static boolean isThreeOfAKind(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 3);
    }

    // Check if the hand is Two Pair
    private static boolean isTwoPair(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return ranks.stream().filter(rank -> Collections.frequency(ranks, rank) == 2).distinct().count() == 2;
    }

    // Check if the hand is One Pair
    private static boolean isOnePair(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 2);
    }

    // Get the rank of the high card in the hand
    private static Card.Rank getHighCardRank(List<Card> hand) {
        List<Card.Rank> ranks = getCardRanks(hand);
        return Collections.max(ranks);
    }

    // Get a list of card ranks in the hand
    private static List<Card.Rank> getCardRanks(List<Card> hand) {
        List<Card.Rank> ranks = new ArrayList<>();
        for (Card card : hand) {
            ranks.add(card.getRank());
        }
        return ranks;
    }
}
