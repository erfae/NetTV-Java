package com.google.android.gms.internal.common;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzv implements Iterable<String> {
    public final /* synthetic */ CharSequence zza;
    public final /* synthetic */ zzx zzb;

    public zzv(zzx zzxVar, CharSequence charSequence) {
        this.zzb = zzxVar;
        this.zza = charSequence;
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return this.zzb.zzh(this.zza);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Iterator<String> it = iterator();
        try {
            if (it.hasNext()) {
                String next = it.next();
                Objects.requireNonNull(next);
                sb.append((CharSequence) (next instanceof CharSequence ? next : next.toString()));
                while (it.hasNext()) {
                    sb.append((CharSequence) ", ");
                    String next2 = it.next();
                    Objects.requireNonNull(next2);
                    sb.append((CharSequence) (next2 instanceof CharSequence ? next2 : next2.toString()));
                }
            }
            sb.append(']');
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
