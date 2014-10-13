package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPlay extends Activity {
	
	private int m_gameMode;
	private final static int INFINITY	= 0;
	private final static int STAGE 		= 1;
	
	private final static int LEVEL1		= 1;
	private final static int LEVEL2		= 2;
	private final static int LEVEL3		= 3;
	private final static int LEVEL4		= 4;
	private final static int LEVEL5		= 5;

	private Intent m_intent = null;
	
	ViewBox m_viewBox = null;
	
	TextView m_textViewApple 	= null;
	TextView m_textViewScore 	= null;
	ImageView m_ImagePause		= null;
	
	private boolean m_status = RUNNING;
	private final static Boolean RUNNING 	= true;
	private final static Boolean PAUSE		= false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_play);
		
		m_textViewApple = (TextView)findViewById(R.id.textApple);
		m_textViewScore = (TextView)findViewById(R.id.textScore);
		m_ImagePause = (ImageView)findViewById(R.id.imagePause);
		m_ImagePause.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_status = !m_status;
				m_viewBox.onPause(m_status);
			}	
		});
		
		
		m_intent = getIntent();
		m_gameMode = m_intent.getIntExtra("gamemode", STAGE);
		
		m_viewBox = (ViewBox)findViewById(R.id.viewBox);
		m_viewBox.setActivity(this);
		m_viewBox.startGame(m_gameMode);
		
		
			
	}
	
	
	public void goGameover() {
		
		Intent intent1 = new Intent(ActivityPlay.this, ActivityGameover.class);
		intent1.putExtra("gameScore", 3);
		//intent.putExtra("appleScore", test);
		//intent.putExtra("time", test);
		startActivity(intent1);
			
		finish();
			
	}
	
	public void setTextApple(int appleScore) {
		m_textViewApple.setText(Integer.toString(appleScore));
	}
	public void setTextScore(int gameScore) {
		m_textViewScore.setText(Integer.toString(gameScore));
	}
	
	


}
