package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class VerticalGridView extends BaseGridView {
    public VerticalGridView(Context context) {
        this(context, null);
    }

    public void setColumnWidth(TypedArray typedArray) {
        int i = R.styleable.lbVerticalGridView_columnWidth;
        if (typedArray.peekValue(i) != null) {
            setColumnWidth(typedArray.getLayoutDimension(i, 0));
        }
    }

    public void setNumColumns(int i) {
        this.mLayoutManager.setNumRows(i);
        requestLayout();
    }

    public VerticalGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutManager.setOrientation(1);
        initBaseGridViewAttributes(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbVerticalGridView);
        setColumnWidth(typedArrayObtainStyledAttributes);
        setNumColumns(typedArrayObtainStyledAttributes.getInt(R.styleable.lbVerticalGridView_numberOfColumns, 1));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setColumnWidth(int i) {
        this.mLayoutManager.setRowHeight(i);
        requestLayout();
    }
}
