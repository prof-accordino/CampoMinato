import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Mainclass 
{
	static int righe = 10;
	static int colonne = 10;
	static int bomb_prob = 10;
	static final int button_num = righe * colonne;
	static Random g = new Random();
	
	static final ImageIcon bombIcon = new ImageIcon("bomb.png");
	static final ImageIcon flagIcon = new ImageIcon("flag.png");
	static ArrayList<Tile> tiles = new ArrayList<Tile>();

	static JFrame frame = new JFrame("Campo minato");
	static JPanel buttonsPanel;
	static JLabel statusLabel;

	public static void main(String args[])
	{
		Bottoni b = new Bottoni(righe, colonne, bomb_prob);
		buttonsPanel = new JPanel(new GridLayout(righe, colonne));

		for (int i = 0; i < righe; i++)
		{
			for (int j = 0; j < colonne; j++)
			{
				boolean isBomb = g.nextInt(button_num) < bomb_prob/100.0*button_num;				
				Tile t = new Tile(i, j, isBomb);
				t.addMouseListener(b);
				buttonsPanel.add(t);
				tiles.add(t);				
			}
		}		
		
		Tile.setReady();
		for(Tile t : tiles)
			t.assignNeighbors();		
		
		frame.setLayout(new BorderLayout());
		frame.add(buttonsPanel, BorderLayout.CENTER);
		frame.add(statusLabel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
}
