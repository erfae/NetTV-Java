package com.nettv.livestore.models;

import androidx.core.provider.FontsContractCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleModel implements Serializable {

    @SerializedName("data")
    public List<DataModel> dataModels;

    @SerializedName("page")
    public int page;

    @SerializedName("per_page")
    public int per_page;

    @SerializedName("total_count")
    public int total_count;

    @SerializedName("total_pages")
    public int total_pages;

    public static class AttributesModel implements Serializable {

        @SerializedName("ai_translated")
        public boolean ai_translated;

        @SerializedName("comments")
        public String comments;

        @SerializedName("download_count")
        public int download_count;

        @SerializedName("feature_details")
        public FeatureDetailModel featureDetailModel;

        @SerializedName("files")
        public List<FileModel> fileModels;

        @SerializedName("foreign_parts_only")
        public boolean foreign_parts_only;

        @SerializedName("fps")
        public float fps;

        @SerializedName("from_trusted")
        public boolean from_trusted;

        @SerializedName("hd")
        public boolean hd;

        @SerializedName("hearing_impaired")
        public boolean hearing_impaired;

        @SerializedName("language")
        public String language;

        @SerializedName("legacy_subtitle_id")
        public String legacy_subtitle_id;

        @SerializedName("machine_translated")
        public boolean machine_translated;

        @SerializedName("new_download_count")
        public int new_download_count;

        @SerializedName("ratings")
        public float ratings;

        @SerializedName("related_links")
        public List<RelatedLinkModel> relatedLinkModels;

        @SerializedName("release")
        public String release;

        @SerializedName("subtitle_id")
        public String subtitle_id;

        @SerializedName("upload_date")
        public String upload_date;

        @SerializedName("uploader")
        public UploaderModel uploaderModel;

        @SerializedName(ImagesContract.URL)
        public String url;

        public List<FileModel> getFileModels() {
            List<FileModel> list = this.fileModels;
            return list == null ? new ArrayList() : list;
        }

        public String getLanguage() {
            return this.language;
        }

        public String getSubtitle_id() {
            return this.subtitle_id;
        }
    }

    public static class DataModel implements Serializable {

        @SerializedName("attributes")
        public AttributesModel attributesModel;

        @SerializedName(TtmlNode.ATTR_ID)
        public String id;

        @SerializedName("type")
        public String type;

        public AttributesModel getAttributesModel() {
            return this.attributesModel;
        }

        public String getId() {
            return this.id;
        }

        public String getType() {
            return this.type;
        }
    }

    public static class FeatureDetailModel implements Serializable {

        @SerializedName("feature_id")
        public String feature_id;

        @SerializedName("feature_type")
        public String feature_type;

        @SerializedName("imdb_id")
        public String imdb_id;

        @SerializedName("movie_name")
        public String movie_name;

        @SerializedName("title")
        public String title;

        @SerializedName("tmdb_id")
        public String tmdb_id;

        @SerializedName("year")
        public String year;

        public String getFeature_id() {
            return this.feature_id;
        }

        public String getFeature_type() {
            return this.feature_type;
        }

        public String getImdb_id() {
            return this.imdb_id;
        }

        public String getMovie_name() {
            return this.movie_name;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTmdb_id() {
            return this.tmdb_id;
        }

        public String getYear() {
            return this.year;
        }
    }

    public static class FileModel implements Serializable {

        @SerializedName("cd_number")
        public int cd_number;

        @SerializedName(FontsContractCompat.Columns.FILE_ID)
        public int file_id;

        @SerializedName("file_name")
        public String file_name;

        public int getCd_number() {
            return this.cd_number;
        }

        public int getFile_id() {
            return this.file_id;
        }

        public String getFile_name() {
            return this.file_name;
        }
    }

    public static class RelatedLinkModel implements Serializable {

        @SerializedName("img_url")
        public String img_url;

        @SerializedName("label")
        public String label;

        @SerializedName(ImagesContract.URL)
        public String url;

        public String getImg_url() {
            return this.img_url;
        }

        public String getLabel() {
            return this.label;
        }

        public String getUrl() {
            return this.url;
        }
    }

    public static class UploaderModel implements Serializable {

        @SerializedName("name")
        public String name;

        @SerializedName("rank")
        public String rank;

        @SerializedName("uploader_id")
        public String uploader_id;

        public String getName() {
            return this.name;
        }

        public String getRank() {
            return this.rank;
        }

        public String getUploader_id() {
            return this.uploader_id;
        }
    }

    public List<DataModel> getDataModels() {
        List<DataModel> list = this.dataModels;
        return list == null ? new ArrayList() : list;
    }
}
