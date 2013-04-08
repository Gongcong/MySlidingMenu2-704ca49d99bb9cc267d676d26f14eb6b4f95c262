package com.capinfo.cn.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capinfo.cn.Person;
import com.capinfo.myslidingmenu.UserInfo;
import com.example.myslidingmenu2.R;

public class SectionComposerAdapter extends SpellHeaderAdapter implements OnClickListener{

	private Person person;
	 public  List<Pair<String, List<Person>>> all;
	 public Context context;

	 public int currentPosition=-1;
	
	public SectionComposerAdapter(Context context,List<Pair<String, List<Person>>> all) {
	
		this.all = all;
		this.context = context;
	}
	 public SectionComposerAdapter(){}

	public int getCount() {
		int res = 0;
		for (int i = 0; i < all.size(); i++) {
			res += all.get(i).second.size();
		}
		return res;
	}

	public Person getItem(int position) {
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (position >= c && position < c + all.get(i).second.size()) {
				return all.get(i).second.get(position - c);
			}
			c += all.get(i).second.size();
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	protected void onNextPageRequested(int page) {
	}

	protected void bindSectionHeader(View view, int position,
			boolean displaySectionHeader) {

		if (displaySectionHeader) {
			view.findViewById(R.id.header).setVisibility(View.VISIBLE);
			TextView lSectionTitle = (TextView) view
					.findViewById(R.id.header);

			lSectionTitle
					.setText(getSections()[getSectionForPosition(position)]);
		} else {
			view.findViewById(R.id.header).setVisibility(View.GONE);
		}
	}

	public View getAmazingView(int position, View convertView,
			ViewGroup parent) {
		Holder viewHolder;
		person = getItem(position);
		if (convertView == null) {
			convertView =View.inflate(context,R.layout.item_person,
					null);
			viewHolder = new Holder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.name);
			viewHolder.job = (TextView) convertView.findViewById(R.id.job);
			viewHolder.ortherLinearLayout = (LinearLayout) convertView
					.findViewById(R.id.layout_other);
			viewHolder.callMobile = (LinearLayout) convertView
					.findViewById(R.id.item_callMobile);
			viewHolder.callPhone = (LinearLayout) convertView
					.findViewById(R.id.item_callPhone);
			viewHolder.inDetail = (LinearLayout) convertView
					.findViewById(R.id.item_inDetail);
			viewHolder.callMobile.setClickable(true);
			viewHolder.callMobile.setOnClickListener(this);
			viewHolder.callPhone.setClickable(true);
			viewHolder.callPhone.setOnClickListener(this);

			viewHolder.inDetail.setClickable(true);
			viewHolder.inDetail.setOnClickListener(this);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (Holder) convertView.getTag();

		}

		if (position == currentPosition) {
			viewHolder.ortherLinearLayout.setVisibility(View.VISIBLE);

		} else {

			viewHolder.ortherLinearLayout.setVisibility(View.GONE);
		}
		viewHolder.name.setText(person.getName());
		viewHolder.job.setText(person.getJob());

		return convertView;
	}

	public void configurePinnedHeader(View header, int position) {

		TextView lSectionHeader = (TextView) header;

		lSectionHeader
				.setText(getSections()[getSectionForPosition(position)]);
		/*
		 * lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
		 * lSectionHeader.setTextColor(alpha << 24 | (0x000000));
		 */
	}

	public int getPositionForSection(int section) {
		if (section < 0)
			section = 0;
		if (section >= all.size())
			section = all.size() - 1;
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (section == i) {
				return c;
			}
			c += all.get(i).second.size();
		}
		return 0;
	}

	public int getSectionForPosition(int position) {
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (position >= c && position < c + all.get(i).second.size()) {
				return i;
			}
			c += all.get(i).second.size();
		}
		return -1;
	}

	public String[] getSections() {
		String[] res = new String[all.size()];
		for (int i = 0; i < all.size(); i++) {
			res[i] = all.get(i).first;
		}
		return res;
	}

	@Override
	public int getSelectSideBar(String str) {
		// TODO Auto-generated method stub
		int c = 0;
		String[] res = new String[all.size()];
		for (int i = 0; i < all.size(); i++) {
			res[i] = all.get(i).first;
			if (str.equalsIgnoreCase(res[i])) {

				return c;
			}
			c += all.get(i).second.size();
		}
		return -1;
	}

	public void call(String num) {
		Uri uri = Uri.parse("tel:" + num);
		Intent it = new Intent(Intent.ACTION_CALL, uri);
		context.startActivity(it);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.item_callMobile:

			call(person.getMobile());

			break;
		case R.id.item_callPhone:
			call(person.getPhoneNum());
			break;
		case R.id.item_inDetail:
			Intent it = new Intent(context, UserInfo.class);
			context.startActivity(it);

			break;

		default:
			break;
		}

	}
	static class Holder {
		TextView name;
		TextView job;
		LinearLayout callMobile;
		LinearLayout callPhone;
		LinearLayout inDetail;
		LinearLayout ortherLinearLayout;
	}


}
