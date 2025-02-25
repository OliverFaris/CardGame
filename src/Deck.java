import javax.swing.*;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;
    private SolitaireViewer screen;

    public Deck(char[] rank, char[] suit, int[] value, SolitaireViewer screen) {
        cards = new ArrayList<Card>();
        this.screen = screen;
        for (int i = 0; i < suit.length; i++) {
            String cardSuit = "";
            if(i == 0)
                cardSuit = "c";
            else if(i == 1)
                cardSuit = "s";
            else if(i == 2)
                cardSuit = "h";
            else if(i ==3)
                cardSuit = "d";
            for (int j = 0; j < rank.length; j++) {
                cards.add(new Card(rank[j], suit[i], value[j], new ImageIcon("Resources/"+ cardSuit + (j+1) +".png").getImage(), screen));
            }
        }
        cardsLeft = cards.size();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    // Checks if deck is empty
    public boolean isEmpty() {
        if (cardsLeft <= 0)
            return true;
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (isEmpty())
            return null;
        return cards.get(--cardsLeft);
    }

    // Shuffles cards
    public void shuffle() {
        // shuffle
        for (int i = 0; i < cards.size() - 1; i++) {
            int index = (int) (Math.random()*cards.size());
            Card copy = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, copy);
        }

        cardsLeft = cards.size();

    }

    // Deals the card and removes it from the deck
    // Makes it easier to cycle through the deck because it removes cards that shouldn't be in the deck anymore
    public Card dealAndRemove() {
        if (isEmpty())
            return null;
        return cards.remove(--cardsLeft);
    }
}