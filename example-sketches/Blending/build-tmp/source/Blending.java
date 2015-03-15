import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Blending extends PApplet {

/**
 * Blending 
 * by Andres Colubri. 
 * 
 * Images can be blended using one of the 10 blending modes 
 * (currently available only in P2D and P3).
 * Click to go to cycle through the modes.  
 */

// NOTE: THIS EXAMPLE IS IN PROGRESS -- REAS

PImage img1, img2;
int selMode = REPLACE;
String name = "REPLACE";
int picAlpha = 255;

public void setup() {
  size(640, 360, P3D);
  img1 = loadImage("layer1.jpg");
  img2 = loadImage("layer2.jpg"); 
  noStroke();
}

public void draw() {
  
  picAlpha = PApplet.parseInt(map(mouseX, 0, width, 0, 255));
  
  background(0);
  
  tint(255, 255);
  image(img1, 0, 0);

  blendMode(selMode);  
  tint(255, picAlpha);
  image(img2, 0, 0);

  blendMode(REPLACE); 
  fill(255);
  rect(0, 0, 94, 22);
  fill(0);
  text(name, 10, 15);
}

public void mousePressed() {
  
  if (selMode == REPLACE) { 
    selMode = BLEND;
    name = "BLEND";
  } else if (selMode == BLEND) { 
    selMode = ADD;
    name = "ADD";
  } else if (selMode == ADD) { 
    selMode = SUBTRACT;
    name = "SUBTRACT";
  } else if (selMode == SUBTRACT) { 
    selMode = LIGHTEST;
    name = "LIGHTEST";
  } else if (selMode == LIGHTEST) { 
    selMode = DARKEST;
    name = "DARKEST";
  } else if (selMode == DARKEST) { 
    selMode = DIFFERENCE;
    name = "DIFFERENCE";
  } else if (selMode == DIFFERENCE) { 
    selMode = EXCLUSION;  
    name = "EXCLUSION";
  } else if (selMode == EXCLUSION) { 
    selMode = MULTIPLY;  
    name = "MULTIPLY";
  } else if (selMode == MULTIPLY) { 
    selMode = SCREEN;
    name = "SCREEN";
  } else if (selMode == SCREEN) { 
    selMode = REPLACE;
    name = "REPLACE";
  }
}

public void mouseDragged() {
  if (height - 50 < mouseY) {
    picAlpha = PApplet.parseInt(map(mouseX, 0, width, 0, 255));
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Blending" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
