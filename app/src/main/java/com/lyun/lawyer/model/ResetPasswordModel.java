package com.lyun.lawyer.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.lawyer.api.API;
import com.lyun.lawyer.api.request.ResetPasswordBean;
import com.lyun.library.mvvm.model.Model;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/24.
 */

public class ResetPasswordModel extends Model {
    public Observable<APIResult> resetPassword(String username, String password, String newPassword) {
        ResetPasswordBean bean = new ResetPasswordBean();
        bean.setUserName(username);
        bean.setPassword(password);
        bean.setNewPassword(newPassword);
        return API.auth.resetPassword(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
