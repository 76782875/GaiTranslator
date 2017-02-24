package com.lyun.lawyer.activity;

import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityMainBinding;
import com.lyun.lawyer.viewmodel.MainActivityViewModel;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/9
 * do(主页面)
 */
public class MainActivity extends MvvmActivity<ActivityMainBinding, MainActivityViewModel> {
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
}

