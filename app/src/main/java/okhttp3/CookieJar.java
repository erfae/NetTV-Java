package okhttp3;

import com.google.android.gms.common.internal.ImagesContract;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CookieJar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&¨\u0006\u000b"}, d2 = {"Lokhttp3/CookieJar;", "", "loadForRequest", "", "Lokhttp3/Cookie;", ImagesContract.URL, "Lokhttp3/HttpUrl;", "saveFromResponse", "", "cookies", "Companion", "okhttp"}, k = 1, mv = {1, 4, 1})
public interface CookieJar {

    @JvmField
    @NotNull
    public static final CookieJar NO_COOKIES = new Companion.NoCookies();

    @NotNull
    List<Cookie> loadForRequest(@NotNull HttpUrl url);

    void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies);
}
