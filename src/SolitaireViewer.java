import javax.swing.*;
import java.awt.*;

public class SolitaireViewer extends JFrame {
    Game game;
    private final int WINDOW_WIDTH = 850;
    private final int WINDOW_HEIGHT = 1050;
    Image blank;

    public SolitaireViewer(Game game) {
        this.game = game;
        blank = new ImageIcon("Resources/Clubs-8.png.png").getImage();

        // Last four lines
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tic Tac Toe");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(10, 10, 10));
        for (int i = 0; i < 7; i++) {
            g.drawImage(blank, 40+ i*(75 + 10), 90, 105,105,this);
        }
        g.drawImage(blank, 705, 90, 105, 105, this);
    }
}
