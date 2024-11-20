public class Card {
    private char rank;
    private char suit;
    private int value;
    private String design;

    public Card(char rank, char suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.design = "|" + rank + "-" + suit + "|";
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

    public String getDesign() {
        return design;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}
