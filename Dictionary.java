/**
 * Created by Zhou on 2016/11/26.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


//仅用于测试，不再更新
public class Dictionary extends JFrame{
    private JTextField input;            //输入框
    private JButton search, user, card, like1, like2, like3;              //搜索按钮, 分享按钮， 点赞按钮
    private JTextArea output1, output2, output3, blank1, blank2;     //输出内容
    private JScrollPane result1, result2, result3;                //查询结果输出
    private JCheckBox baidu, youdao, bing;
    private boolean bd, yd, bg;                    //记录复选框是否被选中

    public void like()                       //发送数据库更新请求，待完善
    {

    }

    public void search_word()
    {
        String word = input.getText();

        network_search net = new network_search();

        output1.setText("");
        output2.setText("");
        output3.setText("");

        if(bd == true)
            output1.setText(net.baidu_search(word));
        if(yd == true)
            output2.setText(net.youdao_search(word));
        if(bg == true)
            output3.setText(net.bing_search(word));

        //System.out.println(word);
        //System.out.println(bd + "  " + yd + "  "+ bg);
    }

    private void set_in()              //登陆窗口函数，待完善
    {

    }

    public Dictionary()
    {
        super("网络词典");

        bd = false;
        yd = false;
        bg = false;

        //建立图形界面的各个组件的对象及相关信息
        blank1 = new JTextArea(1,20);
        blank1.setFont(new Font("宋体", Font.PLAIN, 25));
        blank1.setText("                  My Dictionary");
        blank1.setEditable(false);

        blank2 = new JTextArea(1,15);
        blank2.setFont(new Font("宋体", Font.PLAIN, 20));
        blank2.setText("     输入框");
        blank2.setBackground(Color.cyan);
        blank2.setEditable(false);

        //输入框设置
        input = new JTextField(40);
        input.setFont(new Font("宋体", Font.PLAIN, 20));

        //按钮初始化
        search = new JButton("查询");
        search.setFont(new Font("宋体", Font.PLAIN, 18));
        user = new JButton("查看当前在线用户");
        user.setFont(new Font("宋体", Font.PLAIN, 18));
        card = new JButton("制作单词卡并分享");
        card.setFont(new Font("宋体", Font.PLAIN, 18));
        like1 = new JButton("赞");
        like1.setFont(new Font("宋体", Font.PLAIN, 18));
        like2 = new JButton("赞");
        like2.setFont(new Font("宋体", Font.PLAIN, 18));
        like3 = new JButton("赞");
        like3.setFont(new Font("宋体", Font.PLAIN, 18));

        //添加对点击按钮的监听事件
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                search_word();
            }
        });

        like1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                like();
            }
        });

        like2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                like();
            }
        });

        like3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                like();
            }
        });

        //添加输出结果显示区域并将其置于JScrollPane中来实现滚动条功能
        output1 = new JTextArea(7, 57);
        output1.setEditable(false);
        output1.setFont(new Font("宋体", Font.PLAIN, 17));
        output1.setForeground(Color.BLUE);
        output1.setBorder (BorderFactory.createLineBorder(Color.gray,1));
        output1.setLineWrap(true);

        output2 = new JTextArea(7, 57);
        output2.setEditable(false);
        output2.setFont(new Font("宋体", Font.PLAIN, 17));
        output2.setForeground(Color.BLUE);
        output2.setBorder (BorderFactory.createLineBorder(Color.gray,1));
        output2.setLineWrap(true);

        output3 = new JTextArea(7, 57);
        output3.setEditable(false);
        output3.setFont(new Font("宋体", Font.PLAIN, 17));
        output3.setForeground(Color.BLUE);
        output3.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        output3.setLineWrap(true);

        //纵向滚动条一直出现，横向仅在有需要时出现
        result1 = new JScrollPane(output1, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        result2 = new JScrollPane(output2, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        result3 = new JScrollPane(output3, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //初始化并设置复选框
        baidu = new JCheckBox("百度");
        baidu.setMnemonic(KeyEvent.VK_C);
        baidu.setSelected(false);
        baidu.setFont(new Font("宋体", Font.PLAIN, 22));

        youdao= new JCheckBox("有道");
        youdao.setMnemonic(KeyEvent.VK_G);
        youdao.setSelected(false);
        youdao.setFont(new Font("宋体", Font.PLAIN, 22));

        bing = new JCheckBox("必应");
        bing.setMnemonic(KeyEvent.VK_H);
        bing.setSelected(false);
        bing.setFont(new Font("宋体", Font.PLAIN, 22));

        //添加复选框监听事件
        baidu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(baidu.isSelected()) { bd = true; }
                else { bd = false; }
            }
        });

        youdao.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(youdao.isSelected()) { yd = true; }
                else { yd = false; }
            }
        });

        bing.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(bing.isSelected()) { bg = true; }
                else { bg = false; }
            }
        });

        //界面布局： BorderLayout 嵌套 GridLayout, GridLayout 嵌套 BorderLayout
        Panel buttons = new Panel();
        GridLayout layout1 = new GridLayout(1,2);
        buttons.setLayout(layout1);
        buttons.add(user);
        buttons.add(card);

        Panel search1 = new Panel();
        FlowLayout layout3 = new FlowLayout(1, 20, 5);
        search1.setLayout(layout3);
        search1.add(result1);
        search1.add(like1);

        Panel search2 = new Panel();
        FlowLayout layout4 = new FlowLayout(1, 20, 5);
        search2.setLayout(layout4);
        search2.add(result2);
        search2.add(like2);

        Panel search3 = new Panel();
        FlowLayout layout5 = new FlowLayout(1, 20, 5);
        search3.setLayout(layout5);
        search3.add(result3);
        search3.add(like3);

        Panel checkbox = new Panel();
        FlowLayout layout7 = new FlowLayout(1, 35, 5);
        checkbox.setLayout(layout7);
        checkbox.add(baidu);
        checkbox.add(youdao);
        checkbox.add(bing);

        Panel box_button = new Panel();
        BorderLayout layout6= new BorderLayout();
        box_button.setLayout(layout6);
        box_button.add(BorderLayout.CENTER, checkbox);
        box_button.add(BorderLayout.SOUTH, buttons);

        Panel south = new Panel();
        GridLayout layout2 = new GridLayout(4, 1);
        south.setLayout(layout2);
        south.add(search1);
        south.add(search2);
        south.add(search3);
        south.add(box_button);

        //使用BorderLayout布局管理
        BorderLayout layout = new BorderLayout(5,5);
        setLayout(layout);
        getContentPane().add(BorderLayout.NORTH,blank1);
        getContentPane().add(BorderLayout.WEST, blank2);
        getContentPane().add(BorderLayout.CENTER, input);
        getContentPane().add(BorderLayout.EAST, search);
        getContentPane().add(BorderLayout.SOUTH, south);

        //设置窗口参数
        setSize(650,720);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setVisible(true);
    }

    public static void main (String[] args) {
        new Dictionary();
    }
}