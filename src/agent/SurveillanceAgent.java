package agent;

public class SurveillanceAgent extends Agent{
	
	private double baseSpeed = 1.4;
	private int angle;
	//visual range 6 m
	
	public SurveillanceAgent() {
		
	}
	
	@Override
	public void turn(int newAngle) {
		double duration = (newAngle-angle)/180;
		angle = newAngle;
	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}


	
}