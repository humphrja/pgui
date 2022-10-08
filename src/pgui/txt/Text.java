package pgui.txt; // This indicates the name of the library from which to import (pgui.Text.*)

import pgui.type.Element;
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
        if (hidden){return;}

        c.textAlign(alignX, alignY);
        c.textSize(size);

        c.noFill();
        c.fill(palette.stroke);

        c.text(content, x, y);

        // Draw a box around the text object to indicate tab index
        if (tabbed()){
            outline(c);
        }

        if (underline) {
            c.stroke(palette.stroke);
            c.strokeWeight(4);
            c.line(x - underlineLength / 2, y + 5, x + underlineLength / 2, y + 5);
        }
    }

    void outline(PGraphics c){
        c.noFill();
        c.stroke(palette.stroke, 200);

        float minX;
        float minY;
        float boxWidth = width();
        float boxHeight = size;

        // Account for different alignments
        switch (alignX){
            case (PConstants.LEFT):
                minX = x;
                break;
            case (PConstants.CENTER):
                minX = x - boxWidth/2;
                break;
            case (PConstants.RIGHT):
                minX = x - boxWidth;
                break;
            default:
                minX = 0;
                break;
        }

        switch (alignY){
            case (PConstants.TOP):
                minY = y;
                break;
            case (PConstants.CENTER):
                minY = y - boxHeight/2;
                break;
            case (PConstants.BOTTOM):
                minY = y - boxHeight;
                break;
            default:
                minY = 0;
                break;
        }

        c.rect(minX, minY, boxWidth, boxHeight);
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

    /**
     * Returns the width of the text on screen
     * @return Text's width
     * @see <a href="https://processing.org/reference/textWidth_.html">textWidth()</a>
     */
    public float width(){
        sketch.textSize(size);
        return sketch.textWidth(content);
    }
}