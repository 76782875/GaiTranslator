package com.lyun.lawyer.api.request;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoBean extends BaseRequestBean {
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
