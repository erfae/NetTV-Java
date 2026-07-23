package retrofit2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
abstract class ServiceMethod<T> {
    public static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
        RequestFactory requestFactoryBuild = new RequestFactory.Builder(retrofit, method).build();
        Type genericReturnType = method.getGenericReturnType();
        if (Utils.hasUnresolvableType(genericReturnType)) {
            throw Utils.methodError(method, null, "Method return type must not include a type variable or wildcard: %s", genericReturnType);
        }
        if (genericReturnType != Void.TYPE) {
            return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactoryBuild);
        }
        throw Utils.methodError(method, null, "Service methods cannot return void.", new Object[0]);
    }

    @Nullable
    public abstract T invoke(Object[] objArr);
}
