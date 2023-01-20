package clientserver;

import java.io.Serializable;


public record CompMove(int pos, int dir) implements Serializable {
}
