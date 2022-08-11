package pgui.win;

/**
 * A datatype for storing and accessing a colour palette.
 *
 *  This class is used for organising different colours associated with an object into one class
 *
 *  Each {@link Window} has its own colour palette.
 *  This palette is transferred to each {@link pgui.Element} within the window.
 */
public class Palette {
    public int background, stroke, primary, highlight, select;

    /**
     * Use the <a href="https://processing.org/reference/color_.html">color()</a> method for the parameters.
     *
     * @param colors The colours stored within the Palette
     */
    public Palette(int... colors) {
        background = colors[0];
        stroke = colors[1];
        primary = colors[2];
        highlight = colors[3];
        select = colors[4];
    }
}
