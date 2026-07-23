package com.google.android.exoplayer2.text.ttml;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class TextEmphasis {
    public static final int MARK_SHAPE_AUTO = -1;
    public static final int POSITION_OUTSIDE = -2;
    public final int markFill;
    public final int markShape;
    public final int position;
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private static final ImmutableSet<String> SINGLE_STYLE_VALUES = ImmutableSet.of("auto", "none");
    private static final ImmutableSet<String> MARK_SHAPE_VALUES = ImmutableSet.of(TtmlNode.TEXT_EMPHASIS_MARK_DOT, TtmlNode.TEXT_EMPHASIS_MARK_SESAME, TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE);
    private static final ImmutableSet<String> MARK_FILL_VALUES = ImmutableSet.of(TtmlNode.TEXT_EMPHASIS_MARK_FILLED, TtmlNode.TEXT_EMPHASIS_MARK_OPEN);
    private static final ImmutableSet<String> POSITION_VALUES = ImmutableSet.of(TtmlNode.ANNOTATION_POSITION_AFTER, TtmlNode.ANNOTATION_POSITION_BEFORE, TtmlNode.ANNOTATION_POSITION_OUTSIDE);

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
    }

    private TextEmphasis(int i, int i2, int i3) {
        this.markShape = i;
        this.markFill = i2;
        this.position = i3;
    }

    @Nullable
    public static TextEmphasis parse(@Nullable String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = Ascii.toLowerCase(str.trim());
        if (lowerCase.isEmpty()) {
            return null;
        }
        return parseWords(ImmutableSet.copyOf(TextUtils.split(lowerCase, WHITESPACE_PATTERN)));
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0042  */
    /* JADX WARN: Code duplicated, block: B:37:0x0084  */
    /* JADX WARN: Code duplicated, block: B:60:0x00d6  */
    private static TextEmphasis parseWords(ImmutableSet<String> immutableSet) {
        byte b;
        int i;
        byte b2;
        String str = (String) Iterables.getFirst(Sets.intersection(POSITION_VALUES, immutableSet), TtmlNode.ANNOTATION_POSITION_OUTSIDE);
        int iHashCode = str.hashCode();
        int i2 = 2;
        int i3 = -1;
        byte b3 = 1;
        if (iHashCode != -1392885889) {
            if (iHashCode != -1106037339) {
                if (iHashCode == 92734940 && str.equals(TtmlNode.ANNOTATION_POSITION_AFTER)) {
                    b = 0;
                } else {
                    b = -1;
                }
            } else if (str.equals(TtmlNode.ANNOTATION_POSITION_OUTSIDE)) {
                b = 1;
            } else {
                b = -1;
            }
        } else if (str.equals(TtmlNode.ANNOTATION_POSITION_BEFORE)) {
            b = 2;
        } else {
            b = -1;
        }
        if (b != 0) {
            i = b != 1 ? 1 : -2;
        } else {
            i = 2;
        }
        Sets.SetView setViewIntersection = Sets.intersection(SINGLE_STYLE_VALUES, immutableSet);
        if (!setViewIntersection.isEmpty()) {
            String str2 = (String) setViewIntersection.iterator().next();
            int iHashCode2 = str2.hashCode();
            if (iHashCode2 != 3005871) {
                if (iHashCode2 == 3387192 && str2.equals("none")) {
                    b3 = 0;
                } else {
                    b3 = -1;
                }
            } else if (!str2.equals("auto")) {
                b3 = -1;
            }
            return new TextEmphasis(b3 == 0 ? 0 : -1, 0, i);
        }
        Sets.SetView setViewIntersection2 = Sets.intersection(MARK_FILL_VALUES, immutableSet);
        Sets.SetView setViewIntersection3 = Sets.intersection(MARK_SHAPE_VALUES, immutableSet);
        if (setViewIntersection2.isEmpty() && setViewIntersection3.isEmpty()) {
            return new TextEmphasis(-1, 0, i);
        }
        String str3 = (String) Iterables.getFirst(setViewIntersection2, TtmlNode.TEXT_EMPHASIS_MARK_FILLED);
        int iHashCode3 = str3.hashCode();
        if (iHashCode3 != -1274499742) {
            if (iHashCode3 == 3417674 && str3.equals(TtmlNode.TEXT_EMPHASIS_MARK_OPEN)) {
                b2 = 0;
            } else {
                b2 = -1;
            }
        } else if (str3.equals(TtmlNode.TEXT_EMPHASIS_MARK_FILLED)) {
            b2 = 1;
        } else {
            b2 = -1;
        }
        int i4 = b2 != 0 ? 1 : 2;
        String str4 = (String) Iterables.getFirst(setViewIntersection3, TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE);
        int iHashCode4 = str4.hashCode();
        if (iHashCode4 != -1360216880) {
            if (iHashCode4 != -905816648) {
                if (iHashCode4 == 99657 && str4.equals(TtmlNode.TEXT_EMPHASIS_MARK_DOT)) {
                    i3 = 0;
                }
            } else if (str4.equals(TtmlNode.TEXT_EMPHASIS_MARK_SESAME)) {
                i3 = 1;
            }
        } else if (str4.equals(TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE)) {
            i3 = 2;
        }
        if (i3 != 0) {
            i2 = i3 != 1 ? 1 : 3;
        }
        return new TextEmphasis(i2, i4, i);
    }
}
