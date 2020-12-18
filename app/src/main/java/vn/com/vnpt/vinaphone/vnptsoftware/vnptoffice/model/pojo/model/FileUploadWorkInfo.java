package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class FileUploadWorkInfo {
    @SerializedName("name")
    @Setter
    @Getter
    private String name;
    @SerializedName("uri")
    @Setter
    @Getter
    private Uri uri;
    @SerializedName("fileRoot")
    @Setter
    @Getter
    private boolean fileRoot;// file gốc không cho xóa hay thêm lên server. chỉ có mục đích hiển thị file gốc của văn bản.
}
