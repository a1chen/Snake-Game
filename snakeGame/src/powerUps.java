import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class powerUps extends Rectangle {

	final static int INCREASE_SCORE = 1;
	final static int KILL_ENEMY = 2;
	final static int ENEMY_SPEED_INCREASE = 3;
	final static int ENEMY_SPEED_DECREASE = 4;
	final static int DECREASE_SNAKE_SPEED = 5;
	final static int INCREASE_SNAKE_SPEED = 6;
	final static int POWER_UP_COUNT = 6;

	snakeHead head;
	snakeBody body;
	Food food;
	Enemy enemy1;

	// Random generator
	Random gen = new Random();
	Boolean shown = false, showWord1 = false, showWord2 = false, showWord3 = false, showWord4 = false,
			showWord5 = false, showWord6 = false;
	int timer = 0, timeDelay = 200, whatPower = gen.nextInt(6) + 1, displayTimer = 0, displayDelay = 150;

	Font impactSmall = new Font("Impact", 50, 50);

	static SoundDriver sound;

	public powerUps(snakeHead headUsed, snakeBody bodyUsed, Food foodUsed, Enemy enemyUsed) {
		super(100, 100, 100, 100);
		head = headUsed;
		food = foodUsed;
		body = bodyUsed;
		enemy1 = enemyUsed;

		String[] stringName = new String[1];
		stringName[0] = "Mario Kart Item Box Sound.wav";
		sound = new SoundDriver(stringName);
	}

	public void draw(Graphics2D win) {
		if (!shown) {
			if (timer == timeDelay) {
				if (gen.nextInt(2) == 0) {
					this.setLocation(gen.nextInt(700) + 200, gen.nextInt(700) + 200);
					shown = true;
				}
				timer = 0;
			} else {
				timer++;
			}
		}
		if (shown) {

			if (this.intersects(head)) {
				sound.play(0);
				this.setLocation(gen.nextInt(700) + 200, gen.nextInt(700) + 200);

				if (whatPower == INCREASE_SCORE) {
					food.score++;
					showWord1 = true;
				}
				if (whatPower == KILL_ENEMY) {
					enemy1.alive = false;
					showWord2 = true;
				}
				if (whatPower == ENEMY_SPEED_INCREASE) {
					enemy1.speed += 1;
					showWord3 = true;
				}
				if (whatPower == ENEMY_SPEED_DECREASE) {
					// Make sure speed is at least 1
					if (enemy1.speed > 2) {
						enemy1.speed -= 1;
					}
					showWord4 = true;
				}
				if (whatPower == DECREASE_SNAKE_SPEED) {
					head.speed -= 1;
					showWord5 = true;

				}
				if (whatPower == INCREASE_SNAKE_SPEED) {
					head.speed += 1;
					showWord6 = true;
				}

				whatPower = gen.nextInt(POWER_UP_COUNT) + 1;
				shown = false;
			}

		}
		
		// Display powerup message
		win.setFont(impactSmall);
		if (showWord1 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;
				showWord1 = false;
			} else {
				displayTimer++;
				win.drawString("Score Increase", 50, 150);
			}
		}
		if (showWord2 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;

				showWord2 = false;
			} else {
				displayTimer++;
				win.drawString("Enemy killed", 50, 150);
			}
		}
		if (showWord3 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;

				showWord3 = false;
			} else {
				displayTimer++;
				win.drawString("Enemy speed increased", 50, 150);
			}
		}
		if (showWord4 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;

				showWord4 = false;
			} else {
				displayTimer++;
				win.drawString("Enemy speed decreased", 50, 150);
			}
		}
		if (showWord5 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;
				showWord5 = false;
			} else {
				displayTimer++;
				win.drawString("Snake speed decreased", 50, 150);
			}
		}
		if (showWord6 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;
				showWord6 = false;
			} else {
				displayTimer++;
				win.drawString("Snake speed increased", 50, 150);
			}
		}
	}
}
