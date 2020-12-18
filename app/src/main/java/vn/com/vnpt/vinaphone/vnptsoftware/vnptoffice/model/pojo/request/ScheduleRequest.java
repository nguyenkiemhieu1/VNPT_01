package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ScheduleRequest {
    @SerializedName("startDate") @Setter @Getter private String startDate;
    @SerializedName("endDate") @Setter @Getter private String endDate;
}
