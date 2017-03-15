package com.lyun.lawyer.im.receiver.handler;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.im.receiver.attach.Attach;
import com.lyun.lawyer.im.receiver.attach.TranslationOrderFinish;
import com.lyun.lawyer.service.TranslationOrder;
import com.lyun.lawyer.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * 翻译端挂断翻译服务
 */

public class TranslationOrderFinishByTranslatorHandler implements AttachContentHandler<TranslationOrderFinish> {

    @Override
    public void handleNotification(Context context, TranslationOrderFinish data) {
        TranslationOrderService.stop(context, TranslationOrder.TRANSLATOR, "翻译中断了本次服务");
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderFinish>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return Attach.Type.TRANSLATION_ORDER_FINISH_BY_TRANSLATOR;
    }

}
