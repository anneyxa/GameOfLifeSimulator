package agh.cs.gameoflife.view;

import java.io.Serializable;

public class GameInfo implements Serializable {

    private final GameState gameState;

    public GameInfo(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
