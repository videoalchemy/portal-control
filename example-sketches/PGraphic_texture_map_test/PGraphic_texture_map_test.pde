PGraphics pg;

PImage testImg;
PImage buffer;

int screenScale = 3;

void setup() {
  size(400*screenScale, 400*screenScale, P2D);
  pg = createGraphics(40*screenScale, 40*screenScale);
  buffer = createImage(width, height, ARGB);
  testImg = loadImage("../../images/angels/angel000.png");
}

void draw() {
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
