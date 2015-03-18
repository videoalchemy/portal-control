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




	// Pass a Channel Class and return a feedback loop
	void createFeedbackFrom(Channel chnl) {
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


