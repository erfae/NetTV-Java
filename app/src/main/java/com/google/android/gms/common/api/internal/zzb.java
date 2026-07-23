package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.internal.common.zzi;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class zzb extends Fragment implements LifecycleFragment {
    private static final WeakHashMap<Activity, WeakReference<zzb>> zza = new WeakHashMap<>();
    private final Map<String, LifecycleCallback> zzb = Collections.synchronizedMap(new ArrayMap());
    private int zzc = 0;

    @Nullable
    private Bundle zzd;

    public static zzb zzc(Activity activity) {
        zzb zzbVar;
        WeakHashMap<Activity, WeakReference<zzb>> weakHashMap = zza;
        WeakReference<zzb> weakReference = weakHashMap.get(activity);
        if (weakReference != null && (zzbVar = weakReference.get()) != null) {
            return zzbVar;
        }
        try {
            zzb zzbVar2 = (zzb) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
            if (zzbVar2 == null || zzbVar2.isRemoving()) {
                zzbVar2 = new zzb();
                activity.getFragmentManager().beginTransaction().add(zzbVar2, "LifecycleFragmentImpl").commitAllowingStateLoss();
            }
            weakHashMap.put(activity, new WeakReference<>(zzbVar2));
            return zzbVar2;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleFragment
    public final void addCallback(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (this.zzb.containsKey(str)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 59), "LifecycleCallback with tag ", str, " already added to this fragment."));
        }
        this.zzb.put(str, lifecycleCallback);
        if (this.zzc > 0) {
            new zzi(Looper.getMainLooper()).post(new zza(this, lifecycleCallback, str));
        }
    }

    @Override // android.app.Fragment
    public final void dump(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleFragment
    @Nullable
    public final <T extends LifecycleCallback> T getCallbackOrNull(String str, Class<T> cls) {
        return cls.cast(this.zzb.get(str));
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleFragment
    @Nullable
    public final Activity getLifecycleActivity() {
        return getActivity();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleFragment
    public final boolean isCreated() {
        return this.zzc > 0;
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleFragment
    public final boolean isStarted() {
        return this.zzc >= 2;
    }

    @Override // android.app.Fragment
    public final void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Fragment
    public final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.zzc = 1;
        this.zzd = bundle;
        for (Map.Entry<String, LifecycleCallback> entry : this.zzb.entrySet()) {
            entry.getValue().onCreate(bundle != null ? bundle.getBundle(entry.getKey()) : null);
        }
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.zzc = 5;
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().onDestroy();
        }
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.zzc = 3;
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().onResume();
        }
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        for (Map.Entry<String, LifecycleCallback> entry : this.zzb.entrySet()) {
            Bundle bundle2 = new Bundle();
            entry.getValue().onSaveInstanceState(bundle2);
            bundle.putBundle(entry.getKey(), bundle2);
        }
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        this.zzc = 2;
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().onStart();
        }
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        this.zzc = 4;
        Iterator<LifecycleCallback> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            it.next().onStop();
        }
    }
}
