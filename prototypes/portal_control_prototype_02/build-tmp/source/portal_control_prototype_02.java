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

public class portal_control_prototype_02 extends PApplet {

/* jstephens portal_control_prototype_02  - 2015-03

1. [] create PImage array of journals
2. [] display random journal at mousepress
3. [] display random journal at iPhone press

- [] channel class????

*/


////////////////
//  GLOBALS FOR DRAWING 
//

// Set to 'true' to preload all images before starting (slower).
// Set to 'false' to load images as they're used (good for development).
boolean PRELOAD_IMAGES 	= true;

// Size of the output screen.  Use 'displayWidth' and 'displayHeight' for full screen size, or specify explicit size.
int SCREEN_WIDTH 		= 1024; 
int SCREEN_HEIGHT 		= 768; 

// Factor by which the dimensions of the source images must be multiplied in order to match the SCREEN_WIDTH
//    - ratio of the source images (640x480) to the screen size (SCREEN_WIDTH x SCREEN_HEIGHT)
float VIDEO_SCALE 		= SCREEN_WIDTH/640;      // 1.6

// FACTOR by which the dimensions of source images (640)must be multiplied to match chnl_MONITOR size (SCREEN_WIDTH/5)
float MONITOR_SCALE 	= (SCREEN_WIDTH/640) * (.2f);				// 1.6 * .2 (increase to SCREEN_WIDTH, then reduce by 1/5 the screen size

// FACTOR by which scale +/- at each iteration.  not sure if this will be useful  given touchOSc controls
float SCALE_FACTOR 		= 1.5f;
// Location where we'll save snapshots.
String SNAP_FOLDER_PATH = "~/videoalchemy/snaps/portal_control_snaps/proto_02/";

//
//  END GLOBALS FOR DRAWING 
////////////////


// PImage arrays to hold the source images.
int numOfEmblems 	= 20; //total = xx , 20=troubleshooting 
PImage[] emblem = new PImage[numOfEmblems];

int numOfJournalPages 	= 9; //total = xx , // gotta keep it here until we figure out what to do with the leading ZEROS
PImage[] journal = new PImage[numOfJournalPages];


 
public void setup() {
 println("Initializing window at " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
 size (SCREEN_WIDTH, SCREEN_HEIGHT);

 //optional
 smooth();

 // 
 // preload images if necessary
 //
 if (PRELOAD_IMAGES) {
    for (int i = 0; i < numOfJournalPages; i++)      
    	getJournalPage(i);
    } 
}

public void draw() {
	ellipse(mouseX, mouseY, width, height);
}

/////////////////////////
//  FUNCTIONS
//

//
public PImage getJournalPage(int journalPage) {
	if (journal[journalPage] == null) {
		println("loading journal page "+journalPage+" of "+numOfJournalPages);
        journal[journalPage] = loadImage("../../images/journal-pages/00"+journalPage+".png");
    }
	return journal[journalPage]; 
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control_prototype_02" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
