package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.SpinnerScheduleDepart;

public class SpinnerScheduleDepartAdapter extends ArrayAdapter<SpinnerScheduleDepart> {
    private Context mContext;
    private ArrayList<SpinnerScheduleDepart> datalist;
    SpinnerScheduleDepartAdapter myAdapter;

    public SpinnerScheduleDepartAdapter(Context context, int resource, List<SpinnerScheduleDepart> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.datalist = (ArrayList<SpinnerScheduleDepart>) objects;
        this.myAdapter = this;
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
        final SpinnerScheduleDepartAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.item_spinner_schedule_depart, null);
            holder = new SpinnerScheduleDepartAdapter.ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.txtNhom);
            holder.mLineDiv = (View) convertView.findViewById(R.id.lineDiv);
            convertView.setTag(holder);
        } else {
            holder = (SpinnerScheduleDepartAdapter.ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(datalist.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private View mLineDiv;
    }
}
