package net.galvin.comm;

/**
 * Created by Administrator on 2016/9/28.
 */
public class StringUtils {

    public static boolean isEmpty(String value){
        return value == null ? true : value.trim().length() <= 0;
    }

}
