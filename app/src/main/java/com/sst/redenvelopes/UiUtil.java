package com.sst.redenvelopes;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by iceph0x on 8/29/15.
 */
public class UiUtil {

    private static Point screenSize;


    public static int dip2Pixel(int dp) {
        Resources res = App.getInstance().getResources();
        return dip2Pixel(dp, res);
    }

    /**
     * px转dip
     *
     * @param pixel
     * @return
     */
    public static int pixel2Dip(int pixel) {
        int pre = dip2Pixel(1);
        return pixel / pre;
    }

    public static int dip2Pixel(int dp, Resources res) {
        float scale = res.getDisplayMetrics().density;
        return (int) (dp * scale + (dp >= 0 ? 0.5f : -0.5f));
    }


    public static void closeProgress(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static int getScreenWidth() {
        initScreenSize();
        return screenSize.x;
    }

    public static int getScreenHeight() {
        initScreenSize();
        return screenSize.y;
    }

    private static void initScreenSize() {
        if (screenSize == null) {
            screenSize = new Point();
            WindowManager wm = (WindowManager) App.getInstance().getSystemService(Application.WINDOW_SERVICE);
            wm.getDefaultDisplay().getSize(screenSize);
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                pw.setHeight(height);
                pw.showAsDropDown(anchor, xoff, yoff);
            } else {
                pw.showAsDropDown(anchor, xoff, yoff);
            }
        } catch (Exception e) {

        }
    }

    public static double change(double a) {
        return a * Math.PI / 180;
    }

    public static double changeAngle(double a) {
        return a * 180 / Math.PI;
    }

    private static int commomSize = 80;

//    public static int getAdapterWidth(int imageWidth, int imageHeight) {
//        float attachHeight;
//        float attachWidth;
//        int minWidth = commomSize;
//        int minHeight = commomSize;
//        float maxWidth = (int) (minWidth * 2.5);
//        float maxHeight = (int) (minHeight * 2.5);
//
//        if (imageWidth > imageHeight) {
//            if (imageWidth > maxWidth) {
//                attachWidth = maxWidth;
//                attachHeight = imageHeight * (attachWidth / imageWidth);
//                attachHeight = attachHeight < minHeight ? minHeight : attachHeight;
//            } else {
//                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
//                attachHeight = imageHeight < minHeight ? minHeight : imageHeight;
//            }
//        } else {
//            if (imageHeight > maxHeight) {
//                attachHeight = maxHeight;
//                attachWidth = imageWidth * (attachHeight / imageHeight);
//                attachWidth = attachWidth < minWidth ? minWidth : attachWidth;
//            } else {
//                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
//                attachHeight = imageHeight < minWidth ? minHeight : imageHeight;
//            }
//        }
//        float scale = BaseApplication.getApplication().getResources().getDisplayMetrics().density;
//        return (int) (attachWidth * scale);
//    }
//
//    public static int getAdapterHeight(int imageWidth, int imageHeight) {
//        float attachHeight;
//        float attachWidth;
//        int minWidth = commomSize;
//        int minHeight = commomSize;
//        float maxWidth = (int) (minWidth * 2.5);
//        float maxHeight = (int) (minHeight * 2.5);
//
//        if (imageWidth > imageHeight) {
//            if (imageWidth > maxWidth) {
//                attachWidth = maxWidth;
//                attachHeight = imageHeight * (attachWidth / imageWidth);
//                attachHeight = attachHeight < minHeight ? minHeight : attachHeight;
//            } else {
//                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
//                attachHeight = imageHeight < minHeight ? minHeight : imageHeight;
//            }
//        } else {
//            if (imageHeight > maxHeight) {
//                attachHeight = maxHeight;
//                attachWidth = imageWidth * (attachHeight / imageHeight);
//                attachWidth = attachWidth < minWidth ? minWidth : attachWidth;
//            } else {
//                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
//                attachHeight = imageHeight < minWidth ? minHeight : imageHeight;
//            }
//        }
//        float scale = BaseApplication.getApplication().getResources().getDisplayMetrics().density;
//        return (int) (attachHeight * scale);
//    }

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";

    public static Map<String, Integer> getAdaptSize(int imageWidth, int imageHeight) {
        float attachHeight;
        float attachWidth;
        int minWidth = commomSize;
        int minHeight = commomSize;
        float maxWidth = (int) (minWidth * 2.5);
        float maxHeight = (int) (minHeight * 2.5);

        Map<String, Integer> map = new HashMap<>();

        if (imageWidth > imageHeight) { // 横图
            if (imageWidth > maxWidth) {    // 裁剪
                attachWidth = maxWidth;
                attachHeight = imageHeight * (attachWidth / imageWidth);
                attachHeight = attachHeight < minHeight ? minHeight : attachHeight;
            } else {
                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
                attachHeight = imageHeight < minHeight ? minHeight : imageHeight;
            }
        } else { // 竖图
            if (imageHeight > maxHeight) {
                attachHeight = maxHeight;
                attachWidth = imageWidth * (attachHeight / imageHeight);
                attachWidth = attachWidth < minWidth ? minWidth : attachWidth;
            } else {
                attachWidth = imageWidth < minWidth ? minWidth : imageWidth;
                attachHeight = imageHeight < minWidth ? minHeight : imageHeight;
            }
        }
        float scale = App.getInstance().getResources().getDisplayMetrics().density;
        int resultHeight = (int) (attachHeight * scale);
        int resultWidth = (int) (attachWidth * scale);
        map.put(HEIGHT, resultHeight);
        map.put(WIDTH, resultWidth);
        return map;
    }

    public static Map<String, Integer> getSignleImgAdaptSize(int imageWidth, int imageHeight) {

        float scale = App.getInstance().getResources().getDisplayMetrics().density;
        float attachHeight;
        float attachWidth;
        Map<String, Integer> map = new HashMap<>();

        float rate = (float) imageWidth / imageHeight;
        if (rate < 1 / 3) {
            attachWidth = 300 / 3;
            attachHeight = 300;
        } else if (1 / 3 <= rate && rate < 1) {
            attachWidth = 300 * rate;
            attachHeight = 300;
        } else if (1 == rate) {
            attachWidth = 200;
            attachHeight = 200;
        } else if (1 < rate && rate <= 3) {
            attachWidth = 300;
            attachHeight = 300 / rate;
        } else {
            attachWidth = (getScreenWidth() - dip2Pixel(10 * 2)) / scale;
            attachHeight = attachWidth / 3;
        }

        int resultHeight = (int) (attachHeight * scale);
        int resultWidth = (int) (attachWidth * scale);
        map.put(HEIGHT, resultHeight);
        map.put(WIDTH, resultWidth);
        return map;
    }

    public static int getNavigationBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int height = context.getResources().getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getStatusBarHeight() {
        Resources resources = App.getInstance().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}