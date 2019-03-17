package main;

import java.awt.Point;

import agent.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import board.Menu;


public class Run {

	private Timeline GameTimer;
	//private SurveillanceAgent agent = new SurveillanceAgent(new Point(0,0));;
	private int time = 50;
	
	public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame mainFrame =
			new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(mainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();

	//GameTimer.stop();
	}
	
	
	public void update() {
		//agent.walk(time);
		Menu.agent1.walk(time);
	}

}
