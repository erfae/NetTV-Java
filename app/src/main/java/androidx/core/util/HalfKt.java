package androidx.core.util;

import android.annotation.SuppressLint;
import android.util.Half;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Half.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0005H\u0087\b¨\u0006\u0006"}, d2 = {"toHalf", "Landroid/util/Half;", "", "", "", "", "core-ktx_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
@SuppressLint({"ClassVerificationFailure"})
public final class HalfKt {
    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(short s) {
        Half halfValueOf = Half.valueOf(s);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(float f) {
        Half halfValueOf = Half.valueOf(f);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Half halfValueOf = Half.valueOf(str);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(double d) {
        Half halfValueOf = Half.valueOf((float) d);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }
}
