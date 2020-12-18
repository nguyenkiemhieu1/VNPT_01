package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.choosePersonProcess;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class PersonProcessInfo extends choosePersonProcess {
    @SerializedName("id")
    @Setter @Getter
    private int id;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("dob")
    @Setter @Getter
    private String dob;
    @SerializedName("email")
    @Setter @Getter
    private String email;
    @SerializedName("userId")
    @Setter @Getter
    private String userId;
    @SerializedName("unitId")
    @Setter @Getter
    private int unitId;
    @SerializedName("unitCode")
    @Setter @Getter
    private String unitCode;
    @SerializedName("firstUnitName")
    @Setter @Getter
    private String firstUnitName;
    @SerializedName("secondUnitName")
    @Setter @Getter
    private String secondUnitName;
    @SerializedName("stt")
    @Setter @Getter
    private int stt;


}
