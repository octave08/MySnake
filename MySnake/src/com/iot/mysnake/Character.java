package com.iot.mysnake;

public class Character {
	
	public final static int DIRECTION_NOT		= 0;
	public final static int DIRECTION_UP		= 1;
	public final static int DIRECTINO_DOWN		= 2;
	public final static int DIRECTINO_LEFT		= 3;
	public final static int DIRECTINO_RIGHT		= 4;
	
	private int m_x 		= 0;
	private int m_y			= 0;
	private ViewBox m_view	= null;
	private int m_direction	= 0;
	
	public Character(ViewBox view, int x, int y, int direction) {
		m_view 		= view;
		m_x 		= x;
		m_y 		= y;
		m_direction	= 0;
	}

	public int getM_x() 			{return m_x;}
	public void setM_x(int m_x) 	{this.m_x = m_x;}
	public int getM_y() 			{return m_y;}
	public void setM_y(int m_y) 	{this.m_y = m_y;}
	
	

}
