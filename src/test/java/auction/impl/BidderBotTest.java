package auction.impl;

import auction.api.BidderWithDebugInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BidderBotTest {

    private BidderWithDebugInfo bot;

    @BeforeEach
    public void setUp() {
        bot = new BidderBot();
        bot.init(101, 100); // odd QU is used (for even QU there is testHandlesEvenQuantityCorrectly)
    }

    @Test
    public void testInitialBidIsValid() {
        int bid = bot.placeBid();

        assertTrue(bid >= 0 && bid <= 100, "Bid must be between 0 and initial cash");
    }

    @Test
    public void testBotDoesNotOverspend() {
        int remainingCash = 100;

        for (int i = 0; i < 51; i++) {
            int bid = bot.placeBid();

            assertTrue(bid <= remainingCash, "Bot should never bid more than it has");

            bot.bids(bid, 1);

            remainingCash -= bid;
        }
    }

    @Test
    public void testZeroBidWhenNoCashLeft() {
        for (int i = 0; i < 100; i++) {
            int bid = bot.placeBid();

            if (bid == 0) break;

            bot.bids(bid, 0);
        }

        assertEquals(0, bot.placeBid(), "Bid must be zero when cash is gone");
    }

    @Test
    public void testAdaptationToOpponent() {
        bot.bids(5, 10);
        bot.bids(4, 10);

        int nextBid = bot.placeBid();

        assertTrue(nextBid >= 10, "Bot should adapt to higher opponent bids");
    }

    @Test
    public void testHandlesOddQuantityCorrectly() {
        for (int i = 0; i < 50; i++) {
            int bid = bot.placeBid();

            bot.bids(bid, 0);
        }

        // one QU left
        assertEquals(1, bot.getRemainingQuantity(), "Bot should have 1 QU left after 50 rounds");

        // here should be 0 QUs left
        bot.bids(bot.placeBid(), 0);
        assertEquals(0, bot.getRemainingQuantity(), "Bot should finish odd QU correctly");
    }

    @Test
    public void testHandlesEvenQuantityCorrectly() {
        bot = new BidderBot();
        bot.init(100, 100); // reassigning bot with even QUs (100)

        int remainingCash = 100;

        for (int i = 0; i < 50; i++) {
            int bid = bot.placeBid();

            assertTrue(bid <= remainingCash, "Bid must not exceed available cash");

            bot.bids(bid, 1);

            remainingCash -= bid;
        }

        assertEquals(0, bot.getRemainingQuantity(), "All QU should be consumed with even quantity");
    }

    @Test
    public void testRemainingQuantityDecreasesCorrectly() {
        BidderWithDebugInfo bot = new BidderBot();
        bot.init(100, 100);

        int bid = bot.placeBid();

        bot.bids(bid, 1);

        assertEquals(98, bot.getRemainingQuantity());

        bot.bids(bot.placeBid(), 2);
        bot.bids(bot.placeBid(), 2);

        assertEquals(94, bot.getRemainingQuantity());
    }

    @Test
    public void testRemainingCashDecreasesCorrectly() {
        BidderWithDebugInfo bot = new BidderBot();
        bot.init(100, 100);

        bot.bids(10, 5);

        assertEquals(90, bot.getRemainingCash());

        bot.bids(15, 5);

        assertEquals(75, bot.getRemainingCash());
    }

    @Test
    public void testRemainingQuantityHandlesFinalOne() {
        BidderWithDebugInfo bot = new BidderBot();
        bot.init(101, 100); // непарна QU

        for (int i = 0; i < 50; i++) {
            bot.bids(bot.placeBid(), 0);
        }

        assertEquals(1, bot.getRemainingQuantity());

        // 1 remaining QU after 50 rounds
        bot.bids(bot.placeBid(), 0);

        assertEquals(0, bot.getRemainingQuantity());
    }
}
