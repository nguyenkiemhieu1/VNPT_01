package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public class PersonInGroupChiDaoRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private List<Object> data;
}
