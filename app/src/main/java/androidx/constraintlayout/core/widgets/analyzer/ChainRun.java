package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ChainRun extends WidgetRun {
    private int chainStyle;
    public ArrayList<WidgetRun> widgets;

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        this.widgets = new ArrayList<>();
        this.orientation = i;
        build();
    }

    private void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.widget;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        while (true) {
            ConstraintWidget constraintWidget3 = previousChainMember;
            constraintWidget = constraintWidget2;
            constraintWidget2 = constraintWidget3;
            if (constraintWidget2 == null) {
                break;
            } else {
                previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
            }
        }
        this.widget = constraintWidget;
        this.widgets.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.widgets.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        for (WidgetRun widgetRun : this.widgets) {
            int i = this.orientation;
            if (i == 0) {
                widgetRun.widget.horizontalChainRun = this;
            } else if (i == 1) {
                widgetRun.widget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl()) && this.widgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.widgets;
            this.widget = arrayList.get(arrayList.size() - 1).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.widgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.widgets.get(size);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.widgets.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = this.widgets.get(0).widget;
        ConstraintWidget constraintWidget2 = this.widgets.get(size - 1).widget;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target = getTarget(constraintAnchor, 0);
            int margin = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin = firstVisibleWidget.mLeft.getMargin();
            }
            if (target != null) {
                addTarget(this.start, target, margin);
            }
            DependencyNode target2 = getTarget(constraintAnchor2, 0);
            int margin2 = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin2 = lastVisibleWidget.mRight.getMargin();
            }
            if (target2 != null) {
                addTarget(this.end, target2, -margin2);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode target3 = getTarget(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (target3 != null) {
                addTarget(this.start, target3, margin3);
            }
            DependencyNode target4 = getTarget(constraintAnchor4, 1);
            int margin4 = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin4 = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target4 != null) {
                addTarget(this.end, target4, -margin4);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).applyToWidget();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.widgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            wrapDimension = ((long) widgetRun.end.margin) + widgetRun.getWrapDimension() + wrapDimension + ((long) widgetRun.start.margin);
        }
        return wrapDimension;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        int size = this.widgets.size();
        for (int i = 0; i < size; i++) {
            if (!this.widgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        for (WidgetRun widgetRun : this.widgets) {
            sb.append("<");
            sb.append(widgetRun);
            sb.append("> ");
        }
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:113:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:294:0x00f4 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:64:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:65:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:67:0x00ec A[ADDED_TO_REGION] */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float f;
        boolean z;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        float f2;
        if (this.start.resolved && this.end.resolved) {
            ConstraintWidget parent = this.widget.getParent();
            boolean zIsRtl = parent instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer) parent).isRtl() : false;
            int i13 = this.end.value - this.start.value;
            int size = this.widgets.size();
            int i14 = 0;
            while (true) {
                i = -1;
                i2 = 8;
                if (i14 >= size) {
                    i14 = -1;
                    break;
                } else if (this.widgets.get(i14).widget.getVisibility() != 8) {
                    break;
                } else {
                    i14++;
                }
            }
            int i15 = size - 1;
            for (int i16 = i15; i16 >= 0; i16--) {
                if (this.widgets.get(i16).widget.getVisibility() != 8) {
                    i = i16;
                    break;
                }
            }
            int i17 = 0;
            while (true) {
                if (i17 >= 2) {
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    f = 0.0f;
                    break;
                }
                int i18 = 0;
                i4 = 0;
                i5 = 0;
                int i19 = 0;
                f = 0.0f;
                while (i18 < size) {
                    WidgetRun widgetRun = this.widgets.get(i18);
                    if (widgetRun.widget.getVisibility() != i2) {
                        i19++;
                        if (i18 > 0 && i18 >= i14) {
                            i4 += widgetRun.start.margin;
                        }
                        DimensionDependency dimensionDependency = widgetRun.dimension;
                        int i20 = dimensionDependency.value;
                        boolean z2 = widgetRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        if (z2) {
                            int i21 = this.orientation;
                            if (i21 == 0 && !widgetRun.widget.horizontalRun.dimension.resolved) {
                                return;
                            }
                            if (i21 == 1 && !widgetRun.widget.verticalRun.dimension.resolved) {
                                return;
                            } else {
                                i11 = i20;
                            }
                        } else {
                            i11 = i20;
                            if (widgetRun.matchConstraintsType == 1 && i17 == 0) {
                                i12 = dimensionDependency.wrapValue;
                                i5++;
                            } else {
                                if (dimensionDependency.resolved) {
                                    i12 = i11;
                                }
                                if (z2) {
                                    i4 += i12;
                                } else {
                                    i5++;
                                    f2 = widgetRun.widget.mWeight[this.orientation];
                                    if (f2 >= 0.0f) {
                                        f += f2;
                                    }
                                }
                                if (i18 >= i15 && i18 < i) {
                                    i4 += -widgetRun.end.margin;
                                }
                            }
                            z2 = true;
                            if (z2) {
                                i5++;
                                f2 = widgetRun.widget.mWeight[this.orientation];
                                if (f2 >= 0.0f) {
                                    f += f2;
                                }
                            } else {
                                i4 += i12;
                            }
                            if (i18 >= i15) {
                            }
                        }
                        i12 = i11;
                        if (z2) {
                            i5++;
                            f2 = widgetRun.widget.mWeight[this.orientation];
                            if (f2 >= 0.0f) {
                                f += f2;
                            }
                        } else {
                            i4 += i12;
                        }
                        if (i18 >= i15) {
                        }
                    }
                    i18++;
                    i2 = 8;
                }
                if (i4 < i13 || i5 == 0) {
                    i3 = i19;
                    break;
                } else {
                    i17++;
                    i2 = 8;
                }
            }
            int i22 = this.start.value;
            if (zIsRtl) {
                i22 = this.end.value;
            }
            if (i4 > i13) {
                i22 = zIsRtl ? i22 + ((int) (((i4 - i13) / 2.0f) + 0.5f)) : i22 - ((int) (((i4 - i13) / 2.0f) + 0.5f));
            }
            if (i5 > 0) {
                float f3 = i13 - i4;
                int i23 = (int) ((f3 / i5) + 0.5f);
                int i24 = 0;
                int i25 = 0;
                while (i24 < size) {
                    WidgetRun widgetRun2 = this.widgets.get(i24);
                    int i26 = i23;
                    int i27 = i4;
                    if (widgetRun2.widget.getVisibility() != 8 && widgetRun2.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        DimensionDependency dimensionDependency2 = widgetRun2.dimension;
                        if (dimensionDependency2.resolved) {
                            zIsRtl = zIsRtl;
                            i22 = i22;
                            f3 = f3;
                        } else {
                            int i28 = f > 0.0f ? (int) (((widgetRun2.widget.mWeight[this.orientation] * f3) / f) + 0.5f) : i26;
                            if (this.orientation == 0) {
                                ConstraintWidget constraintWidget = widgetRun2.widget;
                                i10 = constraintWidget.mMatchConstraintMaxWidth;
                                i9 = constraintWidget.mMatchConstraintMinWidth;
                            } else {
                                ConstraintWidget constraintWidget2 = widgetRun2.widget;
                                int i29 = constraintWidget2.mMatchConstraintMaxHeight;
                                i9 = constraintWidget2.mMatchConstraintMinHeight;
                                i10 = i29;
                            }
                            int iMax = Math.max(i9, widgetRun2.matchConstraintsType == 1 ? Math.min(i28, dimensionDependency2.wrapValue) : i28);
                            if (i10 > 0) {
                                iMax = Math.min(i10, iMax);
                            }
                            if (iMax != i28) {
                                i25++;
                                i28 = iMax;
                            }
                            widgetRun2.dimension.resolve(i28);
                        }
                    } else {
                        zIsRtl = zIsRtl;
                        i22 = i22;
                        f3 = f3;
                    }
                    i24++;
                    i23 = i26;
                    i4 = i27;
                    i22 = i22;
                    f3 = f3;
                    zIsRtl = zIsRtl;
                    i3 = i3;
                }
                z = zIsRtl;
                i6 = i3;
                i7 = i22;
                int i30 = i4;
                if (i25 > 0) {
                    i5 -= i25;
                    int i31 = 0;
                    for (int i32 = 0; i32 < size; i32++) {
                        WidgetRun widgetRun3 = this.widgets.get(i32);
                        if (widgetRun3.widget.getVisibility() != 8) {
                            if (i32 > 0 && i32 >= i14) {
                                i31 += widgetRun3.start.margin;
                            }
                            i31 += widgetRun3.dimension.value;
                            if (i32 < i15 && i32 < i) {
                                i31 += -widgetRun3.end.margin;
                            }
                        }
                    }
                    i4 = i31;
                } else {
                    i4 = i30;
                }
                i8 = 2;
                if (this.chainStyle == 2 && i25 == 0) {
                    this.chainStyle = 0;
                }
            } else {
                z = zIsRtl;
                i6 = i3;
                i7 = i22;
                i8 = 2;
            }
            if (i4 > i13) {
                this.chainStyle = i8;
            }
            if (i6 > 0 && i5 == 0 && i14 == i) {
                this.chainStyle = i8;
            }
            int i33 = this.chainStyle;
            if (i33 == 1) {
                int i34 = i6;
                int i35 = i34 > 1 ? (i13 - i4) / (i34 - 1) : i34 == 1 ? (i13 - i4) / 2 : 0;
                if (i5 > 0) {
                    i35 = 0;
                }
                int i36 = i7;
                for (int i37 = 0; i37 < size; i37++) {
                    WidgetRun widgetRun4 = this.widgets.get(z ? size - (i37 + 1) : i37);
                    if (widgetRun4.widget.getVisibility() == 8) {
                        widgetRun4.start.resolve(i36);
                        widgetRun4.end.resolve(i36);
                    } else {
                        if (i37 > 0) {
                            i36 = z ? i36 - i35 : i36 + i35;
                        }
                        if (i37 > 0 && i37 >= i14) {
                            i36 = z ? i36 - widgetRun4.start.margin : i36 + widgetRun4.start.margin;
                        }
                        if (z) {
                            widgetRun4.end.resolve(i36);
                        } else {
                            widgetRun4.start.resolve(i36);
                        }
                        DimensionDependency dimensionDependency3 = widgetRun4.dimension;
                        int i38 = dimensionDependency3.value;
                        if (widgetRun4.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                            i38 = dimensionDependency3.wrapValue;
                        }
                        i36 = z ? i36 - i38 : i36 + i38;
                        if (z) {
                            widgetRun4.start.resolve(i36);
                        } else {
                            widgetRun4.end.resolve(i36);
                        }
                        widgetRun4.resolved = true;
                        if (i37 < i15 && i37 < i) {
                            i36 = z ? i36 - (-widgetRun4.end.margin) : i36 + (-widgetRun4.end.margin);
                        }
                    }
                }
                return;
            }
            int i39 = i6;
            if (i33 == 0) {
                int i40 = (i13 - i4) / (i39 + 1);
                if (i5 > 0) {
                    i40 = 0;
                }
                int i41 = i7;
                for (int i42 = 0; i42 < size; i42++) {
                    WidgetRun widgetRun5 = this.widgets.get(z ? size - (i42 + 1) : i42);
                    if (widgetRun5.widget.getVisibility() == 8) {
                        widgetRun5.start.resolve(i41);
                        widgetRun5.end.resolve(i41);
                    } else {
                        int i43 = z ? i41 - i40 : i41 + i40;
                        if (i42 > 0 && i42 >= i14) {
                            i43 = z ? i43 - widgetRun5.start.margin : i43 + widgetRun5.start.margin;
                        }
                        if (z) {
                            widgetRun5.end.resolve(i43);
                        } else {
                            widgetRun5.start.resolve(i43);
                        }
                        DimensionDependency dimensionDependency4 = widgetRun5.dimension;
                        int iMin = dimensionDependency4.value;
                        if (widgetRun5.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                            iMin = Math.min(iMin, dimensionDependency4.wrapValue);
                        }
                        i41 = z ? i43 - iMin : i43 + iMin;
                        if (z) {
                            widgetRun5.start.resolve(i41);
                        } else {
                            widgetRun5.end.resolve(i41);
                        }
                        if (i42 < i15 && i42 < i) {
                            i41 = z ? i41 - (-widgetRun5.end.margin) : i41 + (-widgetRun5.end.margin);
                        }
                    }
                }
                return;
            }
            if (i33 == 2) {
                float horizontalBiasPercent = this.orientation == 0 ? this.widget.getHorizontalBiasPercent() : this.widget.getVerticalBiasPercent();
                if (z) {
                    horizontalBiasPercent = 1.0f - horizontalBiasPercent;
                }
                int i44 = (int) (((i13 - i4) * horizontalBiasPercent) + 0.5f);
                if (i44 < 0 || i5 > 0) {
                    i44 = 0;
                }
                int i45 = z ? i7 - i44 : i7 + i44;
                for (int i46 = 0; i46 < size; i46++) {
                    WidgetRun widgetRun6 = this.widgets.get(z ? size - (i46 + 1) : i46);
                    if (widgetRun6.widget.getVisibility() == 8) {
                        widgetRun6.start.resolve(i45);
                        widgetRun6.end.resolve(i45);
                    } else {
                        if (i46 > 0 && i46 >= i14) {
                            i45 = z ? i45 - widgetRun6.start.margin : i45 + widgetRun6.start.margin;
                        }
                        if (z) {
                            widgetRun6.end.resolve(i45);
                        } else {
                            widgetRun6.start.resolve(i45);
                        }
                        DimensionDependency dimensionDependency5 = widgetRun6.dimension;
                        int i47 = dimensionDependency5.value;
                        if (widgetRun6.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                            i47 = dimensionDependency5.wrapValue;
                        }
                        i45 = z ? i45 - i47 : i45 + i47;
                        if (z) {
                            widgetRun6.start.resolve(i45);
                        } else {
                            widgetRun6.end.resolve(i45);
                        }
                        if (i46 < i15 && i46 < i) {
                            i45 = z ? i45 - (-widgetRun6.end.margin) : i45 + (-widgetRun6.end.margin);
                        }
                    }
                }
            }
        }
    }
}
