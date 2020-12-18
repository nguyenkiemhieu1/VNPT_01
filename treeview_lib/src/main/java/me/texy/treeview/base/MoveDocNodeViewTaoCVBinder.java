package me.texy.treeview.base;

import android.view.View;

import me.texy.treeview.TreeNode;

/**
 * Created by linhl on 10/31/2018.
 */

public abstract class MoveDocNodeViewTaoCVBinder extends BaseNodeViewTaoCVBinder {

    public MoveDocNodeViewTaoCVBinder(View itemView) {
        super(itemView);
    }
    public abstract int getCheckXLCViewId();

    public abstract int getCheckPHViewId();

    public abstract int getCheckXEMViewId();

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
