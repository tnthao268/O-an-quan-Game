package model;

import static org.junit.jupiter.api.Assertions.*;
import  org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GameTest {

    List<int[]> copy_boardlist = new ArrayList<>();


    /**
     * Test method newGame()
     */

    @Test
    void newGame() {

    }

    /**
     * Test method randomMove()
     */

    @Test
    void randomMove() {
        // First player's turn
        var game1 = new Game(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        Move m1 = game1.randomMove();
        // player 1 is only allowed to play from board position 1->5
        assertTrue(1<=m1.position && m1.position<=5);
        //Direction is only allowed with either 1 or -1
        assertTrue(m1.direction == 1 || m1.direction == -1);
        // Position of the field is chosen only when the number of stones there does not equal zero
        assertTrue(game1.board[m1.position] != 0);

        // Second player's turn
        var game2 = new Game(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 2);
        Move m2 = game2.randomMove();
        //player 2 is only allowed to play from board position 7->11
        assertTrue(7<=m2.position && m2.position <=11);
        assertTrue(m2.direction == 1 || m2.direction == -1);
        assertTrue(game2.board[m2.position] != 0);
    }

    /**
     * Test method change_position(int position)
     */

    @Test
    void change_position() {
        var game1 = new Game(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        //when position < 0
        Move m = Move.of(-1,-1);
        Move changed_m = Move.of(11, -1);
        assertTrue(game1.change_position(m.position) == changed_m.position);

        //when position > 11
        Move m1 = Move.of(13,1);
        Move changed_m1 = Move.of(0,1);
        assertTrue(game1.change_position(m1.position) == changed_m1.position);

    }

    /**
     * Test method lose_turn(int[] board, int position, int direction)
     */

    @Test
    void lose_turn() {
        var game1 = new Game(new int[]{10, 5, 5, 0, 0, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        Move m = Move.of(2,1);
        assertTrue(game1.lose_turn(game1.board, m.position,m.direction));

        Move m1 = Move.of(5,1);
        assertTrue(game1.lose_turn(game1.board, m1.position,m1.direction));
        Move m2 = Move.of(11,1);
        assertTrue(game1.lose_turn(game1.board, m2.position,m2.direction));

        Move m3 = Move.of(1,1);
        var game2 = new Game(new int[]{0, 5, 5, 0, 0, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        assertFalse(game2.lose_turn(game2.board, m2.position,m2.direction));
        assertFalse(game2.lose_turn(game2.board,m3.position,m3.direction ));

        var game3 = new Game(new int[]{0, 5, 5, 0, 4, 0, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        Move m4 = Move.of(4,1);
        Move m5 = Move.of(1,-1);
        assertFalse(game3.lose_turn(game3.board,m4.position,m4.direction ));
        assertFalse(game3.lose_turn(game3.board, m5.position,m5.direction));
    }

    /**
     * Test method play(Move m)
     */

    @Test
    void testPlay() {

        var game1 = new Game(new int[]{10, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 2);
        var game2 = game1.play(Move.of(10,1));
        assertArrayEquals(game2.board, new int[]{11, 6, 6, 6, 0, 6, 11, 6, 6, 6, 0, 0});
        assertEquals(game2.human_score,6);
        assertEquals(game2.AI_score,0);

        // ????
        List<int[]> secondList=new ArrayList<>();
        secondList.add(new int[] {10,5,5,5,5,5,10,5,5,5,5,5});
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
        List<int[]> differences = game1.copy_boardlist.stream()
                .filter(element -> !secondList.contains(element)).toList();
        // assertEquals(0,differences.size());


        //check if the rule of winning some stones is correct
        var game3ai = new Game(new int[]{0, 1, 0 , 0 , 2 , 0, 1, 1, 0, 2, 0, 0},copy_boardlist,0, 0, 1);
        var game3c = game3ai.play(Move.of(1,1));
        assertArrayEquals(game3c.board,new int[] {0, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0, 0});
        assertEquals(game3c.AI_score,3);

        //check if player's move ends when reaching 2 next empty fields
        var game3 = new Game(new int[]{0, 1, 0 , 0 , 2 , 0, 1, 1, 0, 2, 0, 0},copy_boardlist,0, 0, 2);

        var game3a = game3.play(Move.of(6,1));
        assertArrayEquals(game3a.board,new int[] {0, 1, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0});
        assertEquals(game3a.human_score,2);

        //check if the rule of winning is applied for big field as well
        var game4 = new Game(new int[]{0, 1, 0 , 2 , 0 , 2, 0, 2, 0, 0, 1, 0},copy_boardlist,0, 0, 2);

        var game5 = game4.play(Move.of(10,1));
        assertEquals(game5.human_score,7);

        //before scattering stones : {5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}
        var game6 = new Game(new int[]{5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},copy_boardlist,10, 10, 1);
        var game6_played = game6.play(Move.of(4,-1));
        assertArrayEquals(game6_played.board,new int[] {5, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1}); //after scattering 5 stones on each of 5 fields


    }

    /**
     * Test method change_player(Game g, int score_turn)
     */

    @Test
    void change_player(){
        var game = new Game(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5},copy_boardlist,2, 0, 2);
        int score_turn = 10;
        game.change_player(game,score_turn);
        assertEquals(1, game.player);
        assertEquals(12,game.human_score);

        var game1 = new Game(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        game1.change_player(game1, score_turn);
        assertEquals(2, game1.player);
        assertEquals(10,game1.AI_score);
    }

    /**
     * Test method check_stones_over(int player, int [] board)
     */

    @Test
    void check_stones_over() {
        var game1 = new Game(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        assertTrue(game1.check_stones_over(game1.player, game1.board));
        assertFalse(game1.check_stones_over(2, game1.board));

        var game2 = new Game(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},copy_boardlist,0, 0, 2);
        assertTrue(game2.check_stones_over(game2.player, game2.board));
        assertFalse(game1.check_stones_over(1, game2.board));

    }

    /**
     * Test method stones_over(Game g, int [] b, int player)
     */

    @Test
    void stones_over() {
        var game1 = new Game(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5},copy_boardlist,0, 10, 1);
        game1.stones_over(game1,game1.board, game1.player);
        assertArrayEquals(game1.board, new int[] {0,1,1,1,1,1,1,5,5,5,5,5});

        var game2 = new Game(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},copy_boardlist,10, 0, 2);
        game2.stones_over(game2, game2.board ,game2.player);
        assertArrayEquals(game2.board, new int[] {0,1,0,0,0,0,1,1,1,1,1,1});



        var game3 = new Game(new int[]{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},copy_boardlist,0, 10, 2);
        game3.stones_over(game3, game3.board,game3.player);
        assertArrayEquals(game3.board, new int[] {0,1,0,0,0,0,1,0,0,0,0,0});
    }

    /**
     * Test the isEndgame() method in IModel
     */

    @Test
    void test_isEndgame(){
        //case when 2 big fields are empty
        var game = new Game(new int[]{0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 2);
        assertTrue(game.isEndgame());

        //case when player 1 has less than 5 points
        // and does not have any stones to play on their side
        var g1 = new Game(new int[]{10, 0, 0, 0, 0, 0, 10, 5, 5, 5, 5, 5},copy_boardlist,0, 0, 1);
        assertTrue(g1.isEndgame());


        //case when player 2 has less than 5 points
        // and does not have any stones to play on their side
        var g2 = new Game(new int[]{10, 1, 1, 1, 1, 1, 10, 0, 0, 0, 0, 0},copy_boardlist,0, 0, 2);
        assertTrue(g2.isEndgame());

        var game6 = new Game(new int[]{10,0,1,0,0,0,0,0,0,0,0,0},copy_boardlist,1,2,2);
        assertTrue(game6.isEndgame());

        var game1 = new Game(new int[]{0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 5, 5},copy_boardlist,0,10,1);
        assertFalse(game1.isEndgame());


    }
}