package auction.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomBidderTestBotTest {

    private RandomBidderTestBot bot;

    @BeforeEach
    public void setUp() {
        bot = new RandomBidderTestBot();
        bot.init(100, 50);
    }

    @Test
    public void testBidIsWithinRemainingCash() {
        for (int i = 0; i < 20; i++) {
            int bid = bot.placeBid();

            assertTrue(bid >= 0 && bid <= 50, "Bid should be between 0 and 50");
        }
    }

    @Test
    public void testCashDecreasesAfterBid() {
        int previousCash = 50;

        for (int i = 0; i < 5; i++) {
            int bid = bot.placeBid();

            bot.bids(bid, 0); // other is ignored anyway

            previousCash -= bid;

            int nextBid = bot.placeBid();

            assertTrue(nextBid <= previousCash, "Bot should not bid more than remaining cash");
        }
    }

    @Test
    public void testZeroBidWhenCashGone() {
        bot.bids(50, 0); // all cash gone

        int bid = bot.placeBid();

        assertEquals(0, bid, "Bot should bid 0 when no cash is left");
    }

    @Test
    public void testMultipleRounds() {
        for (int i = 0; i < 10; i++) {
            int bid = bot.placeBid();

            assertTrue(bid >= 0, "Bid should be non-negative");

            bot.bids(bid, 0);
        }
    }
}
