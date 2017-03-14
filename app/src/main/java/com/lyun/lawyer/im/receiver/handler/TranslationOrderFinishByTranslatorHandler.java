package com.lyun.lawyer.im.receiver.handler;

import com.google.gson.reflect.TypeToken;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.im.receiver.attach.TranslationOrderFinish;
import com.lyun.lawyer.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * 翻译端挂断翻译服务
 */

public class TranslationOrderFinishByTranslatorHandler implements AttachContentHandler<TranslationOrderFinish> {

    @Override
    public void handleNotification(TranslationOrderFinish data) {
        TranslationOrderService.stop(AppApplication.getInstance());
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderFinish>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return 3;
    }

}
