package com.lyun.lawyer.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.lawyer.Account;
import com.lyun.lawyer.api.response.LoginResponse;
import com.lyun.lawyer.im.login.NimLoginHelper;
import com.lyun.lawyer.model.LoginModel;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.utils.RegExMatcherUtils;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 郑成裕 on 2017/1/22.
 */
public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    @WatchThis
    public final BaseObservable onLoginSuccess = new BaseObservable();
    @WatchThis
    public final ObservableField<Throwable> onLoginFailed = new ObservableField<>();
    @WatchThis
    public final ObservableField<String> onLoginResult = new ObservableField<>();
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入手机号");
        } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入有效手机号");
        } else if (("".equals(password.get())) || (null == password.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入密码");
        } else {
            login(username.get(), password.get());
        }
    });

    private void login(String username, String password) {
        progressDialogShow.set(true);
        new LoginModel().login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            loginNim(username, password, loginResponse);
                            progressDialogShow.set(false);
                        },
                        throwable -> {
                            onLoginFailed.set(throwable);
                            progressDialogShow.set(false);
                        });
    }

    private void loginNim(String username, String password, LoginResponse loginResponse) {
        NimLoginHelper.login(loginResponse.getCardNo(), loginResponse.getYunXinToken()).subscribe(
                loginInfo -> {
                    Account.preference().savePhone(username);
                    Account.preference().savePassword(password);
                    Account.preference().saveToken(loginResponse.getAppToken());
                    Account.preference().saveCardNo(loginResponse.getCardNo());
                    Account.preference().saveNimToken(loginResponse.getYunXinToken());
                    Account.preference().setLogin(true);
                    onLoginSuccess.notifyChange();
                },
                throwable -> onLoginFailed.set(throwable));
    }

}
