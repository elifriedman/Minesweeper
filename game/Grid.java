package game;

/**
 * The Grid Class organizes all of the tiles into a grid. It provides methods for finding and accessing tiles
 * as well as locating adjacent tiles.
 * 
 * @author (Eli Friedman) 
 * @version (1.02.6)
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
public class Grid
{
    private int width; //The width of the Minesweeper Grid
    private int height; //The height of the Minesweeper Grid
    private int mines; //The number of Mines in the Grid
    private int flagCount; //The number of flagged Tiles.
    private Tile[][] tileGrid; //An array to hold all the Tiles in the Grid
    private ArrayList<MineTile> mineList;
    private ArrayList<NumberTile> revealedNums; //An array of all revealed NumberTiles.
    
    /**
     * The default constructor constructs a 30 x 16 Grid with 99 mines.
     */
    public Grid() {
        width = 30; //Columns
        height = 16; //Rows
        mines = 99;
        flagCount = 0;
        mineList = new ArrayList<MineTile>();
        revealedNums = new ArrayList<NumberTile>();
        fillTileGrid();
    }

    /**
     * Constructs a grid of <code>width</code> width and <code>height</code> height 
     * with <code>mines</code> mines.
     * 
     * @param width the width of the Grid
     * @param height the height of the Grid
     * @param mines the number of mines in the grid
     */
    public Grid(int width, int height, int mines) {
        if (width <= 0)
            throw new IllegalArgumentException("width <= 0");
        if (height <= 0)
            throw new IllegalArgumentException("height <= 0");
        if (mines <= 0)
            throw new IllegalArgumentException("mines <= 0");
        if (mines >= width * height - 1)
            throw new IllegalArgumentException("too many mines");
        this.width = width;
        this.height = height;
        this.mines = mines;
        flagCount = 0;
        mineList = new ArrayList<MineTile>();
        revealedNums = new ArrayList<NumberTile>();
        fillTileGrid();
        
    }

    /**
     * Fills the grid with width * height number of tiles.
     */
    private void fillTileGrid() {
        tileGrid = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tileGrid[i][j] = new Tile(this, new Location(i,j));
            }
        }
    }
    
    /**
     * When one mine has been clicked, we want to reveal all the mines.
     */
    public void revealAllMines() {
        for(Iterator<MineTile> it = mineList.iterator(); it.hasNext(); ) {
            MineTile mt = it.next();
            mt.revealOnlyThis();
        }
    }
    
    /**
     * Adds a NumberTile to the list of revealed NumberTiles.
     * 
     * @param nt The revealed NumberTile to be added.
     */
    public void addRevealedNum(NumberTile nt) {
            revealedNums.add(nt);
            //System.out.println(revealedNums.size() + ": " + revealedNums.get(revealedNums.size()-1));
    }
    
    /**
     * Returns the width of the grid.
     * 
     * @return the width of the Grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the grid
     * 
     * @return the <code>height</code> of the Grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the number of mines in the grid
     * 
     * @return the number of <code>mines</code> in the Grid
     */
    public int getMineCount() {
        return mines;
    }
    
    /**
     * Gets the tile at location tileLoc
     * 
     * @param tileLoc the tile at location tileLoc
     * @return The tile at the coordinates (x,y)
     */
    public Tile getTile(Location tileLoc) {
        if(testValidLocation(tileLoc)) 
        {
            return tileGrid[ tileLoc.getX() ][ tileLoc.getY() ];
        } else 
        {
            throw new IllegalArgumentException("Location " + tileLoc
                    + " is not valid");
        }
    }
    
    /**
     * Gets the tile at point (x,y)
     * 
     * @param x The x coordinate of the Tile's location.
     * @param y The y coordinate of the Tile's locaiton.
     * @return The tile at the coordinates (x,y)
     */
    public Tile getTile(int x, int y) {
        if(testValidLocation(x,y)) 
        {
            return tileGrid[ x ][ y ];
        } else 
        {
            throw new IllegalArgumentException("Location (" + x + "," + y
                    + ") is not valid");
        }
    }
    
    public ArrayList<NumberTile> getRevealedNums() {
        return revealedNums;
    }
    /**
     * Returns a valid location on the grid. i.e. the location is on the grid.
     * 
     * @param tileLoc the location to be tested for validity
     * @return returns true if the location lies on the grid, false if it does not.
     */
    public boolean testValidLocation(Location tileLoc) {
        if(0 <= tileLoc.getX()  &&  tileLoc.getX() < width
            && 0 <= tileLoc.getY() && tileLoc.getY() < height) 
        {
            return true;
        }
        return false;
    }
    
    /**
     * Returns a valid location on the grid. i.e. the location is on the grid.
     * 
     * @param x The x coordinate of the location to be tested for validity.
     * @param y The y coordinate of the location to be tested for validity.
     * @return returns true if the location lies on the grid, false if it does not.
     */
    public boolean testValidLocation(int x, int y) {
        if(0 <= x  &&  x < width
            && 0 <= y && y < height) 
        {
            return true;
        }
        return false;
    }

    /**
     * Sets the mines. However, it makes sure that Location loc is not surrounded by any mines. This makes
     * the game startable.
     * 
     * @param loc The location that the user clicks to start the game. This location is then made into a blank
     * tile.
     */
    private void setMines(Location loc) {
        //obviously 5 isn't the probability. it means 1/5 but since i want to use nextInt in 
        //setMines, this needs to be an integer.
        int mineProbability = 5; 
        int numMines = 0;
        
        mineTheField: //Just need something to break out of if we reach the right number of mines
        while(numMines < mines) { //Make sure there are the right # of mines in the minefield

            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {

                    Random ranNum = new Random();
                    int chosenInt = ranNum.nextInt(mineProbability);
					
					Location location = new Location(i,j);
                    if (chosenInt == 0 && !loc.equals(location)) {
                       

                        //Make sure that the mine to be placed is not around the tile that's first clicked in
                        //the game
                        if(!(getTile(location) instanceof MineTile)) { 
                            MineTile mine = new MineTile(this, location);
                            tileGrid[i][j] = mine;
                            mineList.add(mine);
                            numMines++;
                        }
                    }
                    if(numMines >= mines)
                        break mineTheField;
                }
            } 
        }
    }

    /**
     * Sets all tiles that are not mines to numbers.
     * @precondition The mines must be set before this method is called, or else all the tiles
     * will be number tiles with the number 0. And we don't want that happening, now, do we?
     */
    private void setNumbers(Location loc) {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {

                Location location = new Location(i,j);
                if(!(getTile(location) instanceof MineTile)) {
                    NumberTile num = new NumberTile(this, location);
                    tileGrid[i][j] = num;
                }
            }
        }
		getTile(loc).click();
    }
    
    /**
     * The initialization method. After the unrevealed tiles have been set up, the Tiles have
     * not yet determined whether they will be numbers or mines. That's only decided when the user
     * clicks a location, because that location will be a blank tile with no mines around it. 
     * The game can only start when the user clicks.
     * 
     * @param loc The location that the user clicks in order to start the game.
     */
    public void initialize(Location loc) {
        setMines(loc);
        setNumbers(loc);
    }
}
