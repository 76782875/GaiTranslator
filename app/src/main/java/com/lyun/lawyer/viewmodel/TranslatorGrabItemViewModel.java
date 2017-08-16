package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.lyun.lawyer.api.response.TranslationOrderResponse;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorGrabItemViewModel extends ViewModel {

    public final ObservableField<String> userName = new ObservableField<>();// 需求翻译的用户名
    public final ObservableField<String> orderType = new ObservableField<>();// 抢单按钮类型及文字
    public final ObservableInt orderTextBg = new ObservableInt();//抢单按钮的字体颜色

    @WatchThis
    public final ObservableField<TranslationOrderResponse> onGrabOrder = new ObservableField<>();

    public TranslatorGrabItemViewModel(TranslationOrderResponse order) {
        onGrabOrder.set(order);
        this.userName.set(order.getUsername().substring(0, 3) + "*****" + order.getUsername().substring(8, 11));
        this.orderType.set(order.getOrdertype());
    }

    public void init(int position) {
        switch (orderType.get()) {
            case "语音":
                orderTextBg.set(Color.parseColor("#209ced"));
                break;
            case "图文":
                orderTextBg.set(Color.parseColor("#ffc11e"));
                break;
            default:
                break;
        }
    }

    public final RelayCommand onGrabOrderClick = new RelayCommand(() -> ObservableNotifier.alwaysNotify(onGrabOrder, onGrabOrder.get()));
}
