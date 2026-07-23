package com.google.android.exoplayer2.extractor;

import android.net.Uri;
import androidx.constraintlayout.core.state.Transition$$ExternalSyntheticLambda0;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface ExtractorsFactory {
    public static final ExtractorsFactory EMPTY = Transition$$ExternalSyntheticLambda0.INSTANCE$15;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.extractor.ExtractorsFactory$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        static {
            ExtractorsFactory extractorsFactory = ExtractorsFactory.EMPTY;
        }

        public static /* synthetic */ Extractor[] lambda$static$0() {
            return new Extractor[0];
        }
    }

    Extractor[] createExtractors();

    Extractor[] createExtractors(Uri uri, Map<String, List<String>> map);
}
