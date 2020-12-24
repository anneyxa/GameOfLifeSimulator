package agh.cs.gameoflife.view;

import java.io.*;

public class LocalStorageImpl {

    private static File GAME_DATA = new File(
            "src/main/resources",
            "status.txt"
    );

    public void updateGameData(GameInfo game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Info");
        }
    }

    public GameInfo getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            GameInfo gameState = (GameInfo) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("File not found");
        }
    }
}
