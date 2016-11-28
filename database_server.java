/**
 * Created by Liu on 2016/11/26.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class database_server extends JFrame{
    private JTextArea text1, text2;

    public database_server()
    {
        text1 = new JTextArea(1,50);
        text1.setFont(new Font("宋体", Font.PLAIN, 20));
        text1.setText("        服务器端图形界面：  以下是相关提示信息");
        text1.setEditable(false);

        text2 = new JTextArea(50,50);
        text2.setFont(new Font("宋体", Font.PLAIN, 18));
        text2.setBackground(Color.cyan);
        text2.setEditable(false);

        BorderLayout layout = new BorderLayout(5,5);
        setLayout(layout);
        getContentPane().add(BorderLayout.NORTH, text1);
        getContentPane().add(BorderLayout.SOUTH, text2);

        setSize(550,650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setVisible(true);
    }

    public String get_like_num()           //从数据库获取点赞数目
    {
        return "";
    }

    public boolean update_like_num()        //更新数据库中信息
    {
        return true;
    }

    public String get_password()        //在数据库中获取密码
    {
        return "";
    }

    public boolean send_like_message()     //发送点赞数相关的信息
    {
        return true;
    }

    public boolean send_sign_message()
    {
        return true;
    }

    public void receive_message()
    {

    }

    public static void main(String args[])
    {
         new database_server();
    }
}
