package pl.droidsonroids.gif;

import androidx.annotation.NonNull;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class GifIOException extends IOException {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final long serialVersionUID = 13038402904505L;
    private final String mErrnoMessage;

    @NonNull
    public final GifError reason;

    public GifIOException(int i, String str) {
        this.reason = GifError.fromCode(i);
        this.mErrnoMessage = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.mErrnoMessage == null) {
            return this.reason.getFormattedDescription();
        }
        return this.reason.getFormattedDescription() + ": " + this.mErrnoMessage;
    }
}
