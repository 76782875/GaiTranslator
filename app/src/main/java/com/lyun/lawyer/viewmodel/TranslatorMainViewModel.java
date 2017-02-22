package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.R;
import com.lyun.lawyer.adapter.TranslatorGrabAdapter;
import com.lyun.lawyer.api.response.TranslationOrder;
import com.lyun.lawyer.model.TranslationOrderModel;
import com.lyun.library.mvvm.bindingadapter.recyclerview.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorMainViewModel extends ViewModel {

    public final ObservableBoolean isAutoRefresh = new ObservableBoolean();
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<TranslatorGrabItemViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt headViewRes = new ObservableInt();
    public final ObservableField<RelayCommand> itemClickCommand = new ObservableField<>();

    public final ObservableInt refreshResult = new ObservableInt();
    public final ObservableInt loadMoreResult = new ObservableInt();

    private int currentTranslationOrderPage = 0;
    private int nextTranslationOrderPage = 0;
    private int totalTranslationOrderPage = 0;

    public TranslatorMainViewModel() {
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

    public PullToRefreshLayout.OnRefreshListener onRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            queryOrders(0, true);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            if (nextTranslationOrderPage <= totalTranslationOrderPage) {
                queryOrders(nextTranslationOrderPage, false);
            } else {
                loadMoreResult.set(PullToRefreshLayout.DONE);
                loadMoreResult.notifyChange();
            }
        }
    };

    public RelayCommand<RecyclerView> recyclerViewLayoutManageCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });

    public RelayCommand<ViewBindingAdapter.ClickListenerData> clickListenerDataRelayCommand = new RelayCommand<ViewBindingAdapter.ClickListenerData>(data -> {
        getToast().show("点击" + data.position);
    });

    public void queryOrders(int page, boolean refresh) {
        new TranslationOrderModel()
                .queryOrder(page)
                .subscribe(pageAPIResult -> {

                    headViewRes.set(0);

                    List<TranslatorGrabItemViewModel> datas = new ArrayList<>();

                    Page<List<TranslationOrder>> content = pageAPIResult.getContent();
                    List<TranslationOrder> orders = null;
                    if (content != null) {
                        orders = content.getData();
                    }

                    if (orders != null) {
                        for (TranslationOrder order : orders) {
                            datas.add(new TranslatorGrabItemViewModel(order.getUsername(), order.getOrdertype()));
                        }
                    }

                    notifyData.set(datas);
                    currentTranslationOrderPage = page;
                    nextTranslationOrderPage = currentTranslationOrderPage + 1;
                    if (refresh) {
                        refreshResult.set(PullToRefreshLayout.SUCCEED);
                        refreshResult.notifyChange();
                    } else {
                        loadMoreResult.set(PullToRefreshLayout.SUCCEED);
                        loadMoreResult.notifyChange();
                    }
                }, throwable -> {
                    if (refresh) {
                        refreshResult.set(PullToRefreshLayout.FAIL);
                        refreshResult.notifyChange();
                    } else {
                        loadMoreResult.set(PullToRefreshLayout.FAIL);
                        loadMoreResult.notifyChange();
                    }
                });
    }

}
