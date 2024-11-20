public class Card {
    private char rank;
    private char suit;
    private int value;
    private String design;
    private int index;

    public Card(char rank, char suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.design = "|" + rank + "-" + suit + "|";
        if (suit == '♣')
            this.index = 0;
        else if (suit == '♠')
            this.index = 1;
        else if(suit == '♥')
            this.index = 2;
        else
            this.index = 3;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public char getSuit() {
        return suit;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getDesign() {
        return design;
    }

    public String toString() {
        return rank + " of " + suit;
    }

}
