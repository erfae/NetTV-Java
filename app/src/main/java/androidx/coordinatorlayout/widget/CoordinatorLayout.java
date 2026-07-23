package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.R;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2, NestedScrollingParent3 {
    public static final Class<?>[] CONSTRUCTOR_PARAMS;
    public static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    private static final int TYPE_ON_INTERCEPT = 0;
    private static final int TYPE_ON_TOUCH = 1;
    public static final String WIDGET_PACKAGE_NAME;
    public static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
    private static final Pools.Pool<Rect> sRectPool;
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private final int[] mBehaviorConsumed;
    private View mBehaviorTouchView;
    private final DirectedAcyclicGraph<View> mChildDag;
    private final List<View> mDependencySortedChildren;
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    private boolean mNeedsPreDrawListener;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private View mNestedScrollingTarget;
    private final int[] mNestedScrollingV2ConsumedCompat;
    public ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList;
    private final List<View> mTempList1;

    public interface AttachedBehavior {
        @NonNull
        Behavior getBehavior();
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        @Nullable
        public static Object getTag(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).mBehaviorTag;
        }

        public static void setTag(@NonNull View view, @Nullable Object obj) {
            ((LayoutParams) view.getLayoutParams()).mBehaviorTag = obj;
        }

        public boolean blocksInteractionBelow(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v) {
            return getScrimOpacity(coordinatorLayout, v) > 0.0f;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect) {
            return false;
        }

        @ColorInt
        public int getScrimColor(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v) {
            return -16777216;
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float getScrimOpacity(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v) {
            return 0.0f;
        }

        public boolean layoutDependsOn(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
            return false;
        }

        @NonNull
        public WindowInsetsCompat onApplyWindowInsets(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
            return false;
        }

        public void onDependentViewRemoved(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, int i) {
            return false;
        }

        public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, int i, int i2, int i3, int i4) {
            return false;
        }

        public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2, boolean z) {
            return false;
        }

        public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
            return false;
        }

        @Deprecated
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr) {
        }

        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
            if (i3 == 0) {
                onNestedPreScroll(coordinatorLayout, v, view, i, i2, iArr);
            }
        }

        @Deprecated
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4) {
        }

        @Deprecated
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                onNestedScroll(coordinatorLayout, v, view, i, i2, i3, i4);
            }
        }

        @Deprecated
        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i) {
        }

        public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
            if (i2 == 0) {
                onNestedScrollAccepted(coordinatorLayout, v, view, view2, i);
            }
        }

        public boolean onRequestChildRectangleOnScreen(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect, boolean z) {
            return false;
        }

        public void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Parcelable parcelable) {
        }

        @Nullable
        public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i) {
            return false;
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
            if (i2 == 0) {
                return onStartNestedScroll(coordinatorLayout, v, view, view2, i);
            }
            return false;
        }

        @Deprecated
        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view) {
        }

        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i) {
            if (i == 0) {
                onStopNestedScroll(coordinatorLayout, v, view);
            }
        }

        public boolean onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull MotionEvent motionEvent) {
            return false;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4, int i5, @NonNull int[] iArr) {
            iArr[0] = iArr[0] + i3;
            iArr[1] = iArr[1] + i4;
            onNestedScroll(coordinatorLayout, v, view, i, i2, i3, i4, i5);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Deprecated
    public @interface DefaultBehavior {
        Class<? extends Behavior> value();
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface DispatchChangeEvent {
    }

    public class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public HierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public OnPreDrawListener() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
        }
    }

    public static class ViewElevationComparator implements Comparator<View> {
        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            float z = ViewCompat.getZ(view);
            float z2 = ViewCompat.getZ(view2);
            if (z > z2) {
                return -1;
            }
            return z < z2 ? 1 : 0;
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        WIDGET_PACKAGE_NAME = r0 != null ? r0.getName() : null;
        TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal<>();
        sRectPool = new Pools.SynchronizedPool(12);
    }

    public CoordinatorLayout(@NonNull Context context) {
        this(context, null);
    }

    @NonNull
    private static Rect acquireTempRect() {
        Rect rectAcquire = sRectPool.acquire();
        return rectAcquire == null ? new Rect() : rectAcquire;
    }

    private static int clamp(int i, int i2, int i3) {
        if (i < i2) {
            return i2;
        }
        return i > i3 ? i3 : i;
    }

    private void constrainChildRect(LayoutParams layoutParams, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
        rect.set(iMax, iMax2, i + iMax, i2 + iMax2);
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        Behavior behavior;
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (ViewCompat.getFitsSystemWindows(childAt) && (behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior()) != null) {
                windowInsetsCompat = behavior.onApplyWindowInsets(this, childAt, windowInsetsCompat);
                if (windowInsetsCompat.isConsumed()) {
                    break;
                }
            }
        }
        return windowInsetsCompat;
    }

    private void getDesiredAnchoredChildRectWithoutConstraints(View view, int i, Rect rect, Rect rect2, LayoutParams layoutParams, int i2, int i3) {
        int iWidth;
        int iHeight;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(layoutParams.gravity), i);
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(resolveGravity(layoutParams.anchorGravity), i);
        int i4 = absoluteGravity & 7;
        int i5 = absoluteGravity & 112;
        int i6 = absoluteGravity2 & 7;
        int i7 = absoluteGravity2 & 112;
        if (i6 != 1) {
            iWidth = i6 != 5 ? rect.left : rect.right;
        } else {
            iWidth = rect.left + (rect.width() / 2);
        }
        if (i7 != 16) {
            iHeight = i7 != 80 ? rect.top : rect.bottom;
        } else {
            iHeight = rect.top + (rect.height() / 2);
        }
        if (i4 == 1) {
            iWidth -= i2 / 2;
        } else if (i4 != 5) {
            iWidth -= i2;
        }
        if (i5 == 16) {
            iHeight -= i3 / 2;
        } else if (i5 != 80) {
            iHeight -= i3;
        }
        rect2.set(iWidth, iHeight, i2 + iWidth, i3 + iHeight);
    }

    private int getKeyline(int i) {
        int[] iArr = this.mKeylines;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i);
            return 0;
        }
        if (i >= 0 && i < iArr.length) {
            return iArr[i];
        }
        Log.e("CoordinatorLayout", "Keyline index " + i + " out of range for " + this);
        return 0;
    }

    private void getTopSortedChildren(List<View> list) {
        list.clear();
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            list.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i) : i));
        }
        Comparator<View> comparator = TOP_SORTED_CHILDREN_COMPARATOR;
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }

    private boolean hasDependencies(View view) {
        return this.mChildDag.hasOutgoingEdges(view);
    }

    private void layoutChild(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rectAcquireTempRect = acquireTempRect();
        rectAcquireTempRect.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this) && !ViewCompat.getFitsSystemWindows(view)) {
            rectAcquireTempRect.left = this.mLastInsets.getSystemWindowInsetLeft() + rectAcquireTempRect.left;
            rectAcquireTempRect.top = this.mLastInsets.getSystemWindowInsetTop() + rectAcquireTempRect.top;
            rectAcquireTempRect.right -= this.mLastInsets.getSystemWindowInsetRight();
            rectAcquireTempRect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect rectAcquireTempRect2 = acquireTempRect();
        GravityCompat.apply(resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rectAcquireTempRect, rectAcquireTempRect2, i);
        view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom);
        releaseTempRect(rectAcquireTempRect);
        releaseTempRect(rectAcquireTempRect2);
    }

    private void layoutChildWithAnchor(View view, View view2, int i) {
        Rect rectAcquireTempRect = acquireTempRect();
        Rect rectAcquireTempRect2 = acquireTempRect();
        try {
            ViewGroupUtils.getDescendantRect(this, view2, rectAcquireTempRect);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            getDesiredAnchoredChildRectWithoutConstraints(view, i, rectAcquireTempRect, rectAcquireTempRect2, layoutParams, measuredWidth, measuredHeight);
            constrainChildRect(layoutParams, rectAcquireTempRect2, measuredWidth, measuredHeight);
            view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom);
        } finally {
            releaseTempRect(rectAcquireTempRect);
            releaseTempRect(rectAcquireTempRect2);
        }
    }

    private void layoutChildWithKeyline(View view, int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams.gravity), i2);
        int i3 = absoluteGravity & 7;
        int i4 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (i2 == 1) {
            i = width - i;
        }
        int keyline = getKeyline(i) - measuredWidth;
        int i5 = 0;
        if (i3 == 1) {
            keyline += measuredWidth / 2;
        } else if (i3 == 5) {
            keyline += measuredWidth;
        }
        if (i4 == 16) {
            i5 = 0 + (measuredHeight / 2);
        } else if (i4 == 80) {
            i5 = measuredHeight + 0;
        }
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, Math.min(keyline, ((width - getPaddingRight()) - measuredWidth) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, Math.min(i5, ((height - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
        view.layout(iMax, iMax2, measuredWidth + iMax, measuredHeight + iMax2);
    }

    private void offsetChildByInset(View view, Rect rect, int i) {
        boolean z;
        boolean z2;
        int width;
        int i2;
        int i3;
        int i4;
        int height;
        int i5;
        int i6;
        int i7;
        if (ViewCompat.isLaidOut(view) && view.getWidth() > 0 && view.getHeight() > 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Behavior behavior = layoutParams.getBehavior();
            Rect rectAcquireTempRect = acquireTempRect();
            Rect rectAcquireTempRect2 = acquireTempRect();
            rectAcquireTempRect2.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (behavior == null || !behavior.getInsetDodgeRect(this, view, rectAcquireTempRect)) {
                rectAcquireTempRect.set(rectAcquireTempRect2);
            } else if (!rectAcquireTempRect2.contains(rectAcquireTempRect)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Rect should be within the child's bounds. Rect:");
                sbM.append(rectAcquireTempRect.toShortString());
                sbM.append(" | Bounds:");
                sbM.append(rectAcquireTempRect2.toShortString());
                throw new IllegalArgumentException(sbM.toString());
            }
            releaseTempRect(rectAcquireTempRect2);
            if (rectAcquireTempRect.isEmpty()) {
                releaseTempRect(rectAcquireTempRect);
                return;
            }
            int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.dodgeInsetEdges, i);
            boolean z3 = true;
            if ((absoluteGravity & 48) != 48 || (i6 = (rectAcquireTempRect.top - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - layoutParams.mInsetOffsetY) >= (i7 = rect.top)) {
                z = false;
            } else {
                setInsetOffsetY(view, i7 - i6);
                z = true;
            }
            if ((absoluteGravity & 80) == 80 && (height = ((getHeight() - rectAcquireTempRect.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) + layoutParams.mInsetOffsetY) < (i5 = rect.bottom)) {
                setInsetOffsetY(view, height - i5);
                z = true;
            }
            if (!z) {
                setInsetOffsetY(view, 0);
            }
            if ((absoluteGravity & 3) != 3 || (i3 = (rectAcquireTempRect.left - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - layoutParams.mInsetOffsetX) >= (i4 = rect.left)) {
                z2 = false;
            } else {
                setInsetOffsetX(view, i4 - i3);
                z2 = true;
            }
            if ((absoluteGravity & 5) != 5 || (width = ((getWidth() - rectAcquireTempRect.right) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin) + layoutParams.mInsetOffsetX) >= (i2 = rect.right)) {
                z3 = z2;
            } else {
                setInsetOffsetX(view, width - i2);
            }
            if (!z3) {
                setInsetOffsetX(view, 0);
            }
            releaseTempRect(rectAcquireTempRect);
        }
    }

    private boolean performIntercept(MotionEvent motionEvent, int i) {
        int actionMasked = motionEvent.getActionMasked();
        List<View> list = this.mTempList1;
        getTopSortedChildren(list);
        int size = list.size();
        MotionEvent motionEventObtain = null;
        boolean zOnInterceptTouchEvent = false;
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            View view = list.get(i2);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Behavior behavior = layoutParams.getBehavior();
            if (!(zOnInterceptTouchEvent || z) || actionMasked == 0) {
                if (!zOnInterceptTouchEvent && behavior != null) {
                    if (i == 0) {
                        zOnInterceptTouchEvent = behavior.onInterceptTouchEvent(this, view, motionEvent);
                    } else if (i == 1) {
                        zOnInterceptTouchEvent = behavior.onTouchEvent(this, view, motionEvent);
                    }
                    if (zOnInterceptTouchEvent) {
                        this.mBehaviorTouchView = view;
                    }
                }
                boolean zDidBlockInteraction = layoutParams.didBlockInteraction();
                boolean zIsBlockingInteractionBelow = layoutParams.isBlockingInteractionBelow(this, view);
                z = zIsBlockingInteractionBelow && !zDidBlockInteraction;
                if (zIsBlockingInteractionBelow && !z) {
                    break;
                }
            } else if (behavior != null) {
                if (motionEventObtain == null) {
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                if (i == 0) {
                    behavior.onInterceptTouchEvent(this, view, motionEventObtain);
                } else if (i == 1) {
                    behavior.onTouchEvent(this, view, motionEventObtain);
                }
            }
        }
        list.clear();
        return zOnInterceptTouchEvent;
    }

    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        this.mChildDag.clear();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams resolvedLayoutParams = getResolvedLayoutParams(childAt);
            resolvedLayoutParams.findAnchorView(this, childAt);
            this.mChildDag.addNode(childAt);
            for (int i2 = 0; i2 < childCount; i2++) {
                if (i2 != i) {
                    View childAt2 = getChildAt(i2);
                    if (resolvedLayoutParams.dependsOn(this, childAt, childAt2)) {
                        if (!this.mChildDag.contains(childAt2)) {
                            this.mChildDag.addNode(childAt2);
                        }
                        this.mChildDag.addEdge(childAt2, childAt);
                    }
                }
            }
        }
        this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
        Collections.reverse(this.mDependencySortedChildren);
    }

    private static void releaseTempRect(@NonNull Rect rect) {
        rect.setEmpty();
        sRectPool.release(rect);
    }

    private void resetTouchBehaviors(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior();
            if (behavior != null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z) {
                    behavior.onInterceptTouchEvent(this, childAt, motionEventObtain);
                } else {
                    behavior.onTouchEvent(this, childAt, motionEventObtain);
                }
                motionEventObtain.recycle();
            }
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            ((LayoutParams) getChildAt(i2).getLayoutParams()).resetTouchBehaviorTracking();
        }
        this.mBehaviorTouchView = null;
        this.mDisallowInterceptReset = false;
    }

    private static int resolveAnchoredChildGravity(int i) {
        if (i == 0) {
            return 17;
        }
        return i;
    }

    private static int resolveGravity(int i) {
        if ((i & 7) == 0) {
            i |= GravityCompat.START;
        }
        return (i & 112) == 0 ? i | 48 : i;
    }

    private static int resolveKeylineGravity(int i) {
        if (i == 0) {
            return 8388661;
        }
        return i;
    }

    private void setInsetOffsetX(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetX;
        if (i2 != i) {
            ViewCompat.offsetLeftAndRight(view, i - i2);
            layoutParams.mInsetOffsetX = i;
        }
    }

    private void setInsetOffsetY(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetY;
        if (i2 != i) {
            ViewCompat.offsetTopAndBottom(view, i - i2);
            layoutParams.mInsetOffsetY = i;
        }
    }

    private void setupForInsets() {
        if (!ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.setOnApplyWindowInsetsListener(this, null);
            return;
        }
        if (this.mApplyWindowInsetsListener == null) {
            this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: androidx.coordinatorlayout.widget.CoordinatorLayout.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
                }
            };
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
        setSystemUiVisibility(1280);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void dispatchDependentViewsChanged(@NonNull View view) {
        List incomingEdges = this.mChildDag.getIncomingEdges(view);
        if (incomingEdges == null || incomingEdges.isEmpty()) {
            return;
        }
        for (int i = 0; i < incomingEdges.size(); i++) {
            View view2 = (View) incomingEdges.get(i);
            Behavior behavior = ((LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onDependentViewChanged(this, view2, view);
            }
        }
    }

    public boolean doViewsOverlap(@NonNull View view, @NonNull View view2) {
        boolean z = false;
        if (view.getVisibility() != 0 || view2.getVisibility() != 0) {
            return false;
        }
        Rect rectAcquireTempRect = acquireTempRect();
        getChildRect(view, view.getParent() != this, rectAcquireTempRect);
        Rect rectAcquireTempRect2 = acquireTempRect();
        getChildRect(view2, view2.getParent() != this, rectAcquireTempRect2);
        try {
            if (rectAcquireTempRect.left <= rectAcquireTempRect2.right && rectAcquireTempRect.top <= rectAcquireTempRect2.bottom && rectAcquireTempRect.right >= rectAcquireTempRect2.left && rectAcquireTempRect.bottom >= rectAcquireTempRect2.top) {
                z = true;
            }
            return z;
        } finally {
            releaseTempRect(rectAcquireTempRect);
            releaseTempRect(rectAcquireTempRect2);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Behavior behavior = layoutParams.mBehavior;
        if (behavior != null) {
            float scrimOpacity = behavior.getScrimOpacity(this, view);
            if (scrimOpacity > 0.0f) {
                if (this.mScrimPaint == null) {
                    this.mScrimPaint = new Paint();
                }
                this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view));
                this.mScrimPaint.setAlpha(clamp(Math.round(scrimOpacity * 255.0f), 0, 255));
                int iSave = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), Region.Op.DIFFERENCE);
                }
                canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), this.mScrimPaint);
                canvas.restoreToCount(iSave);
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mStatusBarBackground;
        boolean state = false;
        if (drawable != null && drawable.isStateful()) {
            state = false | drawable.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public final void getChildRect(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            ViewGroupUtils.getDescendantRect(this, view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    @NonNull
    public List<View> getDependencies(@NonNull View view) {
        List<View> outgoingEdges = this.mChildDag.getOutgoingEdges(view);
        this.mTempDependenciesList.clear();
        if (outgoingEdges != null) {
            this.mTempDependenciesList.addAll(outgoingEdges);
        }
        return this.mTempDependenciesList;
    }

    @VisibleForTesting
    public final List<View> getDependencySortedChildren() {
        prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    @NonNull
    public List<View> getDependents(@NonNull View view) {
        List incomingEdges = this.mChildDag.getIncomingEdges(view);
        this.mTempDependenciesList.clear();
        if (incomingEdges != null) {
            this.mTempDependenciesList.addAll(incomingEdges);
        }
        return this.mTempDependenciesList;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final LayoutParams getResolvedLayoutParams(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            if (view instanceof AttachedBehavior) {
                Behavior behavior = ((AttachedBehavior) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                layoutParams.setBehavior(behavior);
                layoutParams.mBehaviorResolved = true;
            } else {
                DefaultBehavior defaultBehavior = null;
                for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                    defaultBehavior = (DefaultBehavior) superclass.getAnnotation(DefaultBehavior.class);
                    if (defaultBehavior != null) {
                        break;
                    }
                }
                if (defaultBehavior != null) {
                    try {
                        layoutParams.setBehavior(defaultBehavior.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                    } catch (Exception e) {
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Default behavior class ");
                        sbM.append(defaultBehavior.value().getName());
                        sbM.append(" could not be instantiated. Did you forget a default constructor?");
                        Log.e("CoordinatorLayout", sbM.toString(), e);
                    }
                }
                layoutParams.mBehaviorResolved = true;
            }
        }
        return layoutParams;
    }

    @Nullable
    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    @Override // android.view.View
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    @Override // android.view.View
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    public boolean isPointInChildBounds(@NonNull View view, int i, int i2) {
        Rect rectAcquireTempRect = acquireTempRect();
        ViewGroupUtils.getDescendantRect(this, view, rectAcquireTempRect);
        try {
            return rectAcquireTempRect.contains(i, i2);
        } finally {
            releaseTempRect(rectAcquireTempRect);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        resetTouchBehaviors(false);
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this);
        }
        this.mIsAttachedToWindow = true;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00dd  */
    public final void onChildViewsChanged(int i) {
        int i2;
        Rect rect;
        int i3;
        Rect rect2;
        int i4;
        boolean zOnDependentViewChanged;
        View view;
        Behavior behavior;
        int i5 = i;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int size = this.mDependencySortedChildren.size();
        Rect rectAcquireTempRect = acquireTempRect();
        Rect rectAcquireTempRect2 = acquireTempRect();
        Rect rectAcquireTempRect3 = acquireTempRect();
        int i6 = 0;
        while (i6 < size) {
            View view2 = this.mDependencySortedChildren.get(i6);
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            if (i5 == 0 && view2.getVisibility() == 8) {
                i2 = i6;
                i3 = i5;
                i4 = size;
                rect = rectAcquireTempRect;
                rect2 = rectAcquireTempRect3;
            } else {
                int i7 = 0;
                while (i7 < i6) {
                    if (layoutParams.mAnchorDirectChild == this.mDependencySortedChildren.get(i7)) {
                        LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                        if (layoutParams2.mAnchorView != null) {
                            Rect rectAcquireTempRect4 = acquireTempRect();
                            Rect rectAcquireTempRect5 = acquireTempRect();
                            Rect rectAcquireTempRect6 = acquireTempRect();
                            ViewGroupUtils.getDescendantRect(this, layoutParams2.mAnchorView, rectAcquireTempRect4);
                            getChildRect(view2, false, rectAcquireTempRect5);
                            int measuredWidth = view2.getMeasuredWidth();
                            int measuredHeight = view2.getMeasuredHeight();
                            view = view2;
                            getDesiredAnchoredChildRectWithoutConstraints(view2, layoutDirection, rectAcquireTempRect4, rectAcquireTempRect6, layoutParams2, measuredWidth, measuredHeight);
                            boolean z = (rectAcquireTempRect6.left == rectAcquireTempRect5.left && rectAcquireTempRect6.top == rectAcquireTempRect5.top) ? false : true;
                            constrainChildRect(
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00af: INVOKE 
                                  (r25v0 'this' androidx.coordinatorlayout.widget.CoordinatorLayout A[IMMUTABLE_TYPE, THIS])
                                  (r20v0 androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams)
                                  (r15v0 'rectAcquireTempRect6' android.graphics.Rect)
                                  (r11v6 'measuredWidth' int)
                                  (r14v4 'measuredHeight' int)
                                 DIRECT call: androidx.coordinatorlayout.widget.CoordinatorLayout.constrainChildRect(androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams, android.graphics.Rect, int, int):void A[MD:(androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams, android.graphics.Rect, int, int):void (m)] (LINE:23) in method: androidx.coordinatorlayout.widget.CoordinatorLayout.onChildViewsChanged(int):void, file: classes.dex
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:173)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:173)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:291)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:270)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:420)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:299)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                                	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
                                	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
                                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
                                	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
                                	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
                                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:295)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:284)
                                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:268)
                                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:160)
                                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:104)
                                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                                	at jadx.core.ProcessClass.process(ProcessClass.java:89)
                                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:127)
                                	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:405)
                                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:393)
                                	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:343)
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r20v0 androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams
                                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
                                */
                            /*
                                Method dump skipped, instruction units count: 509
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onChildViewsChanged(int):void");
                        }

                        @Override // android.view.ViewGroup, android.view.View
                        public void onDetachedFromWindow() {
                            super.onDetachedFromWindow();
                            resetTouchBehaviors(false);
                            if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
                                getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
                            }
                            View view = this.mNestedScrollingTarget;
                            if (view != null) {
                                onStopNestedScroll(view);
                            }
                            this.mIsAttachedToWindow = false;
                        }

                        @Override // android.view.View
                        public void onDraw(Canvas canvas) {
                            super.onDraw(canvas);
                            if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
                                return;
                            }
                            WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
                            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
                            if (systemWindowInsetTop > 0) {
                                this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
                                this.mStatusBarBackground.draw(canvas);
                            }
                        }

                        @Override // android.view.ViewGroup
                        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                            int actionMasked = motionEvent.getActionMasked();
                            if (actionMasked == 0) {
                                resetTouchBehaviors(true);
                            }
                            boolean zPerformIntercept = performIntercept(motionEvent, 0);
                            if (actionMasked == 1 || actionMasked == 3) {
                                resetTouchBehaviors(true);
                            }
                            return zPerformIntercept;
                        }

                        @Override // android.view.ViewGroup, android.view.View
                        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
                            Behavior behavior;
                            int layoutDirection = ViewCompat.getLayoutDirection(this);
                            int size = this.mDependencySortedChildren.size();
                            for (int i5 = 0; i5 < size; i5++) {
                                View view = this.mDependencySortedChildren.get(i5);
                                if (view.getVisibility() != 8 && ((behavior = ((LayoutParams) view.getLayoutParams()).getBehavior()) == null || !behavior.onLayoutChild(this, view, layoutDirection))) {
                                    onLayoutChild(view, layoutDirection);
                                }
                            }
                        }

                        public void onLayoutChild(@NonNull View view, int i) {
                            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                            View view2 = layoutParams.mAnchorView;
                            if (view2 == null && layoutParams.mAnchorId != -1) {
                                throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
                            }
                            if (view2 != null) {
                                layoutChildWithAnchor(view, view2, i);
                                return;
                            }
                            int i2 = layoutParams.keyline;
                            if (i2 >= 0) {
                                layoutChildWithKeyline(view, i2, i);
                            } else {
                                layoutChild(view, i);
                            }
                        }

                        /* JADX WARN: Code duplicated, block: B:64:0x013b  */
                        /* JADX WARN: Code duplicated, block: B:67:0x0145  */
                        /* JADX WARN: Code duplicated, block: B:70:0x0167  */
                        @Override // android.view.View
                        public final void onMeasure(int i, int i2) {
                            boolean z;
                            int i3;
                            int iMax;
                            int i4;
                            int i5;
                            Behavior behavior;
                            LayoutParams layoutParams;
                            int i6;
                            int i7;
                            int i8;
                            int i9;
                            int i10;
                            int i11;
                            prepareChildren();
                            int childCount = getChildCount();
                            int i12 = 0;
                            while (true) {
                                if (i12 >= childCount) {
                                    z = false;
                                    break;
                                } else {
                                    if (hasDependencies(getChildAt(i12))) {
                                        z = true;
                                        break;
                                    }
                                    i12++;
                                }
                            }
                            if (z != this.mNeedsPreDrawListener) {
                                if (z) {
                                    if (this.mIsAttachedToWindow) {
                                        if (this.mOnPreDrawListener == null) {
                                            this.mOnPreDrawListener = new OnPreDrawListener();
                                        }
                                        getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
                                    }
                                    this.mNeedsPreDrawListener = true;
                                } else {
                                    if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
                                        getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
                                    }
                                    this.mNeedsPreDrawListener = false;
                                }
                            }
                            int paddingLeft = getPaddingLeft();
                            int paddingTop = getPaddingTop();
                            int paddingRight = getPaddingRight();
                            int paddingBottom = getPaddingBottom();
                            int layoutDirection = ViewCompat.getLayoutDirection(this);
                            boolean z2 = layoutDirection == 1;
                            int mode = View.MeasureSpec.getMode(i);
                            int size = View.MeasureSpec.getSize(i);
                            int mode2 = View.MeasureSpec.getMode(i2);
                            int size2 = View.MeasureSpec.getSize(i2);
                            int i13 = paddingLeft + paddingRight;
                            int i14 = paddingTop + paddingBottom;
                            int suggestedMinimumWidth = getSuggestedMinimumWidth();
                            int suggestedMinimumHeight = getSuggestedMinimumHeight();
                            boolean z3 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this);
                            int size3 = this.mDependencySortedChildren.size();
                            int i15 = suggestedMinimumWidth;
                            int i16 = suggestedMinimumHeight;
                            int iCombineMeasuredStates = 0;
                            int i17 = 0;
                            while (i17 < size3) {
                                View view = this.mDependencySortedChildren.get(i17);
                                if (view.getVisibility() == 8) {
                                    i10 = i17;
                                    i7 = size3;
                                    i8 = paddingLeft;
                                } else {
                                    LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                                    int i18 = layoutParams2.keyline;
                                    if (i18 < 0 || mode == 0) {
                                        i3 = iCombineMeasuredStates;
                                    } else {
                                        int keyline = getKeyline(i18);
                                        int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams2.gravity), layoutDirection) & 7;
                                        i3 = iCombineMeasuredStates;
                                        if ((absoluteGravity == 3 && !z2) || (absoluteGravity == 5 && z2)) {
                                            iMax = Math.max(0, (size - paddingRight) - keyline);
                                        } else if ((absoluteGravity == 5 && !z2) || (absoluteGravity == 3 && z2)) {
                                            iMax = Math.max(0, keyline - paddingLeft);
                                        }
                                        if (z3 || ViewCompat.getFitsSystemWindows(view)) {
                                            i4 = i;
                                            i5 = i2;
                                        } else {
                                            int systemWindowInsetRight = this.mLastInsets.getSystemWindowInsetRight() + this.mLastInsets.getSystemWindowInsetLeft();
                                            int systemWindowInsetBottom = this.mLastInsets.getSystemWindowInsetBottom() + this.mLastInsets.getSystemWindowInsetTop();
                                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - systemWindowInsetRight, mode);
                                            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2 - systemWindowInsetBottom, mode2);
                                            i4 = iMakeMeasureSpec;
                                            i5 = iMakeMeasureSpec2;
                                        }
                                        behavior = layoutParams2.getBehavior();
                                        if (behavior != null) {
                                            layoutParams = layoutParams2;
                                            i9 = i3;
                                            i10 = i17;
                                            i6 = i16;
                                            i8 = paddingLeft;
                                            i11 = i15;
                                            i7 = size3;
                                            if (!behavior.onMeasureChild(this, view, i4, iMax, i5, 0)) {
                                            }
                                            LayoutParams layoutParams3 = layoutParams;
                                            int iMax2 = Math.max(i11, view.getMeasuredWidth() + i13 + ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams3).rightMargin);
                                            int iMax3 = Math.max(i6, view.getMeasuredHeight() + i14 + ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin);
                                            iCombineMeasuredStates = View.combineMeasuredStates(i9, view.getMeasuredState());
                                            i15 = iMax2;
                                            i16 = iMax3;
                                        } else {
                                            layoutParams = layoutParams2;
                                            i6 = i16;
                                            i7 = size3;
                                            i8 = paddingLeft;
                                            i9 = i3;
                                            i10 = i17;
                                            i11 = i15;
                                        }
                                        onMeasureChild(view, i4, iMax, i5, 0);
                                        LayoutParams layoutParams4 = layoutParams;
                                        int iMax4 = Math.max(i11, view.getMeasuredWidth() + i13 + ((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams4).rightMargin);
                                        int iMax5 = Math.max(i6, view.getMeasuredHeight() + i14 + ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin);
                                        iCombineMeasuredStates = View.combineMeasuredStates(i9, view.getMeasuredState());
                                        i15 = iMax4;
                                        i16 = iMax5;
                                    }
                                    iMax = 0;
                                    if (z3) {
                                        i4 = i;
                                        i5 = i2;
                                    } else {
                                        i4 = i;
                                        i5 = i2;
                                    }
                                    behavior = layoutParams2.getBehavior();
                                    if (behavior != null) {
                                        layoutParams = layoutParams2;
                                        i9 = i3;
                                        i10 = i17;
                                        i6 = i16;
                                        i8 = paddingLeft;
                                        i11 = i15;
                                        i7 = size3;
                                        if (!behavior.onMeasureChild(this, view, i4, iMax, i5, 0)) {
                                        }
                                        LayoutParams layoutParams5 = layoutParams;
                                        int iMax6 = Math.max(i11, view.getMeasuredWidth() + i13 + ((ViewGroup.MarginLayoutParams) layoutParams5).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams5).rightMargin);
                                        int iMax7 = Math.max(i6, view.getMeasuredHeight() + i14 + ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams5).bottomMargin);
                                        iCombineMeasuredStates = View.combineMeasuredStates(i9, view.getMeasuredState());
                                        i15 = iMax6;
                                        i16 = iMax7;
                                    } else {
                                        layoutParams = layoutParams2;
                                        i6 = i16;
                                        i7 = size3;
                                        i8 = paddingLeft;
                                        i9 = i3;
                                        i10 = i17;
                                        i11 = i15;
                                    }
                                    onMeasureChild(view, i4, iMax, i5, 0);
                                    LayoutParams layoutParams6 = layoutParams;
                                    int iMax8 = Math.max(i11, view.getMeasuredWidth() + i13 + ((ViewGroup.MarginLayoutParams) layoutParams6).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams6).rightMargin);
                                    int iMax9 = Math.max(i6, view.getMeasuredHeight() + i14 + ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams6).bottomMargin);
                                    iCombineMeasuredStates = View.combineMeasuredStates(i9, view.getMeasuredState());
                                    i15 = iMax8;
                                    i16 = iMax9;
                                }
                                i17 = i10 + 1;
                                paddingLeft = i8;
                                size3 = i7;
                            }
                            int i19 = iCombineMeasuredStates;
                            setMeasuredDimension(View.resolveSizeAndState(i15, i, (-16777216) & i19), View.resolveSizeAndState(i16, i2, i19 << 16));
                        }

                        public void onMeasureChild(View view, int i, int i2, int i3, int i4) {
                            measureChildWithMargins(view, i, i2, i3, i4);
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public boolean onNestedFling(View view, float f, float f2, boolean z) {
                            Behavior behavior;
                            int childCount = getChildCount();
                            boolean zOnNestedFling = false;
                            for (int i = 0; i < childCount; i++) {
                                View childAt = getChildAt(i);
                                if (childAt.getVisibility() != 8) {
                                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                    if (layoutParams.isNestedScrollAccepted(0) && (behavior = layoutParams.getBehavior()) != null) {
                                        zOnNestedFling |= behavior.onNestedFling(this, childAt, view, f, f2, z);
                                    }
                                }
                            }
                            if (zOnNestedFling) {
                                onChildViewsChanged(1);
                            }
                            return zOnNestedFling;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public boolean onNestedPreFling(View view, float f, float f2) {
                            Behavior behavior;
                            int childCount = getChildCount();
                            boolean zOnNestedPreFling = false;
                            for (int i = 0; i < childCount; i++) {
                                View childAt = getChildAt(i);
                                if (childAt.getVisibility() != 8) {
                                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                    if (layoutParams.isNestedScrollAccepted(0) && (behavior = layoutParams.getBehavior()) != null) {
                                        zOnNestedPreFling |= behavior.onNestedPreFling(this, childAt, view, f, f2);
                                    }
                                }
                            }
                            return zOnNestedPreFling;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
                            onNestedPreScroll(view, i, i2, iArr, 0);
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
                            onNestedScroll(view, i, i2, i3, i4, 0);
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public void onNestedScrollAccepted(View view, View view2, int i) {
                            onNestedScrollAccepted(view, view2, i, 0);
                        }

                        @Override // android.view.View
                        public final void onRestoreInstanceState(Parcelable parcelable) {
                            Parcelable parcelable2;
                            if (!(parcelable instanceof SavedState)) {
                                super.onRestoreInstanceState(parcelable);
                                return;
                            }
                            SavedState savedState = (SavedState) parcelable;
                            super.onRestoreInstanceState(savedState.getSuperState());
                            SparseArray<Parcelable> sparseArray = savedState.behaviorStates;
                            int childCount = getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View childAt = getChildAt(i);
                                int id = childAt.getId();
                                Behavior behavior = getResolvedLayoutParams(childAt).getBehavior();
                                if (id != -1 && behavior != null && (parcelable2 = sparseArray.get(id)) != null) {
                                    behavior.onRestoreInstanceState(this, childAt, parcelable2);
                                }
                            }
                        }

                        @Override // android.view.View
                        public final Parcelable onSaveInstanceState() {
                            Parcelable parcelableOnSaveInstanceState;
                            SavedState savedState = new SavedState(super.onSaveInstanceState());
                            SparseArray<Parcelable> sparseArray = new SparseArray<>();
                            int childCount = getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View childAt = getChildAt(i);
                                int id = childAt.getId();
                                Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior();
                                if (id != -1 && behavior != null && (parcelableOnSaveInstanceState = behavior.onSaveInstanceState(this, childAt)) != null) {
                                    sparseArray.append(id, parcelableOnSaveInstanceState);
                                }
                            }
                            savedState.behaviorStates = sparseArray;
                            return savedState;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public boolean onStartNestedScroll(View view, View view2, int i) {
                            return onStartNestedScroll(view, view2, i, 0);
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
                        public void onStopNestedScroll(View view) {
                            onStopNestedScroll(view, 0);
                        }

                        /* JADX WARN: Code duplicated, block: B:11:0x002b A[PHI: r3
  0x002b: PHI (r3v4 boolean) = (r3v2 boolean), (r3v5 boolean) binds: [B:9:0x0022, B:5:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
                        /* JADX WARN: Code duplicated, block: B:14:0x0031  */
                        /* JADX WARN: Code duplicated, block: B:15:0x0037 A[DONT_INVERT] */
                        /* JADX WARN: Code duplicated, block: B:16:0x0039  */
                        /* JADX WARN: Code duplicated, block: B:18:0x004c  */
                        /* JADX WARN: Code duplicated, block: B:22:0x0054  */
                        @Override // android.view.View
                        public boolean onTouchEvent(MotionEvent motionEvent) {
                            boolean zPerformIntercept;
                            boolean zOnTouchEvent;
                            MotionEvent motionEventObtain;
                            int actionMasked = motionEvent.getActionMasked();
                            if (this.mBehaviorTouchView == null) {
                                zPerformIntercept = performIntercept(motionEvent, 1);
                                if (!zPerformIntercept) {
                                    zOnTouchEvent = false;
                                }
                                motionEventObtain = null;
                                if (this.mBehaviorTouchView == null) {
                                    zOnTouchEvent |= super.onTouchEvent(motionEvent);
                                } else if (zPerformIntercept) {
                                    long jUptimeMillis = SystemClock.uptimeMillis();
                                    motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                                    super.onTouchEvent(motionEventObtain);
                                }
                                if (motionEventObtain != null) {
                                    motionEventObtain.recycle();
                                }
                                if (actionMasked != 1 || actionMasked == 3) {
                                    resetTouchBehaviors(false);
                                }
                                return zOnTouchEvent;
                            }
                            zPerformIntercept = false;
                            Behavior behavior = ((LayoutParams) this.mBehaviorTouchView.getLayoutParams()).getBehavior();
                            if (behavior != null) {
                                zOnTouchEvent = behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent);
                            } else {
                                zOnTouchEvent = false;
                            }
                            motionEventObtain = null;
                            if (this.mBehaviorTouchView == null) {
                                zOnTouchEvent |= super.onTouchEvent(motionEvent);
                            } else if (zPerformIntercept) {
                                long jUptimeMillis2 = SystemClock.uptimeMillis();
                                motionEventObtain = MotionEvent.obtain(jUptimeMillis2, jUptimeMillis2, 3, 0.0f, 0.0f, 0);
                                super.onTouchEvent(motionEventObtain);
                            }
                            if (motionEventObtain != null) {
                                motionEventObtain.recycle();
                            }
                            if (actionMasked != 1) {
                                resetTouchBehaviors(false);
                            } else {
                                resetTouchBehaviors(false);
                            }
                            return zOnTouchEvent;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent
                        public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
                            Behavior behavior = ((LayoutParams) view.getLayoutParams()).getBehavior();
                            if (behavior == null || !behavior.onRequestChildRectangleOnScreen(this, view, rect, z)) {
                                return super.requestChildRectangleOnScreen(view, rect, z);
                            }
                            return true;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent
                        public void requestDisallowInterceptTouchEvent(boolean z) {
                            super.requestDisallowInterceptTouchEvent(z);
                            if (!z || this.mDisallowInterceptReset) {
                                return;
                            }
                            resetTouchBehaviors(false);
                            this.mDisallowInterceptReset = true;
                        }

                        @Override // android.view.View
                        public void setFitsSystemWindows(boolean z) {
                            super.setFitsSystemWindows(z);
                            setupForInsets();
                        }

                        @Override // android.view.ViewGroup
                        public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
                            this.mOnHierarchyChangeListener = onHierarchyChangeListener;
                        }

                        public void setStatusBarBackground(@Nullable Drawable drawable) {
                            Drawable drawable2 = this.mStatusBarBackground;
                            if (drawable2 != drawable) {
                                if (drawable2 != null) {
                                    drawable2.setCallback(null);
                                }
                                Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
                                this.mStatusBarBackground = drawableMutate;
                                if (drawableMutate != null) {
                                    if (drawableMutate.isStateful()) {
                                        this.mStatusBarBackground.setState(getDrawableState());
                                    }
                                    DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection(this));
                                    this.mStatusBarBackground.setVisible(getVisibility() == 0, false);
                                    this.mStatusBarBackground.setCallback(this);
                                }
                                ViewCompat.postInvalidateOnAnimation(this);
                            }
                        }

                        public void setStatusBarBackgroundColor(@ColorInt int i) {
                            setStatusBarBackground(new ColorDrawable(i));
                        }

                        public void setStatusBarBackgroundResource(@DrawableRes int i) {
                            setStatusBarBackground(i != 0 ? ContextCompat.getDrawable(getContext(), i) : null);
                        }

                        @Override // android.view.View
                        public void setVisibility(int i) {
                            super.setVisibility(i);
                            boolean z = i == 0;
                            Drawable drawable = this.mStatusBarBackground;
                            if (drawable == null || drawable.isVisible() == z) {
                                return;
                            }
                            this.mStatusBarBackground.setVisible(z, false);
                        }

                        public final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
                            if (ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) {
                                return windowInsetsCompat;
                            }
                            this.mLastInsets = windowInsetsCompat;
                            boolean z = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
                            this.mDrawStatusBarBackground = z;
                            setWillNotDraw(!z && getBackground() == null);
                            WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsetsToBehaviors = dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat);
                            requestLayout();
                            return windowInsetsCompatDispatchApplyWindowInsetsToBehaviors;
                        }

                        @Override // android.view.View
                        public final boolean verifyDrawable(Drawable drawable) {
                            return super.verifyDrawable(drawable) || drawable == this.mStatusBarBackground;
                        }

                        public CoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
                            this(context, attributeSet, R.attr.coordinatorLayoutStyle);
                        }

                        @Override // android.view.ViewGroup
                        public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
                            return new LayoutParams(getContext(), attributeSet);
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
                            Behavior behavior;
                            int childCount = getChildCount();
                            boolean z = false;
                            int iMax = 0;
                            int iMax2 = 0;
                            for (int i4 = 0; i4 < childCount; i4++) {
                                View childAt = getChildAt(i4);
                                if (childAt.getVisibility() != 8) {
                                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                    if (layoutParams.isNestedScrollAccepted(i3) && (behavior = layoutParams.getBehavior()) != null) {
                                        int[] iArr2 = this.mBehaviorConsumed;
                                        iArr2[0] = 0;
                                        iArr2[1] = 0;
                                        behavior.onNestedPreScroll(this, childAt, view, i, i2, iArr2, i3);
                                        int[] iArr3 = this.mBehaviorConsumed;
                                        iMax = i > 0 ? Math.max(iMax, iArr3[0]) : Math.min(iMax, iArr3[0]);
                                        int[] iArr4 = this.mBehaviorConsumed;
                                        iMax2 = i2 > 0 ? Math.max(iMax2, iArr4[1]) : Math.min(iMax2, iArr4[1]);
                                        z = true;
                                    }
                                }
                            }
                            iArr[0] = iMax;
                            iArr[1] = iMax2;
                            if (z) {
                                onChildViewsChanged(1);
                            }
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
                            onNestedScroll(view, i, i2, i3, i4, 0, this.mNestedScrollingV2ConsumedCompat);
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
                            Behavior behavior;
                            this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i, i2);
                            this.mNestedScrollingTarget = view2;
                            int childCount = getChildCount();
                            for (int i3 = 0; i3 < childCount; i3++) {
                                View childAt = getChildAt(i3);
                                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                if (layoutParams.isNestedScrollAccepted(i2) && (behavior = layoutParams.getBehavior()) != null) {
                                    behavior.onNestedScrollAccepted(this, childAt, view, view2, i, i2);
                                }
                            }
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
                            int childCount = getChildCount();
                            boolean z = false;
                            for (int i3 = 0; i3 < childCount; i3++) {
                                View childAt = getChildAt(i3);
                                if (childAt.getVisibility() != 8) {
                                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                    Behavior behavior = layoutParams.getBehavior();
                                    if (behavior != null) {
                                        boolean zOnStartNestedScroll = behavior.onStartNestedScroll(this, childAt, view, view2, i, i2);
                                        z |= zOnStartNestedScroll;
                                        layoutParams.setNestedScrollAccepted(i2, zOnStartNestedScroll);
                                    } else {
                                        layoutParams.setNestedScrollAccepted(i2, false);
                                    }
                                }
                            }
                            return z;
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onStopNestedScroll(View view, int i) {
                            this.mNestedScrollingParentHelper.onStopNestedScroll(view, i);
                            int childCount = getChildCount();
                            for (int i2 = 0; i2 < childCount; i2++) {
                                View childAt = getChildAt(i2);
                                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                if (layoutParams.isNestedScrollAccepted(i)) {
                                    Behavior behavior = layoutParams.getBehavior();
                                    if (behavior != null) {
                                        behavior.onStopNestedScroll(this, childAt, view, i);
                                    }
                                    layoutParams.setNestedScrollAccepted(i, false);
                                    layoutParams.resetChangedAfterNestedScroll();
                                }
                            }
                            this.mNestedScrollingTarget = null;
                        }

                        public CoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
                            TypedArray typedArrayObtainStyledAttributes;
                            super(context, attributeSet, i);
                            this.mDependencySortedChildren = new ArrayList();
                            this.mChildDag = new DirectedAcyclicGraph<>();
                            this.mTempList1 = new ArrayList();
                            this.mTempDependenciesList = new ArrayList();
                            this.mBehaviorConsumed = new int[2];
                            this.mNestedScrollingV2ConsumedCompat = new int[2];
                            this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
                            if (i == 0) {
                                typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout);
                            } else {
                                typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, i, 0);
                            }
                            if (Build.VERSION.SDK_INT >= 29) {
                                if (i == 0) {
                                    saveAttributeDataForStyleable(context, R.styleable.CoordinatorLayout, attributeSet, typedArrayObtainStyledAttributes, 0, R.style.Widget_Support_CoordinatorLayout);
                                } else {
                                    saveAttributeDataForStyleable(context, R.styleable.CoordinatorLayout, attributeSet, typedArrayObtainStyledAttributes, i, 0);
                                }
                            }
                            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
                            if (resourceId != 0) {
                                Resources resources = context.getResources();
                                this.mKeylines = resources.getIntArray(resourceId);
                                float f = resources.getDisplayMetrics().density;
                                int length = this.mKeylines.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    int[] iArr = this.mKeylines;
                                    iArr[i2] = (int) (iArr[i2] * f);
                                }
                            }
                            this.mStatusBarBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
                            typedArrayObtainStyledAttributes.recycle();
                            setupForInsets();
                            super.setOnHierarchyChangeListener(new HierarchyChangeListener());
                            if (ViewCompat.getImportantForAccessibility(this) == 0) {
                                ViewCompat.setImportantForAccessibility(this, 1);
                            }
                        }

                        @Override // android.view.ViewGroup
                        public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
                            if (layoutParams instanceof LayoutParams) {
                                return new LayoutParams((LayoutParams) layoutParams);
                            }
                            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                                return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
                            }
                            return new LayoutParams(layoutParams);
                        }

                        @Override // androidx.core.view.NestedScrollingParent3
                        public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, int i5, @NonNull int[] iArr) {
                            Behavior behavior;
                            int iMin;
                            int childCount = getChildCount();
                            boolean z = false;
                            int iMax = 0;
                            int i6 = 0;
                            for (int i7 = 0; i7 < childCount; i7++) {
                                View childAt = getChildAt(i7);
                                if (childAt.getVisibility() != 8) {
                                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                    if (layoutParams.isNestedScrollAccepted(i5) && (behavior = layoutParams.getBehavior()) != null) {
                                        int[] iArr2 = this.mBehaviorConsumed;
                                        iArr2[0] = 0;
                                        iArr2[1] = 0;
                                        behavior.onNestedScroll(this, childAt, view, i, i2, i3, i4, i5, iArr2);
                                        int[] iArr3 = this.mBehaviorConsumed;
                                        iMax = i3 > 0 ? Math.max(iMax, iArr3[0]) : Math.min(iMax, iArr3[0]);
                                        if (i4 > 0) {
                                            iMin = Math.max(i6, this.mBehaviorConsumed[1]);
                                        } else {
                                            iMin = Math.min(i6, this.mBehaviorConsumed[1]);
                                        }
                                        i6 = iMin;
                                        z = true;
                                    }
                                }
                            }
                            iArr[0] = iArr[0] + iMax;
                            iArr[1] = iArr[1] + i6;
                            if (z) {
                                onChildViewsChanged(1);
                            }
                        }

                        public static class SavedState extends AbsSavedState {
                            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.coordinatorlayout.widget.CoordinatorLayout.SavedState.1
                                @Override // android.os.Parcelable.Creator
                                public SavedState[] newArray(int i) {
                                    return new SavedState[i];
                                }

                                /* JADX WARN: Can't rename method to resolve collision */
                                @Override // android.os.Parcelable.ClassLoaderCreator
                                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                                    return new SavedState(parcel, classLoader);
                                }

                                @Override // android.os.Parcelable.Creator
                                public SavedState createFromParcel(Parcel parcel) {
                                    return new SavedState(parcel, null);
                                }
                            };
                            public SparseArray<Parcelable> behaviorStates;

                            public SavedState(Parcel parcel, ClassLoader classLoader) {
                                super(parcel, classLoader);
                                int i = parcel.readInt();
                                int[] iArr = new int[i];
                                parcel.readIntArray(iArr);
                                Parcelable[] parcelableArray = parcel.readParcelableArray(classLoader);
                                this.behaviorStates = new SparseArray<>(i);
                                for (int i2 = 0; i2 < i; i2++) {
                                    this.behaviorStates.append(iArr[i2], parcelableArray[i2]);
                                }
                            }

                            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
                            public void writeToParcel(Parcel parcel, int i) {
                                super.writeToParcel(parcel, i);
                                SparseArray<Parcelable> sparseArray = this.behaviorStates;
                                int size = sparseArray != null ? sparseArray.size() : 0;
                                parcel.writeInt(size);
                                int[] iArr = new int[size];
                                Parcelable[] parcelableArr = new Parcelable[size];
                                for (int i2 = 0; i2 < size; i2++) {
                                    iArr[i2] = this.behaviorStates.keyAt(i2);
                                    parcelableArr[i2] = this.behaviorStates.valueAt(i2);
                                }
                                parcel.writeIntArray(iArr);
                                parcel.writeParcelableArray(parcelableArr, i);
                            }

                            public SavedState(Parcelable parcelable) {
                                super(parcelable);
                            }
                        }

                        public static class LayoutParams extends ViewGroup.MarginLayoutParams {
                            public int anchorGravity;
                            public int dodgeInsetEdges;
                            public int gravity;
                            public int insetEdge;
                            public int keyline;
                            public View mAnchorDirectChild;
                            public int mAnchorId;
                            public View mAnchorView;
                            public Behavior mBehavior;
                            public boolean mBehaviorResolved;
                            public Object mBehaviorTag;
                            private boolean mDidAcceptNestedScrollNonTouch;
                            private boolean mDidAcceptNestedScrollTouch;
                            private boolean mDidBlockInteraction;
                            private boolean mDidChangeAfterNestedScroll;
                            public int mInsetOffsetX;
                            public int mInsetOffsetY;
                            public final Rect mLastChildRect;

                            public LayoutParams(int i, int i2) {
                                super(i, i2);
                                this.mBehaviorResolved = false;
                                this.gravity = 0;
                                this.anchorGravity = 0;
                                this.keyline = -1;
                                this.mAnchorId = -1;
                                this.insetEdge = 0;
                                this.dodgeInsetEdges = 0;
                                this.mLastChildRect = new Rect();
                            }

                            private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
                                View viewFindViewById = coordinatorLayout.findViewById(this.mAnchorId);
                                this.mAnchorView = viewFindViewById;
                                if (viewFindViewById == null) {
                                    if (coordinatorLayout.isInEditMode()) {
                                        this.mAnchorDirectChild = null;
                                        this.mAnchorView = null;
                                        return;
                                    } else {
                                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Could not find CoordinatorLayout descendant view with id ");
                                        sbM.append(coordinatorLayout.getResources().getResourceName(this.mAnchorId));
                                        sbM.append(" to anchor view ");
                                        sbM.append(view);
                                        throw new IllegalStateException(sbM.toString());
                                    }
                                }
                                if (viewFindViewById == coordinatorLayout) {
                                    if (!coordinatorLayout.isInEditMode()) {
                                        throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                                    }
                                    this.mAnchorDirectChild = null;
                                    this.mAnchorView = null;
                                    return;
                                }
                                for (ViewParent parent = viewFindViewById.getParent(); parent != coordinatorLayout && parent != null; parent = parent.getParent()) {
                                    if (parent == view) {
                                        if (!coordinatorLayout.isInEditMode()) {
                                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                                        }
                                        this.mAnchorDirectChild = null;
                                        this.mAnchorView = null;
                                        return;
                                    }
                                    if (parent instanceof View) {
                                        viewFindViewById = parent;
                                    }
                                }
                                this.mAnchorDirectChild = viewFindViewById;
                            }

                            private boolean shouldDodge(View view, int i) {
                                int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).insetEdge, i);
                                return absoluteGravity != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, i) & absoluteGravity) == absoluteGravity;
                            }

                            private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
                                if (this.mAnchorView.getId() != this.mAnchorId) {
                                    return false;
                                }
                                View view2 = this.mAnchorView;
                                for (ViewParent parent = view2.getParent(); parent != coordinatorLayout; parent = parent.getParent()) {
                                    if (parent == null || parent == view) {
                                        this.mAnchorDirectChild = null;
                                        this.mAnchorView = null;
                                        return false;
                                    }
                                    if (parent instanceof View) {
                                        view2 = parent;
                                    }
                                }
                                this.mAnchorDirectChild = view2;
                                return true;
                            }

                            public final boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
                                Behavior behavior;
                                return view2 == this.mAnchorDirectChild || shouldDodge(view2, ViewCompat.getLayoutDirection(coordinatorLayout)) || ((behavior = this.mBehavior) != null && behavior.layoutDependsOn(coordinatorLayout, view, view2));
                            }

                            public final boolean didBlockInteraction() {
                                if (this.mBehavior == null) {
                                    this.mDidBlockInteraction = false;
                                }
                                return this.mDidBlockInteraction;
                            }

                            public final View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
                                if (this.mAnchorId == -1) {
                                    this.mAnchorDirectChild = null;
                                    this.mAnchorView = null;
                                    return null;
                                }
                                if (this.mAnchorView == null || !verifyAnchorView(view, coordinatorLayout)) {
                                    resolveAnchorView(view, coordinatorLayout);
                                }
                                return this.mAnchorView;
                            }

                            @IdRes
                            public int getAnchorId() {
                                return this.mAnchorId;
                            }

                            @Nullable
                            public Behavior getBehavior() {
                                return this.mBehavior;
                            }

                            public final boolean getChangedAfterNestedScroll() {
                                return this.mDidChangeAfterNestedScroll;
                            }

                            public final boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
                                boolean z = this.mDidBlockInteraction;
                                if (z) {
                                    return true;
                                }
                                Behavior behavior = this.mBehavior;
                                boolean zBlocksInteractionBelow = (behavior != null ? behavior.blocksInteractionBelow(coordinatorLayout, view) : false) | z;
                                this.mDidBlockInteraction = zBlocksInteractionBelow;
                                return zBlocksInteractionBelow;
                            }

                            public final boolean isNestedScrollAccepted(int i) {
                                if (i == 0) {
                                    return this.mDidAcceptNestedScrollTouch;
                                }
                                if (i != 1) {
                                    return false;
                                }
                                return this.mDidAcceptNestedScrollNonTouch;
                            }

                            public final void resetChangedAfterNestedScroll() {
                                this.mDidChangeAfterNestedScroll = false;
                            }

                            public final void resetTouchBehaviorTracking() {
                                this.mDidBlockInteraction = false;
                            }

                            public void setAnchorId(@IdRes int i) {
                                this.mAnchorDirectChild = null;
                                this.mAnchorView = null;
                                this.mAnchorId = i;
                            }

                            public void setBehavior(@Nullable Behavior behavior) {
                                Behavior behavior2 = this.mBehavior;
                                if (behavior2 != behavior) {
                                    if (behavior2 != null) {
                                        behavior2.onDetachedFromLayoutParams();
                                    }
                                    this.mBehavior = behavior;
                                    this.mBehaviorTag = null;
                                    this.mBehaviorResolved = true;
                                    if (behavior != null) {
                                        behavior.onAttachedToLayoutParams(this);
                                    }
                                }
                            }

                            public final void setChangedAfterNestedScroll(boolean z) {
                                this.mDidChangeAfterNestedScroll = z;
                            }

                            public final void setNestedScrollAccepted(int i, boolean z) {
                                if (i == 0) {
                                    this.mDidAcceptNestedScrollTouch = z;
                                } else {
                                    if (i != 1) {
                                        return;
                                    }
                                    this.mDidAcceptNestedScrollNonTouch = z;
                                }
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
                                Behavior behaviorNewInstance;
                                super(context, attributeSet);
                                this.mBehaviorResolved = false;
                                this.gravity = 0;
                                this.anchorGravity = 0;
                                this.keyline = -1;
                                this.mAnchorId = -1;
                                this.insetEdge = 0;
                                this.dodgeInsetEdges = 0;
                                this.mLastChildRect = new Rect();
                                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
                                this.gravity = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
                                this.mAnchorId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
                                this.anchorGravity = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
                                this.keyline = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
                                this.insetEdge = typedArrayObtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
                                this.dodgeInsetEdges = typedArrayObtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
                                int i = R.styleable.CoordinatorLayout_Layout_layout_behavior;
                                boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(i);
                                this.mBehaviorResolved = zHasValue;
                                if (zHasValue) {
                                    String string = typedArrayObtainStyledAttributes.getString(i);
                                    String str = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                                    if (TextUtils.isEmpty(string)) {
                                        behaviorNewInstance = null;
                                    } else {
                                        if (string.startsWith(".")) {
                                            string = context.getPackageName() + string;
                                        } else if (string.indexOf(46) < 0) {
                                            String str2 = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                                            if (!TextUtils.isEmpty(str2)) {
                                                string = str2 + '.' + string;
                                            }
                                        }
                                        try {
                                            ThreadLocal<Map<String, Constructor<Behavior>>> threadLocal = CoordinatorLayout.sConstructors;
                                            Map<String, Constructor<Behavior>> map = threadLocal.get();
                                            if (map == null) {
                                                map = new HashMap<>();
                                                threadLocal.set(map);
                                            }
                                            Constructor<Behavior> constructor = map.get(string);
                                            if (constructor == null) {
                                                constructor = Class.forName(string, false, context.getClassLoader()).getConstructor(CoordinatorLayout.CONSTRUCTOR_PARAMS);
                                                constructor.setAccessible(true);
                                                map.put(string, constructor);
                                            }
                                            behaviorNewInstance = constructor.newInstance(context, attributeSet);
                                        } catch (Exception e) {
                                            throw new RuntimeException(Insets$$ExternalSyntheticOutline0.m("Could not inflate Behavior subclass ", string), e);
                                        }
                                    }
                                    this.mBehavior = behaviorNewInstance;
                                }
                                typedArrayObtainStyledAttributes.recycle();
                                Behavior behavior = this.mBehavior;
                                if (behavior != null) {
                                    behavior.onAttachedToLayoutParams(this);
                                }
                            }

                            public LayoutParams(LayoutParams layoutParams) {
                                super((ViewGroup.MarginLayoutParams) layoutParams);
                                this.mBehaviorResolved = false;
                                this.gravity = 0;
                                this.anchorGravity = 0;
                                this.keyline = -1;
                                this.mAnchorId = -1;
                                this.insetEdge = 0;
                                this.dodgeInsetEdges = 0;
                                this.mLastChildRect = new Rect();
                            }

                            public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
                                super(marginLayoutParams);
                                this.mBehaviorResolved = false;
                                this.gravity = 0;
                                this.anchorGravity = 0;
                                this.keyline = -1;
                                this.mAnchorId = -1;
                                this.insetEdge = 0;
                                this.dodgeInsetEdges = 0;
                                this.mLastChildRect = new Rect();
                            }

                            public LayoutParams(ViewGroup.LayoutParams layoutParams) {
                                super(layoutParams);
                                this.mBehaviorResolved = false;
                                this.gravity = 0;
                                this.anchorGravity = 0;
                                this.keyline = -1;
                                this.mAnchorId = -1;
                                this.insetEdge = 0;
                                this.dodgeInsetEdges = 0;
                                this.mLastChildRect = new Rect();
                            }
                        }
                    }
