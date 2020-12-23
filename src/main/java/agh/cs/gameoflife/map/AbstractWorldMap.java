package agh.cs.gameoflife.map;

import agh.cs.gameoflife.map.interfaces.IPositionChangeObserver;
import agh.cs.gameoflife.map.interfaces.IWorldMap;
import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Vector2d;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected int width;
    protected int height;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;

    protected Map<Vector2d, List<Animal>> animalsPositions;
    protected List<Animal> animals; //just to move animals

    public AbstractWorldMap(int width, int height){
        this.animalsPositions = new ConcurrentHashMap<>();
        this.animals = Collections.synchronizedList(new LinkedList<>());
        this.width = width;
        this.height = height;
        this.setLowerUpper();
    }

    private void setLowerUpper(){
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void placeAnimal(Animal animal) {
        animal.addObserver(this);
        this.animals.add(animal);
        if(this.animalsPositions.containsKey(animal.getPosition())){
            this.animalsPositions.get(animal.getPosition()).add(animal);
        }else{
            this.animalsPositions.put(animal.getPosition(), new LinkedList<>(Arrays.asList(animal)));
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        this.animalsPositions.get(oldPosition).remove(animal);
        if(this.animalsPositions.get(newPosition) == null){
            this.animalsPositions.put(newPosition, new LinkedList<>(Arrays.asList(animal)));
        }else{
            this.animalsPositions.get(newPosition).add(animal);
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.animalsPositions.get(position) != null && !this.animalsPositions.get(position).isEmpty();
    }

    @Override
    public Object objectsAt(Vector2d position) {
        if(this.animalsPositions.get(position) != null && !this.animalsPositions.get(position).isEmpty()){
            return this.animalsPositions.get(position);
        }
        return null;
    }

    public Map<Vector2d, List<Animal>> getAnimalsPositions(){
        return this.animalsPositions;
    }
}
