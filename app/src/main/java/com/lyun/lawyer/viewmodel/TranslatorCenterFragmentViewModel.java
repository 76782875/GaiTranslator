package com.lyun.lawyer.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.lawyer.Account;
import com.lyun.lawyer.AppIntent;
import com.lyun.lawyer.R;
import com.lyun.lawyer.model.StatisticsCardNoModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.utils.FormatUtil;
import com.lyun.utils.TimeUtil;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorCenterFragmentViewModel extends ViewModel {

    public final ObservableBoolean topVisible = new ObservableBoolean(false);//android 5.0以上显示，否则不显示
    public final ObservableField<String> userName = new ObservableField<>();//昵称
    public final ObservableField<String> translateTime = new ObservableField<>();//翻译时长
    public final ObservableField<String> personTime = new ObservableField<>();//人次
    public final ObservableInt exitVisible = new ObservableInt();//退出登录按钮的显示
    public final ObservableField<String> cardNo = new ObservableField<>("");

    private Intent intent;

    @WatchThis
    public final BaseObservable onLogout = new BaseObservable();

    @Override
    public void onResume() {
        super.onResume();
        if (Account.preference().isLogin()) {
            getTranslatorDes(Account.preference().getCardNo());//获取数据统计
            Observable.empty()
                    .delay(2, TimeUnit.SECONDS)
                    .doOnComplete(() -> getTranslatorDes(Account.preference().getCardNo()))
                    .subscribe();
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
        cardNo.set(Account.Preference.instance().getCardNo());
        userName.set(FormatUtil.formatUserName(Account.preference().getCardNo()));
        exitVisible.set(View.VISIBLE);
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
                    if (apiResult.isSuccess()) {//获取成功
                        translateTime.set(TimeUtil.convertMin2Str(apiResult.getContent().getSurplusTime()));
                        personTime.set(apiResult.getContent().getCallFrequency());
                    }
                });
    }

    public TranslatorCenterFragmentViewModel() {
        init();//初始化数据
    }

    private void init() {
        userName.set("昵称");
        translateTime.set("-- 分钟");
        personTime.set("-- ");
        exitVisible.set(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(true);
        } else {
            topVisible.set(false);
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
