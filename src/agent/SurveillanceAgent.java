package agent;

public class SurveillanceAgent extends Agent{
	
	private double baseSpeed = 1.4;
	private int angle;
	
	public SurveillanceAgent() {
		
	}
	
	public void turn(int newAngle) {
		double duration = (newAngle-angle)/180;
		angle = newAngle;
	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn() {
		// TODO Auto-generated method stub
		
	}
	
}