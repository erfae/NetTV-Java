package com.google.common.cache;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public final class CacheBuilderSpec {
    private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
    private static final ImmutableMap<String, ValueParser> VALUE_PARSERS;

    @VisibleForTesting
    public long accessExpirationDuration;

    @VisibleForTesting
    @CheckForNull
    public TimeUnit accessExpirationTimeUnit;

    @VisibleForTesting
    @CheckForNull
    public Integer concurrencyLevel;

    @VisibleForTesting
    @CheckForNull
    public Integer initialCapacity;

    @VisibleForTesting
    @CheckForNull
    public LocalCache.Strength keyStrength;

    @VisibleForTesting
    @CheckForNull
    public Long maximumSize;

    @VisibleForTesting
    @CheckForNull
    public Long maximumWeight;

    @VisibleForTesting
    @CheckForNull
    public Boolean recordStats;

    @VisibleForTesting
    public long refreshDuration;

    @VisibleForTesting
    @CheckForNull
    public TimeUnit refreshTimeUnit;
    private final String specification;

    @VisibleForTesting
    @CheckForNull
    public LocalCache.Strength valueStrength;

    @VisibleForTesting
    public long writeExpirationDuration;

    @VisibleForTesting
    @CheckForNull
    public TimeUnit writeExpirationTimeUnit;

    /* JADX INFO: renamed from: com.google.common.cache.CacheBuilderSpec$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$common$cache$LocalCache$Strength;

        static {
            int[] iArr = new int[LocalCache.Strength.values().length];
            $SwitchMap$com$google$common$cache$LocalCache$Strength = iArr;
            try {
                iArr[LocalCache.Strength.WEAK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$cache$LocalCache$Strength[LocalCache.Strength.SOFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class AccessDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public final void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.accessExpirationTimeUnit == null, "expireAfterAccess already set");
            cacheBuilderSpec.accessExpirationDuration = j;
            cacheBuilderSpec.accessExpirationTimeUnit = timeUnit;
        }
    }

    public static class ConcurrencyLevelParser extends IntegerParser {
        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        public final void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i) {
            Integer num = cacheBuilderSpec.concurrencyLevel;
            Preconditions.checkArgument(num == null, "concurrency level was already set to ", num);
            cacheBuilderSpec.concurrencyLevel = Integer.valueOf(i);
        }
    }

    public static abstract class DurationParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            TimeUnit timeUnit;
            if (Strings.isNullOrEmpty(str2)) {
                throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(str, 21), "value of key ", str, " omitted"));
            }
            try {
                char cCharAt = str2.charAt(str2.length() - 1);
                if (cCharAt == 'd') {
                    timeUnit = TimeUnit.DAYS;
                } else if (cCharAt == 'h') {
                    timeUnit = TimeUnit.HOURS;
                } else if (cCharAt == 'm') {
                    timeUnit = TimeUnit.MINUTES;
                } else {
                    if (cCharAt != 's') {
                        throw new IllegalArgumentException(CacheBuilderSpec.format("key %s invalid unit: was %s, must end with one of [dhms]", str, str2));
                    }
                    timeUnit = TimeUnit.SECONDS;
                }
                parseDuration(cacheBuilderSpec, Long.parseLong(str2.substring(0, str2.length() - 1)), timeUnit);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2));
            }
        }

        public abstract void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit);
    }

    public static class InitialCapacityParser extends IntegerParser {
        @Override // com.google.common.cache.CacheBuilderSpec.IntegerParser
        public final void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i) {
            Integer num = cacheBuilderSpec.initialCapacity;
            Preconditions.checkArgument(num == null, "initial capacity was already set to ", num);
            cacheBuilderSpec.initialCapacity = Integer.valueOf(i);
        }
    }

    public static abstract class IntegerParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            if (Strings.isNullOrEmpty(str2)) {
                throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(str, 21), "value of key ", str, " omitted"));
            }
            try {
                parseInteger(cacheBuilderSpec, Integer.parseInt(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e);
            }
        }

        public abstract void parseInteger(CacheBuilderSpec cacheBuilderSpec, int i);
    }

    public static class KeyStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public KeyStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.keyStrength;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.keyStrength = this.strength;
        }
    }

    public static abstract class LongParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            if (Strings.isNullOrEmpty(str2)) {
                throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(str, 21), "value of key ", str, " omitted"));
            }
            try {
                parseLong(cacheBuilderSpec, Long.parseLong(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", str, str2), e);
            }
        }

        public abstract void parseLong(CacheBuilderSpec cacheBuilderSpec, long j);
    }

    public static class MaximumSizeParser extends LongParser {
        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        public final void parseLong(CacheBuilderSpec cacheBuilderSpec, long j) {
            Long l = cacheBuilderSpec.maximumSize;
            Preconditions.checkArgument(l == null, "maximum size was already set to ", l);
            Long l2 = cacheBuilderSpec.maximumWeight;
            Preconditions.checkArgument(l2 == null, "maximum weight was already set to ", l2);
            cacheBuilderSpec.maximumSize = Long.valueOf(j);
        }
    }

    public static class MaximumWeightParser extends LongParser {
        @Override // com.google.common.cache.CacheBuilderSpec.LongParser
        public final void parseLong(CacheBuilderSpec cacheBuilderSpec, long j) {
            Long l = cacheBuilderSpec.maximumWeight;
            Preconditions.checkArgument(l == null, "maximum weight was already set to ", l);
            Long l2 = cacheBuilderSpec.maximumSize;
            Preconditions.checkArgument(l2 == null, "maximum size was already set to ", l2);
            cacheBuilderSpec.maximumWeight = Long.valueOf(j);
        }
    }

    public static class RecordStatsParser implements ValueParser {
        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "recordStats does not take values");
            Preconditions.checkArgument(cacheBuilderSpec.recordStats == null, "recordStats already set");
            cacheBuilderSpec.recordStats = Boolean.TRUE;
        }
    }

    public static class RefreshDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public final void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.refreshTimeUnit == null, "refreshAfterWrite already set");
            cacheBuilderSpec.refreshDuration = j;
            cacheBuilderSpec.refreshTimeUnit = timeUnit;
        }
    }

    public interface ValueParser {
        void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2);
    }

    public static class ValueStrengthParser implements ValueParser {
        private final LocalCache.Strength strength;

        public ValueStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.ValueParser
        public void parse(CacheBuilderSpec cacheBuilderSpec, String str, @CheckForNull String str2) {
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            LocalCache.Strength strength = cacheBuilderSpec.valueStrength;
            Preconditions.checkArgument(strength == null, "%s was already set to %s", str, strength);
            cacheBuilderSpec.valueStrength = this.strength;
        }
    }

    public static class WriteDurationParser extends DurationParser {
        @Override // com.google.common.cache.CacheBuilderSpec.DurationParser
        public final void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.writeExpirationTimeUnit == null, "expireAfterWrite already set");
            cacheBuilderSpec.writeExpirationDuration = j;
            cacheBuilderSpec.writeExpirationTimeUnit = timeUnit;
        }
    }

    static {
        ImmutableMap.Builder builderPut = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", new MaximumSizeParser()).put("maximumWeight", new MaximumWeightParser()).put("concurrencyLevel", new ConcurrencyLevelParser());
        LocalCache.Strength strength = LocalCache.Strength.WEAK;
        VALUE_PARSERS = builderPut.put("weakKeys", new KeyStrengthParser(strength)).put("softValues", new ValueStrengthParser(LocalCache.Strength.SOFT)).put("weakValues", new ValueStrengthParser(strength)).put("recordStats", new RecordStatsParser()).put("expireAfterAccess", new AccessDurationParser()).put("expireAfterWrite", new WriteDurationParser()).put("refreshAfterWrite", new RefreshDurationParser()).put("refreshInterval", new RefreshDurationParser()).build();
    }

    private CacheBuilderSpec(String str) {
        this.specification = str;
    }

    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }

    @CheckForNull
    private static Long durationInNanos(long j, @CheckForNull TimeUnit timeUnit) {
        if (timeUnit == null) {
            return null;
        }
        return Long.valueOf(timeUnit.toNanos(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String format(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static CacheBuilderSpec parse(String str) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(str);
        if (!str.isEmpty()) {
            for (String str2 : KEYS_SPLITTER.split(str)) {
                ImmutableList immutableListCopyOf = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(str2));
                Preconditions.checkArgument(!immutableListCopyOf.isEmpty(), "blank key-value pair");
                Preconditions.checkArgument(immutableListCopyOf.size() <= 2, "key-value pair %s with more than one equals sign", str2);
                String str3 = (String) immutableListCopyOf.get(0);
                ValueParser valueParser = VALUE_PARSERS.get(str3);
                Preconditions.checkArgument(valueParser != null, "unknown key %s", str3);
                valueParser.parse(cacheBuilderSpec, str3, immutableListCopyOf.size() == 1 ? null : (String) immutableListCopyOf.get(1));
            }
        }
        return cacheBuilderSpec;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheBuilderSpec)) {
            return false;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec) obj;
        return Objects.equal(this.initialCapacity, cacheBuilderSpec.initialCapacity) && Objects.equal(this.maximumSize, cacheBuilderSpec.maximumSize) && Objects.equal(this.maximumWeight, cacheBuilderSpec.maximumWeight) && Objects.equal(this.concurrencyLevel, cacheBuilderSpec.concurrencyLevel) && Objects.equal(this.keyStrength, cacheBuilderSpec.keyStrength) && Objects.equal(this.valueStrength, cacheBuilderSpec.valueStrength) && Objects.equal(this.recordStats, cacheBuilderSpec.recordStats) && Objects.equal(durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(cacheBuilderSpec.writeExpirationDuration, cacheBuilderSpec.writeExpirationTimeUnit)) && Objects.equal(durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(cacheBuilderSpec.accessExpirationDuration, cacheBuilderSpec.accessExpirationTimeUnit)) && Objects.equal(durationInNanos(this.refreshDuration, this.refreshTimeUnit), durationInNanos(cacheBuilderSpec.refreshDuration, cacheBuilderSpec.refreshTimeUnit));
    }

    public int hashCode() {
        return Objects.hashCode(this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(this.refreshDuration, this.refreshTimeUnit));
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(toParsableString()).toString();
    }
}
