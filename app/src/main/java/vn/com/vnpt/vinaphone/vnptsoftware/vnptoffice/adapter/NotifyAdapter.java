package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.NotifyActivity;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model.Notify;

/**
 * Created by LinhLK - 0948012236 on 8/31/2017.
 */

public class NotifyAdapter extends ArrayAdapter<NotifyInfo> {
    private Context context;
    private int resource;
    private List<NotifyInfo> notifyInfoList;
    private boolean tickAll;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private UserInfoDao userInfoDao;
    private NotifyActivity notifyActivity;
    public NotifyAdapter(Context context, int resource, List<NotifyInfo> notifyInfoList, boolean tickAll) {
        super(context, resource, notifyInfoList);
        this.context = context;
        this.resource = resource;
        this.notifyInfoList = notifyInfoList;
        this.tickAll = tickAll;
        this.notifyActivity = (NotifyActivity) context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final NotifyInfo notifyInfo = notifyInfoList.get(position);
        ConstraintLayout layoutNotifyDetail =  view.findViewById(R.id.itemNotify);
        final ImageView imgAvatar = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
        if (notifyInfo.getType() != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(notifyInfo.getType())) {
            Glide.with(context).load(R.drawable.icon_schedule_notify).error(R.drawable.icon_schedule_notify)
                    .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        } else {
            Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                    .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
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
                    userInfoDao.onGetAvatarDao(callFinishedListener, notifyInfo.getCreatedUser());
                } catch (Exception ex) {

                }
            }
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDate1 = (TextView) view.findViewById(R.id.txtDate1);
        TextView txtDate2 = (TextView) view.findViewById(R.id.txtDate2);
        final CheckBox checkNotify = (CheckBox) view.findViewById(R.id.checkNotify);
        txtTitle.setTypeface(Application.getApp().getTypeface());
        txtDate1.setTypeface(Application.getApp().getTypeface());
        txtDate2.setTypeface(Application.getApp().getTypeface());
        txtTitle.setText(notifyInfo.getTitle());
        try {
            String[] timeStr = notifyInfo.getCreatedDate().split(" ");
            if (Constants.TYPE_NOTIFY_DOCUMENT.contains(notifyInfo.getType())) {
                txtDate1.setText(timeStr[0]);
                txtDate2.setText(timeStr[1]);
            }
            if (Constants.TYPE_NOTIFY_SCHEDULE.contains(notifyInfo.getType())) {
                txtDate1.setText(timeStr[1]);
                txtDate2.setText(timeStr[0]);
                txtDate1.setTextColor(context.getResources().getColor(R.color.md_red_500));
                txtDate2.setTextColor(context.getResources().getColor(R.color.md_blue_800));
            } else {
                txtDate1.setText(timeStr[0]);
                txtDate2.setText(timeStr[1]);
            }
        } catch (Exception ex) {
            txtDate1.setText("");
            txtDate2.setText("");
        }
        if (tickAll) {
            checkNotify.setChecked(true);
        } else {
            checkNotify.setChecked(false);
        }
        checkNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
                List<Notify> notifyList = notifyEvent.getNotifyList();
                if (!checkNotify.isChecked()) {
                    for (int i = 0; i < notifyList.size(); i++) {
                        if (notifyList.get(i).getId().equals(notifyInfo.getId())) {
                            notifyList.remove(i);
                            break;
                        }
                    }
                } else {
                    notifyList.add(new Notify(notifyInfo.getId()));
                }
                notifyEvent.setNotifyList(notifyList);
                EventBus.getDefault().postSticky(notifyEvent);
            }
        });
        layoutNotifyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String docID = "";
                if(notifyInfo.getLink() != null){
                    try {
                        docID = notifyInfo.getLink().split("\\|")[1];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                notifyActivity.getDetailNotify(notifyInfo.getId(), docID);
            }
        });
        return view;
    }


}
