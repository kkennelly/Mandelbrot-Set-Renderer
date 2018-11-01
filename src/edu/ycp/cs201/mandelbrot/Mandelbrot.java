package edu.ycp.cs201.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Mandelbrot {
	public static final int HEIGHT = 600;

	public static final int WIDTH = 600;

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		/*System.out.println("Please enter coordinates of region to render:");
		System.out.print("  x1: ");
		double x1 = keyboard.nextDouble();
		System.out.print("  y1: ");
		double y1 = keyboard.nextDouble();
		System.out.print("  x2: ");
		double x2 = keyboard.nextDouble();
		System.out.print("  y2: ");
		double y2 = keyboard.nextDouble();
*/
		//System.out.print("Output filename: ");
		//String fileName = keyboard.next();
		
		double x1 = -1.286667;
		double y1 = -0.413333;
		double x2 = -1.066667;
		double y2 = -0.193333;
		
		String fileName = "output.png";
		
		int [][] iterCounts = new int[HEIGHT][WIDTH];
		
		double yDiff = Math.abs(y2 - y1) / 2;
		
		if(y1 > y2)
		{
			double temp = y1;
			y1 = y2;
			y2 = temp;
		}
		
		MandelbrotTask task0 = new MandelbrotTask(x1, y1, x2, y1 + yDiff, 0, WIDTH, 0, HEIGHT / 2, iterCounts);
		MandelbrotTask task1 = new MandelbrotTask(x1, y1, x2, y2 - yDiff, 0, WIDTH, HEIGHT / 2, HEIGHT, iterCounts);
		
		Thread thread0 = new Thread(task0);
		Thread thread1 = new Thread(task1);
		
		System.out.println("Working...");
	
		//start the tasks
		thread0.run();
		thread1.run();
		
		try
		{
			//wait for the tasks to finish computation
			thread0.join();
			thread1.join();
	
			BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.getGraphics();
			
			// use g to perform drawing operations
			for(int row = 0; row < HEIGHT; row++)
			{				
				for(int col = 0; col < WIDTH; col++)
				{					
					g.setColor(getColor(iterCounts[row][col]));
					g.fillRect(col, row, 1, 1);
				}
			}
		
			try
			{
				//output image to file
				OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
				
				try
				{
					ImageIO.write(bufferedImage, "PNG", os);
				}
				finally
				{
					os.close();
				}
				
				g.dispose();
				
				System.out.println("Output file was written successfully!");
			}
			catch (Exception FileNotFoundException )
			{
				System.out.println("FileNotFoundException. Please create file and try again");
			}
			
		}
		catch(Exception InterruptionException)
		{
			System.out.println("ERROR: InterruptionException");
		}
		

		keyboard.close();
	}

	/*
	 * @param integer
	 * @return the Color associated with the integer parameter
	 */
	private static Color getColor(double value)
	{
		/*
		 * convert 0-999 to 0-90 for sine and cos(red and blue) and  for green
		 */
		if(value == 1000)
			return Color.BLACK;
		
		int blue = (int)(Math.abs(Math.cos(value / 999 * 6.2831 )) * 255.0);
		int red = (int)(Math.abs(Math.sin(value / 999 * 6.2831)) * 255.0);
		int green = (int)(value / 999 * 255.0);
				
		return new Color(red, green, blue);
	}
}


