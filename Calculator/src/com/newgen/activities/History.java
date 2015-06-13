package com.newgen.activities;

import com.newgen.calculator.R;
import com.newgen.helper.Prefs;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History extends ActionBarActivity {
	private ListView history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		history = (ListView) this.findViewById(R.id.history); 
		String values[]=Prefs.getHistory(this);
		if(values==null){
			values=new String[1];
			values[0]="No History";
			
		}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         android.R.layout.simple_list_item_1, android.R.id.text1, values);
        history.setAdapter(adapter); 
	}
}
