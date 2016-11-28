/**
 * Created by Liu on 2016/11/26.
 */

import java.util.*;

public class database_server {
    public database_server()
    {

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
