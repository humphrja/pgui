package pgui.txt; // This indicates the name of the library from which to import (pgui.Text.*)

import pgui.Element;
import pgui.win.Window;
import processing.core.*;
// import processing.core.PApplet.*;

// The Text object is used to display text to the screen, like a textbox or a title

public class Text extends Element {
    public String content;
    public float x, y;
    int stroke;
    int alignX, alignY, size;

    boolean underline;
    float underlineLength;

    public Text(String t, float xpos, float ypos, int tsize, Window window) {
        super(window);

        content = t;
        x = xpos;
        y = ypos;
        stroke = palette.stroke;
        size = tsize;
        alignX = PConstants.CENTER; // Default align is CENTER, CENTER
        alignY = PConstants.CENTER;
        underline = false;
    }

    public void display(PGraphics c) {
        c.textAlign(alignX, alignY);
        c.textSize(size);

        c.noFill();
        c.fill(stroke);

        c.text(content, x, y);

        if (underline) {
            c.stroke(stroke);
            c.strokeWeight(4);
            c.line(x - underlineLength / 2, y + 5, x + underlineLength / 2, y + 5);
        }
    }

    public void align(int ax, int ay) {
        alignX = ax; // LEFT, CENTER or RIGHT
        alignY = ay; // TOP, CENTER or BOTTOM
    }

    public void underline(float l) {
        underline = true;
        underlineLength = l;
    }
}