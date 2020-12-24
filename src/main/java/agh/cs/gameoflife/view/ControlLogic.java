package agh.cs.gameoflife.view;

public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IUserInterfaceContract.View view;

    public ControlLogic(IUserInterfaceContract.View view) {
        this.view = view;
    }

    @Override
    public void onAnimalClick() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }
}
