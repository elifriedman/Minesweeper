package game;

/**
 * The Tile Class provides the method for constructing and handling tiles
 * 
 * @author (Eli Friedman) 
 * @author ()
 * @version (1.02.4)
 */
import java.util.ArrayList;
public class Tile
{

    //These variable are used with the status variable to set its status.
    /**
     * The Tile has been revealed.
     */
    public static final int REVEALED = -1;
    /**
     * The Tile is unrevealed and clear of all markings.
     */
    public static final int CLEAR = 0;
    /**
     * The Tile is marked with a flag.
     */
    public static final int FLAG = 1;
    /**
     * The Tile is marked with a question mark.
     */
    public static final int QUESTION_MARK = 2;

    //indicates whether the tile has been revealed, flagged or set with a "?":
    private int status;

    //The Location in the Grid of this Tile
    private Location loc;

    private Grid field;

    /**
     * Constructs an unrevealed, unflagged, and unquestion marked tile in the given location.
     * @param loc The location in which to construct the tile.
     */
    public Tile(Grid field, Location loc) {
        status = CLEAR;
        this.field = field;
        this.loc = loc;
    }

    /**
     * This changes the state of the tile to revealed.
     */
    void setRevealed() {
        if(!isFlagged())
            status = REVEALED;
    }

    /**
     * This sets the mark on an unrevealed tile.
     * 
     * Tile.CLEAR sets the tile to have no marking,
     * Tile.FLAG sets the tile to have a flag,
     * Tile.QUESTION_MARK sets the tile to have a "?".
     * 
     * @param flagged true if the tile is flagged, false otherwise. Can only be set to
     * true if the tile has not yet been revealed.
     * @return whether the tile is flagged or not
     */
    public void setMarker(int mark) {
        if(!isRevealed())
            switch(mark) { //Make sure that mark is a legal integer.
                case CLEAR:
                case FLAG:
                case QUESTION_MARK:
                    status = mark;
                    break;
        }
    }

    /**
     * Tells whether the tile has been revealed.
     * @return The reveal state of the tile. True if revealed, false if not.
     */
    public boolean isRevealed() {
        return status == REVEALED;
    }

    /**
     * Tells whether the tile has been flagged.
     * @return The reveal state of the tile. True if flagged, false if not.
     */
    public boolean isFlagged() {
        return status == FLAG;
    }

    /**
     * Tells whether the tile has been marked with a question mark.
     * @return The reveal state of the tile. True if marked with a question mark, false if not.
     */
    public boolean isQMarked() {
        return status == QUESTION_MARK;
    }

    /**
     * Tells whether the tile is clear of all marks.
     * @return The reveal state of the tile. True if the tile is umarked.
     */
    public boolean isClear() {
        return status == CLEAR;
    }
    /**
     * Returns the status of the tile.
     * 
     * @return The possible values for the status are:
     * <ul>
     *  <li>Tile.REVEALED -- when the tile is revealed</li>
     *  <li>Tile.CLEAR -- when the tile is unrevealed, but with no markings</li>
     *  <li>Tile.FLAG -- when the tile is unrevealed and has a flag</li>
     *  <li>Tile.QUESTIONMARK -- when the tile is unrevealed and has a question mark</li>
     * </ul>
     */
    public int getMarker() {
        return status;
    }
    /**
     * Returns the location of the tile
     * @return the location of the tile.
     */
    public Location getLocation() {
        return loc;
    }

    /**
     * Returns The grid in which the tile lies.
     * @return The grid in which the tile lies. 
     */
    public Grid getGrid() {
        return field;
    }

    /**
     * Gets all the surrounding tiles. Using this is better than finding the surrounding locations using
     * getLocation.getSurroundingLocations() because this only returns tiles that are within the grid,
     * whereas getNeighboringTiles() returns all the locations, including nonvalid ones.
     * 
     * @return An ArrayList of the valid tiles surrounding this one.
     */
    public ArrayList<Tile> getNeighboringTiles() {
        ArrayList<Tile> around = new ArrayList<Tile>();

        for(Location place : getLocation().getSurroundingLocations()) {
            if(field.testValidLocation(place))
                around.add(field.getTile(place));
        }
        return around;
    }

    /**
     * Gets all the tiles around <code>tile</code> that have yet to be revealed.
     * 
     * @param tile The tile for which you want the unrevealed neighbors.
     * @return An ArrayList of the unrevealed tiles surrounding this one.
     */
    public ArrayList<Tile> getUnrevealedNeighbors() {
        ArrayList<Tile> unrevealed = new ArrayList<Tile>();//8 is the maximum number of unrevealed neighbors

        for(Tile t : getNeighboringTiles()) {
            if(!t.isRevealed())
                unrevealed.add(t);
        }
        return unrevealed;
    }
    
    /**
     * When you click on a tile, its state is set to revealed. If it's a blank number tile, 
     * all the surrounding spaces will be revealed in addition. If it's a mine tile, all the
     * mines will blow up. We told you not to click on those mines...
     * <br />
     * <br />
     * If the tile is a revealed NumberTile, then we check to see how many flags surround it. 
     * If the correct number of flags surround it (the NumberTile's number == the number of flags),
     * then the rest of the surrounding tiles are revealed.
     * 
     * @param tile The Tile that is to be clicked
     */
    public void click() {
        if(!(isRevealed() || isFlagged())) //Can't reveal it if it's been revealed, or flagged.
            setRevealed();
    }
    
    /**
     * When you right click on a Tile that has not yet been revealed, the image will change.
     * That is represented by the <code>status</code> variable in the Tile class.
     * rightClick changes the status based on the current status based on Minesweeper convention.
     * 
     * @param tile The Tile that is to be right clicked
     */
    public void rightClick() {
        if(!isRevealed()) {
            setMarker((status+1)%3); //Advance the marker by 1. i.e. if it's clear, set a flag; if flagged, set "?"
        }
    }
    
    @Override
    public String toString() {
        return "Tile at " + loc;
    }
}
