/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see controller.GameController
 * @see model.Game
 * @see model.IModel
 * @see view.GameView
 * @see processing.core.PApplet
 */

import controller.GameController;
import model.Game;
import model.IModel;
import view.GameView;
import processing.core.PApplet;

/**
 * Class to run the whole game application
 * Where model,controller and view instances are created
 */
public abstract class Main {
    public static void main(String[] args){

        IModel model = Game.newGame();
        var controller = new GameController(model);
        var view = new GameView();

        controller.setView(view);
        view.setController(controller);


        PApplet.runSketch(new String[]{"GameView"},view);

    }

}
