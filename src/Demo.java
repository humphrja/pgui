import pgui.type.Palette;
import processing.core.*;
import pgui.btn.Button;
import pgui.win.*;
import pgui.btn.*;
import pgui.txt.*;

public class Demo extends PApplet {
    float r = 200;

    WindowHandler app;
    Window[] windows;

    // Used to declare the size of the sketch window
    public void settings() {
        size(1400, 900);
        // fullScreen();
    }

    // This is run once, before the first frame of draw()
    public void setup() {
        app = new WindowHandler(this);

        // Creating the palette of the main windows
        Palette winPalette = new Palette(color(53, 45, 57), color(255),
                color(58, 155, 216), color(224, 82, 99), color(238, 122, 49));

        // Main menu - select an example window
        Window w = app.addWindow(winPalette);
        w.addHeading("Choose an example window");
        createStartBtnGrid(w);
        w.addButton("close", new Object[] {}, app, "Exit", width / 2 - 100, height - 100, 200, 70);
        //

        // Window 1 - Different colour palettes
        Palette p2 = new Palette(color(252, 222, 156), color(56, 29, 42), color(196, 214, 176), color(186, 86,
                36), color(255, 165, 82));
        Window w1 = app.addWindow(p2);
        w1.addHeading("Windows can have different colour palettes.");
        createBackToHomeBtn(w1);
        //

        // Window 2 - Display different content
        Window w2 = app.addWindow(winPalette);
        w2.addHeading("Windows can display content from custom methods too");
        createBackToHomeBtn(w2);
        w2.addContent("myMethod", new Object[] {}, this);
        //

        // Window 3 - Windows can be of different sizes and be within each other
        Window w3 = app.addWindow(winPalette);
        w3.addHeading("Windows can exist within different windows!");
        createBackToHomeBtn(w3);
        //

        // Window 4 - scroll window
        Window w4 = app.addWindow(winPalette);
        w4.addHeading("Scroll windows allow you to have scroll features :)");
        createBackToHomeBtn(w4);

        int swidth = 600;
        ScrollWindow sw = w4.addScrollWindow(p2, width / 2 - swidth - 25, 100, swidth, 600, 1000);
        sw.addHeading("This is a scroll window");
        createBackToHomeBtn(sw);

        ScrollWindow sw2 = w4.addScrollWindow(p2, width / 2 + 25, 100, swidth, 600, 800);
        sw2.addScrollBarBackground();
        sw2.addHeading("This is another scroll window!");
        sw2.addSlider(50, 200, sw2.Width / 2, 500, 400);
        sw2.addContent("sliderExample2", new Object[] { sw2 }, this);
        //

        // Window 5 - Buttons - shows different type of button activations ->
        // on_press, on_release, hold
        Window w5 = app.addWindow(winPalette);
        w5.addHeading("Buttons can have different activation types...");
        createBackToHomeBtn(w5);

        // On release (default)
        Button btn1 = w5.addButton("testString", new Object[] { "On Release" }, this, "On Release", 100, 300, 200, 70);

        // On press
        Button btn2 = w5.addButton("testString", new Object[] { "On Press" }, this, "On Press", 350, 300, 200, 70);
        btn2.setActivation("on_press");

        // While holding
        Button btn3 = w5.addButton("testString", new Object[] { "Hold" }, this, "Hold", 600, 300, 200, 70);
        btn3.setActivation("hold");

        btn1.displayLast();
        btn2.displayLast();
        btn3.displayLast();

        println((Object) w5.displayOrder);
        //

        // Window 6 - Button recursion
        Window w6 = app.addWindow(winPalette);
        w6.addHeading("Recursion?!");
        createBackToHomeBtn(w6);
        addNewButton(w6);
        //

        // Window 7 - Sliders and switches
        Window w7 = app.addWindow(winPalette);
        w7.addHeading("Sliders and switches");
        createBackToHomeBtn(w7);

        w7.addDropdown(width/2 - 100, 100, 200, 40, new String[] {"Test", "Test 2", "Test 3", "Test 4", "Test 5"});

        Slider s2 = w7.addSlider(0, 360, width / 2, height - 200, 500);
        s2.increment(4);
        Text t = w7.addText("Hue", width/2, height - 170, 20);
        t.align(CENTER, TOP);
        Slider s = w7.addSlider(25, 225, width - 200, height / 2, 200);
        s.setAxis(Y);
        s.setLabelSide(LEFT);
        s.increment(10);
        Text t2 = w7.addText("Radius", width - 200, height/2 - 200/2 - 10, 20);
        t2.align(CENTER, BOTTOM);

        w7.addContent("sliderExample", new Object[] {}, this);

        w7.addSwitch(width / 2 + 80/2 + 10, height - 100, 80, 45);
        Text t3 = w7.addText("Disable", width/2 - 10, height - 100, 20);
        t3.align(RIGHT, CENTER);

        w7.addSwitch(width - 100, height/2, 80, 45);
        w7.addText("Hide", width - 100, height/2 - 50, 20);

        w7.addRadioButtonGroup(width/6, height/2 - 80, (float) 10, 80, new String[] {"Circle", "Triangle", "Square", "Pentagon"});
        w7.radBtns[0].setIndex(0);
        //

        windows = app.windows;
    }

