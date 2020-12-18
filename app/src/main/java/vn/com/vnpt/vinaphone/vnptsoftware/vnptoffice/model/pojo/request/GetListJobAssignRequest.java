package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 4/18/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class GetListJobAssignRequest {
    @SerializedName("pageNo")
    @Setter @Getter
    private int pageNo;
    @SerializedName("pageRec")
    @Setter @Getter
    private int pageRec;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("status")
    @Setter
    @Getter
    private String status;
//    -1 :tất cả , 1- công việc đơn vi, 0 – công việc cá nhân
    @SerializedName("coquan")
    @Setter
    @Getter
    private Integer coquan;
}
