import javax.swing.JFrame;
public class Brick {

	public static void main(String[] args)// main class
	{
		// TODO Auto-generated method stub
		
		
		JFrame obj= new JFrame();
		Gameplay gameplay= new Gameplay(); //obj of gameplay class
		obj.setBounds(10,10,700,600);
		obj.setResizable(false);
		obj.setTitle("Breakout Ball");
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Rectangle display is created 
        obj.add(gameplay);
	}

}
