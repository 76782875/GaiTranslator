package com.lyun.lawyer.im.receiver.handler;

import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.lyun.lawyer.Account;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.im.receiver.attach.Attach;
import com.lyun.lawyer.im.receiver.attach.TranslationOrderStart;
import com.lyun.lawyer.service.TranslationOrder;
import com.lyun.lawyer.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * Created by ZHAOWEIWEI on 2017/3/2.
 */

public class TranslationOrderStartHandler implements AttachContentHandler<TranslationOrderStart> {

    @Override
    public void handleNotification(TranslationOrderStart data) {
        Intent intent = new Intent(AppApplication.getInstance(), TranslationOrderService.class);
        intent.putExtra(TranslationOrder.ORDER_ID, data.getUserOrderId());
        intent.putExtra(TranslationOrder.TRANSLATOR_ID, data.getOrderHand());
        intent.putExtra(TranslationOrder.USER_ID, Account.preference().getPhone());
        AppApplication.getInstance().startService(intent);
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderStart>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return Attach.Type.TRANSLATION_ORDER_START;
    }
}
