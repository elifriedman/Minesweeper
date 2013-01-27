package game;

/**
 * The Minesweeper Class provides the method for initializing the minesweepr game
 * 
 * @author (Eli Friedman) 
 * @author ()
 * @version (1.02.0)
 */


public class Minesweeper
{
    

    private Grid grid;
    
    /**
     * The default constructor initializes a 32 x 16 grid with 99 mines
     */
    public Minesweeper() {
        grid = new Grid();
    }
    
    /**
     * Constructs a grid of <code>width</code> width and <code>height</code> height with <code>mines</code> mines.
     * 
     * @param width the width of the Grid
     * @param height the height of the Grid
     * @param mines the number of mines in the grid
     */
    public Minesweeper(int height, int width, int mines) {
        grid = new Grid(height, width, mines);
    }
    
    /**
     * Returns the grid that Minesweeper uses.
     * @return Returns the grid.
     */
    public Grid getGrid() {
        return grid;
    }
    
    /**
     * Sets the location of all the mines and numbers. However, it makes sure that Location loc is 
     * not surrounded by any mines. Therefore, when the player clicks the first tile, that tile
     * cannot be a mine.
     * 
     * @param loc The location that the user clicks to start the game. This location is then made into a 
     * blank tile.
     */
    public void play(Location loc) {
        grid.initialize(loc);
    }
}
