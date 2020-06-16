import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class powerUps extends Rectangle {

	snakeHead head;
	snakeBody body;
	Food food;
	Enemy enemy1;

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

				if (whatPower == 1) {
					food.score++;
					showWord1 = true;
				}
				if (whatPower == 2) {
					enemy1.alive = false;
					showWord2 = true;
				}
				if (whatPower == 3) {
					enemy1.speed += 1;
					showWord3 = true;
				}
				if (whatPower == 4) {
					//Make sure speed is at least 1
					if(enemy1.speed > 2) {
						enemy1.speed -= 1;
					}
					showWord4 = true;
				}
				if (whatPower == 5) {
					head.speed -= 1;
					showWord5 = true;

				}
				if (whatPower == 6) {
					head.speed -= 1;
					showWord6 = true;
				}

				whatPower = gen.nextInt(6) + 1;
				shown = false;
			}

		}
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
				win.drawString("Snake speed increased", 50, 150);
			}
		}

		if (showWord6 == true) {
			if (displayTimer == displayDelay) {
				displayTimer = 0;

				showWord6 = false;
			} else {
				displayTimer++;
				win.drawString("Snake speed decreased", 50, 150);
			}
		}
	}
}
