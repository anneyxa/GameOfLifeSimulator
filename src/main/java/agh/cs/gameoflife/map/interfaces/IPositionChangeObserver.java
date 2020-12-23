package agh.cs.gameoflife.map.interfaces;

import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
