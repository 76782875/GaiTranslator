package com.lyun.lawyer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityMainBinding;
import com.lyun.lawyer.im.config.preference.UserPreferences;
import com.lyun.lawyer.im.session.SessionHelper;
import com.lyun.lawyer.model.TranslationOrderModel;
import com.lyun.lawyer.service.TranslationOrder;
import com.lyun.lawyer.service.TranslationOrderService;
import com.lyun.lawyer.viewmodel.MainActivityViewModel;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.netease.nimlib.sdk.NIMClient;

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
        registerReceiver(mOrderStartReceiver, intentFilter);
    }

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        UserPreferences.setNotificationToggle(false);
        NIMClient.toggleNotification(false);
        return new MainActivityViewModel(getActivityViewDataBinding().mainContainer, getSupportFragmentManager());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOrderStartReceiver);
    }

    private BroadcastReceiver mOrderStartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String account = intent.getStringExtra(TranslationOrder.USER_ID);
            String orderId = intent.getStringExtra(TranslationOrder.ORDER_ID);
            TranslationOrderModel.OrderType orderType = (TranslationOrderModel.OrderType) intent.getSerializableExtra(TranslationOrder.ORDER_TYPE);
            String targetLanguage = intent.getStringExtra(TranslationOrder.TARGET_LANGUAGE);
            SessionHelper.startTranslationSession(MainActivity.this, account, orderId, orderType, targetLanguage);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // 按返回键不结束activity
        moveTaskToBack(true);
        super.onBackPressed();
    }

}

