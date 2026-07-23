package com.nettv.livestore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.ExoPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleView extends TextView implements Runnable {
    private static final boolean DEBUG = false;
    private static final String TAG = "SubtitleView";
    private static final int UPDATE_INTERVAL = 300;
    private ExoPlayer player;
    private TreeMap<Long, Line> track;

    public static class Line {
        public String text;
        public long to;

        public Line(long j, long j2, String str) {
            this.to = j2;
            this.text = str;
        }
    }

    public SubtitleView(Context context) {
        super(context);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private TreeMap<Long, Line> getSubtitleFile(String str) throws Throwable {
        Throwable th;
        InputStream inputStream;
        try {
            inputStream = ((HttpURLConnection) new URL(str).openConnection()).getInputStream();
            try {
                try {
                    TreeMap<Long, Line> treeMap = parse(inputStream);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return treeMap;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    private String getTimedText(long j) {
        String str = "";
        for (Map.Entry<Long, Line> entry : this.track.entrySet()) {
            if (j < entry.getKey().longValue()) {
                break;
            }
            if (j < entry.getValue().to) {
                str = entry.getValue().text;
            }
        }
        return str;
    }

    public static TreeMap<Long, Line> parse(InputStream inputStream) throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream, "UTF-8"));
        TreeMap<Long, Line> treeMap = new TreeMap<>();
        while (lineNumberReader.readLine() != null) {
            String line = lineNumberReader.readLine();
            String strM = "";
            while (true) {
                String line2 = lineNumberReader.readLine();
                if (line2 == null || line2.trim().equals("")) {
                    break;
                }
                strM = Insets$$ExternalSyntheticOutline0.m(strM, line2, "\n");
            }
            long j = parse(line.split("-->")[0]);
            treeMap.put(Long.valueOf(j), new Line(j, parse(line.split("-->")[1]), strM));
        }
        return treeMap;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(this, 300L);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null && this.track != null) {
            long currentPosition = exoPlayer.getCurrentPosition() / 1000;
            setText("" + getTimedText(this.player.getCurrentPosition()));
        }
        postDelayed(this, 300L);
    }

    public String secondsToDuration(long j) {
        return String.format("%02d:%02d:%02d", Long.valueOf(j / 3600), Long.valueOf((j % 3600) / 60), Long.valueOf(j % 60), Locale.US);
    }

    public void setPlayer(ExoPlayer exoPlayer) {
        this.player = exoPlayer;
    }

    public void setSubSource(String str) {
        this.track = getSubtitleFile(str);
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SubtitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private static long parse(String str) {
        long j = Long.parseLong(str.split(":")[0].trim());
        long j2 = Long.parseLong(str.split(":")[1].trim());
        long j3 = Long.parseLong(str.split(":")[2].split(",")[0].trim()) * 1000;
        return j3 + (j2 * 60 * 1000) + (j * 60 * 60 * 1000) + Long.parseLong(str.split(":")[2].split(",")[1].trim());
    }
}
