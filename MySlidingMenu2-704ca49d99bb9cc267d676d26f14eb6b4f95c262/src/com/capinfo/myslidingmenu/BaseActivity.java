package com.capinfo.myslidingmenu;

import android.app.Activity;
import android.content.Intent;

import com.capinfo.unlock.LockMain;

public class BaseActivity extends Activity{

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
		System.out.println("onpause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		System.out.println("resume");
		super.onResume();
	}

}
