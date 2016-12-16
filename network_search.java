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
        word = word.replaceAll(" ","");
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
        word = word.replaceAll(" ","");
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

    String jinshan_search(String word)
    {
        word = word.replaceAll(" ","");
        String ret = "";
        try
        {
            String search_url = "http://www.iciba.com/" + word;

            URL url = new URL(search_url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;

            int num = 0;
            while ((data = reader.readLine()) != null)
            {
                String result = blank_del(data);
                int index = result.indexOf("<div class=\"base-speak\">");

                if(index >= 0)
                    break;
            }
            data = reader.readLine();
            data = reader.readLine();

            String yinbiao1 = "";
            yinbiao1 = data.replace("<span>","");
            yinbiao1 = yinbiao1.replace("</span>","");
            yinbiao1 = yinbiao1.replace("</span>","");

            data = reader.readLine();
            data = reader.readLine();
            data = reader.readLine();
            data = reader.readLine();

            String yinbiao2 = "";
            yinbiao2 = data.replace("<span>","");
            yinbiao2 = yinbiao2.replace("</span>","");
            yinbiao2 = yinbiao2.replace("</span>","");

            String yinbiao = yinbiao1.replaceAll(" ","") + ", " + yinbiao2.replaceAll(" ","");

            String result = "";

            while ((data = reader.readLine()) != null)
            {
                String res = blank_del(data);
                int index = res.indexOf("<li class=\"clearfix\">");

                if (index >= 0)
                    break;
            }


            while ((data = reader.readLine()) != null)
            {
                String res = blank_del(data);
                int index = res.indexOf("</ul>");

                if (index >= 0)
                {
                    ret = "金山词典为您提供" + word + "的释义\n" + yinbiao + "\n" + result;
                    return ret;
                }

                String pro = res.replace("</span>","");
                pro = pro.replace("<span class=\"prop\">","");
                pro = pro.replace("<span>","");
                pro = pro.replace("</p>","");
                pro = pro.replace("<p>","");
                pro = pro.replace("</li>","");
                pro = pro.replace(" ","");
                pro = pro.replace("<li class=\"clearfix\">","");

                if(pro.length()>0)
                    result = result + pro + "\n";
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
