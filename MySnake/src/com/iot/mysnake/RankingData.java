package com.iot.mysnake;

public class RankingData {
	
	private long 	ranking = 0;
	private long 	apple 	= 0;
	private long 	score 	= 0;
	private long 	time 	= 0;
	private String 	name 	= null;
	
	public RankingData() {
		super();
	}
	
	
	
	public long getRanking() 			{return ranking;}
	public void setRanking(int ranking) {this.ranking = ranking;}
	public long getApple() 				{return apple;}
	public void setApple(int apple) 	{this.apple = apple;}
	public long getScore() 				{return score;}
	public void setScore(int score) 	{this.score = score;}
	public long getTime() 				{return time;}
	public void setTime(int time) 		{this.time = time;}
	public String getName() 			{return name;}
	public void setName(String name) 	{this.name = name;}
	
}
