package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public  class GetListJobHandLingResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<Data> data;

    public class Data {
        @SerializedName("endDate")
        @Setter
        @Getter
        private String endDate;
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("files")
        @Setter
        @Getter
        private List<AttachFileInfo> files;
        @SerializedName("content")
        @Setter
        @Getter
        private String content;
        @SerializedName("startDate")
        @Setter
        @Getter
        private String startDate;
        @SerializedName("status")
        @Setter
        @Getter
        private String status;
    }

}
