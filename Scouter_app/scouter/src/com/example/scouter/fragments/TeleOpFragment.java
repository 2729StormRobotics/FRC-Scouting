package com.example.scouter.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.scouter.DataHandler;
import com.example.scouter.R;

@SuppressLint("NewApi")
public class TeleOpFragment extends Fragment{
	
	public TeleOpFragment() {
		super();
	}
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 // Inflate the layout for this fragment
	     return inflater.inflate(R.layout.teleop_fragment, container, false);
	 }
	 
	 @Override
	 public void onResume() {
		super.onResume();
		
		//gets the activity and other stuff
		Activity activity = this.getActivity();
		boolean isRed = DataHandler.isRed();
		
		//finds all the text views to update
		TextView tv_fTitle = (TextView)activity.findViewById((isRed)?R.id.tv_redzone_title:R.id.tv_bluezone_title),
				 tv_gTitle = (TextView)activity.findViewById((!isRed)?R.id.tv_redzone_title:R.id.tv_bluezone_title),
				 
				 tv_fThrow = (TextView)activity.findViewById((isRed)?R.id.tv_redzoneThrownAssist:R.id.tv_bluezoneThrownAssist),
				 tv_fCatch = (TextView)activity.findViewById((isRed)?R.id.tv_redzoneCaughtAssist:R.id.tv_bluezoneCaughtAssist),
				 
				 tv_tThrow = (TextView)activity.findViewById(R.id.tv_tzoneThrownAssist),
				 tv_tCatch = (TextView)activity.findViewById(R.id.tv_tzoneCaughtAssist),
				 
				 tv_gThrow = (TextView)activity.findViewById((!isRed)?R.id.tv_redzoneThrownAssist:R.id.tv_bluezoneThrownAssist),
				 tv_gCatch = (TextView)activity.findViewById((!isRed)?R.id.tv_redzoneCaughtAssist:R.id.tv_bluezoneCaughtAssist),
				 
				 tv_catches = (TextView)activity.findViewById(R.id.tv_TeleCaughtAssist),
				 tv_passes = (TextView)activity.findViewById(R.id.tv_TeleThrownAssist),
				 
				 tv_cycle = (TextView)activity.findViewById(R.id.tv_cycleNum),
				 tv_score = (TextView)activity.findViewById(R.id.tv_scoreNum);
			
		//finds all the buttons to update
		Button	 btn_trussPass = (Button) activity.findViewById(R.id.btn_trussThrow),
				 btn_trussCatch = (Button) activity.findViewById(R.id.btn_trussReceive),
				 btn_trussMiss = (Button) activity.findViewById(R.id.btn_trussMiss),
				 btn_miss = (Button) activity.findViewById(R.id.btn_scoreMiss);
		
		//updates zone titles
		tv_fTitle.setText("Feeder Zone");
		tv_gTitle.setText("Goal Zone");
		
		//////updates zone info//////
		
		//feeder zone
		tv_fThrow.setText("Thrown: " + DataHandler.getThrownAssistsThisCycle(DataHandler.FEEDER_ZONE));
		tv_fCatch.setText("Caught: "+DataHandler.getCaughtAssistsThisCycle(DataHandler.FEEDER_ZONE));
		//truss zone
		tv_tThrow.setText("Thrown: "+DataHandler.getThrownAssistsThisCycle(DataHandler.TRUSS_ZONE));
		tv_tCatch.setText("Caught: "+DataHandler.getCaughtAssistsThisCycle(DataHandler.TRUSS_ZONE));
		//goal zone
		tv_gThrow.setText("Thrown: "+DataHandler.getThrownAssistsThisCycle(DataHandler.GOAL_ZONE));
		tv_gCatch.setText("Caught: "+DataHandler.getCaughtAssistsThisCycle(DataHandler.GOAL_ZONE));
		
		//updates total assist text
		tv_passes.setText("Assists Thrown: "+DataHandler.getTotalThrownAssists());
		tv_catches.setText("Assists Caught: "+DataHandler.getTotalCaughtAssists());
		 
		//updates score and cycle at top of activity
		tv_cycle.setText(""+DataHandler.getCycles());
		tv_score.setText(""+DataHandler.getScore());
		
		//updates button text
		btn_trussPass.setText("Throw: "+DataHandler.getTrussPassesThisCycle());
		btn_trussCatch.setText("Catch: "+DataHandler.getTrussCatchesThisCycle());
		btn_trussMiss.setText("Miss: "+DataHandler.getTrussMissesThisCycle());
		btn_miss.setText("Miss: "+DataHandler.getTopGoalMissesTele());
		
		//begins cycle timer if it hasnt started already
		if(!DataHandler.isClockRunning())
			DataHandler.beginMatch();
	 }
}
