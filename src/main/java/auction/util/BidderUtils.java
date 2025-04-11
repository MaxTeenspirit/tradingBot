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
     * Calculates a bid depending on the MU-based game phase:
     * Early → cautious,
     * Mid → adaptive,
     * Late → aggressive.
     * Also adjusts based on whether the bot is currently losing.
     *
     * @param remainingQuantity current QU left
     * @param remainingCash     bot's MU left
     * @param initialCash       bot's initial MU
     * @param currentRound      current round number
     * @param ownBids           own bid history
     * @param opponentBids      opponent's bid history
     * @return bid amount
     */
    public static int calculateBidByGamePhase(
            int remainingQuantity,
            int remainingCash,
            int initialCash,
            int currentRound,
            int[] ownBids,
            int[] opponentBids
    ) {
        if (remainingCash <= 0 || remainingQuantity <= 0) return 0;

        // if auction is short we play all-in
        if (remainingQuantity <= 3 && currentRound == 0) {
            return remainingCash;
        }

        int averageBudget = averageBudgetPerRound(remainingCash, remainingQuantity);
        int lastOpponentBid = (currentRound > 0) ? opponentBids[currentRound - 1] : 1;

        double muRatio = (double) remainingCash / Math.max(1, initialCash);

        // check if bot is loosing
        int myWins = 0;
        int theirWins = 0;

        for (int i = 0; i < currentRound; i++) {
            if (ownBids[i] > opponentBids[i]) {
                myWins++;
            } else if (ownBids[i] < opponentBids[i]) {
                theirWins++;
            } else {
                myWins++;
                theirWins++;
            }
        }

        boolean isLosing = theirWins > myWins;

        if (muRatio > 0.66) {
            // early game
            return Math.min(remainingCash, Math.max(1, lastOpponentBid));
        } else if (muRatio > 0.33) {
            // mid-game
            return Math.min(remainingCash, lastOpponentBid + (isLosing ? 3 : 1));
        } else {
            // late game
            return Math.min(remainingCash, averageBudget * (isLosing ? 3 : 2));
        }
    }

}
