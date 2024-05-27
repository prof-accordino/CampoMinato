import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Mainclass 
{
	static int righe = 10;
	static int colonne = 10;
	static int bomb_prob = 10;
	static int bomb_num = 0;
	static final int button_num = righe * colonne;
	
	static Random g = new Random();
	static JFrame frame = new JFrame("Campo minato");
	static ArrayList<Tile> tiles = new ArrayList<Tile>(button_num);
	static JPanel buttonsPanel;
	static JLabel statusLabel;


	public static void main(String args[])
	{
		buttonsPanel = new JPanel(new GridLayout(righe, colonne));

		for (int i = 0; i < righe; i++)
		{
			for (int j = 0; j < colonne; j++)
			{
				boolean isBomb = g.nextInt(button_num) < bomb_prob/100.0*button_num;
				Tile t = new Tile(i, j, isBomb);
				buttonsPanel.add(t);
				tiles.add(t);
				if(isBomb) 
					bomb_num++;
			}
		}		

		statusLabel = new JLabel("Caselle rimanti: " + (button_num - bomb_num));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);		
		
		GameLogic b = new GameLogic(tiles, statusLabel);
		for(Tile t : tiles)
		{
			t.assignNeighbors();
			t.addMouseListener(b);
		}
		
		frame.setLayout(new BorderLayout());
		frame.add(buttonsPanel, BorderLayout.CENTER);
		frame.add(statusLabel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
}
