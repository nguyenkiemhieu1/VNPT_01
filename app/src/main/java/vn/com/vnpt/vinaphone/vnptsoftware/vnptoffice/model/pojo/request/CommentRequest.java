package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class CommentRequest {
    @Setter @Getter
    @SerializedName("docId")
    private int id;
    @Setter @Getter
    @SerializedName("comment")
    private String comment;

}
