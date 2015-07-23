//PROGRAM NAME - induction of different cadherins
// (Version number/ date is part of the name of this file)

// This program is written in Processing 2.0 (a free, java-like language - see
// http://www.processing.org/ for information and downloadable interpreters/compilers)
// The program has been set out more for clarity than space/runtime optimization, given
// that it is likely to be played with by other scientists. Also, given the technical 
// audience, I have not bothered to write fancy interfaces - variables are declared
// at the top of the code and if you want to change them, just change them there. 
// Respect any advice on variable limits given in the comments box or the program
// will behave wrongly: this is not idiot-proofed!

// The purpose of this program is to model the adhesion-dependent cell sorting of
// mixtures of cells with different adhesion molecules.
// In this cellular automaton version, cells are considered of constant size and shape
// and they move by exchanging locations with a neighbour with no attempt being made
// to model the details of movement realistically. Where there is no energy cost or
// gain, swaps can be made at random (to simulate noise in the system).

// To make a movie, use imageJ, FILE->IMPORT image sequence (just click on the first file
// in the directory), then FILE->SAVE_AS -> animated GIF

// =====================================================================================

// >> SECTION 1: VALUES OF GLOBAL VARIABLES


// Biologically-relevant variables:
// ------------------------------
float startpropstate2 = 0.5; // proportion (0-1) of cells that are type 2 rather than type 1
float energyoneone = -0.5; // energy term for type 1 cell adhering to type 1: must be < 0
float energyonetwo = -0; // energy term for type 1 cell adhering to type 2: must be <= 0
float energytwotwo = -1; // energy term for type 2 cell adhering to type 2: must be < 0
// note - if energy were not < 0 the cells would not want to adhere - think about thermodyamics


// Variables involved only in the mechanics of computing - no direct biological correlates:
// ---------------------------------------------------------------------------------------
int movieflag = 0; // 1 to make a movie of the output, 0 not to bother
int startframeflag = 1; // 1 to save the start frame as an image
int endframeflag = 1; // 1 to save the 'end' frame as an image: define 'end' as below
int endframe = 10000; // probably at least 4000. Note this is just for saving an image - you still have to stop the program manually.
float movieframegap = 10; // if making a movie, how many iterations to run between save frames?
float mainloopiteration = 0; // just initializing a global variable
int fieldsize = 50; // this is the size of the field in squares (each 4um x 4um) - make this number EVEN
int [] [] fieldarray = new int [fieldsize + 1] [fieldsize + 1]; // this is the array containing the field.
int [] [] colourlookup = new int [3] [3];
int [] steparoundx = new int [9]; // this array will be used for holding offsets for looking at a cell's neigbours
int [] steparoundy = new int [9]; // ditto
float [] [] diffneighbourlist = new float [9] [5];
PFont f;







// =====================================================================================================


// >> SECTION 2: MAIN PROGRAM FLOW

void setup() { // ('setup' is always the first function that Processing runs, handing on to 'draw')
  f = loadFont("ArialNarrow-12.vlw");
  textFont(f);
  size(fieldsize * 5 + 5, fieldsize * 5 + 35); // set up the size of the screen window
  String metadata = "Propn Green = " + startpropstate2 + "; R-R= " + energyoneone + ", R-G= " + energyonetwo + ", G-G= " +energytwotwo;
  String credit = "ver JAD 15 Oct 2014";  
  setupsteparoundarray(); // this sets up an array of offsets used for looking at a square's neighbours
  setupcolourlookup (); // this just sets up the colour lookup table
  initialanatomy(); // set up the field at the start of the experiment
  drawthefield();
  fill (0,0,0);
  text (metadata, 2, fieldsize * 5 + 15);
  text (credit, fieldsize * 5 -100, fieldsize * 5 + 30);
}


// --------------------

void draw() { // ('draw' is the main program in Processing. It always loops until stopped).
  drawthefield();
  mainloopiteration ++;
  givearandomcellthechancetomove ();
  println ("iteration " + mainloopiteration);
}


// ========================================================================================================

// SECTION 3: THE FUNCTIONS CALLED BY setup() AND draw():


void setupsteparoundarray() { // this just loads up an array of offsets used for 
                              // inspecting a cell's 9 neighbours.
  steparoundx [0] = -1; // top left
  steparoundy [0] = 1;
  steparoundx [1] = 0; // up
  steparoundy [1] = 1;
  steparoundx [2] = 1; // top right
  steparoundy [2] = 1;
  steparoundx [3] = 1; // right
  steparoundy [3] = 0;
  steparoundx [4] = 1; // bottom right
  steparoundy [4] = -1;
  steparoundx [5] = 0; // down
  steparoundy [5] = -1;
  steparoundx [6] = -1; // down left
  steparoundy [6] = -1;
  steparoundx [7] = -1; // left
  steparoundy [7] = 0;
}
//--------------------------------------------------------------

