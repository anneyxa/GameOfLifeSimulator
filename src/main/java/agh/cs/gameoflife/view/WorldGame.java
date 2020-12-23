package agh.cs.gameoflife.view;

import java.io.Serializable;

public class WorldGame implements Serializable {

    private final GameState gameState;
    private final int[][] gridState;

    public WorldGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

//    public int[][] getCopyOfGridState() {
//        return WorldUtilities.copyToNewArray(gridState);
//    }
}
