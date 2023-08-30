package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEval {

    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE,
        FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    public static HandRank evaluateHand(List<CardGame.Card> hand) {
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

    private static boolean isRoyalFlush(List<CardGame.Card> hand) {
        return isStraightFlush(hand) && getHighCardRank(hand) == CardGame.Rank.ACE;
    }

    private static boolean isStraightFlush(List<CardGame.Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }

    private static boolean isFourOfAKind(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 4);
    }

    private static boolean isFullHouse(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return ranks.stream().distinct().count() == 2 &&
               (Collections.frequency(ranks, ranks.get(0)) == 2 ||
                Collections.frequency(ranks, ranks.get(0)) == 3);
    }

    private static boolean isFlush(List<CardGame.Card> hand) {
        return hand.stream().map(CardGame.Card::getSuit).distinct().count() == 1;
    }

    private static boolean isStraight(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        int distinctRanks = (int) ranks.stream().distinct().count();
        return distinctRanks == 5 && (ranks.get(0).ordinal() - ranks.get(4).ordinal() == 4 ||
                                      ranks.get(0) == CardGame.Rank.ACE && ranks.get(1) == CardGame.Rank.FIVE);
    }

    private static boolean isThreeOfAKind(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 3);
    }

    private static boolean isTwoPair(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return ranks.stream().filter(rank -> Collections.frequency(ranks, rank) == 2).distinct().count() == 2;
    }

    private static boolean isOnePair(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return ranks.stream().anyMatch(rank -> Collections.frequency(ranks, rank) == 2);
    }

    private static CardGame.Rank getHighCardRank(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = getCardRanks(hand);
        return Collections.max(ranks);
    }

    private static List<CardGame.Rank> getCardRanks(List<CardGame.Card> hand) {
        List<CardGame.Rank> ranks = new ArrayList<>();
        for (CardGame.Card card : hand) {
            ranks.add(card.getRank());
        }
        return ranks;
    }
}
