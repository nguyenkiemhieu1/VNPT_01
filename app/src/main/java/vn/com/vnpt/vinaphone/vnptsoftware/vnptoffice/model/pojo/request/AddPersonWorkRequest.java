package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
public class AddPersonWorkRequest {

    @SerializedName("units")
    @Setter
    @Getter
    private List<ObjectAssignRequest> units;
    @SerializedName("users")
    @Setter
    @Getter
    private List<ObjectAssignRequest> users;
    @SerializedName("id")
    @Setter
    @Getter
    private String id;


}
