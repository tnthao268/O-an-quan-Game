/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see org.junit.jupiter.api.Assertions
 * @see model.Game
 * @see model.IModel
 * @see model.Move
 * @see org.junit.jupiter.api.Test
 * @see java.util.ArrayList
 * @see java.util.List
 * @see test
 */



package test;

import static org.junit.jupiter.api.Assertions.*;

import model.Game;
import model.IModel;
import model.Move;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to test the correctness of the game's model (class Game)
 */

class GameTest {
    /**
     * Standard constructor
     */
    public GameTest(){};

    /**
     * List to save states of the boards in each move
     */

    private final List<int[]> boardlist = new ArrayList<>();


    /**
     * Test method newGame()
     */

    @Test
    void newGame() {
        IModel g = Game.newGame();
        var game1 = Game.of(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 2);
        assertArrayEquals(g.getBoard(),game1.getBoard());
        assertEquals(g.getAI_score(),game1.getHuman_score());
        assertEquals(g.getPlayer(),game1.getPlayer());
        assertEquals(g.getCopyBoardlist(),boardlist);

    }

    /**
     * Test method randomMove()
     */

    @Test
    void randomMove() {
        // First player's turn
        var game1 = Game.of(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        Move m1 = game1.randomMove();
        // player 1 is only allowed to play from board position 1->5
        assertTrue(1<=m1.position && m1.position<=5);
        //Direction is only allowed with either 1 or -1
        assertTrue(m1.direction == 1 || m1.direction == -1);
        // Position of the field is chosen only when the number of stones there does not equal zero
        assertTrue(game1.getBoard()[m1.position] != 0);

        // Second player's turn
        var game2 = Game.of(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 2);
        Move m2 = game2.randomMove();
        //player 2 is only allowed to play from board position 7->11
        assertTrue(7<=m2.position && m2.position <=11);
        assertTrue(m2.direction == 1 || m2.direction == -1);
        assertTrue(game2.getBoard()[m2.position] != 0);
    }

    /**
     * Test method change_position(int position)
     */

    @Test
    void change_position() {
        var game1 = Game.of(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        //when position < 0
        Move m = Move.of(-1,-1);
        Move changed_m = Move.of(11, -1);
        assertEquals(game1.change_position(m.position), changed_m.position);

        //when position > 11
        Move m1 = Move.of(13,1);
        Move changed_m1 = Move.of(0,1);
        assertEquals(game1.change_position(m1.position), changed_m1.position);

    }

    /**
     * Test method lose_turn(int[] board, int position, int direction)
     */

    @Test
    void lose_turn() {
        var game1 = Game.of(new int[]{10, 5, 5, 0, 0, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        Move m = Move.of(2,1);
        assertTrue(game1.lose_turn(game1.getBoard(), m.position,m.direction));

        Move m1 = Move.of(5,1);
        assertTrue(game1.lose_turn(game1.getBoard(), m1.position,m1.direction));
        Move m2 = Move.of(11,1);
        assertTrue(game1.lose_turn(game1.getBoard(), m2.position,m2.direction));
        

        Move m3 = Move.of(1,1);
        var game2 = Game.of(new int[]{0, 5, 5, 0, 0, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        assertFalse(game2.lose_turn(game2.getBoard(), m2.position,m2.direction));

        assertFalse(game2.lose_turn(game2.getBoard(),m3.position,m3.direction ));

        var game3 = Game.of(new int[]{0, 5, 5, 0, 4, 0, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        Move m4 = Move.of(4,1);
        Move m5 = Move.of(1,-1);
        assertFalse(game3.lose_turn(game3.getBoard(),m4.position,m4.direction ));
        assertFalse(game3.lose_turn(game3.getBoard(), m5.position,m5.direction));
    }

    /**
     * Test method play(Move m)
     */

    @Test
    void testPlay() {

        var game1 = Game.of(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 2);
        var game2 = game1.play(Move.of(10,1));
        assertArrayEquals(game2.getBoard(), new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 6, 0, 0});
        assertEquals(game2.getHuman_score(),6);
        assertEquals(game2.getAI_score(),0);

        // create a new list with arrays which are different states of the board after a move
        List<int[]> secondList=new ArrayList<>();

        secondList.add(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 0, 5});
        secondList.add(new int[] {10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 5, 5, 5, 5, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 5, 5, 5, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 5, 5, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 5, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 5, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 10, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 5, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 6, 5, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 5, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 6, 0, 6});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 6, 0, 0});
        secondList.add(new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 6, 0, 0});
        //list which needs to be tested
        List<int[]> copyBoardlist = game1.getCopyBoardlist();

        // test if two list has same elements
        // by comparing each array of 1 list equals from another in the same position in the list

        assertArrayEquals(copyBoardlist.get(1),secondList.get(1));
        assertArrayEquals(copyBoardlist.get(2),secondList.get(2));
        assertArrayEquals(copyBoardlist.get(3),secondList.get(3));
        assertArrayEquals(copyBoardlist.get(4),secondList.get(4));
        assertArrayEquals(copyBoardlist.get(5),secondList.get(5));
        assertArrayEquals(copyBoardlist.get(6),secondList.get(6));
        assertArrayEquals(copyBoardlist.get(7),secondList.get(7));
        assertArrayEquals(copyBoardlist.get(8),secondList.get(8));
        assertArrayEquals(copyBoardlist.get(9),secondList.get(9));
        assertArrayEquals(copyBoardlist.get(10),secondList.get(10));
        assertArrayEquals(copyBoardlist.get(11),secondList.get(11));
        assertArrayEquals(copyBoardlist.get(12),secondList.get(12));
        assertArrayEquals(copyBoardlist.get(13),secondList.get(13));



        //check if the rule of winning some stones is correct
        var game3ai = Game.of(new int[]{0, 1, 0 , 0 , 2 , 0, 1, 1, 0, 2, 0, 0}, boardlist,0, 0, 1);
        var game3c = game3ai.play(Move.of(1,1));
        assertArrayEquals(game3c.getBoard(),new int[] {0, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0, 0});
        assertEquals(game3c.getAI_score(),3);

        //check if player's move ends when reaching 2 next empty fields
        var game3 = Game.of(new int[]{0, 1, 0 , 0 , 0 , 1, 1, 1, 0, 2, 0, 0}, boardlist,0, 0, 2);

        var game3a = game3.play(Move.of(1,1));
        assertArrayEquals(game3a.getBoard(),new int[] {0, 0, 1, 0, 0, 1, 1, 1, 0, 2, 0, 0});
        assertEquals(game3a.getHuman_score(),0);

        //check if the rule of winning is applied for big field as well
        var game4 = Game.of(new int[]{0, 1, 0 , 2 , 0 , 2, 0, 2, 0, 0, 1, 0}, boardlist,0, 0, 2);

        var game5 = game4.play(Move.of(10,1));
        assertEquals(game5.getHuman_score(),7);

        //test the rules of scattering stones when there are no stones to play on one player's side

        //before scattering stones : {5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}
        var game6 = Game.of(new int[]{5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, boardlist,10, 10, 1);
        var game6_played = game6.play(Move.of(4,-1));
        assertArrayEquals(game6_played.getBoard(),new int[] {5, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1}); //after scattering 5 stones on each of 5 fields

        //test that user is not allowed to play king field

        var game7 = Game.of(new int[]{5, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0}, boardlist,10, 10, 1);

        assertArrayEquals(game7.play(Move.of(6,1)).getBoard(),new int[]{5, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0});
        assertArrayEquals(game7.play(Move.of(0,1)).getBoard(),new int[]{5, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0});

        //test that user is not allowed to choose field without stones
        assertArrayEquals(game7.play(Move.of(1,1)).getBoard(),new int[]{5, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0});
    }

    /**
     * Test method change_player(Game g, int score_turn)
     */

    @Test
    void change_player(){
        var game = Game.of(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5}, boardlist,2, 0, 2);
        int score_turn = 10;
        game.change_player(game,score_turn);
        assertEquals(1, game.getPlayer());
        assertEquals(12, game.getHuman_score());

        var game1 = Game.of(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        game1.change_player(game1, score_turn);
        assertEquals(2, game1.getPlayer());
        assertEquals(10, game1.getAI_score());
    }

    /**
     * Test method check_stones_over(int player, int [] board)
     */

    @Test
    void check_stones_over() {
        var game1 = Game.of(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        assertTrue(game1.check_stones_over(game1.getPlayer(), game1.getBoard()));
        assertFalse(game1.check_stones_over(2, game1.getBoard()));

        var game2 = Game.of(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, boardlist,0, 0, 2);
        assertTrue(game2.check_stones_over(game2.getPlayer(), game2.getBoard()));
        assertFalse(game1.check_stones_over(1, game2.getBoard()));

    }

    /**
     * Test method stones_over(Game g, int [] b, int player)
     */

    @Test
    void stones_over() {
        var game1 = Game.of(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5}, boardlist,0, 10, 1);
        game1.stones_over(game1, game1.getBoard(), game1.getPlayer());
        assertArrayEquals(game1.getBoard(), new int[] {0,1,1,1,1,1,1,5,5,5,5,5});

        var game2 = Game.of(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, boardlist,10, 0, 2);
        game2.stones_over(game2, game2.getBoard(), game2.getPlayer());
        assertArrayEquals(game2.getBoard(), new int[] {0,1,0,0,0,0,1,1,1,1,1,1});



        var game3 = Game.of(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, boardlist,0, 10, 2);
        game3.stones_over(game3, game3.getBoard(), game3.getPlayer());
        assertArrayEquals(game3.getBoard(), new int[] {0,1,0,0,0,0,1,0,0,0,0,0});
    }

    /**
     * Test the isEndgame() method in IModel
     */

    @Test
    void test_isEndgame(){
        //case when 2 big fields are empty
        var game = Game.of(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5}, boardlist,0, 0, 2);
        assertTrue(game.isEndgame());

        //case when player 1 has fewer than 5 points
        // and does not have any stones to play on their side
        var g1 = Game.of(new int[]{10, 0, 0, 0, 0, 0, 10, 5, 5, 5, 5, 5}, boardlist,0, 0, 1);
        assertTrue(g1.isEndgame());


        //case when player 2 has fewer than 5 points
        // and does not have any stones to play on their side
        var g2 = Game.of(new int[]{10, 1, 1, 1, 1, 1, 10, 0, 0, 0, 0, 0}, boardlist,0, 0, 2);
        assertTrue(g2.isEndgame());

        var game6 = Game.of(new int[]{10,0,1,0,0,0,0,0,0,0,0,0}, boardlist,1,2,2);
        assertTrue(game6.isEndgame());

        var game1 = Game.of(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5}, boardlist,0,10,1);
        assertFalse(game1.isEndgame());


    }

    /**
     * Test ToString() from game method
     */
    @Test
    void test_game_ToString(){
        var game = Game.of(new int[]{10, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5}, boardlist,0, 0, 2);
        assertEquals(game.toString(),"Board: [10, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5]");
    }

}