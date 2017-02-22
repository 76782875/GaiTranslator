package com.lyun.lawyer.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.api.response.Page;
import com.lyun.lawyer.api.APIConstants;
import com.lyun.lawyer.api.request.QueryTranslationOrdersBean;
import com.lyun.lawyer.api.response.TranslationOrder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public interface TranslationOrderService {

    @POST(APIConstants.QUERY_TRANSLATION_ORDERS)
    Observable<APIResult<Page<List<TranslationOrder>>>> queryOrders(@Body QueryTranslationOrdersBean query);

}
