
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Food extends Rectangle {
	static Random gen = new Random();
	static int foodPosX = gen.nextInt(800);
	static int foodPosY = gen.nextInt(800);
	snakeHead headUsed;
	snakeBody bodyUsed;
	Boolean touched = false;
	int speed = 3, dx = 0, dy = 0, randomInt, timer = 0, timeDelay = 15, score = 0, timerP = 0, timeDelayP = 10,
			randomIntP = 0;
	static SoundDriver sound;

	public Food(snakeHead head, snakeBody body) {
		super(foodPosX, foodPosY, 75, 75);
		String[] stringName = new String[1];
		stringName[0] = "Eating.wav";
		sound = new SoundDriver(stringName);
		headUsed = head;
		bodyUsed = body;
	}

	public void draw(Graphics2D win) {

		if (score >= 10) {
			this.translate(dx, dy);

			// Choose random direction to move in after timer == timeDelay
			if (timer == timeDelay) {
				randomInt = gen.nextInt(4);
				timer = 0;
			} else {
				timer++;
			}

			// random movements for food
			if (randomInt == 0) {
				dx = speed;
			}
			if (randomInt == 1) {
				dx = -speed;
			}
			if (randomInt == 2) {
				dy = speed;
			}
			if (randomInt == 3) {
				dy = -speed;
			}

			// Make sure food doesn't move past border
			if (this.getX() + 75 > 1000) {
				dx *= -1;
				this.x -= 50;
				timer = 0;
			}
			if (this.getX() - 75 < 0) {
				dx *= -1;
				this.x += 50;
				timer = 5;
			}
			if (this.getY() + 100 > 1000) {
				this.y -= 50;
				dy *= -1;
				timer = 0;
			}
			if (this.getY() - 75 < 0) {
				this.y += 50;
				dy *= -1;
				timer = 0;
			}
		}
		
		//Spawn a new apple and increase score
		if (collision()) {
			sound.play(0);
			this.setLocation(gen.nextInt(500) + 300, gen.nextInt(500) + 300);
			bodyUsed.addPart();
			score++;
		}
	}
	
	//Check for collision with snake head
	public boolean collision() {
		boolean result = false;
		if (this.intersects(headUsed)) {
			result = true;
		}
		return result;
	}

}
