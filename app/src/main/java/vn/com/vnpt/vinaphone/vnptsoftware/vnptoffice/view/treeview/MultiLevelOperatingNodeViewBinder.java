package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableOperatingNodeViewBinder;
import me.texy.treeview.base.CheckableOperatingV2NodeViewBinder;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonChiDao;

public class MultiLevelOperatingNodeViewBinder extends CheckableOperatingV2NodeViewBinder {

    TextView textViewName;
    TextView textViewOffice;
    TextView textViewEmail;
    ImageView imageView;
    ImageView imageViewAvatar;
    AppCompatCheckBox checkBoxPerson;
    int ResourceLayout;

    public MultiLevelOperatingNodeViewBinder(View itemView, int resourceLayout) {
        super(itemView);
        this.ResourceLayout = resourceLayout;
        imageViewAvatar = (ImageView) itemView.findViewById(R.id.node_image_view);
        textViewName = (TextView) itemView.findViewById(R.id.node_name_view);
        textViewOffice = (TextView) itemView.findViewById(R.id.node_office_view);
        textViewEmail = (TextView) itemView.findViewById(R.id.node_email_view);

        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
        checkBoxPerson = (AppCompatCheckBox) itemView.findViewById(R.id.checkBoxPerson);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBoxPerson;
    }


    @Override
    public int getLayoutId() {
        return ResourceLayout;
    }

    @Override
    public void bindView(final TreeNode treeNode, Context context) {
        if (!treeNode.hasChild()) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            if (treeNode.isExpanded()) {
                imageView.setImageResource(R.drawable.ic_minus);
            } else {
                imageView.setImageResource(R.drawable.ic_add);
            }
        }
        textViewName.setTypeface(Application.getApp().getTypeface());
        textViewOffice.setTypeface(Application.getApp().getTypeface());
        textViewEmail.setTypeface(Application.getApp().getTypeface());
        textViewName.setText(((PersonChiDao) treeNode.getValue()).getFullName());
        if (((PersonChiDao) treeNode.getValue()).getEmail() != null && !((PersonChiDao) treeNode.getValue()).getEmail().isEmpty()) {
            if (((PersonChiDao) treeNode.getValue()).getEmail().equals("UNIT")) {
                imageViewAvatar.setVisibility(View.GONE);
                textViewOffice.setVisibility(View.GONE);
                textViewEmail.setVisibility(View.GONE);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            } else {
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                imageViewAvatar.setVisibility(View.VISIBLE);
                Glide.with(context.getApplicationContext()).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                        .bitmapTransform(new CircleTransform(context)).into(imageViewAvatar);
                if (((PersonChiDao) treeNode.getValue()).getChucVu() != null &&
                        !((PersonChiDao) treeNode.getValue()).getChucVu().isEmpty()) {
                    textViewOffice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    textViewOffice.setVisibility(View.VISIBLE);
                    textViewOffice.setText(((PersonChiDao) treeNode.getValue()).getChucVu());
                } else {
                    textViewOffice.setVisibility(View.GONE);
                }
                textViewEmail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                textViewEmail.setVisibility(View.VISIBLE);
                textViewEmail.setText(((PersonChiDao) treeNode.getValue()).getEmail());
            }
        } else {
            imageViewAvatar.setVisibility(View.GONE);
            textViewOffice.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (treeNode.hasChild()) {
            if (expand) {
                imageView.setImageResource(R.drawable.ic_minus);
            } else {
                imageView.setImageResource(R.drawable.ic_add);
            }
        }
    }
}
