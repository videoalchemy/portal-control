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

public class portal_control_app extends PApplet {

/*******************************************************************
 *	VideoAlchemy "Portal Control" Computational Feedback Interface
 *
 * Copyright (c) 2015 Jason Stephens & Video Alchemy Collective
 * The MIT License (MIT)
 *******************************************************************/

/* TODO
	[] Made FEEDBACK its own CLASS!!!!
	[] display random journal at iPhone press

*/

String project = "portal-control";
String version = "v0.6.0_dev";

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
float MONITOR_SCALE 	=  (SCREEN_WIDTH/640) * (.2f);		// 20% of full screen should be 5 monitors		// 1.6 * .2 (increase to SCREEN_WIDTH, then reduce by 1/5 the screen size

// FACTOR by which scale +/- at each iteration.  not sure if this will be useful  given touchOSc controls
float SCALE_FACTOR 		= 1.5f;
// Location where we'll save snapshots.
String SNAP_FOLDER_PATH = "../../snaps/portal_control_snaps/";

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
Channel chnl_1_journals;
Channel chnl_2_emblems;
Channel chnl_3;

PImage feedback_of_chnl_1;
PImage feedback_of_chnl_2;

 
public void setup() {
 	feedback_of_chnl_1 = createImage(width, height, ARGB);
 	feedback_of_chnl_2 = createImage(width, height, ARGB);

 	println("Initializing window at " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
 	size (SCREEN_WIDTH, SCREEN_HEIGHT, P2D);  //ditch the 'P2D' if we have Kinect issues

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

    // create channels and prepopulate with an image
    chnl[0] = chnl_1_journals = new Channel("  chnl_1_journals", journal[1]);
	chnl[1] = chnl_2_emblems = new Channel("  chnl_2_emblems", emblem[1]);
	chnl[2] = chnl_3 = new Channel("  chnl_3", emblem[1]);
  	//chnl[2] = blueArm = new Arm(" blue", blueLeft, blueRight, BLUE_OPACITY);

	
	printInstructions();
}

public void draw() {
	
	//image(getJournalPage(pageNum), 0, 0, width/2, height);
	//image(getEmblem(pageNum), width/2, 0, width/2, height);


	///////////////////////
	//    display the Channel outputs

	//chnl_1_journals.display(chnl_1_journals.output());
	//chnl_2_emblems.display(chnl_2_emblems.output());
	



	///////////////////////
	//   ADD SOURCE CHANNELS TO FEEDBACk LOOP
	//feedback_of_chnl_1 = chnl_3.getFeedbackFrom(chnl_1_journals.output()); // ask for PImage
	feedback_of_chnl_2 = chnl_3.getFeedbackFrom(chnl_2_emblems); // ask for object
	

	///////////////////////
	//    DISPLAY FEEDBACK LOOPS
	//chnl_3.display(feedback_of_chnl_1);
	chnl_3.display(feedback_of_chnl_2);


	//chnl_3.display(chnl_3.feedback(chnl_1_journals.output()));
	

	///////////////////////
	//    display the Channel Monitors

	chnl_1_journals.monitor(chnl_1_journals.output(), MONITOR_SCALE, 0);
	chnl_2_emblems.monitor(chnl_2_emblems.output(), MONITOR_SCALE, .2f);
	chnl_3.monitor(feedback_of_chnl_1, MONITOR_SCALE, .4f);


	// checks for button press
	updateControlsFromKeyboard();
}	













///////////////////////////////////////
//  GO GET THE SOURCE IMAGE!

public PImage getJournalPage(int journalPage) {
	if (journal[journalPage] == null) {
		println("loading journal page "+journalPage+" of "+numOfJournalPages);
        journal[journalPage] = loadImage("../images/journal-pages/00"+journalPage+".png");
    }
	return journal[journalPage]; 
}

//
public PImage getEmblem(int anEmblem) {
	if (emblem[anEmblem] == null) {
		println("loading emblem "+anEmblem+" of "+numOfEmblems);
        emblem[anEmblem] = loadImage("../images/angels/angel00"+anEmblem+".png");
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
   	println("   'J'	  selects a new journal page as the source image for chnl_1_journals");
   	println("   'E'	  selects a new emblem as the source image for chnl_1_journals");
   	println("   '1'	  selects a new journal page as the source image for chnl_2_emblems");
   	println("   'Q'	  selects a new emblem as the source image for chnl_2_emblems");
   	println("          -----------------------------------");
   	println("");
}
class Channel {

	String name;


	PImage sourceImage;
	PImage chnl_output;
	PImage chnl_feedback;

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	
	
	
	Channel(String _name, PImage _sourceImage)  {
		name      = _name;
		sourceImage = _sourceImage;
		chnl_feedback = createImage(width, height, ARGB);
		
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

	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	public void monitor(PImage monitor, float monitorScale, float monitorPosition) {
		image(monitor, width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}

	// use this to view a channel's processed OUTPUT at full screen size
	// this function internally calls the PImage OUTPUT function used by 
	// other channels and mixers 
	public void display(PImage display) {
		image(display, 0, 0, width, height);
	}


	public PImage output(){
		
		/////////////////////
		// use pixel array here
		chnl_output = sourceImage;

		
		//////////////////////
		//   IMAGE PROCESSING


		///////////////////////
		//   

		/////////////////////////
		//   ROTATE SCALE TRANSFORM

		return chnl_output;
	}




	public PImage getFeedbackFrom(PImage channelIn) {

		
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		//image(chnl_output, 0, 0, width, height);

		image(channelIn, 0, 0, width, height);

		/////////////////////////
		//  GENERATE FEEDBACK
		imageMode(CENTER);

		image(chnl_feedback, mouseX, mouseY, width, height);

		imageMode(CORNER);


		chnl_feedback = get();

		//  END FEEDBACK
		//////////////////////////


		/////////////////
		// add some simple feedback here
		// draw yourself to something that changes, then get yourself


		return chnl_feedback;
	}


	// Pass a Channel Class and return a feedback loop
	public PImage getFeedbackFrom(Channel chnl) {

		
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		//image(chnl_output, 0, 0, width, height);

		image(chnl.output(), 0, 0, width, height);

		/////////////////////////
		//  GENERATE FEEDBACK
		imageMode(CENTER);

		//image(chnl_feedback, mouseX, mouseY, width-150, height-150);

		

		//////////////////
		//  START TEXTURE MAP
  		beginShape();
   		textureMode(NORMAL);
  		texture(chnl_feedback);
        vertex(mouseX, mouseY, 0, 0);
        vertex(width, 0, 1,0);
  		vertex(width, height, 1, 1);
  		vertex(0, height, 0, 1);
  		textureMode(IMAGE);
  		endShape(CLOSE);

  		imageMode(CORNER);

		chnl_feedback = get();

		//  END FEEDBACK
		//////////////////////////


		/////////////////
		// add some simple feedback here
		// draw yourself to something that changes, then get yourself


		return chnl_feedback;
	}
}


/*
class FeedbackChannel {

	String name;


	PImage defaultSource;
 
	PImage output;
	PImage buffer;
	PImage feedback

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	
	
	
	FeedbackChannel(String _name, PImage _defaultSource)  {
		name 	      = _name;
		defaultSource = _defaultSource;
		
		output = createImage(width, height, ARGB);
		buffer = createImage(width, height, ARGB);
		feedback = createImage(width, height, ARGB);
		
	}





	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	void monitor(PImage monitor, float monitorScale, float monitorPosition) {
		image(monitor, width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}

	// use this to view a channel's processed OUTPUT at full screen size
	// this function internally calls the PImage OUTPUT function used by 
	// other channels and mixers 
	void display(PImage display) {
		image(display, 0, 0, width, height);
	}


	PImage output(){
		
		/////////////////////
		// use pixel array here
		chnl_output = sourceImage;

		
		//////////////////////
		//   IMAGE PROCESSING


		///////////////////////
		//   

		/////////////////////////
		//   ROTATE SCALE TRANSFORM

		return chnl_output;
	}

}




/*
	PImage feedback(PImage channelIn) {

		
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		//image(chnl_output, 0, 0, width, height);

		image(channelIn, 0, 0, width, height);

		/////////////////////////
		//  GENERATE FEEDBACK
		imageMode(CENTER);

		image(chnl_feedback, mouseX, mouseY, width, height);

		imageMode(CORNER);


		chnl_feedback = get();

		//  END FEEDBACK
		//////////////////////////


		/////////////////
		// add some simple feedback here
		// draw yourself to something that changes, then get yourself


		return chnl_feedback;
	}



*/
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
    chnl_1_journals.changeSourceImage("journals");
  } else if (currentKey == 'E') {
    chnl_1_journals.changeSourceImage("emblems");
  }

   else if (currentKey == '1') {
    chnl_2_emblems.changeSourceImage("journals");
  } else if (currentKey == 'Q') {
    chnl_2_emblems.changeSourceImage("emblems");
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
    String[] appletArgs = new String[] { "portal_control_app" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
