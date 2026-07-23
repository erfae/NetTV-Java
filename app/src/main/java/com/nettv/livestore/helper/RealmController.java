package com.nettv.livestore.helper;

import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import com.nettv.livestore.apps.Constants;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.models.CategoryModel;
import com.nettv.livestore.models.EPGChannel;
import com.nettv.livestore.models.EpisodeModel;
import com.nettv.livestore.models.MovieModel;
import com.nettv.livestore.models.ResumeModel;
import com.nettv.livestore.models.ResumeSeriesModel;
import com.nettv.livestore.models.Season;
import com.nettv.livestore.models.SeriesModel;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes2.dex */
public class RealmController {
    private static HashMap<String, List<EpisodeModel>> episodeModelHashMap;
    public static RealmController instance;
    public Realm realm = Realm.getInstance(new RealmConfiguration.Builder().name("MTV.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().allowWritesOnUiThread(true).build());

    private static void addEpisodeToSeason(EpisodeModel episodeModel) {
        String season_name = episodeModel.getSeason_name();
        List<EpisodeModel> arrayList = episodeModelHashMap.get(season_name);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(episodeModel);
        episodeModelHashMap.put(season_name, arrayList);
    }

    private static List<Season> getSeasonFromEpisodes(List<EpisodeModel> list) {
        episodeModelHashMap = new HashMap<>();
        Iterator<EpisodeModel> it = list.iterator();
        while (it.hasNext()) {
            addEpisodeToSeason(it.next());
        }
        TreeSet<String> treeSet = new TreeSet(episodeModelHashMap.keySet());
        ArrayList arrayList = new ArrayList();
        for (String str : treeSet) {
            List<EpisodeModel> list2 = episodeModelHashMap.get(str);
            if (list2 != null && list2.size() > 0) {
                Season season = new Season();
                season.setName(str);
                season.setCategory_name(list2.get(0).getCategory_name());
                season.setEpisodeModels(list2);
                arrayList.add(season);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addPositionToMovies$4(String str, boolean z, long j, int i, String str2, Realm realm) {
        MovieModel movieModel = (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(realm, MovieModel.class, "name", str);
        if (movieModel != null) {
            movieModel.setIs_recent(z);
            movieModel.setTime(j);
            movieModel.setPro(i);
            movieModel.setTmdb_id(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToFavChannels$0(String str, boolean z, Realm realm) {
        EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
        if (ePGChannel != null) {
            ePGChannel.setIs_favorite(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToFavMovie$3(String str, boolean z, Realm realm) {
        MovieModel movieModel = (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(realm, MovieModel.class, "name", str);
        if (movieModel != null) {
            movieModel.setIs_favorite(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToFavSeries$5(String str, boolean z, Realm realm) {
        SeriesModel seriesModel = (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(realm, SeriesModel.class, "name", str);
        if (seriesModel != null) {
            seriesModel.setIs_favorite(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToLockChannels$1(String str, boolean z, Realm realm) {
        EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
        if (ePGChannel != null) {
            ePGChannel.setIs_locked(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToRecentChannels$2(String str, boolean z, Realm realm) {
        EPGChannel ePGChannel = (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(realm, EPGChannel.class, "name", str);
        if (ePGChannel != null) {
            ePGChannel.setIs_recent(z);
            ePGChannel.setRecent_pos(System.currentTimeMillis() / 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToRecentSeries$6(String str, boolean z, int i, int i2, Realm realm) {
        SeriesModel seriesModel = (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(realm, SeriesModel.class, "name", str);
        if (seriesModel != null) {
            seriesModel.setIs_recent(z);
            seriesModel.setSeason_pos(i);
            seriesModel.setEpisode_pos(i2);
        }
    }

    public static RealmController with() {
        if (instance == null) {
            Realm.init(LTVApp.getInstance());
            instance = new RealmController();
        }
        return instance;
    }

    public void addPositionToMovies(final String str, final String str2, final boolean z, final long j, final int i, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        Realm.Transaction transaction = new Realm.Transaction() { // from class: com.nettv.livestore.helper.RealmController$$ExternalSyntheticLambda3
            @Override // io.realm.Realm.Transaction
            public final void execute(Realm realm2) {
                RealmController.lambda$addPositionToMovies$4(str, z, j, i, str2, realm2);
            }
        };
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(transaction, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 6));
    }

    public void addToFavChannels(String str, boolean z, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        int i = 0;
        RealmController$$ExternalSyntheticLambda1 realmController$$ExternalSyntheticLambda1 = new RealmController$$ExternalSyntheticLambda1(str, z, i);
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(realmController$$ExternalSyntheticLambda1, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, i));
    }

    public void addToFavMovie(String str, boolean z, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        RealmController$$ExternalSyntheticLambda1 realmController$$ExternalSyntheticLambda1 = new RealmController$$ExternalSyntheticLambda1(str, z, 4);
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(realmController$$ExternalSyntheticLambda1, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 5));
    }

    public void addToFavSeries(String str, boolean z, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        RealmController$$ExternalSyntheticLambda1 realmController$$ExternalSyntheticLambda1 = new RealmController$$ExternalSyntheticLambda1(str, z, 2);
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(realmController$$ExternalSyntheticLambda1, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 3));
    }

    public void addToLockChannels(String str, boolean z, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        RealmController$$ExternalSyntheticLambda1 realmController$$ExternalSyntheticLambda1 = new RealmController$$ExternalSyntheticLambda1(str, z, 3);
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(realmController$$ExternalSyntheticLambda1, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 4));
    }

    public void addToRecentChannels(String str, boolean z, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        RealmController$$ExternalSyntheticLambda1 realmController$$ExternalSyntheticLambda1 = new RealmController$$ExternalSyntheticLambda1(str, z, 1);
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(realmController$$ExternalSyntheticLambda1, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 2));
    }

    public void addToRecentSeries(final String str, final boolean z, final int i, final int i2, RealmChangeItemListener realmChangeItemListener) {
        Realm realm = this.realm;
        Realm.Transaction transaction = new Realm.Transaction() { // from class: com.nettv.livestore.helper.RealmController$$ExternalSyntheticLambda2
            @Override // io.realm.Realm.Transaction
            public final void execute(Realm realm2) {
                RealmController.lambda$addToRecentSeries$6(str, z, i, i2, realm2);
            }
        };
        Objects.requireNonNull(realmChangeItemListener);
        realm.executeTransactionAsync(transaction, new RealmController$$ExternalSyntheticLambda0(realmChangeItemListener, 1));
    }

    public int getAllEpgChannelSize() {
        return this.realm.where(EPGChannel.class).findAll().size();
    }

    public EPGChannel getChannelByNumber(int i) {
        return (EPGChannel) this.realm.where(EPGChannel.class).equalTo("num", Integer.valueOf(i)).findFirst();
    }

    public MovieModel getContainMoviesByTitle(String str) {
        return (MovieModel) this.realm.where(MovieModel.class).contains("name", str).findFirst();
    }

    public EPGChannel getEpgChannelByName(String str) {
        return (EPGChannel) MainActivity$$ExternalSyntheticOutline0.m(this.realm, EPGChannel.class, "name", str);
    }

    public RealmResults<EPGChannel> getEpgChannelsByNameArray(List<String> list, String str) {
        return this.realm.where(EPGChannel.class).in("name", (String[]) list.toArray(new String[list.size()])).contains("name", str.toLowerCase(), Case.INSENSITIVE).findAll();
    }

    public List<EpisodeModel> getEpisodesBySeason(String str, String str2) {
        return new ArrayList(this.realm.where(EpisodeModel.class).equalTo("series_name", str).equalTo("season_name", str2).findAll());
    }

    public List<String> getFavChannelNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.realm.where(EPGChannel.class).equalTo("is_favorite", Boolean.TRUE).findAll().iterator();
        while (it.hasNext()) {
            arrayList.add(((EPGChannel) it.next()).getName());
        }
        return arrayList;
    }

    public List<String> getFavMovieNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).findAll().iterator();
        while (it.hasNext()) {
            arrayList.add(((MovieModel) it.next()).getName());
        }
        return arrayList;
    }

    public List<String> getFavSeriesNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).findAll().iterator();
        while (it.hasNext()) {
            arrayList.add(((SeriesModel) it.next()).getName());
        }
        return arrayList;
    }

    public RealmResults<EPGChannel> getLiveChannelsByCategory(CategoryModel categoryModel, String str, boolean z, int i) {
        String id;
        String str2 = z ? "category_name" : "category_id";
        if (z) {
            id = categoryModel.getName().contains("!@#%") ? categoryModel.getName().split("!@#%")[1] : categoryModel.getName();
        } else {
            id = categoryModel.getId();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.fav_id)) {
            if (i != 1) {
                return i != 2 ? this.realm.where(EPGChannel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).findAll() : this.realm.where(EPGChannel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(EPGChannel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.ASCENDING).findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.resume_id)) {
            return this.realm.where(EPGChannel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("recent_pos", Sort.DESCENDING).findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.all_id)) {
            if (i != 1) {
                return i != 2 ? this.realm.where(EPGChannel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).findAll() : this.realm.where(EPGChannel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(EPGChannel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.ASCENDING).findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.lock_id)) {
            if (i != 1) {
                return i != 2 ? this.realm.where(EPGChannel.class).equalTo("is_locked", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).findAll() : this.realm.where(EPGChannel.class).equalTo("is_locked", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(EPGChannel.class).equalTo("is_locked", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.ASCENDING).findAll();
        }
        if (i != 1) {
            return i != 2 ? this.realm.where(EPGChannel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).findAll() : this.realm.where(EPGChannel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
        }
        return this.realm.where(EPGChannel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.ASCENDING).findAll();
    }

    public RealmResults<EPGChannel> getLiveChannelsByKey(String str, boolean z) {
        if (!z) {
            return this.realm.where(EPGChannel.class).contains("name", str.toLowerCase(Locale.ROOT), Case.INSENSITIVE).notEqualTo("category_id", Constants.xxx_live_categories.size() > 0 ? Constants.xxx_live_categories.get(0) : "").findAll();
        }
        RealmQuery realmQueryNot = this.realm.where(EPGChannel.class).contains("name", str.toLowerCase(Locale.ROOT)).not();
        Case r4 = Case.INSENSITIVE;
        return realmQueryNot.contains("category_name", "xxx", r4).not().contains("category_name", "adult", r4).not().contains("category_name", "porn", r4).findAll();
    }

    public List<String> getLockChannelNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.realm.where(EPGChannel.class).equalTo("is_locked", Boolean.TRUE).findAll().iterator();
        while (it.hasNext()) {
            arrayList.add(((EPGChannel) it.next()).getName());
        }
        return arrayList;
    }

    public MovieModel getMovieById(String str) {
        return (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(this.realm, MovieModel.class, "stream_id", str);
    }

    public MovieModel getMovieByName(String str) {
        return (MovieModel) MainActivity$$ExternalSyntheticOutline0.m(this.realm, MovieModel.class, "name", str);
    }

    public RealmResults<MovieModel> getMovieModelsByCategory(CategoryModel categoryModel, String str, boolean z, int i) {
        String id;
        String str2 = z ? "category_name" : "category_id";
        if (z) {
            id = categoryModel.getName().contains("!@#%") ? categoryModel.getName().split("!@#%")[1] : categoryModel.getName();
        } else {
            id = categoryModel.getId();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.resume_id)) {
            if (i == 1) {
                return this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("added", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.all_id)) {
            if (i == 1) {
                return this.realm.where(MovieModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("added", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(MovieModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(MovieModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(MovieModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(MovieModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.fav_id)) {
            if (i == 1) {
                return this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("added", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(MovieModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (i == 1) {
            return this.realm.where(MovieModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("added", Sort.DESCENDING).findAll();
        }
        if (i == 2) {
            return this.realm.where(MovieModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
        }
        if (i != 3) {
            return i != 4 ? this.realm.where(MovieModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(MovieModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
        }
        return this.realm.where(MovieModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
    }

    public RealmResults<MovieModel> getMoviesByKey(String str, boolean z) {
        String str2 = z ? "category_name" : "category_id";
        if (!z) {
            return this.realm.where(MovieModel.class).contains("name", str.toLowerCase(Locale.ROOT), Case.INSENSITIVE).notEqualTo("category_id", Constants.xxx_vod_categories.size() > 0 ? Constants.xxx_vod_categories.get(0) : "").findAll();
        }
        RealmQuery realmQueryNot = this.realm.where(MovieModel.class).contains("name", str.toLowerCase(Locale.ROOT)).not();
        Case r5 = Case.INSENSITIVE;
        return realmQueryNot.contains(str2, "xxx", r5).not().contains(str2, "adult", r5).not().contains(str2, "porn", r5).findAll();
    }

    public List<String> getRecentChannelNames() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.realm.where(EPGChannel.class).equalTo("is_recent", Boolean.TRUE).findAll().iterator();
        while (it.hasNext()) {
            arrayList.add(((EPGChannel) it.next()).getName());
        }
        return arrayList;
    }

    public List<ResumeSeriesModel> getResentSeriesNames() {
        ArrayList arrayList = new ArrayList();
        for (SeriesModel seriesModel : this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).findAll()) {
            ResumeSeriesModel resumeSeriesModel = new ResumeSeriesModel();
            resumeSeriesModel.setName(seriesModel.getName());
            resumeSeriesModel.setSeason_pos(seriesModel.getSeason_pos());
            resumeSeriesModel.setEpisode_pos(seriesModel.getEpisode_pos());
            arrayList.add(resumeSeriesModel);
        }
        return arrayList;
    }

    public List<ResumeModel> getResumeMovies() {
        ArrayList arrayList = new ArrayList();
        for (MovieModel movieModel : this.realm.where(MovieModel.class).equalTo("is_recent", Boolean.TRUE).findAll()) {
            ResumeModel resumeModel = new ResumeModel();
            resumeModel.setName(movieModel.getName());
            resumeModel.setPro(movieModel.getPro());
            resumeModel.setLast_position(movieModel.getTime());
            resumeModel.setTmdb_id(movieModel.getTmdb_id());
            arrayList.add(resumeModel);
        }
        return arrayList;
    }

    public List<Season> getSeasonBySeriesName(String str) {
        return getSeasonFromEpisodes(new ArrayList(this.realm.where(EpisodeModel.class).equalTo("series_name", str).findAll()));
    }

    public SeriesModel getSeriesById(String str) {
        return (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(this.realm, SeriesModel.class, "series_id", str);
    }

    public RealmResults<SeriesModel> getSeriesByKey(String str) {
        return this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(Locale.ROOT), Case.INSENSITIVE).findAll();
    }

    public SeriesModel getSeriesByName(String str) {
        return (SeriesModel) MainActivity$$ExternalSyntheticOutline0.m(this.realm, SeriesModel.class, "name", str);
    }

    public RealmResults<SeriesModel> getSeriesModelsByCategory(CategoryModel categoryModel, String str, boolean z, int i) {
        String id;
        String str2 = z ? "category_name" : "category_id";
        if (z) {
            id = categoryModel.getName().contains("!@#%") ? categoryModel.getName().split("!@#%")[1] : categoryModel.getName();
        } else {
            id = categoryModel.getId();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.all_id)) {
            if (i == 1) {
                return this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("last_modified", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(SeriesModel.class).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.fav_id)) {
            if (i == 1) {
                return this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("last_modified", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(SeriesModel.class).equalTo("is_favorite", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (categoryModel.getId().equalsIgnoreCase(Constants.resume_id)) {
            if (i == 1) {
                return this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("last_modified", Sort.DESCENDING).findAll();
            }
            if (i == 2) {
                return this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
            }
            if (i != 3) {
                return i != 4 ? this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
            }
            return this.realm.where(SeriesModel.class).equalTo("is_recent", Boolean.TRUE).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
        }
        if (i == 1) {
            return this.realm.where(SeriesModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("last_modified", Sort.DESCENDING).findAll();
        }
        if (i == 2) {
            return this.realm.where(SeriesModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("rating", Sort.DESCENDING).findAll();
        }
        if (i != 3) {
            return i != 4 ? this.realm.where(SeriesModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("num").findAll() : this.realm.where(SeriesModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name", Sort.DESCENDING).findAll();
        }
        return this.realm.where(SeriesModel.class).equalTo(str2, id).contains("name", str.toLowerCase(), Case.INSENSITIVE).sort("name").findAll();
    }
}
