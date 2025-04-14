
# TradingBot

A Java implementation of an auction bot that simulates bidding with limited monetary units (MU) and quantity units (QU). Designed to become slightly aggressive when looses and to deal with odd QUs.

## ⚙️ Bot Strategy

The bot uses a **multi-phase adaptive strategy**, adjusting its bids depending on the stage of the game and its performance relative to the opponent.

### Game Phases

- **Early Game (MU > 66% of starting cash):**
  - Conservative bidding to preserve MU and observe opponent behavior.
- **Mid Game (MU between 33%–66%):**
  - Slightly aggressive bidding, adapting based on the opponent’s last bid.
- **Late Game (MU < 33%):**
  - Aggressive bidding using most or all remaining MU to secure remaining QU.

### Adaptive Behavior

- If the bot is **losing** (fewer wins than the opponent), it increases its bid to try and catch up.
- In **very short games** (e.g., when only 2 QU are available), the bot goes all-in on the first round.

## 🚀 How to Run

### Requirements

- Java 21 or higher
- Maven 3.6 or higher

### Clone & Run

```bash
git clone https://github.com/MaxTeenspirit/tradingBot.git
cd tradingBot
mvn clean compile exec:java -Dexec.mainClass="auction.Main"
```

### Run tests

```bash
mvn test
```

## 🧪 Testing

The project includes a simulation engine to test the **BidderBot** against a dummy **RandomBidderTestBot**. Example:

```java
Bidder bidder1 = new BidderBot();
Bidder bidder2 = new RandomBidderTestBot();

MatchResult result = AuctionSimulator.runMatch(bidder1, bidder2, 100, 1000);

System.out.println(result);
```


## 📁 Project Structure

- `src/main/java/auction/`
  - `api/` – Interfaces:
    - `Bidder`
    - `BidderWithDebugInfo`
  - `impl/` – Bot implementations:
    - `BidderBot`
    - `RandomBidderTestBot`
  - `simulator/` – Auction engine and result:
    - `AuctionSimulator`
    - `MatchResult`
  - `util/` – Strategy utilities:
    - `BidderUtils`
  - `Main.java` – Entry point for running the simulation

- `src/test/java/auction/` – Unit tests:
  - `BidderUtilsTest`
  - `BidderBotTest`
  - `AuctionSimulatorTest`
