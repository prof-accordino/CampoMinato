import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Tile extends JToggleButton
{
	ImageIcon bombIcon = new ImageIcon("bomb.png");
	ImageIcon flagIcon = new ImageIcon("flag.png");
	static final int SIZE = 40;	
	private static final long serialVersionUID = 1L;
	final boolean isBomb;
	// Metto le coordinate anche dentro il bottone
	final int i, j;
	int nearBombs = 0;
	boolean flagged = false;
	// Per facilitare i controlli metto i vicini in un array
	private ArrayList<Tile> neighbors = new ArrayList<Tile> ();
	
	public Tile(int i, int j, boolean isBomb)
	{
		this.i = i;
		this.j = j;
		this.isBomb = isBomb;
		setPreferredSize(new Dimension(SIZE,SIZE));
		setMargin(new Insets(0, 0, 0, 0));
	}	
	
	public void assignNeighbors()
	{
		int min_i = Math.max(0, i-1);	
		int max_i = Math.min(Mainclass.righe -1, i+1);
		int min_j = Math.max(0, j-1);
		int max_j = Math.min(Mainclass.colonne -1, j+1);
		
		for(Tile t : Mainclass.tiles)
		{
			if(t.i >= min_i && t.j >= min_j && t.i <= max_i && t.j <= max_j)
			{
				if(!(t.i == i && t.j == j))
				{
					neighbors.add(t);
					if (t.isBomb)
						nearBombs++;
				}
			}
		}
	}
	
	public boolean isClean()
	{
		return nearBombs == 0 && !isSelected();
	}

	public ArrayList<Tile> getNeighbors()
	{
		return neighbors;
	}
	
	@Override
	public void setSelected(boolean b)
	{
		super.setSelected(b);
		if(isBomb)
			setIcon(new ImageIcon("bomb.png"));
		else if (nearBombs > 0)
			setText(nearBombs + "");
	}
	
	public void toggleFlag()
	{
		flagged = (getIcon() != null);
		setIcon(flagged ? null : flagIcon);
	}
}
