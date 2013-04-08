package com.capinfo.myslidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myslidingmenu2.R;

public class UserInfo extends Activity {

	private RelativeLayout callMobile;
	private Button sendMMS;
	private ImageView user_pic;
	private Float x;
	private Float ux;
	ShakeListener mShakeListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initview();
		initData();
	}

	/**
	 * 初始化布局
	 */
	private void initview() {
		// TODO Auto-generated method stub
		callMobile = (RelativeLayout) findViewById(R.id.user_info_mobilenum);
		sendMMS = (Button) findViewById(R.id.send_mms);
		user_pic = (ImageView) findViewById(R.id.user_info_userface);
		callMobile.setOnTouchListener(callMobileTouchListener);
		sendMMS.setOnClickListener(sendMMSClickListener);
		//mShakeListener = new ShakeListener(this);
		//mShakeListener.setOnShakeListener(new shakeLitener());
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private View.OnTouchListener callMobileTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				x = event.getX();
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				ux = event.getX();
				if (Math.abs(x - ux) > 20) {
					callMobileAnim(v);
				}
			}
			return false;
		}
	};

	private View.OnClickListener sendMMSClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String a = "343242342";
			// TODO Auto-generated method stub
			Uri uri = Uri.parse("smsto:" + a);
			Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			// it.putExtra("sms_body", "The SMS text");
			startActivity(it);
		}
	};

	/**
	 * 滑动拨打电话动画效果
	 * @param v
	 */
	private void callMobileAnim(View v) {
		final Animation animation = (Animation) AnimationUtils.loadAnimation(
				v.getContext(), R.anim.item_anim);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}
			public void onAnimationRepeat(Animation animation) {
			}
			public void onAnimationEnd(Animation animation) {
				animation.cancel();
			}
		});
		v.startAnimation(animation);

	}
//	 private class shakeLitener implements OnShakeListener{
//		  @Override
//		  public void onShake() {
//		   // TODO Auto-generated method stub			  
//		   mShakeListener.stop();
//		  }
//		  
//		 }

}
