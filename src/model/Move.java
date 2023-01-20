package model;

public class Move {
    public int position;
    public int direction;

    //links: -1, rechts:+1
    private Move(int position, int direction) {
        this.position = position;
        this.direction = direction;
    }

    public static Move of(int position, int direction) {
        return new Move(position, direction);
    }
    //public  String toString(){return Integer.toString(position);}

}
