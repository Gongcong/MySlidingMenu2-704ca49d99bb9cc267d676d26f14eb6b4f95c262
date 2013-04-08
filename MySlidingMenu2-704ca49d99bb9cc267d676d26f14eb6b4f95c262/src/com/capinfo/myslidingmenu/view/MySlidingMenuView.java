package com.capinfo.myslidingmenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author Gongcong
 * @fileName MySlidingMenuView.java
 * @doc 侧滑分类菜单
 */
public class MySlidingMenuView extends ViewGroup {

	private float lTouchX;// touch操作被触发时记录当前X坐标
	private float lTouchY;// touch操作被触发时记录当前Y坐标
	private int lTouchSlop;// 滑动时产生粘性效果
	private int lTouchUpOffux;// 离开屏幕时当前的偏移量
	private int LEFT_VIEW_WIDTH = 0;// 菜单宽度
	private boolean LEFT_VIEW_STATE;// 菜单状态 true显示
	private int DURATION_TIME = 350;// 放手时自动移动的持续时间
	private boolean isSlidingState;// 侧滑状态 用来控制显示隐藏菜单是使用

	private int lSlidingMenuState;// 菜单状态，平铺式出或覆盖式

	/**
	 * 此属性控制BODY是否可以滑动出界, 通过setlSlidingOutBorder(boolean lSlidingOutBorder)方法设置
	 */
	private boolean lSlidingOutBorder;// BODY是否可以滑动出界

	private boolean PULL_DIRECTION;// true向右拉动 false向左拉动

	private final static int TOUCH_STATE_REST = 0;// 空闲状态
	private final static int TOUCH_STATE_SCROLLING = 1;// 滚动状态
	public int lTouchState = TOUCH_STATE_REST;// 当前状态

	// 拉动比率 ：当view已经被拉过边缘时，操作没有停止，VIEW的跟随减慢的比率
	private double PULL_RATIO = 0.5;

	private boolean enter = true;// 是否可以进入验证，手指离开屏幕时使用
	public static boolean ismenuclose = true; // menu状态

	private Scroller lScroller;

	/**
	 * 平铺样式（默认）
	 */
	public static final int SLIDING_MENU_TILE = 0;

	/**
	 * 覆盖式样式
	 */
	public static final int SLIDING_MENU_COVER = 1;

	public MySlidingMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MySlidingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MySlidingMenuView(Context context) {
		super(context);
		init(context);
	}

