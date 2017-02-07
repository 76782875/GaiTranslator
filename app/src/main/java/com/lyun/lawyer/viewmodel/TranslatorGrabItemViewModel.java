package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.lyun.lawyer.R;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorGrabItemViewModel extends ViewModel {
    public final ObservableField<String> userName = new ObservableField<>();// 需求翻译的用户名
    public final ObservableField<String> userTime = new ObservableField<>();// 需求翻译的时间
    public final ObservableField<String> grabText = new ObservableField<>();// 抢单按钮类型及文字
    public final ObservableBoolean btnGrabClickable = new ObservableBoolean();//抢单按钮是否可点击
    public final ObservableInt btnGrabBgColor = new ObservableInt();//抢单按钮的背景颜色
    public final ObservableInt btnGrabTextColor = new ObservableInt();//抢单按钮的字体颜色

    public TranslatorGrabItemViewModel() {
    }

    public TranslatorGrabItemViewModel(String userName, String userTime, String grabText) {
        this.userName.set(userName);
        this.userTime.set(userTime);
        this.grabText.set(grabText);
    }

    public void init(int position) {
        switch (grabText.get()) {
            case "语音":
                btnGrabBgColor.set(R.drawable.bg_blue_no_border_round_corner);
                btnGrabTextColor.set(Color.parseColor("#ffffff"));
                btnGrabClickable.set(true);
                break;
            case "图文":
                btnGrabBgColor.set(R.drawable.bg_yellow_no_border_round_corner);
                btnGrabTextColor.set(Color.parseColor("#ffffff"));
                btnGrabClickable.set(true);
                break;
            case "已抢":
                btnGrabBgColor.set(R.drawable.no_border_round_corner_gray_bg);
                btnGrabTextColor.set(Color.parseColor("#ffffff"));
                btnGrabClickable.set(false);
                break;
            case "超时":
                btnGrabBgColor.set(R.drawable.no_border_gray_round_corner_white_bg);
                btnGrabTextColor.set(Color.parseColor("#999999"));
                btnGrabClickable.set(false);
                break;
            default:
                break;
        }
    }
}
