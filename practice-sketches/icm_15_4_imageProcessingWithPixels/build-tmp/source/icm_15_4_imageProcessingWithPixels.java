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
TODO:
1. [x] draw images to buffer and buffer to output screen
2. [ ] use pixel array to accomplish first task


Def:
loadPixels()
	- load the pixel data for the display window into the pixel[].
	- this function must be called 
		before reading from OR writing to pixel[]
	- the rule is:
		anytime you want to manipulate the pixel array (pixel[])
		you must first call loadPixels(),
		and after changes are made, you must call updatePixels()
  updatePixels()
  	- updates the display window with the data in the display window
	- Use in conjunction with loadPixels(). 
	- If you're only reading pixels from the array, there's no need to call 
		updatePixels() \u2014 updating is only necessary to apply changes. 
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

	//image(outputScreen, 0, 0, width, height);

	/* use loadPixels to manipulate what's already on the display window
		by walking through the pixel array */

/*	
	loadPixels();	
	for (int i=0; i<width*height/2; i++) {
		pixels[i+width*height/2] = pixels[i];
		}
	updatePixels();
*/
	
	loadPixels();	
	//outputScreen.loadPixels();

	for (int i=0; i<width*height; i++) {
		pixels[i] = outputScreen.pixels[i];
		
		if ((i>mouseX+width*mouseY) && (i<width*height)){
			pixels[i] = outputScreen.pixels[i];
		}
		else{
			pixels[i] = imgNeek.pixels[i];
		}
		
	}
	updatePixels();

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
