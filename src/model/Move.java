/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see model
 */


package model;

/**
 * This class implements move chosen by player of each turn
 */

public class Move {

    /**
     *  Position of the square that is chosen to play
     */

    public int position;

    /**
     * Direction of scattering stones, left : direction = 1, right: direction = -1
     */
    public int direction;

    /**
     * Private constructor of the class move
     * @param position position of the move
     * @param direction direction of the move
     */

    private Move(int position, int direction) {
        this.position = position;
        this.direction = direction;
    }

    /**
     * Method to return new move
     * @param position position of the move
     * @param direction direction of the move
     * @return new move
     */

    public static Move of(int position, int direction) {
        return new Move(position, direction);
    }

    /**
     * Text presentation of the move which is played
     * @return String presentation of position and direction of the move
     */
    @Override
    public String toString(){
        return String.format("Position %d | Direction %s", position, direction);}

}
