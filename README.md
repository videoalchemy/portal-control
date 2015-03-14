##Portal Control
> gesture controlled video feedback installation
___________________

#####see [Portal Control wiki](https://github.com/VideoAlchemy/portal-control/wiki) for detailed dev docs, including tech and feature specs, study notes and tutorials.

####requirements
1. all displayed images are textures
2. sources are never altered
3. pixel manipulation is always via pixels array
4. analog video equipment metaphor drives design

___________________________
###var_NAMES and analog videolab metaphor

####PImages

#####<source>
- pristine original source.  the mother mold
- provides a copy to a specific channel for DISPLAY and/or MONITOR
- is never itself altered or displayed

#####chnl<x>_MONITOR
- provides view of unaltered source
- always appears in the same place in the mx_MONITOR
- becomes the texture used for the MONITOR shape


#####chnl<x>_DISPLAY
- receives a source
- image processing takes place when pixel values are copied from source to DISPLAY
- becomes the texture used for DISPLAY

#####mx_MONITOR
- contains all chnl<x>_MONITOR textures
- has blue background
- can display each chnl<x>_DISPLAY separately



___________________________
###Controller Components (touchOSC)

####chnl<x>Controls

#####chnl<x>_SEND 
- select new image
- send chnl<x>_DISPLAY to mx_MONITOR where it can be isolated for evaluation, test, etc. seen above the blue test background of mx_MONITOR
- by default, the chnl<x>_MONITOR always appears on mx_MONITOR, so no need to toggle this
- send chnl<x>_DISPLAY to mx_OUTPUT to view on main screen

#####chnl<x>_POSITION
- vertex-location-control
     - 4 2D grids, each control one corner
- image-location-control (used with imageMode(CENTER); )
     - 1 2D grid to position the object
- scale
- rotate
- rotateZ ????

#####chnl<x>_PIXELS
- alpha threshold
     - at what level of brightness does alpha go to 100%
     - this forms an irregular shape.  not sure if this will work if the image is a vertex based shape.  it should punch holes in the shape, no?  see the layers underneath? sheet hope so
- alpha level
     - adjust the overall alpha of that channel
- blendModes
     - blends each pixel as they are copied to the mx_OUTPUT
     - ideally, blend modes can be applied from current chnl to any other chnl or from chnl to mx_OUTPUT.



___________________________
####functions to implement

#####blendColor();
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


#####blend(img, x,y,h,d, u,v,uw,vh, BLENDMODE);
- apply blend mode from piece of img to piece of graphics window


#####filter(<filteName>, <factor>);
- modifies the graphics window directly
- INVERT may be the most useful, like the old MX-1 Videonics effect
```
// create negative/positive oscillation by inverting then apply unfiltered 
1. feedback layer get the unfiltered mx_OUTPUT
2. draw mx_OUTPUT to screen
3. apply INVERT to the screen
4. draw unfiltered texture map to the screen
```

#####tint(r,g,b,a); 
- applied directly to graphics window
- use this to create transparency
```
// shade output RED
tint(255,0,0);
image(myImage,0,0);

// shut it off
noTint();

// create transparency
image(opaqueImage, 0,0);
tint(255,255,255,)






#####copy(); 
- copies portion of display window and places it back into display
- or copies portion of source image and places into display window
```
// copy from processing screen back to processing screen
copy(sx, sy, sw, sh, dx, dy, dw, dh); 

// copy from srcImage to processing screen
copy(srcImage, sx, sy, sw, sh, dx, dy, dw, dh); 
```

#####imageMode(CENTER);
- control the PImage from it's center!

#####alphaMask with MASK, from PGraphic
```
imgToMask = loadImage("imageToMask.png");
imgForMasking = loadImage("imageForMasking.png");
imgToMask.mask(imgForMasking);
```
#####texture(img);
```
beginShape();
texture(img);
vertex(shapesX, shapesY, [anchored to] texturesU, texturesV);
vertex(shapesX2, shapesY2, [anchored to] texturesU2, texturesV2);
endShape(CLOSE); // this last line needs verification
```

__________________________________
##Chabot specs:

###Chabot Tech Specs:
- 2 [Optima projectors] 1024x768 fed via independent HDMI cables
- 10 foot extension for Kinect (TBD).  time to test mine
- remote control of the mac mini:
     - either controlled via screen share from laptop on a 50 foot ethernet cable
     - or controlled from bluetooth keyboard and bluetooth trackpad (preferred but command central may be too far from mac mini, or signal may be impeded.  will require testing)


###Chabot feature specs:
- [ ] videdScale = 1.6  //ratio of source images (640x480) to the output screen (1024x768)
- [ ] display underlying image
- [ ] silouette determines the feedback zones of underlying image (w/o emergent shape).  After viewing images of the space, it occurs to me that the image projected on the floor is small compared to the space.  If Kinect and Projector are aligned, the functional interaction space shrinks to the size of the image, eliminating all other in the space.  Potentially a good idea.  

_________________________________________

##[Shared doc for the visuals team](https://docs.google.com/document/d/1Ob_se0SsQ8vo-zfbqum9-aZ8LHm4XTG3tVy2hwaRi_w/edit#)

##[photos of the site](https://plus.google.com/photos/+PeterTjeerdsma/albums/6113277440510066033)

________________________________

#####devLog-2015-03-11
- [x] convert images to 1024, 768 at 72 DPI
- [x] display image
- [x] create feedback using get
- [x] create feedback using pixel array

#####devlog-2015-0312
- [x] implement analog-rvl metaphor
- [ ] create an alpha


________________________________
###Ditched Descriptions
- Portal Control is an interactive video installation where implicate order and computational feedback emerge from the individual and collective behaviors of participants.


___________________________
[Optima projectors]:http://www.projectorreviews.com/optoma/optoma-zx210st-short-throw-ledlaser-dlp-projector-review/
