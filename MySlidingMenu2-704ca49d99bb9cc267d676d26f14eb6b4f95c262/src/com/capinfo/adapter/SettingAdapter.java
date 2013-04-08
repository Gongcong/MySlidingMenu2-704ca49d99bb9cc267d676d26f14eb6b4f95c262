package com.capinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myslidingmenu2.R;

/**
 * 
 * @author Gongcong
 * 
 */
public class SettingAdapter extends BaseAdapter {

	private Context mContext;
	private String[] title;
	private TextView tvTitle;
	private ImageButton btn;
	private ImageView img2;

	public SettingAdapter(Context mContext, String[] title) {
		super();
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.title = title;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item,
					null);
			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.menu_item);
			viewHolder.btn = (ImageButton) convertView
					.findViewById(R.id.button1);
			viewHolder.image = convertView.findViewById(R.id.img);
			// viewHolder.image2 = convertView.findViewById(R.id.img2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 当title为版本号时不显示图片
		if (position == 0 || position == 6) {
			viewHolder.image.setVisibility(View.INVISIBLE);
		}
		// 当title为拨打电话二次确认是显示按钮，不显示图片
		if (position == 5) {
			viewHolder.btn.setVisibility(View.VISIBLE);
			// viewHolder.image2.setVisibility(i);
			viewHolder.image.setVisibility(View.INVISIBLE);
		}
		if (btn != null) {
			// btn.setOnTouchListener(new OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// // TODO Auto-generated method stub
			// if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// // 更改为按下时的背景图片
			// System.out.println("down");
			// v.setBackgroundResource(R.drawable.after);
			// } else if (event.getAction() == MotionEvent.ACTION_UP) {
			// // 改为抬起时的图片
			// v.setBackgroundResource(R.drawable.before);
			// }
			// return false;
			// }
			// });
			convertView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					System.out.println("down");
					// TODO Auto-generated method stub
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// 更改为按下时的背景图片
						System.out.println("down");
						//v.setBackgroundResource(R.drawable.after);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						// 改为抬起时的图片
						//v.setBackgroundResource(R.drawable.before);
					}

					return false;
				}
			});

		}
		viewHolder.tvTitle.setText(this.title[position]);
		return convertView;
	}

	final static class ViewHolder {
		TextView tvTitle;
		ImageButton btn;
		View image;
		View image2;
	}
}
