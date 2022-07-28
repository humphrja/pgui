package pgui;

import processing.core.PGraphics;
import pgui.win.*;

public class Element {
    public Window window; // This is the window within which the element exists
    public Palette palette; // Colour palette of the element, usually the same as the window

    // Each window has its own colour palette. This palette is transferred to the
    // interface elements within the window

    // Used for most elements that exist within a window
    public Element(Window win) {
        window = win;
        palette = window.palette;
    }

    // Used for main windows that do not have a parent window
    public Element(Palette cols) {
        palette = cols;
    }

    // All elements have a display() method
    public void display(PGraphics c) {
        ;
    }
}