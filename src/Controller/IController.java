package Controller;

import Model.IModel;
import Model.Move;

public interface IController {
    void return_choose_direction();
    void return_draw_step_stones();

    void return_computer_turn();

    void return_draw_score();
    void return_which_turn();

    void return_win();

    IModel getGame();


}
