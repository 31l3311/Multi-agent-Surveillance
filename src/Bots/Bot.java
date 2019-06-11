import java.awt.Point;
import java.util.ArrayList;




public abstract class Bot{
	
	
	private int[][] map;
	private ArrayList bots;
	
	public abstract void updateMap(Point loc, int i);
	public abstract ArrayList update();
	public abstract Agent getAgent();
	
	
	
	
}