package com.capinfo.cn;


import com.capinfo.cn.widget.SpellHeaderAdapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class MySideBar extends View {
	private TextView textView;// 显示框
	private OnTouchingLetterChangedListener Listener;
	public String[] serachContent;
	public int color;

	private boolean showBkg = false; // 获取焦点
	int choose = -1;// 选中状态
	Paint paint = new Paint();
	private boolean isListener = false;
	private ListView listview;
	private SpellHeaderAdapter adapter;
	private int singleHeight;

	public MySideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MySideBar(Context context) {
		super(context);
		init();
	}

	public MySideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		String as[] ={"#","A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		serachContent = as;
		color = 0xffa6a9aa;
	}

	public ListView getListview() {
		return listview;
	}

	public void setListview(ListView listview) {
		this.listview = listview;
		adapter = (SpellHeaderAdapter) listview.getAdapter();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}

		singleHeight = getMeasuredHeight() / serachContent.length;
		float textSize = (float) (getMeasuredWidth() / 2.5D);
		for (int i = 0; i < serachContent.length; i++) {
			paint.setColor(color);
			paint.setAntiAlias(true);
			paint.setTextSize(textSize);
			paint.setTextAlign(android.graphics.Paint.Align.CENTER);
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			canvas.drawText(serachContent[i], getMeasuredWidth() / 2, 0
					+ singleHeight + i * singleHeight, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		int action = event.getAction();
		int y = (int) event.getY(); // 点击y坐标
		int oldChoose = choose;

		// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
//		final int c = (int) (y / getHeight() * serachContent.length);
		int idx = y /singleHeight ;
		if (idx >= serachContent.length) {
			idx = serachContent.length - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
			showBkg = true;
			if (oldChoose != idx && isListener) {
				if (idx >= 0 && idx < serachContent.length) {
					Listener.onTouchingLetterChanged(serachContent[idx]);
					choose = idx;// 选项
					invalidate();
					if(listview==null){
						return true;
					}else{
						if (adapter == null) {
							adapter = (SpellHeaderAdapter) listview.getAdapter();
						}
						//这里可以再处理adapter下
						int position = adapter.getSelectSideBar(serachContent[choose]);
						if (position == -1) {
							return true;
						}
						listview.setSelection(position);
					}
				}
			}
		}else if(action == MotionEvent.ACTION_UP){
			Listener.onNoTouchingLetterChanged();
			showBkg = false;
			choose = -1;
			// textView.setVisibility(GONE);
			invalidate();
		}

		return true;
	}

	public String[] getSerachContent() {
		return serachContent;
	}

	public void setSerachContent(String[] serachContent) {
		this.serachContent = serachContent;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
		public void onNoTouchingLetterChanged();
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener Listener) {
		this.Listener = Listener;
		isListener = true;
	}

}
