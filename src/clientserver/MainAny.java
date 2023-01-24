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
 * For testing:
 * - start MainServer and MainAny --> MainAny is a client.
 * - start MainAny and MainClient --> MainAny is a server.
 * - start two MainAny --> The first is a server, the second a client
 */
public abstract class MainAny {
    public static void main(String[] args) {
        IModel model = Game.newGame();
        var controller = new GameController(model);

        var view = GameView.newAny("localhost", 8080);

        controller.setView(view);
        view.setController(controller);
        PApplet.runSketch(new String[]{"View"}, view);
    }
}
