/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see view
 * @see controller.IController
 * @see controller.IView
 * @see model.IModel
 * @see model.Move
 * @see clientserver
 * @see processing.core.PApplet
 * @see processing.core.PImage
 * @see java.util.Arrays
 * @see java.util.List
 */


package view;

import controller.IController;
import controller.IView;
import model.IModel;
import model.Move;
import clientserver.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Arrays;
import java.util.List;

/**
 * GUI implementation of the game "O an quan" , with Processing library.
 * This class implements IView interface
 * <p>
 * An example of playing this game online
 * <a href ="https://gamevui.vn/o-an-quan/game">... </a>
 *
 * </p>
 * <p>
 * Source codes:
 * Professor Dr. Martin Weigel: "Lecture codes: VW10 - Pong.java"
 * </p>
 */


public class GameView extends PApplet implements IView {

    /**
     * Standard constructor
     */
    public GameView(){};

    /**
     * IController object
     */
    private IController controller;

    /**
     * To access controller from outside the class
     * @param controller IController object
     */

    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Thread for client-server communication
     */

    private ClientServerThread thread;

    /**
     * State of the board to be transmitted
     */
    private GameState state ;


    /**
     * A lock to avoid conflicts between draw() and ClientServerThread
     */

    private final Object lock = new Object();

    /**
     * Handles input as a GameState object
     * @param obj GameState object
     */

    public void setGameState(GameState obj) {
           synchronized (lock) {
               this.state = obj;
           }
    }

    /**
     * Handles input as mouseInput object
     * @param obj MouseInput object
     */

    public void handleClientInput(MouseInput obj){
           synchronized (lock) {
               mouseX = obj.mouseX();
               mouseY = obj.mouseY();
               execute_mouse(mouseX, mouseY);
           }
    }

    /**
     * Create new thread as server
     * @param ip IP address for server to connect to
     * @param port port for server to connect to
     * @return object from GameView class
     */

    public static GameView newServer(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newServer(port, view);
        view.thread.start();
        return view;
    }

    /**
     * Create new thread as client
     * @param ip IP address for client to connect to
     * @param port port for client to connect to
     * @return object from GameView class
     */

    public static GameView newClient(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newClient(ip, port, view);
        view.thread.start();
        return view;
    }

    /**
     * Create new thread which can either be server or client
     * @param ip IP address to connect to
     * @param port port to connect to
     * @return new GameView object
     */

    public static GameView newAny(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newAny(ip, port, view);
        view.thread.start();
        return view;
    }

    /**
     * Images to be drawn on the graphics
     */

    private PImage back_ground,stone_5, stone_3, stone_2,stone_1,stone_4,stone_6,stone_more,big_stone,blank,red_arrow,green_arrow;

    /**
     * x-coordinate of the left half circle of the board
     */
    private int arc_x = 320;

    /**
     * y-coordinate of the left half circle of the board
     */
    private int arc_y = 380;

    /**
     * Radius of the left half circle of the board
     */
    private int arc_r = 240;

    /**
     * Y- Coordinate of the rectangle of the board
     */
    private int rect_y = arc_y - arc_r/2;
    /**
     * Width of the rectangle of the board
     */
    private int rect_width = arc_r*3;
    /**
     * Distance of between small squares on the board
     */
    private int dist = rect_width/5;

    /**
     * Number of stones on the small squares
     */
    private int s_stone = 5;

    /**
     * Number of stones on the side half circles (which 1 big stone represent)
     */
    private int s_big_stone = 10 ;
    /**
     * Index of square's position on the board
     */
    private int i;
    /**
     * List of possible values of i
     */
    private List<Integer> a = Arrays.asList(0,1,2,3,4);

