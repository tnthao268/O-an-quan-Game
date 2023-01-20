package View;

import Controller.IController;
import Controller.IView;
import Model.IModel;
import Model.Move;
import clientserver.ClientServerThread;
import clientserver.GameState;
import clientserver.MouseInput;
import clientserver.MoveState;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Arrays;
import java.util.List;

  /*problems: ok 1. lam quan stones thay doi theo logic
              ok 2. graphic thay doi sau khi human choi, cho 1 luc roi cho may tinh choi, graphic up doi
              ok 3. text: number cua so da
                ok 4. khi ng bam vao o khong co gi hien len tren man hinh kh dc choi,
              ok 5. graphic rai soi tu tu -> truyen Array List cac array state cua ban co cho Graphic
               ok 6. BUG Graphic: ben phai van hien ra quan lon (1 hien ra nhu 11)
     */

//abstract ? then 2 views ?
public class GameView extends PApplet implements IView {
        private IController controller;

        public void setController(IController controller) {
        this.controller = controller;
    }

        private ClientServerThread thread;

        private GameState state ;

        private MoveState movestate;

    /**
     * A lock to avoid confilcts between draw() and ClientServerThread
     */

       private Object lock = new Object();

       public void setGameState(GameState obj) {
           synchronized (lock) {
               //state.board = controller.getGame().getBoard();
               //initially state: null
               this.state = obj;
           }
       }

       public void setMoveState(MoveState obj){
           synchronized (lock) {
               this.movestate = obj;
           }
       }
       public void handleClientInput(MouseInput obj){
           mouseX = obj.mouseX();
           mouseY = obj.mouseY();
           execute_mouse(mouseX,mouseY);
       }

    public static GameView newServer(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newServer(port, view);
        view.thread.start();
        return view;
    }

    public static GameView newClient(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newClient(ip, port, view);
        view.thread.start();
        return view;
    }

    public static GameView newAny(String ip, int port) {
        var view = new GameView();
        view.thread = ClientServerThread.newAny(ip, port, view);
        view.thread.start();
        return view;
    }












        boolean AI_turn;

        int human_score,AI_score;
        boolean hasPopped;
        PImage back_ground,stone_5, stone_3, stone_2,stone_1,stone_4,stone_6,stone_more,big_stone,blank,red_arrow,green_arrow;
        int arc_x = 320; //x-Koordinate des linken hälften Kreis
        int arc_y = 380; //y-Koordinate des linken hälften Kreis
        int arc_r = 240;
        int rect_y = arc_y - arc_r/2;
        int rect_width = arc_r*3;
        int dist = rect_width/5;
        int s_stone = 5; //number to determine which photo of stones are shown
        int s_big_stone = 10 ;
        List<Integer> a = Arrays.asList(0,1,2,3,4);
        boolean has_up = false;
        boolean has_down = false;
        boolean your_turn = true;
        int new_stone_x;
        int new_stone_y;
        int text_x;
        int text_y;
        boolean chose_left = false;
        boolean chose_right = false;
        int direction;
        int colour;
        int colour1;
        int colour2;
        int i; // index of position of stone field

        Big_stone s1 = new Big_stone(arc_x-30,arc_y+80,30,50);
        Big_stone s2= new Big_stone(arc_x+rect_width+30,arc_y-60,30,50);


        //variable tells for_loop to stop when array in copy_board equals board
        boolean equal = false;

        boolean drawsetup = true;


        boolean red = false;


        public void settings(){size(1300,700);}
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

    public void draw() {

        synchronized (lock) {
            if (thread.isConnected() & state != null ) {


                //drawsetup just one time: start of the game
                if (drawsetup) {
                    drawSetup();
                    drawsetup = false;
                }


                human_turn();

                controller.return_draw_score();

                controller.return_which_turn();

                    //System.out.println("Board is " + Arrays.toString(game.getBoard()));

                controller.return_win();



                //thread.send(new MouseInput(mouseX, mouseY));
                state= new GameState(controller.getGame().getBoard());
                thread.send(state);
            }
        }
    }


