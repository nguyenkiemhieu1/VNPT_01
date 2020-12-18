package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class GetListStatusComboxRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<Data> data;

    public static class Data {
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("value")
        @Setter
        @Getter
        private int value;

        public Data() {
        }

        public Data(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
