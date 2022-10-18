package pgui.win; //            This indicates the name of the library from which to import (pgui.*)

import pgui.type.Palette;
import processing.core.*;
import java.lang.reflect.*; //  Used for accessing Method types
import java.util.Arrays; //     Used for appending to arrays

import pgui.type.Element;
import pgui.btn.*;
import pgui.txt.*;
import pgui.btn.Button;
import processing.event.KeyEvent;

/**
 * A Window is a class that contains a collection of 'elements' - other interface objects, such as buttons, text or other windows.
 *
 * @author Jack Humphreys
 * @version 1.3
 * @see Element
 */
public class Window extends Element {

    public Element[] elements = new Element[0];

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
    /**
     * Contains all {@link RadioButtonGroup} objects within the Window.
     */
    public RadioButtonGroup[] radBtns = new RadioButtonGroup[0];
    /**
     * Contains all {@link Dropdown} objects within the Window
     */
    public Dropdown[] dropdowns = new Dropdown[0];
    /**
     * Contains all sub-{@link Window} objects within the Window
     */
    public Window[] windows = new Window[0];

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

    public boolean tabbing;    // currently tabbing through elements
    boolean shifting;   // SHIFT is pressed - used to iterate backwards
    int tabIndex;
    int pTabIndex;      // previous

    /**
     * Indicates a window is currently being displayed to keyboard input handlers
     */
    public boolean displaying;

    /**
     * An array containing the elements to be displayed last.
     * This is ordered such that the last index is the most front element (displayed last), 2nd last is next (displayed 2nd last), and so on...
     */
    public Element[] displayOrder = new Element[0];

    /**
     * Used for main windows, with no parent window.
     *
     * @param sketch A reference to the Processing sketch - use keyword 'this'
     * @param colours The Window's colour palette
     */
    public Window(PApplet sketch, Palette colours) {
        super(colours, sketch);

        // By default, the dimensions of a window are the screen size (x = y = displayX = displayY = 0)
        Width = sketch.width;
        Height = sketch.height;

        // This line of code allows for the keyEvent handler to be called whenever a key event occurs
        sketch.registerMethod("keyEvent", this);
    }

    /**
     * Used for sub-windows.
     *
     * @param colours The Window's colour palette
     * @param parent The parent window within which the current window belongs to
     */
    public Window(Palette colours, Window parent) {
        super(parent);
        this.palette = colours.copy();
    }

    public void display(PGraphics c) { // c is short for canvas
        if (hidden){return;}

        c.beginDraw();
        c.noStroke();
        c.fill(palette.background);
        c.rect(x, y, Width, Height);    // Background

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

        c.colorMode(PConstants.RGB, 255);

        if (displaying) {
            for (Window w : windows) {
                w.displaying = true;
            }
            for (ScrollWindow sw : sWindows) {
                sw.displaying = true;
            }
        }

        // Display all elements except those to be displayed last
        for (Element e : elements){
            if (!e.isDisplayedLast()) {
                e.display(c);
            }
        }

        for (Element e : displayOrder) {
            e.display(c);
        }

        if (border) { // Display border
            c.strokeWeight(borderStrokeWeight);
            c.stroke(palette.stroke);
            c.noFill();
            c.rect(0, 0, Width, Height);
        }

        // May need to replace this in the future with a registerMethod (like for key events - press, release, etc.)
        if (sketch.mousePressed){
            tabbing = false;
            elements[tabIndex].setTabbed(false);
        }

        c.translate(-translateX, -translateY);
        c.endDraw();

        displaying = false;
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

    /**
     * This is called whenever there is keyboard input and handles the key events to call a corresponding method.
     *
     * @param e The corresponding key event
     * @see KeyEvent
     */
    public void keyEvent(KeyEvent e) {
        if (displaying) {
            switch (e.getAction()) {
                case (KeyEvent.PRESS):
                    keyPressed(e);
                    break;
                case (KeyEvent.RELEASE):
                    keyReleased(e);
                    break;
            }
        }
    }

    void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case (PConstants.SHIFT):    // Important for combining keys together
                shifting = false;
                break;
            case (PConstants.ENTER):
            case (PConstants.RETURN):
            case (' '):
                System.out.println("ENTER/RETURN/space released.");
                elements[tabIndex].triggerKeyPressed = true;
                break;
        }
    }

    void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (PConstants.SHIFT):    // Combined with TAB/ENTER for order
                shifting = true;
                break;
            case (PConstants.TAB):  // Cycle through elements
                handleTabPress();
                break;

            // Necessary to reset triggerKeyPressed back to false
            default:
                elements[tabIndex].triggerKeyPressed = false;
                break;
        }
    }

    void handleTabPress(){
        if (tabbing) {
            pTabIndex = tabIndex;
            // Used to skip past disabled (and hidden) elements while tabbing
            boolean elementDisabled = true;
            while (elementDisabled) {
                if (shifting) {      // Shift is used to iterate backwards
                    tabIndex--;
                } else {
                    tabIndex++;
                }

                // Adding elements.length before taking modulo allows for cycling backwards, below zero
                tabIndex = (tabIndex + elements.length) % elements.length;
                elementDisabled = elements[tabIndex].isDisabled();
            }
        }
        tabbing = true;

        elements[pTabIndex].setTabbed(false);
        elements[tabIndex].setTabbed(true);

        System.out.println("TAB index ID " + elements[tabIndex].ID);
    }

