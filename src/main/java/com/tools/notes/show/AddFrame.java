package com.tools.notes.show;

import com.tools.notes.model.Things;
import com.tools.notes.services.ThingsService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class AddFrame extends JFrame{
    private String action;

    AddFrame() {
        init();
    }

    //登录界面初始化
    public void init() {
        JFrame frame = new JFrame("添加事件");
        frame.setLayout(null);
        JLabel timeStr = new JLabel("时间:");
        timeStr.setBounds(10, 10, 50, 25);
        frame.add(timeStr);
        JLabel thingsStr = new JLabel("事件:");
        thingsStr.setBounds(10, 50, 50, 25);
        frame.add(thingsStr);
        JTextField userID = new JTextField();
        userID.setBounds(55, 10, 150, 25);
        frame.add(userID);
        JTextField password = new JTextField();
        password.setBounds(55, 50, 150, 25);
        frame.add(password);
        JButton buttonYes = new JButton("确认");
        buttonYes.setBounds(5, 90, 70, 25);
        frame.add(buttonYes);
        JButton buttonNO = new JButton("取消");
        buttonNO.setBounds(135, 90, 70, 25);
        frame.add(buttonNO);
        frame.setBounds(400, 100, 230, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //为登录按钮添加监听器
         buttonYes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (userID.getText().strip().length() == 0) {
                    JOptionPane.showMessageDialog(null, "输入日期不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    //清除密码框中的信息
                    password.setText("");
                    //清除账号框中的信息
                    userID.setText("");
                } else {
                    if (password.getText().strip().length() == 0) {
                        JOptionPane.showMessageDialog(null, "输入事件不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                        //清除密码框中的信息
                        password.setText("");
                        //清除账号框中的信息
                        userID.setText("");
                    } else {
                        if(!isValidDate(userID.getText())){
                            JOptionPane.showMessageDialog(null, "输入日期格式不正确", "错误", JOptionPane.ERROR_MESSAGE);
                            //清除密码框中的信息
                            password.setText("");
                            //清除账号框中的信息
                            userID.setText("");
                        }
                        Timestamp time = Timestamp.valueOf(userID.getText());
                        String things = password.getText();
                        //创建一个Admin用户，把输入框中的用户名密码和提出来
                        Things thingO = new Things();
                        thingO.setDate(time);
                        thingO.setThings(things);
                        try {
                            int insert = new ThingsService().insert(thingO);
                            if (insert >= 1) {
                                JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                                //清除密码框中的信息
                                password.setText("");
                                //清除账号框中的信息
                                userID.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "添加失败", "失败", JOptionPane.ERROR_MESSAGE);
                                //清除密码框中的信息
                                password.setText("");
                                //清除账号框中的信息
                                userID.setText("");
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "数据库错误", "错误", JOptionPane.ERROR_MESSAGE);
                            //清除密码框中的信息
                            password.setText("");
                            //清除账号框中的信息
                            userID.setText("");
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            private boolean isValidDate(String str) {
                 boolean convertSuccess = true;
                 // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
                 SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 try {
                     formatDateTime.parse(str);
                     convertSuccess = true;
                     return convertSuccess;
                 } catch (ParseException e) {
                     // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                     convertSuccess = false;
                 }

                 SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                 try {
                     formatDate.parse(str);
                     convertSuccess = true;
                     return convertSuccess;
                 } catch (ParseException e) {
                     // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                     convertSuccess = false;
                 }
                 return convertSuccess;
             }
         });
         
         //为注册按钮添加监听器
         buttonNO.addActionListener(e -> frame.dispose());
    }
}


