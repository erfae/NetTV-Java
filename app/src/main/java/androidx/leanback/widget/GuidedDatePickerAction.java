package androidx.leanback.widget;

import android.content.Context;
import android.os.Bundle;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class GuidedDatePickerAction extends GuidedAction {
    public long mDate;
    public String mDatePickerFormat;
    public long mMinDate = Long.MIN_VALUE;
    public long mMaxDate = Long.MAX_VALUE;

    public static final class Builder extends BuilderBase<Builder> {
        public Builder(Context context) {
            super(context);
        }

        public GuidedDatePickerAction build() {
            GuidedDatePickerAction guidedDatePickerAction = new GuidedDatePickerAction();
            applyDatePickerValues(guidedDatePickerAction);
            return guidedDatePickerAction;
        }
    }

    public static abstract class BuilderBase<B extends BuilderBase> extends GuidedAction.BuilderBase<B> {
        private long mDate;
        private String mDatePickerFormat;
        private long mMaxDate;
        private long mMinDate;

        public BuilderBase(Context context) {
            super(context);
            this.mMinDate = Long.MIN_VALUE;
            this.mMaxDate = Long.MAX_VALUE;
            this.mDate = Calendar.getInstance().getTimeInMillis();
            hasEditableActivatorView(true);
        }

        public final void applyDatePickerValues(GuidedDatePickerAction guidedDatePickerAction) {
            applyValues(guidedDatePickerAction);
            guidedDatePickerAction.mDatePickerFormat = this.mDatePickerFormat;
            guidedDatePickerAction.mDate = this.mDate;
            long j = this.mMinDate;
            long j2 = this.mMaxDate;
            if (j > j2) {
                throw new IllegalArgumentException("MinDate cannot be larger than MaxDate");
            }
            guidedDatePickerAction.mMinDate = j;
            guidedDatePickerAction.mMaxDate = j2;
        }

        public B date(long j) {
            this.mDate = j;
            return this;
        }

        public B datePickerFormat(String str) {
            this.mDatePickerFormat = str;
            return this;
        }

        public B maxDate(long j) {
            this.mMaxDate = j;
            return this;
        }

        public B minDate(long j) {
            this.mMinDate = j;
            return this;
        }
    }

    public long getDate() {
        return this.mDate;
    }

    public String getDatePickerFormat() {
        return this.mDatePickerFormat;
    }

    public long getMaxDate() {
        return this.mMaxDate;
    }

    public long getMinDate() {
        return this.mMinDate;
    }

    @Override // androidx.leanback.widget.GuidedAction
    public void onRestoreInstanceState(Bundle bundle, String str) {
        setDate(bundle.getLong(str, getDate()));
    }

    @Override // androidx.leanback.widget.GuidedAction
    public void onSaveInstanceState(Bundle bundle, String str) {
        bundle.putLong(str, getDate());
    }

    public void setDate(long j) {
        this.mDate = j;
    }
}
