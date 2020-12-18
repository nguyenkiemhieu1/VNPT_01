package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.EditchiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ForwardChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ReplyChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.SendChiDaoV2Activity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class FileChiDaoAdapter extends ArrayAdapter<FileChiDao> {
    private Context context;
    private int resource;
    private List<FileChiDao> attachFileInfoList;
    private String type;

    public FileChiDaoAdapter(Context context, int resource, List<FileChiDao> attachFileInfoList, String type) {
        super(context, resource, attachFileInfoList);
        this.context = context;
        this.resource = resource;
        this.attachFileInfoList = attachFileInfoList;
        this.type = type;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final LinearLayout itemAttackFile = (LinearLayout) view.findViewById(R.id.itemAttackFile);
        ImageView ic_file = (ImageView) view.findViewById(R.id.ic_file);
        ImageView ic_remove = (ImageView) view.findViewById(R.id.ic_remove);
        TextView filename = (TextView) view.findViewById(R.id.filename);
        filename.setTypeface(Application.getApp().getTypeface());
        final FileChiDao attachFileInfo = attachFileInfoList.get(position);
        filename.setText(attachFileInfo.getName());
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOC) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLS) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPT) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        ic_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemAttackFile.setVisibility(View.GONE);
                if (type.equals("SEND")) {
                    ((SendChiDaoV2Activity) context).removeFile(attachFileInfo.getName());
                    itemAttackFile.removeAllViews();
                }
                if (type.equals("EDIT")) {
                    ((EditchiDaoV2Activity) context).removeFile(attachFileInfo.getName());
                    itemAttackFile.removeAllViews();
                }
//                if (type.equals("FW")) {
//                    ((ForwardChiDaoV2Activity) context).removeFile(attachFileInfo.getName());
//                    itemAttackFile.removeAllViews();
//                }
                if (type.equals("REPLY")) {
                    ((ReplyChiDaoV2Activity) context).removeFile(attachFileInfo.getName());
                    itemAttackFile.removeAllViews();
                }
            }
        });
        return view;
    }

}
