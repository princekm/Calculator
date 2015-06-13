package com.newgen.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.newgen.adapters.Adapter;
import com.newgen.calculator.R;
import com.newgen.helper.Prefs;

public class Settings extends ActionBarActivity {
	private ListView listView[];
	private Adapter<String> adapter[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		init();
			}
	private void init(){
		listView = new ListView[2];
		adapter = new Adapter[2];
		listView[0] = (ListView) this.findViewById(R.id.calc);
		listView[1] = (ListView) this.findViewById(R.id.info);
		String info_head[]=this.getResources().getStringArray(R.array.info_head);
		String info_desc[]=this.getResources().getStringArray(R.array.info_desc);
		String calc_head[] =this.getResources().getStringArray(R.array.calc_head);
		String calc_desc[] =this.getResources().getStringArray(R.array.calc_desc);
		adapter[0] = new Adapter<String>(this, calc_head, calc_desc);
		adapter[1] = new Adapter<String>(this, info_head, info_desc);
		listView[0].setAdapter(adapter[0]);
		listView[1].setAdapter(adapter[1]);
		//if(Prefs.getHistoryCount(this)>0)
		
	//		adapter[0].getView(1,null,null).setBackgroundColor(Color.RED);
		OnItemClickListener ol = new OnItemClickListener() {
			@Override
			synchronized public void onItemClick(AdapterView<?> arg0,
					View arg1, int index, long arg3) {
				switch (index) {
				case 0:viewHistory();break;
				case 1:alert();//arg1.setEnabled(false);
				break;
				case 2:Prefs.toggle(Prefs.sound, Settings.this);break;
						
				case 3:Prefs.toggle(Prefs.vibrate, Settings.this);break;
				}
			}
		};
		listView[0].setOnItemClickListener(ol);

	}
	private void viewHistory() {
		if (Prefs.getHistoryCount(this) > 0) {
			Intent intent = new Intent(Settings.this, History.class);
			Settings.this.startActivity(intent);
		} else
			Toast.makeText(Settings.this, "No history.", 2).show();
	}

	public void alert() {
		if (Prefs.getHistoryCount(this) > 0) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setMessage("Are you Sure?");
			alertDialogBuilder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Prefs.clearHistory(Settings.this);
						}
					});
			alertDialogBuilder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		} else
			Toast.makeText(this, "Nothing to clear.", 2).show();

	}
}
