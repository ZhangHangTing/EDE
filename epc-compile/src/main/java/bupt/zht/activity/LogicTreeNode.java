package bupt.zht.activity;

import bupt.zht.EpcObject;

import java.io.Serializable;

/**
 * @author zhanghangting
 * @date 2018/11/11 12:05
 */
public class LogicTreeNode implements Serializable {

    private LogicTreeNode left;
    private LogicTreeNode right;
    private EpcObject object;

    public LogicTreeNode(EpcObject object) {
        this.left = null;
        this.right = null;
        this.object = object;
    }

    public LogicTreeNode getLeft() {
        return left;
    }

    public void setLeft(LogicTreeNode left) {
        this.left = left;
    }

    public LogicTreeNode getRight() {
        return right;
    }

    public void setRight(LogicTreeNode right) {
        this.right = right;
    }

    public EpcObject getEpcObject(){
        return object;
    }
}
