package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import com.google.android.material.R;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class DateStrings {
    private DateStrings() {
    }

    public static Pair<String, String> getDateRangeString(@Nullable Long l, @Nullable Long l2) {
        if (l == null && l2 == null) {
            return Pair.create(null, null);
        }
        if (l == null) {
            return Pair.create(null, getDateString$1(l2.longValue()));
        }
        if (l2 == null) {
            return Pair.create(getDateString$1(l.longValue()), null);
        }
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf.setTimeInMillis(l.longValue());
        Calendar utcCalendarOf2 = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf2.setTimeInMillis(l2.longValue());
        if (utcCalendarOf.get(1) == utcCalendarOf2.get(1)) {
            return utcCalendarOf.get(1) == todayCalendar.get(1) ? Pair.create(getMonthDay(l.longValue(), Locale.getDefault()), getMonthDay(l2.longValue(), Locale.getDefault())) : Pair.create(getMonthDay(l.longValue(), Locale.getDefault()), getYearMonthDay(l2.longValue(), Locale.getDefault()));
        }
        return Pair.create(getYearMonthDay(l.longValue(), Locale.getDefault()), getYearMonthDay(l2.longValue(), Locale.getDefault()));
    }

    public static String getDateString$1(long j) {
        return isDateWithinCurrentYear(j) ? getMonthDay(j, Locale.getDefault()) : getYearMonthDay(j);
    }

    public static String getDayContentDescription(Context context, long j, boolean z, boolean z2, boolean z3) {
        String str;
        if (isDateWithinCurrentYear(j)) {
            Locale locale = Locale.getDefault();
            str = Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthWeekdayDayFormat(locale).format(new Date(j)) : UtcDates.getFullFormat(locale).format(new Date(j));
        } else {
            Locale locale2 = Locale.getDefault();
            str = Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthWeekdayDayFormat(locale2).format(new Date(j)) : UtcDates.getFullFormat(locale2).format(new Date(j));
        }
        if (z) {
            str = String.format(context.getString(R.string.mtrl_picker_today_description), str);
        }
        if (z2) {
            return String.format(context.getString(R.string.mtrl_picker_start_date_description), str);
        }
        return z3 ? String.format(context.getString(R.string.mtrl_picker_end_date_description), str) : str;
    }

    public static String getMonthDay(long j, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthDayFormat(locale).format(new Date(j)) : UtcDates.getMediumNoYear(locale).format(new Date(j));
    }

    public static String getYearMonthDay(long j) {
        return getYearMonthDay(j, Locale.getDefault());
    }

    private static boolean isDateWithinCurrentYear(long j) {
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf(null);
        utcCalendarOf.setTimeInMillis(j);
        return todayCalendar.get(1) == utcCalendarOf.get(1);
    }

    public static String getYearMonthDay(long j, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthDayFormat(locale).format(new Date(j)) : UtcDates.getMediumFormat(locale).format(new Date(j));
    }
}
