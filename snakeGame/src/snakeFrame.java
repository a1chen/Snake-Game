

import javax.swing.JFrame;

public class snakeFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame j1 = new JFrame();
		j1.setTitle("Snake");
		j1.setSize(1000, 1000);
		j1.setLocationRelativeTo(null);
		j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j1.add(new snakeComp());
		j1.setVisible(true);
	}

}
