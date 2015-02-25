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

public class icm_15_4_imageProcessingWithPixels extends PApplet {

/* j.stephens - prep for portal controller - 2015-02-23
    - ex. image processing with Pixels
    - Shiffman's lecture: 
https://vimeo.com/channels/introcompmedia/108975594
*/



PImage blankImage;
PImage outputScreen;
PImage imgNeek;
PImage imgGit;
PImage imgICM;
boolean isOn = true;


public void setup() {
	size(640,480);
	background(0);
	imgNeek = loadImage("../../data/neek.png");
	imgGit = loadImage("../../data/gitGraph.png");
	imgICM = loadImage("../../data/icm.png");
	//outputScreen = createImage();
}



public void draw() {
	if (isOn) {
		outputScreen = imgGit;
	}
	else {
		outputScreen = imgICM;
	}

	image(outputScreen, 0, 0, width, height);
	
	

}

public void mousePressed() {
	isOn = !isOn;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "icm_15_4_imageProcessingWithPixels" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
