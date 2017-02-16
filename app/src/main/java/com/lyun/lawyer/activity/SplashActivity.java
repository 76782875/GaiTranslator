package com.lyun.lawyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lyun.activity.BaseActivity;
import com.lyun.lawyer.Account;
import com.lyun.lawyer.R;


/**
 * Created by 郑成裕 on 2017/1/20.
 */

public class SplashActivity extends BaseActivity {
    private static int sleepTime=3500;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (Account.preference().isLogin()) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },sleepTime);
    }
}
