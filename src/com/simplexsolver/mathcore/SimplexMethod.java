package com.simplexsolver.mathcore;
/* Nathan Vanderleest */
public class SimplexMethod
{
	private int ROWS;
	private int COLUMNS;
	
	private int count;
	private boolean done = false;
    private boolean bound = true;
	
	private double[][] xValues;
    private double[][] num;
//    {
//      {0,  8,   12,  18, 1, 0, 0, 5200},
//      {0, 12,   18,  24, 0, 1, 0, 6000},
//      {0,  4,    8,  12, 0, 0, 1, 2200},
//      {1, -4, -4.5,  -6, 0, 0, 0,    0}
//    };
    
    public boolean bound()
    {
    	return bound;
    }
    
    public boolean done()
    {
    	return done;
    }
    
      public SimplexMethod()
      {
    	ROWS = 3;
    	COLUMNS = 6;
    	num = new double[ROWS][COLUMNS];
    	xValues = new double[COLUMNS - 2 - (ROWS - 1)][2];
    	count = 0;
    	done = false;
    	bound = true;
      }
      
      public SimplexMethod(int r, int c)
      {
      	ROWS = r;
      	COLUMNS = c;
      	num = new double[ROWS][COLUMNS];
      	xValues = new double[COLUMNS - 2 - (ROWS - 1)][2];
      	count = 0;
      	done = false;
      	bound = true;
      }
      
      public double getValueAtIndex(int r, int c)
      {
    	  return num[r][c];
      }
      
      public void setValueAtIndex(int r, int c, double t)
      {
    	  num[r][c] = t;
      }
      
      public double getXValue(int index)
      {
    	  //xValues = new double[COLUMNS - 2 - (ROWS - 1)][2];
    	  return xValues[index][0];
      }
      
      public double getP()
      {
    	  //num = new double[ROWS][COLUMNS];
    	  return num[this.getRows() - 1][this.getColumns()- 1];
      }
      
      public int getRows()
      {
    	  return ROWS;
      }
      
      public void setRows(int r)
      {
    	  System.out.println("this.ROWS = " + r);
    	  ROWS = r;
      }
      
      public int getColumns()
      {
    	  return COLUMNS;
      }
      
      public void setColumns(int c)
      {
    	  System.out.println("this.COLUMNS = " + c);
    	  COLUMNS = c;
      }
      
      public int getCount()
      {
    	  return count;
      }
	
	  public void simplexSolver()
	  { 
	    bound = true;
	    xValues = new double[COLUMNS - 2 - (ROWS - 1)][2];
	    intializeXArray();
	    do
	    {
	    display(num);
	    
	    // find the pivot column
	    int pivotColumn = findPivotColumn(ROWS, COLUMNS, num);
	    done = checkObectiveRow(num);
	    if(done) { break; }
	    
	    // find the pivot row
	    int pivotRow = findPivotRow(num, pivotColumn);
	    done = checkIfUnbound(num, pivotColumn);
	    if(done) { break; }
	    
	    // set pivot element to 1
	    pivotPivotRow(pivotRow, pivotColumn, num);
	    
	    // set other rows to 0
	    pivotFirstPivotRows(pivotRow, pivotColumn, num);
	    pivotSecondPivotRows(pivotRow, pivotColumn, num);
	    done = checkObectiveRow(num);
	    if(done) { break; }
	    
	    display(num);
	    done = checkObectiveRow(num);
	    done = checkIfUnbound(num, pivotColumn);
	    if(done) { break; }
	    }while(!done);
	    display(num);
	    
	    // finish part
	    System.out.println("Program done!");
	  }
	  
	  private void pivotSecondPivotRows(int pr, int pc, double[][] n)
	  {
		System.out.println("After Pivot: ");
	    for(int i = pr + 1; i < ROWS; i++)
	    {
	      double R = (double) n[i][pc];
	      for(int j = 0; j < COLUMNS; j++)
	      {
	        n[i][j] = (double) ((double) n[pr][j] * (double) (-R)) + (double) n[i][j];
	      }
	    }
	    count++;
	  }
	  
	  private void pivotFirstPivotRows(int pr, int pc, double[][] n)
	  {
	    for(int i = 0; i < pr; i++)
	    {
	      double R = (double) n[i][pc];
	      for(int j = 0; j < COLUMNS; j++)
	      {
	        n[i][j] = (double) ((double)n[pr][j] * (double) (-R)) + (double) n[i][j];
	      }
	    }
	  }
	  
