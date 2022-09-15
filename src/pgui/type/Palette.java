package pgui.type;

import pgui.win.Window;
import processing.core.PApplet;

/**
 * A datatype for storing and accessing a colour palette.
 *
 *  This class is used for organising different colours associated with an object into one class
 *
 *  Each {@link Window} has its own colour palette.
 *  This palette is transferred to each {@link Element} within the window.
 */
public class Palette {

    /**
     * Background colour for Windows
     */
    public int background;
    /**
     * The stroke colour for elements - shape outlines, text colour, etc.
     */
    public int stroke;
    /**
     * Main fill colour for elements
     */
    public int primary;
    /**
     * Fill when interactive elements are hovered over
     */
    public int highlight;
    /**
     * Fill when elements are selected
     */
    public int select;

    /**
     * This defines the colours stored within the Palette.
     * Use the <a href="https://processing.org/reference/color_.html">color()</a> method for each parameters.
     *
     * @param background {@link Palette#background}
     * @param stroke {@link Palette#stroke}
     * @param primary {@link Palette#primary}
     * @param highlight {@link Palette#highlight}
     * @param select {@link Palette#select}
     */
    public Palette(int background, int stroke, int primary, int highlight, int select) {
        this.background = background;
        this.stroke = stroke;
        this.primary = primary;
        this.highlight = highlight;
        this.select = select;
    }

    /**
     * A copy method to create a new Palette object for later alterations
     * @return Equivalent Palette object
     */
    public Palette copy(){
        return new Palette(background, stroke, primary, highlight, select);
    }


    /**
     * Returns a faded copy of the current Palette.
     * Linearly interpolates between each colour in the Palette and colourTo.
     * @see <a href="https://processing.org/reference/lerpColor_.html">lerpColor()</a>
     * @param colourTo Colour to fade towards - background colour of parent window
     * @param amount Degree of fading, between 0.0 and 1.0
     * @param sketch A reference to Processing sketch
     * @return A new faded Palette object
     */
    public Palette fade(int colourTo, float amount, PApplet sketch){
        return new Palette(
                sketch.lerpColor(background, colourTo, amount),
                sketch.lerpColor(stroke, colourTo, amount),
                sketch.lerpColor(primary, colourTo, amount),
                sketch.lerpColor(highlight, colourTo, amount),
                sketch.lerpColor(select, colourTo, amount)
        );
    }
}