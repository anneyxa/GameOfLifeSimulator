package agh.cs.gameoflife.model;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    MapDirection next(){
        switch(this){
            case NORTH: return NORTHEAST;
            case NORTHEAST: return EAST;
            case EAST: return SOUTHEAST;
            case SOUTHEAST: return SOUTH;
            case SOUTH: return SOUTHWEST;
            case SOUTHWEST: return WEST;
            case WEST: return NORTHWEST;
            case NORTHWEST: return NORTH;
        }
        return null;
    }

    MapDirection previous(){
        switch(this){
            case NORTH: return NORTHWEST;
            case NORTHWEST: return WEST;
            case WEST: return SOUTHWEST;
            case SOUTHWEST: return SOUTH;
            case SOUTH: return SOUTHEAST;
            case SOUTHEAST: return EAST;
            case EAST: return NORTHEAST;
            case NORTHEAST: return NORTH;
        }
        return null;
    }

    Vector2d toUnitVector(){
        switch(this){
            case NORTH: return new Vector2d(0, 1);
            case NORTHEAST: return new Vector2d(1, 1);
            case EAST: return new Vector2d(1, 0);
            case SOUTHEAST: return new Vector2d(1, -1);
            case SOUTH: return new Vector2d(0, -1);
            case SOUTHWEST: return new Vector2d(-1, -1);
            case WEST: return new Vector2d(-1, 0);
            case NORTHWEST: return new Vector2d(-1, 1);
        }
        return null;
    }

    public static MapDirection getRandomDirection(){
        Random random = new Random();
        int size = MapDirection.values().length;
        return Arrays.asList(MapDirection.values()).get(random.nextInt(size));
    }

    @Override
    public String toString() {
        switch(this){
            case NORTH: return "N";
            case NORTHEAST: return "NE";
            case EAST: return "E";
            case SOUTHEAST: return "SE";
            case SOUTH: return "S";
            case SOUTHWEST: return "SW";
            case WEST: return "W";
            case NORTHWEST: return "NW";
        }
        return null;
    }
}
