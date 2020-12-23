package agh.cs.gameoflife.view;

public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IUserInterfaceContract.View view;

    public ControlLogic(IUserInterfaceContract.View view) {
        this.view = view;
    }

    @Override
    public void onUpdatePositions() {
        System.out.println("POSITION CHANGE");
    }

    @Override
    public void onButtonCLick() {
        System.out.println("BUTTON CLICK");
    }

    @Override
    public void onDialogCLick() {
//        this.view.
    }
}
