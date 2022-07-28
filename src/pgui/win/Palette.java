package pgui.win;

// This class is used for organising different colours associated with an object into one class

// Each window has its own colour palette. This palette is transferred to the
// interface elements within the window

public class Palette {
    public int background, stroke, primary, highlight, select;

    public Palette(int... colors) {
        background = colors[0];
        stroke = colors[1];
        primary = colors[2];
        highlight = colors[3];
        select = colors[4];
    }
}
