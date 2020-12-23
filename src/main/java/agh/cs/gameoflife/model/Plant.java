package agh.cs.gameoflife.model;

import agh.cs.gameoflife.map.interfaces.IMapElement;
import agh.cs.gameoflife.map.interfaces.IWorldMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Plant extends Rectangle implements IMapElement {
    private Vector2d position;
    private IWorldMap map;

    public Plant(IWorldMap map, Vector2d position){
        this.position = position;
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public IWorldMap getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Color color() {
        return Color.GREEN;
    }

    @Override
    public boolean canMove(){
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant)) return false;
        Plant plant = (Plant) o;
        return Objects.equals(getPosition(), plant.getPosition()) &&
                Objects.equals(getMap(), plant.getMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getMap());
    }
}
