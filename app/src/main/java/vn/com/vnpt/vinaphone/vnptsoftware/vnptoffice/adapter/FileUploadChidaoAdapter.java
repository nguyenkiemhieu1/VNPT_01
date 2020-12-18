package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.FileUploadWorkInfo;

public class FileUploadChidaoAdapter extends RecyclerView.Adapter<FileUploadChidaoAdapter.ViewHolder> {
    private Context context;
    private int resource;
    private ArrayList<FileChiDao> listFile;
    private ArrayList<String> listDeleteFile;

    public FileUploadChidaoAdapter(Context context, ArrayList<FileChiDao> listFile, ArrayList<String> listDeleteFile) {
        this.context = context;
        this.listFile = listFile;
        this.listDeleteFile = listDeleteFile;
    }


    @NonNull
    @Override
    public FileUploadChidaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_upload_work, parent, false);
        FileUploadChidaoAdapter.ViewHolder holder = new FileUploadChidaoAdapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull FileUploadChidaoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FileChiDao fileChiDao = listFile.get(position);

        holder.tv_name.setText(fileChiDao.getName());
        if (fileChiDao.isFileRoot()) {
            holder.tv_name.setTextColor(context.getColor(R.color.md_blue_grey_300));
        } else {
            holder.tv_name.setTextColor(context.getColor(R.color.md_black));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.DOC) || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.XLS) || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.PPT) || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (fileChiDao.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || fileChiDao.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            holder.image_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listFile.get(position).isSended()) {
                    listDeleteFile.add(listFile.get(position).getName());
                }
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
