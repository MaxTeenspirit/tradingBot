package auction.simulator;

public class MatchResult {
    public final int bidder1Units;
    public final int bidder2Units;

    public final int bidder1CashLeft;
    public final int bidder2CashLeft;

    public final String winner;

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
