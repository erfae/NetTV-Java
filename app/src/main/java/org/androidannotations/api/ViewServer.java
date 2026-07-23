package org.androidannotations.api;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes2.dex */
public class ViewServer implements Runnable {
    private static final String BUILD_TYPE_USER = "user";
    private static final String COMMAND_PROTOCOL_VERSION = "PROTOCOL";
    private static final String COMMAND_SERVER_VERSION = "SERVER";
    private static final String COMMAND_WINDOW_MANAGER_AUTOLIST = "AUTOLIST";
    private static final String COMMAND_WINDOW_MANAGER_GET_FOCUS = "GET_FOCUS";
    private static final String COMMAND_WINDOW_MANAGER_LIST = "LIST";
    private static final String LOG_TAG = "ViewServer";
    private static final String VALUE_PROTOCOL_VERSION = "4";
    private static final String VALUE_SERVER_VERSION = "4";
    private static final int VIEW_SERVER_DEFAULT_PORT = 4939;
    private static final int VIEW_SERVER_MAX_CONNECTIONS = 10;
    private static ViewServer sServer;
    private final ReentrantReadWriteLock mFocusLock;
    private View mFocusedWindow;
    private final List<WindowListener> mListeners;
    private final int mPort;
    private ServerSocket mServer;
    private Thread mThread;
    private ExecutorService mThreadPool;
    private final HashMap<View, String> mWindows;
    private final ReentrantReadWriteLock mWindowsLock;

    public static class NoopViewServer extends ViewServer {
        @Override // org.androidannotations.api.ViewServer
        public void addWindow(Activity activity) {
        }

        @Override // org.androidannotations.api.ViewServer
        public void addWindow(View view, String str) {
        }

        @Override // org.androidannotations.api.ViewServer
        public boolean isRunning() {
            return false;
        }

        @Override // org.androidannotations.api.ViewServer
        public void removeWindow(Activity activity) {
        }

        @Override // org.androidannotations.api.ViewServer
        public void removeWindow(View view) {
        }

        @Override // org.androidannotations.api.ViewServer, java.lang.Runnable
        public void run() {
        }

        @Override // org.androidannotations.api.ViewServer
        public void setFocusedWindow(Activity activity) {
        }

        @Override // org.androidannotations.api.ViewServer
        public void setFocusedWindow(View view) {
        }

        @Override // org.androidannotations.api.ViewServer
        public boolean start() throws IOException {
            return false;
        }

        @Override // org.androidannotations.api.ViewServer
        public boolean stop() {
            return false;
        }

        private NoopViewServer() {
            super();
        }
    }

    public static class UncloseableOuputStream extends OutputStream {
        private final OutputStream mStream;

