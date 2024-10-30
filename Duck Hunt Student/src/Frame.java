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

    int greenSpeed = -10;
    int greenX = 500;
    int greenY = (int) (Math.random() * (700 - 11)) + 10;
    int astSpeed = -10;
    int astX = 400;
    int astY = (int) (Math.random() * (400 - 11)) + 10;
    int score = 0;
    int health = 20;

    Font font = new Font("Serif", Font.BOLD, 85);
    boolean greenActive = true; // Track if the green plane is active
    boolean movingToAsteroid = false; // Track if the green plane is moving to the asteroid

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        BG.paint(g);

        // Only paint the green plane if it is active or moving towards the asteroid
        if (greenActive) {
            green.setXY(greenX, greenY);
            green.paint(g);
        } else if (movingToAsteroid) {
            // Move the green plane towards the asteroid
            moveGreenPlaneToAsteroid();
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
        movingToAsteroid = false; // Reset movement state
    }

    private void moveGreenPlaneToAsteroid() {
        int targetX = astX;
        int targetY = astY;

        // Calculate direction vector
        int dx = targetX - greenX;
        int dy = targetY - greenY;

        // Normalize direction vector
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            dx = (int) (dx / distance * 10); // Increased speed to 10
            dy = (int) (dy / distance * 10); // Increased speed to 10
            greenX += dx;
            greenY += dy;

            // Check if the green plane has reached the asteroid
            if (Math.abs(greenX - targetX) < 5 && Math.abs(greenY - targetY) < 5) {
                movingToAsteroid = false; // Stop moving
                greenActive = false; // Make the green plane disappear
                System.out.println("Green plane reached the asteroid and disappeared!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // Create a hitbox for the mouse click
        Rectangle rMouse = new Rectangle(arg0.getX(), arg0.getY(), 100, 100);
        
        // Create a larger hitbox for the green plane
        int hitboxWidth = 200;  // Increased width for better hit detection
        int hitboxHeight = 200; // Increased height for better hit detection
        Rectangle rGreenPlane = new Rectangle(greenX - (hitboxWidth / 2), greenY - (hitboxHeight / 2), hitboxWidth, hitboxHeight);

        // Calculate the blue plane's hitbox
        int blueX = d.getX(); // Get the x coordinate of the blue plane
        int blueY = d.getY(); // Get the y coordinate of the blue plane
        Rectangle rBluePlane = new Rectangle(blueX, blueY, 100, 100);

        // Check if the blue plane's hitbox intersects with the green plane's hitbox
        if (rBluePlane.intersects(rGreenPlane) && greenActive) {
            score += 1; // Increase score by one
            greenActive = false; // Make the green plane disappear
            movingToAsteroid = true; // Start moving towards the asteroid
            System.out.println("Green plane hit! Score: " + score);
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
