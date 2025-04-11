package auction.impl;

import auction.api.BidderWithDebugInfo;
import auction.util.BidderUtils;

/**
 * The BidderBot adapts his bids to an average opponent's bid.
 * It bids just a bit higher, than opponent's average to win QUs without spending too many MUs.
 */
public class BidderBot implements BidderWithDebugInfo {
    private int initialCash;
    private int remainingQuantity;
    private int remainingCash;

    private int maxRounds;
    private int[] opponentBids;
    private int[] ownBids;

    private int currentRound;

    @Override
    public void init(int quantity, int cash) {
        this.initialCash = cash;
        this.remainingQuantity = quantity;
        this.remainingCash = cash;

        this.maxRounds = (quantity + 1) / 2;
        this.opponentBids = new int[maxRounds];
        this.ownBids = new int[maxRounds];
        this.currentRound = 0;
    }

    @Override
    public int placeBid() {
        return BidderUtils.calculateBidByGamePhase(
                remainingQuantity,
                remainingCash,
                initialCash,
                currentRound,
                ownBids,
                opponentBids
        );
    }

    @Override
    public void bids(int own, int other) {
        if (currentRound < maxRounds) {
            ownBids[currentRound] = own;
            opponentBids[currentRound] = other;
            currentRound++;
        }

        remainingCash -= own;

        // subtraction of 1 QU from remaining quantity just in case the number of QUs is not even
        remainingQuantity -= Math.min(2, remainingQuantity);
    }

    @Override
    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    @Override
    public int getRemainingCash() {
        return remainingCash;
    }
}
