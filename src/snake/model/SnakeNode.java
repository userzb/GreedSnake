package snake.model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SnakeNode {
	private int x = 0;
	private int y = 0;
	public final static int width = 10;
	public final static int height = 10;
	
	public SnakeNode() {
		
	}
	public SnakeNode(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public Rectangle getRectangle() {
		return new Rectangle(x*width,y*height,width,height);
	}
	
	public Rectangle getRectangle(Paint paint) {
		Rectangle rect = new Rectangle(x*width,y*height,width,height);
		rect.setFill(paint);
		return rect;
	}
}
