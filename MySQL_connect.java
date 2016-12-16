/**
 * Created by Liu on 2016/12/13.
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//连接MySQL数据库进行数据插入，更新与查询
public class MySQL_connect {
    private Connection getConn() {              //连接数据库
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/Java_project";
        String username = "root";
        String password = "19950614";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int insert_user(String name_pass)
    {
        Connection conn = getConn();

        String name = name_pass.split("\t")[0];
        String password = name_pass.split("\t")[1];

        int i = 0;
        String sql = "insert into user (NAME,PASSWORD,SIGN_IN) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, "false");
            System.out.println(pstmt);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int insert_word(String word_index)
    {
        Connection conn = getConn();

        String word = word_index.split("\t")[0];
        String index = word_index.split("\t")[1];

        int i = 0;
        String sql = "insert into Like_num (WORD,NET,NUM) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.setString(2, index);
            pstmt.setString(3, "1");
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int update_signin(String user, String current) {
        Connection conn = getConn();
        int i = 0;

        String sql = "";
        if(current.contains("true"))
            sql = "update user set SIGN_IN='" + "false" + "' where NAME='" + user + "'";
        else
            sql = "update user set SIGN_IN='" + "true" + "' where NAME='" + user + "'";

        System.out.println(sql);

        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int update_like(String word_index, int current_num) {
        Connection conn = getConn();

        String word = word_index.split("\t")[0];
        String index = word_index.split("\t")[1];

        int i = 0;
        String sql = "update Like_num set NUM='" + (int)(current_num + 1) + "' where WORD='" + word + "' and NET='" + index +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public String get_password(String user_name) {
        Connection conn = getConn();
        String sql = "select PASSWORD from USER where NAME='" + user_name + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return rs.getString("PASSWORD");
            else
                return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get_signin(String user_name) {
        Connection conn = getConn();
        String sql = "select SIGN_IN from USER where NAME='" + user_name + "'";
        System.out.println(sql);
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return rs.getString("SIGN_IN");
            else
                return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int get_likenum(String word_index) {
        Connection conn = getConn();
        String word = word_index.split("\t")[0];
        String index = word_index.split("\t")[1];

        String sql = "select NUM from Like_num where WORD='" + word + "' and NET='"+index+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return rs.getInt("NUM");
            else
                return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String get_online() {
        Connection conn = getConn();
        String sql = "select NAME from USER where SIGN_IN='true'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder ret = new StringBuilder();
            while(rs.next())
            {
                ret.append(rs.getString("NAME"));
                //System.out.println("asdf "+ rs.getString("NAME"));
                ret.append("\n");
            }
            return ret.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    public static void main(String args[]) {
        MySQL_connect.insert_user("test" + "\t" + "012345678");
        MySQL_connect.insert_user("test2" + "\t" + "012345678");
        MySQL_connect.insert_user("test3" + "\t" + "012345678");
        MySQL_connect.insert_user("test4" + "\t" + "012345678");
        String sign = MySQL_connect.get_signin("test");
        MySQL_connect.update_signin("test", sign);
        MySQL_connect.update_signin("test2", "false");
        MySQL_connect.update_signin("test3", "false");
        MySQL_connect.update_signin("test4", "false");
        MySQL_connect.insert_word("game" + "\t" + "2");
        int num = MySQL_connect.get_likenum("game" + "\t" + "2");
        MySQL_connect.update_like("game" + "\t" + "2",num);
        System.out.println(MySQL_connect.get_password("test"));
        System.out.println(MySQL_connect.get_likenum("game" + "\t" + "2"));
        System.out.println(MySQL_connect.get_signin("test"));
        System.out.println("\n" + MySQL_connect.get_online());
    }*/
}
