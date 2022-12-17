package View;

import Controller.IController;
import Controller.IView;
import processing.core.PApplet;

//abstract ? then 2 views ?
public class GameView extends PApplet implements IView {

    private IController controller;

    public void setController(IController controller) {
        this.controller = controller;
    }

    //mousePressed()


}
