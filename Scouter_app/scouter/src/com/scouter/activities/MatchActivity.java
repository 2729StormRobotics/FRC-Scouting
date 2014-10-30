package com.scouter.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouter.R;
import com.scouter.activities.help.MatchHelpActivity;
import com.scouter.fragments.AutoFragment;
import com.scouter.fragments.SubmitFragment;
import com.scouter.fragments.TeleOpFragment;
import com.scouter.util.DataHandler;

/**
 * 
 * Match activity class. This is the screen visible during the match.
 * It contains three fragments, one for Autonomous, Tele-Op, and 
 * submitting the data. The user can switch between using tabs.
 * 
 * @author Anthony Drenik
 * 
 * @see com.scouter.fragments.AutoFragment	 
 * @see com.scouter.fragments.TeleOpFragment	 
 * @see com.scouter.fragments.SubmitFragment	 
 * 
 */

@SuppressLint("NewApi")
public class MatchActivity extends FragmentActivity implements TabListener,DialogInterface.OnClickListener{
	
	//////////CONSTANTS//////////
	public static final String EXTRA_MESSAGE = "com.example.scouter.MESSAGE";
	public static final String ASSISTS="Assists", THROWN="Thrown", CAUGHT="Caught";
	/////////////////////////////
	
	//////////VARIABLES//////////
	private String[] tabNames = {"Auto","Tele-Op", "Submit"};	//tab names
	
	public static AutoFragment fragmentAuto;					//autonomous fragment
	public static TeleOpFragment fragmentTeleOp;				//teleOp fragment
	public static SubmitFragment fragmentSubmit;				//submit fragment
	
	private static boolean dialogShot=false, dialogTop=false;
	
	private static MatchActivity copyActivity;
	
	public static FragmentManager fragmentManager;				//fragment manager
	private int zone;											//current zone id
	
	/////////////////////////////
	
