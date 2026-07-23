package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
public interface MenuProvider {

    /* JADX INFO: renamed from: androidx.core.view.MenuProvider$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onMenuClosed(MenuProvider menuProvider, @NonNull Menu menu) {
        }

        public static void $default$onPrepareMenu(MenuProvider menuProvider, @NonNull Menu menu) {
        }
    }

    void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater);

    void onMenuClosed(@NonNull Menu menu);

    boolean onMenuItemSelected(@NonNull MenuItem menuItem);

    void onPrepareMenu(@NonNull Menu menu);
}
