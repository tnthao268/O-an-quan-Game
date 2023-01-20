package clientserver;

import Controller.GameController;
import Model.Game;
import Model.IModel;
import View.GameView;
import processing.core.PApplet;


public abstract class MainClient {
        public static void main(String[] args) {
            IModel model = Game.newGame();
            var controller = new GameController(model);
            var view = GameView.newClient("localhost", 8080);

            controller.setView(view);
            view.setController(controller);

            PApplet.runSketch(new String[]{"Pong"}, view);
        }
    }
