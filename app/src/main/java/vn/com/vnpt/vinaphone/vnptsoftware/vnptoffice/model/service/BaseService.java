package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ErrorDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.HeaderConfiguration;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.TimeConfiguration;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.ErrorBodyContent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.ILoginService;

/**
 * Created by LinhLK - 0948012236 on 8/26/2016.
 */
public class BaseService {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder().setLenient().create();
    private static Retrofit.Builder builder = null;
    private static LoginServiceHolder myServiceHolder = new LoginServiceHolder();
    private  static SSLContext sslContext;

    public static <S> S createService(Class<S> serviceClass) {
        builder = new Retrofit.Builder().baseUrl(Application.getApp().getBaseAPIUrl()).addConverterFactory(GsonConverterFactory.create(gson));
        httpClient.connectTimeout(TimeConfiguration.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TimeConfiguration.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TimeConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(new SessionRequestInterceptor());
        httpClient.addNetworkInterceptor(new ReceivedCookiesInterceptor());
        Retrofit retrofit = builder.client(getSafeOkHttpClient()).build();
//        Retrofit retrofit = builder.client(httpClient.build()).build();
        if (ILoginService.class.isAssignableFrom(serviceClass)) {
            myServiceHolder.set((ILoginService) retrofit.create(serviceClass));
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceException(Class<S> serviceClass) {
        builder = new Retrofit.Builder().baseUrl(Application.getApp().getBaseAPIUrl()).addConverterFactory(GsonConverterFactory.create(gson));
        httpClient.connectTimeout(TimeConfiguration.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TimeConfiguration.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TimeConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(new SessionRequestInterceptor());
        httpClient.addNetworkInterceptor(new ReceivedCookiesInterceptor());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceSigning(Class<S> serviceClass) {
        builder = new Retrofit.Builder().baseUrl(Application.getApp().getBaseAPISigningUrl()).addConverterFactory(GsonConverterFactory.create(gson));
        httpClient.connectTimeout(TimeConfiguration.CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(TimeConfiguration.READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(TimeConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static SSLContext sslContextForTrustedCertificates(InputStream in) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
            if (certificates.isEmpty()) {
                throw new IllegalArgumentException("expected non-empty set of trusted certificates");
            }
            // Put the certificates a key store.
            char[] password = Application.getApp().getKeyStorePassword().toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(password);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }
            // Wrap it up in an SSL context.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return null;
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OkHttpClient getSafeOkHttpClient() {
        try {
            TokenAuthenticator authenticator = new TokenAuthenticator(myServiceHolder);
            // Install the all-trusting trust manager
            //InputStream certIS = new ByteArrayInputStream(Application.getApp().getCert().getBytes());
//            final SSLContext sslContext = sslContextForTrustedCertificates(certIS);
            final SSLContext sslContext = getSSLContext();
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient okHttpClient = new OkHttpClient();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = okHttpClient.newBuilder()
                    .addNetworkInterceptor(new SessionRequestInterceptor())
                    .addNetworkInterceptor(new ReceivedCookiesInterceptor())
                    .connectTimeout(TimeConfiguration.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TimeConfiguration.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TimeConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            Certificate[] certificates = null;
                            try {
                                certificates = session.getPeerCertificates();
                            } catch (Exception ex) {
                                return false;
                            }
                            //String host = session.getPeerHost();
                            X509Certificate x509Certificate = (X509Certificate) certificates[0];
                            String DN = x509Certificate.getSubjectDN().toString();
                            int i = DN.indexOf("CN=");
                            int j = DN.indexOf(",", DN.indexOf("CN="));
                            String cn = DN.substring(i, (j < 0) ? DN.length() : j);
                            String[] cns = cn.split("=");

                            String tmp = cns[1];
                            int k = tmp.indexOf("*");
                            if (k >= 0) {
                                tmp = cns[1].substring(++k);
                                return (hostname.endsWith(tmp)) ? true : false;
                            } else {
                                return (hostname.equals(tmp)) ? true : false;
                            }
                        }
                    })
                    .authenticator(authenticator)
                    .build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> createAuthenHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put(HeaderConfiguration.CONTENT_TYPE_HEADER, HeaderConfiguration.CONTENT_TYPE_JSON_VALUE);
        map.put(HeaderConfiguration.ACCEPT_HEADER, HeaderConfiguration.ACCEPT_JSON_VALUE);
        map.put(HeaderConfiguration.AUTHORIZATION_HEADER, Application.getApp().getAppPrefs().getToken());
        return map;
    }

    public static Map<String, String> createAuthenHeadersException() {
        Map<String, String> map = new HashMap<>();
        map.put(HeaderConfiguration.AUTHORIZATION_HEADER, HeaderConfiguration.EXCEPTION_AUTHOR);
        return map;
    }

    public static Map<String, String> createMultipartHeaders() {
        Map<String, String> map = new HashMap<>();
        //map.put(HeaderConfiguration.CONTENT_TYPE_HEADER, HeaderConfiguration.CONTENT_TYPE_MULTIPART_VALUE);
        map.put(HeaderConfiguration.AUTHORIZATION_HEADER, Application.getApp().getAppPrefs().getToken());
        return map;
    }

    public static Map<String, String> createAuthenHeader() {
        Map<String, String> map = new HashMap<>();
        map.put(HeaderConfiguration.CONTENT_TYPE_HEADER, HeaderConfiguration.CONTENT_TYPE_JSON_VALUE);
        return map;
    }

    public static <T> APIError parseErrorHandler(Response<T> response) {
        Converter<ResponseBody, ErrorBodyContent> converter = builder.client(httpClient.build()).build().responseBodyConverter(ErrorBodyContent.class, new Annotation[0]);
        ErrorBodyContent content = null;
        try {
            content = converter.convert(response.errorBody());
        } catch (Exception e) {
            return new APIError(0, ErrorDef.MESSAGE_RESPONSE);
        }
        if (content == null) {
            return new APIError(0, ErrorDef.MESSAGE_RESPONSE);
        }
        return new APIError(response.code(), content.getResponeAPI().getMessage());
    }

    private static void getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class ReceivedCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();
                for (String header : originalResponse.headers("Set-Cookie")) {
                    String[] values = header.split(";");
                    if (values.length > 0) {
                        cookies.add(values[0]);
                    }
                }
                Application.getApp().getAppPrefs().putHashSet(cookies);
            }
            return originalResponse;
        }
    }

    public static class SessionRequestInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            HashSet<String> cookies = Application.getApp().getAppPrefs().getHashSet();
            StringBuilder sb = new StringBuilder();
            for (String cookie : cookies) {
                sb.append(cookie).append("; ");
            }

            if (sb.length() > 0) {
                builder.addHeader("Cookie", sb.toString());
            }
            String tokeUser = Application.getApp().getAppPrefs().getToken();
            builder.addHeader(HeaderConfiguration.CONTENT_TYPE_HEADER, HeaderConfiguration.CONTENT_TYPE_JSON_VALUE);
            builder.addHeader(HeaderConfiguration.ACCEPT_HEADER, HeaderConfiguration.ACCEPT_JSON_VALUE);
            builder.addHeader(HeaderConfiguration.AUTHORIZATION_HEADER, tokeUser);
            Log.d("tokeUser",tokeUser);
            return chain.proceed(builder.build());
        }

    }
    public static SSLContext getSSLContext() throws Exception {
        if (sslContext == null) {
            // loading CA from an InputStream
            String certificates = ConvertUtils.readRawTextFile(Application.getApp(),R.raw.certificate);
            String certificateArray[] = certificates.split("-----BEGIN CERTIFICATE-----");

            // creating a KeyStore containing our trusted CAs
            // Put the certificates a key store.
            char[] password = Application.getApp().getKeyStorePassword().toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(password);
            for (int i = 1; i < certificateArray.length; i++) {
                certificateArray[i] = "-----BEGIN CERTIFICATE-----" + certificateArray[i];
                //LogAV.d("cert:" + certificateArray[i]);

                // generate input stream for certificate factory
                InputStream stream = new ByteArrayInputStream(certificateArray[i].getBytes());

                // CertificateFactory
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                // certificate
                Certificate ca;
                try {
                    ca = cf.generateCertificate(stream);
                    if (certificates.isEmpty()) {
                        throw new IllegalArgumentException("expected non-empty set of trusted certificates");
                    }
                } finally {
                }
                String certificateAlias = Integer.toString(i);
                keyStore.setCertificateEntry(certificateAlias, ca);
            }
            // Wrap it up in an SSL context.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Create a SSLContext with the certificate that uses tmf (TrustManager)
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        }

        return sslContext;
    }
}
