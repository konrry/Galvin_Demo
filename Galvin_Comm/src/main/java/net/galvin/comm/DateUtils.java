package net.galvin.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/8.
 */
public class DateUtils {

    private static String DATE_FORMATE = "yyyy-MM-dd hh:mm:ss";

    public static Date str2Date(String strDate){
        if(StringUtils.isEmpty(strDate)){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {

        }
        return date;
    }

}
