package auction.api;

/**
 * Extended interface for testing/debug purposes.
 */
public interface BidderWithDebugInfo extends Bidder {
    /**
     * Gets remaining QUs of the product.
     *
     * @return number of remaining QUs
     */
    int getRemainingQuantity();

    /**
     * Gets remaining MUs of the product.
     *
     * @return number of remaining MUs
     */
    int getRemainingCash();
}