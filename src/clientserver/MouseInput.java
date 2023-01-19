package clientserver;

import java.io.Serializable;

public record MouseInput(int mouseX, int mouseY) implements Serializable {
}
