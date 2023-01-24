/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see controller
 * @see model.IModel
 */

package controller;

import model.IModel;

/**
 * This interface contains all necessary methods from GameView class that are used by controller
 */

public interface IView {
    /**
     * Draw states of the board of each move
     * Runs till when array element,which is state of the board during the move,
     * does not equal the last state of the board yet
     * @param game Game's state
     */
    void draw_step_stones(IModel game);

    /**
     *  Shows GUI and what happens to the game when it is computer turn
     * @param game Game's state
     */
    void computer_turn(IModel game);

    /**
     * Play the move of computer from server thread
     * @param game Game's state
     * @return new Game's state after the played move
     */

    IModel computer_play(IModel game);

    /**
     * Return game after player human chose left direction to play
     * @param game Game's state
     * @return Game's state
     */

    IModel human_play_left(IModel game);

    /**
     * Return game after player human chose right direction to play
     * @param game Game's state
     * @return Game's state
     */

    IModel human_play_right(IModel game);

    /**
     * Method to draw human_score and computer score text
     * @param game Game's state
     */
    void draw_score(IModel game);

    /**
     * When human turn's ends, plays the computer
     * @param game Game's state
     */

    void which_turn(IModel game);

    /**
     * Shows GUI and game's state when the game ends
     * @param game Game's state
     */
    void win(IModel game);

    /**
     * Avoid user choosing field without stones
     * @param game Game's state
     * @return false when field chosen is not empty
     */

    boolean field_is_empty(IModel game);

    /**
     * Play move of computer from client thread
     * @param game Game's state
     */

    void thread_computer_move(IModel game);
}
