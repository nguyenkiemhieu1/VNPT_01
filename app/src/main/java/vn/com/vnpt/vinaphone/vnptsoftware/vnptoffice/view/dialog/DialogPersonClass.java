package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import mehdi.sakout.fancybuttons.FancyButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ObjectAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.SelectUnitForPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewCreateWorkCustomFactory;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewUnitCreateWorkFactory;

public class DialogPersonClass extends Dialog {
    @BindView(R.id.layout_unit)
    ViewGroup layoutUnit;
    @BindView(R.id.btn_save)
    FancyButton btnSave;
    @BindView(R.id.btn_close)
    FancyButton btnClose;
    @BindView(R.id.layout_person)
    ViewGroup viewGroup;
    @BindView(R.id.spiner_unit)
    EditText spinerUnit;
    @BindView(R.id.layout_person_select)
    LinearLayout layoutPersonSelect;
    private TreeNode root;
    private TreeView treeView;
    private TreeNode rootUnit;
    private TreeView treeViewUnit;
    private String strEndDate = "";
    private List<PersonSendNotifyInfo> lstPersonByUnit = new ArrayList<>();
    private List<PersonSendNotifyInfo> lstUnit = new ArrayList<>();
    private Activity activity;

    public DialogPersonClass(@NonNull Context context) {
        super(context);

    }

