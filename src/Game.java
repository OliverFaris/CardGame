import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Card[][] board;
    Card[] stack;

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
        stack = new Card[4];
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
        // Fills board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i < 1)
                    board[i][j] = deck.dealAndRemove();
                else
                    board[i][j] = null;
            }
        }


        // Prints whole deck (temporary)
        // h
        // e
        // y
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (i % 13.0 == 0) {
                System.out.println(" ");
            }
            System.out.print(deck.getCards().get(i).getDesign() + ", ");
        }
        System.out.println("\n");
    }

    public void display() {
        // ATTENTION TEMPORARY


        // Prints board
        System.out.print("< Board >");
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

        // Prints the stack
        System.out.println("\n< Stack >");
        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == null && i == 0)
                System.out.print("  ♣   ");
            else if (stack[i] == null && i == 1)
                System.out.print("  ♠   ");
            else if (stack[i] == null && i == 2)
                System.out.print("  ♥   ");
            else if (stack[i] == null && i == 3)
                System.out.print("  ♦   ");
            else
                System.out.print(stack[i].getDesign());
        }
    }

    public void gameLoop() {
        while (true) {
            display();

            System.out.println("\nYou have a " + deck.getCards().get(deck.getCardsLeft()-1));

            // User move input
            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter move, press 1 to go to next card, press 0 to put something in the stack: ");
            int move = input.nextInt();
            if (move == 1) {
                // Go to next card
                deck.getCards().add(0, deck.getCards().remove(deck.getCardsLeft()-1));
            }
            else if (move == 0) {
                // Places card in stack
                System.out.print("Enter card coordinates or press 1 if the card you want is the one in your deck: ");
                int cardCoord = input.nextInt();
                // Checks if card is one greater than the one in the stack already
//                if(cardCoord == 1 && stack[deck.getCards().get(deck.getCardsLeft()-1).getIndex()] == null && deck.getCards().get(deck.getCardsLeft()-1).getValue() == 1)
//                    stack[deck.getCards().get(deck.getCardsLeft()-1).getIndex()] = deck.deal();
//                else if (cardCoord == 1 && stack[deck.getCards().get(deck.getCardsLeft()-1).getIndex()].getValue() == deck.getCards().get(deck.getCardsLeft()-1).getValue()+1) {
//                    stack[deck.getCards().get(deck.getCardsLeft()-1).getIndex()] = deck.deal();
//                }
//                else
//                    System.out.println("Invalid");

            }

            // Places move onto board   || move/10 ==0 && board[move%10-1][move/10-1] == null
            else if (board[move%10-2][move/10-1] != null && board[move%10-1][move/10-1] == null) {
                if (board[move%10-2][move/10-1].getValue() == deck.getCards().get(deck.getCardsLeft()-1).getValue()+1) {
                    board[move % 10 - 1][move / 10 - 1] = deck.dealAndRemove();
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
