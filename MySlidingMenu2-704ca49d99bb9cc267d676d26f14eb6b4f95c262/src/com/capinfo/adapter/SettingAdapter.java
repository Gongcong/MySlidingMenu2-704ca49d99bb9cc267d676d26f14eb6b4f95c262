package com.capinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
			viewHolder.image = (ImageView) convertView.findViewById(R.id.img);
			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
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
			viewHolder.checkbox.setVisibility(View.VISIBLE);
			viewHolder.image.setVisibility(View.INVISIBLE);

		}

		viewHolder.tvTitle.setText(this.title[position]);
		return convertView;
	}


	final static class ViewHolder {
		TextView tvTitle;
		ImageButton btn;
		ImageView image;
		CheckBox checkbox;
	}

}
