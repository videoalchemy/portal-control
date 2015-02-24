/* j.stephens - prep for portal controller - 2015-02-23
    - ex. image processing with Pixels
    - Shiffman's lecture: 
https://vimeo.com/channels/introcompmedia/108975594
*/



PImage blankImage;
PImage outputScreen;
PImage imgNeek;
PImage imgGit;
PImage imgICM;
boolean isOn;


void setup() {
	size(640,480);
	background(0);
	isOn = true;
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
	
	

}

void mousePressed() {
	isOn = !isOn;
}