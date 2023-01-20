package clientserver;

import java.io.Serializable;
import java.util.Arrays;

public class GameState implements Serializable {
      //GameState is actually position of MouseX, MouseY and board
     //Client transfer mouseX, mouseY and to server. Server transmit state of the board


    public int[] board ;

    //public int mouseX, mouseY;

    public GameState(int[] board){
        this.board = board;
    }

    public String toString(){
        return String.format("Board %s", Arrays.toString(board));
    }

}
