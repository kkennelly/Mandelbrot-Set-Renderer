package edu.ycp.cs201.mandelbrot;

public class MandelbrotTask implements Runnable
{
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private int startCol;
	private int endCol;
	private int startRow;
	private int endRow;
	private int [][] iterCounts;
	
	public MandelbrotTask(double x1, double y1, double x2, double y2,
						  int startCol, int endCol, int startRow,
						  int endRow, int [][] iterCounts)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.startCol = startCol;
		this.endCol = endCol;
		this.startRow = startRow;
		this.endRow = endRow;
		this.iterCounts = iterCounts;
	}
	
	@Override
	public void run()
	{
		/*
		 * Puts values into the parts of the matrix starting with the startRow and 
		 * startCol values and ending with endRow and endCol values. The values are dependent on
		 * the position of the array designated with x1, x2, y1, and y2
		 */
		for(int row = startRow; row < endRow; row++)
		{			
			for(int col = startCol; col < endCol; col++)
			{
				Complex c = getComplex(row,col);			
				int iterCount = computeIterCount(c);
				iterCounts[row][col] = iterCount;
			}
		}
	}
	
	/*
	 * @param int row cannot be a negative number or greater than matrix.length
	 * @param int col cannot be a negative number or greater than matrix[0].length
	 * @returns a Complex object representing the complex number in the matrix at (row, col)
	 */
	private Complex getComplex(int row, int col)
	{
		Complex c = new Complex(x1 + col * Math.abs(x2 - x1)/(endCol - startCol), y1 + row * Math.abs(y2 - y1)/(endRow- startRow));
		
		return c;
	}
	
	/*
	 * @param Complex object
	 * @returns the amount of iterations it takes for the complex number to be greater or equal to 2 with the max
	 * being 10000
	 */
	private int computeIterCount(Complex com)
	{
		Complex z = new Complex(0,0);
		
		for(int i = 0; i < 1000; i++)
		{
			z = z.multiply(z).add(com);
			
			if(z.getMagnitude() >= 2.0)
				return i;
		}
		
		return 1000;
	}
}
