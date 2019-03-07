public class Card implements Comparable<Card> {

    enum ranks {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private final int value;

        ranks(final int value) {
            this.value = value;
        }

        public int getValue() { return value; }
    }
    enum suits {
        HEARTS,
        DIAMONDS,
        SPADES,
        CLUBS
    }

    private ranks cardRank;
    private suits cardSuit;
    private int pointValue;

    Card(ranks rank, suits suit) {
        this.cardRank = rank;
        this.cardSuit = suit;
        this.pointValue = rank.getValue();
    }

    Card(ranks rank, suits suit, int pointValue) {
        this(rank, suit);
        this.pointValue = pointValue;
    }


    @Override
    public int compareTo(Card card) {
        return this.pointValue - card.getPointValue();
    }

    public suits getCardSuit() {
        return cardSuit;
    }

    public ranks getCardRank() {
        return cardRank;
    }

    public int getPointValue() {
        return pointValue;
    }
}
