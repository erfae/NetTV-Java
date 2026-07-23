package org.androidannotations.api.builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import org.androidannotations.api.builder.ActivityIntentBuilder;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ActivityIntentBuilder<I extends ActivityIntentBuilder<I>> extends IntentBuilder<I> {
    public ActivityIntentBuilder(Context context, Class<?> cls) {
        super(context, cls);
    }

    public void start() {
        this.context.startActivity(this.intent);
    }

    public void startForResult(int i) {
        Context context = this.context;
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(this.intent, i);
        } else {
            context.startActivity(this.intent);
        }
    }

    public ActivityIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }
}
