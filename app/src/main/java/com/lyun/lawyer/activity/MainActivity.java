package com.lyun.lawyer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityMainBinding;
import com.lyun.lawyer.im.session.SessionHelper;
import com.lyun.lawyer.service.TranslationOrder;
import com.lyun.lawyer.service.TranslationOrderService;
import com.lyun.lawyer.viewmodel.MainActivityViewModel;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/9
 * do(主页面)
 */
public class MainActivity extends MvvmActivity<ActivityMainBinding, MainActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(TranslationOrderService.Action.START);
        registerReceiver(orderStartReceiver, intentFilter);
    }

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        return new MainActivityViewModel(getActivityViewDataBinding().mainContainer, getSupportFragmentManager());
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(orderStartReceiver);
    }

    private BroadcastReceiver orderStartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderId = intent.getStringExtra(TranslationOrder.ORDER_ID);
            SessionHelper.startTranslationSession(MainActivity.this, intent.getStringExtra(TranslationOrder.USER_ID), orderId);
        }
    };

}

