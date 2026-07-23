package org.androidannotations.api.sharedpreferences;

import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public final class SetXmlSerializer {
    private static final String NAMESPACE = "";
    private static final String SET_TAG = "AA_set";
    private static final String STRING_TAG = "AA_string";

    private SetXmlSerializer() {
    }

    public static Set<String> deserialize(String str) {
        TreeSet treeSet = new TreeSet();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        try {
            xmlPullParserNewPullParser.setInput(new StringReader(str));
            xmlPullParserNewPullParser.next();
            xmlPullParserNewPullParser.require(2, "", SET_TAG);
            while (xmlPullParserNewPullParser.next() != 3) {
                xmlPullParserNewPullParser.require(2, "", STRING_TAG);
                xmlPullParserNewPullParser.next();
                xmlPullParserNewPullParser.require(4, null, null);
                treeSet.add(xmlPullParserNewPullParser.getText());
                xmlPullParserNewPullParser.next();
                xmlPullParserNewPullParser.require(3, null, STRING_TAG);
            }
            return treeSet;
        } catch (IOException e) {
            Log.w("getStringSet", e);
            return null;
        } catch (XmlPullParserException e2) {
            Log.w("getStringSet", e2);
            return null;
        }
    }

    public static String serialize(Set<String> set) {
        if (set == null) {
            set = Collections.emptySet();
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        try {
            xmlSerializerNewSerializer.setOutput(stringWriter);
            xmlSerializerNewSerializer.startTag("", SET_TAG);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                xmlSerializerNewSerializer.startTag("", STRING_TAG).text(it.next()).endTag("", STRING_TAG);
            }
            xmlSerializerNewSerializer.endTag("", SET_TAG).endDocument();
        } catch (IOException | IllegalArgumentException | IllegalStateException unused) {
        }
        return stringWriter.toString();
    }
}
