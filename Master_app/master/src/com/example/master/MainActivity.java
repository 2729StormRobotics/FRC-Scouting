package com.example.master;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import net.sourceforge.zbar.Symbol;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.master.fragments.AssistsFragment;
import com.example.master.fragments.AutoFragment;
import com.example.master.fragments.CycleFragment;
import com.example.master.fragments.DataFragment;
import com.example.master.fragments.DownloadFragment;
import com.example.master.fragments.DriverFragment;
import com.example.master.fragments.GoalsFragment;
import com.example.master.fragments.RankingFragment;
import com.example.master.fragments.ScannerFragment;
import com.example.master.fragments.TrussShotFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "NewApi", "ResourceAsColor" })
public class MainActivity extends FragmentActivity implements TabListener{

	private final String SCOUTER_ID = "@stormscouting ";
	
	//database
	public static DBHelper db; 	
	public static String[] tempStorage = {"","","","","",""};
	public static String allDataString = "";
	public static ArrayList<GraphHandler> teams = new ArrayList<GraphHandler>();
	
	//ranking
	public static TableLayout rankingsTable_tl;	
	
	//fragments or tabs
	public static FragmentManager fragmentManager;				//fragment manager
	public static ScannerFragment fragmentScanner;				//scanner fragment class
	public static DownloadFragment fragmentDownload;			//download fragment class
	public static DataFragment fragmentData;					//database fragment class
	public static DriverFragment fragmentDriver;				//Changed to uploading fragment class
	public static RankingFragment fragmentRanking;				//ranking fragment class
	public static AutoFragment fragmentAuto;					//ranking fragment class
	public static AssistsFragment fragmentAssists;				//ranking fragment class
	public static CycleFragment fragmentCycle;					//Changed to Driver fragment class
	public static GoalsFragment fragmentGoal;					//ranking fragment class
	public static TrussShotFragment fragmentTruss;				//ranking fragment class
	
	
	//fragment/tab variables
	private String[] tabNames = {"Scan","Download","Database", "Ranking", "Auto", "Assists", "Driver", "Goals", "Truss", "Upload"};	//tab names
	
	//for pop up entries
	private boolean yes = false;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//database init
		db = new DBHelper(this);
		
		//initializes action bar...
		final ActionBar actionBar = getActionBar();
		
		//fragments init
		fragmentScanner = new ScannerFragment();
		fragmentDownload = new DownloadFragment();
		fragmentData = new DataFragment();
		fragmentDriver = new DriverFragment();
		fragmentRanking = new RankingFragment();
		fragmentAuto = new AutoFragment();
		fragmentAssists = new AssistsFragment();
		fragmentCycle = new CycleFragment();
		fragmentGoal = new GoalsFragment();
		fragmentTruss = new TrussShotFragment();
		
