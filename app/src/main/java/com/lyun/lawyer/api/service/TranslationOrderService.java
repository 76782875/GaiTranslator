package com.lyun.lawyer.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.api.APIConstants;
import com.lyun.lawyer.api.request.GrabOrderRequest;
import com.lyun.lawyer.api.request.HeartBeatBean;
import com.lyun.lawyer.api.request.QueryTranslationOrdersBean;
import com.lyun.lawyer.api.response.TranslationOrderResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public interface TranslationOrderService {

    @POST(APIConstants.QUERY_TRANSLATION_ORDERS)
    Observable<APIResult<Page<List<TranslationOrderResponse>>>> queryOrders(@Body QueryTranslationOrdersBean query);

    @POST(APIConstants.GRAB_TRANSLATION_ORDERS)
    Observable<APIResult<String>> grabOrder(@Body GrabOrderRequest request);

    @POST(APIConstants.HEART_BEAT)
    Observable<APIResult> heartBeat(@Body HeartBeatBean body);

}
