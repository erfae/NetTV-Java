package com.nettv.livestore.helper;

import android.widget.Toast;
import com.nettv.livestore.dlgfragment.AddPlaylistDlgFragment;
import com.nettv.livestore.utils.Utils;

/** Handles local fixed portals after the existing form validation. */
public final class PortalCredentialUi {
    private PortalCredentialUi() { }

    public static boolean saveFixed(AddPlaylistDlgFragment form) {
        if (form.playlist_position < 0 || form.playlist_position >= 3) {
            return false;
        }
        if (form.is_xc) {
            form.playlistUrl = PortalCredentialStore.xtreamUrl(form.playlist_position,
                    form.username, form.et_password.getText().toString().trim());
        }
        if (!PortalCredentialStore.save(form.sharedPreferenceHelper.settings,
                form.appInfoModel, form.playlist_position, form.playlistUrl, form.playlistType)) {
            Toast.makeText(form.context, "No se pudo guardar el portal", 0).show();
            return true;
        }
        form.sharedPreferenceHelper.setSharedPreferenceAppInfo(form.appInfoModel);
        Utils.saveToFile(form.appInfoModel);
        Toast.makeText(form.context, "Credenciales guardadas", 0).show();
        form.listener.onSkip();
        return true;
    }
}
