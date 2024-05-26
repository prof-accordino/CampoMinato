import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bottoni implements MouseListener
{
	private boolean gameOver = false;
	public Bottoni(int righe, int colonne, int bomb_prob)
	{
		Mainclass.statusLabel = new JLabel("Caselle rimanti: " + remainingButtons());
		Mainclass.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		Tile tile = (Tile) e.getSource();
		
		if (e.getButton() == MouseEvent.BUTTON3)
			tile.toggleFlag();
		else if(tile.isBomb)
			gameOver(false);
		else if(tile.nearBombs > 0)
			tile.setSelected(true);
		else
			discoverArea(tile);
		
		if(checkWin() && ! gameOver)
			gameOver(true);
	}
	
	private void gameOver(boolean win)
	{
		for (Tile tile : Mainclass.tiles)		
			tile.setSelected(true);
		gameOver = true;
		Mainclass.statusLabel.setText("HAI " + (win ? "VINTO" : "PERSO!"));
	}
	
	private boolean checkWin()
	{
		if (remainingButtons() == 0)
			return true;
		for (Tile tile : Mainclass.tiles)
			if(tile.flagged != tile.isBomb)
			return false;
		return true;
	}

	private int remainingButtons()
	{
		int toClick = 0;
		for (Tile t : Mainclass.tiles)
				if(t.isClean())
					toClick++;
		return toClick;
	}

	private void discoverArea(Tile t)
	{
		ArrayList<Tile> toCheckList = new ArrayList<Tile>();
		toCheckList.add(t);
		while(!toCheckList.isEmpty())
		{
			Tile toCheck = toCheckList.get(0);
			toCheckList.remove(toCheck);			
			toCheck.setSelected(true);;

			for(Tile neighbor : toCheck.getNeighbors())
			{
				if(neighbor.isClean() && !toCheckList.contains(neighbor))
				{
					System.out.println("Aggiungo: i: " + neighbor.i + " j:" + neighbor.j);
					toCheckList.add(neighbor);
				}
				else
				{
					neighbor.setSelected(true);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}