/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see model
 * @see java.util.Arrays
 * @see java.util.List
 */


package model;

import java.util.Arrays;
import java.util.List;

/**
 * Interface of the game's model
 * Contains methods which are used by controller
 */

public interface IModel {

    /**
     * Method to return current board's state of the game.
     * For example: [10,5,5,5,5,5,10,5,5,5,5,5]
     * @return board
     */
    int [] getBoard();

    /**
     * Method to return List of different changed states of the board after each move
     * @return list of the board's states
     */

    List<int[]> getCopyBoardlist();

    /**
     * Method to return human's score
     * @return human score
     */

    int getHuman_score();
    /**
     * Method to return computer's score
     * @return computer score
     */

    int getAI_score();

    /**
     * Method to return current player .
     * Player 1: computer, Player 2: human
     * @return current player
     */

    int getPlayer();

    /**
     * Method to return move played by computer
     * @return move
     */
    Move randomMove();
    //Move bestMove();

    /**
     * Method to play the move c
     * @param c move
     * @return state of the game after playing this move
     */
    Game play(Move c);

    /**
     * Check if there are no stones left to play on one side of the board for the current player
     * @param pl current player playing
     * @param board current state of the board
     * @return true if there are no stones left, false otherwise
     */

    boolean check_stones_over(int pl, int[] board);

    /**
     *  Scatter the stones from player's own bank on the game board, 1 stone per square
     *  when there are no more stones for the player to play on their side
     * @param g game Object
     * @param board current board
     * @param pl current player
     */
    void stones_over(Game g, int [] board, int pl);

    /**
     * Check if game ends and update the end scores of players
     * @return true if the game ends, false if otherwise
     */


    default boolean isEndgame(){
        if ((getBoard()[0] == 0 && getBoard()[6] == 0) || (getAI_score() < 5 && check_stones_over(1,getBoard()) )
                || (getHuman_score() < 5 && check_stones_over(2,getBoard()) )){
            int endScore_AI = getAI_score() + Arrays.stream(getBoard(), 1, 6).sum();
            int endScore_human = getHuman_score()+ Arrays.stream(getBoard(), 7, 12).sum();
            if (endScore_AI > endScore_human) System.out.println("Sorry, you lose! Try again next time");
            if (endScore_AI == endScore_human) System.out.println("Game is tied");
            if (endScore_AI < endScore_human) System.out.println(" Congratulations! You win!");
            System.out.println("Player human end score = " + endScore_human);
            System.out.println("Player computer end score = " + endScore_AI);
            //int [] end_board = Arrays.stream(getBoard(),0,12).map(n -> n = 0).toArray();
            return true;
        }
        return false;
    }
}
