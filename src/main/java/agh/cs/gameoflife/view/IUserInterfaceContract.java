package agh.cs.gameoflife.view;


import agh.cs.gameoflife.model.Animal;
import agh.cs.gameoflife.model.Plant;

public interface IUserInterfaceContract {
    interface  EventListener {
        void onUpdatePositions();
        void onButtonCLick();
        void onDialogCLick();
    }

    interface View {
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateBoard();
        void placeAnimalOnBoard(Animal animal);
        void placePlantOnBoard(Plant plant);
        void dialogHandle(String message);
        void showError(String message);
    }
}
