package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/7/2017.
 */

public class LoginInfo {
    @SerializedName("username")
    @Setter
    @Getter
    private String username;
    @SerializedName("password")
    @Setter
    @Getter
    private String password;
    @SerializedName("agent")
    @Setter
    @Getter
    private String agent;
    @SerializedName("agentInfo")
    @Setter
    @Getter
    private String agentInfo;
    @SerializedName("fullName")
    @Setter
    @Getter
    private String fullName;
    @SerializedName("avatar")
    @Setter
    @Getter
    private String avatar;
    @SerializedName("unitName")
    @Setter
    @Getter
    private String unitName;
    @SerializedName("unitId")
    @Setter
    @Getter
    private String unitId;
    @SerializedName("token")
    @Setter
    @Getter
    private String token;
    @SerializedName("kho")
    @Setter
    @Getter
    private List<String> khoVanBan;

    @SerializedName("multilPrimary")
    @Setter
    @Getter
    private String multilPrimary;
    @SerializedName("switchUser")
    @Setter
    @Getter
    private List<UserSwitch> listSwitchUser;
    @Setter
    @Getter
    private boolean commentFinish;
    @Setter
    @Getter
    private boolean briefDisplay;

    public class UserSwitch {
        @SerializedName("userid")
        @Setter
        @Getter
        private String userid;
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
    }

    @SerializedName("menu")
    @Setter
    @Getter
    private List<Menu> menu;
    @SerializedName("roles")
    @Setter
    @Getter
    private List<String> roles;
    @SerializedName("configs")
    @Setter
    @Getter
    private Configs configs;

    public class Menu {
        @SerializedName("name")
        @Setter
        @Getter
        private String name;
        @SerializedName("child")
        @Setter
        @Getter
        private List<Child> child;

        public class Child {
            @SerializedName("name")
            @Setter
            @Getter
            private String name;
            @SerializedName("param")
            @Setter
            @Getter
            private String param;
            @SerializedName("btnChuyenDongThoi")
            @Setter
            @Getter
            private String btnChuyenDongThoi;
            @SerializedName("btnKetThucDongThoi")
            @Setter
            @Getter
            private String btnKetThucDongThoi;
        }
    }

    public class Configs {
        @SerializedName("LICH_TUAN_MOBILE_DISPLAY")
        @Setter
        @Getter
        private String LICH_TUAN_MOBILE_DISPLAY;
        @SerializedName("LICH_DON_VI_MOBILE_DISPLAY")
        @Setter
        @Getter
        private String LICH_DON_VI_MOBILE_DISPLAY;
    }

    @SerializedName("calendarDisplay")
    @Setter
    @Getter
    private String calendarDisplay;
}
