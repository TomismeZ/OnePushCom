package com.myncic.push.otherpush.jpush;

import android.content.Context;
import android.util.Log;

import com.myncic.push.OnePush;
import com.myncic.push.OneRepeater;

import java.util.Iterator;
import java.util.Set;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by pengyuantao on 2017/10/25 18:55.
 */

public class JPushTagOrAliasReceiver extends JPushMessageReceiver {

  private static final String TAG = "JPushTagOrAliasReceiver";

  @Override
  public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
    Log.e(TAG, jPushMessage.toString());
    OneRepeater.transmitCommandResult(context, jPushMessage.getSequence(), jPushMessage.getErrorCode()==0? OnePush.RESULT_OK:jPushMessage.getErrorCode(), null, jPushMessage.getAlias(), null);
  }

  @Override
  public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
    Log.e(TAG, jPushMessage.toString());
    Set<String> tagSet = jPushMessage.getTags();
    Iterator<String> iterator = tagSet.iterator();
    StringBuilder builder = new StringBuilder();
    while (iterator.hasNext()) {
      builder.append(iterator.next()).append(",");
    }
    if (builder.length() > 0) {
      builder.deleteCharAt(builder.length() - 1);
    }
    OneRepeater.transmitCommandResult(context, jPushMessage.getSequence(), jPushMessage.getErrorCode()==0? OnePush.RESULT_OK:jPushMessage.getErrorCode(), null, builder.toString(), null);
  }

  @Override
  public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
    super.onCheckTagOperatorResult(context, jPushMessage);
  }
}
