package com.capinfo.unlock;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.capinfo.myslidingmenu.MainActivity;
import com.capinfo.unlock.LockPatternView.Cell;
import com.capinfo.unlock.LockPatternView.DisplayMode;
import com.capinfo.unlock.LockPatternView.OnPatternListener;
import com.example.myslidingmenu2.R;

public class LockMain extends Activity implements OnClickListener {

	// private OnPatternListener onPatternListener;

	private LockPatternView lockPatternView;

	private LockPatternUtils lockPatternUtils;

	private Button btn_set_pwd;

	private Button btn_reset_pwd;

	private Button btn_check_pwd;

	private boolean opFLag = true;
	List<Cell> spattern;
	String mpattern;
	String npattern;
	int i = 0;
	private LockPatternView lockPatternViewSmall;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unlock);
		lockPatternView = (LockPatternView) findViewById(R.id.lpv_lock);

		lockPatternViewSmall = (LockPatternView) findViewById(R.id.lpv_lock_first);

		btn_reset_pwd = (Button) findViewById(R.id.btn_reset_pwd);
		btn_set_pwd = (Button) findViewById(R.id.btn_set_pwd);
		btn_check_pwd = (Button) findViewById(R.id.btn_check_pwd);
		btn_reset_pwd.setOnClickListener(this);
		btn_set_pwd.setOnClickListener(this);
		btn_check_pwd.setOnClickListener(this);

		lockPatternUtils = new LockPatternUtils(this);

		lockPatternView.setOnPatternListener(new OnPatternListener() {

			public void onPatternStart() {
			}

			public void onPatternDetected(List<Cell> pattern) {
				LockMain.this.spattern = pattern;
				if (opFLag) {
					int result = lockPatternUtils.checkPattern(pattern);
					if (result != 1) {
						if (result == 0) {
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
							Toast.makeText(LockMain.this, "密码错误",
									Toast.LENGTH_LONG).show();
							lockPatternViewSmall.setPattern(DisplayMode.Wrong,
									pattern);

						} else {
							lockPatternView.clearPattern();
							lockPatternViewSmall.clearPattern();
							Toast.makeText(LockMain.this, "请设置密码",
									Toast.LENGTH_LONG).show();
						}

					} else {
						lockPatternViewSmall.setPattern(DisplayMode.Correct,
								pattern);
						lockPatternView.clearPattern();
						lockPatternViewSmall.clearPattern();
						Toast.makeText(LockMain.this, "密码正确", Toast.LENGTH_LONG)
								.show();
						Intent it = new Intent();
						it.setClass(LockMain.this, MainActivity.class);
						startActivity(it);
					}
				} else {
					if (pattern.size() < 4) {
						Toast.makeText(LockMain.this, "密码过短，至少4位，请重新输入",
								Toast.LENGTH_LONG).show();
					} else {
						if (i == 0) {
							mpattern = LockPatternUtils
									.patternToString(pattern);
							System.out.println("mpattern" + mpattern);
							i = i + 1;
							lockPatternViewSmall.setPattern(
									DisplayMode.Correct, pattern);
							lockPatternView.clearPattern();
							Toast.makeText(LockMain.this, "again",
									Toast.LENGTH_LONG).show();
						} else {
							npattern = LockPatternUtils
									.patternToString(pattern);

							if (npattern != null) {
								System.out.println("npattern" + npattern);
								System.out.println("npattern.equals(mpattern)"
										+ pattern.equals(mpattern));
								if (npattern.equals(mpattern)) {
									lockPatternUtils.saveLockPattern(pattern);
									lockPatternViewSmall.setPattern(
											DisplayMode.Correct, pattern);
									Toast.makeText(LockMain.this, "密码已经设置",
											Toast.LENGTH_LONG).show();
									// lockPatternViewSmall.clearPattern();
									lockPatternView.clearPattern();

								} else {
									Toast.makeText(LockMain.this,
											"2次输入不一致，请重新输入", Toast.LENGTH_LONG)
											.show();
									lockPatternViewSmall.setPattern(
											DisplayMode.Correct, pattern);
									lockPatternView.clearPattern();
									i = i - 1;
								}
							}
						}
					}
				}

			}

			@Override
			public void onPatternCleared() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
				
			}


		});
	}

	public void onClick(View v) {
		if (v == btn_reset_pwd) {
			lockPatternViewSmall.clearPattern();
			lockPatternView.clearPattern();
			lockPatternUtils.clearLock();

		} else if (v == btn_check_pwd) {
			opFLag = true;
		} else {
			opFLag = false;
		}
	}

}