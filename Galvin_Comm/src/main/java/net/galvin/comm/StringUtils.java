package net.galvin.comm;

import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/9/28.
 */
public class StringUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    public static boolean isEmpty(String value){
        return value == null ? true : value.trim().length() <= 0;
    }

    public static Integer str2Integer(String object){
        Integer integerVal = null;
        try {
            integerVal = Integer.valueOf(String.valueOf(object));
        }catch (Exception e){
            LOGGER.error(ExceptionFormatUtil.getTrace(e));
        }
        return integerVal;
    }

}
