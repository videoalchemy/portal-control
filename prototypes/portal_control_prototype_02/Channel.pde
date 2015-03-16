class Channel {

	String name;

	PImage sourceImage;
	PImage chnl_output;

	int imageNum;  			// index of the displayed image

	float opacity;  		// for use with tint
	float theta;			// track rotation


	float maxWidthScale; 	// max as 	

	float monitorX;				// x cordinate of the channel's monitor image
	float monitorY;				// y cordinate of the channel's monitor image

	
	
	
	Channel(String _name, PImage _sourceImage)  {
		name      = _name;
		sourceImage = _sourceImage;
		
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
		//image(sourceImage, 0,0, width, height);

		chnl_output = sourceImage;

		// 1. set the output image to be the source

		// use a buffer image so that the sourceImage is unchanged


		// add image processing here
		//////////////////////


		// add texture mapping here
		////////////////////////////

		return chnl_output;
	}


}


