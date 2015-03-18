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

	void changeSourceImage(String sourceName) {
    	if (sourceName == "journals") {
    		int journalPage = int(random(numOfJournalPages-1));
    		sourceImage 	= journal[journalPage];
    	} else if (sourceName == "emblems") {
    		int anEmblem 	= int(random(numOfEmblems-1));
    		sourceImage 	=  emblem[anEmblem];
    	}
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

		//  END FEEDBACK
		//////////////////////////


		/////////////////
		// add some simple feedback here
		// draw yourself to something that changes, then get yourself


		return chnl_feedback;
	}


	// Pass a Channel Class and return a feedback loop
	PImage getFeedbackFrom(Channel chnl) {

		
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


