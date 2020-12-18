package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common;

/**
 * Created by LinhLK - 0948012236 on 9/8/2017.
 */

public class Constants {
    public static final double SCHEDULE_HOUR_DEFAULT = 8;
    public static final int SCHEDULE_NUMBER_DAY_OF_WEEK_DEFAULT = 5;
    public static final String RESPONE_SUCCESS = "0";
    public static final String IS_READ = "TRUE";
    public static final String IS_NOT_READ = "FALSE";
    public static final String HAS_NOT_FILE = "none";
    public static final String HAS_FILE = "null";
    public static final String CHANGE_PASSWORD_SUCCESS = "TRUE";
    public static final int DISPLAY_RECORD_SIZE = 10;
    public static final String PAGE_NO_DEFAULT = "-1";
    public static final String PAGE_NO_ALL = "1";
    public static final int RESPONE_UNAUTHEN = 401;
    public static final int RESPONE_INVALID = 400;
    public static final int RESPONE_SUCCESS_HANDLER = 200;
    public static final String DOCUMENT_RECEIVE = "1";
    public static final String DOCUMENT_SEND = "2";
    public static final String DOCUMENT_RECEIVE_RECOVERED = "TRUE";
    public static final String DOCUMENT_RECEIVE_NOT_RECOVERED = "FALSE";
    public static final String DOCUMENT_SEND_NOT_RECOVERED = "none";
    public static final String DOCUMENT_RECOVERED_SUCCESS = "TRUE";
    public static final String CHECK_RECOVER_ACTION = "CHECKRECOVER";
    public static final String RECOVER_ACTION = "RECOVER";
    public static final String CHECK_MARK_ACTION = "CHECKMARK";
    public static final String MARK_ACTION = "MARK";
    public static final String DOCUMENT_WAITING = "DOCUMENT_WAITING";
    public static final String DOCUMENT_PROCESSED = "DOCUMENT_PROCESSED";
    public static final String DOCUMENT_NOTIFICATION = "DOCUMENT_NOTIFICATION";
    public static final String DOCUMENT_MARK = "DOCUMENT_MARK";
    public static final String DOCUMENT_EXPIRED = "DOCUMENT_EXPIRED";
    public static final int DELAY_TIME_SEARCH = 2000;

    public static final String NEW_HISTORY = "0";
    public static final String PROCESSING_HISTORY = "1";
    public static final String PROCESSED_HISTORY = "2";

    public static final String TYPE_NOTIFY_DOCUMENT = "1,2,KYVANBAN";
    public static final String TYPE_NOTIFY_SIGN_DOCUMENT = "KYVANBAN";
    public static final String TYPE_TRANGCHU = "TRANGCHU";
    public static final String TYPE_NOTIFY_WORK = "3";
    public static final String TYPE_NOTIFY_PROFILE = "4";
    public static final String TYPE_NOTIFY_SCHEDULE = "5";
    public static final String TYPE_NOTIFY_MAIL = "6";
    public static final String TYPE_NOTIFY_CHIDAO = "7";

    public static final String MARKED = "1";
    public static final String NOT_MARKED = "0";
    public static final String MARKED_SUCCESS = "true";

    public static final String FINISH_SUCCESS = "true";
    public static final String IS_FINISH = "true";

    public static final String IS_CHANGE_DOC = "true";

    public static final String SEND_RULE = "TRUE";
    public static final String SIGN_RULE = "TRUE";
    public static final String COMMENT_RULE = "true";
    public static final String RECOVER_RULE = "true";

    public static final String CHANGE_DOC_SUCCESS = "TRUE";

    public static final String SEND_TAG = "SEND_TAG";
    public static final String COMMENT_TAG = "COMMENT_TAG";
    public static final String MARK_TAG = "MARK_TAG";
    public static final String SIGN_TAG = "SIGN_TAG";
    public static final String FLOW_TAG = "FLOW_TAG";
    public static final String HISTORY_TAG = "HISTORY_TAG";

