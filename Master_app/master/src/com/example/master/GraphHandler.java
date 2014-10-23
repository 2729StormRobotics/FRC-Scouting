package com.example.master;


import java.util.ArrayList;


public class GraphHandler {

	//VARIABLES//
	private ArrayList<AerialAssist> matches = new ArrayList<AerialAssist>();
	private int teamNumber;
	/////////////
		
	
	//CONSTRUCTORS//
	public GraphHandler(ArrayList<AerialAssist> list) {
		matches = list;
		teamNumber = matches.get(0).getTeamNum();
	}
	public GraphHandler(AerialAssist[] array) {
		for(AerialAssist a:array)
			matches.add(a);
		teamNumber = matches.get(0).getTeamNum();
	}
	////////////////
	
	//MISC GETTING METHODS//
	public int getTeamNum() {return teamNumber;}
	public int getMatchNum() {return matches.size();}
	public ArrayList<AerialAssist> getRawTeamData(){return matches;}
	
	public int getSum(int[] array) {
		
		int sum = 0;
		
		for(int i: array)
			sum += i;
		
		return sum;
		
	}
	public double getSum(double[] array) {
		
		double sum = 0;
		
		for(double i: array)
			sum += i;
		
		return sum;
		
	}
	
	public String arrayToString(int[] array) {
		
		String result = "[";
		
		for(int i=0; i< array.length; i++){
			
			if(i == array.length - 1)
				result += array[i] + "]";
			else
				result += array[i] + ",";
			
		}
		
		return result;
		
	}
	public String arrayToString(double[] array) {
		
		String result = "[";
		
		for(int i=0; i< array.length; i++){
			
			if(i == array.length - 1)
				result += array[i] + "]";
			else
				result += array[i] + ",";
			
		}
		
		return result;
	
	}
	public int[] convertToIntegers(double[] a){
		int [] result = new int[a.length];
		
		for (int i = 0; i < a.length; i++)
			result[i] = (int) Math.round(a[i]);
		
			
		return result;
	}
	public double getMin(double a, double b){
		if (a<b)
			return a;
		else
			return b;
	}
	public int getMin(int a, int b){
		if (a<b)
			return a;
		else
			return b;
	}
	//////////////////////// getting the average data field f
	public double getAverageTrussThrowArray(){
		//sum
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += getSum(matches.get(i).getTrussThrowArray()) * 1.0;
		
		return result/matches.size();
	}
	public double getAverageTrussMissArray(){
		//sum
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += getSum(matches.get(i).getTrussMissArray()) * 1.0;
	
		return result/matches.size();
	}
	public double getAverageCatchFZone(){
		double result = 0;
	
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getCatchFZone() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageThrowFZone(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getThrowFZone() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageAssistThrowArray(){
		//getSum
		double result = 0;
	
		for (int i = 0; i < matches.size(); i++)
			result += getSum(matches.get(i).getAssistThrowArray()) * 1.0;
	
		return result/matches.size();
	}
	public double getAverageAssistCatchArray(){
		//getSum
		double result = 0;
	
		for (int i = 0; i < matches.size(); i++)
			result += getSum(matches.get(i).getAssistCatchArray()) * 1.0;
	
		return result/matches.size();
	}
	public double getAverageCycle(){
		double result = 0;
	
		for (int i = 0; i < matches.size(); i++)
			result += (matches.get(i).getCycle() * 1.0);
	
		return result/matches.size();
	}
	public double getAverageBottomAuto(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getBottomAuto() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageTopAuto(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getTopAuto() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageTrussCatchArray(){
		//getSum
		double result = 0;
	
		for (int i = 0; i < matches.size(); i++)
			result += getSum(matches.get(i).getTrussCatchArray()) * 1.0;
	
		return result/matches.size();
	}
	public double getAverageCatchTZone(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getCatchTZone() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageThrowTZone(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getThrowTZone() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageTopTele(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getTopTele() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageTopMissesAuto(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getTopMissesAuto() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageTopMissesTele(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getTopMissesTele() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageCatchGZone(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getCatchGZone() * 1.0;
	
		return result/matches.size();
	}
	public double getAverageThrowGZone(){
		double result = 0;
		
		for (int i = 0; i < matches.size(); i++)
			result += matches.get(i).getThrowGZone() * 1.0;
	
		return result/matches.size();
	}
	
	//////////////////////////////
	
	//GETS DATA FROM SINGLE MATCH//
	public double[] getFeederData() {
		
		double[] data = new double[4];
		
		data[0] = ((this.getMin(this.getAverageTrussThrowArray(), 20.0))/20.0)*20;
		
		try{
			data[1] = ((this.getAverageCatchFZone() + this.getAverageThrowFZone()) / 
					  (this.getAverageAssistThrowArray() + this.getAverageAssistCatchArray() * 1.0)) * 30;
		}catch(Exception e){
			
		}
	
		data[2] = (this.getMin(this.getAverageCycle(), 6.0)/6.0) * 25;
			
		data[3] = (this.getMin((this.getAverageBottomAuto() + this.getAverageTopAuto()), 75.0)/75.0) * 25;
	
		return data;
		
	}
	
	public double[] getCenterData() {
		
		double[] data = new double[5];
		
		data[0] = ((this.getMin(this.getAverageTrussThrowArray(), 20.0))/20.0)*20;
		
		data[1] = (this.getMin(this.getAverageTrussCatchArray(),6.0) / 6.0) * 5;
		
		try{
			data[2] = ((this.getAverageCatchTZone() + this.getAverageThrowTZone()) / 
					  (this.getAverageAssistThrowArray() + this.getAverageAssistCatchArray() * 1.0)) * 30;
		}catch(Exception e){
			
		}
		
		data[3] = (this.getMin(this.getAverageCycle(), 6.0)/6.0) * 25;
		
		data[4] = (this.getMin((this.getAverageBottomAuto() + this.getAverageTopAuto()), 75.0)/75.0) * 25;
		
		return data;
		
	}
	
	public double[] getGoalData() {
	
		double[] data = new double[6];
	
		data[0] = ((this.getMin(this.getAverageTrussThrowArray(), 20.0))/20.0) *20;
		
		data[1] = (this.getMin(this.getAverageTrussCatchArray(), 6.0)/6.0) * 5;
		
		try{
			data[2] = (((this.getAverageTopAuto() + this.getAverageTopTele()) / 
					  (this.getAverageTopAuto() + this.getAverageTopTele() + 
					  this.getAverageTopMissesAuto() + this.getAverageTopMissesTele() )) * 15);	
		}catch(Exception e) {
	
		}
		
		try{
			data[3] = ((this.getAverageCatchGZone() + this.getAverageThrowGZone()) / 
				      (this.getAverageAssistThrowArray() + this.getAverageAssistCatchArray()) * 1.0 * 30);
		
		
		}catch(Exception e) {
		
		}	
		
		data[4] = (this.getMin(this.getAverageCycle(), 6.0)/6.0) * 25;
		
		data[5] = (this.getMin((this.getAverageBottomAuto() + this.getAverageTopAuto()), 75.0)/75.0) * 25;
		
		return data;
	
	}
	
	//////////////////final getters//////////////////////
	public int[] getTotalMatchData() {
	
		double[] data = new double[3];
		
		double[] feederData = this.getFeederData(),
				 centerData = this.getCenterData(),
				 goalData = this.getGoalData();
		
		data[0] =  (int) Math.round(this.getSum(feederData) / 3.0);
		data[1] =  (int) Math.round(this.getSum(centerData) / 3.0);
		data[2] =  (int) Math.ceil(this.getSum(goalData) / 3.0);
	
		return this.convertToIntegers(data);
	
	}
	public int[] getGoalDataInt(){
		return this.convertToIntegers(this.getGoalData());		
	}
	public int[] getCenterDataInt(){
		return this.convertToIntegers(this.getCenterData());		
	}
	public int[] getFeederDataInt(){
		return this.convertToIntegers(this.getFeederData());
	}
	
	///getters of sum of things///
	public int[] getTeamAuto(){
		//array consisting of sums of: moved, autobottomhot, autobottom, autotophot, autotop, autotopmiss
		int[] result = new int[6];
		
		for (int i = 0; i < matches.size(); i++)
			result[0]+=(matches.get(i).getMovement());
		
		for (int i = 0; i < matches.size(); i++)
			result[1]+=(matches.get(i).getBottomHotAuto());
		
		for (int i = 0; i < matches.size(); i++)
			result[2]+=(matches.get(i).getBottomAuto()); 
	
		for (int i = 0; i < matches.size(); i++)
			result[3]+=(matches.get(i).getTopHotAuto());
	
		for (int i = 0; i < matches.size(); i++)
			result[4]+=(matches.get(i).getTopAuto());
	
		for (int i = 0; i < matches.size(); i++)
			result[5]+=(matches.get(i).getTopMissesAuto());
		
		return result;
	}
	public int[] getTeamAssists(){
		//feeder, truss, goal
		int[] result = new int[6];
		
		for (int i = 0; i < matches.size(); i++)
			result[0]+=(matches.get(i).getThrowFZone());
		
		for (int i = 0; i < matches.size(); i++)
			result[1]+=(matches.get(i).getThrowTZone());
	
		for (int i = 0; i < matches.size(); i++)
			result[2]+=(matches.get(i).getThrowGZone());
	
		for (int i = 0; i < matches.size(); i++)
			result[3]+=(matches.get(i).getCatchFZone());
		
		for (int i = 0; i < matches.size(); i++)
			result[4]+=(matches.get(i).getCatchTZone());
	
		for (int i = 0; i < matches.size(); i++)
			result[5]+=(matches.get(i).getCatchGZone());
		
		return result;
	}
	public int getTotalCycleTime(){
		int total = 0;
		
		for (int i = 0; i < matches.size(); i++)
			total+=(this.getSum(matches.get(i).getCycleTimeArray()));
		
		return total;
	}
	public int[] getTotalTrussShotsAndCatches(){
		//shots made, caught, or missed when attempting truss
		int[] result = new int[3];
		
		for (int i = 0; i < matches.size(); i++)
			result[0]+=(getSum(matches.get(i).getTrussThrowArray()));
		
		for (int i = 0; i < matches.size(); i++)
			result[1]+=(getSum(matches.get(i).getTrussCatchArray()));
	
		for (int i = 0; i < matches.size(); i++)
			result[2]+=(getSum(matches.get(i).getTrussMissArray()));
	
		
		return result;
	}
	public int getAccuracy(){
		int result = 0;
		int hit = 0;
		int miss = 0;
		
	
		for (int i = 0; i < matches.size(); i++){
			hit+=((matches.get(i).getTopTele()));
			miss+=((matches.get(i).getTopMissesTele()));
		}
		
		result = (int)((hit*1.0/((miss+hit)*1.0))*100);
		
		return result;
	}
	public int[] getTeamGoals(){
		//misses, bottom, top, getAccuracy
		int[] result = new int[4];
	
		for (int i = 0; i < matches.size(); i++)
			result[0]+=((matches.get(i).getTopMissesTele()));
		
		for (int i = 0; i < matches.size(); i++)
			result[1]+=((matches.get(i).getBottomTele()));
	
		for (int i = 0; i < matches.size(); i++)
			result[2]+=((matches.get(i).getTopTele()));
		
		result[3] = getAccuracy();
		
		return result;
	}
	
	//////////////////////////////////////////////////////
	
	}