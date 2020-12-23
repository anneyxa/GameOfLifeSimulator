package agh.cs.gameoflife.map.interfaces;

import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Vector2d;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     */
    void placeAnimal(Animal animal);

    /**
     * Add dead animal to deadAnimals list and remove dead animal from animals
     */
    void handleDeadAnimals();

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectsAt(Vector2d position);

    //dodane z powodu periodycznych warunków brzegowych, aby przenosić zwierzaka na drugi
    //koniec mapki, musi on znać jej rozmiar, czyli moze go wziąć z map.getWidth() i map.getHeight()
    //aby zaimplementowac dobrze PWB

    int getWidth();

    int getHeight();
}
