package com.newgen.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.newgen.activities.MainActivity;
import com.newgen.activities.Settings;

public class Prefs {
	public static String sound="SOUND";
	public static String vibrate="VIBRATE";
	public static String key="KEY";
	public static String count="count";
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static void clearHistory(Context context){
				SharedPreferences shpref = context.getSharedPreferences(
				MyPREFERENCES, Context.MODE_PRIVATE);
				String sound=shpref.getString(Prefs.sound,"");
				String vibrate=shpref.getString(Prefs.vibrate,"");
				Editor editor = shpref.edit();
				editor.clear();
				editor.putString(Prefs.count, "0");
				editor.putString(Prefs.sound, sound);
				editor.putString(Prefs.vibrate, vibrate);
				editor.commit();
				Toast.makeText(context, "History cleared.", 2).show();
	}
	public static  String getValue(String key,Context context){
		SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		return sharedpreferences.getString(key,"");
	}
	public static int getHistoryCount(Context context) {
		SharedPreferences sharedpreferences = context.getSharedPreferences(
				Prefs.MyPREFERENCES, Context.MODE_PRIVATE);
		int count = Integer.parseInt(sharedpreferences.getString("count", ""));
		return count;
	}
	public static void toggle(String key,Context context){
		SharedPreferences sharedpreferences = context.getSharedPreferences(
		Prefs.MyPREFERENCES, Context.MODE_PRIVATE);
		String sound=sharedpreferences.getString(key,"");
		Editor editor = sharedpreferences.edit();
		if(sound.equals("off"))
			sound="on";
		else if(sound.equals("on"))
			sound="off";
		Toast.makeText(context, "Turned "+sound, 2).show();
		editor.putString(key,sound);	
		editor.commit();
	}
	  public static void store(String value,Context context){
	    	SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	    	Editor editor = sharedpreferences.edit();
	    	int count=Integer.parseInt(sharedpreferences.getString("count",""));   	
	    	editor.putString(key+count,value);
	    	editor.putString(Prefs.count,""+(count+1));
	    	editor.commit();
	    }
	  public static void ResetHistoryCount(Context context){
	    	SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	    	Editor editor = sharedpreferences.edit();
	    	if(!sharedpreferences.contains("count")){
	    		editor.putString(Prefs.count,"0");
	    		editor.commit();
	    	}
	    }
	    public static String[] getHistory(Context context){
	    	SharedPreferences sharedpreferences = context.getSharedPreferences(Prefs.MyPREFERENCES, Context.MODE_PRIVATE);
	    	int count=Integer.parseInt(sharedpreferences.getString("count",""));
	    	String s[]=new String[count];
	    	int i=0;
	    	while(count>0){
	    		s[i++]=sharedpreferences.getString(Prefs.key+(count-1),"");
	    		count--;
	    	}
	    	return s;
}
	  public static void ResetSettings(Context context){
	    	SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	    	Editor editor = sharedpreferences.edit();
	    	if(!sharedpreferences.contains(sound)){
	    		editor.putString(sound,"off");
	    		editor.commit();
	    	}
	    	if(!sharedpreferences.contains(vibrate)){
	    		editor.putString(vibrate,"off");
	    		editor.commit();
	    	}
	    }

}
