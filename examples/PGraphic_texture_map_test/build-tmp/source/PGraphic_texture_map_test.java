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

public class PGraphic_texture_map_test extends PApplet {

PGraphics pg;

PImage testImg;
PImage buffer;

int screenScale = 3;

public void setup() {
  size(400*screenScale, 400*screenScale, P2D);
  pg = createGraphics(40*screenScale, 40*screenScale);
  buffer = createImage(width, height, ARGB);
  testImg = loadImage("../../images/angels/angel000.png");
}

public void draw() {
  pg.beginDraw();
  //pg.background(100);
  pg.stroke(255);
  pg.line(20*screenScale, 20*screenScale, mouseX, mouseY);
  



pg.endDraw();


  buffer.beginShape();
  buffer.texture(testImg);
  buffer.vertex(0,0,0,0);
  buffer.vertex(width,0,testImg.width,0);
  buffer.vertex(width,height,testImg.width,testImg.height);
  buffer.vertex(0,height,0,testImg.height);
  buffer.endShape();


/*
  beginShape();
  texture(testImg);
  vertex(0,0,0,0);
  vertex(width,0,testImg.width,0);
  vertex(width,height,testImg.width,testImg.height);
  vertex(0,height,0,testImg.height);
  endShape(CLOSE);
*/



  image(pg, 9*screenScale, 30); 

  image(buffer, 200, 200);
 // image(pg, 51*screenScale, 30);

  //image(testImg, 51*screenScale, 30);


}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PGraphic_texture_map_test" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
