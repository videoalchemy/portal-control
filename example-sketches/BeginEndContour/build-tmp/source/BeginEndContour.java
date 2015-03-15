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

public class BeginEndContour extends PApplet {

/**
 * BeginEndContour
 * 
 * How to cut a shape out of another using beginContour() and endContour()
 */
 
PShape s;
PImage img;

public void setup() {
  size(640, 360, P2D);
  smooth();

  img = loadImage("../../data/000.png");

  
  // Make a shape
  s = createShape();
  s.beginShape();
  s.fill(0);
  s.stroke(255);
  s.strokeWeight(2);
  // Exterior part of shape
  
  s.vertex(-100,-100);
  s.vertex(100,-100);
  s.vertex(100,100);
  s.vertex(-100,100);
  
  // Interior part of shape
  s.beginContour();
  s.vertex(-10,-10);
  s.vertex(10,-10);
  s.vertex(10,10);
  s.vertex(-10,10);
  s.endContour();
  
  // Finishing off shape
  s.endShape(CLOSE);

  
}

public void draw() {
  background(52);
  // Display shape
  // Make a shape

  //image(img,0,0,width,height);
  /*
  s = createShape();
  s.beginShape();
  s.fill(0);
  s.stroke(255);
  s.strokeWeight(2);
  // Exterior part of shape
  s.vertex(-100,-100);
  s.vertex(100,-100);
  s.vertex(100,100);
  s.vertex(-100,100);
  
  // Interior part of shape
  s.beginContour();
  s.vertex(-10,-10);
  s.vertex(10,-10);
  s.vertex(10,10);
  s.vertex(-10,10);
  s.endContour();
  
  // Finishing off shape
  s.endShape(CLOSE);
  */

  translate(width/2, height/2);
  // Shapes can be rotated
  s.rotate(0.01f);
  shape(s);
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BeginEndContour" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
