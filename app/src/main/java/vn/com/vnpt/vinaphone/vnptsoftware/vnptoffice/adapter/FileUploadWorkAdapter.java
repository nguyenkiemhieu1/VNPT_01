package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileUploadWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonProcessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.FileChiDao;

public class FileUploadWorkAdapter extends RecyclerView.Adapter<FileUploadWorkAdapter.ViewHolder> {
    private Context context;
    private int resource;
    private ArrayList<FileUploadWorkInfo> listFile;

    public FileUploadWorkAdapter(Context context, ArrayList<FileUploadWorkInfo> listFile) {
        this.context = context;
        this.listFile = listFile;
    }


    @NonNull
    @Override
    public FileUploadWorkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_upload_work, parent, false);
        FileUploadWorkAdapter.ViewHolder holder = new FileUploadWorkAdapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull FileUploadWorkAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FileUploadWorkInfo fileUploadWorkInfo = listFile.get(position);

        holder.tv_name.setText(fileUploadWorkInfo.getName());
        if (fileUploadWorkInfo.isFileRoot()) {
            holder.tv_name.setTextColor(context.getColor(R.color.md_blue_grey_300));
        } else {
            holder.tv_name.setTextColor(context.getColor(R.color.md_black));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.DOC) || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.XLS) || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.PPT) || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || fileUploadWorkInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        if (fileUploadWorkInfo.isFileRoot()) {
            holder.image_delete.setVisibility(View.GONE);
        } else {
            holder.image_delete.setVisibility(View.VISIBLE);
        }
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFile.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listFile.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_file;
        private TextView tv_name;
        private ImageView image_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            image_file = itemView.findViewById(R.id.image_file);
            tv_name = itemView.findViewById(R.id.tv_name);
            image_delete = itemView.findViewById(R.id.image_delete);
        }
    }
}
