package com.lyun.lawyer.fragment;

import android.os.Bundle;

import com.lyun.fragment.BaseFragment;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译中心页面)
 */

public class TranslatorCenterFragment extends BaseFragment{
    public TranslatorCenterFragment() {
    }
    public static TranslatorCenterFragment newInstance() {
        TranslatorCenterFragment fragment = new TranslatorCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
