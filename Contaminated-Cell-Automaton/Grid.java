import java.awt.*;
import javax.swing.JPanel;

import java.util.*;

public class Grid extends JPanel
{
    private static final int MAX_TYPES = 10;
	private static final int FIELD_SIZE = 50;
	private Cell[][] cells;
	//private Cell[] cellTypes;
	Random r = new Random();
	Color mag = new Color(255,0,255);
	Color yel = new Color(255,255,0);
	Color cyan = new Color(0,255,255);
	Color oran = new Color(255,175,50);
	Color purp = new Color(170,50,255);
	Color pink = new Color(255,50,200);
	Color darkgr = new Color(50,150,100);
	
	/*public static final int[] STEP_X = new int[8]; // used to hold offsets when looking at cell's neighbours
	public static final int[] STEP_Y = new int[8]; // used to hold offsets when looking at cell's neighbours
	public static final double[][] DIFF_NEIGH = new double[9][4];*/
	  
	public Grid(Cell[][] theCells)
	{
		this.cells = theCells;
		//this.cellTypes = theTypes;
	}
	
	public void updateGrid(Cell[][] newField)
	{
		this.cells = newField;
	}
	
	/*public void testGridUpdateBlue(Graphics g)
	{
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(Color.BLUE);
	}
	
	public void testGridUpdatePink(Graphics g)
	{
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(Color.PINK);
	}*/
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		 
		/*Color mag = new Color(255,0,255);
		Color yel = new Color(255,255,0);
		Color cyan = new Color(0,255,255);
		Color oran = new Color(255,175,50);
		Color purp = new Color(170,50,255);
		Color pink = new Color(255,50,200);
		Color darkgr = new Color(50,150,100);*/
		
