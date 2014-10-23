package com.example.test2222;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.master.AerialAssist;


public class DatabaseAccess {

	private String filePath = "";
	private String fileName = "";	
	private String tableName = "Aerial_Assist";
	
	//array of all the columns and their names
	private String[] columnNames = {	"team_num",				//team number
										"match_num",			//match number
										"is_red",				//whether alliance is red
										"top_auto",				//top goals in auto
										"topMisses_auto",		//top goal misses in auto
										"topHot_auto",			//top hot goals in auto
										"bottom_auto",			//bottom goals in auto
										"botHot_auto",			//bottom hot goals in auto
										"defense",				//whether team defended
										"movement",				//whether team moved
										"cycle",				//total cycle num
										"assit_throw",			//assist passes (array)
										"throw_fzone",			//passes from feeder zone
										"throw_tzone",			//from truss zone
										"throw_gzone",			//from goal zone
										"assit_catch",			//assist catches (array)
										"catch_fzone",			//catches from feeder zone
										"catch_tzone",			//from truss zone
										"catch_gzone",			//from goal zone
										"truss_throw",			//all truss throws (array)
										"truss_miss",			//all missed truss throws (array)
										"truss_catch",			//truss catches (array)
										"top_tele",				//all top goals made
										"topMisses_tele",		//all top goal misses
										"bottom_tele",			//all bottom goals made
										"cycle_goals",			//which goal was made in each cycle (array)
																/***************************	
																 * 	-1 = missed
																 * 	 0 = nothing
																 * 	 1 = bottom goal
																 * 	 2 = top goal
																 ***************************/
										"cycle_time"			//duration of each cycle
									};
	
