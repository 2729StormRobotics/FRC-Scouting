package com.scouter.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scouter.R;
import com.scouter.util.DataHandler;


@SuppressLint("NewApi")
public class AutoFragment extends Fragment {

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	     // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.auto_fragment, container, false);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 
		 //gets activity and handler
		 Activity activity = this.getActivity();
		 
		 //finds buttons to update
		 Button btn_top = (Button)activity.findViewById(R.id.btn_top),
				btn_topHot = (Button)activity.findViewById(R.id.btn_topHot),
				btn_topMiss = (Button)activity.findViewById(R.id.btn_topMiss),
				btn_bottom = (Button)activity.findViewById(R.id.btn_bottom),
				btn_botHot = (Button)activity.findViewById(R.id.btn_bottomHot);
			
		 //updates all the button text
		 btn_top.setText("Top: "+DataHandler.getTopGoalsAuto());
		 btn_topHot.setText("Top Hot: "+DataHandler.getTopHotGoals());
		 btn_bottom.setText("Bottom: "+DataHandler.getBotGoalsAuto());
		 btn_botHot.setText("Bot. Hot: "+DataHandler.getBotHotGoals());
		 btn_topMiss.setText("Miss: "+DataHandler.getTopGoalMissesAuto());
		 
	 }
}
