package com.scouter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class TeamNumbers {
	
	private static String total;
	
	public TeamNumbers(Context context) {
		
		try{
			
			AssetManager manager = context.getAssets();
			InputStream input = manager.open("team_numbers.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			total = reader.readLine();
			reader.close();
			
		}catch(IOException ex){

		}
		
	}
	
	public static boolean isATeamNumber(int team) {
		String teamNum = "," + team + ",";
		
		if(total.contains(teamNum))
			return true;
		else
			return false;

	}

}
