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
 * @doc �໬����˵�
 */
public class MySlidingMenuView extends ViewGroup {

	private float lTouchX;// touch����������ʱ��¼��ǰX����
	private float lTouchY;// touch����������ʱ��¼��ǰY����
	private int lTouchSlop;// ����ʱ����ճ��Ч��
	private int lTouchUpOffux;// �뿪��Ļʱ��ǰ��ƫ����
	private int LEFT_VIEW_WIDTH = 0;// �˵����
	private boolean LEFT_VIEW_STATE;// �˵�״̬ true��ʾ
	private int DURATION_TIME = 350;// ����ʱ�Զ��ƶ��ĳ���ʱ��
	private boolean isSlidingState;// �໬״̬ ����������ʾ���ز˵���ʹ��

	private int lSlidingMenuState;// �˵�״̬��ƽ��ʽ���򸲸�ʽ

	/**
	 * �����Կ���BODY�Ƿ���Ի�������, ͨ��setlSlidingOutBorder(boolean lSlidingOutBorder)��������
	 */
	private boolean lSlidingOutBorder;// BODY�Ƿ���Ի�������

	private boolean PULL_DIRECTION;// true�������� false��������

	private final static int TOUCH_STATE_REST = 0;// ����״̬
	private final static int TOUCH_STATE_SCROLLING = 1;// ����״̬
	public int lTouchState = TOUCH_STATE_REST;// ��ǰ״̬

	// �������� ����view�Ѿ���������Եʱ������û��ֹͣ��VIEW�ĸ�������ı���
	private double PULL_RATIO = 0.5;

	private boolean enter = true;// �Ƿ���Խ�����֤����ָ�뿪��Ļʱʹ��
	public static boolean ismenuclose = true; // menu״̬

	private Scroller lScroller;

	/**
	 * ƽ����ʽ��Ĭ�ϣ�
	 */
	public static final int SLIDING_MENU_TILE = 0;

	/**
	 * ����ʽ��ʽ
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
		// ��ʼ��Scrollerʵ��
		lScroller = new Scroller(context);
		lTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() + 25;// ����25����룬����ʱ����ճ��Ч��
		postDelayed(new Runnable() {
			@Override
			public void run() {
				LEFT_VIEW_WIDTH = getChildAt(0).getWidth();
				scrollTo(LEFT_VIEW_WIDTH, 0);
				if (lSlidingMenuState == SLIDING_MENU_COVER) {
					/* ���ز������˵��ռ䣬������������ִ��ƽ���������� */
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

		// �˴��޸�������Դ�룬��ͼ���ǿ���״̬ʱ���¼����������������´�
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
			//�������˵�ʱ������ұ߿��Թ�ϵ�˵�
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
			// ȥ��lTouchState�жϿ��Ը��õļ��ݷ��б����Ļ���
			// if (lTouchState == TOUCH_STATE_SCROLLING) {
			lTouchUpOffux = getScrollX();// Ĭ�ϲ��ƶ�

			// ��������������Ե
			if (getScrollX() < 0) {
				lTouchUpOffux = 0;
			}
			// ��������������Ե
			if (enter && getScrollX() > LEFT_VIEW_WIDTH) {
				lTouchUpOffux = LEFT_VIEW_WIDTH;
				enter = !enter;
			}

			// ��Ե����������
			if (enter && PULL_DIRECTION) {
				if ((LEFT_VIEW_WIDTH - getScrollX()) >= LEFT_VIEW_WIDTH / 3) {// ����չ����һҳ��
					ismenuclose = false;
					lTouchUpOffux = 0;
				} else {// �������벻�����ص��ڶ�����
					lTouchUpOffux = LEFT_VIEW_WIDTH;
				}
				System.out.print("right---" + ismenuclose);
				
				enter = !enter;
			}

			// ��Ե����������
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
			enter = true;// ÿ��ʹ���껹ԭ״̬
			// }
			lTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_DOWN:
			// ��������
			if (lScroller != null) {
				if (!lScroller.isFinished()) {
					lScroller.abortAnimation();
				}
			}
			lTouchX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			// ��״̬Ϊ����ʱ ִ�иýű�ʵ�ֻ���Ч��
			// if (lTouchState == TOUCH_STATE_SCROLLING) {

			// �������ʱ�˵���Ϊ��ʾ״̬����������
			if (LEFT_VIEW_STATE) {
				getChildAt(0).setVisibility(View.INVISIBLE);
			}
			int offx = 0;// �˴��ƶ���ƫ����
			// ���������Ƿ񳬳���Ե
			if ((getScrollX() + (int) (lTouchX - event.getX()) < 0)
					|| (getScrollX() + (int) (lTouchX - event.getX()) > LEFT_VIEW_WIDTH)) {// VIEW�Ѿ���������Ե
				offx = (int) ((lTouchX - event.getX()) * PULL_RATIO);
			} else {// VIEW���ڱ�Ե��
				offx = (int) (lTouchX - event.getX());
			}

			// ���ݱ��ε��ƶ���������û��ķ���
			PULL_DIRECTION = lTouchX == event.getX() ? PULL_DIRECTION
					: lTouchX < event.getX();

			// BODY�Ƿ���Ի�������Χ
			if (!lSlidingOutBorder) {
				// ������������
				if (PULL_DIRECTION && getScrollX() + offx < 0) {
					offx = 0;
				}

				// ������������
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
			// �����˶���Ч�������ݵ�ǰֵ ÿ�ι���һ��
			scrollTo(lScroller.getCurrX(), lScroller.getCurrY());
			// ��ʱͬ��Ҳ��Ҫˢ��View ������Ч�����������
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
	 * �رղ˵�
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
	 * չ���˵�
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
	 * ��ȡ��ǰ�˵�ʹ�õ���ʽCODE
	 * 
	 * @return
	 */
	public int getlSlidingMenuState() {
		return lSlidingMenuState;
	}

	/**
	 * ���ò˵��ĳ�����ʽ��ȡֵ��Χ��MySlidingMenuView.SLIDING_MENU_TILE(default) or
	 * MySlidingMenuView.SLIDING_MENU_COVER
	 * 
	 * @param lSlidingMenuState
	 */
	public void setlSlidingMenuState(int lSlidingMenuState) {
		this.lSlidingMenuState = lSlidingMenuState;
	}

	/**
	 * ��ȡBODY�Ƿ���Ի������߽緶Χ
	 * 
	 * @return true����
	 */
	public boolean islSlidingOutBorder() {
		return lSlidingOutBorder;
	}

	/**
	 * ����BODY�Ƿ���Ի������߽緶Χ
	 * 
	 * @param lSlidingOutBorder
	 *            true���Ի������߽緶Χ
	 */
	public void setlSlidingOutBorder(boolean lSlidingOutBorder) {
		this.lSlidingOutBorder = lSlidingOutBorder;
	}

}
