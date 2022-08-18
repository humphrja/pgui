import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import pgui.win.Window; 
import pgui.win.Palette; 
import pgui.win.ScrollWindow; 
import pgui.btn.Button; 
import pgui.btn.Slider; 
import pgui.txt.Text; 

import pgui.btn.*; 
import pgui.*; 
import pgui.txt.*; 
import pgui.win.*; 
import japplemenubar.*; 
import processing.awt.*; 
import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.javafx.*; 
import processing.opengl.*; 
import com.jogamp.nativewindow.*; 
import com.jogamp.nativewindow.egl.*; 
import com.jogamp.nativewindow.swt.*; 
import com.jogamp.nativewindow.util.*; 
import jogamp.nativewindow.*; 
import com.jogamp.nativewindow.awt.*; 
import jogamp.nativewindow.awt.*; 
import jogamp.nativewindow.jawt.*; 
import jogamp.nativewindow.jawt.macosx.*; 
import jogamp.nativewindow.jawt.windows.*; 
import jogamp.nativewindow.jawt.x11.*; 
import jogamp.nativewindow.x11.awt.*; 
import com.jogamp.nativewindow.x11.*; 
import jogamp.nativewindow.x11.*; 
import com.jogamp.nativewindow.windows.*; 
import jogamp.nativewindow.windows.*; 
import com.jogamp.nativewindow.macosx.*; 
import jogamp.nativewindow.macosx.*; 
import com.jogamp.gluegen.runtime.opengl.*; 
import com.jogamp.opengl.*; 
import com.jogamp.opengl.fixedfunc.*; 
import com.jogamp.opengl.math.*; 
import com.jogamp.opengl.math.geom.*; 
import com.jogamp.opengl.util.*; 
import com.jogamp.opengl.util.glsl.*; 
import jogamp.opengl.*; 
import jogamp.opengl.util.*; 
import jogamp.opengl.util.glsl.*; 
import com.jogamp.opengl.util.glsl.sdk.*; 
import com.jogamp.opengl.egl.*; 
import jogamp.opengl.egl.*; 
import jogamp.opengl.es1.*; 
import jogamp.opengl.es3.*; 
import com.jogamp.opengl.util.av.*; 
import com.jogamp.opengl.util.glsl.fixedfunc.*; 
import com.jogamp.opengl.util.packrect.*; 
import com.jogamp.opengl.util.stereo.*; 
import com.jogamp.opengl.util.stereo.generic.*; 
import com.jogamp.opengl.util.texture.*; 
import com.jogamp.opengl.util.texture.spi.*; 
import jogamp.opengl.util.av.*; 
import jogamp.opengl.util.av.impl.*; 
import jogamp.opengl.util.jpeg.*; 
import jogamp.opengl.util.pngj.*; 
import jogamp.opengl.util.pngj.chunks.*; 
import jogamp.opengl.util.stereo.*; 
import com.jogamp.graph.curve.*; 
import com.jogamp.graph.curve.opengl.*; 
import com.jogamp.graph.curve.tess.*; 
import com.jogamp.graph.font.*; 
import com.jogamp.graph.geom.*; 
import jogamp.graph.curve.opengl.*; 
import jogamp.graph.curve.opengl.shader.*; 
import jogamp.graph.curve.tess.*; 
import jogamp.graph.font.*; 
import jogamp.graph.font.typecast.*; 
import jogamp.graph.font.typecast.ot.*; 
import jogamp.graph.font.typecast.ot.mac.*; 
import jogamp.graph.font.typecast.ot.table.*; 
import jogamp.graph.font.typecast.t2.*; 
import jogamp.graph.font.typecast.tt.engine.*; 
import jogamp.graph.geom.plane.*; 
import jogamp.opengl.util.glsl.fixedfunc.*; 
import com.jogamp.opengl.awt.*; 
import jogamp.opengl.awt.*; 
import jogamp.opengl.macosx.cgl.awt.*; 
import jogamp.opengl.windows.wgl.awt.*; 
import com.jogamp.opengl.swt.*; 
import com.jogamp.opengl.util.awt.*; 
import com.jogamp.opengl.util.texture.awt.*; 
import com.jogamp.opengl.util.texture.spi.awt.*; 
import jogamp.opengl.x11.glx.*; 
import jogamp.opengl.windows.wgl.*; 
import jogamp.opengl.macosx.cgl.*; 
import jogamp.opengl.gl2.*; 
import jogamp.opengl.gl4.*; 
import com.jogamp.opengl.glu.*; 
import com.jogamp.opengl.glu.gl2es1.*; 
import jogamp.opengl.glu.*; 
import jogamp.opengl.glu.error.*; 
import jogamp.opengl.glu.mipmap.*; 
import jogamp.opengl.glu.tessellator.*; 
import com.jogamp.opengl.glu.gl2.*; 
import jogamp.opengl.glu.gl2.nurbs.*; 
import jogamp.opengl.glu.nurbs.*; 
import jogamp.opengl.glu.registry.*; 
import com.jogamp.opengl.util.gl2.*; 
import com.jogamp.newt.*; 
import com.jogamp.newt.event.*; 
import com.jogamp.newt.util.*; 
import com.jogamp.newt.util.applet.*; 
import jogamp.newt.*; 
import jogamp.newt.driver.*; 
import jogamp.newt.event.*; 
import com.jogamp.newt.opengl.*; 
import com.jogamp.newt.opengl.util.*; 
import com.jogamp.newt.opengl.util.stereo.*; 
import jogamp.newt.driver.opengl.*; 
import com.jogamp.newt.awt.*; 
import com.jogamp.newt.awt.applet.*; 
import com.jogamp.newt.event.awt.*; 
import jogamp.newt.awt.*; 
import jogamp.newt.awt.event.*; 
import jogamp.newt.driver.awt.*; 
import com.jogamp.newt.swt.*; 
import jogamp.newt.swt.*; 
import jogamp.newt.swt.event.*; 
import jogamp.newt.driver.linux.*; 
import jogamp.newt.driver.x11.*; 
import jogamp.newt.driver.windows.*; 
import jogamp.newt.driver.macosx.*; 
import jogamp.newt.driver.bcm.vc.iv.*; 
import com.jogamp.common.*; 
import com.jogamp.common.jvm.*; 
import com.jogamp.common.net.*; 
import com.jogamp.common.net.asset.*; 
import com.jogamp.common.nio.*; 
import com.jogamp.common.os.*; 
import com.jogamp.common.type.*; 
import com.jogamp.common.util.*; 
import com.jogamp.common.util.awt.*; 
import com.jogamp.common.util.cache.*; 
import com.jogamp.common.util.locks.*; 
import com.jogamp.gluegen.runtime.*; 
import jogamp.common.*; 
import jogamp.common.jvm.*; 
import jogamp.common.os.*; 
import jogamp.common.os.elf.*; 
import jogamp.common.util.*; 
import jogamp.common.util.locks.*; 
import jogamp.nativetag.opengl.linux.i586.*; 
import jogamp.nativetag.opengl.linux.amd64.*; 
import jogamp.nativetag.common.linux.i586.*; 
import jogamp.nativetag.opengl.windows.i586.*; 
import jogamp.nativetag.common.linux.amd64.*; 
import jogamp.nativetag.opengl.linux.aarch64.*; 
import jogamp.nativetag.opengl.linux.armv6hf.*; 
import jogamp.nativetag.opengl.windows.amd64.*; 
import jogamp.nativetag.common.windows.i586.*; 
import jogamp.nativetag.common.linux.aarch64.*; 
import jogamp.nativetag.common.linux.armv6hf.*; 
import jogamp.nativetag.common.windows.amd64.*; 
import jogamp.nativetag.opengl.macosx.universal.*; 
import jogamp.nativetag.common.macosx.universal.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class demo extends PApplet {









Window[] windows = new Window[8];
int currentWindow = 0;          // This represents the index of the current window in windows array

float r = 200;

public void setup() {
  

  // Creating the palette of the main windows
  Palette winPalette = new Palette(color(53, 45, 
    57), color(255), color(58, 155, 216), color(224, 82, 99), color(114, 162, 118));

  // Main menu - select an example window
  Window w = new Window(this, winPalette);
  w.addHeading("Choose an example window");
  createStartBtnGrid(w);
  w.addButton("close", new Object[] {}, this, "Exit", width / 2 - 100, height - 100, 200, 70);
  windows[0] = w;
  //

  // Window 1 - Different colour palettes
  Palette p2 = new Palette(color(252, 222, 156), color(56, 29, 42), color(196, 214, 176), color(186, 86, 
    36), color(255, 165, 82));
  Window w1 = new Window(this, p2);
  w1.addHeading("Windows can have different colour palettes.");
  createBackToHomeBtn(w1);
  windows[1] = w1;
  //

  // Window 2 - Display different content
  Window w2 = new Window(this, winPalette);
  w2.addHeading("Windows can display content from custom methods too");
  createBackToHomeBtn(w2);
  w2.addContent("myCircle", new Object[] {}, new myClass());
  windows[2] = w2;
  //

  // Window 3 - Windows can be of different sizes and be within each other
  Window w3 = new Window(this, winPalette);
  w3.addHeading("Windows can exist within different windows!");
  createBackToHomeBtn(w3);
  windows[3] = w3;

  // Window 4 - scroll window
  Window w4 = new Window(this, winPalette);
  w4.addHeading("Scroll windows allow you to have scroll features :)");
  createBackToHomeBtn(w4);

  int swidth = 600;
  ScrollWindow sw = w4.addScrollWindow(p2, width / 2 - swidth - 25, 100, swidth, 600, 1000);
  sw.addHeading("This is a scroll window");
  createBackToHomeBtn(sw);

  ScrollWindow sw2 = w4.addScrollWindow(p2, width / 2 + 25, 100, swidth, 600, 800);
  sw2.addHeading("This is another scroll window!");
  sw2.addSlider(50, 200, sw2.Width / 2, 500, 400);
  sw2.addContent("sliderExample2", new Object[] { sw2 }, this);
  windows[4] = w4;
  //

  // Window 5 - Buttons - shows different type of button activations ->
  // on_press, on_release, hold
  Window w5 = new Window(this, winPalette);
  w5.addHeading("Buttons can have different activation types...");
  createBackToHomeBtn(w5);

  // On release (default)
  w5.addButton("testString", new Object[] { "On Release" }, this, "On Release", 100, 300, 200, 70);

  // On press
  Button btn2 = w5.addButton("testString", new Object[] { "On Press" }, this, "On Press", 350, 300, 200, 70);
  btn2.setActivation("on_press");

  // While holding
  Button btn3 = w5.addButton("testString", new Object[] { "Hold" }, this, "Hold", 600, 300, 200, 70);
  btn3.setActivation("hold");
  windows[5] = w5;
  //

  // Window 6 - Button recursion
  Window w6 = new Window(this, winPalette);
  w6.addHeading("Recursion?!");
  createBackToHomeBtn(w6);
  addNewButton(w6);
  windows[6] = w6;
  //

  // Window 7 - Sliders and switches
  Window w7 = new Window(this, winPalette);
  w7.addHeading("Sliders and switches");
  createBackToHomeBtn(w7);
  w7.addSlider(0, 360, width / 2, height - 200, 500);
  Slider s = w7.addSlider(50, 450, width - 200, height / 2, 200);
  s.setAxis('v', LEFT);
  w7.addContent("sliderExample", new Object[] {}, this);
  windows[7] = w7;
  //
}


public void draw() {
  windows[currentWindow].display(g); // g is the default PGraphics object for the main sketch
}


//public void myMethod() {
//  stroke(255);
//  fill(0);
//  ellipse(mouseX, mouseY, r, r);
//}

public void createStartBtnGrid(Window w) {
  int nx = 3;
  int ny = 2;
  int btnW = 150;
  int btnH = 100;
  int spacing = 25;

  float ix = width / 2 - (nx * btnW + (nx - 1) * spacing) / 2;
  float iy = height / 2 - (ny * btnH + (ny + 1) * spacing) / 2;

  String f = "setWindow";

  String[] labels = { "Colour", "Visuals", "Sub-windows", "Scroll windows", "Buttons", "Recursion" };

  for (int row = 0; row < ny; row++) {
    for (int col = 0; col < nx; col++) {
      float x = ix + col * (btnW + spacing);
      float y = iy + row * (btnH + spacing);

      Object[] args = new Object[] { col + row * nx + 1 };
      w.addButton(f, args, this, labels[col + row * nx], x, y, btnW, btnH);
    }
  }

  w.addButton(f, new Object[] { 7 }, this, "Interactives", ix + 1 * (btnW + spacing), iy + 2 * (btnH + spacing), 
    btnW, 
    btnH);
}

public void createBackToHomeBtn(Window w) {
  w.addButton("setWindow", new Object[] { 0 }, this, "Back to main menu", w.Width - 120, w.Height - 120, 100, 
    100);
}

public void close() {
  exit();
}

public void setWindow(int newWindowNum) {
  currentWindow = newWindowNum;
}

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
  colorMode(HSB, 360, 100, 100);
  strokeWeight(2);
  stroke(0);
  fill(hue, 100, 100);
  ellipse(width / 2, height / 2, radius, radius);
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
public class myClass {
  myClass() {
  }

  public void myCircle() {
    stroke(255);
    fill(0);
    ellipse(mouseX, mouseY, r, r);
  }
}
  public void settings() {  size(1400, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "demo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
