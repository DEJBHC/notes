package com.tools.notes.show;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Entity;
import com.tools.notes.model.Things;
import com.tools.notes.services.ThingsService;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MainFrame extends JDialog{
    private JPopupMenu menu=new JPopupMenu();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    // 设置窗体默认大小,使其适应屏幕大小
    private int DEFAULE_WIDTH = (int) (toolkit.getScreenSize().getWidth() * 0.4);
    private int DEFAULE_HEIGH = (int) (toolkit.getScreenSize().getHeight() * 0.5);
    // 设置窗体位置,使其在屏幕居中
    private int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
    private int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    int mouseAtX = 0;
    int mouseAtY = 0;
    private JButton b2;

    public MainFrame() throws SQLException {
        //设置窗体的标题栏不可见
        setUndecorated(true);
        //设置窗体可拖动
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //获取点击鼠标时的坐标
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                //设置拖拽后，窗口的位置
                setLocation((e.getXOnScreen() - mouseAtX), (e.getYOnScreen() - mouseAtY));
            }
        });
        List<String> texts = getText();
        JPanel contentPane = new JPanel();
        int windowHeight = texts.size()*60;
        //实例化简单组件
        for (int i = 0; i <texts.size(); i++) {
            JLabel label = new JLabel(texts.get(i), JLabel.CENTER);
            label.setBounds(0, i*50, 450, 45);
            label.setFont(new Font("华文新魏", Font.BOLD, 20));
            contentPane.setLayout(null);
            contentPane.add(label);
        }
        setContentPane(contentPane);//设置ContentPane
        setSize(500, windowHeight);// 设置窗体默认大小,使其适应屏幕大小
        setLocation(0, 0);//设置窗体在屏幕中的位置
        setVisible(true); //设置窗体可见
        rightMouse();//添加右键菜单
        setAlwaysOnTop(true);//设置窗体置顶
    }

    private List<String> getText() throws SQLException {
        List<String> lines = new ArrayList<String>();
        List<Entity> entities = new ThingsService().queryFromDate(new Things(new Timestamp(System.currentTimeMillis())),new Things(DateUtil.tomorrow().toTimestamp()));
        for (Entity entity : entities) {
            lines.add(entity.get("date").toString()+"\t"+entity.get("things").toString()+"\n");
        }
        return lines;
    }

    private void rightMouse() {
        Items menuItems = getItems();
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()==MouseEvent.BUTTON3) {
                    //弹出右键菜单
                    menu.show(MainFrame.this, e.getX(), e.getY());
                }
            }
        });
        //功能实现
        funcs(menuItems);
    }

    private void funcs(Items menuItems) {
        //添加事件
        addThings(menuItems.mAdd());
        //删除事件
        removeThings(menuItems.mDel());
        //修改事件
        editThings(menuItems.mCha());
        //退出系统
        exitThings(menuItems.mExit());
    }

    private void exitThings(JMenuItem jMenuItem) {
        jMenuItem.addActionListener(e -> System.exit(0));
    }

    private void editThings(JMenuItem jMenuItem) {
        jMenuItem.addActionListener(e -> {
            new AddFrame();
        });
    }

    private void removeThings(JMenuItem jMenuItem) {
        jMenuItem.addActionListener(e -> {
            Object objResult = JOptionPane.showInputDialog(null, "输入删除内容", "删除", 2, null, null, null);
            String name = objResult.toString();
            if(name.trim().length() > 0) {
                try {
                    List<String> strings = removeItem(name);
                    if (strings.size() > 0) {
                        Things things = new Things();
                        things.setThings(name);
                        int delete = new ThingsService().delete(things);
                        if (delete > 0) {
                            JOptionPane.showMessageDialog(null, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败", "失败", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "事件不存在", "失败", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "数据库错误", "错误", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "请输入内容", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private List<String> removeItem(String name) throws SQLException {
        List<String> lines = new ArrayList<String>();
        Things things = new Things();
        things.setThings(name);
        List<Entity> entities = new ThingsService().queryFromThings(things);
        for (Entity entity : entities) {
            lines.add(entity.get("date").toString()+"\t"+entity.get("things").toString()+"\n");
        }
        return lines;
    }

    private static void addThings(JMenuItem menuItems) {
        menuItems.addActionListener(e -> {
            new AddFrame();
        });
    }

    private Items getItems() {
        JMenuItem mAdd, mCopy, mCha, mPaste, mDel,mExit;
        menu = new JPopupMenu();
        mAdd = new JMenuItem("添加事件(A)");
        menu.add(mAdd);
        mDel = new JMenuItem("删除事件(D)");
        menu.add(mDel);
        mCha = new JMenuItem("修改事件(C)");
        menu.add(mCha);
        mExit = new JMenuItem("退出(E)");
        menu.add(mExit);
        Items menuItems = new Items(mAdd, mCha, mDel, mExit);
        return menuItems;
    }



    private record Items(JMenuItem mAdd, JMenuItem mCha, JMenuItem mDel, JMenuItem mExit) {
    }
}
