package pgui.btn;

import processing.core.*;
import java.lang.reflect.*; // Used for accessing Method types

import pgui.type.Element;
import pgui.win.Window;

// A button is displayed as a rectangle with a text label. Buttons can be activated via the mouse to trigger an external event.

public class Button extends Element {
    Method event; //            The button triggers this event (method) when it is activated
    Object[] eventArgs; //      The arguments passed into the event method
    Object tempObj; //          A temporary instance of the class used for invoking the event method
    //                              use 'this' without quotation marks if the event is not within a class but
    //                              just within the sketch (this refers to the instance of the PApplet class, see Processing documentation)

    public String label, activationType;
    public float x, y, Width, Height;
    public int textSize, strokeWeight;

    public boolean selected, pMousePressed;

    // When passing in args, use the following format:
    // new Object[] {arg1, arg2, arg3}

    // Example:
    // new Button("method_1", new Object[] {arg1, arg2, arg3}, this, "Click Me!",
    // 100, 100, 200, 70, btnPalette)

    public Button(PApplet applet, String mName, Object[] args, Object classInstance, String t, float btnx, float btny,
            float w, float h, Window window) {
        super(window);
        sketch = applet;

        tempObj = classInstance;
        Method[] methods = classInstance.getClass().getDeclaredMethods();
        // Returns all methods within the appropriate class into an array

        // Searches through the array by method name and assigns onPress to the corresponding method
        for (Method m : methods) {
            if (m.getName() == mName) {
                // println(m);
                event = m;
            }
        }

        eventArgs = args;

        label = t;
        x = btnx;
        y = btny;
        Width = w;
        Height = h;

        // colours = cols; // A Palette object
        textSize = 20;
        strokeWeight = 4; // Thickness of the outline

        // textSize = int(w*h/(50*t.length())); // Default values, good for horizontally long rectangles
        strokeWeight = (int) (2 * (w + h) / 150); // Convert float -> int via (int), known as 'casting'

        activationType = "on_release";
        pMousePressed = false;
    }

    public void setActivation(String type) { // "on_press" or "on_release" or "hold"
        activationType = type;
    }

    /**
     * Returns true if mouse is over button
     * @return If mouse is over button
     */
    public boolean mouseOver() {
        return sketch.mouseX >= x + window.displayX + window.translateX
                && sketch.mouseY >= y + window.displayY + window.translateY
                && sketch.mouseX <= x + Width + window.displayX + window.translateX
                && sketch.mouseY <= y + Height + window.displayY + window.translateY
                && !disabled;
    }

    /**
     * Used to determine when the button method should be triggered
     * @return If the button has been activated
     */
    public boolean activated() {
        if (disabled){return false;};
        if (activationType == "on_press") {
            return !pMousePressed && sketch.mousePressed; // True on first frame of mouse being pressed
        } else if (activationType == "on_release") {
            return pMousePressed && !sketch.mousePressed; // True on first frame of mouse not being pressed
        } else if (activationType == "hold") {
            return sketch.mousePressed;
        } else {
            return false;
        }
    }

    // The PGraphics object is passed as an argument - this is akin to a 'canvas' to
    // draw to. g is the default PGraphics object
    public void display(PGraphics c) {
        if (hidden){return;}

        if (mouseOver() && !disabled) {
            c.fill(palette.highlight);
        } else if (selected) {
            c.fill(palette.select);
        } else {
            c.fill(palette.primary); // Set button colour
        }

        c.strokeWeight(strokeWeight);
        c.stroke(palette.stroke);
        c.rectMode(PConstants.CORNER);
        c.rect(x, y, Width, Height); // Draw rectangle

        c.noFill();
        c.fill(palette.stroke);
        c.textAlign(PConstants.CENTER, PConstants.CENTER);
        c.textSize(textSize);
        c.text(label, x, y, Width, Height); // Draw button text

        if (mouseOver() && activated() && !disabled) { // If button is activated
            try {
                event.invoke(tempObj, eventArgs); // Invoke method = call function
            } catch (Exception e) { // Handle any errors (Java requirement)
                e.printStackTrace();
            }
        }

        pMousePressed = sketch.mousePressed;
    }
}