
// create function to recv and parse oscP5 messages
void oscEvent (OscMessage theOscMessage) {

/*
  String addr = theOscMessage.addrPattern();  //never did fully understand string syntaxxx
  float val = theOscMessage.get(0).floatValue(); // this is returning the get float from bellow

  println(addr);
*/

/*
  if (addr.equals("/1/lookLR")) {  //remove the if statement and put it in draw
    rcvLookLR = val; //assign received value.  then call function in draw to pass parameter
  }
  else if (addr.equals("/1/lookUD")) {
    rcvLookUD = val;// assigned receive val. prepare to pass parameter in called function: end of draw
  }
*/




 //////////////print the address pattern and the typetag of the received OscMessage
  print("### received an osc message.");
  print(" addrpattern: "+theOscMessage.addrPattern());
  println(" typetag: "+theOscMessage.typetag());

//println("### received an osc message. with address pattern "+theOscMessage.addrPattern());  

 
 //for (int i = 1; i < chnl.length; i ++) {}
 if(theOscMessage.checkAddrPattern("/3/position")==true) {
    float xPos = theOscMessage.get(1).floatValue();  
    float yPos = theOscMessage.get(0).floatValue();  
    print ("xPos ="+xPos+" yPos = "+yPos);
    // update chnl_3
    //chnl_1_journals.updateChannelShapeLocation(xPos, yPos);
    chnl_3.updateChannelShapeLocation(xPos, yPos);
        
}
    
else	if(theOscMessage.checkAddrPattern("/3/multipush1/1/1")==true) {
    print ("Yes");
    //float x = theOscMessage.get(0).floatValue();  
    changeSource = !changeSource;

    if (changeSource) {
       chnl_1_journals.changeSourceImage("journals");
        chnl_2_emblems.changeSourceImage("emblems");
        chnl_3.changeSourceImage("journals");
}

else  if(theOscMessage.checkAddrPattern("/3/fader38")==true) {
    rotate(1);

}

}








/*
   if(theOscMessage.checkTypetag("ifs")) {
      //parse theOscMessage and extract the values from the osc message arguments.
      int firstValue = theOscMessage.get(0).intValue();  
      float secondValue = theOscMessage.get(1).floatValue();
      String thirdValue = theOscMessage.get(2).stringValue();
      print("### received an osc message /test with typetag ifs.");
      println(" values: "+firstValue+", "+secondValue+", "+thirdValue);
      return;
    }  
*/    






///////////////
}      ///////
//////////////