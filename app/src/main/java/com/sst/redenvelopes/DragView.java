package com.sst.redenvelopes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Create by sst on 2019/8/23
 */
public class DragView extends FrameLayout implements View.OnTouchListener {

    private static final String TAG = "DragView->";

    private float mStartX;
    private float mStartY;
    private int rawX;
    private int rawY;
    private int lastX;
    private int lastY;
    private int pHeight;
    private int pWidth;
    private long mLastTime;
    private ViewGroup parent;

    private OnDragViewClickListener dragViewClickListener;
    private OnDragViewMoveXClickListener onDragViewXClickListener;
    private float y;

    public void setOnDragViewXClickListener(OnDragViewMoveXClickListener onDragViewXClickListener) {
        this.onDragViewXClickListener = onDragViewXClickListener;
    }

    public interface OnDragViewClickListener {
        void onDragViewListener(View view);
    }

    //移动回调
    public interface OnDragViewMoveXClickListener {
        void moveY(float y);

        void moveX(float x);
    }

    public void setOnDragViewClickListener(OnDragViewClickListener dragViewClickListener) {
        this.dragViewClickListener = dragViewClickListener;
    }

    public DragView(Context context) {
        this(context, null);

    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContentView(View view) {
        removeAllViews();
        if (view != null) {
            addView(view);
//            view.setOnTouchListener(this);
        }
    }


    //判断是否触摸view
    private boolean isTouchInView(ImageView view, float xAxis, float yAxis) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (yAxis >= top && yAxis <= bottom && xAxis >= left
                && xAxis <= right) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        rawX = (int) event.getRawX();
        rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                //获取相对View的坐标，即以此View左上角为原点
                mStartX = event.getRawX();
                mStartY = event.getRawY();
//                if (isTouchInView(iv_close, rawX, rawY)) {
//                    flag = true;
//                }
                mLastTime = System.currentTimeMillis();
                lastX = rawX;
                lastY = rawY;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    pHeight = parent.getHeight();
                    pWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                float x = getX() + dx;
                y = getY() + dy;
                //判断是否到边界
                x = x < 0 ? 0 : x > pWidth - getWidth() ? pWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > pHeight ? pHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;

                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                if (Math.abs(mStartX - event.getRawX()) < 10.0 && Math.abs(mStartY - event.getRawY()) < 10.0) {
                    //处理点击的事件
                    if (dragViewClickListener != null) {
                        dragViewClickListener.onDragViewListener(v);
                    }
                } else {
                    if (lastX > UiUtil.getScreenWidth() / 2 - this.getMeasuredWidth() / 2) {
//                        scrollTo(UiUtil.getScreenWidth(), (int) y);
                        lastX = UiUtil.getScreenWidth() - getMeasuredWidth();
                    } else {
                        lastX = 0;
                    }
                    setX(lastX);
                    if (onDragViewXClickListener != null) {
                        onDragViewXClickListener.moveX(lastX );

                        onDragViewXClickListener.moveY(y);
                    }
                }
                break;
        }
        return true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //获取相对屏幕的坐标，即以屏幕左上角为原点
//        rawX = (int) event.getRawX();
//        rawY = (int) event.getRawY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
//                //获取相对View的坐标，即以此View左上角为原点
//                mStartX = event.getRawX();
//                mStartY = event.getRawY();
////                if (isTouchInView(iv_close, rawX, rawY)) {
////                    flag = true;
////                }
//                mLastTime = System.currentTimeMillis();
//                lastX = rawX;
//                lastY = rawY;
//                if (getParent() != null) {
//                    parent = (ViewGroup) getParent();
//                    pHeight = parent.getHeight();
//                    pWidth = parent.getWidth();
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
//                int dx = rawX - lastX;
//                int dy = rawY - lastY;
//                float x = getX() + dx;
//                float y = getY() + dy;
//                //判断是否到边界
//                x = x < 0 ? 0 : x > pWidth - getWidth() ? pWidth - getWidth() : x;
//                y = getY() < 0 ? 0 : getY() + getHeight() > pHeight ? pHeight - getHeight() : y;
//                setX(x);
//                setY(y);
//                lastX = rawX;
//                lastY = rawY;
//                break;
//            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
//                if (System.currentTimeMillis() - mLastTime < 800) {
//                    if (Math.abs(mStartX - event.getRawX()) < 10.0 && Math.abs(mStartY - event.getRawY()) < 10.0) {
//                        //处理点击的事件
//                        if (flag) {
//                        }
//                    }
//                }
//                flag = false;
//                break;
//        }
//        return true;
//    }

}
