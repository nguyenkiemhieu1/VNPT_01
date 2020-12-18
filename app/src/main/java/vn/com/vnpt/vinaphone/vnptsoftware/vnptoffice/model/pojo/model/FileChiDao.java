package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/28/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class FileChiDao {
    @SerializedName("name")
    @Getter
    @Setter
    private String name;
    @SerializedName("hdd")
    @Getter
    @Setter
    private String hdd;
    @SerializedName("attachmentId")
    @Getter
    @Setter
    private int attachmentId;
    @Getter
    @Setter
    private boolean fileRoot = true; //phân biệt file gốc với file up lên
    @Getter
    @Setter
    private boolean sended;// check đã gửi hay chưa gửi nếu back từ step2 về step1
    @Getter
    @Setter
    private Uri uri;
}