	//////////IMPLEMENTED METHODS//////////
	protected void onCreate(Bundle savedInstanceState) {
		
		//sets up activity layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match);
		
		//receives intent and extras
		Intent intent = this.getIntent();
		String matchNum = intent.getStringExtra(MainActivity.EXTRA_MATCH_NUM),
			   teamNum = intent.getStringExtra(MainActivity.EXTRA_TEAM_NUM);

		//initializes action bar
		final ActionBar actionBar = getActionBar();
		
		//initializes fragments
		fragmentAuto = new AutoFragment();
		fragmentTeleOp = new TeleOpFragment();
		fragmentSubmit = new SubmitFragment();
		
		//tries to convert match and team numbers to integers, gives data to DataHandler class
		try
		{
			if(!DataHandler.isClockRunning()){
				DataHandler.setMatchNum(Integer.parseInt(matchNum));
				DataHandler.setTeamNum(Integer.parseInt(teamNum));
			}
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		
		this.setTitle("Team " + DataHandler.getTeamNum() + ", Match " + DataHandler.getMatchNum());
		
		//starts user in the autonomous fragment
		fragmentManager = this.getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container,fragmentAuto);
		transaction.addToBackStack(null);
        transaction.commit();
		
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // Add 3 tabs, specifying the tab's text and TabListener
	    for (int i = 0; i < 3; i++) {
	        actionBar.addTab(actionBar.newTab().setText(tabNames[i]).setTabListener(this));
	    }
	    
	    //setting the text of the zones
	    boolean isRed = intent.getBooleanExtra(MainActivity.EXTRA_IS_RED,false);
	    if(isRed){
	    	DataHandler.setToRedTeam();
	    	zone = DataHandler.FEEDER_ZONE;
	    }else{
	    	zone = DataHandler.GOAL_ZONE;
	    }
	    
	    copyActivity = this;
	  
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match, menu);
		return true;
	}
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {} //unused method
	
	//runs whenever a new tab is selected, changes fragments accordingly
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		//gets the title of the tab selected
		String string = (String) tab.getText();
		 
		//begins a new fragment transaction
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		 
		/*
		 * this is basically just a switch statement, we couldn't use an
		 * actual switch statement because string is a string (duh)
		 * Checks to see which tab was actually selected and makes
		 * that fragment the one visible in the activity
		 */
		
	    if(string == tabNames[0]){
	    	
	    	transaction.replace(R.id.fragment_container,fragmentAuto);		//shows the autonomous fragment
	    	if(DataHandler.isClockRunning())
	    		DataHandler.stopTimer();									//stops the cycle timer				
	    	
	    }else if(string == tabNames[1]){
	    	 
	    	transaction.replace(R.id.fragment_container,fragmentTeleOp);	//shows the teleop fragment
	    	if(DataHandler.isClockRunning())
	    		DataHandler.startTimer();									//starts the cycle timer
	    	 
	    }else if(string == tabNames[2]){
	    	 
	    	transaction.replace(R.id.fragment_container,fragmentSubmit);	//shows the submit fragment
	    	if(DataHandler.isClockRunning())
	    		DataHandler.stopTimer();									//stops the cycle timer
	    	 
	    }
	     
	    transaction.addToBackStack(null);
	    transaction.commit();
	}
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {} //unused method
	
	//this is the onClick() for the end cycle dialog box
	public void onClick(DialogInterface dialog, int which) {
        switch (which){
        case DialogInterface.BUTTON_POSITIVE:
        	
        	//if the cycle ended with a goal, adds that goal to the appropriate array
            if(dialogShot){
				
				if(dialogTop)
					DataHandler.addTopGoalTele(false);
				else
					DataHandler.addBottomGoalTele();
				
			}
			
            //updates the info in the dummy activity
			copyActivity.updateTopInfo();
			copyActivity.endCycle();
			
            break;
        case DialogInterface.BUTTON_NEGATIVE:
            break;
        }
        
        //updates the info in this activity
        this.updateTopInfo();
        
    }
	
	/*
	 * The onResume() method runs every time the activity becomes visible. 
	 * This is different from onCreate(), which runs once when the first 
	 * time the activity is shown. onResume() can run multiple times.
	 */
	
	public void onResume() {
		
		super.onResume();
		
		//sets the title of the activity
		this.setTitle("Team " + DataHandler.getTeamNum() + ", Match "+ DataHandler.getMatchNum());
	
	}
	
	//////////////////////////////////////
	
	//////////UPDATING METHODS//////////
	/*
	* These methods are used to update the various GUI elements. Most simply
	* update the text inside various TextViews in the activity
	*/
	
	//updates the text inside the zone selection buttons
	public void updateZones() {
		
		//checks whether the alliance color is red
		boolean isRed = DataHandler.isRed();
		
		//finds all the TextViews 
		//Note: if alliance is red, the text in the feeder and goal zones are swapped
		TextView tv_fTitle = (TextView)this.findViewById((isRed)?R.id.tv_redzone_title:R.id.tv_bluezone_title),
				 tv_gTitle = (TextView)this.findViewById((!isRed)?R.id.tv_redzone_title:R.id.tv_bluezone_title),
				 
				 tv_fThrow = (TextView)this.findViewById((isRed)?R.id.tv_redzoneThrownAssist:R.id.tv_bluezoneThrownAssist),
				 tv_fCatch = (TextView)this.findViewById((isRed)?R.id.tv_redzoneCaughtAssist:R.id.tv_bluezoneCaughtAssist),
				 
				 tv_tThrow = (TextView)this.findViewById(R.id.tv_tzoneThrownAssist),
				 tv_tCatch = (TextView)this.findViewById(R.id.tv_tzoneCaughtAssist),
				 
				 tv_gThrow = (TextView)this.findViewById((!isRed)?R.id.tv_redzoneThrownAssist:R.id.tv_bluezoneThrownAssist),
				 tv_gCatch = (TextView)this.findViewById((!isRed)?R.id.tv_redzoneCaughtAssist:R.id.tv_bluezoneCaughtAssist);
		
		//sets the titles of the feeder and goal zones
		//Truss zone's title never changes so it does not need to be updated here
		tv_fTitle.setText("Feeder Zone");
		tv_gTitle.setText("Goal Zone");
		
		//updates the number of feeder zone catches and throws shown
		tv_fThrow.setText(THROWN+": "+DataHandler.getThrownAssistsThisCycle(DataHandler.FEEDER_ZONE));
		tv_fCatch.setText(CAUGHT+": "+DataHandler.getCaughtAssistsThisCycle(DataHandler.FEEDER_ZONE));
		
		//updates the number of truss zone catches and throws shown
		tv_tThrow.setText(THROWN+": "+DataHandler.getThrownAssistsThisCycle(DataHandler.TRUSS_ZONE));
		tv_tCatch.setText(CAUGHT+": "+DataHandler.getCaughtAssistsThisCycle(DataHandler.TRUSS_ZONE));
		
		//updates the number of goal zone catches and throws shown
		tv_gThrow.setText(THROWN+": "+DataHandler.getThrownAssistsThisCycle(DataHandler.GOAL_ZONE));
		tv_gCatch.setText(CAUGHT+": "+DataHandler.getCaughtAssistsThisCycle(DataHandler.GOAL_ZONE));
		
		//this updates the total number of assists shown
		this.updateAssistText();
		
	}
	
	//updates score and cycle number at top of fragment
	public void updateTopInfo() {
		
		//finds TextViews
		TextView tv_cycles = (TextView)this.findViewById(R.id.tv_cycleNum),
				 tv_score = (TextView)this.findViewById(R.id.tv_scoreNum);
		
		//updates text
		tv_cycles.setText(""+DataHandler.getCycles());
		tv_score.setText(""+DataHandler.getScore());
	
	}
	
	//updates total number of assists shown
	public void updateAssistText() {
		
		//finds TextViews
		TextView catches = (TextView) this.findViewById(R.id.tv_TeleCaughtAssist),
				 passes = (TextView) this.findViewById(R.id.tv_TeleThrownAssist);
		
		//updates text
		passes.setText("Assists Thrown: "+DataHandler.getTotalThrownAssists());
		catches.setText("Assists Caught: "+DataHandler.getTotalCaughtAssists());
	
	}
	
	//updates text inside truss buttons
	public void updateScoringText() {
		
		//finds buttons
		Button	 btn_trussPass = (Button) this.findViewById(R.id.btn_trussThrow),
				 btn_trussCatch = (Button) this.findViewById(R.id.btn_trussReceive),
				 btn_trussMiss = (Button) this.findViewById(R.id.btn_trussMiss),
				 btn_miss = (Button) this.findViewById(R.id.btn_scoreMiss);
		
		//updates text
		btn_trussPass.setText("Throw: "+DataHandler.getTrussPassesThisCycle());
		btn_trussCatch.setText("Catch: "+DataHandler.getTrussCatchesThisCycle());
		btn_trussMiss.setText("Miss: "+DataHandler.getTrussMissesThisCycle());
		
		btn_miss.setText("Miss: "+DataHandler.getTopGoalMissesTele());
	
	}
	
	//ends the current cycle
	public void endCycle() {
			
			//this lets the DataHandler know that a cycle ended
			DataHandler.endCycle();
			
			//mainly for debugging, this gives the user a message saying how long the last cycle was
			Toast.makeText(this,"Cycle Ended. Length = "+DataHandler.getCycleLength(DataHandler.getCycles()-1)+" sec",Toast.LENGTH_SHORT).show();
			
			//updates all the text in the activity
			this.updateZones();
			this.updateAssistText();
			this.updateScoringText();
			
		}
	
	//updates all data and text in autonomous fragment
	//we decided to just make this one method because there isnt much to update in Auto
	public void updateAuto() {
		
		 //finds buttons
		 Button btn_top = (Button)this.findViewById(R.id.btn_top),
				btn_topHot = (Button)this.findViewById(R.id.btn_topHot),
				btn_topMiss = (Button)this.findViewById(R.id.btn_topMiss),
				btn_bottom = (Button)this.findViewById(R.id.btn_bottom),
				btn_botHot = (Button)this.findViewById(R.id.btn_bottomHot);
		 
		 //updates text
		 btn_top.setText("Top: "+DataHandler.getTopGoalsAuto());
		 btn_topHot.setText("Top Hot: "+DataHandler.getTopHotGoals());
		 btn_bottom.setText("Bottom: "+DataHandler.getBotGoalsAuto());
		 btn_botHot.setText("Bot. Hot: "+DataHandler.getBotHotGoals());
		 btn_topMiss.setText("Miss: "+DataHandler.getTopGoalMissesAuto());
	
	}
	
	////////////////////////////////////////
	
	
	//////////BUTTON CLICK METHODS//////////
	
	/**
	 * This method is only used by the buttons at the bottom of each fragment that
	 * switch fragments. This method DOES NOT run when the user selects a different
	 * tab on their own.
	 * 
	 * @param view - the button pressed
	 * 
	 */
	public void switchTabs(View view) {
		
		//finds the button
		Button btn = (Button)view;
		
		switch(btn.getId()){
		
		//toTeleOp button
		case R.id.btn_toTeleOp:
			
			this.getActionBar().selectTab(this.getActionBar().getTabAt(1));		//selects the TeleOp tab
			if(DataHandler.getCycles()==0){DataHandler.endCycle();}				//if the match hasn't started, it starts the match
			break;
		
		//endMatch button
		case R.id.btn_endMatch:
			
			this.getActionBar().selectTab(this.getActionBar().getTabAt(2));		//selects the Submit tab
			break;
		
		}
		
	}
	
	public boolean onOptionsItemSelected(MenuItem i) {
    	switch(i.getItemId()){
		case R.id.action_MatchHelp:
			Intent intent = new Intent(this,MatchHelpActivity.class);
			startActivity(intent);
			return true;
		default:
			return false;
    	}
    }
	
	//submits the data to be turned into a QR code
	public void submit(View view) {
		
		Intent intent = new Intent(this,QRGeneratorActivity.class);					//creates a new intent
		
		intent.putExtra(EXTRA_MESSAGE,DataHandler.getAllData()); 					//gets all the data in one long string
		intent.putExtra(MainActivity.EXTRA_MATCH_NUM,DataHandler.getMatchNum());	//gets the currents match number 
		
		startActivity(intent); 														//starts QRGeneratorActivity
	
	}
	
	//handles all button clicks in autonomous
	public void buttonClickedAuto(View view) {
		
		//finds button
		Button btn = (Button)view;
		
		switch(btn.getId()){
			 
		/////SCORING BUTTONS/////
		
		/*
		 * When one of these scoring buttons are pressed, the switch statement
		 * adds the appropriate goal to the dataHandler, then updates the text
		 * throughout the fragment
		 */
		
		//top goal
		case R.id.btn_top:
			DataHandler.addTopGoalAuto(false,false);
			this.updateAuto();
			break;
		
		//bottom goal
		case R.id.btn_bottom:
			DataHandler.addBottomGoalAuto(false);
			this.updateAuto();
			break;
			
		//top hot goal
		case R.id.btn_topHot:
			DataHandler.addTopGoalAuto(true,false);
			this.updateAuto();
			break;
			
		//bottom hot goal
		case R.id.btn_bottomHot:
			DataHandler.addBottomGoalAuto(true);
			this.updateAuto();
			break;
			 
		//top goal miss
		case R.id.btn_topMiss:
			DataHandler.addTopGoalAuto(false,true);
			this.updateAuto();
			break;
			
		//////////////////////
			
		/////MISC BUTTONS/////
			
		//clear auto
		case R.id.btn_clearAuto:
			DataHandler.clearAuto();
			this.updateAuto();
			break;
			
		//defense button, a CheckBox for whether or not the team defended in Auto
		case R.id.btn_defense:
			 
			//finds the CheckBox
			CheckBox chk_def = (CheckBox) this.findViewById(R.id.chk_defense);
			 
			//alternates didDefend between true and false every time the button is clicked
			if(!DataHandler.didDefend()){
				DataHandler.setDefense(true);
				chk_def.setChecked(true);
			}else{
				DataHandler.setDefense(false);
				chk_def.setChecked(false);
			}
			
			break;
			 
		//moved button, another CheckBox for whether or not the team moved in Auto
		case R.id.btn_moved:
			
			//finds the CheckBox
			CheckBox chk_mov = (CheckBox) this.findViewById(R.id.chk_move);
			
			//alternates didMove between true and false
			if(!DataHandler.didMove()){
				DataHandler.setMoved(true);
				chk_mov.setChecked(true);
			}else{
				DataHandler.setMoved(false);
				chk_mov.setChecked(false);
			}
			
			break;
		
		}
	}
	
	//handles all button clicks in teleOp
	public void buttonClickedTele(View view) {
		 Button btn = (Button)view;
		 switch(btn.getId()){
		 
		 //assist buttons
		 case R.id.btn_assistThrowAdd:
			 DataHandler.passed(zone);
			 this.updateZones();
			 break;
		 case R.id.btn_assistCaughtAdd:
			 DataHandler.caught(zone);
			 this.updateZones();
			 break;
			 
		 case R.id.btn_assistThrowMinus:
			 DataHandler.cancelPass(zone);
			 this.updateZones();
			 break;
		 case R.id.btn_assistCaughtMinus:
			 DataHandler.cancelCatch(zone);
			 this.updateZones();
			 break;
			 
		 //truss buttons
		 case R.id.btn_trussThrow:
			 DataHandler.trussPassed();
			 this.updateScoringText();
			 break;
		 case R.id.btn_trussMiss:
			 DataHandler.trussMissed();
			 this.updateScoringText();
			 break;
		 case R.id.btn_trussReceive:
			 DataHandler.trussCaught();
			 this.updateScoringText();
			 break;
		 case R.id.btn_trussClear:
			 DataHandler.clearTrussInfo();
			 this.updateScoringText();
			 break;
			 
		 //scoring buttons
		 case R.id.btn_scoreTop:
			 dialog(true,true);
			 break;
		 case R.id.btn_scoreBottom:
			 dialog(true,false);
			 break;
		 case R.id.btn_scoreMiss:
			 DataHandler.addTopGoalTele(true);
			 this.updateScoringText();
			 break;
		 case R.id.btn_missSub:
			 DataHandler.cancelTopMiss();
			 this.updateScoringText();
			 break;
			 
		 //end cycle
		 case R.id.btn_endCycle:
			 dialog(false,false);
			 break;
			 
		 }
	 }
	
	/**
	 * 
	 * This creates a dialog box checking if the user really wants to end the
	 * current cycle
	 * 
	 * @param shot - whether the current cycle ended with a goal
	 * @param top - if the cycle ended with a top goal being made
	 */
	
	public void dialog(boolean shot, boolean top) {
		
		//makes a new dialog box
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		//sets all the text in the dialog box
		builder.setTitle("End cycle");
	    builder.setMessage("Are you sure?");
	    builder.setPositiveButton("Yes", this);
	    builder.setNegativeButton("No", this);
	    builder.show();
	    
	    //sets the static variables for shot and top
	    dialogShot = shot;
	    dialogTop = top;
	    
	}

	//selects whichever zone user pressed
	public void selectZone(View view){
		
		//finds which zone the user selected
		View redZone = this.findViewById(R.id.layout_redzone),
			 tZone = this.findViewById(R.id.layout_tzone),
			 blueZone = this.findViewById(R.id.layout_bluezone);
		
		//checks whether alliance color is red
		boolean isRed = DataHandler.isRed();
		
		
		switch(view.getId()){
		
		//red zone
		case R.id.layout_redzone:
			
			//sets selected zone to appropriate zone ID
			zone = (isRed)?DataHandler.FEEDER_ZONE:DataHandler.GOAL_ZONE;
			
			//highlights the red zone
			redZone.setBackgroundResource(R.drawable.high_rzone_border);
			tZone.setBackgroundResource(R.drawable.trans_wzone_border);
			blueZone.setBackgroundResource(R.drawable.trans_bzone_border);
			
			break;
			
		//truss zone (This one does not change based on alliance color)
		case R.id.layout_tzone:
			
			//sets the current zone ID to TRUSS_ZONE
			zone = DataHandler.TRUSS_ZONE;
			
			//highlights the truss zone
			redZone.setBackgroundResource(R.drawable.trans_rzone_border);
			tZone.setBackgroundResource(R.drawable.high_wzone_border);
			blueZone.setBackgroundResource(R.drawable.trans_bzone_border);
			
			break;
			
		//blue zone
		case R.id.layout_bluezone:
			
			//sets selected zone to appropriate zone ID
			zone = (!isRed)?DataHandler.FEEDER_ZONE:DataHandler.GOAL_ZONE;
			
			//highlights the blue zone
			redZone.setBackgroundResource(R.drawable.trans_rzone_border);
			tZone.setBackgroundResource(R.drawable.trans_wzone_border);
			blueZone.setBackgroundResource(R.drawable.high_bzone_border);
			
			break;
			
		}
	}
	//////////////////////////////////////////
	
}
