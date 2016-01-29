import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class buildGrid extends JFrame{
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
	  
	static Cell red1 = new Cell(red,1,0.33333);
	  static Cell green2 = new Cell(green,2,0.33333);
	  static Cell blue3 = new Cell(blue,3,0.33334);
	  static Cell mag4 = new Cell(mag,4,0.0);
	  static Cell yel5 = new Cell(yel,5,0.0);
	  static Cell cyan6 = new Cell(cyan,6,0.0);
	  static Cell oran7 = new Cell(oran,7,0.0);
	  static Cell purp8 = new Cell(purp,8,0.0);
	  static Cell pink9 = new Cell(pink,9,0.0);
	  static Cell darkgr10 = new Cell(darkgr,10,0.0);
	  static Cell border = new Cell(black,0,0.0);
	  
	  static double redProp = 0.33333;
	  static double greenProp = 0.33333;
	  static double blueProp = 0.33334;
	  static double magProp = 0.0;
	  static double yelProp = 0.0;
	  static double cyanProp = 0.0;
	  static double oranProp = 0.0;
	  static double purpProp = 0.0;
	  static double pinkProp = 0.0;
	  static double darkgrProp = 0.0;
	  
	public static final int FIELD_SIZE = 50;
	static final Cell[] CELL_ARR = new Cell[]{red1,green2,blue3,mag4,yel5,cyan6,oran7,purp8,pink9,darkgr10};
	public static Cell[][] FIELD = new Cell[50][50];
	
	 public static void initialCellDist()
	  {
	    // Generates a stream of random numbers for placing cells
	    Random r = new Random(5);
	    
	    // Copies array of proportions into cumulative Proportion array
	    double[] cumProp = new double[10];
	    for (int i=0; i<10; i++)
	    {
	    	cumProp[i] = CELL_ARR[i].getProportion();
	    	System.out.println(cumProp[i]);
	    }
	    // Creates running total in cumulative proportion array for placing cells
	    for (int i=1; i<10; i++)
	    {
	      cumProp[i] = cumProp[i] + cumProp[i-1];
	    }
	    
	    // loops through all locations in field
	    for (int x=0;x<50;x++)
	    {
	      for (int y=0;y<50;y++)
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
	        if (x==0 || x == 49 || y == 0 || y == 49)
		    {
		          FIELD[x][y] = new Cell (black,0,0.0); // sets borders to be black "cells"
		    }
	      }
	    }
	  }
	 public static void main(String[] args)
	 {
		 initialCellDist();
		  Grid grid = new Grid(FIELD);
		  JFrame frame = new JFrame("Cell Automaton");
		  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  frame.add(grid);
		  frame.setSize(1020,1040);
		  frame.setLocationRelativeTo(null);
		  frame.setVisible(true);
	 }
}
