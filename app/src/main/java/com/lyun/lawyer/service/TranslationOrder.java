package com.lyun.lawyer.service;

import com.lyun.lawyer.model.TranslationOrderModel;

import java.io.Serializable;

/**
 * Created by ZHAOWEIWEI on 2017/2/28.
 */

public class TranslationOrder implements Serializable {

    private String orderId;
    private TranslationOrderModel.OrderType orderType;
    private String targetLanguage;
    private long startTime;
    private long servicedTime;
    private long endTime;
    private String userId;
    private String translatorId;

    public static final String ORDER_ID = "orderId";
    public static final String ORDER_TYPE = "orderType";
    public static final String TARGET_LANGUAGE = "targetLanguage";
    public static final String START_TIME = "startTime";
    public static final String SERVICED_TIME = "servicedTime";
    public static final String END_TIME = "endTime";
    public static final String USER_ID = "userId";
    public static final String TRANSLATOR_ID = "translatorId";

    public TranslationOrder(String orderId, TranslationOrderModel.OrderType orderType, String targetLanguage, long startTime, String userId, String translatorId) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.targetLanguage = targetLanguage;
        this.startTime = startTime;
        this.userId = userId;
        this.translatorId = translatorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TranslationOrderModel.OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(TranslationOrderModel.OrderType orderType) {
        this.orderType = orderType;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getServicedTime() {
        return servicedTime;
    }

    public void setServicedTime(long servicedTime) {
        this.servicedTime = servicedTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTranslatorId() {
        return translatorId;
    }

    public void setTranslatorId(String translatorId) {
        this.translatorId = translatorId;
    }
}
