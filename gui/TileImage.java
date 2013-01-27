package gui;


/**
 * Enumeration class TileImage - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
import javax.swing.ImageIcon;

public enum TileImage
{
    ZERO ("openblank"),
    ONE ("open1"),
    TWO ("open2"),
    THREE ("open3"),
    FOUR ("open4"),
    FIVE ("open5"),
    SIX ("open6"),
    SEVEN ("open7"),
    EIGHT ("open8"),
    MINE ("bombrevealed"),
    MINE_DEATH ("bombdeath"), //For a mine that was clicked on, for example
    MINE_MISFLAG ("bombmisflagged"), //For a number that was flagged
    UNREVEALED ("blank"),
    FLAG ("bombflagged"),
    QUESTION_MARK ("bombquestion"),
    UNREVEALED_DEPRESSED ("blankdepressed"); //For a blank tile with a MouseDown event.
    
    private final ImageIcon image;
    private TileImage(String imgName) {
        String location = "Resources/";
        String extension = ".gif";
        image = new ImageIcon(location + imgName + extension);
    }
    
    public ImageIcon getImage() {
        return image;
    }
}
