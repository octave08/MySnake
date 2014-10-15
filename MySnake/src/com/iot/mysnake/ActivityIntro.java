package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;


public class ActivityIntro extends Activity {

	private ImageButton btnStart;
	private ImageButton btnBackFromStage;
	private ImageButton	btnGoStage1;
	private ImageButton btnBackFromInfinity;
	private ImageButton btnGoInfinity;
	
	private ImageButton btnMode;
	private ImageButton btnBackFromMode;
	private ImageButton btnSetStage;
	private ImageButton btnSetInfinity;
	
	private ImageButton btnRank;
	
	private View m_linearIntro		= null;
	private View m_linearStage		= null;
	private View m_linearInfinity	= null;
	private View m_linearMode		= null;
	
	private int m_gameMode = 1;
	private final static int INFINITY 	= 0;
	private final static int STAGE 		= 1;
	
	private int m_gameLevel = 0;
	private final static int LEVEL1		= 1;
	private final static int LEVEL2		= 2;
	private final static int LEVEL3		= 3;
	private final static int LEVEL4		= 4;
	private final static int LEVEL5		= 5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_intro);
		
		initLinear();
		
		setBtnStart();
		setBtnMode();


	}
	
	private void initLinear() {
		m_linearIntro		= (View)findViewById(R.id.LinearIntro);
		m_linearStage 		= (View)findViewById(R.id.lineartStage);
		m_linearInfinity 	= (View)findViewById(R.id.linearInfinity);
		m_linearMode		= (View)findViewById(R.id.linearMode);
	}
	
	// 스타트관련 버튼 설정
	private void setBtnStart() {
		btnStart = (ImageButton)findViewById(R.id.btnGoStart);
		btnStart.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(m_gameMode == STAGE) {
					m_linearStage.setVisibility(View.VISIBLE);
					m_linearIntro.setVisibility(View.INVISIBLE);
				}
				else
					m_linearInfinity.setVisibility(View.VISIBLE);
					m_linearIntro.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnBackFromStage = (ImageButton)findViewById(R.id.btnBackFromStage);
		btnBackFromStage.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_linearIntro.setVisibility(View.VISIBLE);
					m_linearStage.setVisibility(View.INVISIBLE);
					
			}
			
		});
		
		btnGoStage1 = (ImageButton)findViewById(R.id.btnStage1);
		btnGoStage1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_gameLevel = LEVEL1;
				
				Intent intent = new Intent(ActivityIntro.this, ActivityPlay.class);
				intent.putExtra("gamemode", m_gameLevel);
				startActivity(intent);
				m_linearIntro.setVisibility(View.VISIBLE);
				m_linearStage.setVisibility(View.INVISIBLE);
				
			}
			
		});
		
		btnBackFromInfinity = (ImageButton)findViewById(R.id.btnBackFromInfinity);
		btnBackFromInfinity.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_linearIntro.setVisibility(View.VISIBLE);
					m_linearInfinity.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnGoInfinity = (ImageButton)findViewById(R.id.btnInfinity);
		btnGoInfinity.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityIntro.this, ActivityPlay.class);
				intent.putExtra("gamemode", m_gameMode);
				startActivity(intent);
				m_linearIntro.setVisibility(View.VISIBLE);
				m_linearInfinity.setVisibility(View.INVISIBLE);
			}
			
		});
	}

	
	// Mode관련 버튼 설정
	private void setBtnMode() {
		btnMode = (ImageButton)findViewById(R.id.btnGoMode);
		btnMode.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_linearMode.setVisibility(View.VISIBLE);
					m_linearIntro.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnBackFromMode = (ImageButton)findViewById(R.id.btnBackFromMode);
		btnBackFromMode.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_linearIntro.setVisibility(View.VISIBLE);
					m_linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnSetStage = (ImageButton)findViewById(R.id.btnSetStage);
		btnSetStage.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_gameMode = STAGE;
					m_linearIntro.setVisibility(View.VISIBLE);
					m_linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnSetInfinity = (ImageButton)findViewById(R.id.btnSetInfinity);
		btnSetInfinity.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_gameMode = INFINITY;
					m_linearIntro.setVisibility(View.VISIBLE);
					m_linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
		
		
		btnRank = (ImageButton)findViewById(R.id.btnGoRank);
		btnRank.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityIntro.this, ActivityRank.class);
				//intent.putExtra("gameScore", test);
				//intent.putExtra("appleScore", test);
				//intent.putExtra("time", test);
				startActivity(intent);
				
			}
			
		}); 
	}
	

	
	
	
}
