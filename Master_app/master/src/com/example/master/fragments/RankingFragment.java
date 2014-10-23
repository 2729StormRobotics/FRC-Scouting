package com.example.master.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.master.MainActivity;
import com.example.master.R;

@SuppressLint("NewApi")
public class RankingFragment extends Fragment {


	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	     // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.activity_ranking_fragment, container, false);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 
		 //gets activity and handler
		 Activity activity = this.getActivity();
		 
		 TableLayout rankingsTable = (TableLayout) activity.findViewById(R.id.rankings_tl);
		 rankingsTable = MainActivity.rankingsTable_tl;
		 
		 //finds buttons to update
		 //Button btn_top = (Button)activity.findViewById(R.id.btn_top);
			
		 //updates all the button text
		 //btn_top.setText("Top: "+DataHandler.getTopGoalsAuto());
		 
	 }

}
