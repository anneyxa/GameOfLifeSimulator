package agh.cs.gameoflife.map.interfaces;

import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Plant;

public interface IAnimalPlantObserver {
    void plantRemoved(Plant plant);
    void animalDied(Animal animal);
    void plantPlaced(Plant plant);
    void animalPlaced(Animal animal);
}
