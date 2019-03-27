import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    enum games {
        BLACKJACK,
        POKER,
        TEXAS
    }

    private int balance;
    private Deck deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> dealer;
    private static Scanner in;

    Game(int balance, games game) {
        in = new Scanner(System.in);
        deck = new Deck();
        this.balance = balance;
        switch (game) {
            case BLACKJACK:
                blackJack();
                break;
            case POKER:
                poker();
                break;
            case TEXAS:
                texas();
                break;
        }
    }

    private void blackJack() {
        deck = new Deck();
        loop: while (balance > 0) {
            System.out.println("Current Balance: $" + this.balance);
            System.out.println("Would you like to play a hand? It will cost $2");
            if (balance < 2) {
                System.out.println("You cannot afford it!");
                break loop;
            }
            else {
                String temp = "";
                while (true) {
                    temp = (String) getInput(String.class);
                    if (temp.equals("yes"))
                        break;
                    else if (temp.equals("no"))
                        break loop;
                }
                startBlackJack();
            }
        }
        if (balance <= 0)
            System.out.println("You have no money");
        else
            System.out.println("Final balance: $" + balance);
    }

    private void startBlackJack() {
        balance -= 2;
        int bet;

        do {
            System.out.println("Starting bet is $2, how much more would you like to add?");
        } while ((bet = (int) getInput(Integer.class) + 2) < 2);

        boolean cont = true;
        while (cont) {
            System.out.print("Current hand: ");
            hand = new ArrayList<>();
            hit(this.hand, 2);
            printHand(this.hand, false);
            System.out.println(" = " + sum(this.hand, false));

            System.out.print("Dealer's hand: ");
            dealer = new ArrayList<>();
            hit(this.dealer, 2);
            printHand(this.dealer, true);
            System.out.println(" = " + sum(this.dealer, true));

        }
    }

    private void poker() { // TODO
        System.out.println("Not yet implemented");
    }

    private void texas() { // TODO
        System.out.println("Not yet implemented");
    }

    private void hit(ArrayList<Card> whichHand, int i) {
        Card temp;
        while (i-- > 0) {
            temp = deck.deal();
            if (temp == null) {
                deck.shuffle();
                temp = deck.deal();
            }
            whichHand.add(temp);
        }
    }

    private int sum(ArrayList<Card> whichHand, boolean dealer) {
        int sum = 0;
        int aces = 0;
        for (Card card : whichHand) {
            if (card.getCardRank() == Card.ranks.ACE)
                aces++;
            else
                sum += card.getPointValue();
        }
        if (dealer) {
            sum -= whichHand.get(0).getPointValue();
            if (whichHand.get(0).getCardRank() == Card.ranks.ACE)
                sum -= 10;
        }
        while (aces-- > 0)
            if (sum <= 10 && sum + aces + 10 < 21)
                sum += 11;
            else
                sum++;
        return sum;
    }

    private void printHand(ArrayList<Card> whichHand, boolean dealer) {
        if (dealer) {
            System.out.print("HIDDEN, ");
        }
        for (int i = (dealer) ? 1 : 0; i < whichHand.size(); i++) {
            System.out.print(whichHand.get(i).getCardRank() + ((i != whichHand.size() - 1) ? ", " : ""));
        }
    }

    private Object getInput(Class<?> type) {
        String temp;
        while (true) {
            try {
                temp = in.nextLine();
                if (type.isAssignableFrom(String.class)) {
                    return type.cast(temp);
                }
                else if (type.isAssignableFrom(Integer.class)) {
                    return Integer.parseInt(temp);
                }
                else if (type.isAssignableFrom(Boolean.class)) {
                    return Boolean.parseBoolean(temp);
                }
                else if (type.isAssignableFrom(Double.class)) {
                    return Double.parseDouble(temp);
                }
                else {
                    throw new IllegalArgumentException("Bad type.");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }
}
