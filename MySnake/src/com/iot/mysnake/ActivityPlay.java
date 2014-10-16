package com.iot.mysnake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
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
	
	
	private int m_status = READY;
	private final static int READY		= 0;
	private final static int RUNNING 	= 1;
	private final static int PAUSE		=-1;
	private final static int GAMEOVER	= 2;
	
	private ThreadTime m_thread;
	private Handler m_handler;
		
	private int m_m10	= 0;	private ImageView m_imgM10;
	private int m_m01	= 0;	private ImageView m_imgM01;
	private int m_s10	= 0;	private ImageView m_imgS10;
	private int m_s01	= 0;	private ImageView m_imgS01;
	private int m_ms10	= 0;	private ImageView m_imgMs10;
	private int m_ms01	= 0;	private ImageView m_imgMs01;
	private int[] m_imgNum = new int[10];

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_play);
		
		//get Info from Intro
		m_intent = getIntent();
		m_gameMode = m_intent.getIntExtra("gamemode", STAGE);
		
		m_textViewApple = (TextView)findViewById(R.id.textApple);
		m_textViewScore = (TextView)findViewById(R.id.textScore);
		m_ImagePause = (ImageView)findViewById(R.id.imagePause);
		m_ImagePause.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_status*=(-1);
				if(m_status == PAUSE)
					m_thread.pauseNresumeThread(true);
				else if(m_status == RUNNING)
					m_thread.pauseNresumeThread(false);
				m_viewBox.onPause(m_status);
			}	
		});
		
		//start a Snakegame
				m_viewBox = (ViewBox)findViewById(R.id.viewBox);
				m_viewBox.setActivity(this);
				m_viewBox.startGame(m_gameMode);
		
		
		this.setImageNum();
		this.setImageTime();
		
		m_handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				m_ms01 ++;
				
				if(m_ms01 ==10) 	{m_ms10++;	m_ms01=0;}
				if(m_ms10 ==10) 	{m_s01++;	m_ms10=0;}
				if(m_s01  ==10) 	{m_s10++;	m_s01=0;}
				if(m_s10  ==6) 		{m_m01++;	m_s10=0;}
				if(m_m01  ==10) 	{m_m10++;	m_m01=0;}
				if(m_m10  ==10) 	{m_m10=0;	m_m01=0;}
				setImageResouce();
			}
		};  
		m_thread = new ThreadTime(m_handler);
		m_thread.start();
				
	}
	
	//call by ViewBox
	public void goGameover() {
		m_status = GAMEOVER;
		m_thread.stopThread();
		
		Intent intent = new Intent(ActivityPlay.this, ActivityGameover.class);
		intent.putExtra("gameScore", m_textViewScore.getText().toString());
		intent.putExtra("appleScore", m_textViewApple.getText().toString());
		intent.putExtra("m10", m_m10);
		intent.putExtra("m01", m_m01);
		intent.putExtra("s10", m_s10);
		intent.putExtra("s01", m_s01);
		intent.putExtra("ms10", m_ms01);
		intent.putExtra("ms01", m_ms10);
		intent.putExtra("gameMode", m_gameMode);
		startActivity(intent);
			
		finish();	
	}
	public void setTextApple(int appleScore) {
		m_textViewApple.setText(Integer.toString(appleScore));
	}
	public void setTextScore(int gameScore) {
		m_textViewScore.setText(Integer.toString(gameScore));
	}
	public void setPlayStatus(int status) {
		m_status = status;
	}
	
	
	private void setImageNum() {
		for(int i=0;i<10;i++) {
			m_imgNum[i] = R.drawable.num0+i;
		}
	}
	private void setImageTime() {
		m_imgM10 	=(ImageView)findViewById(R.id.imageM10);
		m_imgM01	=(ImageView)findViewById(R.id.imageM01);
		m_imgS10	=(ImageView)findViewById(R.id.imageS10);
		m_imgS01	=(ImageView)findViewById(R.id.imageS01);
		m_imgMs10	=(ImageView)findViewById(R.id.imageMs10);
		m_imgMs01	=(ImageView)findViewById(R.id.imageMs01);
	}
	private void setImageResouce() {
		m_imgM10.setImageResource(m_imgNum[m_m10]);
		m_imgM01.setImageResource(m_imgNum[m_m01]);
		m_imgS10.setImageResource(m_imgNum[m_s10]);
		m_imgS01.setImageResource(m_imgNum[m_s01]);
		m_imgMs10.setImageResource(m_imgNum[m_ms10]);
		m_imgMs01.setImageResource(m_imgNum[m_ms01]);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			m_thread.stopThread();
			m_viewBox.setStatus(READY);
			finish();
		}
		return true;
	}

}
