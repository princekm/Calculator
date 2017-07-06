package com.newgen.adapters;


import com.newgen.calculator.R;
import com.newgen.helper.Prefs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter<String> extends ArrayAdapter<String> {

	private Context context;
	private String[] values;
	private String[] desc;
	public Adapter(Context context, String[] values,String[] desc) 
	{
		    super(context,R.layout.row, values);
		    this.context = context;
		    this.values = values;
		    this.desc=desc;
	}
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) 
	{
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View row = inflater.inflate(R.layout.row, parent, false);
	    TextView textView = (TextView) row.findViewById(R.id.label);
	    textView.setText(""+values[position]);
		textView.setTextColor(Color.WHITE);
	    String s = values[position];	    
	    TextView descView=(TextView) row.findViewById(R.id.desc);
	    descView.setText(""+desc[position]);
	    return row;
	  }
}
