package pgui.btn;

import pgui.type.Element;
import pgui.txt.Text;
import pgui.win.Window;
import processing.core.*;

/**
 * A handle on a line that can be dragged to set a value within a given range.
 */
public class Slider extends Element {
    /**
     * Value of slider
     */
    public float value;
    float minimum, maximum, x, y, length, sx, sy, sr;
    int axis;
    boolean activated, pMousePressed;

    Text label;
    float displacement = 20;
    int labelSide;

    boolean discrete = false;   // True if N > 2
    int N;              // Number of divisions
    float inc;          // Increment = (max-min)/(N-1)
    float pinc;         // Pixel increment
    float lockPercentage;   // Margin around increments that triggers locking

    /**
     * Constructor
     * @param min Minimum value
     * @param max Maximum value
     * @param cx Center x position
     * @param cy Center y position
     * @param l Length of slider
     * @param window Parent window
     */
    public Slider(float min, float max, float cx, float cy, float l, Window window) {
        super(window);

        value = min + (max - min) / 2;
        minimum = min;
        maximum = max;

        x = sx = cx; //  Central value
        y = sy = cy;
        length = l;

        axis = PConstants.X;
        sr = (float) 12.5;

        label = new Text(PApplet.str(value), sx, sy, 20, window);
        setLabelSide(PConstants.TOP);

        pMousePressed = false;
    }

    /**
     * Sets the axis the slider is aligned onto
     * @param a axis: X (horizontal) or Y (vertical)
     */
    public void setAxis(int a) {
        // X = 0, Y = 1
        if (a == PConstants.X || a == PConstants.Y) {
            axis = a;
        } else {
            System.out.println("Invalid axis parameter - input either X or Y for horizontal or vertical");
        }
    }

    /**
     * Sets the side the label appears on
     * @param side TOP, BOTTOM, LEFT or RIGHT
     */
    public void setLabelSide(int side){
        if (labelSide == side){
            return;
        }
        labelSide = side;
        label.x = x;
        label.y = y;
        switch (side){
            case (PApplet.TOP):
                label.align(PApplet.CENTER, PApplet.BOTTOM);
                label.y -= length/2 + 10;
//                displacement *= -1;
                break;
            case (PApplet.BOTTOM):
                label.align(PApplet.CENTER, PApplet.TOP);
                label.y += length/2 + 10;
//                displacement *= 1;
                break;
            case (PApplet.LEFT):
                label.align(PApplet.RIGHT, PApplet.CENTER);
                label.x -= length/2 + 10;
//                displacement *= -1;
                break;
            case (PApplet.RIGHT):
                label.align(PApplet.LEFT, PApplet.CENTER);
                label.x += length/2 + 10;
//                displacement *= 1;
                break;
            default:
                setLabelSide(PConstants.TOP);
                System.out.println("Invalid label side - input ether TOP, BOTTOM, LEFT or RIGHT");
                break;
        }
    }

    /**
     * Returns true if mouse is over circle
     * @return If mouse is over circle
     */
    public boolean mouseOver() {
        return PApplet.dist(sketch.mouseX, sketch.mouseY, sx + window.displayX + window.translateX,
                sy + window.displayY + window.translateY) <= sr && !disabled;
    }

    public void display(PGraphics c) {
        if (hidden){return;}

        float d = 15; // End cap distance / 2

        c.stroke(palette.stroke);
        c.strokeWeight(4);

        // Horizontal
        if (axis == PConstants.X) {
            sx = x + PApplet.map(value, minimum, maximum, -length / 2, length / 2);
            // Long line
            c.line(x - length / 2, y, x + length / 2, y);

            // End caps
            c.line(x - length / 2, y - d, x - length / 2, y + d);
            c.line(x + length / 2, y - d, x + length / 2, y + d);

            // Divisions
            if (discrete){
                for (float divx = x - length / 2 + pinc; divx < x + length / 2; divx += pinc){
                    c.line(divx, y - (float) 0.45*d, divx, y + (float) 0.45*d);
                }
            }

            // Locks label to slider handle
            if (labelSide != PConstants.LEFT && labelSide != PConstants.RIGHT) {
                label.x = sx;
                label.y = sy + (float) (2 * (labelSide - 101.5))*displacement;
            }

        // Vertical
        } else if (axis == PConstants.Y) {
            sy = y + PApplet.map(value, maximum, minimum, -length / 2, length / 2);
            // Long line
            c.line(x, y - length / 2, x, y + length / 2);

            // End caps
            c.line(x - d, y - length / 2, x + d, y - length / 2);
            c.line(x - d, y + length / 2, x + d, y + length / 2);

            // Divisions
            if (discrete){
                for (float divy = y - length / 2 + pinc; divy < y + length / 2; divy += pinc){
                    c.line(x - (float) 0.45*d, divy, x + (float) 0.45*d, divy);
                }
            }

            // Locks label to slider handle
            if (labelSide != PConstants.TOP && labelSide != PConstants.BOTTOM){
                label.y = sy - sketch.textDescent()/2;
                label.x = sx + (float) (labelSide - 38)*displacement;
            }
        }

        if (mouseOver() && sketch.mousePressed) {
            activated = true;
        }

        if (pMousePressed && !sketch.mousePressed) { // Mouse released
            activated = false;
        }

        if (activated) {
            if (axis == PConstants.X) {
                value = PApplet.map(sketch.mouseX - window.displayX - window.translateX, x - length / 2, x + length / 2,
                        minimum, maximum);
            } else if (axis == PConstants.Y) {
                value = PApplet.map(sketch.mouseY - window.displayY - window.translateY, y - length / 2, y + length / 2,
                        maximum, minimum);
            }

            if (value < minimum) {
                value = minimum;
            } else if (value > maximum) {
                value = maximum;
            }

            if (discrete) {
                // Rounds to nearest integer number of increments, 0.5 must be added because (int) = floor()
                value = (int) (PApplet.map(value, minimum, maximum, 0, N-1) + (float) 0.5);
                value = minimum + inc*value;
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

    /**
     * Adds increments to the slider, quantising its output into a set of N discrete values.
     * Each division will be separated by an increment of: range/(N-1)
     * @param N number of divisions (N > 1)
     */
    public void increment(int N){
        increment(N, 1);
    }

    /**
     * Adds increments to the slider, but handle will only lock to each increment near
     * @param N number of divisions (N > 1)
     * @see Slider#increment
     */
    public void softIncrement(int N){
        increment(N, (float) 0.25);
    }

    void increment(int N, float lp){
        if (N < 2){
            System.out.println("Number of divisions (N) must be larger than 1");
            return;
        }
        discrete = true;
        this.N = N;
        inc = (maximum-minimum)/(N-1);
        pinc = length/(N-1);
        lockPercentage = lp;
    }
}