		//fragment manager init...starts user on scanner fragment
		fragmentManager = this.getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container, fragmentScanner);
		transaction.addToBackStack(null);
		transaction.commit();
		
		// Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // Add tabs, specifying the tab's text and TabListener
	    for (int i = 0; i < tabNames.length; i++) {
	        actionBar.addTab(actionBar.newTab().setText(tabNames[i]).setTabListener(this));
	    }
	    
	    //initializes any other varibles
	    updateDataString();
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	////////////////////////////////////////////////////////// Database related methods ////////////////////////////////////////////////////////////////
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
		try{
	    if(resultCode == RESULT_OK){
	       String input = data.getStringExtra(ZBarConstants.SCAN_RESULT);
	       
	       //checking if the qr entry is from the scouter
	       //id check: "@stormscouting "       
	       if (input.indexOf(SCOUTER_ID) >= 0){
	    	   //formating input to exclude SCOUTER_ID and spaces
		       input = input.substring(input.indexOf(" ")+1,input.length());
		       
		       //storing data into temporary storage before putting into database
		       if (!tempStorage[5].equals(""))
		   			tempToDB();
		       for (int i = 0; i<tempStorage.length; i++){
		    	   if (tempStorage[i].equals("")){
		    		   tempStorage[i]=input;
		    		   break;
		    	   }
		       }
		       
		       displayTemp();
		       
	       }else{
	    	   Toast.makeText(this, "Not a QR Code from Scouter", Toast.LENGTH_SHORT).show();
	       }
	    }else if(resultCode == RESULT_CANCELED){
	        Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
	    }
		}catch(Exception e){
	        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	public void displayTemp(){
	       //displaying what is in tempStorage
	       TextView team1 = (TextView) this.findViewById(R.id.tv_team1);
	       if (tempStorage[0].indexOf(",") > 0)
	       		team1.setText(tempStorage[0].substring(0, tempStorage[0].indexOf(",")));
	       else	
	    	   team1.setText("");
	       
	       TextView team2 = (TextView) this.findViewById(R.id.tv_team2);
	       if (tempStorage[1].indexOf(",") > 0)
	       		team2.setText(tempStorage[1].substring(0, tempStorage[1].indexOf(",")));
	       else	
	    	   	team2.setText("");
	       
	       TextView team3 = (TextView) this.findViewById(R.id.tv_team3);
	       if (tempStorage[2].indexOf(",") > 0)
	       		team3.setText(tempStorage[2].substring(0, tempStorage[2].indexOf(",")));
	       else	
	    	   	team3.setText("");
	       
	       TextView team4 = (TextView) this.findViewById(R.id.tv_team4);
	       if (tempStorage[3].indexOf(",") > 0)
	       		team4.setText(tempStorage[3].substring(0, tempStorage[3].indexOf(",")));
	       else	
	    	   	team4.setText("");
	       
	       TextView team5 = (TextView) this.findViewById(R.id.tv_team5);
	       if (tempStorage[4].indexOf(",") > 0)
	       		team5.setText(tempStorage[4].substring(0, tempStorage[4].indexOf(",")));
	       else	
	    	   	team5.setText("");
	       
	       TextView team6 = (TextView) this.findViewById(R.id.tv_team6);
	       if (tempStorage[5].indexOf(",") > 0)
	       		team6.setText(tempStorage[5].substring(0, tempStorage[5].indexOf(",")));
	       else	
	    	   	team6.setText("");
	}
	public void addDatatoDB(String input){
	       //putting entry into database
	       try{
		   	   AerialAssist aa = new AerialAssist(input);
		   	   db.addData(aa);
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
	}
	public void addDatatoDB(AerialAssist input){
	       //putting entry into database
	       try{
		   	   db.addData(input);
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
	}
	
	public void updateDataString(){
    	//updates the data displayed with what is on the database
		allDataString = "";
		
		ArrayList<AerialAssist> aaArray = db.getAllData();
		for (AerialAssist a:aaArray)
			allDataString += a.getTextData()+"\n\n";
	}
	public void updateRankingsData(int type, boolean ordering){
    	//updates the data displayed in the rankings
		//gets array of GraphHandlers and display its info
		
		//database vars
		//updates the teams and their info
		if (!ordering){
			ArrayList<Integer> teamNumbers = new ArrayList<Integer>();
			teamNumbers = db.getAllTeamNums();
			for (int i = 0; i < teamNumbers.size(); i++)
				teams.add(new GraphHandler(db.getTeamData(teamNumbers.get(i))));
		}
		
		//vars
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
		rowParams.setMargins(10, 10, 10, 10);
		
		String col2 = "";
		if (type == 1){
			rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl);
			col2 = "Team Ratings";
		}else if (type == 2){
				rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl_auto);
				col2 = "Auto Ratings";
		}else if (type == 3){
			rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl_assists);
			col2 = "Assists Ratings";
		}else if (type == 4){
			rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl_cycle);
			col2 = "Info";
		}else if (type == 5){
			rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl_truss);
			col2 = "Truss Ratings";
		}else if (type == 6){
			rankingsTable_tl = (TableLayout) this.findViewById(R.id.rankings_tl_goal);
			col2 = "Goal Ratings";
		}
		rankingsTable_tl.setColumnStretchable(1, true);
		
		//clears everything out first
		rankingsTable_tl.removeAllViews();
		
		//column subtitles row is add
		TableRow headerRow = new TableRow(this);
		headerRow.setLayoutParams(rankingsTable_tl.getLayoutParams());
		headerRow.setGravity(Gravity.CENTER);
		
		TextView teamNumHeader_tv = new TextView(this);
		teamNumHeader_tv.setLayoutParams(rowParams);
		teamNumHeader_tv.setGravity(Gravity.LEFT);
		teamNumHeader_tv.setTextColor(getResources().getColor(android.R.color.white));
		teamNumHeader_tv.setTextSize(20);
		teamNumHeader_tv.setPadding(5,5,5,5);
		teamNumHeader_tv.setText(R.string.team_num);

		TextView teamRatingHeader_tv = new TextView(this);
		teamRatingHeader_tv.setLayoutParams(rowParams);
		teamRatingHeader_tv.setGravity(Gravity.CENTER);
		teamRatingHeader_tv.setTextColor(getResources().getColor(android.R.color.white));
		teamRatingHeader_tv.setTextSize(20);
		teamRatingHeader_tv.setPadding(5,5,5,5);
		teamRatingHeader_tv.setText(col2);
		
		headerRow.addView(teamNumHeader_tv);
		headerRow.addView(teamRatingHeader_tv);
		
		rankingsTable_tl.addView(headerRow);

		for (int i = 0; i < teams.size(); i++){
			//making of each row that contains a team's number and its ranking score
			TableRow row = new TableRow(this);
			row.setLayoutParams(rankingsTable_tl.getLayoutParams());
			row.setGravity(Gravity.CENTER);
			ShapeDrawable border = new ShapeDrawable(new RectShape());
			border.getPaint().setStyle(Style.STROKE);
			border.getPaint().setColor(Color.WHITE);
			row.setBackground(border);
			
			//content of the row
			TextView team_tv = new TextView(this);
			team_tv.setLayoutParams(rowParams);
			team_tv.setIncludeFontPadding(false);
			team_tv.setGravity(Gravity.TOP);
			team_tv.setTextColor(getResources().getColor(android.R.color.white));
			team_tv.setTextSize(20);
			team_tv.setPadding(5,5,5,5);
			
			String teamNum = ""+teams.get(i).getTeamNum();	//testing team
			team_tv.setText(teamNum);
	
			TextView rankScore_tv = new TextView(this);
			rankScore_tv.setLayoutParams(rowParams);
			rankScore_tv.setGravity(Gravity.LEFT);
			rankScore_tv.setTextSize(10);
			rankScore_tv.setPadding(20,5,5,5);
			
			String rankScore = "";
			//number into |||||
			if (type == 1){
				int[] ranks = ((teams.get(i)).getTotalMatchData());
				
				String[] rankString = new String[3];
				for (int l = 0; l <rankString.length; l++)
					rankString[l] = "";
				for (int k = 0; k <ranks.length; k++){
					for (int j = 0; j < ranks[k]; j++)
						rankString[k] += "|";
				}
				
				rankScore = "<font color=#ff3333>"+rankString[0]+"</font>"
							+ "<font color=#ffffff>"+rankString[1]+"</font>"
							+ "<font color=#5c85ff>"+rankString[2]+"</font>"
							+ "<br /> <font color=#ffffff>F: "+ranks[0] +" C: "+ranks[1] +" G: "+ranks[2]+" Total: "+ (ranks[0]+ranks[1]+ranks[2])+"</font>";	//testing rank
				
			}else if (type == 2){
				int[] ranks = ((teams.get(i)).getTeamAuto());
				
				String[] rankString = new String[6];
				for (int l = 0; l <rankString.length; l++)
					rankString[l] = "";
				for (int k = 0; k <ranks.length; k++){
					for (int j = 0; j < ranks[k]; j++)
						rankString[k] += "";
				}
				
				
				rankScore = "<font color=#ffffff>Moved: ("+ranks[0]+") "+rankString[0]+"</font>"
							+ "<br /><font color=#ffffff>Hot Bottom Goal: ("+ranks[1]+") "+rankString[1]+"</font>"
							+ "<br /><font color=#ffffff>Bottom Goal: ("+ranks[2]+") "+rankString[2]+"</font>"
							+ "<br /><font color=#ffffff>Hot Top Goal: ("+ranks[3]+") "+rankString[3]+"</font>"
							+ "<br /><font color=#ffffff>Top Goal: ("+ranks[4]+") "+rankString[4]+"</font>"
							+ "<br /><font color=#ffffff>Misses Top: ("+ranks[5]+") "+rankString[5]+"</font>";
				
			}else if (type == 3){
				int[] ranks = ((teams.get(i)).getTeamAssists());
				
				String[] rankString = new String[6];
				for (int l = 0; l <rankString.length; l++)
					rankString[l] = "";
				for (int k = 0; k <ranks.length; k++){
					for (int j = 0; j < ranks[k]; j++)
						rankString[k] += "";
				}
				
				rankScore = "<font color=#ffffff>Feeder Passes: ("+ranks[0]+") "+rankString[0]+"</font>"
						+ "<br /><font color=#ffffff>Truss Passes: ("+ranks[1]+") "+rankString[1]+"</font>"
						+ "<br /><font color=#ffffff>Goal Passes: ("+ranks[2]+") "+rankString[2]+"</font>"
						+ "<br /><font color=#ffffff>Feeder Catches: ("+ranks[3]+") "+rankString[3]+"</font>"
						+ "<br /><font color=#ffffff>Truss Catches: ("+ranks[4]+") "+rankString[4]+"</font>"
						+ "<br /><font color=#ffffff>Goal Catches: ("+ranks[5]+") "+rankString[5]+"</font>";
							
			}else if (type == 4){
				/****************************Driver fragment...gives drivers necessary information***********************************/
				int searchedTeams[] = new int[6];
				try{
					searchedTeams[0] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_1)).getText().toString());
					searchedTeams[1] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_2)).getText().toString());
					searchedTeams[2] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_3)).getText().toString());
					searchedTeams[3] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_4)).getText().toString());
					searchedTeams[4] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_5)).getText().toString());
					searchedTeams[5] = Integer.parseInt(((EditText)this.findViewById(R.id.team_num_search_6)).getText().toString());
				}catch(Exception e){
					Toast.makeText(this, "Please recheck the search teams ("+e.toString()+")", Toast.LENGTH_LONG).show();
				}
						
				
				if (teams.get(i).getTeamNum() == searchedTeams[0] || teams.get(i).getTeamNum() == searchedTeams[1] || teams.get(i).getTeamNum() == searchedTeams[2]
						|| teams.get(i).getTeamNum() == searchedTeams[3] || teams.get(i).getTeamNum() == searchedTeams[4] || teams.get(i).getTeamNum() == searchedTeams[5]){
				AerialAssist lastMatchPlayed = teams.get(i).getRawTeamData().get(teams.get(i).getMatchNum()-1);
				String alliance = "";
				if (lastMatchPlayed.getIsRed() == 1) alliance = "Red Alliance";
				else alliance = "Blue Alliance";
				
				rankScore = "<h1><font color=#ffffff>Last Match: "+lastMatchPlayed.getMatchNum()+" ("+alliance+")</font></h1>"
							+ "<font color=#ffffff>"
									+ "<b><font size=\"15\">+ AUTO:</font></b><br />"
									+ "&emsp;Top Goals<br />&emsp;&emsp;Normal: "+lastMatchPlayed.getTopAuto()+"<br />&emsp;&emsp;Hot: "+lastMatchPlayed.getTopHotAuto()+""
									+ "<br />&emsp;&emsp; Missed: "+lastMatchPlayed.getTopMissesAuto()+""
									+ "<br />&emsp;Bottom Goals <br />&emsp;&emsp;Normal: "+lastMatchPlayed.getBottomAuto()+"<br />&emsp;&emsp;Hot: "+lastMatchPlayed.getBottomHotAuto()+""
									+ "<br />&emsp;Defense: "+lastMatchPlayed.getDefense()+""
									+ "<br />&emsp;Movement: "+lastMatchPlayed.getMovement()+""
									+"<br /><br />"
									
									+ "<b><font size=\"15\">+ TELE:</font></b>"
									+ "<br />"
									+ "&emsp;Top Goals<br />&emsp;&emsp;Made: "+lastMatchPlayed.getTopTele()+""
									+ "<br />&emsp;&emsp;Missed: "+lastMatchPlayed.getTopMissesTele()+""
									+ "<br />&emsp;Bottom Goals: "+lastMatchPlayed.getBottomTele()+""
									+ "<br />&emsp;Assists (passes/catches)<br />&emsp;&emsp;Feeder Zone: "+lastMatchPlayed.getCatchFZone()+" / "+lastMatchPlayed.getThrowFZone()+"<br />"
																					+ "&emsp;&emsp;Center Zone: "+lastMatchPlayed.getCatchTZone()+" / "+lastMatchPlayed.getThrowTZone()+"<br />"
																					+ "&emsp;&emsp;Goal Zone: "+lastMatchPlayed.getCatchGZone()+" / "+lastMatchPlayed.getThrowGZone()+""
										+""
									+ "<br />&emsp;Truss Shots<br />&emsp;&emsp;Made: "+teams.get(i).getSum(lastMatchPlayed.getTrussThrowArray())+"<br />"
																	+"&emsp;&emsp;Missed: "+teams.get(i).getSum(lastMatchPlayed.getTrussMissArray())+"<br />"
																	+"&emsp;&emsp;Caught: "+teams.get(i).getSum(lastMatchPlayed.getTrussCatchArray())
										+ ""
							+ "<br /></font>"		
								+ "<h1><font color=#ffffff>Strategy</font></h1><font color=#ffffff>"
									+ "&emsp;Top Shots (hit/miss)<br />&emsp;&emsp;Auto: "+(teams.get(i).getTeamAuto()[4]+teams.get(i).getTeamAuto()[3])+" / "+teams.get(i).getTeamAuto()[5]+"<br />"
																				+"&emsp;&emsp;Tele: "+teams.get(i).getTeamGoals()[2]+" / "+teams.get(i).getTeamGoals()[0]+ " ["+teams.get(i).getTeamGoals()[3]+"%]"
										+ ""
									+ "<br />&emsp;Zone Assists (passes/catches)<br />&emsp;&emsp;Feeder Zone: "+teams.get(i).getTeamAssists()[0]+" / "+teams.get(i).getTeamAssists()[3]+"<br />"
																					+ "&emsp;&emsp;Center Zone: "+teams.get(i).getTeamAssists()[1]+" / "+teams.get(i).getTeamAssists()[4]+"<br />"
																					+ "&emsp;&emsp;Goal Zone: "+teams.get(i).getTeamAssists()[2]+" / "+teams.get(i).getTeamAssists()[5]+""
											+""
									+ "<br />&emsp;Average Cycle Time: "+teams.get(i).getAverageCycle()+" secs"
									+ "<br />&emsp;Truss Shots<br />&emsp;&emsp;Made: "+teams.get(i).getTotalTrussShotsAndCatches()[0]+"<br />"
																+"&emsp;&emsp;Missed: "+teams.get(i).getTotalTrussShotsAndCatches()[2]+"<br />"
																+"&emsp;&emsp;Caught: "+teams.get(i).getTotalTrussShotsAndCatches()[1]+""
							+ "</font>";
				}
			}else if (type == 5){
				int[] ranks = ((teams.get(i)).getTotalTrussShotsAndCatches());
				
				String[] rankString = new String[3];
				for (int l = 0; l <rankString.length; l++)
					rankString[l] = "";
				for (int k = 0; k <ranks.length; k++){
					for (int j = 0; j < ranks[k]; j++)
						rankString[k] += "";
				}
				
				rankScore = "<font color=#ffffff>Made Truss: ("+ranks[0]+") "+rankString[0]+"</font>"
						+ "<br /><font color=#ffffff>Caught Truss: ("+ranks[1]+") "+rankString[1]+"</font>"
						+ "<br /><font color=#ffffff>Missed Truss: ("+ranks[2]+") "+rankString[2]+"</font>";
							
			}else if (type == 6){
				int[] ranks = ((teams.get(i)).getTeamGoals());
				
				String[] rankString = new String[4];
				for (int l = 0; l <rankString.length; l++)
					rankString[l] = "";
				for (int k = 0; k <ranks.length; k++){
					for (int j = 0; j < ranks[k]; j++)
						rankString[k] += "";
				}
				
				rankScore =  "<font color=#ffffff>Misses: ("+ranks[0]+") "+rankString[0]+"</font>"
						+ "<br /><font color=#ffffff>Bottom Goal: ("+ranks[1]+") "+rankString[1]+"</font>"
						+ "<br /><font color=#ffffff>Top Goal: ("+ranks[2]+") "+rankString[2]+"</font>"
								+ "<br /><font color=#FFFFFF>Shot Accuracy: ("+ranks[3]+") "+rankString[3]+"</font>";
							
			}
			rankScore_tv.setText(Html.fromHtml(rankScore));
			
			//adding text content to row
			row.addView(team_tv);
			row.addView(rankScore_tv);
			
			//adding row to tableLayout
			rankingsTable_tl.addView(row);
		}
	}
	
	public void tempToDB(){
 	   	for (int i = 0; i<tempStorage.length; i++){
 	   		if (tempStorage[i].indexOf(",") > 0)
 	   		{
 	   			addDatatoDB(tempStorage[i]);
 	 	   		tempStorage[i] = "";
 	   		}
 	   	}
 	   	displayTemp();
	}
	public void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Clear Database");
	    builder.setMessage("Are you sure you want to clear all the Data?");
	    builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked
				MainActivity.this.yes = true;
				MainActivity.db.clearDB();
			}
		  });
	    builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked
				MainActivity.this.yes = false;
			}
		  });
		// create alert dialog
		AlertDialog alertDialog = builder.create();

		// show it
		alertDialog.show();
	}
	
	
	////////////////////////////////////////// Button methods ///////////////////////////////////////////
	public void scan(View view) {
		Intent intent = new Intent(this, ZBarScannerActivity.class);
		intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
		startActivityForResult(intent, 0);
	}	
	public void addData(View view){
		tempToDB();
	}
	public void clearDB(View view){
		//to clear every row of the database
		dialog();
		
		updateDataString();
		
    	TextView tv_data = (TextView) this.findViewById(R.id.tv_data);
		tv_data.setText(allDataString);
	}
	public void upload(View view){
		EditText et_upload = (EditText) this.findViewById(R.id.upload_et);
		
		String input = et_upload.getText().toString();
		input = input.replaceAll("\\s+", "");
		et_upload.setText(input);
		ArrayList<AerialAssist> aaArray = new ArrayList<AerialAssist>();
		
		while(input.indexOf(";") > 0){
			aaArray.add(new AerialAssist(input.substring(0, input.indexOf(";"))));
			
			if (input.indexOf(";") < input.length())
				input = input.substring(input.indexOf(";")+1, input.length());
			else
				input = "";
		}
		try{
						
			for (int i = 0; i<aaArray.size(); i++){
				addDatatoDB(aaArray.get(i));
			}
			
		}catch(Exception e){
			Toast.makeText(this, "Could not be uploaded: "+e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void orderByRank(View view){
//		int temp[] = new int[teams.size()];
//		
//		try{
//		for (int i = 0; i<temp.length; i++){
//			temp[i] = teams.get(i).getSum(teams.get(i).getTotalMatchData());
//		}
//		
//		Arrays.sort(temp);
//		
//		ArrayList<GraphHandler> temp2 = teams;
//		
//		for (int i = 0; i<teams.size(); i++){
//			for (int j = 0; temp2.size()>1; j++){
//				if (temp2.get(j).getSum(temp2.get(j).getTotalMatchData()) == temp[i]){
//					temp2.remove(j);
//					teams.set(i, temp2.get(j));
//					break;
//				}
//			}
//		}
//		}catch(Exception e){
//			Toast.makeText(this, "ERROR: "+e.toString(), Toast.LENGTH_LONG).show();
//		}
//
//		updateRankingsData(1,true);
	}
	public void updateRanking1(View view){
		updateRankingsData(1,false);
	}
	public void updateRanking2(View view){
		updateRankingsData(2,false);
	}
	public void updateRanking3(View view){
		updateRankingsData(3,false);
	}
	public void updateRanking4(View view){
		updateRankingsData(4,false);
	}
	public void updateRanking5(View view){
		updateRankingsData(5,false);
	}
	public void updateRanking6(View view){
		updateRankingsData(6,false);
	}
	public void updateData(View view){
		updateDataString();

    	TextView tv_data = (TextView) this.findViewById(R.id.tv_data);
		tv_data.setText(allDataString);
	}
	
	public void downloadXMLAuto(View view){
		//download the xml file...saves xml file created from database into /DOWNLOADS
		
		try {           
				File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AerialAssist_Auto.xml");       
				BufferedWriter writer1 = new BufferedWriter(new FileWriter(file1));
				
				//writing into the files
				writer1.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
							"<Auto xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
				writer1.write(db.getAllXMLAuto());
				
				writer1.write("</Auto>");
			    
			    writer1.close();
			    
			Toast.makeText(this, "AerialAssist_Auto XML files has been downloaded to your /DOWNLOADS folder", Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
			Toast.makeText(this, "ERROR: XML files not downloaded...", Toast.LENGTH_SHORT).show();
			TextView errorLog = (TextView) this.findViewById(R.id.tv_errorLog);
			errorLog.setText(e.toString());
	    }
	}
	public void downloadXMLTele(View view){
		//download the xml file...saves xml file created from database into /DOWNLOADS
		
		try {   
				File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AerialAssist_Tele.xml");       
				BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2));
				
				//writing into the files
				writer2.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
							"<Tele-OP xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
				
				writer2.write(db.getAllXMLTele());
				writer2.write("</Tele-OP>");
			    
			    writer2.close();
			    
			    
			Toast.makeText(this, "AerialAssist_Tele XML files has been downloaded to your /DOWNLOADS folder", Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
			Toast.makeText(this, "ERROR: XML files not downloaded...", Toast.LENGTH_SHORT).show();
			TextView errorLog = (TextView) this.findViewById(R.id.tv_errorLog);
			errorLog.setText(e.toString());
	    }
	}
	public void downloadText(View view){
		//download the xml file...saves xml file created from database into /DOWNLOADS
		try {                      
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Raw_AerialAssist.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			ArrayList<AerialAssist> aaArray = db.getAllData();

			for (AerialAssist a:aaArray){
				String result = "\n" + a.getTextData() + ";" +System.getProperty("line.separator");;
				writer.write(result);
				writer.newLine();
			}
		    
		    writer.close();
		    
			Toast.makeText(this, "The text file has been downloaded to your /DOWNLOADS folder", Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
			TextView errorLog = (TextView) this.findViewById(R.id.tv_errorLog);
			errorLog.setText(e.toString());
	    }
	}
	///////////////////////////////// For checking storage permissions ///////////////////////////////////
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

	///////////////////////////////// For fragments and tabs ///////////////////////////////////
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		String string = (String) tab.getText();
		
		 
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		 
        if(string == tabNames[0]){
        	transaction.replace(R.id.fragment_container,fragmentScanner);
        }else if(string == tabNames[1]){
           	transaction.replace(R.id.fragment_container,fragmentDownload);
        }else if(string == tabNames[2]){
           	transaction.replace(R.id.fragment_container,fragmentData);	
        }else if(string == tabNames[3]){
           	transaction.replace(R.id.fragment_container,fragmentRanking);	
        }else if(string == tabNames[4]){
           	transaction.replace(R.id.fragment_container,fragmentAuto);	
        }else if(string == tabNames[5]){
	       	transaction.replace(R.id.fragment_container,fragmentAssists);	
		}else if(string == tabNames[6]){
		   	transaction.replace(R.id.fragment_container,fragmentCycle);	
		}else if(string == tabNames[7]){
		   	transaction.replace(R.id.fragment_container,fragmentGoal);	
		}else if(string == tabNames[8]){
		   	transaction.replace(R.id.fragment_container,fragmentTruss);	
		}else if(string == tabNames[9]){
		   	transaction.replace(R.id.fragment_container,fragmentDriver);	
		}
        
        transaction.addToBackStack(null);
        transaction.commit();
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


}
