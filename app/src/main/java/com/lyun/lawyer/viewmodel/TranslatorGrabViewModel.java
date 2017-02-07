package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.lawyer.R;
import com.lyun.lawyer.adapter.TranslatorGrabAdapter;
import com.lyun.library.mvvm.bindingadapter.recyclerview.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorGrabViewModel extends ViewModel {
    public final ObservableBoolean isAutoRefresh = new ObservableBoolean();
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt headViewRes = new ObservableInt();
    public final ObservableField<RelayCommand> itemClickCommand = new ObservableField<>();
    public PullToRefreshLayout.OnRefreshListener onRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            List<ViewModel> list = new ArrayList<>();
            list.add(new TranslatorGrabItemViewModel("姓名1", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名2", "15:11:45", "图文"));
            list.add(new TranslatorGrabItemViewModel("姓名3", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名4", "15:11:45", "已抢"));
            list.add(new TranslatorGrabItemViewModel("姓名5", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名6", "15:11:45", "超时"));
            list.add(new TranslatorGrabItemViewModel("姓名7", "15:11:45", "已抢"));
            list.add(new TranslatorGrabItemViewModel("姓名8", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名9", "15:11:45", "图文"));
            list.add(new TranslatorGrabItemViewModel("姓名10", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名11", "15:11:45", "超时"));
            list.add(new TranslatorGrabItemViewModel("姓名12", "15:11:45", "图文"));
            list.add(new TranslatorGrabItemViewModel("姓名13", "15:11:45", "语音"));
            list.add(new TranslatorGrabItemViewModel("姓名14", "15:11:45", "超时"));
            headViewRes.set(0);
            notifyData.set(list);
            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//            List<ViewModel> list = new ArrayList<>();
//            list.add(new TranslatorGrabItemViewModel("姓名1","15:11:45","语音"));
//            list.add(new TranslatorGrabItemViewModel("姓名2","15:11:45","图文"));
//            list.add(new TranslatorGrabItemViewModel("姓名3","15:11:45","语音"));
//            list.add(new TranslatorGrabItemViewModel("姓名4","15:11:45","已抢"));
//            list.add(new TranslatorGrabItemViewModel("姓名5","15:11:45","语音"));
//            list.add(new TranslatorGrabItemViewModel("姓名6","15:11:45","超时"));
//            list.add(new TranslatorGrabItemViewModel("姓名7","15:11:45","已抢"));
//            list.add(new TranslatorGrabItemViewModel("姓名8","15:11:45","语音"));
//            list.add(new TranslatorGrabItemViewModel("姓名9","15:11:45","图文"));
//            list.add(new TranslatorGrabItemViewModel("姓名10","15:11:45","语音"));
//            list.add(new TranslatorGrabItemViewModel("姓名11","15:11:45","超时"));
//            notifyData.set(list);
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    };
    public RelayCommand<RecyclerView> recyclerViewLayoutManageCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });
    public RelayCommand<ViewBindingAdapter.ClickListenerData> clickListenerDataRelayCommand = new RelayCommand<ViewBindingAdapter.ClickListenerData>(data -> {
        getToast().show("点击" + data.position);
    });

    public TranslatorGrabViewModel() {
        init();
    }

    private void init() {
        List<TranslatorGrabItemViewModel> list = new ArrayList<>();
        TranslatorGrabAdapter adapter = new TranslatorGrabAdapter(list, R.layout.item_translator_grab_layout);
        this.adapter.set(adapter);
        headViewRes.set(R.layout.translator_grab_null_bg_layout);
        itemClickCommand.set(clickListenerDataRelayCommand);
        isAutoRefresh.set(true);
    }
}
