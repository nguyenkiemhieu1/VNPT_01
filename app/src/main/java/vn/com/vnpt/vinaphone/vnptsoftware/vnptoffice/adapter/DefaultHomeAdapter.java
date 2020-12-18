package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.DefaultHome;

/**
 * Created by LinhLK - 0948012236 on 4/27/2017.
 */

public class DefaultHomeAdapter extends ArrayAdapter<String> {

    Integer row_index = -1;

    public DefaultHomeAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    static class ViewHolder {
        public TextView tvName;
        public LinearLayout lo_img_checked;
        public ImageView img_isclick;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_default_home, parent, false);
        } else {
            view = convertView;
        }
        String nameRow = getDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername());
        if (nameRow != null) {
            switch (nameRow) {
                case Constants.HOME_TRANGCHU:
                    row_index = 0;
                    break;
                case Constants.HOME_DEN_CHO_XU_LY:
                    row_index = 1;
                    break;
                case Constants.HOME_DEN_DA_XU_LY:
                    row_index = 2;
                    break;
                case Constants.HOME_DI_CHO_XU_LY:
                    row_index = 3;
                    break;
                case Constants.HOME_DI_DA_PHAT_HANH:
                    row_index = 4;
                    break;
                case Constants.HOME_DI_DA_XU_LY:
                    row_index = 5;
                    break;
                case Constants.HOME_VANBAN_XEMDEBIET:
                    row_index = 6;
                    break;
                case Constants.HOME_VANBANDANHDAU:
                    row_index = 7;
                    break;
                case Constants.HOME_DANHBA:
                    row_index = 8;
                    break;
                case Constants.HOME_QUANLYLICHHOP:
                    row_index = 9;
                    break;

            }
        } else {
            row_index = 0;
        }
        final DefaultHomeAdapter.ViewHolder holder = new DefaultHomeAdapter.ViewHolder();
        holder.tvName = (TextView) view.findViewById(R.id.tv_name);
        holder.img_isclick = (ImageView) view.findViewById(R.id.img_isclick);
        holder.lo_img_checked = (LinearLayout) view.findViewById(R.id.lo_img_checked);
        holder.tvName.setTypeface(Application.getApp().getTypeface());
        holder.tvName.setText(getItem(position));
        holder.lo_img_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                switch (position) {
                    case 0:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_TRANGCHU);
                        break;
                    case 1:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DEN_CHO_XU_LY);
                        break;
                    case 2:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DEN_DA_XU_LY);
                        break;
                    case 3:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DI_CHO_XU_LY);
                        break;
                    case 4:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DI_DA_PHAT_HANH);
                        break;
                    case 5:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DI_DA_XU_LY);
                        break;
                    case 6:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_VANBAN_XEMDEBIET);
                        break;
                    case 7:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_VANBANDANHDAU);
                        break;
                    case 8:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_DANHBA);
                        break;
                    case 9:
                        saveDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername(), Constants.HOME_QUANLYLICHHOP);
                        break;
                }
                notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            holder.img_isclick.setImageResource(R.drawable.ic_circle_check);
        } else {
            holder.img_isclick.setImageResource(R.drawable.ic_circle_uncheck);
        }
        return view;
    }

    private void saveDefaultHome(String userId, String defaultName) {
        Realm realm = RealmDao.with(Application.getApp()).getRealm();
        realm.beginTransaction();
        if (getDefaultHome(userId) == null) {
            realm.copyToRealm(new DefaultHome(userId, defaultName));
        } else {
            realm.copyToRealmOrUpdate(new DefaultHome(userId, defaultName));
        }
        realm.commitTransaction();
    }

    private String getDefaultHome(String userId) {
        DefaultHome results = RealmDao.with(Application.getApp()).findByKey(DefaultHome.class, userId, "userId");
        if (results != null && results.getName() != null && !results.getName().equals("")) {
            return results.getName();
        } else {
            return null;
        }
    }

}