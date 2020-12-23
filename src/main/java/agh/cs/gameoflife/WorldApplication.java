package agh.cs.gameoflife;

import agh.cs.gameoflife.simulation.Simulation;
import javafx.application.Application;
import javafx.stage.Stage;


public class WorldApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Simulation simulation = new Simulation(primaryStage);
        simulation.startSimulation();
    }
}
