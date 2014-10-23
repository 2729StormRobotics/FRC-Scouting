package com.example.testdb;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		DBHelper db = new DBHelper(this);
		
		//testing 
			//inserting
			db.addCycleTime("winner Anthony");
			db.addCycleTime("winner Pranjal");
			db.addCycleTime("loser Pramod");
			//reading
			String result = db.getAllData();
			//output
			TextView tv = (TextView) this.findViewById(R.id.tv_output);
			tv.setText(result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
