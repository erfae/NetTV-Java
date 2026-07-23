package org.bson.json;

import java.io.Reader;
import kotlin.text.Typography;
import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes2.dex */
class JsonScanner {
    private final JsonBuffer buffer;

    /* JADX INFO: renamed from: org.bson.json.JsonScanner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonScanner$NumberState;
        public static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState;

        static {
            int[] iArr = new int[NumberState.values().length];
            $SwitchMap$org$bson$json$JsonScanner$NumberState = iArr;
            try {
                iArr[NumberState.SAW_LEADING_MINUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_LEADING_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_INTEGER_DIGITS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_DECIMAL_POINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_FRACTION_DIGITS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_LETTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_SIGN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_DIGITS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_MINUS_I.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.INVALID.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.DONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr2 = new int[RegularExpressionState.values().length];
            $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState = iArr2;
            try {
                iArr2[RegularExpressionState.IN_PATTERN.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.IN_ESCAPE_SEQUENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.IN_OPTIONS.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.DONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.INVALID.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    public enum NumberState {
        SAW_LEADING_MINUS,
        SAW_LEADING_ZERO,
        SAW_INTEGER_DIGITS,
        SAW_DECIMAL_POINT,
        SAW_FRACTION_DIGITS,
        SAW_EXPONENT_LETTER,
        SAW_EXPONENT_SIGN,
        SAW_EXPONENT_DIGITS,
        SAW_MINUS_I,
        DONE,
        INVALID
    }

    public enum RegularExpressionState {
        IN_PATTERN,
        IN_ESCAPE_SEQUENCE,
        IN_OPTIONS,
        DONE,
        INVALID
    }

    public JsonScanner(String str) {
        this.buffer = new JsonStringBuffer(str);
    }

    /* JADX WARN: Code duplicated, block: B:110:0x0160  */
    /* JADX WARN: Code duplicated, block: B:112:0x0166  */
    /* JADX WARN: Code duplicated, block: B:70:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:71:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:92:0x0132  */
    /* JADX WARN: Code duplicated, block: B:94:0x0138  */
    private JsonToken scanNumber(char c) {
        JsonTokenType jsonTokenType;
        NumberState numberState;
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        NumberState numberState2 = c != '-' ? c != '0' ? NumberState.SAW_INTEGER_DIGITS : NumberState.SAW_LEADING_ZERO : NumberState.SAW_LEADING_MINUS;
        JsonTokenType jsonTokenType2 = JsonTokenType.INT64;
        while (true) {
            int i = this.buffer.read();
            switch (AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$NumberState[numberState2.ordinal()]) {
                case 1:
                    if (i == 48) {
                        numberState2 = NumberState.SAW_LEADING_ZERO;
                    } else if (i == 73) {
                        numberState2 = NumberState.SAW_MINUS_I;
                    } else if (!Character.isDigit(i)) {
                        numberState2 = NumberState.INVALID;
                    } else {
                        numberState2 = NumberState.SAW_INTEGER_DIGITS;
                    }
                    break;
                case 2:
                    if (i == -1 || i == 41 || i == 44) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 46) {
                        numberState2 = NumberState.SAW_DECIMAL_POINT;
                    } else if (i == 69) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 93) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 101) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 125) {
                        numberState2 = NumberState.DONE;
                    } else if (!Character.isDigit(i)) {
                        numberState2 = !Character.isWhitespace(i) ? NumberState.INVALID : NumberState.DONE;
                    } else {
                        numberState2 = NumberState.SAW_INTEGER_DIGITS;
                    }
                    break;
                case 3:
                    if (i == -1 || i == 41 || i == 44) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 46) {
                        numberState2 = NumberState.SAW_DECIMAL_POINT;
                    } else if (i == 69) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 93) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 101) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 125) {
                        numberState2 = NumberState.DONE;
                    } else if (!Character.isDigit(i)) {
                        numberState2 = !Character.isWhitespace(i) ? NumberState.INVALID : NumberState.DONE;
                    } else {
                        numberState2 = NumberState.SAW_INTEGER_DIGITS;
                    }
                    break;
                case 4:
                    jsonTokenType = JsonTokenType.DOUBLE;
                    numberState = Character.isDigit(i) ? NumberState.SAW_FRACTION_DIGITS : NumberState.INVALID;
                    NumberState numberState3 = numberState;
                    jsonTokenType2 = jsonTokenType;
                    numberState2 = numberState3;
                    break;
                case 5:
                    if (i == -1 || i == 41 || i == 44) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 69) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 93) {
                        numberState2 = NumberState.DONE;
                    } else if (i == 101) {
                        numberState2 = NumberState.SAW_EXPONENT_LETTER;
                    } else if (i == 125) {
                        numberState2 = NumberState.DONE;
                    } else if (!Character.isDigit(i)) {
                        numberState2 = !Character.isWhitespace(i) ? NumberState.INVALID : NumberState.DONE;
                    } else {
                        numberState2 = NumberState.SAW_FRACTION_DIGITS;
                    }
                    break;
                case 6:
                    jsonTokenType = JsonTokenType.DOUBLE;
                    numberState = (i == 43 || i == 45) ? NumberState.SAW_EXPONENT_SIGN : Character.isDigit(i) ? NumberState.SAW_EXPONENT_DIGITS : NumberState.INVALID;
                    NumberState numberState4 = numberState;
                    jsonTokenType2 = jsonTokenType;
                    numberState2 = numberState4;
                    break;
                case 7:
                    numberState2 = !Character.isDigit(i) ? NumberState.INVALID : NumberState.SAW_EXPONENT_DIGITS;
                    break;
                case 8:
                    if (i == 41 || i == 44 || i == 93 || i == 125) {
                        numberState2 = NumberState.DONE;
                    } else if (!Character.isDigit(i)) {
                        numberState2 = !Character.isWhitespace(i) ? NumberState.INVALID : NumberState.DONE;
                    } else {
                        numberState2 = NumberState.SAW_EXPONENT_DIGITS;
                    }
                    break;
                case 9:
                    char[] cArr = {'n', 'f', 'i', 'n', 'i', 't', 'y'};
                    boolean z = false;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= 7) {
                            z = true;
                        } else if (i == cArr[i2]) {
                            sb.append((char) i);
                            i = this.buffer.read();
                            i2++;
                        }
                    }
                    if (!z) {
                        numberState2 = NumberState.INVALID;
                    } else {
                        jsonTokenType = JsonTokenType.DOUBLE;
                        numberState = (i == -1 || i == 41 || i == 44 || i == 93 || i == 125 || Character.isWhitespace(i)) ? NumberState.DONE : NumberState.INVALID;
                        NumberState numberState5 = numberState;
                        jsonTokenType2 = jsonTokenType;
                        numberState2 = numberState5;
                    }
                    break;
            }
            int i3 = AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$NumberState[numberState2.ordinal()];
            if (i3 == 10) {
                throw new JsonParseException("Invalid JSON number");
            }
            if (i3 == 11) {
                this.buffer.unread(i);
                String string = sb.toString();
                JsonTokenType jsonTokenType3 = JsonTokenType.DOUBLE;
                if (jsonTokenType2 == jsonTokenType3) {
                    return new JsonToken(jsonTokenType3, Double.valueOf(Double.parseDouble(string)));
                }
                long j = Long.parseLong(string);
                return (j < -2147483648L || j > 2147483647L) ? new JsonToken(JsonTokenType.INT64, Long.valueOf(j)) : new JsonToken(JsonTokenType.INT32, Integer.valueOf((int) j));
            }
            sb.append((char) i);
        }
    }

    private JsonToken scanRegularExpression() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        RegularExpressionState regularExpressionState = RegularExpressionState.IN_PATTERN;
        while (true) {
            int i = this.buffer.read();
            int[] iArr = AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$RegularExpressionState;
            int i2 = iArr[regularExpressionState.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    regularExpressionState = RegularExpressionState.IN_PATTERN;
                } else if (i2 == 3) {
                    if (i == -1 || i == 41 || i == 44 || i == 93) {
                        regularExpressionState = RegularExpressionState.DONE;
                    } else if (i == 105 || i == 109 || i == 115 || i == 120) {
                        regularExpressionState = RegularExpressionState.IN_OPTIONS;
                    } else {
                        regularExpressionState = (i == 125 || Character.isWhitespace(i)) ? RegularExpressionState.DONE : RegularExpressionState.INVALID;
                    }
                }
            } else if (i == -1) {
                regularExpressionState = RegularExpressionState.INVALID;
            } else if (i != 47) {
                regularExpressionState = i != 92 ? RegularExpressionState.IN_PATTERN : RegularExpressionState.IN_ESCAPE_SEQUENCE;
            } else {
                regularExpressionState = RegularExpressionState.IN_OPTIONS;
            }
            int i3 = iArr[regularExpressionState.ordinal()];
            if (i3 == 4) {
                this.buffer.unread(i);
                return new JsonToken(JsonTokenType.REGULAR_EXPRESSION, new BsonRegularExpression(sb.toString(), sb2.toString()));
            }
            if (i3 == 5) {
                throw new JsonParseException("Invalid JSON regular expression. Position: %d.", Integer.valueOf(this.buffer.getPosition()));
            }
            if (iArr[regularExpressionState.ordinal()] != 3) {
                sb.append((char) i);
            } else if (i != 47) {
                sb2.append((char) i);
            }
        }
    }

    private JsonToken scanString(char c) {
        int i;
        StringBuilder sb = new StringBuilder();
        do {
            i = this.buffer.read();
            if (i == 92) {
                i = this.buffer.read();
                if (i == 34) {
                    sb.append(Typography.quote);
                } else if (i == 39) {
                    sb.append('\'');
                } else if (i == 47) {
                    sb.append('/');
                } else if (i == 92) {
                    sb.append('\\');
                } else if (i == 98) {
                    sb.append('\b');
                } else if (i == 102) {
                    sb.append('\f');
                } else if (i == 110) {
                    sb.append('\n');
                } else if (i == 114) {
                    sb.append('\r');
                } else if (i == 116) {
                    sb.append('\t');
                } else {
                    if (i != 117) {
                        throw new JsonParseException("Invalid escape sequence in JSON string '\\%c'.", Integer.valueOf(i));
                    }
                    int i2 = this.buffer.read();
                    int i3 = this.buffer.read();
                    int i4 = this.buffer.read();
                    int i5 = this.buffer.read();
                    if (i5 != -1) {
                        sb.append((char) Integer.parseInt(new String(new char[]{(char) i2, (char) i3, (char) i4, (char) i5}), 16));
                    }
                }
            } else {
                if (i == c) {
                    return new JsonToken(JsonTokenType.STRING, sb.toString());
                }
                if (i != -1) {
                    sb.append((char) i);
                }
            }
        } while (i != -1);
        throw new JsonParseException("End of file in JSON string.");
    }

    private JsonToken scanUnquotedString(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        int i = this.buffer.read();
        while (true) {
            if (i != 36 && i != 95 && !Character.isLetterOrDigit(i)) {
                this.buffer.unread(i);
                return new JsonToken(JsonTokenType.UNQUOTED_STRING, sb.toString());
            }
            sb.append((char) i);
            i = this.buffer.read();
        }
    }

    public void discard(int i) {
        this.buffer.discard(i);
    }

    public int mark() {
        return this.buffer.mark();
    }

    public JsonToken nextToken() {
        int i = this.buffer.read();
        while (i != -1 && Character.isWhitespace(i)) {
            i = this.buffer.read();
        }
        if (i == -1) {
            return new JsonToken(JsonTokenType.END_OF_FILE, "<eof>");
        }
        if (i != 34) {
            if (i == 44) {
                return new JsonToken(JsonTokenType.COMMA, ",");
            }
            if (i == 47) {
                return scanRegularExpression();
            }
            if (i == 58) {
                return new JsonToken(JsonTokenType.COLON, ":");
            }
            if (i == 91) {
                return new JsonToken(JsonTokenType.BEGIN_ARRAY, "[");
            }
            if (i == 93) {
                return new JsonToken(JsonTokenType.END_ARRAY, "]");
            }
            if (i == 123) {
                return new JsonToken(JsonTokenType.BEGIN_OBJECT, "{");
            }
            if (i == 125) {
                return new JsonToken(JsonTokenType.END_OBJECT, "}");
            }
            switch (i) {
                case 39:
                    break;
                case 40:
                    return new JsonToken(JsonTokenType.LEFT_PAREN, "(");
                case 41:
                    return new JsonToken(JsonTokenType.RIGHT_PAREN, ")");
                default:
                    if (i == 45 || Character.isDigit(i)) {
                        return scanNumber((char) i);
                    }
                    if (i == 36 || i == 95 || Character.isLetter(i)) {
                        return scanUnquotedString((char) i);
                    }
                    int position = this.buffer.getPosition();
                    this.buffer.unread(i);
                    throw new JsonParseException("Invalid JSON input. Position: %d. Character: '%c'.", Integer.valueOf(position), Integer.valueOf(i));
            }
        }
        return scanString((char) i);
    }

    public void reset(int i) {
        this.buffer.reset(i);
    }

    public JsonScanner(Reader reader) {
        this.buffer = new JsonStreamBuffer(reader);
    }
}
