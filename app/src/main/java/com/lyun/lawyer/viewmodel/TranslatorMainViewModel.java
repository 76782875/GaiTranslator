package com.lyun.lawyer.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.R;
import com.lyun.lawyer.adapter.TranslatorGrabAdapter;
import com.lyun.lawyer.api.response.TranslationOrderResponse;
import com.lyun.lawyer.model.TranslationOrderModel;
import com.lyun.lawyer.viewmodel.watchdog.ITranslatorGrabItemViewModelCallbacks;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.utils.L;
import com.lyun.widget.refresh.PullToRefreshLayout;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class TranslatorMainViewModel extends ViewModel implements ITranslatorGrabItemViewModelCallbacks {

    public final ObservableBoolean isAutoRefresh = new ObservableBoolean();
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableList<TranslatorGrabItemViewModel> notifyData = new ObservableArrayList<>();
    public final ObservableInt headViewRes = new ObservableInt();
    public final ObservableInt backGround = new ObservableInt();

    @WatchThis
    public final ObservableField onGrabOrderStart = new ObservableField<>();
    @WatchThis
    public final ObservableField<TranslationOrderResponse> onGrabOrderSuccess = new ObservableField<>();
    @WatchThis
    public final ObservableField<String> onGrabOrderFail = new ObservableField<>();

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
        backGround.set(R.mipmap.translator_grab_bg);
        headViewRes.set(R.layout.translator_grab_null_bg_layout);
        queryOrders(0, true);
    }

    protected Timer mAutoRefreshTimer = new Timer();

    public void startAutoRefresh() {
        mAutoRefreshTimer = new Timer();
        mAutoRefreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ObservableNotifier.alwaysNotify(isAutoRefresh, true);
            }
        }, 0, 10000);
    }

    public void stopAutoRefresh() {
        mAutoRefreshTimer.cancel();
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
                ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
            }
        }
    };

    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());

    public void queryOrders(int page, boolean refresh) {
        new TranslationOrderModel()
                .queryOrder(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    if (refresh) {
                        notifyData.clear();
                    }

                    List<TranslatorGrabItemViewModel> datas = new ArrayList<>();

                    List<TranslationOrderResponse> orders = null;
                    if (result != null) {
                        orders = result.getData();
                    }

                    if (orders != null && orders.size() > 0) {
                        for (TranslationOrderResponse order : orders) {
                            datas.add(new TranslatorGrabItemViewModel(order).setPropertyChangeListener(this));
                        }
                    } else if (orders != null && orders.size() == 0 && !refresh) {
                        ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
                        return;
                    }

                    notifyData.addAll(datas);

                    currentTranslationOrderPage = page;
                    nextTranslationOrderPage = currentTranslationOrderPage + 1;
                    if (refresh) {
                        ObservableNotifier.alwaysNotify(refreshResult, PullToRefreshLayout.SUCCEED);
                    } else {
                        ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.SUCCEED);
                    }
                }, throwable -> {
                    if (refresh) {
                        ObservableNotifier.alwaysNotify(refreshResult, PullToRefreshLayout.FAIL);
                    } else {
                        ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.FAIL);
                    }
                }, () -> {
                    if (notifyData.size() == 0) {
                        headViewRes.set(R.layout.translator_grab_null_bg_layout);
                    } else {
                        headViewRes.set(0);
                    }
                });
    }

    private boolean hasGrabOrderProcessing = false;

    private void resetGrabOrder() {
        Observable.empty()
                .delay(1, TimeUnit.SECONDS)
                .doOnComplete(() -> {
                    L.i(getClass().getSimpleName(), "抢单请求处理完成，恢复抢单");
                    hasGrabOrderProcessing = false;
                })
                .subscribe();
    }

    @Override
    public void onGrabOrder(ObservableField<TranslationOrderResponse> observableField, int fieldId) {

        if (hasGrabOrderProcessing) {
            L.i(getClass().getSimpleName(), "当前有正在处理的抢单请求，本次点击不处理");
            return;
        }

        hasGrabOrderProcessing = true;

        L.i(getClass().getSimpleName(), "开始抢单：" + new Gson().toJson(observableField.get()));

        ObservableNotifier.alwaysNotify(onGrabOrderStart, null);

        new TranslationOrderModel().grabOrder(observableField.get().getUserorderid())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            ObservableNotifier.alwaysNotify(onGrabOrderSuccess, observableField.get());
                            resetGrabOrder();
                        },
                        throwable -> {
                            ObservableNotifier.alwaysNotify(onGrabOrderFail, throwable.getMessage());
                            resetGrabOrder();
                        })
        ;
    }

}
