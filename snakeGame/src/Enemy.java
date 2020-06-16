import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle{
	int dx = 0, dy = 0, speed = 1;
	snakeHead mainHead;
	Food gameFood;
	int direction = 0, timer = 0, timeDelay = 200;
	Random gen = new Random();
	Boolean alive = true;
	
	public Enemy(snakeHead head, Food food) {
		super(100,100, 50, 100);
		mainHead = head;
		gameFood = food;
	}
	
	public void draw(Graphics2D win) {
		if(gameFood.score >= 5) {
			if(alive) {
				this.translate(dx, dy);
			
				if(mainHead.getX() > this.getX()) {
					dx = speed;
				}
				if(mainHead.getX() < this.getX()) {
					dx = -speed;
				}
				if(mainHead.getY() > this.getY()) {
					dy = speed;
				}
				if(mainHead.getY() < this.getY()) {
					dy = -speed;
				}
			}
			if(!alive) {
				if(timer == timeDelay) {
					alive = true;
					this.setLocation(gen.nextInt(1000),0);
					timer = 0;
				}
				else {
					timer++;
				}
			}
		}
		else { //enemy isn't show under score of 5
			
		}
			
	}
}
