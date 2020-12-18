package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public  class ListFileCongViecResponse extends StatusRespone {

    @SerializedName("data")
    @Setter
    @Getter
    private List<FilesObj> data;

    public static class FilesObj {
        @Expose
        @SerializedName("id")
        @Getter
        @Setter
        private String id;
        @Expose
        @Getter
        @Setter
        @SerializedName("name")
        private String name;
    }


}
