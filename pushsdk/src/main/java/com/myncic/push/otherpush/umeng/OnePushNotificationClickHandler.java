package com.myncic.push.otherpush.umeng;

import android.content.Context;

import com.myncic.push.OneRepeater;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by pyt on 2017/6/12.
 */

public class OnePushNotificationClickHandler extends UmengNotificationClickHandler {

    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        OneRepeater.transmitNotificationClick(context, 0, uMessage.title, uMessage.text, uMessage.custom, uMessage.extra);
    }



}
