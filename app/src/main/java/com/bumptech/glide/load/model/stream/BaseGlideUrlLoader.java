package com.bumptech.glide.load.model.stream;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGlideUrlLoader<Model> implements ModelLoader<Model, InputStream> {
    private final ModelLoader<GlideUrl, InputStream> concreteLoader;

    @Nullable
    private final ModelCache<Model, GlideUrl> modelCache;

    private static List<Key> getAlternateKeys(Collection<String> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(new GlideUrl(it.next()));
        }
        return arrayList;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    @Nullable
    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull Model model, int i, int i2, @NonNull Options options) {
        ModelCache<Model, GlideUrl> modelCache = this.modelCache;
        GlideUrl glideUrl = modelCache != null ? modelCache.get(model, i, i2) : null;
        if (glideUrl == null) {
            String url = getUrl();
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            GlideUrl glideUrl2 = new GlideUrl(url, Headers.DEFAULT);
            ModelCache<Model, GlideUrl> modelCache2 = this.modelCache;
            if (modelCache2 != null) {
                modelCache2.put(model, i, i2, glideUrl2);
            }
            glideUrl = glideUrl2;
        }
        List listEmptyList = Collections.emptyList();
        ModelLoader.LoadData<InputStream> loadDataBuildLoadData = this.concreteLoader.buildLoadData(glideUrl, i, i2, options);
        return (loadDataBuildLoadData == null || listEmptyList.isEmpty()) ? loadDataBuildLoadData : new ModelLoader.LoadData<>(loadDataBuildLoadData.sourceKey, getAlternateKeys(listEmptyList), loadDataBuildLoadData.fetcher);
    }

    public abstract String getUrl();
}
