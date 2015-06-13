package com.newgen.calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import com.newgen.calculator.RunTimeError.ZeroByZero;

public class Calculator {
	public String separate(String infix){
    	String modInfix="";
    	String prev="";
    	int i=0;
    	while(i<infix.length()){
    		if(isOperator(""+infix.charAt(i))){
    			if(!isOperator(prev)){
    				modInfix+=","+infix.charAt(i)+",";                                        
    				prev=""+infix.charAt(i);
    		System.out.print("("+infix.charAt(i)+")");
                        }
                        else{
                            System.out.print("("+infix.charAt(i)+")");
                            modInfix+=infix.charAt(i);
                            prev="";
                        }
                }
    			else
    			{    				
    				modInfix+=infix.charAt(i);
                                prev="";
    			}
    		
    		i++;
    	}
    	return modInfix;
    }
    public Stack toPostFix(String infix)
  {
      infix=infix+",)";
  	Stack numbers,symbols;
  	numbers=new Stack();
  	symbols=new Stack();
  	int len=infix.split(",").length;
      symbols.push("(");
  	if(len>0){
  		String arr[]=infix.split(",");
  		int i=0;
  		while(i<arr.length){
                  if(!isOperator(arr[i].trim())){                 
  			numbers.push(arr[i++]);
                  }
                  else if(arr[i].equals(")")){
                      String pop;
                      while(!(pop=symbols.pop().toString()).equals("("))
                              numbers.push(pop);                        
                         i++;
                  }
                  else{
                      char incomer=arr[i].charAt(0);
                      char instack=symbols.peek().toString().charAt(0);
                      if(icp(incomer)>isp(instack)){
                          symbols.push(""+incomer);                    
                          i++;                               
                      }
                      else{
                          //System.out.print(""+arr[i]);
                          String s=(String)symbols.pop();
                          
                          numbers.push(s);                                    
                      }
                  }
  			
  		}
  	}
  	return numbers;
  }
  public boolean isOperator(String op){
  	if(op.equals("+")||op.equals("-")||op.equals("/")||op.equals("x")||op.equals(")")||op.equals("("))
  		return true;
  	return false;
  }
  public String evaluate(Stack postfix) throws RunTimeError.ZeroByZero,RunTimeError.DivByZero{
      Stack answer=new Stack();
      String s;
      BigDecimal a,b,c;
      while(!postfix.empty()){
          s=(String)postfix.pop();
          if(!isOperator(s))
              answer.push(s);
          else{
              a=new BigDecimal((String)answer.pop());
              b=new BigDecimal((String)answer.pop());
              a.setScale(14);
              b.setScale(14);
              if(a.equals(new BigDecimal("0"))&&b.equals(new BigDecimal("0")))
            	  throw new RunTimeError.ZeroByZero("");
              else if(a.equals(new BigDecimal("0")))
            	  throw new RunTimeError.DivByZero("");
              c=operate(b,a,s.charAt(0));
              System.out.print(b+" "+s.charAt(0)+" "+a+"="+c+"\n");
              answer.push(""+c);
          }
          
      }
      String res=answer.pop().toString();
      return stripZeros(res);
  }
  private String stripZeros(String res)
  {
	  if(res.contains(".")){		  
		  while(res.endsWith("0")||res.endsWith(".")){
			  int len=res.length();
			  res=res.substring(0,len-1);
		  }
		  
	  }
	  return res;
  }
  private BigDecimal operate(BigDecimal a,BigDecimal b,char op){
      switch(op){
          case '+':return a.add(b);
          case '-':return a.subtract(b);
          case '/':return a.divide(b,14, RoundingMode.DOWN);
          case 'x':return a.multiply(b).setScale(14);    
          default:return null;    
      }
  }
  private int isp(char c){
  	switch(c){
  		case '(':return -1;
  		case '+':return 0;
  		case '-':return 0;
  		case 'x':return 1;
  		case '/':return 1;
  		default:return -2;
  	}
  }
  private int icp(char c){
  	switch(c){
  		case '(':return 3;
  		default:return isp(c);
  	}
  }


}
