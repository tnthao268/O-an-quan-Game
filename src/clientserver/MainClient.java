/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see clientserver
 * @see controller.GameController
 * @see model.Game
 * @see model.IModel
 * @see view.GameView
 * @see processing.core.PApplet
 */


package clientserver;

import controller.GameController;
import model.Game;
import model.IModel;
import view.GameView;
import processing.core.PApplet;

/**
 * To start client thread.
 * Server needs to start first in order for both server and client threads to run
 */

public abstract class MainClient {
    /**
     * Standard constructor
     */
    public MainClient(){};
    /**
     * Main method
     * @param args String arguments
     */
        public static void main(String[] args) {
            IModel model = Game.newGame();
            var controller = new GameController(model);
            var view = GameView.newClient("localhost", 8080);
            controller.setView(view);
            view.setController(controller);

            PApplet.runSketch(new String[]{"View"}, view);
        }
    }

