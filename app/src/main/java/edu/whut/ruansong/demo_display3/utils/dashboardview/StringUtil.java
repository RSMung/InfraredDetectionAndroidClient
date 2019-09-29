package edu.whut.ruansong.demo_display3.utils.dashboardview;

import java.math.BigDecimal;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/12
 * Time: 23:09
 */
public class StringUtil {
    /**
     * float 转成一位小数
     *
     * @param value
     * @return
     */
    public static String floatFormat(float value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_DOWN);
        return bd.toString();
    }
}
