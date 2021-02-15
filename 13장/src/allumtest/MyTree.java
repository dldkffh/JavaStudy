package allumtest;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
public class MyTree {
 JFrame myFrame;
 
 public MyTree(String title){
  myFrame = new JFrame(title);
  Container con = myFrame.getContentPane();
  
  DefaultMutableTreeNode root = new DefaultMutableTreeNode("루트노드요"); 
  DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("1번째자식");
  DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("2번째 자식");
  DefaultMutableTreeNode child1_child1 = new DefaultMutableTreeNode("1번째자식의 1번째자식");
  DefaultMutableTreeNode child1_child2 = new DefaultMutableTreeNode("1번째자식의 2번째자식");
  DefaultMutableTreeNode child2_child1 = new DefaultMutableTreeNode("2번째자식의 1번째자식");
  DefaultMutableTreeNode child2_child2 = new DefaultMutableTreeNode("2번째자식의 2번째자식");
  
  root.add(child1); root.add(child2);
  child1.add(child1_child1); child1.add(child1_child2);
  child2.add(child2_child1); child2.add(child2_child2);
  JTree myTree = new JTree(root);
  con.setLayout(new BorderLayout());
  con.add(myTree);
  
  myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  myFrame.setSize(300, 200);
  myFrame.setVisible(true);
 }
 
 public static void main(String[] args) {
  MyTree t = new MyTree("트리만들기");
 }
}

