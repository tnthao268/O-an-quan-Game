package Controller;

import Model.IModel;

public interface IView {
    void choose_direction(IModel game);
    void draw_step_stones(IModel game);
    void computer_turn(IModel game);
    void draw_score(IModel game);

    void which_turn(IModel game);
    void win(IModel game);
}
