import java.util.*;

public class Cell
{
  // Note: these restrictions apply in other parts of program
  // public static final double finalProp = 1.0;
  public static final int numCellTypes = 10;
  
  // object consisting of 3 ints (R,G,B) and string name
  private RGB colour;
  
  // bindingEnergies will be indexed as follows:
  // thisCell-0, thisCell-1, thisCell-2, thisCell-3, ... thisCell-9
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
  
  // ----------------------
  // GET PROPERTIES OF CELL
  // ----------------------
  
  //gets the colour of the cell
  public RGB getRGB()
  {
    return colour;
  }
  
  //Returns array consisting of binding energies ordered as follows:
  // this.cell-1, this.cell-2, ... this.cell-10
  // Cells that do not exist will be default 0.0
  public double[] getBindEnergies()
  {
	  return this.bindingEnergies;
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
  
  // -------------------------
  // CHANGE PROPERTIES OF CELL
  // -------------------------
  
  // Changes the colour of the cell
  public void setRGB(RGB newColour)
  {
	  this.colour.setR(newColour.getR());
	  this.colour.setG(newColour.getG());
	  this.colour.setB(newColour.getB());
	  this.colour.setColour(newColour.getColour());
  }
  
  // adds binding energy e to cellType "bindsTo", e must be <= 0
  public void changeBindEnergy(Cell bindsTo, double e)
  {
    this.bindingEnergies[bindsTo.cellType] = e;
  }
  
  // Copies over a new array of Binding Energies into the double[] bindingEnergies
  public void copyBindEnergies(double[] newEnergies)
  {
	  for (int i=0; i < this.bindingEnergies.length; i++)
	  {
		  this.bindingEnergies[i] = newEnergies[i];
	  }
  }
  
  // Changes the proportion of this.cell in mix, newProp must be <= 1.0
  public void setProportion(double newProp)
  {
    this.proportion = newProp;
  }
  
  // Changes the value of the int CellType
  public void setCellType(int newType)
  {
	  this.cellType = newType;
  }
  
  // Copies this Cell into newCell
  public void copyCell(Cell newCell)
  {
	  newCell.setRGB(this.getRGB());
	  this.copyBindEnergies(newCell.getBindEnergies());
	  newCell.setProportion(this.getProportion());
	  newCell.setCellType(this.getCellType());
  }

}