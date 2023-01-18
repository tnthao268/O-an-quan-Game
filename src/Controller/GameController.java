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

    //mouse_input(int mouseX, int mouseY)
    public void mouse_input(){

    }

    public void return_choose_direction(){
        view.choose_direction(game);
    }
    //drawstepstones?
    public void return_draw_step_stones(){
        view.draw_step_stones(game);
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





}