		int w = FIELD_SIZE;
		int h = FIELD_SIZE;
		
		
		for (int i=0;i<w;i++)
		{
			for (int j=0;j<h;j++)
			{
				if (isRed(this.cells[i][j]))
				{
					graphics.setColor(Color.red);
				}
				else if (isGreen(this.cells[i][j]))
				{
					graphics.setColor(Color.green);
				}
				else if (isBlue(this.cells[i][j]))
				{
					graphics.setColor(Color.blue);
				}
				else if (isMag(this.cells[i][j]))
				{
					graphics.setColor(mag);
				}
				else if (isYel(this.cells[i][j]))
				{
					graphics.setColor(yel);
				}
				else if (isCyan(this.cells[i][j]))
				{
					graphics.setColor(cyan);
				}
				else if (isOran(this.cells[i][j]))
				{
					graphics.setColor(oran);
				}
				else if (isPurp(this.cells[i][j]))
				{
					graphics.setColor(purp);
				}
				else if (isPink(this.cells[i][j]))
				{
					graphics.setColor(pink);
				}
				else if (isDarkgr(this.cells[i][j]))
				{
					graphics.setColor(darkgr);
				}
				else if (isBlack(this.cells[i][j]))
				{
					graphics.setColor(Color.black);
				}
				graphics.fillOval(i*10,j*10,10,10);
			}
		}
	}
	
	public boolean isRed(Cell cell)
	{
		if (cell.getCellType() == 1)
		{
			return true;
		}
		return false;
	}
	public boolean isGreen(Cell cell)
	{
		if (cell.getCellType() == 2)
		{
			return true;
		}
		return false;
	}
	public boolean isBlue(Cell cell)
	{
		if (cell.getCellType() == 3)
		{
			return true;
		}
		return false;
	}
	public boolean isMag(Cell cell)
	{
		if (cell.getCellType() == 4)
		{
			return true;
		}
		return false;
	}
	public boolean isYel(Cell cell)
	{
		if (cell.getCellType() == 5)
		{
			return true;
		}
		return false;
	}
	public boolean isCyan(Cell cell)
	{
		if (cell.getCellType() == 6)
		{
			return true;
		}
		return false;
	}
	public boolean isOran(Cell cell)
	{
		if (cell.getCellType() == 7)
		{
			return true;
		}
		return false;
	}
	public boolean isPurp(Cell cell)
	{
		if (cell.getCellType() == 8)
		{
			return true;
		}
		return false;
	}
	public boolean isPink(Cell cell)
	{
		if (cell.getCellType() == 9)
		{
			return true;
		}
		return false;
	}
	public boolean isDarkgr(Cell cell)
	{
		if (cell.getCellType() == 10)
		{
			return true;
		}
		return false;
	}
	public boolean isBlack(Cell cell)
	{
		if (cell.getCellType() == 0)
		{
			return true;
		}
		return false;
	}
	
	/*public void setStepArray()
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
	
	public void moveCell(Cell[][] field)
	{
		int diffNeighTot = 0;
		int testX = 0;
		int testY = 0;
		Cell testCell = null;
		double currEnergy = 0.0;
		double swapEnergy = 0.0;
		double cellEnergy = 0.0;
		int neighType = 999;
		
		// chooses a random cell in the grid
		int[] cellCoord = chooseRandomCell(); // gets x and y coordinates of cell
		int x = cellCoord[0];
		int y = cellCoord[1];
		Cell chosenCell = field[x][y]; // stores Cell object at the chosen coordinates
		
		for (int i=0;i<8;i++)
		{
			neighType = field[x + STEP_X[i]][y + STEP_Y[i]].getCellType();
			if (neighType != chosenCell.getCellType())
			{
				diffNeighTot++;
				DIFF_NEIGH[diffNeighTot][0] = i; // makes a list of neighbours that are different
			}
		} // diffNeighTot is total number of different neighbours
		
		if (diffNeighTot > 0)
		{
			cellEnergy = energyOfCell(x,y,chosenCell,field); // gets energy of randomly chosen cell
			
			for (int i=0; i<=diffNeighTot; i++)
			{
				testX = x + STEP_X[(int) DIFF_NEIGH[i][0]]; // gets x coordinate of test cell type
				testY = y + STEP_Y[(int) DIFF_NEIGH[i][0]]; // gets y coordinate of test cell type
				testCell = field[testX][testY];	// gets Cell object of cell at coordinate x,y
				
				currEnergy = energyOfCell(testX,testY,testCell,field) + cellEnergy; // gets energy of test cell + chosen cell energy
				
				DIFF_NEIGH[i][1] = currEnergy; // column 1 stores the energy without swapping
				
				swapEnergy = energyOfCell(x,y,testCell,field); // gets energy if a swap is initiated
				
				DIFF_NEIGH[i][2] = swapEnergy; // column 2 stores the energy with a swap
				DIFF_NEIGH[i][3] = DIFF_NEIGH[i][2] - DIFF_NEIGH[i][1]; // column 3 stores the difference between swap and no swap energies
			}
			
			double bestSwap = 0.0;
			int bestChoice = 0;
			int numEquals = 0;
			int offsetIndex = 0;
			int offset = r.nextInt(9);
			
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
					if (r.nextInt(1) < (1/numEquals))
					{
						bestSwap = DIFF_NEIGH[offsetIndex][4];
					}
				}
			}
			
			if (bestSwap <= 0)
			{
				int swapX = x + STEP_X[(int) DIFF_NEIGH[bestChoice][1]];
				int swapY = y + STEP_Y[(int) DIFF_NEIGH[bestChoice][1]];
				
				if (field[swapX][swapY].getCellType() != 0) // as long as the cell to swap with is not a black border cell
				{
					field[x][y] = field[swapX][swapY];
					field[swapX][swapY] = chosenCell;
				}
			}
		}
	}
	
	// Calculates the energy of each cell using the 8 surrounding cells
	public double energyOfCell(int x, int y, Cell cell, Cell[][] field)
	{
		double totalE = 0.0; // will contain total energy of cell and surrounding cells
		int neighType = 0; // stores the integer code of the neighbouring cell

		for (int i=0; i<STEP_X.length; i++)
		{
			neighType = field[x + STEP_X[i]][y + STEP_Y[i]].getCellType(); // gets the type of each surrounding cell in turn
			
			if (neighType == 0)
			{
				totalE = 999.0; // boundary cell
			}
			else
			{
				totalE += cell.getBindEnergies()[neighType-1]; // sums the binding energy of this cell to each surrounding cell in turn
			}
		}
		return totalE;
	}
	
	public int[] chooseRandomCell()
	{
		int[] coord = new int[2];
		coord[0] = r.nextInt(FIELD_SIZE-1) + 1;
		coord[1] = r.nextInt(FIELD_SIZE-1) + 1;
		return coord;
	}*/
}