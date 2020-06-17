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
		//Only spawn if score is greater than 5
		if(gameFood.score >= 5) {
			if(alive) {
				this.translate(dx, dy);
				
				//Move towards the snake
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
			//Spawn a new lumberjack after some time.
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
		
		//Don't spawn until score is 5
		else { 
			
		}
			
	}
}
