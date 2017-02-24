package com.lyun.lawyer.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.lawyer.BuildConfig;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorSettingActivityViewModel extends ViewModel {
    public final ObservableField<String> appVersion = new ObservableField<>();

    @WatchThis
    public final BaseObservable onNavigationModifyPassword = new BaseObservable();

    public TranslatorSettingActivityViewModel() {
        init();
    }

    private void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
    }

    public RelayCommand onModifyPasswordButtonClick = new RelayCommand(() -> {
        onNavigationModifyPassword.notifyChange();
    });

}
