package auction;

import auction.api.Bidder;
import auction.impl.BidderBot;
import auction.impl.RandomBidderTestBot;
import auction.simulator.AuctionSimulator;
import auction.simulator.MatchResult;

/**
 * Entry point to test the BidderBot against a dummy RandomBidderTestBot.
 */
public class Main {
    public static void main(String[] args) {
        // feel free to change these values below: quantity and cash
        int quantity = 100;
        int cash = 1000;

        Bidder bot = new BidderBot();
        Bidder random = new RandomBidderTestBot();

        MatchResult result = AuctionSimulator.runMatch(bot, random, quantity, cash);

        System.out.println(">>>>>>> BidderBot vs RandomBidderTestBot <<<<<<<");
        System.out.println("QU = " + quantity + ", MU = " + cash);
        System.out.println(result);
    }
}