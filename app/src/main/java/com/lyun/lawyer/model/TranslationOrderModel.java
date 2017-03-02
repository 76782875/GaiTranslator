package com.lyun.lawyer.model;

import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.Account;
import com.lyun.lawyer.api.API;
import com.lyun.lawyer.api.request.GrabOrderRequest;
import com.lyun.lawyer.api.request.HeartBeatBean;
import com.lyun.lawyer.api.request.QueryTranslationOrdersBean;
import com.lyun.lawyer.api.response.TranslationOrderResponse;
import com.lyun.library.mvvm.model.Model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class TranslationOrderModel extends Model {

    public Observable<Page<List<TranslationOrderResponse>>> queryOrder(int page) {
        QueryTranslationOrdersBean request = new QueryTranslationOrdersBean(page + "", "20");
        return parseAPIObservable(API.translationOrder.queryOrders(request))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

    }

    public Observable<APIResult> heartBeat(String userOrderId) {
        return API.translationOrder.heartBeat(new HeartBeatBean(userOrderId, Account.preference().getPhone(), "1"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<String> grabOrder(String orderId) {
        GrabOrderRequest request = new GrabOrderRequest(orderId, Account.preference().getPhone());
        return parseAPIObservable(API.translationOrder.grabOrder(request))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

}