    /**
     * Returns true if upper part of the board is played
     */
    private boolean has_up = false;
    /**
     * Returns true if lower part of the board is played
     */
    private boolean has_down = false;
    /**
     * Return true if it is currently human's turn, false if it is computer's turn
     */
    private boolean human_turn = true;
    /**
     * x-coordinate of small stones' image in the half circle sides of the board
     */
    private int circle_small_stone_x;
    /**
     * y-coordinate of small stones' image in the half circle sides of the board
     */
    private int circle_small_stone_y;
    /**
     * x-coordinate of text which shows number of stones in a field of the board
     */
    private int text_x;
    /**
     * y-coordinate of text which shows number of stones in a field of the board
     */
    private int text_y;
    /**
     * Returns true if human player chooses left direction to play
     */
    private boolean chose_left = false;
    /**
     * Returns true if human player chooses right direction to play
     */
    private boolean chose_right = false;
    /**
     * Direction of the move played by human player. 1 if direction is left. -1 if direction is right
     */
    private int direction;

    /**
     * Variable tells for_loop to stop when array in copy_board list equals board
     */
    private boolean equal = false;
    /**
     * Returns true when game is set up in the beginning, falls otherwise
     */
    private boolean draw_set_up = true;
    /**
     * Returns true when small circle is drawn red, when false it is drawn white
     */

    private boolean red = false;

    /**
     * Two big stones on the sides of the board are created
     */
    Big_stone s1 = new Big_stone(arc_x-30,arc_y+80,30,50);
    /**
     * Two big stones on the sides of the board are created
     */
    Big_stone s2= new Big_stone(arc_x+rect_width+30,arc_y-60,30,50);

    /**
     * Settings of GUI with window size
     */
    public void settings(){size(1300,700);}

    /**
     * Game set up
     */
    public void setup(){

            back_ground = loadImage("cay tre.jpg");
            stone_5 = loadImage("stone5.png");
            big_stone= loadImage("big_stone.png");
            stone_6 = loadImage("stone6.png");
            stone_4 = loadImage("stone4.png");
            stone_3 = loadImage("stone3.png");
            stone_2 = loadImage("stone2.png");
            stone_1 = loadImage("stone1.png");
            stone_more= loadImage("stone_more.png");
            blank = loadImage("blank.png");
            red_arrow = loadImage("red_arrow.png");
            green_arrow = loadImage("green_arrow.png");
            back_ground.resize(1300,700);
            //red_arrow.resize(80,45);
            background(back_ground);
            state = new GameState(controller.getGame().getBoard());
    }

    /**
     * Draw the animation
     */

    public void draw() {

        synchronized (lock) {
            if (thread.isConnected() ) {


                //draw_set_up just one time: start of the game
                if (draw_set_up) {
                    drawSetup();
                    draw_set_up = false;
                }

                end_human_turn();
                controller.return_draw_score();

                controller.return_which_turn();
                //System.out.println("Board is " + Arrays.toString(game.getBoard()));

                controller.return_win();

                textSize(25);
                fill(0,100,100);
                text("Human's turn", 520,100);

            }
        }
    }

    /**
     * When mouse is pressed, human move is played and automatically computer move is played
     */


    public void mousePressed() {
            thread.send(new MouseInput(mouseX, mouseY));
            execute_mouse(mouseX,mouseY);

    }

    /**
     * Execute player's move based on the position of the pressed mouse,
     * which indicates position and direction of the move
     * @param mouseX x-coordinate of mouse
     * @param mouseY y-coordinate of mouse
     */

