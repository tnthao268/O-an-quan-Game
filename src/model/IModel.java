package model;

import java.util.Arrays;
import java.util.List;

public interface IModel {
    int [] getBoard();

    List<int[]> getCopyBoardlist();
    int getHuman_score();
    int getAI_score();



    int getPlayer();
    Move randomMove();
    //Move bestMove();
    Game play(Move c);

    boolean check_stones_over(int pl, int[] board);
    void stones_over(Game g, int [] board, int pl);


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
