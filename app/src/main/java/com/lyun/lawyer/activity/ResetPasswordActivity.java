package com.lyun.lawyer.activity;

import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityResetPasswordBinding;
import com.lyun.lawyer.viewmodel.ResetPasswordActivityViewModel;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class ResetPasswordActivity extends GeneralToolbarActivity<ActivityResetPasswordBinding, ResetPasswordActivityViewModel> {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_reset_password;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("修改密码");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected ResetPasswordActivityViewModel createBodyViewModel() {
        return new ResetPasswordActivityViewModel().setPropertyChangeListener(this);
    }
}
