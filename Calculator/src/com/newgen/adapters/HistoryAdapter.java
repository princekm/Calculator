package com.newgen.adapters;


import com.newgen.calculator.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoryAdapter<String> extends ArrayAdapter<String> {

	private Context context;
	private String[] values;
	public HistoryAdapter(Context context, String[] values) 
	{
		    super(context,R.layout.row, values);
		    this.context = context;
		    this.values = values;
	}
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) 
	{
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View row = inflater.inflate(R.layout.list, parent, false);
	    TextView textView = (TextView) row.findViewById(R.id.entry);
	    textView.setText(""+values[position]);
	    String s = values[position];	    
	    return row;
	  }
}
