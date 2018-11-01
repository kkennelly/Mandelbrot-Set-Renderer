package edu.ycp.cs201.mandelbrot;

import java.lang.Math;

public class Complex 
{
	private double real;
	private double imaginary;
	
	//a complex number is made up of a real and imaginary combination in the form 
	//a + bi when a = real and bi = imaginary
	public Complex(double real, double imag)
	{
		this.real = real;
		this.imaginary = imag;
	}
	
	/*
	 * @return Complex object representing the sum of both complex numbers 
	 */
	public Complex add(Complex other)
	{
		return new Complex(other.real + real, other.imaginary + imaginary);
	}
	
	/*
	 * @return Complex object representing the product of both complex numbers 
	 */
	public Complex multiply(Complex other)
	{
		double first = (other.real * real) - (imaginary * other.imaginary);
		double second = (real * other.imaginary) + (other.real * imaginary);
		
		return new Complex(first, second);
	}
	
	/*
	 * @return double representing the magnitude of the Complex object
	 */
	public double getMagnitude()
	{
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	/*
	 * @return String that represents the Complex number in "a + bi" form
	 */
	public String toString()
	{
		return "" + real + " + " + imaginary + "i";
	}
}
