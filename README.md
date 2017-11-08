## Portal Control
> A gesture controlled video-feedback installation.
> Installed April 2015 at the Chabot Space and Science Center
___________________

### Vids and Picts
- [R&D on Flickr](https://www.flickr.com/photos/jaycody9/albums/72157651532556476)

#### repo organization
##### portal-control/portal-control 
- contains the most recent version in development

##### _exhibitions
- versions completed for a specific exhibition live here.
- tags will also be used for each release, including exhibition releases, so you can roll back to an exhibition release
- however, I may want to compare exhibition / major release versions side by side rather than rolling back each time I want to look at previous versions.  Thus, the _exhibition folder
- also, the exhibition folder provides some historical context.

##### images
- for any image, inlcuding those used by the app.  going to keep this in place so as to not break the prototypes

##### docs
- kept the markdown docs here before creating a wiki
- but as development cycles shortened, the wiki became too slow to use, to cumbersome. 
- so started using the README again.  
- which means that neither the docs folder nor the wiki are up to date, although they contain lots of background research and ideas not covered in the more relevant README of the main repo.



__________________________
##### see [Portal Control wiki](https://github.com/VideoAlchemy/portal-control/wiki) for detailed dev docs, including tech and feature specs, study notes and tutorials.

#### requirements
1. all displayed images are textures
2. sources are never altered
3. pixel manipulation is always via pixels array
4. analog video equipment metaphor drives design
5. pixel manipulation takes place inside mx_OUTPUT

___________________________
### var_NAMES and analog videolab metaphor

##### {source} PImage
- pristine original source.  the mother mold
- provides a copy to a specific channel for DISPLAY and/or MONITOR
- is never itself altered or displayed
- includes PImage arrays for the following
```
PImage journals[ ]
PImage emblems[ ] 
```

##### depthImage & silhouette PImage
- derived from the depthImage pixel array
- includes depth info for all pixels within the min max 
- exludes the floor
```
/* retains depth info for each pixel within range, and is:
    - more difficult, 
    - less like a silhoutte, 
    - useful depth information is retained
*/
for each pixel of location loc in the depthImage,
if the pixel depth in within min max
    then pixel of location loc in silhouette image is greyscale 0-255
    where greyscale is mapped from (pixelDepth, 0, 1024, 0, 255)
else, the pixel at location loc in silhouette is completely transparent (0,0,0,255)
```
- normalize the range of depth where closest is 100% and furthest is 0%
```
// or, keep just the silhouette
for each pixel[loc] in depth image:
	if your within min max range
		then create to silh.pixels[loc] = color(255) // a white pixel
	else place a transparent pixel in that location

upon iteration through depthImage pixels array,
    apply erode filter to remove small white noise
    	// unfortunately the filter(); only applies to graphics screen
    	// openCV has erode abilities which likely can be applied to Pimgaes
```


##### chnl{x}_MONITOR PImage
- provides view of unaltered source
- always appears in the same place in the mx_MONITOR
- becomes the texture used for the MONITOR shape


##### chnl{x}_DISPLAY PImage
- receives a source
- image processing takes place when pixel values are copied from source to DISPLAY
- becomes the texture used for DISPLAY


##### mx_MONITOR PImage
- contains all chnl<x>_MONITOR textures
- has blue background
- can display each chnl<x>_DISPLAY separately for testing

##### mx_MIXING_BUFFER PImage
- handles all chnl to chnl pixel manipulation, blending between channels
- returned from a function responsible for mixing chnls:
```
mx_MIXED_BUFFER = returnBlendOf(chnl{x}_DISPLAY, chnl{z}_DISPLAY, blendMode b);
```



##### mx_OUTPUT PImage
- receives each chnl{x}_DISPLAY
- home of all image processing algorithms (eg blendColor(), etc)
- contains the shapes into which the chnl{x}_DISPLAYS are used for texture
- apply textures after all image processing takes place
```
// chnl{x}_DISPLAYS in mx_OUTPUT as texture for chnl{x}_DISPLAY_SHAPE
// or, in future OOP version:
src{x}.send_TO(channel chnl);
chnl{x}.send_TO(mixer mx);
chnl{x}.shape_IS(displayShape shape);
chnl{x}.blend_WITH(chnl{z}, blendMode blend);
chnl{x}.displays_IN(mx_OUTPUT);
etc
etc
etc
```

##### feedback{x} PImage
- TBD




___________________________
### Controller Components (touchOSC)

#### chnl{x}_CONTROLLER

##### chnl{x}_SEND 
- select new image
- send chnl<x>_DISPLAY to mx_MONITOR where it can be isolated for evaluation, test, etc. seen above the blue test background of mx_MONITOR
- by default, the chnl<x>_MONITOR always appears on mx_MONITOR, so no need to toggle this
- send chnl<x>_DISPLAY to mx_OUTPUT to view on main screen

##### chnl{x}_POSITION
- vertex-location-control
     - 4 2D grids, each control one corner
- image-location-control (used with imageMode(CENTER); )
     - 1 2D grid to position the object
- scale
- rotate
- rotateZ ????

##### chnl{x}_PIXELS
- alpha threshold
     - at what level of brightness does alpha go to 100%
     - this forms an irregular shape.  not sure if this will work if the image is a vertex based shape.  it should punch holes in the shape, no?  see the layers underneath? sheet hope so
- alpha level
     - adjust the overall alpha of that channel
- blendModes
     - blends each pixel as they are copied to the mx_OUTPUT
     - ideally, blend modes can be applied from current chnl to any other chnl or from chnl to mx_OUTPUT.



__________________________
### Functions to create

##### OOP
- the question isn't whether or not it's a ggod idea, it's whether or not it can be implemented fast enough to save time in the long run.  Chabot install :: t-minus 4 days.
- if we did, it would look like this:
```
class Channel 
	has a source
	has dimensions
	can be monitored
	can be displayed
	can be blended with other channels
	can be moved
outputs a shape texture by img

void display() {
	
	PImage journalPage = getJournalPage(journalPage);

	beginShape();
	texture(journalPage);
	vertex(x,y,u,v);
}

```

##### returnBlendOf(PImage, PImage, blendMode);
- this allows blends to take place prior to display as texture in mx_OUTPUT
```
mx_MIXED_BUFFER = returnBlendOf(chnl{x}_DISPLAY, chnl{z}_DISPLAY, blendMode b);
```

##### applyEffectsTo(PImage, effect, amount)
```
return applyEffectTo(chnl{x}_DISPLAYS, effectName, amount )
```


_________________________
### Existing functions to implement

##### blendColor();
- applies blend from one pixel to another as pixel by pixel are copied over from pixels[]
- this can be applied from chnl<x> to chnl<z> or from the composite of all non-silhouette chnls to the silhouette in the final steps of mx_OUTPUT.  (Was that an uncontrived use of 'chnl<z>' in a project?  Nice! Gettin' nothin' but static.)
```
//exact syntax unknow, but prolly something like:
mxOUTPUT.pixels[loc] = blendColor(journal.pixels[loc], silhouette.pixels[loc], HARD_LIGHT);
```
- includes: BLEND, DARKEST, LIGHTEST, SOFT_LIGHT, etc
- use this to blend between images, or to apply mask
- use with silhouette
- ideally, blend modes can be applied from current chnl to any other chnl or from chnl to mx_OUTPUT.
- think like shiffman
```
bufferMixesSignal.loadPixels();
accumulateForces(); --> into a single force
applyForce();
updatePixels into a buffer mixerStation
```


##### blend(img, x,y,h,d, u,v,uw,vh, BLENDMODE);
- apply blend mode from piece of img to piece of graphics window



##### filter(<filteName>, <factor>);
- modifies the graphics window directly
- INVERT may be the most useful, like the old MX-1 Videonics effect
```
// create negative/positive oscillation by inverting then apply unfiltered 
1. feedback layer get the unfiltered mx_OUTPUT
2. draw mx_OUTPUT to screen
3. apply INVERT to the screen
4. draw unfiltered texture map to the screen
```


##### tint(r,g,b,a); 
- applied directly to graphics window
- use this to create transparency
```
// solid base layer image
image(opaqueImage,0,0);

// adjust tint of upcoming image
tint(255,150);

// draw the tint-adjusted image
image(myImage,0,0);

// shut it off tint so as to not affect remainder of images
noTint();

// create transparency
image(opaqueImage, 0,0);
tint(255,150)
```



##### copy(); 
- copies portion of display window and places it back into display
- or copies portion of source image and places into display window
```
// copy from processing screen back to processing screen
copy(sx, sy, sw, sh, dx, dy, dw, dh); 

// copy from srcImage to processing screen
copy(srcImage, sx, sy, sw, sh, dx, dy, dw, dh); 
```

##### imageMode(CENTER);
- control the PImage from it's center!


##### alphaMask with MASK, from PGraphic
```
imgToMask = loadImage("imageToMask.png");
imgForMasking = loadImage("imageForMasking.png");
imgToMask.mask(imgForMasking);
```


##### texture(img);
```
beginShape();
texture(img);
vertex(shapesX, shapesY, [anchored to] texturesU, texturesV);
vertex(shapesX2, shapesY2, [anchored to] texturesU2, texturesV2);
endShape(CLOSE); // this last line needs verification
```

__________________________________
## Chabot specs:

### Chabot Tech Specs:
- 2 [Optima projectors] 1024x768 fed via independent HDMI cables
- 10 foot extension for Kinect (TBD).  time to test mine
- remote control of the mac mini:
     - either controlled via screen share from laptop on a 50 foot ethernet cable
     - or controlled from bluetooth keyboard and bluetooth trackpad (preferred but command central may be too far from mac mini, or signal may be impeded.  will require testing)


### Chabot feature specs:
- [ ] videdScale = 1.6  //ratio of source images (640x480) to the output screen (1024x768)
- [ ] display underlying image
- [ ] silouette determines the feedback zones of underlying image (w/o emergent shape).  After viewing images of the space, it occurs to me that the image projected on the floor is small compared to the space.  If Kinect and Projector are aligned, the functional interaction space shrinks to the size of the image, eliminating all other in the space.  Potentially a good idea.  

_________________________________________

## [Shared doc for the visuals team](https://docs.google.com/document/d/1Ob_se0SsQ8vo-zfbqum9-aZ8LHm4XTG3tVy2hwaRi_w/edit#)

## [photos of the site](https://plus.google.com/photos/+PeterTjeerdsma/albums/6113277440510066033)

________________________________

##### devLog-2015-03-11
- [x] convert images to 1024, 768 at 72 DPI
- [x] display image
- [x] create feedback using get
- [x] create feedback using pixel array

##### devlog-2015-0312
- [x] implement analog-rvl metaphor
- [ ] create an alpha


________________________________
### Ditched Descriptions
- Portal Control is an interactive video installation where implicate order and computational feedback emerge from the individual and collective behaviors of participants.


___________________________
[Optima projectors]:http://www.projectorreviews.com/optoma/optoma-zx210st-short-throw-ledlaser-dlp-projector-review/
