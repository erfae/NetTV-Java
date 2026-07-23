package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintWidget {
    public static final int ANCHOR_BASELINE = 4;
    public static final int ANCHOR_BOTTOM = 3;
    public static final int ANCHOR_LEFT = 0;
    public static final int ANCHOR_RIGHT = 1;
    public static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int BOTH = 2;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int UNKNOWN = -1;
    private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
    public static final int WRAP_BEHAVIOR_INCLUDED = 0;
    public static final int WRAP_BEHAVIOR_SKIPPED = 3;
    public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
    private boolean OPTIMIZE_WRAP;
    private boolean OPTIMIZE_WRAP_ON_RESOLVED;
    public WidgetFrame frame;
    private boolean hasBaseline;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public HorizontalWidgetRun horizontalRun;
    private boolean horizontalSolvingPass;
    private boolean inPlaceholder;
    public boolean[] isTerminalWidget;
    public ArrayList<ConstraintAnchor> mAnchors;
    private boolean mAnimated;
    public ConstraintAnchor mBaseline;
    public int mBaselineDistance;
    public ConstraintAnchor mBottom;
    public ConstraintAnchor mCenter;
    public ConstraintAnchor mCenterX;
    public ConstraintAnchor mCenterY;
    private float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    public float mDimensionRatio;
    public int mDimensionRatioSide;
    public int mHeight;
    private int mHeightOverride;
    public float mHorizontalBiasPercent;
    public int mHorizontalChainStyle;
    public ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    private boolean mInVirtualLayout;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    private int mLastHorizontalMeasureSpec;
    private int mLastVerticalMeasureSpec;
    public ConstraintAnchor mLeft;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    public ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    private boolean mMeasureRequested;
    public int mMinHeight;
    public int mMinWidth;
    public ConstraintWidget[] mNextChainWidget;
    public int mOffsetX;
    public int mOffsetY;
    public ConstraintWidget mParent;
    public float mResolvedDimensionRatio;
    public int mResolvedDimensionRatioSide;
    public int[] mResolvedMatchConstraintDefault;
    public ConstraintAnchor mRight;
    public ConstraintAnchor mTop;
    private String mType;
    public float mVerticalBiasPercent;
    public int mVerticalChainStyle;
    public ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    private int mVisibility;
    public float[] mWeight;
    public int mWidth;
    private int mWidthOverride;
    private int mWrapBehaviorInParent;
    public int mX;
    public int mY;
    public boolean measured;
    private boolean resolvedHorizontal;
    private boolean resolvedVertical;
    public WidgetRun[] run;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public VerticalWidgetRun verticalRun;
    private boolean verticalSolvingPass;

    /* JADX INFO: renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public ConstraintWidget() {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0171  */
    /* JADX WARN: Code duplicated, block: B:102:0x0175  */
    /* JADX WARN: Code duplicated, block: B:108:0x019f  */
    /* JADX WARN: Code duplicated, block: B:111:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:113:0x01de  */
    /* JADX WARN: Code duplicated, block: B:115:0x01ec A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:242:0x03ac  */
    /* JADX WARN: Code duplicated, block: B:244:0x03b0  */
    /* JADX WARN: Code duplicated, block: B:250:0x03c3  */
    /* JADX WARN: Code duplicated, block: B:252:0x03f4  */
    /* JADX WARN: Code duplicated, block: B:259:0x040d  */
    /* JADX WARN: Code duplicated, block: B:268:0x042e  */
    /* JADX WARN: Code duplicated, block: B:278:0x0445  */
    /* JADX WARN: Code duplicated, block: B:281:0x044b  */
    /* JADX WARN: Code duplicated, block: B:282:0x044d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:285:0x0453 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:288:0x0458  */
    /* JADX WARN: Code duplicated, block: B:291:0x045d  */
    /* JADX WARN: Code duplicated, block: B:293:0x0461  */
    /* JADX WARN: Code duplicated, block: B:296:0x0466  */
    /* JADX WARN: Code duplicated, block: B:298:0x046a  */
    /* JADX WARN: Code duplicated, block: B:300:0x046d  */
    /* JADX WARN: Code duplicated, block: B:303:0x0474  */
    /* JADX WARN: Code duplicated, block: B:305:0x047a A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:309:0x0482  */
    /* JADX WARN: Code duplicated, block: B:312:0x0494  */
    /* JADX WARN: Code duplicated, block: B:314:0x0498  */
    /* JADX WARN: Code duplicated, block: B:315:0x049d  */
    /* JADX WARN: Code duplicated, block: B:317:0x04a0  */
    /* JADX WARN: Code duplicated, block: B:319:0x04a6 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:31:0x008c  */
    /* JADX WARN: Code duplicated, block: B:327:0x04c1  */
    /* JADX WARN: Code duplicated, block: B:351:0x04fc A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:353:0x0508 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:372:0x053d  */
    /* JADX WARN: Code duplicated, block: B:384:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:44:0x00b2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x00b4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:48:0x00bf A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:51:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:54:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:55:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:58:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:70:0x0114  */
    /* JADX WARN: Code duplicated, block: B:71:0x0116  */
    /* JADX WARN: Code duplicated, block: B:73:0x0119  */
    /* JADX WARN: Code duplicated, block: B:74:0x011b  */
    /* JADX WARN: Code duplicated, block: B:80:0x0124  */
    /* JADX WARN: Code duplicated, block: B:82:0x012f  */
    /* JADX WARN: Code duplicated, block: B:86:0x0136  */
    /* JADX WARN: Code duplicated, block: B:88:0x0139  */
    /* JADX WARN: Code duplicated, block: B:89:0x013f  */
    /* JADX WARN: Code duplicated, block: B:91:0x0146  */
    /* JADX WARN: Code duplicated, block: B:94:0x014b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:95:0x014d  */
    /* JADX WARN: Code duplicated, block: B:96:0x0152 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:97:0x0154  */
    /* JADX WARN: Code duplicated, block: B:98:0x015c  */
    private void applyConstraints(LinearSystem linearSystem, boolean z, boolean z2, boolean z3, boolean z4, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z5, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i, int i2, int i3, int i4, float f, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i5, int i6, int i7, int i8, float f2, boolean z11) {
        int i9;
        boolean z12;
        int iMin;
        int i10;
        int i11;
        int i12;
        int i13;
        boolean z13;
        ConstraintAnchor.Type type;
        ConstraintAnchor.Type type2;
        SolverVariable solverVariableCreateObjectVariable;
        SolverVariable solverVariableCreateObjectVariable2;
        boolean z14;
        int i14;
        char c;
        boolean z15;
        ConstraintAnchor constraintAnchor3;
        int i15;
        int i16;
        int i17;
        boolean z16;
        boolean z17;
        boolean z18;
        boolean z19;
        int i18;
        ConstraintWidget constraintWidget;
        boolean z20;
        ConstraintWidget constraintWidget2;
        int iMax;
        int margin;
        int iMin2;
        int i19;
        int i20;
        boolean z21;
        int i21;
        int i22;
        int i23;
        int i24;
        boolean z22;
        boolean z23;
        ConstraintWidget constraintWidget3;
        int i25;
        ConstraintWidget constraintWidget4;
        int i26 = i8;
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor2);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor.getTarget());
        SolverVariable solverVariableCreateObjectVariable6 = linearSystem.createObjectVariable(constraintAnchor2.getTarget());
        if (LinearSystem.getMetrics() != null) {
            LinearSystem.getMetrics().nonresolvedWidgets++;
        }
        boolean zIsConnected = constraintAnchor.isConnected();
        boolean zIsConnected2 = constraintAnchor2.isConnected();
        boolean zIsConnected3 = this.mCenter.isConnected();
        int i27 = zIsConnected2 ? (zIsConnected ? 1 : 0) + 1 : zIsConnected ? 1 : 0;
        if (zIsConnected3) {
            i27++;
        }
        int i28 = z6 ? 3 : i5;
        int i29 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[dimensionBehaviour.ordinal()];
        if (i29 != 1 && i29 != 2 && i29 != 3 && i29 == 4) {
            i9 = i28;
            z12 = i9 != 4;
            iMin = this.mWidthOverride;
            if (iMin == -1 && z) {
                this.mWidthOverride = -1;
                z12 = false;
            } else {
                iMin = i2;
            }
            i10 = this.mHeightOverride;
            if (i10 != -1 && !z) {
                this.mHeightOverride = -1;
                iMin = i10;
                z12 = false;
            }
            if (this.mVisibility == 8) {
                iMin = 0;
                z12 = false;
            }
            if (z11) {
                if (zIsConnected && !zIsConnected2 && !zIsConnected3) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, i);
                } else if (zIsConnected && !zIsConnected2) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                }
            }
            if (!z12) {
                if (i27 == 2 && !z6 && (i9 == 1 || i9 == 0)) {
                    int iMax2 = Math.max(i7, iMin);
                    if (i26 > 0) {
                        iMax2 = Math.min(i26, iMax2);
                    }
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMax2, 8);
                    z13 = false;
                } else {
                    if (i7 == -2) {
                        i11 = iMin;
                    } else {
                        i11 = i7;
                    }
                    if (i26 == -2) {
                        i26 = iMin;
                    } else {
                        i26 = i26;
                    }
                    if (iMin > 0 && i9 != 1) {
                        iMin = 0;
                    }
                    if (i11 > 0) {
                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i11, 8);
                        iMin = Math.max(iMin, i11);
                    }
                    if (i26 > 0) {
                        if (z2 || i9 != 1) {
                            z14 = true;
                        } else {
                            z14 = false;
                        }
                        if (z14) {
                            i12 = 8;
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i26, 8);
                        } else {
                            i12 = 8;
                        }
                        iMin = Math.min(iMin, i26);
                    } else {
                        i12 = 8;
                    }
                    if (i9 == 1) {
                        if (z2) {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                        } else if (z8) {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                        } else {
                            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                        }
                        i27 = i27 == true ? 1 : 0;
                        solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        z13 = z12;
                        solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                        z4 = z4;
                        i13 = i11;
                    } else if (i9 == 2) {
                        type = constraintAnchor.getType();
                        type2 = ConstraintAnchor.Type.TOP;
                        if (type != type2 || constraintAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                            solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                            solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                        } else {
                            solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                            solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                        }
                        SolverVariable solverVariable3 = solverVariableCreateObjectVariable;
                        ArrayRow arrayRowCreateRow = linearSystem.createRow();
                        i27 = (i27 == true ? 1 : 0) == true ? 1 : 0;
                        solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                        i13 = i11;
                        solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        linearSystem.addConstraint(arrayRowCreateRow.createRowDimensionRatio(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, solverVariable3, f2));
                        if (z2) {
                            z12 = false;
                        }
                        z13 = z12;
                        z4 = z4;
                    } else {
                        i27 = i27 == true ? 1 : 0;
                        solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                        i13 = i11;
                        z13 = z12;
                        z4 = true;
                    }
                }
                if (z11 || z8) {
                    i14 = 0;
                    c = 2;
                    if (i27 >= c && z2 && z4) {
                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, i14, 8);
                        boolean z24 = z || this.mBaseline.mTarget == null;
                        if (z || (constraintAnchor3 = this.mBaseline.mTarget) == null) {
                            z15 = z24;
                        } else {
                            ConstraintWidget constraintWidget5 = constraintAnchor3.mOwner;
                            if (constraintWidget5.mDimensionRatio != 0.0f) {
                                DimensionBehaviour[] dimensionBehaviourArr = constraintWidget5.mListDimensionBehaviors;
                                DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[i14];
                                DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                                if (dimensionBehaviour2 == dimensionBehaviour3 && dimensionBehaviourArr[1] == dimensionBehaviour3) {
                                    z15 = true;
                                } else {
                                    z15 = false;
                                }
                            } else {
                                z15 = false;
                            }
                        }
                        if (z15) {
                            linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, i14, 8);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (!zIsConnected && !zIsConnected2 && !zIsConnected3) {
                    z20 = z2;
                    i25 = 5;
                } else if (!zIsConnected || zIsConnected2) {
                    if (zIsConnected || !zIsConnected2) {
                        if (zIsConnected && zIsConnected2) {
                            ConstraintWidget constraintWidget6 = constraintAnchor.mTarget.mOwner;
                            ConstraintWidget constraintWidget7 = constraintAnchor2.mTarget.mOwner;
                            ConstraintWidget parent = getParent();
                            if (z13) {
                                if (i9 == 0) {
                                    if (i26 != 0 || i13 != 0) {
                                        i22 = 5;
                                        i24 = 5;
                                        z22 = true;
                                        z23 = false;
                                        z16 = true;
                                    } else if (solverVariableCreateObjectVariable5.isFinalValue && solverVariableCreateObjectVariable6.isFinalValue) {
                                        linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                                        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), 8);
                                        return;
                                    } else {
                                        i22 = 8;
                                        i24 = 8;
                                        z22 = false;
                                        z23 = true;
                                        z16 = false;
                                    }
                                    if ((constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier)) {
                                        solverVariable2 = solverVariable2;
                                        i15 = i22;
                                        z18 = z23;
                                        i16 = 6;
                                        z17 = z22;
                                        i17 = 4;
                                    } else {
                                        z18 = z23;
                                        z17 = z22;
                                        i17 = i24;
                                        i15 = i22;
                                        i16 = 6;
                                    }
                                } else if (i9 == 2) {
                                    if (!(constraintWidget6 instanceof Barrier) && !(constraintWidget7 instanceof Barrier)) {
                                        solverVariable2 = solverVariable2;
                                        i16 = 6;
                                        i15 = 5;
                                        i17 = 5;
                                    }
                                    z17 = true;
                                    z16 = true;
                                    z18 = false;
                                } else if (i9 == 1) {
                                    i16 = 6;
                                    i15 = 8;
                                    i17 = 4;
                                    z17 = true;
                                    z16 = true;
                                    z18 = false;
                                } else if (i9 == 3) {
                                    if (this.mResolvedDimensionRatioSide == -1) {
                                        if (z9) {
                                            solverVariable2 = solverVariable2;
                                            i16 = z2 ? 5 : 4;
                                        } else {
                                            solverVariable2 = solverVariable2;
                                            i16 = 8;
                                        }
                                        i15 = 8;
                                    } else if (z6) {
                                        if (i6 == 2 || i6 == 1) {
                                            i22 = 5;
                                            i23 = 4;
                                        } else {
                                            i22 = 8;
                                            i23 = 5;
                                        }
                                        i17 = i23;
                                        z17 = true;
                                        z16 = true;
                                        z18 = true;
                                        i15 = i22;
                                        i16 = 6;
                                    } else {
                                        if (i26 > 0) {
                                            solverVariable2 = solverVariable2;
                                            i16 = 6;
                                            i15 = 5;
                                        } else {
                                            if (i26 != 0 || i13 != 0) {
                                                i16 = 6;
                                                i15 = 5;
                                            } else if (z9) {
                                                i15 = (constraintWidget6 == parent || constraintWidget7 == parent) ? 5 : 4;
                                                i16 = 6;
                                            } else {
                                                solverVariable2 = solverVariable2;
                                                i16 = 6;
                                                i15 = 5;
                                                i17 = 8;
                                            }
                                            i17 = 4;
                                        }
                                        z17 = true;
                                        z16 = true;
                                        z18 = true;
                                    }
                                    i17 = 5;
                                    z17 = true;
                                    z16 = true;
                                    z18 = true;
                                } else {
                                    solverVariable2 = solverVariable2;
                                    i16 = 6;
                                    i15 = 5;
                                    i17 = 4;
                                    z17 = false;
                                    z16 = false;
                                    z18 = false;
                                }
                                if (z16 || solverVariableCreateObjectVariable5 != solverVariableCreateObjectVariable6 || constraintWidget6 == parent) {
                                    z19 = true;
                                } else {
                                    z16 = false;
                                    z19 = false;
                                }
                                if (z17) {
                                    if (z13 && !z7 && !z9 && solverVariableCreateObjectVariable5 == solverVariable && solverVariableCreateObjectVariable6 == solverVariable2) {
                                        z20 = false;
                                        i21 = 8;
                                        i20 = 8;
                                        z21 = false;
                                    } else {
                                        z20 = z2;
                                        i20 = i16;
                                        z21 = z19;
                                        i21 = i15;
                                    }
                                    i18 = i9;
                                    constraintWidget = parent;
                                    linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariableCreateObjectVariable6, solverVariableCreateObjectVariable4, constraintAnchor2.getMargin(), i20);
                                    i15 = i21;
                                    z19 = z21;
                                } else {
                                    i18 = i9;
                                    constraintWidget = parent;
                                    z20 = z2;
                                }
                                if (this.mVisibility != 8 && !constraintAnchor2.hasDependents()) {
                                    return;
                                }
                                if (z16) {
                                    if (z20 && solverVariableCreateObjectVariable5 != solverVariableCreateObjectVariable6 && !z13 && ((constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier))) {
                                        i15 = 6;
                                    }
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), i15);
                                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), i15);
                                } else {
                                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                                }
                                if (z20 || !z10 || (constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier)) {
                                    constraintWidget2 = constraintWidget;
                                } else {
                                    constraintWidget2 = constraintWidget;
                                    if (constraintWidget7 != constraintWidget2) {
                                        i15 = 6;
                                        iMax = 6;
                                        z19 = true;
                                    }
                                    if (z19) {
                                        if (z18 && (!z9 || z3)) {
                                            if (constraintWidget6 != constraintWidget2 || constraintWidget7 == constraintWidget2) {
                                                i19 = 6;
                                            } else {
                                                i19 = iMax;
                                            }
                                            if ((constraintWidget6 instanceof Guideline) || (constraintWidget7 instanceof Guideline)) {
                                                i19 = 5;
                                            }
                                            if ((constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier)) {
                                                i19 = 5;
                                            }
                                            if (z9) {
                                                i19 = 5;
                                            }
                                            iMax = Math.max(i19, iMax);
                                        }
                                        if (z20) {
                                            iMin2 = Math.min(i15, iMax);
                                            if (z6 || z9 || !(constraintWidget6 == constraintWidget2 || constraintWidget7 == constraintWidget2)) {
                                                iMax = iMin2;
                                            } else {
                                                iMax = 4;
                                            }
                                        }
                                        linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), iMax);
                                        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), iMax);
                                    }
                                    if (z20) {
                                        if (solverVariable == solverVariableCreateObjectVariable5) {
                                            margin = constraintAnchor.getMargin();
                                        } else {
                                            margin = 0;
                                        }
                                        if (solverVariableCreateObjectVariable5 != solverVariable) {
                                            linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, margin, 5);
                                        }
                                    }
                                    if (!z20 && z13 && i3 == 0 && i13 == 0) {
                                        if (z13 && i18 == 3) {
                                            linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 8);
                                        } else {
                                            linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 5);
                                        }
                                    }
                                }
                                iMax = i17;
                                if (z19) {
                                    if (z18) {
                                        if (constraintWidget6 != constraintWidget2) {
                                            i19 = 6;
                                        } else {
                                            i19 = 6;
                                        }
                                        if (constraintWidget6 instanceof Guideline) {
                                            i19 = 5;
                                        } else {
                                            i19 = 5;
                                        }
                                        if (constraintWidget6 instanceof Barrier) {
                                            i19 = 5;
                                        } else {
                                            i19 = 5;
                                        }
                                        if (z9) {
                                            i19 = 5;
                                        }
                                        iMax = Math.max(i19, iMax);
                                    }
                                    if (z20) {
                                        iMin2 = Math.min(i15, iMax);
                                        if (z6) {
                                            iMax = iMin2;
                                        } else {
                                            iMax = iMin2;
                                        }
                                    }
                                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), iMax);
                                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), iMax);
                                }
                                if (z20) {
                                    if (solverVariable == solverVariableCreateObjectVariable5) {
                                        margin = constraintAnchor.getMargin();
                                    } else {
                                        margin = 0;
                                    }
                                    if (solverVariableCreateObjectVariable5 != solverVariable) {
                                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, margin, 5);
                                    }
                                }
                                if (!z20) {
                                }
                            } else if (solverVariableCreateObjectVariable5.isFinalValue && solverVariableCreateObjectVariable6.isFinalValue) {
                                linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariableCreateObjectVariable6, solverVariableCreateObjectVariable4, constraintAnchor2.getMargin(), 8);
                                if (z2 && z4) {
                                    int margin2 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                                    if (solverVariableCreateObjectVariable6 != solverVariable2) {
                                        linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, margin2, 5);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            i16 = 6;
                            i15 = 5;
                            i17 = 4;
                            z17 = true;
                            z16 = true;
                            z18 = false;
                            if (z16) {
                                z19 = true;
                            } else {
                                z19 = true;
                            }
                            if (z17) {
                                if (z13) {
                                    z20 = z2;
                                    i20 = i16;
                                    z21 = z19;
                                    i21 = i15;
                                } else {
                                    z20 = z2;
                                    i20 = i16;
                                    z21 = z19;
                                    i21 = i15;
                                }
                                i18 = i9;
                                constraintWidget = parent;
                                linearSystem.addCentering(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), f, solverVariableCreateObjectVariable6, solverVariableCreateObjectVariable4, constraintAnchor2.getMargin(), i20);
                                i15 = i21;
                                z19 = z21;
                            } else {
                                i18 = i9;
                                constraintWidget = parent;
                                z20 = z2;
                            }
                            if (this.mVisibility != 8) {
                            }
                            if (z16) {
                                if (z20) {
                                    i15 = 6;
                                }
                                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), i15);
                                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                                linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), i15);
                            } else {
                                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                            }
                            if (z20) {
                                constraintWidget2 = constraintWidget;
                                iMax = i17;
                            } else {
                                constraintWidget2 = constraintWidget;
                                iMax = i17;
                            }
                            if (z19) {
                                if (z18) {
                                    if (constraintWidget6 != constraintWidget2) {
                                        i19 = 6;
                                    } else {
                                        i19 = 6;
                                    }
                                    if (constraintWidget6 instanceof Guideline) {
                                        i19 = 5;
                                    } else {
                                        i19 = 5;
                                    }
                                    if (constraintWidget6 instanceof Barrier) {
                                        i19 = 5;
                                    } else {
                                        i19 = 5;
                                    }
                                    if (z9) {
                                        i19 = 5;
                                    }
                                    iMax = Math.max(i19, iMax);
                                }
                                if (z20) {
                                    iMin2 = Math.min(i15, iMax);
                                    if (z6) {
                                        iMax = iMin2;
                                    } else {
                                        iMax = iMin2;
                                    }
                                }
                                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), iMax);
                                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), iMax);
                            }
                            if (z20) {
                                if (solverVariable == solverVariableCreateObjectVariable5) {
                                    margin = constraintAnchor.getMargin();
                                } else {
                                    margin = 0;
                                }
                                if (solverVariableCreateObjectVariable5 != solverVariable) {
                                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, margin, 5);
                                }
                            }
                            if (!z20) {
                            }
                        }
                        i25 = 5;
                    } else {
                        linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable6, -constraintAnchor2.getMargin(), 8);
                        if (z2) {
                            if (this.OPTIMIZE_WRAP && solverVariableCreateObjectVariable3.isFinalValue && (constraintWidget3 = this.mParent) != null) {
                                ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget3;
                                if (z) {
                                    constraintWidgetContainer.addHorizontalWrapMinVariable(constraintAnchor);
                                } else {
                                    constraintWidgetContainer.addVerticalWrapMinVariable(constraintAnchor);
                                }
                            } else {
                                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariable, 0, 5);
                            }
                        }
                        z20 = z2;
                        i25 = 5;
                    }
                    z20 = z2;
                    i25 = 5;
                } else {
                    z20 = z2;
                    i25 = (z2 && (constraintAnchor.mTarget.mOwner instanceof Barrier)) ? 8 : 5;
                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                }
                if (z20 && z4) {
                    int margin3 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                    if (solverVariableCreateObjectVariable6 != solverVariable2) {
                        if (!this.OPTIMIZE_WRAP || !solverVariableCreateObjectVariable4.isFinalValue || (constraintWidget4 = this.mParent) == null) {
                            linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable4, margin3, i25);
                            return;
                        }
                        ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget4;
                        if (z) {
                            constraintWidgetContainer2.addHorizontalWrapMaxVariable(constraintAnchor2);
                            return;
                        } else {
                            constraintWidgetContainer2.addVerticalWrapMaxVariable(constraintAnchor2);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (z5) {
                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 3);
                if (i3 > 0) {
                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i3, 8);
                }
                if (i4 < Integer.MAX_VALUE) {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i4, 8);
                }
            } else {
                linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
            }
            z13 = z12;
            i13 = i7;
            if (z11) {
                i14 = 0;
                c = 2;
            } else {
                i14 = 0;
                c = 2;
            }
            if (i27 >= c) {
            }
        }
        i9 = i28;
        iMin = this.mWidthOverride;
        if (iMin == -1) {
            iMin = i2;
        } else {
            iMin = i2;
        }
        i10 = this.mHeightOverride;
        if (i10 != -1) {
            this.mHeightOverride = -1;
            iMin = i10;
            z12 = false;
        }
        if (this.mVisibility == 8) {
            iMin = 0;
            z12 = false;
        }
        if (z11) {
            if (zIsConnected) {
                if (zIsConnected) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
                }
            } else if (zIsConnected) {
                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, constraintAnchor.getMargin(), 8);
            }
        }
        if (!z12) {
            if (i27 == 2) {
            }
            if (i7 == -2) {
                i11 = iMin;
            } else {
                i11 = i7;
            }
            if (i26 == -2) {
                i26 = iMin;
            } else {
                i26 = i26;
            }
            if (iMin > 0) {
                iMin = 0;
            }
            if (i11 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i11, 8);
                iMin = Math.max(iMin, i11);
            }
            if (i26 > 0) {
                if (z2) {
                    z14 = true;
                } else {
                    z14 = true;
                }
                if (z14) {
                    i12 = 8;
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i26, 8);
                } else {
                    i12 = 8;
                }
                iMin = Math.min(iMin, i26);
            } else {
                i12 = 8;
            }
            if (i9 == 1) {
                if (z2) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                } else if (z8) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                } else {
                    linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 5);
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, i12);
                }
                i27 = i27 == true ? 1 : 0;
                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                z13 = z12;
                solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                z4 = z4;
                i13 = i11;
            } else if (i9 == 2) {
                type = constraintAnchor.getType();
                type2 = ConstraintAnchor.Type.TOP;
                if (type != type2) {
                    solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                    solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                } else {
                    solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                    solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                }
                SolverVariable solverVariable4 = solverVariableCreateObjectVariable;
                ArrayRow arrayRowCreateRow2 = linearSystem.createRow();
                i27 = (i27 == true ? 1 : 0) == true ? 1 : 0;
                solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                i13 = i11;
                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                linearSystem.addConstraint(arrayRowCreateRow2.createRowDimensionRatio(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, solverVariable4, f2));
                if (z2) {
                    z12 = false;
                }
                z13 = z12;
                z4 = z4;
            } else {
                i27 = i27 == true ? 1 : 0;
                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                solverVariableCreateObjectVariable6 = solverVariableCreateObjectVariable6;
                i13 = i11;
                z13 = z12;
                z4 = true;
            }
            if (z11) {
                i14 = 0;
                c = 2;
            } else {
                i14 = 0;
                c = 2;
            }
            if (i27 >= c) {
            }
        }
        if (z5) {
            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, 0, 3);
            if (i3 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i3, 8);
            }
            if (i4 < Integer.MAX_VALUE) {
                linearSystem.addLowerThan(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, i4, 8);
            }
        } else {
            linearSystem.addEquality(solverVariableCreateObjectVariable4, solverVariableCreateObjectVariable3, iMin, 8);
        }
        z13 = z12;
        i13 = i7;
        if (z11) {
            i14 = 0;
            c = 2;
        } else {
            i14 = 0;
            c = 2;
        }
        if (i27 >= c) {
        }
    }

    private boolean isChainHead(int i) {
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        if (constraintAnchorArr[i2].mTarget != null && constraintAnchorArr[i2].mTarget.mTarget != constraintAnchorArr[i2]) {
            int i3 = i2 + 1;
            if (constraintAnchorArr[i3].mTarget != null && constraintAnchorArr[i3].mTarget.mTarget == constraintAnchorArr[i3]) {
                return true;
            }
        }
        return false;
    }

    private void serializeAnchor(StringBuilder sb, String str, ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append(str);
        sb.append(" : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("',");
        sb.append(constraintAnchor.mMargin);
        sb.append(",");
        sb.append(constraintAnchor.mGoneMargin);
        sb.append(",");
        sb.append(" ] ,\n");
    }

    private void serializeAttribute(StringBuilder sb, String str, float f, float f2) {
        if (f == f2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(f);
        sb.append(",\n");
    }

    private void serializeCircle(StringBuilder sb, ConstraintAnchor constraintAnchor, float f) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append("circle : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("',");
        sb.append(constraintAnchor.mMargin);
        sb.append(",");
        sb.append(f);
        sb.append(",");
        sb.append(" ] ,\n");
    }

    private void serializeDimensionRatio(StringBuilder sb, String str, float f, int i) {
        if (f == 0.0f) {
            return;
        }
        sb.append(str);
        sb.append(" :  [");
        sb.append(f);
        sb.append(",");
        sb.append(i);
        sb.append("");
        sb.append("],\n");
    }

    private void serializeSize(StringBuilder sb, String str, int i, int i2, int i3, int i4, int i5, int i6, float f, float f2) {
        sb.append(str);
        sb.append(" :  {\n");
        serializeAttribute(sb, "size", i, Integer.MIN_VALUE);
        serializeAttribute(sb, "min", i2, 0);
        serializeAttribute(sb, "max", i3, Integer.MAX_VALUE);
        serializeAttribute(sb, "matchMin", i5, 0);
        serializeAttribute(sb, "matchDef", i6, 0);
        serializeAttribute(sb, "matchPercent", i6, 1);
        sb.append("},\n");
    }

    public void addChildrenToSolverByDependency(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, HashSet<ConstraintWidget> hashSet, int i, boolean z) {
        if (z) {
            if (!hashSet.contains(this)) {
                return;
            }
            Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem, this);
            hashSet.remove(this);
            addToSolver(linearSystem, constraintWidgetContainer.optimizeFor(64));
        }
        if (i == 0) {
            HashSet<ConstraintAnchor> dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator<ConstraintAnchor> it = dependents.iterator();
                while (it.hasNext()) {
                    it.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
            }
            HashSet<ConstraintAnchor> dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator<ConstraintAnchor> it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    it2.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
                return;
            }
            return;
        }
        HashSet<ConstraintAnchor> dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator<ConstraintAnchor> it3 = dependents3.iterator();
            while (it3.hasNext()) {
                it3.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet<ConstraintAnchor> dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator<ConstraintAnchor> it4 = dependents4.iterator();
            while (it4.hasNext()) {
                it4.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet<ConstraintAnchor> dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator<ConstraintAnchor> it5 = dependents5.iterator();
            while (it5.hasNext()) {
                it5.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
    }

    public final boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    /* JADX WARN: Code duplicated, block: B:194:0x02f6  */
    /* JADX WARN: Code duplicated, block: B:198:0x0300  */
    /* JADX WARN: Code duplicated, block: B:201:0x0305  */
    /* JADX WARN: Code duplicated, block: B:205:0x030f  */
    /* JADX WARN: Code duplicated, block: B:208:0x031a  */
    /* JADX WARN: Code duplicated, block: B:211:0x0320  */
    /* JADX WARN: Code duplicated, block: B:213:0x0323  */
    /* JADX WARN: Code duplicated, block: B:214:0x0325  */
    /* JADX WARN: Code duplicated, block: B:217:0x033d  */
    /* JADX WARN: Code duplicated, block: B:21:0x0054  */
    /* JADX WARN: Code duplicated, block: B:238:0x03a2  */
    /* JADX WARN: Code duplicated, block: B:240:0x03ae  */
    /* JADX WARN: Code duplicated, block: B:241:0x03b6  */
    /* JADX WARN: Code duplicated, block: B:244:0x03bc  */
    /* JADX WARN: Code duplicated, block: B:245:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:248:0x03e9  */
    /* JADX WARN: Code duplicated, block: B:249:0x03ec  */
    /* JADX WARN: Code duplicated, block: B:251:0x0447  */
    /* JADX WARN: Code duplicated, block: B:253:0x045b  */
    /* JADX WARN: Code duplicated, block: B:255:0x0461  */
    /* JADX WARN: Code duplicated, block: B:257:0x0467  */
    /* JADX WARN: Code duplicated, block: B:270:0x04bf  */
    /* JADX WARN: Code duplicated, block: B:274:0x04d3  */
    /* JADX WARN: Code duplicated, block: B:275:0x04d5  */
    /* JADX WARN: Code duplicated, block: B:277:0x04d8  */
    /* JADX WARN: Code duplicated, block: B:315:0x05b6  */
    /* JADX WARN: Code duplicated, block: B:317:0x05bc  */
    /* JADX WARN: Code duplicated, block: B:319:0x05c5  */
    /* JADX WARN: Code duplicated, block: B:320:0x05d5  */
    /* JADX WARN: Code duplicated, block: B:321:0x05e7  */
    /* JADX WARN: Code duplicated, block: B:324:0x05f1  */
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        boolean z3;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        boolean z4;
        boolean z5;
        SolverVariable solverVariable;
        char c;
        int i;
        int i2;
        int i3;
        boolean z6;
        int i4;
        boolean z7;
        DimensionBehaviour dimensionBehaviour;
        DimensionBehaviour dimensionBehaviour2;
        boolean z8;
        int i5;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        int i6;
        int i7;
        char c2;
        ConstraintWidget constraintWidget3;
        boolean z9;
        boolean z10;
        ConstraintWidget constraintWidget4;
        boolean z11;
        VerticalWidgetRun verticalWidgetRun;
        DependencyNode dependencyNode;
        ConstraintWidget constraintWidget5;
        SolverVariable solverVariableCreateObjectVariable;
        ConstraintWidget constraintWidget6;
        SolverVariable solverVariableCreateObjectVariable2;
        DimensionBehaviour[] dimensionBehaviourArr;
        boolean z12;
        HorizontalWidgetRun horizontalWidgetRun;
        int i8;
        int i9;
        boolean zIsInHorizontalChain;
        boolean zIsInVerticalChain;
        HorizontalWidgetRun horizontalWidgetRun2;
        VerticalWidgetRun verticalWidgetRun2;
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(this.mLeft);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(this.mRight);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(this.mTop);
        SolverVariable solverVariableCreateObjectVariable6 = linearSystem.createObjectVariable(this.mBottom);
        SolverVariable solverVariableCreateObjectVariable7 = linearSystem.createObjectVariable(this.mBaseline);
        ConstraintWidget constraintWidget7 = this.mParent;
        if (constraintWidget7 == null) {
            z2 = false;
            z3 = false;
        } else {
            boolean z13 = constraintWidget7 != null && constraintWidget7.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
            boolean z14 = constraintWidget7 != null && constraintWidget7.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
            int i10 = this.mWrapBehaviorInParent;
            if (i10 == 1) {
                z2 = z13;
                z3 = false;
            } else if (i10 == 2) {
                z3 = z14;
                z2 = false;
            } else if (i10 != 3) {
                z3 = z14;
                z2 = z13;
            } else {
                z2 = false;
                z3 = false;
            }
        }
        if (this.mVisibility == 8 && !this.mAnimated && !hasDependencies()) {
            boolean[] zArr = this.mIsInBarrier;
            if (!zArr[0] && !zArr[1]) {
                return;
            }
        }
        boolean z15 = this.resolvedHorizontal;
        if (z15 || this.resolvedVertical) {
            if (z15) {
                linearSystem.addEquality(solverVariableCreateObjectVariable3, this.mX);
                linearSystem.addEquality(solverVariableCreateObjectVariable4, this.mX + this.mWidth);
                if (z2 && (constraintWidget2 = this.mParent) != null) {
                    if (this.OPTIMIZE_WRAP_ON_RESOLVED) {
                        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
                        constraintWidgetContainer.addHorizontalWrapMinVariable(this.mLeft);
                        constraintWidgetContainer.addHorizontalWrapMaxVariable(this.mRight);
                    } else {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mRight), solverVariableCreateObjectVariable4, 0, 5);
                    }
                }
            }
            if (this.resolvedVertical) {
                linearSystem.addEquality(solverVariableCreateObjectVariable5, this.mY);
                linearSystem.addEquality(solverVariableCreateObjectVariable6, this.mY + this.mHeight);
                if (this.mBaseline.hasDependents()) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable7, this.mY + this.mBaselineDistance);
                }
                if (z3 && (constraintWidget = this.mParent) != null) {
                    if (this.OPTIMIZE_WRAP_ON_RESOLVED) {
                        ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget;
                        constraintWidgetContainer2.addVerticalWrapMinVariable(this.mTop);
                        constraintWidgetContainer2.addVerticalWrapMaxVariable(this.mBottom);
                    } else {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget.mBottom), solverVariableCreateObjectVariable6, 0, 5);
                    }
                }
            }
            if (this.resolvedHorizontal && this.resolvedVertical) {
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
                return;
            }
        }
        Metrics metrics = LinearSystem.sMetrics;
        if (metrics != null) {
            metrics.widgets++;
        }
        if (z && (horizontalWidgetRun2 = this.horizontalRun) != null && (verticalWidgetRun2 = this.verticalRun) != null) {
            DependencyNode dependencyNode2 = horizontalWidgetRun2.start;
            if (dependencyNode2.resolved && horizontalWidgetRun2.end.resolved && verticalWidgetRun2.start.resolved && verticalWidgetRun2.end.resolved) {
                if (metrics != null) {
                    metrics.graphSolved++;
                }
                linearSystem.addEquality(solverVariableCreateObjectVariable3, dependencyNode2.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable4, this.horizontalRun.end.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable5, this.verticalRun.start.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable6, this.verticalRun.end.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable7, this.verticalRun.baseline.value);
                if (this.mParent != null) {
                    if (z2 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable4, 0, 8);
                    }
                    if (z3 && this.isTerminalWidget[1] && !isInVerticalChain()) {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable6, 0, 8);
                    }
                }
                this.resolvedHorizontal = false;
                this.resolvedVertical = false;
                return;
            }
        }
        if (metrics != null) {
            metrics.linearSolved++;
        }
        if (this.mParent != null) {
            if (isChainHead(0)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                zIsInHorizontalChain = true;
            } else {
                zIsInHorizontalChain = isInHorizontalChain();
            }
            if (isChainHead(1)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 1);
                zIsInVerticalChain = true;
            } else {
                zIsInVerticalChain = isInVerticalChain();
            }
            if (!zIsInHorizontalChain && z2 && this.mVisibility != 8 && this.mLeft.mTarget == null && this.mRight.mTarget == null) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable4, 0, 1);
            }
            if (!zIsInVerticalChain && z3 && this.mVisibility != 8 && this.mTop.mTarget == null && this.mBottom.mTarget == null && this.mBaseline == null) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), solverVariableCreateObjectVariable6, 0, 1);
            }
            z5 = zIsInHorizontalChain;
            z4 = zIsInVerticalChain;
        } else {
            z4 = false;
            z5 = false;
        }
        int i11 = this.mWidth;
        int i12 = this.mMinWidth;
        if (i11 >= i12) {
            i12 = i11;
        }
        int i13 = this.mHeight;
        int i14 = this.mMinHeight;
        if (i13 >= i14) {
            i14 = i13;
        }
        DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr2[0];
        DimensionBehaviour dimensionBehaviour4 = DimensionBehaviour.MATCH_CONSTRAINT;
        int i15 = i12;
        boolean z16 = dimensionBehaviour3 != dimensionBehaviour4;
        int i16 = i14;
        boolean z17 = dimensionBehaviourArr2[1] != dimensionBehaviour4;
        int i17 = this.mDimensionRatioSide;
        this.mResolvedDimensionRatioSide = i17;
        SolverVariable solverVariable4 = solverVariableCreateObjectVariable7;
        float f = this.mDimensionRatio;
        this.mResolvedDimensionRatio = f;
        SolverVariable solverVariable5 = solverVariableCreateObjectVariable6;
        int i18 = this.mMatchConstraintDefaultWidth;
        SolverVariable solverVariable6 = solverVariableCreateObjectVariable5;
        int i19 = this.mMatchConstraintDefaultHeight;
        if (f > 0.0f) {
            solverVariable = solverVariableCreateObjectVariable3;
            if (this.mVisibility != 8) {
                if (dimensionBehaviourArr2[0] == dimensionBehaviour4 && i18 == 0) {
                    i18 = 3;
                }
                if (dimensionBehaviourArr2[1] == dimensionBehaviour4 && i19 == 0) {
                    i19 = 3;
                }
                if (dimensionBehaviourArr2[0] == dimensionBehaviour4 && dimensionBehaviourArr2[1] == dimensionBehaviour4) {
                    i9 = 3;
                    if (i18 == 3 && i19 == 3) {
                        setupDimensionRatio(z2, z3, z16, z17);
                    }
                    c = 0;
                    i = i18;
                    i2 = i15;
                    i3 = i16;
                    z6 = true;
                } else {
                    i9 = 3;
                }
                if (dimensionBehaviourArr2[0] == dimensionBehaviour4 && i18 == i9) {
                    this.mResolvedDimensionRatioSide = 0;
                    i2 = (int) (f * i13);
                    if (dimensionBehaviourArr2[1] != dimensionBehaviour4) {
                        i19 = i19;
                        i3 = i16;
                        c = 0;
                        z6 = false;
                        i = 4;
                    } else {
                        i = i18;
                        i3 = i16;
                        c = 0;
                        z6 = true;
                    }
                } else {
                    if (dimensionBehaviourArr2[1] == dimensionBehaviour4 && i19 == 3) {
                        this.mResolvedDimensionRatioSide = 1;
                        if (i17 == -1) {
                            this.mResolvedDimensionRatio = 1.0f / f;
                        }
                        int i20 = (int) (this.mResolvedDimensionRatio * i11);
                        c = 0;
                        i3 = i20;
                        i = i18;
                        if (dimensionBehaviourArr2[0] != dimensionBehaviour4) {
                            i2 = i15;
                            z6 = false;
                            i19 = 4;
                        } else {
                            i2 = i15;
                        }
                    } else {
                        c = 0;
                        i = i18;
                        i2 = i15;
                        i3 = i16;
                    }
                    z6 = true;
                }
            }
            int[] iArr = this.mResolvedMatchConstraintDefault;
            iArr[c] = i;
            iArr[1] = i19;
            if (z6) {
                int i21 = this.mResolvedDimensionRatioSide;
                i4 = -1;
                boolean z18 = i21 != 0 || i21 == -1;
                if (z6 || !((i8 = this.mResolvedDimensionRatioSide) == 1 || i8 == i4)) {
                    z7 = false;
                } else {
                    z7 = true;
                }
                dimensionBehaviour = this.mListDimensionBehaviors[0];
                dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour == dimensionBehaviour2 || !(this instanceof ConstraintWidgetContainer)) {
                    z8 = false;
                } else {
                    z8 = true;
                }
                if (z8) {
                    i5 = 0;
                } else {
                    i5 = i2;
                }
                boolean z19 = !this.mCenter.isConnected();
                boolean[] zArr2 = this.mIsInBarrier;
                boolean z20 = zArr2[0];
                boolean z21 = zArr2[1];
                if (this.mHorizontalResolution != 2 || this.resolvedHorizontal) {
                    solverVariable2 = solverVariableCreateObjectVariable4;
                    solverVariable3 = solverVariable;
                } else if (!z || (horizontalWidgetRun = this.horizontalRun) == null) {
                    SolverVariable solverVariable7 = solverVariable;
                    constraintWidget5 = this.mParent;
                    if (constraintWidget5 != null) {
                        solverVariableCreateObjectVariable = linearSystem.createObjectVariable(constraintWidget5.mRight);
                    } else {
                        solverVariableCreateObjectVariable = null;
                    }
                    constraintWidget6 = this.mParent;
                    if (constraintWidget6 != null) {
                        solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(constraintWidget6.mLeft);
                    } else {
                        solverVariableCreateObjectVariable2 = null;
                    }
                    boolean z22 = this.isTerminalWidget[0];
                    dimensionBehaviourArr = this.mListDimensionBehaviors;
                    DimensionBehaviour dimensionBehaviour5 = dimensionBehaviourArr[0];
                    ConstraintAnchor constraintAnchor = this.mLeft;
                    ConstraintAnchor constraintAnchor2 = this.mRight;
                    int i22 = this.mX;
                    int i23 = this.mMinWidth;
                    int i24 = this.mMaxDimension[0];
                    float f2 = this.mHorizontalBiasPercent;
                    if (dimensionBehaviourArr[1] == dimensionBehaviour4) {
                        z12 = true;
                    } else {
                        z12 = false;
                    }
                    solverVariable2 = solverVariableCreateObjectVariable4;
                    solverVariable3 = solverVariable7;
                    applyConstraints(linearSystem, true, z2, z3, z22, solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable, dimensionBehaviour5, z8, constraintAnchor, constraintAnchor2, i22, i5, i23, i24, f2, z18, z12, z5, z4, z20, i, i19, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z19);
                } else {
                    DependencyNode dependencyNode3 = horizontalWidgetRun.start;
                    if (!dependencyNode3.resolved || !horizontalWidgetRun.end.resolved) {
                        SolverVariable solverVariable8 = solverVariable;
                        constraintWidget5 = this.mParent;
                        if (constraintWidget5 != null) {
                            solverVariableCreateObjectVariable = linearSystem.createObjectVariable(constraintWidget5.mRight);
                        } else {
                            solverVariableCreateObjectVariable = null;
                        }
                        constraintWidget6 = this.mParent;
                        if (constraintWidget6 != null) {
                            solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(constraintWidget6.mLeft);
                        } else {
                            solverVariableCreateObjectVariable2 = null;
                        }
                        boolean z23 = this.isTerminalWidget[0];
                        dimensionBehaviourArr = this.mListDimensionBehaviors;
                        DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr[0];
                        ConstraintAnchor constraintAnchor3 = this.mLeft;
                        ConstraintAnchor constraintAnchor4 = this.mRight;
                        int i25 = this.mX;
                        int i26 = this.mMinWidth;
                        int i27 = this.mMaxDimension[0];
                        float f3 = this.mHorizontalBiasPercent;
                        if (dimensionBehaviourArr[1] == dimensionBehaviour4) {
                            z12 = true;
                        } else {
                            z12 = false;
                        }
                        solverVariable2 = solverVariableCreateObjectVariable4;
                        solverVariable3 = solverVariable8;
                        applyConstraints(linearSystem, true, z2, z3, z23, solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable, dimensionBehaviour6, z8, constraintAnchor3, constraintAnchor4, i25, i5, i26, i27, f3, z18, z12, z5, z4, z20, i, i19, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z19);
                    } else if (z) {
                        SolverVariable solverVariable9 = solverVariable;
                        linearSystem.addEquality(solverVariable9, dependencyNode3.value);
                        linearSystem.addEquality(solverVariableCreateObjectVariable4, this.horizontalRun.end.value);
                        if (this.mParent != null && z2 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), solverVariableCreateObjectVariable4, 0, 8);
                        }
                        solverVariable2 = solverVariableCreateObjectVariable4;
                        solverVariable3 = solverVariable9;
                    } else {
                        solverVariable2 = solverVariableCreateObjectVariable4;
                        solverVariable3 = solverVariable;
                    }
                }
                if (z) {
                    constraintWidget3 = this;
                    verticalWidgetRun = constraintWidget3.verticalRun;
                    if (verticalWidgetRun != null) {
                        dependencyNode = verticalWidgetRun.start;
                        if (!dependencyNode.resolved && verticalWidgetRun.end.resolved) {
                            linearSystem = linearSystem;
                            solverVariable6 = solverVariable6;
                            linearSystem.addEquality(solverVariable6, dependencyNode.value);
                            solverVariable5 = solverVariable5;
                            linearSystem.addEquality(solverVariable5, constraintWidget3.verticalRun.end.value);
                            solverVariable4 = solverVariable4;
                            linearSystem.addEquality(solverVariable4, constraintWidget3.verticalRun.baseline.value);
                            ConstraintWidget constraintWidget8 = constraintWidget3.mParent;
                            if (constraintWidget8 == null || z4 || !z3) {
                                i6 = 8;
                                i7 = 0;
                                c2 = 1;
                            } else {
                                c2 = 1;
                                if (constraintWidget3.isTerminalWidget[1]) {
                                    i6 = 8;
                                    i7 = 0;
                                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget8.mBottom), solverVariable5, 0, 8);
                                } else {
                                    i6 = 8;
                                    i7 = 0;
                                }
                            }
                            z9 = false;
                        }
                        if (constraintWidget3.mVerticalResolution == 2) {
                            z10 = false;
                        } else {
                            z10 = z9;
                        }
                        if (!z10 && !constraintWidget3.resolvedVertical) {
                            boolean z24 = constraintWidget3.mListDimensionBehaviors[c2] == dimensionBehaviour2 && (constraintWidget3 instanceof ConstraintWidgetContainer);
                            if (z24) {
                                i3 = 0;
                            }
                            ConstraintWidget constraintWidget9 = constraintWidget3.mParent;
                            SolverVariable solverVariableCreateObjectVariable8 = constraintWidget9 != null ? linearSystem.createObjectVariable(constraintWidget9.mBottom) : null;
                            ConstraintWidget constraintWidget10 = constraintWidget3.mParent;
                            SolverVariable solverVariableCreateObjectVariable9 = constraintWidget10 != null ? linearSystem.createObjectVariable(constraintWidget10.mTop) : null;
                            if (constraintWidget3.mBaselineDistance > 0 || constraintWidget3.mVisibility == i6) {
                                ConstraintAnchor constraintAnchor5 = constraintWidget3.mBaseline;
                                if (constraintAnchor5.mTarget != null) {
                                    linearSystem.addEquality(solverVariable4, solverVariable6, getBaselineDistance(), i6);
                                    linearSystem.addEquality(solverVariable4, linearSystem.createObjectVariable(constraintWidget3.mBaseline.mTarget), constraintWidget3.mBaseline.getMargin(), i6);
                                    if (z3 != 0) {
                                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable8, linearSystem.createObjectVariable(constraintWidget3.mBottom), i7, 5);
                                    }
                                    z11 = false;
                                } else {
                                    if (constraintWidget3.mVisibility == i6) {
                                        linearSystem.addEquality(solverVariable4, solverVariable6, constraintAnchor5.getMargin(), i6);
                                    } else {
                                        linearSystem.addEquality(solverVariable4, solverVariable6, getBaselineDistance(), i6);
                                    }
                                    z11 = z19;
                                }
                            } else {
                                z11 = z19;
                            }
                            boolean z25 = constraintWidget3.isTerminalWidget[c2];
                            DimensionBehaviour[] dimensionBehaviourArr3 = constraintWidget3.mListDimensionBehaviors;
                            applyConstraints(linearSystem, false, z3, z2, z25, solverVariableCreateObjectVariable9, solverVariableCreateObjectVariable8, dimensionBehaviourArr3[c2], z24, constraintWidget3.mTop, constraintWidget3.mBottom, constraintWidget3.mY, i3, constraintWidget3.mMinHeight, constraintWidget3.mMaxDimension[c2], constraintWidget3.mVerticalBiasPercent, z7, dimensionBehaviourArr3[0] == dimensionBehaviour4, z4, z5, z21, i19, i, constraintWidget3.mMatchConstraintMinHeight, constraintWidget3.mMatchConstraintMaxHeight, constraintWidget3.mMatchConstraintPercentHeight, z11);
                        }
                        if (z6) {
                            constraintWidget4 = this;
                            if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                                linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable2, solverVariable3, constraintWidget4.mResolvedDimensionRatio, 8);
                            } else {
                                linearSystem.addRatio(solverVariable2, solverVariable3, solverVariable5, solverVariable6, constraintWidget4.mResolvedDimensionRatio, 8);
                            }
                        } else {
                            constraintWidget4 = this;
                        }
                        if (constraintWidget4.mCenter.isConnected()) {
                            linearSystem.addCenterPoint(constraintWidget4, constraintWidget4.mCenter.getTarget().getOwner(), (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f), constraintWidget4.mCenter.getMargin());
                        }
                        constraintWidget4.resolvedHorizontal = false;
                        constraintWidget4.resolvedVertical = false;
                    }
                    i6 = 8;
                    i7 = 0;
                    c2 = 1;
                } else {
                    i6 = 8;
                    i7 = 0;
                    c2 = 1;
                    constraintWidget3 = this;
                }
                z9 = true;
                if (constraintWidget3.mVerticalResolution == 2) {
                    z10 = false;
                } else {
                    z10 = z9;
                }
                if (!z10) {
                }
                if (z6) {
                    constraintWidget4 = this;
                    if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                        linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable2, solverVariable3, constraintWidget4.mResolvedDimensionRatio, 8);
                    } else {
                        linearSystem.addRatio(solverVariable2, solverVariable3, solverVariable5, solverVariable6, constraintWidget4.mResolvedDimensionRatio, 8);
                    }
                } else {
                    constraintWidget4 = this;
                }
                if (constraintWidget4.mCenter.isConnected()) {
                    linearSystem.addCenterPoint(constraintWidget4, constraintWidget4.mCenter.getTarget().getOwner(), (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f), constraintWidget4.mCenter.getMargin());
                }
                constraintWidget4.resolvedHorizontal = false;
                constraintWidget4.resolvedVertical = false;
            }
            i4 = -1;
            if (z6) {
                z7 = false;
            } else {
                z7 = false;
            }
            dimensionBehaviour = this.mListDimensionBehaviors[0];
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour == dimensionBehaviour2) {
                z8 = false;
            } else {
                z8 = false;
            }
            if (z8) {
                i5 = 0;
            } else {
                i5 = i2;
            }
            boolean z110 = !this.mCenter.isConnected();
            boolean[] zArr3 = this.mIsInBarrier;
            boolean z26 = zArr3[0];
            boolean z27 = zArr3[1];
            if (this.mHorizontalResolution != 2) {
                solverVariable2 = solverVariableCreateObjectVariable4;
                solverVariable3 = solverVariable;
            } else {
                solverVariable2 = solverVariableCreateObjectVariable4;
                solverVariable3 = solverVariable;
            }
            if (z) {
                constraintWidget3 = this;
                verticalWidgetRun = constraintWidget3.verticalRun;
                if (verticalWidgetRun != null) {
                    dependencyNode = verticalWidgetRun.start;
                    if (!dependencyNode.resolved) {
                    }
                }
                i6 = 8;
                i7 = 0;
                c2 = 1;
            } else {
                i6 = 8;
                i7 = 0;
                c2 = 1;
                constraintWidget3 = this;
            }
            z9 = true;
            if (constraintWidget3.mVerticalResolution == 2) {
                z10 = false;
            } else {
                z10 = z9;
            }
            if (!z10) {
            }
            if (z6) {
                constraintWidget4 = this;
                if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                    linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable2, solverVariable3, constraintWidget4.mResolvedDimensionRatio, 8);
                } else {
                    linearSystem.addRatio(solverVariable2, solverVariable3, solverVariable5, solverVariable6, constraintWidget4.mResolvedDimensionRatio, 8);
                }
            } else {
                constraintWidget4 = this;
            }
            if (constraintWidget4.mCenter.isConnected()) {
                linearSystem.addCenterPoint(constraintWidget4, constraintWidget4.mCenter.getTarget().getOwner(), (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f), constraintWidget4.mCenter.getMargin());
            }
            constraintWidget4.resolvedHorizontal = false;
            constraintWidget4.resolvedVertical = false;
        }
        solverVariable = solverVariableCreateObjectVariable3;
        c = 0;
        i = i18;
        i19 = i19;
        i2 = i15;
        i3 = i16;
        z6 = false;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[c] = i;
        iArr2[1] = i19;
        if (z6) {
            int i28 = this.mResolvedDimensionRatioSide;
            i4 = -1;
            if (i28 != 0) {
            }
            if (z6) {
                z7 = false;
            } else {
                z7 = false;
            }
            dimensionBehaviour = this.mListDimensionBehaviors[0];
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour == dimensionBehaviour2) {
                z8 = false;
            } else {
                z8 = false;
            }
            if (z8) {
                i5 = 0;
            } else {
                i5 = i2;
            }
            boolean z111 = !this.mCenter.isConnected();
            boolean[] zArr4 = this.mIsInBarrier;
            boolean z28 = zArr4[0];
            boolean z29 = zArr4[1];
            if (this.mHorizontalResolution != 2) {
                solverVariable2 = solverVariableCreateObjectVariable4;
                solverVariable3 = solverVariable;
            } else {
                solverVariable2 = solverVariableCreateObjectVariable4;
                solverVariable3 = solverVariable;
            }
            if (z) {
                constraintWidget3 = this;
                verticalWidgetRun = constraintWidget3.verticalRun;
                if (verticalWidgetRun != null) {
                    dependencyNode = verticalWidgetRun.start;
                    if (!dependencyNode.resolved) {
                    }
                }
                i6 = 8;
                i7 = 0;
                c2 = 1;
            } else {
                i6 = 8;
                i7 = 0;
                c2 = 1;
                constraintWidget3 = this;
            }
            z9 = true;
            if (constraintWidget3.mVerticalResolution == 2) {
                z10 = false;
            } else {
                z10 = z9;
            }
            if (!z10) {
            }
            if (z6) {
                constraintWidget4 = this;
                if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                    linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable2, solverVariable3, constraintWidget4.mResolvedDimensionRatio, 8);
                } else {
                    linearSystem.addRatio(solverVariable2, solverVariable3, solverVariable5, solverVariable6, constraintWidget4.mResolvedDimensionRatio, 8);
                }
            } else {
                constraintWidget4 = this;
            }
            if (constraintWidget4.mCenter.isConnected()) {
                linearSystem.addCenterPoint(constraintWidget4, constraintWidget4.mCenter.getTarget().getOwner(), (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f), constraintWidget4.mCenter.getMargin());
            }
            constraintWidget4.resolvedHorizontal = false;
            constraintWidget4.resolvedVertical = false;
        }
        i4 = -1;
        if (z6) {
            z7 = false;
        } else {
            z7 = false;
        }
        dimensionBehaviour = this.mListDimensionBehaviors[0];
        dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour == dimensionBehaviour2) {
            z8 = false;
        } else {
            z8 = false;
        }
        if (z8) {
            i5 = 0;
        } else {
            i5 = i2;
        }
        boolean z112 = !this.mCenter.isConnected();
        boolean[] zArr5 = this.mIsInBarrier;
        boolean z210 = zArr5[0];
        boolean z211 = zArr5[1];
        if (this.mHorizontalResolution != 2) {
            solverVariable2 = solverVariableCreateObjectVariable4;
            solverVariable3 = solverVariable;
        } else {
            solverVariable2 = solverVariableCreateObjectVariable4;
            solverVariable3 = solverVariable;
        }
        if (z) {
            constraintWidget3 = this;
            verticalWidgetRun = constraintWidget3.verticalRun;
            if (verticalWidgetRun != null) {
                dependencyNode = verticalWidgetRun.start;
                if (!dependencyNode.resolved) {
                }
            }
            i6 = 8;
            i7 = 0;
            c2 = 1;
        } else {
            i6 = 8;
            i7 = 0;
            c2 = 1;
            constraintWidget3 = this;
        }
        z9 = true;
        if (constraintWidget3.mVerticalResolution == 2) {
            z10 = false;
        } else {
            z10 = z9;
        }
        if (!z10) {
        }
        if (z6) {
            constraintWidget4 = this;
            if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable2, solverVariable3, constraintWidget4.mResolvedDimensionRatio, 8);
            } else {
                linearSystem.addRatio(solverVariable2, solverVariable3, solverVariable5, solverVariable6, constraintWidget4.mResolvedDimensionRatio, 8);
            }
        } else {
            constraintWidget4 = this;
        }
        if (constraintWidget4.mCenter.isConnected()) {
            linearSystem.addCenterPoint(constraintWidget4, constraintWidget4.mCenter.getTarget().getOwner(), (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f), constraintWidget4.mCenter.getMargin());
        }
        constraintWidget4.resolvedHorizontal = false;
        constraintWidget4.resolvedVertical = false;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        if (constraintAnchor.getOwner() == this) {
            connect(constraintAnchor.getType(), constraintAnchor2.getOwner(), constraintAnchor2.getType(), i);
        }
    }

    public void connectCircularConstraint(ConstraintWidget constraintWidget, float f, int i) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        immediateConnect(type, constraintWidget, type, i, 0);
        this.mCircleConstraintAngle = f;
    }

    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = constraintWidget.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = constraintWidget.mIsWidthWrapContent;
        this.mIsHeightWrapContent = constraintWidget.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.hasBaseline = constraintWidget.hasBaseline;
        this.inPlaceholder = constraintWidget.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : map.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.mX = constraintWidget.mX;
        this.mY = constraintWidget.mY;
        this.mOffsetX = constraintWidget.mOffsetX;
        this.mOffsetY = constraintWidget.mOffsetY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mContainerItemSkip = constraintWidget.mContainerItemSkip;
        this.mVisibility = constraintWidget.mVisibility;
        this.mAnimated = constraintWidget.mAnimated;
        this.mDebugName = constraintWidget.mDebugName;
        this.mType = constraintWidget.mType;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        float[] fArr = this.mWeight;
        float[] fArr2 = constraintWidget.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidget.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = constraintWidget.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : map.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget3 != null ? map.get(constraintWidget3) : null;
    }

    public void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public void ensureMeasureRequested() {
        this.mMeasureRequested = true;
    }

    public void ensureWidgetRuns() {
        if (this.horizontalRun == null) {
            this.horizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.verticalRun == null) {
            this.verticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public float getBiasPercent(int i) {
        if (i == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (i == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public DimensionBehaviour getDimensionBehaviour(int i) {
        if (i == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (i == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public boolean getHasBaseline() {
        return this.hasBaseline;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget = this;
        ConstraintWidget constraintWidget2 = null;
        while (constraintWidget2 == null && constraintWidget != null) {
            ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor target = anchor == null ? null : anchor.getTarget();
            ConstraintWidget owner = target == null ? null : target.getOwner();
            if (owner == getParent()) {
                return constraintWidget;
            }
            ConstraintAnchor target2 = owner == null ? null : owner.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            if (target2 == null || target2.getOwner() == constraintWidget) {
                constraintWidget = owner;
            } else {
                constraintWidget2 = constraintWidget;
            }
        }
        return constraintWidget2;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public int getHorizontalMargin() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        int i = constraintAnchor != null ? 0 + constraintAnchor.mMargin : 0;
        ConstraintAnchor constraintAnchor2 = this.mRight;
        return constraintAnchor2 != null ? i + constraintAnchor2.mMargin : i;
    }

    public int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public int getLeft() {
        return getX();
    }

    public int getLength(int i) {
        if (i == 0) {
            return getWidth();
        }
        if (i == 1) {
            return getHeight();
        }
        return 0;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public ConstraintWidget getNextChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public int getOptimizerWrapHeight() {
        int iMax;
        int i = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            iMax = Math.max(this.mMatchConstraintMinHeight, i);
        } else {
            iMax = this.mMatchConstraintMinHeight;
            if (iMax > 0) {
                this.mHeight = iMax;
            } else {
                iMax = 0;
            }
        }
        int i2 = this.mMatchConstraintMaxHeight;
        return (i2 <= 0 || i2 >= iMax) ? iMax : i2;
    }

    public int getOptimizerWrapWidth() {
        int iMax;
        int i = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            iMax = Math.max(this.mMatchConstraintMinWidth, i);
        } else {
            iMax = this.mMatchConstraintMinWidth;
            if (iMax > 0) {
                this.mWidth = iMax;
            } else {
                iMax = 0;
            }
        }
        int i2 = this.mMatchConstraintMaxWidth;
        return (i2 <= 0 || i2 >= iMax) ? iMax : i2;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public ConstraintWidget getPreviousChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mTop).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public WidgetRun getRun(int i) {
        if (i == 0) {
            return this.horizontalRun;
        }
        if (i == 1) {
            return this.verticalRun;
        }
        return null;
    }

    public void getSceneString(StringBuilder sb) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("  ");
        sbM.append(this.stringId);
        sbM.append(":{\n");
        sb.append(sbM.toString());
        sb.append("    actualWidth:" + this.mWidth);
        sb.append("\n");
        sb.append("    actualHeight:" + this.mHeight);
        sb.append("\n");
        sb.append("    actualLeft:" + this.mX);
        sb.append("\n");
        sb.append("    actualTop:" + this.mY);
        sb.append("\n");
        getSceneString(sb, TtmlNode.LEFT, this.mLeft);
        getSceneString(sb, "top", this.mTop);
        getSceneString(sb, TtmlNode.RIGHT, this.mRight);
        getSceneString(sb, "bottom", this.mBottom);
        getSceneString(sb, "baseline", this.mBaseline);
        getSceneString(sb, "centerX", this.mCenterX);
        getSceneString(sb, "centerY", this.mCenterY);
        getSceneString(sb, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        getSceneString(sb, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(sb, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(sb, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(sb, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(sb, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
        serializeAttribute(sb, "    verticalChainStyle", this.mVerticalChainStyle, 0);
        sb.append("  }");
    }

    public int getTop() {
        return getY();
    }

    public String getType() {
        return this.mType;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget = this;
        ConstraintWidget constraintWidget2 = null;
        while (constraintWidget2 == null && constraintWidget != null) {
            ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor target = anchor == null ? null : anchor.getTarget();
            ConstraintWidget owner = target == null ? null : target.getOwner();
            if (owner == getParent()) {
                return constraintWidget;
            }
            ConstraintAnchor target2 = owner == null ? null : owner.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            if (target2 == null || target2.getOwner() == constraintWidget) {
                constraintWidget = owner;
            } else {
                constraintWidget2 = constraintWidget;
            }
        }
        return constraintWidget2;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public int getVerticalMargin() {
        int i = this.mLeft != null ? 0 + this.mTop.mMargin : 0;
        return this.mRight != null ? i + this.mBottom.mMargin : i;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getWrapBehaviorInParent() {
        return this.mWrapBehaviorInParent;
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.mY;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public boolean hasDanglingDimension(int i) {
        if (i == 0) {
            return (this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2;
        }
        return ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0)) + (this.mBaseline.mTarget != null ? 1 : 0) < 2;
    }

    public boolean hasDependencies() {
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            if (this.mAnchors.get(i).hasDependents()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public boolean hasResolvedTargets(int i, int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i == 0) {
            ConstraintAnchor constraintAnchor3 = this.mLeft.mTarget;
            if (constraintAnchor3 != null && constraintAnchor3.hasFinalValue() && (constraintAnchor2 = this.mRight.mTarget) != null && constraintAnchor2.hasFinalValue()) {
                return (this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.getMargin() + this.mLeft.mTarget.getFinalValue()) >= i2;
            }
        } else {
            ConstraintAnchor constraintAnchor4 = this.mTop.mTarget;
            if (constraintAnchor4 != null && constraintAnchor4.hasFinalValue() && (constraintAnchor = this.mBottom.mTarget) != null && constraintAnchor.hasFinalValue()) {
                return (this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.getMargin() + this.mTop.mTarget.getFinalValue()) >= i2;
            }
        }
        return false;
    }

    public void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public boolean isAnimated() {
        return this.mAnimated;
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public boolean isHorizontalSolvingPassDone() {
        return this.horizontalSolvingPass;
    }

    public boolean isInBarrier(int i) {
        return this.mIsInBarrier[i];
    }

    public boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public boolean isResolvedHorizontally() {
        return this.resolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue());
    }

    public boolean isResolvedVertically() {
        return this.resolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue());
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isVerticalSolvingPassDone() {
        return this.verticalSolvingPass;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void markHorizontalSolvingPassDone() {
        this.horizontalSolvingPass = true;
    }

    public void markVerticalSolvingPassDone() {
        this.verticalSolvingPass = true;
    }

    public boolean oppositeDimensionDependsOn(int i) {
        char c = i == 0 ? (char) 1 : (char) 0;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[i];
        DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[c];
        DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
        return dimensionBehaviour == dimensionBehaviour3 && dimensionBehaviour2 == dimensionBehaviour3;
    }

    public boolean oppositeDimensionsTied() {
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
        return dimensionBehaviour == dimensionBehaviour2 && dimensionBehaviourArr[1] == dimensionBehaviour2;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor constraintAnchor) {
        if (getParent() != null && (getParent() instanceof ConstraintWidgetContainer) && ((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            return;
        }
        ConstraintAnchor anchor = getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintAnchor anchor3 = getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor4 = getAnchor(ConstraintAnchor.Type.BOTTOM);
        ConstraintAnchor anchor5 = getAnchor(ConstraintAnchor.Type.CENTER);
        ConstraintAnchor anchor6 = getAnchor(ConstraintAnchor.Type.CENTER_X);
        ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
        if (constraintAnchor == anchor5) {
            if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget() == anchor2.getTarget()) {
                anchor.reset();
                anchor2.reset();
            }
            if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget() == anchor4.getTarget()) {
                anchor3.reset();
                anchor4.reset();
            }
            this.mHorizontalBiasPercent = 0.5f;
            this.mVerticalBiasPercent = 0.5f;
        } else if (constraintAnchor == anchor6) {
            if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget().getOwner() == anchor2.getTarget().getOwner()) {
                anchor.reset();
                anchor2.reset();
            }
            this.mHorizontalBiasPercent = 0.5f;
        } else if (constraintAnchor == anchor7) {
            if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget().getOwner() == anchor4.getTarget().getOwner()) {
                anchor3.reset();
                anchor4.reset();
            }
            this.mVerticalBiasPercent = 0.5f;
        } else if (constraintAnchor == anchor || constraintAnchor == anchor2) {
            if (anchor.isConnected() && anchor.getTarget() == anchor2.getTarget()) {
                anchor5.reset();
            }
        } else if ((constraintAnchor == anchor3 || constraintAnchor == anchor4) && anchor3.isConnected() && anchor3.getTarget() == anchor4.getTarget()) {
            anchor5.reset();
        }
        constraintAnchor.reset();
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent != null && (parent instanceof ConstraintWidgetContainer) && ((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            return;
        }
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            this.mAnchors.get(i).reset();
        }
    }

    public void resetFinalResolution() {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            this.mAnchors.get(i).resetFinalResolution();
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void resetSolvingPassFlag() {
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
    }

    public StringBuilder serialize(StringBuilder sb) {
        sb.append("{\n");
        serializeAnchor(sb, TtmlNode.LEFT, this.mLeft);
        serializeAnchor(sb, "top", this.mTop);
        serializeAnchor(sb, TtmlNode.RIGHT, this.mRight);
        serializeAnchor(sb, "bottom", this.mBottom);
        serializeAnchor(sb, "baseline", this.mBaseline);
        serializeAnchor(sb, "centerX", this.mCenterX);
        serializeAnchor(sb, "centerY", this.mCenterY);
        serializeCircle(sb, this.mCenter, this.mCircleConstraintAngle);
        serializeSize(sb, "width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        serializeSize(sb, "height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(sb, "dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(sb, "horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(sb, "verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        sb.append("}\n");
        return sb;
    }

    public void setAnimated(boolean z) {
        this.mAnimated = z;
    }

    public void setBaselineDistance(int i) {
        this.mBaselineDistance = i;
        this.hasBaseline = i > 0;
    }

    public void setCompanionWidget(Object obj) {
        this.mCompanionWidget = obj;
    }

    public void setContainerItemSkip(int i) {
        if (i >= 0) {
            this.mContainerItemSkip = i;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public void setDebugName(String str) {
        this.mDebugName = str;
    }

    public void setDebugSolverName(LinearSystem linearSystem, String str) {
        this.mDebugName = str;
        SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mLeft);
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mTop);
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(this.mRight);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(this.mBottom);
        solverVariableCreateObjectVariable.setName(str + ".left");
        solverVariableCreateObjectVariable2.setName(str + ".top");
        solverVariableCreateObjectVariable3.setName(str + ".right");
        solverVariableCreateObjectVariable4.setName(str + ".bottom");
        linearSystem.createObjectVariable(this.mBaseline).setName(str + ".baseline");
    }

    public void setDimension(int i, int i2) {
        this.mWidth = i;
        int i3 = this.mMinWidth;
        if (i < i3) {
            this.mWidth = i3;
        }
        this.mHeight = i2;
        int i4 = this.mMinHeight;
        if (i2 < i4) {
            this.mHeight = i4;
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0084 A[PHI: r0
  0x0084: PHI (r0v2 int) = (r0v1 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int) binds: [B:45:0x0084, B:35:0x007d, B:23:0x004f, B:25:0x0055, B:27:0x0061, B:29:0x0065] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0084 -> B:39:0x0085). Please report as a decompilation issue!!! */
    public void setDimensionRatio(String str) {
        float fAbs;
        int i = 0;
        if (str == null || str.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int i2 = -1;
        int length = str.length();
        int iIndexOf = str.indexOf(44);
        int i3 = 0;
        if (iIndexOf > 0 && iIndexOf < length - 1) {
            String strSubstring = str.substring(0, iIndexOf);
            if (strSubstring.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                i2 = 0;
            } else if (strSubstring.equalsIgnoreCase("H")) {
                i2 = 1;
            }
            i3 = iIndexOf + 1;
        }
        int iIndexOf2 = str.indexOf(58);
        try {
            if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                String strSubstring2 = str.substring(i3);
                if (strSubstring2.length() > 0) {
                    fAbs = Float.parseFloat(strSubstring2);
                } else {
                    fAbs = 0.0f;
                }
            } else {
                String strSubstring3 = str.substring(i3, iIndexOf2);
                String strSubstring4 = str.substring(iIndexOf2 + 1);
                if (strSubstring3.length() <= 0 || strSubstring4.length() <= 0) {
                    fAbs = 0.0f;
                } else {
                    float f = Float.parseFloat(strSubstring3);
                    float f2 = Float.parseFloat(strSubstring4);
                    if (f <= 0.0f || f2 <= 0.0f) {
                        fAbs = 0.0f;
                    } else {
                        fAbs = i2 == 1 ? Math.abs(f2 / f) : Math.abs(f / f2);
                    }
                }
            }
        } catch (NumberFormatException unused) {
        }
        i = (fAbs > i ? 1 : (fAbs == i ? 0 : -1));
        if (i > 0) {
            this.mDimensionRatio = fAbs;
            this.mDimensionRatioSide = i2;
        }
    }

    public void setFinalBaseline(int i) {
        if (this.hasBaseline) {
            int i2 = i - this.mBaselineDistance;
            int i3 = this.mHeight + i2;
            this.mY = i2;
            this.mTop.setFinalValue(i2);
            this.mBottom.setFinalValue(i3);
            this.mBaseline.setFinalValue(i);
            this.resolvedVertical = true;
        }
    }

    public void setFinalFrame(int i, int i2, int i3, int i4, int i5, int i6) {
        setFrame(i, i2, i3, i4);
        setBaselineDistance(i5);
        if (i6 == 0) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = false;
        } else if (i6 == 1) {
            this.resolvedHorizontal = false;
            this.resolvedVertical = true;
        } else if (i6 == 2) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = true;
        } else {
            this.resolvedHorizontal = false;
            this.resolvedVertical = false;
        }
    }

    public void setFinalHorizontal(int i, int i2) {
        if (this.resolvedHorizontal) {
            return;
        }
        this.mLeft.setFinalValue(i);
        this.mRight.setFinalValue(i2);
        this.mX = i;
        this.mWidth = i2 - i;
        this.resolvedHorizontal = true;
    }

    public void setFinalLeft(int i) {
        this.mLeft.setFinalValue(i);
        this.mX = i;
    }

    public void setFinalTop(int i) {
        this.mTop.setFinalValue(i);
        this.mY = i;
    }

    public void setFinalVertical(int i, int i2) {
        if (this.resolvedVertical) {
            return;
        }
        this.mTop.setFinalValue(i);
        this.mBottom.setFinalValue(i2);
        this.mY = i;
        this.mHeight = i2 - i;
        if (this.hasBaseline) {
            this.mBaseline.setFinalValue(i + this.mBaselineDistance);
        }
        this.resolvedVertical = true;
    }

    public void setFrame(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i3 - i;
        int i8 = i4 - i2;
        this.mX = i;
        this.mY = i2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i7 < (i6 = this.mWidth)) {
            i7 = i6;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i8 < (i5 = this.mHeight)) {
            i8 = i5;
        }
        this.mWidth = i7;
        this.mHeight = i8;
        int i9 = this.mMinHeight;
        if (i8 < i9) {
            this.mHeight = i9;
        }
        int i10 = this.mMinWidth;
        if (i7 < i10) {
            this.mWidth = i10;
        }
        int i11 = this.mMatchConstraintMaxWidth;
        if (i11 > 0 && dimensionBehaviourArr[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, i11);
        }
        int i12 = this.mMatchConstraintMaxHeight;
        if (i12 > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, i12);
        }
        int i13 = this.mWidth;
        if (i7 != i13) {
            this.mWidthOverride = i13;
        }
        int i14 = this.mHeight;
        if (i8 != i14) {
            this.mHeightOverride = i14;
        }
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int i) {
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()];
        if (i2 == 1) {
            this.mLeft.mGoneMargin = i;
            return;
        }
        if (i2 == 2) {
            this.mTop.mGoneMargin = i;
            return;
        }
        if (i2 == 3) {
            this.mRight.mGoneMargin = i;
        } else if (i2 == 4) {
            this.mBottom.mGoneMargin = i;
        } else {
            if (i2 != 5) {
                return;
            }
            this.mBaseline.mGoneMargin = i;
        }
    }

    public void setHasBaseline(boolean z) {
        this.hasBaseline = z;
    }

    public void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public void setHeightWrapContent(boolean z) {
        this.mIsHeightWrapContent = z;
    }

    public void setHorizontalBiasPercent(float f) {
        this.mHorizontalBiasPercent = f;
    }

    public void setHorizontalChainStyle(int i) {
        this.mHorizontalChainStyle = i;
    }

    public void setHorizontalDimension(int i, int i2) {
        this.mX = i;
        int i3 = i2 - i;
        this.mWidth = i3;
        int i4 = this.mMinWidth;
        if (i3 < i4) {
            this.mWidth = i4;
        }
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public void setHorizontalMatchStyle(int i, int i2, int i3, float f) {
        this.mMatchConstraintDefaultWidth = i;
        this.mMatchConstraintMinWidth = i2;
        if (i3 == Integer.MAX_VALUE) {
            i3 = 0;
        }
        this.mMatchConstraintMaxWidth = i3;
        this.mMatchConstraintPercentWidth = f;
        if (f <= 0.0f || f >= 1.0f || i != 0) {
            return;
        }
        this.mMatchConstraintDefaultWidth = 2;
    }

    public void setHorizontalWeight(float f) {
        this.mWeight[0] = f;
    }

    public final void setInBarrier(int i, boolean z) {
        this.mIsInBarrier[i] = z;
    }

    public void setInPlaceholder(boolean z) {
        this.inPlaceholder = z;
    }

    public void setInVirtualLayout(boolean z) {
        this.mInVirtualLayout = z;
    }

    public void setLastMeasureSpec(int i, int i2) {
        this.mLastHorizontalMeasureSpec = i;
        this.mLastVerticalMeasureSpec = i2;
        setMeasureRequested(false);
    }

    public void setLength(int i, int i2) {
        if (i2 == 0) {
            setWidth(i);
        } else if (i2 == 1) {
            setHeight(i);
        }
    }

    public void setMaxHeight(int i) {
        this.mMaxDimension[1] = i;
    }

    public void setMaxWidth(int i) {
        this.mMaxDimension[0] = i;
    }

    public void setMeasureRequested(boolean z) {
        this.mMeasureRequested = z;
    }

    public void setMinHeight(int i) {
        if (i < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = i;
        }
    }

    public void setMinWidth(int i) {
        if (i < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = i;
        }
    }

    public void setOffset(int i, int i2) {
        this.mOffsetX = i;
        this.mOffsetY = i2;
    }

    public void setOrigin(int i, int i2) {
        this.mX = i;
        this.mY = i2;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.mParent = constraintWidget;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public void setVerticalBiasPercent(float f) {
        this.mVerticalBiasPercent = f;
    }

    public void setVerticalChainStyle(int i) {
        this.mVerticalChainStyle = i;
    }

    public void setVerticalDimension(int i, int i2) {
        this.mY = i;
        int i3 = i2 - i;
        this.mHeight = i3;
        int i4 = this.mMinHeight;
        if (i3 < i4) {
            this.mHeight = i4;
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public void setVerticalMatchStyle(int i, int i2, int i3, float f) {
        this.mMatchConstraintDefaultHeight = i;
        this.mMatchConstraintMinHeight = i2;
        if (i3 == Integer.MAX_VALUE) {
            i3 = 0;
        }
        this.mMatchConstraintMaxHeight = i3;
        this.mMatchConstraintPercentHeight = f;
        if (f <= 0.0f || f >= 1.0f || i != 0) {
            return;
        }
        this.mMatchConstraintDefaultHeight = 2;
    }

    public void setVerticalWeight(float f) {
        this.mWeight[1] = f;
    }

    public void setVisibility(int i) {
        this.mVisibility = i;
    }

    public void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public void setWidthWrapContent(boolean z) {
        this.mIsWidthWrapContent = z;
    }

    public void setWrapBehaviorInParent(int i) {
        if (i < 0 || i > 3) {
            return;
        }
        this.mWrapBehaviorInParent = i;
    }

    public void setX(int i) {
        this.mX = i;
    }

    public void setY(int i) {
        this.mY = i;
    }

    public void setupDimensionRatio(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (z3 && !z4) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!z3 && z4) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            int i = this.mMatchConstraintMinWidth;
            if (i > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else {
                if (i != 0 || this.mMatchConstraintMinHeight <= 0) {
                    return;
                }
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mType != null ? Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("type: "), this.mType, " ") : "");
        sb.append(this.mDebugName != null ? Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("id: "), this.mDebugName, " ") : "");
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        return Insets$$ExternalSyntheticOutline0.m(sb, this.mHeight, ")");
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        boolean zIsResolved = z & this.horizontalRun.isResolved();
        boolean zIsResolved2 = z2 & this.verticalRun.isResolved();
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        int i3 = horizontalWidgetRun.start.value;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        int i4 = verticalWidgetRun.start.value;
        int i5 = horizontalWidgetRun.end.value;
        int i6 = verticalWidgetRun.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i3 = 0;
            i6 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (zIsResolved) {
            this.mX = i3;
        }
        if (zIsResolved2) {
            this.mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (zIsResolved) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (zIsResolved2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i9 < (i = this.mHeight)) {
                i9 = i;
            }
            this.mHeight = i9;
            int i11 = this.mMinHeight;
            if (i9 < i11) {
                this.mHeight = i11;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem, boolean z) {
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int objectVariableValue = linearSystem.getObjectVariableValue(this.mLeft);
        int objectVariableValue2 = linearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = linearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = linearSystem.getObjectVariableValue(this.mBottom);
        if (z && (horizontalWidgetRun = this.horizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    objectVariableValue = dependencyNode.value;
                    objectVariableValue3 = dependencyNode2.value;
                }
            }
        }
        if (z && (verticalWidgetRun = this.verticalRun) != null) {
            DependencyNode dependencyNode3 = verticalWidgetRun.start;
            if (dependencyNode3.resolved) {
                DependencyNode dependencyNode4 = verticalWidgetRun.end;
                if (dependencyNode4.resolved) {
                    objectVariableValue2 = dependencyNode3.value;
                    objectVariableValue4 = dependencyNode4.value;
                }
            }
        }
        int i = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue4 = 0;
            objectVariableValue = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
        }
        setFrame(objectVariableValue, objectVariableValue2, objectVariableValue3, objectVariableValue4);
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2) {
        connect(type, constraintWidget, type2, 0);
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i) {
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        boolean z;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER;
        if (type == type5) {
            if (type2 == type5) {
                ConstraintAnchor.Type type6 = ConstraintAnchor.Type.LEFT;
                ConstraintAnchor anchor = getAnchor(type6);
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.RIGHT;
                ConstraintAnchor anchor2 = getAnchor(type7);
                ConstraintAnchor.Type type8 = ConstraintAnchor.Type.TOP;
                ConstraintAnchor anchor3 = getAnchor(type8);
                ConstraintAnchor.Type type9 = ConstraintAnchor.Type.BOTTOM;
                ConstraintAnchor anchor4 = getAnchor(type9);
                boolean z2 = true;
                if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                    connect(type6, constraintWidget, type6, 0);
                    connect(type7, constraintWidget, type7, 0);
                    z = true;
                } else {
                    z = false;
                }
                if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                    connect(type8, constraintWidget, type8, 0);
                    connect(type9, constraintWidget, type9, 0);
                } else {
                    z2 = false;
                }
                if (z && z2) {
                    getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                    return;
                }
                if (z) {
                    ConstraintAnchor.Type type10 = ConstraintAnchor.Type.CENTER_X;
                    getAnchor(type10).connect(constraintWidget.getAnchor(type10), 0);
                    return;
                } else {
                    if (z2) {
                        ConstraintAnchor.Type type11 = ConstraintAnchor.Type.CENTER_Y;
                        getAnchor(type11).connect(constraintWidget.getAnchor(type11), 0);
                        return;
                    }
                    return;
                }
            }
            ConstraintAnchor.Type type12 = ConstraintAnchor.Type.LEFT;
            if (type2 != type12 && type2 != ConstraintAnchor.Type.RIGHT) {
                ConstraintAnchor.Type type13 = ConstraintAnchor.Type.TOP;
                if (type2 == type13 || type2 == ConstraintAnchor.Type.BOTTOM) {
                    connect(type13, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type2, 0);
                    getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                }
                return;
            }
            connect(type12, constraintWidget, type2, 0);
            connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type2, 0);
            getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor.Type type14 = ConstraintAnchor.Type.CENTER_X;
        if (type == type14 && (type2 == (type4 = ConstraintAnchor.Type.LEFT) || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor anchor5 = getAnchor(type4);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.RIGHT);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type14).connect(anchor6, 0);
            return;
        }
        ConstraintAnchor.Type type15 = ConstraintAnchor.Type.CENTER_Y;
        if (type == type15 && (type2 == (type3 = ConstraintAnchor.Type.TOP) || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type3).connect(anchor8, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(anchor8, 0);
            getAnchor(type15).connect(anchor8, 0);
            return;
        }
        if (type == type14 && type2 == type14) {
            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.LEFT;
            getAnchor(type16).connect(constraintWidget.getAnchor(type16), 0);
            ConstraintAnchor.Type type17 = ConstraintAnchor.Type.RIGHT;
            getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
            getAnchor(type14).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        if (type == type15 && type2 == type15) {
            ConstraintAnchor.Type type18 = ConstraintAnchor.Type.TOP;
            getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
            ConstraintAnchor.Type type19 = ConstraintAnchor.Type.BOTTOM;
            getAnchor(type19).connect(constraintWidget.getAnchor(type19), 0);
            getAnchor(type15).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor anchor9 = getAnchor(type);
        ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
        if (anchor9.isValidConnection(anchor10)) {
            ConstraintAnchor.Type type20 = ConstraintAnchor.Type.BASELINE;
            if (type == type20) {
                ConstraintAnchor anchor11 = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor anchor12 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                if (anchor11 != null) {
                    anchor11.reset();
                }
                if (anchor12 != null) {
                    anchor12.reset();
                }
            } else if (type != ConstraintAnchor.Type.TOP && type != ConstraintAnchor.Type.BOTTOM) {
                if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                    ConstraintAnchor anchor13 = getAnchor(type5);
                    if (anchor13.getTarget() != anchor10) {
                        anchor13.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(type).getOpposite();
                    ConstraintAnchor anchor14 = getAnchor(type14);
                    if (anchor14.isConnected()) {
                        opposite.reset();
                        anchor14.reset();
                    }
                }
            } else {
                ConstraintAnchor anchor15 = getAnchor(type20);
                if (anchor15 != null) {
                    anchor15.reset();
                }
                ConstraintAnchor anchor16 = getAnchor(type5);
                if (anchor16.getTarget() != anchor10) {
                    anchor16.reset();
                }
                ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                ConstraintAnchor anchor17 = getAnchor(type15);
                if (anchor17.isConnected()) {
                    opposite2.reset();
                    anchor17.reset();
                }
            }
            anchor9.connect(anchor10, i);
        }
    }

    private void serializeAttribute(StringBuilder sb, String str, int i, int i2) {
        if (i == i2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(i);
        sb.append(",\n");
    }

    public void setDimensionRatio(float f, int i) {
        this.mDimensionRatio = f;
        this.mDimensionRatioSide = i;
    }

    public void setFrame(int i, int i2, int i3) {
        if (i3 == 0) {
            setHorizontalDimension(i, i2);
        } else if (i3 == 1) {
            setVerticalDimension(i, i2);
        }
    }

    private void getSceneString(StringBuilder sb, String str, int i, int i2, int i3, int i4, int i5, int i6, float f, float f2) {
        sb.append(str);
        sb.append(" :  {\n");
        serializeAttribute(sb, "      size", i, 0);
        serializeAttribute(sb, "      min", i2, 0);
        serializeAttribute(sb, "      max", i3, Integer.MAX_VALUE);
        serializeAttribute(sb, "      matchMin", i5, 0);
        serializeAttribute(sb, "      matchDef", i6, 0);
        serializeAttribute(sb, "      matchPercent", f, 1.0f);
        sb.append("    },\n");
    }

    private void getSceneString(StringBuilder sb, String str, ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append("    ");
        sb.append(str);
        sb.append(" : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("'");
        if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE || constraintAnchor.mMargin != 0) {
            sb.append(",");
            sb.append(constraintAnchor.mMargin);
            if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE) {
                sb.append(",");
                sb.append(constraintAnchor.mGoneMargin);
                sb.append(",");
            }
        }
        sb.append(" ] ,\n");
    }

    public ConstraintWidget(String str) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
        setDebugName(str);
    }

    public ConstraintWidget(int i, int i2, int i3, int i4) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        addAnchors();
    }

    public ConstraintWidget(String str, int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4);
        setDebugName(str);
    }

    public ConstraintWidget(int i, int i2) {
        this(0, 0, i, i2);
    }

    public ConstraintWidget(String str, int i, int i2) {
        this(i, i2);
        setDebugName(str);
    }
}
