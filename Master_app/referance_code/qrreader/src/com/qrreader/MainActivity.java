package com.qrreader;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

public class MainActivity extends Activity implements OnClickListener{

	public static final String RESULT_TEXT = "resultText";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.button_scan);
		button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
	    if(resultCode == RESULT_OK){
	       TextView result = (TextView) findViewById(R.id.tv_result);
	       result.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
	    	
	    }else if(resultCode == RESULT_CANCELED){
	        Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
	    }
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(this, ZBarScannerActivity.class);
		intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
		startActivityForResult(intent, 0);
	}


}
