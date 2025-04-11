package auction.simulator;

import auction.api.Bidder;
import auction.api.BidderWithDebugInfo;

public class AuctionSimulator {
    public static MatchResult runMatch(Bidder bidder1, Bidder bidder2, int quantity, int cash) {
        bidder1.init(quantity, cash);
        bidder2.init(quantity, cash);

        int remaining1 = cash;
        int remaining2 = cash;
        int units1 = 0;
        int units2 = 0;

        int rounds = quantity / 2;

        for (int i = 0; i < rounds; i++) {
            if(getRemainingCash(bidder1, remaining1) == 0 && getRemainingCash(bidder2, remaining2) == 0) break;

            int bid1 = bidder1.placeBid();
            int bid2 = bidder2.placeBid();

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

        // case when 1 QU left
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

    private static int getRemainingCash(Bidder bidder, int fallbackCash) {
        if (bidder instanceof BidderWithDebugInfo debug) {
            return debug.getRemainingCash();
        }
        return fallbackCash;
    }
}