//    /**
//     * Returns the element of the specific ID. If none found, returns null.
//     * A convenience method for {@link ElementList#get}
//     * @param ID ID of Element
//     * @return Element with given ID
//     */
//    public Element getElement(String ID){
//        return elements.get(ID);
//    }



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

    // These methods are used to add interface elements to the window. Note that a
    // palette is not required as the elements inherit the window's palette
    // All of these methods are of the form:
    // Create new interface object
    // Append object to appropriate array
    // Return interface object

    // The created object is returned so as to easily override any default parameters

    // A method to append an element of any type to its corresponding array

    /**
     * This adds an element to the end of an array of elements
     * @param element The element to be added
     * @param arr The array the element will be added to
     * @return The new array
     * @param <Any> Any object that is a child of {@link Element}, e.g. Button, etc.
     * 
     * @see Window#prependElement(Element, Element[]) 
     */
    public <Any extends Element> Any[] appendElement(Any element, Any[] arr){
        // set Element's ID
        String ID = element.abbr() + "#" + String.format("%03d", arr.length);
        element.setID(ID);

        // Appends element to corresponding array
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = element;

        // Appends element to ElementList
        elements = Arrays.copyOf(elements, elements.length + 1);
        elements[elements.length - 1] = element;

        return arr;
    }

    /**
     * This adds an element to the beginning of an array of elements
     * @param element The element to be added
     * @param arr The array the element will be added to
     * @return The new array
     * @param <Any> Any object that is a child of {@link Element}, e.g. Button, etc.
     *             
     * @see Window#appendElement(Element, Element[]) 
     */
    public <Any extends Element> Any[] prependElement(Any element, Any[] arr){
        // Prepends element to corresponding array
        arr = Arrays.copyOf(arr, arr.length + 1);

        // Shifts all elements up by 1 index by iterating backwards through array
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i-1];
        }
        arr[0] = element;

        return arr;
    }

    /**
     * This removes an element from an array of elements
     * @param element The element to be added
     * @param arr The array the element will be added to
     * @return The new array
     * @param <Any> Any object that is a child of {@link Element}, e.g. Button, etc.
     *
     * @see Window#appendElement(Element, Element[])
     */
//    public <Any extends Element> Any[] removeElement(Any element, Any[] arr){
//        // Searches for index of specified element
//        int foundIndex = 0;
//        while (!arr[foundIndex].equals(element)) {
//            foundIndex++;
//        }
//
//        System.out.println(foundIndex);
//
//        // Removes element at found index
//        Any[] temp = Arrays.copyOf(arr, arr.length - 1);
//        for (int i = 0, j = 0; i < temp.length; i++) {
//            if (i != foundIndex) {
//                temp[j++] = arr[i];
//            }
//        }
//        arr = temp;
//
//        System.out.println(arr);
//
//        return arr;
//    }


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
        Button btn = new Button(methodName, methodArgs, instance, label, btnx + x, btny + y, Width, Height, this);
        btns = appendElement(btn, btns);
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
        texts = appendElement(txt, texts);
        return txt;
    }

    /**
     * Adds a {@link Slider} to the Window.
     * @see Slider#setAxis(int)
     *
     * @param min Minimum slider value
     * @param max Maximum slider value
     * @param sx Centre x position relative to Window
     * @param sy Centre y position relative to Window
     * @param length Length of slider
     * @return The created Slider object.
     */
    public Slider addSlider(float min, float max, float sx, float sy, float length) {
        Slider slider = new Slider(min, max, sx + x, sy + y, length, this);
        sliders = appendElement(slider, sliders);
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
        ScrollWindow sw = new ScrollWindow(this, colours, swx + x, swy + y, Width, Height, contentHeight);
        sWindows = appendElement(sw, sWindows);
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
        Switch s = new Switch(cx, cy, Width, Height, this);
        switches = appendElement(s, switches);
        return s;
    }

    /**
     * Adds a group of radio buttons to the Window.
     *
     * @param x x-coordinate of the centre of the first button
     * @param y y-coordinate of the centre of the first button
     * @param r Radius of each button
     * @param spacing Distance between each button
     * @param labels An array containing each RadioButton's label
     * @return The created RadioButtonGroup object.
     */
    public RadioButtonGroup addRadioButtonGroup(float x, float y, float r, float spacing, String[] labels){
        RadioButtonGroup rbg = new RadioButtonGroup(this, x, y, r, spacing, labels);
        radBtns = appendElement(rbg, radBtns);
        return rbg;
    }

    /**
     * Adds a dropdown menu to the Window
     * @param x x coordinate of top left corner of button
     * @param y y coordinate of top left corner of button
     * @param Width width of button
     * @param Height height of button
     * @param selections An array of strings containing the selections available to the user
     * @return The created dropdown menu object
     */
    public Dropdown addDropdown(float x, float y, float Width, float Height, String[] selections) {
        Dropdown dd = new Dropdown(this, this.x + x, this.y + y, Width, Height, selections);
        dropdowns = appendElement(dd, dropdowns);
        return dd;
    }

    /**
     * Adds a sub-window to the window.
     *
     * @param x x-coordinate of top left corner of sub-window
     * @param y y-coordinate of top left corner of sub-window
     * @param Width
     * @param Height
     * @param palette Colour palette of sub-window
     * @return The created sub-window
     */
    public Window addWindow(int x, int y, int Width, int Height, Palette palette) {
        Window w = new Window(palette, this);
        w.setDimensions(x, y, Width, Height, displayX, displayY);
        windows = appendElement(w, windows);
        return w;
    }
}