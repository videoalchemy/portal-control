/* j.stephens - prep for portal controller - 2015-02-23
    - ex. image processing with Pixels
    - Shiffman's lecture: 
https://vimeo.com/channels/introcompmedia/108975594
TODO:
1. [x] draw images to buffer and buffer to output screen
2. [ ] use pixel array to accomplish first task


Def:
loadPixels()
	- load the pixel data for the display window into the pixel[].
	- this function must be called 
		before reading from OR writing to pixel[]
	- the rule is:
		anytime you want to manipulate the pixel array (pixel[])
		you must first call loadPixels(),
		and after changes are made, you must call updatePixels()
  updatePixels()
  	- updates the display window with the data in the display window
	- Use in conjunction with loadPixels(). 
	- If you're only reading pixels from the array, there's no need to call 
		updatePixels() — updating is only necessary to apply changes. 
*/	



PImage blankImage;
PImage outputScreen;
PImage imgNeek;
PImage imgGit;
PImage imgICM;
boolean isOn = true;


void setup() {
	size(640,480);
	background(0);
	imgNeek = loadImage("../../data/neek.png");
	imgGit = loadImage("../../data/gitGraph.png");
	imgICM = loadImage("../../data/icm.png");
	//outputScreen = createImage();
}



void draw() {
	if (isOn) {
		outputScreen = imgGit;
	}
	else {
		outputScreen = imgICM;
	}

	image(outputScreen, 0, 0, width, height);


	imgNeek.loadPixels();	
	

}

void mousePressed() {
	isOn = !isOn;
}