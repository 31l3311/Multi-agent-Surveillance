

import java.awt.Point;
//import agent.SurveillanceAgent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Run {
	
	private Timeline GameTimer;
	private SurveillanceAgent agent = new SurveillanceAgent(new Point(0,0));;
	private int time = 50;
    private int noise = 100; // once a minute

    public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame mainFrame =
			new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(mainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();
	
	//GameTimer.stop();
	}

    public void startNoise() {
        GameTimer = new Timeline();
        KeyFrame mainFrame = new KeyFrame(Duration.millis(noise), e -> update());
        GameTimer.getKeyFrames().add(mainFrame);
        GameTimer.setCycleCount(Timeline.INDEFINITE);
        GameTimer.play();
        //GameTimer.stop();
        double randX = Math.random()*51;
        double randY = Math.random()*51;

        Circle noiseMaker = new Circle(randX, randY, 25);
        noiseMaker.setStroke(Color.LIGHTBLUE);
        Square s= new Square(randX, randY, 25, 25);
        s.getChildren().addAll(noiseMaker);
    }
	
	public void update() {
		agent.walk(time);
		
	}
}
