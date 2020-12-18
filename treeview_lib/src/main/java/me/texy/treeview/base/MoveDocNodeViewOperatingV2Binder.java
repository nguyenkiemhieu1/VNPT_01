package me.texy.treeview.base;

import android.view.View;

import me.texy.treeview.TreeNode;

/**
 * Created by linhl on 10/31/2018.
 */

public abstract class MoveDocNodeViewOperatingV2Binder extends BaseNodeViewOperatingV2Binder {

    public MoveDocNodeViewOperatingV2Binder(View itemView) {
        super(itemView);
    }
    public abstract int getCheckXLCViewId();

    public abstract int getCheckPHViewId();

    public abstract int getCheckXEMViewId();

    public abstract int getTextViewId();
    /**
     * Do something when a node select or deselect（only triggered by clicked）
     *
     * @param treeNode
     * @param selected
     */
    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
