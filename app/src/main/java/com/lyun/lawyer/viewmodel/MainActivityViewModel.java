package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;

import com.lyun.entity.TabItemBean;
import com.lyun.lawyer.R;
import com.lyun.lawyer.fragment.TranslatorCenterFragment;
import com.lyun.lawyer.fragment.TranslatorMainFragment;
import com.lyun.library.mvvm.bindingadapter.tablayout.ViewBindAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/30
 * do(主Activity 处理类)
 */
public class MainActivityViewModel extends ViewModel {
    public final ObservableField<ViewPager> viewPage = new ObservableField<>();
    public final ObservableField<FragmentPagerAdapter> adapter = new ObservableField<>();
    public final ObservableField<TabLayout.OnTabSelectedListener> tabListener = new ObservableField<>();
    public final ObservableField<ViewBindAdapter.TabData> tabData = new ObservableField<>();
    public final ObservableInt selectIndex = new ObservableInt();
    private FragmentManager mFragmentManager;
    private List<Fragment> fragments;

    public MainActivityViewModel(ViewPager viewPager, FragmentManager mFragmentManager) {
        this.viewPage.set(viewPager);
        this.mFragmentManager = mFragmentManager;
        init();
    }

    public void init() {
//        TranslatorMainFragment mSpecialistTranslationFragment = new TranslatorMainFragment();
//        TranslatorCenterFragment mUserCenterFragment = new TranslatorCenterFragment();
        if (fragments == null)
            fragments = new ArrayList<>();
        fragments.add(new TranslatorMainFragment());
        fragments.add(new TranslatorCenterFragment());
        adapter.set(getAdapter());
        List<TabItemBean> tabs = new ArrayList<>();
        tabs.add(new TabItemBean(R.drawable.btn_translator_main_selector, "抢单", R.layout.item_main_tab));
        tabs.add(new TabItemBean(R.drawable.btn_translator_center_selector, "我", R.layout.item_main_tab));
        tabListener.set(onTabSelectedListener);
        ViewBindAdapter.TabData tabData = new ViewBindAdapter.TabData();
        tabData.setTabs(tabs);
        this.tabData.set(tabData);
        selectIndex.set(0);
    }

    protected FragmentPagerAdapter getAdapter() {
        return new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                if (fragments == null)
                    return 0;
                return fragments.size();
            }
        };
    }

    //设置监听底部按钮
    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(true);
            } catch (Exception e) {

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(false);
            } catch (Exception e) {

            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(true);
            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentManager = null;
        if (fragments != null) {
            fragments.clear();
            fragments = null;
        }
    }
}
