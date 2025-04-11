package auction.simulator;

/**
 * Represents the outcome of a match between two bidders.
 * Contains the amount of QUs won by each bidder,
 * the amount of MUs left at the end,
 * and the declared winner.
 */
public class MatchResult {
    public final int bidder1Units;
    public final int bidder2Units;

    public final int bidder1CashLeft;
    public final int bidder2CashLeft;

    public final String winner;

    /**
     * Constructs a result object for a completed match.
     *
     * @param bidder1Units      quantity units won by Bidder 1
     * @param bidder2Units      quantity units won by Bidder 2
     * @param bidder1CashLeft   remaining MU for Bidder 1
     * @param bidder2CashLeft   remaining MU for Bidder 2
     * @param winner            string describing the winner
     */
    public MatchResult(int bidder1Units, int bidder2Units, int bidder1CashLeft, int bidder2CashLeft, String winner) {
        this.bidder1Units = bidder1Units;
        this.bidder2Units = bidder2Units;

        this.bidder1CashLeft = bidder1CashLeft;
        this.bidder2CashLeft = bidder2CashLeft;

        this.winner = winner;
    }

    @Override
    public String toString() {
        return String.format("""
                Bidder 1: %d QU, %d MU left
                Bidder 2: %d QU, %d MU left
                Winner: %s
                """, bidder1Units, bidder1CashLeft, bidder2Units, bidder2CashLeft, winner);
    }
}
