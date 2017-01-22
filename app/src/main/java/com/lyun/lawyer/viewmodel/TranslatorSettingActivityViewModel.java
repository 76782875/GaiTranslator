package com.lyun.lawyer.viewmodel;

import android.content.Intent;
import android.view.View;

import com.lyun.lawyer.R;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class TranslatorSettingActivityViewModel extends ViewModel {
    private Intent intent;

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.textView_resetPassword:
                intent = new Intent("com.lyun.user.intent.action.RESET_PASSWORD");
                getActivity().startActivity(intent);
                break;
        }
    }
}
