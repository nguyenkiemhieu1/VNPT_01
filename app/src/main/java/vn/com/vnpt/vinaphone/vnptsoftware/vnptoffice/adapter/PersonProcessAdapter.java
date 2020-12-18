package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.Person;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class PersonProcessAdapter extends ArrayAdapter<Person> {
    private Context context;
    private int resource;
    private List<Person> personList;
    private String type;

    public PersonProcessAdapter(Context context, int resource, List<Person> personList, String type) {
        super(context, resource, personList);
        this.context = context;
        this.resource = resource;
        this.personList = personList;
        this.type = type;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        Person person = personList.get(position);
        ImageView imgAvatar = (ImageView) view.findViewById(R.id.avatarUser);
        Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDonVi = (TextView) view.findViewById(R.id.txtDonVi);
        txtDonVi.setTypeface(Application.getApp().getTypeface());
        txtName.setTypeface(Application.getApp().getTypeface());
        txtName.setText(person.getName());
        txtDonVi.setText(person.getUnit());
        if (type.equals(StringDef.XULY)) {
            if (person.isXlc()) {
                ImageView checkXLChinh = (ImageView) view.findViewById(R.id.checkXLChinh);
                checkXLChinh.setImageResource(R.drawable.ic_circle_check);
            }
            if (person.isDxl()) {
                ImageView checkDongXL = (ImageView) view.findViewById(R.id.checkDongXL);
                checkDongXL.setImageResource(R.drawable.ic_check);
            }
        } else {
            if (person.isXlc()) {
                ImageView checkXLChinh = (ImageView) view.findViewById(R.id.checkXLChinh);
                checkXLChinh.setImageResource(R.drawable.ic_check);
            }
        }
        return view;
    }
}
