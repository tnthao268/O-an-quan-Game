package view;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

class Stone {
    int x, y;
    PImage pic;

    Stone(PImage pic, int x, int y) {
        this.pic = pic;
        this.x = x;
        this.y = y;
    }

    void display(PGraphics g) {
        g.imageMode(PConstants.CENTER);
        g.image(pic, x, y, 60, 60);
    }
}
