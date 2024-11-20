import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Card[][] board;

    public Game() {
        // Player name input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter player 1 name: ");
        String name1 = input.nextLine();
        this.p1 = new Player(name1);

        // Card Deck
        char[] ranks = {'A','2','3','4','5','6','7','8','9','X','J','Q','K'};
        char[] suits = {'♣', '♠', '♥', '♦'};
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};

        this.deck = new Deck(ranks, suits, values);

        // Board: height, length
        board = new Card[6][7];
    }

    public static void printInstructions() {
        // Prints instructions
    }

    public void playGame() {
        deck.shuffle();
        setUp();
        gameLoop();
    }

    public void setUp() {
        // Prints whole deck
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (i % 13.0 == 0) {
                System.out.println(" ");
            }
            System.out.print(deck.getCards().get(i).getDesign() + ", ");
        }
        System.out.println("\n");

        // Fills board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i < 1)
                    board[i][j] = deck.deal();
                else
                    board[i][j] = null;
            }
        }
    }

    public void display() {
        // Prints board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j%7 == 0)
                    System.out.println(" ");
                if (board[i][j] != null)
                    System.out.print(board[i][j].getDesign() + " ");
                else
                    System.out.print("  -   ");
            }
        }
    }

    public void gameLoop() {
        while (true) {
            display();

            // User move input
            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter move: ");
            int move = input.nextInt();

            // Places move onto board   || move/10 ==0 && board[move%10-1][move/10-1] == null
            if (board[move%10-2][move/10-1] != null && board[move%10-1][move/10-1] == null) {
                if (board[move%10-2][move/10-1].getValue() == deck.getCards().get(deck.getCardsLeft()-1).getValue()+1) {
                    board[move % 10 - 1][move / 10 - 1] = deck.deal();
                    System.out.println("\n");
                }
                else
                    System.out.println("Invalid move");
            }
            else
                System.out.println("Invalid move");
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
