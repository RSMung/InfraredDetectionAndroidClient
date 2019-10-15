package edu.whut.ruansong.demo_display3.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;

import edu.whut.ruansong.demo_display3.R;
import edu.whut.ruansong.demo_display3.bean.ImageMesBean;
import edu.whut.ruansong.demo_display3.utils.ActivityCollector;
import edu.whut.ruansong.demo_display3.utils.BaseActivity;
import edu.whut.ruansong.demo_display3.utils.ImageSocketClient;
import edu.whut.ruansong.demo_display3.utils.dashboardview.DashboardView;

import static java.lang.Thread.sleep;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/12
 * Time: 23:03
 */
public class MDisplay extends BaseActivity {
    Toolbar toolbar;
    DashboardView dashboardView;//仪表盘
    String temp_dashboard[];//仪表盘表盘刻度
    private ImageMesBean imageMesBean = null;
    private TextView temp_max_view = null;
    private TextView temp_min_view = null;
    private ImageView human_image = null;
    private ImageSocketClient s1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdisplay);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDashBoardView();

        temp_max_view = findViewById(R.id.temp_max);
        temp_min_view = findViewById(R.id.temp_min);
        human_image = findViewById(R.id.human_image);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(s1!=null){
            s1.interrupt();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.Flush:
                Log.e("MDisplay","开始请求");
                /******使用socket与主机通信,android主线程不能用socket*****/

                //手动请求服务器
//                s1 = new ImageSocketClient(mHandler);
//                s1.start();

                /*自动请求服务器*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            while(true){
                                s1 = new ImageSocketClient(mHandler);
                                s1.start();
                                sleep(1000);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.About:
                Toast.makeText(this, "Author:RuanSong",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.Exit:
                ActivityCollector.finishAll();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }//回退键   不返回登录界面

    /**初始化仪表盘*/
    public void initDashBoardView(){
        dashboardView = findViewById(R.id.dashboardView);
        temp_dashboard = new String[]{"0", "10", "20", "30", "40", "50", "60", "70"};
        dashboardView.setTikeStrArray(temp_dashboard);
        dashboardView.setText("平均温度");
        dashboardView.setTextSize(40);
        dashboardView.setUnit("0℃");
        dashboardView.setMaxNum(70);
        dashboardView.setPercent(0);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    imageMesBean = (ImageMesBean)msg.obj;
//                    Toast.makeText(MDisplay.this,imageMesBean.toString(),
//                            Toast.LENGTH_LONG).show();
//                    Toast.makeText(MDisplay.this,"数据获取成功",
//                            Toast.LENGTH_SHORT).show();
                    update_UI_After_REQ_Server();
                    s1.interrupt();
                    break;
            }
        }
    };

    public void update_UI_After_REQ_Server(){
        if(imageMesBean!=null){
            //数据获取
            double temp_average = imageMesBean.getTemp_average();//平均温度
            double[] grayValue = imageMesBean.getGrayValue();//归一化灰度值数组
            double temp_max = imageMesBean.getTemp_max();//温度最大值
            double temp_min = imageMesBean.getTemp_min();//温度最小值

            Log.e("MDisplay","temp_max:"+temp_max);
            Log.e("MDisplay","temp_min:"+temp_min);
            Log.e("MDisplay","temp_average:"+temp_average);

            //更新控件--温度
            dashboardView.setPercent((float)(temp_average));
            temp_max_view.setText(temp_max+"℃");
            temp_min_view.setText(temp_min+"℃");

            int[] image_data = new int[grayValue.length];//灰度值数组
            //将归一化的数据变成灰度值
            for(int i =0; i  < grayValue.length; i++) {
                image_data[i] = (int) ((1-grayValue[i]) * 255);
            }
//            Log.e("MDisplay","grayValue after between 0 and 255:"+
//                    Arrays.toString(image_data));
            //生成图片
            int width = 32,height = 24;
            //生成bitmap对象
            Bitmap human_bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565);
            //像素数组
            int[] pixels = new int[width*height];
            for(int f = 0; f < width*height;f++){
                /**
                 * 测试语句
                 * rgb转int,灰度值设置为R,其余为0*/
//                pixels[f] = Color.rgb(255-image_data[f],0,0);
                /**
                 * 实际语句*/
                //GreyToColorRGB()按照函数关系将0-255灰度值映射到rgb三个通道
                pixels[f] = GreyToColorRGB(image_data[f]);
            }
            //stride设置一行打多少像素，通常一行设置为bitmap的宽度
            human_bitmap.setPixels(pixels,0,width,0,0,width,height);
            //缩放
            human_bitmap = zoomImage(human_bitmap,width*30,height*30);

            //更新控件--图片
            human_image.setImageBitmap(human_bitmap);
        }else{
            Toast.makeText(MDisplay.this,"imageMesBean为空",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 缩放*/
    public  Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 用灰度值生成伪彩色图像*/
    public int GreyToColorRGB(int value){
        //https://blog.csdn.net/huixingshao/article/details/42706699#
        int r,g,b;
        //red
        if (value<64)
            r = 0;
        else if (value<128) //192-168=64
            r = (value - 64)*4;
        else
            r=255;

        //green
        if (value<64)
            g = value*200/64;
        else if (value<192)
            g = 200;
        else
            g= (value-255)*(-200)/63;

        //blue
        if (value<64)
            b = 255;
        else if (value<128)
            b = (value-128)*(-255)/64;
        else
            b=0;
        return Color.rgb(r,g,b);
    }


}
