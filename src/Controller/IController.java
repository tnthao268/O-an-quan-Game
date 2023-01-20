package Controller;

import Model.IModel;
import Model.Move;

public interface IController {
    //void return_choose_direction();
    void return_draw_step_stones();

    void return_computer_turn();

    void return_draw_score();
    void return_which_turn();

    void return_win();

    IModel getGame();
    IModel save_computer_move();

    IModel save_human_move_left();
    IModel save_human_move_right();
    boolean is_empty_field();

    IModel thread_play_computer_move();



}
