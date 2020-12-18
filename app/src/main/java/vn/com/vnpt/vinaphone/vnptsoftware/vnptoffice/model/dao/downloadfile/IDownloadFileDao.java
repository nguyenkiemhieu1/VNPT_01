package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.downloadfile;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FileMeeting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public interface IDownloadFileDao {
    void onDownloadFileDao(int id, ICallFinishedListener iCallFinishedListener);
    void onGetUrlFileDao(int id, ICallFinishedListener iCallFinishedListener);
    void onDownloadFileChiDaoDao(DownloadChiDaoRequest downloadChiDaoRequest, ICallFinishedListener iCallFinishedListener);
    void onGetUrlFileDao(String urlFile, ICallFinishedListener iCallFinishedListener);
    void onDownloadFileLichHop(FileMeeting fileMeeting, ICallFinishedListener iCallFinishedListener);
    void onGetUrlFileLichHopDao(String urlFile, ICallFinishedListener iCallFinishedListener);
    void onGetViewFileDocument(int id,ICallFinishedListener iCallFinishedListener);
    void onGetViewFileChiDao(String file,ICallFinishedListener iCallFinishedListener);
    void onGetViewFileSchedule(String file,ICallFinishedListener iCallFinishedListener);
}