    private void execute_mouse(int mouseX, int mouseY){
            for (int i : a) {
                if (mouseX > arc_x + dist * i && mouseX < (arc_x + dist * i + stone_5.width)
                        && mouseY < arc_y && mouseY > (arc_y - stone_5.height)) {

                    System.out.println("It is computer side, please play on the other side");

                }
                if (mouseX > arc_x + dist * i && mouseX < (arc_x + dist * i + stone_5.width)
                        && mouseY > arc_y && mouseY < (arc_y + stone_5.height) && human_turn) {

                    has_down = true;
                    this.i = i;
                    red = true;
                    circle_red(i);


                    if (chose_left) {
                        if (!controller.is_empty_field()) {
                            controller.save_human_move_left();

                            System.out.println("Pos = " + Move.of(i + (5 - i) * 2 + 1, 1).position + "Direction" + direction);
                            //System.out.println("Human score = " + human_score);
                            human_turn = false;
                            chose_left = false;
                            equal = false;

                            //draw_step_stones();
                            controller.return_win();
                            //which_turn();
                        } else {
                            System.out.println("Please do not choose an empty field!");
                            red = false;
                            circle_red(i);

                        }
                    }
                    if (chose_right) {
                        if (!controller.is_empty_field()) {
                            controller.save_human_move_right();
                            System.out.println("Pos = " + Move.of(i + (5 - i) * 2 + 1, -1).position + "Dir = " + direction);
                            human_turn = false;
                            chose_right = false;
                            equal = false;
                            //draw_step_stones();
                            controller.return_win();
                            //which_turn();

                        } else {
                            System.out.println("Please do not choose an empty field!");
                            red = false;
                            circle_red(i);

                        }
                    }
                    //System.out.println(Arrays.toString(game.getBoard()));

                }
            }
            if (mouseX < green_arrow.width / 2 + 580 && mouseX > 580 - green_arrow.width / 2 && mouseY > 600 - green_arrow.height / 2 && mouseY < 600 + green_arrow.height / 2) {
                direction = 1;
                chose_left = true;
                //text_left_right();


            }
            if (mouseX < red_arrow.width / 2 + 720 && mouseX > 720 - red_arrow.width / 2 && mouseY > 600 - red_arrow.height / 2 && mouseY < 600 + red_arrow.height / 2) {
                direction = -1;
                chose_right = true;
                //text_left_right();


            }
        }


    /**
     * Return game after player human chose left direction to play
     * @param game Game object
     * @return Game object
     */

    public IModel human_play_left(IModel game){
            return game.play(Move.of(i+(5-i)*2+1,1));
    }

    /**
     * Return game after player human chose right direction to play
     * @param game Game object
     * @return Game object
     */

    public IModel human_play_right(IModel game){
            return game.play(Move.of(i+(5-i)*2+1,-1));
        }

    /**
     * Avoid user choosing field without stones
     * @param game game Object
     * @return false when field chosen is not empty
     */

    public boolean field_is_empty(IModel game){
        return game.getBoard()[i + (5 - i) * 2 + 1] == 0;
    }

    /**
     * Position of the computer's move chosen by server and to be transmitted to client
     */
    private int comp_pos;
    /**
     * Direction of the computer's move chosen by server and to be transmitted to client
     */
    private int comp_dir;


    /**
     * Method to draw human_score and computer score text
     * @param game game Object
     */
    public void draw_score(IModel game){
            image(blank,450,100);
            blank.resize(500,50);
            fill(0);
            textSize(25);
            text("Computer  score : ", 250, 100);
            text(game.getAI_score(),450,100);

            image(blank,450,550);


            text("Your  score: ", 250, 550);
            text(game.getHuman_score(), 450, 550);
    }

    /**
     * Draw the number of stones on each field.
     * @param board current state of the board
     * @param i position on the board
     * @param up return true if the side of the board is computer's ,false otherwise
     */

    private void draw_number_stones(int [] board, int i, boolean up){
        blank.resize(30,30);


        fill(0);
        if (!up) {
            image(blank,arc_x + dist * (i - 1) + 24, arc_y + 20);
            text(String.valueOf(board[i + (5 - i) * 2 + 2]),arc_x + dist * (i - 1) + 10, arc_y + 30 );
        }
        else {
            image(blank,arc_x + dist * (i - 1) + 24, arc_y - (float) arc_r / 2 + 20);
            text(String.valueOf(board[i]), arc_x + dist * (i - 1) + 10, arc_y - (float) arc_r / 2 + 30);
        }

    }

    /**
     * Makes the field chosen by players having red circle
     * @param i position of the circle
     */


    private void circle_red(int i){
            if(red) fill(252, 199, 199);
            else fill(255);
            noStroke();
            // when it is human turn (has_down = true)
            if (has_down) ellipse(arc_x + dist * i + 20, arc_y + 100, 20, 20);
            // else draw on computer side
            else ellipse(arc_x + dist * i + 20, arc_y - 20 , 20, 20);
        }

