package snake.model;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Snake implements Runnable{
	public Direction uDirection = Direction.RIGHT;
	private Direction direction = Direction.RIGHT;
	private SnakeNode food = null;
	private boolean matrix[][] = new boolean[10][10]; 
	private boolean running = true;
	enum Direction {
		DOWN,UP,LEFT,RIGHT;
	}
	private List<Node> list = null;
	public Snake(List<Node> list) {
		this.list = list;
		matrix[3][1] = true;
		matrix[2][1] = true;
		matrix[1][1] = true;
		this.food = generateFood();
		list.add(this.food.getRectangle(Color.RED));
	}
	@Override
	public void run() {
		while(running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
//				e.printStackTrace();
				break;
			}
			Platform.runLater(()->{
				if(!running) {
					Alert alert = new Alert(AlertType.ERROR,"失败");
					alert.show();
					System.out.println(list.size());
					return ;
				}
				move();
					
			});
		}
	}
	
	public boolean move() {
		Rectangle node = (Rectangle)this.list.get(0);
		int x = (int)node.getX()/SnakeNode.width;
		int y = (int)node.getY()/SnakeNode.height;
		SnakeNode newNode = null;
		switch(this.direction) {
		case DOWN:
			newNode = new SnakeNode(x,(y+1));
			break;
		case UP:
			newNode = new SnakeNode(x,(y-1));
			break;
		case LEFT:
			newNode = new SnakeNode((x-1),y);
			break;
		case RIGHT:
			newNode = new SnakeNode((x+1),y);
			break;
		}
		if(newNode.getX()<0||newNode.getX()>=10||newNode.getY()<0||newNode.getY()>=10) {
			this.running = false;
			return false;
		}
		if(matrix[newNode.getX()][newNode.getY()]) {
			if(newNode.getX()==food.getX()&&newNode.getY()==food.getY()) {
				//食物
				this.list.remove(this.list.size()-1);//删除食物
				list.add(0,this.food.getRectangle());//食物处成为自身
				this.food = generateFood();
				list.add(this.food.getRectangle(Color.RED));
			}else {
				//碰到自身
				//if(newNode.getX()==last.getX()&&newNode.getY()==last.getY())
				this.running = false;
				return false;
			}
		}else {
			this.list.add(0,newNode.getRectangle());
			this.matrix[newNode.getX()][newNode.getY()] = true;
			Rectangle last = (Rectangle)this.list.get(this.list.size()-2);
			this.matrix[(int)last.getX()/SnakeNode.width][(int)last.getY()/SnakeNode.height] = false;
			this.list.remove(this.list.size()-2);
		}
		uDirection = direction;
		return true;
	}
	
	public void changeDirection(KeyCode keycode) {
		switch(keycode) {
		case UP:
			if(this.direction==Direction.DOWN||this.uDirection==Direction.DOWN) return;
			this.direction = Direction.UP;
			break;
		case DOWN:
			if(this.direction==Direction.UP||this.uDirection==Direction.UP) return;
			this.direction = Direction.DOWN;
			break;
		case LEFT:
			if(this.direction==Direction.RIGHT||this.uDirection==Direction.RIGHT) return;
			this.direction = Direction.LEFT;
			break;
		case RIGHT:
			if(this.direction==Direction.LEFT||this.uDirection==Direction.LEFT) return;
			this.direction = Direction.RIGHT;
			break;
		default:
			break;
		}
	}
	
	public SnakeNode generateFood() {
		int randomX = new Random().nextInt(10); 
		int randomY = new Random().nextInt(10);
		while(matrix[randomX][randomY]) {
			randomX = new Random().nextInt(10);
			randomY = new Random().nextInt(10);
		}
		matrix[randomX][randomY] = true;
		return new SnakeNode(randomX,randomY);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void print() {
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[i].length;j++) {
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
