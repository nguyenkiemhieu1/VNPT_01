package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;

import io.realm.Realm;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/7/2017.
 */

public class Application extends android.app.Application {

    @Getter
    private static Application app;
    @Getter
    @Setter
    private ApplicationSharedPreferences appPrefs;
    @Getter
    private Typeface typeface;
    @Getter
    private int countFunction; // Số lượng chức năng bên menu trái
    @Getter
    private int timeSync; // Thời gian đồng bộ dữ liệu
    @Getter
    private String baseAPIUrl;
    @Getter
    private String baseAPISigningUrl;
    @Getter
    private String cert;
    @Getter
    private String keyStorePassword;
    public static Resources resources;

    public static LocaleManager localeManager;


    @Override
    protected void attachBaseContext(Context base) {
        localeManager = new LocaleManager(base);
        super.attachBaseContext(localeManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        app = this;
        resources = getResources();
        appPrefs = new ApplicationSharedPreferences(this);
        typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");
        countFunction = 15;
        timeSync = 180000;
           baseAPIUrl = "https://dev-vpdt.vnptioffice.vn/qlvb/";
//        baseAPIUrl = "https://beta-vpdt.vnptioffice.vn/qlvb_dichvu/";
        baseAPISigningUrl = "http://123.30.60.210:80";
        //cert thật
        cert = "-----BEGIN CERTIFICATE-----\n" +
                "MIIGYjCCBUqgAwIBAgIMKKJThoQ5YMM9S31iMA0GCSqGSIb3DQEBCwUAMEwxCzAJ\n" +
                "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMSIwIAYDVQQDExlB\n" +
                "bHBoYVNTTCBDQSAtIFNIQTI1NiAtIEcyMB4XDTE5MDQwODA3NDMwOVoXDTIxMDQw\n" +
                "ODA3NDMwOVowPjEhMB8GA1UECxMYRG9tYWluIENvbnRyb2wgVmFsaWRhdGVkMRkw\n" +
                "FwYDVQQDDBAqLnZucHRpb2ZmaWNlLnZuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A\n" +
                "MIIBCgKCAQEA57/c/p3BKSZIJzaU2nnJz9Tmh+Ism8VWrNdN3x+ItEJZlsfBUBbn\n" +
                "I6gfuoPokEHNTSn+Ha6b8TUM9rlCG/RXlj/8z8laqrhWLS0G3Y119igZ5DLte9P9\n" +
                "7RP5fX78VNuaoW4InprKX6XlTUzdZuZTHL++nDMQseab7LSEO2/1FE1S10Di0KmJ\n" +
                "TFsTJ0EjV+mst+WvuGHtJm4zUDoUHf4uSM7XAXIqp0VdrPPUvRl3Z7FlVBYexkmy\n" +
                "l0PU7K0iGbSYFQEf/6es2Z3nRrRAYFQNRmqLfYK0Lglza7Kx44i6YbVdGb257rpC\n" +
                "LBK+fDupUXlIs9blg2lI2PK3/WJJven/TwIDAQABo4IDUDCCA0wwDgYDVR0PAQH/\n" +
                "BAQDAgWgMIGJBggrBgEFBQcBAQR9MHswQgYIKwYBBQUHMAKGNmh0dHA6Ly9zZWN1\n" +
                "cmUyLmFscGhhc3NsLmNvbS9jYWNlcnQvZ3NhbHBoYXNoYTJnMnIxLmNydDA1Bggr\n" +
                "BgEFBQcwAYYpaHR0cDovL29jc3AyLmdsb2JhbHNpZ24uY29tL2dzYWxwaGFzaGEy\n" +
                "ZzIwVwYDVR0gBFAwTjBCBgorBgEEAaAyAQoKMDQwMgYIKwYBBQUHAgEWJmh0dHBz\n" +
                "Oi8vd3d3Lmdsb2JhbHNpZ24uY29tL3JlcG9zaXRvcnkvMAgGBmeBDAECATAJBgNV\n" +
                "HRMEAjAAMD4GA1UdHwQ3MDUwM6AxoC+GLWh0dHA6Ly9jcmwyLmFscGhhc3NsLmNv\n" +
                "bS9ncy9nc2FscGhhc2hhMmcyLmNybDArBgNVHREEJDAighAqLnZucHRpb2ZmaWNl\n" +
                "LnZugg52bnB0aW9mZmljZS52bjAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUH\n" +
                "AwIwHQYDVR0OBBYEFCmsxLDBwJbFyPyipz6cvjrdYdLNMB8GA1UdIwQYMBaAFPXN\n" +
                "1TwIUPlqTzq3l9pWg+Zp0mj3MIIBfAYKKwYBBAHWeQIEAgSCAWwEggFoAWYAdgBV\n" +
                "gdTCFpA2AUrqC5tXPFPwwOQ4eHAlCBcvo6odBxPTDAAAAWn75qekAAAEAwBHMEUC\n" +
                "IEfEtjkofTY0dgZHkNNv2Am7zefOKySh8UlnBcuKrqA3AiEAzF0rfigUuJ7fw6MW\n" +
                "UNm+BdjMncm+A67UunTA5bm/TJEAdQCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jj\n" +
                "d80OyA3cEAAAAWn75qeTAAAEAwBGMEQCIBxJFqVHOtTQCT4LulvKUmVFAugdk8Q2\n" +
                "tFQBn993ojYnAiBuOJopAppW6BbTXyuj52GrJEb3ZHeZNJSRoFLD6XErhQB1AG9T\n" +
                "dqwx8DEZ2JkApFEV/3cVHBHZAsEAKQaNsgiaN9kTAAABafvmp9UAAAQDAEYwRAIg\n" +
                "aakdXCkR7qvlL2FtKPYHJOug3YzTlZ/hZRHOKOqMLmUCIGcRZZz79oJ6KMzoYmDs\n" +
                "UA2+jWBJFQTyq48a54SDt6H+MA0GCSqGSIb3DQEBCwUAA4IBAQC/n6XoB6yIYQgw\n" +
                "XXF7bYoVnFjNvH5Xx1V9UnKim1YrQtHBymn4gi+YVjWsr8te4XVuJeiHKLst5eGZ\n" +
                "ZlF2Y1XCIsKc4aQXKEsyKjJKbPgnULm7DCtIzk6X76BBJFqHqiA/gqyVVCaSbag2\n" +
                "VHOzD5CcS5JMYTq3v8TBvtCspgQXf4f6FoZZbwwbO7p9SP9074GMN/9cPxmjXeP/\n" +
                "PT1Gr59JZEfpGm/YzlIgkaDjxYxT7PTL6v6ZxlJEVMOOqMm6w6Q5j/rxOrYWy1Of\n" +
                "1+8OG/CLjwqXk3H4te8FQdcGjTENvaME21Y5Rk8P+Oijwd2/m+JWkCrHnQXqN7IY\n" +
                "sYtUekA5\n" +
                "-----END CERTIFICATE-----\n";
        //cert test
//        cert = "-----BEGIN CERTIFICATE-----\n" +
//                "MIIFWjCCBEKgAwIBAgIQXBIVpi18vK4o8rdz1T5mXjANBgkqhkiG9w0BAQsFADCB\n" +
//                "kDELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
//                "A1UEBxMHU2FsZm9yZDEaMBgGA1UEChMRQ09NT0RPIENBIExpbWl0ZWQxNjA0BgNV\n" +
//                "BAMTLUNPTU9ETyBSU0EgRG9tYWluIFZhbGlkYXRpb24gU2VjdXJlIFNlcnZlciBD\n" +
//                "QTAeFw0xODAzMTQwMDAwMDBaFw0yMDAzMTMyMzU5NTlaMF4xITAfBgNVBAsTGERv\n" +
//                "bWFpbiBDb250cm9sIFZhbGlkYXRlZDEdMBsGA1UECxMUUG9zaXRpdmVTU0wgV2ls\n" +
//                "ZGNhcmQxGjAYBgNVBAMMESoudm5wdHNvZnR3YXJlLnZuMIIBIjANBgkqhkiG9w0B\n" +
//                "AQEFAAOCAQ8AMIIBCgKCAQEAoVXCRhXCqZurEXwHgUfW/3tax+R/TwbCorUQcVkh\n" +
//                "5Muvj/c3+XKehAduJ/0d0i4gRa64yyBxSzsV8+XqRbAW1/FPAQ82jPIEibDObZx1\n" +
//                "givlQlU8hqlt3B0hyd0XFsWiNbjCBH4G8kvWOlv+uJFMM8Js1jJRRYY4guuou5IH\n" +
//                "0nsH2Dm7DSfVSWR0O/H4fharcRPbNlfABCL1MgqsOtXEOHdAXUCU7NDRqOe0kETw\n" +
//                "crSXXpzJFXdb0KkromIVrqQHKYVMB05VMqRBWE0lf8ZWUBUccWPhWEuqKObRlNdX\n" +
//                "TXJPp4xp4R9y38mFXK+u8rGwFhshfH7xC+N6UDiLBTlVfQIDAQABo4IB3zCCAdsw\n" +
//                "HwYDVR0jBBgwFoAUkK9qOpRaC9iQ6hJWc99DtDoo2ucwHQYDVR0OBBYEFAX3yLmS\n" +
//                "tJG/q8qmKgQVDG8mYjRrMA4GA1UdDwEB/wQEAwIFoDAMBgNVHRMBAf8EAjAAMB0G\n" +
//                "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjBPBgNVHSAESDBGMDoGCysGAQQB\n" +
//                "sjEBAgIHMCswKQYIKwYBBQUHAgEWHWh0dHBzOi8vc2VjdXJlLmNvbW9kby5jb20v\n" +
//                "Q1BTMAgGBmeBDAECATBUBgNVHR8ETTBLMEmgR6BFhkNodHRwOi8vY3JsLmNvbW9k\n" +
//                "b2NhLmNvbS9DT01PRE9SU0FEb21haW5WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0Eu\n" +
//                "Y3JsMIGFBggrBgEFBQcBAQR5MHcwTwYIKwYBBQUHMAKGQ2h0dHA6Ly9jcnQuY29t\n" +
//                "b2RvY2EuY29tL0NPTU9ET1JTQURvbWFpblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJD\n" +
//                "QS5jcnQwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmNvbW9kb2NhLmNvbTAtBgNV\n" +
//                "HREEJjAkghEqLnZucHRzb2Z0d2FyZS52boIPdm5wdHNvZnR3YXJlLnZuMA0GCSqG\n" +
//                "SIb3DQEBCwUAA4IBAQBj4XQmxq7XpMzb1C3DK2MFpHgoiMJZTBGY3kUxfRYKmPRr\n" +
//                "Qkztjag6sinOMgZ+j74Vlh9p4o5nRDyN8/vAiBKihRjeg+UX8X4/sEglDFPa3vQ+\n" +
//                "9SDYI2pC6qsjpnBL1M+996j0lnuLPxCOEbLBx3gep+onFPqZLqTumrcne8HGDlUM\n" +
//                "WwlP2NHx2ZoT4BPKlQlgZxa2hQ9C/u0ZhZAXYC3ODnQoqK04/uf52HP9kCTa9ucu\n" +
//                "VF4GfSOrHnbbM40ug29gJDb4lxXoOo5KsCwIw1W1+xQDU3JekU3uiNoc9buBKbBR\n" +
//                "c9c9YJHxWDJs90ghqyI8AJgzucLWgd/sIkYCmvto\n" +
//                "-----END CERTIFICATE-----";
        keyStorePassword = "qlvbdhcaobangpassword";
        Realm.init(this);
    }


}