	  private void pivotPivotRow(int pr, int pc, double[][] n)
	  {
		  boolean equalsOne = n[pr][pc] == 1;
		  if(!equalsOne)
		  {
	    double R = (double) n[pr][pc];
	    for(int i = 0; i < COLUMNS; i++)
	    {
	      n[pr][i] = (double)((double)n[pr][i] / (double)R);
	    }
		  }
	  }
	  
	  private int findPivotColumn(int r, int c, double[][] n)
	  {
	    int pColumn = c - 1;
	    int i = -1;
	    do {
	       i++;
	       if(n[r - 1][i] < 0)
	        {
	        pColumn = i;
	        System.out.println("Pivot Value in objective row: " + n[r - 1][i]);
	        }
	      }while(n[r - 1][i] >= 0 && i < c - 1);
	    System.out.println("Pivot Column: " + pColumn);
	        return pColumn;
	  }
	  
	  private boolean checkObectiveRow(double[][] n)
	  {
	    boolean d = false;
	    for(int i = 0; i < COLUMNS; i++)
	    {
	    	 if(n[ROWS - 1][i] < 0)
	 	        {
	 	        d = false;
	 	        break;
	 	        }
	 	       else
	 	       {
	 	    	   d = true;
	 	       }
	    }
	        return d;
	  }
	  
	  private boolean checkIfUnbound(double[][] n, int pc)
	  {
		  boolean d = false;
		  for(int i = 0; i < ROWS - 1; i++)
		  {	
		    if( n[i][pc] > 0 )
		    {
		    	bound = true;
		    	d = false;
		    	break;
		    }
		    else
		    {
		    	d = true;
		    	bound = false;
		    }
		  }
		  return d;
	  }
	  
	  private int findPivotRow(double[][] n, int pc)
	  {
	    int pRow = 0;
	    double test = 1000000.0;
	    
	    for(int i = 0; i < ROWS - 1; i++)
	    {
	    	
	    if( n[i][pc] > 0 && test > ((double)n[i][COLUMNS - 1] / (double)n[i][pc]) )
	      {
	        test = (double)((double)n[i][COLUMNS - 1] / (double)n[i][pc]);
	        pRow = i;
	      }
	    }
	    System.out.println("Pivot Row: " + pRow);
	    System.out.println("Pivot element: " + n[pRow][pc]);
	    checkForMultiplePivotRows(pRow, pc);
	    //xValues[pc - 1][1] = (double) pRow;
	    return pRow;
	  }
	  
	  private void checkForMultiplePivotRows(int pr, int pc)
	  {
		  for(int i = 0; i < COLUMNS - 2 - (ROWS - 1); i++)
		  {
			  if(xValues[i][1] == pr)
			  {
				  xValues[i][1] = -1;
			  }
				  xValues[pc - 1][1] = (double) pr;
		  }
	  }
	  
	  private void intializeXArray()
	  {
		  for(int i = 0; i < COLUMNS - 2 - (ROWS - 1); i++)
		  {
			  xValues[i][1] = -1;
		  }
	  }
	  
	  private void display(double[][] n)
	  {
	    // display the array
		System.out.println("Display: ");
	    for(int i = 0; i < ROWS; i++)
	    {
	      for(int j = 0; j < COLUMNS; j++)
	      {
	        System.out.printf( "%.2f \t", n[i][j]);
	      }
	      System.out.println();
	    }
	    System.out.println("Number of pivots: " + count);
	    if(!bound)
	      {
	    	  System.out.println("This maxamim problem is Unbound.");
	    	  System.out.println("There is No Solution.");
	      }
	    else if(done)
	    {
		    System.out.println("P = " + num[ROWS - 1][COLUMNS - 1] + ", at:");
		    for(int i = 0; i < COLUMNS - 2 - (ROWS - 1); i++)
		    {
		    	int j = (int) xValues[i][1];
		    	if(j < 0) { xValues[i][0] = 0; }
		    	else { xValues[i][0] = n[j][COLUMNS - 1]; }
		    	System.out.println("X" + (i+1) + " = " + xValues[i][0]);
		    }
	    }
	  }
	}