package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by LinhLK - 0948012236 on 9/20/2017.
 */
public class ExpandedMenuModel {
    @Getter @Setter private int id = -1; //id gom nhóm menu
    @Getter @Setter private int iconImg = -1;
    @Getter @Setter private String iconName = "";
    @Getter @Setter private int number = 0;
    @Getter @Setter private String kho = ""; // dùng cho các param của kho văn bản đến, văn bản đi
    @Getter @Setter private boolean isChildren = false;// menu con default là 0 có

    public ExpandedMenuModel(boolean isChildren, int id, int iconImg, String iconName, String kho) {
        this.isChildren = isChildren;
        this.id = id;
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.number = 0;
        this.kho = kho;
    }

    public ExpandedMenuModel(int iconImg, String iconName, int number, String kho) {
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.number = number;
        this.kho = kho;
    }

    public ExpandedMenuModel(int id, int iconImg, String iconName, int number, String kho) {
        this.id = id;
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.number = number;
        this.kho = kho;
    }

    public ExpandedMenuModel(int id, int iconImg, String iconName, String kho) {
        this.id = id;
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.kho = kho;
    }
}
