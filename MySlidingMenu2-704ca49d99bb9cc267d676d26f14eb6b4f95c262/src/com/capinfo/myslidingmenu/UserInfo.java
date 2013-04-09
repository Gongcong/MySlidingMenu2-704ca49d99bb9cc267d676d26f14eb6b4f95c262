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
		if (sensorManager != null) {// ע�������
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);

		}

	}

	/**
	 * ��ʼ������
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
	 * �𶯼�����
	 */
	SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// ��������Ϣ�ı�ʱִ�и÷���
			float[] values = event.values;
			float x = values[0]; // x�᷽����������ٶȣ�����Ϊ��
			float y = values[1]; // y�᷽����������ٶȣ���ǰΪ��
			float z = values[2]; // z�᷽����������ٶȣ�����Ϊ��
			// һ����������������������ٶȴﵽ40�ʹﵽ��ҡ���ֻ���״̬��
			int medumValue = 19;// ���� i9250��ô�ζ����ᳬ��20��û�취��ֻ����19��
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
				Toast.makeText(UserInfo.this, "��⵽ҡ�Σ�ִ�в�����", Toast.LENGTH_SHORT)
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
	 * ��������绰����Ч��
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
