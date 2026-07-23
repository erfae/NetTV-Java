package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import androidx.annotation.IntRange;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class TimePicker extends Picker {
    private static final int AM_INDEX = 0;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int PM_INDEX = 1;
    public PickerColumn mAmPmColumn;
    public int mColAmPmIndex;
    public int mColHourIndex;
    public int mColMinuteIndex;
    private final PickerUtility.TimeConstant mConstant;
    private int mCurrentAmPmIndex;
    private int mCurrentHour;
    private int mCurrentMinute;
    public PickerColumn mHourColumn;
    private boolean mIs24hFormat;
    public PickerColumn mMinuteColumn;
    private String mTimePickerFormat;

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private String extractTimeFields() {
        String bestHourMinutePattern = getBestHourMinutePattern();
        boolean z = TextUtils.getLayoutDirectionFromLocale(this.mConstant.locale) == 1;
        boolean z2 = bestHourMinutePattern.indexOf(97) < 0 || bestHourMinutePattern.indexOf("a") > bestHourMinutePattern.indexOf("m");
        String str = z ? "mh" : "hm";
        if (is24Hour()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (z2) {
            sb.append(str);
            sb.append("a");
        } else {
            sb.append("a");
            sb.append(str);
        }
        return sb.toString();
    }

    private static boolean isAnyOf(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    private void setAmPmValue() {
        if (is24Hour()) {
            return;
        }
        setColumnValue(this.mColAmPmIndex, this.mCurrentAmPmIndex, false);
    }

    private void updateColumns() {
        String bestHourMinutePattern = getBestHourMinutePattern();
        if (TextUtils.equals(bestHourMinutePattern, this.mTimePickerFormat)) {
            return;
        }
        this.mTimePickerFormat = bestHourMinutePattern;
        String strExtractTimeFields = extractTimeFields();
        String bestHourMinutePattern2 = getBestHourMinutePattern();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        char[] cArr = {'H', 'h', 'K', 'k', 'm', 'M', 'a'};
        boolean z = false;
        char c = 0;
        for (int i = 0; i < bestHourMinutePattern2.length(); i++) {
            char cCharAt = bestHourMinutePattern2.charAt(i);
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
        if (arrayList.size() != strExtractTimeFields.length() + 1) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Separators size: ");
            sbM.append(arrayList.size());
            sbM.append(" must equal");
            sbM.append(" the size of timeFieldsPattern: ");
            sbM.append(strExtractTimeFields.length());
            sbM.append(" + 1");
            throw new IllegalStateException(sbM.toString());
        }
        setSeparators(arrayList);
        String upperCase = strExtractTimeFields.toUpperCase();
        this.mAmPmColumn = null;
        this.mMinuteColumn = null;
        this.mHourColumn = null;
        this.mColAmPmIndex = -1;
        this.mColMinuteIndex = -1;
        this.mColHourIndex = -1;
        ArrayList arrayList2 = new ArrayList(3);
        for (int i2 = 0; i2 < upperCase.length(); i2++) {
            char cCharAt2 = upperCase.charAt(i2);
            if (cCharAt2 == 'A') {
                PickerColumn pickerColumn = new PickerColumn();
                this.mAmPmColumn = pickerColumn;
                arrayList2.add(pickerColumn);
                this.mAmPmColumn.setStaticLabels(this.mConstant.ampm);
                this.mColAmPmIndex = i2;
                updateMin(this.mAmPmColumn, 0);
                updateMax(this.mAmPmColumn, 1);
            } else if (cCharAt2 == 'H') {
                PickerColumn pickerColumn2 = new PickerColumn();
                this.mHourColumn = pickerColumn2;
                arrayList2.add(pickerColumn2);
                this.mHourColumn.setStaticLabels(this.mConstant.hours24);
                this.mColHourIndex = i2;
            } else {
                if (cCharAt2 != 'M') {
                    throw new IllegalArgumentException("Invalid time picker format.");
                }
                PickerColumn pickerColumn3 = new PickerColumn();
                this.mMinuteColumn = pickerColumn3;
                arrayList2.add(pickerColumn3);
                this.mMinuteColumn.setStaticLabels(this.mConstant.minutes);
                this.mColMinuteIndex = i2;
            }
        }
        setColumns(arrayList2);
    }

    private void updateColumnsRange() {
        updateMin(this.mHourColumn, !this.mIs24hFormat ? 1 : 0);
        updateMax(this.mHourColumn, this.mIs24hFormat ? 23 : 12);
        updateMin(this.mMinuteColumn, 0);
        updateMax(this.mMinuteColumn, 59);
        PickerColumn pickerColumn = this.mAmPmColumn;
        if (pickerColumn != null) {
            updateMin(pickerColumn, 0);
            updateMax(this.mAmPmColumn, 1);
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

    public String getBestHourMinutePattern() {
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mConstant.locale, this.mIs24hFormat ? "Hma" : "hma");
        return TextUtils.isEmpty(bestDateTimePattern) ? "h:mma" : bestDateTimePattern;
    }

    public int getHour() {
        if (this.mIs24hFormat) {
            return this.mCurrentHour;
        }
        return this.mCurrentAmPmIndex == 0 ? this.mCurrentHour % 12 : (this.mCurrentHour % 12) + 12;
    }

    public int getMinute() {
        return this.mCurrentMinute;
    }

    public boolean is24Hour() {
        return this.mIs24hFormat;
    }

    public boolean isPm() {
        return this.mCurrentAmPmIndex == 1;
    }

    @Override // androidx.leanback.widget.picker.Picker
    public void onColumnValueChanged(int i, int i2) {
        if (i == this.mColHourIndex) {
            this.mCurrentHour = i2;
        } else if (i == this.mColMinuteIndex) {
            this.mCurrentMinute = i2;
        } else {
            if (i != this.mColAmPmIndex) {
                throw new IllegalArgumentException("Invalid column index.");
            }
            this.mCurrentAmPmIndex = i2;
        }
    }

    public void setHour(@IntRange(from = 0, to = 23) int i) {
        if (i < 0 || i > 23) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("hour: ", i, " is not in [0-23] range in"));
        }
        this.mCurrentHour = i;
        if (!is24Hour()) {
            int i2 = this.mCurrentHour;
            if (i2 >= 12) {
                this.mCurrentAmPmIndex = 1;
                if (i2 > 12) {
                    this.mCurrentHour = i2 - 12;
                }
            } else {
                this.mCurrentAmPmIndex = 0;
                if (i2 == 0) {
                    this.mCurrentHour = 12;
                }
            }
            setAmPmValue();
        }
        setColumnValue(this.mColHourIndex, this.mCurrentHour, false);
    }

    public void setIs24Hour(boolean z) {
        if (this.mIs24hFormat == z) {
            return;
        }
        int hour = getHour();
        int minute = getMinute();
        this.mIs24hFormat = z;
        updateColumns();
        updateColumnsRange();
        setHour(hour);
        setMinute(minute);
        setAmPmValue();
    }

    public void setMinute(@IntRange(from = 0, to = 59) int i) {
        if (i < 0 || i > 59) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("minute: ", i, " is not in [0-59] range."));
        }
        this.mCurrentMinute = i;
        setColumnValue(this.mColMinuteIndex, i, false);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        PickerUtility.TimeConstant timeConstantInstance = PickerUtility.getTimeConstantInstance(Locale.getDefault(), context.getResources());
        this.mConstant = timeConstantInstance;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbTimePicker);
        this.mIs24hFormat = typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbTimePicker_is24HourFormat, DateFormat.is24HourFormat(context));
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbTimePicker_useCurrentTime, true);
        updateColumns();
        updateColumnsRange();
        if (z) {
            Calendar calendarForLocale = PickerUtility.getCalendarForLocale(null, timeConstantInstance.locale);
            setHour(calendarForLocale.get(11));
            setMinute(calendarForLocale.get(12));
            setAmPmValue();
        }
    }
}
