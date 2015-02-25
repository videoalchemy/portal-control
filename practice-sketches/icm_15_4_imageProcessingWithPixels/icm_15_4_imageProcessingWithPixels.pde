/* j.stephens - prep for portal controller - 2015-02-23
    - ex. image processing with Pixels
    - Shiffman's lecture: 
https://vimeo.com/channels/introcompmedia/108975594
TODO:
1. [x] draw images to buffer and buffer to output screen
2. [ ] use pixel array to accomplish first task
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


	//imgNeek.loadPixels();	
	

}

void mousePressed() {
	isOn = !isOn;
}