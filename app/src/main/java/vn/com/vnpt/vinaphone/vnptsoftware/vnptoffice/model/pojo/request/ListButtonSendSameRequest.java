package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor(suppressConstructorProperties = true)
public class ListButtonSendSameRequest {
    @SerializedName("docId")
    @Setter
    @Getter
    private String docId;
}