	void init(Context context) {
		// 初始化Scroller实例
		lScroller = new Scroller(context);
		lTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() + 25;// 增加25点距离，滑动时产生粘性效果
		postDelayed(new Runnable() {
			@Override
			public void run() {
				LEFT_VIEW_WIDTH = getChildAt(0).getWidth();
				scrollTo(LEFT_VIEW_WIDTH, 0);
				if (lSlidingMenuState == SLIDING_MENU_COVER) {
					/* 隐藏并保留菜单空间，如果不隐藏则会执行平铺拉出操作 */
					getChildAt(0).setVisibility(View.INVISIBLE);
				}
			}
		}, 50);
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final View child1 = getChildAt(0);
		// child1.setVisibility(INVISIBLE);
		child1.measure(child1.getLeft() + child1.getRight(), heightMeasureSpec);
		System.out.println("-------------" + child1.getLayoutParams().width
				+ "/" + child1.getLeft() + "/" + child1.getRight());
		final View child2 = getChildAt(1);
		child2.measure(widthMeasureSpec, heightMeasureSpec);
		System.out
				.println("child1.getLayoutParams().width=" + widthMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;

		final int count = getChildCount();
		System.out.println("------------" + count);
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				// child.setVisibility(INVISIBLE);
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		// 此处修改于网络源码，意图当非空闲状态时将事件由自身处理，不继续下传
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (lTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:

			final int xDiff = (int) Math.abs(x - lTouchX);
			final int yDiff = (int) Math.abs(y - lTouchY);

			final int touchSlop = lTouchSlop;
			boolean xMoved = xDiff > touchSlop;
			boolean yMoved = yDiff > touchSlop;

			if (xMoved || yMoved) {
				if (xMoved) {
					// Scroll if the user moved far enough along the X axis
					lTouchState = TOUCH_STATE_SCROLLING;
					// ismenuclose = false;
					enableChildrenCache();
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			System.out.println("interupter down");
			lTouchX = x;
			lTouchY = y;
			lTouchState = lScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// Release the drag
			clearChildrenCache();
			lTouchState = TOUCH_STATE_REST;
			//当划开菜单时，点击右边可以关系菜单
			if (!ismenuclose) {
				if (x > LEFT_VIEW_WIDTH) {
				closeMenu();
				lTouchState = 1;
				}
			}
			break;
		}
		return lTouchState != TOUCH_STATE_REST;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// 去掉lTouchState判断可以更好的兼容非列表界面的滑动
			// if (lTouchState == TOUCH_STATE_SCROLLING) {
			lTouchUpOffux = getScrollX();// 默认不移动

			// 向右拉动超过边缘
			if (getScrollX() < 0) {
				lTouchUpOffux = 0;
			}
			// 向左拉动超过边缘
			if (enter && getScrollX() > LEFT_VIEW_WIDTH) {
				lTouchUpOffux = LEFT_VIEW_WIDTH;
				enter = !enter;
			}

			// 边缘内向右拉动
			if (enter && PULL_DIRECTION) {
				if ((LEFT_VIEW_WIDTH - getScrollX()) >= LEFT_VIEW_WIDTH / 3) {// 可以展开第一页面
					ismenuclose = false;
					lTouchUpOffux = 0;
				} else {// 拉动距离不够，回到第二界面
					lTouchUpOffux = LEFT_VIEW_WIDTH;
				}
				System.out.print("right---" + ismenuclose);
				
				enter = !enter;
			}

			// 边缘内向左拉动
			if (enter && !PULL_DIRECTION) {
				if (getScrollX() >= LEFT_VIEW_WIDTH / 3) {
					lTouchUpOffux = LEFT_VIEW_WIDTH;
					ismenuclose = true;
				} else {
					lTouchUpOffux = 0;
				}
				System.out.print("left---" + ismenuclose);
				
			}
			isSlidingState = true;
			lScroller.startScroll(getScrollX(), 0,
					lTouchUpOffux - getScrollX(), 0, DURATION_TIME);
			invalidate();
			enter = true;// 每次使用完还原状态
			// }
			lTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_DOWN:
			// 结束动画
			if (lScroller != null) {
				if (!lScroller.isFinished()) {
					lScroller.abortAnimation();
				}
			}
			lTouchX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			// 当状态为滑动时 执行该脚本实现滑动效果
			// if (lTouchState == TOUCH_STATE_SCROLLING) {

			// 如果滑动时菜单项为显示状态，将其隐藏
			if (LEFT_VIEW_STATE) {
				getChildAt(0).setVisibility(View.INVISIBLE);
			}
			int offx = 0;// 此次移动的偏移量
			// 计算拉动是否超出边缘
			if ((getScrollX() + (int) (lTouchX - event.getX()) < 0)
					|| (getScrollX() + (int) (lTouchX - event.getX()) > LEFT_VIEW_WIDTH)) {// VIEW已经被拉过边缘
				offx = (int) ((lTouchX - event.getX()) * PULL_RATIO);
			} else {// VIEW处于边缘内
				offx = (int) (lTouchX - event.getX());
			}

			// 根据本次的移动距离计算用户的方向
			PULL_DIRECTION = lTouchX == event.getX() ? PULL_DIRECTION
					: lTouchX < event.getX();

			// BODY是否可以滑动出范围
			if (!lSlidingOutBorder) {
				// 向右拉动限制
				if (PULL_DIRECTION && getScrollX() + offx < 0) {
					offx = 0;
				}

				// 想做拉动限制
				if (!PULL_DIRECTION && getScrollX() + offx > LEFT_VIEW_WIDTH) {
					offx = LEFT_VIEW_WIDTH - getScrollX();
				}
			}
			System.out.println("offx=" + offx);
			scrollBy(offx, 0);
			lTouchX = event.getX();

			// }
			break;
		case MotionEvent.ACTION_CANCEL:
			lTouchState = TOUCH_STATE_REST;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		if (lScroller.computeScrollOffset()) {
			// 产生了动画效果，根据当前值 每次滚动一点
			scrollTo(lScroller.getCurrX(), lScroller.getCurrY());
			// 此时同样也需要刷新View ，否则效果可能有误差
			postInvalidate();
		} else {
			if (isSlidingState && lSlidingMenuState == SLIDING_MENU_COVER) {
				if (lTouchUpOffux == 0) {
					LEFT_VIEW_STATE = false;
					getChildAt(0).setVisibility(View.VISIBLE);
				}
				if (lTouchUpOffux == LEFT_VIEW_WIDTH) {
					LEFT_VIEW_STATE = true;
					getChildAt(0).setVisibility(View.INVISIBLE);
				}
				isSlidingState = false;
			}
		}
	}

	void enableChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(true);
		}
	}

	void clearChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		while (!ismenuclose) {
			lScroller.startScroll(getScrollX(), 0, LEFT_VIEW_WIDTH, 0,
					DURATION_TIME);
			ismenuclose = true;
			lTouchState = TOUCH_STATE_REST;
			invalidate();
		}
	}

	/**
	 * 展开菜单
	 */
	public void openMenu() {
		while (ismenuclose) {
			System.out.println("---------------openmenu");
			lScroller.startScroll(getScrollX(), 0, -LEFT_VIEW_WIDTH, 0,
					DURATION_TIME);
			ismenuclose = false;
			lTouchState = TOUCH_STATE_REST;
			invalidate();
		}
	}

	/**
	 * 获取当前菜单使用的样式CODE
	 * 
	 * @return
	 */
	public int getlSlidingMenuState() {
		return lSlidingMenuState;
	}

	/**
	 * 设置菜单的出现样式。取值范围：MySlidingMenuView.SLIDING_MENU_TILE(default) or
	 * MySlidingMenuView.SLIDING_MENU_COVER
	 * 
	 * @param lSlidingMenuState
	 */
	public void setlSlidingMenuState(int lSlidingMenuState) {
		this.lSlidingMenuState = lSlidingMenuState;
	}

	/**
	 * 获取BODY是否可以滑动出边界范围
	 * 
	 * @return true可以
	 */
	public boolean islSlidingOutBorder() {
		return lSlidingOutBorder;
	}

	/**
	 * 设置BODY是否可以滑动出边界范围
	 * 
	 * @param lSlidingOutBorder
	 *            true可以滑动出边界范围
	 */
	public void setlSlidingOutBorder(boolean lSlidingOutBorder) {
		this.lSlidingOutBorder = lSlidingOutBorder;
	}

}
