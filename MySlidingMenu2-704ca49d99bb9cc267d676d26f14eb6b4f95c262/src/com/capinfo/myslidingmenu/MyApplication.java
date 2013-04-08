package com.capinfo.myslidingmenu;

import com.capinfo.unlock.LockPatternUtils;
import com.capinfo.util.StringUtils;

import android.app.Application;

public class MyApplication extends Application{
	
	public boolean lockState = false;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	/**
	 * 判断是否已经存有解锁图案
	 * @return boolean
	 */
	public boolean isPatternEmpty() {
		String pattern = LockPatternUtils.getLockPaternString();
		if (StringUtils.isEmpty(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	public void setLockState(boolean opflag) {
		lockState = opflag;
	}
	
	public boolean getLockState() {
		return lockState;
	}
	
}