    // This is run every frame
    public void draw() {
        // These are used to handle what happens when switches are turned on or off
        if (windows[7].switches[0].toggledOn()){
            windows[7].sliders[0].disable();
            windows[7].texts[1].disable();
            windows[7].dropdowns[0].disable();
        } else if (windows[7].switches[0].toggledOff()){
            windows[7].sliders[0].enable();
            windows[7].texts[1].enable();
            windows[7].dropdowns[0].enable();
        }

        if (windows[7].switches[1].toggledOn()){
            windows[7].sliders[1].hide();
            windows[7].texts[2].hide();
            windows[7].dropdowns[0].hide();
        } else if (windows[7].switches[1].toggledOff()){
            windows[7].sliders[1].enable();
            windows[7].texts[2].enable();
            windows[7].dropdowns[0].enable();
        }

        if (windows[7].radBtns[0].newSelection()) {
            System.out.println('a');
        } else if (windows[7].dropdowns[0].newSelection()) {
            System.out.println('b');
        }

        app.displayCurrentWindow();
    }

    public static void main(String[] args) {
        Demo mySketch = new Demo();
        String[] processingArgs = { "App" };
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void myMethod() {
        stroke(255);
        fill(0);
        ellipse(mouseX, mouseY, r, r);
    }

    void createStartBtnGrid(Window w) {
        int nx = 3;
        int ny = 2;
        int btnW = 150;
        int btnH = 100;
        int spacing = 25;

        float ix = width / 2 - (nx * btnW + (nx - 1) * spacing) / 2;
        float iy = height / 2 - (ny * btnH + (ny + 1) * spacing) / 2;

        String m = "setWindow"; // Method

        String[] labels = { "Colour", "Visuals", "Sub-windows", "Scroll windows", "Buttons", "Recursion" };

        for (int row = 0; row < ny; row++) {
            for (int col = 0; col < nx; col++) {
                float x = ix + col * (btnW + spacing);
                float y = iy + row * (btnH + spacing);

                Object[] args = new Object[] { col + row * nx + 1 };
                w.addButton(m, args, app, labels[col + row * nx], x, y, btnW, btnH);
            }
        }

        w.addButton(m, new Object[] {7}, app, "Interactives",
                ix + btnW + spacing, iy + 2*(btnH + spacing), btnW, btnH);
    }

    void createBackToHomeBtn(Window w) {
        w.addButton("setWindow", new Object[] { 0 }, app, "Back to main menu",
                w.Width - 120, w.Height - 120, 100, 100);
    }

//    public void close() {
//        exit();
//    }
//
//    public void setWindow(int newWindowNum) {
//        currentWindow = newWindowNum;
//    }

    public void testString(String str) {
        fill(255, 0, 0);
        ellipse(width / 2, height / 2 + 200, 150, 150);
        System.out.println(str);
    }

    public void addNewButton(Window window) {
        window.addButton("addNewButton", new Object[] { window }, this, "btn",
                random(width - 100), random(height - 100), 100, 100);
    }

    public void sliderExample() {
        float hue = windows[7].sliders[0].value;
        float radius = windows[7].sliders[1].value;
        int shape = windows[7].radBtns[0].index;
        colorMode(HSB, 360, 100, 100);
        strokeWeight(3);
        stroke(360);
        fill(hue, 100, 100);
//        switch (shape){
//            case (0):   // Circle
//                circle(width / 2, height / 2, 2*radius);
//                break;
//            case (1):   // Triangle
//                beginShape();
//                for (float a = 0; a <= TWO_PI; a += TWO_PI/3){
//                    vertex(width/2 + radius*cos(a), height/2 + radius*sin(a));
//                }
//                endShape();
////                pushMatrix();
////                translate(width/2, height/2);
////                triangle(0, -radius, radius*sqrt(3)/2, radius/2, -radius*sqrt(3)/2, radius/2);
////                popMatrix();
//                break;
//            case (2):   // Square
//                square(width/2 - radius, height/2 - radius, 2*radius);
//                break;
//        }


        if (shape > 0){
            beginShape();
            for (float a = -HALF_PI; a <= TWO_PI-HALF_PI; a += TWO_PI/(shape+2)){
                vertex(width/2 + radius*cos(a), height/2 + radius*sin(a));
            }
            endShape();
        } else {
            circle(width / 2, height / 2, 2*radius);
        }
        colorMode(RGB, 255, 255, 255);
    }

    public void sliderExample2(ScrollWindow win) {
        float radius = win.sliders[0].value;
        PGraphics c = win.canvas;
        c.colorMode(HSB, 360, 100, 100);
        c.strokeWeight(2);
        c.stroke(0);
        c.fill(0, 100, 100);
        c.ellipse(win.Width / 2, win.Height / 2, radius, radius);
    }
}