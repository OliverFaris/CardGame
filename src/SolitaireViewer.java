import javax.swing.*;
import java.awt.*;

public class SolitaireViewer extends JFrame {
    Game game;
    private final int WINDOW_WIDTH = 770;
    private final int WINDOW_HEIGHT = 1050;
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
        phaseImage[2] = new ImageIcon("Resource/endScreen.png").getImage();



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
        if (game.getPhase() == 0)
            g.drawImage(phaseImage[0],0, 0,this);

        else if (game.getPhase() == 2)
            g.drawImage(phaseImage[2], 0, 0, this);

        else {
            if (game.getBoard() != null) {
                g.drawImage(phaseImage[1], 0, 0, this);

                // Draw board
                for (int i = 0; i < game.getBoard().length; i++) {
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
                for (int i = 0; i < 4; i++) {
                    if (game.getStack()[i] == null)
                        g.drawImage(utilityCards[i+3], 665, i*115 + 213, this);
                    else
                        g.drawImage(game.getStack()[i].getCardImage(), 665, i*115 + 213, this);
                }

            }
        }
    }
}