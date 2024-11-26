import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Card[][] board;
    Card[] stack;
    int cardsLeft;
    boolean didUserError;

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
        board = new Card[19][7];
        stack = new Card[4];
        cardsLeft = 52;
        didUserError = false;
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
        System.out.print("Press {Enter} to begin: ");
        Scanner input = new Scanner(System.in);
        if (input.nextLine().equals("")) {
            gameLoop();
            System.out.println("Congrats " + p1.getName() + ", you completed solitaire!");
        }
    }

    // Fills board
    public void setUp() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Number of cards per row decreases
                if (j >i-1) {
                    board[i][j] = deck.dealAndRemove();
                    cardsLeft--;
                    // Card isn't at the top of the deck, make it hidden
                    if (i != j)
                        board[i][j].setIsHidden(true);
                }
                else
                    // All other cards are null
                    board[i][j] = null;
            }
        }
    }

    // Prints board and stack
    public void display() {
        // Display error message
        if (didUserError) {
            System.out.println("\n\n\n\nInvalid move, try again\n\n");
            didUserError = false;
        }
        else
            System.out.println("\n\n\n\n\n\n");

        System.out.println("< Board >");
        System.out.println("      1.    2.    3.    4.    5.    6.    7.");
        // Align and print numbers on the side
        for (int i = 0; i < board.length; i++) {
            if (i+1 <10)
                System.out.print(" ");
            System.out.print((i+1) + ". ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null)
                    // Displays non-hidden cards
                    // Checks if a card should be shown or not
                    if (board[i][j].getIsHidden() == false || (board[i+1][j] == null && board[i][j].getIsHidden())) {
                        board[i][j].setIsHidden(false);
                        System.out.print(board[i][j].getDesign() + " ");
                    }
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

    public boolean isBoardEmpty(Card[][] board) {
        // If the first row is empty, the whole board is empty
        for (int i = 0; i < board[0].length; i++) {
            if(board[0][i] != null)
                return false;
        }
        return true;
    }

    // Finds the highest non-null Y coordinate of a given X coordinate
    public int findYCoord(int xCoord) {
        if (xCoord >= 7)
            return -1;
        for (int i = 0; i < board.length; i++) {
            // Upon finding the first null, return the index before
            if (board[i][xCoord] == null) {
                return i -1;
            }
        }
        return -1;
    }

    // Returns false if a card has to be taken of board
    public boolean interactWithStack(Card card, Card cardBelow, int move) {
        // Allows user to put in ace from board or deck into an empty stack
        if (stack[card.getIndex()] == null && card.getValue() == 1)
            if (move == 9) {
                stack[card.getIndex()] = deck.dealAndRemove();
                cardsLeft--;
            }
            else {
                stack[card.getIndex()] = card;
                return false;
            }
        // If the card in the stack is one value below the user's card, place their card into the stack
        else if (stack[card.getIndex()] != null && stack[card.getIndex()].getValue() == card.getValue()-1) {
            if (move == 9) {
                stack[card.getIndex()] = deck.dealAndRemove();
                cardsLeft--;
            }

            else if (card != null && cardBelow == null && stack[card.getIndex()].getValue() == card.getValue()-1) {
                stack[card.getIndex()] = card;
                return false;
            }
        }
        else
            didUserError = true;
        return true;
    }

    public void gameLoop() {
        Card firstCard = null;
        int xCoord = 0;
        int yCoord = 0;
        int move = 0;
        while (!deck.isEmpty() && !isBoardEmpty(board)) {
            firstCard = deck.getCards().get(deck.getCardsLeft()-1);

            display();
            System.out.println("\nYou have a " + firstCard + "   |   You have " + cardsLeft + " cards left");

            // User move input
            Scanner input = new Scanner(System.in);
            System.out.print("\n{0} to go to next card | {8} to interact with stack | {9} to interact with the board: ");
            move = input.nextInt();

            // Allows user to go to next card in deck
            if (move == 0) {
                deck.getCards().add(0, deck.getCards().remove(deck.getCardsLeft()-1));
            }
            // If user wants to interact with stack
            else if (move == 8) {
                System.out.print("Enter column you want to place the last card of into the stack -OR- {9} to place the card you have in the stack: ");
                move = input.nextInt();

                if (move ==9)
                    interactWithStack(firstCard, null, 9);
                else {
                    xCoord = move - 1;
                    yCoord = findYCoord(xCoord);
                    if (!interactWithStack(board[yCoord][xCoord], board[yCoord + 1][xCoord], 0))
                        board[yCoord][xCoord] = null;
                }
            }
            // If user enters in coordinates
            // If the box above is empty and the box you're on is empty, you can play there
            else if(move == 9) {
                System.out.print("Enter column # where you want your card to go to -OR- Enter card coordinates first then enter the column # of the spot you want to move them, in this form -> xyh: ");
                move = input.nextInt();

                if (move <= 8) {
                    xCoord = move -1;
                    // yCoord is +1 because it represents the space below the last card of the column
                    yCoord = findYCoord(xCoord)+1;
                    // Allow user to put a king in an empty column
                    if (yCoord == 0 && firstCard.getValue() == 13) {
                        board[yCoord][xCoord] = deck.dealAndRemove();
                        cardsLeft--;
                    }
                    // Checks for valid space
                    else if (board[yCoord - 1][xCoord] != null && board[yCoord][xCoord] == null) {
                        // If you're card's value is one less than the card above, you can play there
                        if (board[yCoord - 1][xCoord].getValue() == firstCard.getValue() + 1) {
                            board[yCoord][xCoord] = deck.dealAndRemove();
                            cardsLeft--;
                        }
                        else
                            didUserError = true;
                    }
                    else
                        didUserError = true;
                }
                else {
                    // Separates the 3 digit input into respective coordinates
                    xCoord = move/100 -1;
                    yCoord = ((move/10)%10) -1;
                    int newXCoord = move%10 -1;
                    int newYCoord = findYCoord(newXCoord);
                    // How many cards are being moved is the range
                    int range = findYCoord(xCoord) - yCoord +1;
                    // Checks to see if it's a valid move
                    // Allows user to put a king into a space in the first row of the board
                    if ((newYCoord == -1 && board[yCoord][xCoord].getValue() == 13) || (board[yCoord][xCoord] != null && board[newYCoord][newXCoord].getValue() == board[yCoord][xCoord].getValue()+1 ))
                        for (int i = 0; i < range; i++) {
                            board[newYCoord+i+1][newXCoord] = board[yCoord+i][xCoord];
                            board[yCoord+i][xCoord] = null;
                        }
                    else
                        didUserError = true;
                }
            }
            else
                didUserError = true;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
