package Controller;

import Model.IModel;
import Model.Move;

public interface IView {
    //void choose_direction(IModel game);
    void draw_step_stones(IModel game);
    void computer_turn(IModel game);

    IModel computer_play(IModel game);

    IModel human_play_left(IModel game);

    IModel human_play_right(IModel game);
    void draw_score(IModel game);

    void which_turn(IModel game);
    void win(IModel game);

    boolean field_is_empty(IModel game);
}
