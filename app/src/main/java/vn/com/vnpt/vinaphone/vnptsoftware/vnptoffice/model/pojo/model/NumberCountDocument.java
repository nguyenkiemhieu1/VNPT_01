package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 10/25/2018.
 */

public class NumberCountDocument {

    @Expose
    @Getter
    @Setter
    @SerializedName("data")
    public List<DataNumber> data;
    @Expose
    @Getter
    @Setter
    @SerializedName("status")
    public Status status;

    public  class DataNumber {
        @Expose
        @Getter
        @Setter
        @SerializedName("name")
        public String name;
        @Expose
        @Getter @Setter
        @SerializedName("number")
        public int number;


    }

    public static class Status {
        @Expose
        @Getter @Setter
        @SerializedName("message")
        public String message;
        @Expose
        @Getter @Setter
        @SerializedName("code")
        public String code;
    }
}
