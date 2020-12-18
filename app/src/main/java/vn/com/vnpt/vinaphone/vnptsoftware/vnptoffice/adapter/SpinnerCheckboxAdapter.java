package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChoiseGroupEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.StateVO;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class SpinnerCheckboxAdapter extends ArrayAdapter<StateVO> {
    private Context mContext;
    private ArrayList<StateVO> datalist;
    private SpinnerCheckboxAdapter myAdapter;
    private String typeSelected;

    public SpinnerCheckboxAdapter(Context context, int resource, List<StateVO> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.datalist = (ArrayList<StateVO>) objects;
        this.myAdapter = this;
    }

    public SpinnerCheckboxAdapter(Context context, int resource, List<StateVO> objects,String typeSelected) {
        super(context, resource, objects);
        this.mContext = context;
        this.datalist = (ArrayList<StateVO>) objects;
        this.myAdapter = this;
        this.typeSelected = typeSelected;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.item_spinner_checkbox, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.txtNhom);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkNhom);
            holder.mLineDiv = (View)  convertView.findViewById(R.id.lineDiv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(datalist.get(position).getTitle());
        holder.mCheckBox.setChecked(datalist.get(position).isSelected());
        holder.mCheckBox.setTag(position);
        if (position > 1) {
            convertView.setPadding(50, 0, 0, 0);
            holder.mTextView.setTypeface(Application.getApp().getTypeface(), Typeface.NORMAL);
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        if (position == 1) {
            convertView.setPadding(0, 0, 0, 0);
            holder.mTextView.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            convertView.setPadding(0, 0, 0, 0);
            holder.mTextView.setTypeface(Application.getApp().getTypeface(), Typeface.NORMAL);
            holder.mTextView.setTextColor(Application.getApp().getResources().getColor(R.color.colorHint));
            holder.mLineDiv.setVisibility(View.GONE);
            holder.mCheckBox.setVisibility(View.GONE);
        }
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                String groups = "";
                if (position == 1) {
                    if (!datalist.get(position).isSelected()) {
                        datalist.get(0).setTitle(datalist.get(position).getTitle());
                        datalist.get(1).setSelected(true);
                        for (int i = 2; i < datalist.size(); i++) {
                            groups += datalist.get(i).getId() + ",";
                            datalist.get(i).setSelected(true);
                        }
                        groups = groups.substring(0, groups.length() - 1);
                    } else {
                        datalist.get(0).setTitle(mContext.getString(R.string.DEFAULT_GROUP));
                        datalist.get(1).setSelected(false);
                        for (int i = 2; i < datalist.size(); i++) {
                            datalist.get(i).setSelected(false);
                        }
                        groups = "";
                    }
                }
                if (position > 1) {
                    String s = "";
                    boolean check = false;
                    if (!datalist.get(position).isSelected()) {
                        datalist.get(position).setSelected(true);
                        for (int i = 2; i < datalist.size(); i++) {
                            if (!datalist.get(i).isSelected()) {
                                check = true;
                            } else {
                                s += datalist.get(i).getTitle() + ", ";
                                groups += datalist.get(i).getId() + ",";
                            }
                        }
                        if (!check) {
                            datalist.get(0).setTitle(datalist.get(1).getTitle());
                            datalist.get(1).setSelected(true);
                        } else {
                            datalist.get(0).setTitle(s.substring(0, s.length() - 2));
                        }
                        groups = groups.substring(0, groups.length() - 1);
                    } else {
                        datalist.get(position).setSelected(false);
                        datalist.get(1).setSelected(false);
                        for (int i = 2; i < datalist.size(); i++) {
                            if (datalist.get(i).isSelected()) {
                                s += datalist.get(i).getTitle() + ", ";
                                groups += datalist.get(i).getId() + ",";
                                check = true;
                            }
                        }
                        if (s.length() > 0) {
                            datalist.get(0).setTitle(s.substring(0, s.length() - 2));
                        }
                        if (groups.length() > 0) {
                            groups = groups.substring(0, groups.length() - 1);
                        }
                        if (!check) {
                            datalist.get(0).setTitle(mContext.getString(R.string.DEFAULT_GROUP));
                            groups = "";
                        }
                    }
                }
                ChoiseGroupEvent choiseGroupEvent = new ChoiseGroupEvent(groups,typeSelected);
                EventBus.getDefault().postSticky(choiseGroupEvent);
                myAdapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
        private View mLineDiv;
    }

}
