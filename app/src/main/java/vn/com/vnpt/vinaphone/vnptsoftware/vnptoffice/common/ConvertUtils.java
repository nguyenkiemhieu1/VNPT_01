package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinhLK - 0948012236 on 8/25/2017.
 */

public class ConvertUtils {

    public static <T> T fromJSON(Object object, Class<T> classConvert) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            T t = gson.fromJson(jsonElement, classConvert);
            return t;
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> List<T> fromJSONList(Object object, Class<T> classConvert) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String source = gson.toJson(object);
            JsonParser parser = new JsonParser();
            JsonArray Jarray = parser.parse(source).getAsJsonArray();
            List<T> result = new ArrayList<T>();
            for (JsonElement obj : Jarray) {
                T t = gson.fromJson(obj, classConvert);
                result.add(t);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static <T> ArrayList<T> fromJSONArraylist(Object object, Class<T> classConvert) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String source = gson.toJson(object);
            JsonParser parser = new JsonParser();
            JsonArray Jarray = parser.parse(source).getAsJsonArray();
            ArrayList<T> result = new ArrayList<T>();
            for (JsonElement obj : Jarray) {
                T t = gson.fromJson(obj, classConvert);
                result.add(t);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
