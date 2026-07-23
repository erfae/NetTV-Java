package androidx.constraintlayout.core.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public class CLParser {
    private boolean hasComment = false;
    private int lineNumber;
    private String mContent;

    /* JADX INFO: renamed from: androidx.constraintlayout.core.parser.CLParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE;

        static {
            int[] iArr = new int[TYPE.values().length];
            $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE = iArr;
            try {
                iArr[TYPE.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.KEY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.TOKEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public enum TYPE {
        UNKNOWN,
        OBJECT,
        ARRAY,
        NUMBER,
        STRING,
        KEY,
        TOKEN
    }

    public CLParser(String str) {
        this.mContent = str;
    }

    private CLElement createElement(CLElement cLElement, int i, TYPE type, boolean z, char[] cArr) {
        CLElement cLElementAllocate;
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[type.ordinal()]) {
            case 1:
                cLElementAllocate = CLObject.allocate(cArr);
                i++;
                break;
            case 2:
                cLElementAllocate = CLArray.allocate(cArr);
                i++;
                break;
            case 3:
                cLElementAllocate = CLString.allocate(cArr);
                break;
            case 4:
                cLElementAllocate = CLNumber.allocate(cArr);
                break;
            case 5:
                cLElementAllocate = CLKey.allocate(cArr);
                break;
            case 6:
                cLElementAllocate = CLToken.allocate(cArr);
                break;
            default:
                cLElementAllocate = null;
                break;
        }
        if (cLElementAllocate == null) {
            return null;
        }
        cLElementAllocate.setLine(this.lineNumber);
        if (z) {
            cLElementAllocate.setStart(i);
        }
        if (cLElement instanceof CLContainer) {
            cLElementAllocate.setContainer((CLContainer) cLElement);
        }
        return cLElementAllocate;
    }

    private CLElement getNextJsonElement(int i, char c, CLElement cLElement, char[] cArr) throws CLParsingException {
        if (c == '\t' || c == '\n' || c == '\r' || c == ' ') {
            return cLElement;
        }
        if (c == '\"' || c == '\'') {
            return cLElement instanceof CLObject ? createElement(cLElement, i, TYPE.KEY, true, cArr) : createElement(cLElement, i, TYPE.STRING, true, cArr);
        }
        if (c == '[') {
            return createElement(cLElement, i, TYPE.ARRAY, true, cArr);
        }
        if (c != ']') {
            if (c == '{') {
                return createElement(cLElement, i, TYPE.OBJECT, true, cArr);
            }
            if (c != '}') {
                switch (c) {
                    case '+':
                    case '-':
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        return createElement(cLElement, i, TYPE.NUMBER, true, cArr);
                    case ',':
                    case ':':
                        return cLElement;
                    case '/':
                        int i2 = i + 1;
                        if (i2 >= cArr.length || cArr[i2] != '/') {
                            return cLElement;
                        }
                        this.hasComment = true;
                        return cLElement;
                    default:
                        if (!(cLElement instanceof CLContainer) || (cLElement instanceof CLObject)) {
                            return createElement(cLElement, i, TYPE.KEY, true, cArr);
                        }
                        CLElement cLElementCreateElement = createElement(cLElement, i, TYPE.TOKEN, true, cArr);
                        CLToken cLToken = (CLToken) cLElementCreateElement;
                        if (cLToken.validate(c, i)) {
                            return cLElementCreateElement;
                        }
                        throw new CLParsingException("incorrect token <" + c + "> at line " + this.lineNumber, cLToken);
                }
            }
        }
        cLElement.setEnd(i - 1);
        CLElement container = cLElement.getContainer();
        container.setEnd(i);
        return container;
    }

    public static CLObject parse(String str) throws CLParsingException {
        return new CLParser(str).parse();
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0147 A[EDGE_INSN: B:103:0x0147->B:113:? BREAK  A[LOOP:1: B:14:0x0034->B:87:0x0141], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:104:0x00aa A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:107:0x0141 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:24:0x004b  */
    /* JADX WARN: Code duplicated, block: B:26:0x0051  */
    /* JADX WARN: Code duplicated, block: B:28:0x0058  */
    /* JADX WARN: Code duplicated, block: B:30:0x005e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x0060  */
    /* JADX WARN: Code duplicated, block: B:32:0x0068  */
    /* JADX WARN: Code duplicated, block: B:33:0x006d  */
    /* JADX WARN: Code duplicated, block: B:35:0x0073 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x0075  */
    /* JADX WARN: Code duplicated, block: B:37:0x007d  */
    /* JADX WARN: Code duplicated, block: B:38:0x0082  */
    /* JADX WARN: Code duplicated, block: B:40:0x0088  */
    /* JADX WARN: Code duplicated, block: B:42:0x008f  */
    /* JADX WARN: Code duplicated, block: B:43:0x009b  */
    /* JADX WARN: Code duplicated, block: B:45:0x009f  */
    /* JADX WARN: Code duplicated, block: B:53:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:55:0x00da  */
    /* JADX WARN: Code duplicated, block: B:57:0x00de A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:79:0x011f  */
    /* JADX WARN: Code duplicated, block: B:82:0x012e  */
    public CLObject parse() throws CLParsingException {
        boolean z;
        long j;
        char c;
        long j2;
        CLElement container;
        CLToken cLToken;
        long j3;
        char[] charArray = this.mContent.toCharArray();
        int length = charArray.length;
        int i = 1;
        this.lineNumber = 1;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            }
            char c2 = charArray[i2];
            if (c2 == '{') {
                break;
            }
            if (c2 == '\n') {
                this.lineNumber++;
            }
            i2++;
        }
        if (i2 == -1) {
            throw new CLParsingException("invalid json content", null);
        }
        CLObject cLObjectAllocate = CLObject.allocate(charArray);
        cLObjectAllocate.setLine(this.lineNumber);
        cLObjectAllocate.setStart(i2);
        int i3 = i2 + 1;
        CLElement container2 = cLObjectAllocate;
        while (i3 < length) {
            char c3 = charArray[i3];
            if (c3 == '\n') {
                this.lineNumber += i;
            }
            if (this.hasComment) {
                if (c3 == '\n') {
                    this.hasComment = z2;
                    if (container2 == null) {
                        break;
                        break;
                    }
                    if (container2.isDone()) {
                        container = getNextJsonElement(i3, c3, container2, charArray);
                    } else {
                        if (container2 instanceof CLObject) {
                            if (c3 == '}') {
                                container2.setEnd(i3 - 1);
                            } else {
                                container = getNextJsonElement(i3, c3, container2, charArray);
                            }
                        } else if (container2 instanceof CLArray) {
                            z = container2 instanceof CLString;
                            if (z) {
                                j3 = container2.start;
                                if (charArray[(int) j3] == c3) {
                                    container2.setStart(j3 + 1);
                                    container2.setEnd(i3 - 1);
                                }
                            } else {
                                if (container2 instanceof CLToken) {
                                    cLToken = (CLToken) container2;
                                    if (!cLToken.validate(c3, i3)) {
                                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("parsing incorrect token ");
                                        sbM.append(cLToken.content());
                                        sbM.append(" at line ");
                                        sbM.append(this.lineNumber);
                                        throw new CLParsingException(sbM.toString(), cLToken);
                                    }
                                }
                                if (container2 instanceof CLKey) {
                                    j = container2.start;
                                    c = charArray[(int) j];
                                    if (c != '\'') {
                                        container2.setStart(j + 1);
                                        container2.setEnd(i3 - 1);
                                    } else {
                                        container2.setStart(j + 1);
                                        container2.setEnd(i3 - 1);
                                    }
                                } else {
                                    j = container2.start;
                                    c = charArray[(int) j];
                                    if (c != '\'') {
                                        container2.setStart(j + 1);
                                        container2.setEnd(i3 - 1);
                                    } else {
                                        container2.setStart(j + 1);
                                        container2.setEnd(i3 - 1);
                                    }
                                }
                                if (!container2.isDone()) {
                                    j2 = i3 - 1;
                                    container2.setEnd(j2);
                                    if (c3 != '}') {
                                    }
                                    container = container2.getContainer();
                                    container.setEnd(j2);
                                    if (container instanceof CLKey) {
                                        container = container.getContainer();
                                        container.setEnd(j2);
                                    }
                                }
                            }
                        } else if (c3 == ']') {
                            container2.setEnd(i3 - 1);
                        } else {
                            container = getNextJsonElement(i3, c3, container2, charArray);
                        }
                        if (!container2.isDone()) {
                        }
                    }
                    container2 = container;
                    if (!container2.isDone()) {
                    }
                } else {
                    continue;
                }
            } else {
                if (container2 == null) {
                    break;
                }
                if (container2.isDone()) {
                    container = getNextJsonElement(i3, c3, container2, charArray);
                } else {
                    if (container2 instanceof CLObject) {
                        if (c3 == '}') {
                            container2.setEnd(i3 - 1);
                        } else {
                            container = getNextJsonElement(i3, c3, container2, charArray);
                        }
                    } else if (container2 instanceof CLArray) {
                        z = container2 instanceof CLString;
                        if (z) {
                            j3 = container2.start;
                            if (charArray[(int) j3] == c3) {
                                container2.setStart(j3 + 1);
                                container2.setEnd(i3 - 1);
                            }
                        } else {
                            if (container2 instanceof CLToken) {
                                cLToken = (CLToken) container2;
                                if (!cLToken.validate(c3, i3)) {
                                    StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("parsing incorrect token ");
                                    sbM2.append(cLToken.content());
                                    sbM2.append(" at line ");
                                    sbM2.append(this.lineNumber);
                                    throw new CLParsingException(sbM2.toString(), cLToken);
                                }
                            }
                            if ((container2 instanceof CLKey) || z) {
                                j = container2.start;
                                c = charArray[(int) j];
                                if ((c != '\'' || c == '\"') && c == c3) {
                                    container2.setStart(j + 1);
                                    container2.setEnd(i3 - 1);
                                }
                            }
                            if (!container2.isDone() && (c3 == '}' || c3 == ']' || c3 == ',' || c3 == ' ' || c3 == '\t' || c3 == '\r' || c3 == '\n' || c3 == ':')) {
                                j2 = i3 - 1;
                                container2.setEnd(j2);
                                if (c3 != '}' || c3 == ']') {
                                    container = container2.getContainer();
                                    container.setEnd(j2);
                                    if (container instanceof CLKey) {
                                        container = container.getContainer();
                                        container.setEnd(j2);
                                    }
                                }
                            }
                        }
                    } else if (c3 == ']') {
                        container2.setEnd(i3 - 1);
                    } else {
                        container = getNextJsonElement(i3, c3, container2, charArray);
                    }
                    if (!container2.isDone() && (!(container2 instanceof CLKey) || ((CLKey) container2).mElements.size() > 0)) {
                        container2 = container2.getContainer();
                    }
                }
                container2 = container;
                if (!container2.isDone()) {
                }
            }
            i3++;
            i = 1;
            z2 = false;
        }
        while (container2 != null && !container2.isDone()) {
            if (container2 instanceof CLString) {
                container2.setStart(((int) container2.start) + 1);
            }
            container2.setEnd(length - 1);
            container2 = container2.getContainer();
        }
        return cLObjectAllocate;
    }
}
