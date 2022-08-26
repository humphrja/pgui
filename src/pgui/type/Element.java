package pgui.type;

import processing.core.PApplet;
import processing.core.PGraphics;
import pgui.win.*;

/**
 * An element is an object within a Graphical User Interface (GUI).
 *
 * @author Jack Humphreys
 * @version 1.0
 */
public class Element {
    /**
     * This is the window within which the element exists
     */
    public Window window;

    /**
     * Colour palette of the element, usually the same as the window
     */
    public Palette palette;
    // Constants
    Palette enabledPalette;
    Palette disabledPalette;

    protected PApplet sketch;

    // 'protected' -> can be accessed by subclasses
    protected boolean disabled = false;
    protected boolean hidden = false;

    public String ID;

    // Each window has its own colour palette. This palette is transferred to the
    // interface elements within the window

    /** Used for most elements that exist within a window
     *
     * @param win The window object within which the element is situated.
     * @see Window
     */
    public Element(Window win) {
        window = win;
        palette = window.palette;
        sketch = win.sketch;

        enabledPalette = palette.copy();
        disabledPalette = palette.fade(window.palette.background, (float) 0.7, sketch);
    }

    /** Used for main windows that do not have a parent window
     *
     * @param cols The colour palette associated with the Element.
     * @see Palette
     */
    public Element(Palette cols, PApplet sketch) {
        palette = cols;
        enabledPalette = palette.copy();
        this.sketch = sketch;
    }

    /**
     * Draws the object onto the canvas.
     *
     * @param c The PGraphics object (canvas) for which to draw to.
     */
    public void display(PGraphics c) {
    }

    /**
     * Sets the element to be visible and responsive
     */
    public void enable(){
        disabled = false;
        hidden = false;
        palette = enabledPalette;
    }

    /**
     * Sets the element to be visible but unresponsive
     */
    public void disable(){
        disabled = true;
        hidden = false;
        palette = disabledPalette;
    }

    /**
     * Sets the element to be invisible and unresponsive
     */
    public void hide(){
        disabled = true;
        hidden = true;
    }

    public void setID(String ID){
        this.ID = ID;
    }
}