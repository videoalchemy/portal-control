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
float MONITOR_SCALE 	=  (SCREEN_WIDTH/640) * (.2);		// 20% of full screen should be 5 monitors		// 1.6 * .2 (increase to SCREEN_WIDTH, then reduce by 1/5 the screen size

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


// Create 1 channel
Channel[] chnl = new Channel[3];
Channel chnl_1_journals;
Channel chnl_2_emblems;
Channel chnl_3;

PImage feedback_of_chnl_1;
PImage feedback_of_chnl_2;

 
void setup() {
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

void draw() {
	
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
	chnl_2_emblems.monitor(chnl_2_emblems.output(), MONITOR_SCALE, .2);
	chnl_3.monitor(feedback_of_chnl_1, MONITOR_SCALE, .4);


	// checks for button press
	updateControlsFromKeyboard();
}	













///////////////////////////////////////
//  GO GET THE SOURCE IMAGE!

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


void mousePressed() {
	// test for randomly indexing into the array
	randomJournalPage();

}

void randomJournalPage(){
	print("Switching from journal page "+pageNum);
	pageNum = int(random(numOfJournalPages-1));
    println(" to "+pageNum);
}

//
void printInstructions() {
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
