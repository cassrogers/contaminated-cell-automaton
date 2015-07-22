import java.util.*;

public class Cell
{
  // Note: these restrictions apply in other parts of program
  // public static final double finalProp = 1.0;
  public static final int numCellTypes = 10;
  
  // object consisting of 3 ints (R,G,B) and string name
  private RGB colour;
  
  // bindingEnergies will be indexed as follows:
  // thisCell-1, thisCell-2, thisCell-3, thisCell-4, ... thisCell-10
  private double[] bindingEnergies;
  
  // stores a number 1...10
  private int cellType;
  
  // proportion out of 1.0 of this cell type (must be <= 1.0)
  private double proportion;
  
  // Cell constructor:
  public Cell(RGB theColour, int theCellType, double theProp)
  {
    this.colour = theColour;
    this.bindingEnergies = new double[numCellTypes];   // initialises array of binding energies to all other celltypes
    this.cellType = theCellType;
    this.proportion = theProp;
  }
  
  //gets the colour of the cell
  public RGB getColour()
  {
    return colour;
  }
  
  // adds binding energy e to cellType "bindsTo", e must be <= 0
  public void changeBindEnergy(Cell bindsTo, double e)
  {
    this.bindingEnergies[bindsTo.cellType] = e;
  }
  
  // Returns array consisting of binding energies ordered as follows:
  // this.cell-1, this.cell-2, ... this.cell-10
  // Cells that do not exist will be default 0.0
  public double[] getBindEnergies()
  {
    return this.bindingEnergies;
  }
  
  // Changes the proportion of this.cell in mix, newProp must be <= 1.0
  public void changeProportion(double newProp)
  {
    this.proportion = newProp;
  }
  
  // returns the float proportion of this.cell in mix
  public double getProportion()
  {
    return this.proportion;
  }
  
  // returns the int cellType of this.cell
  public int getCellType()
  {
    return this.cellType;
  }
}