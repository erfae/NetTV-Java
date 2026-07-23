package com.getkeepsafe.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes.dex */
public class Program64Header extends Elf.ProgramHeader {
    public Program64Header(ElfParser elfParser, Elf.Header header, long j) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = (j * ((long) header.phentsize)) + header.phoff;
        this.type = elfParser.readWord(byteBufferAllocate, j2);
        elfParser.read(byteBufferAllocate, 8 + j2, 8);
        this.offset = byteBufferAllocate.getLong();
        elfParser.read(byteBufferAllocate, 16 + j2, 8);
        this.vaddr = byteBufferAllocate.getLong();
        elfParser.read(byteBufferAllocate, j2 + 40, 8);
        this.memsz = byteBufferAllocate.getLong();
    }
}