    /**
     * Draw states of the board of each move
     * Runs till when array element which is state of the board during the move does not equal the last state of the board yet
     * @param game Game object
     */
    public void draw_step_stones(IModel game){

            if (!equal) {
                for (int[] b : game.getCopyBoardlist()) {
                    red = false;
                    circle_red(i);
                    draw_small_stones_down_tem(b);
                    draw_small_stones_up_tem(b);
                    delay(1000);
                    if (Arrays.equals(b, game.getBoard())) {
                        System.out.println("Equal");
                        equal = true;
                        game.getCopyBoardlist().clear();
                        break;
                    }
                }

            }
    }

    /**
     * Draw initial state of the game
     */

    private void drawSetup(){
            fill(255, 255, 255);
            strokeWeight(6);
            //Brettspiel zeigen
            arc(arc_x, arc_y, arc_r, arc_r, HALF_PI, PI + HALF_PI);
            rect(arc_x, rect_y, rect_width, arc_r);
            arc(arc_x + rect_width, arc_y, arc_r, arc_r, PI + HALF_PI, TWO_PI + HALF_PI, CHORD);
            fill(0);
            line(arc_x, arc_y, arc_x + rect_width, arc_y);
            Stone left = new Stone(green_arrow,580,600);
            //new Stone(red_arrow,red_arrow.width/2-15,red_arrow.height/2+5).display(g);
            //new Stone(green_arrow,green_arrow.width/2-15,green_arrow.height/2+5).display(g);
            left.display(g); //zeigt grüne Pfeil
            Stone right = new Stone(red_arrow,720,600);
            right.display(g); //zeigt rechte Pfeil

            for (int i = 1; i <= 5; i++) {
                fill(0);
                line(arc_x + dist * i, rect_y, arc_x + dist * i, rect_y + arc_r);
                //ellipse(arc_x-dist/2+dist*i, rect_y +dist/2,20,20);
                //Steine zeigen
                new Stone(stone_5, arc_x - dist / 2 + dist * i, rect_y + dist / 2 - 10).display(g);
                new Stone(stone_5, arc_x - dist / 2 + dist * i, rect_y + dist / 2 * 3 - 30).display(g);
                fill(0);
                textSize(30);
                //fill(color_right_arrow);
                text(String.valueOf(s_stone), arc_x + dist * (i - 1) + 10, arc_y + 30);
                text(String.valueOf(s_stone), arc_x + dist * (i - 1) + 10, arc_y - (float)arc_r / 2 + 30);
                // Zwei größer Steine als Königen zeigen
                fill(255, 255, 0);
                strokeWeight(2);
                s1.draw(super.g);
                s2.draw(super.g);
                //new Stone(stone_5,arc_x-50,arc_y).display(g);
                textSize(30);
                fill(0, 0, 0);
                text(String.valueOf(s_big_stone), arc_x + dist * (0 - 1) + 10, arc_y - (float) arc_r / 2 + 29 );
                text(String.valueOf(s_big_stone), arc_x + dist * (6 - 1) + 20, arc_y + 25);
            }
            if (has_down) {
                //new Stone(blank, circle_small_stone_x,circle_small_stone_y).display(g);
                //System.out.println(Arrays.toString(game.getBoard()));
                HasPopped_down(i);
                setImage(i+1,0,false); //draw blank image when the user clicked (chooses a stone field)

                //draw_small_stones_down ();
            }
            if(has_up){
                HasPopped_up(i);
                setImage(i+1,0,false);
            }
    }

    /**
     * Draw the board's states when human plays
     */

    private void end_human_turn(){
            if(!human_turn) {
                //state.board = controller.getGame().getBoard();
                controller.return_draw_step_stones();
                //System.out.println("Drew");
            }
    }

    /**
     * When human turn's ends, plays the computer
     * @param game Game object
     */

    public void which_turn(IModel game){
            if (!human_turn && !game.isEndgame()) {
                delay(3000);
                System.out.println("It is computer's turn");
                controller.return_computer_turn();
                human_turn = true;
            }
    }

    /**
     * Shows GUI and what happens to the game when it is computer turn
     * @param game Game Object
     */

