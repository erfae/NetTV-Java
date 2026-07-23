package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    /* JADX WARN: Code duplicated, block: B:114:0x01bb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:115:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:117:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:211:0x03b3  */
    /* JADX WARN: Code duplicated, block: B:226:0x0400  */
    /* JADX WARN: Code duplicated, block: B:38:0x0082 A[PHI: r19 r20 r21
  0x0082: PHI (r19v4 boolean) = (r19v2 boolean), (r19v8 boolean) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0082: PHI (r20v4 boolean) = (r20v2 boolean), (r20v8 boolean) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0082: PHI (r21v1 int) = (r21v0 int), (r21v21 int) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:39:0x0084 A[PHI: r19 r20 r21
  0x0084: PHI (r19v6 boolean) = (r19v2 boolean), (r19v8 boolean) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0084: PHI (r20v6 boolean) = (r20v2 boolean), (r20v8 boolean) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0084: PHI (r21v20 int) = (r21v0 int), (r21v21 int) binds: [B:37:0x0080, B:27:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r43v0, types: [androidx.constraintlayout.core.LinearSystem] */
    /* JADX WARN: Type inference failed for: r6v33, types: [androidx.constraintlayout.core.SolverVariable] */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* JADX WARN: Type inference failed for: r9v4, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        Object obj;
        int i5;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        int i6;
        ConstraintWidget constraintWidget4;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor;
        SolverVariable solverVariable2;
        ConstraintWidget constraintWidget5;
        ConstraintAnchor constraintAnchor2;
        SolverVariable solverVariable3;
        ConstraintWidget constraintWidget6;
        int size;
        ConstraintAnchor constraintAnchor3;
        int i7;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        if (i == 0) {
            i2 = constraintWidgetContainer2.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer2.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mVerticalChainsArray;
            i3 = 2;
        }
        int i8 = 0;
        while (i8 < i2) {
            ChainHead chainHead = chainHeadArr[i8];
            chainHead.define();
            if (arrayList == 0 || arrayList.contains(chainHead.mFirst)) {
                ConstraintWidget constraintWidget7 = chainHead.mFirst;
                ConstraintWidget constraintWidget8 = chainHead.mLast;
                ConstraintWidget constraintWidget9 = chainHead.mFirstVisibleWidget;
                ConstraintWidget constraintWidget10 = chainHead.mLastVisibleWidget;
                ConstraintWidget constraintWidget11 = chainHead.mHead;
                float f = chainHead.mTotalWeight;
                boolean z4 = constraintWidgetContainer2.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (i == 0) {
                    int i9 = constraintWidget11.mHorizontalChainStyle;
                    z = i9 == 0;
                    i4 = i8;
                    z2 = i9 == 1;
                    if (i9 == 2) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                } else {
                    i4 = i8;
                    int i10 = constraintWidget11.mVerticalChainStyle;
                    z = i10 == 0;
                    z2 = i10 == 1;
                    if (i10 == 2) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                }
                ?? r9 = constraintWidget7;
                boolean z5 = false;
                while (true) {
                    obj = null;
                    if (z5) {
                        break;
                    }
                    ConstraintAnchor constraintAnchor4 = r9.mListAnchors[i3];
                    int i11 = z3 ? 1 : 4;
                    int margin = constraintAnchor4.getMargin();
                    float f2 = f;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = r9.mListDimensionBehaviors[i];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    boolean z6 = dimensionBehaviour == dimensionBehaviour2 && r9.mResolvedMatchConstraintDefault[i] == 0;
                    ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
                    if (constraintAnchor5 != null && r9 != constraintWidget7) {
                        margin = constraintAnchor5.getMargin() + margin;
                    }
                    int i12 = margin;
                    if (z3 && r9 != constraintWidget7 && r9 != constraintWidget9) {
                        i11 = 8;
                    }
                    ConstraintAnchor constraintAnchor6 = constraintAnchor4.mTarget;
                    if (constraintAnchor6 != null) {
                        if (r9 == constraintWidget9) {
                            linearSystem.addGreaterThan(constraintAnchor4.mSolverVariable, constraintAnchor6.mSolverVariable, i12, 6);
                        } else {
                            linearSystem.addGreaterThan(constraintAnchor4.mSolverVariable, constraintAnchor6.mSolverVariable, i12, 8);
                        }
                        if (z6 && !z3) {
                            i11 = 5;
                        }
                        linearSystem.addEquality(constraintAnchor4.mSolverVariable, constraintAnchor4.mTarget.mSolverVariable, i12, (r9 == constraintWidget9 && z3 && r9.isInBarrier(i)) ? 5 : i11);
                    } else {
                        chainHead = chainHead;
                        constraintWidget7 = constraintWidget7;
                    }
                    if (z4) {
                        if (r9.getVisibility() == 8 || r9.mListDimensionBehaviors[i] != dimensionBehaviour2) {
                            i7 = 0;
                        } else {
                            ConstraintAnchor[] constraintAnchorArr = r9.mListAnchors;
                            i7 = 0;
                            linearSystem.addGreaterThan(constraintAnchorArr[i3 + 1].mSolverVariable, constraintAnchorArr[i3].mSolverVariable, 0, 5);
                        }
                        linearSystem.addGreaterThan(r9.mListAnchors[i3].mSolverVariable, constraintWidgetContainer2.mListAnchors[i3].mSolverVariable, i7, 8);
                    }
                    ConstraintAnchor constraintAnchor7 = r9.mListAnchors[i3 + 1].mTarget;
                    if (constraintAnchor7 != null) {
                        ConstraintWidget constraintWidget12 = constraintAnchor7.mOwner;
                        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget12.mListAnchors;
                        if (constraintAnchorArr2[i3].mTarget != null && constraintAnchorArr2[i3].mTarget.mOwner == r9) {
                            obj = constraintWidget12;
                        }
                    }
                    if (obj != null) {
                        r9 = obj;
                    } else {
                        z5 = true;
                    }
                    constraintWidget11 = constraintWidget11;
                    f = f2;
                    i2 = i2;
                    constraintWidget7 = constraintWidget7;
                    chainHead = chainHead;
                    r9 = r9;
                }
                ChainHead chainHead2 = chainHead;
                ConstraintWidget constraintWidget13 = constraintWidget11;
                float f3 = f;
                ConstraintWidget constraintWidget14 = constraintWidget7;
                i5 = i2;
                if (constraintWidget10 != null) {
                    int i13 = i3 + 1;
                    if (constraintWidget8.mListAnchors[i13].mTarget != null) {
                        ConstraintAnchor constraintAnchor8 = constraintWidget10.mListAnchors[i13];
                        if ((constraintWidget10.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget10.mResolvedMatchConstraintDefault[i] == 0) && !z3) {
                            ConstraintAnchor constraintAnchor9 = constraintAnchor8.mTarget;
                            if (constraintAnchor9.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor8.mSolverVariable, constraintAnchor9.mSolverVariable, -constraintAnchor8.getMargin(), 5);
                            } else if (z3) {
                                constraintAnchor3 = constraintAnchor8.mTarget;
                                if (constraintAnchor3.mOwner == constraintWidgetContainer2) {
                                    linearSystem.addEquality(constraintAnchor8.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor8.getMargin(), 4);
                                }
                            }
                        } else if (z3) {
                            constraintAnchor3 = constraintAnchor8.mTarget;
                            if (constraintAnchor3.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor8.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor8.getMargin(), 4);
                            }
                        }
                        linearSystem.addLowerThan(constraintAnchor8.mSolverVariable, constraintWidget8.mListAnchors[i13].mTarget.mSolverVariable, -constraintAnchor8.getMargin(), 6);
                    }
                }
                if (z4) {
                    int i14 = i3 + 1;
                    SolverVariable solverVariable4 = constraintWidgetContainer2.mListAnchors[i14].mSolverVariable;
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget8.mListAnchors;
                    linearSystem.addGreaterThan(solverVariable4, constraintAnchorArr3[i14].mSolverVariable, constraintAnchorArr3[i14].getMargin(), 8);
                }
                ArrayList<ConstraintWidget> arrayList2 = chainHead2.mWeightedMatchConstraintsWidgets;
                if (arrayList2 != null && (size = arrayList2.size()) > 1) {
                    if (chainHead2.mHasUndefinedWeights && !chainHead2.mHasComplexMatchWeights) {
                        f3 = chainHead2.mWidgetsMatchCount;
                    }
                    float f4 = 0.0f;
                    ConstraintWidget constraintWidget15 = null;
                    int i15 = 0;
                    float f5 = 0.0f;
                    while (i15 < size) {
                        ConstraintWidget constraintWidget16 = arrayList2.get(i15);
                        float f6 = constraintWidget16.mWeight[i];
                        if (f6 < f4) {
                            if (chainHead2.mHasComplexMatchWeights) {
                                ConstraintAnchor[] constraintAnchorArr4 = constraintWidget16.mListAnchors;
                                linearSystem.addEquality(constraintAnchorArr4[i3 + 1].mSolverVariable, constraintAnchorArr4[i3].mSolverVariable, 0, 4);
                            } else {
                                f6 = 1.0f;
                            }
                            arrayList2 = arrayList2;
                            i15++;
                            arrayList2 = arrayList2;
                            f4 = 0.0f;
                        }
                        if (f6 == 0.0f) {
                            ConstraintAnchor[] constraintAnchorArr5 = constraintWidget16.mListAnchors;
                            linearSystem.addEquality(constraintAnchorArr5[i3 + 1].mSolverVariable, constraintAnchorArr5[i3].mSolverVariable, 0, 8);
                            arrayList2 = arrayList2;
                        } else {
                            if (constraintWidget15 != null) {
                                ConstraintAnchor[] constraintAnchorArr6 = constraintWidget15.mListAnchors;
                                SolverVariable solverVariable5 = constraintAnchorArr6[i3].mSolverVariable;
                                int i16 = i3 + 1;
                                SolverVariable solverVariable6 = constraintAnchorArr6[i16].mSolverVariable;
                                ConstraintAnchor[] constraintAnchorArr7 = constraintWidget16.mListAnchors;
                                SolverVariable solverVariable7 = constraintAnchorArr7[i3].mSolverVariable;
                                SolverVariable solverVariable8 = constraintAnchorArr7[i16].mSolverVariable;
                                ArrayRow arrayRowCreateRow = linearSystem.createRow();
                                arrayRowCreateRow.createRowEqualMatchDimensions(f5, f3, f6, solverVariable5, solverVariable6, solverVariable7, solverVariable8);
                                linearSystem.addConstraint(arrayRowCreateRow);
                            }
                            constraintWidget15 = constraintWidget16;
                            f5 = f6;
                        }
                        i15++;
                        arrayList2 = arrayList2;
                        f4 = 0.0f;
                    }
                }
                if (constraintWidget9 == null || !(constraintWidget9 == constraintWidget10 || z3)) {
                    constraintWidget = constraintWidget10;
                    constraintWidget2 = constraintWidget9;
                    constraintWidget3 = constraintWidget8;
                    i6 = i4;
                    ConstraintWidget constraintWidget17 = constraintWidget14;
                    if (z && constraintWidget2 != null) {
                        int i17 = chainHead2.mWidgetsMatchCount;
                        boolean z7 = i17 > 0 && chainHead2.mWidgetsCount == i17;
                        ConstraintWidget constraintWidget18 = constraintWidget2;
                        ConstraintWidget constraintWidget19 = constraintWidget18;
                        while (constraintWidget19 != null) {
                            ConstraintWidget constraintWidget20 = constraintWidget19.mNextChainWidget[i];
                            while (constraintWidget20 != null && constraintWidget20.getVisibility() == 8) {
                                constraintWidget20 = constraintWidget20.mNextChainWidget[i];
                            }
                            if (constraintWidget20 != null || constraintWidget19 == constraintWidget) {
                                ConstraintAnchor constraintAnchor10 = constraintWidget19.mListAnchors[i3];
                                SolverVariable solverVariable9 = constraintAnchor10.mSolverVariable;
                                ConstraintAnchor constraintAnchor11 = constraintAnchor10.mTarget;
                                SolverVariable solverVariable10 = constraintAnchor11 != null ? constraintAnchor11.mSolverVariable : null;
                                if (constraintWidget18 != constraintWidget19) {
                                    solverVariable10 = constraintWidget18.mListAnchors[i3 + 1].mSolverVariable;
                                } else if (constraintWidget19 == constraintWidget2) {
                                    ConstraintAnchor[] constraintAnchorArr8 = constraintWidget17.mListAnchors;
                                    solverVariable10 = constraintAnchorArr8[i3].mTarget != null ? constraintAnchorArr8[i3].mTarget.mSolverVariable : null;
                                }
                                int margin2 = constraintAnchor10.getMargin();
                                int i18 = i3 + 1;
                                int margin3 = constraintWidget19.mListAnchors[i18].getMargin();
                                if (constraintWidget20 != null) {
                                    constraintAnchor2 = constraintWidget20.mListAnchors[i3];
                                    constraintWidget5 = constraintWidget17;
                                    solverVariable3 = constraintAnchor2.mSolverVariable;
                                } else {
                                    constraintWidget5 = constraintWidget17;
                                    constraintAnchor2 = constraintWidget3.mListAnchors[i18].mTarget;
                                    if (constraintAnchor2 != null) {
                                        solverVariable3 = constraintAnchor2.mSolverVariable;
                                    } else {
                                        solverVariable3 = null;
                                    }
                                    SolverVariable solverVariable11 = constraintWidget19.mListAnchors[i18].mSolverVariable;
                                    if (constraintAnchor2 != null) {
                                        margin3 += constraintAnchor2.getMargin();
                                    }
                                    int margin4 = constraintWidget18.mListAnchors[i18].getMargin() + margin2;
                                    if (solverVariable9 != null || solverVariable10 == null || solverVariable3 == null || solverVariable11 == null) {
                                        constraintWidget6 = constraintWidget5;
                                    } else {
                                        if (constraintWidget19 == constraintWidget2) {
                                            margin4 = constraintWidget2.mListAnchors[i3].getMargin();
                                        }
                                        constraintWidget6 = constraintWidget5;
                                        linearSystem.addCentering(solverVariable9, solverVariable10, margin4, 0.5f, solverVariable3, solverVariable11, constraintWidget19 == constraintWidget ? constraintWidget.mListAnchors[i18].getMargin() : margin3, z7 ? 8 : 5);
                                    }
                                }
                                SolverVariable solverVariable12 = constraintWidget19.mListAnchors[i18].mSolverVariable;
                                if (constraintAnchor2 != null) {
                                    margin3 += constraintAnchor2.getMargin();
                                }
                                int margin5 = constraintWidget18.mListAnchors[i18].getMargin() + margin2;
                                if (solverVariable9 != null) {
                                    constraintWidget6 = constraintWidget5;
                                } else {
                                    constraintWidget6 = constraintWidget5;
                                }
                            } else {
                                constraintWidget6 = constraintWidget17;
                            }
                            constraintWidget18 = constraintWidget19.getVisibility() != 8 ? constraintWidget19 : constraintWidget18;
                            constraintWidget19 = constraintWidget20;
                            constraintWidget17 = constraintWidget6;
                        }
                    } else if (z2 && constraintWidget2 != null) {
                        int i19 = chainHead2.mWidgetsMatchCount;
                        boolean z8 = i19 > 0 && chainHead2.mWidgetsCount == i19;
                        ConstraintWidget constraintWidget21 = constraintWidget2;
                        ConstraintWidget constraintWidget22 = constraintWidget21;
                        while (constraintWidget21 != null) {
                            ConstraintWidget constraintWidget23 = constraintWidget21.mNextChainWidget[i];
                            while (constraintWidget23 != null && constraintWidget23.getVisibility() == 8) {
                                constraintWidget23 = constraintWidget23.mNextChainWidget[i];
                            }
                            if (constraintWidget21 == constraintWidget2 || constraintWidget21 == constraintWidget || constraintWidget23 == null) {
                                constraintWidget22 = constraintWidget22;
                                constraintWidget4 = constraintWidget23;
                            } else {
                                ConstraintWidget constraintWidget24 = constraintWidget23 == constraintWidget ? null : constraintWidget23;
                                ConstraintAnchor constraintAnchor12 = constraintWidget21.mListAnchors[i3];
                                SolverVariable solverVariable13 = constraintAnchor12.mSolverVariable;
                                int i20 = i3 + 1;
                                SolverVariable solverVariable14 = constraintWidget22.mListAnchors[i20].mSolverVariable;
                                int margin6 = constraintAnchor12.getMargin();
                                int margin7 = constraintWidget21.mListAnchors[i20].getMargin();
                                if (constraintWidget24 != null) {
                                    constraintAnchor = constraintWidget24.mListAnchors[i3];
                                    solverVariable2 = constraintAnchor.mSolverVariable;
                                    ConstraintAnchor constraintAnchor13 = constraintAnchor.mTarget;
                                    solverVariable = constraintAnchor13 != null ? constraintAnchor13.mSolverVariable : null;
                                } else {
                                    ConstraintAnchor constraintAnchor14 = constraintWidget.mListAnchors[i3];
                                    SolverVariable solverVariable15 = constraintAnchor14 != null ? constraintAnchor14.mSolverVariable : null;
                                    solverVariable = constraintWidget21.mListAnchors[i20].mSolverVariable;
                                    constraintAnchor = constraintAnchor14;
                                    solverVariable2 = solverVariable15;
                                }
                                int margin8 = constraintAnchor != null ? constraintAnchor.getMargin() + margin7 : margin7;
                                int margin9 = constraintWidget22.mListAnchors[i20].getMargin() + margin6;
                                int i21 = z8 ? 8 : 4;
                                if (solverVariable13 != null && solverVariable14 != null && solverVariable2 != null && solverVariable != null) {
                                    linearSystem.addCentering(solverVariable13, solverVariable14, margin9, 0.5f, solverVariable2, solverVariable, margin8, i21);
                                }
                                constraintWidget4 = constraintWidget24;
                            }
                            constraintWidget22 = constraintWidget21.getVisibility() != 8 ? constraintWidget21 : constraintWidget22;
                            constraintWidget21 = constraintWidget4;
                        }
                        ConstraintAnchor constraintAnchor15 = constraintWidget2.mListAnchors[i3];
                        ConstraintAnchor constraintAnchor16 = constraintWidget17.mListAnchors[i3].mTarget;
                        int i22 = i3 + 1;
                        ConstraintAnchor constraintAnchor17 = constraintWidget.mListAnchors[i22];
                        ConstraintAnchor constraintAnchor18 = constraintWidget3.mListAnchors[i22].mTarget;
                        if (constraintAnchor16 == null) {
                            constraintAnchor17 = constraintAnchor17;
                        } else if (constraintWidget2 != constraintWidget) {
                            linearSystem.addEquality(constraintAnchor15.mSolverVariable, constraintAnchor16.mSolverVariable, constraintAnchor15.getMargin(), 5);
                            constraintAnchor17 = constraintAnchor17;
                        } else if (constraintAnchor18 != null) {
                            linearSystem.addCentering(constraintAnchor15.mSolverVariable, constraintAnchor16.mSolverVariable, constraintAnchor15.getMargin(), 0.5f, constraintAnchor17.mSolverVariable, constraintAnchor18.mSolverVariable, constraintAnchor17.getMargin(), 5);
                        }
                        if (constraintAnchor18 != 0 && constraintWidget2 != constraintWidget) {
                            linearSystem.addEquality(constraintAnchor17.mSolverVariable, constraintAnchor18.mSolverVariable, -constraintAnchor17.getMargin(), 5);
                        }
                    }
                } else {
                    ConstraintAnchor constraintAnchor19 = constraintWidget14.mListAnchors[i3];
                    int i23 = i3 + 1;
                    ConstraintAnchor constraintAnchor20 = constraintWidget8.mListAnchors[i23];
                    ConstraintAnchor constraintAnchor21 = constraintAnchor19.mTarget;
                    SolverVariable solverVariable16 = constraintAnchor21 != null ? constraintAnchor21.mSolverVariable : null;
                    ConstraintAnchor constraintAnchor22 = constraintAnchor20.mTarget;
                    SolverVariable solverVariable17 = constraintAnchor22 != null ? constraintAnchor22.mSolverVariable : null;
                    ConstraintAnchor constraintAnchor23 = constraintWidget9.mListAnchors[i3];
                    if (constraintWidget10 != null) {
                        constraintAnchor20 = constraintWidget10.mListAnchors[i23];
                    }
                    if (solverVariable16 == null || solverVariable17 == null) {
                        constraintWidget = constraintWidget10;
                        constraintWidget2 = constraintWidget9;
                        i6 = i4;
                        constraintWidget3 = constraintWidget8;
                    } else {
                        constraintWidget = constraintWidget10;
                        constraintWidget2 = constraintWidget9;
                        i6 = i4;
                        linearSystem.addCentering(constraintAnchor23.mSolverVariable, solverVariable16, constraintAnchor23.getMargin(), i == 0 ? constraintWidget13.mHorizontalBiasPercent : constraintWidget13.mVerticalBiasPercent, solverVariable17, constraintAnchor20.mSolverVariable, constraintAnchor20.getMargin(), 7);
                        constraintWidget3 = constraintWidget8;
                    }
                }
                if ((z || z2) && constraintWidget2 != null && constraintWidget2 != constraintWidget) {
                    ConstraintAnchor[] constraintAnchorArr9 = constraintWidget2.mListAnchors;
                    ConstraintAnchor constraintAnchor24 = constraintAnchorArr9[i3];
                    ConstraintWidget constraintWidget25 = constraintWidget == null ? constraintWidget2 : constraintWidget;
                    int i24 = i3 + 1;
                    ConstraintAnchor constraintAnchor25 = constraintWidget25.mListAnchors[i24];
                    ConstraintAnchor constraintAnchor26 = constraintAnchor24.mTarget;
                    SolverVariable solverVariable18 = constraintAnchor26 != null ? constraintAnchor26.mSolverVariable : null;
                    ConstraintAnchor constraintAnchor27 = constraintAnchor25.mTarget;
                    SolverVariable solverVariable19 = constraintAnchor27 != null ? constraintAnchor27.mSolverVariable : null;
                    if (constraintWidget3 == constraintWidget25) {
                        obj = solverVariable19;
                    } else {
                        ConstraintAnchor constraintAnchor28 = constraintWidget3.mListAnchors[i24].mTarget;
                        if (constraintAnchor28 != null) {
                            solverVariable19 = constraintAnchor28.mSolverVariable;
                            obj = solverVariable19;
                        }
                    }
                    if (constraintWidget2 == constraintWidget25) {
                        constraintAnchor24 = constraintAnchorArr9[i3];
                        constraintAnchor25 = constraintAnchorArr9[i24];
                    }
                    if (solverVariable18 != null && obj != null) {
                        linearSystem.addCentering(constraintAnchor24.mSolverVariable, solverVariable18, constraintAnchor24.getMargin(), 0.5f, obj, constraintAnchor25.mSolverVariable, constraintWidget25.mListAnchors[i24].getMargin(), 5);
                    }
                }
            } else {
                i6 = i8;
                i5 = i2;
            }
            i8 = i6 + 1;
            constraintWidgetContainer2 = constraintWidgetContainer;
            i2 = i5;
        }
    }
}
