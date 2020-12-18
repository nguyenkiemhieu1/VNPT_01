package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.syncdata;

/**
 * Created by LinhLK - 0948012236 on 9/6/2017.
 */

public interface HandleSyncService {
    interface HandleGetCount {
        void onSuccessGetCount(Object object);
        void onErrorGetCount(Object object);
    }
    interface HandleGetRecords {
        void onSuccessGetRecords(Object object);
        void onErrorGetRecords(Object object);
    }
    interface HandleGetSchedules {
        void onSuccessGetSchedules(Object object);
        void onErrorGetSchedules(Object object);
    }
    interface HandleGetListButtonSendSame {
        void onSuccessGetListButtonSendSame(Object object);
        void onErrorGetListButtonSendSame(Object object);
    }
    interface HandleFinishDocumentSame {
        void onSuccessFinishDocumentSame(Object object);
        void onErrorFinishDocumentSame(Object object);
    }
}