    public void mousePressed() {
            thread.send(new MouseInput(mouseX, mouseY));

            //wenn Player auf der Seite gehört zu Computer von dem Spielbrett spielt
            execute_mouse(mouseX,mouseY);

        }

        public void execute_mouse(int mouseX, int mouseY){
            for (int i : a) {
                if (mouseX > arc_x + dist * i && mouseX < (arc_x + dist * i + stone_5.width)
                        && mouseY < arc_y && mouseY > (arc_y - stone_5.height)) {

                    System.out.println("It is computer side, please play on the other side");

                }
                if (mouseX > arc_x + dist * i && mouseX < (arc_x + dist * i + stone_5.width)
                        && mouseY > arc_y && mouseY < (arc_y + stone_5.height) && your_turn) {

                    has_down = true;
                    this.i = i;
                    red = true;
                    circle_red(i);


                    if (chose_left) {
                        if (!controller.is_empty_field()) {
                            controller.save_human_move_left();

                            System.out.println("Pos = " + Move.of(i + (5 - i) * 2 + 1, 1).position + "Direction" + direction);
                            //System.out.println("Human score = " + human_score);
                            your_turn = false;
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
                            your_turn = false;
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

                    colour = color(255);
                }
            }
            if (mouseX < green_arrow.width / 2 + 580 && mouseX > 580 - green_arrow.width / 2 && mouseY > 600 - green_arrow.height / 2 && mouseY < 600 + green_arrow.height / 2) {
                direction = 1;
                colour1 = color(128, 186, 36); //green
                chose_left = true;
                //text_left_right();


            }
            if (mouseX < red_arrow.width / 2 + 720 && mouseX > 720 - red_arrow.width / 2 && mouseY > 600 - red_arrow.height / 2 && mouseY < 600 + red_arrow.height / 2) {
                direction = -1;
                colour2 = color(184, 0, 64); //red
                chose_right = true;
                //text_left_right();


            }
        }



        //if chose_left, chose_right

        public IModel human_play_left(IModel game){
            return game.play(Move.of(i+(5-i)*2+1,1));
        }

        public IModel human_play_right(IModel game){
            return game.play(Move.of(i+(5-i)*2+1,-1));
        }

    /**
     * Avoid user choosing field without stones
     * @param game game Object
     * @return false when field chosen is not empty
     */

    public boolean field_is_empty(IModel game){
            if (game.getBoard()[i+(5-i)*2+1] == 0) {
                return true;
            }
            return false;
        }



    /** Method to draw human_score and computer score text
     *
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
     */

    public void draw_number_stones(int [] board, int i, boolean up){
        blank.resize(30,30);


        fill(0);
        if (!up) {
            image(blank,arc_x + dist * (i - 1) + 24, arc_y + 20);
            text(String.valueOf(board[i + (5 - i) * 2 + 2]),arc_x + dist * (i - 1) + 10, arc_y + 30 );
        }
        else {
            image(blank,arc_x + dist * (i - 1) + 24, arc_y - arc_r / 2 + 20);
            text(String.valueOf(board[i]), arc_x + dist * (i - 1) + 10, arc_y - arc_r / 2 + 30);
        }

    }


        // makes the field chosen turn red/pink

        void circle_red(int i){
            if(red) fill(252, 199, 199);
            else fill(255);
            noStroke();
            // when it is human turn (has_down = true)
            if (has_down) ellipse(arc_x + dist * i + 20, arc_y + 100, 20, 20);
            // else draw on computer side
            else ellipse(arc_x + dist * i + 20, arc_y - 20 , 20, 20);
        }

        //draw states of the board of each move
        // runs only when the array element which is state of the board during the move does not equal the last state of the board yet
        //
        public void draw_step_stones(IModel game){

            if (!equal) {
                for (int[] b : game.getCopyBoardlist()) {
                    red = false;
                    circle_red(i);
                    draw_small_stones_down_tem(b);
                    draw_small_stones_up_tem(b);
                    //delay(2000);
                    if (Arrays.equals(b, game.getBoard())) {
                        System.out.println("Equal");
                        equal = true;
                        game.getCopyBoardlist().clear();
                        break;
                    }
                }

            }
        }

        //method to draw text which is number of stones in each field


        //draw initial state of the game
        public void drawSetup(){
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
                fill(colour);
                textSize(30);
                //fill(colour2);
                text(String.valueOf(s_stone), arc_x + dist * (i - 1) + 10, arc_y + 30);
                text(String.valueOf(s_stone), arc_x + dist * (i - 1) + 10, arc_y - arc_r / 2 + 30);
                // Zwei größer Steine als Königen zeigen
                fill(255, 255, 0);
                strokeWeight(2);
                s1.draw(super.g);
                s2.draw(super.g);
                //new Stone(stone_5,arc_x-50,arc_y).display(g);
                textSize(30);
                fill(0, 0, 0);
                text(String.valueOf(s_big_stone), arc_x + dist * (0 - 1) + 10, arc_y - arc_r / 2 + 29 );
                text(String.valueOf(s_big_stone), arc_x + dist * (6 - 1) + 20, arc_y + 25);
            }
            if (has_down) {
                //new Stone(blank, new_stone_x,new_stone_y).display(g);
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



    /*

    public void mouseReleased(){
        draw_step_stones();
    }

     */



        void human_turn(){
            if(!your_turn) {
                state.board = controller.getGame().getBoard();
                controller.return_draw_step_stones();
                System.out.println("Drawed");
            }
        }



       public void which_turn(IModel game){
            if (!your_turn && !game.isEndgame()) {
                delay(6000);
                System.out.println("It is computer's turn");
                controller.return_computer_turn();
                your_turn = true;
            }
        }


        public void computer_turn(IModel game) {
            has_down = false;
            textSize(25);
            fill(0,100,100);
            text("Computer plays!", 520,100);

            movestate = new MoveState(comp_pos,comp_dir);
            thread.send(movestate);

            if(!thread.isServer())controller.save_computer_move();
            else controller.thread_play_computer_move();

            red = true;
            circle_red(i);
            System.out.println("Your turn.");
            equal = false;
            delay(6000);
            controller.return_draw_step_stones();

        }

    /**
     * Play move of computer from another thread (server)
     * @param game Object game
     * @return play the move of computer
     */
    public IModel thread_computer_move(IModel game){
        i = movestate.pos - 1;
        return game.play(Move.of(movestate.pos,movestate.dir));
    }

    int comp_pos, comp_dir;

    /**
     * Play the move of computer
     * @param game Object game
     * @return play the move of computer
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

            //System.out.println("Computer pos = " + computer_move.position + "Direction = " + direction);
            i = computer_move.position - 1;
            return game.play(computer_move);
        }
        public void win(IModel game) {
            if(game.isEndgame()) {
                fill(255,100,100);
                textSize(50);
                String s = game.getHuman_score() > game.getAI_score() ? "You won!" : "Sorry.Computer won!";
                String tie = "Tie!";
                text(s,500,250);
                if (game.getHuman_score() == game.getAI_score()) text(tie,500,150);
                //drawsetup = true;
                //drawSetup();
            }
        }
        void HasPopped_up(int i){
            new_stone_x = arc_x - dist / 2 + dist*(i+1);
            new_stone_y = rect_y + dist / 2 - 10;
            text_x = arc_x + dist * i + 10;
            text_y = arc_y - arc_r / 2 + 30;
        }

        void HasPopped_down(int i){
            new_stone_x = arc_x - dist / 2 + dist*(i+1);
            new_stone_y = rect_y + dist / 2 * 3 - 30;
            text_x = arc_x + dist * i + 10;
            text_y = arc_y + 30;
        }

        PImage img;
        PImage img_bigStone;


        //number of stones define which pictures are loaded and shown

        void num_stone(int num){
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

        //draw stone field when number of stones = num, boolean up: true when computer side
        void setImage(int i, int num, boolean up){
            //if num < 10 and i = 0 or 7 then put blank image on the big stone postition

            num_stone(num);

        /*
        if (num < 10 && (i==7 || i==0)) {
            img_bigStone = blank;
            blank = loadImage("blank.png");
        }

        if (num > 10 && (i==7 || i==0)){
            img_bigStone = big_stone;
            big_stone = loadImage("big_stone.png");
        }

         */




            //new Stone(stone_5, arc_x - dist / 2 + dist * i, rect_y + dist / 2 * 3 - 30).display(g);

            //if i == 7 or 10 and num < 10 then draw blank image on the position of big stone, num > 10 draw big stone there.
            //Big_stone s1 = new Big_stone(arc_x-30,arc_y+80,30,50);
            //    Big_stone s2= new Big_stone(arc_x+rect_width+30,arc_y-60,30,50)
            if (up) {
                image(img,arc_x - dist / 2 + dist * i, rect_y + dist / 2 - 10,60,60);
                //if (num != 10 && (i==0)) image(img_bigStone,arc_x-30,arc_y+80,30,50);
            }
            else {
                image(img,arc_x - dist / 2 + dist * i, rect_y + dist / 2 * 3 - 30,60,60);
                //if (num != 10 && i==7) image(img_bigStone, arc_x+rect_width+30,arc_y-60,30,50);
            }

            textSize(30);
            text(String.valueOf(num),text_x,text_y);
        }

        void set_big_stone_image(int i, int[] b, boolean up){
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
         * Draw images of stones in the 2 "Big Stone" fields
         * @param i position in the board array
         * @param num number of stones from b[i] position
         */

        void set_small_stones_near_big_stone(int i, int num){
            num_stone(num);
            if (i == 0) {
                image(img,arc_x- 40, arc_y - 20 , 60,60);
            }
            if (i == 6) image(img,arc_x + rect_width + 70, arc_y + 10 ,60,60);
        }

        void draw_small_stones_up_tem(int [] b){
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

        //b[11],10,8,9,7
        void draw_small_stones_down_tem(int[] b){
            //for every temporary board in copy list
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

            /*
              if (b[i + (5 - i) * 2+2] > 6) {
                    if(b[i + (5 - i) * 2+2] < 10) { // <= 10 ??
                        setImage(i, b[i + (5 - i) * 2+2] ,  false);
                        //set_big_stone_image(i + (5 - i) * 2+2,b,false);
                    }
                    else if ( (i + (5 - i) * 2+2) == 6 && b[i + (5 - i) * 2+2] >= 10) {
                        setImage(i, b[i + (5 - i) * 2+2] - 10 ,  false);
                        set_big_stone_image(i + (5 - i) * 2+2,b,false);
                    }
                }

                if ((i + (5 - i) * 2+2) == 6 && b[i + (5 - i) * 2+2]< 10) set_big_stone_image(i + (5 - i) * 2+2,b,false);

             */
                //if (b[i + (5 - i) * 2+2] > 6) setImage(i, b[i + (5 - i) * 2+2],  false);
                //delay(500);
            }
            //System.out.println(Arrays.toString(b));
        }





    /*
    public void mouseClicked(){
        if (mouseX<green_arrow.width+580 && mouseX>580 && mouseY > 600 && mouseY < 600+green_arrow.height){
            direction = -1;
        }
        if (mouseX<red_arrow.width+720 && mouseX>720 && mouseY > 800 && mouseY<800+red_arrow.height){
            direction = 1;
        }
    }
*/
        //void computer_turn(){

    /*
    public void next(){
        text(String.valueOf())
    }
    */






    }





