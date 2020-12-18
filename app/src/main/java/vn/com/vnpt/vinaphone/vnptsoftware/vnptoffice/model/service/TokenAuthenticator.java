package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.HeaderConfiguration;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url.ServiceUrl;

public class TokenAuthenticator implements Authenticator {
    private LoginServiceHolder myServiceHolder;

    public TokenAuthenticator(LoginServiceHolder myServiceHolder) {
        this.myServiceHolder = myServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (myServiceHolder == null) {
            return null;
        }

        try {
            ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
            String jsonResponse = responseBodyCopy.string();
            JSONObject objReponse = new JSONObject(jsonResponse);
            JSONObject objStatus = objReponse.optJSONObject("status");
            if (objStatus != null && objStatus.optString("code") != null
                    && objStatus.optString("code").equalsIgnoreCase("error_authentication_required")) {
                retrofit2.Response retrofitResponse = myServiceHolder.get()
                        .getUserLogin(Application.getApp().getAppPrefs().getAccount()).execute();

                if (retrofitResponse != null) {
                    String newAccessToken = "";
                    LoginRespone refreshTokenResponse = (LoginRespone) retrofitResponse.body();
                    if (refreshTokenResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                        LoginInfo loginInfo = ConvertUtils.fromJSON(refreshTokenResponse.getData(), LoginInfo.class);
                        newAccessToken = loginInfo.getToken();
                        Application.getApp().getAppPrefs().setToken(newAccessToken);
                    }


                    return response.request().newBuilder()
                            .header(HeaderConfiguration.AUTHORIZATION_HEADER, newAccessToken)
                            .build();
                }

            }
            else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        try {
//            boolean refreshResult = refreshToken(Application.getApp().getBaseAPIUrl(), Application.getApp().getAppPrefs().getAccount().getUsername()
//                    , Application.getApp().getAppPrefs().getAccount().getPassword(), Application.getApp().getAppPrefs().getAccount().getTokenFireBase());
//            if (refreshResult) {
//                String accessToken = Application.getApp().getAppPrefs().getToken();
//                return response.request().newBuilder()
//                        .header(HeaderConfiguration.AUTHORIZATION_HEADER, accessToken)
//                        .build();
//            } else {
//                //Khi refresh token failed ban co the thuc hien action refresh lan tiep theo
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    public boolean refreshToken(String url, String username, String password, String tokenFireBase) throws IOException {
        try {
            URL refreshUrl = new URL(url + ServiceUrl.LOGIN_V2_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setUseCaches(false);
            String urlParameters = "username=" + username + "&password=" + password + "&tokenFireBase=" + tokenFireBase;
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("username", username);
            jsonParam.put("password", password);
            jsonParam.put("tokenFireBase", tokenFireBase);
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(jsonParam.toString());
            wr.flush();
            wr.close();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // this gson part is optional , you can read response directly from Json too
                Gson gson = new Gson();
                LoginRespone refreshTokenResult = gson.fromJson(response.toString(), LoginRespone.class);
                if (refreshTokenResult.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                    LoginInfo loginInfo = ConvertUtils.fromJSON(refreshTokenResult.getData(), LoginInfo.class);
                    String newAccessToken = loginInfo.getToken();
                    Application.getApp().getAppPrefs().setToken(newAccessToken);
                }
                // handle new token ...
                // save it to the sharedpreferences, storage bla bla ...
                return true;

            } else {
                //cannot refresh
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }
}
