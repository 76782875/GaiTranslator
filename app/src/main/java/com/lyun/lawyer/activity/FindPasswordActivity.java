package com.lyun.lawyer.activity;

import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityFindPasswordBinding;
import com.lyun.lawyer.viewmodel.FindPasswordActivityViewModel;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class FindPasswordActivity extends GeneralToolbarActivity<ActivityFindPasswordBinding, FindPasswordActivityViewModel> {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_find_password;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("找回密码");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected FindPasswordActivityViewModel createBodyViewModel() {
        return new FindPasswordActivityViewModel().setPropertyChangeListener(this);
    }
}
