package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.annotation.AnyThread;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@AnyThread
@RequiresApi(19)
@RestrictTo({RestrictTo.Scope.LIBRARY})
final class EmojiProcessor {
    private static final int ACTION_ADVANCE_BOTH = 1;
    private static final int ACTION_ADVANCE_END = 2;
    private static final int ACTION_FLUSH = 3;

    @Nullable
    private final int[] mEmojiAsDefaultStyleExceptions;

    @NonNull
    private EmojiCompat.GlyphChecker mGlyphChecker;

    @NonNull
    private final MetadataRepo mMetadataRepo;

    @NonNull
    private final EmojiCompat.SpanFactory mSpanFactory;
    private final boolean mUseEmojiAsDefaultStyle;

    @RequiresApi(19)
    public static final class CodepointIndexFinder {
        private static final int INVALID_INDEX = -1;

        private CodepointIndexFinder() {
        }
    }

    public static final class ProcessorSm {
        private static final int STATE_DEFAULT = 1;
        private static final int STATE_WALKING = 2;
        private int mCurrentDepth;
        private MetadataRepo.Node mCurrentNode;
        private final int[] mEmojiAsDefaultStyleExceptions;
        private MetadataRepo.Node mFlushNode;
        private int mLastCodepoint;
        private final MetadataRepo.Node mRootNode;
        private int mState = 1;
        private final boolean mUseEmojiAsDefaultStyle;

        public ProcessorSm(MetadataRepo.Node node, boolean z, int[] iArr) {
            this.mRootNode = node;
            this.mCurrentNode = node;
            this.mUseEmojiAsDefaultStyle = z;
            this.mEmojiAsDefaultStyleExceptions = iArr;
        }

        private static boolean isEmojiStyle(int i) {
            return i == 65039;
        }

        private static boolean isTextStyle(int i) {
            return i == 65038;
        }

