package agh.cs.gameoflife.simulation;

import agh.cs.gameoflife.map.JungleWorldMap;
import agh.cs.gameoflife.map.interfaces.IAnimalPlantObserver;
import agh.cs.gameoflife.view.*;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;


public class Simulation implements ISimulation {

    private IUserInterfaceContract.View uiImpl;
    private LocalStorageImpl localStorageImpl;
    private JungleWorldMap map;
    private AnimationTimer timer;
    private GameState gameState;
    private GameInfo gameInfo;
    private int lastAnimalIDHolder;
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
        this.lastAnimalIDHolder = 0;

        //world init
        this.setGameState(GameState.RUNNING);
        this.map.initPlants(this.map.getPlantsNumber());
        this.lastAnimalIDHolder = this.map.initAnimals(this.map.getAnimalsNumber());
        this.day = 0;

        //game info init
        this.gameInfo = new GameInfo(this.map.getAnimals(), this.map.getAnimalsDead(), this.map.getPlantsFields(), day);

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
        t += 0.3; //slow down
        if( t >= 5 ){
            System.out.println("---------------------------------------DAY: " + this.day + " --------------------------------------------------");
            this.day++;
            this.lastAnimalIDHolder++;
            this.map.handleDeadAnimals();
            this.map.moveAnimals();
            this.map.animalsEatGrass();
            this.map.reproduce(this.lastAnimalIDHolder);
            this.map.placePlantRandom();
            this.map.placePlantIntoJungle();
            this.uiImpl.updateBoard();
            this.updateGameInfo();
            this.uiImpl.updateStatistics(this.gameInfo);
            t = 0;
        }
    }

    public void updateGameInfo(){
        this.gameInfo.setAnimalsAlive(this.map.getAnimals());
        this.gameInfo.setAnimalsDead(this.map.getAnimalsDead());
        this.gameInfo.setCurrentPlantsNumber(this.map.getPlantsFields());
        this.gameInfo.setAverageAnimalsEnergy(this.map.getAnimals());
        this.gameInfo.setDayNumber(this.day);
    }

    public LocalStorageImpl getLocalStorageImpl() {
        return localStorageImpl;
    }
}
