package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class GetListSubTaskResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<Data> data;

    public static class Data {
        @SerializedName("status")
        @Setter
        @Getter
        private String status;
        @SerializedName("content")
        @Setter
        @Getter
        private String content;
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
    }

}
