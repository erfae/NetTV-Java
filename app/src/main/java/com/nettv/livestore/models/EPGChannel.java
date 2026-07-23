package com.nettv.livestore.models;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import iptv.m3u.parser.M3UItem;
import java.io.Serializable;
import java.util.Random;

/* JADX INFO: loaded from: classes2.dex */
public class EPGChannel extends RealmObject implements Serializable, com_nettv_livestore_models_EPGChannelRealmProxyInterface {

    @SerializedName("epg_channel_id")
    private String Id;

    @SerializedName("added")
    private String added;

    @SerializedName("category_id")
    private String category_id;
    private String category_name;
    private int cell;
    private int channelID;

    @SerializedName("custom_sid")
    private String custom_sid;

    @SerializedName("direct_source")
    private String direct_source;
    private boolean is_favorite;
    private boolean is_locked;
    private boolean is_recent;

    @SerializedName("name")
    private String name;

    @SerializedName("num")
    private String num;
    private long recent_pos;
    public boolean selected;

    @SerializedName("stream_icon")
    private String stream_icon;

    @SerializedName("stream_id")
    private String stream_id;

    @SerializedName("stream_type")
    private String stream_type;

    @SerializedName("tv_archive")
    private String tv_archive;

    @SerializedName("tv_archive_duration")
    private String tv_archive_duration;
    private String url;

