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

1. [x] create PImage array of journals
2. [x] display random journal at mousepress
3. [ ] implement channel class


[] display random journal at iPhone press
*/
String project = "portal-control";
String version = "v0.5.4";

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
String SNAP_FOLDER_PATH = "../../../snaps/portal_control_snaps/";

//
//  END GLOBALS FOR DRAWING 
////////////////


// PImage arrays to hold the source images.
int numOfEmblems 	= 9; //total = xx , 20=troubleshooting 
PImage[] emblem = new PImage[numOfEmblems];

int numOfJournalPages 	= 9; //total = xx , // gotta keep it here until we figure out what to do with the leading ZEROS
PImage[] journal = new PImage[numOfJournalPages];

// selecting a random page num at mouse press sets this
int pageNum = 0;


// Create 1 channel
Channel[] chnl = new Channel[3];
Channel chnl_0;
Channel chnl_1;
Channel chnl_2;

 
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
    	for (int i = 0; i < numOfEmblems; i++)      
    		getEmblem(i);
    } 

    // create channels
    chnl[0] = chnl_0 = new Channel("  chnl_0", journal[1]);
	chnl[1] = chnl_1 = new Channel("  chnl_1", emblem[1]);
  	//chnl[2] = blueArm = new Arm(" blue", blueLeft, blueRight, BLUE_OPACITY);

	
	printInstructions();
}

public void draw() {
	
	//image(getJournalPage(pageNum), 0, 0, width/2, height);
	//image(getEmblem(pageNum), width/2, 0, width/2, height);


	// display the Channels
	chnl_0.display(0, .5f);
	chnl_1.display( .5f, .5f);


	// checks for button press
	updateControlsFromKeyboard();
}	

///////////////////////////////////////
//  GO GET THE SOURCE IMAGE!

public PImage getJournalPage(int journalPage) {
	if (journal[journalPage] == null) {
		println("loading journal page "+journalPage+" of "+numOfJournalPages);
        journal[journalPage] = loadImage("../../images/journal-pages/00"+journalPage+".png");
    }
	return journal[journalPage]; 
}

//
public PImage getEmblem(int anEmblem) {
	if (emblem[anEmblem] == null) {
		println("loading emblem "+anEmblem+" of "+numOfEmblems);
        emblem[anEmblem] = loadImage("../../images/angels/angel00"+anEmblem+".png");
    }
	return emblem[anEmblem]; 
}
//  END GET THE SOURCE IMAGE
////////////////////////////////////////


public void mousePressed() {
	// test for randomly indexing into the array
	randomJournalPage();

}

public void randomJournalPage(){
	print("Switching from journal page "+pageNum);
	pageNum = PApplet.parseInt(random(numOfJournalPages-1));
    println(" to "+pageNum);
}

//
public void printInstructions() {
	println("");
	println("                 Keyboard controls");
  	println("          -----------------------------------");
   	println("   ENTER  takes a snapshot and saves it to "+SNAP_FOLDER_PATH);
   	println("   TAB	  clears background");
   	println("   'J'	  selects a new journal page as the source image for chnl_0");
   	println("   'E'	  selects a new emblem as the source image for chnl_0");
   	println("   '1'	  selects a new journal page as the source image for chnl_1");
   	println("   'Q'	  selects a new emblem as the source image for chnl_1");
   	println("          -----------------------------------");
   	println("");
}
class Channel {

	String name;

	PImage sourceImage;

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	
	
	Channel(String _name, PImage _sourceImage)  {
		name      = _name;
		sourceImage = _sourceImage;
		
	}

	public void changeSourceImage(String sourceName) {
    	if (sourceName == "journals") {
    		int journalPage = PApplet.parseInt(random(numOfJournalPages-1));
    		sourceImage 	= journal[journalPage];
    	} else if (sourceName == "emblems") {
    		int anEmblem 	= PApplet.parseInt(random(numOfEmblems-1));
    		sourceImage 	=  emblem[anEmblem];
    	}
	}
	
	public void monitor() {
		//image(sourceImage, monitorX);
	}

	public void display(float monitorColumn, float widthFactor) {
		image(sourceImage, width*monitorColumn, 0, width*widthFactor, height);
	}

}


//////////////////////////////
// KeyEVENTS - flipping switches!


/////////////////////
//  KEYCODE FOR EVENTS

// Current keyCode for the pressed key
int currentKey = -1;

// Remember the current key when it goes down.
public void keyPressed() {
  currentKey = keyCode;
}

// Clear the current key when it goes up.
public void keyReleased() {
  currentKey = -1;
}

public void updateControlsFromKeyboard() {
  // if no key is currently down, make sure all of the buttons are up and bail  
  if (currentKey == -1) {
    //clearButtons();
    return;
  }

  //saves an image everytime the ENTER is pressed.
  if (currentKey==ENTER) {
    String filename = nowAsString() + ".png";
    println("SAVED AS "+filename);
    saveFrame(SNAP_FOLDER_PATH + filename);
    //saveFrame(SNAP_FOLDER_PATH + "screen-####.png");
    noCursor();
  } else {
    //println(currentKey);rg
    cursor();
  }

  // TAB key clears background (to black)
  if (currentKey==TAB) {
    background(0);
  } 


  // change the source image
  else if (currentKey == 'J') {
    chnl_0.changeSourceImage("journals");
  } else if (currentKey == 'E') {
    chnl_0.changeSourceImage("emblems");
  }

   else if (currentKey == '1') {
    chnl_1.changeSourceImage("journals");
  } else if (currentKey == 'Q') {
    chnl_1.changeSourceImage("emblems");
  }
}
//  END KEYCODE FOR EVENTS
/////////////////////////



///////////////////
//    SCREEN SNAPS


// output: right now + project + version as a string
// 2015-03-15_portal-control/portal-control_v0.5.3_01-42-50
public String nowAsString() {
  return nf(year(), 4)+"-"+
    nf(month(), 2)+"-"+
    nf(day(), 2)+"_"+
    project+"/"+
    project+"_"+
    version+"_"+
    nf(hour(), 2)+"-"+
    nf(minute(), 2)+"-"+
    nf(second(), 2);
}

// Save the current screen state as a .png in the SNAP_FOLDER_PATH,
// If you pass a filename, we'll use that, otherwise we'll default to the current date.
// NOTE: do NOT pass the ".jpg" or the path.
// Returns the name of the file saved.
public String saveScreen() {
  return saveScreen(null);
}
public String saveScreen(String fileName) {
  if (fileName == null) {
    fileName = nowAsString();
  }

  save(SNAP_FOLDER_PATH + fileName + ".png");
  println("SAVED AS "+fileName);
  return fileName;
}


//
///////////////////////

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control_prototype_02" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
