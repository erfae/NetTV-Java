package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.R;
import com.google.android.material.timepicker.TimeModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DatePicker extends Picker {
    private static final int[] DATE_FIELDS = {5, 2, 1};
    public int mColDayIndex;
    public int mColMonthIndex;
    public int mColYearIndex;
    public PickerUtility.DateConstant mConstant;
    public Calendar mCurrentDate;
    public final SimpleDateFormat mDateFormat;
    private String mDatePickerFormat;
    public PickerColumn mDayColumn;
    public Calendar mMaxDate;
    public Calendar mMinDate;
    public PickerColumn mMonthColumn;
    public Calendar mTempDate;
    public PickerColumn mYearColumn;

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private static boolean isAnyOf(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    private boolean isNewDate(int i, int i2, int i3) {
        return (this.mCurrentDate.get(1) == i && this.mCurrentDate.get(2) == i3 && this.mCurrentDate.get(5) == i2) ? false : true;
    }

    private boolean parseDate(String str, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (ParseException unused) {
            Log.w("DatePicker", "Date: " + str + " not in format: MM/dd/yyyy");
            return false;
        }
    }

    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(i, i2, i3);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    private void updateCurrentLocale() {
        PickerUtility.DateConstant dateConstantInstance = PickerUtility.getDateConstantInstance(Locale.getDefault(), getContext().getResources());
        this.mConstant = dateConstantInstance;
        this.mTempDate = PickerUtility.getCalendarForLocale(this.mTempDate, dateConstantInstance.locale);
        this.mMinDate = PickerUtility.getCalendarForLocale(this.mMinDate, this.mConstant.locale);
        this.mMaxDate = PickerUtility.getCalendarForLocale(this.mMaxDate, this.mConstant.locale);
        this.mCurrentDate = PickerUtility.getCalendarForLocale(this.mCurrentDate, this.mConstant.locale);
        PickerColumn pickerColumn = this.mMonthColumn;
        if (pickerColumn != null) {
            pickerColumn.setStaticLabels(this.mConstant.months);
            setColumnAt(this.mColMonthIndex, this.mMonthColumn);
        }
    }

    private static boolean updateMax(PickerColumn pickerColumn, int i) {
        if (i == pickerColumn.getMaxValue()) {
            return false;
        }
        pickerColumn.setMaxValue(i);
        return true;
    }

    private static boolean updateMin(PickerColumn pickerColumn, int i) {
        if (i == pickerColumn.getMinValue()) {
            return false;
        }
        pickerColumn.setMinValue(i);
        return true;
    }

    private void updateSpinners(final boolean z) {
        post(new Runnable() { // from class: androidx.leanback.widget.picker.DatePicker.1
            @Override // java.lang.Runnable
            public void run() {
                DatePicker.this.updateSpinnersImpl(z);
            }
        });
    }

    public long getDate() {
        return this.mCurrentDate.getTimeInMillis();
    }

    public String getDatePickerFormat() {
        return this.mDatePickerFormat;
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    @Override // androidx.leanback.widget.picker.Picker
    public final void onColumnValueChanged(int i, int i2) {
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        int currentValue = getColumnAt(i).getCurrentValue();
        if (i == this.mColDayIndex) {
            this.mTempDate.add(5, i2 - currentValue);
        } else if (i == this.mColMonthIndex) {
            this.mTempDate.add(2, i2 - currentValue);
        } else {
            if (i != this.mColYearIndex) {
                throw new IllegalArgumentException();
            }
            this.mTempDate.add(1, i2 - currentValue);
        }
        setDate(this.mTempDate.get(1), this.mTempDate.get(2), this.mTempDate.get(5));
        updateSpinners(false);
    }

    public void setDatePickerFormat(String str) {
        if (TextUtils.isEmpty(str)) {
            str = new String(DateFormat.getDateFormatOrder(getContext()));
        }
        if (TextUtils.equals(this.mDatePickerFormat, str)) {
            return;
        }
        this.mDatePickerFormat = str;
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mConstant.locale, str);
        if (TextUtils.isEmpty(bestDateTimePattern)) {
            bestDateTimePattern = "MM/dd/yyyy";
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        char[] cArr = {'Y', 'y', 'M', 'm', 'D', 'd'};
        boolean z = false;
        char c = 0;
        for (int i = 0; i < bestDateTimePattern.length(); i++) {
            char cCharAt = bestDateTimePattern.charAt(i);
            if (cCharAt != ' ') {
                if (cCharAt != '\'') {
                    if (z || !isAnyOf(cCharAt, cArr)) {
                        sb.append(cCharAt);
                    } else if (cCharAt != c) {
                        arrayList.add(sb.toString());
                        sb.setLength(0);
                    }
                    c = cCharAt;
                } else if (z) {
                    z = false;
                } else {
                    sb.setLength(0);
                    z = true;
                }
            }
        }
        arrayList.add(sb.toString());
        if (arrayList.size() != str.length() + 1) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Separators size: ");
            sbM.append(arrayList.size());
            sbM.append(" must equal");
            sbM.append(" the size of datePickerFormat: ");
            sbM.append(str.length());
            sbM.append(" + 1");
            throw new IllegalStateException(sbM.toString());
        }
        setSeparators(arrayList);
        this.mDayColumn = null;
        this.mMonthColumn = null;
        this.mYearColumn = null;
        this.mColMonthIndex = -1;
        this.mColDayIndex = -1;
        this.mColYearIndex = -1;
        String upperCase = str.toUpperCase();
        ArrayList arrayList2 = new ArrayList(3);
        for (int i2 = 0; i2 < upperCase.length(); i2++) {
            char cCharAt2 = upperCase.charAt(i2);
            if (cCharAt2 == 'D') {
                if (this.mDayColumn != null) {
                    throw new IllegalArgumentException("datePicker format error");
                }
                PickerColumn pickerColumn = new PickerColumn();
                this.mDayColumn = pickerColumn;
                arrayList2.add(pickerColumn);
                this.mDayColumn.setLabelFormat(TimeModel.ZERO_LEADING_NUMBER_FORMAT);
                this.mColDayIndex = i2;
            } else if (cCharAt2 != 'M') {
                if (cCharAt2 != 'Y') {
                    throw new IllegalArgumentException("datePicker format error");
                }
                if (this.mYearColumn != null) {
                    throw new IllegalArgumentException("datePicker format error");
                }
                PickerColumn pickerColumn2 = new PickerColumn();
                this.mYearColumn = pickerColumn2;
                arrayList2.add(pickerColumn2);
                this.mColYearIndex = i2;
                this.mYearColumn.setLabelFormat(TimeModel.NUMBER_FORMAT);
            } else {
                if (this.mMonthColumn != null) {
                    throw new IllegalArgumentException("datePicker format error");
                }
                PickerColumn pickerColumn3 = new PickerColumn();
                this.mMonthColumn = pickerColumn3;
                arrayList2.add(pickerColumn3);
                this.mMonthColumn.setStaticLabels(this.mConstant.months);
                this.mColMonthIndex = i2;
            }
        }
        setColumns(arrayList2);
        updateSpinners(false);
    }

    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMaxDate.get(1) || this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            this.mMaxDate.setTimeInMillis(j);
            if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMinDate.get(1) || this.mTempDate.get(6) == this.mMinDate.get(6)) {
            this.mMinDate.setTimeInMillis(j);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public void updateDate(int i, int i2, int i3, boolean z) {
        if (isNewDate(i, i2, i3)) {
            setDate(i, i2, i3);
            updateSpinners(z);
        }
    }

    public final void updateSpinnersImpl(boolean z) {
        int[] iArr = {this.mColDayIndex, this.mColMonthIndex, this.mColYearIndex};
        boolean z2 = true;
        boolean z3 = true;
        for (int length = DATE_FIELDS.length - 1; length >= 0; length--) {
            if (iArr[length] >= 0) {
                int i = DATE_FIELDS[length];
                PickerColumn columnAt = getColumnAt(iArr[length]);
                boolean zUpdateMin = (z2 ? updateMin(columnAt, this.mMinDate.get(i)) : updateMin(columnAt, this.mCurrentDate.getActualMinimum(i))) | false | (z3 ? updateMax(columnAt, this.mMaxDate.get(i)) : updateMax(columnAt, this.mCurrentDate.getActualMaximum(i)));
                z2 &= this.mCurrentDate.get(i) == this.mMinDate.get(i);
                z3 &= this.mCurrentDate.get(i) == this.mMaxDate.get(i);
                if (zUpdateMin) {
                    setColumnAt(iArr[length], columnAt);
                }
                setColumnValue(iArr[length], this.mCurrentDate.get(i), z);
            }
        }
    }

    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        updateCurrentLocale();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbDatePicker);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.lbDatePicker_android_minDate);
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.lbDatePicker_android_maxDate);
        this.mTempDate.clear();
        if (TextUtils.isEmpty(string) || !parseDate(string, this.mTempDate)) {
            this.mTempDate.set(1900, 0, 1);
        }
        this.mMinDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
        this.mTempDate.clear();
        if (TextUtils.isEmpty(string2) || !parseDate(string2, this.mTempDate)) {
            this.mTempDate.set(2100, 0, 1);
        }
        this.mMaxDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
        String string3 = typedArrayObtainStyledAttributes.getString(R.styleable.lbDatePicker_datePickerFormat);
        setDatePickerFormat(TextUtils.isEmpty(string3) ? new String(DateFormat.getDateFormatOrder(context)) : string3);
    }
}
