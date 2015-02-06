##Portal Control
> gesture controlled video feedback installation
___________________

________________________________
###Overview
Portal Control is an interactive installation containing video feedback loops controlled by movement and gesture.

####Main components:  
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

#####0.1.1 - setup specs 
- [ ] determine setup specs
     - [ ] screen size
     - [ ] footprint 
     - [ ] throw distance
- [ ] get setup specs to Peter Tjeerdsma peter@petert.net

#####0.2.0 - requirements phase
- [ ] requirements
- [ ] overview metaphor
- [ ] general research
- [ ] list features

#####0.3.0
- [ ] mockups

#####1.0.0 - minimal live system for Chabot


#####Future Releases


________________________________
###Resources
####VideoExamples

####Terms


####Notes
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
- source: [Git Tip of the Week: Tags](http://alblue.bandlem.com/2011/04/git-tip-of-week-tags.html)



_________________________


