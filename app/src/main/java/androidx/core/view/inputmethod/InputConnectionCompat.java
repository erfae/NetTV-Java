package androidx.core.view.inputmethod;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.ViewCompat;
import com.nettv.livestore.activities.MovieActivity$$ExternalSyntheticLambda2;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"PrivateConstructorForUtilityClass"})
public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_INTEROP_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String EXTRA_INPUT_CONTENT_INFO = "androidx.core.view.extra.INPUT_CONTENT_INFO";
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
    private static final String LOG_TAG = "InputConnectionCompat";

    @RequiresApi(25)
    public static class Api25Impl {
        private Api25Impl() {
        }

        @DoNotInline
        public static boolean commitContent(InputConnection inputConnection, InputContentInfo inputContentInfo, int i, Bundle bundle) {
            return inputConnection.commitContent(inputContentInfo, i, bundle);
        }
    }

    public interface OnCommitContentListener {
        boolean onCommitContent(@NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle);
    }

    @Deprecated
    public InputConnectionCompat() {
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001e  */
    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
        char c;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 25) {
            return Api25Impl.commitContent(inputConnection, (InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle);
        }
        boolean z = true;
        if (i2 >= 25) {
            c = 1;
        } else {
            Bundle bundle2 = editorInfo.extras;
            if (bundle2 == null) {
                c = 0;
            } else {
                boolean zContainsKey = bundle2.containsKey("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
                boolean zContainsKey2 = editorInfo.extras.containsKey("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
                if (zContainsKey && zContainsKey2) {
                    c = 4;
                } else if (zContainsKey) {
                    c = 3;
                } else if (zContainsKey2) {
                    c = 2;
                } else {
                    c = 0;
                }
            }
        }
        if (c != 2) {
            if (c != 3 && c != 4) {
                return false;
            }
            z = false;
        }
        Bundle bundle3 = new Bundle();
        bundle3.putParcelable(z ? COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY : COMMIT_CONTENT_CONTENT_URI_KEY, inputContentInfoCompat.getContentUri());
        bundle3.putParcelable(z ? COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY : COMMIT_CONTENT_DESCRIPTION_KEY, inputContentInfoCompat.getDescription());
        bundle3.putParcelable(z ? COMMIT_CONTENT_LINK_URI_INTEROP_KEY : COMMIT_CONTENT_LINK_URI_KEY, inputContentInfoCompat.getLinkUri());
        bundle3.putInt(z ? COMMIT_CONTENT_FLAGS_INTEROP_KEY : COMMIT_CONTENT_FLAGS_KEY, i);
        bundle3.putParcelable(z ? COMMIT_CONTENT_OPTS_INTEROP_KEY : COMMIT_CONTENT_OPTS_KEY, bundle);
        return inputConnection.performPrivateCommand(z ? COMMIT_CONTENT_INTEROP_ACTION : COMMIT_CONTENT_ACTION, bundle3);
    }

    @NonNull
    private static OnCommitContentListener createOnCommitContentListenerUsingPerformReceiveContent(@NonNull View view) {
        Preconditions.checkNotNull(view);
        return new MovieActivity$$ExternalSyntheticLambda2(view, 2);
    }

    @NonNull
    @Deprecated
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull final OnCommitContentListener onCommitContentListener) {
        ObjectsCompat.requireNonNull(inputConnection, "inputConnection must be non-null");
        ObjectsCompat.requireNonNull(editorInfo, "editorInfo must be non-null");
        ObjectsCompat.requireNonNull(onCommitContentListener, "onCommitContentListener must be non-null");
        if (Build.VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.1
                @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                    if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, i, bundle);
                }
            };
        }
        return EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0 ? inputConnection : new InputConnectionWrapper(inputConnection) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.2
            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean performPrivateCommand(String str, Bundle bundle) throws Throwable {
                boolean z;
                ResultReceiver resultReceiver;
                OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
                boolean zOnCommitContent = false;
                zOnCommitContent = false;
                zOnCommitContent = false;
                zOnCommitContent = false;
                if (bundle != null) {
                    if (!TextUtils.equals(InputConnectionCompat.COMMIT_CONTENT_ACTION, str)) {
                        z = TextUtils.equals(InputConnectionCompat.COMMIT_CONTENT_INTEROP_ACTION, str);
                    }
                    try {
                        resultReceiver = (ResultReceiver) bundle.getParcelable(z ? InputConnectionCompat.COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY : InputConnectionCompat.COMMIT_CONTENT_RESULT_RECEIVER_KEY);
                        try {
                            Uri uri = (Uri) bundle.getParcelable(z ? InputConnectionCompat.COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY : InputConnectionCompat.COMMIT_CONTENT_CONTENT_URI_KEY);
                            ClipDescription clipDescription = (ClipDescription) bundle.getParcelable(z ? InputConnectionCompat.COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY : InputConnectionCompat.COMMIT_CONTENT_DESCRIPTION_KEY);
                            Uri uri2 = (Uri) bundle.getParcelable(z ? InputConnectionCompat.COMMIT_CONTENT_LINK_URI_INTEROP_KEY : InputConnectionCompat.COMMIT_CONTENT_LINK_URI_KEY);
                            int i = bundle.getInt(z ? InputConnectionCompat.COMMIT_CONTENT_FLAGS_INTEROP_KEY : InputConnectionCompat.COMMIT_CONTENT_FLAGS_KEY);
                            Bundle bundle2 = (Bundle) bundle.getParcelable(z ? InputConnectionCompat.COMMIT_CONTENT_OPTS_INTEROP_KEY : InputConnectionCompat.COMMIT_CONTENT_OPTS_KEY);
                            if (uri != null && clipDescription != null) {
                                zOnCommitContent = onCommitContentListener2.onCommitContent(new InputContentInfoCompat(uri, clipDescription, uri2), i, bundle2);
                            }
                            if (resultReceiver != null) {
                                resultReceiver.send(zOnCommitContent ? 1 : 0, null);
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (resultReceiver != null) {
                                resultReceiver.send(0, null);
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        resultReceiver = null;
                    }
                }
                if (zOnCommitContent) {
                    return true;
                }
                return super.performPrivateCommand(str, bundle);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createOnCommitContentListenerUsingPerformReceiveContent$0(View view, InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 25 && (i & 1) != 0) {
            try {
                inputContentInfoCompat.requestPermission();
                InputContentInfo inputContentInfo = (InputContentInfo) inputContentInfoCompat.unwrap();
                bundle = bundle == null ? new Bundle() : new Bundle(bundle);
                bundle.putParcelable(EXTRA_INPUT_CONTENT_INFO, inputContentInfo);
            } catch (Exception e) {
                Log.w(LOG_TAG, "Can't insert content from IME; requestPermission() failed", e);
                return false;
            }
        }
        return ViewCompat.performReceiveContent(view, new ContentInfoCompat.Builder(new ClipData(inputContentInfoCompat.getDescription(), new ClipData.Item(inputContentInfoCompat.getContentUri())), 2).setLinkUri(inputContentInfoCompat.getLinkUri()).setExtras(bundle).build()) == null;
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull View view, @NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo) {
        return createWrapper(inputConnection, editorInfo, createOnCommitContentListenerUsingPerformReceiveContent(view));
    }
}
