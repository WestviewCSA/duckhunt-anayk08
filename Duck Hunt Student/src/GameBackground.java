import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class GameBackground{
	
	private Image img; 	
	private AffineTransform tx;
	private int x, y;
	private double xScale = 1, yScale = 1;

	public GameBackground(String bg) {
		
		img = getImage("/imgs/"+bg); //load the image for Tree

		tx = AffineTransform.getTranslateInstance(0, 0);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public GameBackground(String bg, int x, int y) {
		
		img = getImage("/imgs/"+bg); //load the image for Tree
		this.x = x;
		this.y = y;
		tx = AffineTransform.getTranslateInstance(0, 0);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public void setScale(double d, double e ) {
		this.xScale = d;
		this.yScale = e;
		init(x,y);
	}
	
	public void setXY(int x, int y) {
		
		this.x = x;
		this.y = y;
		init(x,y);
		
	}
	
	
	public void changePicture(String newFileName) {
		
		img = getImage(newFileName);
		init(0, 0);
		
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);

	}
	
	private void init(double a, double b) {
		
		tx.setToTranslation(a, b);
		tx.scale(xScale, yScale);
		
	}

	private Image getImage(String path) {
		
		Image tempImage = null;
		try {
			URL imageURL = GameBackground.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
		
	}

}
