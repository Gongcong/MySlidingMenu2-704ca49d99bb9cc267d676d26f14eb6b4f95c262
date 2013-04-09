package com.capinfo.myslidingmenu;

import com.capinfo.unlock.LockPatternUtils;
import com.capinfo.util.StringUtils;

import android.R.bool;
import android.app.Application;

public class MyApplication extends Application{
	
	public boolean lockState = false;
	public boolean loginState = false;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	/**
	 * �ж��Ƿ��Ѿ����н���ͼ��
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
	
	public void setLoginSate(boolean loginflag) {
		loginState = loginflag;
	}
	
	public boolean getLoginSate() {
		return loginState;
	}
}
