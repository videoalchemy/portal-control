import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 

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

  // ipad action


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
float MONITOR_SCALE 	=  (SCREEN_WIDTH/640) * (.166f);		// (.2 fits 5 screen)

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



//PRELOAD 

// Create 1 channel
Channel[] chnl = new Channel[6];
Channel chnl_1_journals;
Channel chnl_2_emblems;
Channel chnl_3;
Channel chnl_4;
Channel chnl_5_kinect;
Channel chnl_6_trackShape;




 
public void setup() {
	//start oscP5 listening for incoming messages at port 8000
    oscP5 = new OscP5(this, 8000);

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
    chnl[0] = chnl_1_journals = new Channel("  chnl_1_journals", journal[8], 1);
	chnl[1] = chnl_2_emblems = new Channel("  chnl_2_emblems", emblem[5], 1);
	chnl[2] = chnl_3 = new Channel("  chnl_3",  journal[5], 1);
  	chnl[3] = chnl_4 = new Channel("  chnl_4", emblem[3],	1);
  	chnl[4] = chnl_5_kinect = new Channel("  chnl_5_kinect", journal[3], 1);
	chnl[5] = chnl_6_trackShape = new Channel(" chnl_6_chnl_shape", emblem[2], 1 );

  	//    END CREATE CHANNELS
  	////////////////////////////////////////////////
}

