package edu.whut.ruansong.demo_display3.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/12
 * Time: 23:02
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
