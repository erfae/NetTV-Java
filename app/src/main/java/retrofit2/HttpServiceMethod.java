package retrofit2;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.ResponseBody;

/* JADX INFO: loaded from: classes2.dex */
abstract class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT> {
    private final okhttp3.Call.Factory callFactory;
    private final RequestFactory requestFactory;
    private final Converter<ResponseBody, ResponseT> responseConverter;

    public static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
        private final CallAdapter<ResponseT, ReturnT> callAdapter;

        public CallAdapted(RequestFactory requestFactory, okhttp3.Call.Factory factory, Converter<ResponseBody, ResponseT> converter, CallAdapter<ResponseT, ReturnT> callAdapter) {
            super(requestFactory, factory, converter);
            this.callAdapter = callAdapter;
        }

        @Override // retrofit2.HttpServiceMethod
        public final ReturnT adapt(Call<ResponseT> call, Object[] objArr) {
            return this.callAdapter.adapt(call);
        }
    }

    public static final class SuspendForBody<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;
        private final boolean isNullable;

        public SuspendForBody(RequestFactory requestFactory, okhttp3.Call.Factory factory, Converter converter, CallAdapter callAdapter) {
            super(requestFactory, factory, converter);
            this.callAdapter = callAdapter;
            this.isNullable = false;
        }

        @Override // retrofit2.HttpServiceMethod
        public final Object adapt(Call<ResponseT> call, Object[] objArr) {
            Call<ResponseT> callAdapt = this.callAdapter.adapt(call);
            Continuation continuation = (Continuation) objArr[objArr.length - 1];
            try {
                return this.isNullable ? KotlinExtensions.awaitNullable(callAdapt, continuation) : KotlinExtensions.await(callAdapt, continuation);
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }

    public static final class SuspendForResponse<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;

        public SuspendForResponse(RequestFactory requestFactory, okhttp3.Call.Factory factory, Converter<ResponseBody, ResponseT> converter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter) {
            super(requestFactory, factory, converter);
            this.callAdapter = callAdapter;
        }

        @Override // retrofit2.HttpServiceMethod
        public final Object adapt(Call<ResponseT> call, Object[] objArr) {
            Call<ResponseT> callAdapt = this.callAdapter.adapt(call);
            Continuation continuation = (Continuation) objArr[objArr.length - 1];
            try {
                return KotlinExtensions.awaitResponse(callAdapt, continuation);
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }

    public HttpServiceMethod(RequestFactory requestFactory, okhttp3.Call.Factory factory, Converter<ResponseBody, ResponseT> converter) {
        this.requestFactory = requestFactory;
        this.callFactory = factory;
        this.responseConverter = converter;
    }

    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> createCallAdapter(Retrofit retrofit, Method method, Type type, Annotation[] annotationArr) {
        try {
            return (CallAdapter<ResponseT, ReturnT>) retrofit.callAdapter(type, annotationArr);
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create call adapter for %s", type);
        }
    }

    private static <ResponseT> Converter<ResponseBody, ResponseT> createResponseConverter(Retrofit retrofit, Method method, Type type) {
        try {
            return retrofit.responseBodyConverter(type, method.getAnnotations());
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create converter for %s", type);
        }
    }

    public static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(Retrofit retrofit, Method method, RequestFactory requestFactory) {
        Type genericReturnType;
        boolean z;
        boolean z2 = requestFactory.isKotlinSuspendFunction;
        Annotation[] annotations = method.getAnnotations();
        if (z2) {
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Type parameterUpperBound = ((ParameterizedType) genericParameterTypes[genericParameterTypes.length - 1]).getActualTypeArguments()[0];
            if (parameterUpperBound instanceof WildcardType) {
                parameterUpperBound = ((WildcardType) parameterUpperBound).getLowerBounds()[0];
            }
            if (Utils.getRawType(parameterUpperBound) == Response.class && (parameterUpperBound instanceof ParameterizedType)) {
                parameterUpperBound = Utils.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound);
                z = true;
            } else {
                z = false;
            }
            genericReturnType = new Utils.ParameterizedTypeImpl(null, Call.class, parameterUpperBound);
            annotations = SkipCallbackExecutorImpl.ensurePresent(annotations);
        } else {
            genericReturnType = method.getGenericReturnType();
            z = false;
        }
        CallAdapter callAdapterCreateCallAdapter = createCallAdapter(retrofit, method, genericReturnType, annotations);
        Type typeResponseType = callAdapterCreateCallAdapter.responseType();
        if (typeResponseType == okhttp3.Response.class) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("'");
            sbM.append(Utils.getRawType(typeResponseType).getName());
            sbM.append("' is not a valid response body type. Did you mean ResponseBody?");
            throw Utils.methodError(method, null, sbM.toString(), new Object[0]);
        }
        if (typeResponseType == Response.class) {
            throw Utils.methodError(method, null, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        }
        if (requestFactory.httpMethod.equals("HEAD") && !Void.class.equals(typeResponseType)) {
            throw Utils.methodError(method, null, "HEAD method must use Void as response type.", new Object[0]);
        }
        Converter converterCreateResponseConverter = createResponseConverter(retrofit, method, typeResponseType);
        okhttp3.Call.Factory factory = retrofit.callFactory;
        if (z2) {
            return z ? new SuspendForResponse(requestFactory, factory, converterCreateResponseConverter, callAdapterCreateCallAdapter) : new SuspendForBody(requestFactory, factory, converterCreateResponseConverter, callAdapterCreateCallAdapter);
        }
        return new CallAdapted(requestFactory, factory, converterCreateResponseConverter, callAdapterCreateCallAdapter);
    }

    @Nullable
    public abstract ReturnT adapt(Call<ResponseT> call, Object[] objArr);

    @Override // retrofit2.ServiceMethod
    @Nullable
    public final ReturnT invoke(Object[] objArr) {
        return adapt(new OkHttpCall(this.requestFactory, objArr, this.callFactory, this.responseConverter), objArr);
    }
}
