package agh.cs.gameoflife.model;

import agh.cs.gameoflife.map.interfaces.IMapElement;
import agh.cs.gameoflife.map.interfaces.IPositionChangeObserver;
import agh.cs.gameoflife.map.interfaces.IWorldMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static agh.cs.gameoflife.model.GenesManager.genesNumber;

public class Animal extends Rectangle implements IMapElement {
    private int animalID;
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers;
    private int energy;
    private int moveEnergy;
    private int defaultEnergy;
    private List<Integer> genes;

    public Animal(IWorldMap map, Vector2d initialPosition, int animalID){
        this.animalID = animalID;
        this.direction = MapDirection.getRandomDirection();
        this.position = initialPosition;
        this.map = map;
        this.observers = new ArrayList<>();
        this.genes = GenesManager.initGenes();
        this.energy = 20;
        this.defaultEnergy = 20;
        this.moveEnergy = 1;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int animalID, int energy, int moveEnergy){
        this(map, initialPosition, animalID);
        this.energy = energy;
        this.defaultEnergy = energy;
        this.moveEnergy = moveEnergy;
    }

    public Animal(Animal parent1, Animal parent2, int animalID){
        this.map = parent1.getMap();
        this.energy = parent1.getEnergy()/4 + parent2.getEnergy()/4;
        this.moveEnergy = parent1.moveEnergy;
        this.animalID = animalID;
        this.observers = new ArrayList<>();
        this.direction = MapDirection.getRandomDirection();
        parent1.decreaseEnergy(parent1.getEnergy()/4);
        parent2.decreaseEnergy(parent2.getEnergy()/4);
        int divisor1 = ThreadLocalRandom.current().nextInt(0, genesNumber - 1);
        int divisor2 = ThreadLocalRandom.current().nextInt(divisor1,genesNumber + 1);
        this.genes = GenesManager.createChildGenes(parent1.getGenes(), parent2.getGenes(), divisor1, divisor2);
        this.position = parent1.getPosition();
        int movesNumber = MapDirection.values().length;
        System.out.println("MOVES NUMBER: " + movesNumber);
        List<MapDirection> possibleDirections = new ArrayList<>(Arrays.asList((MapDirection.values())));
        Collections.shuffle(possibleDirections);
        boolean flag = false;
        for(MapDirection direction : possibleDirections) {
            Vector2d futurePosition = this.countPWBCase(parent1.getPosition().add(Objects.requireNonNull(direction.toUnitVector())));
            if(!parent1.getMap().isOccupied(futurePosition)){
                this.position = futurePosition;
                flag = true;
                break;
            }
        }
        if(!flag){
            Collections.shuffle(possibleDirections);
            this.position = this.countPWBCase(parent1.getPosition().add(Objects.requireNonNull(possibleDirections.get(0).toUnitVector())));
        }
    }

    public IWorldMap getMap(){
        return map;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public void increaseEnergy(int value) {
        this.energy = this.energy + value;
    }

    public void decreaseEnergy(int value){
        this.energy = this.energy - value;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public List<Integer> getGenes() {
        return this.genes;
    }

    public int getAnimalID() {
        return animalID;
    }

    public boolean isDead(){
        return this.energy <= 0;
    }

    public void move(MoveDirection direction){
        Vector2d futurePosition;
        Vector2d currentPosition = this.position;
        this.rotate();
        switch (direction){
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
                futurePosition = this.countPWBCase(this.position.add(Objects.requireNonNull(this.direction.toUnitVector())));
                this.position = futurePosition;
                this.positionChanged(currentPosition, futurePosition);
                System.out.println("changed position from: " + currentPosition + " to " + futurePosition);
                break;
            case BACKWARD:
                futurePosition = this.countPWBCase(this.position.add(Objects.requireNonNull(this.direction.toUnitVector()).opposite()));
                this.position = futurePosition;
                this.positionChanged(currentPosition, futurePosition);
                break;
        }
    }

    private void rotate(){ // increment +1 means always +45 degrees
        int numberOfTurns = GenesManager.getRandGene(this.genes);
        MapDirection direction = this.direction;
        for(int i=0; i<numberOfTurns; i++){
            this.direction = direction.next();
        }
    }

    private Vector2d countPWBCase(Vector2d futurePosition){
        Vector2d outputVector = new Vector2d(futurePosition.getX() % this.getMap().getWidth(), futurePosition.getY() % this.getMap().getHeight());

        if(outputVector.getX() >= 0 && outputVector.getY() >= 0){
            return outputVector;
        }
        if(outputVector.getX() < 0){
            return new Vector2d(this.getMap().getWidth() + outputVector.getX(), outputVector.getY());
        }
        if(outputVector.getY() < 0){
            return new Vector2d(outputVector.getX(), this.getMap().getHeight() + outputVector.getY());
        }
        return outputVector;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer : this.observers){
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    @Override
    public boolean canMove(){
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getAnimalID() == animal.getAnimalID() &&
                getMap().equals(animal.getMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnimalID(), getMap());
    }


    @Override
    public String toString() {
        if(this.energy > 0) return this.direction.toString() + " " + this.energy;
        else return "x";
    }

    @Override
    public javafx.scene.paint.Color color() {
        System.out.println("ENERGY: " + this.energy);
        if (energy == 0) return Color.rgb(255, 255, 255);
        if (energy < 0.25 * defaultEnergy) return Color.rgb(255, 255, 153);
        if (energy < 0.5 * defaultEnergy) return Color.rgb(255, 255, 51);
        if (energy < defaultEnergy) return Color.rgb(90, 218, 255);
        if (energy == defaultEnergy) return Color.rgb(14, 137, 204);
        if (energy < 1.5 * defaultEnergy) return Color.rgb(24, 78, 174);;
        if (energy < 2 * defaultEnergy) return Color.rgb(100, 11, 255);
        if (energy < 2.5 * defaultEnergy) return Color.rgb(140, 75, 130);
        if (energy < 3 * defaultEnergy) return Color.rgb(124, 34, 126);
        if (energy < 5 * defaultEnergy) return Color.rgb(74, 0, 88);
        if (energy > 10 * defaultEnergy) return Color.rgb(46, 0, 48);
        return Color.rgb(0, 0, 0);   }
}
