import java.awt.*;

public class Card {
    private char rank;
    private char suit;
    private int value;
    private String design;
    private int index;
    private boolean isHidden;

    private Image cardImage;
    private int x;
    private int y;
    private boolean isDrawn;
    private SolitaireViewer screen;

    public Card(char rank, char suit, int value, Image cardImage, SolitaireViewer screen) {
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
        this.isHidden = false;

        this.x = 0;
        this.y = 0;
        this.cardImage = cardImage;
        this.isDrawn = false;
        this.screen = screen;
    }

    public void draw(Graphics g) {
        if(isHidden)
            g.drawImage(screen.getUtilityCards()[0],40+ (x*85), 78 +(y*80), screen);
        else if (isDrawn) {
            g.drawImage(cardImage, 40+ (x*85), 78+(y*80), screen);
        }
    }

    public Image getCardImage() {
        return cardImage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getDrawn() {
        return isDrawn;
    }

    public void setDrawn(boolean drawn) {
        isDrawn = drawn;
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

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String toString() {
        return rank + " of " + suit;
    }

}
