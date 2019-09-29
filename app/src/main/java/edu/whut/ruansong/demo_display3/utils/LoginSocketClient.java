package edu.whut.ruansong.demo_display3.utils;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/17
 * Time: 8:31
 */
public class LoginSocketClient extends Thread{
    private String user,password;
    private String server_ip = "47.103.21.160";
    private int port = 1234;
    private String result;//用于保存主机返回的密码验证状态

    public LoginSocketClient(String user, String password) {
        this.user = user;
        this.password = password;
    }
    @Override
    public void run(){
//        Socket socket = null;
//        OutputStream outputStream = null;
//        InputStream inputStream = null;

    }
}
