package com.lyun.lawyer.fragment;

import android.os.Bundle;

import com.lyun.fragment.BaseFragment;

/**
 * @author Gordon
 * @since 2017/1/20
 * do(翻译主页抢单页面)
 */

public class TranslatorMainFragment extends BaseFragment {
    public TranslatorMainFragment() {
    }
    public static TranslatorMainFragment newInstance() {
        TranslatorMainFragment fragment = new TranslatorMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
