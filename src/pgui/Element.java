package pgui;

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
    }

    /** Used for main windows that do not have a parent window
     *
     * @param cols The colour palette associated with the Element.
     * @see Palette
     */
    public Element(Palette cols) {
        palette = cols;
    }

    /**
     * Draws the object onto the canvas.
     *
     * @param c The PGraphics object (canvas) for which to draw to.
     */
    public void display(PGraphics c) {
    }
}