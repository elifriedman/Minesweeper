/**
 * Write a description of class RollOverComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JFrame;
import java.awt.Dimension;

import gui.GridGUI;
public class Play
{
	private GridGUI grid;
	
	public Play(int width, int height, int mines) {
		grid = new GridGUI(width,height,mines);
		grid.setSize(16*width, 16*height);
	}
	public void play() {
		JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(grid);
        frame.setResizable(false);
		frame.pack(); 
        frame.setVisible(true);
	}
	
	public GridGUI getGrid() {
		return grid;
	}
    public static void main(String args[]) {
		Play p;
        if (args.length < 3) {
			p = new Play(30,16,99);
		} else {
			int xwidth = Integer.valueOf(args[0]);
			int xheight = Integer.valueOf(args[1]);
			int xmines = Integer.valueOf(args[2]);
			p = new Play(xwidth,xheight,xmines);
		}
		p.play();
		
    }
}