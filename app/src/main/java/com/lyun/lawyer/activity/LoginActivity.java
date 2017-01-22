package com.lyun.lawyer.activity;

import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityLoginBinding;
import com.lyun.lawyer.viewmodel.LoginActivityViewModel;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginActivityViewModel> {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("登录");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected LoginActivityViewModel createBodyViewModel() {
        return new LoginActivityViewModel().setPropertyChangeListener(this);
    }
}
