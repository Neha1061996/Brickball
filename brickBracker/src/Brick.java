

import javax.swing.JFrame;
public class Brick {

	public static void main(String[] args)// main class
	{
		// TODO Auto-generated method stub
		
		
		JFrame obj= new JFrame();
		Gamepla gameplay= new Gamepla(); //obj of gamepla class
		obj.setBounds(10,10,700,600);// x, y coordinates of top left, width, height
		obj.setResizable(false);
		obj.setTitle("Breakout Ball");
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Rectangle display is created 
        obj.add(gameplay);
	}
	

}
