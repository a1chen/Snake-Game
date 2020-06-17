import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Laser extends Rectangle implements KeyListener {
	snakeHead mainHead;
	Enemy mainEnemy;
	Food mainFood;
	Boolean drawn = false;
	int dx = 0, dy = 0, speed = 10, timer = 0, timeDelay = 20, enemiesKilled = 0;
	static SoundDriver sound;

	public Laser(snakeHead head, Enemy enemy, Food food) {
		super(-100, 0, 25, 100);
		mainHead = head;
		mainFood = food;
		mainEnemy = enemy;
		String[] stringName = new String[1];
		stringName[0] = "Laser-SoundBible.com-602495617.wav";
		sound = new SoundDriver(stringName);
	}

	public void draw(Graphics2D win) {
		if (this.drawn == true) {
			this.translate(dx, dy);
			win.setColor(Color.red);
			win.fill(this);

			// Check if out of bounds
			if (this.getX() + 50 > 1000 || this.getX() < 0 || this.getY() + 50 > 1000 || this.getY() < 0) {
				drawn = false;
			}

			// Check if hit enemy
			if (this.intersects(mainEnemy)) {
				mainEnemy.alive = false;
				drawn = false;
				mainFood.score++;
				enemiesKilled++;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// Shoot laser when pressed space
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			// Only shoot one laser at a time
			if (drawn == false) {
				sound.play(0);
				this.setLocation(mainHead.getLocation());
				drawn = true;

				// Shoot in same direction as the snake is going
				if (mainHead.dx == -mainHead.speed) {
					this.setSize(100, 30);
					dx = mainHead.dx - speed;
					dy = 0;
				}
				if (mainHead.dx == mainHead.speed) {
					this.setSize(100, 30);
					dx = mainHead.dx + speed;
					dy = 0;
				}
				if (mainHead.dy == mainHead.speed) {
					this.setSize(30, 100);
					dy = mainHead.dy + speed;
					dx = 0;
				}
				if (mainHead.dy == -mainHead.speed) {
					this.setSize(30, 100);
					dy = mainHead.dy - speed;
					dx = 0;
				}
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
