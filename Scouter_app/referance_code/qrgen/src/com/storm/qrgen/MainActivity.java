package com.storm.qrgen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.storm.qrgen.MESSAGE";
	
	private EditText etxt;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//var declarations
		etxt = (EditText) findViewById(R.id.eText);
		tv = (TextView) findViewById(R.id.tv_output);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void convertToQR(View view){
		//receive text to be converted
		String input = etxt.getText().toString();
		
		//display text to be converted
		tv.setText(input + " , " + Environment.DIRECTORY_DOWNLOADS.toString());
		
		//make new activity and pass it the string
		Intent i = new Intent(this, DisplayMessageActivity.class);
		i.putExtra(EXTRA_MESSAGE, input);
		startActivity(i);
		
	}
}
