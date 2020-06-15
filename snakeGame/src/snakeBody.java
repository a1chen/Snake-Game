

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class snakeBody extends Rectangle {
	ArrayList<snakeBody> body = new ArrayList<snakeBody>();
	snakeHead mainHead;
	int xPos = 0, yPos = 0;
	public snakeBody(snakeHead head) {
		super(-50,0,50,50);
		mainHead = head;	
	}
	
	public void addPart() {
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));	
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));		
		body.add(new snakeBody(mainHead));		
		}
	
	
	
	
	public void follow() {
		
		
		if(body.size() != 0) {
			body.get(0).setLocation((int)mainHead.getX(), (int)mainHead.getY());
			for(int i = body.size()-1; i > 0; i--) {
				body.get(i).setLocation((int)body.get(i-1).getX(),(int)body.get(i-1).getY());
			}
		}
	}
	
	public void draw(Graphics2D win) {
		if(body.size() != 0) {
			for(int i = 0; i<body.size() -1; i++) {
				win.setColor(Color.black);
				win.fill(body.get(i));
				
			}
		}
	}
	
	
}
