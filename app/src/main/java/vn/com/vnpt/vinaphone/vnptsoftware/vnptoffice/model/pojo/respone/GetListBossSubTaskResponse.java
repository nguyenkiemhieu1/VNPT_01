package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/12/2017.
 */

public class GetListBossSubTaskResponse extends StatusRespone {


    @SerializedName("data")
    @Setter
    @Getter
    private List<Data> data;

    public static class Data {
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("userId")
        @Setter
        @Getter
        private String userId;

        public Data(String name, String userId) {
            this.name = name;
            this.userId = userId;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
