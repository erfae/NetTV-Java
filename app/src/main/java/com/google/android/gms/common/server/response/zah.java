package com.google.android.gms.common.server.response;

import androidx.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zah implements zai<BigDecimal> {
    @Override // com.google.android.gms.common.server.response.zai
    @Nullable
    public final /* synthetic */ BigDecimal zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return fastParser.zas(bufferedReader);
    }
}
