package com.lyun.lawyer.model;

import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.Account;
import com.lyun.lawyer.api.API;
import com.lyun.lawyer.api.request.CancelTranslationOrderBean;
import com.lyun.lawyer.api.request.GrabOrderRequest;
import com.lyun.lawyer.api.request.HeartBeatBean;
import com.lyun.lawyer.api.request.QueryTranslationOrdersBean;
import com.lyun.lawyer.api.request.TranslatorStatusBean;
import com.lyun.lawyer.api.response.TranslationOrderResponse;
import com.lyun.lawyer.api.response.TranslatorStatusResponse;
import com.lyun.library.mvvm.model.Model;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class TranslationOrderModel extends Model {

    public Observable<Page<List<TranslationOrderResponse>>> queryOrder(int page) {
        QueryTranslationOrdersBean request = new QueryTranslationOrdersBean(page + "", "20", Account.preference().getPhone());
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

    public Observable<APIResult<String>> cancelOrder(String userOrderId) {
        return API.translationOrder.cancelOrder(new CancelTranslationOrderBean(userOrderId))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult<TranslatorStatusResponse>> setTranslatorStatus(String userOrderId, String phoneState) {
        return API.translationOrder.setTranslatorStatus(new TranslatorStatusBean(userOrderId, phoneState))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public enum OrderType implements Serializable {

        // 0=图文 1=语音
        MESSAGE("0"), AUDIO("1");

        private String value;

        OrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
