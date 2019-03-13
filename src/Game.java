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

    Game(int balance, games game) {
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
        while (balance > 0) {
            System.out.println("Current Balance: $" + this.balance);
            System.out.println("Would you like to play a hand? It will cost $2");
            if (balance < 2) {
                System.out.println("You cannot afford it!");
                break;
            }
            else {
                // TODO: user input

            }
            System.out.print("Current Hand: ");
            hand = new ArrayList<>();
            hit(2);
            for (Card card : this.hand) {
                System.out.print(card.getCardRank() + " ");
            }
            System.out.println(" = " + sum());
            while (sum() <= 21) {

            }
            if (sum() > 21) {
                System.out.println("You busted");
            }
        }
        if (balance <= 0)
            System.out.println("You have no money");
        else
            System.out.println("Final balance: $" + balance);
    }

    private void poker() { // TODO
        System.out.println("Not yet implemented");
    }

    private void texas() { // TODO
        System.out.println("Not yet implemented");
    }

    private void hit(int i) {
        Card temp;
        while (i-- > 0) {
            temp = deck.deal();
            if (temp == null) {
                deck.shuffle();
                temp = deck.deal();
            }
            hand.add(temp);
        }
    }

    private int sum() {
        int sum = 0;
        int aces = 0;
        for (Card card : this.hand) {
            if (card.getCardRank() == Card.ranks.ACE)
                aces++;
            else
                sum += card.getPointValue();
        }
        while (aces-- > 0)
            if (sum <= 10 && sum + aces + 10 < 21)
                sum += 11;
            else
                sum++;
        return sum;
    }
}