    public DialogPersonClass(@NonNull Context context, String strEndDate,
                             List<PersonSendNotifyInfo> lstPersonByUnit, List<PersonSendNotifyInfo> lstUnit) {
        super(context);
        activity = (Activity) context;
        this.strEndDate = strEndDate;
        this.lstPersonByUnit = lstPersonByUnit;
        this.lstUnit = new ArrayList<>(lstUnit);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_person_select);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        displayTreeView(lstPersonByUnit, 1, viewGroup);

    }

    @OnClick({R.id.btn_save, R.id.btn_close, R.id.spiner_unit})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                EventBus.getDefault().postSticky(new ListUserSelectEvent(sendUserSelected()));
                dismiss();
                break;
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.spiner_unit:
                if (layoutUnit.getVisibility() == View.GONE) {
                    layoutUnit.setVisibility(View.VISIBLE);
                    if (lstUnit != null && lstUnit.size() > 0) {
                        List<PersonSendNotifyInfo> lstUnitClone = new ArrayList<>();
                        for (PersonSendNotifyInfo notifyInfo : lstUnit) {

                            PersonSendNotifyInfo objClone = new PersonSendNotifyInfo();
                            objClone.setId(notifyInfo.getId());
                            objClone.setName(notifyInfo.getName());
                            objClone.setParentId(notifyInfo.getParentId());
                            objClone.setLevel(notifyInfo.getLevel());
                            objClone.setEmpId(notifyInfo.getEmpId());
                            objClone.setUnitId(notifyInfo.getUnitId());
                            objClone.setTrace(notifyInfo.isTrace());
                            objClone.setChildrenList(notifyInfo.getChildrenList());

                            lstUnitClone.add(objClone);
                        }
                        displayTreeView(lstUnitClone, 2, layoutUnit);
                    }
                } else {
                    layoutUnit.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectUnitForPersonEvent event) {
        /* Do something */
        if (event != null) {
            layoutUnit.setVisibility(View.GONE);
            spinerUnit.setText(event.getUnitSelected().getName());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void displayTreeView(List<PersonSendNotifyInfo> personSendNotifyInfoList,
                                 int typeDisplay, ViewGroup viewGroup) {

        List<PersonSendNotifyInfo> listData = new ArrayList<>();
        List<PersonSendNotifyInfo> listDataParent = new ArrayList<>();

        viewGroup.removeAllViews();
        root = TreeNode.root();
        rootUnit = TreeNode.root();
        if (personSendNotifyInfoList == null)
            return;
        if (typeDisplay == 1) {
            buildTreeData(personSendNotifyInfoList, 0, root);
            treeView = new TreeView(root, activity, new MyNodeViewCreateWorkCustomFactory());
            View view = treeView.getView();
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewGroup.addView(view);

            treeView.expandLevel(0);
        } else if (typeDisplay == 2) {
            for (int i = 0; i < personSendNotifyInfoList.size(); i++) {
                if (personSendNotifyInfoList.get(i).getParentId() == null ||
                        personSendNotifyInfoList.get(i).getParentId().equals("null")) {
                    listData = buildUnitTree(personSendNotifyInfoList, null);
                    break;
                } else {
                    boolean isExisted = false;
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyInfoList.get(i).getParentId())) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        listDataParent.addAll(buildUnitTree(personSendNotifyInfoList, personSendNotifyInfoList.get(i).getParentId()));
                    }
                }
            }
            if (listData != null && listData.size() > 0) {
                buildTreeData(listData, 0, rootUnit);
            } else if (listDataParent != null && listDataParent.size() > 0) {
                buildTreeData(listDataParent, 0, rootUnit);
            }
            treeViewUnit = new TreeView(rootUnit, activity, new MyNodeViewUnitCreateWorkFactory());
            View view = treeViewUnit.getView();
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewGroup.addView(view);

            treeViewUnit.expandLevel(0);
        }


    }

    private List<PersonSendNotifyInfo> buildUnitTree(List<PersonSendNotifyInfo> personSendTreeNodeNotifyInfoList, final String id) {

        final List<PersonSendNotifyInfo> results = new ArrayList<>();
        if (personSendTreeNodeNotifyInfoList == null) {
            return null;
        }
        for (PersonSendNotifyInfo unit : personSendTreeNodeNotifyInfoList) {
            if (!unit.isTrace() && (unit.getParentId() == id || (unit.getParentId() != null &&
                    unit.getParentId().equalsIgnoreCase(id)))) {
                unit.setTrace(true);
                PersonSendNotifyInfo dicItem = new PersonSendNotifyInfo();
                dicItem.setId(unit.getId());
                dicItem.setName(unit.getName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChildrenList(buildUnitTree(personSendTreeNodeNotifyInfoList, unit.getId()));
                results.add(dicItem);
            }
        }
        return results;
    }

    private void buildTreeData(List<PersonSendNotifyInfo> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        if (level > 2) {
            Log.e("buildTreeData", level + ((PersonSendNotifyInfo) root.getValue()).getName());
        }
        for (PersonSendNotifyInfo itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
        }
    }

    private List<ObjectAssignRequest> sendUserSelected() {
        List<TreeNode> selectedNodes = treeView.getSelectedXLCNodes();
        List<TreeNode> selectedXEMNodes = treeView.getSelectedXEMNodes();
        List<TreeNode> selectedPHNodes = treeView.getSelectedPHNodes();
        List<ObjectAssignRequest> personList = new ArrayList<>();


        if (selectedNodes != null && selectedNodes.size() > 0) {

            for (int i = 0; i < selectedNodes.size(); i++) {

                ObjectAssignRequest personXLC = new ObjectAssignRequest();
                personXLC.setEndDate(strEndDate);
                personXLC.setNameUser(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName());
                personXLC.setStartDate(getCurrentDate());
                personXLC.setUnitId(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getUnitId());
                personXLC.setUserId(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getEmpId());
                personXLC.setVaiTro("1");
                if (personXLC != null) {
                    personList.add(personXLC);
                }
            }
        }
        if (selectedXEMNodes != null && selectedXEMNodes.size() > 0) {

            for (int i = 0; i < selectedXEMNodes.size(); i++) {
                ObjectAssignRequest personXEM = new ObjectAssignRequest();
                personXEM.setEndDate(strEndDate);
                personXEM.setNameUser(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getName());
                personXEM.setStartDate(getCurrentDate());
                personXEM.setUnitId(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getUnitId());
                personXEM.setUserId(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getEmpId());
                personXEM.setVaiTro("3");
                if (personXEM != null) {
                    personList.add(personXEM);
                }
            }
        }
        if (selectedPHNodes != null && selectedPHNodes.size() > 0) {

            for (int i = 0; i < selectedPHNodes.size(); i++) {
                ObjectAssignRequest personPHN = new ObjectAssignRequest();
                personPHN.setEndDate(strEndDate);
                personPHN.setNameUser(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getName());
                personPHN.setStartDate(getCurrentDate());
                personPHN.setUnitId(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getUnitId());
                personPHN.setUserId(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getEmpId());
                personPHN.setVaiTro("2");
                if (personPHN != null) {
                    personList.add(personPHN);
                }
            }
        }
        return personList;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c);
    }

    public void updatePersonList(List<PersonSendNotifyInfo> lstPersonByUnit) {
        this.lstPersonByUnit = lstPersonByUnit;
        viewGroup.removeAllViews();
        displayTreeView(this.lstPersonByUnit, 1, viewGroup);
    }
}
