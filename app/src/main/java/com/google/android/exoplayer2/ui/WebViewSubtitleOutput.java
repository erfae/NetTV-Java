package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.androidannotations.api.rest.MediaType;

/* JADX INFO: loaded from: classes.dex */
final class WebViewSubtitleOutput extends FrameLayout implements SubtitleView.Output {
    private static final float CSS_LINE_HEIGHT = 1.2f;
    private static final String DEFAULT_BACKGROUND_CSS_CLASS = "default_bg";
    private float bottomPaddingFraction;
    private final CanvasSubtitleOutput canvasSubtitleOutput;
    private float defaultTextSize;
    private int defaultTextSizeType;
    private CaptionStyleCompat style;
    private List<Cue> textCues;
    private final WebView webView;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.ui.WebViewSubtitleOutput$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$android$text$Layout$Alignment;

        static {
            int[] iArr = new int[Layout.Alignment.values().length];
            $SwitchMap$android$text$Layout$Alignment = iArr;
            try {
                iArr[Layout.Alignment.ALIGN_NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_OPPOSITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public WebViewSubtitleOutput(Context context) {
        this(context, null);
    }

    private static int anchorTypeToTranslatePercent(int i) {
        if (i != 1) {
            return i != 2 ? 0 : -100;
        }
        return -50;
    }

    private static String convertAlignmentToCss(@Nullable Layout.Alignment alignment) {
        if (alignment == null) {
            return TtmlNode.CENTER;
        }
        int i = AnonymousClass2.$SwitchMap$android$text$Layout$Alignment[alignment.ordinal()];
        if (i != 1) {
            return i != 2 ? TtmlNode.CENTER : TtmlNode.END;
        }
        return TtmlNode.START;
    }

    private static String convertCaptionStyleToCssTextShadow(CaptionStyleCompat captionStyleCompat) {
        int i = captionStyleCompat.edgeType;
        if (i == 1) {
            return Util.formatInvariant("1px 1px 0 %1$s, 1px -1px 0 %1$s, -1px 1px 0 %1$s, -1px -1px 0 %1$s", HtmlUtils.toCssRgba(captionStyleCompat.edgeColor));
        }
        if (i == 2) {
            return Util.formatInvariant("0.1em 0.12em 0.15em %s", HtmlUtils.toCssRgba(captionStyleCompat.edgeColor));
        }
        if (i != 3) {
            return i != 4 ? "unset" : Util.formatInvariant("-0.05em -0.05em 0.15em %s", HtmlUtils.toCssRgba(captionStyleCompat.edgeColor));
        }
        return Util.formatInvariant("0.06em 0.08em 0.15em %s", HtmlUtils.toCssRgba(captionStyleCompat.edgeColor));
    }

    private String convertTextSizeToCss(int i, float f) {
        float fResolveTextSize = SubtitleViewUtils.resolveTextSize(i, f, getHeight(), (getHeight() - getPaddingTop()) - getPaddingBottom());
        return fResolveTextSize == -3.4028235E38f ? "unset" : Util.formatInvariant("%.2fpx", Float.valueOf(fResolveTextSize / getContext().getResources().getDisplayMetrics().density));
    }

    private static String convertVerticalTypeToCss(int i) {
        if (i != 1) {
            return i != 2 ? "horizontal-tb" : "vertical-lr";
        }
        return "vertical-rl";
    }

    private static String getBlockShearTransformFunction(Cue cue) {
        float f = cue.shearDegrees;
        if (f == 0.0f) {
            return "";
        }
        int i = cue.verticalType;
        return Util.formatInvariant("%s(%.2fdeg)", (i == 2 || i == 1) ? "skewY" : "skewX", Float.valueOf(f));
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:26:0x010b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0125  */
    /* JADX WARN: Code duplicated, block: B:30:0x0128  */
    /* JADX WARN: Code duplicated, block: B:33:0x0141  */
    /* JADX WARN: Code duplicated, block: B:35:0x0144 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x0146  */
    /* JADX WARN: Code duplicated, block: B:38:0x014a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x014d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x014f  */
    /* JADX WARN: Code duplicated, block: B:48:0x0162  */
    /* JADX WARN: Code duplicated, block: B:52:0x018a  */
    /* JADX WARN: Code duplicated, block: B:58:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:62:0x0226  */
    /* JADX WARN: Code duplicated, block: B:63:0x0242  */
    private void updateWebView() {
        String invariant;
        int iAnchorTypeToTranslatePercent;
        boolean z;
        float f;
        String invariant2;
        int i;
        int i2;
        int i3;
        String str;
        String str2;
        int i4;
        String str3;
        String str4;
        int i5;
        SpannedToHtmlConverter.HtmlAndCss htmlAndCssConvert;
        Iterator it;
        SpannedToHtmlConverter.HtmlAndCss htmlAndCss;
        Layout.Alignment alignment;
        String str5;
        boolean z2;
        WebViewSubtitleOutput webViewSubtitleOutput = this;
        StringBuilder sb = new StringBuilder();
        char c = 0;
        String strConvertTextSizeToCss = webViewSubtitleOutput.convertTextSizeToCss(webViewSubtitleOutput.defaultTextSizeType, webViewSubtitleOutput.defaultTextSize);
        int i6 = 1;
        float f2 = CSS_LINE_HEIGHT;
        sb.append(Util.formatInvariant("<body><div style='-webkit-user-select:none;position:fixed;top:0;bottom:0;left:0;right:0;color:%s;font-size:%s;line-height:%.2f;text-shadow:%s;'>", HtmlUtils.toCssRgba(webViewSubtitleOutput.style.foregroundColor), strConvertTextSizeToCss, Float.valueOf(CSS_LINE_HEIGHT), convertCaptionStyleToCssTextShadow(webViewSubtitleOutput.style)));
        HashMap map = new HashMap();
        map.put(HtmlUtils.cssAllClassDescendantsSelector(DEFAULT_BACKGROUND_CSS_CLASS), Util.formatInvariant("background-color:%s;", HtmlUtils.toCssRgba(webViewSubtitleOutput.style.backgroundColor)));
        int i7 = 0;
        while (i7 < webViewSubtitleOutput.textCues.size()) {
            Cue cue = webViewSubtitleOutput.textCues.get(i7);
            float f3 = cue.position;
            float f4 = f3 != -3.4028235E38f ? f3 * 100.0f : 50.0f;
            int iAnchorTypeToTranslatePercent2 = anchorTypeToTranslatePercent(cue.positionAnchor);
            float f5 = cue.line;
            if (f5 == -3.4028235E38f) {
                Object[] objArr = new Object[i6];
                objArr[c] = Float.valueOf((1.0f - webViewSubtitleOutput.bottomPaddingFraction) * 100.0f);
                invariant = Util.formatInvariant("%.2f%%", objArr);
                iAnchorTypeToTranslatePercent = -100;
            } else if (cue.lineType != i6) {
                Object[] objArr2 = new Object[i6];
                objArr2[c] = Float.valueOf(f5 * 100.0f);
                invariant = Util.formatInvariant("%.2f%%", objArr2);
                iAnchorTypeToTranslatePercent = cue.verticalType == i6 ? -anchorTypeToTranslatePercent(cue.lineAnchor) : anchorTypeToTranslatePercent(cue.lineAnchor);
            } else {
                if (f5 >= 0.0f) {
                    Object[] objArr3 = new Object[i6];
                    objArr3[c] = Float.valueOf(f5 * f2);
                    invariant = Util.formatInvariant("%.2fem", objArr3);
                    iAnchorTypeToTranslatePercent = 0;
                } else {
                    Object[] objArr4 = new Object[i6];
                    objArr4[c] = Float.valueOf(((-f5) - 1.0f) * f2);
                    invariant = Util.formatInvariant("%.2fem", objArr4);
                    iAnchorTypeToTranslatePercent = 0;
                    z = true;
                }
                f = cue.size;
                if (f != -3.4028235E38f) {
                    Object[] objArr5 = new Object[i6];
                    objArr5[0] = Float.valueOf(f * 100.0f);
                    invariant2 = Util.formatInvariant("%.2f%%", objArr5);
                } else {
                    invariant2 = "fit-content";
                }
                String strConvertAlignmentToCss = convertAlignmentToCss(cue.textAlignment);
                String strConvertVerticalTypeToCss = convertVerticalTypeToCss(cue.verticalType);
                String strConvertTextSizeToCss2 = webViewSubtitleOutput.convertTextSizeToCss(cue.textSizeType, cue.textSize);
                if (cue.windowColorSet) {
                    i = cue.windowColor;
                } else {
                    i = webViewSubtitleOutput.style.windowColor;
                }
                String cssRgba = HtmlUtils.toCssRgba(i);
                i2 = iAnchorTypeToTranslatePercent;
                i3 = cue.verticalType;
                str = TtmlNode.RIGHT;
                str2 = TtmlNode.LEFT;
                if (i3 != 1) {
                    if (z) {
                        str = TtmlNode.LEFT;
                    }
                    str2 = "top";
                    i4 = 2;
                    str3 = str;
                } else if (i3 != 2) {
                    str3 = z ? "bottom" : "top";
                    i4 = 2;
                } else {
                    if (!z) {
                        str = TtmlNode.LEFT;
                    }
                    str2 = "top";
                    i4 = 2;
                    str3 = str;
                }
                if (i3 != i4 || i3 == 1) {
                    str4 = "height";
                    i5 = i2;
                    i2 = iAnchorTypeToTranslatePercent2;
                } else {
                    str4 = "width";
                    i5 = iAnchorTypeToTranslatePercent2;
                }
                htmlAndCssConvert = SpannedToHtmlConverter.convert(cue.text, getContext().getResources().getDisplayMetrics().density);
                it = map.keySet().iterator();
                while (it.hasNext()) {
                    Iterator it2 = it;
                    String str6 = (String) it.next();
                    SpannedToHtmlConverter.HtmlAndCss htmlAndCss2 = htmlAndCssConvert;
                    str5 = (String) map.put(str6, (String) map.get(str6));
                    if (str5 != null || str5.equals(map.get(str6))) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    Assertions.checkState(z2);
                    it = it2;
                    htmlAndCssConvert = htmlAndCss2;
                }
                htmlAndCss = htmlAndCssConvert;
                sb.append(Util.formatInvariant("<div style='position:absolute;z-index:%s;%s:%.2f%%;%s:%s;%s:%s;text-align:%s;writing-mode:%s;font-size:%s;background-color:%s;transform:translate(%s%%,%s%%)%s;'>", Integer.valueOf(i7), str2, Float.valueOf(f4), str3, invariant, str4, invariant2, strConvertAlignmentToCss, strConvertVerticalTypeToCss, strConvertTextSizeToCss2, cssRgba, Integer.valueOf(i5), Integer.valueOf(i2), getBlockShearTransformFunction(cue)));
                sb.append(Util.formatInvariant("<span class='%s'>", DEFAULT_BACKGROUND_CSS_CLASS));
                alignment = cue.multiRowAlignment;
                if (alignment != null) {
                    sb.append(Util.formatInvariant("<span style='display:inline-block; text-align:%s;'>", convertAlignmentToCss(alignment)));
                    sb.append(htmlAndCss.html);
                    sb.append("</span>");
                } else {
                    sb.append(htmlAndCss.html);
                }
                sb.append("</span>");
                sb.append("</div>");
                i7++;
                f2 = CSS_LINE_HEIGHT;
                c = 0;
                webViewSubtitleOutput = this;
                i6 = 1;
            }
            z = false;
            f = cue.size;
            if (f != -3.4028235E38f) {
                Object[] objArr6 = new Object[i6];
                objArr6[0] = Float.valueOf(f * 100.0f);
                invariant2 = Util.formatInvariant("%.2f%%", objArr6);
            } else {
                invariant2 = "fit-content";
            }
            String strConvertAlignmentToCss2 = convertAlignmentToCss(cue.textAlignment);
            String strConvertVerticalTypeToCss2 = convertVerticalTypeToCss(cue.verticalType);
            String strConvertTextSizeToCss3 = webViewSubtitleOutput.convertTextSizeToCss(cue.textSizeType, cue.textSize);
            if (cue.windowColorSet) {
                i = cue.windowColor;
            } else {
                i = webViewSubtitleOutput.style.windowColor;
            }
            String cssRgba2 = HtmlUtils.toCssRgba(i);
            i2 = iAnchorTypeToTranslatePercent;
            i3 = cue.verticalType;
            str = TtmlNode.RIGHT;
            str2 = TtmlNode.LEFT;
            if (i3 != 1) {
                if (z) {
                    str = TtmlNode.LEFT;
                }
                str2 = "top";
                i4 = 2;
                str3 = str;
            } else if (i3 != 2) {
                if (z) {
                }
                i4 = 2;
            } else {
                if (!z) {
                    str = TtmlNode.LEFT;
                }
                str2 = "top";
                i4 = 2;
                str3 = str;
            }
            if (i3 != i4) {
                str4 = "height";
                i5 = i2;
                i2 = iAnchorTypeToTranslatePercent2;
            } else {
                str4 = "height";
                i5 = i2;
                i2 = iAnchorTypeToTranslatePercent2;
            }
            htmlAndCssConvert = SpannedToHtmlConverter.convert(cue.text, getContext().getResources().getDisplayMetrics().density);
            it = map.keySet().iterator();
            while (it.hasNext()) {
                Iterator it3 = it;
                String str7 = (String) it.next();
                SpannedToHtmlConverter.HtmlAndCss htmlAndCss3 = htmlAndCssConvert;
                str5 = (String) map.put(str7, (String) map.get(str7));
                if (str5 != null) {
                    z2 = true;
                } else {
                    z2 = true;
                }
                Assertions.checkState(z2);
                it = it3;
                htmlAndCssConvert = htmlAndCss3;
            }
            htmlAndCss = htmlAndCssConvert;
            sb.append(Util.formatInvariant("<div style='position:absolute;z-index:%s;%s:%.2f%%;%s:%s;%s:%s;text-align:%s;writing-mode:%s;font-size:%s;background-color:%s;transform:translate(%s%%,%s%%)%s;'>", Integer.valueOf(i7), str2, Float.valueOf(f4), str3, invariant, str4, invariant2, strConvertAlignmentToCss2, strConvertVerticalTypeToCss2, strConvertTextSizeToCss3, cssRgba2, Integer.valueOf(i5), Integer.valueOf(i2), getBlockShearTransformFunction(cue)));
            sb.append(Util.formatInvariant("<span class='%s'>", DEFAULT_BACKGROUND_CSS_CLASS));
            alignment = cue.multiRowAlignment;
            if (alignment != null) {
                sb.append(Util.formatInvariant("<span style='display:inline-block; text-align:%s;'>", convertAlignmentToCss(alignment)));
                sb.append(htmlAndCss.html);
                sb.append("</span>");
            } else {
                sb.append(htmlAndCss.html);
            }
            sb.append("</span>");
            sb.append("</div>");
            i7++;
            f2 = CSS_LINE_HEIGHT;
            c = 0;
            webViewSubtitleOutput = this;
            i6 = 1;
        }
        sb.append("</div></body></html>");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<html><head><style>");
        for (String str8 : map.keySet()) {
            sb2.append(str8);
            sb2.append("{");
            sb2.append((String) map.get(str8));
            sb2.append("}");
        }
        sb2.append("</style></head>");
        sb.insert(0, sb2.toString());
        this.webView.loadData(Base64.encodeToString(sb.toString().getBytes(Charsets.UTF_8), 1), MediaType.TEXT_HTML, "base64");
    }

    public void destroy() {
        this.webView.destroy();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!z || this.textCues.isEmpty()) {
            return;
        }
        updateWebView();
    }

    @Override // com.google.android.exoplayer2.ui.SubtitleView.Output
    public void update(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f, int i, float f2) {
        this.style = captionStyleCompat;
        this.defaultTextSize = f;
        this.defaultTextSizeType = i;
        this.bottomPaddingFraction = f2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            Cue cue = list.get(i2);
            if (cue.bitmap != null) {
                arrayList.add(cue);
            } else {
                arrayList2.add(cue);
            }
        }
        if (!this.textCues.isEmpty() || !arrayList2.isEmpty()) {
            this.textCues = arrayList2;
            updateWebView();
        }
        this.canvasSubtitleOutput.update(arrayList, captionStyleCompat, f, i, f2);
        invalidate();
    }

    public WebViewSubtitleOutput(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textCues = Collections.emptyList();
        this.style = CaptionStyleCompat.DEFAULT;
        this.defaultTextSize = 0.0533f;
        this.defaultTextSizeType = 0;
        this.bottomPaddingFraction = 0.08f;
        CanvasSubtitleOutput canvasSubtitleOutput = new CanvasSubtitleOutput(context, attributeSet);
        this.canvasSubtitleOutput = canvasSubtitleOutput;
        WebView webView = new WebView(context, attributeSet) { // from class: com.google.android.exoplayer2.ui.WebViewSubtitleOutput.1
            @Override // android.webkit.WebView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                super.onTouchEvent(motionEvent);
                return false;
            }

            @Override // android.view.View
            public boolean performClick() {
                super.performClick();
                return false;
            }
        };
        this.webView = webView;
        webView.setBackgroundColor(0);
        addView(canvasSubtitleOutput);
        addView(webView);
    }
}
