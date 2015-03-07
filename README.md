##Portal Control
> gesture controlled video feedback installation
___________________

#####see [Portal Control wiki](https://github.com/VideoAlchemy/portal-control/wiki) for detailed dev docs, including tech and feature specs, study notes and tutorials.
________________________________
###Overview
Portal Control is an interactive video installation where implicate order and computational feedback emerge from the individual and collective behaviors of participants.


________________________________
##Review Similar projects:
- http://funprogramming.org/143-Using-PGraphics-as-layers-in-Processing.html
- KinectPhysics polygon blob sketch in kidkinect project / ex-kinect-proj
- CustomShape sketch from same directory
- [AR Sandbox] - install instructions including Kinect / Projector Alignment and Calibration

_________________________________
####Project components:  
1. the gear
     - screens, projectors, kinect, computer, cables, and software
2. closest point tracking using the kinect
     - The feedback loop will track the point associated with the closest object detected by the Kinect.
3. Feedback loop 
     - using PImage and PGraphics in a way that samples the output screen, creating a feedback loop
4. reference image
     - reference images used to inform pixel color
5. flow field
     - to control particle movement and/or to use as in conjuction with the feedback loop somehow (tentative)

####Processing Libraries under consideration
1. [openCV for Processing] - a Processing Library for the OpenCV computer vision library by GregBorstein
2. [Keystone] - video projection mapping library for Processing
3. [Blobscanner] - blob detection and analysis
4. [Kinect Projector Toolkit] - Gene Kogan's library for aligning a projector and a Kinect
5. [SurfaceMapper] - enables the projection of textures on multiple surfaceds


________________________________
###Requirements for 1.0.0 - Chabot
####Setup Requirements
- Structure must be free standing
- screen size?----------------
- rear-projection throw distance?-------


________________________________
###Features by Release
#####0.1.0 - setup
- [x] git repo
- [x] readme
- [x] release tags in git

#####0.1.1 - complete practice sketches for image processing
- [x] create processsing sketches for icm shiffman practice 15.0-15.9
- [ ] 15.0 Intro to images
- [ ] 15.1 Animate an Image
- [ ] 15.2 Array of Images
- [ ] 15.3 the Pixel Array
- [ ] 15.4 Image processing with Pixels
- [ ] 15.5 Pixel Neighbors
- [ ] 15.6 Painting with pixels
- [ ] 15.7 Capture and Live Video
- [ ] 15.8 Movie Objects and displaying vids
- [ ] 15.9 Intro to Computer Vision

#####0.1.2 - complete practice sessions for flow fields

#####0.1.3 - setup specs 
- [ ] get setup specs from Peter Tjeerdsma peter@petert.net
     - [ ] what's the screen resolution for both projectors?
     - [ ] throw dimensions? and footprint 
     - [ ] are the projectors on separate signals?
     - [ ] how far is the computer location from the installation
     - [ ] where with the kinect be mounted?  close enought to computers?
     - [ ] can the Kinect go overheadh


#####0.2.0 - requirements phase
- [ ] requirements
- [ ] overview metaphor
- [ ] general research
- [ ] list features

#####0.3.0
- [ ] mockups

#####1.0.0 - minimal live system for Chabot


#####Future Release Ideas
- [] Treat silheoutte blob (from kinect/openCV) as a sprite
     - sprites are what Shiffman uses when he attaches a png as the image for a particle syste.  This combined with an additive effect, or something similar, produces partcle systems like smoke, fire, etc.
     - sprites as png can have their own shape, size AND alpha  (something I'd thought I have to rely loadGraphics to do)
     - grab the frame from kinect, process through openCV, ask for each blob, make a png out of it and draw to screen.  Essentially creating a particle system where each particle is a png (alpah intact) image of 1 frame, or one slice of time.
     - each blob has an associated image with its associated parameters
     - blob info can be translated into the affect the blob is having on the image

- the blob object
     - is a depth map greyscale siloutte of a user as detected by openCV
     - each blob has unique id
     - each blob has an arrary containing sprites (pngs with alphas inact), which follow the passing of time.  Use this for the feedback loop.  Whereas shiffman's particle system all have the same sprite assigned to each particle, the portal control particle systems will contain particles, each from a different moment in time (and thus the feedback loop, printing imgaes of previous selves.
     - each blob has an image assigned to it
     - each blob generates its own behavioral patterns.  
     - each blob affects its image different
     - each blob has a unique image assigned to it.(Colombo!)
     - the particle system inherets a its behavoior from the blob.

- fractal tree branching algorithm shiffman covers in NOC about efficient way of handling recursion, may be helpful when handling entire arrays of sprites, each array contains the passing of time as told by a series of single images sorted chronologically.  A looping series of pgraphics? 


________________________________
###Resources and Examples

#####PGraphcis examples
- [Using PGraphics as layers in Processng] - PGraphcis to layer objects, drawy behind objects without worrying about the function's order in draw
- PGraphcis in action
```
// 0. Declare the variable with Pgraphics type
PGraphics layer01;
PGraphics layer02;

// 1. declare the variable using PGraphics data type
layer01 = createGraphics(width, height);
layer02 = createGraphics(width, height);

// 3. Use drawing functions on the variable 
layer01.beginDraw();
layer01.ellipse(x,y,z,a);
layer01.endDraw();
```

#####Kinect and Projector Alignment and Calibration
- The [AR Sandbox] project from UC Davis provides install insructions and along with procedures for Kinect/projector alignment.

______________________________________
####DevNotes
#####2015-01-19: from zoran
- Opportunity to build interactive installation at Chabot Space and Science Center in March is exciting. March leaves you about a month and a half to get things ready.  
- STEPS:
     - Most important document is a **short list of the business requirements.** 
     - Next is choosing an **appropriate metaphor** that captures the essence of what it is you are doing. 
     - Then **build a minimal live system** with important components in place. Do you have a prototype you can reuse?
     - Once you have the live system, the cycle is: 
          1. write a test, 
          2. modify code to pass new test, 
          3. then run regression tests, 
          4. and finally accept modified code. 
- If you are careful to add important features which require special processing first, you will run into bugs early in the process.
- As for finding help, remember mythical man-month. You will take a performance hit as you bring someone else on board, which will be difficult to overcome on a short schedule.

#####Git Tags
- create tag  
```git tag v1.0.0```    
- list tags  
```git tag```  
- delete tag  
```git tag -d v1.0.0```  
- push tags from CLI  
```git push --tags```  
- source: [Git Tip of the Week: Tags](http://alblue.bandlem.com/2011/04/git-tip-of-week-tags.html)



_________________________


[Using PGraphics as layers in Processng]:http://funprogramming.org/143-Using-PGraphics-as-layers-in-Processing.html
[openCV for Processing]:https://github.com/atduskgreg/opencv-processing
[keystone]:http://keystonep5.sourceforge.net/
http://www.ixagon.se/surfacemapper/
[Blobscanner]:https://sites.google.com/site/blobscanner/home/
[Kinect Projector Toolkit]:http://www.genekogan.com/works/kinect-projector-toolkit.html
[SurfaceMapper]:http://www.ixagon.se/surfacemapper/
[AR Sandbox]:http://idav.ucdavis.edu/~okreylos/ResDev/SARndbox/Instructions.html