    /* JADX WARN: Multi-variable type inference failed */
    public EPGChannel() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$num("");
        realmSet$name("");
        realmSet$stream_type("");
        realmSet$stream_id("-1");
        realmSet$stream_icon("");
        realmSet$Id("");
        realmSet$added("");
        realmSet$category_id("-1");
        realmSet$custom_sid("");
        realmSet$tv_archive("0");
        realmSet$direct_source("");
        realmSet$tv_archive_duration("");
        realmSet$is_locked(false);
        realmSet$is_favorite(false);
        realmSet$is_recent(false);
        realmSet$recent_pos(0L);
        realmSet$cell(-1);
        realmSet$channelID(0);
    }

    @Nullable
    public static EPGChannel fromM3UItem(M3UItem m3UItem) {
        try {
            EPGChannel ePGChannel = new EPGChannel();
            ePGChannel.setCategory_name(TextUtils.isEmpty(m3UItem.getGroupTitle()) ? "All" : m3UItem.getGroupTitle());
            if (!TextUtils.isEmpty(m3UItem.getChannelId())) {
                ePGChannel.setId(m3UItem.getChannelId());
            }
            if (!TextUtils.isEmpty(m3UItem.getChannelName())) {
                ePGChannel.setName(m3UItem.getChannelName());
            }
            if (!TextUtils.isEmpty(m3UItem.getStreamURL())) {
                ePGChannel.setUrl(m3UItem.getStreamURL());
                if (m3UItem.getStreamURL().contains("live")) {
                    try {
                        String str = ePGChannel.getUrl().split("/")[6];
                        if (str.contains(".")) {
                            ePGChannel.setStream_id(str.split(".")[0]);
                        } else {
                            ePGChannel.setStream_id(str);
                        }
                    } catch (Exception unused) {
                        ePGChannel.setStream_id("" + new Random().nextInt());
                    }
                } else {
                    try {
                        ePGChannel.setStream_id(ePGChannel.getUrl().split("/")[5]);
                    } catch (Exception unused2) {
                        ePGChannel.setStream_id("" + new Random().nextInt());
                    }
                }
            }
            if (!TextUtils.isEmpty(m3UItem.getLogoURL())) {
                ePGChannel.setStream_icon(m3UItem.getLogoURL());
            }
            return ePGChannel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAdded() {
        return realmGet$added();
    }

    public String getCategory_id() {
        return realmGet$category_id() == null ? "" : realmGet$category_id();
    }

    public String getCategory_name() {
        return realmGet$category_name() == null ? "" : realmGet$category_name();
    }

    public int getCell() {
        return realmGet$cell();
    }

    public int getChannelID() {
        return realmGet$channelID();
    }

    public String getCustom_sid() {
        return realmGet$custom_sid();
    }

    public String getDirect_source() {
        return realmGet$direct_source();
    }

    public String getId() {
        return realmGet$Id() != null ? realmGet$Id() : "";
    }

    public String getName() {
        return realmGet$name() == null ? "unknown channel" : realmGet$name();
    }

    public String getNum() {
        return realmGet$num() == null ? "0" : realmGet$num();
    }

    public long getRecent_pos() {
        return realmGet$recent_pos();
    }

    public String getStream_icon() {
        return realmGet$stream_icon() == null ? "" : realmGet$stream_icon();
    }

    public String getStream_id() {
        return realmGet$stream_id() == null ? "" : realmGet$stream_id();
    }

    public String getStream_type() {
        return realmGet$stream_type();
    }

    public String getTv_archive() {
        return realmGet$tv_archive();
    }

    public String getTv_archive_duration() {
        return realmGet$tv_archive_duration();
    }

    public String getUrl() {
        return realmGet$url() == null ? "" : realmGet$url();
    }

    public boolean isIs_recent() {
        return realmGet$is_recent();
    }

    public boolean isSelected() {
        return realmGet$selected();
    }

    public boolean is_favorite() {
        return realmGet$is_favorite();
    }

    public boolean is_locked() {
        return realmGet$is_locked();
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$Id() {
        return this.Id;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$added() {
        return this.added;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$category_id() {
        return this.category_id;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$category_name() {
        return this.category_name;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public int realmGet$cell() {
        return this.cell;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public int realmGet$channelID() {
        return this.channelID;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$custom_sid() {
        return this.custom_sid;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$direct_source() {
        return this.direct_source;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_favorite() {
        return this.is_favorite;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_locked() {
        return this.is_locked;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$is_recent() {
        return this.is_recent;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$name() {
        return this.name;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$num() {
        return this.num;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public long realmGet$recent_pos() {
        return this.recent_pos;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public boolean realmGet$selected() {
        return this.selected;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_icon() {
        return this.stream_icon;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_id() {
        return this.stream_id;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$stream_type() {
        return this.stream_type;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$tv_archive() {
        return this.tv_archive;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$tv_archive_duration() {
        return this.tv_archive_duration;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public String realmGet$url() {
        return this.url;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$Id(String str) {
        this.Id = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$added(String str) {
        this.added = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$category_id(String str) {
        this.category_id = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$category_name(String str) {
        this.category_name = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$cell(int i) {
        this.cell = i;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$channelID(int i) {
        this.channelID = i;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$custom_sid(String str) {
        this.custom_sid = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$direct_source(String str) {
        this.direct_source = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_favorite(boolean z) {
        this.is_favorite = z;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_locked(boolean z) {
        this.is_locked = z;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$is_recent(boolean z) {
        this.is_recent = z;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$name(String str) {
        this.name = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$num(String str) {
        this.num = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$recent_pos(long j) {
        this.recent_pos = j;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$selected(boolean z) {
        this.selected = z;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$stream_icon(String str) {
        this.stream_icon = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$stream_id(String str) {
        this.stream_id = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$stream_type(String str) {
        this.stream_type = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$tv_archive(String str) {
        this.tv_archive = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$tv_archive_duration(String str) {
        this.tv_archive_duration = str;
    }

    @Override // io.realm.com_nettv_livestore_models_EPGChannelRealmProxyInterface
    public void realmSet$url(String str) {
        this.url = str;
    }

    public void setAdded(String str) {
        realmSet$added(str);
    }

    public void setCategory_id(String str) {
        realmSet$category_id(str);
    }

    public void setCategory_name(String str) {
        realmSet$category_name(str);
    }

    public void setCell(int i) {
        realmSet$cell(i);
    }

    public void setChannelID(int i) {
        realmSet$channelID(i);
    }

    public void setCustom_sid(String str) {
        realmSet$custom_sid(str);
    }

    public void setDirect_source(String str) {
        realmSet$direct_source(str);
    }

    public void setId(String str) {
        realmSet$Id(str);
    }

    public void setIs_favorite(boolean z) {
        realmSet$is_favorite(z);
    }

    public void setIs_locked(boolean z) {
        realmSet$is_locked(z);
    }

    public void setIs_recent(boolean z) {
        realmSet$is_recent(z);
    }

    public void setName(String str) {
        realmSet$name(str);
    }

    public void setNum(String str) {
        realmSet$num(str);
    }

    public void setRecent_pos(long j) {
        realmSet$recent_pos(j);
    }

    public void setSelected(boolean z) {
        realmSet$selected(z);
    }

    public void setStream_icon(String str) {
        realmSet$stream_icon(str);
    }

    public void setStream_id(String str) {
        realmSet$stream_id(str);
    }

    public void setStream_type(String str) {
        realmSet$stream_type(str);
    }

    public void setTv_archive(String str) {
        realmSet$tv_archive(str);
    }

    public void setTv_archive_duration(String str) {
        realmSet$tv_archive_duration(str);
    }

    public void setUrl(String str) {
        realmSet$url(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EPGChannel(String str, String str2, int i, String str3, String str4, String str5) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$num("");
        realmSet$name("");
        realmSet$stream_type("");
        realmSet$stream_id("-1");
        realmSet$stream_icon("");
        realmSet$Id("");
        realmSet$added("");
        realmSet$category_id("-1");
        realmSet$custom_sid("");
        realmSet$tv_archive("0");
        realmSet$direct_source("");
        realmSet$tv_archive_duration("");
        realmSet$is_locked(false);
        realmSet$is_favorite(false);
        realmSet$is_recent(false);
        realmSet$recent_pos(0L);
        realmSet$cell(-1);
        realmSet$channelID(0);
        realmSet$stream_icon(str);
        realmSet$name(str2);
        realmSet$channelID(i);
        realmSet$Id(str3);
        realmSet$num(str4);
        realmSet$stream_id(str5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EPGChannel(String str, String str2, String str3) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$num("");
        realmSet$name("");
        realmSet$stream_type("");
        realmSet$stream_id("-1");
        realmSet$stream_icon("");
        realmSet$Id("");
        realmSet$added("");
        realmSet$category_id("-1");
        realmSet$custom_sid("");
        realmSet$tv_archive("0");
        realmSet$direct_source("");
        realmSet$tv_archive_duration("");
        realmSet$is_locked(false);
        realmSet$is_favorite(false);
        realmSet$is_recent(false);
        realmSet$recent_pos(0L);
        realmSet$cell(-1);
        realmSet$channelID(0);
        realmSet$stream_icon(str);
        realmSet$name(str2);
        realmSet$Id(str3);
    }
}
