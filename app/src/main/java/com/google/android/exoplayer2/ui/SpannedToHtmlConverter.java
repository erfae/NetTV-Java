package com.google.android.exoplayer2.ui;

import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class SpannedToHtmlConverter {
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("(&#13;)?&#10;");

    public static class HtmlAndCss {
        public final Map<String, String> cssRuleSets;
        public final String html;

        private HtmlAndCss(String str, Map<String, String> map) {
            this.html = str;
            this.cssRuleSets = map;
        }
    }

    public static final class SpanInfo {
        public final String closingTag;
        public final int end;
        public final String openingTag;
        public final int start;
        private static final Comparator<SpanInfo> FOR_OPENING_TAGS = SpannedToHtmlConverter$SpanInfo$$ExternalSyntheticLambda0.INSTANCE;
        private static final Comparator<SpanInfo> FOR_CLOSING_TAGS = SpannedToHtmlConverter$SpanInfo$$ExternalSyntheticLambda0.INSTANCE$1;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$static$0(SpanInfo spanInfo, SpanInfo spanInfo2) {
            int iCompare = Integer.compare(spanInfo2.end, spanInfo.end);
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompareTo = spanInfo.openingTag.compareTo(spanInfo2.openingTag);
            return iCompareTo != 0 ? iCompareTo : spanInfo.closingTag.compareTo(spanInfo2.closingTag);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$static$1(SpanInfo spanInfo, SpanInfo spanInfo2) {
            int iCompare = Integer.compare(spanInfo2.start, spanInfo.start);
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompareTo = spanInfo2.openingTag.compareTo(spanInfo.openingTag);
            return iCompareTo != 0 ? iCompareTo : spanInfo2.closingTag.compareTo(spanInfo.closingTag);
        }

        private SpanInfo(int i, int i2, String str, String str2) {
            this.start = i;
            this.end = i2;
            this.openingTag = str;
            this.closingTag = str2;
        }
    }

    public static final class Transition {
        private final List<SpanInfo> spansAdded = new ArrayList();
        private final List<SpanInfo> spansRemoved = new ArrayList();
    }

    private SpannedToHtmlConverter() {
    }

    public static HtmlAndCss convert(@Nullable CharSequence charSequence, float f) {
        if (charSequence == null) {
            return new HtmlAndCss("", ImmutableMap.of());
        }
        if (!(charSequence instanceof Spanned)) {
            return new HtmlAndCss(escapeHtml(charSequence), ImmutableMap.of());
        }
        Spanned spanned = (Spanned) charSequence;
        HashSet hashSet = new HashSet();
        int i = 0;
        for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) spanned.getSpans(0, spanned.length(), BackgroundColorSpan.class)) {
            hashSet.add(Integer.valueOf(backgroundColorSpan.getBackgroundColor()));
        }
        HashMap map = new HashMap();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            map.put(HtmlUtils.cssAllClassDescendantsSelector(Insets$$ExternalSyntheticOutline0.m23m("bg_", iIntValue)), Util.formatInvariant("background-color:%s;", HtmlUtils.toCssRgba(iIntValue)));
        }
        SparseArray<Transition> sparseArrayFindSpanTransitions = findSpanTransitions(spanned, f);
        StringBuilder sb = new StringBuilder(spanned.length());
        int i2 = 0;
        while (i < sparseArrayFindSpanTransitions.size()) {
            int iKeyAt = sparseArrayFindSpanTransitions.keyAt(i);
            sb.append(escapeHtml(spanned.subSequence(i2, iKeyAt)));
            Transition transition = sparseArrayFindSpanTransitions.get(iKeyAt);
            Collections.sort(transition.spansRemoved, SpanInfo.FOR_CLOSING_TAGS);
            Iterator it2 = transition.spansRemoved.iterator();
            while (it2.hasNext()) {
                sb.append(((SpanInfo) it2.next()).closingTag);
            }
            Collections.sort(transition.spansAdded, SpanInfo.FOR_OPENING_TAGS);
            Iterator it3 = transition.spansAdded.iterator();
            while (it3.hasNext()) {
                sb.append(((SpanInfo) it3.next()).openingTag);
            }
            i++;
            i2 = iKeyAt;
        }
        sb.append(escapeHtml(spanned.subSequence(i2, spanned.length())));
        return new HtmlAndCss(sb.toString(), map);
    }

    private static String escapeHtml(CharSequence charSequence) {
        return NEWLINE_PATTERN.matcher(Html.escapeHtml(charSequence)).replaceAll("<br>");
    }

    private static SparseArray<Transition> findSpanTransitions(Spanned spanned, float f) {
        SparseArray<Transition> sparseArray = new SparseArray<>();
        for (Object obj : spanned.getSpans(0, spanned.length(), Object.class)) {
            String openingTag = getOpeningTag(obj, f);
            String closingTag = getClosingTag(obj);
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (openingTag != null) {
                Assertions.checkNotNull(closingTag);
                SpanInfo spanInfo = new SpanInfo(spanStart, spanEnd, openingTag, closingTag);
                getOrCreate(sparseArray, spanStart).spansAdded.add(spanInfo);
                getOrCreate(sparseArray, spanEnd).spansRemoved.add(spanInfo);
            }
        }
        return sparseArray;
    }

    @Nullable
    private static String getClosingTag(Object obj) {
        if ((obj instanceof StrikethroughSpan) || (obj instanceof ForegroundColorSpan) || (obj instanceof BackgroundColorSpan) || (obj instanceof HorizontalTextInVerticalContextSpan) || (obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan) || (obj instanceof TextEmphasisSpan)) {
            return "</span>";
        }
        if (obj instanceof TypefaceSpan) {
            if (((TypefaceSpan) obj).getFamily() != null) {
                return "</span>";
            }
            return null;
        }
        if (obj instanceof StyleSpan) {
            int style = ((StyleSpan) obj).getStyle();
            if (style == 1) {
                return "</b>";
            }
            if (style == 2) {
                return "</i>";
            }
            if (style == 3) {
                return "</i></b>";
            }
        } else {
            if (obj instanceof RubySpan) {
                return Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("<rt>"), escapeHtml(((RubySpan) obj).rubyText), "</rt></ruby>");
            }
            if (obj instanceof UnderlineSpan) {
                return "</u>";
            }
        }
        return null;
    }

    @Nullable
    private static String getOpeningTag(Object obj, float f) {
        if (obj instanceof StrikethroughSpan) {
            return "<span style='text-decoration:line-through;'>";
        }
        if (obj instanceof ForegroundColorSpan) {
            return Util.formatInvariant("<span style='color:%s;'>", HtmlUtils.toCssRgba(((ForegroundColorSpan) obj).getForegroundColor()));
        }
        if (obj instanceof BackgroundColorSpan) {
            return Util.formatInvariant("<span class='bg_%s'>", Integer.valueOf(((BackgroundColorSpan) obj).getBackgroundColor()));
        }
        if (obj instanceof HorizontalTextInVerticalContextSpan) {
            return "<span style='text-combine-upright:all;'>";
        }
        if (obj instanceof AbsoluteSizeSpan) {
            AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) obj;
            return Util.formatInvariant("<span style='font-size:%.2fpx;'>", Float.valueOf(absoluteSizeSpan.getDip() ? absoluteSizeSpan.getSize() : absoluteSizeSpan.getSize() / f));
        }
        if (obj instanceof RelativeSizeSpan) {
            return Util.formatInvariant("<span style='font-size:%.2f%%;'>", Float.valueOf(((RelativeSizeSpan) obj).getSizeChange() * 100.0f));
        }
        if (obj instanceof TypefaceSpan) {
            String family = ((TypefaceSpan) obj).getFamily();
            if (family != null) {
                return Util.formatInvariant("<span style='font-family:\"%s\";'>", family);
            }
            return null;
        }
        if (obj instanceof StyleSpan) {
            int style = ((StyleSpan) obj).getStyle();
            if (style == 1) {
                return "<b>";
            }
            if (style == 2) {
                return "<i>";
            }
            if (style != 3) {
                return null;
            }
            return "<b><i>";
        }
        if (!(obj instanceof RubySpan)) {
            if (obj instanceof UnderlineSpan) {
                return "<u>";
            }
            if (!(obj instanceof TextEmphasisSpan)) {
                return null;
            }
            TextEmphasisSpan textEmphasisSpan = (TextEmphasisSpan) obj;
            return Util.formatInvariant("<span style='-webkit-text-emphasis-style:%1$s;text-emphasis-style:%1$s;-webkit-text-emphasis-position:%2$s;text-emphasis-position:%2$s;display:inline-block;'>", getTextEmphasisStyle(textEmphasisSpan.markShape, textEmphasisSpan.markFill), getTextEmphasisPosition(textEmphasisSpan.position));
        }
        int i = ((RubySpan) obj).position;
        if (i == -1) {
            return "<ruby style='ruby-position:unset;'>";
        }
        if (i == 1) {
            return "<ruby style='ruby-position:over;'>";
        }
        if (i != 2) {
            return null;
        }
        return "<ruby style='ruby-position:under;'>";
    }

    private static Transition getOrCreate(SparseArray<Transition> sparseArray, int i) {
        Transition transition = sparseArray.get(i);
        if (transition != null) {
            return transition;
        }
        Transition transition2 = new Transition();
        sparseArray.put(i, transition2);
        return transition2;
    }

    private static String getTextEmphasisPosition(int i) {
        return i != 2 ? "over right" : "under left";
    }

    private static String getTextEmphasisStyle(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        if (i2 == 1) {
            sb.append("filled ");
        } else if (i2 == 2) {
            sb.append("open ");
        }
        if (i == 0) {
            sb.append("none");
        } else if (i == 1) {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE);
        } else if (i == 2) {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_DOT);
        } else if (i != 3) {
            sb.append("unset");
        } else {
            sb.append(TtmlNode.TEXT_EMPHASIS_MARK_SESAME);
        }
        return sb.toString();
    }
}
