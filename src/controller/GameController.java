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
 * A controller implementation for a GUI view.This controller creates and uses{@link model.Game}.
 * It implements game's state to keep track of it and give view indications on what to draw
 */

public class GameController implements IController{

    /**
     * A GameView object implemented by IView
     */
    private IView view;
    /**
     * A Game object implemented by IModel
     */

    private IModel game ;


    //Game_Interface game = Game.newGame();

    /**
     * Constructor for GameController class
     * @param game A Game object implemented by IModel
     */
    public GameController(IModel game) {
        this.game = game;
    }

    /**
     * Return current game's state
     * @return game's state
     */

    public IModel getGame() {
        return game;
    }



    /**
     * To access view object from outside the class
     * @param view view object
     */

    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Runs the draw_step_stones method of view
     */
    public void return_draw_step_stones(){
        view.draw_step_stones(game);
    }

    /**
     * Runs the computer_play method of view
     */

    public void save_computer_move(){
        game = view.computer_play(game);
    }

    /**
     * Runs the view's human_play_left method
     */

    public void save_human_move_left(){
        game = view.human_play_left(game);
    }

    /**
     * Runs the view's save_human_move_right method
     */

    public void save_human_move_right(){
        game = view.human_play_right(game);
    }

    /**
     *  Runs the view's draw_score method
     */

    public void return_draw_score(){
        view.draw_score(game);
    }

    /**
     * Runs the view's which_turn method
     */
    public void return_which_turn(){
        view.which_turn(game);
    }

    /**
     * Runs the view's computer_turn method
     */
    public void return_computer_turn(){
        view.computer_turn(game);
    }


    /**
     *  Runs the view's return_win method
     */
    public void return_win(){
        view.win(game);
    }

    /**
     * Runs the view's field_is_empty method
     * @return true if field is empty and false otherwise
     */

    public boolean is_empty_field(){
        return view.field_is_empty(game);
    }

    /**
     * Runs the view's thread_computer_move method
     */

    public void thread_play_computer_move(){
        view.thread_computer_move(game);
    }





}
