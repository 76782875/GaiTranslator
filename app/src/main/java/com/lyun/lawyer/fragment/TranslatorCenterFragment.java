package com.lyun.lawyer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.fragment.BaseFragment;
import com.lyun.lawyer.R;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译中心页面)
 */

public class TranslatorCenterFragment extends BaseFragment {
    public TranslatorCenterFragment() {
    }

    public static TranslatorCenterFragment newInstance() {
        TranslatorCenterFragment fragment = new TranslatorCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translator_center, container, false);
        return view;
    }
}
