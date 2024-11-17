import java.util.ArrayList;

public class Player {
    private int points;
    private String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.points = 0;
        this.name = name;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.points = 0;
        this.name = name;
        this.hand = hand;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public String toString() {
        return name + " has " + points + "\n" + name + "'s cards: " + hand;
     }
}
