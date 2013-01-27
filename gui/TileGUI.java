package gui;

/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JLabel;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Component;

import java.util.ArrayList;

import game.Tile;
import game.NumberTile;
import game.MineTile;
import game.Location;

public class TileGUI extends JLabel implements MouseListener
{
    private TileImage state;
    private Tile tile;
    private boolean initiated;
    private GridGUI parent;
    
    public TileGUI(Tile tile, GridGUI parent) {
        super(TileImage.UNREVEALED.getImage());
        
        state = TileImage.UNREVEALED;
        this.tile = tile;
        this.parent = parent;
        initiated = false;
        
        addMouseListener(this);
        setPreferredSize(new java.awt.Dimension(16,16));
    }

    public void mouseClicked(MouseEvent e) {
        if(!initiated) {
            GridGUI contain = (GridGUI)((Component)e.getSource()).getParent();
            contain.run(tile.getLocation());
        }
        
        if(e.getButton() == MouseEvent.BUTTON1) {//Left-click
            tile.click();
            if(tile instanceof NumberTile && ((NumberTile)tile).getMineCount() == NumberTile.BLANK)
                updateBlanks();
            if(tile instanceof MineTile)
                endGame();
        } else if(e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) //Middle or Right-click
            tile.rightClick();
        updateState();
        setIcon(state.getImage());
    }

    public void mousePressed(MouseEvent e) {
        if(state == TileImage.UNREVEALED)
            setIcon(TileImage.UNREVEALED_DEPRESSED.getImage());
    }

    public void mouseReleased(MouseEvent e) {
        if(getIcon() == TileImage.UNREVEALED_DEPRESSED.getImage())
                setIcon(state.getImage());
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
        if(getIcon() == TileImage.UNREVEALED_DEPRESSED.getImage())
            setIcon(state.getImage());
    }

    public void updateState() {
        switch(tile.getMarker()) {
            case Tile.REVEALED:
                if(tile instanceof NumberTile) {
                    NumberTile numTile = (NumberTile)tile;
                    state = TileImage.values()[ numTile.getMineCount() ];
                } else if(tile instanceof MineTile) {
                    state = TileImage.MINE;
                    if(((MineTile)tile).isDeathCauser())
                        state = TileImage.MINE_DEATH;
                }
                break;
            case Tile.CLEAR:
                state = TileImage.UNREVEALED;
                break;
            case Tile.FLAG:
                state = TileImage.FLAG;
                break;
            case Tile.QUESTION_MARK:
                state = TileImage.QUESTION_MARK;
                break;
        }
    }
    
    public TileImage getState() {
        return state;
    }
    
    /**
     * GridGUI uses this method to change this TileGUI's tile from a Tile to a NumberTile or a MineTile
     * 
     * @param t The new NumberTile or MineTile that will be represented by the TileGUI.
     */
    public void changeTile(Tile t) {
        if(t instanceof NumberTile || t instanceof MineTile) {
            tile = t;
            initiated = true;
        }
    }
    
    public Tile getTile() {
        return tile;
    }
    
    public boolean isRevealed() {
        return state != TileImage.UNREVEALED;
    }
    
    public void updateBlanks() {
        for(Tile t : tile.getNeighboringTiles()) {
            TileGUI tg = parent.getTileGUI(t.getLocation());
            if(!tg.isRevealed()){
                tg.updateState();
                tg.setIcon(tg.getState().getImage());
                if(((NumberTile)t).getMineCount() == NumberTile.BLANK)
                    tg.updateBlanks();
            }
        }
    }
    
    public void endGame() {
        for(int i = 0; i < parent.width(); i++) {
            for(int j = 0; j < parent.height(); j++) {
                TileGUI tg = parent.getTileGUI(i,j);
                if(tg.getTile() instanceof NumberTile) {
                    tg.removeMouseListener(tg.getMouseListeners()[0]);
                    if(tg.getTile().isFlagged())
                        tg.setIcon(TileImage.MINE_MISFLAG.getImage());
                }
                else if(tg.getTile() instanceof MineTile) {
                    tg.updateState();
                    tg.setIcon(tg.getState().getImage());
                }
            }
        }
    }
}
