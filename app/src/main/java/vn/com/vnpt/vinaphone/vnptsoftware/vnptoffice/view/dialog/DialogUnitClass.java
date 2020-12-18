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

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeViewTaoCV;
import mehdi.sakout.fancybuttons.FancyButton;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.request.ObjectAssignRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.ListUnitSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.UserSelectEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.treeview.MyNodeViewCreateWorkCustomTaoCVFactory;

public class DialogUnitClass extends Dialog {
    @BindView(R.id.layout_unit)
    ViewGroup viewGroup;
    @BindView(R.id.btn_save)
    FancyButton btnSave;
    @BindView(R.id.btn_close)
    FancyButton btnClose;
    private TreeNode root;
    private TreeViewTaoCV treeView;
    private String strEndDate = "";
    private List<PersonSendNotifyInfo> personSendNotifyInfoList = new ArrayList<>();
    private Activity activity;
    List<ObjectAssignRequest> listSeleted;
    List<ObjectAssignRequest> listRemove;

    public DialogUnitClass(@NonNull Context context) {
        super(context);

    }

    public DialogUnitClass(@NonNull Context context, String strEndDate, List<PersonSendNotifyInfo> personSendNotifyInfoList, List<ObjectAssignRequest> listSeleted) {
        super(context);
        activity = (Activity) context;
        this.strEndDate = strEndDate;
        this.listSeleted = listSeleted;
        for (PersonSendNotifyInfo notifyInfo : personSendNotifyInfoList) {

            PersonSendNotifyInfo objClone = new PersonSendNotifyInfo();
            objClone.setId(notifyInfo.getId());
            objClone.setName(notifyInfo.getName());
            objClone.setParentId(notifyInfo.getParentId());
            objClone.setLevel(notifyInfo.getLevel());
            objClone.setEmpId(notifyInfo.getEmpId());
            objClone.setUnitId(notifyInfo.getUnitId());
            objClone.setTrace(notifyInfo.isTrace());
            objClone.setChildrenList(notifyInfo.getChildrenList());
            this.personSendNotifyInfoList.add(objClone);
        }
    }

    public DialogUnitClass(@NonNull Context context, String strEndDate, List<PersonSendNotifyInfo> personSendNotifyInfoList) {
        super(context);
        activity = (Activity) context;
        this.strEndDate = strEndDate;
        for (PersonSendNotifyInfo notifyInfo : personSendNotifyInfoList) {

            PersonSendNotifyInfo objClone = new PersonSendNotifyInfo();
            objClone.setId(notifyInfo.getId());
            objClone.setName(notifyInfo.getName());
            objClone.setParentId(notifyInfo.getParentId());
            objClone.setLevel(notifyInfo.getLevel());
            objClone.setEmpId(notifyInfo.getEmpId());
            objClone.setUnitId(notifyInfo.getUnitId());
            objClone.setTrace(notifyInfo.isTrace());
            objClone.setChildrenList(notifyInfo.getChildrenList());

            this.personSendNotifyInfoList.add(objClone);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_group_select);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
        displayTreeView(personSendNotifyInfoList);

    }

    private void displayTreeView(List<PersonSendNotifyInfo> personSendNotifyInfoList) {
        listRemove = new ArrayList<>();
        List<PersonSendNotifyInfo> listData = new ArrayList<>();
        List<PersonSendNotifyInfo> listDataParent = new ArrayList<>();


        root = TreeNode.root();
        if (personSendNotifyInfoList == null)
            return;
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
            buildTreeData(listData, 0, root);
        } else if (listDataParent != null && listDataParent.size() > 0) {
            buildTreeData(listDataParent, 0, root);
        }
        treeView = new TreeViewTaoCV(root, activity, new MyNodeViewCreateWorkCustomTaoCVFactory());
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);
        if (listSeleted != null && listSeleted.size() > 0)
            setSeletedRole(treeView);
        treeView.expandLevel(0);
    }

    private void setSeletedRole(TreeViewTaoCV treeView) throws NullPointerException {
        if (treeView != null) {
            List<TreeNode> treeNodes = treeView.getAllNodes();
            for (TreeNode treeNode : treeNodes) {
                PersonSendNotifyInfo personSendNotifyInfo = (PersonSendNotifyInfo) treeNode.getValue();
                for (int i = 0; i < listSeleted.size(); i++) {
                    if(listSeleted.get(i).getUserId()==null||listSeleted.get(i).getUserId().length()==0)
                        if ( personSendNotifyInfo.getId().equalsIgnoreCase(listSeleted.get(i).getUnitId())) {
                            checkRole(treeNode, listSeleted.get(i).getVaiTro());
                            expandTreeView(treeNode);
                            listRemove.add(listSeleted.get(i));
                        }
                }
            }
        }
    }

    private void checkRole(TreeNode treeNode, String role) {
        if (role.equals("1")) {
            treeNode.setSelectedXLC(true);
        } else if (role.equals("2")) {
            treeNode.setSelectedPH(true);
        } else if (role.equals("3")) {
            treeNode.setSelectedXEM(true);
        }
    }

    private void expandTreeView(TreeNode treeNode) {
        if (treeNode.getParent() == null) {
            return;
        } else {
            if (!treeNode.isExpanded()) treeNode.setExpanded(true);
            expandTreeView(treeNode.getParent());
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

    private List<ObjectAssignRequest> sendUnitSelected() {
        for (ObjectAssignRequest o : listRemove)
            EventBus.getDefault().postSticky(new UserSelectEvent(o, 3));
        List<TreeNode> selectedNodes = treeView.getSelectedXLCNodes(); // XLC
        List<TreeNode> selectedXEMNodes = treeView.getSelectedXEMNodes();  // XEM
        List<TreeNode> selectedPHNodes = treeView.getSelectedPHNodes(); //PH

        List<ObjectAssignRequest> personList = new ArrayList<>();

        if (selectedNodes != null && selectedNodes.size() > 0) {

            for (int i = 0; i < selectedNodes.size(); i++) {

                ObjectAssignRequest personXLC = new ObjectAssignRequest();
                personXLC.setEndDate(strEndDate);
                personXLC.setNameUser(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName());
                personXLC.setStartDate(getCurrentDate());
                personXLC.setUnitId(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getId());
                personXLC.setUserId("");
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
                personXEM.setUnitId(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getId());
                personXEM.setUserId("");
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
                personPHN.setUnitId(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getId());
                personPHN.setUserId("");
                personPHN.setVaiTro("2");
                if (personPHN != null) {
                    personList.add(personPHN);
                }
            }
        }
        //     treeView.deselectAll();
        return personList;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c);
    }

    @OnClick({R.id.btn_save, R.id.btn_close})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                EventBus.getDefault().postSticky(new ListUnitSelectEvent(sendUnitSelected()));
                dismiss();
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
