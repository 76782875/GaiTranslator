package com.lyun.lawyer.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.lawyer.R;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorCenterFragmentViewModel extends ViewModel {
    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示
    public final ObservableField<String> userName = new ObservableField<>();//昵称
    public final ObservableField<String> translateTime = new ObservableField<>();//翻译时长
    public final ObservableField<String> makeMoney = new ObservableField<>();//赚钱
    public final ObservableField<String> personTime = new ObservableField<>();//人次
    public final ObservableInt exitVisible = new ObservableInt();//退出登录按钮的显示
    private Intent intent;

    public TranslatorCenterFragmentViewModel() {
        init();
    }

    private void init() {
        userName.set("昵称");
        translateTime.set("0 分钟");
        makeMoney.set("0 元");
        personTime.set("0 次");
        exitVisible.set(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.translator_center_setting:
                intent = new Intent("com.lyun.user.intent.action.TRANSLATOR_SETTING");
                getActivity().startActivity(intent);
                break;
            case R.id.translator_center_avatar:
                intent = new Intent("com.lyun.user.intent.action.TRANSLATOR_LOGIN");
                getActivity().startActivity(intent);
                break;
        }
    }

}
