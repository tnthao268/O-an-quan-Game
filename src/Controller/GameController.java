package Controller;

import Model.IModel;

public class GameController implements IController{


    private IView view;

    private final IModel model;

    //contructor
    public GameController(IModel model) {
        this.model = model;
    }



    //set View

    public void setView(IView view) {
        this.view = view;
    }

    //mouse_input(int mouseX, int mouseY)

    //draw_score() ?

    //drawstepstones?

    //which_turn

    //computer_turn()

    //win()





}
