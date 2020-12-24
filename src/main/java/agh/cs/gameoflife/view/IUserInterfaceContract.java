package agh.cs.gameoflife.view;


import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Plant;

public interface IUserInterfaceContract {
    void updateBoard();
    void placeAnimalOnBoard(Animal animal);
    void placePlantOnBoard(Plant plant);
    void dialogHandle(String message);
    void updateStatistics(GameInfo gameInfo);
}
