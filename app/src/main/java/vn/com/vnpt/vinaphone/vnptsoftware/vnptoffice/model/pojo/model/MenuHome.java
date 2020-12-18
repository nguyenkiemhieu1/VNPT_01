package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor(suppressConstructorProperties = true)
public class MenuHome {
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private Drawable drawableBackdround;
    @Getter
    @Setter
    private Drawable drawableImgae;


}
