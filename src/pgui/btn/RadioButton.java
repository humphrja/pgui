package pgui.btn;

import pgui.type.Element;
import pgui.win.Window;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A circular button that can either be selected or unselected.
 * @see RadioButtonGroup
 */
public class RadioButton extends Element {
    /**
     * The group of radio buttons within which this button is a member of
     */
    RadioButtonGroup group;
    /**
     * The position of the button's centre on the parent window
     */
    public float x, y;
    /**
     * The radius of the button
     */
    public float r;

    float strokeWeight;
    boolean selected;

    /**
     *
     * @param parent {@link Element#window}
     * @param x {@link RadioButton#x}
     * @param y {@link RadioButton#y}
     * @param r {@link RadioButton#r}
     */
    public RadioButton(Window parent, RadioButtonGroup group, float x, float y, float r){
        super(parent);

        this.group = group;
        this.x = x;
        this.y = y;
        this.r = r;

        strokeWeight = r/8;
    }

    /**
     * Returns if mouse is over button.
     * @return True if mouse is over button.
     */
    public boolean mouseOver(){
        if (disabled){return false;};

        float minX = x - 3*r/2;
        float maxX = group.labels[getIndex()].x + group.labels[getIndex()].width();
        float minY = y - 3*r/2;
        float maxY = y + 3*r/2;

        return  sketch.mouseX > minX &&
                sketch.mouseY > minY &&
                sketch.mouseX < maxX &&
                sketch.mouseY < maxY;
    }

    // Returns the index of the radio button within it's group (does not return the group's index attribute)
    int getIndex(){
        for (int i = 0; i < group.btns.length; i ++){
            if (group.btns[i].equals(this)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void display(PGraphics c) {
        if (hidden){return;}

        c.ellipseMode(PConstants.CENTER);
        c.strokeWeight(strokeWeight);
        c.stroke(palette.stroke);
        c.noFill();
        c.ellipse(x, y, (float) (2.0*r), (float) (2.0*r));

        if (selected){
            c.fill(palette.stroke);
            c.ellipse(x, y, (float) (0.6*2*r), (float) (0.6*2*r));
        }
        else if (mouseOver()){
            c.stroke(sketch.lerpColor(palette.stroke, palette.background, (float) 0.5));
            c.fill(sketch.lerpColor(palette.stroke, palette.background, (float) 0.5));
            c.ellipse(x, y, (float) (0.6*2.0*r), (float) (0.6*2.0*r));
        }
    }
}