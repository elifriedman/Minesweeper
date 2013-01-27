package game;



/**
 * The Bomb class represents the mines in the game Minesweeper
 * 
 * @author (Eli Friedman) 
 * @version (1.02.3)
 */
public class MineTile extends Tile
{
    private boolean deathCauser; //This becomes true if this bomb was the cause of death i.e. it was clicked on.
    /**
     * Creates a bomb tile.
     */
    public MineTile(Grid grid, Location loc)
    {
        super(grid, loc);
        deathCauser = false;
    }
    
    /**
     * This changes the state of the tile to revealed.
     * Since this is a mine, it's going to explode if it's revealed, so we need to
     * reveal ALL the mines to show that this mine has exploded.
     * 
     *     <p style="text-align:center"> ######## Note ######## </p>
     * The game makers would prefer that you not reveal any of the mines. The mines
     * are delicate creatures, who try to stay hidden, so that they can peacefully enjoy 
     * the quiet and calm beneath their tiles. Therefore, they should not be blamed at all
     * for exploding angrily if you disturb their peace and quiet by rudely clicking on their
     * tiles.<br/>
     * Thank you and have a nice day.
     */
    @Override
    void setRevealed() {
        super.setRevealed(); //reveal the tile
        deathCauser = true;
        getGrid().revealAllMines();
    }
    
    /**
     * This method, as opposed to setRevealed, only reveals this Tile. The method setRevealed() is 
     * called when a tile, with a mine beneath it is clicked. That reveals the rest of the mines. 
     * We don't want to loop through all the mines each time a mine is revealed, so instead, this 
     * method is called, since it only reveals one mine.
     */
    public void revealOnlyThis() {
            super.setRevealed();
    }
    /**
     * If this mine was accidentally clicked, we want it to take the blame.
     * 
     * @return deathCauser If true, we know who's fault it really is...
     */
    public boolean isDeathCauser() {
        return deathCauser;
    }
    @Override
    public String toString() {
        return "*";
    }
}
