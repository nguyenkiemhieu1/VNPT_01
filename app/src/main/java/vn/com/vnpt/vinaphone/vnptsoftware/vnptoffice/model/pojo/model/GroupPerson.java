package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * An event model that was built for automatic serialization from json to object.
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
public class GroupPerson {
    @Expose
    @SerializedName("id")
    @Getter @Setter
    private String id;
    @Expose
    @SerializedName("name")
    @Getter @Setter
    private String name;
}
