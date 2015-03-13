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

public class portal_control extends PApplet {

//jstephens portal-control 2015-03

//includes beginnings of rvl-config-metaphor
// - mixers, sources, displays, monitors, inputs outputs etc

/*STEPS
1. [ ] standard, non-feedback, dual input video switcher

*/

// SOURCES
PImage src1;

// RECIEVERS
PImage mx1; 
PImage mxBase;

// MONITORS
PImage src1_MONITOR;
PImage mx1_MONITOR;
PImage mxBase_MONITOR;


// DISPLAYS
PImage projector_DISPLAY;

// videoScale holds the ratio of source imagery to output screen
//   - in this case, 1024/768 :: 640/480  
float videoScale = 1.6f;

public void setup() {
	//project dimensions from  Chabot
	size (1024, 768);

	//select source material
	src1 			= loadImage("../data/000.png");


	//turn on the mixers
	mx1 			= createImage(width, height, ARGB);
	mxBase 			= createImage(width, height, ARGB);

	//hook up monitors
	src1_MONITOR 	= createImage(width, height, ARGB);
	mx1_MONITOR 	= createImage(width, height, ARGB);
	mxBase_MONITOR 	= createImage(width, height, ARGB);
	
	//hook up displays
	projector_DISPLAY=createImage(width, height, ARGB);

}



public void draw() {
	fill(255);
	//ellipse(mouseX,mouseY,20,20);

	image(src1,mouseX,mouseY, src1.width*videoScale, src1.height*videoScale);
}	
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
