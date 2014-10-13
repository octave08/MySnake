package com.iot.mysnake;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewBox extends View {
	
	
	ActivityPlay	m_activity = null;
	
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
	
	private int m_gameMode = 1;
	private final static int INFINITY 	= 0;
	private final static int STAGE 		= 1;
	
	private int m_gameLevel = 0;
	private final static int LEVEL1		= 1;
	private final static int LEVEL2		= 2;
	private final static int LEVEL3		= 3;
	private final static int LEVEL4		= 4;
	private final static int LEVEL5		= 5;
	
	private int m_nextDirection = RIGHT;
	private int m_direction 	= RIGHT;
	private final static int UP			= 1;
	private final static int DOWN		= 2;
	private final static int RIGHT		= 3;
	private final static int LEFT		= 4;
	
	private int m_status = RUNNING;
	private final static int READY		= 0;
	private final static int RUNNING 	= 1;
	private final static int PAUSE		= 2;
	
	int m_gameScore 	= 0;
	int m_appleScore 	= 0;
	
	float touchDownX	=0;
	float touchDownY	=0;
	float touchUpX		=0;
	float touchUpY		=0;
	
	
	private RefreshHandler m_refreshHandler = new RefreshHandler();
	private long m_moveDelay = 600;
	
	
	
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
	
	public void clearTile() 
	{
		for(int y=0;y<SIZE_MAP_Y;y++) 
		{
			for(int x=0;x<SIZE_MAP_X;x++) 
				setTile(BOX_EMPTY, x, y);
		}
	}
	
	
	void initGame() {
		
		if(m_activity != null) ;
			
		initMap();
		initSnake();
	}
	
	//MAP 설정
	private void initMap() {
		m_tileMap = new int[SIZE_MAP_Y][SIZE_MAP_X];
		clearTile();
		
		setWallOnMap();
	}
	
	
	protected void setWallOnMap() {
		for(int x=0;x<SIZE_MAP_X;x++) {
			setTile(BOX_YELLOW, x, 0);
			setTile(BOX_YELLOW, x, SIZE_MAP_Y-1);
		}
		for(int y=1;y<SIZE_MAP_Y-1;y++) {
			setTile(BOX_YELLOW, 0, y);
			setTile(BOX_YELLOW, SIZE_MAP_X-1, y);
		}
	}
	
	//스테이지 설정
	void initStage(int gameLevel) {
		switch(gameLevel) {
		case LEVEL1:
			setLevel1OnMap();
			break;
		case LEVEL2:
			break;
		case LEVEL3:
			break;
		case LEVEL4:
			break;
		case LEVEL5:
			break;
		default :
			break;
		}
	}
	
	protected void setLevel1OnMap() {
		
	}
	
	//snake설정
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
		boolean growSnake	= false;
		
		Character head 		= m_snakeTrail.get(0);
		Character newHead 	= null;
		
		m_direction = m_nextDirection;
		
		switch (m_nextDirection) {
		case RIGHT:
			newHead = new Character(this, head.getM_x()+1, head.getM_y(), Character.DIRECTION_NOT);
			break;
		case LEFT:
			newHead = new Character(this, head.getM_x()-1, head.getM_y(), Character.DIRECTION_NOT);
			break;
		case UP:
			newHead = new Character(this, head.getM_x(), head.getM_y()-1, Character.DIRECTION_NOT);
			break;
		case DOWN:
			newHead = new Character(this, head.getM_x(), head.getM_y()+1, Character.DIRECTION_NOT);
			break;
		}
		
		//collision detect
		//detect collision of BOX_YELLOW
		if(m_tileMap[newHead.getM_y()][newHead.getM_x()] == BOX_YELLOW)
			this.m_activity.goGameover();
		//detect collision of snakeTail
		for(Character c : m_snakeTrail) {
			if(newHead.getM_x() == c.getM_x() &&
					newHead.getM_y() == c.getM_y()) {
				this.m_activity.goGameover();
			}
		}
		//detect collision of BOX_APPLE
		
		for(int i=0;i<m_appleVector.size();i++) {
			Character c = m_appleVector.get(i);
			if(newHead.getM_x() == c.getM_x() &&
					newHead.getM_y() == c.getM_y()) {
				m_appleVector.remove(c);
				addRandomApple();
				
				m_appleScore+=1;
				m_gameScore+=100;
				
				m_moveDelay*=0.5;
				
				growSnake = true;	
			}
		}
		
		m_snakeTrail.add(0, newHead);
		if(growSnake == false)
			m_snakeTrail.remove(m_snakeTrail.size() -1);
		
		int i=0;
		for(Character c : m_snakeTrail) {
			if(i==0)
				setTile(BOX_HEAD, c.getM_x(), c.getM_y());
			else
				setTile(BOX_GREEN, c.getM_x(), c.getM_y());
			i++;
		}	
	}

	
	//Apple설정
	protected void initApple() {
		m_appleVector.clear();
		
		addRandomApple();
		addRandomApple();
		
		setAppleOnMap();
	}
	
	protected void setAppleOnMap() {
		for(Character c : m_appleVector) 
			setTile(BOX_APPLE, c.getM_x(), c.getM_y());
	}
	
	protected void addRandomApple() {
		Character newCharacter = null;
		boolean foundMap = false;
		
		//사과가 충돌하면 반복해라.
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
	
	private void update() {
		if(m_status == RUNNING) {
			this.clearTile();
			setWallOnMap();
			setSnakeOnMap();
			setAppleOnMap();
		}
		
		m_activity.setTextApple(m_appleScore);
		m_activity.setTextScore(m_gameScore);
		
		m_refreshHandler.sleep(m_moveDelay);
	}
	
	
	protected void startGame(int gameMode) {
		selectGame(gameMode);
		readyGame(m_gameMode);
		update();
	}
	
	protected void selectGame(int gameMode) {
		if(gameMode == INFINITY)
			m_gameMode = INFINITY;
		else {
			m_gameMode 	= STAGE;
			m_gameLevel	= gameMode;
		}
	}
	
	protected void readyGame(int gameMode) {
		if(gameMode == INFINITY) {
			initApple();
		}
		else {
			initStage(m_gameLevel);
			initApple();
		}
	}
	
	
	public void setActivity(ActivityPlay activity) {
		m_activity = activity;
	}
	
	public void onPause(Boolean status) {
		if(status)
			m_status = RUNNING;
		else
			m_status = PAUSE;
	}
	
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub	

		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {	
			touchDownX = event.getRawX();
			touchDownY = event.getRawY();
		}
		else if(event.getAction() == MotionEvent.ACTION_UP) {
			touchUpX = event.getRawX();
			touchUpY = event.getRawY();
			
		
			switch(m_direction) {
			case RIGHT:
				if(touchDownY < touchUpY) 
					m_nextDirection = DOWN;
				else
					m_nextDirection = UP;
				break;
			case LEFT:
				if(touchDownY < touchUpY) 
					m_nextDirection = DOWN;
				else
					m_nextDirection = UP;
				break;
			case UP:
				if(touchDownX < touchUpX) 
					m_nextDirection = RIGHT;
				else
					m_nextDirection = LEFT;
				break;
			case DOWN:
				if(touchDownX < touchUpX) 
					m_nextDirection = RIGHT;
				else
					m_nextDirection = LEFT;
				break;
			}
		}
			
		return true;
	}
		

	
	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			ViewBox.this.update();
			ViewBox.this.invalidate();
		}
		
		public void sleep(long delayMills) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMills);
		}
		
	}


	
}
