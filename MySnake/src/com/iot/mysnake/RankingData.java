package com.iot.mysnake;

public class RankingData {
	
	private long 	ranking = 0;
	private long 	apple 	= 0;
	private long 	score 	= 0;
	private long 	m10		= 0;
	private long 	m01		= 0;
	private long 	s10		= 0;
	private long 	s01		= 0;
	private long 	ms10	= 0;
	private long 	ms01	= 0;
	private String 	name 	= null;
	
	public RankingData() {
		super();
	}
	
	
	
	public long getRanking() 			{return ranking;}
	public void setRanking(long ranking) {this.ranking = ranking;}
	public long getApple() 				{return apple;}
	public void setApple(long apple) 	{this.apple = apple;}
	public long getScore() 				{return score;}
	public void setScore(long score2) 	{this.score = score2;}
	public long getTime() 				{return time;}
	public void setTime(int time) 		{this.time = time;}
	public String getName() 			{return name;}
	public void setName(String name) 	{this.name = name;}



	public long getM10() {
		return m10;
	}



	public void setM10(long m10) {
		this.m10 = m10;
	}
	
}
