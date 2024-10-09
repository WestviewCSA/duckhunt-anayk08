import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
    blueplane d = new blueplane();
    greenplane green = new greenplane();
    bg BG = new bg();
    Asteroid asteroid = new Asteroid();

    int greenSpeed = -20;
    int greenX = 500;
    int greenY = (int) (Math.random() * (700 - 11)) + 10;
    int astSpeed = -10;
    int astX = 400;
    int astY = (int) (Math.random() * (400 - 11)) + 10;
    int score = 0;
    int health = 20;

    Font font = new Font("Serif", Font.BOLD, 85);
    boolean greenActive = true; // Track if the green plane is active

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        BG.paint(g);

        // Only paint the green plane if it is active
        if (greenActive) {
            green.setXY(greenX, greenY);
            green.paint(g);
        }

        asteroid.setXY(astX, astY);
        asteroid.paint(g);
        d.paint(g);

        greenX += greenSpeed;
        astSpeed = 0;

        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        d.setXY(x - 200, y - 150);

        if (greenX < 0) {
            greenX = 1800;
            greenY = (int) (Math.random() * (700 - 11)) + 10;
            health--;
            if (health <= 0) {
                g.setColor(Color.red);
                g.drawString("Game Over!", 800, 400);
                g.drawString("Press 'R' to Restart", 800, 500);
            }
        }

        if (astX < -20) {
            astX = 2000;
            astY = (int) (Math.random() * (700 - 11)) + 10;
        }

        g.setColor(Color.white);
        g.drawString("Score: " + score, 800, 100);
        g.setColor(Color.red);
        g.drawString("Health: " + health, 800, 200);
    }

    public static void main(String[] arg) {
        Frame f = new Frame();
    }

    public Frame() {
        JFrame f = new JFrame("Duck Hunt");
        f.setSize(new Dimension(2000, 1000));
        f.setBackground(Color.blue);
        f.add(this);
        f.setResizable(false);
        f.setLayout(new GridLayout(1, 2));
        f.addMouseListener(this);
        f.addKeyListener(this);
        Timer t = new Timer(16, this);
        t.start();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void restartGame() {
        score = 0;
        health = 20; // Reset health to 20
        greenX = 500; // Reset green plane position
        greenY = (int) (Math.random() * (700 - 11)) + 10;
        greenActive = true; // Set green plane active again
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // Handle mouse click event
        Rectangle rMouse = new Rectangle(arg0.getX(), arg0.getY(), 1, 1);
        Rectangle rPlane = new Rectangle(greenX, greenY, green.getWidth(), green.getHeight());

        if (rMouse.intersects(rPlane) && greenActive) {
            score += 1;
            greenActive = false; // Make the green plane disappear
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouse) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent mouse) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // Restart game if 'R' is pressed and health is 0
        if (arg0.getKeyCode() == KeyEvent.VK_R && health <= 0) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {}

    @Override
    public void keyTyped(KeyEvent arg0) {}
}
