/**
 * Created by Liu on 2016/12/15.
 */

import java.io.Serializable;

public class MSG implements Serializable
{
    enum m
    {  SIGN_IN,
        LIKE_NUM,
        SEARCH,
        SIGN_UP,
        UPDATE_LIKE
    } m msg;

    String information;

    MSG(m msg)
    {
        this.msg = msg;
    }

    MSG(){}
}