package Controller;

import Model.Game;
import Model.IModel;
import Model.Move;

public class GameController implements IController{


    private IView view;

    private IModel game ;


    //Game_Interface game = Game.newGame();

    //contructor
    public GameController(IModel game) {
        this.game = game;
    }

    public IModel getGame() {
        return game;
    }

    //set View

    public void setView(IView view) {
        this.view = view;
    }

    //drawstepstones?
    public void return_draw_step_stones(){
        view.draw_step_stones(game);
    }

    public IModel save_computer_move(){
        return game = view.computer_play(game);
    }

    public IModel save_human_move_left(){
        return game = view.human_play_left(game);
    }

    public IModel save_human_move_right(){
        return game = view.human_play_right(game);
    }



    //draw_score()
    public void return_draw_score(){
        view.draw_score(game);
    }



    //which_turn
    public void return_which_turn(){
        view.which_turn(game);
    }

    //computer_turn()
    public void return_computer_turn(){
        view.computer_turn(game);
    }


    //win()
    public void return_win(){
        view.win(game);
    }

    public boolean is_empty_field(){
        return view.field_is_empty(game);
    }





}
