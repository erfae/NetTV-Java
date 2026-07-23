package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@Beta
@GwtIncompatible
public final class FileBackedOutputStream extends OutputStream {

    @CheckForNull
    @GuardedBy("this")
    private File file;
    private final int fileThreshold;

    @CheckForNull
    @GuardedBy("this")
    private MemoryOutput memory;

    @GuardedBy("this")
    private OutputStream out;

    @CheckForNull
    private final File parentDirectory;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    public static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        public final byte[] getBuffer() {
            return ((ByteArrayOutputStream) this).buf;
        }

        public final int getCount() {
            return ((ByteArrayOutputStream) this).count;
        }
    }

    public FileBackedOutputStream(int i) {
        this(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        Objects.requireNonNull(this.memory);
        return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
    }

    @GuardedBy("this")
    private void update(int i) throws IOException {
        MemoryOutput memoryOutput = this.memory;
        if (memoryOutput == null || memoryOutput.getCount() + i <= this.fileThreshold) {
            return;
        }
        File fileCreateTempFile = File.createTempFile("FileBackedOutputStream", null, this.parentDirectory);
        if (this.resetOnFinalize) {
            fileCreateTempFile.deleteOnExit();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            fileOutputStream.write(this.memory.getBuffer(), 0, this.memory.getCount());
            fileOutputStream.flush();
            this.out = fileOutputStream;
            this.file = fileCreateTempFile;
            this.memory = null;
        } catch (IOException e) {
            fileCreateTempFile.delete();
            throw e;
        }
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.out.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    public synchronized void reset() throws IOException {
        try {
            close();
            MemoryOutput memoryOutput = this.memory;
            if (memoryOutput == null) {
                this.memory = new MemoryOutput();
            } else {
                memoryOutput.reset();
            }
            this.out = this.memory;
            File file = this.file;
            if (file != null) {
                this.file = null;
                if (!file.delete()) {
                    String strValueOf = String.valueOf(file);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 18);
                    sb.append("Could not delete: ");
                    sb.append(strValueOf);
                    throw new IOException(sb.toString());
                }
            }
        } catch (Throwable th) {
            if (this.memory == null) {
                this.memory = new MemoryOutput();
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            File file2 = this.file;
            if (file2 != null) {
                this.file = null;
                if (!file2.delete()) {
                    String strValueOf2 = String.valueOf(file2);
                    StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 18);
                    sb2.append("Could not delete: ");
                    sb2.append(strValueOf2);
                    throw new IOException(sb2.toString());
                }
            }
            throw th;
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i) throws IOException {
        update(1);
        this.out.write(i);
    }

    public FileBackedOutputStream(int i, boolean z) {
        this(i, z, null);
    }

    private FileBackedOutputStream(int i, boolean z, @CheckForNull File file) {
        this.fileThreshold = i;
        this.resetOnFinalize = z;
        this.parentDirectory = file;
        MemoryOutput memoryOutput = new MemoryOutput();
        this.memory = memoryOutput;
        this.out = memoryOutput;
        if (z) {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.1
                public final void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable th) {
                        th.printStackTrace(System.err);
                    }
                }

                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        } else {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.2
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) throws IOException {
        update(i2);
        this.out.write(bArr, i, i2);
    }
}
