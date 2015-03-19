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

public class feedback_texture_01 extends PApplet {




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
  cam.start();


  img = loadImage("000.png"); //start with an initial image
  tex = loadImage("002.png"); //start with an initial image
  patternLang = loadImage ("002.png");
  stroke(255);
}

public void draw() {
  strokeWeight(frameSize);
  background(0);
  image(tex,0,0,width,height);
  translate(width / 2, height / 2);
  

  // use 2 full rotations in both directions with 2*PI
  rotateY(map(mouseX, 0, width, -2*PI, 2*PI));
  rotateX(map(mouseY, 0, width, -2*PI, 2*PI));
  rotate(map(mouseY, 0, width, -2*PI, 2*PI));

  
//  image(cam, -50, -50, 200, 200);

  beginShape();
  texture(img);
  vertex(-250, -250, 100, 0, 0);
  vertex(250, -250, 100, 500, 0);
  vertex(250, 250, 100, 500, 500);
  vertex(-250, 250, 100, 0, 500);
  endShape(CLOSE);
  img=get(100,100 , 500, 500);



  /*
  //ORIGINAL SHAPE
  //begin mesh shape and place the grabbed texture from get()
  beginShape();
  texture(img);
  vertex(-200, -200, 0, 0, 0);
  vertex(200, -200, 0, 400, 0);
  vertex(200, 200, 0, 400, 400);
  vertex(-200, 200, 0, 0, 400);
  endShape(CLOSE);

  //place a png from previous generative art piece
  //image(patternLang, -200, -200, 200, 200);
  
  //////////////////////////
    // acquire pixels within these dimensions and store in PImage varible 'img'
    // there parameters match the 'u' 'v' parameters from 1st shape 
  img=get(200, 200, 400, 400);
  */


  // add extra feedback layerss 
  //image(img,mouseX,mouseY);

  /////////////////////////
  //another feedback layer
  //img=get(0, 0, width, height);
  //image(img,0,0);


}

public void keyPressed() {
  if (keyCode == 38) { //arrow up
    frameSize++;
  }
  else if (keyCode==40) { //arrow down
    frameSize--;
  }
}

public void captureEvent(Capture cam){
  cam.read();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "feedback_texture_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
