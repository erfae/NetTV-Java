package androidx.multidex;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipException;

/* JADX INFO: loaded from: classes.dex */
final class ZipUtil {
    private static final int BUFFER_SIZE = 16384;
    private static final int ENDHDR = 22;
    private static final int ENDSIG = 101010256;

    public static class CentralDirectory {
        public long offset;
        public long size;
    }

    public static CentralDirectory findCentralDirectory(RandomAccessFile randomAccessFile) throws IOException {
        long length = randomAccessFile.length() - 22;
        if (length < 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("File too short to be a zip file: ");
            sbM.append(randomAccessFile.length());
            throw new ZipException(sbM.toString());
        }
        long j = length - PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        long j2 = j >= 0 ? j : 0L;
        int iReverseBytes = Integer.reverseBytes(ENDSIG);
        do {
            randomAccessFile.seek(length);
            if (randomAccessFile.readInt() == iReverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.size = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                centralDirectory.offset = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                return centralDirectory;
            }
            length--;
        } while (length >= j2);
        throw new ZipException("End Of Central Directory signature not found");
    }
}
