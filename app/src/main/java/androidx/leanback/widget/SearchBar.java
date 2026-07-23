package androidx.leanback.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.leanback.R;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SearchBar extends RelativeLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    private AudioManager mAudioManager;
    public boolean mAutoStartRecognition;
    private int mBackgroundAlpha;
    private int mBackgroundSpeechAlpha;
    private Drawable mBadgeDrawable;
    private ImageView mBadgeView;
    private Drawable mBarBackground;
    private int mBarHeight;
    private final Context mContext;
    public final Handler mHandler;
    private String mHint;
    private final InputMethodManager mInputMethodManager;
    private boolean mListening;
    private SearchBarPermissionListener mPermissionListener;
    public boolean mRecognizing;
    public SearchBarListener mSearchBarListener;
    public String mSearchQuery;
    public SearchEditText mSearchTextEditor;
    public SparseIntArray mSoundMap;
    public SoundPool mSoundPool;
    public SpeechOrbView mSpeechOrbView;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    private final int mTextColor;
    private final int mTextColorSpeechMode;
    private final int mTextHintColor;
    private final int mTextHintColorSpeechMode;
    private String mTitle;

    public interface SearchBarListener {
        void onKeyboardDismiss(String str);

        void onSearchQueryChange(String str);

        void onSearchQuerySubmit(String str);
    }

    public interface SearchBarPermissionListener {
        void requestAudioPermission();
    }

    public SearchBar(Context context) {
        this(context, null);
    }

    private boolean isVoiceMode() {
        return this.mSpeechOrbView.isFocused();
    }

    private void loadSounds(Context context) {
        int[] iArr = {R.raw.lb_voice_failure, R.raw.lb_voice_open, R.raw.lb_voice_no_input, R.raw.lb_voice_success};
        for (int i = 0; i < 4; i++) {
            int i2 = iArr[i];
            this.mSoundMap.put(i2, this.mSoundPool.load(context, i2, 1));
        }
    }

    private void play(final int i) {
        this.mHandler.post(new Runnable() { // from class: androidx.leanback.widget.SearchBar.10
            @Override // java.lang.Runnable
            public void run() {
                SearchBar.this.mSoundPool.play(SearchBar.this.mSoundMap.get(i), 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
    }

    private void playSearchNoInput() {
        play(R.raw.lb_voice_no_input);
    }

    private void updateHint() {
        String string = getResources().getString(R.string.lb_search_bar_hint);
        if (!TextUtils.isEmpty(this.mTitle)) {
            string = isVoiceMode() ? getResources().getString(R.string.lb_search_bar_hint_with_title_speech, this.mTitle) : getResources().getString(R.string.lb_search_bar_hint_with_title, this.mTitle);
        } else if (isVoiceMode()) {
            string = getResources().getString(R.string.lb_search_bar_hint_speech);
        }
        this.mHint = string;
        SearchEditText searchEditText = this.mSearchTextEditor;
        if (searchEditText != null) {
            searchEditText.setHint(string);
        }
    }

    public void displayCompletions(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new CompletionInfo(arrayList.size(), arrayList.size(), it.next()));
            }
        }
        displayCompletions((CompletionInfo[]) arrayList.toArray(new CompletionInfo[arrayList.size()]));
    }

    public Drawable getBadgeDrawable() {
        return this.mBadgeDrawable;
    }

    public CharSequence getHint() {
        return this.mHint;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public final void hideNativeKeyboard() {
        this.mInputMethodManager.hideSoftInputFromWindow(this.mSearchTextEditor.getWindowToken(), 0);
    }

    public boolean isRecognizing() {
        return this.mRecognizing;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSoundPool = new SoundPool(2, 1, 0);
        loadSounds(this.mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        stopRecognition();
        this.mSoundPool.release();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBarBackground = ((RelativeLayout) findViewById(R.id.lb_search_bar_items)).getBackground();
        this.mSearchTextEditor = (SearchEditText) findViewById(R.id.lb_search_text_editor);
        ImageView imageView = (ImageView) findViewById(R.id.lb_search_bar_badge);
        this.mBadgeView = imageView;
        Drawable drawable = this.mBadgeDrawable;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        this.mSearchTextEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.SearchBar.1
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    final SearchBar searchBar = SearchBar.this;
                    searchBar.mHandler.post(new Runnable() { // from class: androidx.leanback.widget.SearchBar.8
                        @Override // java.lang.Runnable
                        public void run() {
                            SearchBar.this.mSearchTextEditor.requestFocusFromTouch();
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, SearchBar.this.mSearchTextEditor.getWidth(), SearchBar.this.mSearchTextEditor.getHeight(), 0));
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, SearchBar.this.mSearchTextEditor.getWidth(), SearchBar.this.mSearchTextEditor.getHeight(), 0));
                        }
                    });
                } else {
                    SearchBar.this.hideNativeKeyboard();
                }
                SearchBar.this.updateUi(z);
            }
        });
        final Runnable runnable = new Runnable() { // from class: androidx.leanback.widget.SearchBar.2
            @Override // java.lang.Runnable
            public void run() {
                SearchBar searchBar = SearchBar.this;
                searchBar.setSearchQueryInternal(searchBar.mSearchTextEditor.getText().toString());
            }
        };
        this.mSearchTextEditor.addTextChangedListener(new TextWatcher() { // from class: androidx.leanback.widget.SearchBar.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SearchBar searchBar = SearchBar.this;
                if (searchBar.mRecognizing) {
                    return;
                }
                searchBar.mHandler.removeCallbacks(runnable);
                SearchBar.this.mHandler.post(runnable);
            }
        });
        this.mSearchTextEditor.setOnKeyboardDismissListener(new SearchEditText.OnKeyboardDismissListener() { // from class: androidx.leanback.widget.SearchBar.4
            @Override // androidx.leanback.widget.SearchEditText.OnKeyboardDismissListener
            public void onKeyboardDismiss() {
                SearchBar searchBar = SearchBar.this;
                SearchBarListener searchBarListener = searchBar.mSearchBarListener;
                if (searchBarListener != null) {
                    searchBarListener.onKeyboardDismiss(searchBar.mSearchQuery);
                }
            }
        });
        this.mSearchTextEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.leanback.widget.SearchBar.5
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (3 == i || i == 0) {
                    SearchBar searchBar = SearchBar.this;
                    if (searchBar.mSearchBarListener != null) {
                        searchBar.hideNativeKeyboard();
                        SearchBar.this.mHandler.postDelayed(new Runnable() { // from class: androidx.leanback.widget.SearchBar.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                SearchBarListener searchBarListener;
                                SearchBar searchBar2 = SearchBar.this;
                                if (TextUtils.isEmpty(searchBar2.mSearchQuery) || (searchBarListener = searchBar2.mSearchBarListener) == null) {
                                    return;
                                }
                                searchBarListener.onSearchQuerySubmit(searchBar2.mSearchQuery);
                            }
                        }, 500L);
                        return true;
                    }
                }
                if (1 == i) {
                    SearchBar searchBar2 = SearchBar.this;
                    if (searchBar2.mSearchBarListener != null) {
                        searchBar2.hideNativeKeyboard();
                        SearchBar.this.mHandler.postDelayed(new Runnable() { // from class: androidx.leanback.widget.SearchBar.5.2
                            @Override // java.lang.Runnable
                            public void run() {
                                SearchBar searchBar3 = SearchBar.this;
                                searchBar3.mSearchBarListener.onKeyboardDismiss(searchBar3.mSearchQuery);
                            }
                        }, 500L);
                        return true;
                    }
                }
                if (2 != i) {
                    return false;
                }
                SearchBar.this.hideNativeKeyboard();
                SearchBar.this.mHandler.postDelayed(new Runnable() { // from class: androidx.leanback.widget.SearchBar.5.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SearchBar searchBar3 = SearchBar.this;
                        searchBar3.mAutoStartRecognition = true;
                        searchBar3.mSpeechOrbView.requestFocus();
                    }
                }, 500L);
                return true;
            }
        });
        this.mSearchTextEditor.setPrivateImeOptions("escapeNorth,voiceDismiss");
        SpeechOrbView speechOrbView = (SpeechOrbView) findViewById(R.id.lb_search_bar_speech_orb);
        this.mSpeechOrbView = speechOrbView;
        speechOrbView.setOnOrbClickedListener(new View.OnClickListener() { // from class: androidx.leanback.widget.SearchBar.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SearchBar searchBar = SearchBar.this;
                if (searchBar.mRecognizing) {
                    searchBar.stopRecognition();
                } else {
                    searchBar.startRecognition();
                }
            }
        });
        this.mSpeechOrbView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.SearchBar.7
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    SearchBar.this.hideNativeKeyboard();
                    SearchBar searchBar = SearchBar.this;
                    if (searchBar.mAutoStartRecognition) {
                        searchBar.startRecognition();
                        SearchBar.this.mAutoStartRecognition = false;
                    }
                } else {
                    SearchBar.this.stopRecognition();
                }
                SearchBar.this.updateUi(z);
            }
        });
        updateUi(hasFocus());
        updateHint();
    }

    public final void playSearchFailure() {
        play(R.raw.lb_voice_failure);
    }

    public final void playSearchOpen() {
        play(R.raw.lb_voice_open);
    }

    public final void playSearchSuccess() {
        play(R.raw.lb_voice_success);
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        ImageView imageView = this.mBadgeView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (drawable != null) {
                this.mBadgeView.setVisibility(0);
            } else {
                this.mBadgeView.setVisibility(8);
            }
        }
    }

    @Override // android.view.View
    public void setNextFocusDownId(int i) {
        this.mSpeechOrbView.setNextFocusDownId(i);
        this.mSearchTextEditor.setNextFocusDownId(i);
    }

    public void setPermissionListener(SearchBarPermissionListener searchBarPermissionListener) {
        this.mPermissionListener = searchBarPermissionListener;
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        SpeechOrbView speechOrbView = this.mSpeechOrbView;
        if (speechOrbView != null) {
            speechOrbView.setNotListeningOrbColors(colors);
        }
    }

    public void setSearchAffordanceColorsInListening(SearchOrbView.Colors colors) {
        SpeechOrbView speechOrbView = this.mSpeechOrbView;
        if (speechOrbView != null) {
            speechOrbView.setListeningOrbColors(colors);
        }
    }

    public void setSearchBarListener(SearchBarListener searchBarListener) {
        this.mSearchBarListener = searchBarListener;
    }

    public void setSearchQuery(String str) {
        stopRecognition();
        this.mSearchTextEditor.setText(str);
        setSearchQueryInternal(str);
    }

    public void setSearchQueryInternal(String str) {
        if (TextUtils.equals(this.mSearchQuery, str)) {
            return;
        }
        this.mSearchQuery = str;
        SearchBarListener searchBarListener = this.mSearchBarListener;
        if (searchBarListener != null) {
            searchBarListener.onSearchQueryChange(str);
        }
    }

    @Deprecated
    public void setSpeechRecognitionCallback(SpeechRecognitionCallback speechRecognitionCallback) {
        this.mSpeechRecognitionCallback = speechRecognitionCallback;
        if (speechRecognitionCallback != null && this.mSpeechRecognizer != null) {
            throw new IllegalStateException("Can't have speech recognizer and request");
        }
    }

    public void setSpeechRecognizer(SpeechRecognizer speechRecognizer) {
        stopRecognition();
        SpeechRecognizer speechRecognizer2 = this.mSpeechRecognizer;
        if (speechRecognizer2 != null) {
            speechRecognizer2.setRecognitionListener(null);
            if (this.mListening) {
                this.mSpeechRecognizer.cancel();
                this.mListening = false;
            }
        }
        this.mSpeechRecognizer = speechRecognizer;
        if (this.mSpeechRecognitionCallback != null && speechRecognizer != null) {
            throw new IllegalStateException("Can't have speech recognizer and request");
        }
    }

    public void setTitle(String str) {
        this.mTitle = str;
        updateHint();
    }

    public void startRecognition() {
        SearchBarPermissionListener searchBarPermissionListener;
        if (this.mRecognizing) {
            return;
        }
        if (!hasFocus()) {
            requestFocus();
        }
        if (this.mSpeechRecognitionCallback != null) {
            this.mSearchTextEditor.setText("");
            this.mSearchTextEditor.setHint("");
            this.mSpeechRecognitionCallback.recognizeSpeech();
            this.mRecognizing = true;
            return;
        }
        if (this.mSpeechRecognizer == null) {
            return;
        }
        if (getContext().checkCallingOrSelfPermission("android.permission.RECORD_AUDIO") != 0) {
            if (Build.VERSION.SDK_INT < 23 || (searchBarPermissionListener = this.mPermissionListener) == null) {
                throw new IllegalStateException("android.permission.RECORD_AUDIO required for search");
            }
            searchBarPermissionListener.requestAudioPermission();
            return;
        }
        this.mRecognizing = true;
        this.mSearchTextEditor.setText("");
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        this.mSpeechRecognizer.setRecognitionListener(new RecognitionListener() { // from class: androidx.leanback.widget.SearchBar.9
            @Override // android.speech.RecognitionListener
            public void onBeginningOfSpeech() {
            }

            @Override // android.speech.RecognitionListener
            public void onBufferReceived(byte[] bArr) {
            }

            @Override // android.speech.RecognitionListener
            public void onEndOfSpeech() {
            }

            @Override // android.speech.RecognitionListener
            public void onError(int i) {
                switch (i) {
                    case 1:
                        int i2 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer network timeout");
                        break;
                    case 2:
                        int i3 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer network error");
                        break;
                    case 3:
                        int i4 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer audio error");
                        break;
                    case 4:
                        int i5 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer server error");
                        break;
                    case 5:
                        int i6 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer client error");
                        break;
                    case 6:
                        int i7 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer speech timeout");
                        break;
                    case 7:
                        int i8 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer no match");
                        break;
                    case 8:
                        int i9 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer busy");
                        break;
                    case 9:
                        int i10 = SearchBar.$r8$clinit;
                        Log.w("SearchBar", "recognizer insufficient permissions");
                        break;
                    default:
                        int i11 = SearchBar.$r8$clinit;
                        Log.d("SearchBar", "recognizer other error");
                        break;
                }
                SearchBar.this.stopRecognition();
                SearchBar.this.playSearchFailure();
            }

            @Override // android.speech.RecognitionListener
            public void onEvent(int i, Bundle bundle) {
            }

            @Override // android.speech.RecognitionListener
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                if (stringArrayList == null || stringArrayList.size() == 0) {
                    return;
                }
                SearchBar.this.mSearchTextEditor.updateRecognizedText(stringArrayList.get(0), stringArrayList.size() > 1 ? stringArrayList.get(1) : null);
            }

            @Override // android.speech.RecognitionListener
            public void onReadyForSpeech(Bundle bundle) {
                SearchBar.this.mSpeechOrbView.showListening();
                SearchBar.this.playSearchOpen();
            }

            @Override // android.speech.RecognitionListener
            public void onResults(Bundle bundle) {
                SearchBarListener searchBarListener;
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                if (stringArrayList != null) {
                    SearchBar.this.mSearchQuery = stringArrayList.get(0);
                    SearchBar searchBar = SearchBar.this;
                    searchBar.mSearchTextEditor.setText(searchBar.mSearchQuery);
                    SearchBar searchBar2 = SearchBar.this;
                    if (!TextUtils.isEmpty(searchBar2.mSearchQuery) && (searchBarListener = searchBar2.mSearchBarListener) != null) {
                        searchBarListener.onSearchQuerySubmit(searchBar2.mSearchQuery);
                    }
                }
                SearchBar.this.stopRecognition();
                SearchBar.this.playSearchSuccess();
            }

            @Override // android.speech.RecognitionListener
            public void onRmsChanged(float f) {
                SearchBar.this.mSpeechOrbView.setSoundLevel(f < 0.0f ? 0 : (int) (f * 10.0f));
            }
        });
        this.mListening = true;
        this.mSpeechRecognizer.startListening(intent);
    }

    public void stopRecognition() {
        if (this.mRecognizing) {
            this.mSearchTextEditor.setText(this.mSearchQuery);
            this.mSearchTextEditor.setHint(this.mHint);
            this.mRecognizing = false;
            if (this.mSpeechRecognitionCallback != null || this.mSpeechRecognizer == null) {
                return;
            }
            this.mSpeechOrbView.showNotListening();
            if (this.mListening) {
                this.mSpeechRecognizer.cancel();
                this.mListening = false;
            }
            this.mSpeechRecognizer.setRecognitionListener(null);
        }
    }

    public final void updateUi(boolean z) {
        if (z) {
            this.mBarBackground.setAlpha(this.mBackgroundSpeechAlpha);
            if (isVoiceMode()) {
                this.mSearchTextEditor.setTextColor(this.mTextHintColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            } else {
                this.mSearchTextEditor.setTextColor(this.mTextColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            }
        } else {
            this.mBarBackground.setAlpha(this.mBackgroundAlpha);
            this.mSearchTextEditor.setTextColor(this.mTextColor);
            this.mSearchTextEditor.setHintTextColor(this.mTextHintColor);
        }
        updateHint();
    }

    public SearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SearchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mAutoStartRecognition = false;
        this.mSoundMap = new SparseIntArray();
        this.mRecognizing = false;
        this.mContext = context;
        Resources resources = getResources();
        LayoutInflater.from(getContext()).inflate(R.layout.lb_search_bar, (ViewGroup) this, true);
        this.mBarHeight = getResources().getDimensionPixelSize(R.dimen.lb_search_bar_height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.mBarHeight);
        layoutParams.addRule(10, -1);
        setLayoutParams(layoutParams);
        setBackgroundColor(0);
        setClipChildren(false);
        this.mSearchQuery = "";
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.mTextColorSpeechMode = resources.getColor(R.color.lb_search_bar_text_speech_mode);
        this.mTextColor = resources.getColor(R.color.lb_search_bar_text);
        this.mBackgroundSpeechAlpha = resources.getInteger(R.integer.lb_search_bar_speech_mode_background_alpha);
        this.mBackgroundAlpha = resources.getInteger(R.integer.lb_search_bar_text_mode_background_alpha);
        this.mTextHintColorSpeechMode = resources.getColor(R.color.lb_search_bar_hint_speech_mode);
        this.mTextHintColor = resources.getColor(R.color.lb_search_bar_hint);
        this.mAudioManager = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
    }

    public void displayCompletions(CompletionInfo[] completionInfoArr) {
        this.mInputMethodManager.displayCompletions(this.mSearchTextEditor, completionInfoArr);
    }
}
