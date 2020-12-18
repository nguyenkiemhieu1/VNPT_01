package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alamkanak.weekview.WeekViewEvent;
import com.jaredrummler.android.processes.AndroidProcesses;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConfigNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.notify.NotifyDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DetailNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailChiDaoActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentNotificationActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentProcessedActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.DetailWorkManagementActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.LetterActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ProfileWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NhanWebViewEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.WorkManageEvent;

public class HandleNotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "TagFirebaseMessService";
    private ICallFinishedListener callFinishedListener;
    private NotifyDao notifyDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("TITLE");
        String message = intent.getStringExtra("MESSAGE");
        String json = intent.getStringExtra("JSON");
        try {
            JSONObject jsonObject = new JSONObject(json);
            handleNotification(context, title, message, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void handleNotification(final Context context, final String title, final String message, final JSONObject json) {
        try {
            final String notificationId = json.getString(ConfigNotification.NOTIFICATION_IOFFICE_ID);
            notifyDao = new NotifyDao();
            int notificationID = 0;
            try {
                notificationID = Integer.parseInt(notificationId);
            } catch (Exception ex) {
                Log.e(TAG, "Parse iOffice ID: " + ex.getMessage());
            }

            //goi API
            callFinishedListener = new ICallFinishedListener() {
                @Override
                public void onCallSuccess(Object object) {

                    if (object instanceof DetailNotifyRespone) {
                        DetailNotifyRespone DetailNotifyRespone = (DetailNotifyRespone) object;
                        if (DetailNotifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                            try {
                                DetailNotifyInfo detailNotifyInfo = ConvertUtils.fromJSON(DetailNotifyRespone.getData(), DetailNotifyInfo.class);
                                JSONObject jsonData = new JSONObject(json.toString());
                                String link = jsonData.getString("link");
                                String idDoc = link.split("\\|")[1];
                                Intent intent = null;
                                switch (detailNotifyInfo.getType()) {
                                    case "CHOXULY":
                                        DocumentWaitingInfo itemWaiting = new DocumentWaitingInfo();
                                        itemWaiting.setId(idDoc);
                                        itemWaiting.setIsChuTri(detailNotifyInfo.getParamDetailNotifyInfo().getIsChuTri());
                                        itemWaiting.setIsCheck(detailNotifyInfo.getParamDetailNotifyInfo().getIsCheck());
                                        itemWaiting.setProcessDefinitionId(detailNotifyInfo.getParamDetailNotifyInfo().getProcessKey());
                                        itemWaiting.setCongVanDenDi(detailNotifyInfo.getParamDetailNotifyInfo().getCongVanDenDi());

//                                        EventBus.getDefault().postSticky(itemWaiting);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                                        intent = new Intent(context, DetailDocumentWaitingActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_WATTING_INFOR, itemWaiting);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC, new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR, new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));

                                        // xu ly khac
                                        EventBus.getDefault().postSticky(itemWaiting);
                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                                        break;
                                    case "TRACUU":
                                        DocumentSearchInfo item = new DocumentSearchInfo();
                                        item.setId(idDoc);
//                                        EventBus.getDefault().postSticky(item);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        intent = new Intent(context, DetailDocumentNotificationActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_SEARCH_INFOR, item);

                                        // xu ly khac
                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        break;
                                    case "DAXULY":
                                        DocumentProcessedInfo itemProcessed = new DocumentProcessedInfo();
                                        itemProcessed.setId(idDoc);
                                        itemProcessed.setIsChuTri(detailNotifyInfo.getParamDetailNotifyInfo().getIsChuTri());
                                        itemProcessed.setIsCheck(detailNotifyInfo.getParamDetailNotifyInfo().getIsCheck());
                                        itemProcessed.setProcessDefinitionId(detailNotifyInfo.getParamDetailNotifyInfo().getProcessKey());
                                        itemProcessed.setCongVanDenDi(detailNotifyInfo.getParamDetailNotifyInfo().getCongVanDenDi());
//                                        EventBus.getDefault().postSticky(itemProcessed);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                                        intent = new Intent(context, DetailDocumentProcessedActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_PROCESSED_INFOR, itemProcessed);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC,
                                                new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));

                                        // xu ly khac
                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                                        break;
                                    case "THONGBAO":
                                        DocumentNotificationInfo itemNotification = new DocumentNotificationInfo();
                                        itemNotification.setId(idDoc);
                                        itemNotification.setIsChuTri(detailNotifyInfo.getParamDetailNotifyInfo().getIsChuTri());
                                        itemNotification.setIsCheck(detailNotifyInfo.getParamDetailNotifyInfo().getIsCheck());
                                        itemNotification.setProcessDefinitionId(detailNotifyInfo.getParamDetailNotifyInfo().getProcessKey());
                                        itemNotification.setCongVanDenDi(detailNotifyInfo.getParamDetailNotifyInfo().getCongVanDenDi());
//                                        EventBus.getDefault().postSticky(itemNotification);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemNotification.getId(), itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                                        intent = new Intent(context, DetailDocumentNotificationActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_NOTIFICATION_INFOR, itemNotification);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC,
                                                new TypeChangeDocumentRequest(itemNotification.getId(),
                                                        itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));

                                        // xu ly khac
                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemNotification.getId(),
                                                itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                                        break;
                                    case "WEB":
                                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), "Bạn vui lòng truy cập website để xử lý thông báo này", true, AlertDialogManager.INFO);
                                        break;
                                    case "CVDUOCGIAO":
                                        if (detailNotifyInfo.getParamDetailNotifyInfo() == null || detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec() == null || detailNotifyInfo.getParamDetailNotifyInfo().getNxlId() == null) {
                                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION),context.getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.ERROR);
                                            return;
                                        }
                                        EventBus.getDefault().postSticky(new WorkManageEvent(detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec()
                                                , detailNotifyInfo.getParamDetailNotifyInfo().getNxlId()));
                                        Intent intentcv = new Intent(context, DetailWorkManagementActivity.class);
                                        intentcv.putExtra("id", detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec());
                                        intentcv.putExtra("nxlid", detailNotifyInfo.getParamDetailNotifyInfo().getNxlId());
                                        intentcv.putExtra("typeWork", 1);//type =1 nếu là công việc được giao
                                        context.startActivity(intentcv);
                                        break;
                                    case "CVDAGIAO":
                                        if (detailNotifyInfo.getParamDetailNotifyInfo() == null || detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec() == null || detailNotifyInfo.getParamDetailNotifyInfo().getNxlId() == null) {
                                            AlertDialogManager.showAlertDialog(context,context.getString(R.string.TITLE_NOTIFICATION),context.getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.ERROR);
                                            return;
                                        }
                                        EventBus.getDefault().postSticky(new WorkManageEvent(detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec()
                                                , detailNotifyInfo.getParamDetailNotifyInfo().getNxlId()));
                                        Intent intentcvd = new Intent(context, DetailWorkManagementActivity.class);
                                        intentcvd.putExtra("id", detailNotifyInfo.getParamDetailNotifyInfo().getIdCongViec());
                                        intentcvd.putExtra("nxlid", detailNotifyInfo.getParamDetailNotifyInfo().getNxlId());
                                        intentcvd.putExtra("typeWork", 2);//type =2 nếu là công việc đã giao
                                        context.startActivity(intentcvd);
                                        break;
                                    case "TTDH":
                                        if (detailNotifyInfo.getParamDetailNotifyInfo().getIdThongTin() != null) {
                                            Intent intentDieuHanh = new Intent(context, DetailChiDaoActivity.class);
                                            intentDieuHanh.putExtra("ID_CHIDAO", detailNotifyInfo.getParamDetailNotifyInfo().getIdThongTin());
                                            context.startActivity(intentDieuHanh);
                                        }
                                        break;
                                }
                                if (AndroidProcesses.isMyProcessInTheForeground()) {
                                    // app is in foreground, broadcast the push message
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                    intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                                    intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                                    context.startActivity(intent);
                                } else {
                                    // app is in background, show the notification in notification tray
                                    intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                    context.startActivity(intent);
                                }
                            } catch (Exception ex) {
                            }
                        } else {
                        }
                    }
                }

                @Override
                public void onCallError(Object object) {
                }
            };
            notifyDao.onReadedNotifysDao(callFinishedListener, new ReadedNotifyRequest(notificationId));
            //--------------

            try {
                JSONObject jsonData = new JSONObject(json.toString());
                if (Application.getApp().getAppPrefs().isLogined()) {
                    // Nếu type = "Trang Chu" -> ko làm gì cả vào màn hình chính
                    if (jsonData.getString("type") != null
                            && jsonData.getString("type").equalsIgnoreCase(Constants.TYPE_TRANGCHU)) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    String link = jsonData.optString("link");
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_DOCUMENT.contains(jsonData.getString("type"))) {
                        notifyDao.onGetDetailNotifyDao(callFinishedListener, notificationID);
                    }
                    if (jsonData.getString("type") != null
                            && jsonData.getString("type").equalsIgnoreCase(Constants.TYPE_NOTIFY_SIGN_DOCUMENT)) {
                        EventBus.getDefault().postSticky(new NhanWebViewEvent(1));
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(jsonData.getString("type"))) {
                        try {
                            WeekViewEvent weekViewEvent = new WeekViewEvent();
                            weekViewEvent.setId(Long.parseLong(link));
                            weekViewEvent.setType(Constants.SCHEDULE_MEETING);
                            EventBus.getDefault().postSticky(weekViewEvent);
                            EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_LIST));
                            Intent intent = new Intent(context, DetailScheduleActivity.class);
                            if (AndroidProcesses.isMyProcessInTheForeground()) {
                                // app is in foreground, broadcast the push message
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                                intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                                context.startActivity(intent);
                            } else {
                                // app is in background, show the notification in notification tray
                                intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                context.startActivity(intent);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_WORK.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(context, DetailWorkActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            context.startActivity(intent);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            context.startActivity(intent);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_PROFILE.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(context, ProfileWorkActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            context.startActivity(intent);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            context.startActivity(intent);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_MAIL.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(context, LetterActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            context.startActivity(intent);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            context.startActivity(intent);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_CHIDAO.contains(jsonData.getString("type"))) {
                        String id = link.split("\\|")[1];
                        Intent intent = new Intent(context, DetailChiDaoActivity.class);
                        intent.putExtra("ID_CHIDAO", id);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            context.startActivity(intent);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            context.startActivity(intent);
                        }
                    }
                }
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            Log.d(TAG, "Exception: " + ex.toString());
        }
    }
}
