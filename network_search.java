/**
 * Created by Liu on 2016/11/24.
 */

import java.util.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class network_search {
    public static String blank_del(String str)
    {
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)!=' ' && str.charAt(i) != '\t')
            {
                str=str.substring(i,str.length());
                break;
            }
        }
        return str;
    }

    static String youdao_search(String word)
    {
        try
        {
            String search_url = "http://dict.youdao.com/w/eng/" + word;

            URL url = new URL(search_url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;
            while ((data = reader.readLine()) != null)
            {
                data = blank_del(data);
                int index = data.indexOf("<span class=\"def\">");

                if(index >= 0)
                    System.out.println(data);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    public static void main(String args[])
    {
        youdao_search("test");
    }
}
