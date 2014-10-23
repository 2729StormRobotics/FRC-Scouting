
public class AerialAssit {
	
	//array of all the columns and their names...is a reference
/*									"team_num",				//team number
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
									"bottom_tele"			//all bottom goals made
									"cycle_goals"			//which goal was made in each cycle (array)
																 * 	-1 = bottom goal
																 * 	 0 = nothing
																 * 	 1 = top goal
																 
					
    */
	private int team_num=0, match_num=0,
			
				top_auto=0, topMisses_auto=0, topHot_auto=0, 
				bottom_auto=0, botHot_auto,
				
				cycle=0,
				
				throw_fzone=0,throw_tzone=0,throw_gzone=0,
				catch_fzone=0,catch_tzone=0,catch_gzone=0,
				
				top_tele=0,topMisses_tele=0,bottom_tele=0;
	
	private String assit_throw, assit_catch, truss_throw, truss_miss, truss_catch, cycle_goals, cycle_time;
	private boolean is_red=false,defense=false,movement=false;
	
	
	public AerialAssit() {
		
	}
	///////////////////////////////////////set methods//////////////////////////////////////////////
	public void setTeamNum(int i)		{this.team_num = i;}
	public void setMatchNum(int i)		{this.match_num = i;}
	public void setIsRed(boolean i)		{this.is_red = i;}
	public void setTopAuto(int i)		{this.top_auto = i;}
	public void setTopMissesAuto(int i)	{this.topMisses_auto = i;}
	public void setTopHotAuto(int i)	{this.topHot_auto = i;}
	public void setBottomAuto(int i)	{this.bottom_auto = i;}
	public void setBottomHotAuto(int i)	{this.botHot_auto = i;}
	public void setDefense(boolean i)	{this.defense = i;}
	public void setMovement(boolean i)	{this.movement = i;}
	public void setCycle(int i)			{this.cycle = i;}
	public void setAssitThrow(String i)	{this.assit_throw = i;}
	public void setThrowFZone(int i)	{this.throw_fzone = i;}
	public void setThrowTZone(int i)	{this.throw_tzone = i;}
	public void setThrowGZone(int i)	{this.throw_gzone = i;}
	public void setAssitCatch(String i)	{this.assit_catch = i;}
	public void setCatchFZone(int i)	{this.catch_fzone = i;}
	public void setCatchTZone(int i)	{this.catch_tzone = i;}
	public void setCatchGZone(int i)	{this.catch_gzone = i;}
	public void setTrussThrow(String i)	{this.truss_throw = i;}
	public void setTrussMiss(String i)	{this.truss_miss = i;}
	public void setTrussCatch(String i)	{this.truss_catch = i;}
	public void setTopTele(int i)		{this.top_tele = i;}
	public void setTopMissesTele(int i)	{this.topMisses_tele = i;}
	public void setBottomTele(int i)	{this.bottom_tele = i;}
	public void setCycleGoals(String i)	{this.cycle_goals = i;}
	public void setCycleTime(String i)	{this.cycle_time = i;}
	
	///////////////////////////////////////get methods//////////////////////////////////////////////
	public int getTeamNum()				{return team_num;}
	public int getMatchNum()			{return match_num;}
	public boolean getIsRed()			{return is_red;}
	public int getTopAuto()				{return top_auto;}
	public int getTopMissesAuto()		{return topMisses_auto;}
	public int getTopHotAuto()			{return topHot_auto;}
	public int getBottomAuto()			{return bottom_auto;}
	public int getBottomHotAuto()		{return botHot_auto;}
	public boolean getDefense()			{return defense;}
	public boolean getMovement()		{return movement;}
	public int getCycle()				{return cycle;}
	public String getAssitThrow()		{return assit_throw;}
	public int getThrowFZone()			{return throw_fzone;}
	public int getThrowTZone()			{return throw_tzone;}
	public int getThrowGZone()			{return throw_gzone;}
	public String getAssitCatch()		{return assit_catch;}
	public int getCatchFZone()			{return catch_fzone;}
	public int getCatchTZone()			{return catch_tzone;}
	public int getCatchGZone()			{return catch_gzone;}
	public String getTrussThrow()		{return truss_throw;}
	public String getTrussMiss()		{return truss_miss;}
	public String getTrussCatch()		{return truss_catch;}
	public int getTopTele()				{return top_tele;}
	public int getTopMissesTele()		{return topMisses_tele;}
	public int getBottomTele()			{return bottom_tele;}
	public String getCycleGoals()		{return cycle_goals;}
	public String getCycleTime()		{return cycle_time;}

}
