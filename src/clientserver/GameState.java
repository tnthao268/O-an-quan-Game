/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see clientserver
 * @see java.io.Serializable
 * @see java.util.Arrays
 */




package clientserver;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Game state contains information about the board state of the game.
 */

public class GameState implements Serializable {
    /**
     * Game's board
     */

    public int[] board ;

    /**
     * Constructor of the class
     * @param board game's board
     */


    public GameState(int[] board){
        this.board = board;
    }

    /**
     * Text presentation of GameState object
     * @return Text presentation of GameState object
     */
    public String toString(){
        return String.format("Board %s ", Arrays.toString(board));
    }

}
