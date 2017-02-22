package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorGrabItemViewModel extends ViewModel {

    public final ObservableField<String> userName = new ObservableField<>();// 需求翻译的用户名
    public final ObservableField<String> orderType = new ObservableField<>();// 抢单按钮类型及文字
    public final ObservableInt orderTextBg = new ObservableInt();//抢单按钮的字体颜色

    public TranslatorGrabItemViewModel() {
    }

    public TranslatorGrabItemViewModel(String userName, String orderType) {
        this.userName.set(userName.substring(0, 3) + "******" + userName.substring(9, 11));
        this.orderType.set(orderType);
    }

    public void init(int position) {
        switch (orderType.get()) {
            case "语音":
                orderTextBg.set(Color.parseColor("#48fe35"));
                break;
            case "图文":
                orderTextBg.set(Color.parseColor("#ffc11e"));
                break;
            default:
                break;
        }
    }
}
