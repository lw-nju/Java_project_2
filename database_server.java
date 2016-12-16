/**
 * Created by Liu on 2016/11/26.
 */

import java.util.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class database_server extends JFrame{
    private JTextArea text1, text2;
    private ServerSocket server;
    private int count = 0;
    private MSG temp;
    private PrintWriter writer;
    private ObjectInputStream input;
    private JScrollPane scroll;

    public void UI_boot()
    {
        text1 = new JTextArea(1,50);
        text1.setFont(new Font("宋体", Font.PLAIN, 20));
        text1.setText("           服务器端图形界面：  以下是相关提示信息");
        text1.setEditable(false);

        text2 = new JTextArea(30,35);
        text2.setFont(new Font("宋体", Font.PLAIN, 18));
        text2.setBackground(Color.cyan);
        text2.setEditable(false);
        text2.setText("");

        scroll = new JScrollPane(text2, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);

        BorderLayout layout = new BorderLayout(5,5);
        setLayout(layout);
        getContentPane().add(BorderLayout.NORTH, text1);
        getContentPane().add(BorderLayout.SOUTH, scroll);

        setSize(650,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setVisible(true);
    }

    public String get_like_num(String word)           //从数据库获取点赞数目
    {
        MySQL_connect sql = new MySQL_connect();
        int num1 = sql.get_likenum(word+"\t1");
        int num2 = sql.get_likenum(word+"\t2");
        int num3 = sql.get_likenum(word+"\t3");
        return num1 + "\t" + num2 + "\t" + num3;
    }

    public boolean update_like_num(String word_index)        //更新数据库中信息
    {
        MySQL_connect sql = new MySQL_connect();
        int current_num = sql.get_likenum(word_index);
        int i = 0;

        if(current_num == 0)
            i = sql.insert_word(word_index);
        else
            i = sql.update_like(word_index, current_num);

        return i>0;
    }

    public String get_password(String name)        //在数据库中获取密码
    {
        MySQL_connect sql = new MySQL_connect();
        return sql.get_password(name);
    }

    public boolean sign_up(String user_pass)
    {
        MySQL_connect sql = new MySQL_connect();
        int i = sql.insert_user(user_pass);
        return i>0;
    }

    public String sign_in_check(String user)
    {
        MySQL_connect sql = new MySQL_connect();
        return sql.get_signin(user);
    }

    public boolean sign_in(String user)
    {
        MySQL_connect sql = new MySQL_connect();
        return sql.update_signin(user,"false") > 0;
    }

    public boolean sign_out(String user)
    {
        MySQL_connect sql = new MySQL_connect();
        return sql.update_signin(user,"true") > 0;
    }

    public String get_online()
    {
        MySQL_connect sql = new MySQL_connect();
        return sql.get_online();
    }

    private void go() {
        try {
            server = new ServerSocket(5001);
            System.out.println("Server ok!");
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString();//获得本机IP    
            System.out.println("服务器IP:" + ip);
            while (true) {
                System.out.println("等待新的服务请求...");
                text2.append("\n等待新的服务请求...");
                text2.setCaretPosition(text2.getText().length());
                //System.out.println(text2.getText());
                Socket sock = server.accept();

                count++;
                System.out.println("接受第" + count + "个服务请求");
                text2.append("\n接受第" + count + "个服务请求");
                input = new ObjectInputStream(sock.getInputStream());
                temp = (MSG) input.readObject();
                System.out.println(temp.msg);
                input.close();

                switch (temp.msg)
                {
                    case SIGN_IN:
                        String user = temp.information.split("\t")[0];
                        String password = temp.information.split("\t")[1];
                        String pass = get_password(user);

                        System.out.println("fasfsad" + temp.information);
                        System.out.println("dsafs "+user+"  "+password + " " +pass);

                        String user_sign_in = sign_in_check(user);
                        String sign_in_info;
                        System.out.println(user_sign_in);
                        if(password.equals(pass))
                        {
                            if(user_sign_in.equals("true"))
                            {
                                text2.append("用户 " + user + " 重复登录！");
                                sign_in_info = "duplicate";
                            }
                            else
                            {
                                sign_in(user);
                                text2.append("用户 " + user + " 登录成功！");
                                sign_in_info = "success";
                            }
                        }
                        else
                        {
                            text2.append("用户 " + user + " 登陆不成功");
                            sign_in_info = "fail";
                        }
                        Socket socket1 = server.accept();
                        writer = new PrintWriter(socket1.getOutputStream());
                        writer.println(sign_in_info);
                        writer.close();
                        socket1.close();
                        break;
                    case LIKE_NUM:
                        String word = temp.information;
                        String like_num = get_like_num(word);
                        Socket socket2 = server.accept();
                        writer = new PrintWriter(socket2.getOutputStream());
                        writer.println(like_num);
                        writer.close();
                        socket2.close();
                        break;
                    case SEARCH:
                        word  = temp.information;
                        String out = "";
                        network_search search = new network_search();
                        String out1 = search.jinshan_search(word);
                        String out2 = search.bing_search(word);
                        String out3 = search.youdao_search(word);
                        out = out1 + "\t" + out2 + "\t" + out3;
                        Socket socket3 = server.accept();
                        writer = new PrintWriter(socket3.getOutputStream());
                        writer.println(out);
                        writer.close();
                        socket3.close();
                        break;
                    case SIGN_UP:
                        boolean sign = sign_up(temp.information);
                        String result;
                        if(sign)
                            result = "success";
                        else
                            result = "fail";

                        Socket socket4 = server.accept();
                        writer = new PrintWriter(socket4.getOutputStream());
                        writer.println(result);
                        writer.close();
                        socket4.close();
                        break;
                    case UPDATE_LIKE:
                        boolean update_sign = update_like_num(temp.information);
                        if(update_sign)
                            result = "success";
                        else
                            result = "fail";

                        Socket socket5 = server.accept();
                        writer = new PrintWriter(socket5.getOutputStream());
                        writer.println(result);
                        writer.close();
                        socket5.close();
                        break;
                    case SIGN_OUT:
                        user = temp.information;
                        user_sign_in = sign_in_check(user);
                        if(user_sign_in.equals("true"))
                        {
                            sign_out(user);
                            text2.append("用户 " + user + " 下线");
                            sign_in_info = "success";
                        }
                        else
                        {
                            text2.append("用户 " + user + " 下线");
                            sign_in_info = "fail";
                        }
                        Socket socket6 = server.accept();
                        writer = new PrintWriter(socket6.getOutputStream());
                        writer.println(sign_in_info);
                        writer.close();
                        socket6.close();
                        break;
                    case ONLINE:
                        String output = get_online();
                        Socket socket7 = server.accept();
                        writer = new PrintWriter(socket7.getOutputStream());
                        writer.println(output);
                        writer.close();
                        socket7.close();
                        break;
                    default:
                        text2.append("无法处理的消息，请检查");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        database_server ser = new database_server();
        ser.UI_boot();
        ser.go();
    }
}