    public void computer_turn(IModel game) {
            has_down = false;
            blank.resize(150,30);
            image(blank,580,100);
            textSize(25);
            fill(0,100,100);
            text("Computer's turn!", 520,100);

            synchronized (this) {
               if(thread.isServer()){
                   controller.save_computer_move();
                   //thread.send(new Player(controller.getGame().getPlayer()));
               }
               else {
                   try {
                       thread.sleep(6000);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
                   controller.thread_play_computer_move();
               }
            }


            red = true;
            circle_red(i);
            System.out.println("Your turn.");
            equal = false;
            delay(6000);
            controller.return_draw_step_stones();
    }



    /**
     * Play move of computer from client thread
     * @param game Game state
     */
    public void thread_computer_move(IModel game){
        System.out.println("Comp pos is " + comp_pos + " ,Comp dir is " + comp_dir);
        thread.send(new CompMove(comp_pos,comp_dir));
        i = comp_pos - 1;
        game.play(Move.of(comp_pos, comp_dir));
    }
    
    /**
     * Play the move of computer from server thread
     * @param game game's state
     * @return new game's state after the played move
     */

    public IModel computer_play(IModel game){
            Move computer_move;
            do {
                computer_move = game.randomMove();
                //game = game.play(computer_move);
            } while (game.getPlayer() == 2);
            assert computer_move.position >= 1 && computer_move.position <=5 : "Wrong position for computer!";
            comp_pos = computer_move.position;
            comp_dir = computer_move.direction;
            //thread.send(state);
            thread.send(new CompMove(comp_pos,comp_dir));

            //System.out.println("Computer pos = " + computer_move.position + "Direction = " + direction);
            i = computer_move.position - 1;
            return game.play(Move.of(comp_pos,comp_dir));
        }

    /**
     * Handles the input which are the chosen position and direction of computer's move
     * @param obj CompMove object
     */

    public void handleCompMove(CompMove obj) {
        comp_pos = obj.pos();
        comp_dir = obj.dir();

    }

    /**
     * Shows GUI and game's state when the game ends
     * @param game game's state
     */

    public void win(IModel game) {
            if(game.isEndgame()) {
                fill(255,100,100);
                textSize(40);
                String s = game.getHuman_score() > game.getAI_score() ? "You won!" : "Computer won!";
                String tie = "Tie!";
                text(s,450,250);
                if (game.getHuman_score() == game.getAI_score()) text(tie,450,150);
                //draw_set_up = true;
                //drawSetup();
            }
        }

    /**
     * When an upper square of stones is chosen to be played,
     * red circle appears on these square
     * @param i index position
     */

    private void HasPopped_up(int i){
            circle_small_stone_x = arc_x - dist / 2 + dist*(i+1);
            circle_small_stone_y = rect_y + dist / 2 - 10;
            text_x = arc_x + dist * i + 10;
            text_y = arc_y - arc_r / 2 + 30;
    }

    /**
     * When a lower square of stones is chosen to be played,
     * red circle appears on these square
     * @param i index position
     */

    private void HasPopped_down(int i){
            circle_small_stone_x = arc_x - dist / 2 + dist*(i+1);
            circle_small_stone_y = rect_y + dist / 2 * 3 - 30;
            text_x = arc_x + dist * i + 10;
            text_y = arc_y + 30;
        }

    /**
     * PImage object to show images of small stones
     */

    private PImage img;
    /**
     * PImage object to show images of big stones
     */
    private PImage img_bigStone;

    /**
     * Number of stones in a fielt define which pictures are loaded and shown
     * @param num number of stones in a field
     */


    private void num_stone(int num){
            if (num == 0) {
                img = blank;
                blank = loadImage("blank.png");
            }

            if (num == 1) {
                img = stone_1;
                stone_1 = loadImage("stone1.png");
            }
            if (num == 2) {
                img = stone_2;
                stone_2 = loadImage("stone2.png");
            }
            if (num == 3) {
                img = stone_3;
                stone_3 = loadImage("stone3.png");
            }
            if (num == 4) {
                img = stone_4;
                stone_4 = loadImage("stone4.png");
            }
            if (num == 5){
                img = stone_5;
                stone_5 = loadImage("stone5.png");
            }
            if (num == 6) {
                img = stone_6;
                stone_6 = loadImage("stone6.png");
            }
            if (num > 6 ) {
                img = stone_more;
                stone_more = loadImage("stone_more.png");
            }

        }

    /**
     * Set the images of stones in small stone field when number of stones = num
     * @param i index position of the image
     * @param num number of stones in a field
     * @param up true when it is computer side (upper part of the board)
     */

    private void setImage(int i, int num, boolean up){
            //if num < 10 and i = 0 or 7 then put blank image on the big stone position

            num_stone(num);

            if (up) {
                image(img,arc_x - (float)dist / 2 + dist * i, rect_y + (float)dist / 2 - 10,60,60);
                //if (num != 10 && (i==0)) image(img_bigStone,arc_x-30,arc_y+80,30,50);
            }
            else {
                image(img,arc_x - (float)dist / 2 + dist * i, rect_y + (float)dist / 2 * 3 - 30,60,60);
                //if (num != 10 && i==7) image(img_bigStone, arc_x+rect_width+30,arc_y-60,30,50);
            }

            textSize(30);
            text(String.valueOf(num),text_x,text_y);
        }

    /**
     * Set the images of big stones in big stone field
     * @param i index position of the image
     * @param b game's board
     * @param up true when it is computer side (upper part of the board)
     */

    private void set_big_stone_image(int i, int[] b, boolean up){
            if (b[i] < 10 ) {
                img_bigStone = blank;
                blank = loadImage("blank.png");
            }
            else if (b[i] >= 10 ) {
                img_bigStone = big_stone;
                big_stone = loadImage("big_stone.png");
            }


            if (up) image(img_bigStone,arc_x-30,arc_y+80,30,50);  // this is the big stone in lower position (but from computer (up) side)
            else image(img_bigStone, arc_x+rect_width+30,arc_y-60,30,50);
        }

        //b[1],2,3,4,5

    /**
         * Set images of small stones in the 2 "Big Stone" fields
         * @param i position in the board array
         * @param num number of stones from b[i] position
     */

    private void set_small_stones_near_big_stone(int i, int num){
            num_stone(num);
            if (i == 0) {
                image(img,arc_x- 40, arc_y - 20 , 60,60);
            }
            if (i == 6) image(img,arc_x + rect_width + 70, arc_y + 10 ,60,60);
        }

    /**
     * Draw the images of small stones in the upper stone fields
     * @param b game's board
     */

    private void draw_small_stones_up_tem(int [] b){
            //for (int[] b : game.getCopyBoardlist()) {
            for (int i = 0; i<6; i++) { //for every temporary board in copy list
                if (i != 0) setImage(i, b[i], true);
                    //if (b[i] > 6)  setImage(i, b[i],  true);
                    //delay(500);
                else {
                    if (b[i] < 10) {
                        set_small_stones_near_big_stone(0, b[i]);
                        set_big_stone_image(i, b, true);
                    }
                    else {
                        set_small_stones_near_big_stone(i, b[i] -10);
                        set_big_stone_image(i, b, true);
                    }
                }
                draw_number_stones(b,i,true);
            }
            System.out.println(Arrays.toString(b));
        }

    /**
     * Draw the images of small stones in the lower stone fields
     * @param b game's board
     */

    private void draw_small_stones_down_tem(int[] b){
            //for every temporary board in copy board list
            for (int i = 1; i<7; i++) {
                if ((i + (5 - i) * 2 + 2) != 6) setImage(i, b[i + (5 - i) * 2 + 2], false);
                else {
                    if (b[i + (5 - i) * 2 + 2] < 10) {
                        set_small_stones_near_big_stone(6, b[i + (5 - i) * 2 + 2]);
                        set_big_stone_image(i + (5 - i) * 2 + 2,b,false);
                    }
                    else if (b[i + (5 - i) * 2+2] >= 10){
                        set_small_stones_near_big_stone(i, b[i + (5 - i) * 2 + 2] -10);
                        set_big_stone_image(i + (5 - i) * 2 + 2,b,false);
                    }
                }
                draw_number_stones(b,i,false);
            }
            //System.out.println(Arrays.toString(b));
        }
}





