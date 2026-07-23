package okhttp3.internal.http2;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PushObserver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0003H&J&\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\t\u001a\u00020\u0003H&J\u001e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\fH&J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0015"}, d2 = {"Lokhttp3/internal/http2/PushObserver;", "", "onData", "", "streamId", "", "source", "Lokio/BufferedSource;", "byteCount", "last", "onHeaders", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "onRequest", "requestHeaders", "onReset", "", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "Companion", "okhttp"}, k = 1, mv = {1, 4, 1})
public interface PushObserver {

    @JvmField
    @NotNull
    public static final PushObserver CANCEL = new Companion.PushObserverCancel();

    boolean onData(int streamId, @NotNull BufferedSource source, int byteCount, boolean last) throws IOException;

    boolean onHeaders(int streamId, @NotNull List<Header> responseHeaders, boolean last);

    boolean onRequest(int streamId, @NotNull List<Header> requestHeaders);

    void onReset(int streamId, @NotNull ErrorCode errorCode);
}
