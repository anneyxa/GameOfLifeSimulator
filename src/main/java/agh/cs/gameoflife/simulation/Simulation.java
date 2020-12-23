package agh.cs.gameoflife.simulation;

import agh.cs.gameoflife.map.JungleWorldMap;
import agh.cs.gameoflife.map.interfaces.IAnimalPlantObserver;
import agh.cs.gameoflife.view.*;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;


public class Simulation implements IEngine {

    private IUserInterfaceContract.View uiImpl;
    private LocalStorageImpl localStorageImpl;
    private JungleWorldMap map;
    private AnimationTimer timer;
    private GameState gameState;
    private double t = 0;
    int day;

    public Simulation(Stage primaryStage, JungleWorldMap map){
        //logic settings
        this.localStorageImpl = new LocalStorageImpl();
        this.map = map;
        this.uiImpl = new UserInterfaceImpl(primaryStage, this.map, this);
        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(this.uiImpl);
        this.uiImpl.setListener(uiLogic);
        this.map.addObserver((IAnimalPlantObserver) this.uiImpl);
        //world init
        this.setGameState(GameState.RUNNING);
        this.map.initAnimals(this.map.getAnimalsNumber());
        this.map.initPlants(this.map.getPlantsNumber());
        this.day = 0;
        System.out.println("___________________________________RUN_______________________________________");
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                run();
            }
        };
    };

    public void startSimulation(){
        this.setGameState(GameState.RUNNING);
        this.timer.start();
    }

    public void pauseSimulation(){
        this.setGameState(GameState.PAUSED);
        this.timer.stop();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void run() {
        t += 0.1; //slow down
        if( t >= 5 ){
            this.day++;
            this.map.handleDeadAnimals();
            this.map.moveAnimals();
            this.map.animalsEatGrass();
            //copulation
            this.map.placePlantRandom();
            this.map.placePlantIntoJungle();
            this.uiImpl.updateBoard();

            System.out.println("---------------------------------------DAY: " + this.day + " --------------------------------------------------");
            t = 0;
        }
    }

    public LocalStorageImpl getLocalStorageImpl() {
        return localStorageImpl;
    }
}
