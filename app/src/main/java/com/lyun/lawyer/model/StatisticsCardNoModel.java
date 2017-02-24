package com.lyun.lawyer.model;

import com.lyun.api.response.APIResult;
import com.lyun.lawyer.api.API;
import com.lyun.lawyer.api.request.StatisticsCardNoBean;
import com.lyun.lawyer.api.response.StatisticsCardNoResponse;
import com.lyun.library.mvvm.model.Model;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoModel extends Model {
    public Observable<APIResult<StatisticsCardNoResponse>> getStatistics(String orderHand) {
        StatisticsCardNoBean bean = new StatisticsCardNoBean(orderHand);
        return API.auth.getStatistics(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
