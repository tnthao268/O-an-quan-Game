/**
 * @author Ngoc Thao Tran
 * @since jdk-version 19.0
 * @version 1.0
 * @see view
 * @see processing.core.PGraphics
 */


package view;

import processing.core.PGraphics;

/**
 * Class to create the 2 big stones which are on the sides of the board
 */

public class Big_stone {
    /**
     * X Coordinate, Y Coordinate, width and height of the big stone's image
     */

    int x,y,width,height;

    /**
     * Constructor of class Big_stone, to create object big stone
     * @param x  X-Coordinate of stone's image
     * @param y  Y-Coordinate of stone's image
     * @param width width of stone's image
     * @param height height of stone's image
     */

    Big_stone (int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
    }

    /**
     * Draw the big stone
     * @param graphic PGraphics object
     */
    void draw(PGraphics graphic){
            graphic.ellipse (x, y, width, height);
        }
    }

