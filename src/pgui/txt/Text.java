package pgui.txt; // This indicates the name of the library from which to import (pgui.Text.*)

import pgui.Element;
import pgui.win.Window;
import processing.core.*;
// import processing.core.PApplet.*;

// The Text object is used to display text to the screen, like a textbox or a title

/**
 * An object for displaying text within a {@link Window}.
 */
public class Text extends Element {
    /**
     * The actual text to be displayed.
     */
    public String content;
    /**
     * The position of the text in its window.
     */
    public float x, y;
    int alignX, alignY, size;

    boolean underline;
    float underlineLength;

    /**
     * @param t The text to be displayed
     * @param xpos The position of the text in its window
     * @param ypos The position of the text in its window
     * @param tsize The text size
     * @param window The parent Window the Text belongs to
     */
    public Text(String t, float xpos, float ypos, int tsize, Window window) {
        super(window);

        content = t;
        x = xpos;
        y = ypos;
        size = tsize;
        alignX = PConstants.CENTER; // Default align is CENTER, CENTER
        alignY = PConstants.CENTER;
        underline = false;
    }

    public void display(PGraphics c) {
        c.textAlign(alignX, alignY);
        c.textSize(size);

        c.noFill();
        c.fill(palette.stroke);

        c.text(content, x, y);

        if (underline) {
            c.stroke(palette.stroke);
            c.strokeWeight(4);
            c.line(x - underlineLength / 2, y + 5, x + underlineLength / 2, y + 5);
        }
    }

    /**
     * Aligns the text using Processing's <a href="https://processing.org/reference/textAlign_.html">textAlign()</a> method.
     *
     * @param ax LEFT, CENTER or RIGHT
     * @param ay TOP, CENTER or BOTTOM
     * @see PConstants
     */
    public void align(int ax, int ay) {
        alignX = ax; // LEFT, CENTER or RIGHT
        alignY = ay; // TOP, CENTER or BOTTOM
    }

    /**
     * Underlines the text.
     *
     * @param l Length of underline
     */
    public void underline(float l) {
        underline = true;
        underlineLength = l;
    }
}