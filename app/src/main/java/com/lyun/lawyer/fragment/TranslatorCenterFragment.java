package com.lyun.lawyer.fragment;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.lyun.lawyer.R;
import com.lyun.lawyer.activity.LoginActivity;
import com.lyun.lawyer.databinding.FragmentTranslatorCenterBinding;
import com.lyun.lawyer.viewmodel.TranslatorCenterFragmentViewModel;
import com.lyun.lawyer.viewmodel.watchdog.ITranslatorCenterFragmentViewModelCallbacks;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译中心页面)
 */

public class TranslatorCenterFragment extends MvvmFragment<FragmentTranslatorCenterBinding, TranslatorCenterFragmentViewModel>
        implements ITranslatorCenterFragmentViewModelCallbacks {

    public TranslatorCenterFragment() {
    }

//    public static TranslatorCenterFragment newInstance() {
//        TranslatorCenterFragment fragment = new TranslatorCenterFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @NonNull
    @Override
    protected TranslatorCenterFragmentViewModel createViewModel() {
        return new TranslatorCenterFragmentViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_translator_center;
    }

    @Override
    public void onLogout(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