public void draw() {
	
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
public void updateChannelShapeVertices(){
    for (int i = 0; i < chnl.length; i++) {
    	chnl[i].updateChannelShapeVertices();
    }
}
//  END UPDATE VETICIES
////////////////////////////////////////



////////////////////////////////////////
//// UPDATE TOOLS
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
////  END UPDATE TOOLS
/////////////////////////////////////////



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
			chnl_2_emblems.monitor(MONITOR_SCALE, .166f);
			chnl_3.monitor(MONITOR_SCALE, .166f*2);
			chnl_4.monitor(MONITOR_SCALE, .166f*3);
			chnl_5_kinect.monitor(MONITOR_SCALE, .166f*4);
			chnl_6_trackShape.monitor(MONITOR_SCALE, .166f*5);
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

	boolean changeSource;
	String name;


float growFactorAmount; // for increasing the size of the feedback layer

//float theta;  //rotate

////////////////////////////////////////////////
///// PShape for the texture mapped chnl_output;
	PShape chnl_plate;
	
	PShape chnl_shape;					// Channels are textured PShapes
		PVector shape_center_location;

		PVector vertex_1_location;
		PVector vertex_2;
		PVector vertex_3;
		PVector vertex_4;
		PVector vertexOffSet;
		PVector move;

		float 	vertexX; 				// part of the initial test.  leaving it in so as to not break something

		float randomVertexX;			// provide some random starting point
		float randomVertexY;

		float theta_channel;			// to populate the rotate function.  NO ANIMATION FOR v1.0.0
		float scale_channel;
		float alpha_threshold;

		float shapeX, shapeY; 			// shape location
///////
////////////////////////////////////////////////


////////////////////////////////////////////////
//////      Channel OUTPUT IMAGES
	PImage sourceImage;
	PImage chnl_output;
	PImage chnl_feedback;

	PImage chnl_basePlate; 	//   To somehow isolate the channel and 
							// elimate the indiscriminate feedback loops generated
							// by .get()-ing everything that falls to the Processing Screen
							// 
							//     Currently, the only method we have for generating feedback is
							//	scooping all the outputs off the floor as one big image.
							//	where all feedback immediately blends.
							//  
							//     A PShape layer (or chnl_basePlate) would give the drippings a place
							// 	place to drip --> onto the base plate, while allowing a background redraw
							//  that would ignore the feedback loops isolated above nestled inside 
							//  their channel's basePlate

	

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	
	////////////////////////////
	//  NOISE
	float xoff = 0.0f;
	float xincrement = 0.01f; 
	float growFactor = 0;
	
	////////////////////////////
	

	/////////////////////////////////////////////
	//   CONSTRUCTOR FOR THE SHAPE VERSION
	Channel(String _name, PImage _preloadImage, int extraArgumentToDistinguishShapes) {
		name      		= _name;
		changeSource = false;
		// PRELOAD THE IMAGE
		chnl_feedback		= _preloadImage;
		//chnl_feedback 	= loadImage("../images/journal-pages/044.png");
		sourceImage 	= loadImage("../images/journal-pages/041.png");
		chnl_output 	= createImage(width, height, ARGB);

		randomVertexX = random(0, width);
		randomVertexY = random(0, height);

		// these belong in draw
		//imageMode(CENTER);

		PVector vertex_1_location = new PVector(random(100), random(100));
	//	PVector vertex_2;
	//	PVector vertex_3;
	//	PVector vertex_4;
		PVector vertexOffSet = new PVector(random(100), random(100),0);   //random value to use as noise scale	
		PVector move = new PVector();	

		/*
		noff = new PVector(random(1000), random(1000));
    	velocity = new PVector();
		*/


		float growFactorAmount = random(2.5f); // for increasing the size of the feedback layer

		float theta = 0;  //rotate

		// Shapes cordinates
		shapeX = 0;
		shapeY = 0;


		// Channels are textured PShapes
		chnl_shape = createShape();
   	    chnl_shape.beginShape();
   	    chnl_shape.textureMode(NORMAL);
    	chnl_shape.noStroke();
    	chnl_shape.texture(chnl_feedback);
    	
    	chnl_shape.vertex(random(0,100),random(0,100), 0, 0);
        chnl_shape.vertex(width-100, 100, 1,0);
  		chnl_shape.vertex(width-100, height-100, 1, 1);
  		chnl_shape.vertex(100, height-100, 0, 1);
    	chnl_shape.endShape(CLOSE);
  		

  		chnl_plate = createShape();
   	    chnl_plate.beginShape();
   	    chnl_plate.textureMode(NORMAL);
    	chnl_plate.noStroke();
    	chnl_plate.texture(chnl_output);
    	
    	chnl_plate.vertex(0,0, 0, 0);
        chnl_plate.vertex(width,0, 1,0);
  		chnl_plate.vertex(width, height, 1, 1);
  		chnl_plate.vertex(0, height, 0, 1);
    	chnl_plate.endShape(CLOSE);
  		// these belong in draw
  		//textureMode(IMAGE);
  		//imageMode(CORNER);
	}
  
// this meant only for the source channels   
public void updateChannelShapeLocation(float xPos, float yPos) {
	shapeX = map(xPos, 0,1, -width/2, width/2);
	shapeY = map(yPos, 0, 1, -height/2, height/2);

	print ("map of shape x = "+shapeX);
	
	
}



	////////////////////////////
	//  Test drawing shape
  public void drawChannelShape() {
  		//background(102);
  		pushMatrix();
  		translate(0,0);
  		//translate(mouseX, mouseY);
  		float zoom = map(mouseX, 0, width, 0.1f, 4.5f);
  		scale(zoom);
  		shape(chnl_shape);
  		//shape(chnl_shape, -140, 140);
  		popMatrix();
	}


/*
    // Initialize center vector
    center = new PVector(); 
    
    // Set the Channel's starting position
    rebirth(width/2, height/2);
  }

  PShape getShape() {
    return part;
  }
*/



	
	/////////////////////////////
	//    FOR USE WITH SourceChannels.  These should really have their own class
	public PImage getSourceImage(){
		return sourceImage;
	}

	public PImage output(){
		return chnl_output;
	}



	// Pass a Channel Class and return a feedback loop
	public void createFeedbackFrom(Channel importedChannel) {
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		

		/////////
		// Introduce the basePlateImage
		//image(importedChannel.output(), 0, 0, width, height);
		//blend(importedChannel.output(), 0, 0, width, height, 0, 0, width, height, DARKEST);

				
		/////////////////////////////
		// INTRODUCING THE PSHAPE
		/*
		pushMatrix();
  		translate(0, 0);
  		float zoom = map(mouseX, 0, width, 0.1, 4.5);
  		scale(zoom);
  		//shape(this.chnl_shape, -140, -140);
  		shape(this.chnl_shape, 0, 0);
  		popMatrix();
  		//////////////////////////////
		*/

		//shape(importedChannel.chnl_shape);
		//shape(this.chnl_shape);
	

		/////////////////////////
		//  GENERATE FEEDBACK



		//image(chnl_feedback, mouseX, mouseY, width-150, height-150);

		/////////////////////////////////
		//  ---------APPLYING TEXTURE MAPS---------------
				
		///////////////
		// THIS TEXTURE MAP currently employs 'immediate drawing' which is WAY slower
		// START TEXTURE MAP


  		/*
  		chnl_plate.beginShape();
  		chnl_plate.texture(importedChannel.output());
		chnl_plate.endShape(CLOSE);
		shape(chnl_plate, mouseX, mouseY);
	*/  		
  		pushMatrix();
  		
  		translate(0,0);
  		translate(-width/2, -height/2);
  		rotate(theta);

  		scale(growFactor);
  		shape(importedChannel.chnl_shape);
  		//translate(mouseX, mouseY);
  		chnl_plate.beginShape();
  		chnl_plate.texture(this.output());
		chnl_plate.endShape(CLOSE);
		shape(chnl_plate, mouseX, mouseY);
		popMatrix();

		/*
		//  DISPLAY THIS CHANNEL'S SHAPE OF FEEDBACK
  		importedChannel.chnl_shape.beginShape();
  		importedChannel.chnl_shape.texture(importedChannel.output());
  		importedChannel.chnl_shape.endShape(CLOSE);
  		shape(importedChannel.chnl_shape);
		*/


		/*
		vertex(0,0,0,0);
		vertex(width,0,1,0);
		vertex(width,height,1,1);
		vertex(0, height, 0,1);
		endShape(CLOSE);
  		imageMode(CORNER);
		//
		
		/*
        vertex(mouseX*1.5, mouseY*1.5, 0, 0);
        vertex(randomVertexX, 0, 1,0);
  		vertex(width-mouseX*1.5, height-mouseY*1.5, 1, 1);
  		vertex(chnl.vertexX, height-randomVertexY, 0, 1);
  		textureMode(IMAGE);
  		endShape(CLOSE);
  		imageMode(CORNER);
  		*/
  		//   END TEXTURE MAP of IMMEDIATE DRAW METHOD
  		//       - far more efficient to create Vertex Object Buffers 
  		//         by including PShape in the Class definition, then dot syntaxing into the shape
  		//          using getVertex and setVertex
  		//////////////////////////////////////////////////////
		
		//chnl_feedback = get(); 

  		//////////////////////////////
  		//  DISPLAY THIS CHANNEL'S SHAPE OF FEEDBACK
  		chnl_shape.beginShape();
  		chnl_shape.texture(chnl_feedback);
  		chnl_shape.endShape(CLOSE);
  		shape(chnl_shape, shapeX, shapeY);


  		/*
		pushMatrix();
  		translate(width/2, height/2);
  		float zoom = map(mouseX, 0, width, 0.1, 4.5);
  		scale(zoom);
  		chnl_shape.beginShape();
  		chnl_shape.texture(chnl_feedback);
  		chnl_shape.endShape(CLOSE);
  		shape(chnl_shape, -140, -140);
  		popMatrix();
  		//shape(chnl_shape, -140, -140);
		*/
  		
  		////////////////////////
  		//
		chnl_feedback = get();     	// <-- Grabs EVERYTHING FROM THE PROCESSING WINDOW
		//							// <-- soo very crude.  Need to pass in the Channel's BaseCasePlate
		////////////////////////	  						Then do the above drawing in the basePlate
									//						 - to protect the layer


		/////////////////////////////
		//  SET THE OBJECT's OUTPUT SIGNAL to the RESULT of this FEEDBACK LOOP
		//  				If we're creating feedback, then we're likely not also serving SourceImages
		chnl_output = chnl_feedback;		
	}

/////////////////////////////////////////////////////////
//////      UPDATE VERTICE CONTROL 
	public void updateChannelShapeVertices() {
			//   Manipulating the Vertices of a PShape in Real-Time
			//   PShape allows you to dynamically access and alter the vertices through the methods getVertex() and setVertex().
			//   To iterate over the vertices of a PShape, you can loop from 0 to the total number of vertices (getVertexCount()). 
			//   The vertices can be retrieved as PVector objects with getVertexCount().
/*
	for (int i = 0; i < chnl_shape.getVertexCount(); i++) {
  		PVector v = chnl_shape.getVertex(i);
		}
*/


// REWRITE SHIFFMANS EXAMPLE to control vertex #3 of 0,1,2,3 by substracting the conditional by -1 
	for (int i = 0; i < chnl_shape.getVertexCount(); i++) {
  		PVector v = chnl_shape.getVertex(i);
  		v.x += random(-1,1);
  		v.y += random(-1,1);
  		chnl_shape.setVertex(i,v.x,v.y);
		}
	//chnl_shape.setVertex(3, mouseX, mouseY);
}

/*
////////////////////////////////
/////////ADDED NOISE
// REWRITE SHIFFMANS EXAMPLE to control vertex #3 of 0,1,2,3 by substracting the conditional by -1 
	for (int i = 0; i < chnl_shape.getVertexCount(); i++) {
  		PVector v = chnl_shape.getVertex(i);
  		
		move.x = map(noise(vertexOffSet.x), 0, 1, -3, 3);
  		move.y = map(noise(vertexOffSet.y), 0, 1, -3, 3);  		
  		v.add(move);
  		chnl_shape.setVertex(i,v.x,v.y);
		}
	vertexOffSet.add(0.01, 0.01,0);
	//chnl_shape.setVertex(3, mouseX, mouseY);
	}
	//////////////////

	 // Get a noise value based on xoff and scale it according to the window's width
  float n = noise(xoff)*width;
  
  // With each cycle, increment xoff
  xoff += xincrement;
  */


/////////////////////////////////////
	/*
	velocity.x = map(noise(noff.x), 0, 1, -1, 1);
    velocity.y = map(noise(noff.y), 0, 1, -1, 1);
    velocity.mult(5);

    noff.add(0.01, 0.01);
    */
///////////////////////////////////////////////////////////


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
    		// If we're pulling a source image, that must mean the output for this channel is a sourceImage and NOT a feedback loop
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

	// MONITORS THE CHANNELS OWN OUTPUT
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


///////////////
} //// END OF THE LINE
///////////////

