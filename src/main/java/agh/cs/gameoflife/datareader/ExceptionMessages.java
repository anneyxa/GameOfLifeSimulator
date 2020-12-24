package agh.cs.gameoflife.datareader;

public class ExceptionMessages {
    public static final String INVALID_PLANTS_NUMBER = "Number of init plants cannot be <= 0.";
    public static final String INVALID_ANIMALS_NUMBER = "Number of init animals cannot be <= 0";
    public static final String INVALID_WIDTH = "Map width cannot be <= 0";
    public static final String INVALID_HEIGHT = "Map height cannot be <= 0";
    public static final String INVALID_JUNGLE_RATIO = "Jungle ratio cannot be <= 0 or >= 1";
    public static final String INVALID_ANIMAL_INIT_ENERGY = "Animal init energy cannot be < 0";
    public static final String INVALID_ANIMAL_MOVE_ENERGY = "Animal move energy cannot be < 0";
    public static final String INVALID_PLANT_ENERGY_CONSUMED = "Consumed energy from plant cannot be < 0";
    public static final String INVALID_EPOCH_FREQUENCY = "Epoch frequency cannot be <= 0";
}
