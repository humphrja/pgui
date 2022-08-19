package pgui.win; //            This indicates the name of the library from which to import (pgui.*)

import processing.core.*;
import java.lang.reflect.*; //  Used for accessing Method types
import java.util.Arrays; //     Used for appending to arrays

import pgui.type.Element;
import pgui.btn.*;
import pgui.txt.*;
import pgui.btn.Button;

/**
 * A Window is a class that contains a collection of 'elements' - other interface objects, such as buttons, text or other windows.
 *
 * @author Jack Humphreys
 * @version 1.3
 * @see Element
 */
public class Window extends Element {
    PApplet sketch;

    // These arrays contain the window's elements
    /**
     * Contains all {@link Button} objects within the Window.
     */
    public Button[] btns = new Button[0];
    /**
     * Contains all {@link Text} objects within the Window.
     */
    public Text[] texts = new Text[0];
    /**
     * Contains all {@link ScrollWindow} objects within the Window.
     */
    public ScrollWindow[] sWindows = new ScrollWindow[0];
    /**
     * Contains all {@link Slider} objects within the Window.
     */
    public Slider[] sliders = new Slider[0];
    /**
     * Contains all {@link Switch} objects within the Window.
     */
    public Switch[] switches = new Switch[0];

    // These 3 arrays are used for displaying any visual methods
    Method[] displayMethods = new Method[0]; //     Contains the display methods
    Object[][] displayArgs = new Object[0][0]; //   Contains the arguments for the methods
    Object[] classInstances = new Object[0]; //     Contains the instances of the methods' classes

    /**
     * The position of the top left corner of the window on the parent PGraphics object. Set to 0 by default.
     */
    public int x, y;
    /**
     * The position of the parent PGraphics object on the screen. Set to 0 by default.
     */
    public int displayX, displayY;
    /**
     * Any translations applied to the window content.
     */
    public int translateX, translateY;
    public int Width, Height;
    /**
     * Default set to false
     */
    boolean border = false;
    /**
     * Default set to 0
     */
    int borderStrokeWeight = 0;

    /**
     * Used for main windows, with no parent window.
     *
     * @param applet A reference to the Processing sketch
     * @param colours The Window's colour palette
     */
    public Window(PApplet applet, Palette colours) {
        super(colours);
        sketch = applet;

        // By default, the dimensions of a window are the screen size (x = y = displayX = displayY = 0)
        Width = sketch.width;
        Height = sketch.height;
    }

    /**
     * Used for sub-windows.
     *
     * @param applet A reference to the Processing sketch
     * @param colours The Window's colour palette
     * @param parent The parent window within which the current window belongs to
     */
    public Window(PApplet applet, Palette colours, Window parent) {
        super(parent);
        sketch = applet;
        palette = colours;
    }

    public void display(PGraphics c) { // c is short for canvas
        if (hidden){return;}

        c.beginDraw();
        c.noStroke();
        c.fill(palette.background);
        c.rect(x, y, Width, Height); // Background

        c.translate(translateX, translateY);

        // Display non-interface related visual content
        for (int i = 0; i < displayMethods.length; i++) { // Loops through each display method and invokes it
            c.pushMatrix();
            try {
                displayMethods[i].invoke(classInstances[i], displayArgs[i]);
            } catch (Exception e) { // Handles any errors (Java requirement)
                e.printStackTrace();
            }
            c.popMatrix();
            // pushMatrix() & popMatrix() are used to preserve any changes to the canvas,
            // such as translations, scaling, etc.
        }

        for (Button b : btns) { // Display buttons
            b.display(c);
        }

        for (Text t : texts) { // Display text
            t.display(c);
        }

        for (ScrollWindow sw : sWindows) { // Display scroll windows
            sw.display(c);
        }

        for (Slider s : sliders) { // Display sliders
            s.display(c);
        }

        for (Switch s : switches) { // Display switches
            s.display(c);
        }

        if (border) { // Display border
            c.strokeWeight(borderStrokeWeight);
            c.stroke(palette.stroke);
            c.noFill();
            c.rect(0, 0, Width, Height);
        }

        c.translate(-translateX, -translateY);
        c.endDraw();
    }

    /**
     * A method to set the dimensions and position of the window.
     *
     * @param x {@link Window#x}
     * @param y {@link Window#y}
     * @param Width {@link Window#Width}
     * @param Height {@link Window#Height}
     * @param displayX {@link Window#displayX}
     * @param displayY {@link Window#displayY}
     */
    public void setDimensions(int x, int y, int Width, int Height, int displayX, int displayY) { // Used to override window's default dimensions
        this.x = x; //        Position of window on PGraphics object
        this.y = y;
        this.Width = Width;
        this.Height = Height;
        this.displayX = displayX; //   Position of PGraphics object on screen
        this.displayY = displayY;
    }

    /**
     * Adds a border to the window.
     *
     * @param strokeWeight Value to set {@link Window#borderStrokeWeight}
     */
    public void addBorder(int strokeWeight) {
        border = true;
        borderStrokeWeight = strokeWeight;
    }

