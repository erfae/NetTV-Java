package androidx.leanback.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.FrameLayout;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.R;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.SpeechRecognitionCallback;
import androidx.leanback.widget.VerticalGridView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class SearchFragment extends Fragment {
    private static final String ARG_PREFIX;
    private static final String ARG_QUERY;
    private static final String ARG_TITLE;
    private static final String EXTRA_LEANBACK_BADGE_PRESENT = "LEANBACK_BADGE_PRESENT";
    private Drawable mBadgeDrawable;
    private ExternalQuery mExternalQuery;
    private boolean mIsPaused;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    public OnItemViewSelectedListener mOnItemViewSelectedListener;
    private boolean mPendingStartRecognitionWhenPaused;
    public SearchResultProvider mProvider;
    public ObjectAdapter mResultAdapter;
    public RowsFragment mRowsFragment;
    public SearchBar mSearchBar;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    public int mStatus;
    private String mTitle;
    public final ObjectAdapter.DataObserver mAdapterObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.app.SearchFragment.1
        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onChanged() {
            SearchFragment searchFragment = SearchFragment.this;
            searchFragment.mHandler.removeCallbacks(searchFragment.mResultsChangedCallback);
            SearchFragment searchFragment2 = SearchFragment.this;
            searchFragment2.mHandler.post(searchFragment2.mResultsChangedCallback);
        }
    };
    public final Handler mHandler = new Handler();
    public final Runnable mResultsChangedCallback = new Runnable() { // from class: androidx.leanback.app.SearchFragment.2
        @Override // java.lang.Runnable
        public void run() {
            RowsFragment rowsFragment = SearchFragment.this.mRowsFragment;
            if (rowsFragment != null) {
                ObjectAdapter adapter = rowsFragment.getAdapter();
                SearchFragment searchFragment = SearchFragment.this;
                if (adapter != searchFragment.mResultAdapter && (searchFragment.mRowsFragment.getAdapter() != null || SearchFragment.this.mResultAdapter.size() != 0)) {
                    SearchFragment searchFragment2 = SearchFragment.this;
                    searchFragment2.mRowsFragment.setAdapter(searchFragment2.mResultAdapter);
                    SearchFragment.this.mRowsFragment.setSelectedPosition(0);
                }
            }
            SearchFragment.this.updateSearchBarVisibility();
            SearchFragment searchFragment3 = SearchFragment.this;
            int i = searchFragment3.mStatus | 1;
            searchFragment3.mStatus = i;
            if ((i & 2) != 0) {
                searchFragment3.updateFocus();
            }
            SearchFragment.this.updateSearchBarNextFocusId();
        }
    };
    private final Runnable mSetSearchResultProvider = new Runnable() { // from class: androidx.leanback.app.SearchFragment.3
        @Override // java.lang.Runnable
        public void run() {
            ObjectAdapter objectAdapter;
            SearchFragment searchFragment = SearchFragment.this;
            if (searchFragment.mRowsFragment == null) {
                return;
            }
            ObjectAdapter resultsAdapter = searchFragment.mProvider.getResultsAdapter();
            SearchFragment searchFragment2 = SearchFragment.this;
            ObjectAdapter objectAdapter2 = searchFragment2.mResultAdapter;
            if (resultsAdapter != objectAdapter2) {
                boolean z = objectAdapter2 == null;
                if (objectAdapter2 != null) {
                    objectAdapter2.unregisterObserver(searchFragment2.mAdapterObserver);
                    searchFragment2.mResultAdapter = null;
                }
                SearchFragment searchFragment3 = SearchFragment.this;
                searchFragment3.mResultAdapter = resultsAdapter;
                if (resultsAdapter != null) {
                    resultsAdapter.registerObserver(searchFragment3.mAdapterObserver);
                }
                if (!z || ((objectAdapter = SearchFragment.this.mResultAdapter) != null && objectAdapter.size() != 0)) {
                    SearchFragment searchFragment4 = SearchFragment.this;
                    searchFragment4.mRowsFragment.setAdapter(searchFragment4.mResultAdapter);
                }
                SearchFragment searchFragment5 = SearchFragment.this;
                String str = searchFragment5.mPendingQuery;
                if (str != null && searchFragment5.mResultAdapter != null) {
                    searchFragment5.mPendingQuery = null;
                    if (searchFragment5.mProvider.onQueryTextChange(str)) {
                        searchFragment5.mStatus &= -3;
                    }
                }
            }
            SearchFragment.this.updateSearchBarNextFocusId();
            SearchFragment searchFragment6 = SearchFragment.this;
            if (!searchFragment6.mAutoStartRecognition) {
                searchFragment6.updateFocus();
                return;
            }
            searchFragment6.mHandler.removeCallbacks(searchFragment6.mStartRecognitionRunnable);
            SearchFragment searchFragment7 = SearchFragment.this;
            searchFragment7.mHandler.postDelayed(searchFragment7.mStartRecognitionRunnable, 300L);
        }
    };
    public final Runnable mStartRecognitionRunnable = new Runnable() { // from class: androidx.leanback.app.SearchFragment.4
        @Override // java.lang.Runnable
        public void run() {
            SearchFragment searchFragment = SearchFragment.this;
            searchFragment.mAutoStartRecognition = false;
            searchFragment.mSearchBar.startRecognition();
        }
    };
    public String mPendingQuery = null;
    public boolean mAutoStartRecognition = true;
    private SearchBar.SearchBarPermissionListener mPermissionListener = new SearchBar.SearchBarPermissionListener() { // from class: androidx.leanback.app.SearchFragment.5
        @Override // androidx.leanback.widget.SearchBar.SearchBarPermissionListener
        public void requestAudioPermission() {
            PermissionHelper.requestPermissions(SearchFragment.this, new String[]{"android.permission.RECORD_AUDIO"}, 0);
        }
    };

    public static class ExternalQuery {
        public String mQuery;
        public boolean mSubmit;

        public ExternalQuery(String str, boolean z) {
            this.mQuery = str;
            this.mSubmit = z;
        }
    }

    public interface SearchResultProvider {
        ObjectAdapter getResultsAdapter();

        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    static {
        String canonicalName = SearchFragment.class.getCanonicalName();
        ARG_PREFIX = canonicalName;
        ARG_QUERY = Insets$$ExternalSyntheticOutline0.m(canonicalName, ".query");
        ARG_TITLE = Insets$$ExternalSyntheticOutline0.m(canonicalName, ".title");
    }

    private void applyExternalQuery() {
        SearchBar searchBar;
        ExternalQuery externalQuery = this.mExternalQuery;
        if (externalQuery == null || (searchBar = this.mSearchBar) == null) {
            return;
        }
        searchBar.setSearchQuery(externalQuery.mQuery);
        ExternalQuery externalQuery2 = this.mExternalQuery;
        if (externalQuery2.mSubmit) {
            String str = externalQuery2.mQuery;
            this.mStatus |= 2;
            focusOnResults();
            SearchResultProvider searchResultProvider = this.mProvider;
            if (searchResultProvider != null) {
                searchResultProvider.onQueryTextSubmit(str);
            }
        }
        this.mExternalQuery = null;
    }

    public static Bundle createArgs(Bundle bundle, String str) {
        return createArgs(bundle, str, null);
    }

    private void focusOnResults() {
        RowsFragment rowsFragment = this.mRowsFragment;
        if (rowsFragment == null || rowsFragment.getVerticalGridView() == null || this.mResultAdapter.size() == 0 || !this.mRowsFragment.getVerticalGridView().requestFocus()) {
            return;
        }
        this.mStatus &= -2;
    }

    public static SearchFragment newInstance(String str) {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(createArgs(null, str));
        return searchFragment;
    }

    private void onSetSearchResultProvider() {
        this.mHandler.removeCallbacks(this.mSetSearchResultProvider);
        this.mHandler.post(this.mSetSearchResultProvider);
    }

    private void readArguments(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String str = ARG_QUERY;
        if (bundle.containsKey(str)) {
            setSearchQuery(bundle.getString(str));
        }
        String str2 = ARG_TITLE;
        if (bundle.containsKey(str2)) {
            setTitle(bundle.getString(str2));
        }
    }

    private void releaseRecognizer() {
        if (this.mSpeechRecognizer != null) {
            this.mSearchBar.setSpeechRecognizer(null);
            this.mSpeechRecognizer.destroy();
            this.mSpeechRecognizer = null;
        }
    }

    private void resultsAvailable() {
        if ((this.mStatus & 2) != 0) {
            focusOnResults();
        }
        updateSearchBarNextFocusId();
    }

    public void displayCompletions(List<String> list) {
        this.mSearchBar.displayCompletions(list);
    }

    public Drawable getBadgeDrawable() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getBadgeDrawable();
        }
        return null;
    }

    public Intent getRecognizerIntent() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null && searchBar.getHint() != null) {
            intent.putExtra("android.speech.extra.PROMPT", this.mSearchBar.getHint());
        }
        intent.putExtra(EXTRA_LEANBACK_BADGE_PRESENT, this.mBadgeDrawable != null);
        return intent;
    }

    public RowsFragment getRowsFragment() {
        return this.mRowsFragment;
    }

    public String getTitle() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getTitle();
        }
        return null;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        if (this.mAutoStartRecognition) {
            this.mAutoStartRecognition = bundle == null;
        }
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.lb_search_fragment, viewGroup, false);
        SearchBar searchBar = (SearchBar) ((FrameLayout) viewInflate.findViewById(R.id.lb_search_frame)).findViewById(R.id.lb_search_bar);
        this.mSearchBar = searchBar;
        searchBar.setSearchBarListener(new SearchBar.SearchBarListener() { // from class: androidx.leanback.app.SearchFragment.6
            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onKeyboardDismiss(String str) {
                SearchFragment.this.queryComplete();
            }

            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onSearchQueryChange(String str) {
                SearchFragment searchFragment = SearchFragment.this;
                SearchResultProvider searchResultProvider = searchFragment.mProvider;
                if (searchResultProvider == null) {
                    searchFragment.mPendingQuery = str;
                } else if (searchResultProvider.onQueryTextChange(str)) {
                    searchFragment.mStatus &= -3;
                }
            }

            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onSearchQuerySubmit(String str) {
                SearchFragment.this.submitQuery(str);
            }
        });
        this.mSearchBar.setSpeechRecognitionCallback(this.mSpeechRecognitionCallback);
        this.mSearchBar.setPermissionListener(this.mPermissionListener);
        applyExternalQuery();
        readArguments(getArguments());
        Drawable drawable = this.mBadgeDrawable;
        if (drawable != null) {
            setBadgeDrawable(drawable);
        }
        String str = this.mTitle;
        if (str != null) {
            setTitle(str);
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        int i = R.id.lb_results_frame;
        if (childFragmentManager.findFragmentById(i) == null) {
            this.mRowsFragment = new RowsFragment();
            getChildFragmentManager().beginTransaction().replace(i, this.mRowsFragment).commit();
        } else {
            this.mRowsFragment = (RowsFragment) getChildFragmentManager().findFragmentById(i);
        }
        this.mRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() { // from class: androidx.leanback.app.SearchFragment.7
            @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Row row) {
                SearchFragment.this.updateSearchBarVisibility();
                OnItemViewSelectedListener onItemViewSelectedListener = SearchFragment.this.mOnItemViewSelectedListener;
                if (onItemViewSelectedListener != null) {
                    onItemViewSelectedListener.onItemSelected(viewHolder, obj, viewHolder2, row);
                }
            }
        });
        this.mRowsFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mRowsFragment.setExpand(true);
        if (this.mProvider != null) {
            onSetSearchResultProvider();
        }
        return viewInflate;
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter != null) {
            objectAdapter.unregisterObserver(this.mAdapterObserver);
            this.mResultAdapter = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Fragment
    public void onPause() {
        releaseRecognizer();
        this.mIsPaused = true;
        super.onPause();
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 0 && strArr.length > 0 && strArr[0].equals("android.permission.RECORD_AUDIO") && iArr[0] == 0) {
            startRecognition();
        }
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.mIsPaused = false;
        if (this.mSpeechRecognitionCallback == null && this.mSpeechRecognizer == null) {
            SpeechRecognizer speechRecognizerCreateSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(FragmentUtil.getContext(this));
            this.mSpeechRecognizer = speechRecognizerCreateSpeechRecognizer;
            this.mSearchBar.setSpeechRecognizer(speechRecognizerCreateSpeechRecognizer);
        }
        if (!this.mPendingStartRecognitionWhenPaused) {
            this.mSearchBar.stopRecognition();
        } else {
            this.mPendingStartRecognitionWhenPaused = false;
            this.mSearchBar.startRecognition();
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        VerticalGridView verticalGridView = this.mRowsFragment.getVerticalGridView();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.lb_search_browse_rows_align_top);
        verticalGridView.setItemAlignmentOffset(0);
        verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignmentOffset(dimensionPixelSize);
        verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignment(0);
        verticalGridView.setFocusable(false);
        verticalGridView.setFocusableInTouchMode(false);
    }

    public final void queryComplete() {
        this.mStatus |= 2;
        focusOnResults();
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setBadgeDrawable(drawable);
        }
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
        if (onItemViewClickedListener != this.mOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = onItemViewClickedListener;
            RowsFragment rowsFragment = this.mRowsFragment;
            if (rowsFragment != null) {
                rowsFragment.setOnItemViewClickedListener(onItemViewClickedListener);
            }
        }
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
        this.mOnItemViewSelectedListener = onItemViewSelectedListener;
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColors(colors);
        }
    }

    public void setSearchAffordanceColorsInListening(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColorsInListening(colors);
        }
    }

    public void setSearchQuery(String str, boolean z) {
        if (str == null) {
            return;
        }
        this.mExternalQuery = new ExternalQuery(str, z);
        applyExternalQuery();
        if (this.mAutoStartRecognition) {
            this.mAutoStartRecognition = false;
            this.mHandler.removeCallbacks(this.mStartRecognitionRunnable);
        }
    }

    public void setSearchResultProvider(SearchResultProvider searchResultProvider) {
        if (this.mProvider != searchResultProvider) {
            this.mProvider = searchResultProvider;
            onSetSearchResultProvider();
        }
    }

    @Deprecated
    public void setSpeechRecognitionCallback(SpeechRecognitionCallback speechRecognitionCallback) {
        this.mSpeechRecognitionCallback = speechRecognitionCallback;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSpeechRecognitionCallback(speechRecognitionCallback);
        }
        if (speechRecognitionCallback != null) {
            releaseRecognizer();
        }
    }

    public void setTitle(String str) {
        this.mTitle = str;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setTitle(str);
        }
    }

    public void startRecognition() {
        if (this.mIsPaused) {
            this.mPendingStartRecognitionWhenPaused = true;
        } else {
            this.mSearchBar.startRecognition();
        }
    }

    public final void submitQuery(String str) {
        this.mStatus |= 2;
        focusOnResults();
        SearchResultProvider searchResultProvider = this.mProvider;
        if (searchResultProvider != null) {
            searchResultProvider.onQueryTextSubmit(str);
        }
    }

    public final void updateFocus() {
        RowsFragment rowsFragment;
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter == null || objectAdapter.size() <= 0 || (rowsFragment = this.mRowsFragment) == null || rowsFragment.getAdapter() != this.mResultAdapter) {
            this.mSearchBar.requestFocus();
        } else {
            focusOnResults();
        }
    }

    public final void updateSearchBarNextFocusId() {
        ObjectAdapter objectAdapter;
        RowsFragment rowsFragment;
        if (this.mSearchBar == null || (objectAdapter = this.mResultAdapter) == null) {
            return;
        }
        this.mSearchBar.setNextFocusDownId((objectAdapter.size() == 0 || (rowsFragment = this.mRowsFragment) == null || rowsFragment.getVerticalGridView() == null) ? 0 : this.mRowsFragment.getVerticalGridView().getId());
    }

    public final void updateSearchBarVisibility() {
        ObjectAdapter objectAdapter;
        RowsFragment rowsFragment = this.mRowsFragment;
        this.mSearchBar.setVisibility(((rowsFragment != null ? rowsFragment.getSelectedPosition() : -1) <= 0 || (objectAdapter = this.mResultAdapter) == null || objectAdapter.size() == 0) ? 0 : 8);
    }

    public static Bundle createArgs(Bundle bundle, String str, String str2) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(ARG_QUERY, str);
        bundle.putString(ARG_TITLE, str2);
        return bundle;
    }

    public void displayCompletions(CompletionInfo[] completionInfoArr) {
        this.mSearchBar.displayCompletions(completionInfoArr);
    }

    public void setSearchQuery(Intent intent, boolean z) {
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
        if (stringArrayListExtra == null || stringArrayListExtra.size() <= 0) {
            return;
        }
        setSearchQuery(stringArrayListExtra.get(0), z);
    }

    private void setSearchQuery(String str) {
        this.mSearchBar.setSearchQuery(str);
    }
}
