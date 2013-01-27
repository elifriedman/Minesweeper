package game;



/**
 * The Location Class provides the method for locating and placing Tiles
 * 
 * @author (Eli Friedman) 
 * @author ()
 * @version (1.01.2)
 */
public class Location
{
    //The (x,y) coordinate of each tile—final because the coordinate is not going to change at all.
    private final int x;
    private final int y;
    
    /**
     * The compass direction for north.
     */
    public static final int NORTH = 0;
    /**
     * The compass direction for northeast.
     */
    public static final int NORTHEAST = 45;
    /**
     * The compass direction for east.
     */
    public static final int EAST = 90;
    /**
     * The compass direction for southeast.
     */
    public static final int SOUTHEAST = 135;
    /**
     * The compass direction for south.
     */
    public static final int SOUTH = 180;
    /**
     * The compass direction for southwest.
     */
    public static final int SOUTHWEST = 225;
    /**
     * The compass direction for west.
     */
    public static final int WEST = 270;
    /**
     * The compass direction for northwest.
     */
    public static final int NORTHWEST = 315;
    
    /**
     * The turn angle for turning 90 degrees to the left.
     */
    public static final int LEFT = -90;
    /**
     * The turn angle for turning 90 degrees to the right.
     */
    public static final int RIGHT = 90;
    /**
     * The turn angle for turning 45 degrees to the left.
     */
    public static final int HALF_LEFT = -45;
    /**
     * The turn angle for turning 45 degrees to the right.
     */
    public static final int HALF_RIGHT = 45;
    /**
     * The turn angle for turning a full circle.
     */
    public static final int FULL_CIRCLE = 360;
    /**
     * The turn angle for turning a half circle.
     */
    public static final int HALF_CIRCLE = 180;
    /**
     * The turn angle for making no turn.
     */
    public static final int AHEAD = 0;
    
    /**
     * A location containing an x and y coordinate, which refer to the rows and columns respectively.
     * The rows and columns start from 0
     * @param x the x coordinate, which refers to the row. The first row is 0.
     * @param y the y coordinate, which refers to the column. The first column is 0.
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        
    }
    
    /**
    * Returns the x coordinate of the Location
    * @return the x coordinate
    */
    public int getX() {
        return x;
    }
    /**
    * Returns the y coordinate of the Location
    * @return the y coordinate
    */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the adjacent location in any one of the eight compass directions.
     * @param direction the direction in which to find a neighbor location
     * @return the adjacent location in the direction that is closest to
     * <tt>direction</tt>
     */
    public Location getAdjacentLocation(int direction)
    {
        // reduce mod 360 and round to closest multiple of 45
         int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
        if (adjustedDirection < 0)
            adjustedDirection += FULL_CIRCLE;

        adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
        int dc = 0;
        int dr = 0;
        if (adjustedDirection == EAST)
            dc = 1;
        else if (adjustedDirection == SOUTHEAST)
        {
            dc = 1;
            dr = 1;
        }
        else if (adjustedDirection == SOUTH)
            dr = 1;
        else if (adjustedDirection == SOUTHWEST)
        {
            dc = -1;
            dr = 1;
        }
        else if (adjustedDirection == WEST)
            dc = -1;
        else if (adjustedDirection == NORTHWEST)
        {
            dc = -1;
            dr = -1;
        }
        else if (adjustedDirection == NORTH)
            dr = -1;
        else if (adjustedDirection == NORTHEAST)
        {
            dc = 1;
            dr = -1;
        }
        return new Location(getX() + dr, getY() + dc);
    }
    
    /**
     * Returns whether loc is adjacent to this Location. True if it is, False if not.
     * 
     * @return true if it is adjacent and false otherwise.
     */
    public boolean isAdjacent(Location loc) {
        int dc = Math.abs(x - loc.getX()); //Check whether the row is within one row of this location
        int dr = Math.abs(y - loc.getY());  //Check whether the column is within one column of this location
        
        if(dc > 1 || dr > 1)
            return false;
        else 
            return true;
    }
    
    /**
     * Gets the adjacent location in all of the eight compass directions.
     * @return A location array containing the eight surrounding locations.
     */
    public Location[] getSurroundingLocations() {
        Location[] around = new Location[8];
        int j = 0;
        for(int i = AHEAD; i < FULL_CIRCLE; i += HALF_RIGHT) {
            
            around[j++] = getAdjacentLocation(i);
        }
        return around;
    }

    //Use this method to see whether two locations are the same. It overrides the Object class equals method.
    @Override
    public boolean equals(Object o) {
        Location loc;
        if(o instanceof Location) {
            loc = (Location)o;
            if(loc.getX() == x && loc.getY()==y)
                return true;
        }
        return false;
    }

    //This method also overrides the Object class. I don't know what it does, though.
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
    //Returns a string version of the coordinates
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
