
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class snakeComp extends GameDriverV3 implements KeyListener {

	snakeHead head;
	snakeBody body;
	Enemy lumber;
	Food gameFood;
	Laser gun;
	powerUps powerUp;

	BufferedImage arrowKeys;
	BufferedImage gameOver;
	BufferedImage spaceBar;
	BufferedImage apple;
	BufferedImage lumberjack;
	BufferedImage itemBox;
	BufferedImage[] lumberjackFramesRight, lumberjackFramesLeft;

	Rectangle background = new Rectangle(0, 0, 1000, 1000);
	int timeDelay = 1, timer = 0, gameState = 0, framePos = 0, frameDelay = 7, frameTimer = 0;
	Font impactSmall = new Font("Impact", 60, 60);
	Font Impact = new Font("Impact", 75, 75);
	Font impactLarge = new Font("Impact", 150, 150);

	GradientPaint cyanToWhite = new GradientPaint(0, 0, Color.cyan, 1500, 0, Color.WHITE);
	GradientPaint whiteToCyan = new GradientPaint(0, 0, Color.white, 1500, 0, Color.cyan);

	static SoundDriver sound;

	public snakeComp() {

		this.addKeyListener(this);

		String[] stringName = new String[2];
		stringName[0] = "Background.wav";
		stringName[1] = "Mario Kart Item Box Sound.wav";
		sound = new SoundDriver(stringName);
		sound.loop(0);
		itemBox = this.addImage("itemBox1.png");
		arrowKeys = this.addImage("arrowkeys2.png");
		lumberjack = this.addImage("lumberjack_sheet3.png");
		apple = this.addImage("100.png");
		gameOver = this.addImage("gameOver.png");
		this.lumberjackFramesRight = new BufferedImage[4];
		this.lumberjackFramesLeft = new BufferedImage[4];

		int lumberWidth = lumberjack.getWidth() / 4, lumberHeight = lumberjack.getHeight() / 4;
		for (int i = 0; i < 4; i++) {
			lumberjackFramesRight[i] = lumberjack.getSubimage(lumberWidth * i, lumberHeight * 2, lumberWidth,
					lumberHeight);
		}
		for (int i = 0; i < 4; i++) {
			lumberjackFramesLeft[i] = lumberjack.getSubimage(lumberWidth * i, lumberHeight * 1, lumberWidth,
					lumberHeight);
		}
	}

	public void start() {
		head = new snakeHead();
		body = new snakeBody(head);
		gameFood = new Food(head, body);
		lumber = new Enemy(head, gameFood);
		gun = new Laser(head, lumber, gameFood);
		powerUp = new powerUps(head, body, gameFood, lumber);

		this.addKeyListener(head);
		this.addKeyListener(gun);
		gameState = 1;
	}

	public void draw(Graphics2D win) {
		if (gameState == 0) {
			win.setPaint(whiteToCyan);
			win.fill(background);
			win.setColor(Color.YELLOW);
			win.setFont(impactLarge);

			win.drawString("SNAKE", 300, 150);
			win.setFont(Impact);
			win.drawString("By Aaron Chen", 275, 300);
			win.setColor(Color.red);
			win.drawString("Press \"I\" for instructions", 150, 500);
			win.drawString("Press \"Enter\" to start", 200, 650);

			win.drawImage(this.lumberjackFramesRight[framePos], null, 1000, 1000);
			if (frameTimer == frameDelay) {
				framePos++;
				framePos %= 4;
				frameTimer = 0;
			} else {
				frameTimer++;
			}

		}
		if (gameState == 1) { // game start

			win.setPaint(cyanToWhite);
			win.fill(background);

			this.gameFood.draw(win);
			head.moveAndDraw(win);

			if (timer == timeDelay) {

				body.follow();
				timer = 0;
			} else {
				timer++;
			}
			body.draw(win);
			
			//Out of bounds
			if (head.getX() + 25 >= 1000 || head.getX() <= 0 || head.getY() + 75 >= 1000 || head.getY() <= 0) {																									
				gameState = 2;
			}

			for (int i = body.body.size() - 1; i > 7; i--) {
				if (head.intersects(body.body.get(i))) {
					gameState = 2;
				}
			}
			win.drawImage(apple, null, (int) this.gameFood.getX(), (int) gameFood.getY()); // drawing apple
			win.setFont(Impact);
			win.drawString("Score: " + gameFood.score, 400, 75);
			lumber.draw(win);

			if (lumber.dx == lumber.speed && lumber.alive) {
				win.drawImage(this.lumberjackFramesRight[framePos], null, (int) lumber.getX() - 20,
						(int) lumber.getY()); // drawing lumberjack
				if (frameTimer == frameDelay) {
					framePos++;
					framePos %= 4;
					frameTimer = 0;
				} else {
					frameTimer++;
				}
			}

			if (lumber.dx == -lumber.speed && lumber.alive) {
				win.drawImage(this.lumberjackFramesLeft[framePos], null, (int) lumber.getX() - 20, (int) lumber.getY());
				if (frameTimer == frameDelay) {
					framePos++;
					framePos %= 4;
					frameTimer = 0;
				} else {
					frameTimer++;
				}
			}
			if (head.intersects(lumber) && lumber.alive) {// lose game if intersects with lumber
				gameState = 2;
			}
			gun.draw(win);
			powerUp.draw(win);
			if (powerUp.shown) {
				win.drawImage(this.itemBox, null, (int) powerUp.getX(), (int) powerUp.getY());
			}

			if (gameFood.score % 5 == 0 && gameFood.score != 0) {
				head.speed++;
				gameFood.score++;
			}

		}
		
		// death screen
		if (gameState == 2) { 
			win.setColor(Color.blue);
			win.fill(background);
			win.setColor(Color.red);
			win.setFont(Impact);
			win.drawString("Final Score: " + this.gameFood.score, 300, 75);
			win.drawString("Enemies killed:" + gun.enemiesKilled, 250, 200);
			win.drawString("Press \"ESC\" to return home", 100, 325);
			win.drawString("Press \"ENTER\" to play again", 90, 450);
		}
		
		// instruction screen
		if (gameState == 3) { 
			win.setColor(Color.PINK);
			win.fill(background);
			win.setPaint(whiteToCyan);
			win.setFont(Impact);
			win.setColor(Color.black);
			win.drawString("Collect apples!", 250, 75);
			win.drawImage(apple, null, 700, 0);
			win.drawImage(arrowKeys, null, 200, 250);
			win.drawString("Spacebar To shoot", 225, 750);
			win.drawString("Press \"Enter\" to start", 400, 1550);

			win.drawImage(this.lumberjackFramesRight[framePos], null, 450, 825);
			if (frameTimer == frameDelay) {
				framePos++;
				framePos %= 4;
				frameTimer = 0;
			} else {
				frameTimer++;
			}
			win.setFont(Impact);
			win.drawString("Collect" + "         for a random effect!", 30, 200);
			win.drawImage(this.itemBox, null, 255, 125);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// gameState = 1;
			start();
		}
		//Back to home screen
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = 0;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_I) {
			gameState = 3;
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
