/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see clientserver
 * @see java.io.Serializable
 */


package clientserver;

import java.io.Serializable;

/**
 * Record to transfer data of mouse input
 * @param mouseX position of mouse on x-axis
 * @param mouseY position of mouse on y-axis
 */

public record MouseInput(int mouseX, int mouseY) implements Serializable {
}
