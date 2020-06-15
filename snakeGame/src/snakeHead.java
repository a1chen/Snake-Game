

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class snakeHead extends Rectangle implements KeyListener{
	int dx = 0, dy = 0, currentX = 0, currentY = 0, speed = 10;
	
	boolean up = false, down = false, left = false, right = false;
	
	public snakeHead() {
		super(1000,1000,50,50);
		
		
	}
	
	public void moveAndDraw(Graphics2D win) {
		this.translate(dx, dy);
		
		if (up && dy != speed) {
			dx = 0;
			dy = -speed;
		}
		
		if(down && dy != -speed){
			dx = 0;
			dy = speed;
		}
		if(left && dx != speed) {
			dy = 0;
			dx = -speed;
		}
		if(right && dx != -speed) {
			dy = 0;
			dx = speed;
		}
		
		
			
		
		win.setColor(Color.white);
		win.draw(this);
		win.setColor(Color.BLACK);
		win.fill(this);
	}

	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;

		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;

		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
