import java.util.*;

public class RGB
{
  private int r;
  private int g;
  private int b;
  private String colour;
  
  // Colour constructor
  public RGB(int thisR, int thisG, int thisB, String thisColour)
  {
    this.r = thisR;
    this.g = thisG;
    this.b = thisB;
    this.colour = thisColour;
  }
  
  // Returns r value of this colour (0-255)
  public int getR()
  {
    return r;
  }
  
  // Returns g value of this colour (0-255)
  public int getG()
  {
    return g;
  }
  
  // Returns b value of this colour (0-255)
  public int getB()
  {
    return b;
  }
  
  public void setR(int newR)
  {
	  this.r = newR;
  }
  
  public void setG(int newG)
  {
	  this.g = newG;
  }
  
  public void setB(int newB)
  {
	  this.b = newB;
  }
  
  public void setColour(String newColour)
  {
	  this.colour = newColour;
  }
  
  // Returns name of this Colour
  public String getColour()
  {
    return colour;
  }
  
}