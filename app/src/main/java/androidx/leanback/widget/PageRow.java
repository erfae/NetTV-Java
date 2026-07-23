package androidx.leanback.widget;

/* JADX INFO: loaded from: classes.dex */
public class PageRow extends Row {
    public PageRow(HeaderItem headerItem) {
        super(headerItem);
    }

    @Override // androidx.leanback.widget.Row
    public final boolean isRenderedAsRowView() {
        return false;
    }
}
