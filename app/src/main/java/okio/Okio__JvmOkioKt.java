package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: JvmOkio.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\n\u001a\u00020\u000b*\u00020\f\u001a\u0012\u0010\r\u001a\u00020\u000e*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u0012\u0010\u001a\u001a\u00020\u001b*\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0012\u0010\u001a\u001a\u00020\u001b*\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u0016\u0010\u001c\u001a\u00020\u000b*\u00020\f2\b\b\u0002\u0010\u001d\u001a\u00020\u0006H\u0007\u001a\n\u0010\u001c\u001a\u00020\u000b*\u00020\u001e\u001a\n\u0010\u001c\u001a\u00020\u000b*\u00020\u001f\u001a%\u0010\u001c\u001a\u00020\u000b*\u00020 2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0\"\"\u00020#H\u0007¢\u0006\u0002\u0010$\u001a\n\u0010%\u001a\u00020\u0013*\u00020\f\u001a\n\u0010%\u001a\u00020\u0013*\u00020&\u001a\n\u0010%\u001a\u00020\u0013*\u00020\u001f\u001a%\u0010%\u001a\u00020\u0013*\u00020 2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0\"\"\u00020#H\u0007¢\u0006\u0002\u0010'\"\u001c\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u001c\u0010\u0005\u001a\u00020\u0006*\u00060\u0007j\u0002`\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\t¨\u0006("}, d2 = {"logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "getLogger$Okio__JvmOkioKt", "()Ljava/util/logging/Logger;", "isAndroidGetsocknameError", "", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "(Ljava/lang/AssertionError;)Z", "appendingSink", "Lokio/Sink;", "Ljava/io/File;", "cipherSink", "Lokio/CipherSink;", "cipher", "Ljavax/crypto/Cipher;", "cipherSource", "Lokio/CipherSource;", "Lokio/Source;", "hashingSink", "Lokio/HashingSink;", "digest", "Ljava/security/MessageDigest;", "mac", "Ljavax/crypto/Mac;", "hashingSource", "Lokio/HashingSource;", "sink", "append", "Ljava/io/OutputStream;", "Ljava/net/Socket;", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "source", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "okio"}, k = 5, mv = {1, 4, 1}, xs = "okio/Okio")
final /* synthetic */ class Okio__JvmOkioKt {
    private static final Logger logger = Logger.getLogger("okio.Okio");

    @NotNull
    public static final Sink appendingSink(@NotNull File appendingSink) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(appendingSink, "$this$appendingSink");
        return Okio.sink(new FileOutputStream(appendingSink, true));
    }

    @NotNull
    public static final CipherSink cipherSink(@NotNull Sink cipherSink, @NotNull Cipher cipher) {
        Intrinsics.checkNotNullParameter(cipherSink, "$this$cipherSink");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        return new CipherSink(Okio.buffer(cipherSink), cipher);
    }

    @NotNull
    public static final CipherSource cipherSource(@NotNull Source cipherSource, @NotNull Cipher cipher) {
        Intrinsics.checkNotNullParameter(cipherSource, "$this$cipherSource");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        return new CipherSource(Okio.buffer(cipherSource), cipher);
    }

    @NotNull
    public static final HashingSink hashingSink(@NotNull Sink hashingSink, @NotNull Mac mac) {
        Intrinsics.checkNotNullParameter(hashingSink, "$this$hashingSink");
        Intrinsics.checkNotNullParameter(mac, "mac");
        return new HashingSink(hashingSink, mac);
    }

    @NotNull
    public static final HashingSource hashingSource(@NotNull Source hashingSource, @NotNull Mac mac) {
        Intrinsics.checkNotNullParameter(hashingSource, "$this$hashingSource");
        Intrinsics.checkNotNullParameter(mac, "mac");
        return new HashingSource(hashingSource, mac);
    }

    public static final boolean isAndroidGetsocknameError(@NotNull AssertionError isAndroidGetsocknameError) {
        Intrinsics.checkNotNullParameter(isAndroidGetsocknameError, "$this$isAndroidGetsocknameError");
        if (isAndroidGetsocknameError.getCause() == null) {
            return false;
        }
        String message = isAndroidGetsocknameError.getMessage();
        return message != null ? StringsKt.contains$default(message, "getsockname failed") : false;
    }

    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File file) throws FileNotFoundException {
        return sink$default(file, false, 1, null);
    }

    @NotNull
    public static final Sink sink(@NotNull OutputStream sink) {
        Intrinsics.checkNotNullParameter(sink, "$this$sink");
        return new OutputStreamSink(sink, new Timeout());
    }

    public static /* synthetic */ Sink sink$default(File file, boolean z, int i, Object obj) throws FileNotFoundException {
        if ((i & 1) != 0) {
            z = false;
        }
        return Okio.sink(file, z);
    }

    @NotNull
    public static final Source source(@NotNull InputStream source) {
        Intrinsics.checkNotNullParameter(source, "$this$source");
        return new InputStreamSource(source, new Timeout());
    }

    @NotNull
    public static final HashingSink hashingSink(@NotNull Sink hashingSink, @NotNull MessageDigest digest) {
        Intrinsics.checkNotNullParameter(hashingSink, "$this$hashingSink");
        Intrinsics.checkNotNullParameter(digest, "digest");
        return new HashingSink(hashingSink, digest);
    }

    @NotNull
    public static final HashingSource hashingSource(@NotNull Source hashingSource, @NotNull MessageDigest digest) {
        Intrinsics.checkNotNullParameter(hashingSource, "$this$hashingSource");
        Intrinsics.checkNotNullParameter(digest, "digest");
        return new HashingSource(hashingSource, digest);
    }

    @NotNull
    public static final Sink sink(@NotNull Socket sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "$this$sink");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout(sink);
        OutputStream outputStream = sink.getOutputStream();
        Intrinsics.checkNotNullExpressionValue(outputStream, "getOutputStream()");
        return socketAsyncTimeout.sink(new OutputStreamSink(outputStream, socketAsyncTimeout));
    }

    @NotNull
    public static final Source source(@NotNull Socket source) throws IOException {
        Intrinsics.checkNotNullParameter(source, "$this$source");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout(source);
        InputStream inputStream = source.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream()");
        return socketAsyncTimeout.source(new InputStreamSource(inputStream, socketAsyncTimeout));
    }

    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File sink, boolean z) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(sink, "$this$sink");
        return Okio.sink(new FileOutputStream(sink, z));
    }

    @NotNull
    public static final Source source(@NotNull File source) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(source, "$this$source");
        return Okio.source(new FileInputStream(source));
    }

    @IgnoreJRERequirement
    @NotNull
    public static final Sink sink(@NotNull Path sink, @NotNull OpenOption... options) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "$this$sink");
        Intrinsics.checkNotNullParameter(options, "options");
        OutputStream outputStreamNewOutputStream = Files.newOutputStream(sink, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(outputStreamNewOutputStream, "Files.newOutputStream(this, *options)");
        return Okio.sink(outputStreamNewOutputStream);
    }

    @IgnoreJRERequirement
    @NotNull
    public static final Source source(@NotNull Path source, @NotNull OpenOption... options) throws IOException {
        Intrinsics.checkNotNullParameter(source, "$this$source");
        Intrinsics.checkNotNullParameter(options, "options");
        InputStream inputStreamNewInputStream = Files.newInputStream(source, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(inputStreamNewInputStream, "Files.newInputStream(this, *options)");
        return Okio.source(inputStreamNewInputStream);
    }
}
