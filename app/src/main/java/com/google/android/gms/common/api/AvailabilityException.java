package com.google.android.gms.common.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public class AvailabilityException extends Exception {
    private final ArrayMap<ApiKey<?>, ConnectionResult> zaa;

    public AvailabilityException(@NonNull ArrayMap<ApiKey<?>, ConnectionResult> arrayMap) {
        this.zaa = arrayMap;
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull GoogleApi<? extends Api.ApiOptions> googleApi) {
        ApiKey<O> apiKey = googleApi.getApiKey();
        boolean z = this.zaa.get(apiKey) != null;
        String strZab = apiKey.zab();
        StringBuilder sb = new StringBuilder(String.valueOf(strZab).length() + 58);
        sb.append("The given API (");
        sb.append(strZab);
        sb.append(") was not part of the availability request.");
        Preconditions.checkArgument(z, sb.toString());
        return (ConnectionResult) Preconditions.checkNotNull(this.zaa.get(apiKey));
    }

    @Override // java.lang.Throwable
    @NonNull
    public String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (ApiKey<?> apiKey : this.zaa.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) Preconditions.checkNotNull(this.zaa.get(apiKey));
            z &= !connectionResult.isSuccess();
            String strZab = apiKey.zab();
            String strValueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(strZab).length() + 2 + strValueOf.length());
            sb.append(strZab);
            sb.append(": ");
            sb.append(strValueOf);
            arrayList.add(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        if (z) {
            sb2.append("None of the queried APIs are available. ");
        } else {
            sb2.append("Some of the queried APIs are unavailable. ");
        }
        sb2.append(TextUtils.join("; ", arrayList));
        return sb2.toString();
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull HasApiKey<? extends Api.ApiOptions> hasApiKey) {
        ApiKey<O> apiKey = hasApiKey.getApiKey();
        boolean z = this.zaa.get(apiKey) != null;
        String strZab = apiKey.zab();
        StringBuilder sb = new StringBuilder(String.valueOf(strZab).length() + 58);
        sb.append("The given API (");
        sb.append(strZab);
        sb.append(") was not part of the availability request.");
        Preconditions.checkArgument(z, sb.toString());
        return (ConnectionResult) Preconditions.checkNotNull(this.zaa.get(apiKey));
    }
}
