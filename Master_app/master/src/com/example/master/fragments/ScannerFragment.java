package com.example.master.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.master.MainActivity;
import com.example.master.R;


@SuppressLint("NewApi")
public class ScannerFragment extends Fragment {

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	     // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.activity_scanner_fragment, container, false);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 
		 //gets activity and handler
		 Activity activity = this.getActivity();
		 

	       //displaying what is in tempStorage
	       TextView team1 = (TextView) activity.findViewById(R.id.tv_team1);
	       if (MainActivity.tempStorage[0].indexOf(",") > 0)
	       		team1.setText(MainActivity.tempStorage[0].substring(0, MainActivity.tempStorage[0].indexOf(",")));
	       else	
	    	   team1.setText("");
	       TextView team2 = (TextView) activity.findViewById(R.id.tv_team2);
	       if (MainActivity.tempStorage[1].indexOf(",") > 0)
	       		team2.setText(MainActivity.tempStorage[1].substring(0, MainActivity.tempStorage[1].indexOf(",")));
	       else	
	    	   	team2.setText("");
	       TextView team3 = (TextView) activity.findViewById(R.id.tv_team3);
	       if (MainActivity.tempStorage[2].indexOf(",") > 0)
	       		team3.setText(MainActivity.tempStorage[2].substring(0, MainActivity.tempStorage[2].indexOf(",")));
	       else	
	    	   	team3.setText("");
	       TextView team4 = (TextView) activity.findViewById(R.id.tv_team4);
	       if (MainActivity.tempStorage[3].indexOf(",") > 0)
	       		team4.setText(MainActivity.tempStorage[3].substring(0, MainActivity.tempStorage[3].indexOf(",")));
	       else	
	    	   	team4.setText("");
	       TextView team5 = (TextView) activity.findViewById(R.id.tv_team5);
	       if (MainActivity.tempStorage[4].indexOf(",") > 0)
	       		team5.setText(MainActivity.tempStorage[4].substring(0, MainActivity.tempStorage[4].indexOf(",")));
	       else	
	    	   	team5.setText("");
	       TextView team6 = (TextView) activity.findViewById(R.id.tv_team6);
	       if (MainActivity.tempStorage[5].indexOf(",") > 0)
	       		team6.setText(MainActivity.tempStorage[5].substring(0, MainActivity.tempStorage[5].indexOf(",")));
	       else	
	    	   	team6.setText("");
	 }
}
