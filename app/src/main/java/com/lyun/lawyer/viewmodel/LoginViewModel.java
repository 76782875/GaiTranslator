package com.lyun.lawyer.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.lawyer.Account;
import com.lyun.lawyer.api.response.LoginResponse;
import com.lyun.lawyer.im.login.NimLoginHelper;
import com.lyun.lawyer.model.LoginModel;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

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

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        login(username.get(), password.get());
    });

    private void login(String username, String password) {
        new LoginModel().login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponseAPIResult -> loginNim(username, password, loginResponseAPIResult.getContent()),
                        throwable -> onLoginFailed.set(throwable));
    }

    private void loginNim(String username, String password, LoginResponse loginResponse) {
        NimLoginHelper.login(username, loginResponse.getYunXinToken()).subscribe(
                loginInfo -> {
                    Account.preference().savePhone(username);
                    Account.preference().savePassword(password);
                    Account.preference().saveToken(loginResponse.getAppKey());
                    Account.preference().setLogin(true);
                    onLoginSuccess.notifyChange();
                },
                throwable -> onLoginFailed.set(throwable));
    }

}
