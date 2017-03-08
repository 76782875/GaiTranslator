package com.lyun.lawyer.im.receiver.handler;

import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.lyun.lawyer.AppApplication;
import com.lyun.lawyer.im.receiver.attach.TranslationOrderFinish;
import com.lyun.lawyer.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * 用户端挂断翻译服务
 */

public class TranslationOrderFinishByUserHandler implements AttachContentHandler<TranslationOrderFinish> {

    @Override
    public void handleNotification(TranslationOrderFinish data) {
        Intent intent = new Intent(AppApplication.getInstance(), TranslationOrderService.class);
        AppApplication.getInstance().stopService(intent);
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderFinish>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return 2;
    }

}
