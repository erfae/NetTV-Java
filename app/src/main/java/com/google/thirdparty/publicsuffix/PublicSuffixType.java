package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@GwtCompatible
public enum PublicSuffixType {
    PRIVATE(':', ','),
    REGISTRY('!', '?');

    private final char innerNodeCode;
    private final char leafNodeCode;

    PublicSuffixType(char c, char c2) {
        this.innerNodeCode = c;
        this.leafNodeCode = c2;
    }

    public static PublicSuffixType fromCode(char c) {
        for (PublicSuffixType publicSuffixType : values()) {
            if (publicSuffixType.innerNodeCode == c || publicSuffixType.leafNodeCode == c) {
                return publicSuffixType;
            }
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("No enum corresponding to given code: ");
        sb.append(c);
        throw new IllegalArgumentException(sb.toString());
    }
}
