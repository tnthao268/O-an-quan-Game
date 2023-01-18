package View;

import processing.core.PGraphics;

public class Big_stone {
        int x,y,width,height;

        Big_stone (int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void draw(PGraphics graphic){
            //raphic.stroke(250,30);
            graphic.ellipse (x, y, width, height);
        }
    }

