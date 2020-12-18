package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConfigNotification;


/**
 * Created by LinhLK - 0948012236 on 9/29/2016.
 */

public class VNPTiOfficeFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "TagFirebaseIDService";
    private ApplicationSharedPreferences appPrefs;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token ID: " + refreshedToken);
        appPrefs = Application.getApp().getAppPrefs();
        storeRegIdInPref(refreshedToken);
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(ConfigNotification.REGISTRATION_COMPLETE);
        registrationComplete.putExtra(StringDef.TOKEN_DEVICE_ID, refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void storeRegIdInPref(String token) {
        appPrefs.setFirebaseToken(token);
    }

}
