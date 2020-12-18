package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.downloadfile;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.FileMeeting;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.IDownloadService;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ExceptionCallAPIEvent;

/**
 * Created by LinhLK - 0948012236 on 8/29/2017.
 */

public class DownloadFileDao extends BaseDao implements IDownloadFileDao {
    private IDownloadService downloadService;

    @Override
    public void onDownloadFileDao(int id, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.download(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onDownloadFileChiDaoDao(DownloadChiDaoRequest downloadChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.download(BaseService.createAuthenHeaders(), downloadChiDaoRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetUrlFileDao(int id, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<GetViewFileObj> call = downloadService.getUrlFileDoc(BaseService.createAuthenHeaders(), id);

        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    @Override
    public void onGetUrlFileDao(String urlFile, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<GetViewFileObj> call = downloadService.getUrlFileDoc(BaseService.createAuthenHeaders(), urlFile);

        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }


    @Override
    public void onDownloadFileLichHop(FileMeeting filesInfo, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.download(BaseService.createAuthenHeaders(), filesInfo);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetUrlFileLichHopDao(String urlFile, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<GetViewFileObj> call = downloadService.getUrlFileLichHopDoc(BaseService.createAuthenHeaders(), urlFile);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetViewFileDocument(int id, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.getViewFileDocument(BaseService.createAuthenHeaders(),id);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onGetViewFileChiDao(String file, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.getViewFileChiDao(BaseService.createAuthenHeaders(),file);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }

    @Override
    public void onGetViewFileSchedule(String file, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.getViewFileSchedule(BaseService.createAuthenHeaders(),file);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }
}
