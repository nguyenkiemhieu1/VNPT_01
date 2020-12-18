package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.url;

/**
 * Created by LinhLK - 0948012236 on 4/10/2017.
 */

public class ServiceUrl {
    public static final String GET_DOCUMENT_SAVED = "api/brief/getlistbrief/";
    public static final String POST_DOCUMENT_SAVED = "api/brief/savebrief/";

    public static final String CHECK_VERSION_URL = "api/checkversion/";
    public static final String LOGIN_URL = "api/login/";
    public static final String LOGIN_V2_URL = "api/login/v2/";
    public static final String LOGOUT_URL = "api/logout/";
    public static final String CHANGE_PASSWORD_URL = "api/updatepassword/";
    public static final String GET_USER_INFO_URL = "api/getuserinfo/me/";
    public static final String GET_CONTACT_INFO_URL = "api/getuserinfo/{userid}/";
    public static final String GET_CONTACT_URL = "api/getcontacts/";
    public static final String GET_COUNT_DOC_WAIT_URL = "api/document/getpagingwaitingdocument/";
    public static final String GET_DOC_WAIT_URL = "api/document/getlistwaitingdocument/";
    public static final String GET_COUNT_DOC_PROCECSSED_URL = "api/document/getpagingprocesseddocument/";
    public static final String GET_DOC_PROCECSSED_URL = "api/document/getlistprocesseddocument/";
    public static final String CHECK_RECOVER_DOC_RECEIVE_URL = "api/incommingdocument/checkrecoverdocument/{id}/";
    public static final String RECOVER_DOC_RECEIVE_URL = "api/incommingdocument/recoverdocument/{id}/";
    public static final String RECOVER_DOC_SEND_URL = "api/outgoingdocument/recovertranferdocument/{id}/";
    public static final String GET_LIST_SWITCH_USER_URL = "api/switch/{userid}/";
    public static final String VIEW_DIAGRAM_URL = "/qlvbdh/view_img.jsp";
    public static final String GET_COUNT_DOC_NOTIFICATION_URL = "api/document/getpagingnotifydocument/";
    public static final String GET_DOC_NOTIFICATION_URL = "api/document/getlistnotifydocument/";
    public static final String GET_COUNT_DOC_EXPIRED_URL = "api/document/getpagingoutdatedocument/";
    public static final String GET_DOC_EXPIRED_URL = "api/document/getlistoutdatedocument/";
    public static final String GET_LIST_BUTTON_SEND_SAME_URL = "api/document/getapprovedvaluecoeval/";
    public static final String ISSUING_DOCUMENTS_COME_SAME_URL = "api/incommingdocument/promulgatedocumentcoeval/";
    public static final String SEND_DOCUMENT_COME_SAME_URL = "api/incommingdocument/tranferdocumentcoeval/";
    public static final String FINISH_DOCUMENT_SAME_URL = "api/document/finishcoeval/";
    public static final String SEND_PROCESS_DOCUMENT_SAME_URL = "api/document/forwarddocumentcoeval/";
    public static final String GET_DETAIL_DOCUMENT_WAITING_URL = "api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_WAITING_URL = "api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_WAITING_URL = "api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_WAITING_URL = "api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_WAITING_URL = "api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_PROCESSED_URL = "api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_PROCESSED_URL = "api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_PROCESSED_URL = "api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_PROCESSED_URL = "api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_PROCESSED_URL = "api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_EXPIRED_URL = "api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_EXPIRED_URL = "api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_EXPIRED_URL = "api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_EXPIRED_URL = "api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_EXPIRED_URL = "api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_NOTIFICATION_URL = "api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_NOTIFICATION_URL = "api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_NOTIFICATION_URL = "api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_NOTIFICATION_URL = "api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_NOTIFICATION_URL = "api/file/getfilerelated/{id}/";
    public static final String GET_LIST_TYPE_DOC_URL = "api/document/getlisttypecode/";
    public static final String GET_LIST_FIELD_DOC_URL = "api/document/getlistfield/";
    public static final String GET_LIST_PRIORITY_DOC_URL = "api/document/getlistpriority/";
    //public static final String GET_LIST_BOOK_DOC_URL = "api/document/getlistbook/";
    public static final String GET_LIST_TYPE_CHANGE_DOC_URL = "api/document/getapprovedvalue/";
    public static final String GET_LIST_TYPE_CHANGE_DOC_VIEW_FILES_URL = "api/document/getapprovedvaluedetailfile/";
    public static final String GET_COUNT_DOC_SEARCH_URL = "api/document/paginglookupbyparam/";
    public static final String GET_DOC_SEARCH_URL = "api/document/getlistlookupbyparam/";
    public static final String GET_COUNT_DOC_ADVANCE_SEARCH_URL = "api/document/paginglookupdocument/";
    public static final String GET_DOC_ADVANCE_SEARCH_URL = "api/document/lookupdocument/";
    public static final String GET_COUNT_DOC_MARK_URL = "api/document/pagingsigneddocument/";
    public static final String GET_DOC_MARK_URL = "api/document/getlistsigneddocument/";
    public static final String GET_DETAIL_DOCUMENT_MARK_URL = "api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_MARK_URL = "api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_MARK_URL = "api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_MARK_URL = "api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_MARK_URL = "api/file/getfilerelated/{id}/";
    public static final String GET_SCHEDULES_URL = "api/schedule/getlistschedule/";
    public static final String GET_LIST_UNIT_SCHEDULES_URL = "api/schedule/getlistunit/";
    public static final String GET_LIST_SCHEDULES_DEPART_URL = "api/schedule/getcalendar/";
    public static final String CHECK_MARK_DOC_URL = "api/document/checksigneddocument/{id}/";
    public static final String MARK_DOC_URL = "api/document/signeddocument/{id}/";
    public static final String SIGN_DOC_URL = "/VNPTsignature/rest/ioffice/signature";
    public static final String GET_DETAIL_SCHEDULE_MEETING_URL = "api/schedule/getdetailmeetingschedule/{id}/";
    public static final String GET_DETAIL_SCHEDULE_BUSSINESS_URL = "api/schedule/getdetailworkingschedule/{id}/";
    public static final String GET_LIST_SCHEDULE_WEEK_URL = "api/schedule/getlistweekschedule/";
    public static final String GET_CHUTRI_SCHEDULE_WEEK_URL = "api/schedule/getlistchutri/";
    public static final String CREATE_SCHEDULE_WEEK_URL = "api/schedule/createweekschedule/";
    public static final String GET_PERSON_AND_UNIT_SCHEDULE_WEEK_URL = "api/schedule/getlistuser/";
    public static final String GET_PERSONS_PROCESS_URL = "api/document/getusertotranfer/";
    public static final String GET_PERSONS_OR_UNIT_EXPECTED_URL = "api/incommingdocument/getlistexpected/{id}/";
    public static final String GET_PERSONS_SEND_URL = "api/document/getuserconcurrentsend/";
    public static final String GET_PERSONS_NOTIFY_URL = "api/getuserunitnotify/";
    public static final String GET_REPORT_DOCUMENT_URL = "api/report/documentreport/";
    public static final String GET_REPORT_WORK_URL = "api/report/jobreport/{month}/";
    public static final String CHECK_COMMENT_DOC_URL = "api/incommingdocument/checksendcomment/{id}/";
    public static final String COMMENT_DOC_URL = "api/incommingdocument/sendcomment/";
    public static final String CHANGE_DOC_SEND_URL = "api/outgoingdocument/tranferdocument/";
    public static final String CHANGE_DOC_RECEIVE_URL = "api/incommingdocument/tranferdocument/";
    public static final String CHANGE_DOC_PROCESS_URL = "api/document/forwarddocument/";
    public static final String CHANGE_DOC_NOTIFY_URL = "api/document/forwarddocument/";
    public static final String CHANGE_DOC_DIRECT_URL = "api/incommingdocument/promulgatedocument/";
//    public static final String GET_NOTIFY_URL = "api/notifycation/getlistnotify/";
    public static final String GET_NOTIFY_URL = "api/notifycation/getlistnotify/v2/";
    public static final String READED_NOTIFY_URL = "api/notifycation/setreadnotify/";
    public static final String GET_JOB_POSSITION_URL = "api/jobposition/getlistjobposition/";
    public static final String GET_UNIT_URL = "api/document/getlistunit/";
    public static final String DOWNLOAD_FILE_URL = "api/file/download/{id}/";
    public static final String GET_VIEW_FILE_DOCUMENT = "api/file/downloadpdf/{id}/";
    public static final String GET_VIEW_FILE_CHIDAO = "api/information/downloadpdf/";
    public static final String GET_VIEW_FILE_SCHEDULE = "api/schedule/downloadpdf/";
    public static final String GET_FILE_URL_DOC = "api/generateotp/{id}/";
    public static final String GET_AVATAR_URL = "api/getavatar/{userid}/";
    public static final String GET_CHIDAO_NHAN_URL = "api/information/getlistreceive/";
    public static final String GET_CHIDAO_GUI_URL = "api/information/getlistsend/";
    public static final String UPLOAD_FILE_URL = "api/file/upload/";
    public static final String CREATE_CHIDAO_URL = "api/information/create/";
    public static final String EDIT_CHIDAO_URL = "api/information/edit/";
    public static final String GET_PERSON_CHIDAO_URL = "api/getuserunit/";
    public static final String SAVE_PERSON_CHIDAO_URL = "api/information/updateemployee/";
    public static final String GET_PERSON_RECEIVE_CHIDAO_URL = "api/information/getlistnotify/";
    public static final String GET_PERSON_GROUP_CHIDAO_URL = "api/information/getgroup/";
    public static final String SEND_CHIDAO_URL = "api/information/send/";
    public static final String GET_FLOW_CHIDAO_URL = "api/information/getflow/{id}/";
    public static final String GET_FILE_CHIDAO_URL = "api/information/getfiles/{id}/";
    public static final String GET_DELETE_CHIDAO_URL = "api/information/delete/{id}/";
    public static final String GET_DETAIL_CHIDAO_URL = "api/information/getdetail/{id}/";
    public static final String DOWNLOAD_FILE_CHIDAO_URL = "api/information/download/";
    public static final String GET_PERSON_IN_GROUP_CHIDAO_URL = "api/information/getuserbygroup/";
    public static final String GET_PERSON_REPLY_CHIDAO_URL = "api/information/getlistuser/";
    public static final String GET_PERSON_RECEIVED_CHIDAO_URL = "api/information/getuserreceiver/";
    public static final String GET_LINK_EDIT_FILES = "api/getlinkeditfile/{id}/";
    public static final String CHECK_FINISH_DOC_URL = "api/document/checkfinish/{id}/";
    public static final String GET_LIEN_THONG_XL_URL = "api/unit/getlistinternaltranfer/";
    public static final String GET_LIEN_THONG_BH_URL = "api/unit/getlistinternal/{id}/";
//    public static final String GET_SCHEDULES_BOSS_URL = "api/schedule/getlistworkingschedule/";
    public static final String GET_SCHEDULES_BOSS_URL = "api/schedule/getlistschedule/";
    public static final String CHECK_CHANGE_DOC_PROCECSSED_URL = "api/document/checktranferadditional/{id}/";
    public static final String CHANGE_DOC_NOTIFY_XEMDB_URL = "api/document/tranfernotify/";
    public static final String GET_GROUP_PERSON_CN_URL = "api/group/getlistgroupuser/";
    public static final String GET_GROUP_PERSON_DV_URL = "api/group/getlistgroupunit/";
    public static final String GET_PERSONS_SEND_PROCESS_URL = "api/document/getlistusertranfer/";
    public static final String GET_UNIT_CLERK_URL = "api/document/getlistunitpublish/";
    public static final String GET_DETAIL_NOTIFY_URL = "api/notifycation/getdetailnotify/{id}/";
    public static final String GET_LIST_FORM_DATA_URL = "api/document/getlistcomment/";
    public static final String GET_LIST_JOB_DATA_ASSIGN = "api/job/getlistjobdagiao/";
    public static final String GET_LIST_JOB_DATA_RECEIVE = "api/job/getlistjobduocgiao/";
    public static final String GET_LIST_STATUS_BOX = "api/job/getliststatus/{type}/";
    public static final String GET_LIST_JOB_RECEIVE = "api/job/getdetailjobduocgiao/{id}/{nxlid}/";
    public static final String GET_LIST_JOB_ASSIGN = "api/job/getdetailjobdagiao/{id}/";
    public static final String GET_LIST_JOB_HANDLING_PROGRESS = "api/job/getlistjobflow/{id}/";
    public static final String CALL_JOB_UPDATE_STATUS = "api/job/updatestatus/";
    public static final String GET_LIST_SUB_TASK_DETAIL = "api/job/getlistchildjob/{id}/";
    public static final String GET_LIST_UNIT_PERSON_DETAIL = "api/job/getlistprocesser/{id}/";
    public static final String GET_LIST_BOSS_SUBTASK = "api/job/getlistboss/";
    public static final String GET_LIST_UNIT_SUBTASK = "api/job/getlistunit/";
    public static final String GET_LIST_PERSON_SUBTASK = "api/job/getlistperson/{id}/";
    public static final String CREATE_SUBTASK = "api/job/createjob/";
    public static final String GET_LIST_FILE_SUBTASK = "api/job/getlistfile/{id}/";
    public static final String THONG_TIN_DIEU_HANH_DOC_URL = "api/generateotpinformation/";
    public static final String FILE_MEETING_URL = "api/file/downloadfileschedule/";
    public static final String LICH_HOP_DOC_URL = "api/generateotpschedule/";
    public static final String CHECK_RECOVER_DOC_SEND_URL = "api/outgoingdocument/checkrecoverdocument/{id}/";
    public static final String UPDATE_CONGVIEC = "api/job/updatejob/";
    public static final String DANHGIA_CONGVIEC = "api/job/evaluatejob/";
    public static final String CALL_API_UPDATE_ASSIGN = "api/job/updateassign/";
    public static final String LIST_FILE_CONG_VIEC = "api/job/getlistfile/{id}/";
    public static final String FINISH_DOC_URL_V2 = "api/document/finish/v2/";
    public static final String KY_VAN_BAN_URL = "api/document/signdocument/v2/";
    public static final String KY_VAN_BAN_CA_URL = "api/document/signserverca/";
    public static final String KY_VAN_BAN_PKI_URL = "api/document/signdocumentpki/";
    public static final String KY_VAN_BAN_SIGN_SERVICE_OTP_URL = "api/validateotp/{otp}/";
    public static final String KY_VAN_BAN_RESEND_SERVICE_OTP_URL = "api/resendotp/";

    public static final String GET_COUNT_COMMENT_URL = "api/document/countmenu/";
    public static final String EXCEPTION_URL = "api/createexception/";
    public static final String GET_DONVI_NHAN_URL = "api/information/getlistagent/";
    public static final String GET_DANHSACH_DONVI_NHAN_URL = "api/getuserunitbyagent/";
    public static final String UPLOAD_FILE_CHUYEN_VB_URL = "api/file/uploadfile/";


}