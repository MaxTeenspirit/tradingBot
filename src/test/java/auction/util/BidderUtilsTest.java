package auction.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BidderUtilsTest {

    @Test
    public void testSumCorrectlyAddsUp() {
        int[] arr = {3, 5, 7, 10};
        assertEquals(8, BidderUtils.sum(arr, 2));

        assertEquals(25, BidderUtils.sum(arr, 4));

        assertEquals(0, BidderUtils.sum(arr, 0));
    }

    @Test
    public void testAverageBudgetHandlesDivision() {
        assertEquals(10, BidderUtils.averageBudgetPerRound(100, 20)); // 100 / (20 / 2) = 10

        assertEquals(100, BidderUtils.averageBudgetPerRound(100, 1)); // 100 / 1 = 100

        assertEquals(100, BidderUtils.averageBudgetPerRound(100, 0)); // to check protection of dividing by 0
    }

    @Test
    public void testCalculateBidDoesNotExceedCash() {
        int[] opponent = {5, 6, 7};

        int bid = BidderUtils.calculateBid(8, 3, opponent, 5);

        assertTrue(bid <= 8, "Bid should not exceed available cash");
    }

    @Test
    public void testCalculateBidReactsToOpponentAverage() {
        int[] opponent = {4, 6, 8};

        int bid = BidderUtils.calculateBid(100, 3, opponent, 5);

        assertTrue(bid >= 7, "Bid should be at least opponent avg + 1");
    }
}
