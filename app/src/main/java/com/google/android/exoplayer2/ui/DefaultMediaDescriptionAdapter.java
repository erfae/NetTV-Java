package com.google.android.exoplayer2.ui;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Player;

/* JADX INFO: loaded from: classes.dex */
public final class DefaultMediaDescriptionAdapter implements PlayerNotificationManager.MediaDescriptionAdapter {

    @Nullable
    private final PendingIntent pendingIntent;

    public DefaultMediaDescriptionAdapter(@Nullable PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    @Nullable
    public PendingIntent createCurrentContentIntent(Player player) {
        return this.pendingIntent;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    @Nullable
    public CharSequence getCurrentContentText(Player player) {
        if (!player.isCommandAvailable(18)) {
            return null;
        }
        CharSequence charSequence = player.getMediaMetadata().artist;
        return !TextUtils.isEmpty(charSequence) ? charSequence : player.getMediaMetadata().albumArtist;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public CharSequence getCurrentContentTitle(Player player) {
        if (!player.isCommandAvailable(18)) {
            return "";
        }
        CharSequence charSequence = player.getMediaMetadata().displayTitle;
        if (!TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        CharSequence charSequence2 = player.getMediaMetadata().title;
        return charSequence2 != null ? charSequence2 : "";
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    @Nullable
    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback bitmapCallback) {
        byte[] bArr;
        if (player.isCommandAvailable(18) && (bArr = player.getMediaMetadata().artworkData) != null) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
    public final /* synthetic */ CharSequence getCurrentSubText(Player player) {
        return PlayerNotificationManager.MediaDescriptionAdapter.CC.$default$getCurrentSubText(this, player);
    }
}
