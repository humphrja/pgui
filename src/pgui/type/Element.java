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

    /**
     * A reference to the current Processing sketch
     */
    protected PApplet sketch;

    // 'protected' -> can be accessed by subclasses
    /**
     * True indicates Element cannot be interacted with
     */
    protected boolean disabled = false;
    /**
     * True indicates Element is invisible & disabled
     */
    protected boolean hidden = false;

    boolean displayedLast = false;

    /**
     * A unique set of characters identifying the element
     */
    public String ID;

    /**
     * True indicates the element is being selected via tab key
     * Use {@link Element#tabbed()} to get & {@link Element#setTabbed(boolean)} to set
     */
    boolean tabbed = false;
    /**
     * Indicates the equivalent of a mouse press, but for keys ENTER/RETURN/space
     */
    public boolean triggerKeyPressed = false;
    /**
     * Previous frame's {@link Element#triggerKeyPressed}
     */
    public boolean pTriggerKeyPressed = false;


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

    /**
     * Returns true if element is disabled
     * @return {@link Element#disabled}
     */
    public boolean isDisabled(){
        return disabled;
    }

    /**
     * Returns true if element is hidden
     * @return {@link Element#hidden}
     */
    public boolean isHidden(){
        return hidden;
    }

    public void setPalette(Palette p) {
        palette = p.copy();
    }

    /**
     * Sets the ID of the Element.
     * @param ID A unique ID.
     */
    public void setID(String ID){
        this.ID = ID;
    }

    @Override
    public String toString(){
        return ID;
    }

    /**
     * Converts a class name into a 3 character long abbreviation.
     *
     * BTN: Button
     * RAD: RadioButton
     * RBG: RadioButtonGroup
     * SCW: ScrollWindow
     * SLD: Slider
     * SWT: Switch
     * TXT: Text
     * WIN: Window
     *
     * @return Abbreviated string.
     * @see <a href=https://humphrja.github.io/pgui/doc/allclasses-index.html> All Classes </a>
     */
    public String abbr(){
        switch (this.getClass().getSimpleName().toString()){
            case ("Button"):            return "BTN";
            case ("RadioButton"):       return "RAD";
            case ("RadioButtonGroup"):  return "RBG";
            case ("ScrollWindow"):      return "SCW";
            case ("Slider"):            return "SLD";
            case ("Switch"):            return "SWT";
            case ("Text"):              return "TXT";
            case ("Window"):            return "WIN";
            default:                    return  null;
        }
    }

    /**
     * True if element is currently being selected via tab
     * @return If element is currently being selected via tab
     */
    public boolean tabbed(){
        return tabbed && window.tabbing;
    }

    /**
     * Toggles if element is currently being selected via tab
     * @param b True/False
     */
    public void setTabbed(boolean b){
        tabbed = b;
    }

    /**
     * Instructs the parent window to display this element last.
     * This is the same as 'send to front' for image overlap.
     */
    public void displayLast() {
        window.displayOrder = window.appendElement(this, window.displayOrder);
        displayedLast = true;
//        window.elements = window.removeElement(this, window.elements);
    }

    /**
     * Indicates if the element has been instructed to display last (or near last).
     * @return True if element is displayed last
     */
    public boolean isDisplayedLast() {
        return displayedLast;
    }
}