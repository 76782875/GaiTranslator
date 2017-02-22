package com.lyun.lawyer.model;

import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.api.API;
import com.lyun.lawyer.api.request.QueryTranslationOrdersBean;
import com.lyun.lawyer.api.response.TranslationOrder;
import com.lyun.library.mvvm.model.Model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class TranslationOrderModel extends Model {

    public Observable<APIResult<Page<List<TranslationOrder>>>> queryOrder(int page) {
        QueryTranslationOrdersBean request = new QueryTranslationOrdersBean(page + "", "20");
        return API.translationOrder.queryOrders(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

    }

}
