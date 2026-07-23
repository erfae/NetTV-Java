package kotlin.text;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"kotlin/text/StringsKt__AppendableKt", "kotlin/text/StringsKt__IndentKt", "kotlin/text/StringsKt__RegexExtensionsJVMKt", "kotlin/text/StringsKt__RegexExtensionsKt", "kotlin/text/StringsKt__StringBuilderJVMKt", "kotlin/text/StringsKt__StringBuilderKt", "kotlin/text/StringsKt__StringNumberConversionsJVMKt", "kotlin/text/StringsKt__StringNumberConversionsKt", "kotlin/text/StringsKt__StringsJVMKt", "kotlin/text/StringsKt__StringsKt", "kotlin/text/StringsKt___StringsJvmKt", "kotlin/text/StringsKt___StringsKt"}, k = 4, mv = {1, 7, 1}, xi = 49)
public final class StringsKt extends StringsKt___StringsKt {
    private StringsKt() {
    }

    public static /* bridge */ /* synthetic */ boolean contains$default(CharSequence charSequence, char c) {
        return StringsKt__StringsKt.contains$default(charSequence, c, false, 2, (Object) null);
    }

    public static /* bridge */ /* synthetic */ boolean contains$default(CharSequence charSequence, CharSequence charSequence2) {
        return StringsKt__StringsKt.contains$default(charSequence, charSequence2, false, 2, (Object) null);
    }

    public static /* bridge */ /* synthetic */ boolean endsWith$default(String str, String str2) {
        return StringsKt__StringsJVMKt.endsWith$default(str, str2, false, 2, null);
    }

    public static /* bridge */ /* synthetic */ boolean equals(@Nullable String str, @Nullable String str2) {
        return StringsKt__StringsJVMKt.equals(str, str2, true);
    }

    public static /* bridge */ /* synthetic */ int indexOf$default(CharSequence charSequence, char c, int i, int i2) {
        return StringsKt__StringsKt.indexOf$default(charSequence, c, i, false, i2, (Object) null);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String removeSuffix(@NotNull String str) {
        return StringsKt__StringsKt.removeSuffix(str, (CharSequence) "Client");
    }

    public static /* bridge */ /* synthetic */ boolean startsWith(@NotNull String str, @NotNull String str2) {
        return StringsKt__StringsJVMKt.startsWith(str, str2, true);
    }

    public static /* bridge */ /* synthetic */ boolean startsWith(@NotNull String str, @NotNull String str2, int i) {
        return StringsKt__StringsJVMKt.startsWith(str, str2, i, true);
    }

    public static /* bridge */ /* synthetic */ boolean startsWith$default(String str, String str2) {
        return StringsKt__StringsJVMKt.startsWith$default(str, str2, false, 2, null);
    }
}
