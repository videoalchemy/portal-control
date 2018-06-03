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

import oscP5.*;  // ipad action
import netP5.*;

OscP5 oscP5;

String project = "portal-control";
String version = "v0.6.0_dev";

////////////////
//  GLOBALS FOR DRAWING 
//

/*
boolean sketchFullScreen() {
 return true;
 }
 */
boolean changeSource = false;
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
float MONITOR_SCALE 	=  (SCREEN_WIDTH/640) * (.166);		// (.2 fits 5 screen)

// FACTOR by which scale +/- at each iteration.  not sure if this will be useful  given touchOSc controls
float SCALE_FACTOR 		= 1.5;
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



//PRELOAD 

// Create 1 channel
Channel[] chnl = new Channel[6];
Channel chnl_1_journals;
Channel chnl_2_emblems;
Channel chnl_3;
Channel chnl_4;
Channel chnl_5_kinect;
Channel chnl_6_trackShape;





void setup() {
  //////////////////////////////////////
  //  SHAPE HAS IT"S OWN DAMG MODE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  shapeMode(CENTER); 


  //start oscP5 listening for incoming messages at port 8000
  oscP5 = new OscP5(this, 8000);

  println("Initializing window at " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
  //size (SCREEN_WIDTH, SCREEN_HEIGHT, P2D);  //ditch the 'P2D' if we have Kinect issues
  size (1024, 768, P2D);  //

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
  chnl[0] = chnl_1_journals = new Channel("  chnl_1_journals", journal[8], 1);
  chnl[1] = chnl_2_emblems = new Channel("  chnl_2_emblems", emblem[5], 1);
  chnl[2] = chnl_3 = new Channel("  chnl_3", journal[5], 1);
  chnl[3] = chnl_4 = new Channel("  chnl_4", emblem[3], 	1);
  chnl[4] = chnl_5_kinect = new Channel("  chnl_5_kinect", journal[3], 1);
  chnl[5] = chnl_6_trackShape = new Channel(" chnl_6_chnl_shape", emblem[2], 1 );

  //    END CREATE CHANNELS
  ////////////////////////////////////////////////
}

void draw() {

  // Create alpha blended background
  // fill(0, 10);
  // rect(0,0,width,height);
  /////////////////////////



  /*
///////////////////////////////////////////////////////
   //  SEND IN THE SOURCES
   	chnl_4.createFeedbackFrom(chnl_1_journals);
   	blend(chnl_3.output(), 0, 0, width, height, 0, 0, width, height, DARKEST);
   	chnl_3.createFeedbackFrom(chnl_2_emblems);
   	shape(chnl_4.chnl_shape);
   ////////////////////////////////////////////////////////
   */



  ///////////////////////////////////////////////////////
  //  SEND IN THE SOURCES
  chnl_4.createFeedbackFrom(chnl_1_journals);
  //blend(chnl_3.output(), 0, 0, width, height, 0, 0, width, height, SUBTRACT);
  chnl_3.createFeedbackFrom(chnl_2_emblems);
  shape(chnl_4.chnl_shape);
  ////////////////////////////////////////////////////////




  //chnl_5_kinect.createFeedbackFrom(chnl_3);
  //chnl_3.createFeedbackFrom(chnl_1_journals);
  //chnl_1_journals.display();

  //shape(chnl_3.chnl_shape);

  //shape(chnl_4.chnl_shape);

  //chnl_5_kinect.createFeedbackFrom(chnl_4);
  //chnl_3.createFeedbackFrom(chnl_2_emblems);
  //chnl_5_kinect.createFeedbackFrom(chnl_4);

  //chnl_3.createFeedbackFrom(chnl_1_journals);
  //chnl_5_kinect.createFeedbackFrom(chnl_3);

  //




  //////////////////////////////////

  ///////////////////////////////////////////////////////
  //  SEND CHANNEL TO PERLIN NOISE ITS VERTICES
  // 
  // -->
  // -->
  // -->


  ///////////////////////////////////////////////////////
  //  CALL IN THE KINECT IMAGES
  // ---->
  // ---->


  /////////////////////////
  //   BLEND THE KINECT IMAGES TO OUTPUT
  // ---->
  // ---->







  /////////////////////////////////////
  //      UPDATE TOOLS AND MONITORS	
  update_PortalTools();

  /////////////////////////////////////

  ////////////////////////////////////
  ///     UPDATE CHANNEL SHAPE VERTICES
  updateChannelShapeVertices();

  /////////////////////////
}	



////////////////////////////////////////
// UPDATE VERTICES
void updateChannelShapeVertices() {
  for (int i = 0; i < chnl.length; i++) {
    chnl[i].updateChannelShapeVertices();
  }
}
//  END UPDATE VETICIES
////////////////////////////////////////



////////////////////////////////////////
//// UPDATE TOOLS
// check_KeyboardControls_ChannelMonitors_ChannelDisplayOverrideSwitches
void update_PortalTools() {
  //    SWITCH BETWEEN CHANNEL DISPLAYS - for testing or in case you get lost
  switchDisplayChannel();
  ///////////////////////
  //    SHOW the Channel Monitors
  showChannelMonitors();
  // checks for button press
  updateControlsFromKeyboard();
}
////  END UPDATE TOOLS
/////////////////////////////////////////



/////////////////////////////////////////////////////////////////
//      DISPLAY SELECTED CHANNEL ON MAIN SCREEN
////////     ----this is screaming for an awesome interface--------
void switchDisplayChannel() {
  switch(DISPLAY_CHANNEL) {
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
void showChannelMonitors() {
  if (SHOW_MONITORS) {

    //DEBUG::    REDUCE FLICKER of monitor view by looping through 2x
    for (int i = 0; i < 1; i++) {
      chnl_1_journals.monitor(MONITOR_SCALE, 0);
      chnl_2_emblems.monitor(MONITOR_SCALE, .166);
      chnl_3.monitor(MONITOR_SCALE, .166*2);
      chnl_4.monitor(MONITOR_SCALE, .166*3);
      chnl_5_kinect.monitor(MONITOR_SCALE, .166*4);
      chnl_6_trackShape.monitor(MONITOR_SCALE, .166*5);
    }
  }
}
//    END SHOW MONITORS
/////////////////////////////////////////////////////////



///////////////////////////////////////
//  GO GET THE SOURCE IMAGES!
PImage getJournalPage(int journalPage) {
  if (journal[journalPage] == null) {
    println("loading journal page "+journalPage+" of "+numOfJournalPages);
    journal[journalPage] = loadImage("../images/journal-pages/00"+journalPage+".png");
  }
  return journal[journalPage];
}
//
PImage getEmblem(int anEmblem) {
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
void printInstructions() {
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




void mousePressed() {
  // test for randomly indexing into the array
  //randomJournalPage();
}


void randomJournalPage() {
  print("Switching from journal page "+pageNum);
  pageNum = int(random(numOfJournalPages-1));
  println(" to "+pageNum);
}
