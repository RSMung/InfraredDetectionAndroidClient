package edu.whut.ruansong.demo_display3.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/16
 * Time: 15:15
 */
public class ImageMesBean implements Serializable{
    //和app端的包名要一致

    private static final long serialVersionUID = 1L;
    /**
     * 用来存储数据的JavaBean对象
     */
    private double temp_average;//环境温度平均值
    private double[] grayValue;//图片归一化的灰度值数组
    private double temp_max;//温度最大值
    private double temp_min;//温度最小值
    public ImageMesBean(double temp_average, double[] grayValue, double temp_max, double temp_min) {
        super();
        this.temp_average = temp_average;
        this.grayValue = grayValue;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }
    public double getTemp_average() {
        return temp_average;
    }
    public void setTemp_average(double temp_average) {
        this.temp_average = temp_average;
    }
    public double[] getGrayValue() {
        return grayValue;
    }
    public void setGrayValue(double[] grayValue) {
        this.grayValue = grayValue;
    }
    public double getTemp_max() {
        return temp_max;
    }
    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
    public double getTemp_min() {
        return temp_min;
    }
    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }
    @Override
    public String toString() {
        return "ImageMesBean [temp_average=" + temp_average + ", grayValue=" + Arrays.toString(grayValue)
                + ", temp_max=" + temp_max + ", temp_min=" + temp_min + "]";
    }
}
