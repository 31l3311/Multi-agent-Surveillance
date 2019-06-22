import java.awt.Point;
import java.util.ArrayList;

public class splitMap{
	private ArrayList<Point> startingPoints = new ArrayList<Point>();
	
	public splitMap(int amount, Point size) {
		if(amount == 1) {
//			startingPoints.add(new Point(0,0));
//			startingPoints.add(size);
			
			startingPoints.add(new Point((int)size.x/2,0));
			startingPoints.add(new Point(size.x, (int)size.y/2));
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
			
			startingPoints.add(new Point(0,size.y/2));
			startingPoints.add(new Point((int)(2*size.x/3), size.y));
			
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
			
			//first agent
			startingPoints.add(new Point((int)size.x/2,0));
			startingPoints.add(new Point(size.x, (int)size.y/2));
			
			//second agent
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
			
			//first agent
			startingPoints.add(new Point((int)2*size.x/5 ,0));
			startingPoints.add(new Point((int)4*size.x/5, (int)size.y/2));
			
			//second agent
			startingPoints.add(new Point((int)2*size.x/5,(int)size.y/2));
			startingPoints.add(new Point((int)4*size.x/5, size.y));
			
			//first agent
			startingPoints.add(new Point((int)4*size.x/5,0));
			startingPoints.add(size);
			
		}
		if(amount == 6) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/3), size.y/2));
			
			startingPoints.add(new Point(0,size.y/2));
			startingPoints.add(new Point((int)(size.x/3), size.y));
			
			startingPoints.add(new Point((int)(size.x/3),0));
			startingPoints.add(new Point((int)(2*size.x/3), size.y/2));
			
			startingPoints.add(new Point((int)(size.x/3),size.y/2));
			startingPoints.add(new Point((int)(2*size.x/3), size.y));
			
			startingPoints.add(new Point((int)(2*size.x/3),0));
			startingPoints.add(new Point(size.x, size.y/2));
			
			startingPoints.add(new Point((int)(2*size.x/3),size.y/2));
			startingPoints.add(size);
		}
		if(amount == 7) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/4));
			
			startingPoints.add(new Point(0,(int)size.y/4));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/2));
			
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)(size.x/2), (int)(3*size.y/4)));
			
			startingPoints.add(new Point(0,(int)(3*size.y/4)));
			startingPoints.add(new Point((int)(size.x/2), size.y));
			
			startingPoints.add(new Point((int)(size.x/2),0));
			startingPoints.add(new Point(size.x, (int)size.y/3));
			
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/3));
			startingPoints.add(new Point(size.x, (int)2*size.y/3));
			
			startingPoints.add(new Point((int)(size.x/2),(int)(2*size.y/3)));
			startingPoints.add(size);
		}
		
		if(amount == 8) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/4));
			
			startingPoints.add(new Point(0,(int)size.y/4));
			startingPoints.add(new Point((int)(size.x/2), (int)size.y/2));
			
			startingPoints.add(new Point(0,(int)size.y/2));
			startingPoints.add(new Point((int)(size.x/2), (int)(3*size.y/4)));
			
			startingPoints.add(new Point(0,(int)(3*size.y/4)));
			startingPoints.add(new Point((int)(size.x/2), size.y));
			
			startingPoints.add(new Point((int)(size.x/2),0));
			startingPoints.add(new Point((int)(size.x), (int)size.y/4));
			
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/4));
			startingPoints.add(new Point((int)(size.x), (int)size.y/2));
			
			startingPoints.add(new Point((int)(size.x/2),(int)size.y/2));
			startingPoints.add(new Point((int)(size.x), (int)(3*size.y/4)));
			
			startingPoints.add(new Point((int)(size.x/2),(int)(3*size.y/4)));
			startingPoints.add(new Point(size));
			
		}
		
		if(amount == 9) {
			//first agent
			startingPoints.add(new Point(0,0));
			startingPoints.add(new Point((int)(size.x/3), (int)size.y/3));
			
			startingPoints.add(new Point(0,(int)size.y/3));
			startingPoints.add(new Point((int)(size.x/3), (int)2*size.y/3));
			
			startingPoints.add(new Point(0,(int)2*size.y/3));
			startingPoints.add(new Point((int)(size.x/3), size.y));
			
			startingPoints.add(new Point((int)(size.x/3), 0) );
			startingPoints.add(new Point((int)(2*size.x/3), (int)(size.y/3)));
			
			startingPoints.add(new Point((int)(size.x/3),(int)(size.y/3)));
			startingPoints.add(new Point((int)(2*size.x/3),(int)(2*size.y/3)));
			
			startingPoints.add(new Point((int)(size.x/3),(int)2*size.y/3));
			startingPoints.add(new Point((int)(2*size.x/3), (int)size.y));
			
			startingPoints.add(new Point((int)(2*size.x/3),0));
			startingPoints.add(new Point((int)(size.x), (int)(size.y/3)));
			
			startingPoints.add(new Point((int)(2*size.x/3),(int)(size.y/3)));
			startingPoints.add(new Point((int)(size.x), (int)(2*size.y/3)));
			
			startingPoints.add(new Point((int)(2*size.x/3),(int)(2*size.y/3)));
			startingPoints.add(size);
		}
		

		System.out.println("SPLITTING THE MAP:");
		for(int i = 0; i<startingPoints.size(); i++) {
			System.out.println(startingPoints.get(i));
		}
	}
		public ArrayList<Point> startingPoints(){
			return startingPoints;
		}
		
	}
	
	
	
	
	
	

