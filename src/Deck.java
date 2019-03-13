import java.util.ArrayList;
import java.util.Random;

class Deck extends ArrayList<Card> {

    private int unShuffled;

    Deck() {
        super(52);
        fillDeck();
        shuffle();
    }

    private void fillDeck() {
        for (Card.ranks rank : Card.ranks.values()) {
            for (Card.suits suit : Card.suits.values()) {
                this.add(new Card(rank, suit));
            }
        }
    }

    public Card deal() {
        if (isEmpty()) {
            return null;
        }
        return this.get(--unShuffled);
    }

    public boolean isEmpty() {
        return this.unShuffled <= 0;
    }

    public void shuffle() {
        Random rand = new Random();
        int r;
        for (int k = size() - 1; k > 0; k--) {
            r = rand.nextInt(k + 1);
            this.set(k, this.set(r, this.get(k)));
        }
        unShuffled = size();
    }
}
