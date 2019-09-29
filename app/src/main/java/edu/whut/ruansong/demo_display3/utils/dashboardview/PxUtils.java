package edu.whut.ruansong.demo_display3.utils.dashboardview;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/12
 * Time: 23:08
 */
public class PxUtils {
    /**
     * 单位转换
     */
    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}