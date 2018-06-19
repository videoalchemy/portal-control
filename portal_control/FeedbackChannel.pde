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
