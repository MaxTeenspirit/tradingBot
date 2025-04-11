package auction.simulator;

import auction.api.Bidder;
import auction.api.BidderWithDebugInfo;

/**
 * Utility class to simulate a full auction match between two bidders.
 * Provides a static method to run a simulation and generate a {@link MatchResult}.
 */
public class AuctionSimulator {
    /**
     * Runs a full auction between two bidders with the initial QUs and MUs.
     * <p>
     * Each round both bidders submit their bids simultaneously. The higher bidder gets 2 QUs.
     * In case of equal bids both get 1 QU.
     * The auction continues until all QU are distributed or both bidders run out of money.
     * </p>
     *
     * @param bidder1  The first bidder
     * @param bidder2  The second bidder
     * @param quantity Total quantity units (QU) to be auctioned
     * @param cash     Initial monetary units (MU) for each bidder
     *
     * @return MatchResult containing the final outcome
     */
    public static MatchResult runMatch(Bidder bidder1, Bidder bidder2, int quantity, int cash) {
        bidder1.init(quantity, cash);
        bidder2.init(quantity, cash);

        int remaining1 = cash;
        int remaining2 = cash;
        int units1 = 0;
        int units2 = 0;

        int rounds = quantity / 2;

        for (int i = 0; i < rounds; i++) {
            int bid1 = Math.min(remaining1, bidder1.placeBid());
            int bid2 = Math.min(remaining2, bidder2.placeBid());

            bidder1.bids(bid1, bid2);
            bidder2.bids(bid2, bid1);

            if (bid1 > bid2) units1 += 2;
            else if (bid2 > bid1) units2 += 2;
            else {
                units1 += 1;
                units2 += 1;
            }

            remaining1 -= bid1;
            remaining2 -= bid2;
        }

        // case if 1 QU left
        if (quantity % 2 != 0) {
            int bid1 = Math.min(remaining1, bidder1.placeBid());
            int bid2 = Math.min(remaining2, bidder2.placeBid());

            bidder1.bids(bid1, bid2);
            bidder2.bids(bid2, bid1);

            if (bid1 > bid2) units1 += 1;
            else if (bid2 > bid1) units2 += 1;

            remaining1 -= bid1;
            remaining2 -= bid2;
        }

        String winner;

        if (units1 > units2) winner = "Bidder 1";
        else if (units2 > units1) winner = "Bidder 2";
        else {
            int cash1 = getRemainingCash(bidder1, remaining1);
            int cash2 = getRemainingCash(bidder2, remaining2);

            if (cash1 > cash2) winner = "Bidder 1 (by MU)";
            else if (cash2 > cash1) winner = "Bidder 2 (by MU)";
            else winner = "Tie";
        }

        return new MatchResult(units1, units2, remaining1, remaining2, winner);
    }

    /**
     * Gets the remaining MUs from a bidder using {@link BidderWithDebugInfo}.
     * If not available, returns the fallback value provided by the simulator.
     *
     * @param bidder        the bidder instance
     * @param fallbackCash  MUs tracked internally by the simulator
     *
     * @return remaining cash
     */
    private static int getRemainingCash(Bidder bidder, int fallbackCash) {
        if (bidder instanceof BidderWithDebugInfo debug) {
            return debug.getRemainingCash();
        }
        return fallbackCash;
    }
}
