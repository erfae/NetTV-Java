package com.google.android.gms.common.server.response;

import androidx.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zae implements zai<Boolean> {
    @Override // com.google.android.gms.common.server.response.zai
    @Nullable
    public final /* bridge */ /* synthetic */ Boolean zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Boolean.valueOf(fastParser.zay(bufferedReader, false));
    }
}
