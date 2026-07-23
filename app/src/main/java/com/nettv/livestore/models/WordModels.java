package com.nettv.livestore.models;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.common.net.HttpHeaders;
import com.google.gson.annotations.SerializedName;
import com.nettv.livestore.apps.Constants;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class WordModels implements Serializable {

    @SerializedName("_12_hour_format")
    private String _12_hour_format;

    @SerializedName("_24_hour_format")
    private String _24_hour_format;

    @SerializedName("account")
    private String account;

    @SerializedName("account_expired")
    private String account_expired;

    @SerializedName("activate_success")
    private String activate_success;

    @SerializedName("add_correct_alert")
    private String add_correct_alert;

    @SerializedName(Constants.add_group)
    private String add_group;

    @SerializedName("add_m3u")
    private String add_m3u;

    @SerializedName("add_playlist")
    private String add_playlist;

    @SerializedName("add_to_favorite")
    private String add_to_favorite;

    @SerializedName("age")
    private String age;

    @SerializedName(TtmlNode.COMBINE_ALL)
    private String all;

    @SerializedName("app_is_up_to_date")
    private String app_is_up_to_date;

    @SerializedName("app_status")
    private String app_status;

    @SerializedName("audio_track")
    private String audio_track;

    @SerializedName("auto_update_2day")
    private String auto_update_2day;

    @SerializedName("auto_update_everyday")
    private String auto_update_everyday;

    @SerializedName("auto_update_everytime")
    private String auto_update_everytime;

    @SerializedName("automatic")
    private String automatic;

    @SerializedName("back")
    private String back;

    @SerializedName("cancel")
    private String cancel;

    @SerializedName("cant_add_this_movie")
    private String cant_add_this_movie;

    @SerializedName("cast")
    private String cast;

    @SerializedName("catch_up")
    private String catch_up;

    @SerializedName("change_language")
    private String change_language;

    @SerializedName("change_layout")
    private String change_layout;

    @SerializedName("change_my_group")
    private String change_my_group;

    @SerializedName("change_playlist")
    private String change_playlist;

    @SerializedName("change_theme")
    private String change_theme;

    @SerializedName("channel_added_to_fav")
    private String channel_added_to_fav;

    @SerializedName("channel_locked")
    private String channel_locked;

    @SerializedName("channel_locked_default")
    private String channel_locked_default;

    @SerializedName("channel_removed_from_fav")
    private String channel_removed_from_fav;

    @SerializedName("channel_unlocked")
    private String channel_unlocked;

    @SerializedName("check_internet")
    private String check_internet;

    @SerializedName("clear_all")
    private String clear_all;

    @SerializedName("clear_channel_header")
    private String clear_channel_header;

    @SerializedName("clear_history_channels")
    private String clear_history_channels;

    @SerializedName("clear_history_movies")
    private String clear_history_movies;

    @SerializedName("clear_history_series")
    private String clear_history_series;

    @SerializedName("clear_movie_header")
    private String clear_movie_header;

    @SerializedName("clear_series_header")
    private String clear_series_header;

    @SerializedName("confirm_pass_cant_empty")
    private String confirm_pass_cant_empty;

    @SerializedName("confirm_pass_not_matched")
    private String confirm_pass_not_matched;

    @SerializedName("confirm_password")
    private String confirm_password;

    @SerializedName("connect")
    private String connect;

    @SerializedName("contact")
    private String contact;

    @SerializedName("current_expired")
    private String current_expired;

    @SerializedName("date_added")
    private String date_added;

    @SerializedName("days")
    private String days;

    @SerializedName("default")
    private String default_resolution;

    @SerializedName("delete")
    private String delete;

    @SerializedName("delete_cache")
    private String delete_cache;

    @SerializedName("delete_playlist")
    private String delete_playlist;

    @SerializedName("device_key")
    private String device_key;

    @SerializedName("director")
    private String director;

    @SerializedName("edit")
    private String edit;

    @SerializedName("edit_group_name")
    private String edit_group_name;

    @SerializedName("enable_subtitles")
    private String enable_subtitles;

    @SerializedName("enjoy_tv")
    private String enjoy_tv;

    @SerializedName("enter_m3u")
    private String enter_m3u;

    @SerializedName("epg")
    private String epg;

    @SerializedName("exit")
    private String exit;

    @SerializedName("exit_description")
    private String exit_description;

    @SerializedName("expire_date")
    private String expire_date;

    @SerializedName("external_player")
    private String external_player;

    @SerializedName("favorite")
    private String favorite;

    @SerializedName("fill_screen")
    private String fill_screen;

    @SerializedName("find_movie")
    private String find_movie;

    @SerializedName("fit_screen")
    private String fit_screen;

    @SerializedName("grid_layout")
    private String grid_layout;

    @SerializedName("hide_live_category")
    private String hide_live_category;

    @SerializedName("hide_series_category")
    private String hide_series_category;

    @SerializedName("hide_vod_category")
    private String hide_vod_category;

    @SerializedName("home")
    private String home;

    @SerializedName("ibo_pro_description")
    private String ibo_pro_description;

    @SerializedName("ibo_pro_general_player")
    private String ibo_pro_general_player;

    @SerializedName("install_external_player")
    private String install_external_player;

    @SerializedName("length")
    private String length;

    @SerializedName("list_layout")
    private String list_layout;

    @SerializedName("live")
    private String live;

    @SerializedName("live_sort")
    private String live_sort;

    @SerializedName("live_stream_format")
    private String live_stream_format;

    @SerializedName("live_tv")
    private String live_tv;

    @SerializedName("lock")
    private String lock;

    @SerializedName("mac_activated")
    private String mac_activated;

    @SerializedName("mac_address")
    private String mac_address;

    @SerializedName("movie_is_added_to_fav")
    private String movie_is_added_to_fav;

    @SerializedName("movie_is_removed_from_fav")
    private String movie_is_removed_from_fav;

    @SerializedName("movies")
    private String movies;

    @SerializedName("mx_player")
    private String mx_player;

    @SerializedName("net_pass")
    private String net_pass;

    @SerializedName("new_pass_cant_empty")
    private String new_pass_cant_empty;

    @SerializedName("new_pass_length")
    private String new_pass_length;

    @SerializedName("new_software_update_available")
    private String new_software_update_available;

    @SerializedName("next_channel")
    private String next_channel;

    @SerializedName("no")
    private String no;

    @SerializedName("no_audio")
    private String no_audio;

    @SerializedName("no_channels")
    private String no_channels;

    @SerializedName("no_connection")
    private String no_connection;

    @SerializedName("no_epg_avaliable")
    private String no_epg_avaliable;

    @SerializedName("no_episode")
    private String no_episode;

    @SerializedName("no_information")
    private String no_information;

    @SerializedName("no_movies")
    private String no_movies;

    @SerializedName("no_playlist")
    private String no_playlist;

    @SerializedName("no_playlist_description")
    private String no_playlist_description;

    @SerializedName("no_recently_channels")
    private String no_recently_channels;

    @SerializedName("no_recently_movies")
    private String no_recently_movies;

    @SerializedName("no_recently_series")
    private String no_recently_series;

    @SerializedName("no_series")
    private String no_series;

    @SerializedName("no_subtitle")
    private String no_subtitle;

    @SerializedName("no_trailer")
    private String no_trailer;

    @SerializedName("ok")
    private String ok;

    @SerializedName("open_website")
    private String open_website;

    @SerializedName("order_by_added")
    private String order_by_added;

    @SerializedName("order_by_number")
    private String order_by_number;

    @SerializedName("order_by_rating")
    private String order_by_rating;

    @SerializedName("parent_control")
    private String parent_control;

    @SerializedName("parent_pass_cant_empty")
    private String parent_pass_cant_empty;

    @SerializedName("parent_pass_changed")
    private String parent_pass_changed;

    @SerializedName("pass_incorrect")
    private String pass_incorrect;

    @SerializedName("password")
    private String password;

    @SerializedName("pay_with_google_pay")
    private String pay_with_google_pay;

    @SerializedName("pin_incorrect")
    private String pin_incorrect;

    @SerializedName("play")
    private String play;

    @SerializedName("play_back_error")
    private String play_back_error;

    @SerializedName("play_back_error_description")
    private String play_back_error_description;

    @SerializedName("playback_description")
    private String playback_description;

    @SerializedName("playlist_expire_date")
    private String playlist_expire_date;

    @SerializedName("playlist_is_loading")
    private String playlist_is_loading;

    @SerializedName("playlist_is_not_working")
    private String playlist_is_not_working;

    @SerializedName("playlist_name")
    private String playlist_name;

    @SerializedName("playlist_protected")
    private String playlist_protected;

    @SerializedName("port")
    private String port;

    @SerializedName("portal_loaded_successfully")
    private String portal_loaded_successfully;

    @SerializedName("previous_channel")
    private String previous_channel;

    @SerializedName("put_m3u_link")
    private String put_m3u_link;

    @SerializedName("put_password")
    private String put_password;

    @SerializedName("put_pin_code")
    private String put_pin_code;

    @SerializedName("put_username")
    private String put_username;

    @SerializedName("rate_us")
    private String rate_us;

    @SerializedName("rating")
    private String rating;

    @SerializedName("recent_channels_removed")
    private String recent_channels_removed;

    @SerializedName("recently_viewed")
    private String recently_viewed;

    @SerializedName("refresh_playlist")
    private String refresh_playlist;

    @SerializedName("reload_portal")
    private String reload_portal;

    @SerializedName("remove_favorites")
    private String remove_favorites;

    @SerializedName("rename")
    private String rename;

    @SerializedName("request_download")
    private String request_download;

    @SerializedName("resume")
    private String resume;

    @SerializedName("resume_plyaback_from_ast_position")
    private String resume_plyaback_from_ast_position;

    @SerializedName("resume_to_watch")
    private String resume_to_watch;

    @SerializedName("retry")
    private String retry;

    @SerializedName("scan_code")
    private String scan_code;

    @SerializedName("search")
    private String search;

    @SerializedName("search_by_title")
    private String search_by_title;

    @SerializedName("season")
    private String season;

    @SerializedName("see_all")
    private String see_all;

    @SerializedName("select")
    private String select;

    @SerializedName("select_all")
    private String select_all;

    @SerializedName("select_categories_you_want_to_hide")
    private String select_categories_you_want_to_hide;

    @SerializedName("select_device_type")
    private String select_device_type;

    @SerializedName("select_theme")
    private String select_theme;

    @SerializedName("series")
    private String series;

    @SerializedName("settings")
    private String settings;

    @SerializedName("skip")
    private String skip;

    @SerializedName("sort_a_z")
    private String sort_a_z;

    @SerializedName("sort_z_a")
    private String sort_z_a;

    @SerializedName("stop_playback")
    private String stop_playback;

    @SerializedName("str_add_channel")
    private String str_add_channel;

    @SerializedName("str_add_channels")
    private String str_add_channels;

    @SerializedName("str_add_groups")
    private String str_add_groups;

    @SerializedName("str_complete")
    private String str_complete;

    @SerializedName("str_confirm")
    private String str_confirm;

    @SerializedName("str_contain_this_name")
    private String str_contain_this_name;

    @SerializedName("str_continue")
    private String str_continue;

    @SerializedName("str_delete")
    private String str_delete;

    @SerializedName("str_edit_playlist")
    private String str_edit_playlist;

    @SerializedName("str_group_name")
    private String str_group_name;

    @SerializedName("str_group_string")
    private String str_group_string;

    @SerializedName("str_mark_channels")
    private String str_mark_channels;

    @SerializedName("str_more_episodes")
    private String str_more_episodes;

    @SerializedName("str_my_group")
    private String str_my_group;

    @SerializedName("str_no_lock_channels")
    private String str_no_lock_channels;

    @SerializedName("str_playlist_uploaded_successfully")
    private String str_playlist_uploaded_successfully;

    @SerializedName("protected")
    private String str_protected;

    @SerializedName("str_restart")
    private String str_restart;

    @SerializedName("str_search_channels")
    private String str_search_channels;

    @SerializedName("str_trial_description")
    private String str_trial_description;

    @SerializedName("str_update_playlist")
    private String str_update_playlist;

    @SerializedName("yes")
    private String str_yes;

    @SerializedName("string_default")
    private String string_default;

    @SerializedName("sub_remaining")
    private String sub_remaining;

    @SerializedName("subtitel_background")
    private String subtitel_background;

    @SerializedName("subtitel_color")
    private String subtitel_color;

    @SerializedName("subtitel_size")
    private String subtitel_size;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("subtitle_settings")
    private String subtitle_settings;

    @SerializedName("time_format")
    private String time_format;

    @SerializedName("to_add_manage")
    private String to_add_manage;

    @SerializedName("to_continue")
    private String to_continue;

    @SerializedName("trailer")
    private String trailer;

    @SerializedName("trial_ended")
    private String trial_ended;

    @SerializedName("tv_is_trial")
    private String tv_is_trial;

    @SerializedName("tv_mac_expired")
    private String tv_mac_expired;

    @SerializedName("update_failed")
    private String update_failed;

    @SerializedName("update_now")
    private String update_now;

    @SerializedName("user_incorrect")
    private String user_incorrect;

    @SerializedName("username")
    private String username;

    @SerializedName("version")
    private String version;

    @SerializedName("via_website")
    private String via_website;

    @SerializedName("vlc_player")
    private String vlc_player;

    @SerializedName("want_delete_playlist")
    private String want_delete_playlist;

    @SerializedName("want_external_player")
    private String want_external_player;

    @SerializedName("watch_season")
    private String watch_season;

    @SerializedName("watch_trailer")
    private String watch_trailer;

    @SerializedName("xtreme_codes")
    private String xtreme_codes;

    public String getAccount() {
        String str = this.account;
        return (str == null || str.isEmpty()) ? "Account" : this.account;
    }

    public String getAccount_expired() {
        String str = this.account_expired;
        return (str == null || str.isEmpty()) ? "Account is expired" : this.account_expired;
    }

    public String getActivate_success() {
        String str = this.activate_success;
        return (str == null || str.isEmpty()) ? "Your device is activated successfully." : this.activate_success;
    }

    public String getAdd_correct_alert() {
        String str = this.add_correct_alert;
        return (str == null || str.isEmpty()) ? "Please add correct m3u playlist" : this.add_correct_alert;
    }

    public String getAdd_group() {
        String str = this.add_group;
        return (str == null || str.isEmpty()) ? "+ Add Group" : this.add_group;
    }

    public String getAdd_m3u() {
        String str = this.add_m3u;
        return (str == null || str.isEmpty()) ? "Add M3U URL" : this.add_m3u;
    }

    public String getAdd_playlist() {
        String str = this.add_playlist;
        return (str == null || str.isEmpty()) ? "Add Playlist" : this.add_playlist;
    }

    public String getAdd_to_favorite() {
        String str = this.add_to_favorite;
        return (str == null || str.isEmpty()) ? "Add to Favorite" : this.add_to_favorite;
    }

    public String getAge() {
        String str = this.age;
        return (str == null || str.isEmpty()) ? "Age:" : this.age;
    }

    public String getAll() {
        String str = this.all;
        return (str == null || str.isEmpty()) ? "All" : this.all;
    }

    public String getApp_is_up_to_date() {
        String str = this.app_is_up_to_date;
        return (str == null || str.isEmpty()) ? "Your version is up to date." : this.app_is_up_to_date;
    }

    public String getApp_status() {
        String str = this.app_status;
        return (str == null || str.isEmpty()) ? "Account State" : this.app_status;
    }

    public String getAudio_track() {
        String str = this.audio_track;
        return (str == null || str.isEmpty()) ? "Audio" : this.audio_track;
    }

    public String getAuto_update_2day() {
        String str = this.auto_update_2day;
        return (str == null || str.isEmpty()) ? "Auto-Update Live,Movie, & Series after 2 days" : this.auto_update_2day;
    }

    public String getAuto_update_everyday() {
        String str = this.auto_update_everyday;
        return (str == null || str.isEmpty()) ? "Auto-Update Live,Movie & Series everyday" : this.auto_update_everyday;
    }

    public String getAuto_update_everytime() {
        String str = this.auto_update_everytime;
        return (str == null || str.isEmpty()) ? "Auto-Update Live,Movie & Series everytime" : this.auto_update_everytime;
    }

    public String getAutomatic() {
        String str = this.automatic;
        return (str == null || str.isEmpty()) ? "Automatic" : this.automatic;
    }

    public String getBack() {
        String str = this.back;
        return (str == null || str.isEmpty()) ? "Back" : this.back;
    }

    public String getCancel() {
        String str = this.cancel;
        return (str == null || str.isEmpty()) ? "Cancel" : this.cancel;
    }

    public String getCant_add_this_movie() {
        String str = this.cant_add_this_movie;
        return (str == null || str.isEmpty()) ? "You can not add this movie to favorite." : this.cant_add_this_movie;
    }

    public String getCast() {
        String str = this.cast;
        return (str == null || str.isEmpty()) ? "Cast:" : this.cast;
    }

    public String getCatch_up() {
        String str = this.catch_up;
        return (str == null || str.isEmpty()) ? "Catch up" : this.catch_up;
    }

    public String getChangeLayout() {
        String str = this.change_layout;
        return (str == null || str.isEmpty()) ? "Change Layout" : this.change_layout;
    }

    public String getChange_language() {
        String str = this.change_language;
        return (str == null || str.isEmpty()) ? "Change Language" : this.change_language;
    }

    public String getChange_my_group() {
        String str = this.change_my_group;
        return (str == null || str.isEmpty()) ? "Change My Group" : this.change_my_group;
    }

    public String getChange_playlist() {
        String str = this.change_playlist;
        return (str == null || str.isEmpty()) ? "Change playlist" : this.change_playlist;
    }

    public String getChange_theme() {
        String str = this.change_theme;
        return (str == null || str.isEmpty()) ? "Change Theme" : this.change_theme;
    }

    public String getChannel_added_to_fav() {
        String str = this.channel_added_to_fav;
        return (str == null || str.isEmpty()) ? "This channel is added to Favorite" : this.channel_added_to_fav;
    }

    public String getChannel_locked() {
        String str = this.channel_locked;
        return (str == null || str.isEmpty()) ? "This channel is locked by you successfully." : this.channel_locked;
    }

    public String getChannel_locked_default() {
        String str = this.channel_locked_default;
        return (str == null || str.isEmpty()) ? "This channel is already locked by default." : this.channel_locked_default;
    }

    public String getChannel_removed_from_fav() {
        String str = this.channel_removed_from_fav;
        return (str == null || str.isEmpty()) ? "This channel is removed from Favorite.”" : this.channel_removed_from_fav;
    }

    public String getChannel_unlocked() {
        String str = this.channel_unlocked;
        return (str == null || str.isEmpty()) ? "This channel is unlocked by you successfully." : this.channel_unlocked;
    }

    public String getCheck_internet() {
        String str = this.check_internet;
        return (str == null || str.isEmpty()) ? "Network Error! Please check your internet connection and try again." : this.check_internet;
    }

    public String getClear_all() {
        String str = this.clear_all;
        return (str == null || str.isEmpty()) ? "Deselect All" : this.clear_all;
    }

    public String getClear_channel_header() {
        String str = this.clear_channel_header;
        return (str == null || str.isEmpty()) ? "Select channel names that you want to clear." : this.clear_channel_header;
    }

    public String getClear_history_channels() {
        String str = this.clear_history_channels;
        return (str == null || str.isEmpty()) ? "Clear History Channels" : this.clear_history_channels;
    }

    public String getClear_history_movies() {
        String str = this.clear_history_movies;
        return (str == null || str.isEmpty()) ? "Clear History Movies" : this.clear_history_movies;
    }

    public String getClear_history_series() {
        String str = this.clear_history_series;
        return (str == null || str.isEmpty()) ? "Clear History Series" : this.clear_history_series;
    }

    public String getClear_movie_header() {
        String str = this.clear_movie_header;
        return (str == null || str.isEmpty()) ? "Select movie names that you want to clear." : this.clear_movie_header;
    }

    public String getClear_series_header() {
        String str = this.clear_series_header;
        return (str == null || str.isEmpty()) ? "Select series names that you want to clear." : this.clear_series_header;
    }

    public String getConfirm_pass_cant_empty() {
        String str = this.confirm_pass_cant_empty;
        return (str == null || str.isEmpty()) ? "Confirm password can't be empty!" : this.confirm_pass_cant_empty;
    }

    public String getConfirm_pass_not_matched() {
        String str = this.confirm_pass_not_matched;
        return (str == null || str.isEmpty()) ? "Confirm password is not matched!" : this.confirm_pass_not_matched;
    }

    public String getConfirm_password() {
        String str = this.confirm_password;
        return (str == null || str.isEmpty()) ? "Confirm Password" : this.confirm_password;
    }

    public String getConnect() {
        String str = this.connect;
        return (str == null || str.isEmpty()) ? "Connect" : this.connect;
    }

    public String getContact() {
        String str = this.contact;
        return (str == null || str.isEmpty()) ? "Contact" : this.contact;
    }

    public String getCurrent_expired() {
        String str = this.current_expired;
        return (str == null || str.isEmpty()) ? "Expires:" : this.current_expired;
    }

    public String getDate_added() {
        String str = this.date_added;
        return (str == null || str.isEmpty()) ? "Date Added" : this.date_added;
    }

    public String getDays() {
        String str = this.days;
        return (str == null || str.isEmpty()) ? "days" : this.days;
    }

    public String getDefault_resolution() {
        String str = this.default_resolution;
        return (str == null || str.isEmpty()) ? "Default Resolution" : this.default_resolution;
    }

    public String getDelete() {
        String str = this.delete;
        return (str == null || str.isEmpty()) ? "Delete" : this.delete;
    }

    public String getDelete_cache() {
        String str = this.delete_cache;
        return (str == null || str.isEmpty()) ? "Delete Cache" : this.delete_cache;
    }

    public String getDelete_playlist() {
        String str = this.delete_playlist;
        return (str == null || str.isEmpty()) ? "Delete Playlist" : this.delete_playlist;
    }

    public String getDevice_key() {
        String str = this.device_key;
        return (str == null || str.isEmpty()) ? "Device Key" : this.device_key;
    }

    public String getDirector() {
        String str = this.director;
        return (str == null || str.isEmpty()) ? "Director:" : this.director;
    }

    public String getEdit() {
        String str = this.edit;
        return (str == null || str.isEmpty()) ? "Edit" : this.edit;
    }

    public String getEdit_group_name() {
        String str = this.edit_group_name;
        return (str == null || str.isEmpty()) ? "To edit the group name, please hold on the group name." : this.edit_group_name;
    }

    public String getEnable_subtitles() {
        String str = this.enable_subtitles;
        return (str == null || str.isEmpty()) ? "Enable Subtitle" : this.enable_subtitles;
    }

    public String getEnjoy_tv() {
        String str = this.enjoy_tv;
        return (str == null || str.isEmpty()) ? "Everybody can enjoy the best TV channels as well popular movies and series." : this.enjoy_tv;
    }

    public String getEnter_m3u() {
        String str = this.enter_m3u;
        return (str == null || str.isEmpty()) ? "Enter M3U" : this.enter_m3u;
    }

    public String getEpg() {
        String str = this.epg;
        return (str == null || str.isEmpty()) ? "Epg" : this.epg;
    }

    public String getExit() {
        String str = this.exit;
        return (str == null || str.isEmpty()) ? "Exit" : this.exit;
    }

    public String getExit_description() {
        String str = this.exit_description;
        return (str == null || str.isEmpty()) ? "Click Yes to exit the app. Click No to cancel." : this.exit_description;
    }

    public String getExpire_date() {
        String str = this.expire_date;
        return (str == null || str.isEmpty()) ? "Expiry Date" : this.expire_date;
    }

    public String getExternal_player() {
        String str = this.external_player;
        return (str == null || str.isEmpty()) ? "External player" : this.external_player;
    }

    public String getFavorite() {
        String str = this.favorite;
        return (str == null || str.isEmpty()) ? "Favorite" : this.favorite;
    }

    public String getFill_screen() {
        String str = this.fill_screen;
        return (str == null || str.isEmpty()) ? "Fill Screen" : this.fill_screen;
    }

    public String getFind_movie() {
        String str = this.find_movie;
        return (str == null || str.isEmpty()) ? "Find Movie" : this.find_movie;
    }

    public String getFit_screen() {
        String str = this.fit_screen;
        return (str == null || str.isEmpty()) ? "Fit Screen" : this.fit_screen;
    }

    public String getGrid_layout() {
        String str = this.grid_layout;
        return (str == null || str.isEmpty()) ? "Grid Layout" : this.grid_layout;
    }

    public String getHide_live_category() {
        String str = this.hide_live_category;
        return (str == null || str.isEmpty()) ? "Hide Live Categories" : this.hide_live_category;
    }

    public String getHide_series_category() {
        String str = this.hide_series_category;
        return (str == null || str.isEmpty()) ? "Hide Series Categories" : this.hide_series_category;
    }

    public String getHide_vod_category() {
        String str = this.hide_vod_category;
        return (str == null || str.isEmpty()) ? "Hide Vod Categories" : this.hide_vod_category;
    }

    public String getHome() {
        String str = this.home;
        return (str == null || str.isEmpty()) ? "Home" : this.home;
    }

    public String getIbo_pro_description() {
        String str = this.ibo_pro_description;
        return (str == null || str.isEmpty()) ? "Ibo Pro Player doesn't sell playlist or subscriptions." : this.ibo_pro_description;
    }

    public String getIbo_pro_general_player() {
        String str = this.ibo_pro_general_player;
        return (str == null || str.isEmpty()) ? "Ibo Pro Player is General Media Player and it doesn't include any content or playlists." : this.ibo_pro_general_player;
    }

    public String getInstall_external_player() {
        String str = this.install_external_player;
        return (str == null || str.isEmpty()) ? "Install External Player" : this.install_external_player;
    }

    public String getLength() {
        String str = this.length;
        return (str == null || str.isEmpty()) ? "Length:" : this.length;
    }

    public String getList_layout() {
        String str = this.list_layout;
        return (str == null || str.isEmpty()) ? "List Layout" : this.list_layout;
    }

    public String getLive() {
        String str = this.live;
        return (str == null || str.isEmpty()) ? "Live" : this.live;
    }

    public String getLive_sort() {
        String str = this.live_sort;
        return (str == null || str.isEmpty()) ? "Live sort" : this.live_sort;
    }

    public String getLive_stream_format() {
        String str = this.live_stream_format;
        return (str == null || str.isEmpty()) ? "Live Stream Format" : this.live_stream_format;
    }

    public String getLive_tv() {
        String str = this.live_tv;
        return (str == null || str.isEmpty()) ? "Live TV" : this.live_tv;
    }

    public String getLock() {
        String str = this.lock;
        return (str == null || str.isEmpty()) ? "Lock" : this.lock;
    }

    public String getMac_activated() {
        String str = this.mac_activated;
        return (str == null || str.isEmpty()) ? "Your MAC is Activated." : this.mac_activated;
    }

    public String getMac_address() {
        String str = this.mac_address;
        return (str == null || str.isEmpty()) ? "Mac Address" : this.mac_address;
    }

    public String getMovie_is_added_to_fav() {
        String str = this.movie_is_added_to_fav;
        return (str == null || str.isEmpty()) ? "This movie is added to Favorite." : this.movie_is_added_to_fav;
    }

    public String getMovie_is_removed_from_fav() {
        String str = this.movie_is_removed_from_fav;
        return (str == null || str.isEmpty()) ? "This movie is removed from Favorite." : this.movie_is_removed_from_fav;
    }

    public String getMovies() {
        String str = this.movies;
        return (str == null || str.isEmpty()) ? "Movies" : this.movies;
    }

    public String getMx_player() {
        String str = this.mx_player;
        return (str == null || str.isEmpty()) ? "MX player" : this.mx_player;
    }

    public String getNet_pass() {
        String str = this.net_pass;
        return (str == null || str.isEmpty()) ? "New Password" : this.net_pass;
    }

    public String getNew_pass_cant_empty() {
        String str = this.new_pass_cant_empty;
        return (str == null || str.isEmpty()) ? "New password can't be empty!" : this.new_pass_cant_empty;
    }

    public String getNew_pass_length() {
        String str = this.new_pass_length;
        return (str == null || str.isEmpty()) ? "New password length need to be 4!" : this.new_pass_length;
    }

    public String getNew_software_update_available() {
        String str = this.new_software_update_available;
        return (str == null || str.isEmpty()) ? "New software update available." : this.new_software_update_available;
    }

    public String getNext_channel() {
        String str = this.next_channel;
        return (str == null || str.isEmpty()) ? "Next Channel" : this.next_channel;
    }

    public String getNo() {
        String str = this.no;
        return (str == null || str.isEmpty()) ? "No" : this.no;
    }

    public String getNo_audio() {
        String str = this.no_audio;
        return (str == null || str.isEmpty()) ? "No audio tracks or not loading yet" : this.no_audio;
    }

    public String getNo_channels() {
        String str = this.no_channels;
        return (str == null || str.isEmpty()) ? "There are no channels." : this.no_channels;
    }

    public String getNo_connection() {
        String str = this.no_connection;
        return (str == null || str.isEmpty()) ? "No Connection!" : this.no_connection;
    }

    public String getNo_epg_avaliable() {
        String str = this.no_epg_avaliable;
        return (str == null || str.isEmpty()) ? "No EPG available." : this.no_epg_avaliable;
    }

    public String getNo_episode() {
        String str = this.no_episode;
        return (str == null || str.isEmpty()) ? "No Episodes" : this.no_episode;
    }

    public String getNo_information() {
        String str = this.no_information;
        return (str == null || str.isEmpty()) ? "No Information" : this.no_information;
    }

    public String getNo_movies() {
        String str = this.no_movies;
        return (str == null || str.isEmpty()) ? "There are no movies." : this.no_movies;
    }

    public String getNo_playlist() {
        String str = this.no_playlist;
        return (str == null || str.isEmpty()) ? "There is no playlist uploaded for your Device." : this.no_playlist;
    }

    public String getNo_playlist_description() {
        String str = this.no_playlist_description;
        return (str == null || str.isEmpty()) ? "You have not uploaded your playlist yet, you can continue with our demo content or upload your playlist in following website." : this.no_playlist_description;
    }

    public String getNo_recently_channels() {
        String str = this.no_recently_channels;
        return (str == null || str.isEmpty()) ? "There is no recently viewed channels." : this.no_recently_channels;
    }

    public String getNo_recently_movies() {
        String str = this.no_recently_movies;
        return (str == null || str.isEmpty()) ? "There is no recently viewed movies." : this.no_recently_movies;
    }

    public String getNo_recently_series() {
        String str = this.no_recently_series;
        return (str == null || str.isEmpty()) ? "There is no recently viewed series." : this.no_recently_series;
    }

    public String getNo_series() {
        String str = this.no_series;
        return (str == null || str.isEmpty()) ? "There are no series." : this.no_series;
    }

    public String getNo_subtitle() {
        String str = this.no_subtitle;
        return (str == null || str.isEmpty()) ? "No subtitle or not loading yet" : this.no_subtitle;
    }

    public String getNo_trailer() {
        String str = this.no_trailer;
        return (str == null || str.isEmpty()) ? "No trailer" : this.no_trailer;
    }

    public String getOk() {
        String str = this.ok;
        return (str == null || str.isEmpty()) ? "Ok" : this.ok;
    }

    public String getOpen_website() {
        String str = this.open_website;
        return (str == null || str.isEmpty()) ? "Open Website" : this.open_website;
    }

    public String getOrder_by_added() {
        String str = this.order_by_added;
        return (str == null || str.isEmpty()) ? "Order by added" : this.order_by_added;
    }

    public String getOrder_by_number() {
        String str = this.order_by_number;
        return (str == null || str.isEmpty()) ? "Order by number" : this.order_by_number;
    }

    public String getOrder_by_rating() {
        String str = this.order_by_rating;
        return (str == null || str.isEmpty()) ? "Order by rating" : this.order_by_rating;
    }

    public String getParent_control() {
        String str = this.parent_control;
        return (str == null || str.isEmpty()) ? "Parent control" : this.parent_control;
    }

    public String getParent_pass_cant_empty() {
        String str = this.parent_pass_cant_empty;
        return (str == null || str.isEmpty()) ? "Password can't be empty!" : this.parent_pass_cant_empty;
    }

    public String getParent_pass_changed() {
        String str = this.parent_pass_changed;
        return (str == null || str.isEmpty()) ? "Parent password is changed successfully." : this.parent_pass_changed;
    }

    public String getPass_incorrect() {
        String str = this.pass_incorrect;
        return (str == null || str.isEmpty()) ? "Password is incorrect!" : this.pass_incorrect;
    }

    public String getPassword() {
        String str = this.password;
        return (str == null || str.isEmpty()) ? "Password" : this.password;
    }

    public String getPay_with_google_pay() {
        String str = this.pay_with_google_pay;
        return (str == null || str.isEmpty()) ? "Pay With Google Pay" : this.pay_with_google_pay;
    }

    public String getPin_incorrect() {
        String str = this.pin_incorrect;
        return (str == null || str.isEmpty()) ? "Your Pin code is incorrect. Please try again." : this.pin_incorrect;
    }

    public String getPlay() {
        String str = this.play;
        return (str == null || str.isEmpty()) ? "Watch Now" : this.play;
    }

    public String getPlay_back_error() {
        String str = this.play_back_error;
        return (str == null || str.isEmpty()) ? "Play back Error!" : this.play_back_error;
    }

    public String getPlay_back_error_description() {
        String str = this.play_back_error_description;
        return (str == null || str.isEmpty()) ? "Please contact the server provider for more information." : this.play_back_error_description;
    }

    public String getPlayback_description() {
        String str = this.playback_description;
        return (str == null || str.isEmpty()) ? "Click YES to exit the play. Click NO to cancel." : this.playback_description;
    }

    public String getPlaylist_expire_date() {
        String str = this.playlist_expire_date;
        return (str == null || str.isEmpty()) ? "Playlist Expiry Date" : this.playlist_expire_date;
    }

    public String getPlaylist_is_loading() {
        String str = this.playlist_is_loading;
        return (str == null || str.isEmpty()) ? "Playlist is loading, again. You need to wait few minutes." : this.playlist_is_loading;
    }

    public String getPlaylist_is_not_working() {
        String str = this.playlist_is_not_working;
        return (str == null || str.isEmpty()) ? "Playlist is not working." : this.playlist_is_not_working;
    }

    public String getPlaylist_name() {
        String str = this.playlist_name;
        return (str == null || str.isEmpty()) ? "Playlist Name" : this.playlist_name;
    }

    public String getPlaylist_protected() {
        String str = this.playlist_protected;
        return (str == null || str.isEmpty()) ? "Playlist is protected." : this.playlist_protected;
    }

    public String getPort() {
        String str = this.port;
        return (str == null || str.isEmpty()) ? "Port" : this.port;
    }

    public String getPortal_loaded_successfully() {
        String str = this.portal_loaded_successfully;
        return (str == null || str.isEmpty()) ? "Portal is reloaded successfully." : this.portal_loaded_successfully;
    }

    public String getPrevious_channel() {
        String str = this.previous_channel;
        return (str == null || str.isEmpty()) ? "Previous Channel" : this.previous_channel;
    }

    public String getPut_m3u_link() {
        String str = this.put_m3u_link;
        return (str == null || str.isEmpty()) ? "Please put m3u url." : this.put_m3u_link;
    }

    public String getPut_password() {
        String str = this.put_password;
        return (str == null || str.isEmpty()) ? "Please put password." : this.put_password;
    }

    public String getPut_pin_code() {
        String str = this.put_pin_code;
        return (str == null || str.isEmpty()) ? "Please put pin code." : this.put_pin_code;
    }

    public String getPut_username() {
        String str = this.put_username;
        return (str == null || str.isEmpty()) ? "Please put username." : this.put_username;
    }

    public String getRate_us() {
        String str = this.rate_us;
        return (str == null || str.isEmpty()) ? "Rate us" : this.rate_us;
    }

    public String getRating() {
        String str = this.rating;
        return (str == null || str.isEmpty()) ? "Rating:" : this.rating;
    }

    public String getRecent_channels_removed() {
        String str = this.recent_channels_removed;
        return (str == null || str.isEmpty()) ? "Recent channels have been successfully cleared." : this.recent_channels_removed;
    }

    public String getRecently_viewed() {
        String str = this.recently_viewed;
        return (str == null || str.isEmpty()) ? "Recently Viewed" : this.recently_viewed;
    }

    public String getRefresh_playlist() {
        String str = this.refresh_playlist;
        return (str == null || str.isEmpty()) ? "Refresh playlist" : this.refresh_playlist;
    }

    public String getReload_portal() {
        String str = this.reload_portal;
        return (str == null || str.isEmpty()) ? "Reload" : this.reload_portal;
    }

    public String getRemove_favorites() {
        String str = this.remove_favorites;
        return (str == null || str.isEmpty()) ? "Remove from Favorite" : this.remove_favorites;
    }

    public String getRename() {
        String str = this.rename;
        return (str == null || str.isEmpty()) ? "Rename" : this.rename;
    }

    public String getRequest_download() {
        String str = this.request_download;
        return (str == null || str.isEmpty()) ? "Downloading latest version." : this.request_download;
    }

    public String getResume() {
        String str = this.resume;
        return (str == null || str.isEmpty()) ? "Resume" : this.resume;
    }

    public String getResume_plyaback_from_ast_position() {
        String str = this.resume_plyaback_from_ast_position;
        return (str == null || str.isEmpty()) ? "Resume playback from last position" : this.resume_plyaback_from_ast_position;
    }

    public String getResume_to_watch() {
        String str = this.resume_to_watch;
        return (str == null || str.isEmpty()) ? "Resume to Watch" : this.resume_to_watch;
    }

    public String getRetry() {
        String str = this.retry;
        return (str == null || str.isEmpty()) ? "Retry" : this.retry;
    }

    public String getScan_code() {
        String str = this.scan_code;
        return (str == null || str.isEmpty()) ? "" : this.scan_code;
    }

    public String getSearch() {
        String str = this.search;
        return (str == null || str.isEmpty()) ? "Search" : this.search;
    }

    public String getSearch_by_title() {
        String str = this.search_by_title;
        return (str == null || str.isEmpty()) ? "Search By Title" : this.search_by_title;
    }

    public String getSeason() {
        String str = this.season;
        return (str == null || str.isEmpty()) ? "Season" : this.season;
    }

    public String getSee_all() {
        String str = this.see_all;
        return (str == null || str.isEmpty()) ? "See All" : this.see_all;
    }

    public String getSelect() {
        String str = this.select;
        return (str == null || str.isEmpty()) ? "Select" : this.select;
    }

    public String getSelect_all() {
        String str = this.select_all;
        return (str == null || str.isEmpty()) ? "Select all" : this.select_all;
    }

    public String getSelect_categories_you_want_to_hide() {
        String str = this.select_categories_you_want_to_hide;
        return (str == null || str.isEmpty()) ? "Select categories that you want to hide" : this.select_categories_you_want_to_hide;
    }

    public String getSelect_device_type() {
        String str = this.select_device_type;
        return (str == null || str.isEmpty()) ? "Select Device Type" : this.select_device_type;
    }

    public String getSelect_theme() {
        String str = this.select_theme;
        return (str == null || str.isEmpty()) ? "Select Theme" : this.select_theme;
    }

    public String getSeries() {
        String str = this.series;
        return (str == null || str.isEmpty()) ? "Series" : this.series;
    }

    public String getSettings() {
        String str = this.settings;
        return (str == null || str.isEmpty()) ? "Settings" : this.settings;
    }

    public String getSkip() {
        String str = this.skip;
        return (str == null || str.isEmpty()) ? "Skip" : this.skip;
    }

    public String getSort_a_z() {
        String str = this.sort_a_z;
        return (str == null || str.isEmpty()) ? "Order by A-Z" : this.sort_a_z;
    }

    public String getSort_z_a() {
        String str = this.sort_z_a;
        return (str == null || str.isEmpty()) ? "Order by Z-A" : this.sort_z_a;
    }

    public String getStop_playback() {
        String str = this.stop_playback;
        return (str == null || str.isEmpty()) ? "Stop Playback?" : this.stop_playback;
    }

    public String getStr_add_channel() {
        String str = this.str_add_channel;
        return (str == null || str.isEmpty()) ? "+ Add Channel" : this.str_add_channel;
    }

    public String getStr_add_channels() {
        String str = this.str_add_channels;
        return (str == null || str.isEmpty()) ? "Add Channel" : this.str_add_channels;
    }

    public String getStr_add_groups() {
        String str = this.str_add_groups;
        return (str == null || str.isEmpty()) ? "Add Group" : this.str_add_groups;
    }

    public String getStr_complete() {
        String str = this.str_complete;
        return (str == null || str.isEmpty()) ? "Complete" : this.str_complete;
    }

    public String getStr_confirm() {
        String str = this.str_confirm;
        return (str == null || str.isEmpty()) ? "Confirm" : this.str_confirm;
    }

    public String getStr_contain_this_name() {
        String str = this.str_contain_this_name;
        return (str == null || str.isEmpty()) ? "Your categories already contain this name." : this.str_contain_this_name;
    }

    public String getStr_continue() {
        String str = this.str_continue;
        return (str == null || str.isEmpty()) ? "Continue" : this.str_continue;
    }

    public String getStr_delete() {
        String str = this.str_delete;
        return (str == null || str.isEmpty()) ? "Delete" : this.str_delete;
    }

    public String getStr_edit_playlist() {
        String str = this.str_edit_playlist;
        return (str == null || str.isEmpty()) ? "Edit Playlist" : this.str_edit_playlist;
    }

    public String getStr_group_name() {
        String str = this.str_group_name;
        return (str == null || str.isEmpty()) ? "Group Name" : this.str_group_name;
    }

    public String getStr_group_string() {
        String str = this.str_group_string;
        return (str == null || str.isEmpty()) ? "Now you can add channels \nto the group" : this.str_group_string;
    }

    public String getStr_mark_channels() {
        String str = this.str_mark_channels;
        return (str == null || str.isEmpty()) ? "Mark the channels to add\nto the group" : this.str_mark_channels;
    }

    public String getStr_more_episodes() {
        String str = this.str_more_episodes;
        return (str == null || str.isEmpty()) ? "More episodes" : this.str_more_episodes;
    }

    public String getStr_my_group() {
        String str = this.str_my_group;
        return (str == null || str.isEmpty()) ? "My Groups" : this.str_my_group;
    }

    public String getStr_no_lock_channels() {
        String str = this.str_no_lock_channels;
        return (str == null || str.isEmpty()) ? "There is no Lock Channels." : this.str_no_lock_channels;
    }

    public String getStr_playlist_uploaded_successfully() {
        String str = this.str_playlist_uploaded_successfully;
        return (str == null || str.isEmpty()) ? "Playlist is uploaded successfully." : this.str_playlist_uploaded_successfully;
    }

    public String getStr_protected() {
        String str = this.str_protected;
        return (str == null || str.isEmpty()) ? "Protected" : this.str_protected;
    }

    public String getStr_restart() {
        String str = this.str_restart;
        return (str == null || str.isEmpty()) ? "Restart" : this.str_restart;
    }

    public String getStr_search_channels() {
        String str = this.str_search_channels;
        return (str == null || str.isEmpty()) ? "Search Channels" : this.str_search_channels;
    }

    public String getStr_trial_description() {
        String str = this.str_trial_description;
        return (str == null || str.isEmpty()) ? "The application can be used free of charge for first 7 days." : this.str_trial_description;
    }

    public String getStr_update_playlist() {
        String str = this.str_update_playlist;
        return (str == null || str.isEmpty()) ? "Update playlist" : this.str_update_playlist;
    }

    public String getStr_yes() {
        String str = this.str_yes;
        return (str == null || str.isEmpty()) ? "Yes" : this.str_yes;
    }

    public String getString_default() {
        String str = this.string_default;
        return (str == null || str.isEmpty()) ? "Default" : this.string_default;
    }

    public String getSub_remaining() {
        String str = this.sub_remaining;
        return (str == null || str.isEmpty()) ? "Your subscription is remaining" : this.sub_remaining;
    }

    public String getSubtitel_background() {
        String str = this.subtitel_background;
        return (str == null || str.isEmpty()) ? "Subtitle Background" : this.subtitel_background;
    }

    public String getSubtitel_color() {
        String str = this.subtitel_color;
        return (str == null || str.isEmpty()) ? "Subtitle Color" : this.subtitel_color;
    }

    public String getSubtitel_size() {
        String str = this.subtitel_size;
        return (str == null || str.isEmpty()) ? "Subtitle Size" : this.subtitel_size;
    }

    public String getSubtitle() {
        String str = this.subtitle;
        return (str == null || str.isEmpty()) ? "Subtitle" : this.subtitle;
    }

    public String getSubtitle_settings() {
        String str = this.subtitle_settings;
        return (str == null || str.isEmpty()) ? "Subtitle Settings" : this.subtitle_settings;
    }

    public String getTime_format() {
        String str = this.time_format;
        return (str == null || str.isEmpty()) ? "Time Format" : this.time_format;
    }

    public String getTo_add_manage() {
        String str = this.to_add_manage;
        return (str == null || str.isEmpty()) ? "" : this.to_add_manage;
    }

    public String getTo_continue() {
        String str = this.to_continue;
        return (str == null || str.isEmpty()) ? "To continue the app, please pay" : this.to_continue;
    }

    public String getTrailer() {
        String str = this.trailer;
        return (str == null || str.isEmpty()) ? HttpHeaders.TRAILER : this.trailer;
    }

    public String getTrial_ended() {
        String str = this.trial_ended;
        return (str == null || str.isEmpty()) ? "Trial is ended" : this.trial_ended;
    }

    public String getTv_is_trial() {
        String str = this.tv_is_trial;
        return (str == null || str.isEmpty()) ? "Your MAC is Free Trial." : this.tv_is_trial;
    }

    public String getTv_mac_expired() {
        String str = this.tv_mac_expired;
        return (str == null || str.isEmpty()) ? "Your MAC is expired." : this.tv_mac_expired;
    }

    public String getUpdate_failed() {
        String str = this.update_failed;
        return (str == null || str.isEmpty()) ? "Update Failed." : this.update_failed;
    }

    public String getUpdate_now() {
        String str = this.update_now;
        return (str == null || str.isEmpty()) ? "Update Now" : this.update_now;
    }

    public String getUser_incorrect() {
        String str = this.user_incorrect;
        return (str == null || str.isEmpty()) ? "User account is incorrect" : this.user_incorrect;
    }

    public String getUsername() {
        String str = this.username;
        return (str == null || str.isEmpty()) ? "Username" : this.username;
    }

    public String getVersion() {
        String str = this.version;
        return (str == null || str.isEmpty()) ? "Version" : this.version;
    }

    public String getVia_website() {
        String str = this.via_website;
        return (str == null || str.isEmpty()) ? "via website or Google Pay." : this.via_website;
    }

    public String getVlc_player() {
        String str = this.vlc_player;
        return (str == null || str.isEmpty()) ? "VLC player" : this.vlc_player;
    }

    public String getWant_delete_playlist() {
        String str = this.want_delete_playlist;
        return (str == null || str.isEmpty()) ? "Do you want to delete selected playlist for your Mac Address?" : this.want_delete_playlist;
    }

    public String getWant_external_player() {
        String str = this.want_external_player;
        return (str == null || str.isEmpty()) ? "Do you want to install external player?" : this.want_external_player;
    }

    public String getWatch_season() {
        String str = this.watch_season;
        return (str == null || str.isEmpty()) ? "Watch Season" : this.watch_season;
    }

    public String getWatch_trailer() {
        String str = this.watch_trailer;
        return (str == null || str.isEmpty()) ? "Watch Trailer" : this.watch_trailer;
    }

    public String getXtreme_codes() {
        String str = this.xtreme_codes;
        return (str == null || str.isEmpty()) ? "Xtream-Codes-Api" : this.xtreme_codes;
    }

    public String get_12_hour_format() {
        String str = this._12_hour_format;
        return (str == null || str.isEmpty()) ? "12 hour format" : this._12_hour_format;
    }

    public String get_24_hour_format() {
        String str = this._24_hour_format;
        return (str == null || str.isEmpty()) ? "24 hour format" : this._24_hour_format;
    }
}
