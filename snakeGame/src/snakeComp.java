
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class snakeComp extends GameDriverV3 implements KeyListener {
	
	final static int STATE_SPLASH_PAGE = 0;
	final static int STATE_GAME_START = 1;
	final static int STATE_DEATH = 2;
	final static int STATE_INSTRUCTIONS = 3;
	
	//Pieces of game
	snakeHead head;
	snakeBody body;
	Enemy lumber;
	Food gameFood;
	Laser gun;
	powerUps powerUp;

	//Images used
	BufferedImage arrowKeys;
	BufferedImage gameOver;
	BufferedImage spaceBar;
	BufferedImage apple;
	BufferedImage lumberjack;
	BufferedImage itemBox;
	BufferedImage[] lumberjackFramesRight, lumberjackFramesLeft;

	Rectangle background = new Rectangle(0, 0, 1000, 1000);
	
	Font impactSmall = new Font("Impact", 60, 60);
	Font Impact = new Font("Impact", 75, 75);
	Font impactLarge = new Font("Impact", 150, 150);

	GradientPaint cyanToWhite = new GradientPaint(0, 0, Color.cyan, 1500, 0, Color.WHITE);
	GradientPaint whiteToCyan = new GradientPaint(0, 0, Color.white, 1500, 0, Color.cyan);
	
	int timeDelay = 1, timer = 0, gameState = 0, framePos = 0, frameDelay = 7, frameTimer = 0;
	
	static SoundDriver sound;

	public snakeComp() {
		this.addKeyListener(this);
		
		//Add all resources such as sound and images
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
		
		//Get all the frames for the lumberjack in an array
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

	//Create all instances of items
	public void start() {
		head = new snakeHead();
		body = new snakeBody(head);
		gameFood = new Food(head, body);
		lumber = new Enemy(head, gameFood);
		gun = new Laser(head, lumber, gameFood);
		powerUp = new powerUps(head, body, gameFood, lumber);

		this.addKeyListener(head);
		this.addKeyListener(gun);
		gameState = STATE_GAME_START;
	}

	public void draw(Graphics2D win) {
		if (gameState == STATE_SPLASH_PAGE) {
			//Fill background
			win.setPaint(whiteToCyan);
			win.fill(background);
			win.setColor(Color.YELLOW);
			win.setFont(impactLarge);
			
			//Draw all the strings
			win.drawString("SNAKE", 300, 150);
			win.setFont(Impact);
			win.drawString("By Aaron Chen", 275, 300);
			win.setColor(Color.red);
			win.drawString("Press \"I\" for instructions", 150, 500);
			win.drawString("Press \"Enter\" to start", 200, 650);
			
			//Draw lumberjack
			win.drawImage(this.lumberjackFramesRight[framePos], null, 450, 825);
			if (frameTimer == frameDelay) {
				framePos++;
				framePos %= 4;
				frameTimer = 0;
			} else {
				frameTimer++;
			}

		}
		//Game start
		if (gameState == STATE_GAME_START) { 
			//Draw background
			win.setPaint(cyanToWhite);
			win.fill(background);
			
			//Draw food and snake
			this.gameFood.draw(win);
			head.moveAndDraw(win);
			
			//Body of snake follows the snake head
			if (timer == timeDelay) {
				body.follow();
				timer = 0;
			} else {
				timer++;
			}
			body.draw(win);
			
			//Check if out of bounds
			if (head.getX() + 25 >= 1000 || head.getX() <= 0 || head.getY() + 75 >= 1000 || head.getY() <= 0) {																									
				gameState = STATE_DEATH;
			}

			//Body intersects
			for (int i = body.body.size() - 1; i > 7; i--) {
				if (head.intersects(body.body.get(i))) {
					gameState = STATE_DEATH;
				}
			}
			//Draw items
			win.drawImage(apple, null, (int) this.gameFood.getX(), (int) gameFood.getY()); 
			lumber.draw(win);
			gun.draw(win);
			powerUp.draw(win);
			
			//Keep score
			win.setFont(Impact);
			win.drawString("Score: " + gameFood.score, 400, 75);
			
			//Draw lumberjack going left and right directions
			if (lumber.dx == lumber.speed && lumber.alive) {
				win.drawImage(this.lumberjackFramesRight[framePos], null, (int) lumber.getX() - 20,
						(int) lumber.getY()); 
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
			
			//Check if snake intersects with lumberjack
			if (head.intersects(lumber) && lumber.alive) {
				gameState = 2;
			}
			
			//Show powerups
			if (powerUp.shown) {
				win.drawImage(this.itemBox, null, (int) powerUp.getX(), (int) powerUp.getY());
			}

			//Increase speed every 5 points
			if (gameFood.score % 5 == 0 && gameFood.score != 0) {
				head.speed++;
				gameFood.score++;
			}

		}
		
		//Death Screen
		if (gameState == STATE_DEATH) { 
			win.setColor(Color.blue);
			win.fill(background);
			win.setColor(Color.red);
			win.setFont(Impact);
			win.drawString("Final Score: " + this.gameFood.score, 300, 75);
			win.drawString("Enemies killed: " + gun.enemiesKilled, 250, 200);
			win.drawString("Press \"ESC\" to return home", 100, 325);
			win.drawString("Press \"ENTER\" to play again", 90, 450);
		}
		
		//Instruction screen
		if (gameState == STATE_INSTRUCTIONS) { 
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
			start();
		}
		//Back to home screen
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = STATE_SPLASH_PAGE;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_I) {
			gameState = STATE_INSTRUCTIONS;
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
