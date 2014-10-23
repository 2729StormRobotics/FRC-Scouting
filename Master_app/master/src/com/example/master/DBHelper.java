package com.example.master;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME="Aerial_Assist";
	private static final String TABLE_NAME="aerial_assist";
	
    private static final int DATABASE_VERSION = 1;
	
	//names of columns of all needed data
    private static final String ROW_ID = "ROWID";
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
										"assist_throw",			//assist passes (array)
										"throw_fzone",			//passes from feeder zone
										"throw_tzone",			//from truss zone
										"throw_gzone",			//from goal zone
										"assist_catch",			//assist catches (array)
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
	
	public DBHelper(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {		  
		//creating the table "aerial_assist" in the database
		String COMMAND = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
 			   "("+ROW_ID+" 					INTEGER		PRIMARY KEY		AUTOINCREMENT," + //needed to make every data entry a unique entry...starts at 1
 			   " "+columnNames[0]+" 			INT 		    			NOT NULL," +
 			   " "+columnNames[1]+"           	INT    						NOT NULL," + 
               " "+columnNames[2]+"             INT    						NOT NULL," + 
               " "+columnNames[3]+"             INT							NOT NULL," + 
               " "+columnNames[4]+"         	INT							NOT NULL," +
               " "+columnNames[5]+" 			INT 				    	NOT NULL," +
 			   " "+columnNames[6]+"           	INT    						NOT NULL," + 
               " "+columnNames[7]+"           	INT    						NOT NULL," + 
               " "+columnNames[8]+" 			INT					    	NOT NULL," +
 			   " "+columnNames[9]+"           	INT	  						NOT NULL," + 
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
		  db.execSQL(COMMAND);
		    
		 }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //testing
 
        // Create tables again
        onCreate(db);
	}
	
	/********************************* DATABASE INTERACTION METHODS *****************************/
	
	/////////////////////////////////////////// ADDERS ///////////////////////////////////////////
	public void addData(AerialAssist aerialAssist) {
		//adds all data from qr code into the database
		
	    SQLiteDatabase db = this.getWritableDatabase();
	 	 
	    //defining value to put into database
	    ContentValues values = new ContentValues();
	    values.put(columnNames[0], aerialAssist.getTeamNum());
	    values.put(columnNames[1], aerialAssist.getMatchNum()); 
	    values.put(columnNames[2], aerialAssist.getIsRed()); 
	    values.put(columnNames[3], aerialAssist.getTopAuto()); 
	    values.put(columnNames[4], aerialAssist.getTopMissesAuto()); 
	    values.put(columnNames[5], aerialAssist.getTopHotAuto()); 
	    values.put(columnNames[6], aerialAssist.getBottomAuto()); 
	    values.put(columnNames[7], aerialAssist.getBottomHotAuto()); 
	    values.put(columnNames[8], aerialAssist.getDefense()); 
	    values.put(columnNames[9], aerialAssist.getMovement()); 
	    values.put(columnNames[10], aerialAssist.getCycle()); 
	    values.put(columnNames[11], aerialAssist.getAssistThrow()); 
	    values.put(columnNames[12], aerialAssist.getThrowFZone()); 
	    values.put(columnNames[13], aerialAssist.getThrowTZone()); 
	    values.put(columnNames[14], aerialAssist.getThrowGZone()); 
	    values.put(columnNames[15], aerialAssist.getAssistCatch()); 
	    values.put(columnNames[16], aerialAssist.getCatchFZone()); 
	    values.put(columnNames[17], aerialAssist.getCatchTZone()); 
	    values.put(columnNames[18], aerialAssist.getCatchGZone()); 
	    values.put(columnNames[19], aerialAssist.getTrussThrow()); 
	    values.put(columnNames[20], aerialAssist.getTrussMiss()); 
	    values.put(columnNames[21], aerialAssist.getTrussCatch()); 
	    values.put(columnNames[22], aerialAssist.getTopTele()); 
	    values.put(columnNames[23], aerialAssist.getTopMissesTele()); 
	    values.put(columnNames[24], aerialAssist.getBottomTele());  
	    values.put(columnNames[25], aerialAssist.getCycleGoals()); 
	    values.put(columnNames[26], aerialAssist.getCycleTime()); 
	 
	    // Inserting all values into next Row
	    db.insert(TABLE_NAME, null, values);
	    db.close(); // Closing database connection
	}
	public void addCycleTime(String input) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 	 
	    //defining value to put into database
	    ContentValues values = new ContentValues();
	    values.put(columnNames[26], input); 
	 
	    // Inserting all values into next Row
	    db.insert(TABLE_NAME, null, values);
	    db.close(); // Closing database connection
	}
	/////////////////////////////////////////// GETTERS ///////////////////////////////////////////
	public ArrayList<AerialAssist> getAllData() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        //String result = "";//testing
        ArrayList<AerialAssist> aaArray = new ArrayList<AerialAssist>();
        if (cursor.moveToFirst()) {
            do {
            	// (int i=0; i<columnNames.length+1; i++){ //num of columns in database is ROW_ID + columnNames.length
                	//result += cursor.getString(i) + " , ";//testing
                	
                //}
            	AerialAssist aa = new AerialAssist( cursor.getInt(0),
            										cursor.getInt(1),
            										cursor.getInt(2),
            										cursor.getInt(3),
            										cursor.getInt(4),
            										cursor.getInt(5),
            										cursor.getInt(6),
            										cursor.getInt(7),
            										cursor.getInt(8),
            										cursor.getInt(9),
            										cursor.getInt(10),
            										cursor.getInt(11),
            										cursor.getString(12),
            										cursor.getInt(13),
            										cursor.getInt(14),
            										cursor.getInt(15),
            										cursor.getString(16),
            										cursor.getInt(17),
            										cursor.getInt(18),
            										cursor.getInt(19),
            										cursor.getString(20),
            										cursor.getString(21),
            										cursor.getString(22),
            										cursor.getInt(23),
            										cursor.getInt(24),
            										cursor.getInt(25),
            										cursor.getString(26),
            										cursor.getString(27)
            										);
            	aaArray.add(aa);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return aaArray;
    }
	
	//get list of existing teams in database
	public ArrayList<Integer> getAllTeamNums(){
		ArrayList<Integer> teams =  new ArrayList<Integer>();
		boolean existInList = false;
		
        String selectQuery = "SELECT "+columnNames[0]+" FROM " + TABLE_NAME+" ORDER BY "+columnNames[0]+" ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	for (int i = 0; i < teams.size(); i++){
            		if (teams.get(i) == cursor.getInt(0)){
            			existInList = true;
            			break;
            		}else{
            			existInList = false;
            		}
            	}
            	if (!existInList){
                	teams.add(cursor.getInt(0));
            	}
            								
            } while (cursor.moveToNext());
        }
        
        return teams;
	}
	public ArrayList<AerialAssist> getTeamData(int teamNum){
		ArrayList<AerialAssist> result =  new ArrayList<AerialAssist>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
            	if (cursor.getInt(1) == teamNum){
	            	AerialAssist aa = new AerialAssist( cursor.getInt(0),
														cursor.getInt(1),
														cursor.getInt(2),
														cursor.getInt(3),
														cursor.getInt(4),
														cursor.getInt(5),
														cursor.getInt(6),
														cursor.getInt(7),
														cursor.getInt(8),
														cursor.getInt(9),
														cursor.getInt(10),
														cursor.getInt(11),
														cursor.getString(12),
														cursor.getInt(13),
														cursor.getInt(14),
														cursor.getInt(15),
														cursor.getString(16),
														cursor.getInt(17),
														cursor.getInt(18),
														cursor.getInt(19),
														cursor.getString(20),
														cursor.getString(21),
														cursor.getString(22),
														cursor.getInt(23),
														cursor.getInt(24),
														cursor.getInt(25),
														cursor.getString(26),
														cursor.getString(27)
														);
	            	result.add(aa);
            	}
            								
            } while (cursor.moveToNext());
        }
		
		return result;
	}
	//for xml file formating...xml file to export to excel sheet
	public String getAllXMLAuto(){
		String result = "";
		
		ArrayList<AerialAssist> aaArray = getAllData();
		
		for (AerialAssist a:aaArray)
			result += a.getXMLAutoRecord();
		
		return result;
	}
	//for xml file formating...xml file to export to excel sheet
	public String getAllXMLTele(){
		String result = "";
		
		ArrayList<AerialAssist> aaArray = getAllData();
		
		for (AerialAssist a:aaArray)
			result += a.getXMLTeleRecord();
		
		return result;
	}
	//for xml file formating...xml file to export to excel sheet
	public String getXMLAuto(int row){
		ArrayList<AerialAssist> aaArray = getAllData();
		
		String result = aaArray.get(row).getXMLAutoRecord();
		
		return result;
	}
	//for xml file formating...xml file to export to excel sheet
	public String getXMLTele(int row){
		ArrayList<AerialAssist> aaArray = getAllData();
		
		String result = aaArray.get(row).getXMLTeleRecord();
		
		return result;
	}
	
	public int getNumRows() {
        // Remember to subtract by 1 if using with an array
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and finding last rowID
        int numRows = 0;
        if (cursor.moveToFirst()) {
            do {
            	numRows = cursor.getInt(0);
            								
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return numRows;
    }
	
	// methods to immplement later
	/*// Updating single contact
	public int updateContact(Contact contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, contact.getName());
	    values.put(KEY_PH_NO, contact.getPhoneNumber());
	 
	    // updating row
	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(contact.getID()) });
	}*/
	// Deleting single contact
	public void deleteEntry(int row_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    db.delete(TABLE_NAME, ROW_ID + " = ?",
	            new String[] { String.valueOf(row_id) }); // deletes entry where ROW_ID = row_id
	    db.close();
	}
	public void clearDB(){
	    SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+ROW_ID+" > -1;");
        db.close();
	}
}
