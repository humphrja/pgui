package pgui.btn;

import pgui.txt.Text;
import pgui.type.Element;
import pgui.win.ScrollWindow;
import pgui.win.Window;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * This is the object for a dropdown menu - a clickable rectangular button that reveals a list of unique options that can be selected.
 * For selecting data, a dropdown menu is equivalent to a group of radio buttons.
 */
public class Dropdown extends Element {
    public float x, y;
    public float Width, Height;

    /**
     * The value currently selected by the drop down
     */
    public String selection;
    String pSelection;
    String[] selections;
    Text label;

    // The button to trigger the drop down
    Button selectButton;

    // The menu of the drop down that can be scrolled through and hidden/not hidden
    ScrollWindow menu;

    /**
     * Creates a new dropdown menu object.
     *
     * @param parent Parent window of menu
     * @param x x-coordinate of top left corner of button
     * @param y y-coordinate of top left corner of button
     * @param Width width of button
     * @param Height height of button
     * @param selections String array of possible selections available to the user
     */
    public Dropdown(Window parent, float x, float y, float Width, float Height, String[] selections) {
        super(parent);

        this.x = x;
        this.y = y;
        this.Width = Width;
        this.Height = Height;

        this.selections = selections;

        int textSize = (int) (Height * 0.6);

        // This finds an appropriate textSize by decrementing the textSize until the text fits within the boundaries of the button
        sketch.textSize(textSize);
        while (sketch.textWidth("Select...") > Width - Height) {
            textSize--;
            sketch.textSize(textSize);
        }

        label = new Text("Select...", x + (int) ((Width-Height)/2), y + Height/2, textSize, parent);

        selectButton = new Button("showMenu", new Object[] {}, this, "", x, y, Width, Height, parent);

        float h = 3*Height;
        if (selections.length < 3) {
            h = selections.length * Height;
        }

        // Create new menu (ScrollWindow)
        menu = new ScrollWindow(parent, palette, (int) x, (int) (y + Height), (int) Width, (int) h, (int) (selections.length * Height));
        menu.keepScrollOnWindow();
        menu.addScrollBarBackground();
        menu.displayLast();
        menu.setBorderStrokeWeight(selectButton.strokeWeight);

        menu.barHeight = 50;
        menu.barWidth = 10;

        for (int i = 0; i < selections.length; i++) {
            Button b = menu.addButton("setSelection", new Object[] {i}, this, selections[i], 0, i*Height, Width - menu.barWidth, Height);
            b.strokeWeight = 1;
        }
        menu.hide();
    }

    public void display(PGraphics c) {
        if (hidden) {return;}

        // Display a Button with no label
        // Display text and arrow over Button
        // Arrow is centred within a square on the right of the Button (of length equal to button Height)
        // Text is centred in the remaining space to the left

        selectButton.pTriggerKeyPressed = this.pTriggerKeyPressed;
        selectButton.triggerKeyPressed = this.triggerKeyPressed;

        pSelection = selection;

        // Closes menu if mouse clicks off
        // Adding the condition for selectButton.mouseOver() ensures the button is not double triggered as the button is set to on_release activation
        // Adding the condition for tabbed() closes the menu when the next element is tabbed
        if ((sketch.mousePressed && !(menu.mouseOver() || selectButton.mouseOver())) || (!tabbed() && window.tabbing)) {
            menu.hide();
            menu.scroll = 0;
        }

        selectButton.display(c);
        label.display(c);
        displayArrow(c);
        menu.display(c);
        menu.displaying = true;

        pTriggerKeyPressed = triggerKeyPressed;
    }

    /**
     * Shows the drop down menu
     */
    public void showMenu(){
        if (menu.isHidden()) {
            menu.enable();
        } else {
            menu.hide();
        }
        menu.tabbing = window.tabbing;
    }

    /**
     * Sets the currently selected option.
     * @param index The index of the selection within the possible selections, top-down
     */
    public void setSelection(int index) {
        selection = selections[index];
        label.content = selection;
        menu.scroll = 0;
        menu.hide();
    }

    /**
     * Sets the prompt message the button displays before a selection is made
     * @param prompt The message to be initially displayed
     */
    public void setPrompt(String prompt) {
        label.content = prompt;
    }

    /**
     * Used to determine a change in selection
     * @return True if a new selection has been made
     */
    public boolean newSelection() {
        return selection != pSelection;
    }

    void displayArrow(PGraphics c) {
        // This flips the arrow if the menu is open
        int direction = 1;
        if (menu.isHidden()) {
            direction = -1;
        }

        c.fill(palette.stroke);
        c.noStroke();

        float cx = x + Width - Height/2;
        float cy = y + Height/2;
        float r = Height/5;

        // Displays an equilateral triangle centred in a square to the right of the button, aligned vertically
        c.beginShape();
        for (float a = -PConstants.HALF_PI; a < PConstants.TWO_PI; a += 2*PConstants.PI/3){
            c.vertex(cx + r*PApplet.cos(a), cy + direction * r*PApplet.sin(a));
        }
        c.endShape();
    }

    public void disable() {
        selectButton.disable();
        label.disable();
        super.disable();
    }

    public void enable() {
        selectButton.enable();
        label.enable();
        super.enable();
    }

    public void setTabbed(boolean b) {
        selectButton.setTabbed(b);
        super.setTabbed(b);
    }
}
