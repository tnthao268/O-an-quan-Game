package clientserver;

import java.io.Serializable;
//test with record

public record CompMove(int pos, int dir) implements Serializable {
}
