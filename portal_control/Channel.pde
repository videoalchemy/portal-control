class Channel {

	String name;

	PImage sourceImage;

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
	
	void monitor() {
		//image(sourceImage, monitorX);
	}

	void display() {
		image(sourceImage, 0, 0, width/2, height/2);
	}

}


