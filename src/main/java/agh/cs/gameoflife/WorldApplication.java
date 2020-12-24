package agh.cs.gameoflife;

import agh.cs.gameoflife.map.JungleWorldMap;
import agh.cs.gameoflife.simulation.Simulation;
import javafx.application.Application;
import javafx.stage.Stage;


public class WorldApplication extends Application {

    JungleWorldMap map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.map = new JungleWorldMap(100, 20, 20, 15, (float) 0.5, 30, 1, 3);
        Simulation simulation = new Simulation(primaryStage, this.map);
        simulation.startSimulation();
    }
}