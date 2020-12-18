package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.Date;
import java.util.Map;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by LinhLK - 0948012236 on 8/28/2017.
 */

public class RealmDao {
    private static RealmDao instance;
    private final Realm realm;

    public RealmDao(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmDao with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmDao(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmDao with(Activity activity) {
        if (instance == null) {
            instance = new RealmDao(activity.getApplication());
        }
        return instance;
    }

    public static RealmDao with(Application application) {
        if (instance == null) {
            instance = new RealmDao(application);
        }
        return instance;
    }

    public static RealmDao getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    public long getUniqueId(Class className) {
        Number number = realm.where(className).max("uid");
        if (number == null) {
            return 1;
        } else {
            return (long) number + 1;
        }
    }

    public <T extends RealmObject> RealmResults<T> findAll(Class<T> className) {
        RealmResults<T> results = realm.where(className).findAll();
        return results;
    }

    public <T extends RealmObject> RealmResults<T> findFilterAnd(Class<T> className, Map<String, String> mapFileds) {
        RealmQuery<T> realmQuery = realm.where(className);
        for (Map.Entry<String, String> entry : mapFileds.entrySet()) {
            realmQuery = realmQuery.equalTo(entry.getKey(), entry.getValue());
        }
        return realmQuery.findAll();
    }

    public <T extends RealmObject> RealmResults<T> findByDate(Class<T> className, String keyFromDate, String keyToDate, Date fromDate, Date toDate) {
        RealmQuery<T> realmQuery = realm.where(className).greaterThanOrEqualTo(keyToDate, fromDate).or().lessThanOrEqualTo(keyFromDate, toDate);
        return realmQuery.findAll();
    }

    public <T extends RealmObject> RealmResults<T> findFilterOr(Class<T> className, Map<String, String> mapFileds) {
        RealmQuery<T> realmQuery = realm.where(className);
        int index = 1;
        for (Map.Entry<String, String> entry : mapFileds.entrySet()) {
            if (index == mapFileds.size()) {
                realmQuery = realmQuery.contains(entry.getKey(), entry.getValue(), Case.INSENSITIVE);
            } else {
                realmQuery = realmQuery.contains(entry.getKey(), entry.getValue(), Case.INSENSITIVE).or();
            }
        }
        return realmQuery.findAll();
    }

    public <T extends RealmObject> T findByKey(Class<T> className, String value, String key) {
        return realm.where(className).equalTo(key, value).findFirst();
    }

    public <T extends RealmObject> long countFilterAnd(Class<T> className, Map<String, String> mapFileds) {
        RealmQuery<T> realmQuery = realm.where(className);
        for (Map.Entry<String, String> entry : mapFileds.entrySet()) {
            realmQuery = realmQuery.equalTo(entry.getKey(), entry.getValue());
        }
        return realmQuery.count();
    }

}
