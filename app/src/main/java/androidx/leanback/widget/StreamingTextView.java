package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import androidx.core.widget.TextViewCompat;
import androidx.leanback.R;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
class StreamingTextView extends EditText {
    private static final boolean DEBUG = false;
    private static final boolean DOTS_FOR_PENDING = true;
    private static final boolean DOTS_FOR_STABLE = false;
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\S+");
    private static final Property<StreamingTextView, Integer> STREAM_POSITION_PROPERTY = new Property<StreamingTextView, Integer>() { // from class: androidx.leanback.widget.StreamingTextView.1
        @Override // android.util.Property
        public Integer get(StreamingTextView streamingTextView) {
            return Integer.valueOf(streamingTextView.getStreamPosition());
        }

        @Override // android.util.Property
        public void set(StreamingTextView streamingTextView, Integer num) {
            streamingTextView.setStreamPosition(num.intValue());
        }
    };
    private static final long STREAM_UPDATE_DELAY_MILLIS = 50;
    private static final String TAG = "StreamingTextView";
    private static final float TEXT_DOT_SCALE = 1.3f;
    public Bitmap mOneDot;
    public final Random mRandom;
    public int mStreamPosition;
    private ObjectAnimator mStreamingAnimation;
    public Bitmap mTwoDot;

    public class DottySpan extends ReplacementSpan {
        private final int mPosition;
        private final int mSeed;

        public DottySpan(int i, int i2) {
            this.mSeed = i;
            this.mPosition = i2;
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            int iMeasureText = (int) paint.measureText(charSequence, i, i2);
            int width = StreamingTextView.this.mOneDot.getWidth();
            int i6 = width * 2;
            int i7 = iMeasureText / i6;
            int i8 = (iMeasureText % i6) / 2;
            boolean zIsLayoutRtl = StreamingTextView.isLayoutRtl(StreamingTextView.this);
            StreamingTextView.this.mRandom.setSeed(this.mSeed);
            int alpha = paint.getAlpha();
            for (int i9 = 0; i9 < i7; i9++) {
                int i10 = this.mPosition + i9;
                StreamingTextView streamingTextView = StreamingTextView.this;
                if (i10 >= streamingTextView.mStreamPosition) {
                    break;
                }
                float f2 = (width / 2) + (i9 * i6) + i8;
                float f3 = zIsLayoutRtl ? ((iMeasureText + f) - f2) - width : f + f2;
                paint.setAlpha((streamingTextView.mRandom.nextInt(4) + 1) * 63);
                if (StreamingTextView.this.mRandom.nextBoolean()) {
                    Bitmap bitmap = StreamingTextView.this.mTwoDot;
                    canvas.drawBitmap(bitmap, f3, i4 - bitmap.getHeight(), paint);
                } else {
                    Bitmap bitmap2 = StreamingTextView.this.mOneDot;
                    canvas.drawBitmap(bitmap2, f3, i4 - bitmap2.getHeight(), paint);
                }
            }
            paint.setAlpha(alpha);
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return (int) paint.measureText(charSequence, i, i2);
        }
    }

    public StreamingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRandom = new Random();
    }

    private void addColorSpan(SpannableStringBuilder spannableStringBuilder, int i, String str, int i2) {
        spannableStringBuilder.setSpan(new ForegroundColorSpan(i), i2, str.length() + i2, 33);
    }

    private void addDottySpans(SpannableStringBuilder spannableStringBuilder, String str, int i) {
        Matcher matcher = SPLIT_PATTERN.matcher(str);
        while (matcher.find()) {
            int iStart = matcher.start() + i;
            spannableStringBuilder.setSpan(new DottySpan(str.charAt(matcher.start()), iStart), iStart, matcher.end() + i, 33);
        }
    }

    private void cancelStreamAnimation() {
        ObjectAnimator objectAnimator = this.mStreamingAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private Bitmap getScaledBitmap(int i, float f) {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), i);
        return Bitmap.createScaledBitmap(bitmapDecodeResource, (int) (bitmapDecodeResource.getWidth() * f), (int) (bitmapDecodeResource.getHeight() * f), false);
    }

    public static boolean isLayoutRtl(View view) {
        return 1 == view.getLayoutDirection();
    }

    private void startStreamAnimation() {
        cancelStreamAnimation();
        int streamPosition = getStreamPosition();
        int length = length();
        int i = length - streamPosition;
        if (i > 0) {
            if (this.mStreamingAnimation == null) {
                ObjectAnimator objectAnimator = new ObjectAnimator();
                this.mStreamingAnimation = objectAnimator;
                objectAnimator.setTarget(this);
                this.mStreamingAnimation.setProperty(STREAM_POSITION_PROPERTY);
            }
            this.mStreamingAnimation.setIntValues(streamPosition, length);
            this.mStreamingAnimation.setDuration(((long) i) * STREAM_UPDATE_DELAY_MILLIS);
            this.mStreamingAnimation.start();
        }
    }

    private void updateText(CharSequence charSequence) {
        setText(charSequence);
        bringPointIntoView(length());
    }

    public int getStreamPosition() {
        return this.mStreamPosition;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mOneDot = getScaledBitmap(R.drawable.lb_text_dot_one, TEXT_DOT_SCALE);
        this.mTwoDot = getScaledBitmap(R.drawable.lb_text_dot_two, TEXT_DOT_SCALE);
        reset();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(StreamingTextView.class.getCanonicalName());
    }

    public void reset() {
        this.mStreamPosition = -1;
        cancelStreamAnimation();
        setText("");
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback));
    }

    public void setFinalRecognizedText(CharSequence charSequence) {
        updateText(charSequence);
    }

    public void setStreamPosition(int i) {
        this.mStreamPosition = i;
        invalidate();
    }

    public void updateRecognizedText(String str, String str2) {
        if (str == null) {
            str = "";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (str2 != null) {
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((CharSequence) str2);
            addDottySpans(spannableStringBuilder, str2, length);
        }
        this.mStreamPosition = Math.max(str.length(), this.mStreamPosition);
        updateText(new SpannedString(spannableStringBuilder));
        startStreamAnimation();
    }

    public void updateRecognizedText(String str, List<Float> list) {
    }

    public StreamingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRandom = new Random();
    }
}
