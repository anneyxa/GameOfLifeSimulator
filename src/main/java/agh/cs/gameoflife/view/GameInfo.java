package agh.cs.gameoflife.view;

import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Plant;
import agh.cs.gameoflife.model.Vector2d;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameInfo implements Serializable {

    private int animalsAlive;
    private int animalsDead;
    private int currentPlantsNumber;
    private int averageAnimalsEnergy;
    private int dayNumber;

    public GameInfo(List<Animal> animalsAlive, List<Animal> animalsDead, Map<Vector2d, Plant> plants, int dayNumber) {
        this.animalsAlive = animalsAlive.size();
        this.animalsDead = animalsDead.size();
        this.currentPlantsNumber = plants.size();
        setAverageAnimalsEnergy(animalsAlive);
        this.dayNumber = dayNumber;
    }

    public void setAnimalsAlive(List<Animal> animalsAlive) {
        this.animalsAlive = animalsAlive.size();
    }

    public void setAnimalsDead(List<Animal> animalsDead) {
        this.animalsDead = animalsDead.size();
    }

    public void setCurrentPlantsNumber(Map<Vector2d, Plant> plants) {
        this.currentPlantsNumber = plants.size();
    }

    public void setAverageAnimalsEnergy(List<Animal> animalsAlive) {
        int sum = 0;
        for(Animal animal : animalsAlive){
            sum += animal.getEnergy();
        }
        this.averageAnimalsEnergy = sum / this.animalsAlive;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    @Override
    public String toString() {
        return "Statistics: \n" +
                "\n Alive animals number: " + animalsAlive +
                "\n Dead animals number:" + animalsDead +
                "\n Current plants number: " + currentPlantsNumber +
                "\n averageAnimalsEnergy: " + averageAnimalsEnergy +
                "\n dayNumber: " + dayNumber;
    }
}
