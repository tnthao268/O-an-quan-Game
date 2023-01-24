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
 * This is interface of a controller from the perspective of a view object
 * View can only see the methods in this interface,
 * other elements of controller are invisible to view
 *
 */

public interface IController {
    /**
     * Runs the draw_step_stones method of view
     */

    void return_draw_step_stones();

    /**
     * Runs the view's computer_turn method
     */

    void return_computer_turn();

    /**
     *  Runs the view's draw_score method
     */

    void return_draw_score();
    /**
     * Runs the view's which_turn method
     */

    void return_which_turn();

    /**
     *  Runs the view's return_win method
     */


    void return_win();
    /**
     * Return current game's state
     * @return game's state
     */
    IModel getGame();

    /**
     * Runs the computer_play method of view
     */

    void save_computer_move();

    /**
     * Runs the view's human_play_left method
     */

    void save_human_move_left();
    /**
     * Runs the view's save_human_move_right method
     */

    void save_human_move_right();

    /**
     * Runs the view's field_is_empty method
     * @return true if field is empty and false otherwise
     */


    boolean is_empty_field();

    /**
     * Runs the view's thread_computer_move method
     */

    void thread_play_computer_move();



}
