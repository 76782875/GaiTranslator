package com.lyun.lawyer.activity;

import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityUserSettingBinding;
import com.lyun.lawyer.viewmodel.TranslatorSettingActivityViewModel;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorSettingActivity extends GeneralToolbarActivity<ActivityUserSettingBinding, TranslatorSettingActivityViewModel> {


    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_user_setting;
    }

    @NonNull
    @Override
    protected TranslatorSettingActivityViewModel createBodyViewModel() {
        return new TranslatorSettingActivityViewModel().setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("设置");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }


}
