package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class FileCongViec extends StatusRespone {

    @SerializedName("data")
    @Setter @Getter
    private List<FilesInfo> files;

    public class FilesInfo{
        @SerializedName("name")
        @Setter @Getter
        private String name;
        @SerializedName("id")
        @Setter @Getter
        private String id;
    }
}
