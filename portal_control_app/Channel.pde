class Channel {

	String name;

////////////////////////////////////////////////
///// PShape for the texture mapped chnl_output;
	PShape chnl_shape;					// Channels are textured PShapes
		PVector shape_center_location;

		PVector vertex_1;
		PVector vertex_2;
		PVector vertex_3;
		PVector vertex_4;
		float 	vertexX; 				// part of the initial test.  leaving it in so as to not break something

		float randomVertexX;			// provide some random starting point
		float randomVertexY;

		float theta_channel;			// to populate the rotate function.  NO ANIMATION FOR v1.0.0
		float scale_channel;
		float alpha_threshold;
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

	
	


	////////////////////////////////////////////////
	//      CHANNELS ARE SHAPES TEXTURED BY PIMAGES
	Channel(String _name)  {
		name      		= _name;
		sourceImage 	= createImage(width, height, ARGB);
		chnl_feedback 	= createImage(width, height, ARGB);
		chnl_output 	= createImage(width, height, ARGB);

	
	}


	/////////////////////////////////////////////
	//   CONSTRUCTOR FOR THE SHAPE VERSION
	Channel(String _name, int extraArgumentToDistinguishShapes) {
		name      		= _name;
		sourceImage 	= createImage(width, height, ARGB);
		chnl_feedback 	= loadImage("../images/journal-pages/044.png");
		chnl_output 	= createImage(width, height, ARGB);

		randomVertexX = random(0, width);
		randomVertexY = random(0, height);

		// these belong in draw
		//imageMode(CENTER);

		// Channels are textured PShapes
		chnl_shape = createShape();
   	    chnl_shape.beginShape();
   	    chnl_shape.textureMode(NORMAL);
    	chnl_shape.noStroke();
    	chnl_shape.texture(chnl_feedback);
    	
    	chnl_shape.vertex(mouseX, mouseY, 0, 0);
        chnl_shape.vertex(randomVertexX, 0, 1,0);
  		chnl_shape.vertex(width-mouseX*.5, height-mouseY*.5, 1, 1);
  		chnl_shape.vertex(randomVertexX, randomVertexY, 0, 1);
    	chnl_shape.endShape(CLOSE);
  		
  		// these belong in draw
  		//textureMode(IMAGE);
  		//imageMode(CORNER);
	}
    

	////////////////////////////
	//  Test drawing shape
  void drawChannelShape() {
  		//background(102);
  		pushMatrix();
  		translate(width/2, height/2);
  		float zoom = map(mouseX, 0, width, 0.1, 4.5);
  		scale(zoom);
  		shape(chnl_shape, -140, -140);
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
		

		/////////
		// Introduce the basePlateImage
		image(chnl.output(), 0, 0, width, height);


		/*		
		/////////////////////////////
		// INTRODUCING THE PSHAPE
		pushMatrix();
  		translate(width/2, height/2);
  		float zoom = map(mouseX, 0, width, 0.1, 4.5);
  		scale(zoom);
  		shape(chnl_shape, -140, -140);
  		popMatrix();
  		//////////////////////////////
		*/
	

		/////////////////////////
		//  GENERATE FEEDBACK



		//image(chnl_feedback, mouseX, mouseY, width-150, height-150);

		/////////////////////////////////
		//  ---------APPLYING TEXTURE MAPS---------------
		
////////REPLACE WITH PShape object
		
		///////////////
		// THIS TEXTURE MAP currently employs 'immediate drawing' which is WAY slower
		//  START TEXTURE MAP
  		imageMode(CENTER);
  		beginShape();
   		textureMode(NORMAL);
  		texture(chnl_feedback);
        vertex(mouseX*1.5, mouseY*1.5, 0, 0);
        vertex(randomVertexX, 0, 1,0);
  		vertex(width-mouseX*1.5, height-mouseY*1.5, 1, 1);
  		vertex(chnl.vertexX, height-randomVertexY, 0, 1);
  		textureMode(IMAGE);
  		endShape(CLOSE);
  		imageMode(CORNER);
  		//   END TEXTURE MAP of IMMEDIATE DRAW METHOD
  		//       - far more efficient to create Vertex Object Buffers 
  		//         by including PShape in the Class definition, then dot syntaxing into the shape
  		//          using getVertex and setVertex
  		//////////////////////////////////////////////////////

  		chnl_shape.beginShape();
  		chnl_shape.texture(chnl_feedback);
  		chnl_shape.endShape(CLOSE);
  		shape(chnl_shape);


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
    		// If we're pulling a source image, that must mean the output for this channel is a sourceImage and NOT a feedback loop
    		chnl_output = sourceImage;	
    	}
	}
	//     END SOURCE CONTENT SELECTION
	/////////////////////////////////////////////////



/////////////////////////////////////////////////////////
//////      TESTING SHAPE VERTICES UPDATE
	void updateChannelShapeVertices() {

			//   Manipulating the Vertices of a PShape in Real-Time

			//   PShape allows you to dynamically access and alter the vertices through the methods getVertex() and setVertex().

			//   To iterate over the vertices of a PShape, you can loop from 0 to the total number of vertices (getVertexCount()). 
			//   The vertices can be retrieved as PVector objects with getVertexCount().

	for (int i = 0; i < chnl_shape.getVertexCount(); i++) {
  		PVector v = chnl_shape.getVertex(i);
		}


// REWRITE SHIFFMANS EXAMPLE to control vertex #3 of 0,1,2,3 by substracting the conditional by -1 
	for (int i = 0; i < chnl_shape.getVertexCount() - 1; i++) {
  		PVector v = chnl_shape.getVertex(i);
  		v.x += random(-1,1);
  		v.y += random(-1,1);
  		chnl_shape.setVertex(i,v.x,v.y);
		}
	chnl_shape.setVertex(3, mouseX, mouseY);
	}
///////////////////////////////////////////////////////////



///////////////
} //// END OF THE LINE
///////////////



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

