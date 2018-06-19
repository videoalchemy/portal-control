/* jstephens - pgraphics practice - 2015-03

Create two pgraphics layers and draw them into the processing screen
*/


PGraphics top;
PGraphics bottom;


void setup() {
size(640, 480);
top = createGraphics(width, height);
bottom = createGraphics(width, height);

}


void draw(){

	
	top.beginDraw();
	top.fill(255,0,0,150);
	//top.translate(width/2, height/2);
	top.translate(mouseX,mouseY);
	top.ellipse(0, 0, 100, 100);
	top.endDraw();

	image(top,0, 0,width, height);
}