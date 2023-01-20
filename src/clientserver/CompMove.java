package clientserver;

import java.io.Serializable;

/**
 * Data structure to send move of the computer to other thread
 * @param pos Position of computer's move
 * @param dir Direction of computer's move
 */
public record CompMove(int pos, int dir) implements Serializable {
}