	//getting connection to database file
	public DatabaseAccess(String path, String name){
		filePath = path;
		fileName = name;

	    Connection c = null; //the connection for accessing database
	    Statement stmt = null; 
	    try {
			//getting connection to file/making it
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+filePath +"/"+ fileName);
			c.setAutoCommit(false);
			
			//creating a new table in that database with all the needed columns
			//game specific, so must change for next competition
		    stmt = c.createStatement();
		    String str = "create table if not exists "+ tableName +
	    			   "("+"ROWID"+" 					INTEGER		PRIMARY KEY		AUTOINCREMENT," + //needed to make every data entry a unique entry
	    			   " "+columnNames[0]+" 			INT 		    			NOT NULL," +
	    			   " "+columnNames[1]+"           	INT    						NOT NULL," + 
	                   " "+columnNames[2]+"            	BOOLEAN    					NOT NULL," + 
	                   " "+columnNames[3]+"        	    INT							NOT NULL," + 
	                   " "+columnNames[4]+"         	INT							NOT NULL," +
	                   " "+columnNames[5]+" 			INT 				    	NOT NULL," +
	    			   " "+columnNames[6]+"           	INT    						NOT NULL," + 
	                   " "+columnNames[7]+"            	INT    						NOT NULL," + 
	                   " "+columnNames[8]+" 			BOOLEAN				    	NOT NULL," +
	    			   " "+columnNames[9]+"           	BOOLEAN  					NOT NULL," + 
	                   " "+columnNames[10]+"            INT    						NOT NULL," + 
	                   " "+columnNames[11]+" 			TEXT 				    	NOT NULL," +
	    			   " "+columnNames[12]+"           	INT  	  					NOT NULL," + 
	                   " "+columnNames[13]+"            INT    						NOT NULL," + 
	                   " "+columnNames[14]+" 			INT 				    	NOT NULL," +
	    			   " "+columnNames[15]+"           	TEXT   						NOT NULL," + 
	                   " "+columnNames[16]+"            INT    						NOT NULL," + 
	                   " "+columnNames[17]+" 			INT 				    	NOT NULL," +
	    			   " "+columnNames[18]+"           	INT    						NOT NULL," + 
	                   " "+columnNames[19]+"            TEXT   						NOT NULL," + 
	                   " "+columnNames[20]+" 			TEXT 				    	NOT NULL," +
	    			   " "+columnNames[21]+"           	TEXT   						NOT NULL," + 
	                   " "+columnNames[22]+"            INT    						NOT NULL," + 
	                   " "+columnNames[23]+" 			INT 				    	NOT NULL," +
	                   " "+columnNames[24]+" 			INT 				    	NOT NULL," +
	                   " "+columnNames[25]+"         	TEXT						NOT NULL," +
	                   " "+columnNames[26]+"         	TEXT						NOT NULL);"; 
		    stmt.executeUpdate(str); 
			
		    //closes everything
		    stmt.close();
		    c.commit(); //must commit!!! extremely important
		    c.close();
				
	    }catch ( Exception e ) {
		    e.printStackTrace();
		}	 	    
	}
	//with an input...puts input into database
	public void updateDatabase(String input) {

	    Connection c = null; //the connection for accessing database
	    Statement stmt = null; 
	    try {
	    	//establishes connection to the database file
	    	Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+filePath+fileName+".db");
			c.setAutoCommit(false);

		    stmt = c.createStatement();
		    
		    //input data to database
		    String sql = 	"INSERT INTO "+tableName+
	                   		" VALUES (NULL,"+input+");";  	//the input data
		    stmt.executeUpdate(sql); 					//actually does the command in sqlite
		    
		    //closes everything
		    stmt.close();
		    c.commit();
		    c.close();
	    	
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	    }
	 }
	//returns everything as a 2d arraylist
	public ArrayList<AerialAssist> getDatabase() {
		//returns a ResultSet of all results
	    ArrayList<AerialAssist> results = new ArrayList<AerialAssist>();
		
	    Connection c = null; //the connection for accessing database
	    Statement stmt = null; 
	    try {
	    	//establishes connection to the database file
	    	Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+filePath+fileName+".db");
			c.setAutoCommit(false);

		    stmt = c.createStatement();
		    
		    //gets the data from the database and puts it all in a 2d string array
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM "+tableName+";" );		//all the data
		    while ( rs.next() ) {
		    	//Aerial assist, game specific, database retriever
		    	AerialAssist aa = new AerialAssist();
		    	  aa.setTeamNum				(rs.getInt		(2));
		    	  aa.setMatchNum			(rs.getInt		(3));
		    	  aa.setIsRed				(rs.getBoolean	(4));
		    	  aa.setTopAuto				(rs.getInt		(5));
		    	  aa.setTopMissesAuto		(rs.getInt		(6));
		    	  aa.setTopHotAuto			(rs.getInt		(7));
		    	  aa.setBottomAuto			(rs.getInt		(8));
		    	  aa.setBottomHotAuto		(rs.getInt		(9));
		    	  aa.setDefense				(rs.getBoolean	(10));
		    	  aa.setMovement			(rs.getBoolean	(11));
		    	  aa.setCycle				(rs.getInt		(12));
		    	  aa.setAssitThrow			(rs.getString	(13));
		    	  aa.setThrowFZone			(rs.getInt		(14));
		    	  aa.setThrowTZone			(rs.getInt		(15));
		    	  aa.setThrowGZone			(rs.getInt		(16));
		    	  aa.setAssitCatch			(rs.getString	(17));
		    	  aa.setCatchFZone			(rs.getInt		(18));
		    	  aa.setCatchTZone			(rs.getInt		(19));
		    	  aa.setCatchGZone			(rs.getInt		(20));
		    	  aa.setTrussThrow			(rs.getString	(21));
		    	  aa.setTrussMiss			(rs.getString	(22));
		    	  aa.setTrussCatch			(rs.getString	(23));
		    	  aa.setTopTele				(rs.getInt		(24));
		    	  aa.setTopMissesTele		(rs.getInt		(25));
		    	  aa.setBottomTele			(rs.getInt		(26));
		    	  aa.setCycleGoals			(rs.getString	(27));
		    	  aa.setCycleTime			(rs.getString	(28));
		       
		       //adding to the array
		       results.add(aa);
	    	 }
		   
		    //closes everything
		    rs.close();
		    stmt.close();
		    c.commit();
		    c.close();
	    	
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	    }
	    
	    return results;
	}
	}