package model;

public class Test_game {
    public static void main(String[] args){
        IModel g = Game.newGame();
        while (!g.isEndgame()){
            g = g.play(g.randomMove());
            System.out.println(g);
            //problem3: rai quan
        }
    }
}
