package com.lyun.lawyer.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoBean extends BaseRequest {
    private String orderHand;//翻译手机号

    public StatisticsCardNoBean(String orderHand) {
        this.orderHand = orderHand;
    }

    public String getOrderHand() {
        return orderHand;
    }

    public void setOrderHand(String orderHand) {
        this.orderHand = orderHand;
    }
}
