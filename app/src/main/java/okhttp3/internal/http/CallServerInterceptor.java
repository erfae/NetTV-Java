package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.http2.ConnectionShutdownException;
import okio.BufferedSink;
import okio.Okio;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CallServerInterceptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k = 1, mv = {1, 4, 1})
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00b1 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00dd A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x00e6 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x0127 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:58:0x0150 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x015b A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x0161 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x0166  */
    /* JADX WARN: Code duplicated, block: B:69:0x016e A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:71:0x0188 A[Catch: IOException -> 0x019c, TryCatch #3 {IOException -> 0x019c, blocks: (B:39:0x00a8, B:41:0x00b1, B:42:0x00b5, B:44:0x00dd, B:46:0x00e6, B:47:0x00e9, B:48:0x010d, B:52:0x0118, B:54:0x0137, B:56:0x0145, B:63:0x015b, B:65:0x0161, B:69:0x016e, B:71:0x0188, B:72:0x0190, B:73:0x019a, B:58:0x0150, B:53:0x0127), top: B:87:0x00a8 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Response.Builder responseHeaders;
        Response responseBuild;
        int iCode;
        Response responseBuild2;
        ResponseBody responseBodyBody;
        long contentLength;
        boolean z;
        Intrinsics.checkNotNullParameter(chain, "chain");
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Exchange exchange = realInterceptorChain.getExchange();
        Intrinsics.checkNotNull(exchange);
        Request request = realInterceptorChain.getRequest();
        RequestBody requestBodyBody = request.body();
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = true;
        try {
            exchange.writeRequestHeaders(request);
            if (!HttpMethod.permitsRequestBody(request.method()) || requestBodyBody == null) {
                exchange.noRequestBody();
                responseHeaders = null;
            } else {
                if (StringsKt.equals("100-continue", request.header(com.google.common.net.HttpHeaders.EXPECT))) {
                    exchange.flushRequest();
                    responseHeaders = exchange.readResponseHeaders(true);
                    try {
                        exchange.responseHeadersStart();
                        z = false;
                    } catch (IOException e) {
                        e = e;
                        if ((e instanceof ConnectionShutdownException) || !exchange.getHasFailure()) {
                            throw e;
                        }
                        if (responseHeaders == null) {
                            try {
                                responseHeaders = exchange.readResponseHeaders(false);
                                Intrinsics.checkNotNull(responseHeaders);
                                if (z2) {
                                    exchange.responseHeadersStart();
                                    z2 = false;
                                }
                            } catch (IOException e2) {
                                if (e == null) {
                                    throw e2;
                                }
                                ExceptionsKt.addSuppressed(e, e2);
                                throw e;
                            }
                        }
                        responseBuild = responseHeaders.request(request).handshake(exchange.getConnection().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
                        iCode = responseBuild.code();
                        if (iCode == 100) {
                            Response.Builder responseHeaders2 = exchange.readResponseHeaders(false);
                            Intrinsics.checkNotNull(responseHeaders2);
                            if (z2) {
                                exchange.responseHeadersStart();
                            }
                            responseBuild = responseHeaders2.request(request).handshake(exchange.getConnection().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
                            iCode = responseBuild.code();
                        }
                        exchange.responseHeadersEnd(responseBuild);
                        if (this.forWebSocket) {
                            responseBuild2 = responseBuild.newBuilder().body(exchange.openResponseBody(responseBuild)).build();
                        } else {
                            responseBuild2 = responseBuild.newBuilder().body(exchange.openResponseBody(responseBuild)).build();
                        }
                        if (StringsKt.equals("close", responseBuild2.request().header(com.google.common.net.HttpHeaders.CONNECTION))) {
                            exchange.noNewExchangesOnConnection();
                        } else {
                            exchange.noNewExchangesOnConnection();
                        }
                        if (iCode != 204) {
                            responseBodyBody = responseBuild2.body();
                            if (responseBodyBody != null) {
                                contentLength = responseBodyBody.getContentLength();
                            } else {
                                contentLength = -1;
                            }
                            if (contentLength > 0) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("HTTP ");
                                sb.append(iCode);
                                sb.append(" had non-zero Content-Length: ");
                                ResponseBody responseBodyBody2 = responseBuild2.body();
                                sb.append(responseBodyBody2 != null ? Long.valueOf(responseBodyBody2.getContentLength()) : null);
                                throw new ProtocolException(sb.toString());
                            }
                        } else {
                            responseBodyBody = responseBuild2.body();
                            if (responseBodyBody != null) {
                                contentLength = responseBodyBody.getContentLength();
                            } else {
                                contentLength = -1;
                            }
                            if (contentLength > 0) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("HTTP ");
                                sb2.append(iCode);
                                sb2.append(" had non-zero Content-Length: ");
                                ResponseBody responseBodyBody3 = responseBuild2.body();
                                sb2.append(responseBodyBody3 != null ? Long.valueOf(responseBodyBody3.getContentLength()) : null);
                                throw new ProtocolException(sb2.toString());
                            }
                        }
                        return responseBuild2;
                    }
                } else {
                    responseHeaders = null;
                    z = true;
                }
                try {
                    if (responseHeaders != null) {
                        exchange.noRequestBody();
                        if (!exchange.getConnection().isMultiplexed$okhttp()) {
                            exchange.noNewExchangesOnConnection();
                        }
                    } else if (requestBodyBody.isDuplex()) {
                        exchange.flushRequest();
                        requestBodyBody.writeTo(Okio.buffer(exchange.createRequestBody(request, true)));
                    } else {
                        BufferedSink bufferedSinkBuffer = Okio.buffer(exchange.createRequestBody(request, false));
                        requestBodyBody.writeTo(bufferedSinkBuffer);
                        bufferedSinkBuffer.close();
                    }
                    z2 = z;
                } catch (IOException e3) {
                    e = e3;
                    z2 = z;
                    if (e instanceof ConnectionShutdownException) {
                        throw e;
                    }
                    throw e;
                }
            }
            if (requestBodyBody == null || !requestBodyBody.isDuplex()) {
                exchange.finishRequest();
            }
            e = null;
        } catch (IOException e4) {
            e = e4;
            responseHeaders = null;
        }
        if (responseHeaders == null) {
            responseHeaders = exchange.readResponseHeaders(false);
            Intrinsics.checkNotNull(responseHeaders);
            if (z2) {
                exchange.responseHeadersStart();
                z2 = false;
            }
        }
        responseBuild = responseHeaders.request(request).handshake(exchange.getConnection().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        iCode = responseBuild.code();
        if (iCode == 100) {
            Response.Builder responseHeaders3 = exchange.readResponseHeaders(false);
            Intrinsics.checkNotNull(responseHeaders3);
            if (z2) {
                exchange.responseHeadersStart();
            }
            responseBuild = responseHeaders3.request(request).handshake(exchange.getConnection().getHandshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
            iCode = responseBuild.code();
        }
        exchange.responseHeadersEnd(responseBuild);
        if (this.forWebSocket || iCode != 101) {
            responseBuild2 = responseBuild.newBuilder().body(exchange.openResponseBody(responseBuild)).build();
        } else {
            responseBuild2 = responseBuild.newBuilder().body(Util.EMPTY_RESPONSE).build();
        }
        if (StringsKt.equals("close", responseBuild2.request().header(com.google.common.net.HttpHeaders.CONNECTION)) || StringsKt.equals("close", Response.header$default(responseBuild2, com.google.common.net.HttpHeaders.CONNECTION, null, 2, null))) {
            exchange.noNewExchangesOnConnection();
        }
        if (iCode != 204 || iCode == 205) {
            responseBodyBody = responseBuild2.body();
            if (responseBodyBody != null) {
                contentLength = responseBodyBody.getContentLength();
            } else {
                contentLength = -1;
            }
            if (contentLength > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("HTTP ");
                sb3.append(iCode);
                sb3.append(" had non-zero Content-Length: ");
                ResponseBody responseBodyBody4 = responseBuild2.body();
                sb3.append(responseBodyBody4 != null ? Long.valueOf(responseBodyBody4.getContentLength()) : null);
                throw new ProtocolException(sb3.toString());
            }
        }
        return responseBuild2;
    }
}
