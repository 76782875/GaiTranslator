package com.lyun.lawyer.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.lawyer.Account;
import com.lyun.lawyer.AppIntent;
import com.lyun.lawyer.R;
import com.lyun.lawyer.model.StatisticsCardNoModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorCenterFragmentViewModel extends ViewModel {

    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示
    public final ObservableField<String> userName = new ObservableField<>();//昵称
    public final ObservableField<String> translateTime = new ObservableField<>();//翻译时长
    public final ObservableField<String> personTime = new ObservableField<>();//人次
    public final ObservableInt exitVisible = new ObservableInt();//退出登录按钮的显示

    private Intent intent;

    @WatchThis
    public final BaseObservable onLogout = new BaseObservable();

    @Override
    public void onResume() {
        super.onResume();
        if (Account.preference().isLogin()) {
            getTranslatorDes(Account.preference().getPhone());//获取数据统计
            setTranslatorInformation();//更新昵称
        } else {
            exitVisible.set(View.INVISIBLE);
            userName.set("");
        }
    }

    /**
     * 更新昵称
     */
    private void setTranslatorInformation() {
        userName.set(hideUserName(Account.preference().getPhone()));
        exitVisible.set(View.VISIBLE);
    }

    private String hideUserName(String phone) {
        try {
            return phone.substring(0, 3) + "*****" + phone.substring(8);
        } catch (Exception e) {
            return phone;
        }
    }

    /**
     * 数据统计
     *
     * @param orderHand
     */
    private void getTranslatorDes(String orderHand) {
        new StatisticsCardNoModel().getStatistics(orderHand)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if ("0".equals(apiResult.getStatus())) {//获取成功
                        translateTime.set(apiResult.getContent().getSurplusTime());
                        personTime.set(apiResult.getContent().getCallFrequency());
                    }
                });
    }

    public TranslatorCenterFragmentViewModel() {
        init();//初始化数据
    }

    private void init() {
        userName.set("昵称");
        translateTime.set("-- ");
        personTime.set("-- ");
        exitVisible.set(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.translator_center_setting:
                intent = new Intent(AppIntent.ACTION_SETTINGS);
                getActivity().startActivity(intent);
                break;
            case R.id.translator_center_avatar:
                // intent = new Intent(AppIntent.ACTION_LOGIN);
                // getActivity().startActivity(intent);
                break;
            case R.id.translator_center_exit:
                exit();
                break;
        }
    }

    //退出登录
    private void exit() {
        Account.preference().clear();
        onLogout.notifyChange();
    }

}
