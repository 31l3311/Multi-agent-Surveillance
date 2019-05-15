import java.awt.Point;
import java.util.ArrayList;

public class IntruderBot extends Bot{

    public Agent intruder;
    private boolean surveillance;
    private int[][] map;
    private int counter = 0;
    private ArrayList<Bot> intruders;

    public IntruderBot(boolean surveillance, Point position, int time, Point size){
        this.surveillance = surveillance;
        map = new int[size.x][size.y];
        intruder = new Intruder(position, time, size);
    }

    public void setIntruders(ArrayList intruders) {
        this.intruders = intruders;
    }

    public ArrayList<Bot> getIntruders(){
        return intruders;
    }

    public ArrayList updateIntruderBot() {
        if(intruder.myDirection.size()>2) {
            if(map[intruder.myDirection.get(2).x][intruder.myDirection.get(2).y] != 0) {
                System.out.println("I should turn around now (INTRUDER)!");
                return intruder.update(true,(intruder.getAngle() + 160));
            }}
        if(counter >= 100 || avoidObjects()) {
            counter = 0;
            return intruder.update(changeAngle());
        }
        counter++;
        return intruder.update();
    }

    public double changeAngle() {
        double angle = (360*Math.random());
        //System.out.println("New angle = " + angle);
        return angle;
    }

    public void updateMap(Point loc, int i) {
        map[loc.x][loc.y] = i;
        if(i == 1) {
            // implement method to avoid tree or shit
            //System.out.println("I see a tree!");
        }
    }

    public void updateAgent() {
        intruder.update();
        intruder.hear(getIntruders());
    }

    public boolean avoidObjects() {
        for( int i = 0; i < intruder.myDirection.size(); i++) {
            if (map[intruder.myDirection.get(i).x][intruder.myDirection.get(i).y] != 0) {
                System.out.println("I see something at : " + intruder.myDirection.get(i).x + ", " + intruder.myDirection.get(i).y);
                return true;
            }
        }
        return false;
    }
}