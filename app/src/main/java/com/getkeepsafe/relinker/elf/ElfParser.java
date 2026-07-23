package com.getkeepsafe.relinker.elf;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.UShort;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes.dex */
public class ElfParser implements Closeable, Elf {
    private final int MAGIC = 1179403647;
    private final FileChannel channel;

    public ElfParser(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.channel = new FileInputStream(file).getChannel();
    }

    private long offsetFromVma(Elf.Header header, long j, long j2) throws IOException {
        for (long j3 = 0; j3 < j; j3++) {
            Elf.ProgramHeader programHeader = header.getProgramHeader(j3);
            if (programHeader.type == 1) {
                long j4 = programHeader.vaddr;
                if (j4 <= j2 && j2 <= programHeader.memsz + j4) {
                    return (j2 - j4) + programHeader.offset;
                }
            }
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.channel.close();
    }

    public Elf.Header parseHeader() throws IOException {
        this.channel.position(0L);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        if (readWord(byteBufferAllocate, 0L) != 1179403647) {
            throw new IllegalArgumentException("Invalid ELF Magic!");
        }
        short s = readByte(byteBufferAllocate, 4L);
        boolean z = readByte(byteBufferAllocate, 5L) == 2;
        if (s == 1) {
            return new Elf32Header(z, this);
        }
        if (s == 2) {
            return new Elf64Header(z, this);
        }
        throw new IllegalStateException("Invalid class type!");
    }

    public List<String> parseNeededDependencies() throws IOException {
        long j;
        this.channel.position(0L);
        ArrayList arrayList = new ArrayList();
        Elf.Header header = parseHeader();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = header.phnum;
        int i = 0;
        if (j2 == WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            j2 = header.getSectionHeader(0).info;
        }
        long j3 = 0;
        while (true) {
            if (j3 >= j2) {
                j = 0;
                break;
            }
            Elf.ProgramHeader programHeader = header.getProgramHeader(j3);
            if (programHeader.type == 2) {
                j = programHeader.offset;
                break;
            }
            j3++;
        }
        if (j == 0) {
            return Collections.unmodifiableList(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        long j4 = 0;
        while (true) {
            Elf.DynamicStructure dynamicStructure = header.getDynamicStructure(j, i);
            long j5 = j;
            long j6 = dynamicStructure.tag;
            if (j6 == 1) {
                arrayList2.add(Long.valueOf(dynamicStructure.val));
            } else if (j6 == 5) {
                j4 = dynamicStructure.val;
            }
            i++;
            if (dynamicStructure.tag == 0) {
                break;
            }
            j = j5;
        }
        if (j4 == 0) {
            throw new IllegalStateException("String table offset not found!");
        }
        long jOffsetFromVma = offsetFromVma(header, j2, j4);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            long jLongValue = ((Long) it.next()).longValue() + jOffsetFromVma;
            StringBuilder sb = new StringBuilder();
            while (true) {
                long j7 = jLongValue + 1;
                short s = readByte(byteBufferAllocate, jLongValue);
                if (s != 0) {
                    sb.append((char) s);
                    jLongValue = j7;
                }
            }
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public final void read(ByteBuffer byteBuffer, long j, int i) throws IOException {
        byteBuffer.position(0);
        byteBuffer.limit(i);
        long j2 = 0;
        while (j2 < i) {
            int i2 = this.channel.read(byteBuffer, j + j2);
            if (i2 == -1) {
                throw new EOFException();
            }
            j2 += (long) i2;
        }
        byteBuffer.position(0);
    }

    public final short readByte(ByteBuffer byteBuffer, long j) throws IOException {
        read(byteBuffer, j, 1);
        return (short) (byteBuffer.get() & 255);
    }

    public final int readHalf(ByteBuffer byteBuffer, long j) throws IOException {
        read(byteBuffer, j, 2);
        return byteBuffer.getShort() & UShort.MAX_VALUE;
    }

    public final long readWord(ByteBuffer byteBuffer, long j) throws IOException {
        read(byteBuffer, j, 4);
        return ((long) byteBuffer.getInt()) & 4294967295L;
    }
}
