package com.google.android.gms.dynamite;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzl implements DynamiteModule.VersionPolicy {
    @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy
    public final DynamiteModule.VersionPolicy.SelectionResult selectModule(Context context, String str, DynamiteModule.VersionPolicy.IVersions iVersions) throws DynamiteModule.LoadingException {
        int iZzb;
        DynamiteModule.VersionPolicy.SelectionResult selectionResult = new DynamiteModule.VersionPolicy.SelectionResult();
        int iZza = iVersions.zza(context, str);
        selectionResult.localVersion = iZza;
        int i = 0;
        if (iZza != 0) {
            iZzb = iVersions.zzb(context, str, false);
            selectionResult.remoteVersion = iZzb;
        } else {
            iZzb = iVersions.zzb(context, str, true);
            selectionResult.remoteVersion = iZzb;
        }
        int i2 = selectionResult.localVersion;
        if (i2 == 0) {
            if (iZzb == 0) {
                selectionResult.selection = 0;
            }
            return selectionResult;
        }
        i = i2;
        if (iZzb >= i) {
            selectionResult.selection = 1;
        } else {
            selectionResult.selection = -1;
        }
        return selectionResult;
    }
}
