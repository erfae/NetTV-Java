package androidx.constraintlayout.core;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class SolverVariable implements Comparable<SolverVariable> {
    private static final boolean INTERNAL_DEBUG = false;
    public static final int STRENGTH_BARRIER = 6;
    public static final int STRENGTH_CENTERING = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 8;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static final boolean VAR_USE_HASH = false;
    private static int uniqueConstantId = 1;
    private static int uniqueErrorId = 1;
    private static int uniqueId = 1;
    private static int uniqueSlackId = 1;
    private static int uniqueUnrestrictedId = 1;
    public float computedValue;
    public int definitionId;
    public float[] goalStrengthVector;
    public int id;
    public boolean inGoal;
    public boolean isFinalValue;
    public boolean isSynonym;
    public ArrayRow[] mClientEquations;
    public int mClientEquationsCount;
    private String mName;
    public Type mType;
    public int strength;
    public float[] strengthVector;
    public int synonym;
    public float synonymDelta;
    public int usageInRowCount;

    /* JADX INFO: renamed from: androidx.constraintlayout.core.SolverVariable$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type = iArr;
            try {
                iArr[Type.UNRESTRICTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[Type.CONSTANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[Type.SLACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[Type.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[Type.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public SolverVariable(String str, Type type) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.strengthVector = new float[9];
        this.goalStrengthVector = new float[9];
        this.mClientEquations = new ArrayRow[16];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.isSynonym = false;
        this.synonym = -1;
        this.synonymDelta = 0.0f;
        this.mName = str;
        this.mType = type;
    }

    private static String getUniqueName(Type type, String str) {
        if (str != null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(str);
            sbM.append(uniqueErrorId);
            return sbM.toString();
        }
        int i = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$SolverVariable$Type[type.ordinal()];
        if (i == 1) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("U");
            int i2 = uniqueUnrestrictedId + 1;
            uniqueUnrestrictedId = i2;
            sbM2.append(i2);
            return sbM2.toString();
        }
        if (i == 2) {
            StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("C");
            int i3 = uniqueConstantId + 1;
            uniqueConstantId = i3;
            sbM3.append(i3);
            return sbM3.toString();
        }
        if (i == 3) {
            StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m(ExifInterface.LATITUDE_SOUTH);
            int i4 = uniqueSlackId + 1;
            uniqueSlackId = i4;
            sbM4.append(i4);
            return sbM4.toString();
        }
        if (i == 4) {
            StringBuilder sbM5 = Insets$$ExternalSyntheticOutline0.m("e");
            int i5 = uniqueErrorId + 1;
            uniqueErrorId = i5;
            sbM5.append(i5);
            return sbM5.toString();
        }
        if (i != 5) {
            throw new AssertionError(type.name());
        }
        StringBuilder sbM6 = Insets$$ExternalSyntheticOutline0.m(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        int i6 = uniqueId + 1;
        uniqueId = i6;
        sbM6.append(i6);
        return sbM6.toString();
    }

    public static void increaseErrorId() {
        uniqueErrorId++;
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i = 0;
        while (true) {
            int i2 = this.mClientEquationsCount;
            if (i >= i2) {
                ArrayRow[] arrayRowArr = this.mClientEquations;
                if (i2 >= arrayRowArr.length) {
                    this.mClientEquations = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.mClientEquations;
                int i3 = this.mClientEquationsCount;
                arrayRowArr2[i3] = arrayRow;
                this.mClientEquationsCount = i3 + 1;
                return;
            }
            if (this.mClientEquations[i] == arrayRow) {
                return;
            } else {
                i++;
            }
        }
    }

    public String getName() {
        return this.mName;
    }

    public final void removeFromRow(ArrayRow arrayRow) {
        int i = this.mClientEquationsCount;
        int i2 = 0;
        while (i2 < i) {
            if (this.mClientEquations[i2] == arrayRow) {
                while (i2 < i - 1) {
                    ArrayRow[] arrayRowArr = this.mClientEquations;
                    int i3 = i2 + 1;
                    arrayRowArr[i2] = arrayRowArr[i3];
                    i2 = i3;
                }
                this.mClientEquationsCount--;
                return;
            }
            i2++;
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.isFinalValue = false;
        this.isSynonym = false;
        this.synonym = -1;
        this.synonymDelta = 0.0f;
        int i = this.mClientEquationsCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.mClientEquations[i2] = null;
        }
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.inGoal = false;
        Arrays.fill(this.goalStrengthVector, 0.0f);
    }

    public void setFinalValue(LinearSystem linearSystem, float f) {
        this.computedValue = f;
        this.isFinalValue = true;
        this.isSynonym = false;
        this.synonym = -1;
        this.synonymDelta = 0.0f;
        int i = this.mClientEquationsCount;
        this.definitionId = -1;
        for (int i2 = 0; i2 < i; i2++) {
            this.mClientEquations[i2].updateFromFinalVariable(linearSystem, this, false);
        }
        this.mClientEquationsCount = 0;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setSynonym(LinearSystem linearSystem, SolverVariable solverVariable, float f) {
        this.isSynonym = true;
        this.synonym = solverVariable.id;
        this.synonymDelta = f;
        int i = this.mClientEquationsCount;
        this.definitionId = -1;
        for (int i2 = 0; i2 < i; i2++) {
            this.mClientEquations[i2].updateFromSynonymVariable(linearSystem, this, false);
        }
        this.mClientEquationsCount = 0;
        linearSystem.displayReadableRows();
    }

    public void setType(Type type, String str) {
        this.mType = type;
    }

    public String toString() {
        if (this.mName != null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("");
            sbM.append(this.mName);
            return sbM.toString();
        }
        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("");
        sbM2.append(this.id);
        return sbM2.toString();
    }

    public final void updateReferencesWithNewDefinition(LinearSystem linearSystem, ArrayRow arrayRow) {
        int i = this.mClientEquationsCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.mClientEquations[i2].updateFromRow(linearSystem, arrayRow, false);
        }
        this.mClientEquationsCount = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(SolverVariable solverVariable) {
        return this.id - solverVariable.id;
    }

    public SolverVariable(Type type, String str) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.strengthVector = new float[9];
        this.goalStrengthVector = new float[9];
        this.mClientEquations = new ArrayRow[16];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.isSynonym = false;
        this.synonym = -1;
        this.synonymDelta = 0.0f;
        this.mType = type;
    }
}
