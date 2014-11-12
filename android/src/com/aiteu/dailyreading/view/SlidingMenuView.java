package com.aiteu.dailyreading.view;


import com.aiteu.dailyreading.R;
import com.nineoldandroids.view.ViewHelper;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenuView extends HorizontalScrollView {

	private LinearLayout mLayout;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private VelocityTracker mVelocityTracker = null; //定义速度追踪器
	private int mPointerId; //用于求滑动速度的实例 id为点击触点ID
	private int slidstate = 0; //0表示未达到速度阈值；1表示达到向右滑动的阈值；2表示达到向左滑动的阈值
	
	private int screenWidth;
	private int mMenuWidth;
	private int mMenuPaddingRight = 150;
	private float velocityX;
	
	private boolean once;
	private boolean isOpen;
	
	/**
	 * 未使用属性定义事，调用
	 * @param context
	 * @param attrs
	 */
	@SuppressLint("ServiceCast")
	public SlidingMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}
	
	/**
	 * 使用属性定义时，调用
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlidingMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenuView, defStyle, 0);
		int n = array.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenuView_rightPadding:
				mMenuPaddingRight = array.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		
		array.recycle();
		
		WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wManager.getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		
		//把db转换成px
		
//		mMenuPaddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics());
	}


	public SlidingMenuView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 设置子View的宽和高
	 * 设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (!once) {
			mLayout = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mLayout.getChildAt(0);
			mContent = (ViewGroup) mLayout.getChildAt(1);
			this.mMenuWidth = mMenu.getLayoutParams().width = screenWidth - mMenuPaddingRight;
			mContent.getLayoutParams().width = screenWidth;
			once = true;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/**
	 * 通过设置偏移量，将menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//获得VelocityTracker类实例
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		int action = ev.getAction();
		mVelocityTracker.addMovement(ev);  //Add a user’s movement to the tracker.
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mPointerId = ev.getPointerId(0);
			break;
		case MotionEvent.ACTION_MOVE:
			//求瞬间速度：
			mVelocityTracker.computeCurrentVelocity(1, 1200);
			velocityX = mVelocityTracker.getXVelocity(mPointerId);
			Log.i("lyc","sudu:" + velocityX);
			//改变slidstate的状态
			if (velocityX >= 3.0 && slidstate == 0) {
				slidstate = 2;
			}
			if (velocityX <= -3.0 && slidstate == 0) {
				slidstate =1;
			}
			break;
		case MotionEvent.ACTION_UP:
			releaseVelocityTracker();
			int ScrollX = getScrollX();
			Log.i("lyc","slidestate:"+slidstate);
			
			switch (slidstate) {
			case 0:
				if (ScrollX >= mMenuWidth / 2) {
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				}else {
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
				break;
			case 1:
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
				break;
			case 2:
				this.smoothScrollTo(0, 0);
				isOpen = true;
				break;
			}
			slidstate = 0;
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	//释放VelocityTrack
	private void releaseVelocityTracker() {
		// TODO Auto-generated method stub
		if (mVelocityTracker != null) {
			mVelocityTracker.clear();
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	public void openMenu() {
		if (isOpen) {
			return;
		}
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}
	
	public void closeMenu(){
		if (!isOpen) {
			return;
		}
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}
	
	
	/**
	 * 将侧滑变成抽屉式
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		
		float rightScale = 0.7f + 0.3f * scale;
		float leftScale = 1.0f - 0.3f * scale;
		float leftAlpha = 0.6f + 0.4f * (1 - scale);
		
		
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);
		
		//设置content的缩放中心点
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
	}

}
