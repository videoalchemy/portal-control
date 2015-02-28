/* j.stephens - prep for portal controller - 2015-02-23
    - ex. image processing with Pixels
    - Shiffman's lecture: 
https://vimeo.com/channels/introcompmedia/108975594
TODO:
1. [x] draw images to buffer and buffer to output screen
2. [ ] recreate the image function by copying the pixel value from
		each pixel location on an image and copying directly to the screen pixel for pixel
3. [ ] manipulate the pixel value for eacn pixel in the pixel array 
		(without changing the pixel index value)
		pixel[loc] = img1.pixel[loc]/2;

4. [ ] recreate the image function, but this time 
		separate the rgb components color values for each pixel
		'Give me the red, green and blue component of each pixel'
		and then, make a new color(r,g,b)

		float r = red(img1.pixels[loc]);
		float g = green(img1.pixels[loc]);
		float b = blue(img1.pixels[loc]);

		pixels[loc] = color(r,g,b);

5. [ ] manipulate the values of rgb components before copying the pixel
		value to the processing screen.  manipulation takes place
		at the point where the pixels are copied over, after having been faithful
		copied from the source image
6. [ ] exercises:
	- swap two colors --> pixels[loc] = color(g,r,b);
	- change one color ---> pixels[loc] = color(g,r,b*2);

7. [ ] redo the entire exercise using the blank canvas as an intermediary 
		followed by the image function to place the blankCanvas into the Processing screen.

8. [ ] more advanced exercises (beyond applying same color change to each pixel)
	1. Remember, pixels are spatially oriented! We KNOW the x,y values!  
		[ ] Use a pixel location's x and y values to manipulate the pixel value
		(ie Do something different to each pixel depending on its location)
			//after attaining the r,g,b but add spatial dependent manipulation
			if (x > 200) {
				pixels[loc] = color(g, r, b*2);
			} else {
				pixels[loc] = color(r,g,b);
			}
		[ ] Calculate the distance between the center of the image and a pixel's location,
			and use that to affect the pixel brightnesss, such that images closer to the
			center are brighter
				- how do we affect the brightness of a pixel?
				- ans: the brightness of a pixel is affected by the magnitude of its color value
			float distToCenter = dist(width/2, height/2, x, y);
			pixels[loc] = color(r+distToCenter, g+distToCenter, b+distToCenter);
		
		[ ] use mouse location to affect pixel brightness
			pixels[loc] = color(r+mouseX, g+mouseX, b+mouseY);
		
		[ ] use scalar quantities to affect pixel brightness by a factor of some amount
			pixels[loc] = color(r*1.1, g*1.2, b*2.2);
		
		[ ] use the map function to use one full range and map it to another range, and use this
				to affect pixel brightness
				float factor = map(distToCenter, 0, 200, 0, 2); //one range mapped to another
				then multiple each color component by that factor
				pixels[loc] = color(r*factor, g*factor, b*factor);

		[ ] undo the hardcoded calculation for pixel distance from center of image and replace
			with dinstance to mouse, such that
			float d = dist(mouseX, mouseY, x, y);  //calc distance between mouse and each pixel
		
		[ ] manipulate the brightness of every single pixel according to its disance from the mouse

		[ ] create novel manipulation 

		[ ] Create a THRESHOLD FILTER
			1. commnet out the color aquisition code,
			2. aquire the brightness compoent for each pixel
				float b = brightness(img1.pixels[loc])
			2. make every pixel black
				 pixels[loc] = color(0);
			3. make every pixel white
				pixels[loc] = color(255);
			4. create a conditional  
				if (b > 100) {
					pixels[loc] = color(255);
				} else {
					pixels[loc] = color(0);
					}

		[ ] update the THRESHOLD FILTER to adjust the threshold with the mouse location
				if (b > mouseX) {
				  	pixels[loc] = color(255);
				} else {
					pixels[loc] = color (0);
				}

9. [ ] use the createImage() function to generate an emptyImage, then redo all the above examples
	drawing the image to a blank image and then from the blank image to the processing window

10. [ ] apply affects to the blank image once it's applied to the processing screen
		- rotate
		- transform
		- grown


Notes:
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
pixel brightness
	- how do we affect the brightness of a pixel?
	- ans: the brightness of a pixel is affected by the magnitude of its color value
	
*/



PImage emptyScreen;
PImage outputScreen;
PImage imgNeek;
PImage imgGit;
PImage imgICM;
int imageCount = 1;
int imageModulo = 0;


void setup() {
	size(640,480);
	background(0);
	imgNeek = loadImage("../../data/neek.png");
	imgGit = loadImage("../../data/gitGraph.png");
	imgICM = loadImage("../../data/icm.png");
	//outputScreen = createImage();
}



void draw() {
	
	switch(imageModulo){
		case 0:
			outputScreen = imgGit;
			break;
		case 1:
			outputScreen = imgNeek;
			break;
		case 2:
			outputScreen = imgICM;
			break;
		default:
			outputScreen = imgGit;
			break;
		}

	image(outputScreen, 0, 0, width, height);
}



void mousePressed() {

	imageCount = imageCount + 1;
	imageModulo = imageCount % 3;
}



