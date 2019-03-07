import java.util.ArrayList;

public class Game {

    enum games {
        BLACKJACK,
        POKER,
        TEXAS
    }

    private int balance;
    private Deck deck;
    private ArrayList<Card> hand;

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
        
    }

    private void poker() {
        System.out.println("Not yet implemented");
    }

    private void texas() {
        System.out.println("Not yet implemented");
    }
}
