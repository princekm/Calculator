package com.newgen.activities;

import java.math.BigDecimal;
import java.util.Stack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.newgen.calculator.R;
import com.newgen.helper.Calculator;
import com.newgen.helper.Prefs;
import com.newgen.helper.RunTimeError;

public class MainActivity extends ActionBarActivity {

	private EditText display;// calculator display
	private Calculator calculator;
	private Typeface font;
	private Button[] button;
	private String expression;

	// public static final String MyPREFERENCES = "MyPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		display.setText("" + display.getSelectionStart());
		display.setSelection(1);
		Prefs.ResetHistoryCount(this);
		Prefs.ResetSettings(this);
	}

	private void init() {
		display = (EditText) findViewById(R.id.display);
		display.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(display.getWindowToken(), 0);
				display.clearFocus();// for disabling further menus
			}

		});
		display.setLongClickable(false);
		calculator = new Calculator();
		font = Typeface.createFromAsset(getAssets(), "fonts/lcd.ttf");
		display.setTypeface(font);
		button = new Button[18];
		int ids[] = { R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6,
				R.id.b7, R.id.b8, R.id.b9, R.id.b10, R.id.b11, R.id.b12,
				R.id.b13, R.id.b14, R.id.b15, R.id.b16, R.id.b17, R.id.b18 };
		for (int i = 0; i < 18; ++i) {
			button[i] = (Button) findViewById(ids[i]);
			button[i].setTypeface(font);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, Settings.class);
			this.startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getKey(View view) {
		if (Prefs.getValue(Prefs.sound, this).equals("on"))
			this.playsound();
		if (Prefs.getValue(Prefs.vibrate, this).equals("on"))
			vibrate();
		String text = ((Button) view).getText().toString();
		int position = display.getSelectionStart();
		if (isPrintable(text)) {
			boolean flag = false;
			StringBuilder str = new StringBuilder(display.getText().toString());
			if (str.toString().equals("Error") || str.toString().equals("NaN")) {
				str = new StringBuilder("0");
				flag = true;
			} else if (position == str.length())
				flag = true;
			if (!(str.toString().equals("0") && text.equals("0"))) {
				if (str.toString().equals("0") && !calculator.isOperator(text)) {
					if (!text.equals(".")) {
						str = new StringBuilder("");
						position = 0;
					}
				}
				Log.d("pos", "getkey:" + position);
				str.insert(position, text);
				display.setText(str);
				if (flag)
					display.setSelection((position = display.getText()
							.toString().length()));
				else
					display.setSelection(position);
			}
		} else if (isClearKey(text)) {
			display.setText("0");
			display.setSelection(1);
		} else if (isDelKey(text)) {
			String str = new String(display.getText().toString());
			if (str.equals("NaN") || str.equals("Error")) {
				display.setText("0");
				display.setSelection(1);
			} else {
				boolean flag = false;
				if (position == str.length())
					flag = true;
				if (!str.toString().equals("0") && position >= 1) {
					str = str.substring(0, position - 1)
							+ str.substring(position);
					if (str.equals(""))
						str = "0";
					display.setText(str);
					if (flag)
						display.setSelection((position = display.getText()
								.toString().length()));
					else
						display.setSelection(position);
				}
			}

		} else if (isEqKey(text)) {
			String infix = display.getText().toString();
			expression = infix;
			Stack stack = calculator.toPostFix(calculator.separate(infix));
			Stack postfix = new Stack();
			while (!stack.empty())
				postfix.push(stack.pop());
			String result;
			try {
				result = calculator.evaluate(postfix);
				BigDecimal n = new BigDecimal(result);
				expression += "=" + result;
				Prefs.store(expression, this);
			} catch (RunTimeError.DivByZero de) {
				result = "∞";
				Prefs.store(expression, this);
			} catch (RunTimeError.ZeroByZero ze) {
				result = "NaN";
			} catch (ArithmeticException ae) {
				result = "∞";
				Prefs.store(expression, this);
			} catch (Exception e) {
				result = "Error";
			}
			if (result.equals(""))
				result = "0";
			Animation anim = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.bounce);
			display.startAnimation(anim);
			display.setText(result);
			display.setSelection(display.getText().toString().length());
		}

	}

	private boolean isPrintable(String text) {
		if (isEqKey(text) || isDelKey(text) || isClearKey(text))
			return false;
		return true;
	}

	private boolean isClearKey(String text) {
		if (text.equals("CE"))
			return true;
		return false;
	}

	private boolean isDelKey(String text) {
		if (text.equals("DEL"))
			return true;
		return false;
	}

	private boolean isEqKey(String text) {
		if (text.equals("="))
			return true;
		return false;
	}

	private void playsound() {
		try {
			MediaPlayer mp;
			mp = MediaPlayer.create(this, R.raw.click);
			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp.reset();
					mp.release();
					mp = null;
				}

			});
			mp.start();
		} catch (Exception e) {

		}
	}

	private void vibrate() {
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
	}

}
