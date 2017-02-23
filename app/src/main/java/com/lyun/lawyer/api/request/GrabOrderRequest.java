package com.lyun.lawyer.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/2/22.
 */

public class GrabOrderRequest extends BaseRequest {

    // 订单号
    private String userOrderId;
    // 抢单人手机号
    private String orderHand;

    public GrabOrderRequest(String userOrderId, String orderHand) {
        this.userOrderId = userOrderId;
        this.orderHand = orderHand;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getOrderHand() {
        return orderHand;
    }

    public void setOrderHand(String orderHand) {
        this.orderHand = orderHand;
    }
}
