package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class CastModel implements Serializable {
    public boolean adult;
    public int cast_id;
    public String character;
    public String credit_id;
    public int gender;
    public int id;
    public String known_for_department;
    public String name;
    public int order;
    public String original_name;
    public float popularity;
    public String profile_path;

    public int getCast_id() {
        return this.cast_id;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getCredit_id() {
        return this.credit_id;
    }

    public int getGender() {
        return this.gender;
    }

    public int getId() {
        return this.id;
    }

    public String getKnown_for_department() {
        return this.known_for_department;
    }

    public String getName() {
        return this.name;
    }

    public int getOrder() {
        return this.order;
    }

    public String getOriginal_name() {
        return this.original_name;
    }

    public float getPopularity() {
        return this.popularity;
    }

    public String getProfile_path() {
        return this.profile_path;
    }

    public boolean isAdult() {
        return this.adult;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CastModel{adult='");
        sbM.append(this.adult);
        sbM.append('\'');
        sbM.append(", gender=");
        sbM.append(this.gender);
        sbM.append(", id=");
        sbM.append(this.id);
        sbM.append(", known_for_department='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.known_for_department, '\'', ", name='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.name, '\'', ", original_name='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.original_name, '\'', ", popularity=");
        sbM.append(this.popularity);
        sbM.append(", profile_path='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.profile_path, '\'', ", cast_id=");
        sbM.append(this.cast_id);
        sbM.append(", character='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.character, '\'', ", credit_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.credit_id, '\'', ", order=");
        return Insets$$ExternalSyntheticOutline0.m(sbM, this.order, '}');
    }
}
