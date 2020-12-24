package agh.cs.gameoflife;

import agh.cs.gameoflife.datareader.DataParser;
import agh.cs.gameoflife.map.JungleWorldMap;
import agh.cs.gameoflife.simulation.Simulation;
import javafx.application.Application;
import javafx.stage.Stage;


public class WorldApplication extends Application {

    private JungleWorldMap map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        DataParser inputData = DataParser.readDataFromJson("src/main/resources/data.json");
        this.map = new JungleWorldMap(
                inputData.getPlantsNumber(),
                inputData.getAnimalsNumber(),
                inputData.getWidth(),
                inputData.getHeight(),
                inputData.getJungleRatio(),
                inputData.getAnimalInitEnergy(),
                inputData.getAnimalMoveEnergy(),
                inputData.getPlantEnergy()
        );

        Simulation simulation = new Simulation(primaryStage, this.map);
        simulation.startSimulation();
    }
}