void setupcolourlookup () {
  colourlookup [0] [0] = 0;
  colourlookup [0] [1] = 0;
  colourlookup [0] [2] = 0;
  colourlookup [1] [0] = 255;
  colourlookup [1] [1] = 0;
  colourlookup [1] [2] = 0;
  colourlookup [2] [0] = 0;
  colourlookup [2] [1] = 255;
  colourlookup [2] [2] = 0;
} // end the function 'setupcolourlookup'

  
//---------------------------------------------------------------

void initialanatomy () {
   for (int x = 1; x < fieldsize + 1; x++) { // these 2 nested loops visit every location 
      for (int y = 1; y < fieldsize +1; y++) {
        fieldarray [x] [y] = 1; // default cell type
        if (random(0,1) < startpropstate2) {
          fieldarray [x] [y] = 2; // set this one as the other cell type
        } // end 'if (random...'
        if (x==1 | x == fieldsize | y==1 | y == fieldsize) {
          fieldarray [x] [y] = 0; // set the borders to state zero
        }
      } // end y loop
   }// end x loop
  } // end function 'initialanatomy'
  
// -------------------------------------------------------------

void drawthefield() { // this simply draws the thing on the screen
    int r = 0; // just initializing a variable for this routine
    int g = 0; // ditto
    int b = 0; // ditto
    int celltype = 0; // ditto
    for (int x = 1; x < fieldsize + 1; x++) { // these 2 nested loops visit every location 
        for (int y = 1; y < fieldsize +1; y++) {
          celltype = fieldarray [x] [y];
          r = colourlookup [celltype] [0];
          g = colourlookup [celltype] [1];
          b = colourlookup [celltype] [2];         
          fill (r,g,b); // sets the colour the pixel will be at the 'point' command below
          ellipse (x * 5, y * 5, 5,5);
        } // end the y loop
    } // end the x loop
    float trap = mainloopiteration/movieframegap;
    float inttrap = int(trap);
    println (trap + "   " + inttrap);
    if (movieflag ==1 && trap == inttrap) { 
    makeanewimage();
      }
    if (startframeflag == 1 && mainloopiteration ==0) {
    makestartimage();
    }
    if (endframeflag ==1 && mainloopiteration ==endframe) {
    makeendimage();
    }  
  fill (200,200,200);
  stroke (200,200,200);
  rect (2, fieldsize * 5 + 18, 70, fieldsize * 5 + 22);  
  String itstring = "Cycle " + mainloopiteration;
  fill (0,0,255);
  text (itstring, 2, fieldsize * 5 + 30);
} // end the function 'drawthefield'

