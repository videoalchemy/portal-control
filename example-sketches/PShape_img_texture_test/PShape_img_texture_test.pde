PShape star;

PImage testImg;
PImage buffer;

PGraphics pg;


int screenScale = 3;

void setup() {
  size(400*screenScale, 400*screenScale, P2D);
  pg = createGraphics(40*screenScale, 40*screenScale);
  buffer = createImage(width, height, ARGB);
  testImg = loadImage("../../images/angels/angel000.png");


  // First create the shape
  star = createShape();

  star.beginShape();
  star.textureMode(NORMAL);
  star.texture(testImg);
  // You can set fill and stroke
  //star.fill(102);
  //star.stroke(255);
  star.strokeWeight(2);
  // Here, we are hardcoding a series of vertices
  star.vertex(100, 100,0,0);
  star.vertex(200, 100,1,0);
  star.vertex(200, 200,1,1);
  star.vertex(100, 200,0,1);

  /*
  star.vertex(29, 40);
  star.vertex(0, 25);
  star.vertex(-29, 40);
  star.vertex(-23, 7);
  star.vertex(-47, -15);
  star.vertex(-14, -20);
  */

  star.endShape(CLOSE);
}


void draw() {
  background(51);
  
  pushMatrix();
  image(testImg, mouseX, mouseY,51*screenScale, 30);
  popMatrix();

  
  translate(mouseX, mouseY);
  shape(star);


}