        public UncloseableOuputStream(OutputStream outputStream) {
            this.mStream = outputStream;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        public boolean equals(Object obj) {
            return this.mStream.equals(obj);
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.mStream.flush();
        }

        public int hashCode() {
            return this.mStream.hashCode();
        }

        public String toString() {
            return this.mStream.toString();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.mStream.write(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.mStream.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.mStream.write(i);
        }
    }

    public class ViewServerWorker implements Runnable, WindowListener {
        private Socket mClient;
        private final Object[] mLock = new Object[0];
        private boolean mNeedWindowListUpdate = false;
        private boolean mNeedFocusedWindowUpdate = false;

        public ViewServerWorker(Socket socket) {
            this.mClient = socket;
        }

        private View findWindow(int i) {
            if (i == -1) {
                ViewServer.this.mWindowsLock.readLock().lock();
                try {
                    return ViewServer.this.mFocusedWindow;
                } finally {
                    ViewServer.this.mWindowsLock.readLock().unlock();
                }
            }
            ViewServer.this.mWindowsLock.readLock().lock();
            try {
                for (Map.Entry entry : ViewServer.this.mWindows.entrySet()) {
                    if (System.identityHashCode(entry.getKey()) == i) {
                        return (View) entry.getKey();
                    }
                }
                return null;
            } finally {
                ViewServer.this.mWindowsLock.readLock().unlock();
            }
        }

        private boolean getFocusedWindow(Socket socket) throws Throwable {
            boolean z = false;
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
                    try {
                        ViewServer.this.mFocusLock.readLock().lock();
                        try {
                            View view = ViewServer.this.mFocusedWindow;
                            ViewServer.this.mFocusLock.readLock().unlock();
                            if (view != null) {
                                ViewServer.this.mWindowsLock.readLock().lock();
                                try {
                                    String str = (String) ViewServer.this.mWindows.get(ViewServer.this.mFocusedWindow);
                                    ViewServer.this.mWindowsLock.readLock().unlock();
                                    bufferedWriter2.write(Integer.toHexString(System.identityHashCode(view)));
                                    bufferedWriter2.write(32);
                                    bufferedWriter2.append((CharSequence) str);
                                } catch (Throwable th) {
                                    ViewServer.this.mWindowsLock.readLock().unlock();
                                    throw th;
                                }
                            }
                            bufferedWriter2.write(10);
                            bufferedWriter2.flush();
                            bufferedWriter2.close();
                            z = true;
                            return z;
                        } catch (Throwable th2) {
                            ViewServer.this.mFocusLock.readLock().unlock();
                            throw th2;
                        }
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        return z;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception unused3) {
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (IOException unused4) {
            }
        }

        private boolean listWindows(Socket socket) throws Throwable {
            boolean z = false;
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    ViewServer.this.mWindowsLock.readLock().lock();
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
                    try {
                        for (Map.Entry entry : ViewServer.this.mWindows.entrySet()) {
                            bufferedWriter2.write(Integer.toHexString(System.identityHashCode(entry.getKey())));
                            bufferedWriter2.write(32);
                            bufferedWriter2.append((CharSequence) entry.getValue());
                            bufferedWriter2.write(10);
                        }
                        bufferedWriter2.write("DONE.\n");
                        bufferedWriter2.flush();
                        ViewServer.this.mWindowsLock.readLock().unlock();
                        bufferedWriter2.close();
                        z = true;
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        ViewServer.this.mWindowsLock.readLock().unlock();
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        ViewServer.this.mWindowsLock.readLock().unlock();
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException unused4) {
            }
            return z;
        }

        private boolean windowCommand(Socket socket, String str, String str2) throws Throwable {
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    try {
                        int iIndexOf = str2.indexOf(32);
                        if (iIndexOf == -1) {
                            iIndexOf = str2.length();
                        }
                        int i = (int) Long.parseLong(str2.substring(0, iIndexOf), 16);
                        str2 = iIndexOf < str2.length() ? str2.substring(iIndexOf + 1) : "";
                        View viewFindWindow = findWindow(i);
                        if (viewFindWindow == null) {
                            return false;
                        }
                        Method declaredMethod = ViewDebug.class.getDeclaredMethod("dispatchCommand", View.class, String.class, String.class, OutputStream.class);
                        declaredMethod.setAccessible(true);
                        declaredMethod.invoke(null, viewFindWindow, str, str2, new UncloseableOuputStream(socket.getOutputStream()));
                        if (!socket.isOutputShutdown()) {
                            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            try {
                                bufferedWriter2.write("DONE\n");
                                bufferedWriter2.flush();
                                bufferedWriter = bufferedWriter2;
                            } catch (Exception e) {
                                e = e;
                                bufferedWriter = bufferedWriter2;
                            } catch (Throwable th) {
                                th = th;
                                bufferedWriter = bufferedWriter2;
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                throw th;
                            }
                        }
                        if (bufferedWriter == null) {
                            return true;
                        }
                        bufferedWriter.close();
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
                Log.w(ViewServer.LOG_TAG, "Could not send command " + str + " with parameters " + str2, e);
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException unused2) {
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:51:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Not initialized variable reg: 2, insn: 0x0071: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:41:0x0071 */
        private boolean windowManagerAutolistLoop() throws Throwable {
            BufferedWriter bufferedWriter;
            Exception e;
            BufferedWriter bufferedWriter2;
            boolean z;
            boolean z2;
            boolean z3;
            ViewServer.this.addWindowListener(this);
            BufferedWriter bufferedWriter3 = null;
            try {
                try {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.mClient.getOutputStream()));
                    while (!Thread.interrupted()) {
                        try {
                            synchronized (this.mLock) {
                                while (true) {
                                    z = this.mNeedWindowListUpdate;
                                    if (z || this.mNeedFocusedWindowUpdate) {
                                        break;
                                    }
                                    this.mLock.wait();
                                }
                                z2 = false;
                                if (z) {
                                    this.mNeedWindowListUpdate = false;
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                                if (this.mNeedFocusedWindowUpdate) {
                                    this.mNeedFocusedWindowUpdate = false;
                                    z2 = true;
                                }
                            }
                            if (z3) {
                                bufferedWriter.write("LIST UPDATE\n");
                                bufferedWriter.flush();
                            }
                            if (z2) {
                                bufferedWriter.write("FOCUS UPDATE\n");
                                bufferedWriter.flush();
                            }
                        } catch (Exception e2) {
                            e = e2;
                            Log.w(ViewServer.LOG_TAG, "Connection error: ", e);
                            if (bufferedWriter != null) {
                            }
                            ViewServer.this.removeWindowListener(this);
                            return true;
                        }
                    }
                } catch (Exception e3) {
                    bufferedWriter = null;
                    e = e3;
                } catch (Throwable th) {
                    th = th;
                    if (bufferedWriter3 != null) {
                        try {
                            bufferedWriter3.close();
                        } catch (IOException unused) {
                        }
                    }
                    ViewServer.this.removeWindowListener(this);
                    throw th;
                }
                try {
                    bufferedWriter.close();
                } catch (IOException unused2) {
                }
                ViewServer.this.removeWindowListener(this);
                return true;
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter3 = bufferedWriter2;
                if (bufferedWriter3 != null) {
                    bufferedWriter3.close();
                }
                ViewServer.this.removeWindowListener(this);
                throw th;
            }
        }

        @Override // org.androidannotations.api.ViewServer.WindowListener
        public void focusChanged() {
            synchronized (this.mLock) {
                this.mNeedFocusedWindowUpdate = true;
                this.mLock.notifyAll();
            }
        }

        /* JADX WARN: Code duplicated, block: B:75:0x00cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:77:0x00d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:84:? A[SYNTHETIC] */
        @Override // java.lang.Runnable
        public void run() throws Throwable {
            BufferedReader bufferedReader;
            IOException e;
            Socket socket;
            Socket socket2;
            String strSubstring;
            boolean zWindowManagerAutolistLoop;
            BufferedReader bufferedReader2 = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(this.mClient.getInputStream()), 1024);
                try {
                    try {
                        String line = bufferedReader.readLine();
                        int iIndexOf = line.indexOf(32);
                        if (iIndexOf == -1) {
                            strSubstring = "";
                        } else {
                            String strSubstring2 = line.substring(0, iIndexOf);
                            strSubstring = line.substring(iIndexOf + 1);
                            line = strSubstring2;
                        }
                        if (ViewServer.COMMAND_PROTOCOL_VERSION.equalsIgnoreCase(line) || ViewServer.COMMAND_SERVER_VERSION.equalsIgnoreCase(line)) {
                            zWindowManagerAutolistLoop = ViewServer.access$200(this.mClient);
                        } else if (ViewServer.COMMAND_WINDOW_MANAGER_LIST.equalsIgnoreCase(line)) {
                            zWindowManagerAutolistLoop = listWindows(this.mClient);
                        } else if (ViewServer.COMMAND_WINDOW_MANAGER_GET_FOCUS.equalsIgnoreCase(line)) {
                            zWindowManagerAutolistLoop = getFocusedWindow(this.mClient);
                        } else {
                            zWindowManagerAutolistLoop = ViewServer.COMMAND_WINDOW_MANAGER_AUTOLIST.equalsIgnoreCase(line) ? windowManagerAutolistLoop() : windowCommand(this.mClient, line, strSubstring);
                        }
                        if (!zWindowManagerAutolistLoop) {
                            Log.w(ViewServer.LOG_TAG, "An error occurred with the command: " + line);
                        }
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        socket2 = this.mClient;
                        if (socket2 == null) {
                            return;
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        socket = this.mClient;
                        if (socket != null) {
                            throw th;
                        }
                        try {
                            socket.close();
                            throw th;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                } catch (IOException e5) {
                    e = e5;
                    Log.w(ViewServer.LOG_TAG, "Connection error: ", e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    socket2 = this.mClient;
                    if (socket2 == null) {
                        return;
                    }
                }
            } catch (IOException e7) {
                bufferedReader = null;
                e = e7;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                socket = this.mClient;
                if (socket != null) {
                    throw th;
                }
                socket.close();
                throw th;
            }
            try {
                socket2.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        }

        @Override // org.androidannotations.api.ViewServer.WindowListener
        public void windowsChanged() {
            synchronized (this.mLock) {
                this.mNeedWindowListUpdate = true;
                this.mLock.notifyAll();
            }
        }
    }

    public interface WindowListener {
        void focusChanged();

        void windowsChanged();
    }

    public static /* synthetic */ boolean access$200(Socket socket) {
        return writeValue(socket, "4");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addWindowListener(WindowListener windowListener) {
        if (this.mListeners.contains(windowListener)) {
            return;
        }
        this.mListeners.add(windowListener);
    }

    private void fireFocusChangedEvent() {
        Iterator<WindowListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().focusChanged();
        }
    }

    private void fireWindowsChangedEvent() {
        Iterator<WindowListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().windowsChanged();
        }
    }

    public static ViewServer get(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (!BUILD_TYPE_USER.equals(Build.TYPE) || (applicationInfo.flags & 2) == 0) {
            sServer = new NoopViewServer();
        } else {
            if (sServer == null) {
                sServer = new ViewServer(VIEW_SERVER_DEFAULT_PORT);
            }
            if (!sServer.isRunning()) {
                try {
                    sServer.start();
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Error:", e);
                }
            }
        }
        return sServer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeWindowListener(WindowListener windowListener) {
        this.mListeners.remove(windowListener);
    }

    private static boolean writeValue(Socket socket, String str) throws Throwable {
        boolean z = false;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
                try {
                    bufferedWriter2.write(str);
                    bufferedWriter2.write("\n");
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    z = true;
                } catch (Exception unused) {
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    return z;
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
        return z;
    }

    public void addWindow(Activity activity) {
        String string;
        String string2 = activity.getTitle().toString();
        if (TextUtils.isEmpty(string2)) {
            string = activity.getClass().getCanonicalName() + "/0x" + System.identityHashCode(activity);
        } else {
            StringBuilder sbM25m = Insets$$ExternalSyntheticOutline0.m25m(string2, "(");
            sbM25m.append(activity.getClass().getCanonicalName());
            sbM25m.append(")");
            string = sbM25m.toString();
        }
        addWindow(activity.getWindow().getDecorView(), string);
    }

    public boolean isRunning() {
        Thread thread = this.mThread;
        return thread != null && thread.isAlive();
    }

    public void removeWindow(Activity activity) {
        removeWindow(activity.getWindow().getDecorView());
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.mServer = new ServerSocket(this.mPort, 10, InetAddress.getLocalHost());
        } catch (Exception e) {
            Log.w(LOG_TAG, "Starting ServerSocket error: ", e);
        }
        while (this.mServer != null && Thread.currentThread() == this.mThread) {
            try {
                Socket socketAccept = this.mServer.accept();
                ExecutorService executorService = this.mThreadPool;
                if (executorService != null) {
                    executorService.submit(new ViewServerWorker(socketAccept));
                } else {
                    try {
                        socketAccept.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                Log.w(LOG_TAG, "Connection error: ", e3);
            }
        }
    }

    public void setFocusedWindow(Activity activity) {
        setFocusedWindow(activity.getWindow().getDecorView());
    }

    public boolean start() throws IOException {
        if (this.mThread != null) {
            return false;
        }
        this.mThread = new Thread(this, Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("Local View Server [port="), this.mPort, "]"));
        this.mThreadPool = Executors.newFixedThreadPool(10);
        this.mThread.start();
        return true;
    }

    public boolean stop() {
        Thread thread = this.mThread;
        if (thread != null) {
            thread.interrupt();
            ExecutorService executorService = this.mThreadPool;
            if (executorService != null) {
                try {
                    executorService.shutdownNow();
                } catch (SecurityException unused) {
                    Log.w(LOG_TAG, "Could not stop all view server threads");
                }
            }
            this.mThreadPool = null;
            this.mThread = null;
            try {
                this.mServer.close();
                this.mServer = null;
                return true;
            } catch (IOException unused2) {
                Log.w(LOG_TAG, "Could not close the view server");
            }
        }
        this.mWindowsLock.writeLock().lock();
        try {
            this.mWindows.clear();
            this.mWindowsLock.writeLock().unlock();
            this.mFocusLock.writeLock().lock();
            try {
                this.mFocusedWindow = null;
                return false;
            } finally {
                this.mFocusLock.writeLock().unlock();
            }
        } catch (Throwable th) {
            this.mWindowsLock.writeLock().unlock();
            throw th;
        }
    }

    private ViewServer() {
        this.mListeners = new CopyOnWriteArrayList();
        this.mWindows = new HashMap<>();
        this.mWindowsLock = new ReentrantReadWriteLock();
        this.mFocusLock = new ReentrantReadWriteLock();
        this.mPort = -1;
    }

    public void removeWindow(View view) {
        this.mWindowsLock.writeLock().lock();
        try {
            this.mWindows.remove(view.getRootView());
            this.mWindowsLock.writeLock().unlock();
            fireWindowsChangedEvent();
        } catch (Throwable th) {
            this.mWindowsLock.writeLock().unlock();
            throw th;
        }
    }

    public void setFocusedWindow(View view) {
        View rootView;
        this.mFocusLock.writeLock().lock();
        if (view == null) {
            rootView = null;
        } else {
            try {
                rootView = view.getRootView();
            } catch (Throwable th) {
                this.mFocusLock.writeLock().unlock();
                throw th;
            }
        }
        this.mFocusedWindow = rootView;
        this.mFocusLock.writeLock().unlock();
        fireFocusChangedEvent();
    }

    private ViewServer(int i) {
        this.mListeners = new CopyOnWriteArrayList();
        this.mWindows = new HashMap<>();
        this.mWindowsLock = new ReentrantReadWriteLock();
        this.mFocusLock = new ReentrantReadWriteLock();
        this.mPort = i;
    }

    public void addWindow(View view, String str) {
        this.mWindowsLock.writeLock().lock();
        try {
            this.mWindows.put(view.getRootView(), str);
            this.mWindowsLock.writeLock().unlock();
            fireWindowsChangedEvent();
        } catch (Throwable th) {
            this.mWindowsLock.writeLock().unlock();
            throw th;
        }
    }
}
