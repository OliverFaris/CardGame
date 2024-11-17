import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Player p2;

    public Game() {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter player 1 name: ");
        String name1 = input.nextLine();

        System.out.println("Enter player 1 name: ");
        String name2 = input.nextLine();

        this.p1 = new Player(name1);
        this.p2 = new Player(name2);
        String[] ranks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String[] suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};

        this.deck = new Deck(ranks, suits, values);
    }

    public static void printInstructions() {
        // Prints instructions
    }

    public void playGame() {
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (i % 13.0 == 0) {
                System.out.println("\n");
            }
            System.out.print(deck.getCards().get(i) + ",   ");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
