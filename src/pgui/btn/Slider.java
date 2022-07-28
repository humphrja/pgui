package pgui.btn;

import pgui.Element;
import pgui.txt.Text;
import pgui.win.Window;
import processing.core.*;

public class Slider extends Element {
    PApplet sketch;

    public float value;
    float minimum, maximum, x, y, length, sx, sy, sr;
    char axis;
    boolean activated, pMousePressed;

    Text label;
    float displacement = 20;

    public Slider(PApplet applet, float min, float max, float xpos, float ypos, float l, Window window) {
        super(window);
        sketch = applet;

        value = min + (max - min) / 2;
        minimum = min;
        maximum = max;

        x = sx = xpos; //  Central value 
        y = sy = ypos;
        length = l;

        axis = 'h';
        sr = (float) 12.5;

        label = new Text(PApplet.str(value), sx, sy, 20, window);
        label.align(PApplet.CENTER, PApplet.BOTTOM);

        pMousePressed = false;
    }

    public void setAxis(char a, int labelSide) {
        if (a == 'h' || a == 'v') {
            axis = a;
            if (a == 'h') {
                label.align(PApplet.CENTER, 203 - labelSide);
                // TOP = 101, BOTTOM = 102
                displacement *= (float) (2 * (labelSide - 101.5));
            } else {
                label.align(76 - labelSide, PApplet.CENTER);
                // LEFT = 37, RIGHT = 39
                displacement *= (labelSide - 38);
            }
        } else {
            System.out.println("Invalid axis parameter - input either 'h' or 'v' for horizontal or vertical");
        }
    }

    public boolean mouseOver() { // Returns true if mouse is over circle
        return PApplet.dist(sketch.mouseX, sketch.mouseY, sx + window.displayX + window.translateX,
                sy + window.displayY + window.translateY) <= sr;
    }

    public void display(PGraphics c) {
        float d = 15; // End cap distance / 2

        c.stroke(palette.stroke);
        c.strokeWeight(4);

        if (axis == 'h') {
            sx = x + PApplet.map(value, minimum, maximum, -length / 2, length / 2);
            c.line(x - length / 2, y, x + length / 2, y); // Long line

            c.line(x - length / 2, y - d, x - length / 2, y + d); // End caps
            c.line(x + length / 2, y - d, x + length / 2, y + d);

            label.x = sx;
            label.y = sy - displacement;

        } else if (axis == 'v') {
            sy = y + PApplet.map(value, maximum, minimum, -length / 2, length / 2);
            c.line(x, y - length / 2, x, y + length / 2); // Long line

            c.line(x - d, y - length / 2, x + d, y - length / 2); // End caps
            c.line(x - d, y + length / 2, x + d, y + length / 2);

            label.y = sy;
            label.x = sx + displacement;
        }

        if (mouseOver() && sketch.mousePressed) {
            activated = true;
        }

        if (pMousePressed && !sketch.mousePressed) { // Mouse released
            activated = false;
        }

        if (activated) {
            if (axis == 'h') {
                value = PApplet.map(sketch.mouseX - window.displayX - window.translateX, x - length / 2, x + length / 2,
                        minimum, maximum);
            } else if (axis == 'v') {
                value = PApplet.map(sketch.mouseY - window.displayY - window.translateY, y - length / 2, y + length / 2,
                        maximum, minimum);
            }

            if (value < minimum) {
                value = minimum;
            } else if (value > maximum) {
                value = maximum;
            }
        }

        label.content = PApplet.str(PApplet.round(value));

        if (mouseOver() || activated) {
            label.display(c);
            c.fill(palette.highlight);
        } else {
            c.fill(palette.primary);
        }

        c.strokeWeight(3);
        c.ellipse(sx, sy, 2 * sr, 2 * sr);

        pMousePressed = sketch.mousePressed;
    }
}