    /**
     * Adds a {@link Text} object to the Window that is a Heading.
     *
     * @param title Heading text
     */
    public void addHeading(String title) {
        Text t = this.addText(title, Width / 2, 65, 35);
        t.align(PConstants.CENTER, PConstants.BOTTOM);
        t.underline(5 * Width / 6);
    }

    // These methods are used to add interface elements to the window. Note that a
    // palette is not required as the elements inherit the window's palette
    // All of these methods are of the form:
    // Create new interface object
    // Append object to appropriate array
    // Return interface object

    // The created object is returned so as to easily override any default parameters

    /**
     * Adds a {@link Button} to the Window.
     *
     * @param methodName The name of the method the Button will invoke
     * @param methodArgs The parameters to be passed to the Button's activation method
     * @param instance An instance of the Class the method belongs to. Use keyword 'this' if the method is defined within the Processing Editor without a class.
     * @param label The Button's label - {@link Button#label}
     * @param btnx The position of the Button's top left corner relative to the Window
     * @param btny The position of the Button's top left corner relative to the Window
     * @param Width The Button's width - {@link Button#Width}
     * @param Height The Button's height - {@link Button#Height}
     * @return The created Button.
     */
    public Button addButton(String methodName, Object[] methodArgs, Object instance, String label, float btnx,
            float btny, float Width, float Height) {
        Button btn = new Button(sketch, methodName, methodArgs, instance, label, btnx + x, btny + y, Width, Height, this);

        btns = Arrays.copyOf(btns, btns.length + 1);
        btns[btns.length - 1] = btn;

        return btn;
    }

    /**
     * Adds a {@link Text} object to the Window.
     *
     * @see Text#align(int, int)
     * @param text The string to be displayed.
     * @param tx x position relative to Window
     * @param ty y position relative to Window
     * @param size Text size
     * @return The created Text object.
     */
    public Text addText(String text, float tx, float ty, int size) {
        Text txt = new Text(text, tx + x, ty + y, size, this);

        texts = Arrays.copyOf(texts, texts.length + 1);
        texts[texts.length - 1] = txt;

        return txt;
    }

    /**
     * Adds a {@link Slider} to the Window.
     * @see Slider#setAxis(char, int)
     *
     * @param min Minimum slider value
     * @param max Maximum slider value
     * @param sx Centre x position relative to Window
     * @param sy Centre y position relative to Window
     * @param length Length of slider
     * @return The created Slider object.
     */
    public Slider addSlider(float min, float max, float sx, float sy, float length) {
        Slider slider = new Slider(sketch, min, max, sx + x, sy + y, length, this);

        sliders = Arrays.copyOf(sliders, sliders.length + 1);
        sliders[sliders.length - 1] = slider;

        return slider;
    }

    /**
     * Adds a {@link ScrollWindow} to the Window.
     *
     * @param colours The colour palette for the ScrollWindow
     * @param swx The x position of top left corner relative to parent Window
     * @param swy The y position of top left corner relative to parent Window
     * @param Width
     * @param Height
     * @param contentHeight The vertical length of the scroll canvas
     * @return The created ScrollWindow object.
     */
    public ScrollWindow addScrollWindow(Palette colours, int swx, int swy, int Width, int Height, int contentHeight) {
        ScrollWindow sw = new ScrollWindow(sketch, colours, swx + x, swy + y, Width, Height, contentHeight);

        sWindows = Arrays.copyOf(sWindows, sWindows.length + 1);
        sWindows[sWindows.length - 1] = sw;

        return sw;
    }

    /**
     * Adds a toggle switch to the Window.
     * @param cx Centre x relative to Window
     * @param cy Centre y relative to Window
     * @param Width
     * @param Height
     * @return The created switch object.
     */
    public Switch addSwitch(float cx, float cy, float Width, float Height) {
        Switch s = new Switch(sketch, cx, cy, Width, Height, this);

        switches = Arrays.copyOf(switches, switches.length + 1);
        switches[switches.length - 1] = s;

        return s;
    }


    /**
     * Provide the appropriate visual function(s) the window should display by adding a method to the window
     *
     * @param mName Name of visual method
     * @param args Method arguments
     * @param classInstance Instance of parent class of method
     * @return The visual method
     */
    public Method addContent(String mName, Object[] args, Object classInstance) {
        Method displayContent = null;

        // Returns all methods within the appropriate class into an array
        Method[] methods = classInstance.getClass().getDeclaredMethods();

        // Searches through the array by method name and assigns displayContent to the
        // corresponding method
        for (Method m : methods) {
            if (m.getName() == mName) {
                displayContent = m;
            }
        }

        // Append method details to each array
        displayMethods = Arrays.copyOf(displayMethods, displayMethods.length + 1);
        displayMethods[displayMethods.length - 1] = displayContent;

        displayArgs = Arrays.copyOf(displayArgs, displayArgs.length + 1);
        displayArgs[displayArgs.length - 1] = args;

        classInstances = Arrays.copyOf(classInstances, classInstances.length + 1);
        classInstances[classInstances.length - 1] = classInstance;

        return displayContent;
    }
}