package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class LogInfoAdapter extends ArrayAdapter<LogInfo> {
    private Context context;
    private int resource;
    private List<LogInfo> logInfoList;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private UserInfoDao userInfoDao;

    public LogInfoAdapter(Context context, int resource, List<LogInfo> logInfoList) {
        super(context, resource, logInfoList);
        this.context = context;
        this.resource = resource;
        this.logInfoList = logInfoList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        LogInfo logInfo = logInfoList.get(position);
        final ImageView imgAvatar = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
        Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
        TextView txtTime = (TextView) view.findViewById(R.id.txtTime);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDate.setTypeface(Application.getApp().getTypeface());
        txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtTime.setTypeface(Application.getApp().getTypeface());
        txtTitle.setTypeface(Application.getApp().getTypeface());
        txtName.setText(logInfo.getFullName());
        if (logInfo.getUpdateDate() != null && !logInfo.getUpdateDate().trim().equals("") ) {
            txtDate.setText(logInfo.getUpdateDate().split(" ")[0]);
            txtTime.setText("     " + logInfo.getUpdateDate().split(" ")[1]);
        }
        txtTitle.setText(logInfo.getComment());
        connectionDetector = new ConnectionDetector(context);
        if (connectionDetector.isConnectingToInternet()) {
            callFinishedListener = new ICallFinishedListener() {
                @Override
                public void onCallSuccess(Object object) {
                    try {
                        ResponseBody responseBody = (ResponseBody) object;
                        Glide.with(context).load(responseBody.bytes()).error(R.drawable.ic_avatar)
                                .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
                    } catch (Exception ex) {

                    }
                }
                @Override
                public void onCallError(Object object) {
                }
            };
            try {
                userInfoDao = new UserInfoDao();
                userInfoDao.onGetAvatarDao(callFinishedListener, logInfo.getUpdateBy().substring(1, logInfo.getUpdateBy().length() - 1));
            } catch (Exception ex) {

            }
        }
        return view;
    }
}
