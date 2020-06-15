
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Food extends Rectangle{
	static Random gen = new Random();
	static int foodPosX = gen.nextInt(1900);
	static int foodPosY = gen.nextInt(1900);
	snakeHead headUsed;
	snakeBody bodyUsed;
	Boolean touched = false;
	int speed = 5, dx = 0, dy = 0, randomInt, timer = 0, timeDelay = 15, score = 0, timerP = 0, timeDelayP = 10, randomIntP = 0;
	static SoundDriver sound;

	
	public Food(snakeHead head, snakeBody body){
		super(foodPosX, foodPosY, 100,100);
		String[] stringName = new String[1];
		stringName[0] = "Eating.wav";
		sound = new SoundDriver(stringName);
		headUsed = head;
		bodyUsed = body;
	}
	
	public void draw(Graphics2D win) {
		
		
		if(score >= 10) {
			this.translate(dx, dy);
			if(timer == timeDelay) { //makes food move in direction for long time
				randomInt = gen.nextInt(4);
				timer = 0;
			}
			else {
				timer++;
			}
			
			if(randomInt == 0) { //random movements for food
				dx = speed;
			}
			if(randomInt == 1) {
				dx = -speed;
			}
			if(randomInt == 2) {
				dy = speed;
			}
			if(randomInt == 3) {
				dy = -speed;
			}
			if(this.getX() + 130 > 2000 || this.getX() - 30 < 0) { //food doesn't pass border
				dx *= -1;
			}
			if(this.getY() + 250 > 2000 || this.getY() - 30 < 0) {
				dy *= -1;
			}
		}
		
		if(randomIntP == 0) {
			
			if(collision()) {
				sound.play(0);
				this.setLocation(gen.nextInt(1400) +300 , gen.nextInt(1400) + 300);
				bodyUsed.addPart();
				score++;
			}
		}
		
		
	}
	
	public boolean collision() {
		boolean result = false;
		if(this.intersects(headUsed)) {
			result = true;
		}
		return result;
	}
	
	
	
	
	
}
