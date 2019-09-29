package edu.whut.ruansong.demo_display3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import edu.whut.ruansong.demo_display3.R;
import edu.whut.ruansong.demo_display3.utils.BaseActivity;

public class Login extends BaseActivity {
    private Button b_confirm ;
    private EditText user_EditText, pass_EditText;
    private CheckBox keep_user,keep_password;
    private String user,pass;
    private SharedPreferences.Editor sharePre_user, sharePre_password;
    private static int status_login = 0;//登录状态
    private String correct_user = "001",correct_pass = "123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(status_login == 0){//未登录
            b_confirm = findViewById(R.id.button_login);
            user_EditText = findViewById(R.id.user_text);
            pass_EditText = findViewById(R.id.pass_text);
            keep_user = findViewById(R.id.keep_user);
            keep_password = findViewById(R.id.keep_password);
            loadDefaultMsg();
            initButton_Confirm();
        }else{//已登录
            jumpToNext();
        }
    }

    public void jumpToNext(){
        Intent jump_intent = new Intent(Login.this,MDisplay.class);
        startActivity(jump_intent);
    }

    public void loadDefaultMsg() {//加载默认用户名和密码

        //加载默认用户名
        SharedPreferences pref = getSharedPreferences("username_data", MODE_PRIVATE);
        String default_name = pref.getString("user_name", "");
        if (!default_name.isEmpty()) {
            user_EditText.setText(default_name);
        }
        //加载默认密码
        SharedPreferences pref_pas = getSharedPreferences("password_data", MODE_PRIVATE);
        String default_password = pref_pas.getString("password", "");
        if (!default_password.isEmpty()) {
            pass_EditText.setText(default_password);
        }
    }

    public void initButton_Confirm(){
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = user_EditText.getText().toString();
                pass = pass_EditText.getText().toString();
                if(!user.isEmpty()&&!pass.isEmpty()){//两个都不为空
                    /**
                     * 保存用户名和密码*/
                    //检测是否想要记住用户名
                    sharePre_user = getSharedPreferences("username_data",
                            MODE_PRIVATE).edit();
                    if (keep_user.isChecked()) {
                        sharePre_user.putString("user_name", user);//存入数据
                    } else {
                        sharePre_user.clear();//清除数据
                    }

                    //检测是否想要记住密码
                    sharePre_password = getSharedPreferences("password_data",
                            MODE_PRIVATE).edit();
                    if (keep_password.isChecked()) {
                        sharePre_password.putString("password", pass);//保存
                    } else {
                        sharePre_password.clear();//清除
                    }

                    sharePre_user.apply();//生效
                    sharePre_password.apply();

                    /**
                     * 验证密码*/
                    if (user.equals(correct_user) && pass.equals(correct_pass)){
                        status_login = 1;
                        jumpToNext();
                    }
                }
            }
        });
    }
}
