package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/11/11 12:05
 */
public class LogicTree {
    private MyTreeNode root;
    private class MyTreeNode{
        private MyTreeNode left;
        private MyTreeNode right;
        private EpcObject data;
        public MyTreeNode(EpcObject object){
            this.left = null;
            this.right = null;
            this.data = data;
        }
    }
    public static void main(String[] args){
        LogicTree logicTree = new LogicTree();
        // System.out.print(logicTree.root);
    }

}
