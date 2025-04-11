package auction.util;

/**
 * Represents bidder utils.
 */
public class BidderUtils {
    /**
     * Calculates sum of bids to defined limit (current round)
     *
     * @param bids  an array of bids
     * @param until the limit for bids sum
     *
     * @return sum of first bids until current round
     */
    public static int sum(int[] bids, int until) {
        int sum = 0;

        for (int i = 0; i < until && i < bids.length; i++) {
            sum += bids[i];
        }

        return sum;
    }

    /**
     * Calculates an average budget per round (per 2 QU)
     *
     * @param remainingCash     bot's MU left
     * @param remainingQuantity bot's QU left
     *
     * @return an average budget
     */
    public static int averageBudgetPerRound(int remainingCash, int remainingQuantity) {
        // usage of the Math.max is a protection for dividing by 0
        return remainingCash / Math.max(1, (remainingQuantity / 2));
    }

    /**
     * Calculates bot's current bid depending on average budget and opponent's bids
     *
     * @param remainingCash bot's MU left
     * @param currentRound  the round
     * @param opponentBids  array of bids by opponent
     * @param avgBudget     bot's average budget per round
     *
     * @return bot's current bid
     */
    public static int calculateBid(int remainingCash, int currentRound, int[] opponentBids, int avgBudget) {
        int opponentAvg = (currentRound > 0)
                ? sum(opponentBids, currentRound) / currentRound
                : avgBudget;

        int higherThanOpponentBid = opponentAvg + 1;

        int safeBid = Math.max(avgBudget, higherThanOpponentBid);

        return Math.min(remainingCash, safeBid);
    }
}
