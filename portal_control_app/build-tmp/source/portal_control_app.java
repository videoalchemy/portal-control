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
/* PROJECT MILESTONES ON GITHUB:
https://github.com/VideoAlchemy/portal-control/milestones

TODO
[] verify the existence of a transparent layer transparency
[] create transparency so that the feedback loop takes the shape of the source image
	- shape the feeback from the base case shape 

[] create controls for the source image texture maps

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

// Toggle the showMonitors feature. Default to on
boolean SHOW_MONITORS = true;

//  OVERRIDE THE MAIN OUTPUT DISPLAY WITH A SINGlE CHANNEL OUTPUT
int DISPLAY_CHANNEL = 0;   

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
Channel[] chnl = new Channel[5];
Channel chnl_1_journals;
Channel chnl_2_emblems;
Channel chnl_3;
Channel chnl_4_has_controls;
Channel chnl_5_vertex1;




 
public void setup() {
 	println("Initializing window at " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
 	size (SCREEN_WIDTH, SCREEN_HEIGHT, P2D);  //ditch the 'P2D' if we have Kinect issues

 	//optional
 	smooth();

 	////////////////////////////////// 
 	// preload images if necessary
 	//
 	if (PRELOAD_IMAGES) {
    	for (int i = 0; i < numOfJournalPages; i++)      
    		getJournalPage(i); 
    	for (int i = 0; i < numOfEmblems; i++)      
    		getEmblem(i);
    } 
	//
	printInstructions();

 	

    ////////////////////////////////////////////////
    //    CREATE CHANNELS
    //   TEST THE UNDERLOAD
    //chnl[0] = chnl_1_journals = new Channel("  chnl_1_journals", journal[1]);
    chnl[0] = chnl_1_journals = new Channel("  chnl_1_journals");
	chnl[1] = chnl_2_emblems = new Channel("  chnl_2_emblems");
	chnl[2] = chnl_3 = new Channel("  chnl_3");
  	chnl[3] = chnl_4_has_controls = new Channel("  chnl_4_has_controls");
  	chnl[4] = chnl_5_vertex1 = new Channel("  chnl_5_vertex1");

  	/////////////////////
  	//   TEST OVERLOADED OBJECT WITH PVECTOR for vertex 1


  	//    END CREATE CHANNELS
  	////////////////////////////////////////////////
}

public void draw() {
	
	////////////////////////////////////////////////////////////////
	//   CREATE FEEDBACK FROM CHANNEL
	//         by pass Channel object to the feedback channel /////
	            /////////////////////////////////////////////////////
	chnl_3.createFeedbackFrom(chnl_2_emblems); // ask for object
	//chnl_3.createFeedbackFrom(chnl_4_has_controls); // ask for object










/////////////////////////////////////
//      UPDATE TOOLS AND MONITORS	
update_PortalTools();
/////////////////////////////////////
}	


///////
// check_KeyboardControls_ChannelMonitors_ChannelDisplayOverrideSwitches
public void update_PortalTools(){
	//    SWITCH BETWEEN CHANNEL DISPLAYS - for testing or in case you get lost
	switchDisplayChannel();
	///////////////////////
	//    SHOW the Channel Monitors
	showChannelMonitors();
	// checks for button press
	updateControlsFromKeyboard();
}

/////////////////////////////////////////////////////////////////
//      DISPLAY SELECTED CHANNEL ON MAIN SCREEN
////////     ----this is screaming for an awesome interface--------
public void switchDisplayChannel(){
	switch(DISPLAY_CHANNEL){
		case 0:
			break;
		case 1:
			//chnl_1_journals.display();
			break;
		case 2:
			//chnl_2_emblems.display();
			break;
		//////////////
		// ADD ALL CHANNELS HERE
		//////////////
		default:
			break;
	}
}
//      END DISPLAY CHANNEL
/////////////////////////////////////////////////////////////////




///////////////////////////////////////////////////////////
//    SHOW MONITORS
public void showChannelMonitors(){
	if (SHOW_MONITORS) {

//DEBUG::    REDUCE FLICKER of monitor view by looping through 2x
		for (int i = 0; i < 1; i++){
			chnl_1_journals.monitor(MONITOR_SCALE, 0);
			chnl_2_emblems.monitor(MONITOR_SCALE, .2f);
			chnl_3.monitor(MONITOR_SCALE, .4f);
	}
}
}
//    END SHOW MONITORS
/////////////////////////////////////////////////////////



///////////////////////////////////////
//  GO GET THE SOURCE IMAGES!
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

////////////////////////////////////////
//   PROVIDE SOME POINTERS
public void printInstructions() {
	println("");
	println("                 Keyboard controls");
  	println("          -----------------------------------");
   	println("   ENTER  takes a snapshot and saves it to "+SNAP_FOLDER_PATH);
   	println("   TAB	   clears background");
   	println("   'q'	   grabs journal page for chnl_1");
   	println("   'w'	   grabs emblem for chnl_1");
   	println("   'e'	   grabs journal page for chnl_2");
   	println("   'r'	   grabs emblem for chnl_2");
   	println("   't'	   grabs journal page for chnl_3");
   	println("   'y'	   grabs emblem for chnl_3");
   	println("   1-9	   sends DISPLAY_CHANNEL to MAIN screen");
   	println("   '0'    removes DISPLAY_CHANNEL from MAIN screen");
   	println("   '-'     ");

   	println("          -----------------------------------");
   	println("");
}




public void mousePressed() {
	// test for randomly indexing into the array
	//randomJournalPage();
}
public void randomJournalPage(){
	print("Switching from journal page "+pageNum);
	pageNum = PApplet.parseInt(random(numOfJournalPages-1));
    println(" to "+pageNum);
}
class Channel {

	String name;


	PImage sourceImage;
	PImage chnl_output;
	PImage chnl_feedback;

	PVector vertex1;

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	float vertexX; 
	


	/////////////////////////////////
	//      CONSTRUCTOR
	Channel(String _name)  {
		name      		= _name;
		sourceImage 	= createImage(width, height, ARGB);
		chnl_feedback 	= createImage(width, height, ARGB);
		chnl_output 	= createImage(width, height, ARGB);
	}
	
	

	






	///////////////////////////////////////
	//    SOURCE CONTENT PROVIDERS
	public void changeSourceImage(String sourceName) {
    	if (sourceName == "journals") {
    		int journalPage = PApplet.parseInt(random(numOfJournalPages-1));
    		sourceImage 	= journal[journalPage];
    		// Make the the new source image the OUTPUT for this source provider
    		chnl_output = sourceImage;
    	} else if (sourceName == "emblems") {
    		int anEmblem 	= PApplet.parseInt(random(numOfEmblems-1));
    		sourceImage 	=  emblem[anEmblem];
    		// Set this channel's output to the source image if we find ourselves asking for a source image from this object.
    		// If we're pulling a source image, that must mean the output for this channel is sourceImage
    		chnl_output = sourceImage;	
    	}
	}
	//     END SOURCE CONTENT SELECTION
	/////////////////////////////////////////////////




	////////////////////////////////////////////
	//    SMALL MONITOR SCREENS - so we can see what we're doing
	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	public void monitor(PImage monitor, float monitorScale, float monitorPosition) {
		image(monitor, width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}

	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	public void monitor(float monitorScale, float monitorPosition) {
		image(this.output(), width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}
	//      END MONITORS
	/////////////////////////////////////////////

	
	/////////////////////////////////////////
	//   CHANNELS CAN DISPLAY THEMSEVES 
	public void display() {
		image(this.output(), 0, 0, width, height);
	}

	public void display(PImage display) {
		image(display, 0, 0, width, height);
	}

	
	/////////////////////////////
	//    FOR USE WITH SourceChannels.  These should really have their own class
	public PImage getSourceImage(){
		return sourceImage;
	}

	public PImage output(){
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
		return chnl_feedback;
	}




	// Pass a Channel Class and return a feedback loop
	public void createFeedbackFrom(Channel chnl) {
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		
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
  		vertex(width-mouseX, height-mouseY, 1, 1);
  		vertex(chnl.vertexX, height, 0, 1);
  		textureMode(IMAGE);
  		endShape(CLOSE);

  		imageMode(CORNER);

		chnl_feedback = get();

		//  END FEEDBACK
		//////////////////////////


		/////////////////////////////
		//  SET THE OBJECT's OUTPUT SIGNAL to the RESULT of this FEEDBACK LOOP
		//  				If we're creating feedback, then we're likely not also serving SourceImages
		chnl_output = chnl_feedback;
		
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


////////////////////////////////////////////////////
//  KEYCODE FOR EVENTS

//////////////////////////////////////
//  CREATE MOMENTARY SWITCH 
// Current keyCode for the pressed key
char currentKey;
int currentKeyCode = -1;
// Remember the current key when it goes down.
public void keyPressed() { 
  currentKeyCode = keyCode; 
  currentKey = key;
//DEBUG: println("keyCode = "+keyCode+ " key = "+key);


  switch(currentKey){
    case '-':
      SHOW_MONITORS = !SHOW_MONITORS;
      break;
    case '1':
      DISPLAY_CHANNEL = 1;
      break;
    case '2':
      DISPLAY_CHANNEL = 2;
      break;
    case '3':
      DISPLAY_CHANNEL = 3;
      break;
    case '4':
      DISPLAY_CHANNEL = 4;
      break;
    case '5':
      DISPLAY_CHANNEL = 5;
      break;
    case '6':
      DISPLAY_CHANNEL = 6;
      break;
    case '7':
      DISPLAY_CHANNEL = 7;
      break;
    case '8':
      DISPLAY_CHANNEL = 8;
      break;
    case '9':
      DISPLAY_CHANNEL = 9;
      break;
    case '0':
      DISPLAY_CHANNEL = 0;
      break;
  }

}
// Clear the current key when it goes up.
public void keyReleased() {
  currentKeyCode = -1;
}
//  END MOMENTARY SWITCH
//////////////////////////////////////


public void updateControlsFromKeyboard() {
  // if no key is currently down, make sure all of the buttons are up and bail  
  if (currentKeyCode == -1) {
    //clearButtons();
    return;
  }
  //////////////////////////////////////////////////
  //  SCREEN CAPTURE  =  ENTER
  //////////////////////////////////////////////////
  if (currentKeyCode==ENTER) {
    String filename = nowAsString() + ".png";
    println("SAVED AS "+filename);
    saveFrame(SNAP_FOLDER_PATH + filename);
    //saveFrame(SNAP_FOLDER_PATH + "screen-####.png");
    noCursor();
  } else {
    cursor();
  }
  /////////////////////////////////////////////////
  //  CLEAR BACKGROUND = TAB 
  /////////////////////////////////////////////////
  if (currentKeyCode==TAB) {
    background(0);
  } 

  /////////////////////////////////////////////////
  //     SELECT NEW SOURCE IMAGES FOR CHANNELS 1-4
  /////////////////////////////////////////////////
  //select source for chnl 1
  if (currentKey == 'q') {
    chnl_1_journals.changeSourceImage("journals");
  } else if (currentKey == 'w') {
    chnl_1_journals.changeSourceImage("emblems");
  }
  //select source for chnl 2
   else if (currentKey == 'e') {
    chnl_2_emblems.changeSourceImage("journals");
  } else if (currentKey == 'r') {
    chnl_2_emblems.changeSourceImage("emblems");
  }
  //select source for chnl 4
  else if (currentKey == 't') {
    chnl_4_has_controls.changeSourceImage("journals");
  } else if (currentKey == 'y') {
    chnl_4_has_controls.changeSourceImage("emblems");
  }
  //     END SELECT NEW SOURCE IMAGE
  ////////////////////////////////////////////////
}
//  END KEYCODE FOR EVENTS
/////////////////////////////////////////////////////////////


////////////////////////////////////////////////////
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
//   END SCREEN SNAPS
////////////////////////////////////////////////////////////

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control_app" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
