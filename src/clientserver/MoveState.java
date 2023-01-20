package clientserver;

import java.io.Serializable;



public class MoveState implements Serializable {

    public static void main(String[] args){
        MoveState m = new MoveState(10,-1);
        System.out.println(m);
    }
    public int pos,dir;

    public MoveState(int pos, int dir){
        this.pos = pos;
        this.dir = dir;
    }

    public String toString(){
        return String.format("Position %d | Direction %d", pos,dir);
    }
}