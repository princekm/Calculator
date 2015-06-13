package com.newgen.calculator;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	private EditText display;
	private String expression;
	private Calculator calculator;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        display.setText(""+display.getSelectionStart());
        display.setSelection(1);

    }
    private void init(){
    	display=(EditText)findViewById(R.id.display);
    	display.setOnClickListener(new OnClickListener() {
    		
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE); 
				 imm.hideSoftInputFromWindow(display.getWindowToken(), 0); 	
				 display.clearFocus();//for disabling further menus
			}
	   
  });
        	display.setLongClickable(false);
        	expression="";
        	calculator=new Calculator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getKey(View view){
    	String text=((Button)view).getText().toString();
    	int position=display.getSelectionStart();
        if(isPrintable(text)){
        	boolean flag=false;
        	StringBuilder str=new StringBuilder(display.getText().toString());
        	if(position==str.length())
        		flag=true;
      		if(!(str.toString().equals("0")&&text.equals("0")))
      		{
      			if(str.toString().equals("0")&&!calculator.isOperator(text)){
      				if(!text.equals(".")){
      				str=new StringBuilder("");
      				position=0;
      				}
      			}
      			Log.d("pos","getkey:"+position);
      			str.insert(position,text);
      			
      			display.setText(str);
      			if(flag)
      				display.setSelection((position=display.getText().toString().length()));
      			else
      				display.setSelection(position);
      		}
    	}
        else if(isClearKey(text))
        {
        	display.setText("0");
        	display.setSelection(1);
        }
        else if(isDelKey(text)){
        	String str=new String(display.getText().toString());
        	if(str.equals("NaN")||str.equals("Error")){
        		display.setText("0");
        		display.setSelection(1);
        	}else{
        	boolean flag=false;
         	
         	if(position==str.length())
        		flag=true;
      		if(!str.toString().equals("0")&&position>=1){
      			str=str.substring(0, position-1) + str.substring(position);
      			if(str.equals(""))
      				str="0";
      			display.setText(str);
      			expression=str.toString();
      			if(flag)
      				display.setSelection((position=display.getText().toString().length()));
      			else
      				display.setSelection(position);
      		}
        	}
      		        	
        }
        else if(isEqKey(text)){
        	String infix=display.getText().toString();
        	Stack stack=calculator.toPostFix(calculator.separate(infix));
        	Stack postfix=new Stack();
        	while(!stack.empty()){
        		postfix.push(stack.pop());
        	}
        	String result;
        	try{
        		 result=calculator.evaluate(postfix);
        	}
        	catch(RunTimeError.DivByZero de){
        		result="∞";
        	}
        	catch(RunTimeError.ZeroByZero ze){
        		result="NaN";
        	}
        	catch(ArithmeticException ae){
        		result="∞";
        		
        	}
        	catch(Exception e)
        	{
        		result="Error";
        	}
        	
        	display.setText(result);
        	display.setSelection(display.getText().toString().length());
        }
        //display.setText(calculator.separate(display.getText().toString()));
        	
        
        
    }
    private boolean isPrintable(String text){
    	if(isEqKey(text)||isDelKey(text)||isClearKey(text))
    		return false;
    	return true;
    }
    private boolean isClearKey(String text){
    	if(text.equals("CE"))
    		return true;
    	return false;
    }
    private boolean isDelKey(String text){
    	if(text.equals("DEL"))
    		return true;
    	return false;
    }
    private boolean isEqKey(String text){
    	if(text.equals("="))
    		return true;
    	return false;
    }
    private boolean isSignKey(String text){
    	if(text.equals("+/-"))
    		return true;
    	return false;
    }

    
}
