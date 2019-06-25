package board;

import java.awt.Point;
import java.util.ArrayList;

public class splitMap{
	private ArrayList<Point> startingPoints = new ArrayList<Point>();

	/**
	 * Splits the map into as many sections as there are surveillance agents
	 * @param amount is the amount of surveillance agents
	 * @param size is the size of the board
	 */
	public splitMap(int amount, Point size) {
		if(amount == 1) {
			startingPoints.add(new Point(0,0));
			startingPoints.add(size);
		}
		if(amount == 2) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)size.x/2, (int)size.y));
			
			//second agent
			startingPoints.add(new Point((int)size.x/2,0));
			startingPoints.add(size);
		}
		if(amount == 3) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(2*size.x/3), size.y/2));

			//second agent
			startingPoints.add(new Point(0,size.y/2));
			startingPoints.add(new Point((int)(2*size.x/3), size.y));

			//third agent
			startingPoints.add(new Point((int)(2*size.x/3),0));
			startingPoints.add(size);
		}
		if(amount == 4) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)size.x/2, (int)size.y/2));
			
			//second agent
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)size.x/2, size.y));
			
			//third agent
			startingPoints.add(new Point((int)size.x/2,0));
			startingPoints.add(new Point(size.x, (int)size.y/2));
			
			//fourth agent
			startingPoints.add(new Point((int)size.x/2,(int)size.y/2));
			startingPoints.add(size);
			
		}
		if(amount == 5) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)2*size.x/5, (int)size.y/2));
			
			//second agent
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)2*size.x/5, size.y));
			
			//third agent
			startingPoints.add(new Point((int)2*size.x/5 ,0));
			startingPoints.add(new Point((int)4*size.x/5, (int)size.y/2));
			
			//fourth agent
			startingPoints.add(new Point((int)2*size.x/5,(int)size.y/2));
			startingPoints.add(new Point((int)4*size.x/5, size.y));
			
			//fifth agent
			startingPoints.add(new Point((int)4*size.x/5,0));
			startingPoints.add(size);
			
		}
		if(amount == 6) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/3), size.y/2));

			//second agent
			startingPoints.add(new Point(0,size.y/2));
			startingPoints.add(new Point((int)(size.x/3), size.y));

			//third agent
			startingPoints.add(new Point((int)(size.x/3),0));
			startingPoints.add(new Point((int)(2*size.x/3), size.y/2));

			//fourth agent
			startingPoints.add(new Point((int)(size.x/3),size.y/2));
			startingPoints.add(new Point((int)(2*size.x/3), size.y));

			//fifth agent
			startingPoints.add(new Point((int)(2*size.x/3),0));
			startingPoints.add(new Point(size.x, size.y/2));

			//sixth agent
			startingPoints.add(new Point((int)(2*size.x/3),size.y/2));
			startingPoints.add(size);
		}
		if(amount == 7) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/4));

			//second agent
			startingPoints.add(new Point(0,(int)size.y/4));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/2));

			//third agent
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)(size.x/2), (int)(3*size.y/4)));

			//fourth agent
			startingPoints.add(new Point(0,(int)(3*size.y/4)));
			startingPoints.add(new Point((int)(size.x/2), size.y));

			//fifth agent
			startingPoints.add(new Point((int)(size.x/2),0));
			startingPoints.add(new Point(size.x, (int)size.y/3));

			//sixth agent
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/3));
			startingPoints.add(new Point(size.x, (int)2*size.y/3));

			//seventh agent
			startingPoints.add(new Point((int)(size.x/2),(int)(2*size.y/3)));
			startingPoints.add(size);
		}
		
		if(amount == 8) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/4));

			//second agent
			startingPoints.add(new Point(0,(int)size.y/4));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/2));

			//third agent
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)(size.x/2), (int)(3*size.y/4)));

			//fourth agent
			startingPoints.add(new Point(0,(int)(3*size.y/4)));
			startingPoints.add(new Point((int)(size.x/2), size.y));

			//fifth agent
			startingPoints.add(new Point((int)(size.x/2),0));
			startingPoints.add(new Point((int)(size.x), (int)size.y/4));

			//sixth agent
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/4));
			startingPoints.add(new Point((int)(size.x), (int)size.y/2));

			//seventh agent
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/2));
			startingPoints.add(new Point((int)(size.x), (int)(3*size.y/4)));

			//eighth agent
			startingPoints.add(new Point((int)(size.x/2),(int)(3*size.y/4)));
			startingPoints.add(new Point(size));
			
		}
		
		if(amount == 9) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/3), (int)size.y/3));

			//second agent
			startingPoints.add(new Point(0,(int)size.y/3));
			startingPoints.add(new Point((int)(size.x/3), (int)2*size.y/3));

			//third agent
			startingPoints.add(new Point(0,(int)2*size.y/3));
			startingPoints.add(new Point((int)(size.x/3), size.y));

			//fourth agent
			startingPoints.add(new Point((int)(size.x/3), 0) );
			startingPoints.add(new Point((int)(2*size.x/3), (int)(size.y/3)));

			//fifth agent
			startingPoints.add(new Point((int)(size.x/3),(int)(size.y/3)));
			startingPoints.add(new Point((int)(2*size.x/3),(int)(2*size.y/3)));

			//sixth agent
			startingPoints.add(new Point((int)(size.x/3),(int)2*size.y/3));
			startingPoints.add(new Point((int)(2*size.x/3), (int)size.y));

			//seventh agent
			startingPoints.add(new Point((int)(2*size.x/3),0));
			startingPoints.add(new Point((int)(size.x), (int)(size.y/3)));

			//eighth agent
			startingPoints.add(new Point((int)(2*size.x/3),(int)(size.y/3)));
			startingPoints.add(new Point((int)(size.x), (int)(2*size.y/3)));

			//ninth agent
			startingPoints.add(new Point((int)(2*size.x/3),(int)(2*size.y/3)));
			startingPoints.add(size);
		}

	}

		/**
		 * Get the starting points of the sections of the board (top left and bottom right corners)
		 * @return an array list of starting points
	 	*/
		public ArrayList<Point> startingPoints(){
			return startingPoints;
		}
		
	}
	
	
	
	
	
	