// ------------------------------------------------------------
          
          
void givearandomcellthechancetomove () {
  
  // first, choose a cell at random
  int chosencellx = int (random (3, fieldsize)) -1; // choose a random cell
  int chosencelly = int (random (3, fieldsize)) -1;
  int chosencelltype = fieldarray [chosencellx] [chosencelly];
  // now clear away some variables that will be used to look at its neighbours
  int diffneighbourtot = 0;
  int swapsytype = 0; // just initializing
  int testx = 0; // just initializing
  int testy = 0; // just initializing
  int testtype = 0; // just initializing
  float currentenergy = 0; // just initializing
  float swappedenergy = 0; // just initializing
  float chosencellenergy = 0; // just initializing
  float swapsychosencellenergy = 0; // just initializing
  int neightype = 999;
  // now look at all of the neighbours and make a list and total of any that are different
  for (int lookabout = 0; lookabout < 8; lookabout ++) {
    neightype = fieldarray [chosencellx + steparoundx [lookabout]] [chosencelly + steparoundy [lookabout]];
    if (neightype != chosencelltype) {
      diffneighbourtot ++;
      diffneighbourlist [diffneighbourtot] [1] = lookabout; // note which neighbours were different
    }
  } // end the lookabout loop: diffenighbourtot is the total of different neighbours
  if (diffneighbourtot > 0) { // only do the rest of this function if there is a different neighbour
    // first calculate the energy term of this cell as it is
    chosencellenergy = energyofthiscell (chosencellx, chosencelly, chosencelltype); // this function is set out later
    if (chosencelltype == 1) {
      swapsytype = 2;
    }
    if (chosencelltype ==2) {
     swapsytype = 1;
    }
    swapsychosencellenergy = energyofthiscell (chosencellx, chosencelly, swapsytype); // energy if swapped
    // now go round each of the neighbours to see the cost of a swap
    for (int neightest = 0; neightest < diffneighbourtot + 1; neightest ++) {      // CHANGED 15 OCT used to start at 1
      testx = chosencellx + steparoundx [int(diffneighbourlist [neightest] [1])];
      testy = chosencelly + steparoundy [int(diffneighbourlist [neightest] [1])];
      testtype = fieldarray [testx] [testy];
      currentenergy = energyofthiscell (testx, testy, testtype) + chosencellenergy; 
      diffneighbourlist [neightest] [2] = currentenergy; // col 2 stores the Hamiltonian with no swap
      if (testtype ==1) {
        swapsytype =2;
      }
      if (testtype ==2) {
        swapsytype = 1;
      }
      swappedenergy = swapsychosencellenergy + energyofthiscell (testx, testy, swapsytype); 
      diffneighbourlist [neightest] [3] = swappedenergy; // col 3 stores Hamiltonian with a swap
      diffneighbourlist [neightest] [4] = diffneighbourlist [neightest] [3] - diffneighbourlist [neightest] [2];
    } // close out the loop through all the neighbours that are different
    // So at this stage, we have a list, in the diffneighbourlist array, of the neighbour index
    // in column 1, the Hamiltonian for the status quo in column 2 and the Hamiltonian for a swap
    // with this specific neighbour in column 3, and the *energy difference* in column 4.
    // (cols 2 and 3 are really retained just for debugging/ verification purposes)
    // Now we need to see which possible change is most energetically favourable
    float bestchange = 0; // null a variable
    int bestchoice = 0;
    int numberofequals = 0;
    int plodindex = 0;
    int plodderoffset = int (random (0,8)); // this is a random offset: it is important to stop systemic bias that would happen if we always started examining neighbours in the same order.
    for (int plodder = 0; plodder < diffneighbourtot + 1; plodder ++) { 
       plodindex = plodder + plodderoffset;
       if (plodindex > 8) {
        plodindex = plodindex - 8;
       } 
       
       if (diffneighbourlist [plodindex] [4] < bestchange) {
        bestchange = diffneighbourlist [plodindex] [4]; // remember the new best
        bestchoice = plodindex; // and remember which cell it came from
      } 
      if (diffneighbourlist [plodindex] [4] == bestchange) {
        numberofequals = numberofequals + 1;
        if (random (0,1) < (1/numberofequals)) {
          bestchange = diffneighbourlist [plodindex] [4]; 
      } 
      } 
    } // end of plodding loop - the best energy change is now in bestchange
    if (bestchange <= 0) { // only move anything if the best energy change is favourable!
       
       // Right - let's actually do the swap
       int swapx = chosencellx + steparoundx [int(diffneighbourlist [bestchoice] [1])];
       int swapy = chosencelly + steparoundy [int(diffneighbourlist [bestchoice] [1])];
       if (fieldarray [swapx] [swapy] > 0) { // ie do not do this if the destination is a border cell
          fieldarray [chosencellx] [chosencelly] = fieldarray [swapx] [swapy];
          fieldarray [swapx] [swapy] = chosencelltype;
       } // end if destination > 0 bit (ie do it only if not a border)
    } // end the 'if bestchange < 0 (ie only move if energetically favourable)   
  } // end 'if diffneighbourtot > 0
} // end the function 'givearandomcellthechancetomove'

// -----------------------------------------------------------


float energyofthiscell (int chosencellx, int chosencelly, int thiscelltype) { // this function calculates the energy of a specified cell
  
  float energytotal = 0; // just initializing
  int thisneigh = 0; // just initializing
  for (int neighbourexplore = 0; neighbourexplore < 8; neighbourexplore ++) {
    thisneigh = fieldarray [chosencellx + steparoundx [neighbourexplore]] [chosencelly + steparoundy [neighbourexplore]];
    if (thisneigh == 1 && thiscelltype ==1 ) {
      energytotal = energytotal + energyoneone;
    }
    if (thisneigh == 1 && thiscelltype == 2) {
      energytotal = energytotal + energyonetwo;
    }
    if (thisneigh == 2 && thiscelltype == 1) {
      energytotal = energytotal + energyonetwo;
    }
    if (thisneigh ==2 && thiscelltype == 2) {
      energytotal = energytotal + energytwotwo;
    }
    if (thisneigh == 0) {
      energytotal = 999; // boundarycondition
    }
  } // finish looking at all the neighbours
  // the energy total for this cell is now stored in 'energytotal'
  return energytotal;
} // end the function 'energyofthiscell'
    
    
// -------------------------------

void makeanewimage() { // this routine copies the screen into an image for saving as a movie 
  save("Pattern" + mainloopiteration + ".jpg");
}

// -------------------------------

void makestartimage() { // this routine copies the screen into an image  
  save("Patternmovie" + mainloopiteration + ".jpg");
}
// -------------------------------

void makeendimage() { // this routine copies the screen into an image for saving as a movie 
  save("Patternmovie" + mainloopiteration + ".jpg");
}



