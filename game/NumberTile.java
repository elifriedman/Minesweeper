package game;



/**
 * This class represents all the non-Mine Tiles. 
 * As soon as a non-Mine tile is clicked on, a Number Tile appears with the number of surrounding Mines
 * 
 * @author (Eli Friedman) 
 * @version (1.02.3)
 */
public class NumberTile extends Tile
{
    /**
     * If no mines surround a Tile, the Tile is BLANK
     */
    public static final int BLANK = 0; 
    /**
     * If one mine is next to a Tile
     */
    public static final int ONE = 1;
    /**
     * If two mines are next to a Tile
     */
    public static final int TWO = 2; 
    /**
     * If three mines are next to a Tile
     */
    public static final int THREE = 3; 
    /**
     * If four mines are next to a Tile
     */
    public static final int FOUR = 4; 
    /**
     * If five mines are next to a Tile
     */
    public static final int FIVE = 5; 
    /**
     * If six mines are next to a Tile
     */
    public static final int SIX = 6; 
    /**
     * If seven mines are next to a Tile
     */
    public static final int SEVEN = 7; 
    /**
     * If eight mines are next to a Tile
     */
    public static final int EIGHT = 8; 

    private int mineCount; //The mineCount of mines surrounding this Tile
    
    /**
     * Constructs a Tile in a given Location with the number of mines that surround it
     * @param grid the grid that contains the number tile
     * @param loc the Location of this Tile
     */
    public NumberTile(Grid grid, Location loc) {
        super(grid, loc);
        mineCount = countSurroundingMines();
    }
    
    /**
     * Counts the number of mines around the number tile. It's used in the construction of the game.
     * @return The number of mines surrounding this tile.
     */
    private int countSurroundingMines() {
       int numMines = 0;
       for(Tile tile : getNeighboringTiles()) {
           if(tile instanceof MineTile) 
               numMines++;
       }
       return numMines;
    }
    
    /**
     * Returns the number of this tile.
     * @return Returns the number of this tile. The number is determined by the number of 
     * mines surrounding this one.
     */
    public int getMineCount() {
        return mineCount;
    }
    
    /**
     * Counts the number of tiles around this one that have been flagged.
     * @return The number of flagged tiles around this one.
     */
    public int countSurroundingFlags() {
       int flagCount = 0;
       for(Tile tile : getUnrevealedNeighbors()) {
           if(!tile.isRevealed() && tile.isFlagged()) 
               flagCount++;
       }
       return flagCount;
    }
    
    /**
     * This changes the state of the tile to revealed.
     * If the tile's number is BLANK, it will also reveal all surrounding tiles.
     * 
     */
    @Override
    void setRevealed() {
        super.setRevealed(); //reveal the tile
		if (!getGrid().getRevealedNums().contains(this)) {
			getGrid().addRevealedNum(this); //Adds this NumberTile to the "Revealed" array in Grid
		}
        if(mineCount == BLANK) { //if the tile is blank, reveal all surrounding tiles
            for(Tile tile : getUnrevealedNeighbors()) {
                tile.setRevealed();
            }
        }
    }
    
    @Override
    public String toString() {
        return "" + mineCount;
    }
}

