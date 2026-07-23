package org.androidannotations.api.builder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import org.androidannotations.api.builder.ServiceIntentBuilder;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ServiceIntentBuilder<I extends ServiceIntentBuilder<I>> extends IntentBuilder<I> {
    public ServiceIntentBuilder(Context context, Class<?> cls) {
        super(context, cls);
    }

    public ComponentName start() {
        return this.context.startService(this.intent);
    }

    public boolean stop() {
        return this.context.stopService(this.intent);
    }

    public ServiceIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }
}
