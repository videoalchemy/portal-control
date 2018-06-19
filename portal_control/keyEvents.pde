//////////////////////////////
// KeyEVENTS - flipping switches!


////////////////////////////////////////////////////
//  KEYCODE FOR EVENTS

//////////////////////////////////////
//  CREATE MOMENTARY SWITCH 
// Current keyCode for the pressed key
char currentKey;
int currentKeyCode = -1;
// Remember the current key when it goes down.
void keyPressed() { 
  currentKeyCode = keyCode; 
  currentKey = key;
//DEBUG: println("keyCode = "+keyCode+ " key = "+key);


  switch(currentKey){
    case '-':
      SHOW_MONITORS = !SHOW_MONITORS;
      break;
    case '1':
      DISPLAY_CHANNEL = 1;
      break;
    case '2':
      DISPLAY_CHANNEL = 2;
      break;
    case '3':
      DISPLAY_CHANNEL = 3;
      break;
    case '4':
      DISPLAY_CHANNEL = 4;
      break;
    case '5':
      DISPLAY_CHANNEL = 5;
      break;
    case '6':
      DISPLAY_CHANNEL = 6;
      break;
    case '7':
      DISPLAY_CHANNEL = 7;
      break;
    case '8':
      DISPLAY_CHANNEL = 8;
      break;
    case '9':
      DISPLAY_CHANNEL = 9;
      break;
    case '0':
      DISPLAY_CHANNEL = 0;
      break;
  }

}
// Clear the current key when it goes up.
void keyReleased() {
  currentKeyCode = -1;
}
//  END MOMENTARY SWITCH
//////////////////////////////////////


void updateControlsFromKeyboard() {
  // if no key is currently down, make sure all of the buttons are up and bail  
  if (currentKeyCode == -1) {
    //clearButtons();
    return;
  }
  //////////////////////////////////////////////////
  //  SCREEN CAPTURE  =  ENTER
  //////////////////////////////////////////////////
  if (currentKeyCode==ENTER) {
    String filename = nowAsString() + ".png";
    println("SAVED AS "+filename);
    saveFrame(SNAP_FOLDER_PATH + filename);
    //saveFrame(SNAP_FOLDER_PATH + "screen-####.png");
    noCursor();
  } else {
    cursor();
  }
  /////////////////////////////////////////////////
  //  CLEAR BACKGROUND = TAB 
  /////////////////////////////////////////////////
  if (currentKeyCode==TAB) {
    background(0);
  } 

  /////////////////////////////////////////////////
  //     SELECT NEW SOURCE IMAGES FOR CHANNELS 1-4
  /////////////////////////////////////////////////
  //select source for chnl 1
  if (currentKey == 'q') {
    chnl_1_journals.changeSourceImage("journals");
  } else if (currentKey == 'w') {
    chnl_1_journals.changeSourceImage("emblems");
  }
  //select source for chnl 2
   else if (currentKey == 'e') {
    chnl_2_emblems.changeSourceImage("journals");
  } else if (currentKey == 'r') {
    chnl_2_emblems.changeSourceImage("emblems");
  }
  //select source for chnl 3
  else if (currentKey == 't') {
    chnl_3.changeSourceImage("journals");
  } else if (currentKey == 'y') {
    chnl_3.changeSourceImage("emblems");
  }
  //select source for chnl 4
  else if (currentKey == 'u') {
    chnl_4.changeSourceImage("journals");
  } else if (currentKey == 'i') {
    chnl_4.changeSourceImage("emblems");
  }
  //select source for chnl 5
  else if (currentKey == 'o') {
    chnl_5_kinect.changeSourceImage("journals");
  } else if (currentKey == 'p') {
    chnl_5_kinect.changeSourceImage("emblems");
  }
  //select source for chnl 6
  else if (currentKey == '[') {
    chnl_6_trackShape.changeSourceImage("journals");
  } else if (currentKey == ']') {
    chnl_6_trackShape.changeSourceImage("emblems");
  }
  
  //     END SELECT NEW SOURCE IMAGE
  ////////////////////////////////////////////////
}
//  END KEYCODE FOR EVENTS
/////////////////////////////////////////////////////////////


////////////////////////////////////////////////////
//    SCREEN SNAPS
// output: right now + project + version as a string
// 2015-03-15_portal-control/portal-control_v0.5.3_01-42-50
String nowAsString() {
  return nf(year(), 4)+"-"+
    nf(month(), 2)+"-"+
    nf(day(), 2)+"_"+
    project+"/"+
    project+"_"+
    version+"_"+
    nf(hour(), 2)+"-"+
    nf(minute(), 2)+"-"+
    nf(second(), 2);
}
// Save the current screen state as a .png in the SNAP_FOLDER_PATH,
// If you pass a filename, we'll use that, otherwise we'll default to the current date.
// NOTE: do NOT pass the ".jpg" or the path.
// Returns the name of the file saved.
String saveScreen() {
  return saveScreen(null);
}
String saveScreen(String fileName) {
  if (fileName == null) {
    fileName = nowAsString();
  }
  save(SNAP_FOLDER_PATH + fileName + ".png");
  println("SAVED AS "+fileName);
  return fileName;
}
//   END SCREEN SNAPS
////////////////////////////////////////////////////////////
