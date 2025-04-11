package auction.impl;

import auction.api.Bidder;

import java.util.Random;

/**
 * The RandomBidderTestBot is a dummy bot to test the BidderBot.
 * It can bid randomly from 0 to all available MUs.
 */
public class RandomBidderTestBot implements Bidder {
    private final Random random = new Random();

    private int remainingCash;

    @Override
    public void init(int quantity, int cash) {
        this.remainingCash = cash;
        // quantity is intentionally ignored
    }

    @Override
    public int placeBid() {
        if (remainingCash <= 0) return 0;
        return random.nextInt(remainingCash + 1);
    }

    @Override
    public void bids(int own, int other) {
        remainingCash -= own;
        // other is intentionally ignored
    }
}
