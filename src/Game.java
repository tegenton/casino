import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    enum games {
        BLACKJACK,
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

                hand = new ArrayList<>();
                hit(this.hand, 2);
                dealer = new ArrayList<>();
                hit(this.dealer, 2);
                startBlackJack();
            }
        }
        if (balance <= 0)
            System.out.println("You have no money");
        else
            System.out.println("Final balance: $" + balance);
    }

    private void startBlackJack() {
        int bet;
        do {
            bet = 2;
            System.out.println("Starting bet is $2, how much more would you like to add?");
            bet += (int) getInput(Integer.class);
        } while ((bet < 2) || bet > balance);
        balance -= bet;

        boolean cont = true;
        while (cont) {
            System.out.println("You have bet $" + bet);
            System.out.print("Current hand: ");
            printHand(this.hand, false);
            System.out.println(" = " + sum(this.hand, false));

            System.out.print("Dealer's hand: ");
            printHand(this.dealer, true);
            System.out.println(" = " + sum(this.dealer, true));

            int input;
            do {
                System.out.println("What would you like to do?\n 1. Hit\n 2. Stand\n 3. Double Down");
                input = (int) getInput(Integer.class);
            } while (input < 1 || input > 3);

            switch (input) {
                case 1:
                    hit(this.hand, 1);
                    break;
                case 2:
                    cont = false;
                    break;
                case 3:
                    do {
                        System.out.println("How much more would you like to bet? You can add up to $" + bet);
                        input = (int) getInput(Integer.class);
                    } while (input > bet);
                    this.balance -= input;
                    hit(this.hand, 1);
                    cont = false;
                    break;
                default:
                    break;
            }
            if (sum(this.hand, false) > 21)
                cont = false;
        }

        int i = 0;
        while (sum(this.dealer, false) < 17) {
            hit(this.dealer, 1);
            i++;
        }
        System.out.println("Dealer takes " + i + " cards.");

        System.out.print("Current hand: ");
        printHand(this.hand, false);
        System.out.println(" = " + sum(this.hand, false));

        System.out.print("Dealer's hand: ");
        printHand(this.dealer, false);
        System.out.println(" = " + sum(this.dealer, false));

        if (sum(this.hand, false) > 21) {
            System.out.println("Dealer wins!");
        }
        else if (sum(this.dealer, false) > 21) {
            System.out.println("You won!");
            this.balance += 2 * bet;
        }
        else if (sum(this.hand, false) > sum(this.dealer, false)) {
            System.out.println("You won!");
            this.balance += 2 * bet;
        }
        else {
            System.out.println("Dealer won!");
        }
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
                temp = in.nextLine().toLowerCase();
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
