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

//    /**
//     *
//     * @param a 'h' (horizontal) or 'v' (vertical)
//     * @param labelSide TOP/BOTTOM or LEFT/RIGHT
//     */
//    public void setAxis(char a, int labelSide) {
//        if (a == 'h' || a == 'v') {
//            axis = a;
//            if (a == 'h') {
//                label.align(PApplet.CENTER, 203 - labelSide);
//                // TOP = 101, BOTTOM = 102
//                displacement *= (float) (2 * (labelSide - 101.5));
//            } else {
//                label.align(76 - labelSide, PApplet.CENTER);
//                // LEFT = 37, RIGHT = 39
//                displacement *= (labelSide - 38);
//            }
//        } else {
//            System.out.println("Invalid axis parameter - input either 'h' or 'v' for horizontal or vertical");
//        }
//    }

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

        if (axis == PConstants.X) {
            sx = x + PApplet.map(value, minimum, maximum, -length / 2, length / 2);
            c.line(x - length / 2, y, x + length / 2, y); // Long line

            c.line(x - length / 2, y - d, x - length / 2, y + d); // End caps
            c.line(x + length / 2, y - d, x + length / 2, y + d);

            if (labelSide != PConstants.LEFT && labelSide != PConstants.RIGHT) {
                label.x = sx;
                label.y = sy + (float) (2 * (labelSide - 101.5))*displacement;
            }

        } else if (axis == PConstants.Y) {
            sy = y + PApplet.map(value, maximum, minimum, -length / 2, length / 2);
            c.line(x, y - length / 2, x, y + length / 2); // Long line

            c.line(x - d, y - length / 2, x + d, y - length / 2); // End caps
            c.line(x - d, y + length / 2, x + d, y + length / 2);

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