package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class ActivityPlay extends Activity {
	
	private int m_gameMode;
	private final static int INFINITY	= 0;
	private final static int STAGE 		= 1;
	
	private final static int LEVEL1		= 1;
	private final static int LEVEL2		= 2;
	private final static int LEVEL3		= 3;
	private final static int LEVEL4		= 4;
	private final static int LEVEL5		= 5;

	ViewBox m_viewBox = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_play);
		
		Intent intent = getIntent();
		m_gameMode = intent.getIntExtra("gamemode", STAGE);
		
		m_viewBox = (ViewBox)findViewById(R.id.viewBox);
		m_viewBox.startGame(m_gameMode);
		
	}


	

}
