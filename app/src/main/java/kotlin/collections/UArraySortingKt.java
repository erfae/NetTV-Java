package kotlin.collections;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", TtmlNode.LEFT, TtmlNode.RIGHT, "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m787partitionnroSd4(long[] jArr, int i, int i2) {
        long jM567getsVKNKU = ULongArray.m567getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m567getsVKNKU(jArr, i), jM567getsVKNKU) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m567getsVKNKU(jArr, i2), jM567getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM567getsVKNKU2 = ULongArray.m567getsVKNKU(jArr, i);
                ULongArray.m572setk8EXiF4(jArr, i, ULongArray.m567getsVKNKU(jArr, i2));
                ULongArray.m572setk8EXiF4(jArr, i2, jM567getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m788partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM411getw2LRezQ = UByteArray.m411getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM411getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m411getw2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m411getw2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM411getw2LRezQ2 = UByteArray.m411getw2LRezQ(bArr, i);
                UByteArray.m416setVurrAj0(bArr, i, UByteArray.m411getw2LRezQ(bArr, i2));
                UByteArray.m416setVurrAj0(bArr, i2, bM411getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m789partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM671getMh2AYeg = UShortArray.m671getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM671getMh2AYeg = UShortArray.m671getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM671getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM671getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m671getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM671getMh2AYeg2 = UShortArray.m671getMh2AYeg(sArr, i);
                UShortArray.m676set01HTLdE(sArr, i, UShortArray.m671getMh2AYeg(sArr, i2));
                UShortArray.m676set01HTLdE(sArr, i2, sM671getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m790partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM489getpVg5ArA = UIntArray.m489getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m489getpVg5ArA(iArr, i), iM489getpVg5ArA) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m489getpVg5ArA(iArr, i2), iM489getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM489getpVg5ArA2 = UIntArray.m489getpVg5ArA(iArr, i);
                UIntArray.m494setVXSXFK8(iArr, i, UIntArray.m489getpVg5ArA(iArr, i2));
                UIntArray.m494setVXSXFK8(iArr, i2, iM489getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m791quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM787partitionnroSd4 = m787partitionnroSd4(jArr, i, i2);
        int i3 = iM787partitionnroSd4 - 1;
        if (i < i3) {
            m791quickSortnroSd4(jArr, i, i3);
        }
        if (iM787partitionnroSd4 < i2) {
            m791quickSortnroSd4(jArr, iM787partitionnroSd4, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m792quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM788partition4UcCI2c = m788partition4UcCI2c(bArr, i, i2);
        int i3 = iM788partition4UcCI2c - 1;
        if (i < i3) {
            m792quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM788partition4UcCI2c < i2) {
            m792quickSort4UcCI2c(bArr, iM788partition4UcCI2c, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m793quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM789partitionAa5vz7o = m789partitionAa5vz7o(sArr, i, i2);
        int i3 = iM789partitionAa5vz7o - 1;
        if (i < i3) {
            m793quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM789partitionAa5vz7o < i2) {
            m793quickSortAa5vz7o(sArr, iM789partitionAa5vz7o, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m794quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM790partitionoBK06Vg = m790partitionoBK06Vg(iArr, i, i2);
        int i3 = iM790partitionoBK06Vg - 1;
        if (i < i3) {
            m794quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM790partitionoBK06Vg < i2) {
            m794quickSortoBK06Vg(iArr, iM790partitionoBK06Vg, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m795sortArraynroSd4(@NotNull long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m791quickSortnroSd4(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m796sortArray4UcCI2c(@NotNull byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m792quickSort4UcCI2c(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m797sortArrayAa5vz7o(@NotNull short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m793quickSortAa5vz7o(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m798sortArrayoBK06Vg(@NotNull int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m794quickSortoBK06Vg(array, i, i2 - 1);
    }
}
