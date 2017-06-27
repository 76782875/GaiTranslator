package com.lyun.lawyer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lyun.adapter.FragmentPagerBaseAdapter;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/9
 * do(主页面adapter)
 */
public class MainPagerAdapter extends FragmentPagerBaseAdapter {

    public MainPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(context, fm, fragments);
    }

}
