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
    private static final double WINDOW_X = 1200;
    private static final double BOARD_PADDING_X = 50;
    private static final double BOARD_PADDING_Y = 50;
    private static double STATISTICS_X;
    private static double STATISTICS_Y;
    private static double LEGEND_X;
    private static double LEGEND_Y;
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
    private Text statistics;

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
        STATISTICS_X = BOARD_PADDING_X * 2 + BOARD_WIDTH;
        STATISTICS_Y = BOARD_PADDING_Y * 2;
        LEGEND_X = STATISTICS_X;
        LEGEND_Y = STATISTICS_Y * 3;
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
        this.statistics = addStatistics(root);
        drawLegend(root);
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
        Text title = new Text(200, 500, WORLD);
        title.setFill(Color.BLACK);
        title.setFont(new Font(50));
        title.setX(BOARD_PADDING_X);
        title.setY(BOARD_HEIGHT + 2 * BOARD_PADDING_Y);
        root.getChildren().add(title);
    }

    private Text addStatistics(Group root){
        Text statistics = new Text(STATISTICS_X, STATISTICS_Y, "");
        statistics.setFill(Color.BLACK);
        statistics.setFont(new Font(25));
        statistics.setX(STATISTICS_X);
        statistics.setY(STATISTICS_Y);
        root.getChildren().add(statistics);
        return statistics;
    }

    public void updateStatistics(GameInfo gameInfo){
        this.statistics.setText(gameInfo.toString());
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

    @Override
    public void dialogHandle(String message) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        dialog.showAndWait();
        if(dialog.getResult() == ButtonType.OK) this.simulation.startSimulation();
    }

    public JungleWorldMap getMap() {
        return this.map;
    }

    private void drawLegend(Group root){
        drawLegendSteppe(root);
        drawLegendJungle(root);
        drawLegendAnimal(root);
        drawLegendPlant(root);
    }

    private void drawLegendSteppe(Group root){
        Rectangle backgroundLegend = new Rectangle();
        backgroundLegend.setX(LEGEND_X);
        backgroundLegend.setY(LEGEND_Y);
        backgroundLegend.setHeight(50);
        backgroundLegend.setWidth(50);
        backgroundLegend.setFill(BOARD_BACKGROUND_COLOR);
        Text backgroundLegendText = new Text();
        backgroundLegendText.setText(" - Steppe board");
        backgroundLegendText.setFont(new Font(22));
        backgroundLegendText.setFill(Color.BLACK);
        backgroundLegendText.setX(LEGEND_X + BOARD_PADDING_X);
        backgroundLegendText.setY(LEGEND_Y + backgroundLegend.getWidth()/2+5);
        root.getChildren().addAll(backgroundLegend, backgroundLegendText);
    }

    private void drawLegendJungle(Group root){
        Rectangle jungleLegend = new Rectangle();
        jungleLegend.setX(LEGEND_X);
        jungleLegend.setY(LEGEND_Y + BOARD_PADDING_Y + 10);
        jungleLegend.setHeight(50);
        jungleLegend.setWidth(50);
        jungleLegend.setFill(JUNGLE_BACKGROUND_COLOR);
        Text jungleLegendText = new Text();
        jungleLegendText.setText(" - Jungle board");
        jungleLegendText.setFont(new Font(22));
        jungleLegendText.setFill(Color.BLACK);
        jungleLegendText.setX(LEGEND_X + BOARD_PADDING_X);
        jungleLegendText.setY(LEGEND_Y + BOARD_PADDING_Y + jungleLegend.getWidth()/2 + 15);
        root.getChildren().addAll(jungleLegend, jungleLegendText);
    }

    private void drawLegendAnimal(Group root){
        Rectangle animalLegend = new Rectangle();
        animalLegend.setX(LEGEND_X);
        animalLegend.setY(LEGEND_Y + 2*(BOARD_PADDING_Y + 10));
        animalLegend.setHeight(50);
        animalLegend.setWidth(50);
        animalLegend.setFill(Color.rgb(14, 137, 204));
        Text animalLegendText = new Text();
        animalLegendText.setText(" - Animal default color. \n (When loses energy -> yellow.\n When gains, -> purple)");
        animalLegendText.setFont(new Font(22));
        animalLegendText.setFill(Color.BLACK);
        animalLegendText.setX(LEGEND_X + BOARD_PADDING_X);
        animalLegendText.setY(LEGEND_Y + 2*BOARD_PADDING_Y + animalLegend.getWidth()/2 + 25);
        root.getChildren().addAll(animalLegend, animalLegendText);
    }

    private void drawLegendPlant(Group root){
        Rectangle plantField = new Rectangle();
        plantField.setX(LEGEND_X);
        plantField.setY(LEGEND_Y + 3*(BOARD_PADDING_Y + 20));
        plantField.setHeight(50);
        plantField.setWidth(50);
        plantField.setFill(Color.GREEN);
        Text plantFieldText = new Text();
        plantFieldText.setText(" - Plant color");
        plantFieldText.setFont(new Font(22));
        plantFieldText.setFill(Color.BLACK);
        plantFieldText.setX(LEGEND_X + BOARD_PADDING_X);
        plantFieldText.setY(LEGEND_Y + 3*BOARD_PADDING_Y + plantField.getWidth()/2 + 70);
        root.getChildren().addAll(plantField, plantFieldText);
    }
}
