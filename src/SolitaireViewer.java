import javax.swing.*;
import java.awt.*;

public class SolitaireViewer extends JFrame {
    Game game;
    private final int WINDOW_WIDTH = 770;
    private final int WINDOW_HEIGHT = 1065;
    private Image[] utilityCards;
    private Image[] phaseImage;

    public SolitaireViewer(Game game) {
        this.game = game;
        utilityCards = new Image[7];
        utilityCards[0] = new ImageIcon("Resources/cardBack.png").getImage();
        utilityCards[1] = new ImageIcon("Resources/cardEmpty.png").getImage();
        utilityCards[2] = new ImageIcon("Resources/cardStack.png").getImage();
        utilityCards[3] = new ImageIcon("Resources/cBlank.png").getImage();
        utilityCards[4] = new ImageIcon("Resources/sBlank.png").getImage();
        utilityCards[5] = new ImageIcon("Resources/hBlank.png").getImage();
        utilityCards[6] = new ImageIcon("Resources/dBlank.png").getImage();
        phaseImage = new Image[3];
        phaseImage[0] = new ImageIcon("Resources/titleScreen.png").getImage();
        phaseImage[1] = new ImageIcon("Resources/background.png").getImage();
        phaseImage[2] = new ImageIcon("Resources/endScreen.png").getImage();



        // Last four lines
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Solitaire");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public Image[] getUtilityCards() {
        return utilityCards;
    }

    public void paint(Graphics g) {
        // Draw title screen
        if (game.getPhase() == 0)
            g.drawImage(phaseImage[0],0, 0,this);

        // Draw end screen
        else if (game.getPhase() == 2)
            g.drawImage(phaseImage[2], 0, 0, this);

        // Draw main game
        else {
            if (game.getBoard() != null) {
                g.drawImage(phaseImage[1], 0, 0, this);

                // Draw indices
                g.setColor(Color.black);
                g.setFont(new Font("Sans Serif", Font.PLAIN, 25));
                g.drawString("1         2         3         4        5         6         7         " +
                        "Deck", 80, 70);

                // Draw board
                for (int i = 0; i < game.getBoard().length; i++) {
                    g.drawString("" + (i+1), 20, 140 + (i*70));
                    for (int j = 0; j < game.getBoard()[i].length; j++) {
                        if (game.getBoard()[i][j] != null)
                            game.getBoard()[i][j].draw(g);
                    }
                }

                // Draw deck
                if (!game.getDeck().getCards().isEmpty()) {
                    g.drawImage(utilityCards[2], 665, 78, this);
                    g.drawImage(game.getDeck().getCards().getLast().getCardImage(), 665,78, this);
                }

                // Draw stack
                g.drawString("Stack", 685, 230);
                for (int i = 0; i < 4; i++) {
                    if (game.getStack()[i] == null)
                        g.drawImage(utilityCards[i+3], 665, i*115 + 240, this);
                    else
                        g.drawImage(game.getStack()[i].getCardImage(), 665, i*115 + 240, this);
                }
            }
        }
    }
}