package com.capinfo.myslidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.capinfo.adapter.SettingAdapter;
import com.capinfo.cn.Data;
import com.capinfo.cn.MySideBar;
import com.capinfo.cn.MySideBar.OnTouchingLetterChangedListener;
import com.capinfo.cn.Person;
import com.capinfo.cn.widget.PinnedHeaderListView;
import com.capinfo.cn.widget.SectionComposerAdapter;
import com.capinfo.myslidingmenu.view.MySlidingMenuView;
import com.example.myslidingmenu2.R;

/**
 * 
 * 
 * @author Gongcong
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener,
		OnTouchingLetterChangedListener {

	private View myAs = null;// 布局
	private String title[] = { "版本号", "设置锁屏手势", "开发团队", "版本更新", "数据更新",
			"拨打电话二次确认", "退出软件" };
	private MySlidingMenuView slidingMenu;
	boolean isExit;
	ListView list1;
	ImageView img2;
	ListView menu_listview;
	PinnedHeaderListView headerListView; // Diy ListView
	SectionComposerAdapter adapter; // 适配器
	MySideBar myView;
	List<Pair<String, List<Person>>> all;
	private TextView select_side_bar_text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();

		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		tm.getDeviceId();
		     System.out.println("1111="+tm.getDeviceId());
	     

	}

	/**
	 * 初始化布局
	 */
	private void init() {
		slidingMenu = (MySlidingMenuView) findViewById(R.id.sliding_menu);
		// 使用覆盖样式，仿网易新闻
		// slidingMenu.setlSlidingMenuState(MySlidingMenuView.SLIDING_MENU_COVER);
		slidingMenu.setlSlidingMenuState(MySlidingMenuView.SLIDING_MENU_TILE);
		// 初始化界面布局
		myAs = findViewById(R.id.SelectDemoActivity);
		ListView menu_listview = (ListView) findViewById(R.id.menu_listView);
		SettingAdapter settingadapter = new SettingAdapter(this, title);
		menu_listview.setAdapter(settingadapter);
		// ImageView img = (ImageView)findViewById(R.id.img2);
		// 设置listview的宽度
		setListViewWidthBaseOnChildren(menu_listview);
		myView = (MySideBar) findViewById(R.id.myView);
		select_side_bar_text = (TextView) findViewById(R.id.select_side_bar_text);
		Button showmain = (Button) findViewById(R.id.button1);
		showmain.setOnClickListener(this);
		Button showmenu = (Button) myAs.findViewById(R.id.showmenu);
		showmenu.setOnClickListener(this);

		menu_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Toast.makeText(getApplicationContext(),
							String.valueOf(position), Toast.LENGTH_LONG).show();
					break;

				case 5:

					break;
				default:
					break;
				}
			}
		});

		// 测试数据
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("啊", "123", "产品研发部", "212", "liu@163.com", "工程师"));
		persons.add(new Person("王五", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("赵六", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("l六", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("啊三", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("龚枞", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("龚枞", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("o枞", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("把枞", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		persons.add(new Person("刘问", "123", "产品研发部", "212", "liu@163.com",
				"工程师"));
		all = Data.getAllData(persons);

		headerListView = (PinnedHeaderListView) findViewById(R.id.lsComposer);
		headerListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.item_composer_header, headerListView, false));
		adapter = new SectionComposerAdapter(this, all);

		myView.setOnTouchingLetterChangedListener(this);
		myView.setListview(headerListView);

		headerListView.setAdapter(adapter);
		headerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				vibrator.vibrate(40);
				if (adapter.currentPosition != position) {

					adapter.currentPosition = position;
					adapter.notifyDataSetChanged();
				} else {
					adapter.currentPosition = -1;
					adapter.notifyDataSetChanged();
				}

			}
		});
		myAs.setVisibility(View.VISIBLE);// 默认显示第一页
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			slidingMenu.closeMenu();
			break;
		case R.id.showmenu:
			slidingMenu.openMenu();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			//System.exit(0);
			finish();
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}

	};

	@Override
	public void onTouchingLetterChanged(String s) {
		// TODO Auto-generated method stub
		if (s != null) {
			select_side_bar_text.setVisibility(View.VISIBLE);
			select_side_bar_text.setText(s);
		}
	}

	@Override
	public void onNoTouchingLetterChanged() {
		// TODO Auto-generated method stub
		select_side_bar_text.setVisibility(View.GONE);
	}

	/**
	 * 动态设置listview的宽度
	 * 
	 * @param listView
	 */
	@SuppressWarnings("deprecation")
	public void setListViewWidthBaseOnChildren(ListView listView) {
		ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
		layoutParams.width = (int) ((getWindowManager().getDefaultDisplay()
				.getWidth()) * 0.75);
		System.out.print("width" + layoutParams.width);
		listView.setLayoutParams(layoutParams);
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

}
