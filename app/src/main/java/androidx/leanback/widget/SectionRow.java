package androidx.leanback.widget;

/* JADX INFO: loaded from: classes.dex */
public class SectionRow extends Row {
    public SectionRow(HeaderItem headerItem) {
        super(headerItem);
    }

    @Override // androidx.leanback.widget.Row
    public final boolean isRenderedAsRowView() {
        return false;
    }

    public SectionRow(long j, String str) {
        super(new HeaderItem(j, str));
    }

    public SectionRow(String str) {
        super(new HeaderItem(str));
    }
}