    public static final String RECOVER_TAG = "RECOVER_TAG";

    public static final String EDIT_TAG = "EDIT_TAG";
    public static final String REMOVE_TAG = "REMOVE_TAG";

    public static final String COMMENT = "true";
    public static final String NOT_COMMENT = "false";
    public static final String COMMENTED = "OK";

    public static final String SCHEDULE_MEETING = "2";
    public static final String SCHEDULE_BUSSINESS = "1";
    public static final String SCHEDULE_LIST = "1";
    public static final String SCHEDULE_NOTIFY = "2";

    public static final String TYPE_PERSON_PROCESS = "TYPE_PERSON_PROCESS";
    public static final String TYPE_PERSON_SEND = "TYPE_PERSON_SEND";
    public static final String TYPE_PERSON_NOTIFY = "TYPE_PERSON_NOTIFY";
    public static final String TYPE_SEND_PERSON_PROCESS = "TYPE_SEND_PROCESS";
    public static final String TYPE_PERSON_DIRECT = "TYPE_PERSON_DIRECT";
    public static final String TYPE_SEND_VIEW = "TYPE_SEND_VIEW";

    public static final String TYPE_SEND_PROCESS_REQUEST = "1";
    public static final String TYPE_SEND_NOTIFY_REQUEST = "0";

    public static final int TYPE_SEND_NOTIFY = 6;
    public static final int TYPE_SEND_PROCESS = 5;

    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String DOCX = "DOCX";
    public static final String XLS = "XLS";
    public static final String XLSX = "XLSX";
    public static final String ZIP = "ZIP";
    public static final String RAR = "RAR";
    public static final String PPT = "PPT";
    public static final String PPTX = "PPTX";
    public static final String JPEG = "JPEG";
    public static final String JPG = "JPG";
    public static final String PNG = "PNG";
    public static final String GIF = "GIF";
    public static final String TIFF = "TIFF";
    public static final String BMP = "BMP";
    public static final String TXT = "TXT";
    public static final String MPP = "MPP";

    public static final String HOME_TRANGCHU = "TRANGCHU";
    public static final String HOME_TRANGTINTUC = "TRANGTINTUC";
    public static final String HOME_DEN_CHO_XU_LY = "DEN_CHO_XU_LY";
    public static final String HOME_DEN_DA_XU_LY = "DEN_DA_XU_LY";
    public static final String HOME_DI_CHO_XU_LY = "DI_CHO_XU_LY";
    public static final String HOME_DI_DA_PHAT_HANH = "DI_DA_PHAT_HANH";
    public static final String HOME_DI_DA_XU_LY = "DI_DA_XU_LY";
    public static final String HOME_VANBAN_XEMDEBIET = "VANBANXEMDEBIET";
    public static final String HOME_VANBANDENHAN = "VANBANDENHAN";
    public static final String HOME_VANBANTHONGBAO = "VANBANTHONGBAO";
    public static final String HOME_VANBANDANHDAU = "VANBANDANHDAU";
    public static final String HOME_DANHBA = "DANHBA";
    public static final String HOME_QUANLYLICHHOP = "QUANLYLICHHOP";
    //add
    public static final String HOME_QUANLYLICHHOPPHONGBAN = "QUANLYLICHHOPPHONGBAN";
    public static final String HOME_BAOCAO = "BAOCAO";
    public static final String HOME_BAOCAOTHONGKE = "BAOCAOTHONGKE";
    public static final String HOME_THONGTINCHIDAO = "THONGTINCHIDAO";

    public static final String NGAY_BAN_HANH_DIALOG = "NGAY_BAN_HANH_DIALOG";
    public static final String NGAY_SOAN_THAO_DIALOG = "NGAY_SOAN_THAO_DIALOG";
    public static final String HAN_XU_LY_DIALOG = "HAN_XU_LY_DIALOG";

