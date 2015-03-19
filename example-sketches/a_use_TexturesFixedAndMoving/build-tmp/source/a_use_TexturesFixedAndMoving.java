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

public class a_use_TexturesFixedAndMoving extends PApplet {


/*

 Fixed Moving Textures 2D by Amnon Owed (May 2013)
 https://github.com/AmnonOwed
 http://vimeo.com/amnon
 
 1. FIXED texture on a MOVING shape
 2. MOVING texture on a MOVING shape
 
 MOUSE PRESS = change texture
 
 Built with Processing 2.0b8 / 2.0b9 / 2.0 Final
 
 Photographs by Folkert Gorter (@folkertgorter / http://superfamous.com/) made available under a CC Attribution 3.0 license.
 
*/

int NUMSEGMENTS = 16; // the number of segments for the shapes
float DIAM_FIXED = 350; // the diameter of the rotating shape with the fixed texture
float DIAM_INNER = 125; // the inner diameter of the moving shape with the moving texture
float DIAM_OUTER = 275; // the outer diameter of the moving shape with the moving texture

// arrays to store the pre-calculated xy directions of all segments
float[] xL = new float[NUMSEGMENTS];
float[] yL = new float[NUMSEGMENTS];

PImage[] images = new PImage[5]; // array to hold 3 input images
int currentImage; // variable to keep track of the current image
float fc1, fc2; // global variables used by many vertices for their dynamic movement

PImage output;
PImage feedback;

public void setup() {
  size(1600, 800, P2D); // use the P2D OpenGL renderer
  textureMode(NORMAL); // set texture coordinate mode to NORMALIZED (0 to 1)
  smooth(6); // set smooth level 6 (default is 2)

  // load the images from the _Images folder (relative path from this sketch's folder)
  images[0] = loadImage("../../images/journal-pages/039.png");
  images[1] = loadImage("../../images/journal-pages/040.png");
  images[2] = loadImage("../../images/journal-pages/041.png");
  images[3] = loadImage("../../images/journal-pages/042.png");
  images[4] = loadImage("../../images/journal-pages/043.png");
  currentImage = PApplet.parseInt(random(images.length)); // randomly choose the currentImage

  output = createImage(width, height, ARGB);
  feedback = createImage(width, height, ARGB);


  float step = TWO_PI/NUMSEGMENTS; // generate the step size based on the number of segments
  // pre-calculate x and y based on angle and store values in two arrays
  for (int i=0; i<xL.length; i++) {
    float theta = step * i; // angle for this segment
    xL[i] = sin(theta);
    yL[i] = cos(theta);
  }
}

public void draw() {
  image(images[currentImage], 0, 0, width, PApplet.parseFloat(width)/height*images[currentImage].height); // resize-display image correctly to cover the whole screen
  
  //start feedback
image(output, 0,0, width, height);


  fill(255, 125+sin(frameCount*0.01f)*125); // white fill with dynamic transparency
  rect(0, 0, width, height); // rect covering the whole canvas


  // 1. FIXED texture on a MOVING shape
  pushMatrix(); // use push/popMatrix so each Shape's translation does not affect other drawings
  translate(0.25f*width, 0.5f*height); // translate to the left-center
  rotate(frameCount*0.01f); // rotate around this center
  noStroke(); // turn off stroke
  beginShape(TRIANGLE_FAN); // input the shapeMode in the beginShape() call
  texture(images[currentImage]); // set the texture to use
  vertex(0, 0, 0.5f, 0.5f); // define a central point for the TRIANGLE_FAN, note the (0.5, 0.5) uv texture coordinates
  for (int i=0; i<NUMSEGMENTS+1; i++) {
    drawVertex(i==NUMSEGMENTS?0:i, DIAM_FIXED); // make sure the end equals the start & draw the vertex using the custom drawVertex() method
  }
  endShape(); // finalize the Shape
  popMatrix(); // use push/popMatrix so each Shape's translation does not affect other drawings

  // calculate fc1 and fc2 once per draw(), since they are used for the dynamic movement of many vertices
  fc1 = frameCount*0.01f;
  fc2 = frameCount*0.02f;

  // 2. MOVING texture on a MOVING shape
  pushMatrix(); // use push/popMatrix so each Shape's translation does not affect other drawings
  translate(0.75f*width, 0.5f*height); // translate to the right-center
  stroke(255); // set stroke to white
  beginShape(TRIANGLE_STRIP); // input the shapeMode in the beginShape() call
  texture(images[currentImage]); // set the texture to use
  for (int i=0; i<NUMSEGMENTS+1; i++) {
    int im = i==NUMSEGMENTS?0:i; // make sure the end equals the start

    // each vertex has a noise-based dynamic movement
    float dynamicInner = 0.5f + noise(fc1+im);
    float dynamicOuter = 0.5f + noise(fc2+im);
    
    drawVertex(im, DIAM_INNER*dynamicInner); // draw the vertex using the custom drawVertex() method
    drawVertex(im, DIAM_OUTER*dynamicOuter); // draw the vertex using the custom drawVertex() method
  }
  endShape(); // finalize the Shape
  popMatrix(); // use push/popMatrix so each Shape's translation does not affect other drawings

/////////
// jasons attempt to make sense of this
pushMatrix();
translate(-width/2, -height/2);
translate(mouseX,mouseY);
 
 //image(feedback,0,0,width, height);

 feedback = get();
 
 output = feedback;
 popMatrix();
///
//////////////
}

// custom method that draws a vertex with correct position and texture coordinates
// based on index and a diameter input parameters
public void drawVertex(int index, float diam) {
  float x = xL[index]*diam; // pre-calculated x direction times diameter
  float y = yL[index]*diam; // pre-calculated y direction times diameter
  // calculate texture coordinates based on the xy position
  float tx = x/images[currentImage].width+0.5f;
  float ty = y/images[currentImage].height+0.5f;
  // draw vertex with the calculated position and texture coordinates
  vertex(x, y, tx, ty);
}

public void mousePressed() {
  currentImage = ++currentImage%images.length; // scroll through images on mouse press
}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "a_use_TexturesFixedAndMoving" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
