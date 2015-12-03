import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CellAutomaton //extends JFrame
{
  
//==============================================================================================
// >> SECTION 1: VALUES OF GLOBAL VARIABLES
  
  // The final proportion of all cell types must add to FINAL_PROP  
  public static final double FINAL_PROP = 1.0;
  // The maximum number of cell types in the model is MAX_TYPES
  public static final int MAX_TYPES = 10;
  
  // Initiates NUM_TYPE RGB objects corresponding to allowed Cell RGB colours  
  public static final RGB red = new RGB(255,0,0,"RED"); 
  public static final RGB green = new RGB(0,255,0,"GREEN");
  public static final RGB blue = new RGB(0,0,255,"BLUE");
  public static final RGB mag = new RGB(255,0,255,"MAG");
  public static final RGB yel = new RGB(255,255,0,"YEL");
  public static final RGB cyan = new RGB(0,255,255,"CYAN");
  public static final RGB oran = new RGB(255,175,50,"ORAN");
  public static final RGB purp = new RGB(180,50,255,"PURP");
  public static final RGB pink = new RGB(255,50,200,"PINK");
  public static final RGB darkgr = new RGB(50,150,100,"DARKGR");
  public static final RGB black = new RGB(255,255,255,"black");
  
  // Initiates NUM_TYPE Cell objects corresponding to the cells: RGB colour, type, and proportion   
  static Cell red1 = new Cell(red,1,0.5);
  static Cell green2 = new Cell(green,2,0.5);
  static Cell blue3 = new Cell(blue,3,0.0);
  static Cell mag4 = new Cell(mag,4,0.0);
  static Cell yel5 = new Cell(yel,5,0.0);
  static Cell cyan6 = new Cell(cyan,6,0.0);
  static Cell oran7 = new Cell(oran,7,0.0);
  static Cell purp8 = new Cell(purp,8,0.0);
  static Cell pink9 = new Cell(pink,9,0.0);
  static Cell darkgr10 = new Cell(darkgr,10,0.0);
  static Cell border = new Cell(black,0,0.0);

  static double redProp = 0.5;
  static double greenProp = 0.5;
  static double blueProp = 0.0;
  static double magProp = 0.0;
  static double yelProp = 0.0;
  static double cyanProp = 0.0;
  static double oranProp = 0.0;
  static double purpProp = 0.0;
  static double pinkProp = 0.0;
  static double darkgrProp = 0.0;
  
  // Initiates array of all possible Cell objects  
  static final Cell[] CELL_ARR = new Cell[]{red1,green2,blue3,mag4,yel5,cyan6,oran7,purp8,pink9,darkgr10};

  // Constants used for drawing and exploring the field of cells
  public static final int FIELD_SIZE = 50; // size of field in squares (each 4um x 4um) -- must be EVEN
  public static Cell[][] FIELD = new Cell[FIELD_SIZE][FIELD_SIZE]; // array containing FIELD
  public static final int[] STEP_X = new int[9]; // used to hold offsets when looking at cell's neighbours
  public static final int[] STEP_Y = new int[9]; // used to hold offsets when looking at cell's neighbours
  public static final double[][] DIFF_NEIGH = new double[9][5];
  public static double[][] BIND_ENERGIES = new double[MAX_TYPES][MAX_TYPES];
  static Random rand = new Random(3);

  
//==============================================================================================
// >> SECTION 2: SET UP
  
  public static void setStepArray()
  {
    for (int i=0;i<8;i++)
    {
      if (i==0 || i==6 || i==7)
        STEP_X[i] = -1;
      else if (i==1 || i==5)		// 0:-1,1 |1:0,1 |2:1,1
        STEP_X[i] = 0;				// ---------------------
      else							// 7:-1,0 |  X   |3:1,0
        STEP_X[i] = 1;				// ---------------------
      if (i==0 || i==1 || i==2)		// 6:-1,-1|5:0,-1|4:1,-1
        STEP_Y[i] = 1;
      else if (i==3 || i==7)
        STEP_Y[i] = 0;
      else
        STEP_Y[i] = -1;
    }
  }
  
  public static void initialCellDist()
  {
    // Generates a stream of random numbers for placing cells
    Random r = new Random(5);
    
    // Copies array of proportions into cumulative Proportion array
    double[] cumProp = new double[10];
    for (int i=0; i<10; i++)
    {
    	cumProp[i] = CELL_ARR[i].getProportion();
    }
    // Creates running total in cumulative proportion array for placing cells
    for (int i=1; i<10; i++)
    {
      cumProp[i] = cumProp[i] + cumProp[i-1];
    }
    
    // loops through all locations in field
    for (int x=0;x<FIELD_SIZE;x++)
    {
      for (int y=0;y<FIELD_SIZE;y++)
      {
    	// FIELD[x][y] = red1; // default cell colour
    	FIELD[x][y] = new Cell(red,1,redProp);
        double rand = r.nextDouble();
        
        if (rand > cumProp[0] && rand <= cumProp[1])
        {
        	//FIELD[x][y] = green2; // places green cell
        	FIELD[x][y] = new Cell(green,2,greenProp);
        }
        else if (rand > cumProp[1] && rand <= cumProp[2])
        {
        	//FIELD[x][y] = blue3; // places blue cell
        	FIELD[x][y] = new Cell(blue,3,blueProp);
        }
        else if (rand > cumProp[2] && rand <= cumProp[3])
        {
        	// FIELD[x][y] = mag4; // places magenta cell
        	FIELD[x][y] = new Cell(mag,4,magProp);
        }
        else if (rand > cumProp[3] && rand <= cumProp[4])
        {
        	// FIELD[x][y] = yel5; // places yellow cell
        	FIELD[x][y] = new Cell(yel,5,yelProp);
        }
        else if (rand > cumProp[4] && rand <= cumProp[5])
        {
        	//FIELD[x][y] = cyan6; // places cyan cell
        	FIELD[x][y] = new Cell(cyan,6,cyanProp);
        }
        else if (rand > cumProp[5] && rand <= cumProp[6])
        {
        	//FIELD[x][y] = oran7; // places orange cell
        	FIELD[x][y] = new Cell(oran,7,oranProp);
        }
        else if (rand > cumProp[6] && rand <= cumProp[7])
        {
        	//FIELD[x][y] = purp8; // places purple cell
        	FIELD[x][y] = new Cell(purp,8,purpProp);
        }
        else if (rand > cumProp[7] && rand <= cumProp[8])
        {
        	//FIELD[x][y] = pink9; // places pink cell
        	FIELD[x][y] = new Cell(pink,9,pinkProp);
        }
        else if (rand > cumProp[8] && rand <= cumProp[9])
        {
        	//FIELD[x][y] = darkgr10; // Places dark green cell
        	FIELD[x][y] = new Cell(darkgr,10,darkgrProp);
        }
        if (x==0 || x == FIELD_SIZE-1 || y == 0 || y == FIELD_SIZE-1)
        {
          FIELD[x][y] = new Cell (black,0,0.0); // sets borders to be black "cells"
        }
      }
    }
  }
  public static void setBindEnergies(double[][] energies, int noTypes)
  {
	  boolean isCorrect=false;
	  while (isCorrect==false)
	  {
		  for (int i=0;i<noTypes;i++)
		  {
			  for (int j=i;j<noTypes;j++)
			  {
				  Scanner sc = new Scanner(System.in);
				  int iCellType=i+1;
				  int jCellType=j+1;
				  System.out.println("Enter the binding energy (a negative Hamiltonian) for cell type " + iCellType + " binding to cell type " + jCellType + ".");
				  double e = sc.nextDouble();
				  energies[i][j] = e;
				  energies[j][i] = e;
			  }
		  }
		  System.out.println("Are these binding energies correct? (y/n)");
		  for (int i=0;i<noTypes;i++)
		  {
			  for (int j=0;j<noTypes;j++)
			  {
				  System.out.print(energies[i][j] + " ");
			  }
			  System.out.println();
		  }
		  Scanner sc = new Scanner(System.in);
		  char a = sc.next().charAt(0);
		  if (a=='y') {isCorrect=true;}
		  else if (a=='n') {isCorrect=false;}
	  }
  }
//==============================================================================================
// >> SECTION 3: MAIN PROGRAM
  
  // Sets energy of thisCell--bindsTo to energy and vice versa
  public void setEnergy(Cell thisCell, Cell bindsTo, double energy)
  {
    thisCell.changeBindEnergy(bindsTo,energy);
    bindsTo.changeBindEnergy(thisCell,energy);
  }

  public static void setProportions(Cell thisCell, double newProp) //throws maxPropExceeded
  {
    thisCell.setProportion(newProp);
    double sumProps = 0.0;
    for (int i=0;i<MAX_TYPES;i++)
    {
    	sumProps += CELL_ARR[i].getProportion();
    }
    /* if (sumProps != 1.0)
    {
      throw new maxPropExceeded("Error: Total proportion of cells must equal 1.0.");
    } */
  }
  
  public static void moveCell()
	{
	  	double noSwapE = 0.0;
	  	double[][] swapE = new double[3][3];
	  
		int[] randCellCoord = chooseRandomCell();
		int x = randCellCoord[0];
		int y = randCellCoord[1];
		if (x > FIELD_SIZE-3 || y > FIELD_SIZE-3 || x < 2 || y < 2) {return;}
		
		Cell chosenCell = new Cell(null,0,0);
		//FIELD[x][y].copyCell(chosenCell); //Insert test case to make sure copyCell works in this case
		chosenCell = FIELD[x][y];
		int numDiffNeighbours = 0;
		
		Cell[][] surrounding = new Cell[3][3];
		
		// Check all surrounding cells and increment numDiffNeighbours by 1 for each different cell
		for (int i=-1;i<2;i++)
		{
			for (int j=-1;j<2;j++)
			{
				//FIELD[x+i][y+i].copyCell(surrounding[i+1][j+1]);
				surrounding[i+1][j+1] = FIELD[x+i][y+j];
				if (i==0 && j==0) {continue;}
				else if (surrounding[i+1][j+1].getCellType() != chosenCell.getCellType())
				{
					numDiffNeighbours++;
				}
			}
		}
		
		// If all of the neighbours of the chosen cell are NOT the same, try swapping with each surrounding cell
		// Store the energy of the new conformation from the swap in swapE
		if (numDiffNeighbours > 0)
		{
			int[] noSwapIndex = {1,1};
			noSwapE = cellEnergy(surrounding,noSwapIndex);
			
			for (int i=0;i<surrounding.length;i++)
			{
				for (int j=0;j<surrounding[0].length;j++)
				{
					if (i==1 && j==1) {continue;}
					else
					{
						int [] swapIndex = {i,j};
						System.out.println("Swapping with " + i + "," + j + "***");
						printMatrix(surrounding);
						swapE[i][j] = cellEnergy(surrounding,swapIndex);
					}
				}
			}
		}
	  
	  /*int diffNeighTot = 0;
		int testX = 0;
		int testY = 0;
		
		// *** THIS REF PROB DOESNT WORK
		//Cell testCell = null;
		
		double currEnergy = 0.0;
		double swapEnergy = 0.0;
		double cellEnergy = 0.0;
		int neighType = 999;
		
		// chooses a random cell in the grid
		int[] cellCoord = chooseRandomCell(); // gets x and y coordinates of cell
		int x = cellCoord[0];
		int y = cellCoord[1];
		
		// There's a better way to do this by altering the chooseRandomCell method
		if (x > FIELD_SIZE-3 || y > FIELD_SIZE-3 || x < 2 || y < 2) { return; } // if the cell is a border cell do not continue
		
		// *** THIS REF PROB DOESNT WORK
		// Cell chosenCell = FIELD[x][y]; // references Cell object at the chosen coordinates
		
		for (int i=0;i<8;i++)
		{
			int xStep = x+STEP_X[i];
			int yStep = y+STEP_Y[i];
			
			//System.out.println("moveCell x: " + x + " y: " + y + " x + stepx[i]: " + xStep + " y+ystep: " + yStep);
			
			neighType = FIELD[x + STEP_X[i]][y + STEP_Y[i]].getCellType();
			if (neighType != FIELD[x][y].getCellType())
			{
				diffNeighTot++;
				DIFF_NEIGH[diffNeighTot-1][0] = i; // makes a list of neighbours that are different
			}
		} // diffNeighTot is total number of different neighbours
		
		if (diffNeighTot > 0)
		{
		  	//System.out.println("here");
			
			cellEnergy = energyOfCell(x,y,FIELD[x][y].getBindEnergies()); // gets energy of randomly chosen cell
			
			for (int i=0; i<=diffNeighTot; i++)
			{
				testX = x + STEP_X[(int) DIFF_NEIGH[i][0]]; // gets x coordinate of test cell type
				testY = y + STEP_Y[(int) DIFF_NEIGH[i][0]]; // gets y coordinate of test cell type
				//testCell = FIELD[testX][testY];	// gets Cell object of cell at coordinate x,y
				
				currEnergy = energyOfCell(testX,testY,FIELD[testX][testY].getBindEnergies()) + cellEnergy; // gets energy of test cell + chosen cell energy
				
				DIFF_NEIGH[i][1] = currEnergy; // column 1 stores the energy without swapping
				
				swapEnergy = energyOfCell(x,y,FIELD[testX][testY].getBindEnergies()); // gets energy if a swap is initiated
				
				// if (swapEnergy >= 999.0) { break; } // if the swap involves a border cell, do not continue
				
				DIFF_NEIGH[i][2] = swapEnergy; // column 2 stores the energy with a swap
				DIFF_NEIGH[i][3] = DIFF_NEIGH[i][2] - DIFF_NEIGH[i][1]; // column 3 stores the difference between swap and no swap energies
			}
			
			double bestSwap = 0.0;
			int bestChoice = 0;
			int numEquals = 0;
			int offsetIndex = 0;
			int offset = rand.nextInt(9);
			
			for (int i=0;i<=diffNeighTot;i++)
			{
				offsetIndex = i + offset;
				if (offsetIndex > 8)
				{
					offsetIndex -= 8;
				}
				
				if (DIFF_NEIGH[offsetIndex][4] < bestSwap)
				{
					bestSwap = DIFF_NEIGH[offsetIndex][4];
					bestChoice = offsetIndex;
				}
				else if (DIFF_NEIGH[offsetIndex][4] == bestSwap)
				{
					numEquals++;
					if (rand.nextInt(1) < (1/numEquals))
					{
						bestSwap = DIFF_NEIGH[offsetIndex][4];
					}
				}
			}
			
			if (bestSwap <= 0)
			{
				//System.out.println("Diff neigh: " + DIFF_NEIGH[bestChoice][0] + " currE: " + currEnergy);
				
				int swapX = x + STEP_X[(int) DIFF_NEIGH[bestChoice][0]];
				int swapY = y + STEP_Y[(int) DIFF_NEIGH[bestChoice][0]];
				
				// Cell swapCell = FIELD[swapX][swapY];
				
				// TESTING
				//System.out.println("swap in cell: " + swapCell.getCellType());
				//System.out.println("original cell: " + FIELD[x][y].getCellType());
				
				/* if (FIELD[swapX][swapY].getCellType() != 0) // as long as the cell to swap with is not a black border cell
				{
					FIELD[x][y] = swapCell;
					FIELD[swapX][swapY] = FIELD[x][y];
					System.out.println("swapped " + x + "," + y + " and " + swapX + "," + swapY + " ");
				} 
				
				swapCells(x,y,swapX,swapY);
				System.out.println("swapped " + x + "," + y + " and " + swapX + "," + swapY + " ");
				
				//System.out.println("cell in x,y: " + FIELD[x][y].getCellType());
				//System.out.println("cell in swapx,swapy: " + FIELD[swapX][swapY].getCellType());
			}
		}*/
	}
	
	// Calculates the energy of each cell using the 8 surrounding cells
  	// switchIndex == {1,1} if no swap is initiated and we just want the energy of the central cell
  	public static double cellEnergy(Cell[][] surrounding, int[] switchIndex)
  	{
  		double totalE = 0.0;	// Stores the total Energy of the cell with or without a swap
  		int neighbourType = 999;// Stores the type of neighbour
  		Cell[][] newSurrounding = new Cell[3][3];	// will contain a new conformation if a switch is initiated
  		
  		boolean isSwitch = false; // true if a switch is initiated, false if switch index = 1,1
  		boolean switched = false; // true if the switch has already been initiated (to make sure switch isn't done twice)
  		
  		// Initiates the switch if the indices indicate that the original conformation isn't being tested
  		if (switchIndex[0] != 1 && switchIndex[1] != 1)	// if the switch index isn't switching the middle cell with itself
  		{
  			isSwitch = true;
  			for (int i=0;i<surrounding.length;i++)
  			{
  				for (int j=0;j<surrounding[0].length;j++)
  				{
  					if (switched == false && (i == switchIndex[0] && j == switchIndex[1]) /*|| (i == 1 && j == 1)*/)	//if the loop lands on the switch index or on the middle cell
  					{
  						//surrounding[1][1].copyCell(newSurrounding[i][j]); // initiate the switch of middle cell and switch index cell in new conf array
  						//surrounding[i][j].copyCell(newSurrounding[1][1]);
  						//newSurrounding[i][j] = surrounding[1][1];
  						newSurrounding[i][j] = new Cell(surrounding[1][1].getRGB(),surrounding[1][1].getCellType(),surrounding[1][1].getProportion());
  						//newSurrounding[1][1] = surrounding[i][j];
  						newSurrounding[1][1] = new Cell(surrounding[i][j].getRGB(),surrounding[i][j].getCellType(),surrounding[i][j].getProportion());
  						switched = true;
  					}
  					else if (switched == false && (i==1 && j==1))
  					{
  						newSurrounding[i][j] = new Cell(surrounding[1][1].getRGB(),surrounding[1][1].getCellType(),surrounding[1][1].getProportion());
  						newSurrounding[1][1] = new Cell(surrounding[i][j].getRGB(),surrounding[i][j].getCellType(),surrounding[i][j].getProportion());
  						//newSurrounding[i][j] = surrounding[1][1];
  						//newSurrounding[1][1] = surrounding[i][j];
  						switched = true;
  					}
  					else
  					{
  						//surrounding[i][j].copyCell(newSurrounding[i][j]); // else just copy the cell exactly as is into the new conf array
  						//newSurrounding[i][j] = surrounding[i][j];
  						newSurrounding[i][j] = new Cell(surrounding[i][j].getRGB(),surrounding[i][j].getCellType(),surrounding[i][j].getProportion());
  					}
  				}
  			}
  			// TEST PRINT
  			System.out.println("surrounding");
  			for (int i=0; i<surrounding.length;i++)
  			{
  				for (int j=0;j<surrounding[0].length;j++)
  				{
  					System.out.print(surrounding[i][j].getCellType());
  				}
  				System.out.println();
  			}
  			System.out.println("new surrounding");
  			for (int i=0; i<surrounding.length;i++)
  			{
  				for (int j=0;j<surrounding[0].length;j++)
  				{
  					System.out.print(newSurrounding[i][j].getCellType());
  				}
  				System.out.println();
  			}
  		}
  		
  		// Calculates energy
  		for (int i=0;i<surrounding.length;i++)
  		{
  			for (int j=0;j<surrounding[0].length;j++)
  			{
  				if (isSwitch)
  				{
  					neighbourType = newSurrounding[i][j].getCellType();
  					totalE += newSurrounding[i][j].getBindEnergies()[neighbourType-1];
  				}	
  				else
  				{
  					neighbourType = surrounding[i][j].getCellType();
  					totalE += surrounding[i][j].getBindEnergies()[neighbourType-1];
  				}
  			}
  		}
  		return totalE;
  	}
	/*public static double energyOfCell(int x, int y, double[] bindEnergies)
	{
		double totalE = 0.0; // will contain total energy of cell and surrounding cells
		int neighType = 0; // stores the integer code of the neighbouring cell

		for (int i=0; i<STEP_X.length; i++)
		{
			//if (x == 99 || y == 99 || x == 0 || y == 0) { return; }
			//System.out.println("x: " + x + " y: " + y);
			neighType = FIELD[x + STEP_X[i]][y + STEP_Y[i]].getCellType(); // gets the type of each surrounding cell in turn
			
			if (neighType == 0)
			{
				totalE = 999.0; // boundary cell
			}
			else
			{
				totalE += bindEnergies[neighType-1]; // sums the binding energy of this cell to each surrounding cell in turn
			}
		}
		return totalE;
	}*/
	
	// Double check this to make sure it chooses a valid cell every time (not a border)
	public static int[] chooseRandomCell()
	{
		int[] coord = new int[2];
		coord[0] = rand.nextInt(FIELD_SIZE-1) + 1;
		coord[1] = rand.nextInt(FIELD_SIZE-1) + 1;
		return coord;
	}
	
	public static void swapCells(int x, int y, int swapX, int swapY)
	{
		Cell temp = new Cell(FIELD[x][y].getRGB(),FIELD[x][y].getCellType(),FIELD[x][y].getProportion());
		temp.copyBindEnergies(FIELD[x][y].getBindEnergies());
		
		// swap colour
		FIELD[x][y].setRGB(FIELD[swapX][swapY].getRGB());
		FIELD[swapX][swapY].setRGB(temp.getRGB());
		
		// swap bindingEnergies
		FIELD[x][y].copyBindEnergies(FIELD[swapX][swapY].getBindEnergies());
		FIELD[swapX][swapY].copyBindEnergies(temp.getBindEnergies());
		
		// swap cell type
		FIELD[x][y].setCellType(FIELD[swapX][swapY].getCellType());
		FIELD[swapX][swapY].setCellType(temp.getCellType());
		
		// swap proportion
		FIELD[x][y].setProportion(FIELD[swapX][swapY].getProportion());
		FIELD[swapX][swapY].setProportion(temp.getProportion());
	}
	public static void printMatrix(Cell[][] array)
	{
		for (int i=0;i<array.length;i++)
		{
			for (int j=0;j<array[0].length;j++)
			{
				System.out.print(array[i][j].getCellType());
			}
			System.out.println();
		}
	}
   
  public static void main(String[] args)
  { 
	  initialCellDist();
	  
	  Grid grid = new Grid(FIELD);
	  JFrame frame = new JFrame("Cell Automaton");
	  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  System.out.println("How many cell types?");
	  Scanner sc = new Scanner(System.in);
	  int noTypes = sc.nextInt();
	  setStepArray();
	  setBindEnergies(BIND_ENERGIES,noTypes);
	  int i = 0;
	  boolean loop = true;
	  while (loop)
	  {
		  /*try { Thread.sleep(250); }
		  catch(InterruptedException ie) {}*/
		  int[][] prevField = new int[FIELD_SIZE][FIELD_SIZE];
		  
		  for (int x=0; x<FIELD_SIZE; x++)
		  {
			  for (int y=0; y<FIELD_SIZE; y++)
			  {
				  prevField[x][y] = FIELD[x][y].getCellType();
			  }
		  }
		  /*for (int j=0; j<FIELD_SIZE; j++)
		  {
			  System.out.println(j);
			  for (int k=0; k<FIELD_SIZE; k++)
			  {
				  System.out.print(FIELD[j][k].getCellType() + "  ");
			  }
			  System.out.println();
		  }*/
		  moveCell();
		  
		  /*grid.updateGrid(FIELD);
		  frame.add(grid);
		  frame.setSize(1020,1040);
		  frame.setLocationRelativeTo(null);
		  frame.setVisible(true);*/
		  
		  System.out.println(i);
		  i++;
		  if (i>100000) {loop=false;}
		  

	  }
  }
}