package okhttp3.internal.cache;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.IOException;
import kotlin.Metadata;
import okio.Sink;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CacheRequest.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lokhttp3/internal/cache/CacheRequest;", "", "abort", "", TtmlNode.TAG_BODY, "Lokio/Sink;", "okhttp"}, k = 1, mv = {1, 4, 1})
public interface CacheRequest {
    void abort();

    @NotNull
    Sink body() throws IOException;
}
