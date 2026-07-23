package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    public WidgetRun firstRun;
    public int groupIndex;
    public int position = 0;
    public boolean dual = false;
    public ArrayList<WidgetRun> runs = new ArrayList<>();

    public RunGroup(WidgetRun widgetRun, int i) {
        this.firstRun = null;
        int i2 = index;
        this.groupIndex = i2;
        index = i2 + 1;
        this.firstRun = widgetRun;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    private boolean defineTerminalWidget(WidgetRun widgetRun, int i) {
        DependencyNode dependencyNode;
        WidgetRun widgetRun2;
        DependencyNode dependencyNode2;
        WidgetRun widgetRun3;
        if (!widgetRun.widget.isTerminalWidget[i]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.dependencies) {
            if ((dependency instanceof DependencyNode) && (widgetRun3 = (dependencyNode2 = (DependencyNode) dependency).run) != widgetRun && dependencyNode2 == widgetRun3.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator<WidgetRun> it = ((ChainRun) widgetRun).widgets.iterator();
                    while (it.hasNext()) {
                        defineTerminalWidget(it.next(), i);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.widget.isTerminalWidget[i] = false;
                }
                defineTerminalWidget(dependencyNode2.run, i);
            }
        }
        for (Dependency dependency2 : widgetRun.end.dependencies) {
            if ((dependency2 instanceof DependencyNode) && (widgetRun2 = (dependencyNode = (DependencyNode) dependency2).run) != widgetRun && dependencyNode == widgetRun2.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator<WidgetRun> it2 = ((ChainRun) widgetRun).widgets.iterator();
                    while (it2.hasNext()) {
                        defineTerminalWidget(it2.next(), i);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.widget.isTerminalWidget[i] = false;
                }
                defineTerminalWidget(dependencyNode.run, i);
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    private long traverseEnd(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = dependencyNode.dependencies.size();
        long jMin = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) dependencyNode.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    jMin = Math.min(jMin, traverseEnd(dependencyNode2, ((long) dependencyNode2.margin) + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return jMin;
        }
        long wrapDimension = j - widgetRun.getWrapDimension();
        return Math.min(Math.min(jMin, traverseEnd(widgetRun.start, wrapDimension)), wrapDimension - ((long) widgetRun.start.margin));
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.Dependency>] */
    private long traverseStart(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = dependencyNode.dependencies.size();
        long jMax = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) dependencyNode.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    jMax = Math.max(jMax, traverseStart(dependencyNode2, ((long) dependencyNode2.margin) + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return jMax;
        }
        long wrapDimension = j + widgetRun.getWrapDimension();
        return Math.max(Math.max(jMax, traverseStart(widgetRun.end, wrapDimension)), wrapDimension - ((long) widgetRun.end.margin));
    }

    public void add(WidgetRun widgetRun) {
        this.runs.add(widgetRun);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.DependencyNode>] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList, java.util.List<androidx.constraintlayout.core.widgets.analyzer.DependencyNode>] */
    public long computeWrapSize(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        WidgetRun widgetRun = this.firstRun;
        long j = 0;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0L;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = (i == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).start;
        DependencyNode dependencyNode2 = (i == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).end;
        boolean zContains = widgetRun.start.targets.contains(dependencyNode);
        boolean zContains2 = this.firstRun.end.targets.contains(dependencyNode2);
        long wrapDimension = this.firstRun.getWrapDimension();
        if (!zContains || !zContains2) {
            if (zContains) {
                DependencyNode dependencyNode3 = this.firstRun.start;
                return Math.max(traverseStart(dependencyNode3, dependencyNode3.margin), ((long) this.firstRun.start.margin) + wrapDimension);
            }
            if (zContains2) {
                DependencyNode dependencyNode4 = this.firstRun.end;
                return Math.max(-traverseEnd(dependencyNode4, dependencyNode4.margin), ((long) (-this.firstRun.end.margin)) + wrapDimension);
            }
            WidgetRun widgetRun2 = this.firstRun;
            return (widgetRun2.getWrapDimension() + ((long) widgetRun2.start.margin)) - ((long) this.firstRun.end.margin);
        }
        long jTraverseStart = traverseStart(this.firstRun.start, 0L);
        long jTraverseEnd = traverseEnd(this.firstRun.end, 0L);
        long j2 = jTraverseStart - wrapDimension;
        WidgetRun widgetRun3 = this.firstRun;
        int i2 = widgetRun3.end.margin;
        if (j2 >= (-i2)) {
            j2 += (long) i2;
        }
        long j3 = widgetRun3.start.margin;
        long j4 = ((-jTraverseEnd) - wrapDimension) - j3;
        if (j4 >= j3) {
            j4 -= j3;
        }
        float biasPercent = widgetRun3.widget.getBiasPercent(i);
        if (biasPercent > 0.0f) {
            j = (long) ((j2 / (1.0f - biasPercent)) + (j4 / biasPercent));
        }
        float f = j;
        long jM = ((long) ((f * biasPercent) + 0.5f)) + wrapDimension + ((long) Insets$$ExternalSyntheticOutline0.m(1.0f, biasPercent, f, 0.5f));
        WidgetRun widgetRun4 = this.firstRun;
        return (((long) widgetRun4.start.margin) + jM) - ((long) widgetRun4.end.margin);
    }

    public void defineTerminalWidgets(boolean z, boolean z2) {
        if (z) {
            WidgetRun widgetRun = this.firstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (z2) {
            WidgetRun widgetRun2 = this.firstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
