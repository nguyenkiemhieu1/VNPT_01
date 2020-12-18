package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListPersonPreEvent;

public class ApplicationSharedPreferences {

    private TinyDB tinyDB;
    private Context _context;
    private static final String IS_LOGINED = "IsLoginedCB";
    private static final String STORE_ACCOUNT_LOGIN = "accountCB";
    private static final String TOKEN_ACCESS = "TokenAccessCB";
    private static final String UNIT_USER = "UnitIdUser";
    private static final String DEFAULT_HOME = "DefaultHomeCB";
    private static final String TIME_GET_CONTACT = "TimeGetContactCB";
    private static final String TIME_SYNC = "TimeSyncCB";
    private static final String SEND_DOC_PERSON_PRE = "SEND_DOC_PERSON_PRE_CB";
    private static final String PREF_KEY_COOKIES = "qlvb.app.COOKIES";
    private static final String FIREBASE_TOKEN = "FIREBASE_TOKEN_CB";
    private static final String IS_CHECK_SAVE_ACCOUNT = "IsCheckSaveAccount";
    private static final String OBJECT_TOKEN_LOGIN = "OBJECT_TOKEN_LOGIN_TOKEN_CB";
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private SharedPreferences preferences;
    public ApplicationSharedPreferences(Context context) {
        this._context = context;
        tinyDB = new TinyDB(_context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogined(boolean isLogined) {
        tinyDB.putBoolean(IS_LOGINED, isLogined);
    }

    public boolean isLogined() {
        return tinyDB.getBoolean(IS_LOGINED);
    }

    public void setAccount(LoginRequest loginRequest) {
        tinyDB.putObject(STORE_ACCOUNT_LOGIN, loginRequest);
    }

    public LoginRequest getAccount() {
        return tinyDB.getObject(STORE_ACCOUNT_LOGIN, LoginRequest.class);
    }

    public void setToken(String token) {
        tinyDB.putString(TOKEN_ACCESS, token);
    }

    public String getUnitUser() {
        return tinyDB.getString(UNIT_USER);
    }

    public void setUnitUser(String unitUser) {
        tinyDB.putString(UNIT_USER, unitUser);
    }

    public String getToken() {
        return tinyDB.getString(TOKEN_ACCESS);
    }

    public void setDefaultHome(String defaultHome) {
        tinyDB.putString(DEFAULT_HOME, defaultHome);
    }

    public String getDefaultHome() {
        return tinyDB.getString(DEFAULT_HOME);
    }

    public void setTimeGetContact(String timeGetContact) {
        tinyDB.putString(TIME_GET_CONTACT, timeGetContact);
    }
    public void setSaveAccount(boolean isCheck) {
        tinyDB.putBoolean(IS_CHECK_SAVE_ACCOUNT, isCheck);
    }
    public void removeTimeGetContact() {
        tinyDB.remove(TIME_GET_CONTACT);
    }
    public boolean isSaveAccount() {
        return tinyDB.getBoolean(IS_CHECK_SAVE_ACCOUNT);
    }

    public String getTimeGetContact() {
        return tinyDB.getString(TIME_GET_CONTACT);
    }

    public void setTimeSync(String timeSync) {
        tinyDB.putString(TIME_SYNC, timeSync);
    }

    public String getTimeSync() {
        return tinyDB.getString(TIME_SYNC);
    }

    public void setPersonPre(ListPersonPreEvent listPersonPreEvent) {
        tinyDB.putObject(SEND_DOC_PERSON_PRE, listPersonPreEvent);
    }
    public void setAccountLogin(LoginInfo loginRequest) {
        tinyDB.putObject(OBJECT_TOKEN_LOGIN, loginRequest);
    }

    public void setDeviceName(String deviceName) {
        tinyDB.putObject(DEVICE_NAME, deviceName);
    }
    public String getDeviceName() {
        return tinyDB.getString(DEVICE_NAME);
    }

    public LoginInfo getAccountLogin() {
        return tinyDB.getObject(OBJECT_TOKEN_LOGIN, LoginInfo.class);
    }
    public ListPersonPreEvent getPersonPre() {
        return tinyDB.getObject(SEND_DOC_PERSON_PRE, ListPersonPreEvent.class);
    }

    public void setFirebaseToken(String firebaseToken) {
        tinyDB.putString(FIREBASE_TOKEN, firebaseToken);
    }

    public String getFirebaseToken() {
        return tinyDB.getString(FIREBASE_TOKEN);
    }

    public void removeAll() {
        tinyDB.remove(TOKEN_ACCESS);
        tinyDB.remove(IS_LOGINED);
        tinyDB.remove(TIME_GET_CONTACT);
        tinyDB.remove(TIME_SYNC);
        tinyDB.remove(IS_CHECK_SAVE_ACCOUNT );
        tinyDB.remove(OBJECT_TOKEN_LOGIN);
        tinyDB.remove(DEVICE_NAME);
    }

    public void removePersonPre() {
        tinyDB.remove(SEND_DOC_PERSON_PRE);
    }

    public HashSet<String> getHashSet() {
        return (HashSet<String>) preferences.getStringSet(PREF_KEY_COOKIES,new HashSet<String>(0));
    }

    public void putHashSet(HashSet<String> cookies) {
        preferences.edit().putStringSet(PREF_KEY_COOKIES, cookies).apply();
    }

}