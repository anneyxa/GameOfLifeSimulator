package agh.cs.gameoflife.datareader;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DataParser {

    private int plantsNumber;
    private int animalsNumber;
    private int width;
    private int height;
    private float jungleRatio;
    private int animalInitEnergy;
    private int animalMoveEnergy;
    private int plantEnergy;
    private int epochFrequency;


    public static DataParser readDataFromJson(String path) {
        try {
            System.out.println("AAAAAAAAAAAA");
            Gson gson = new Gson();
            DataParser data = (DataParser) gson.fromJson(new FileReader(path), DataParser.class);
            data.validateData();
            return data;
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void validateData() throws IllegalArgumentException {
        System.out.println("aaaaaaa");
        if(this.plantsNumber <= 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_PLANTS_NUMBER);}
        if(this.animalsNumber <= 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_ANIMALS_NUMBER);}
        if(this.width <= 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_WIDTH);}
        if(this.height<= 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_HEIGHT);}
        if(this.jungleRatio <= 0 || this.jungleRatio >= 1){ throw new IllegalArgumentException(ExceptionMessages.INVALID_JUNGLE_RATIO);}
        if(this.animalInitEnergy < 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_ANIMAL_INIT_ENERGY);}
        if(this.animalMoveEnergy < 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_ANIMAL_MOVE_ENERGY);}
        if(this.plantEnergy < 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_PLANT_ENERGY_CONSUMED);}
        if(this.epochFrequency <= 0){ throw new IllegalArgumentException(ExceptionMessages.INVALID_EPOCH_FREQUENCY);}
    }

    public int getPlantsNumber() {
        return plantsNumber;
    }

    public int getAnimalsNumber() {
        return animalsNumber;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getJungleRatio() {
        return jungleRatio;
    }

    public int getAnimalInitEnergy() {
        return animalInitEnergy;
    }

    public int getAnimalMoveEnergy() {
        return animalMoveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getEpochFrequency() {
        return epochFrequency;
    }

    public void setEpochFrequency(int epochFrequency) {
        this.epochFrequency = epochFrequency;
    }

    public void setPlantsNumber(int plantsNumber) {
        this.plantsNumber = plantsNumber;
    }

    public void setAnimalsNumber(int animalsNumber) {
        this.animalsNumber = animalsNumber;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setJungleRatio(float jungleRatio) {
        this.jungleRatio = jungleRatio;
    }

    public void setAnimalInitEnergy(int animalInitEnergy) {
        this.animalInitEnergy = animalInitEnergy;
    }

    public void setAnimalMoveEnergy(int animalMoveEnergy) {
        this.animalMoveEnergy = animalMoveEnergy;
    }

    public void setPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }
}