    public static final int HOME = 0;
    public static final int VANBANDEN = 1;
    public static final int VANBANDI = 2;
    public static final int VANBANDANHDAU = 3;
    public static final int VANBANXEMDEBIET = 4;
    public static final int TRACUUVANBAN = 5;
    public static final int DANHBA = 6;
    public static final int QUANLYLICH = 7;
    public static final int QUANLYLICH_LICHTUAN = 0;
    public static final int QUANLYLICH_DANGKYLICHTUAN = 1;
    public static final int LICHDONVI = 8;
    public static final int LICHPHONGBAN = 9;
    public static final int QUANLYCONGVIEC = 10;
    public static final int QUANLYCONGVIEC_CONGVIECDUOCGIAO = 0;
    public static final int QUANLYCONGVIEC_CONGVIECDAGIAO = 1;
    public static final int QUANLYCONGVIEC_TAOCONGVIECMOI = 2;
    public static final int THONGTINDIEUHANH = 11;
    public static final int CHIDAO_NHAN = 0;
    public static final int CHIDAO_GUI = 1;
    public static final int REPORT = 12;
    public static final int SETTING = 13;
    public static final int SETTING_PROFILE = 0;
    public static final int SETTING_CHANGE_PASSWORD = 1;
    public static final int SETTING_DEFAULT_HOME = 2;
    public static final int LOGOUT = 14;
    public static final int DOCUMENT = 1;

    public static final int REPORT_EXT = 5;
    public static final int WORK_MANAGE = 8;


    public static final int DOC_WAITING = 0;
    public static final int DOC_PROCESSED = 1;
    public static final int DOC_EXPIRED = 5;
    public static final int DOC_NOTIFICATION = 2;
    public static final int DOC_MARK = 3;
    public static final int DOC_SEARCH = 4;


    //Type Kho Van ban

    public static final String CONSTANTS_VAN_BAN_DA_XU_LY = "Văn bản đã xử lý";
    public static final String CONSTANTS_VAN_BAN_XEM_DE_BIET = "Văn bản Xem để biết";
    public static final String CONSTANTS_VAN_BAN_DANH_DAU = "Văn bản đánh dấu";
    public static final String CONSTANTS_TRA_CUU_VAN_BAN = "Tra cứu văn bản";
    public static final String CONSTANTS_VAN_BAN_DEN_CHO_XLC = "VB đến chờ xử lý chính ";
    public static final String CONSTANTS_VAN_BAN_DEN_CHO_XLPH = "VB đến chờ xử lý phối hợp";
    public static final String CONSTANTS_VAN_BAN_DI_CHO_XLC = "VB đi chờ xử lý chính";
    public static final String CONSTANTS_VAN_BAN_DI_CHO_XLPH = "VB đi chờ xử lý phối hợp";
    public static final String CONSTANTS_VAN_BAN_CO_KY_SO = "VB đi có ký số";
    public static final String CONSTANTS_VAN_BAN_DI_KHONG_KY_SO = "VB đi không ký số";
    public static final String CONSTANTS_VAN_BAN_DEN_CHO_XU_LY = "Văn bản đến chờ xử lý";
    public static final String CONSTANTS_VAN_BAN_DI_CHO_XU_LY = "Văn bản đi chờ xử lý";
    public static final String CONSTANTS_VAN_BAN_DEN_DA_XU_LY = "Văn bản đến đã xử lý";
    public static final String CONSTANTS_VAN_BAN_DI_CAN_XU_LY = "Văn bản đi chờ xử lý";
    public static final String CONSTANTS_VAN_BAN_DI_DA_PHAT_HANH = "Văn bản đi đã phát hành";
    public static final String CONSTANTS_VAN_BAN_DI_DA_XU_LY = "Văn bản đi đã xử lý";
    public static final String CONSTANTS_VAN_BAN_CHO_TGD_XU_LY = "Chờ TGĐ xử lý";
    public static final String CONSTANTS_VAN_BAN_CHO_PHE_DUYET = "Văn bản chờ phê duyệt";
    public static final String CONSTANTS_CONGVIEC_DUOCGIAO = "Công việc được giao";
    public static final String CONSTANTS_CONGVIEC_DA_GIAO = "Công việc đã giao";
    public static final String CONSTANTS_VAN_BAN_DI = "Văn bản đi";
    public static final String CONSTANTS_VAN_BAN_DEN = "Văn bản đến";
    public static final String CONSTANTS_QUAN_LY_CONG_VIEC = "Quản lý công việc";
    public static final String CONSTANTS_VAN_BAN_DEN_HAN_QUA_HAN = "Văn bản đến hạn/quá hạn";
    public static final String CONSTANTS_VAN_BAN_CHO_TONG_GIAM_DOC_XU_LY = "Văn bản chờ Tổng GĐ xử lý";

