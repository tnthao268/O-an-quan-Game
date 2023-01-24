/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see model
 * @see java.util
 * @see java.util.stream.IntStream
 */

package model;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Logical model of the Vietnamese game "O an quan"
 * Information of the game:
 * <a href="https://en.wikipedia.org/wiki/%C3%94_%C4%83n_quan">...</a>
 * References :
 * <a href ="https://123docz.net/document/3974969-bao-cao-mon-tri-tue-nhan-tao-game-o-an-quan.htm">...</a>
 */

public class Game implements IModel{

    /**
     * Current board's state of the game.
     * For example: [10,5,5,5,5,5,10,5,5,5,5,5]
     */
    private final int [] board ;

    /**
     * Temporary board to save the state of the board after a change during each move
     */
    private int [] tem_board = {10,5,5,5,5,5,10,5,5,5,5,5};

    /**
     * List of different changed states of the board after each move
     */
    private List<int[]> copy_boardlist;

    /**
     * Human score
     */

    private int human_score;

    /**
     * AI_score
     */
    private int AI_score;

    /**
     * Current player.
     * Player 1: Computer, Player 2: Human
     */

    private int player;
    /**
     * New random object
     */
    private final Random r = new Random();
    

    /**
     * Constructor for class Game
     * @param board array which shows current state of the board
     * @param copy_boardlist list of all board's states in a move
     * @param human_score human player's score (score of player 2)
     * @param AI_score computer's score (score of player 1)
     * @param player the player who plays first
     */

    private Game(int[] board,  List<int[]> copy_boardlist, int human_score, int AI_score, int player){
        this.board = board;
        this.AI_score = AI_score;
        this.human_score = human_score;
        this.player = player;
        this.copy_boardlist = copy_boardlist;
    }

    /**
     * To create game object from inside outside the class.
     * This method helps to access private constructor
     * @param board array which shows current state of the board
     * @param copy_boardlist list of all board's states in a move
     * @param human_score human player's score (score of player 2)
     * @param AI_score computer's score (score of player 1)
     * @param player the player who plays first
     *
     */
    public static Game of(int[] board,  List<int[]> copy_boardlist, int human_score, int AI_score, int player) {
        return new Game(board, copy_boardlist, human_score, AI_score, player);
    }

    /**
     * Method to return current board's state of the game.
     * For example: [10,5,5,5,5,5,10,5,5,5,5,5]
     * @return board
     */

    public int [] getBoard() {
        return board;
    }

    /**
     * Method to return List of different changed states of the board after each move
     * @return list of the board's states
     */


    public List<int[]> getCopyBoardlist(){
        return copy_boardlist;
    }

    /**
     * Method to return human's score
     * @return human score
     */

    public int getHuman_score(){
        return human_score;
    }

    /**
     * Method to return computer's score
     * @return computer score
     */
    public int getAI_score(){
        return AI_score;
    }

    /**
     * Method to return current player .
     * Player 1: computer, Player 2: human
     * @return current player
     */

    public int getPlayer(){
        return player;
    }

    /**
     * Method to create new game
     * @return new game object
     */

    public static Game newGame() {
        int [] board1 = {10,5,5,5,5,5,10,5,5,5,5,5};
        System.out.println(Arrays.toString(board1));
        List<int[]> copy_boardList = new ArrayList<>();
        return new Game(board1,copy_boardList,0,0,2) ;
    }

    /**
     * Generates random play move played by computer
     * @return Move.of(position, direction) : position of chosen field on the board and its played direction
     */
    public Move randomMove() {
        assert !isEndgame():"Game is over!";
        int position;
        int direction;
        int stones_picked;

        List<Integer> direction_list = Arrays.asList(-1, 1);
        do{
            position = (player == 1 ? r.nextInt(1,6) : r.nextInt(7,12));
            direction = direction_list.get(r.nextInt(direction_list.size()));
            stones_picked = board[position];} while (stones_picked == 0);
        //player = (int) player_list.get(0); //Spieler = 1 (AI)
        //assert stones_picked != 0: "No stones to choose";
        //assert position != 0 && position != 6 : "Not allowed to choose ";

        System.out.println("Position :" + position);
        System.out.println("Direction:" + direction);
        System.out.println("Stonespicked:" + stones_picked);
        //System.out.println("Random move");
        return Move.of(position, direction);
    }

    /**
     * Changes position of the board when it is out of range (< 0 or > 11 )
     * @param position current played position on the board
     * @return new changed position
     */

    public int change_position(int position) {
        if (position < 0) position = 11;
        if (position > 11) position = 0;
        //System.out.println("Changed position: " + position);
        return position;
    }

    /**
     * The case when the player loses their turn
     * @param board current state of the board
     * @param position chosen position of the field
     * @param direction play direction
     * @return true when the next 2 fields do not have stones or the next field is big field with number of stones > 0
     */
    public boolean lose_turn(int[] board, int position, int direction) {
        int next_position = position + direction;
        next_position = change_position(next_position);//Position des nächsten Quadrats
        int next_next_position = next_position + direction;
        next_next_position = change_position(next_next_position);
        return (board[next_position] == 0 && board[next_next_position] == 0)  || (next_position == 0 && board[next_position] != 0)|| (next_position == 6 && board[next_position] != 0);
    }

    /**
     * Method to play the move which is chosen
     * @param m Move which is played
     * @return new game object
     */

