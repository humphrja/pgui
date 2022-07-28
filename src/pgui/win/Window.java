package pgui.win; //            This indicates the name of the library from which to import (pgui.*)

import processing.core.*;
import java.lang.reflect.*; //  Used for accessing Method types
import java.util.Arrays; //     Used for appending to arrays

import pgui.Element;
import pgui.btn.*;
import pgui.txt.*;
import pgui.btn.Button;

/**
 * @author Jack Humphreys
 * @version 1.3
 * 
 * A Window is a class that contains a collection of 'elements' - other interface objects, such as buttons, text or other windows.
 */

public class Window extends Element {
    PApplet sketch;

    public Button[] btns = new Button[0];
    public Text[] texts = new Text[0];
    public ScrollWindow[] sWindows = new ScrollWindow[0]; // These arrays contain the window's elements
    public Slider[] sliders = new Slider[0];
    public Switch[] switches = new Switch[0];

    // These 3 arrays are used for displaying any visual methods
    Method[] displayMethods = new Method[0]; //     Contains the display methods
    Object[][] displayArgs = new Object[0][0]; //   Contains the arguments for the methods
    Object[] classInstances = new Object[0]; //     Contains the instances of the methods' classes

    public int x, y; //                             The position of the window on the parent PGraphics object
    public int displayX, displayY; //               The position of the PGraphics object on the screen
    public int translateX, translateY; //           Any translations applied to the window content
    public int Width, Height;
    boolean border = false;
    int borderStrokeWeight = 0;

    // Used for main windows, with no parent window
    public Window(PApplet applet, Palette cols) {
        super(cols);
        sketch = applet;

        // By default, the dimensions of a window are the screen size (x = y = displayX = displayY = 0)
        Width = sketch.width;
        Height = sketch.height;
    }

    // Used for sub-windows
    public Window(PApplet applet, Palette cols, Window parent) {
        super(parent);
        sketch = applet;
        palette = cols;
    }

    public void display(PGraphics c) { // c is short for canvas
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
            // Scroll windows display to a different PGraphics object (canvas) than the window itself
            sw.swdisplay(c);
        }

        for (Slider s : sliders) {
            s.display(c);
        }

        for (Switch s : switches) {
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

    public void setDimensions(int xpos, int ypos, int w, int h, int Dx, int Dy) { // Used to override window's default dimensions
        x = xpos; //        Position of window on PGraphics object
        y = ypos;
        Width = w;
        Height = h;
        displayX = Dx; //   Position of PGraphics object on screen
        displayY = Dy;
    }

    public void addBorder(int strokeWeight) {
        border = true;
        borderStrokeWeight = strokeWeight;
    }

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
    public Button addButton(String methodName, Object[] methodArgs, Object instance, String label, float btnx,
            float btny, float w, float h) {
        Button btn = new Button(sketch, methodName, methodArgs, instance, label, btnx + x, btny + y, w, h, this);

        btns = Arrays.copyOf(btns, btns.length + 1);
        btns[btns.length - 1] = btn;

        return btn;
    }

    public Text addText(String text, float tx, float ty, int size) {
        Text txt = new Text(text, tx + x, ty + y, size, this);

        texts = Arrays.copyOf(texts, texts.length + 1);
        texts[texts.length - 1] = txt;

        return txt;
    }

    public Slider addSlider(float min, float max, float sx, float sy, float l) {
        Slider slider = new Slider(sketch, min, max, sx + x, sy + y, l, this);

        sliders = Arrays.copyOf(sliders, sliders.length + 1);
        sliders[sliders.length - 1] = slider;

        return slider;
    }

    public ScrollWindow addScrollWindow(Palette cols, int swx, int swy, int w, int h, int ch) {
        ScrollWindow sw = new ScrollWindow(sketch, cols, swx + x, swy + y, w, h, ch);

        sWindows = Arrays.copyOf(sWindows, sWindows.length + 1);
        sWindows[sWindows.length - 1] = sw;

        return sw;
    }

    public Switch addSwitch(float cx, float cy, float w, float h) {
        Switch s = new Switch(sketch, cx, cy, w, h, this);

        switches = Arrays.copyOf(switches, switches.length + 1);
        switches[switches.length - 1] = s;

        return s;
    }

    // Provide the appropriate visual function(s) the window should display by
    // adding a method to the window
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