    public static final String CONSTANTS_TEXT_TRANG_CHU = "Trang chủ";
    public static final String CONSTANTS_TEXT_TRANG_TIN_TUC= "Trang tin tức";
    public static final String CONSTANTS_TEXT_QUAN_LY_VAN_BAN = "Quản lý văn bản";
    public static final String CONSTANTS_TEXT_QUAN_LY_CONG_VIEC = "Quản lý công việc";
    public static final String CONSTANTS_TEXT_DANH_BA = "Danh bạ";
    public static final String CONSTANTS_TEXT_LICH_CONG_TAC_LANH_DAO = "Lịch Công tác lãnh đạo";
    public static final String CONSTANTS_THONG_TIN_DIEU_HANH = "Thông tin điều hành";
    public static final String CONSTANTS_TEXT_BAO_CAO_THONG_KE = "Báo cáo thống kê";
    public static final String CONSTANTS_TEXT_CAI_DAT_HE_THONG = "Cài đặt hệ thống";
    public static final String CONSTANTS_TEXT_DANG_XUAT = "Đăng xuất";

    public static final String CONSTANTS_DEN_CHO_XU_LY = "DEN_CHO_XU_LY";
    public static final String CONSTANTS_DEN_DA_XU_LY = "DEN_DA_XU_LY";
    public static final String CONSTANTS_DI_CHO_XU_LY = "DI_CHO_XU_LY";
    public static final String CONSTANTS_DI_DA_PHAT_HANH = "DI_DA_PHAT_HANH";
    public static final String CONSTANTS_DI_DA_XU_LY = "DI_DA_XU_LY";
    public static final String CONSTANTS_XEM_DE_BIET = "XEM_DE_BIET";
    public static final String CONSTANTS_CONG_VIEC_DUOC_GIAO = "CONGVIEC_DUOCGIAO";
    public static final String CONSTANTS_CONG_VIEC_DA_GIAO = "CONGVIEC_DAGIAO";
    public static final String CONSTANTS_TTDH_NHAN = "TTDH_NHAN";
    public static final String CONSTANTS_TTDH_GUI = "TTDH_GUI";

    public static final String CONSTANTS_INTENT_DETAIL_INFOR = "DetailDocumentInfo";
    public static final String CONSTANTS_INTENT_TYPE_CHANGE_DOC = "TypeChangeDocumentRequest";
    public static final String CONSTANTS_INTENT_DOC_WATTING_INFOR = "DocumentWaitingInfo";
    public static final String CONSTANTS_INTENT_DOC_SEARCH_INFOR = "DocumentSearchInfo";
    public static final String CONSTANTS_INTENT_DOC_PROCESSED_INFOR = "DocumentProcessedInfo";
    public static final String CONSTANTS_INTENT_DOC_NOTIFICATION_INFOR = "DocumentNotificationInfo";

    public static final String CONSTANTS_ID_CHIDAO = "ID_CHIDAO";
    public static final String CONSTANTS_CHOXULY = "CHOXULY";
    public static final String CONSTANTS_TRACUU = "TRACUU";
    public static final String CONSTANTS_DAXULY = "DAXULY";
    public static final String CONSTANTS_THONGBAO = "THONGBAO";
    public static final String CONSTANTS_WEB = "WEB";

}
