/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see view
 * @see processing.core.PGraphics
 * @see processing.core.PConstants
 * @see processing.core.PImage
 */


package view;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Class to create small stone objects which are played by the players
 */
class Stone {

    /**
     * X Coordinate and Y Coordinate of the stone image
     */
    int x, y;
    /**
     * Image of the stone depends on the number of stones on each field
     */
    PImage pic;

    /**
     * Constructor of class Stone, to create object Stone
     * @param pic image of the stone
     * @param x  X Coordinate of stone's image
     * @param y  Y Coordinate of stone's image
     */

    Stone(PImage pic, int x, int y) {
        this.pic = pic;
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the stone image
     * @param g PGraphics object
     */

    void display(PGraphics g) {
        g.imageMode(PConstants.CENTER);
        g.image(pic, x, y, 60, 60);
    }
}
