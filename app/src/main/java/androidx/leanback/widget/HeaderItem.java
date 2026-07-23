package androidx.leanback.widget;

/* JADX INFO: loaded from: classes.dex */
public class HeaderItem {
    private CharSequence mContentDescription;
    private CharSequence mDescription;
    private final long mId;
    private final String mName;

    public HeaderItem(long j, String str) {
        this.mId = j;
        this.mName = str;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public final long getId() {
        return this.mId;
    }

    public final String getName() {
        return this.mName;
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
    }

    public void setDescription(CharSequence charSequence) {
        this.mDescription = charSequence;
    }

    public HeaderItem(String str) {
        this(-1L, str);
    }
}
