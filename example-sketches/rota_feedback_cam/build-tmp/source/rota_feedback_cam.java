import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.opengl.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class rota_feedback_cam extends PApplet {




Capture cam;

float frameSize=10;
PImage img;
PImage patternLang;
PImage tex;

public void setup() {
  noCursor();

  size(800, 800, P3D);

  // If no device is specified, will just use the default.
  cam = new Capture(this, 320, 240);
  
  img = loadImage("nextGen4.png"); //start with an initial image
  patternLang = loadImage ("nextGen5.png");
  stroke(255);
}

public void draw() {
  strokeWeight(frameSize);
  background(0);

  translate(width / 2, height / 2);

  // use 2 full rotations in both directions with 2*PI
  rotateY(map(mouseX, 0, width, -2*PI, 2*PI));
  rotateX(map(mouseY, 0, width, -2*PI, 2*PI));

  //________________call camera
  //commented this out to prevent flicker
  // if (cam.available() == true) {
  // cam.read();
  // image(cam, -50, -50);
  // }
  cam.read();
  image(cam, -50, -50, 200, 200);

  //begin mesh shape and place the grabbed texture from get()
  beginShape();
  texture(img);
  vertex(-200, -200, 0, 0, 0);
  vertex(200, -200, 0, 400, 0);
  vertex(200, 200, 0, 400, 400);
  vertex(-200, 200, 0, 0, 400);
  endShape(CLOSE);

  //place a png from previous generative art piece
  image(patternLang, -200, -200, 200, 200);
  
    // acquire pixels within these dimensions and store in PImage varible 'img'
  img=get(200, 200, 400, 400);
}

public void keyPressed() {
  if (keyCode == 38) { //arrow up
    frameSize++;
  }
  else if (keyCode==40) { //arrow down
    frameSize--;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rota_feedback_cam" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
