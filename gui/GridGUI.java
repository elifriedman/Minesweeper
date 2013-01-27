package gui;


/**
 * Write a description of class GridGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import game.*;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Dimension;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import java.awt.Image;
import javax.swing.ImageIcon;

import java.util.ArrayList;
public class GridGUI extends JPanel implements ComponentListener
{
    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 16;
    private Minesweeper game;
    private Grid grid;
    private TileGUI[][] allTiles;
    
    public GridGUI() {
        super(new GridLayout(DEFAULT_HEIGHT,DEFAULT_WIDTH,0,0));
        game = new Minesweeper();
        grid = game.getGrid();
        
        allTiles = new TileGUI[width()][height()];
        
        for(int rows = 0; rows < height(); rows++) {
            for(int cols = 0; cols < width(); cols++) {
                allTiles[cols][rows] = new TileGUI(grid.getTile(cols,rows), this);
                add(allTiles[cols][rows]);
            }
        }
        addComponentListener(this);
    }
    public GridGUI(int width, int height, int mines) {
        super(new GridLayout(height,width,0,0));
        game = new Minesweeper(width, height, mines);
        grid = game.getGrid();
        
        allTiles = new TileGUI[width()][height()];
        
        for(int rows = 0; rows < height(); rows++) {
            for(int cols = 0; cols < width(); cols++) {
                allTiles[cols][rows] = new TileGUI(grid.getTile(cols,rows), this);
                add(allTiles[cols][rows]);
            }
        }
        addComponentListener(this);
    }
    
	public GridGUI(Minesweeper g) {
        super(new GridLayout(g.getGrid().getWidth(), g.getGrid().getHeight(),0,0));
        game = g;
        grid = game.getGrid();
        
        allTiles = new TileGUI[width()][height()];
        
        for(int rows = 0; rows < height(); rows++) {
            for(int cols = 0; cols < width(); cols++) {
                allTiles[cols][rows] = new TileGUI(grid.getTile(cols,rows), this);
                add(allTiles[cols][rows]);
            }
        }
        addComponentListener(this);
    }
    
    public void run(Location loc) {
        game.play(loc);
        for(int rows = 0; rows < height(); rows++) {
            for(int cols = 0; cols < width(); cols++) {
                TileGUI t = allTiles[cols][rows];
                Location ref = t.getTile().getLocation();
                t.changeTile(grid.getTile(ref));
            }
        }
    }
    
    public int width() {
        return grid.getWidth();
    }
    
    public int height() {
        return grid.getHeight();
    }
    
     public TileGUI getTileGUI(int x, int y) {
        return allTiles[x][y];
    }
    
    public TileGUI getTileGUI(Location loc) {
        return allTiles[loc.getX()][loc.getY()];
    }
    
    public void componentHidden(ComponentEvent e) {
        
    }
    
    public void componentMoved(ComponentEvent e) {
        
    }
    //If the GridGUI container is resized, we'll want to resize all the TileGUIs. Tough...
    public void componentResized(ComponentEvent e) {
/* 
		int width = this.getWidth();
		int height = this.getHeight();
		for(TileImage t : TileImage.values()) {
			ImageIcon temp = t.getImage();
			temp.setImage(temp.getImage().getScaledInstance(width/DEFAULT_WIDTH,height/DEFAULT_HEIGHT,
				Image.SCALE_SMOOTH ));
		}
*/
    }
    
    public void componentShown(ComponentEvent e) {
        
    }
}
