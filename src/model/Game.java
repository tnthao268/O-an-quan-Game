package model;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Logical model of the Vietnamese game "O an quan"
 * Information of the game: https://en.wikipedia.org/wiki/%C3%94_%C4%83n_quan
 */

public class Game implements IModel{
    int [] board ;
    int [] tem_board = {10,5,5,5,5,5,10,5,5,5,5,5}; //temporary board to save the state of the board after each change
    List<int[]> copy_boardlist = new ArrayList<>();

    int human_score, AI_score;
    boolean endgame; // checkt, ob das Game schon endet
    boolean human_turn; //checkt, ob der menschliche Spieler dran ist
    int player;
    int play_quadrat; //Position von der Quadrate, die zum Spielen gewählt wird
    int direction; //Richtung von der Streuung der Steine, links : direction = 1, rechts: direction = -1

    private Random r = new Random();
    

    /**
     * Constructor for class Game
     * @param board array which shows current state of the board
     * @param copy_boardlist list of all board's states in a move
     * @param human_score human player's score (score of player 2)
     * @param AI_score computer's score (score of player 1)
     * @param player the player who plays first
     */

    public Game(int[] board,  List<int[]> copy_boardlist, int human_score, int AI_score, int player){
        this.board = board;
        this.AI_score = AI_score;
        this.human_score = human_score;
        this.player = player;
        this.copy_boardlist = copy_boardlist;
    }
    public static Game of(int[] board,  List<int[]> copy_boardlist, int human_score, int AI_score, int player) {
        return new Game(board, copy_boardlist, human_score, AI_score, player);
    }

    public int [] getBoard() {return board;}
    public List<int[]> getCopyBoardlist(){
        return copy_boardlist;
    }

    public int getHuman_score(){return human_score;}
    public int getAI_score(){return AI_score;}

    public int getPlayer(){return player;}

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
     * @return Mov.of(position, direction) : position of chosen field on the board and its played direction
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
        assert stones_picked != 0: "No stones to choose";
        assert position != 0 && position != 6 : "Not allowed to choose ";

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
        System.out.println("Changed position: " + position);
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
        List<int[]> boardList = new ArrayList<>();

        Game g = Game.of(board,copy_boardlist,human_score,AI_score,player);


        //System.out.println("player number:" + g.player);

        //int[] board = Arrays.copyOf(board, 12); //neues Spielbrett Array für die aktuellen erzeugen
        int score_turn = 0; //Score von diesem Zug
        int stones_picked = board[position];//Anzahl von den Steinen der ausgewählten Quadrate
        assert stones_picked != 0 : "No stones to play!";

        board[position] = 0; // Anzahl der Steine von der ausgewählten Quadrat ist 0 , nachdem die Steine aufgehoben wurden
        tem_board = Arrays.copyOf(board,12);

        boardList.add(tem_board);
        //System.out.println(Arrays.toString(tem_board));


        boolean loses_turn = false; //lose turn after eating
        // wenn lose_turn true ist, verliert der Spieler den Zug
        while (!loses_turn) {
            while (stones_picked > 0) {
                //board[position] = 0;
                position += direction;
                position = change_position(position);
                stones_picked -= 1;
                board[position] += 1;
                tem_board = Arrays.copyOf(board,12);

                boardList.add(tem_board);
                //System.out.println(Arrays.toString(tem_board));
            }
            if (stones_picked == 0) { //wenn alle Steine des ausgewählten Quadrats gestreut sind
                int next_position = position + direction;  //Position des nächsten Quadrats
                next_position = change_position(next_position);
                int next_next_position = next_position + direction;
                next_next_position = change_position(next_next_position);
                //der Fall, wenn der Spieler denn Zug verliert
                if (lose_turn(board,position,direction)) {
                    break ;
                }

                // der Fall, wenn der Spieler Steine zu sich gewinnt
                while (board[next_position] == 0 && board[next_next_position] > 0) {
                    score_turn += board[next_next_position];
                    board[next_next_position] = 0;
                    //tem_board = board; doesnt work (list returns all last elements, has to be Arrays.copy)
                    tem_board = Arrays.copyOf(board,12);

                    boardList.add(tem_board);
                    //System.out.println(Arrays.toString(tem_board));
                    position = next_next_position;
                    //weiter checken, ob das nächste Quadrat leer ist, sonst verliert man der Zug
                    int t = position + direction;
                    t = change_position(t);
                    if (board[t] > 0 || lose_turn(board,position,direction) ) {
                        loses_turn = true;
                        break;
                    }
                }
                // der Fall, wenn es in dem nächsten Quadrat wieder Steine gibt, dann werden diese Steine weiter gestreut.
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


        for (int[] a: boardList) {
            System.out.println(Arrays.toString(a));
        }


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

        copy_boardlist.addAll(boardList);
        copy_boardlist.add(board);
        boardList.clear();


        return g;

    }

    /**
     * Switch players' turn to play and add score of the turn to player's score
     * @param g Object Game
     * @param score_turn Score gained for one of the players of their current turn
     */

    public void change_player(Game g, int score_turn){
        if (player == 1) {
            g.AI_score += score_turn ;
            g.player = 2;

        }
        else if (player == 2) {
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
     * Scatter the stones from player's own bank on the game board, 1 stone per square
     * When there are no more stones for the player to play on their side
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

    public String toString(){
        return String.format("Board %s", Arrays.toString(board));
    }



}










