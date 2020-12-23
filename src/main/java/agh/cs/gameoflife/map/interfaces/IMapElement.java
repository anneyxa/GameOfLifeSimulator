package agh.cs.gameoflife.map.interfaces;

import agh.cs.gameoflife.model.Vector2d;
import javafx.scene.paint.Color;


public interface IMapElement {
    Vector2d getPosition();
    boolean canMove();
    IWorldMap getMap();
    String toString();
    Color color();
    boolean equals(Object o);
    int hashCode();
}
