##Functional Specs - Portal Control project 
> 2015-02_ANDC_ChabotScienceCenter
> ______________________________
> 
####Project Overview
Portal Control is a responsive graphics installation designed for the Mar 2015 event, Portal, at the Chabot Space and Science Center.  Participants in the installation use their location, shape, and movement to affect a video feedback portal projected around them on the floor and walls.  The installation uses a volumetric sensor to detect the shape, direction, and velocity of users moving through the installation space.  This information is then used to determine the movement and color of a system of particles which combine to form a single image. 

####Spec Overview  
- This doc contains a specs list, functional requirements, use cases, and mockups for the Portal Control project. 
- **Use cases** describe the interaction loop between the user and the installation

#####Specs List:
- **provided onsite**
    + 2 projectors (1 vertical, the other horizontal)
        * where/how do the projection surfaces meet?-------
    + cables from command central to projectors
        * what kind of cables?
    + power
    + [ ] place to sit / setup?  a table??
- **required gear**
    + [ ] laptop
    + [ ] kinect
    + [ ] iPad / iPhone controller running touchOSC
    + [ ] table and chairs??
    + [ ] extension cords for the kinect (usb and power)
    + [ ] dongle / adapters for the projector signal cables
    

#####Functional Requirements (the 'system shall' list)
- **system shall** synchronize between user's location as sensed by the kinect and the user's projected location, as determined by the location of the image on the projection surface.
    + this may prove to be the toughest technical hurdle.  How to get the sensor detection location to jive with the projected location?
        * processing library?
        * Mad Mapper?
        * ask Chikka
- create a depth map of the users in the space
- create a sileohette of the users, where the floor is keyed out
- recognize the closest point
- recognize subltle changes in depth based on closest point to the floor.  (ie, brightness from closest point to foot = 0%-100%, where the distance is mapped to the usable distance between head and toe)
- have kinect suspended overhead

 