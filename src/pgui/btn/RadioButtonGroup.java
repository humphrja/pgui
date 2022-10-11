package pgui.btn;

import pgui.txt.Text;
import pgui.type.Element;
import pgui.win.Window;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A collection of multiple {@link RadioButton} objects, intrinsically linked together.
 * Only one button in the group can be selected at any given time.
 */
public class RadioButtonGroup extends Element {
    /**
     * An array containing each button in the group
     */
    public RadioButton[] btns;
    /**
     * The index of the selected button in {@link RadioButtonGroup#btns}
     */
    public int index;
    int pindex; // Previous index

    /**
     * The position of the first button in the group, relative to the parent Window
     */
    public float x, y;
    /**
     * The radius of each button
     */
    float r;
    /**
     * Number of buttons in group
     */
    public int number;
    /**
     * Distance between adjacent radio buttons
     */
    float spacing;

    /**
     * Each RadioButton's label
     */
    Text[] labels;
    /**
     * Each RadioButton's label
     */
    String[] strings;

    /**
     * xoff = {1 : horizontal; 0 : vertical}.
     * yoff = {0 : horizontal; 1 : vertical}
     */
    float xoff, yoff;

    /**
     *
     * @param parent {@link Element#window}
     * @param x {@link RadioButtonGroup#x}
     * @param y {@link RadioButtonGroup#y}
     * @param r {@link RadioButtonGroup#r}
     * @param labels The String values associated with the group's {@link RadioButtonGroup#labels}
     * @param spacing {@link RadioButtonGroup#spacing}
     */
    public RadioButtonGroup(Window parent, float x, float y, float r, float spacing, String[] labels){
        super(parent);

        this.x = x;
        this.y = y;
        this.r = r;
        this.number = labels.length;
        this.spacing = spacing;

        this.labels = new Text[number];
        this.strings = labels;

        setAxisVertical();
    }

    // Creates array btns
    void createArray(){
        btns = new RadioButton[number];
        for (int i = 0; i < number; i++){
            btns[i] = new RadioButton(window, this, x + i*xoff*spacing, y + i*yoff*spacing, r);
            labels[i] = new Text(strings[i], (x + i*xoff*spacing) + r*3, (y + i*yoff*spacing) - sketch.textDescent(), (int) r*3, window);
            labels[i].align(PConstants.LEFT, PConstants.CENTER);
        }
    }

    /**
     * Sets the group to display along a horizontal line
     */
    public void setAxisHorizontal(){
        xoff = 1;
        yoff = 0;
        createArray();
    }

    /**
     * Sets the group to display along a vertical line
     */
    public void setAxisVertical(){
        xoff = 0;
        yoff = 1;
        createArray();
    }

    public void display(PGraphics c) {
        pindex = index;
        for (int i = 0; i < btns.length; i++){
            RadioButton btn = btns[i];
            // Selects a button on mouseclick
            if (btn.mouseOver() && sketch.mousePressed){
                btn.selected = true;
                index = i;

                // Loops through other buttons and deselects them
                for (RadioButton tempbtn : btns){
                    if (!tempbtn.equals(btn)){
                        tempbtn.selected = false;
                    }
                }
            }
            btn.display(c);
        }

        for (Text t : labels){
            t.display(c);
        }
    }

    /**
     * Overrides the currently selected radio button by setting the group's {@link RadioButtonGroup#index}
     * @param i Index of button
     */
    public void setIndex(int i){
        if (i < 0 || i >= number){
            System.out.print("Index out of range - choose an integer within 0 & ");
            System.out.println(number-1);
            return;
        }
        index = i;
        for (int j = 0; j < btns.length; j++) {
            btns[j].selected = (j == i);
        }
    }

    /**
     * Used to determine a change in selection
     * @return True if a new selection has been made
     */
    public boolean newSelection() {
        return index != pindex;
    }
}