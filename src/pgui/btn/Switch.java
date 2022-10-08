package pgui.btn;

import pgui.type.Element;
import pgui.win.Window;
import processing.core.*;

/**
 * A switch that can be toggled between two states - on/off
 */
public class Switch extends Element {
    /**
     * Switch state (on/off) -> value (True/False)
     */
    public boolean value = false;
    boolean prevValue = false;
    float x, y, Width, Height, R, r, sx;

    /**
     * Constructor
     * @param cx Center x
     * @param cy Center y
     * @param Width
     * @param Height
     * @param window Parent window
     */
    public Switch(float cx, float cy, float Width, float Height, Window window) {
        super(window);

        x = cx; //      Center x
        y = cy; //      Center y
        this.Width = Width;
        this.Height = Height;
        R = Height / 2; //          Larger, external radius
        r = (float) 0.75 * R; //    Smaller, internal radius
    }

    public void display(PGraphics c) {
        if (hidden){return;}

        float dx = Width / 2 - R; //  Distance between x & start of arc

        if (value) { // On
            sx = x + Width / 2 - R;
            c.fill(palette.select);
        } else { //     Off
            sx = x - Width / 2 + R;
            c.noFill();
        }

        c.stroke(palette.stroke);
        c.strokeWeight(2);
        //    centerX, centerY, xDiameter, yDiameter, angleStart, angleEnd
        c.arc(x - dx, y, 2 * R, 2 * R, PApplet.HALF_PI, 3 * PApplet.HALF_PI); //     Left arc
        c.arc(x + dx, y, 2 * R, 2 * R, 3 * PApplet.HALF_PI, 5 * PApplet.HALF_PI); // Right arc

        // Internal rectangle for filling with colour
        c.rectMode(PApplet.CENTER);
        c.noStroke();
        c.rect(x, y, Width - 2 * R + 1, Height); //     +1 is added to the width to help account for any rounding down
        c.strokeWeight(2);
        c.stroke(palette.stroke);
        c.rectMode(PApplet.CORNER);

        // Top and bottom horizontal lines
        c.line(x - dx, y + R, x + dx, y + R); //        Used because rect has noStroke();
        c.line(x - dx, y - R, x + dx, y - R);

        prevValue = value;
        c.fill(255);    // Default

        if ((mouseOver() || tabbed()) && !disabled) {
            c.fill(palette.highlight);
            if (mouseOver() && sketch.mousePressed || tabbed() && triggerKeyPressed && !pTriggerKeyPressed) { //   On press - increases purpose and directness (compared to on release)
                value = !value;
            }
        }

        c.ellipse(sx, y, 2 * r, 2 * r);

        pTriggerKeyPressed = triggerKeyPressed;
    }

    boolean mouseOver() {
        return PApplet.dist(sketch.mouseX, sketch.mouseY, sx, y) <= r && !disabled;
    }

    /**
     * Indicates if the switch has just been switched on
     * @return True if switch has just been switched on
     */
    public boolean toggledOn(){
        return !prevValue && value;
    }

    /**
     * Indicates if the switch has just beeen switched off
     * @return True if switch has just been switched off
     */
    public boolean toggledOff(){
        return prevValue && !value;
    }
}
