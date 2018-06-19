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

public class texture_feedback extends PApplet {

PImage img;

public void setup() {
  size(1024, 768, P3D);
  img = loadImage("000.png");
  noStroke();
}

public void draw() {
  background(0);
  translate(width / 2, height / 2);
  rotateY(map(mouseX, 0, width, -2*PI, 2*PI));
  rotateZ(PI/6);
  beginShape();
  texture(img);
  vertex(-100, -100, 0, 0, 0);
  vertex(100, -100, 0, 400, 0);
  vertex(100, 100, 0, 400, 400);
  vertex(-100, 100, 0, 0, 400);
  endShape();
}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "texture_feedback" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
