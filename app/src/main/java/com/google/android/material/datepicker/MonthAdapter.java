package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import com.google.android.material.R;
import com.google.android.material.timepicker.TimeModel;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class MonthAdapter extends BaseAdapter {
    private static final int NO_DAY_NUMBER = -1;
    public final CalendarConstraints calendarConstraints;
    public CalendarStyle calendarStyle;
    public final DateSelector<?> dateSelector;

    @Nullable
    public final DayViewDecorator dayViewDecorator;
    public final Month month;
    private Collection<Long> previouslySelectedDates;
    public static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendarOf(null).getMaximum(4);
    private static final int MAXIMUM_GRID_CELLS = (UtcDates.getUtcCalendarOf(null).getMaximum(7) + UtcDates.getUtcCalendarOf(null).getMaximum(5)) - 1;

    public MonthAdapter(Month month, DateSelector<?> dateSelector, CalendarConstraints calendarConstraints, @Nullable DayViewDecorator dayViewDecorator) {
        this.month = month;
        this.dateSelector = dateSelector;
        this.calendarConstraints = calendarConstraints;
        this.dayViewDecorator = dayViewDecorator;
        this.previouslySelectedDates = dateSelector.getSelectedDays();
    }

    private String getDayContentDescription(Context context, long j) {
        boolean z;
        boolean z2;
        boolean zIsToday = isToday(j);
        Iterator<Pair<Long, Long>> it = this.dateSelector.getSelectedRanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            Long l = it.next().first;
            if (l != null && l.longValue() == j) {
                z = true;
                break;
            }
        }
        Iterator<Pair<Long, Long>> it2 = this.dateSelector.getSelectedRanges().iterator();
        while (it2.hasNext()) {
            Long l2 = it2.next().second;
            if (l2 != null && l2.longValue() == j) {
                z2 = true;
                return DateStrings.getDayContentDescription(context, j, zIsToday, z, z2);
            }
        }
        z2 = false;
        return DateStrings.getDayContentDescription(context, j, zIsToday, z, z2);
    }

    private void initializeStyles(Context context) {
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
    }

    private boolean isSelected(long j) {
        Iterator<Long> it = this.dateSelector.getSelectedDays().iterator();
        while (it.hasNext()) {
            if (UtcDates.canonicalYearMonthDay(j) == UtcDates.canonicalYearMonthDay(it.next().longValue())) {
                return true;
            }
        }
        return false;
    }

    private boolean isToday(long j) {
        return UtcDates.getTodayCalendar().getTimeInMillis() == j;
    }

    private void updateSelectedState(@Nullable TextView textView, long j, int i) {
        CalendarItemStyle calendarItemStyle;
        boolean z;
        CalendarItemStyle calendarItemStyle2;
        if (textView == null) {
            return;
        }
        Context context = textView.getContext();
        String dayContentDescription = getDayContentDescription(context, j);
        textView.setContentDescription(dayContentDescription);
        boolean zIsValid = this.calendarConstraints.getDateValidator().isValid(j);
        if (zIsValid) {
            textView.setEnabled(true);
            boolean zIsSelected = isSelected(j);
            textView.setSelected(zIsSelected);
            if (zIsSelected) {
                calendarItemStyle2 = this.calendarStyle.selectedDay;
            } else {
                calendarItemStyle2 = isToday(j) ? this.calendarStyle.todayDay : this.calendarStyle.day;
            }
            calendarItemStyle = calendarItemStyle2;
            z = zIsSelected;
        } else {
            textView.setEnabled(false);
            calendarItemStyle = this.calendarStyle.invalidDay;
            z = false;
        }
        DayViewDecorator dayViewDecorator = this.dayViewDecorator;
        if (dayViewDecorator == null || i == -1) {
            calendarItemStyle.styleItem(textView, null);
            return;
        }
        Month month = this.month;
        int i2 = month.year;
        int i3 = month.month;
        boolean z2 = z;
        calendarItemStyle.styleItem(textView, dayViewDecorator.getBackgroundColor(context, i2, i3, i, zIsValid, z2));
        Drawable compoundDrawableLeft = this.dayViewDecorator.getCompoundDrawableLeft(context, i2, i3, i, zIsValid, z2);
        Drawable compoundDrawableTop = this.dayViewDecorator.getCompoundDrawableTop(context, i2, i3, i, zIsValid, z2);
        Drawable compoundDrawableRight = this.dayViewDecorator.getCompoundDrawableRight(context, i2, i3, i, zIsValid, z);
        boolean z3 = z;
        textView.setCompoundDrawables(compoundDrawableLeft, compoundDrawableTop, compoundDrawableRight, this.dayViewDecorator.getCompoundDrawableBottom(context, i2, i3, i, zIsValid, z3));
        textView.setContentDescription(this.dayViewDecorator.getContentDescription(context, i2, i3, i, zIsValid, z3, dayContentDescription));
    }

    private void updateSelectedStateForDate(MaterialCalendarGridView materialCalendarGridView, long j) {
        if (Month.create(j).equals(this.month)) {
            int dayOfMonth = this.month.getDayOfMonth(j);
            updateSelectedState((TextView) materialCalendarGridView.getChildAt(materialCalendarGridView.getAdapter().dayToPosition(dayOfMonth) - materialCalendarGridView.getFirstVisiblePosition()), j, dayOfMonth);
        }
    }

    public final int dayToPosition(int i) {
        return firstPositionInMonth() + (i - 1);
    }

    public final int firstPositionInMonth() {
        return this.month.daysFromStartOfWeekToFirstOfMonth(this.calendarConstraints.getFirstDayOfWeek());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i / this.month.daysInWeek;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public final int lastPositionInMonth() {
        return (firstPositionInMonth() + this.month.daysInMonth) - 1;
    }

    public void updateSelectedStates(MaterialCalendarGridView materialCalendarGridView) {
        Iterator<Long> it = this.previouslySelectedDates.iterator();
        while (it.hasNext()) {
            updateSelectedStateForDate(materialCalendarGridView, it.next().longValue());
        }
        DateSelector<?> dateSelector = this.dateSelector;
        if (dateSelector != null) {
            Iterator<Long> it2 = dateSelector.getSelectedDays().iterator();
            while (it2.hasNext()) {
                updateSelectedStateForDate(materialCalendarGridView, it2.next().longValue());
            }
            this.previouslySelectedDates = this.dateSelector.getSelectedDays();
        }
    }

    @Override // android.widget.Adapter
    @Nullable
    public Long getItem(int i) {
        if (i < firstPositionInMonth() || i > lastPositionInMonth()) {
            return null;
        }
        return Long.valueOf(this.month.getDay((i - firstPositionInMonth()) + 1));
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0056  */
    @Override // android.widget.Adapter
    @NonNull
    public TextView getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        initializeStyles(viewGroup.getContext());
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_day, viewGroup, false);
        }
        int iFirstPositionInMonth = i - firstPositionInMonth();
        int i2 = -1;
        if (iFirstPositionInMonth >= 0) {
            Month month = this.month;
            if (iFirstPositionInMonth >= month.daysInMonth) {
                textView.setVisibility(8);
                textView.setEnabled(false);
            } else {
                int i3 = iFirstPositionInMonth + 1;
                textView.setTag(month);
                textView.setText(String.format(textView.getResources().getConfiguration().locale, TimeModel.NUMBER_FORMAT, Integer.valueOf(i3)));
                textView.setVisibility(0);
                textView.setEnabled(true);
                i2 = i3;
            }
        } else {
            textView.setVisibility(8);
            textView.setEnabled(false);
        }
        Long item = getItem(i);
        if (item == null) {
            return textView;
        }
        updateSelectedState(textView, item.longValue(), i2);
        return textView;
    }
}
