package clientserver;

import java.io.Serializable;
import java.util.Arrays;

public class GameState implements Serializable {
      //GameState is actually position of MouseX, MouseY and board
     //Client transfer mouseX, mouseY to server. Server transmit state of the board

    public static void main(String[] args){
        int[] b = new int[]{1,2,3,4};
        GameState state = new GameState(b,39,45);
        System.out.println(state);
    }

    public int[] board ;

    public int mouseX, mouseY;

    public GameState(int[] board, int mouseX, int mouseY){
        this.board = board;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public String toString(){
        return String.format("Board %s | Mouse position (%d, %d)", Arrays.toString(board),mouseX,mouseY);
    }

}
