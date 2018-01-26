package com.myncic.push.otherpush.jpush;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.myncic.push.OnePush;
import com.myncic.push.OneRepeater;
import com.myncic.push.core.IPushClient;
import com.myncic.push.log.OneLog;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * JPush Client
 *
 */

public class JPushClient implements IPushClient {

    private Context context;

    private HashSet<String> tagSet = new HashSet<String>();

    private Handler handler = new Handler();

    private Runnable setTagRunnable = new Runnable() {
        @Override
        public void run() {
            JPushInterface.addTags(context, OnePush.TYPE_ADD_TAG, (Set<String>) tagSet.clone());
            tagSet.clear();
        }
    };

    @Override
    public void initContext(Context context) {
        this.context = context.getApplicationContext();
        JPushInterface.setDebugMode(OneLog.isDebug());
        JPushInterface.init(context);
    }

    @Override
    public void register() {
        if (JPushInterface.isPushStopped(this.context)) {
            JPushInterface.resumePush(this.context);
        }
        String token = JPushInterface.getRegistrationID(this.context);
        if (!TextUtils.isEmpty(token)) {
            OneRepeater.transmitCommandResult(this.context, OnePush.TYPE_REGISTER, 200, token, null,
                    null);
        }
    }

    @Override
    public void unRegister() {
        JPushInterface.stopPush(this.context);
    }

    @Override
    public void bindAlias(String alias) {
        JPushInterface.setAlias(this.context, OnePush.TYPE_BIND_ALIAS, alias);
    }

    @Override
    public void unBindAlias(String alias) {
        JPushInterface.deleteAlias(this.context, OnePush.TYPE_UNBIND_ALIAS);
    }

    @Override
    public void addTag(final String tag) {
        handler.removeCallbacks(setTagRunnable);
        tagSet.add(tag);
        handler.postDelayed(setTagRunnable, 300);
    }

    @Override
    public void deleteTag(String tag) {
        JPushInterface.deleteTags(this.context, OnePush.TYPE_DEL_TAG, Collections.singleton(tag));
    }


}
