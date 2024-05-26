import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameLogic implements MouseListener
{
	private boolean gameOver = false;
	private int bomb_number;
	JLabel statusLabel;
	ArrayList<Tile> tiles;
	
	public GameLogic(ArrayList<Tile> tiles, JLabel statusLabel)
	{
		this.tiles = tiles;
		this.statusLabel = statusLabel;
		for (Tile t : tiles)
			if(t.isBomb)
				bomb_number++;
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
		else
			statusLabel.setText("Caselle rimanti: " + remainingButtons());
	}

	private void gameOver(boolean win)
	{
		for (Tile tile : tiles)		
			tile.setSelected(true);
		gameOver = true;
		statusLabel.setText("HAI " + (win ? "VINTO" : "PERSO!"));
	}

	private boolean checkWin()
	{
		if (remainingButtons() == 0)
			return true;
		for (Tile tile : tiles)
			if(tile.flagged != tile.isBomb)
				return false;
		return true;
	}

	private int remainingButtons()
	{
		int toClick = 0;
		for (Tile t : tiles)
			if(!t.isSelected())
				toClick++;
		return toClick - bomb_number;
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