/*
	///////////////////////////////////////////////////////////////////////////
	/////////////      START TRANSOMATIONS
	/////////////////////////
	//  ROTATE and SCALE APPLIED TO THE PIMAGE LAYER, not the PSHape.
	//      - this is the simpler earlier method that would work prior to attaching 
	//         the texture maps to a PShape.
	//         Now that I think about it, rotating the PImage will likely rotate the 
	//           texture without rotating the texture shape itself.  MIND = BLOWN
	//		- A channel's output is used as the texture for the textured shape of that channel
	//           thus, the ENTIRE feedback of the PImage remains inside the shape, instead of
	//			 including the transparent area of PImage around the edges of the shape.  
	//      - Moving a vertex deforms the shape and the texture.
	//      - Moving the PImage that makes up the texture moves the texture without moving the shape 
	//				- updated the method name to reflect this added and unaccounted for level of complexity
	void applyRotateAndScaleToChannelOutputTextureImage(){
}

	/////////////////////////
	//  ROTATE and SCALE APPLIED TO THE PSHAPE and EVERYTHING IN IT  (requires PShape channel attribute)
	void applyRotateAndScaleToYourPShape(){


	}
	/////////////      END TRANSOMATIONS
	///////////////////////////////////////////////////////////////////////////
*/

///////////////////////////
// OUT OF COMMISION 
/*
	PImage getFeedbackFrom(PImage channelIn) {
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

*/
///////////////////////////////

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

  if (key == CODED) {

    if (keyCode == LEFT) {
      
       for (int i = 0; i < chnl.length; i++) {
           chnl[i].theta += .01f;
        }
    } else if (keyCode == RIGHT) {
         for (int i = 0; i < chnl.length; i++) {
         chnl[i].theta -= .01f;
       }
    } else if (keyCode == UP) {
         for (int i = 0; i < chnl.length; i++) {
          chnl[i].growFactor += .03f;
          println(chnl[i].growFactor);
      }

    } else if (keyCode == DOWN) {
       for (int i = 0; i < chnl.length; i++) {
        chnl[i].growFactor -= .03f;
        println(chnl[i].growFactor);
      }
    }



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
  //select source for chnl 3
  else if (currentKey == 't') {
    chnl_3.changeSourceImage("journals");
  } else if (currentKey == 'y') {
    chnl_3.changeSourceImage("emblems");
  }
  //select source for chnl 4
  else if (currentKey == 'u') {
    chnl_4.changeSourceImage("journals");
  } else if (currentKey == 'i') {
    chnl_4.changeSourceImage("emblems");
  }
  //select source for chnl 5
  else if (currentKey == 'o') {
    chnl_5_kinect.changeSourceImage("journals");
  } else if (currentKey == 'p') {
    chnl_5_kinect.changeSourceImage("emblems");
  }
  //select source for chnl 6
  else if (currentKey == '[') {
    chnl_6_trackShape.changeSourceImage("journals");
  } else if (currentKey == ']') {
    chnl_6_trackShape.changeSourceImage("emblems");
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

// Elie Zananiri
// Depth thresholding example
// http://www.silentlycrashing.net
/*
import org.openkinect.*;
import org.openkinect.processing.*;

Kinect kinect;
int kWidth  = 640;
int kHeight = 480;
int kAngle  =  15;

PImage depthImg;
int minDepth =  60;
int maxDepth = 860;

void setup() {
  size(kWidth, kHeight);

  kinect = new Kinect(this);
  kinect.start();
  kinect.enableDepth(true);
  kinect.tilt(kAngle);

  depthImg = new PImage(kWidth, kHeight);
}

void draw() {
  // draw the raw image
  
  kinect.getDepthImage();
  //image(kinect.getDepthImage(), 0, 0);

  // threshold the depth image
  int[] rawDepth = kinect.getRawDepth();
  for (int i=0; i < kWidth*kHeight; i++) {
    if (rawDepth[i] >= minDepth && rawDepth[i] <= maxDepth) {
      depthImg.pixels[i] = 0xFFFFFFFF;
    } else {
      depthImg.pixels[i] = 0;
    }
  }

  // draw the thresholded image
  depthImg.updatePixels();
  image(depthImg, 0, 0);

  fill(0);
  text("TILT: " + kAngle, 10, 20);
  text("THRESHOLD: [" + minDepth + ", " + maxDepth + "]", 10, 36);
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      kAngle++;
    } else if (keyCode == DOWN) {
      kAngle--;
    }
    kAngle = constrain(kAngle, 0, 30);
    kinect.tilt(kAngle);
  }
  
  else if (key == 'a') {
    minDepth = constrain(minDepth+10, 0, maxDepth);
  } else if (key == 's') {
    minDepth = constrain(minDepth-10, 0, maxDepth);
  }
  
  else if (key == 'z') {
    maxDepth = constrain(maxDepth+10, minDepth, 2047);
  } else if (key =='x') {
    maxDepth = constrain(maxDepth-10, minDepth, 2047);
  }
}

void stop() {
  kinect.quit();
  super.stop();
}
*/

// create function to recv and parse oscP5 messages
public void oscEvent (OscMessage theOscMessage) {

/*
  String addr = theOscMessage.addrPattern();  //never did fully understand string syntaxxx
  float val = theOscMessage.get(0).floatValue(); // this is returning the get float from bellow

  println(addr);
*/

/*
  if (addr.equals("/1/lookLR")) {  //remove the if statement and put it in draw
    rcvLookLR = val; //assign received value.  then call function in draw to pass parameter
  }
  else if (addr.equals("/1/lookUD")) {
    rcvLookUD = val;// assigned receive val. prepare to pass parameter in called function: end of draw
  }
*/




 //////////////print the address pattern and the typetag of the received OscMessage
  print("### received an osc message.");
  print(" addrpattern: "+theOscMessage.addrPattern());
  println(" typetag: "+theOscMessage.typetag());

//println("### received an osc message. with address pattern "+theOscMessage.addrPattern());  

 
 //for (int i = 1; i < chnl.length; i ++) {}
 if(theOscMessage.checkAddrPattern("/3/position")==true) {
    float xPos = theOscMessage.get(1).floatValue();  
    float yPos = theOscMessage.get(0).floatValue();  
    print ("xPos ="+xPos+" yPos = "+yPos);
    // update chnl_3
    //chnl_1_journals.updateChannelShapeLocation(xPos, yPos);
    chnl_3.updateChannelShapeLocation(xPos, yPos);
    chnl_4.updateChannelShapeLocation(xPos, yPos);
        
}
    
else	if(theOscMessage.checkAddrPattern("/3/multipush1/1/1")==true) {
    print ("Yes");
    //float x = theOscMessage.get(0).floatValue();  
    changeSource = !changeSource;

    if (changeSource) {
       chnl_1_journals.changeSourceImage("journals");
        chnl_2_emblems.changeSourceImage("emblems");
        chnl_3.changeSourceImage("journals");
}

else  if(theOscMessage.checkAddrPattern("/3/fader38")==true) {
    float rot = theOscMessage.get(0).floatValue();
    float rotMap = map(rot, 0,1, -1,1);
    rotate(rotMap);

}

}








/*
   if(theOscMessage.checkTypetag("ifs")) {
      //parse theOscMessage and extract the values from the osc message arguments.
      int firstValue = theOscMessage.get(0).intValue();  
      float secondValue = theOscMessage.get(1).floatValue();
      String thirdValue = theOscMessage.get(2).stringValue();
      print("### received an osc message /test with typetag ifs.");
      println(" values: "+firstValue+", "+secondValue+", "+thirdValue);
      return;
    }  
*/    






///////////////
}      ///////
//////////////
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "portal_control_app" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
