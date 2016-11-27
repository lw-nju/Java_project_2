/**
 * Created by Liu on 2016/11/24.
 */

import java.util.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class network_search {

    private boolean is_English(String str)
    {
        String str2 = str.replace(" ","");
        return str2.matches("[a-zA-Z',()-;]+");
    }

    private String blank_del(String str)
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

    String youdao_search(String word)
    {
        String ret = "";
        try
        {
            String search_url = "http://dict.youdao.com/w/eng/" + word;

            URL url = new URL(search_url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;

            int num = 0;
            while ((data = reader.readLine()) != null)
            {

                String result = blank_del(data);
                int index = result.indexOf("<span class=\"def\">");

                if(index >= 0)
                {
                    int index2 = result.indexOf("</span>");
                    result = result.substring(index + 18,index2);

                    if(!is_English(result))
                    {
                        if(num <= 2)
                        {
                            num ++;
                            ret = ret + result + '\n';
                        }
                    }
                }
                else
                {
                    boolean first = true;
                    index = result.indexOf("<span class=\"pos wordGroup collapse\">");

                    if(index < 0)
                    {
                        index = result.indexOf("<span class=\"pos wordGroup\">");
                        first = false;
                    }

                    if(index >= 0)
                    {
                        int index2 = result.indexOf("</span>");
                        if(first)
                            result = result.substring(index + 37,index2);
                        else
                            result = result.substring(index + 28,index2);

                        if(!result.contains("abbr."))
                        {
                            ret = ret + result + '\n';
                            num = 0;
                        }
                    }
                }

            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(ret == "")
            ret = "无法查询到单词释义，请检查单词拼写";

        return "有道词典为您提供" + word + "的释义\n" + ret;
    }

    String bing_search(String word)
    {
        String ret = "";
        try
        {
            String search_url = "http://cn.bing.com/dict/search?q=" + word;

            URL url = new URL(search_url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;

            int num = 0;
            while ((data = reader.readLine()) != null)
            {

                String result = blank_del(data);
                int index = result.indexOf("<meta name=\"description\" content=\"");
                if(index >= 0)
                {
                    int index2 = result.indexOf("<meta content=\"text/html;");
                    result = result.substring(index + 34,index2 - 5);

                    int index3 = result.indexOf("义");

                    String result1 = result.substring(0,index3 + 1);
                    String result2 = result.substring(index3 + 2,result.length());

                    int index4 = result2.indexOf("网络释义");

                    if(index4 < 0)
                        break;
                    String result3 = result2.substring(0,index4);

                    String sym = result3.substring(0,result3.lastIndexOf("]") + 1);
                    result3 = result3.substring(result3.lastIndexOf("]") + 2, result3.length());

                    String para = "";

                    while(true)
                    {
                        int index5 = result3.lastIndexOf(".");
                        if(index5 < 0)
                            break;

                        int type_length;
                        for(type_length = 0;;type_length++)
                        {
                            if(index5 - type_length < 0)
                                break;
                            if(result3.charAt(index5 - type_length) == ' ')
                                break;
                            if(result3.charAt(index5 - type_length) == '，')
                                break;
                        }

                        //System.out.println(type_length);
                        para = para + '\n' + result3.substring(index5 - type_length + 1, result3.length());
                        result3 = result3.substring(0, index5 - type_length + 1);
                    }

                    String result4 = result2.substring(index4, result2.length());

                    ret = ret + result1 + '\n' + sym + para + '\n' + result4 + '\n';
                }

            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(ret.replace("\n","") == "")
            ret = "无法查询到单词释义，请检查单词拼写";
        return ret;
    }

    String baidu_search(String word)
    {
        String ret = "";
        try
        {
            String search_url = "http://dict.baidu.com/s?wd=" + word;

            URL url = new URL(search_url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;

            int num = 0;
            while ((data = reader.readLine()) != null)
            {
                String result = blank_del(data);
                int index = result.indexOf("<div class=\"en-content\">");

                if(index >= 0)
                    break;
            }
            data = reader.readLine();

            String result = "";
            if(data != null)
            {
                int index = data.indexOf("<p>");
                if(index >= 0)
                    data = data.substring(index + 11,data.length());

                //System.out.println(data);

                result = data.replace("<p>","\n");
                result = result.replace("</strong>","");
                result = result.replace("<strong>","");
                result = result.replace("</p>"," ");
                result = result.replace("<span>","");
                result = result.replace("</span>","");
                result = result.replace("</div>","");
                result = result.replace("</a>"," ");

                result = result.replaceAll("<a href=[a-zA-Z=?\"/= ]+>","");

                ret = "百度词典为您提供" + word + "的释义\n" + result;
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(ret == "")
            ret = "无法查询到单词释义，请检查单词拼写";

        return ret;
    }
}
