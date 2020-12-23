package agh.cs.gameoflife.view;

import agh.cs.gameoflife.map.JungleWorldMap;
import agh.cs.gameoflife.map.interfaces.IAnimalPlantObserver;
import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Plant;
import agh.cs.gameoflife.model.Vector2d;
import agh.cs.gameoflife.simulation.Simulation;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.List;
import java.util.Map;


public class UserInterfaceImpl implements IUserInterfaceContract.View, IAnimalPlantObserver {

    private final Stage stage;
    private final Group root;

    public JungleWorldMap map;

    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 800;
    private static final double WINDOW_X = 800;
    private static final double BOARD_PADDING_X = 50;
    private static final double BOARD_PADDING_Y = 50;
    private static double BOARD_WIDTH;
    private static double JUNGLE_BOARD_WIDTH;
    private static double BOARD_HEIGHT;
    private static double JUNGLE_BOARD_HEIGHT;
    private static double SCALE;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(255, 255, 255);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(189, 189, 189);
    private static final Color JUNGLE_BACKGROUND_COLOR = Color.rgb(122, 120,122);
    private static final String WORLD = "Game of life";
    private Simulation simulation;

    public UserInterfaceImpl(Stage stage, JungleWorldMap map, Simulation simulation) {
        this.simulation = simulation;
        this.stage = stage;
        this.root = new Group();
        this.map = map;
        SCALE = Math.min((double) 600 / map.getWidth(), (double) 600 / map.getHeight());
        System.out.println("Scale: " + SCALE);
        BOARD_WIDTH = map.getWidth() * SCALE;
        BOARD_HEIGHT = map.getHeight() * SCALE;
        JUNGLE_BOARD_WIDTH = map.getJungleWidth() * SCALE;
        JUNGLE_BOARD_HEIGHT = map.getJungleHeight() * SCALE;
        initializeUserInterface();
    }

    private void initializeUserInterface(){
        this.root.setOnKeyPressed(new EventHandler<KeyEvent>() { //pausing game on space
            @Override
            public void handle(KeyEvent event) {
                if(event.getEventType() == KeyEvent.KEY_PRESSED) {
                    if (event.getCode() == KeyCode.SPACE) {
                        if (simulation.getGameState() == GameState.RUNNING) {
                            simulation.pauseSimulation();
                            dialogHandle("Game paused. Press space or click OK to start.");
                        }
                    }
                }
            }
        });
        drawBackground(root);
        drawTitle(root);
        drawWorldBoard(root);
        drawJungle(root);
        root.requestFocus();
        stage.show();
    }


    private void drawWorldBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING_X);
        boardBackground.setY(BOARD_PADDING_Y);
        boardBackground.setWidth(BOARD_WIDTH);
        boardBackground.setHeight(BOARD_HEIGHT);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        System.out.println("WorldBoard: " + BOARD_WIDTH + " " + BOARD_HEIGHT + " XY: : " + boardBackground.getX() + " " + boardBackground.getY());
        root.getChildren().addAll(boardBackground);
    }

    private void drawJungle(Group root) {
        Rectangle jungle = new Rectangle();
        jungle.setX(BOARD_PADDING_X + (BOARD_WIDTH - JUNGLE_BOARD_WIDTH)/2);
        jungle.setY(BOARD_PADDING_Y + (BOARD_HEIGHT - JUNGLE_BOARD_HEIGHT)/2);
        jungle.setWidth(JUNGLE_BOARD_WIDTH);
        jungle.setHeight(JUNGLE_BOARD_HEIGHT);
        jungle.setFill(JUNGLE_BACKGROUND_COLOR);
        System.out.println("Jungle: " + JUNGLE_BOARD_WIDTH + " " + JUNGLE_BOARD_HEIGHT + " XY: " + jungle.getX() + " " + jungle.getY());
        root.getChildren().addAll(jungle);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, WORLD);
        title.setFill(Color.BLACK);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        title.setX(BOARD_PADDING_X);
        title.setY(BOARD_HEIGHT + 2 * BOARD_PADDING_Y);
        root.getChildren().add(title);
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateBoard() {
        for (Map.Entry<Vector2d, Plant> entry : this.map.getPlantsFields().entrySet()) {
            entry.getValue().setX(entry.getValue().getPosition().getX() * SCALE + BOARD_PADDING_X);
            entry.getValue().setY(entry.getValue().getPosition().getY() * SCALE + BOARD_PADDING_Y);
        }
        for (Map.Entry<Vector2d, List<Animal>> entry : this.map.getAnimalsPositions().entrySet()) {
            entry.getValue().forEach(a -> {
                a.setX(a.getPosition().getX() * SCALE + BOARD_PADDING_X);
                a.setY(a.getPosition().getY() * SCALE + BOARD_PADDING_Y);
                a.setFill(a.color());
            });
        }
    }

    @Override
    public void dialogHandle(String message) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        dialog.showAndWait();
//        listener.onDialogCLick();
        if(dialog.getResult() == ButtonType.OK) this.simulation.startSimulation();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }


    @Override
    public void placeAnimalOnBoard(Animal animal){
        animal.setX(animal.getPosition().getX() * SCALE + BOARD_PADDING_X);
        animal.setY(animal.getPosition().getY() * SCALE + BOARD_PADDING_Y);
        animal.setHeight(SCALE);
        animal.setWidth(SCALE);
        animal.setFill(animal.color());
        this.root.getChildren().add(animal);
    }

    @Override
    public void placePlantOnBoard(Plant plant){
        plant.setX(plant.getPosition().getX() * SCALE + BOARD_PADDING_X);
        plant.setY(plant.getPosition().getY() * SCALE + BOARD_PADDING_Y);
        plant.setHeight(SCALE);
        plant.setWidth(SCALE);
        plant.setFill(plant.color());
        this.root.getChildren().add(plant);
    }

    public void removePlantFromBoard(Plant plant){
        this.root.getChildren().remove(plant);
    }

    public void removeAnimalFromBoard(Animal animal){
        this.root.getChildren().remove(animal);
    }

    @Override
    public void plantRemoved(Plant plant) {
        this.removePlantFromBoard(plant);
    }

    @Override
    public void animalDied(Animal animal) {
        this.removeAnimalFromBoard(animal);
    }

    @Override
    public void plantPlaced(Plant plant) {
        this.placePlantOnBoard(plant);
    }

    @Override
    public void animalPlaced(Animal animal) {
        this.placeAnimalOnBoard(animal);
    }

    public JungleWorldMap getMap() {
        return this.map;
    }
}
