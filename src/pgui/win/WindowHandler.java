package pgui.win;

import pgui.type.Palette;
import processing.core.PApplet;
import java.util.Arrays;

/**
 * An object that handles multiple main windows within an application.
 * Automates window switching, displaying, tabbing
 */
public class WindowHandler {
    /**
     * An array of windows that this will handle
     */
    public Window[] windows;
    /**
     * Index of currentWindow in {@link WindowHandler#windows}
     */
    public int currentWindow = 0;

    PApplet sketch;

    /**
     * Creates a new object to handle multiple windows
     * @param sketch Use keyword 'this'
     */
    public WindowHandler(PApplet sketch) {
        this.windows = new Window[0];
        this.sketch = sketch;
    }

    /**
     * Adds a window to the object
     * @param palette Palette of the window
     * @return Created window
     */
    public Window addWindow(Palette palette){
        Window window = new Window(sketch, palette);

        // Appends to windows
        windows = Arrays.copyOf(windows, windows.length + 1);
        windows[windows.length - 1] = window;

        return window;
    }

    /**
     * Displays the appropriate window.
     */
    public void displayCurrentWindow(){
        windows[currentWindow].display(sketch.g);       // g is the default PGraphics object for the main sketch
        windows[currentWindow].displaying = true;       // This enables registerMethod to determine which window a key event is associated with - necessary for keyboard shortcuts
    }

    /**
     * Closes the Processing application.
     * @see <a href="https://processing.org/reference/exit_.html">exit()</a>
     */
    public void close() {
        sketch.exit();
    }

    /**
     * Sets the current window to be displayed
     * @param index Index of window to display in {@link WindowHandler#windows}
     */
    public void setWindow(int index) {
        boolean tabbing = windows[currentWindow].tabbing;
        currentWindow = index;
        if (tabbing){
            windows[currentWindow].tabbing = false;     // This ensures a tab cycle is not triggered when the window-to-be was tabbing
            windows[currentWindow].handleTabPress();    // This enables tabbing on the next window
        }
    }
}
