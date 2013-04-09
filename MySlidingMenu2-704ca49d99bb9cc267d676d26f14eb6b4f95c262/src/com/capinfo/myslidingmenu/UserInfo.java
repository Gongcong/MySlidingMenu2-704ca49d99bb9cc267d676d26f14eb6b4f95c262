package com.capinfo.myslidingmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.capinfo.myslidingmenu.view.MyDialog;
import com.example.myslidingmenu2.R;

public class UserInfo extends Activity {

	private RelativeLayout callMobile;
	private Button sendMMS;
	private ImageView user_pic;
	private Float x;
	private Float ux;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private static final int SENSOR_SHAKE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initview();
		initData();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		if (sensorManager != null) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);

		}

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

	/**
	 * 震动监听器
	 */
	SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
					|| Math.abs(z) > medumValue) {
				vibrator.vibrate(200);
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				Toast.makeText(UserInfo.this, "检测到摇晃，执行操作！", Toast.LENGTH_SHORT)
						.show();

				break;
			}
		}

	};

	private View.OnClickListener sendMMSClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			MyDialog dialog = new MyDialog(UserInfo.this);
			dialog.show();
			// String a = "343242342";
			// // TODO Auto-generated method stub
			// Uri uri = Uri.parse("smsto:" + a);
			// Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			// // it.putExtra("sms_body", "The SMS text");
			// startActivity(it);
		}
	};

	

	/**
	 * 滑动拨打电话动画效果
	 * 
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

}
