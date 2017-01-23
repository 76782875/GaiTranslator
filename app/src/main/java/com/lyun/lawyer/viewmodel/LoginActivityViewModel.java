package com.lyun.lawyer.viewmodel;

import android.content.Intent;
import android.view.View;

import com.lyun.lawyer.R;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * Created by 郑成裕 on 2017/1/22.
 */

public class LoginActivityViewModel extends ViewModel {
    private Intent intent;

    public void onViewModel(View view) {
        switch (view.getId()) {
            case R.id.textView_findPassword:
                intent = new Intent("com.lyun.user.intent.action.FIND_PASSWORD");
                getActivity().startActivity(intent);
                break;
        }
    }
}
