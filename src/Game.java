import java.util.Scanner;

public class Game {
    Deck deck;
    Player p1;
    Card[][] board;
    Card[] stack;
    int cardsLeft;

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
                    cardsLeft--;
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
        System.out.println("\n\n\n\n\n\n\n");
        System.out.println("< Board >");
        System.out.println("      1.    2.    3.    4.    5.    6.    7.");
        for (int i = 0; i < board.length; i++) {
            if (i+1 <10)
                System.out.print(" ");
            System.out.print((i+1) + ". ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null)
                    if (board[i][j].getIsHidden() == false || board[i+1][j] == null) {
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

    public int findYCoord(int xCoord) {
        if (xCoord >= 7)
            return -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i][xCoord] == null) {
                return i -1;
            }
        }
        return -1;
    }

    public boolean interactWithStack(Card card, Card cardBelow, int move) {
        if (stack[card.getIndex()] == null && card.getValue() == 1)
            if (move == 9) {
                stack[card.getIndex()] = deck.dealAndRemove();
                cardsLeft--;
            }
            else {
                stack[card.getIndex()] = card;
                return false;
            }
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
            System.out.println("Invalid move.");
        return true;
    }

    public void gameLoop() {
        Card firstCard = null;
        int xCoord = 0;
        int yCoord = 0;
        int move = 0;
        while (!deck.isEmpty()) {
            firstCard = deck.getCards().get(deck.getCardsLeft()-1);

            display();
            System.out.println("\nYou have a " + firstCard + "   |   You have " + cardsLeft + " cards left");

            // User move input
            Scanner input = new Scanner(System.in);
            System.out.print("\n{0} to go to next card | {8} to interact with stack | {9} to interact with the board: ");
            move = input.nextInt();
            // If user wants to go to next card
            if (move == 0) {
                deck.getCards().add(0, deck.getCards().remove(deck.getCardsLeft()-1));
            }
            // If user wants to interact with stack
            else if (move == 8) {
                System.out.print("Enter column you want to place the last card of into the stack| {9} to place the card you have in the stack: ");
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
                    yCoord = findYCoord(xCoord)+1;
                    System.out.println(yCoord);
                    // Allow user to put a king in an empty column
                    if (yCoord == 0 && firstCard.getValue() == 13) {
                        board[yCoord][xCoord] = deck.dealAndRemove();
                        cardsLeft--;
                    }
                    else if (board[yCoord - 1][xCoord] != null && board[yCoord][xCoord] == null) {
                        // If you're card's value is one less than the card above, you can play there
                        if (board[yCoord - 1][xCoord].getValue() == firstCard.getValue() + 1) {
                            board[yCoord][xCoord] = deck.dealAndRemove();
                            cardsLeft--;
                        }
                        else
                            System.out.println("Invalid move");
                    }
                    else
                        System.out.println("Invalid move");
                }
                else {
                    xCoord = move/100 -1;
                    yCoord = ((move/10)%10) -1;
                    int newXCoord = move%10 -1;
                    int newYCoord = findYCoord(newXCoord);
                    int range = findYCoord(xCoord) - yCoord +1;
                    System.out.println("yCoord: " + newYCoord);
                    if ((newYCoord == -1 && board[yCoord][xCoord].getValue() == 13) || (board[yCoord][xCoord] != null && board[yCoord][xCoord].getValue() == board[newYCoord][newXCoord].getValue() -1 ))
                        for (int i = 0; i < range; i++) {
                            board[newYCoord+i+1][newXCoord] = board[yCoord+i][xCoord];
                            board[yCoord+i][xCoord] = null;
                        }

                }
            }
            else
                System.out.println("Invalid move");

        }
        System.out.println("You win!");

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
