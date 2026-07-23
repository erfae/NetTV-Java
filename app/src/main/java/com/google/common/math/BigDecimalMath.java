package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public class BigDecimalMath {

    public static class BigDecimalToDoubleRounder extends ToDoubleRounder<BigDecimal> {
        public static final BigDecimalToDoubleRounder INSTANCE = new BigDecimalToDoubleRounder();

        private BigDecimalToDoubleRounder() {
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final Number minus(Number number, Number number2) {
            return ((BigDecimal) number).subtract((BigDecimal) number2);
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final double roundToDoubleArbitrarily(Number number) {
            return ((BigDecimal) number).doubleValue();
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final int sign(Number number) {
            return ((BigDecimal) number).signum();
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final Number toX(double d, RoundingMode roundingMode) {
            return new BigDecimal(d);
        }
    }

    private BigDecimalMath() {
    }

    public static double roundToDouble(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return BigDecimalToDoubleRounder.INSTANCE.roundToDouble(bigDecimal, roundingMode);
    }
}
