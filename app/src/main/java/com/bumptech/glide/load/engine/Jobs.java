package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class Jobs {
    private final Map<Key, EngineJob<?>> jobs = new HashMap();
    private final Map<Key, EngineJob<?>> onlyCacheJobs = new HashMap();

    private Map<Key, EngineJob<?>> getJobMap(boolean z) {
        return z ? this.onlyCacheJobs : this.jobs;
    }

    public final EngineJob<?> get(Key key, boolean z) {
        return getJobMap(z).get(key);
    }

    public final void put(Key key, EngineJob<?> engineJob) {
        getJobMap(engineJob.onlyRetrieveFromCache()).put(key, engineJob);
    }

    public final void removeIfCurrent(Key key, EngineJob<?> engineJob) {
        Map<Key, EngineJob<?>> jobMap = getJobMap(engineJob.onlyRetrieveFromCache());
        if (engineJob.equals(jobMap.get(key))) {
            jobMap.remove(key);
        }
    }
}