    public Game play(Move m){
        int position = m.position;
        int direction = m.direction;
        //List which contains all states of board during this move
        List<int[]> boardList = new ArrayList<>();

        //New game object is created
        Game g = Game.of(board,copy_boardlist,human_score,AI_score,player);
        //System.out.println("player number:" + g.player);

        int score_turn = 0; //Score of this turn
        int stones_picked = board[position];//Number of stones in the selected squares
        assert stones_picked != 0 : "No stones to play!";

        //Number of stones from the selected square is 0 after the stones have been picked up
        board[position] = 0;
        tem_board = Arrays.copyOf(board,12);
        //add the copied state of the board into the list
        boardList.add(tem_board);
        //System.out.println(Arrays.toString(tem_board));

        // if lose_turn is true, the player loses the turn
        boolean loses_turn = false;

        //when the player has not lost turn yet
        while (!loses_turn) {
            //when there are still stones to scatter
            while (stones_picked > 0) {
                //position is changed according to the direction of scattering
                position += direction;
                //position is changed in case it is out of range
                position = change_position(position);
                //one stones on the hand is reduced after scattering on a square
                stones_picked -= 1;
                //number of stones on the square which is scattered is increased by 1
                board[position] += 1;

                tem_board = Arrays.copyOf(board,12);
                boardList.add(tem_board);
                //System.out.println(Arrays.toString(tem_board));
            }
            if (stones_picked == 0) { //when all stones of the selected square are scattered
                int next_position = position + direction;  //Position of the next square
                next_position = change_position(next_position);
                int next_next_position = next_position + direction;//Position of the next after next square
                next_next_position = change_position(next_next_position);
                //the case if the player loses the move
                if (lose_turn(board,position,direction)) {
                    break ;
                }

                // the case when the player wins stones to himself
                while (board[next_position] == 0 && board[next_next_position] > 0) {
                    score_turn += board[next_next_position];
                    board[next_next_position] = 0;
                    //tem_board = board; doesn't work (list returns all last elements, has to be Arrays.copy)
                    tem_board = Arrays.copyOf(board,12);

                    boardList.add(tem_board);
                    //System.out.println(Arrays.toString(tem_board));
                    position = next_next_position;
                    //keep checking whether the next square is empty, otherwise player loses the move
                    int t = position + direction;
                    t = change_position(t);
                    if (board[t] > 0 || lose_turn(board,position,direction) ) {
                        loses_turn = true;
                        break;
                    }
                }
                // the case if there are stones again in the next square, then these stones are scattered further.
                if (board[next_position] > 0) {
                    stones_picked = board[next_position];
                    board[next_position] = 0;
                    tem_board = Arrays.copyOf(board,12);

                    boardList.add(tem_board);
                    //System.out.println(Arrays.toString(tem_board));
                    position = next_position;
                }
            }
        }

        /*
        for (int[] a: boardList) {
            System.out.println(Arrays.toString(a));
        }

         */


        System.out.println(Arrays.toString(board));

        //System.out.println(Arrays.toString(boardList.toArray()));

        System.out.println("player:" + g.player);

        //(check_stones_over(2, board) ? (AI_score- 5) : AI_score)


        /*
        if (player == 1) {
            g.AI_score += score_turn ;
            g.player = 2;

        }
        if (player == 2) {
            g.human_score += score_turn ;
            g.player = 1;

        }

         */


        change_player(g,score_turn);

        //update_score(g.board,g.player,g.AI_score,g.human_score,score_turn);
        System.out.println("Player 2 score: "+ g.human_score);
        System.out.println("Player 1 score: " + g.AI_score);
        System.out.println("Score turn :" + score_turn);

        //System.out.println("New player : " + g.player);
        stones_over(g,board,g.player);

        player = g.player;
        AI_score = g.AI_score;
        human_score = g.human_score;

        //add the boardlist list (all board states of this move) to the copy_boardlist
        copy_boardlist.addAll(boardList);
        copy_boardlist.add(board);

        //delete all elements in the list of this move
        boardList.clear();


        return g;

    }

    /**
     * Switch players' turn to play and add score of the turn to player's score
     * @param g Object Game
     * @param score_turn Score gained for one of the players of their current turn
     */

    public void change_player(Game g, int score_turn){
        if (g.player == 1) {
            g.AI_score += score_turn ;
            g.player = 2;
        }
        else if (g.player == 2) {
            g.human_score += score_turn ;
            g.player = 1;


        }

    }


    /**
     * Check if there are no stones left to play on one side of the board for the current player
     * @param player current player playing
     * @param board current state of the board
     * @return true if there are no stones left, false otherwise
     */

    public boolean check_stones_over(int player, int [] board){
        if (player == 1){
            return Arrays.stream(board,1,6).noneMatch(n-> n!= 0);
        }
        else if (player == 2){
            return Arrays.stream(board, 7, 12).noneMatch(n -> n!= 0);
        }
        return false;
    }


    /**
     *  Scatter the stones from player's own bank on the game board, 1 stone per square
     *  when there are no more stones for the player to play on their side
     * @param g game Object
     * @param b current board
     * @param player current player
     */


    public void stones_over(Game g, int [] b, int player) {
        if (check_stones_over(player, b) && !isEndgame()) {
            if (player == 1) {
                g.AI_score -= 5;
                assert AI_score >= 0 : "Score is not allowed to be negative";
                //assert player != 2 : "You are not allowed to play this side!";
                IntStream.range(0, b.length).filter(index -> index < 6 && index > 0).map(index -> b[index] = 1).toArray();
                System.out.println(Arrays.toString(b));
            } else {
                g.human_score -= 5;
                assert human_score >= 0: "Score is not allowed to be negative";
                //assert player != 1 : "You are not allowed to play this side!";
                IntStream.range(0, b.length).filter(index -> index < 12 && index > 6).map(index -> b[index] = 1).toArray();
                System.out.println(Arrays.toString(b));

            }
        }
    }

    /**
     * Text representation of the current game board
     * @return board
     */

    @Override
    public String toString(){
        //return String.format("Board %s", Arrays.toString(board));
        return "Board: " + Arrays.toString(board) ;
    }



}










