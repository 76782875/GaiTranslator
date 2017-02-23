package com.lyun.lawyer.fragment;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.lawyer.R;
import com.lyun.lawyer.api.response.TranslationOrder;
import com.lyun.lawyer.databinding.FragmentTranslatorGrabLayoutBinding;
import com.lyun.lawyer.im.session.SessionHelper;
import com.lyun.lawyer.viewmodel.TranslatorMainViewModel;
import com.lyun.lawyer.viewmodel.watchdog.ITranslatorMainViewModelCallbacks;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译主页抢单页面)
 */

public class TranslatorMainFragment extends MvvmFragment<FragmentTranslatorGrabLayoutBinding, TranslatorMainViewModel>
        implements ITranslatorMainViewModelCallbacks {
    public TranslatorMainFragment() {
    }

    public static TranslatorMainFragment newInstance() {
        TranslatorMainFragment fragment = new TranslatorMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected TranslatorMainViewModel createViewModel() {
        return new TranslatorMainViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_translator_grab_layout;
    }

    @Override
    public void onGrabOrderSuccess(ObservableField<TranslationOrder> observableField, int fieldId) {
        SessionHelper.startP2PSession(getContext(), observableField.get().getUsername());
    }

    @Override
    public void onGrabOrderFail(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getContext(), observableField.get(), Toast.LENGTH_LONG).show();
    }
}
