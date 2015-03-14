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

public class portal_control_prototype_01 extends PApplet {

//jstephens portal-control_prototype 2015-03

//includes beginnings of rvl-config-metaphor
// - mixers, sources, displays, monitors, inputs outputs etc

/*STEPS
0. [ ] signal flow from source to projector
1. [ ] standard, non-feedback, dual input video switcher

*/

// SOURCES
PImage src0;
PImage src1;
PImage src2;
PImage src3;
PImage src4;
PImage src5;
PImage src6;
PImage src7;
PImage src8;
PImage src9;

// RECIEVERS
PImage mx1; 
PImage mxBase;

// MONITORS
PImage src1_MONITOR;
PImage src2_MONITOR;

PImage mx1_MONITOR; // grey like my MX-1 videonices
PImage mx1_input1_MONITOR;
PImage mx1_input2_MONITOR;
PImage mxBase_MONITOR;
PImage output_MONITOR;


// DISPLAYS
PImage projector_DISPLAY;

// videoScale holds the ratio of source imagery to output screen
//   - in this case, 1024/768 :: 640/480  
float videoScale = 1.6f;

// monitor scale to fit MONITORS for display side by side in a dashboard
float monitorScale = .35f;

float growFactor = 1.5f; // for increasing the size of the feedback layer

float theta = 0;  //rotate

int imageCount = 0;
int imageModulo = 0;


PFont f;

public void setup() {
	//project dimensions from  Chabot
	size (1024, 768);

	//select source material
	src0 			= loadImage("../../data/000.png");
	src1 			= loadImage("../../data/001.png");
	src2 			= loadImage("../../data/002.png");
	src3 			= loadImage("../../data/003.png");
	src4 			= loadImage("../../data/004.png");
	src5 			= loadImage("../../data/005.png");
	src6 			= loadImage("../../data/006.png");
	src7 			= loadImage("../../data/007.png");
	src8   			= loadImage("../../data/008.png");
	src9 			= loadImage("../../data/009.png");
	

	//turn on the mixers
	mx1 			= createImage(width, height, ARGB);
	mxBase 			= createImage(width, height, ARGB);

	//hook up monitors
	src1_MONITOR 	= createImage(width, height, ARGB);
	src2_MONITOR 	= createImage(width, height, ARGB);

	mx1_input1_MONITOR = createImage(width, height, ARGB);
    mx1_input2_MONITOR = createImage(width, height, ARGB);
	mx1_MONITOR 	= createImage(width, height, ARGB);
	mxBase_MONITOR 	= createImage(width, height, ARGB);
	output_MONITOR  = createImage(width, height, ARGB);
	

	//hook up displays
	projector_DISPLAY=createImage(width, height, ARGB);


  	//give monitors their own background
	assign_monitor_background_colors();



}



public void draw() {
	//fill(255);
		//label monitors by assigning text
	//write_labels_on_monitors();
	

	//create switch cases to swap out mixers
	switch(imageModulo){
		case 0:
			println("Case 0");
			mx1 = src0.get();
			mx1_input1_MONITOR = src1.get();
    		mx1_input2_MONITOR = src2.get();
			break;
		case 1:
		    println("Case 1");
		    mx1 = src1.get();
		    mx1_input1_MONITOR = src2.get();
    		mx1_input2_MONITOR = src3.get();
			break;
		case 2:
			println("Case 2");
			mx1 = src2.get();
			mx1_input1_MONITOR = src3.get();
    		mx1_input2_MONITOR = src4.get();
			break;
		case 3:
			println("Case 3");
			mx1 = src3.get();
			mx1_input1_MONITOR = src4.get();
    		mx1_input2_MONITOR = src5.get();
			break;
		case 4:
			println("Case 4");
			mx1 = src4.get();
			mx1_input1_MONITOR = src5.get();
    		mx1_input2_MONITOR = src6.get();
			break;
		case 5:
			println("Case 5");
			mx1 = src5.get();
			mx1_input1_MONITOR = src6.get();
    		mx1_input2_MONITOR = src7.get();
			break;
		case 6:
			println("Case 6");
			mx1 = src6.get();
			mx1_input1_MONITOR = src7.get();
    		mx1_input2_MONITOR = src8.get();
			break;
		case 7:
			println("Case 7");
			mx1 = src7.get();
			mx1_input1_MONITOR = src8.get();
    		mx1_input2_MONITOR = src9.get();
			break;
		case 8:
			println("Case 8");
			mx1 = src8.get();
			mx1_input1_MONITOR = src9.get();
    		mx1_input2_MONITOR = src0.get();
			break;
		case 9:
			println("Case 9");
			mx1 = src9.get();
			mx1_input1_MONITOR = src0.get();
    		mx1_input2_MONITOR = src1.get();
			break;
		default:
			println("Case default");
			mx1_input1_MONITOR = src9.get();
    		mx1_input2_MONITOR = src0.get();
			mx1 = src1.get();
			break;
		}


	//// create a view of Mixer output that contains src monitors in the foreground like a real mixer
	
	
	// send mixer to monitor
	mx1_MONITOR = mx1.get();
	// turn on and display the monitor
	image(mx1_MONITOR,0,0,width,height);



	////// connect source to a source monitor and display it
	//src1_MONITOR = src1.get();

	// set first source monitor in bottom left and work across screen with remaining monitor
	image(mx1_input1_MONITOR,0,height-src1.height*monitorScale, src1.width*monitorScale, src1.height*monitorScale);

	////// connect 2nd source to a monitor and display it
	//src2_MONITOR = src2.get();
	image(mx1_input2_MONITOR,0+src1.width*monitorScale,height-src1.height*monitorScale, src1.width*monitorScale, src1.height*monitorScale);




	/////// connect processing screen to a monitor and display it as the last monitors displayed on mixer board
	image(output_MONITOR,width - src1.width*monitorScale, height-src1.height*monitorScale, src1.width*monitorScale, src1.height*monitorScale);
	imageMode(CENTER);
	//display a larger feedback area
	pushMatrix();
	rotate(theta);
	image(output_MONITOR,mouseX, mouseY, src1.width*growFactor, src1.height*growFactor);
	popMatrix();

	imageMode(CORNER);
	output_MONITOR = get();

}

///////////////
//Functions
public void assign_monitor_background_colors(){
	// Create the font
  	printArray(PFont.list());
  	f = createFont("Arial-Black", 24);
  	textFont(f);
	

	//assign pixel color to monitor
	for(int i = 0; i < mx1_MONITOR.pixels.length; i++) {
    	float a = map(i, 0, mx1_MONITOR.pixels.length, 255, 0);
    	mx1_MONITOR.pixels[i] = color(0, 153, 204, a); 
    }
}	

public void write_labels_on_monitors() {
	//assign text to monitors
	textAlign(RIGHT);
	text("mx1_MONITOR", PApplet.parseFloat(width/2), PApplet.parseFloat(30));

}


public void keyPressed(){
	if (key == CODED) {
		if (keyCode == LEFT) {
			theta += .01f;
		} else if (keyCode == RIGHT) {
			theta -= .01f;
		} else if (keyCode == UP) {
			growFactor += .03f;

		} else if (keyCode == DOWN) {
			growFactor -= .03f;
		}

	}
}

public void mousePressed() {
	imageModulo = imageCount % 10;
	imageCount = imageCount + 1;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control_prototype_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