        private int reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
            return 1;
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            if (this.mCurrentNode.getData().isDefaultEmoji() || isEmojiStyle(this.mLastCodepoint)) {
                return true;
            }
            if (this.mUseEmojiAsDefaultStyle) {
                if (this.mEmojiAsDefaultStyleExceptions == null) {
                    return true;
                }
                if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, this.mCurrentNode.getData().getCodepointAt(0)) < 0) {
                    return true;
                }
            }
            return false;
        }

        public final int check(int i) {
            MetadataRepo.Node node = this.mCurrentNode.get(i);
            int iReset = 3;
            if (this.mState == 2) {
                if (node != null) {
                    this.mCurrentNode = node;
                    this.mCurrentDepth++;
                } else if (isTextStyle(i)) {
                    iReset = reset();
                } else if (!isEmojiStyle(i)) {
                    if (this.mCurrentNode.getData() == null) {
                        iReset = reset();
                    } else if (this.mCurrentDepth != 1 || shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                        this.mFlushNode = this.mCurrentNode;
                        reset();
                    } else {
                        iReset = reset();
                    }
                }
                iReset = 2;
            } else if (node == null) {
                iReset = reset();
            } else {
                this.mState = 2;
                this.mCurrentNode = node;
                this.mCurrentDepth = 1;
                iReset = 2;
            }
            this.mLastCodepoint = i;
            return iReset;
        }

        public final EmojiMetadata getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        public final EmojiMetadata getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        public final boolean isInFlushableState() {
            return this.mState == 2 && this.mCurrentNode.getData() != null && (this.mCurrentDepth > 1 || shouldUseEmojiPresentationStyleForSingleCodepoint());
        }
    }

    public EmojiProcessor(@NonNull MetadataRepo metadataRepo, @NonNull EmojiCompat.SpanFactory spanFactory, @NonNull EmojiCompat.GlyphChecker glyphChecker, boolean z, @Nullable int[] iArr) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = z;
        this.mEmojiAsDefaultStyleExceptions = iArr;
    }

    private void addEmoji(@NonNull Spannable spannable, EmojiMetadata emojiMetadata, int i, int i2) {
        Objects.requireNonNull(this.mSpanFactory);
        spannable.setSpan(new TypefaceEmojiSpan(emojiMetadata), i, i2, 33);
    }

    private static boolean delete(@NonNull Editable editable, @NonNull KeyEvent keyEvent, boolean z) {
        EmojiSpan[] emojiSpanArr;
        if (hasModifiers(keyEvent)) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (!hasInvalidSelection(selectionStart, selectionEnd) && (emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                    editable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean handleDeleteSurroundingText(@NonNull InputConnection inputConnection, @NonNull Editable editable, @IntRange(from = 0) int i, @IntRange(from = 0) int i2, boolean z) {
        int iMin;
        if (editable != null && inputConnection != null && i >= 0 && i2 >= 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (hasInvalidSelection(selectionStart, selectionEnd)) {
                return false;
            }
            if (z) {
                int iMax = Math.max(i, 0);
                int length = editable.length();
                if (selectionStart >= 0 && length >= selectionStart && iMax >= 0) {
                    loop0: while (true) {
                        boolean z2 = false;
                        while (true) {
                            if (iMax == 0) {
                                break loop0;
                            }
                            selectionStart--;
                            if (selectionStart < 0) {
                                if (!z2) {
                                    selectionStart = 0;
                                    break loop0;
                                }
                                break loop0;
                            }
                            char cCharAt = editable.charAt(selectionStart);
                            if (z2) {
                                if (Character.isHighSurrogate(cCharAt)) {
                                    iMax--;
                                }
                            } else if (!Character.isSurrogate(cCharAt)) {
                                iMax--;
                            } else if (!Character.isHighSurrogate(cCharAt)) {
                                z2 = true;
                            }
                            selectionStart = -1;
                            break loop0;
                        }
                    }
                }
                selectionStart = -1;
                break loop0;
                int iMax2 = Math.max(i2, 0);
                iMin = editable.length();
                if (selectionEnd >= 0 && iMin >= selectionEnd && iMax2 >= 0) {
                    loop2: while (true) {
                        boolean z3 = false;
                        while (true) {
                            if (iMax2 != 0) {
                                if (selectionEnd >= iMin) {
                                    if (!z3) {
                                        break loop2;
                                    }
                                    break loop2;
                                }
                                char cCharAt2 = editable.charAt(selectionEnd);
                                if (z3) {
                                    if (Character.isLowSurrogate(cCharAt2)) {
                                        iMax2--;
                                        selectionEnd++;
                                    }
                                } else if (!Character.isSurrogate(cCharAt2)) {
                                    iMax2--;
                                    selectionEnd++;
                                } else if (!Character.isLowSurrogate(cCharAt2)) {
                                    selectionEnd++;
                                    z3 = true;
                                }
                                iMin = -1;
                                break loop2;
                            }
                            iMin = selectionEnd;
                            break loop2;
                        }
                    }
                }
                iMin = -1;
                break loop2;
                if (selectionStart == -1 || iMin == -1) {
                    return false;
                }
            } else {
                selectionStart = Math.max(selectionStart - i, 0);
                iMin = Math.min(selectionEnd + i2, editable.length());
            }
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, iMin, EmojiSpan.class);
            if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                for (EmojiSpan emojiSpan : emojiSpanArr) {
                    int spanStart = editable.getSpanStart(emojiSpan);
                    int spanEnd = editable.getSpanEnd(emojiSpan);
                    selectionStart = Math.min(spanStart, selectionStart);
                    iMin = Math.max(spanEnd, iMin);
                }
                int iMax3 = Math.max(selectionStart, 0);
                int iMin2 = Math.min(iMin, editable.length());
                inputConnection.beginBatchEdit();
                editable.delete(iMax3, iMin2);
                inputConnection.endBatchEdit();
                return true;
            }
        }
        return false;
    }

    public static boolean handleOnKeyDown(@NonNull Editable editable, int i, @NonNull KeyEvent keyEvent) {
        boolean zDelete;
        if (i != 67) {
            zDelete = i != 112 ? false : delete(editable, keyEvent, true);
        } else {
            zDelete = delete(editable, keyEvent, false);
        }
        if (!zDelete) {
            return false;
        }
        MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
        return true;
    }

    private boolean hasGlyph(CharSequence charSequence, int i, int i2, EmojiMetadata emojiMetadata) {
        if (emojiMetadata.getHasGlyph() == 0) {
            emojiMetadata.setHasGlyph(this.mGlyphChecker.hasGlyph(charSequence, i, i2, emojiMetadata.getSdkAdded()));
        }
        return emojiMetadata.getHasGlyph() == 2;
    }

    private static boolean hasInvalidSelection(int i, int i2) {
        return i == -1 || i2 == -1 || i != i2;
    }

    private static boolean hasModifiers(@NonNull KeyEvent keyEvent) {
        return !KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState());
    }

    public final int getEmojiMatch(@NonNull CharSequence charSequence) {
        return getEmojiMatch(charSequence, this.mMetadataRepo.getMetadataVersion());
    }

    public final CharSequence process(@NonNull CharSequence charSequence, @IntRange(from = 0) int i, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, boolean z) {
        int iCharCount;
        EmojiSpan[] emojiSpanArr;
        boolean z2 = charSequence instanceof SpannableBuilder;
        if (z2) {
            ((SpannableBuilder) charSequence).beginBatchEdit();
        }
        UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable = null;
        if (z2) {
            unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable((Spannable) charSequence);
        } else {
            try {
                if (charSequence instanceof Spannable) {
                    unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable((Spannable) charSequence);
                } else if ((charSequence instanceof Spanned) && ((Spanned) charSequence).nextSpanTransition(i - 1, i2 + 1, EmojiSpan.class) <= i2) {
                    unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable(charSequence);
                }
            } finally {
                if (z2) {
                    ((SpannableBuilder) charSequence).endBatchEdit();
                }
            }
        }
        if (unprecomputeTextOnModificationSpannable != null && (emojiSpanArr = (EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(i, i2, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = unprecomputeTextOnModificationSpannable.getSpanStart(emojiSpan);
                int spanEnd = unprecomputeTextOnModificationSpannable.getSpanEnd(emojiSpan);
                if (spanStart != i2) {
                    unprecomputeTextOnModificationSpannable.removeSpan(emojiSpan);
                }
                i = Math.min(spanStart, i);
                i2 = Math.max(spanEnd, i2);
            }
        }
        if (i != i2 && i < charSequence.length()) {
            if (i3 != Integer.MAX_VALUE && unprecomputeTextOnModificationSpannable != null) {
                i3 -= ((EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(0, unprecomputeTextOnModificationSpannable.length(), EmojiSpan.class)).length;
            }
            ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
            UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable2 = unprecomputeTextOnModificationSpannable;
            int iCodePointAt = Character.codePointAt(charSequence, i);
            int i4 = 0;
            loop1: while (true) {
                iCharCount = i;
                while (true) {
                    if (i >= i2 || i4 >= i3) {
                        break loop1;
                    }
                    int iCheck = processorSm.check(iCodePointAt);
                    if (iCheck == 1) {
                        iCharCount += Character.charCount(Character.codePointAt(charSequence, iCharCount));
                        if (iCharCount < i2) {
                            iCodePointAt = Character.codePointAt(charSequence, iCharCount);
                        }
                        i = iCharCount;
                    } else if (iCheck == 2) {
                        i += Character.charCount(iCodePointAt);
                        if (i < i2) {
                            iCodePointAt = Character.codePointAt(charSequence, i);
                        }
                    } else if (iCheck != 3) {
                    }
                }
                if (z || !hasGlyph(charSequence, iCharCount, i, processorSm.getFlushMetadata())) {
                    if (unprecomputeTextOnModificationSpannable2 == null) {
                        unprecomputeTextOnModificationSpannable2 = new UnprecomputeTextOnModificationSpannable((Spannable) new SpannableString(charSequence));
                    }
                    addEmoji(unprecomputeTextOnModificationSpannable2, processorSm.getFlushMetadata(), iCharCount, i);
                    i4++;
                }
            }
            if (processorSm.isInFlushableState() && i4 < i3 && (z || !hasGlyph(charSequence, iCharCount, i, processorSm.getCurrentMetadata()))) {
                if (unprecomputeTextOnModificationSpannable2 == null) {
                    unprecomputeTextOnModificationSpannable2 = new UnprecomputeTextOnModificationSpannable(charSequence);
                }
                addEmoji(unprecomputeTextOnModificationSpannable2, processorSm.getCurrentMetadata(), iCharCount, i);
            }
            return unprecomputeTextOnModificationSpannable2 != null ? unprecomputeTextOnModificationSpannable2.getUnwrappedSpannable() : charSequence;
        }
        return charSequence;
    }

    public final int getEmojiMatch(@NonNull CharSequence charSequence, int i) {
        ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        int length = charSequence.length();
        int iCharCount = 0;
        int i2 = 0;
        int i3 = 0;
        while (iCharCount < length) {
            int iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            int iCheck = processorSm.check(iCodePointAt);
            EmojiMetadata currentMetadata = processorSm.getCurrentMetadata();
            if (iCheck == 1) {
                iCharCount += Character.charCount(iCodePointAt);
                i3 = 0;
            } else if (iCheck == 2) {
                iCharCount += Character.charCount(iCodePointAt);
            } else if (iCheck == 3) {
                currentMetadata = processorSm.getFlushMetadata();
                if (currentMetadata.getCompatAdded() <= i) {
                    i2++;
                }
            }
            if (currentMetadata != null && currentMetadata.getCompatAdded() <= i) {
                i3++;
            }
        }
        if (i2 != 0) {
            return 2;
        }
        if (!processorSm.isInFlushableState() || processorSm.getCurrentMetadata().getCompatAdded() > i) {
            return i3 == 0 ? 0 : 2;
        }
        return 1;
    }
}
