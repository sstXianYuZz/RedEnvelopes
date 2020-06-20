package com.sst.redenvelopes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends FragmentActivity {

    private static FrameLayout floatView;
    private static DragView dragView;
    private static CompletedView taskView;

    public static float float_y = -1;
    public static float float_x = -1;

    @Override
    protected void onResume() {
        super.onResume();
        setFloatView(R.layout.layout_task);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (floatView != null) {
            ViewGroup rootView = (ViewGroup) floatView.getParent();
            if (rootView != null) {
                rootView.removeView(floatView);
            }
        }
    }

    public void start() {
        if (taskView == null) {
            return;
        }
        taskView.start();
    }

    public void stop() {
        if (taskView == null) {
            return;
        }
        taskView.stop();
    }

    /**
     * 任务图标显示
     *
     * @param viewId
     */
    public void setFloatView(@LayoutRes int viewId) {
        if (floatView == null) {
            floatView = (FrameLayout) LayoutInflater.from(this).inflate(viewId, null);
            dragView = floatView.findViewById(R.id.float_drag_view);
            taskView = floatView.findViewById(R.id.tasks_view);
            dragView.setOnDragViewClickListener(new DragView.OnDragViewClickListener() {
                @Override
                public void onDragViewListener(View view) {
                    Toast.makeText(BaseActivity.this, "点击效果", Toast.LENGTH_SHORT).show();
                }
            });
            dragView.setOnDragViewXClickListener(new DragView.OnDragViewMoveXClickListener() {
                @Override
                public void moveY(float y) {
                    float_y = y;
                }

                @Override
                public void moveX(float x) {
                    float_x = x;
                }
            });
            taskView.setOnTouchListener(dragView);

        }
        getWindow().addContentView(floatView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        //view加载完成时回调
        floatView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        floatView.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (float_y != -1) {
                            dragView.setY(float_y);
                        }
                        if (float_x != -1) {
                            dragView.setX(float_x);
                        }
                        return true;
                    }
                });

    }
}
