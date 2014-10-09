package com.iot.mysnake;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class ViewBox extends View {
	
	
	ActivityPlay	m_Activity = null;
	
	Vector<Character>	m_snakeTrail		= new Vector<Character>();
	Vector<Character> 	m_appleVector		= new Vector<Character>();
	
	Random random = new Random(System.currentTimeMillis());
	
	private int m_ViewWidth, m_ViewHeight;
	private int m_XMargin, m_YMargin;
	
	private final static int TILE_SIZE = 30;
	
	private final static int BOX_EMPTY 		= 0;
	private final static int BOX_YELLOW		= 1;
	private final static int BOX_GREEN		= 2;
	private final static int BOX_HEAD		= 3;
	private final static int BOX_APPLE		= 4;
	
	
	private Drawable m_drawBoxEmpty		= null;
	private Drawable m_drawBoxYellow	= null;
	private Drawable m_drawBoxGreen		= null;
	private Drawable m_drawBoxHead		= null;
	private Drawable m_drawBoxApple		= null;
	

	private int[][] m_tileMap;
	public static final int SIZE_MAP_X		= 26;
	public static final int SIZE_MAP_Y		= 13; 
	

	
	public ViewBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initDrawable();
		
		initGame();
		
		
	}
	
	protected void initDrawable() {
		m_drawBoxEmpty 	= this.getResources().getDrawable(R.drawable.boxbg);
		m_drawBoxYellow = this.getResources().getDrawable(R.drawable.block1);
		m_drawBoxGreen 	= this.getResources().getDrawable(R.drawable.block2);
		m_drawBoxHead 	= this.getResources().getDrawable(R.drawable.block3);
		m_drawBoxApple	= this.getResources().getDrawable(R.drawable.block4);
	}
	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		m_ViewWidth 	= this.getWidth();
		m_ViewHeight	= this.getHeight();
		
		m_XMargin = (m_ViewWidth - TILE_SIZE*SIZE_MAP_X)/2;
		m_YMargin = (m_ViewHeight - TILE_SIZE*SIZE_MAP_Y)/2;
				
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Drawable drawable = null;
			
		for(int y=0;y<SIZE_MAP_Y;y++) 
		{
			for(int x=0;x<SIZE_MAP_X;x++) 
			{
				
				switch(m_tileMap[y][x]) {
				
				case BOX_EMPTY:
					drawable = m_drawBoxEmpty;
					break;
				case BOX_YELLOW:
					drawable = m_drawBoxYellow;
					break;
				case BOX_GREEN:
					drawable = m_drawBoxGreen;
					break;
				case BOX_HEAD:
					drawable = m_drawBoxHead;
					break;
				case BOX_APPLE:
					drawable = m_drawBoxApple;
					break;
				default:
					break;
				
				}	
				drawable.setBounds(m_XMargin+x*TILE_SIZE, m_YMargin+y*TILE_SIZE
								  ,m_XMargin+(x+1)*TILE_SIZE, m_YMargin+(y+1)*TILE_SIZE);
				drawable.draw(canvas);
		
			} 
		} 
		
	} 

	
	protected void setTile(int index, int x, int y) {
		m_tileMap[y][x] = index;
	}
	
	protected void clearTile() 
	{
		for(int y=0;y<SIZE_MAP_Y;y++) 
		{
			for(int x=0;x<SIZE_MAP_X;x++) 
				setTile(BOX_EMPTY, x, y);
		}
	}
	
	
	void initGame() {
		
		if(m_Activity != null) ;
			
		initMap();
		initSnake();
	}
	
	
	private void initMap() {
		m_tileMap = new int[SIZE_MAP_Y][SIZE_MAP_X];
		clearTile();
		
		setWallsOnMap();
		setApplesOnMap();
	}
	
	
	protected void setWallsOnMap() {
		for(int x=0;x<SIZE_MAP_X;x++) {
			setTile(BOX_YELLOW, x, 0);
			setTile(BOX_YELLOW, x, SIZE_MAP_Y-1);
		}
		for(int y=1;y<SIZE_MAP_Y-1;y++) {
			setTile(BOX_YELLOW, 0, y);
			setTile(BOX_YELLOW, SIZE_MAP_X-1, y);
		}
	}
	
	protected void setApplesOnMap() {
		addRandomApple();
		addRandomApple();
		
		for(Character c : m_appleVector) 
			setTile(BOX_APPLE, c.getM_x(), c.getM_y());
	}
	
	protected void addRandomApple() {
		Character newCharacter = null;
		boolean foundMap = false;
		
		while(foundMap == false) {
			
			Random rnd = new Random();
			int newX = 2 + rnd.nextInt(SIZE_MAP_X -3);
			int newY = 2 + rnd.nextInt(SIZE_MAP_Y -3);
			newCharacter = new Character(this, newX, newY, Character.DIRECTION_NOT);
			
			boolean collision = false;
			//생설될 사과와 Snake의 충돌검사
			for(int i=0;i<m_snakeTrail.size();++i) {
				if(m_snakeTrail.get(i).equals(newCharacter)) {
					collision = true;
				}
			}
			//생성될 사과와 사과의 충돌검사
			if(m_appleVector.size() != 0 && m_appleVector.get(0).equals(newCharacter)) {
				collision = true;
			}
			
			foundMap = !collision;
			
		}
		
		m_appleVector.add(newCharacter);
	}
	
	
	void initSnake() {
		m_snakeTrail.clear();
		
		m_snakeTrail.add(new Character(this, 7, 3, Character.DIRECTION_NOT));
		m_snakeTrail.add(new Character(this, 6, 3, Character.DIRECTION_NOT));
		m_snakeTrail.add(new Character(this, 5, 3, Character.DIRECTION_NOT));
		m_snakeTrail.add(new Character(this, 4, 3, Character.DIRECTION_NOT));
		m_snakeTrail.add(new Character(this, 3, 3, Character.DIRECTION_NOT));
		
		setSnakeOnMap();
	}
	
	protected void setSnakeOnMap() {
		int i=0;
		for(Character c : m_snakeTrail) {
			if(i==0)
				setTile(BOX_HEAD, c.getM_x(), c.getM_y());
			else
				setTile(BOX_GREEN, c.getM_x(), c.getM_y());
			i++;
		}	
	}
	
 
}
