import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Card[][] board;
    Card[] stack;

    public Game() {
        // Player name input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name1 = input.nextLine();
        this.p1 = new Player(name1);

        // Card Deck
        char[] ranks = {'A','2','3','4','5','6','7','8','9','X','J','Q','K'};
        char[] suits = {'♣', '♠', '♥', '♦'};
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};

        this.deck = new Deck(ranks, suits, values);

        // Board: height, length
        board = new Card[9][7];
        stack = new Card[4];
    }

    public static void printInstructions() {
        // Prints instructions
        System.out.println("  ______   ______   ___     _____   ______   ______   _____   ______   ______    ");
        System.out.println("/    ___| |      | |   |   '.   .' |_    _| |      | '.   .' |   .  | |    __'   ");
        System.out.println("\\___   \\  |   '  | |   |_   |   |    |  |   |   '  |  |   |  |   --.' |    __' ");
        System.out.println("/______/  '______' '_____' '_____'   '__'   |_.'`._| '_____' '__.'._\\ '______'  ");
        System.out.println("\nWelcome to Solitaire! Here are the rules:\n 1] The goal is to get all the cards into " +
                "the stack in the correct order (Ex. Ace, then 2, then 3, etc.)\n 2] You can go through your deck " +
                "to get a desired card by pressing {1}\n 3] Each turn, you can place your card onto the board, " +
                "into the stack, or move something already on the board\n 4] To reveal hidden cards you must put " +
                "every card below it in a different location\n 5] An 'X' on a card just means 10");
        System.out.println("___________________________________________________________________________________");
    }

    public void playGame() {
        deck.shuffle();
        printInstructions();
        setUp();
        gameLoop();
    }

    public void setUp() {
        // Fills board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j >i-1) {
                    board[i][j] = deck.dealAndRemove();
                    if (i != j)
                        board[i][j].setIsHidden(true);
                }
                else
                    board[i][j] = null;
            }
        }

        // Prints whole deck (temporary)
        // h
        // e
        // y
//        for (int i = 0; i < deck.getCards().size(); i++) {
//            if (i % 13.0 == 0) {
//                System.out.println(" ");
//            }
//            System.out.print(deck.getCards().get(i).getDesign() + ", ");
//        }
//        System.out.println("\n");


    }

    public void display() {
        // Prints board
        System.out.println("< Board >");
        System.out.println("     1.    2.    3.    4.    5.    6.    7.");
        for (int i = 0; i < board.length; i++) {
            System.out.print((i+1) + ". ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null)
                    if (board[i][j].getIsHidden() == false)
                        System.out.print(board[i][j].getDesign() + " ");
                    else
                        System.out.print("| ? | ");
                else
                    System.out.print("  -   ");
            }
            System.out.print("\n");
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
                System.out.print(stack[i].getDesign() + " ");
        }
    }

//    public void addCoordToStack(Card card) {
//        if (stack[card.getIndex()] == null && card.getValue() == 1)
//    }

    public void gameLoop() {
        Card firstCard = null;
        int xCoord = 0;
        int yCoord = 0;
        int move = 0;
        while (!deck.isEmpty()) {
            firstCard = deck.getCards().get(deck.getCardsLeft()-1);

            display();
            System.out.println("\nYou have a " + firstCard);

            // User move input
            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter coordinates in this format -> xy | {1} to go to next card | {0} to put something in the stack: ");
            move = input.nextInt();
            xCoord = move/10-1;
            yCoord = move%10-1;

            // If user wants to go to next card
            if (move == 1) {
                deck.getCards().add(0, deck.getCards().remove(deck.getCardsLeft()-1));
            }
            // If user wants to add something to the stack
            else if (move == 0) {
                System.out.print("Enter card coordinates you want to put in the stack in this format -> xy | {1} to place the card you have in the stack: ");
                int cardCoords = input.nextInt();
                xCoord = cardCoords/10 -1;
                yCoord = cardCoords%10 -1;

                // Checks if there is nothing in the stack yet
                if(cardCoords == 1 && stack[firstCard.getIndex()] == null && firstCard.getValue() == 1)
                    // Places card in stack
                    stack[firstCard.getIndex()] = deck.dealAndRemove();
                // Checks if card is one greater than the one in the stack already
                else if (cardCoords == 1 && stack[firstCard.getIndex()] != null && stack[firstCard.getIndex()].getValue() == firstCard.getValue()-1)
                    stack[firstCard.getIndex()] = deck.dealAndRemove();


                else if (stack[board[yCoord][xCoord].getIndex()] == null && board[yCoord][xCoord].getValue() == 1) {
                    stack[board[yCoord][xCoord].getIndex()] = board[yCoord][xCoord];
                    board[yCoord][xCoord] = null;
                }
                // Puts a card from the board into the stack
                else if (board[yCoord][xCoord] != null && board[yCoord +1][xCoord] == null && board[yCoord][xCoord].getValue() == stack[board[yCoord][xCoord].getIndex()].getValue() +1) {
                    stack[board[yCoord][xCoord].getIndex()] = board[yCoord][xCoord];
                    board[yCoord][xCoord] = null;
                }
                else
                    System.out.println("Invalid");

            }
            // If user enters in coordinates
            // If the box above is empty and the box you're on is empty, you can play there
            else if (board[yCoord -1][xCoord] != null && board[yCoord][xCoord] == null) {
                // If you're card's value is one less than the card above, you can play there
                if (board[yCoord -1][xCoord].getValue() == firstCard.getValue()+1) {
                    board[yCoord][xCoord] = deck.dealAndRemove();
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
