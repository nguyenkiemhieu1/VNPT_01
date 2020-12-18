package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class FileChiDaoRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private List<FileChiDao> data;
}
