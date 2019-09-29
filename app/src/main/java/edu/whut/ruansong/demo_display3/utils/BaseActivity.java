package edu.whut.ruansong.demo_display3.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/12
 * Time: 23:02
 */
public class BaseActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);//add oneself to the activity manager
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//remove oneself from the activity manager
    }
    public static Context getContext() {
        return context;
    }
}