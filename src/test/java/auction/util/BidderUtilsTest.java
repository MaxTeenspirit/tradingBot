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
    public void testBidDoesNotExceedRemainingCash() {
        int remainingQU = 30;
        int remainingMU = 8;
        int initialMU = 100;
        int currentRound = 3;

        int[] own = {4, 5, 6};
        int[] opponent = {5, 6, 7};

        int bid = BidderUtils.calculateBidByGamePhase(
                remainingQU, remainingMU, initialMU, currentRound, own, opponent
        );

        assertTrue(bid <= remainingMU, "Bid should not exceed available cash");
    }

    @Test
    public void testBidAdaptsToOpponentLastBid() {
        int remainingQU = 50;
        int remainingMU = 50;
        int initialMU = 100;
        int currentRound = 3;

        int[] own = {3, 5, 5};
        int[] opponent = {4, 6, 8};

        int bid = BidderUtils.calculateBidByGamePhase(
                remainingQU, remainingMU, initialMU, currentRound, own, opponent
        );

        assertTrue(bid >= 9, "Bid should be at least last opponent bid + 1 in mid game if losing");
    }
}
