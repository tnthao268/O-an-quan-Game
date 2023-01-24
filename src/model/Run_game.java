/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see model
 */


package model;

/**
 * This class runs the game with 2 players as computers playing automatic with each other
 */

public class Run_game {
    /**
     * Runs the game
     * @param args arguments
     */
    public static void main(String[] args){
        IModel g = Game.newGame();
        while (!g.isEndgame()){
            g = g.play(g.randomMove());
            System.out.println(g);
        }
    }
}
