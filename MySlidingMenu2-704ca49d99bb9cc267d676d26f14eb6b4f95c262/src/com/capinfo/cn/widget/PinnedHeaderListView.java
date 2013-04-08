package com.capinfo.cn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ListAdapter;
import android.widget.ListView;

import com.capinfo.cn.widget.SpellHeaderAdapter.HasMorePagesListener;


public class PinnedHeaderListView extends ListView implements HasMorePagesListener {
	View listFooter;
	boolean footerViewAttached = false;

	private View mHeaderView;
	private boolean mHeaderViewVisible;

	private int mHeaderViewWidth;
	private int mHeaderViewHeight;

	private SpellHeaderAdapter adapter;
	private onHeadViewListeren listeren;

	public void setPinnedHeaderView(View view) {
		mHeaderView = view;

		if (mHeaderView != null) {
		
			setFadingEdgeLength(0);
		}
	
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mHeaderView != null) {
			measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
			mHeaderViewWidth = mHeaderView.getMeasuredWidth();
			mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (mHeaderView != null) {
			mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			configureHeaderView(getFirstVisiblePosition());
		}
	}

	public void setonHeadViewListeren(onHeadViewListeren lister) {
		this.listeren = lister;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			if(mHeaderView!=null){
			Rect headrect = new Rect(mHeaderView.getLeft(),
					mHeaderView.getTop(), mHeaderView.getRight(),
					mHeaderView.getBottom());
			if (headrect.contains(x, y)) {
				 if(listeren!=null){
					 listeren.onClick();
				 }
				return false;
			} else {
				return super.dispatchTouchEvent(ev);
			}
			}
		
		}
		return super.dispatchTouchEvent(ev);
	}

	public interface onHeadViewListeren {
		void onClick();
	}

	public void configureHeaderView(int position) {
		if (mHeaderView == null) {
			return;
		}

		int state = adapter.getPinnedHeaderState(position);
		switch (state) {
		case SpellHeaderAdapter.PINNED_HEADER_GONE: {
			mHeaderViewVisible = false;
			break;
		}

		case SpellHeaderAdapter.PINNED_HEADER_VISIBLE: {
			adapter.configurePinnedHeader(mHeaderView, position);
			if (mHeaderView.getTop() != 0) {
				mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			}
			mHeaderViewVisible = true;
			break;
		}

		case SpellHeaderAdapter.PINNED_HEADER_PUSHED_UP: {
			View firstView = getChildAt(0);

			if (firstView != null) {
				int bottom = firstView.getBottom();
				int headerHeight = mHeaderView.getHeight();
				int y;
				if (bottom < headerHeight) {
					y = (bottom - headerHeight);
				} else {
					y = 0;
				}
				adapter.configurePinnedHeader(mHeaderView, position);
				if (mHeaderView.getTop() != y) {
					mHeaderView.layout(0, y, mHeaderViewWidth,
							mHeaderViewHeight + y);
				}
				mHeaderViewVisible = true;
			}
			break;
		}
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mHeaderViewVisible) {
			drawChild(canvas, mHeaderView, getDrawingTime());
		}
	}

	public PinnedHeaderListView(Context context) {
		super(context);
	}

	public PinnedHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PinnedHeaderListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setLoadingView(View listFooter) {
		this.listFooter = listFooter;
	}

	public View getLoadingView() {
		return listFooter;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (!(adapter instanceof SpellHeaderAdapter)) {
			throw new IllegalArgumentException(
					SpellHeaderAdapter.class.getSimpleName()
							+ " must use adapter of type "
							+ SpellHeaderAdapter.class.getSimpleName());
		}

		// previous adapter
		if (this.adapter != null) {
			this.adapter.setHasMorePagesListener(null);
			this.setOnScrollListener(null);
		}

		this.adapter = (SpellHeaderAdapter) adapter;
		((SpellHeaderAdapter) adapter).setHasMorePagesListener(this);
		this.setOnScrollListener((SpellHeaderAdapter) adapter);

		View dummy = new View(getContext());

		super.addFooterView(dummy);
		super.setAdapter(adapter);
		super.removeFooterView(dummy);
	}

	@Override
	public SpellHeaderAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void noMorePages() {
		if (listFooter != null) {
			this.removeFooterView(listFooter);
		}
		footerViewAttached = false;
	}

	@Override
	public void mayHaveMorePages() {
		if (!footerViewAttached && listFooter != null) {
			this.addFooterView(listFooter);
			footerViewAttached = true;
		}
	}

	public boolean isLoadingViewVisible() {
		return footerViewAttached;
	}

	

}
