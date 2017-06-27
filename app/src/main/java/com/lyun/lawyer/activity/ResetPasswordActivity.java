package com.lyun.lawyer.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.R;
import com.lyun.lawyer.databinding.ActivityResetPasswordBinding;
import com.lyun.lawyer.viewmodel.ResetPasswordActivityViewModel;
import com.lyun.lawyer.viewmodel.watchdog.IResetPasswordActivityViewModelCallbacks;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class ResetPasswordActivity extends GeneralToolbarActivity<ActivityResetPasswordBinding, ResetPasswordActivityViewModel> implements IResetPasswordActivityViewModelCallbacks {
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

    @Override
    public void onResetPasswordResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

    @Override
    public void onLogout(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
