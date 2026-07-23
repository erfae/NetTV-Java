package com.nettv.livestore.apps;

/* JADX INFO: loaded from: classes2.dex */
public class SideMenu {
    public int image_url;
    public String menu;
    public int position;

    public SideMenu(String str, int i, int i2) {
        this.menu = str;
        this.position = i;
        this.image_url = i2;
    }

    public int getImage_url() {
        return this.image_url;
    }

    public String getName() {
        return this.menu;
    }

    public int getPosition() {
        return this.position;
    }
}
