package pgui.win;

import processing.core.*; //            Used for PGraphics
import processing.event.KeyEvent; //    Used to handle key events
import processing.event.MouseEvent; //  Used to handle mouse events
import pgui.txt.Text;

// A ScrollWindow is like a window, except that it's content can be scrolled
// through using a scroll bar, scroll buttons or the mouse scroll wheel

public class ScrollWindow extends Window {
    public PApplet sketch;

    public PGraphics canvas; // scrollCanvas
    public float scroll, maxScroll, scrollSensitivity, barPos; //     Represents the vertical translation of the content

    public float barWidth = 20;
    public float barHeight = 150;

    boolean mouseDown = false; // This variable indicates if the scroll bar is currently being manipulated via mouse input

    public ScrollWindow(PApplet applet, Palette cols, int x, int y, int w, int h, int contentH) {
        super(applet, cols); // This is like creating a new Window object
        sketch = applet;

        // This line of code allows for the mouseEvent handler to be called whenever a mouse event occurs
        sketch.registerMethod("mouseEvent", this);

        // This line of code allows for the keyEvent handler to be called whenever a key event occurs
        sketch.registerMethod("keyEvent", this);

        // For scroll windows, the x & y variables associated with it's window object must be set to 0, as the content should not be translated away from the origin of the scroll canvas.
        setDimensions(0, 0, w, h, x, y);

        canvas = sketch.createGraphics(w, h);

        scroll = 0; // Degree by which canvas has been translated
        maxScroll = contentH - h; // Total additional height through which to scroll through
        scrollSensitivity = 15; // Scalar factor each wheel tick is multiplied by
    }

    public void displayScrollBar() {
        float strokeWeight = (float) 1.5;

        float opacity = 200; // 200/255 % opacity
        if (mouseInBarRegion() || mouseDown) {
            opacity = 255;
        }

        canvas.beginDraw();
        canvas.fill(palette.primary, opacity);

        if (mouseDown) {
            scroll = PApplet.map(sketch.mouseY, barHeight / 2 + displayY, Height - barHeight / 2 + displayY, 0,
                    maxScroll);
            scroll = PApplet.constrain(scroll, 0, maxScroll);
            canvas.fill(palette.highlight);
        }

        // This represents the y value of the upper edge of the scroll bar
        barPos = PApplet.map(scroll, 0, maxScroll, 0, Height - barHeight);

        canvas.stroke(palette.stroke, opacity);
        canvas.strokeWeight(strokeWeight);

        canvas.rect(Width - barWidth + strokeWeight / 2, barPos + strokeWeight / 2, barWidth - strokeWeight,
                barHeight - strokeWeight);

        canvas.endDraw();
    }

    public void swdisplay(PGraphics c) {
        translateY = (int) -scroll;

        // Displays the window's contents to the scroll canvas
        display(canvas);

        if (mouseOver() || mouseDown) {
            displayScrollBar();
        }

        // Draws the scroll canvas as an image to the PGraphics object of the parent window
        c.image(canvas, displayX, displayY, Width, Height);

        // Draws a border to the window
        c.noFill();
        c.strokeWeight(4);
        c.stroke(palette.stroke);
        c.rect(displayX, displayY, Width, Height);
    }

    void updatePos(float newx, float newy) {
        for (Text t : texts) {
            t.x = newx;
            t.y = newy;
        }
    }

    boolean mouseOver() {
        return sketch.mouseX >= x + displayX && sketch.mouseY >= y + displayY
                && sketch.mouseX <= x + Width + displayX && sketch.mouseY <= y + Height + displayY;
    }

    boolean mouseInBarRegion() {
        return sketch.mouseX >= Width - barWidth + displayX && sketch.mouseY >= displayY
                && sketch.mouseX <= Width + displayX && sketch.mouseY <= Height + displayY;
    }

    // This is called whenever there is mouse input and handles the mouse events to call a corresponding method
    public void mouseEvent(MouseEvent e) {
        switch (e.getAction()) {
            case (MouseEvent.PRESS):
                mousePressed(e);
                break;
            case (MouseEvent.RELEASE):
                mouseReleased(e);
                break;
            case (MouseEvent.WHEEL):
                mouseWheel(e);
                break;
            // other mouse events cases here...
        }
    }

    // This is called whenever the mouse is pressed
    void mousePressed(MouseEvent e) {
        if (mouseInBarRegion()) {
            mouseDown = true;
        }
    }

    void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    // This is called whenever the mouse wheel is scrolled
    void mouseWheel(MouseEvent e) {
        if (mouseOver()) {
            scroll += e.getCount() * scrollSensitivity;
            scroll = PApplet.constrain(scroll, 0, maxScroll);
        }
    }

    public void keyEvent(KeyEvent e) {
        switch (e.getAction()) {
            case (KeyEvent.PRESS):
                keyPressed(e);
                break;
            case (KeyEvent.RELEASE):
                keyReleased(e);
                break;
        }
    }

    void keyPressed(KeyEvent e) {
        if (mouseOver()) {
            switch (e.getKeyCode()) {
                case (PApplet.UP):
                    scroll -= scrollSensitivity;
                    scroll = PApplet.constrain(scroll, 0, maxScroll);
                    break;

                case (PApplet.DOWN):
                    scroll += scrollSensitivity;
                    scroll = PApplet.constrain(scroll, 0, maxScroll);
                    break;

                case (PApplet.ENTER):
                    scroll = maxScroll;
                    break;

                case (PApplet.BACKSPACE):
                    scroll = 0;
                    break;
            }
        }
    }

    void keyReleased(KeyEvent e) {
        ;
    }
}