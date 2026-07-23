package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public interface CallAdapter<R, T> {

    public static abstract class Factory {
        @Nullable
        public abstract CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit);
    }

    T adapt(Call<R> call);

    Type responseType();
}
