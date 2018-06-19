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

public class praphic_test_01 extends PApplet {

/* jstephens - pgraphics practice - 2015-03

Create two pgraphics layers and draw them into the processing screen
*/


PGraphics top;
PGraphics bottom;


public void setup() {
size(640, 480);
top = createGraphics(width, height);
bottom = createGraphics(width, height);

}


public void draw(){

	top.beginDraw();
	top.fill(255,0,0,150);
	//top.translate(width/2, height/2);
	top.translate(mouseX,mouseY);
	top.ellipse(0, 0, 100, 100);
	top.endDraw();

	image(top,0, 0,width, height);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "praphic_test_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
