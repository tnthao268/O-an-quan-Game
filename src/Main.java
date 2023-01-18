import Controller.GameController;
import Model.Game;
import Model.IModel;
import View.GameView;
import processing.core.PApplet;

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
