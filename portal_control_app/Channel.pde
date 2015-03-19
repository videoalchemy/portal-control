class Channel {

	String name;


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
	
	

	







	////////////////////////////////////////////
	//    SMALL MONITOR SCREENS - so we can see what we're doing
	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	void monitor(PImage monitor, float monitorScale, float monitorPosition) {
		image(monitor, width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}

	// use this to view a channel's unaltered source Image input on a small screen for the mixer Monitor view	
	void monitor(float monitorScale, float monitorPosition) {
		image(this.output(), width*monitorPosition, height-height*monitorScale, width*monitorScale, height*monitorScale);
		//image(monitor, 0, 0, width/monitorScale, height/monitorScale);
	}
	//      END MONITORS
	/////////////////////////////////////////////

	
	/////////////////////////////////////////
	//   CHANNELS CAN DISPLAY THEMSEVES 
	void display() {
		image(this.output(), 0, 0, width, height);
	}

	void display(PImage display) {
		image(display, 0, 0, width, height);
	}

	
	/////////////////////////////
	//    FOR USE WITH SourceChannels.  These should really have their own class
	PImage getSourceImage(){
		return sourceImage;
	}

	PImage output(){
		return chnl_output;
	}



	// Pass a Channel Class and return a feedback loop
	void createFeedbackFrom(Channel chnl) {
		///////////////////////////////////  AWESOME: My first use of self calling class 'this'///////
		//chnl_output = this.output();
		
		image(chnl.output(), 0, 0, width, height);

		/////////////////////////
		//  GENERATE FEEDBACK
		imageMode(CENTER);

		//image(chnl_feedback, mouseX, mouseY, width-150, height-150);

		/////////////////////////////////
		//  ---------APPLYING TEXTURE MAPS---------------
		
		///////////////
		// THIS TEXTURE MAP currently employs 'immediate drawing' which is WAY slower
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
  		//   END TEXTURE MAP of IMMEDIATE DRAW METHOD
  		//       - far more efficient to create Vertex Object Buffers 
  		//         by including PShape in the Class definition, then dot syntaxing into the shape
  		//          using getVertex and setVertex
  		//////////////////////////////////////////////////////

  		
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




	///////////////////////////////////////
	//    SOURCE CONTENT PROVIDERS
	void changeSourceImage(String sourceName) {
    	if (sourceName == "journals") {
    		int journalPage = int(random(numOfJournalPages-1));
    		sourceImage 	= journal[journalPage];
    		// Make the the new source image the OUTPUT for this source provider
    		chnl_output = sourceImage;
    	} else if (sourceName == "emblems") {
    		int anEmblem 	= int(random(numOfEmblems-1));
    		sourceImage 	=  emblem[anEmblem];
    		// Set this channel's output to the source image if we find ourselves asking for a source image from this object.
    		// If we're pulling a source image, that must mean the output for this channel is sourceImage
    		chnl_output = sourceImage;	
    	}
	}
	//     END SOURCE CONTENT SELECTION
	/////////////////////////////////////////////////

}




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

