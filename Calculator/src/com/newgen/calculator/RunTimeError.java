package com.newgen.calculator;

public class RunTimeError extends Exception{
	
	public RunTimeError(String s)
	{
		
	}
	public String toString(){
		return "error";		
	}
	public static class ZeroByZero extends Exception{
		public ZeroByZero(String s)
		{
			
		}
		public String toString(){
			return "zerobyzero";		
		}	
		

	}
	public static class DivByZero extends Exception{
		public DivByZero(String s)
		{
			
		}
		public String toString(){
			return "divbyzero";		
		}	
	
	}
}
