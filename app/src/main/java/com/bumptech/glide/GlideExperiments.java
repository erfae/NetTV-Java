package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GlideExperiments {
    private final Map<Class<?>, Experiment> experiments;

    public static final class Builder {
        private final Map<Class<?>, Experiment> experiments = new HashMap();

        public final Builder update(Experiment experiment, boolean z) {
            if (z) {
                this.experiments.put(experiment.getClass(), experiment);
            } else {
                this.experiments.remove(experiment.getClass());
            }
            return this;
        }
    }

    public interface Experiment {
    }

    public GlideExperiments(Builder builder) {
        this.experiments = Collections.unmodifiableMap(new HashMap(builder.experiments));
    }

    public boolean isEnabled(Class<? extends Experiment> cls) {
        return this.experiments.containsKey(cls);
    }
}
