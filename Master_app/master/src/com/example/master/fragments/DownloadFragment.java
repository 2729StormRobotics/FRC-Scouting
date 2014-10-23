package com.example.master.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.master.R;

@SuppressLint("NewApi")
public class DownloadFragment extends Fragment {

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	     // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.activity_download_fragment, container, false);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 
		 //gets activity and handler
		 Activity activity = this.getActivity();
		 
 		//View update
 		TextView downloadTV = (TextView)activity.findViewById(R.id.tv_download);
 		downloadTV.setText("All files downloaded will be saved to your /Downloads folder (file path: "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+")");
 		
			
		 //updates all the button text
		 //btn_top.setText("Top: "+DataHandler.getTopGoalsAuto());
		 
	 }

}
