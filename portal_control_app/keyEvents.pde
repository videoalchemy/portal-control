//////////////////////////////
// KeyEVENTS - flipping switches!


/////////////////////
//  KEYCODE FOR EVENTS

// Current keyCode for the pressed key
int currentKey = -1;

// Remember the current key when it goes down.
void keyPressed() {
  currentKey = keyCode;
}

// Clear the current key when it goes up.
void keyReleased() {
  currentKey = -1;
}

void updateControlsFromKeyboard() {
  // if no key is currently down, make sure all of the buttons are up and bail  
  if (currentKey == -1) {
    //clearButtons();
    return;
  }

  //saves an image everytime the ENTER is pressed.
  if (currentKey==ENTER) {
    String filename = nowAsString() + ".png";
    println("SAVED AS "+filename);
    saveFrame(SNAP_FOLDER_PATH + filename);
    //saveFrame(SNAP_FOLDER_PATH + "screen-####.png");
    noCursor();
  } else {
    //println(currentKey);rg
    cursor();
  }

  // TAB key clears background (to black)
  if (currentKey==TAB) {
    background(0);
  } 


  // change the source image
  else if (currentKey == 'J') {
    chnl_1_journals.changeSourceImage("journals");
  } else if (currentKey == 'E') {
    chnl_1_journals.changeSourceImage("emblems");
  }

   else if (currentKey == '1') {
    chnl_2_emblems.changeSourceImage("journals");
  } else if (currentKey == 'Q') {
    chnl_2_emblems.changeSourceImage("emblems");
  }

  else if (currentKey == '4') {
    chnl_4_has_controls.changeSourceImage("journals");
  } else if (currentKey == '5') {
    chnl_4_has_controls.changeSourceImage("emblems");
  }
}
//  END KEYCODE FOR EVENTS
/////////////////////////



///////////////////
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


//
///////////////////////

