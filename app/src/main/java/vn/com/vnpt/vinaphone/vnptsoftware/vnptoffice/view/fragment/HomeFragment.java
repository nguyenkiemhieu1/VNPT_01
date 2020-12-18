package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.adapter.MenuHomeAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.model.MenuHome;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.activity.ReportActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.EventTypeQLVB;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    @BindView(R.id.recycler_home)
    RecyclerView recyclerViewHome;
    MenuHomeAdapter homeAdapter;
    RecyclerView.LayoutManager layoutManager;
    private LoginInfo loginInfo;
    private ApplicationSharedPreferences appPrefs;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        loginInfo = appPrefs.getAccountLogin();
//        txtDocument.setTypeface(Application.getApp().getTypeface());
//        txtSchedule.setTypeface(Application.getApp().getTypeface());
//        txtContact.setTypeface(Application.getApp().getTypeface());
//        txtReportExt.setTypeface(Application.getApp().getTypeface());
//        txtReport.setTypeface(Application.getApp().getTypeface());
//        txtNews.setTypeface(Application.getApp().getTypeface());
        ((MainActivity) getActivity()).showNotify(false);
        recyclerViewHome.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewHome.setLayoutManager(layoutManager);
        addMenu();
    }

    @SuppressLint("NewApi")
    private void addMenu() {
        ArrayList<MenuHome> menuHomeArrayList = new ArrayList<>();
        menuHomeArrayList.add(new MenuHome("DOCUMENT", getContext().getString(R.string.DOCUMENT_MENU), getContext().getDrawable(R.drawable.border_document), getContext().getDrawable(R.drawable.icon_document)));
//        if (loginInfo.getConfigs() != null) {
//            if (loginInfo.getConfigs().getLICH_DON_VI_MOBILE_DISPLAY() != null && loginInfo.getConfigs().getLICH_DON_VI_MOBILE_DISPLAY().equals("1")) {
//                menuHomeArrayList.add(new MenuHome("SCHEDULE", getContext().getString(R.string.HOME_SCHEDULE_MENU), getContext().getDrawable(R.drawable.border_schedule), getContext().getDrawable(R.drawable.icon_calendar)));
//            }
//        }
        menuHomeArrayList.add(new MenuHome("SCHEDULE", getContext().getString(R.string.HOME_SCHEDULE_MENU), getContext().getDrawable(R.drawable.border_schedule), getContext().getDrawable(R.drawable.icon_calendar)));

        menuHomeArrayList.add(new MenuHome("CONTACT", getContext().getString(R.string.CONTACT_MENU), getContext().getDrawable(R.drawable.border_contact), getContext().getDrawable(R.drawable.icon_contact)));
        menuHomeArrayList.add(new MenuHome("OPERATING", getContext().getString(R.string.CHIDAO_MENU), getContext().getDrawable(R.drawable.border_report_ext), getContext().getDrawable(R.drawable.icon_document_information)));
        menuHomeArrayList.add(new MenuHome("WORKMANAGER", getContext().getString(R.string.WORK_MENU), getContext().getDrawable(R.drawable.border_new), getContext().getDrawable(R.drawable.icon_task_manager)));

        homeAdapter = new MenuHomeAdapter(getContext(), menuHomeArrayList);
        recyclerViewHome.setAdapter(homeAdapter);
    }

//    @OnClick({R.id.id_Document, R.id.id_Schedule, R.id.id_Contact, R.id.id_Report_ext, R.id.id_Report, R.id.id_New})
//    public void clickItem(View view) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        switch (view.getId()) {
//            case R.id.id_Document:
//                EventBus.getDefault().postSticky(new EventTypeQLVB(0));
//                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.CONSTANTS_DEN_CHO_XU_LY)
//                        , new DocumentWaitingFragment().getClass().toString()).commit();
//                ((MainActivity) getActivity()).setTitle(Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY);
//                break;
//
//            case R.id.id_Schedule:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
//                ((MainActivity) getActivity()).setTitle(getString(R.string.SCHEDULE_MENU));
//                break;
//            case R.id.id_Contact:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
//                ((MainActivity) getActivity()).setTitle(getString(R.string.CONTACT_MENU));
//                break;
//            case R.id.id_Report_ext:
//                EventBus.getDefault().postSticky(new ChiDaotEvent(0));
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
//                ((MainActivity) getActivity()).setTitle(getString(R.string.CHIDAO_NHAN_MENU));
//                ((MainActivity) getActivity()).hideNotify();
//                break;
//            case R.id.id_Report:
//                startActivity(new Intent(getActivity(), ReportActivity.class));
//                break;
//            case R.id.id_New:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new WorkflowManagementFragment().newInstance(1)).commit();
//                ((MainActivity) getActivity()).setTitle(getString(R.string.tv_congviec_duocgiao));
//                break;
//        }
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
