package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.chidao.ChiDaoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

public class PersonReceiveChiDaoV2Adapter extends ArrayAdapter<PersonReceiveChiDao> {
    private Context context;
    private int resource;
    private List<PersonReceiveChiDao> personList;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private ChiDaoDao chiDaoDao;
    private String thongTinId;
    OnItemOperatingClickListener onItemClickListener;

    public PersonReceiveChiDaoV2Adapter(Context context, int resource, List<PersonReceiveChiDao> personList, String thongTinId) {
        super(context, resource, personList);
        this.context = context;
        this.resource = resource;
        this.personList = personList;
        this.thongTinId = thongTinId;
        onItemClickListener = (OnItemOperatingClickListener) context;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resource, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final PersonReceiveChiDao person = personList.get(position);
        Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(context)).into(viewHolder.imgAvatar);
        viewHolder.txtDonVi.setTypeface(Application.getApp().getTypeface());
        viewHolder.txtName.setTypeface(Application.getApp().getTypeface());
        viewHolder.txtStatus.setTypeface(Application.getApp().getTypeface());
        viewHolder.txtName.setText(person.getFullName());
        viewHolder.txtDonVi.setText(person.getUnitName());
        if (person.getNgayNhan() == null) {
            viewHolder.txtStatus.setText(context.getResources().getString(R.string.tv_not_send));
            viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.md_red_500));
            viewHolder.btnRemove.setAlpha(1f);
            viewHolder.btnSend.setAlpha(1f);
            viewHolder.btnRemove.setEnabled(true);
            viewHolder.btnSend.setEnabled(true);
        } else {
            viewHolder.txtStatus.setText(context.getResources().getString(R.string.tv_sent));
            viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.btnRemove.setAlpha(0.5f);
            viewHolder.btnSend.setAlpha(0.5f);
            viewHolder.btnRemove.setEnabled(false);
            viewHolder.btnSend.setEnabled(false);
        }
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.removeOnclickListener(person);
            }
        });
        viewHolder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.sendOnclickListener(person);
            }
        });
        return convertView;
    }

    public interface OnItemOperatingClickListener {
        void removeOnclickListener(PersonReceiveChiDao personReceiveChiDao);

        void sendOnclickListener(PersonReceiveChiDao personReceiveChiDao);
    }

    public void updateDataSetChanged(List<PersonReceiveChiDao> personList) {
        this.personList = new ArrayList<>(personList);
        notifyDataSetChanged();

    }
    static class ViewHolder {
        ImageView imgAvatar;
        TextView txtName;
        TextView txtDonVi;
        TextView txtStatus;
        TextView btnRemove;
        ImageView btnSend;
        LinearLayout layoutReceive;

        public ViewHolder(View viewItem) {
            imgAvatar = (ImageView) viewItem.findViewById(R.id.avatarUser);
            txtName = (TextView) viewItem.findViewById(R.id.txtName);
            txtDonVi = (TextView) viewItem.findViewById(R.id.txtDonVi);
            txtStatus = (TextView) viewItem.findViewById(R.id.txtStatus);
            btnRemove = (TextView) viewItem.findViewById(R.id.btnRemove);
            btnSend = (ImageView) viewItem.findViewById(R.id.btnSend);
            layoutReceive = (LinearLayout) viewItem.findViewById(R.id.layoutReceive);
        }
    }
}
